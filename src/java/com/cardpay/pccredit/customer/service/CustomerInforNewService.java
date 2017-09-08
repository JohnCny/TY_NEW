package com.cardpay.pccredit.customer.service;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.multipart.MultipartFile;

import com.cardpay.pccredit.Sx.model.SxOutputData;
import com.cardpay.pccredit.common.Dictionary;
import com.cardpay.pccredit.common.UploadFileTool;
import com.cardpay.pccredit.customer.constant.CustomerInforConstant;
import com.cardpay.pccredit.customer.constant.CustomerInforDStatusEnum;
import com.cardpay.pccredit.customer.constant.WfProcessInfoType;
import com.cardpay.pccredit.customer.dao.CustomerInforDao;
import com.cardpay.pccredit.customer.dao.comdao.CustomerInforCommDao;
import com.cardpay.pccredit.customer.filter.CustomerInfoLszFilter;
import com.cardpay.pccredit.customer.filter.CustomerInforFilter;
import com.cardpay.pccredit.customer.filter.VideoAccessoriesFilter;
import com.cardpay.pccredit.customer.model.CIPERSONBASINFO;
import com.cardpay.pccredit.customer.model.CIPERSONBASINFOCOPY;
import com.cardpay.pccredit.customer.model.CUSTORMERINFOUPDATE;
import com.cardpay.pccredit.customer.model.CustomerCareersInformation;
import com.cardpay.pccredit.customer.model.CustomerFirsthendBase;
import com.cardpay.pccredit.customer.model.CustomerFirsthendBaseLocal;
import com.cardpay.pccredit.customer.model.CustomerFirsthendFamilyCc;
import com.cardpay.pccredit.customer.model.CustomerFirsthendFamilyCy;
import com.cardpay.pccredit.customer.model.CustomerFirsthendManage;
import com.cardpay.pccredit.customer.model.CustomerFirsthendRygl;
import com.cardpay.pccredit.customer.model.CustomerFirsthendSafe;
import com.cardpay.pccredit.customer.model.CustomerFirsthendStudy;
import com.cardpay.pccredit.customer.model.CustomerFirsthendWork;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.model.CustomerInforWeb;
import com.cardpay.pccredit.customer.model.MaintenanceLog;
import com.cardpay.pccredit.customer.model.TyMibusidataForm;
import com.cardpay.pccredit.customer.model.TyProductType;
import com.cardpay.pccredit.customer.model.TyRepayLsz;
import com.cardpay.pccredit.customer.model.TyRepayTkmx;
import com.cardpay.pccredit.customer.model.TyRepayYehzVo;
import com.cardpay.pccredit.datapri.service.DataAccessSqlService;
import com.cardpay.pccredit.intopieces.constant.Constant;
import com.cardpay.pccredit.intopieces.constant.IntoPiecesException;
import com.cardpay.pccredit.intopieces.filter.IntoPiecesFilter;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationContact;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationGuarantor;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationInfo;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationOther;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationProcess;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationRecom;
import com.cardpay.pccredit.intopieces.model.IntoPieces;
import com.cardpay.pccredit.intopieces.model.VideoAccessories;
import com.cardpay.pccredit.intopieces.service.IntoPiecesService;
import com.cardpay.pccredit.ipad.model.ProductAttribute;
import com.cardpay.pccredit.jnpad.dao.MonthlyStatisticsDao;
import com.cardpay.pccredit.jnpad.model.MonthlyStatisticsModel;
import com.cardpay.pccredit.manager.dao.ManagerPerformmanceDao;
import com.cardpay.pccredit.manager.form.ManagerPerformmanceForm;
import com.cardpay.pccredit.manager.service.AccountManagerParameterService;
import com.cardpay.pccredit.manager.service.CustomerApplicationInfoSynchScheduleService;
import com.cardpay.pccredit.postLoan.model.TyRarepaylistForm;
import com.cardpay.pccredit.riskControl.model.RiskCustomer;
import com.cardpay.pccredit.system.constants.NodeAuditTypeEnum;
import com.cardpay.pccredit.system.constants.YesNoEnum;
import com.cardpay.pccredit.system.model.Dict;
import com.cardpay.pccredit.system.model.NodeAudit;
import com.cardpay.pccredit.system.model.NodeControl;
import com.cardpay.pccredit.system.model.SystemUser;
import com.cardpay.pccredit.system.service.NodeAuditService;
import com.cardpay.pccredit.tools.CardFtpUtils;
import com.cardpay.pccredit.tools.DataFileConf;
import com.cardpay.pccredit.tools.ImportBankDataFileTools;
import com.cardpay.pccredit.tools.SPTxt;
import com.cardpay.pccredit.tools.UpdateCustomerTool;
import com.cardpay.workflow.models.WfProcessInfo;
import com.cardpay.workflow.models.WfStatusInfo;
import com.cardpay.workflow.models.WfStatusResult;
import com.cardpay.workflow.service.ProcessService;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.id.IDGenerator;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.QueryResult;

