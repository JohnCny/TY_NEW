package com.cardpay.pccredit.customer.service;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.cardpay.pccredit.customer.dao.CustomerInforDao;
import com.cardpay.pccredit.customer.dao.comdao.ReadWholeAndIncrementComdao;
import com.cardpay.pccredit.customer.model.CustomerFirsthendBase;
import com.cardpay.pccredit.customer.model.CustomerFirsthendFamilyCc;
import com.cardpay.pccredit.customer.model.CustomerFirsthendFamilyCy;
import com.cardpay.pccredit.customer.model.CustomerFirsthendManage;
import com.cardpay.pccredit.customer.model.CustomerFirsthendRygl;
import com.cardpay.pccredit.customer.model.CustomerFirsthendSafe;
import com.cardpay.pccredit.customer.model.CustomerFirsthendStudy;
import com.cardpay.pccredit.customer.model.CustomerFirsthendWork;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.model.TyProductType;
import com.cardpay.pccredit.manager.service.AccountManagerParameterService;
import com.cardpay.pccredit.system.model.SystemUser;
import com.cardpay.pccredit.tools.DataFileConf;
import com.cardpay.pccredit.tools.ImportBankDataFileTools;
import com.cardpay.pccredit.tools.SPTxt;
import com.cardpay.pccredit.tools.UpdateCustomerTool;
import com.cardpay.pccredit.toolsjn.FtpUtils;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.id.IDGenerator;

/**
 * @author sc
 * 读取下发数据 文件 
 * 全量和增量的分开读取
 */
@Service
public class ReadWholeAndIncrementService {
	public Logger log = Logger.getLogger(UpdateCustomerTool.class);

	@Autowired
	private CommonDao commonDao;

	@Autowired
	private ReadWholeAndIncrementComdao andIncrementComdao;
	
	@Autowired
	CustomerInforService  customerInforService;
	
	@Autowired
	PlatformTransactionManager txManager;
	
	@Autowired
	private CustomerInforDao customerInforDao;
	
	@Autowired
	private AccountManagerParameterService accountManagerParameterService;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;  
	
	//全量
	private String[] fileTxtWhole ={"t_param_class.txt",
									"t_param_param.txt",
									"t_party_type.txt",
									"t_rbac_group.txt",
									"t_rbac_user.txt"};
	
	//增量
	private String[] fileTxtIncre = {"kkh_grxx.txt","kkh_grjtcy.txt","kkh_grjtcc.txt","kkh_grscjy.txt","kkh_grxxll.txt","kkh_grgzll.txt","kkh_grrbxx.txt","cxd_dkcpmc.txt","kkh_hmdgl.txt","cxd_rygl.txt","kdk_yehz.txt","kdk_lsz.txt","kdk_tkmx.txt","kdk_jh.txt"};
	
	
	
	/**
	 * 清空表数据
	 * 济南
	 * @param fileN
	 */
    private void deleteTableDatas(){
		//删除【参数字典列表】历史数据
		commonDao.queryBySql("truncate   table   T_PARAM_PARAM", null);
		//删除【参数字典基本信息表】历史数据
		commonDao.queryBySql("truncate   table   T_PARAM_CLASS", null);
		//删除【客户类型表】历史数据
		commonDao.queryBySql(" truncate   table   T_PARTY_TYPE", null);
		//删除【机构表】历史数据
		commonDao.queryBySql(" truncate   table   T_RBAC_GROUP", null);
		//删除【客户类型表】历史数据
		commonDao.queryBySql(" truncate   table   T_RBAC_USER", null);
    }
	
	

	/**
	 * 解析增量数据
	 * 济南
	 * @throws Exception
	 * 系统自动 
	 */
	public void doReadFileIncrement(){
		//获取今日日期 yyyyMMdd格式
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		String dateString = format.format(new Date());
		log.info(dateString+"******************开始读取增量信息文件********************");  
	        String gzFile = FtpUtils.bank_ftp_down_path+dateString;
	        for(int i=0;i<fileTxtIncre.length;i++){
				String url = gzFile+File.separator+fileTxtIncre[i];
				File f = new File(url);
				if(f.exists()){
						List<String> spFile = new ArrayList<String>();
						String fileN = "";
						//判断文件大小，超过50M的先分割
						if (f.exists() && f.isFile()){
							if(f.length()>20000000){
								int spCount = (int) (f.length()/20000000);
								SPTxt.splitTxt(url,spCount);
								int to = fileTxtIncre[i].lastIndexOf('.');
						    	fileN = fileTxtIncre[i].substring(0, to);
								for(int j=0;j<spCount;j++){
									spFile.add(fileN+"_"+j+".txt");
								}
							}else{
								int to = fileTxtIncre[i].lastIndexOf('.');
						    	fileN = fileTxtIncre[i].substring(0, to);
								spFile.add(fileN+".txt");
							}
						}
						for(String fn : spFile){
							try{
								if(fn.contains(fileN)) {
								/*	if(fn.startsWith("t_cclmtapplyinfo")){
										log.info("*****************Cc授信申请基本信息（结果表）********************");  
										customerInforService.saveCCLMTAPPLYINFODataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("t_cipersonbadrecord")){
										log.info("*****************对私客户不良记录********************");
										customerInforService.saveCIPERSONBADRECORDDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("t_cipersonbasinfo")){
										log.info("*****************对私客户基本信息********************");
										customerInforService.saveCIPERSONBASINFODataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("t_cipersonfamily")){
										log.info("*****************对私家庭成员信息********************");
										customerInforService.saveCIPERSONFAMILYDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("t_fcloaninfo")){
										log.info("*****************借据月末余额表（结果表）********************");
										customerInforService.saveFCLOANINFODataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("t_fcresulthis")){
										log.info("*****************认定结果表（历史表）********************");
										customerInforService.saveFCRESULTHISDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("t_fcstatisticsdata")){
										log.info("*****************五级分类统计表********************");
										customerInforService.saveFCSTATISTICSDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("t_gcassurecorrespond")){
										log.info("*****************GC担保对应表********************");
										customerInforService.saveGCASSURECORRESPONDDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("t_gcassuremain")){
										log.info("*****************GC担保信息表********************");
										saveGCASSUREMAINDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("t_gccontractmain")){
										log.info("*****************GC合同基本表********************");
										saveGCCONTRACTMAINDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("t_gcassuremulticlient")){
										log.info("*****************GC从合同多方信息表********************");
										saveGCASSUREMULTICLIENTDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("t_gcguarantymain")){
										log.info("*****************GC押品主表********************");
										saveGCGUARANTYMAINDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("t_gcloancard1")){
										log.info("*****************Gc贷款证表 ********************");
										saveGCLOANCARDDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("t_gcloancardcontract")){
										log.info("*****************Gc贷款证合同关联关系表 ********************");
										saveGCLOANCARDCONTRACTDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("t_gcloancredit")){
										log.info("*****************Gc凭证信息表 ********************");
										saveGCLOANCREDITDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("t_mibusidata")){
										log.info("*****************台账——综合业务信息表  ********************");
										saveMIBUSIDATADataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("t_miloancard")){
										log.info("*****************台账——贷款卡片********************");
										saveMILOANCARDDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("t_party_bwlist")){
										log.info("*****************黑名单客户结果表 ********************");
										saveBWLISTDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("t_rarepaylist")){
										log.info("*****************还款情况表  ********************");
										saveRAREPAYLISTDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("t_sarm_specialasset")){
										log.info("*****************不良贷款信息  ********************");
										saveSPECIALASSETDataFile(gzFile+File.separator+fn,dateString);
									}*/
									if(fn.startsWith("cclmtapplyinfo")){
										log.info("*****************Cc授信申请基本信息（结果表）********************");
										customerInforService.saveCCLMTAPPLYINFODataFile(gzFile+File.separator+fn,dateString);
										System.gc();
									}else if(fn.startsWith("CIPERSONBADRECORD")){
										log.info("*****************对私客户不良记录********************");
										customerInforService.saveCIPERSONBADRECORDDataFile(gzFile+File.separator+fn,dateString);
										System.gc();
									}else if(fn.startsWith("cipersonbasinfo")){
										log.info("*****************对私客户基本信息********************");
										customerInforService.saveCIPERSONBASINFODataFile(gzFile+File.separator+fn,dateString);
										System.gc();
									}else if(fn.startsWith("FCLOANINFO")){
										log.info("*****************借据月末余额表（结果表）********************");
										customerInforService.saveFCLOANINFODataFile(gzFile+File.separator+fn,dateString);
										System.gc();
									}else if(fn.startsWith("FCRESULTHIS")){
										log.info("*****************认定结果表（历史表）********************");
										customerInforService.saveFCRESULTHISDataFile(gzFile+File.separator+fn,dateString);
										System.gc();
									}else if(fn.startsWith("gccontractmain")){
										log.info("*****************GC合同基本表********************");
										saveGCCONTRACTMAINDataFile(gzFile+File.separator+fn,dateString);
										System.gc();
									}else if(fn.startsWith("GCASSUREMULTICLIENT")){
										log.info("*****************GC从合同多方信息表********************");
										saveGCASSUREMULTICLIENTDataFile(gzFile+File.separator+fn,dateString);
										System.gc();
									}else if(fn.startsWith("mibusidata")){
										log.info("*****************台账——综合业务信息表  ********************");
										saveMIBUSIDATADataFile(gzFile+File.separator+fn,dateString);
										System.gc();
									}else if(fn.startsWith("rarepaylist")){
										log.info("*****************还款情况表  ********************");
										saveRAREPAYLISTDataFile(gzFile+File.separator+fn,dateString);
										System.gc();
									}else if(fn.startsWith("sarm_specialasset")){
										log.info("*****************不良贷款信息  ********************");
										saveSPECIALASSETDataFile(gzFile+File.separator+fn,dateString);
										System.gc();
									}else if(fn.startsWith("cipersonfamily")){
										log.info("*****************对私家庭成员信息********************");
										customerInforService.saveCIPERSONFAMILYDataFile(gzFile+File.separator+fn,dateString);
										System.gc();
									}else if(fn.startsWith("gcloancredit")){
										log.info("*****************Gc凭证信息表 ********************");
										saveGCLOANCREDITDataFile(gzFile+File.separator+fn,dateString);
										System.gc();
									}
									else if(fn.startsWith("GCCONTRACTMULTICLIENT")){
										log.info("*****************GC合同多方信息表  ********************");
										saveGCCONTRACTMULTICLIENTDataFile(gzFile+File.separator+fn,dateString);
										System.gc();
									}
									else if(fn.startsWith("GCCREDITTYPEINFORMATION")){
										log.info("*****************GC合同授信品种信息表********************");
										saveGCCREDITTYPEINFORMATIONDataFile(gzFile+File.separator+fn,dateString);
										System.gc();
									}
								}
							}catch(Exception e){
								//异常可throws 事务也回滚 但此处用来记录 task 是否成功
								e.printStackTrace();
								//default
								this.updBtachtask("001","incre",dateString);
								throw new RuntimeException(e);
							} 
						}
						f.delete();
						//succ
						accountManagerParameterService.updBatchTaskFlow("100","incre",dateString);
				}
	        }
	        log.info(dateString+"******************完成读取增量信息文件********************");

	}
	
	
	
