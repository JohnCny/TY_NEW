package com.cardpay.pccredit.customer.service;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.cardpay.pccredit.customer.dao.CustomerInforDao;
import com.cardpay.pccredit.customer.dao.comdao.ReadWholeAndIncrementComdao;
import com.cardpay.pccredit.customer.model.CustomerInfor;
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
	
	//全量
	private String[] fileTxtWhole ={"t_param_class.txt",
									"t_param_param.txt",
									"t_party_type.txt",
									"t_rbac_group.txt",
									"t_rbac_user.txt"};
	
	//增量
	private String[] fileTxtIncre = {"sarm_specialasset.txt",     
									 "rarepaylist.txt",  
									 "mibusidata.txt",    
									 "gccontractmain.txt",     
									 "FCRESULTHIS.txt",         
									 "GCASSUREMULTICLIENT.txt",        
									 "FCLOANINFO.txt",   
									 "CIPERSONBADRECORD.txt",       
									 "cclmtapplyinfo.txt",
									 "cipersonbasinfo.txt",
									 "cipersonfamily.txt",
									 "gcloancredit.txt",
									 "GCCONTRACTMULTICLIENT.txt",
									 "GCCREDITTYPEINFORMATION.txt"};
	
	
	
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
	 * @throws Exception
	 * 手动 by date
	 */
	public void doReadFileIncrementByDate(String dateString) {
		//指定日期
		log.info(dateString+"******************开始手动读取增量信息文件********************");  
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
									/*if(fn.startsWith("cclmtapplyinfo")){
										log.info("*****************Cc授信申请基本信息（结果表）********************");
										customerInforService.saveCCLMTAPPLYINFODataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("CIPERSONBADRECORD")){
										log.info("*****************对私客户不良记录********************");
										customerInforService.saveCIPERSONBADRECORDDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("cipersonbasinfo")){
										log.info("*****************对私客户基本信息********************");
										customerInforService.saveCIPERSONBASINFODataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("test")){
										log.info("*****************对私家庭成员信息********************");
										customerInforService.saveCIPERSONFAMILYDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("FCLOANINFO")){
										log.info("*****************借据月末余额表（结果表）********************");
										customerInforService.saveFCLOANINFODataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("FCRESULTHIS")){
										log.info("*****************认定结果表（历史表）********************");
										customerInforService.saveFCRESULTHISDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("test")){
										log.info("*****************五级分类统计表********************");
										customerInforService.saveFCSTATISTICSDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("test")){
										log.info("*****************GC担保对应表********************");
										customerInforService.saveGCASSURECORRESPONDDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("test")){
										log.info("*****************GC担保信息表********************");
										saveGCASSUREMAINDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("gccontractmain")){
										log.info("*****************GC合同基本表********************");
										saveGCCONTRACTMAINDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("GCASSUREMULTICLIENT")){
										log.info("*****************GC从合同多方信息表********************");
										saveGCASSUREMULTICLIENTDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("test")){
										log.info("*****************GC押品主表********************");
										saveGCGUARANTYMAINDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("test")){
										log.info("*****************Gc贷款证表 ********************");
										saveGCLOANCARDDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("test")){
										log.info("*****************Gc贷款证合同关联关系表 ********************");
										saveGCLOANCARDCONTRACTDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("test")){
										log.info("*****************Gc凭证信息表 ********************");
										saveGCLOANCREDITDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("mibusidata")){
										log.info("*****************台账——综合业务信息表  ********************");
										saveMIBUSIDATADataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("test")){
										log.info("*****************台账——贷款卡片********************");
										saveMILOANCARDDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("test")){
										log.info("*****************黑名单客户结果表 ********************");
										saveBWLISTDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("rarepaylist")){
										log.info("*****************还款情况表  ********************");
										saveRAREPAYLISTDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("sarm_specialasset")){
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
	
}