/**
 * 
 * @author shaoming
 *
 */
@Service
public class CustomerInforNewService {
	public Logger log = Logger.getLogger(UpdateCustomerTool.class);

	@Autowired
	private CommonDao commonDao;

	@Autowired
	private CustomerInforService  customerInforService;
	
	@Autowired
	private AccountManagerParameterService accountManagerParameterService;
	
	@Autowired
	PlatformTransactionManager txManager;
	
	//客户原始信息
	private String[] fileTxt = {"cxd_rygl.txt",  //人员管理
								"kkh_grxx.txt",  //客户基本信息表
								"kkh_grjtcy.txt",//客户家庭关系表
								"kkh_grjtcc.txt",//客户家庭财产表
								"kkh_grscjy.txt",//客户生产经营表
								"kkh_grgzll.txt",//客户工作履历表
								"kkh_grrbxx.txt",//客户入保信息表
								"cxd_dkcpmc.txt",//产品信息
								"kkh_hmdgl.txt"};//黑名单
								
	
	//流水账、余额汇总表、借据表
	private String[] fileTxtRepay ={"kdk_tkmx.txt",//借据表
									"kdk_yehz.txt",//余额汇总
									"kdk_lsz.txt",//流水
									"kkh_grxxll.txt",//客户学习表
									"kdk_jh.txt"};//还款计划
	
	
	
	private void deleteFile(){
		// 删除人员管理数据
		String sql1 = " truncate   table   ty_customer_rygl";
		commonDao.queryBySql(sql1, null);
		System.out.println(sql1);

		// 删除客户家庭关系表数据
		String sql2 = " truncate   table   ty_customer_family_cy";
		commonDao.queryBySql(sql2, null);
		System.out.println(sql2);

		// 删除客户家庭财产表数据
		String sql3 = " truncate   table   ty_customer_family_cc";
		commonDao.queryBySql(sql3, null);
		System.out.println(sql3);


		// 删除客户工作履历表数据
		String sql5 = " truncate   table   ty_customer_work";
		commonDao.queryBySql(sql5, null);
		System.out.println(sql5);

		// 删除客户生产经营表数据
		String sql6 = " truncate   table   ty_customer_manage";
		commonDao.queryBySql(sql6, null);
		System.out.println(sql6);

		// 删除客户入保信息表数据
		String sql7 = " truncate   table   ty_customer_safe";
		commonDao.queryBySql(sql7, null);
		System.out.println(sql7);

		// 删除产品信息数据
		String sql8 = " truncate   table   ty_product_type";
		commonDao.queryBySql(sql8, null);
		System.out.println(sql8);

		// 删除黑名单数据
		String sql9 = " truncate   table   f_agr_crd_xyk_cuneg";
		commonDao.queryBySql(sql9, null);
		System.out.println(sql9);
	}
	