	/**
	 * 解析增量数据
	 * 济南
	 * @param status 
	 * @throws Exception
	 * 手动 by date
	 */
	public void doReadFileIncrementByDate(String dateString, String status) {
		//指定日期
		log.info(dateString+"******************开始手动读取增量信息文件********************");  
	        String gzFile = FtpUtils.bank_ftp_down_path+File.separator+dateString;
	        for(int i=0;i<fileTxtIncre.length;i++){
	        	//File.separator
				String url = gzFile+File.separator+fileTxtIncre[i];
				File f = new File(url);
				if(f.exists()){
						List<String> spFile = new ArrayList<String>();
						String fileN = "";
						//判断文件大小，超过50M的先分割
						if (f.exists() && f.isFile()){
							if(f.length()>20000000){
								int spCount = (int) (f.length()/20000000);
								SPTxt.splitTxt(url,spCount);
								int to = fileTxtIncre[i].lastIndexOf('.');
						    	fileN = fileTxtIncre[i].substring(0, to);
								for(int j=0;j<spCount;j++){
									spFile.add(fileN+"_"+j+".txt");
								}
							}else{
								int to = fileTxtIncre[i].lastIndexOf('.');
						    	fileN = fileTxtIncre[i].substring(0, to);
								spFile.add(fileN+".txt");
							}
						}
						if(fileN.startsWith("kdk_yehz")){
							//删除余额汇总历史数据
							String sql = " truncate   table   ty_repay_yehz";
							commonDao.queryBySql(sql, null);
						}
						if(fileN.startsWith("kdk_tkmx")){
							//删除借据表历史数据
							String sql = " truncate   table   ty_repay_tkmx";
							commonDao.queryBySql(sql, null);
						}
						if(fileN.startsWith("cxd_jggl")){
							//删除行内机构表历史数据
							String sql = " truncate   table   ty_org";
							commonDao.queryBySql(sql, null);
						}
						if(fileN.startsWith("kdk_jh")){
							//删除还款计划历史数据
							String sql = " truncate   table   ty_kdk_jh";
							commonDao.queryBySql(sql, null);
						}
						for(String fn : spFile){
							try{
								if(fn.contains(fileN)) {
									if(fn.startsWith("cxd_rygl")){
										log.info("*****************人员管理参数表********************");  
										System.out.println(1111);
										//+File.separator
										saveRyglDataFile(gzFile+File.separator+fn,dateString);
										System.gc();
									}
									if(fn.startsWith("kkh_grxx")){
										log.info("*****************客户基本表********************");  
										saveBaseDataFile(gzFile+File.separator+fn,dateString);
										System.gc();
									}
									if(fn.startsWith("kkh_grjtcy")){
										log.info("*****************客户家庭关系表********************");  
										saveCyDataFile(gzFile+File.separator+fn,dateString);
									}
									if(fn.startsWith("kkh_grjtcc")){
										log.info("*****************客户家庭财产表********************");  
										saveCcDataFile(gzFile+File.separator+fn,dateString);
									}
									if(fn.startsWith("kkh_grxxll")){
										log.info("*****************客户学习表********************");  
										saveStudyDataFile(gzFile+File.separator+fn,dateString);
									}
									if(fn.startsWith("kkh_grgzll")){
										log.info("*****************客户工作履历表********************");  
										saveWorkDataFile(gzFile+File.separator+fn,dateString);
									}
									if(fn.startsWith("kkh_grscjy")){
										log.info("*****************客户生产经营表********************");  
									saveManageDataFile(gzFile+File.separator+fn,dateString);
								}
									if(fn.startsWith("kkh_grrbxx")){
										log.info("*****************客户入保信息表********************");  
										saveSafeDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("cxd_dkcpmc")){
										log.info("*****************产品信息********************");  
										saveProductDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("kkh_hmdgl")){
										log.info("*****************黑名单********************");  
										saveHMDDataFile(gzFile+File.separator+fn,dateString);
									}
									if(fn.startsWith("kdk_lsz")){
										log.info("*****************流水账信息********************");
										//+File.separator
										saveLSZDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("kdk_yehz")){
										log.info("*****************余额汇总信息********************");  
										saveYEHZDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("kdk_tkmx")){
										log.info("*****************借据表信息********************");  
										saveTKMXDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("cxd_jggl")){
										log.info("*****************机构信息********************");  
										saveJGDataFile(gzFile+File.separator+File.separator+fn,dateString);
									}else if(fn.startsWith("kdk_jh")){
										log.info("*****************还款计划信息********************");  
										saveJHDataFile(gzFile+File.separator+fn,dateString);
									}
									
								}
							}catch(Exception e){
								e.printStackTrace();
								//default
								this.updBtachtask("001","incre",dateString);
								throw new RuntimeException(e);
							}
						}
						f.delete();
						//succ
						if(!"002".equals(status)){
							accountManagerParameterService.updBatchTaskFlow("100","incre",dateString);
						}
				}
	        }
	        log.info(dateString+"******************完成手动读取增量信息文件********************");

	}
	