	/**
	 * 基本信息 
	 * 自动 
	 */
	public void readFile(){
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		String dateString = format.format(new Date());
		log.info(dateString+"******************基本信息start********************"); 
	        String gzFile = CardFtpUtils.bank_ftp_down_path+File.separator+dateString;
	        //清空 
	        this.deleteFile();
	        for(int i=0;i<fileTxt.length;i++){
				String url = gzFile+File.separator+fileTxt[i];
				log.info(url);
				File f = new File(url);
				if(f.exists()){
						List<String> spFile = new ArrayList<String>();
						String fileN = "";
						//判断文件大小，超过50M的先分割
						if (f.exists() && f.isFile()){
							if(f.length()>10000000){
								int spCount = (int) (f.length()/10000000);
								SPTxt.splitTxt(url,spCount);
								int to = fileTxt[i].lastIndexOf('.');
						    	fileN = fileTxt[i].substring(0, to);
								for(int j=0;j<spCount;j++){
									spFile.add(fileN+"_"+j+".txt");
								}
							}else{
								int to = fileTxt[i].lastIndexOf('.');
						    	fileN = fileTxt[i].substring(0, to);
								spFile.add(fileN+".txt");
							}
						}
						for(String fn : spFile){
							try{
								if(fn.contains(fileN)) {
									if(fn.startsWith("cxd_rygl")){
										log.info("*****************人员管理参数表********************");  
										customerInforService.saveRyglDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("kkh_grxx")){
										log.info("*****************客户基本表********************");  
										customerInforService.saveBaseDataFile(gzFile+File.separator+fn,dateString);
										System.gc();
									}else if(fn.startsWith("kkh_grjtcy")){
										log.info("*****************客户家庭关系表********************");  
										customerInforService.saveCyDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("kkh_grjtcc")){
										log.info("*****************客户家庭财产表********************");  
										customerInforService.saveCcDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("kkh_grgzll")){
										log.info("*****************客户工作履历表********************");  
										customerInforService.saveWorkDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("kkh_grscjy")){
										log.info("*****************客户生产经营表********************");  
										customerInforService.saveManageDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("kkh_grrbxx")){
										log.info("*****************客户入保信息表********************");  
										customerInforService.saveSafeDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("cxd_dkcpmc")){
										log.info("*****************产品信息********************");  
										customerInforService.saveProductDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("kkh_hmdgl")){
										log.info("*****************黑名单********************");  
										customerInforService.saveHMDDataFile(gzFile+File.separator+fn,dateString);
									}
								} 
							}catch(Exception e){
								e.printStackTrace();
								//default
								this.updBtachtask("001","jb",dateString);
								throw new RuntimeException(e);
							}
						}
						f.delete();
						//success
						accountManagerParameterService.updBatchTaskFlow("100", "jb", dateString);
						
				}
	        }
	        log.info(dateString+"******************基本信息end********************");
	}
	
	
	/**
	 * 基本信息 
	 * 批处理重跑
	 */
	public void readFileByDate(String dateString){
			log.info(dateString+"******************基本信息start (批处理重跑)********************"); 
	        String gzFile = CardFtpUtils.bank_ftp_down_path+File.separator+dateString;
	        //清空 
	        this.deleteFile();
	        for(int i=0;i<fileTxt.length;i++){
				String url = gzFile+File.separator+fileTxt[i];
				log.info(url);
				File f = new File(url);
				if(f.exists()){
						List<String> spFile = new ArrayList<String>();
						String fileN = "";
						//判断文件大小，超过50M的先分割
						if (f.exists() && f.isFile()){
							if(f.length()>10000000){
								int spCount = (int) (f.length()/10000000);
								SPTxt.splitTxt(url,spCount);
								int to = fileTxt[i].lastIndexOf('.');
						    	fileN = fileTxt[i].substring(0, to);
								for(int j=0;j<spCount;j++){
									spFile.add(fileN+"_"+j+".txt");
								}
							}else{
								int to = fileTxt[i].lastIndexOf('.');
						    	fileN = fileTxt[i].substring(0, to);
								spFile.add(fileN+".txt");
							}
						}
						
						// 清空表
						
						for(String fn : spFile){
							try{
								if(fn.contains(fileN)) {
									if(fn.startsWith("cxd_rygl")){
										log.info("*****************人员管理参数表********************");  
										customerInforService.saveRyglDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("kkh_grxx")){
										log.info("*****************客户基本表********************");  
										customerInforService.saveBaseDataFile(gzFile+File.separator+fn,dateString);
										System.gc();
									}else if(fn.startsWith("kkh_grjtcy")){
										log.info("*****************客户家庭关系表********************");  
										customerInforService.saveCyDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("kkh_grjtcc")){
										log.info("*****************客户家庭财产表********************");  
										customerInforService.saveCcDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("kkh_grgzll")){
										log.info("*****************客户工作履历表********************");  
										customerInforService.saveWorkDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("kkh_grscjy")){
										log.info("*****************客户生产经营表********************");  
										customerInforService.saveManageDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("kkh_grrbxx")){
										log.info("*****************客户入保信息表********************");  
										customerInforService.saveSafeDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("cxd_dkcpmc")){
										log.info("*****************产品信息********************");  
										customerInforService.saveProductDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("kkh_hmdgl")){
										log.info("*****************黑名单********************");  
										customerInforService.saveHMDDataFile(gzFile+File.separator+fn,dateString);
									}
								} 
							}catch(Exception e){
								e.printStackTrace();
								//default
								this.updBtachtask("001","jb",dateString);
								throw new RuntimeException(e);
							}
						}
						f.delete();
						//success
						accountManagerParameterService.updBatchTaskFlow("100", "jb", dateString);
				}
	        }
	        log.info(dateString+"******************基本信息end (批处理重跑)********************");
	}
	
	
	private void deleteRepay() {
		// 删除余额汇总历史数据
		String sql1 = " truncate   table   ty_repay_yehz";
		commonDao.queryBySql(sql1, null);
		System.out.println(sql1);
		
		// 删除借据表历史数据
		String sql2 = " truncate   table   ty_repay_tkmx";
		commonDao.queryBySql(sql2, null);
		System.out.println(sql2);
		
		// 删除行内机构表历史数据
		String sql3 = " truncate   table   ty_org";
		commonDao.queryBySql(sql3, null);
		System.out.println(sql3);
		
		// 删除还款计划历史数据
		String sql4 = " truncate   table   ty_kdk_jh";
		commonDao.queryBySql(sql4, null);
		System.out.println(sql4);
		
		// 删除客户学习表数据
		String sql5 = " truncate   table   ty_customer_study";
		commonDao.queryBySql(sql5, null);
		System.out.println(sql5);
	}
	
	/**
	 * 贷款信息
	 * 自动
	 */
	public void readFileRepay() {
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		String dateString = format.format(new Date());
		String gzFile = CardFtpUtils.bank_ftp_down_path+File.separator+dateString;

		log.info(dateString+"******************开始读取贷款文件********************");  
		// 清空表数据
		this.deleteRepay();
        for(int i=0;i<fileTxtRepay.length;i++){
			String url = gzFile+File.separator+fileTxtRepay[i];
			log.info(url);
			File f = new File(url);
			if(f.exists()){
					List<String> spFile = new ArrayList<String>();
					String fileN = "";
					//判断文件大小，超过50M的先分割
					if (f.exists() && f.isFile()){
						if(f.length()>10000000){
							int spCount = (int) (f.length()/10000000);
							SPTxt.splitTxt(url,spCount);
							int to = fileTxtRepay[i].lastIndexOf('.');
					    	fileN = fileTxtRepay[i].substring(0, to);
							for(int j=0;j<spCount;j++){
								spFile.add(fileN+"_"+j+".txt");
							}
						}else{
							int to = fileTxtRepay[i].lastIndexOf('.');
					    	fileN = fileTxtRepay[i].substring(0, to);
							spFile.add(fileN+".txt");
						}
					}
					
					for(String fn : spFile){
						try{
							if(fn.contains(fileN)) {
								if(fn.startsWith("kdk_lsz")){
									log.info("*****************流水账信息********************");
									customerInforService.saveLSZDataFile(gzFile+File.separator+fn,dateString);
								}else if(fn.startsWith("kdk_yehz")){
									log.info("*****************余额汇总信息********************");  
									customerInforService.saveYEHZDataFile(gzFile+File.separator+fn,dateString);
								}else if(fn.startsWith("kdk_tkmx")){
									log.info("*****************借据表信息********************");  
									customerInforService.saveTKMXDataFile(gzFile+File.separator+fn,dateString);
								}else if(fn.startsWith("cxd_jggl")){
									log.info("*****************机构信息********************");  
									customerInforService.saveJGDataFile(gzFile+File.separator+File.separator+fn,dateString);
								}else if(fn.startsWith("kdk_jh")){
									log.info("*****************还款计划信息********************");  
									customerInforService.saveJHDataFile(gzFile+File.separator+fn,dateString);
								}else if(fn.startsWith("kkh_grxxll")){
									log.info("*****************客户学习表********************");  
									customerInforService.saveStudyDataFile(gzFile+File.separator+fn,dateString);
								}
							} 
						}catch(Exception e){
							e.printStackTrace();
							//default
							this.updBtachtask("001","dk",dateString);
							throw new RuntimeException(e);
						}
					}
					f.delete();
					//success
					accountManagerParameterService.updBatchTaskFlow("100", "dk", dateString);
			}
        }
        log.info(dateString+"******************完成读取贷款文件********************");

	}
	
	
	