	//upd
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
    

	
	
	
	
	
	
	/**
	 *  解析全量数据
	 *  济南
	 *  系统自动
	 */
	public void doReadFileWhole(){
		//获取今日日期 yyyyMMdd格式
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		String dateString = format.format(new Date());
		String gzFile = FtpUtils.bank_ftp_down_path+dateString;

		log.info(dateString+"******************开始读取全量数据文件********************");  
		//清空表数据
		this.deleteTableDatas();
        for(int i=0;i<fileTxtWhole.length;i++){
			String url = gzFile+File.separator+fileTxtWhole[i];
			File f = new File(url);
			if(f.exists()){
					List<String> spFile = new ArrayList<String>();
					String fileN = "";
					//判断文件大小，超过50M的先分割
					if (f.exists() && f.isFile()){
						if(f.length()>20000000){
							int spCount = (int) (f.length()/20000000);
							SPTxt.splitTxt(url,spCount);
							int to = fileTxtWhole[i].lastIndexOf('.');
					    	fileN = fileTxtWhole[i].substring(0, to);
							for(int j=0;j<spCount;j++){
								spFile.add(fileN+"_"+j+".txt");
							}
						}else{
							int to = fileTxtWhole[i].lastIndexOf('.');
					    	fileN = fileTxtWhole[i].substring(0, to);
							spFile.add(fileN+".txt");
						}
					}
					
					for(String fn : spFile){
						try{
							if(fn.contains(fileN)) {
								if(fn.startsWith("t_param_class")){
									log.info("*****************参数字典基本信息表 【T_PARAM_CLASS】********************");  
									this.saveParamClassDataFile(gzFile+File.separator+fn,dateString);
								}else if(fn.startsWith("t_param_param")){
									log.info("*****************参数字典列表【T_PARAM_PARAM】********************"); 
									this.saveParamParmDataFile(gzFile+File.separator+fn,dateString);
								}else if(fn.startsWith("t_party_type")){
									log.info("*****************客户类型表【T_PARTY_TYPE】********************");
									this.saveParamTypeDataFile(gzFile+File.separator+fn,dateString);
								}else if(fn.startsWith("t_rbac_group")){
									log.info("*****************机构表【T_RBAC_GROUP】 ********************");
									this.saveRbacGroupDataFile(gzFile+File.separator+fn,dateString);
								}else if(fn.startsWith("t_rbac_user")){
									log.info("*****************用户表【T_RBAC_USER】********************");
									this.saveRbacUserDataFile(gzFile+File.separator+fn,dateString);
								}
							}
						}catch(Exception e){
							e.printStackTrace();
							//default
							this.updBtachtask("001","whole",dateString);
							throw new RuntimeException(e);
						}
					}
					//f.delete();
			}
        }
        //succ
		accountManagerParameterService.updBatchTaskFlow("100","whole",dateString);
        log.info(dateString+"******************完成读取全量数据文件********************");
	}
	
	
	
	/**
	 *  解析全量数据
	 *  济南
	 *  手动
	 */
	public void doReadFileWholeByDate(String dateString){
		//指定日期
		String gzFile = FtpUtils.bank_ftp_down_path+dateString;

		log.info(dateString+"******************开始手动读取全量数据文件********************");  
		//清空表数据
		this.deleteTableDatas();
        for(int i=0;i<fileTxtWhole.length;i++){
			String url = gzFile+File.separator+fileTxtWhole[i];
			File f = new File(url);
			if(f.exists()){
					List<String> spFile = new ArrayList<String>();
					String fileN = "";
					//判断文件大小，超过50M的先分割
					if (f.exists() && f.isFile()){
						if(f.length()>20000000){
							int spCount = (int) (f.length()/20000000);
							SPTxt.splitTxt(url,spCount);
							int to = fileTxtWhole[i].lastIndexOf('.');
					    	fileN = fileTxtWhole[i].substring(0, to);
							for(int j=0;j<spCount;j++){
								spFile.add(fileN+"_"+j+".txt");
							}
						}else{
							int to = fileTxtWhole[i].lastIndexOf('.');
					    	fileN = fileTxtWhole[i].substring(0, to);
							spFile.add(fileN+".txt");
						}
					}
					
					for(String fn : spFile){
						try{
							if(fn.contains(fileN)) {
								if(fn.startsWith("t_param_class")){
									log.info("*****************参数字典基本信息表 【T_PARAM_CLASS】********************");  
									this.saveParamClassDataFile(gzFile+File.separator+fn,dateString);
								}else if(fn.startsWith("t_param_param")){
									log.info("*****************参数字典列表【T_PARAM_PARAM】********************"); 
									this.saveParamParmDataFile(gzFile+File.separator+fn,dateString);
								}else if(fn.startsWith("t_party_type")){
									log.info("*****************客户类型表【T_PARTY_TYPE】********************");
									this.saveParamTypeDataFile(gzFile+File.separator+fn,dateString);
								}else if(fn.startsWith("t_rbac_group")){
									log.info("*****************机构表【T_RBAC_GROUP】 ********************");
									this.saveRbacGroupDataFile(gzFile+File.separator+fn,dateString);
								}else if(fn.startsWith("t_rbac_user")){
									log.info("*****************用户表【T_RBAC_USER】********************");
									this.saveRbacUserDataFile(gzFile+File.separator+fn,dateString);
								}
							}
						}catch(Exception e){
							e.printStackTrace();
							//default
							this.updBtachtask("001","whole",dateString);
							throw new RuntimeException(e);
						}
					}
					//f.delete();
			}
        }
        //succ
		accountManagerParameterService.updBatchTaskFlow("100","whole",dateString);
        log.info(dateString+"******************完成手动读取全量数据文件********************");
	}
	
	
	
	
	
	
//=========================================【全量start】==================================================================================================//
	
	/**
	 * 参数字典基本信息表
	 * @param fileName
	 * @param date
	 * @throws Exception 
	 */
	public void saveParamClassDataFile(String fileName,String date) throws Exception {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/T_PARAM_CLASS.xml");
			// 解析”流水号“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			// 批量插入
			andIncrementComdao.insertParamClass(datas);
			// 释放空间
			datas=null;
	}
	
	
	/**
	 * 参数字典列表
	 * @param fileName
	 * @param date
	 * @throws Exception 
	 */
	public void saveParamParmDataFile(String fileName,String date) throws Exception {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/T_PARAM_PARAM.xml");
			// 解析”流水号“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			// 批量插入
			andIncrementComdao.insertParamParm(datas);
			// 释放空间
			datas=null;
	}
	
	
	/**
	 * 客户类型表
	 * @param fileName
	 * @param date
	 * @throws Exception 
	 */
	public void saveParamTypeDataFile(String fileName,String date) throws Exception {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/T_PARAM_TYPE.xml");
			// 解析”流水号“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			// 批量插入
			andIncrementComdao.insertParamType(datas);
			// 释放空间
			datas=null;
	}
	
	
	/**
	 * 机构表
	 * @param fileName
	 * @param date
	 * @throws Exception 
	 */
	public void saveRbacGroupDataFile(String fileName,String date) throws Exception {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/T_RBAC_GROUP.xml");
			// 解析”流水号“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			// 批量插入
			andIncrementComdao.insertRbacGroup(datas);
			// 释放空间
			datas=null;
	}
	
	
	/**
	 * 用户表
	 * @param fileName
	 * @param date
	 * @throws Exception 
	 */
	public void saveRbacUserDataFile(String fileName,String date) throws Exception {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/T_RBAC_USER.xml");
			// 解析”流水号“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			// 批量插入
			andIncrementComdao.insertRbacUser(datas);
			// 释放空间
			datas=null;
	}
//=========================================【全量end】==================================================================================================//
	
	
	
	
	
	
	
//=========================================【增量start】==================================================================================================//
	/**
	 * GC担保信息表
	 * T_GCASSUREMAIN
	 * @param fileName
	 * @param date
	 * @throws Exception 
	 */
	public void saveGCASSUREMAINDataFile(String fileName,String date) throws Exception {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/T_GCASSUREMAIN.xml");
			// 解析”流水号“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			// 批量插入
			andIncrementComdao.insertGCASSUREMAIN(datas);
			// 释放空间
			datas=null;
	}
	