	/**
	 * 贷款信息
	 * 手动
	 */
	public void readFileRepayByDate(String dateString) {
		String gzFile = CardFtpUtils.bank_ftp_down_path+File.separator+dateString;
		log.info(dateString+"******************贷款信息 start********************"); 
		// 清空表数据
		this.deleteRepay();
        for(int i=0;i<fileTxtRepay.length;i++){
			String url = gzFile+File.separator+fileTxtRepay[i];
			log.info(url);
			File f = new File(url);
			if(f.exists()){
					List<String> spFile = new ArrayList<String>();
					String fileN = "";
					//判断文件大小，超过50M的先分割
					if (f.exists() && f.isFile()){
						if(f.length()>10000000){
							int spCount = (int) (f.length()/10000000);
							SPTxt.splitTxt(url,spCount);
							int to = fileTxtRepay[i].lastIndexOf('.');
					    	fileN = fileTxtRepay[i].substring(0, to);
							for(int j=0;j<spCount;j++){
								spFile.add(fileN+"_"+j+".txt");
							}
						}else{
							int to = fileTxtRepay[i].lastIndexOf('.');
					    	fileN = fileTxtRepay[i].substring(0, to);
							spFile.add(fileN+".txt");
						}
					}
					
					for(String fn : spFile){
						try{
							if(fn.contains(fileN)) {
								if(fn.startsWith("kdk_lsz")){
									log.info("*****************流水账信息********************");
									customerInforService.saveLSZDataFile(gzFile+File.separator+fn,dateString);
								}else if(fn.startsWith("kdk_yehz")){
									log.info("*****************余额汇总信息********************");  
									customerInforService.saveYEHZDataFile(gzFile+File.separator+fn,dateString);
								}else if(fn.startsWith("kdk_tkmx")){
									log.info("*****************借据表信息********************");  
									customerInforService.saveTKMXDataFile(gzFile+File.separator+fn,dateString);
								}else if(fn.startsWith("cxd_jggl")){
									log.info("*****************机构信息********************");  
									customerInforService.saveJGDataFile(gzFile+File.separator+File.separator+fn,dateString);
								}else if(fn.startsWith("kdk_jh")){
									log.info("*****************还款计划信息********************");  
									customerInforService.saveJHDataFile(gzFile+File.separator+fn,dateString);
								}else if(fn.startsWith("kkh_grxxll")){
									log.info("*****************客户学习表********************");  
									customerInforService.saveStudyDataFile(gzFile+File.separator+fn,dateString);
								}
							} 
						}catch(Exception e){
							e.printStackTrace();
							//default
							this.updBtachtask("001","dk",dateString);
							throw new RuntimeException(e);
						}
					}
					f.delete();
					//success
					accountManagerParameterService.updBatchTaskFlow("100", "dk", dateString);
			}
        }
        log.info(dateString+"******************贷款信息 end********************");

	}
	
	
	
	//upd batch task 
	public void updBtachtask(String status,String batchCode,String dateString){
		DefaultTransactionDefinition  transStatus  = new DefaultTransactionDefinition();
		transStatus.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		TransactionStatus one = txManager.getTransaction(transStatus);
		try{
			//upd task
			accountManagerParameterService.updBatchTaskFlow(status,batchCode,dateString);
			txManager.commit(one);
		}catch (Exception e){
			e.printStackTrace();
			txManager.rollback(one);
		}
	}
	
}