	/**
	 * T_GCCONTRACTMAIN
	 * GC合同基本表
	 * @param fileName
	 * @param date
	 * @throws Exception 
	 */
	public void saveGCCONTRACTMAINDataFile(String fileName,String date) throws Exception {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/T_GCCONTRACTMAIN.xml");
			// 解析”流水号“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			
//			for (Map<String, Object> map : datas){
//				customerInforDao.insertMain(map);
//			}
			// 批量插入
			andIncrementComdao.insertGCCONTRACTMAIN(datas);
			// 释放空间
			datas=null;
	}
	
	
	/**
	 * GC从合同多方信息表
	 * T_GCASSUREMULTICLIENT
	 * @param fileName
	 * @param date
	 * @throws Exception 
	 */
	public void saveGCASSUREMULTICLIENTDataFile(String fileName,String date) throws Exception {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/T_GCASSUREMULTICLIENT.xml");
			// 解析”流水号“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			// 批量插入
			andIncrementComdao.insertGCASSUREMULTICLIENT(datas);
			// 释放空间
			datas=null;
	}
	
	
	/**
	 * GC押品主表
	 * T_GCGUARANTYMAIN
	 * @param fileName
	 * @param date
	 * @throws Exception 
	 */
	public void saveGCGUARANTYMAINDataFile(String fileName,String date) throws Exception {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/T_GCGUARANTYMAIN.xml");
			// 解析”流水号“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			// 批量插入
			andIncrementComdao.insertGCGUARANTYMAIN(datas);
			// 释放空间
			datas=null;
	}
	
	
	
	/**
	 * Gc贷款证表 
	 * T_GCLOANCARD
	 * @param fileName
	 * @param date
	 * @throws Exception 
	 */
	public void saveGCLOANCARDDataFile(String fileName,String date) throws Exception {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/T_GCLOANCARD.xml");
			// 解析”流水号“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			// 批量插入
			andIncrementComdao.insertGCLOANCARD(datas);
			// 释放空间
			datas=null;
	}
	
	
	/**
	 * Gc贷款证合同关联关系表
	 * T_GCLOANCARDCONTRACT
	 * @param fileName
	 * @param date
	 * @throws Exception 
	 */
	public void saveGCLOANCARDCONTRACTDataFile(String fileName,String date) throws Exception {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/T_GCLOANCARDCONTRACT.xml");
			// 解析”流水号“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			// 批量插入
			andIncrementComdao.insertGCLOANCARDCONTRACT(datas);
			// 释放空间
			datas=null;
	}
	
	
	
	
	
	
	
	/**
	 * Gc凭证信息表
	 * T_GCLOANCREDIT
	 * @param fileName
	 * @param date
	 * @throws Exception 
	 */
	public void saveGCLOANCREDITDataFile(String fileName,String date) throws Exception {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/T_GCLOANCREDIT.xml");
			// 解析”流水号“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			// 批量插入
			andIncrementComdao.insertGCLOANCREDIT(datas);
			// 释放空间
			datas=null;
	}
	
	
	
	
	
	
	/**
	 * 台账——综合业务信息表 
	 * T_MIBUSIDATA
	 * @param fileName
	 * @param date
	 * @throws Exception 
	 */
	public void saveMIBUSIDATADataFile(String fileName,String date) throws Exception {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			//add
			//List<Map<String, Object>> insertdatas = new ArrayList<Map<String,Object>>();
			//update
			//List<Map<String, Object>> updatedatas = new ArrayList<Map<String,Object>>();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/T_MIBUSIDATA.xml");
			// 解析”流水号“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			andIncrementComdao.insertMIBUSIDATA(datas);
			// 批量插入
			//andIncrementComdao.insertMIBUSIDATA(datas);
			//for
			/*for(Map<String, Object> map : datas){
				int count = customerInforDao.findMIBUSIDATACount(map);
				if(count >0){
					log.info("*************************updatedatas*************************");
					//put updateMap
					updatedatas.add(map);
				}else{
					log.info("*************************insertdatas*************************");
					//put insertMap
					insertdatas.add(map);
				}
			}*/
			//save
			//andIncrementComdao.insertMIBUSIDATA(insertdatas);
			//update
			//andIncrementComdao.updateMIBUSIDATA(updatedatas);
			//释放空间
			//insertdatas=null;
			//updatedatas=null;
			// 释放空间
			datas=null;
	}
	
	
	/**
	 * 台账——贷款卡片
	 * T_MILOANCARD
	 * @param fileName
	 * @param date
	 * @throws Exception 
	 */
	public void saveMILOANCARDDataFile(String fileName,String date) throws Exception {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/T_MILOANCARD.xml");
			// 解析”流水号“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			// 批量插入
			andIncrementComdao.insertMILOANCARD(datas);
			// 释放空间
			datas=null;
	}
	
	
	
	/**
	 * 黑名单客户结果表
	 * T_PARTY_BWLIST
	 * @param fileName
	 * @param date
	 * @throws Exception 
	 */
	public void saveBWLISTDataFile(String fileName,String date) throws Exception {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/T_PARTY_BWLIST.xml");
			// 解析”流水号“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			// 批量插入
			andIncrementComdao.insertBWLIST(datas);
			// 释放空间
			datas=null;
	}
	
	
	
	
	
	/**
	 * 还款情况表 
	 * T_RAREPAYLIST
	 * @param fileName
	 * @param date
	 * @throws Exception 
	 */
	public void saveRAREPAYLISTDataFile(String fileName,String date) throws Exception {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/T_RAREPAYLIST.xml");
			// 解析”流水号“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			// 批量插入
			andIncrementComdao.insertRAREPAYLIST(datas);
			// 释放空间
			datas=null;
	}
	
	
	
	/**
	 * 不良贷款信息
	 * T_SARM_SPECIALASSET
	 * @param fileName
	 * @param date
	 * @throws Exception 
	 */
	public void saveSPECIALASSETDataFile(String fileName,String date) throws Exception {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/T_SARM_SPECIALASSET.xml");
			// 解析”流水号“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			// 批量插入
			andIncrementComdao.insertSPECIALASSET(datas);
			// 释放空间
			datas=null;
	}
	
	
	/**
	 * GC合同多方信息表 
	 * T_GCCONTRACTMULTICLIENT 
	 */
	public void saveGCCONTRACTMULTICLIENTDataFile(String fileName,String date) throws Exception {
		ImportBankDataFileTools tools = new ImportBankDataFileTools();
		// 解析数据文件配置
		List<DataFileConf> confList = tools.parseDataFileConf("/mapping/T_GCCONTRACTMULTICLIENT.xml");
		// 解析”流水号“数据文件
		List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
		// 批量插入
		andIncrementComdao.insertGCCONTRACTMULTICLIENT(datas);
		// 释放空间
		datas=null;
	}
	
	/**
	 * GC合同授信品种信息表 
	 * T_GCCREDITTYPEINFORMATION
	 */
	public void saveGCCREDITTYPEINFORMATIONDataFile(String fileName,String date) throws Exception {
		ImportBankDataFileTools tools = new ImportBankDataFileTools();
		// 解析数据文件配置
		List<DataFileConf> confList = tools.parseDataFileConf("/mapping/T_GCCREDITTYPEINFORMATION.xml");
		// 解析”流水号“数据文件
		List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
		// 批量插入
		andIncrementComdao.insertGCCREDITTYPEINFORMATION(datas);
		// 释放空间
		datas=null;
	}
	
	//------------------------
	/**
	 * 保数据文件到”人员管理参数“表
	 * @param fileName
	 * @return
	 * @throws Exception 
	 */
	public  void saveRyglDataFile(String fileName,String date) {
		try {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/tyCustomerRygl.xml");

			// 解析”帐单记录表“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			int count=0;
			for(Map<String, Object> map : datas){
				count++;
//				System.out.println(count);
				// 保存数据
				//先查询人员管理参数表，存在则更新否则插入
				String sql = "select * from ty_customer_rygl where dm='"+map.get("dm").toString().trim()+"'";
				List<CustomerFirsthendRygl> list = commonDao.queryBySql(CustomerFirsthendRygl.class, sql, null);
				if(list.size()>0){
				}else{
					customerInforDao.insertCustomerRygl(map);
				}
				
			}
			//释放空间
			datas=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 保数据文件到”客户原始资料“表
	 * @param fileName
	 * @return
	 * @throws Exception 
	 */
	public  void saveBaseDataFile(String fileName,String date) {
		try {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/tyCustomerBase.xml");

			// 解析”客户基础表“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			int count=0;
			for(Map<String, Object> map : datas){
				count++;
//				System.out.println(count);
				// 保存数据
				//先查询客户原始表，存在则更新否则插入(更新原始信息表)
				String sql = "select * from ty_customer_base where khnm='"+map.get("khnm").toString()+"'";
				List<CustomerFirsthendBase> list = commonDao.queryBySql(CustomerFirsthendBase.class, sql, null);
				if(list.size()>0){
					customerInforDao.updateCustomerBase(map);
					//同步系统客户主表(由于存在客户经理更改情况，所以更新原始表同时，更新主表客户经理)
					String card_id = map.get("zjhm").toString();
					String name = map.get("khmc").toString();
					String id = map.get("id").toString();
					//客户经理标识
					String khjl = map.get("khjl").toString();
					//先通过标识获取柜员号
					List<CustomerFirsthendRygl> rygl = commonDao.queryBySql(CustomerFirsthendRygl.class, "select * from ty_customer_rygl where dm='"+khjl.trim()+"'", null);
					String gyh1 = "";
					String gyh2 = "";
					if(rygl.size()>0){
						gyh1 = rygl.get(0).getDdrq();
						gyh2 = rygl.get(0).getDldm();
						if(gyh1==null){
							gyh1="";
						}
						if(gyh2==null){
							gyh2="";
						}
					}
					//获取客户经理id(由于两个字段都有可能为柜员号，所以两次判断)
					String user_id=null;
					List<SystemUser> users = commonDao.queryBySql(SystemUser.class, "select * from sys_user where external_id='"+gyh1+"'", null);
					//银行工号匹配本系统uuid，存在则替换，不存在则插入银行工号
					if(users.size()>0){
						user_id = users.get(0).getId();
					}else{
						List<SystemUser> users1 = commonDao.queryBySql(SystemUser.class, "select * from sys_user where external_id='"+gyh2+"'", null);
						if(users1.size()>0){
							user_id = users1.get(0).getId();
						}else{
							if(!gyh2.equals("")){
								user_id=gyh2;
							}else{
								user_id = khjl.trim();
							}
						}
					}
					List<CustomerInfor> infoList = commonDao.queryBySql(CustomerInfor.class, "select * from basic_customer_information where card_id='"+card_id+"'", null);
					//存在则更新客户经理id
					if(infoList.size()>0){
						CustomerInfor info = infoList.get(0);
						info.setUserId(user_id);
						commonDao.updateObject(info);
					}
				}else{
					customerInforDao.insertCustomerBase(map);
					//同步系统客户主表
					String card_id = map.get("zjhm").toString();
					String name = map.get("khmc").toString();
					String id = map.get("id").toString();
					//客户经理标识
					String khjl = map.get("khjl").toString();
					//先通过标识获取柜员号
					List<CustomerFirsthendRygl> rygl = commonDao.queryBySql(CustomerFirsthendRygl.class, "select * from ty_customer_rygl where dm='"+khjl.trim()+"'", null);
					String gyh1 = "";
					String gyh2 = "";
					if(rygl.size()>0){
						gyh1 = rygl.get(0).getDdrq();
						gyh2 = rygl.get(0).getDldm();
						if(gyh1==null){
							gyh1="";
						}
						if(gyh2==null){
							gyh2="";
						}
					}
					//获取客户经理id(由于两个字段都有可能为柜员号，所以两次判断)
					String user_id=null;
					List<SystemUser> users = commonDao.queryBySql(SystemUser.class, "select * from sys_user where external_id='"+gyh1+"'", null);
					//银行工号匹配本系统uuid，存在则替换，不存在则插入银行工号
					if(users.size()>0){
						user_id = users.get(0).getId();
					}else{
						List<SystemUser> users1 = commonDao.queryBySql(SystemUser.class, "select * from sys_user where external_id='"+gyh2+"'", null);
						if(users1.size()>0){
							user_id = users1.get(0).getId();
						}else{
							if(!gyh2.equals("")){
								user_id=gyh2;
							}else{
								user_id = khjl.trim();
							}
						}
					}
					List<CustomerInfor> infoList = commonDao.queryBySql(CustomerInfor.class, "select * from basic_customer_information where card_id='"+card_id+"'", null);
					//不存在则插入
					if(infoList.size()==0){
						CustomerInfor info = new CustomerInfor();
						info.setCardId(card_id);
						info.setChineseName(name);
						info.setTyCustomerId(id);
						info.setId(IDGenerator.generateID());
						info.setUserId(user_id);
						//身份证
						info.setCardType("CST0000000000A");
						commonDao.insertObject(info);
					}else{
						//存在则作关联
						CustomerInfor info = infoList.get(0);
						info.setTyCustomerId(id);
						commonDao.updateObject(info);
					}
				}
				//先查询客户维护表，存在不操作否则插入(新增原始信息维护表)(触发器执行)
//				String sql1 = "select * from ty_customer_base_local where khnm='"+map.get("khnm").toString()+"'";
//				List<CustomerFirsthendBase> list1 = commonDao.queryBySql(CustomerFirsthendBase.class, sql1, null);
//				if(list1.size()==0){
//					customerInforDao.insertCustomerBaseLocal(map);
//				}
				
			}
			//释放空间
			datas=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 保数据文件到”客户家庭关系“表
	 * @param fileName
	 * @return
	 * @throws Exception 
	 */
	public  void saveCyDataFile(String fileName,String date) {
		try {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/tyCustomerFamilyCy.xml");

			// 解析”帐单记录表“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			int count=0;
			for(Map<String, Object> map : datas){
				count++;
//				System.out.println(count);
				// 保存数据
				//先查询家庭关系表，存在则更新否则插入
				String sql = "select * from ty_customer_family_cy where khnm='"+map.get("khnm").toString()+"'";
				List<CustomerFirsthendFamilyCy> list = commonDao.queryBySql(CustomerFirsthendFamilyCy.class, sql, null);
				if(list.size()>0){
					customerInforDao.updateCustomerFamilyCy(map);
				}else{
					customerInforDao.insertCustomerFamilyCy(map);
				}

				
			}
			//释放空间
			datas=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 保数据文件到”客户家庭关系“表
	 * @param fileName
	 * @return
	 * @throws Exception 
	 */
	public  void saveCcDataFile(String fileName,String date) {
		try {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/tyCustomerFamilyCc.xml");

			// 解析”帐单记录表“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			int count=0;
			for(Map<String, Object> map : datas){
				count++;
//				System.out.println(count);
				// 保存数据
				//先查询家庭关系表，存在则更新否则插入
				String sql = "select * from ty_customer_family_cc where khnm='"+map.get("khnm").toString()+"'";
				List<CustomerFirsthendFamilyCc> list = commonDao.queryBySql(CustomerFirsthendFamilyCc.class, sql, null);
				if(list.size()>0){
					customerInforDao.updateCustomerFamilyCc(map);
				}else{
					customerInforDao.insertCustomerFamilyCc(map);
				}

				
			}
			//释放空间
			datas=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 保数据文件到”客户学习履历“表
	 * @param fileName
	 * @return
	 * @throws Exception 
	 */
	public  void saveStudyDataFile(String fileName,String date) {
		try {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/tyCustomerStudy.xml");

			// 解析”帐单记录表“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			int count=0;
			for(Map<String, Object> map : datas){
				count++;
//				System.out.println(count);
				// 保存数据
				//先查询家庭关系表，存在则更新否则插入
				String sql = "select * from ty_customer_study where khnm='"+map.get("khnm").toString()+"'";
				List<CustomerFirsthendStudy> list = commonDao.queryBySql(CustomerFirsthendStudy.class, sql, null);
				if(list.size()>0){
					customerInforDao.updateCustomerStudy(map);
				}else{
					customerInforDao.insertCustomerStudy(map);
				}

				
			}
			//释放空间
			datas=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 保数据文件到”客户工作履历“表
	 * @param fileName
	 * @return
	 * @throws Exception 
	 */
	public  void saveWorkDataFile(String fileName,String date) {
		try {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/tyCustomerWork.xml");

			// 解析”帐单记录表“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			int count=0;
			for(Map<String, Object> map : datas){
				count++;
//				System.out.println(count);
				// 保存数据
				//先查询工作履历表，存在则更新否则插入
				String sql = "select * from ty_customer_work where khnm='"+map.get("khnm").toString()+"'";
				List<CustomerFirsthendWork> list = commonDao.queryBySql(CustomerFirsthendWork.class, sql, null);
				if(list.size()>0){
					customerInforDao.updateCustomerWork(map);
				}else{
					customerInforDao.insertCustomerWork(map);
				}

				
			}
			//释放空间
			datas=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 保数据文件到”客户工作履历“表
	 * @param fileName
	 * @return
	 * @throws Exception 
	 */
	public  void saveManageDataFile(String fileName,String date) {
		try {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/tyCustomerManage.xml");

			// 解析”帐单记录表“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			int count=0;
			for(Map<String, Object> map : datas){
				count++;
//				System.out.println(count);
				// 保存数据
				//先查询生产经营表，存在则更新否则插入
				String sql = "select * from ty_customer_manage where khnm='"+map.get("khnm").toString()+"'";
				List<CustomerFirsthendManage> list = commonDao.queryBySql(CustomerFirsthendManage.class, sql, null);
				if(list.size()>0){
					customerInforDao.updateCustomerManage(map);
				}else{
					customerInforDao.insertCustomerManage(map);
				}

				
			}
			//释放空间
			datas=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 保数据文件到”客户工作履历“表
	 * @param fileName
	 * @return
	 * @throws Exception 
	 */
	public  void saveSafeDataFile(String fileName,String date) {
		try {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/tyCustomerSafe.xml");

			// 解析”帐单记录表“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			for(Map<String, Object> map : datas){
				// 保存数据
				//先查询生产经营表，存在则更新否则插入
				String sql = "select * from ty_customer_safe where khnm='"+map.get("khnm").toString()+"'";
				List<CustomerFirsthendSafe> list = commonDao.queryBySql(CustomerFirsthendSafe.class, sql, null);
				if(list.size()>0){
					customerInforDao.updateCustomerSafe(map);
				}else{
					customerInforDao.insertCustomerSafe(map);
				}

				
			}
			//释放空间
			datas=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 保数据文件到”产品信息“表
	 * @param fileName
	 * @return
	 * @throws Exception 
	 */
	public void saveProductDataFile(String fileName,String date) {
		try {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/tyRepayProduct.xml");
			
			// 解析”流水号“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			int count=0;
			for(Map<String, Object> map : datas){
				count++;
//				System.out.println(count);
				// 保存数据
				String sql = "select * from ty_product_type where product_code='"+map.get("productCode").toString()+"'";
				List<TyProductType> list = commonDao.queryBySql(TyProductType.class, sql, null);
				if(list.size()==0){
					customerInforDao.insertProduct(map);
				}
			}
			//释放空间
			datas=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 保数据文件到”黑名单“表
	 * @param fileName
	 * @return
	 * @throws Exception 
	 */
	public void saveHMDDataFile(String fileName,String date) {
		try {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/tyRepayHMD.xml");

			// 解析”黑名单“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			int count=0;
			//删除历史数据
			String sql = "delete from f_agr_crd_xyk_cuneg where created_time !='"+date+"'";
			commonDao.queryBySql(sql, null);
			for(Map<String, Object> map : datas){
				count++;
//				System.out.println(count);
				// 保存数据
				customerInforDao.insertHmd(map);
			}
			//释放空间
			datas=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//==============
	/**
	 * 保数据文件到”流水账“表
	 * @param fileName
	 * @return
	 * @throws Exception 
	 */
	public void saveLSZDataFile(String fileName,String date) {
		try {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/tyRepayLSZ.xml");

			// 解析”流水号“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			
			//批量插入
			insertLsh(datas);
			//释放空间
			datas=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 保数据文件到”余额“表
	 * @param fileName
	 * @return
	 * @throws Exception 
	 */
	public void saveYEHZDataFile(String fileName,String date) {
		try {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/tyRepayYEHZ.xml");

			// 解析”余额“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			//批量插入
			insertYe(datas);
			//释放空间
			datas=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 保数据文件到”借据表“表
	 * @param fileName
	 * @return
	 * @throws Exception 
	 */
	public void saveTKMXDataFile(String fileName,String date) {
		try {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/tyRepayTKMX.xml");

			// 解析”流水号“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			//批量插入
			insertJjb(datas);
			//释放空间
			datas=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 保数据文件到”机构表“表
	 * @param fileName
	 * @return
	 * @throws Exception 
	 */
	public void saveJGDataFile(String fileName,String date) {
		try {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/tyJGGL.xml");

			// 解析”流水号“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			//批量插入
			insertkdkjh(datas);
			//释放空间
			datas=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 保数据文件到”还款计划“表
	 * @param fileName
	 * @return
	 * @throws Exception 
	 */
	public void saveJHDataFile(String fileName,String date) {
		try {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/tyKdkJh.xml");

			// 解析”还款计划“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			//批量插入insertjggl 
			insertkdkjh(datas);
			//释放空间
			datas=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 批量插入流水号
	 */


	public void insertLsh(List<Map<String, Object>> list){
		 final List<Map<String, Object>> shopsList = list;
	        String sql =    "  insert into ty_repay_lsz (CREATE_TIME,    "+   
					        "    						   YWBH,              "+
					        "    						   KMH,              "+
					        "    						   KHJL,              "+
					        "     						   JJH,              "+
					        "     						   YWJG,              "+
					        "      						   YWJGH,              "+
					        "      						   DKZH,             "+
					        "      						   FZH,             "+
					        "      						   LSH,             "+
					        "      						   YWDM,             "+
					        "      						   JZRQ,             "+
					        "      						   YWRQ,             "+
					        "      						   ZY,             "+
					        "      						   PZH,             "+
					        "      						   JF,             "+
					        "      						   DF,             "+
					        "      						   YE,             "+
					        "      						   QXRX,             "+
					        "      						   ZXRQ,             "+
					        "      						   SFMZ,             "+
					        "      						   MZYH,             "+
					        "      						   MZSJ,             "+
					        "      						   SFCL             )"+
					        "    values    (?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?                              )";
	          jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){
	            public void setValues(PreparedStatement ps,int i)throws SQLException
	               {
	                ps.setString(1, ((Map<String, Object>)shopsList.get(i)).get("createTime").toString());
	                ps.setString(2, ((Map<String, Object>)shopsList.get(i)).get("ywbh").toString());
	                ps.setString(3, ((Map<String, Object>)shopsList.get(i)).get("kmh").toString());
	                ps.setString(4, ((Map<String, Object>)shopsList.get(i)).get("khjl").toString());
	                ps.setString(5, ((Map<String, Object>)shopsList.get(i)).get("jjh").toString());
	                ps.setString(6, ((Map<String, Object>)shopsList.get(i)).get("ywjg").toString());
	                ps.setString(7, ((Map<String, Object>)shopsList.get(i)).get("ywjgh").toString());
	                ps.setString(8, ((Map<String, Object>)shopsList.get(i)).get("dkzh").toString());
	                ps.setString(9, ((Map<String, Object>)shopsList.get(i)).get("fzh").toString());
	                ps.setString(10,((Map<String, Object>)shopsList.get(i)).get("lsh").toString());
	                ps.setString(11,((Map<String, Object>)shopsList.get(i)).get("ywdm").toString());
	                ps.setString(12,((Map<String, Object>)shopsList.get(i)).get("jzrq").toString());
	                ps.setString(13,((Map<String, Object>)shopsList.get(i)).get("ywrq").toString());
	                ps.setString(14,((Map<String, Object>)shopsList.get(i)).get("zy").toString());
	                ps.setString(15,((Map<String, Object>)shopsList.get(i)).get("pzh").toString());
	                ps.setString(16,((Map<String, Object>)shopsList.get(i)).get("jf").toString());
	                ps.setString(17,((Map<String, Object>)shopsList.get(i)).get("df").toString());
	                ps.setString(18,((Map<String, Object>)shopsList.get(i)).get("ye").toString());
	                ps.setString(19,((Map<String, Object>)shopsList.get(i)).get("qxrx").toString());
	                ps.setString(20,((Map<String, Object>)shopsList.get(i)).get("zxrq").toString());
	                ps.setString(21,((Map<String, Object>)shopsList.get(i)).get("sfmz").toString());
	                ps.setString(22,((Map<String, Object>)shopsList.get(i)).get("mzyh").toString());
	                ps.setString(23,((Map<String, Object>)shopsList.get(i)).get("mzsj").toString());
	                ps.setString(24,((Map<String, Object>)shopsList.get(i)).get("sfcl").toString());
	                
	               }
	               public int getBatchSize()
	               {
	                return shopsList.size();
	               }
	        });
	}
	/*
	 * 批量插入余额信息
	 */
	public void insertYe(List<Map<String, Object>> list){
		 final List<Map<String, Object>> shopsList = list;
	        String sql =    "insert into ty_repay_yehz (CREATE_TIME,JJH, YWBH, KHH, ZHTBH,JGDM,KMH,  HBZL,JKJE, QXRQ,DKYE, BNQX,  BWQX,WJFL1,WJFL2,WJFL3,WJFL4,WJFL5, SHBJ, SHLX, LXJS,  KSQXRQ,YHXBJ,YHXLX,  ZHHKR, BQLL) values    (   ?,  ?,  ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
					        
	          jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){
	            public void setValues(PreparedStatement ps,int i)throws SQLException
	               {
	            	ps.setString(1, ((Map<String, Object>)shopsList.get(i)).get("createTime").toString());
	                ps.setString(2, ((Map<String, Object>)shopsList.get(i)).get("jjh").toString());
	                ps.setString(3, ((Map<String, Object>)shopsList.get(i)).get("ywbh").toString());
	                ps.setString(4, ((Map<String, Object>)shopsList.get(i)).get("khh").toString());
	                ps.setString(5, ((Map<String, Object>)shopsList.get(i)).get("zhtbh").toString());
	                ps.setString(6, ((Map<String, Object>)shopsList.get(i)).get("jgdm").toString());
	                ps.setString(7, ((Map<String, Object>)shopsList.get(i)).get("kmh").toString());
	                ps.setString(8, ((Map<String, Object>)shopsList.get(i)).get("hbzl").toString());
	                ps.setString(9, ((Map<String, Object>)shopsList.get(i)).get("jkje").toString());
	                ps.setString(10,((Map<String, Object>)shopsList.get(i)).get("qxrq").toString());
	                ps.setString(11,((Map<String, Object>)shopsList.get(i)).get("dkye").toString());
	                ps.setString(12,((Map<String, Object>)shopsList.get(i)).get("bnqx").toString());
	                ps.setString(13,((Map<String, Object>)shopsList.get(i)).get("bwqx").toString());
	                ps.setString(14,((Map<String, Object>)shopsList.get(i)).get("wjfl1").toString());
	                ps.setString(15,((Map<String, Object>)shopsList.get(i)).get("wjfl2").toString());
	                ps.setString(16,((Map<String, Object>)shopsList.get(i)).get("wjfl3").toString());
	                ps.setString(17,((Map<String, Object>)shopsList.get(i)).get("wjfl4").toString());
	                ps.setString(18,((Map<String, Object>)shopsList.get(i)).get("wjfl5").toString());
	                ps.setString(19,((Map<String, Object>)shopsList.get(i)).get("shbj").toString());
	                ps.setString(20,((Map<String, Object>)shopsList.get(i)).get("shlx").toString());
	                ps.setString(21,((Map<String, Object>)shopsList.get(i)).get("lxjs").toString());
	                ps.setString(22,((Map<String, Object>)shopsList.get(i)).get("ksqxrq").toString());
	                ps.setString(23,((Map<String, Object>)shopsList.get(i)).get("yhxbj").toString());
	                ps.setString(24,((Map<String, Object>)shopsList.get(i)).get("yhxlx").toString());
	                ps.setString(25,((Map<String, Object>)shopsList.get(i)).get("zhhkr").toString());
	                ps.setString(26,((Map<String, Object>)shopsList.get(i)).get("bqll").toString());
	                
	               }
	               public int getBatchSize()
	               {
	                return shopsList.size();
	               }
	        });
	}
	/*
	 * 批量插入借据表
	 */
	public void insertJjb(List<Map<String, Object>> list){
		 final List<Map<String, Object>> shopsList = list;
	        String sql =    "  insert into ty_repay_tkmx (CREATE_TIME,    "+   
					        "    						   YWBH,              "+
					        "    						   KHH,              "+
					        "    						   ZHTBH,              "+
					        "     						   JJH,              "+
					        "     						   JZLL,              "+
					        "      						   SFBL,              "+
					        "      						   HTLL,             "+
					        "      						   TKBH,             "+
					        "      						   FFJE,             "+
					        "      						   TKYT,             "+
					        "      						   JKRQ,             "+
					        "      						   DQRQ,             "+
					        "      						   DKKM,             "+
					        "      						   XGRY,             "+
					        "      						   XGSJ,             "+
					        "      						   DKQX,             "+
					        "      						   ZQDQR,             "+
					        "      						   SFZQ,             "+
					        "      						   SFJQ,             "+
					        "      						   DKZL,             "+
					        "      						   DKFS,             "+
					        "      						   BLJG,             "+
					        "      						   SFZF,             "+
					        "      						   KHJL,             "+
					        "      						   ZQHTH,             "+
					        "      						   DKZH,             "+
					        "      						   SFJZ,             "+
					        "      						   DKFL,             "+
					        "      						   DKLX,             "+
					        "      						   JJBH,             "+
					        "      						   CPMC,             "+
					        "      						   DKXZ,             "+
					        "      						   DKYT,             "+
					        "      						   DKTX,             "+
					        "      						   LLZL,             "+
					        "      						   JXFS,             "+
					        "      						   HKLY,             "+
					        "      						   HKFS,             "+
					        "      						   JQRQ,             "+
					        "      						   YWLX,             "+
					        "      						   ZQRQ,             "+
					        "      						   HBZL,             "+
					        "      						   YTMX,             "+
					        "      						   SFXEDK,             "+
					        "      						   YQSF,             "+
					        "      						   SHYJ,             "+
					        "      						   SHRY,             "+
					        "      						   SHSJ,             "+
					        "      						   TXLX,             "+
					        "      						   YQLL,             "+
					        "      						   SFTXJE,             "+
					        "      						   NYJFBL,             "+
					        "      						   BLRY,             "+
					        "      						   YJJH,             "+
					        "      						   SFSTDK,             "+
					        "      						   YSTXYH,             "+
					        "      						   SFYXDK,             "+
					        "      						   YXR,             "+
					        "      						   FLCORZLC             )"+
					        "    values    (?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?                              )";
	          jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){
	            public void setValues(PreparedStatement ps,int i)throws SQLException
	               {
	                ps.setString(1, ((Map<String, Object>)shopsList.get(i)).get("createTime").toString());
	                ps.setString(2, ((Map<String, Object>)shopsList.get(i)).get("ywbh").toString());
	                ps.setString(3, ((Map<String, Object>)shopsList.get(i)).get("khh").toString());
	                ps.setString(4, ((Map<String, Object>)shopsList.get(i)).get("zhtbh").toString());
	                ps.setString(5, ((Map<String, Object>)shopsList.get(i)).get("jjh").toString());
	                ps.setString(6, ((Map<String, Object>)shopsList.get(i)).get("jzll").toString());
	                ps.setString(7, ((Map<String, Object>)shopsList.get(i)).get("sfbl").toString());
	                ps.setString(8, ((Map<String, Object>)shopsList.get(i)).get("htll").toString());
	                ps.setString(9, ((Map<String, Object>)shopsList.get(i)).get("tkbh").toString());
	                ps.setString(10,((Map<String, Object>)shopsList.get(i)).get("ffje").toString());
	                ps.setString(11,((Map<String, Object>)shopsList.get(i)).get("tkyt").toString());
	                ps.setString(12,((Map<String, Object>)shopsList.get(i)).get("jkrq").toString());
	                ps.setString(13,((Map<String, Object>)shopsList.get(i)).get("dqrq").toString());
	                ps.setString(14,((Map<String, Object>)shopsList.get(i)).get("dkkm").toString());
	                ps.setString(15,((Map<String, Object>)shopsList.get(i)).get("xgry").toString());
	                ps.setString(16,((Map<String, Object>)shopsList.get(i)).get("xgsj").toString());
	                ps.setString(17,((Map<String, Object>)shopsList.get(i)).get("dkqx").toString());
	                ps.setString(18,((Map<String, Object>)shopsList.get(i)).get("zqdqr").toString());
	                ps.setString(19,((Map<String, Object>)shopsList.get(i)).get("sfzq").toString());
	                ps.setString(20,((Map<String, Object>)shopsList.get(i)).get("sfjq").toString());
	                ps.setString(21,((Map<String, Object>)shopsList.get(i)).get("dkzl").toString());
	                ps.setString(22,((Map<String, Object>)shopsList.get(i)).get("dkfs").toString());
	                ps.setString(23,((Map<String, Object>)shopsList.get(i)).get("bljg").toString());
	                ps.setString(24,((Map<String, Object>)shopsList.get(i)).get("sfzf").toString());
	                ps.setString(25,((Map<String, Object>)shopsList.get(i)).get("khjl").toString());
	                ps.setString(26,((Map<String, Object>)shopsList.get(i)).get("zqhth").toString());
	                ps.setString(27,((Map<String, Object>)shopsList.get(i)).get("dkzh").toString());
	                ps.setString(28,((Map<String, Object>)shopsList.get(i)).get("sfjz").toString());
	                ps.setString(29,((Map<String, Object>)shopsList.get(i)).get("dkfl").toString());
	                ps.setString(30,((Map<String, Object>)shopsList.get(i)).get("dklx").toString());
	                ps.setString(31,((Map<String, Object>)shopsList.get(i)).get("jjbh").toString());
	                ps.setString(32,((Map<String, Object>)shopsList.get(i)).get("cpmc").toString());
	                ps.setString(33,((Map<String, Object>)shopsList.get(i)).get("dkxz").toString());
	                ps.setString(34,((Map<String, Object>)shopsList.get(i)).get("dkyt").toString());
	                ps.setString(35,((Map<String, Object>)shopsList.get(i)).get("dktx").toString());
	                ps.setString(36,((Map<String, Object>)shopsList.get(i)).get("llzl").toString());
	                ps.setString(37,((Map<String, Object>)shopsList.get(i)).get("jxfs").toString());
	                ps.setString(38,((Map<String, Object>)shopsList.get(i)).get("hkly").toString());
	                ps.setString(39,((Map<String, Object>)shopsList.get(i)).get("hkfs").toString());
	                ps.setString(40,((Map<String, Object>)shopsList.get(i)).get("jqrq").toString());
	                ps.setString(41,((Map<String, Object>)shopsList.get(i)).get("ywlx").toString());
	                ps.setString(42,((Map<String, Object>)shopsList.get(i)).get("zqrq").toString());
	                ps.setString(43,((Map<String, Object>)shopsList.get(i)).get("hbzl").toString());
	                ps.setString(44,((Map<String, Object>)shopsList.get(i)).get("ytmx").toString());
	                ps.setString(45,((Map<String, Object>)shopsList.get(i)).get("sfxedk").toString());
	                ps.setString(46,((Map<String, Object>)shopsList.get(i)).get("yqsf").toString());
	                ps.setString(47,((Map<String, Object>)shopsList.get(i)).get("shyj").toString());
	                ps.setString(48,((Map<String, Object>)shopsList.get(i)).get("shry").toString());
	                ps.setString(49,((Map<String, Object>)shopsList.get(i)).get("shsj").toString());
	                ps.setString(50,((Map<String, Object>)shopsList.get(i)).get("txlx").toString());
	                ps.setString(51,((Map<String, Object>)shopsList.get(i)).get("yqll").toString());
	                ps.setString(52,((Map<String, Object>)shopsList.get(i)).get("sftxje").toString());
	                ps.setString(53,((Map<String, Object>)shopsList.get(i)).get("nyjfbl").toString());
	                ps.setString(54,((Map<String, Object>)shopsList.get(i)).get("blry").toString());
	                ps.setString(55,((Map<String, Object>)shopsList.get(i)).get("yjjh").toString());
	                ps.setString(56,((Map<String, Object>)shopsList.get(i)).get("sfstdk").toString());
	                ps.setString(57,((Map<String, Object>)shopsList.get(i)).get("ystxyh").toString());
	                ps.setString(58,((Map<String, Object>)shopsList.get(i)).get("sfyxdk").toString());
	                ps.setString(59,((Map<String, Object>)shopsList.get(i)).get("yxr").toString());
	                ps.setString(60,((Map<String, Object>)shopsList.get(i)).get("flcorzlc").toString());
	               }
	               public int getBatchSize()
	               {
	                return shopsList.size();
	               }
	        });
	}
	/*
	 * 批量插入还款计划
	 */
	public void insertkdkjh(List<Map<String, Object>> list){
		 final List<Map<String, Object>> shopsList = list;
	        String sql =    "  insert into ty_kdk_jh (YWBH,  JHLX,QS,TS,RQ,JE,YHLX,BXHJ,DKYE,BZ )  values    (?,  ? ,  ? ,  ? ,  ? ,  ? ,  ? ,  ? ,  ? ,  ? )";
	          jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){
	            public void setValues(PreparedStatement ps,int i)throws SQLException
	               {
	                ps.setString(1, ((Map<String, Object>)shopsList.get(i)).get("ywbh").toString());
	                ps.setString(2, ((Map<String, Object>)shopsList.get(i)).get("jhlx").toString());
	                ps.setString(3, ((Map<String, Object>)shopsList.get(i)).get("qs").toString());
	                ps.setString(4, ((Map<String, Object>)shopsList.get(i)).get("ts").toString());
	                ps.setString(5, ((Map<String, Object>)shopsList.get(i)).get("rq").toString());
	                ps.setString(6, ((Map<String, Object>)shopsList.get(i)).get("je").toString());
	                ps.setString(7, ((Map<String, Object>)shopsList.get(i)).get("yhlx").toString());
	                ps.setString(8, ((Map<String, Object>)shopsList.get(i)).get("bxhj").toString());
	                ps.setString(9, ((Map<String, Object>)shopsList.get(i)).get("dkye").toString());
	                ps.setString(10, ((Map<String, Object>)shopsList.get(i)).get("bz").toString());
	               }
	               public int getBatchSize()
	               {
	                return shopsList.size();
	               }
	        });
	}
}
