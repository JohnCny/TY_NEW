/**
 * 
 */
package com.cardpay.pccredit.manager.service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.intopieces.constant.Constant;
import com.cardpay.pccredit.intopieces.model.IntoPieces;
import com.cardpay.pccredit.intopieces.model.TyApplicationLog;
import com.cardpay.pccredit.intopieces.service.IntoPiecesService;
import com.cardpay.pccredit.manager.model.ManagerSalaryParameter;
import com.cardpay.pccredit.manager.web.AccountManagerParameterForm;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.id.IDGenerator;

/**
 * 描述 ：同步系统中的进件的statusservice
 * @author 宋辰
 */
@Service
public class CustomerApplicationInfoSynchScheduleService {
	
	private Logger logger = Logger.getLogger(CustomerApplicationInfoSynchScheduleService.class);
	
	@Autowired
	private IntoPiecesService intoPiecesService;
	@Autowired
	private AccountManagerParameterService accountManagerParameterService;
	@Autowired
	private CommonDao commonDao;
	
	
	/**
	 * 济南
	 * 同步进件状态 
	 * 已放款
	 * 贷款已结清
	 */
	private void dosynchJnCustAppInfoMethod(){
		//获取今日日期
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		String dateString = format.format(new Date());
		
		logger.info(dateString+"进件状态更新开始（已放款）**********");
		List<IntoPieces> intoPiecesList = intoPiecesService.findCustomerApplicationInfoJnFK();
		for(IntoPieces intoPieces:intoPiecesList){
			IntoPieces  pieces = new IntoPieces();
			pieces.setStatus(Constant.END);//已放款
			pieces.setActualAmt(intoPieces.getAmt());//批准金额
			pieces.setId(intoPieces.getId());
			intoPiecesService.updateCustomerApplicationInfoJn(pieces);
		}
		logger.info(dateString+"进件状态更新结束（已放款）**********");
		
		/*logger.info(dateString+"进件状态更新开始（已还清）**********");
		List<IntoPieces> list = intoPiecesService.findCustomerApplicationInfoJnHQ();
		for(IntoPieces intoPieces:list){
			IntoPieces  pieces = new IntoPieces();
			pieces.setRepayStatus("1");//1-已还清
			pieces.setId(intoPieces.getId());
			intoPiecesService.updateCustomerApplicationInfoJn(pieces);
		}
		logger.info(dateString+"进件状态更新结束（已还清）**********");*/
	}
	

	
	
	
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	

	/**
	 * 同步进件状态(更新为已放款)
	 * @throws IOException 
	 */
	/*private void dosynchMethod() throws IOException{
		//获取今日日期
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		String dateString = format.format(new Date());
		logger.info(dateString+"进件状态更新开始（已放款）**********");
		//查询已经审核通过的进件信息
		List<IntoPieces> intoPiecesList = intoPiecesService.findCustomerApplicationInfo();
		for(IntoPieces intoPieces:intoPiecesList){
			//更新进件申请表 进件状态 status、借据号关联
			IntoPieces  pieces = new IntoPieces();
			pieces.setStatus(Constant.END);//放款成功
			pieces.setId(intoPieces.getId());
			pieces.setJjh(intoPieces.getJjh());
			pieces.setJkrq(intoPieces.getJkrq());
			intoPiecesService.updateCustomerApplicationInfo(pieces);
		}
		logger.info(dateString+"进件状态更新结束（已放款）**********");
	}*/
	
/*	*//**
	 * 同步进件状态（还款已结束）
	 * @throws IOException 
	 *//*
	private void dosynchMethodEnd() throws IOException{
		//获取今日日期
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		String dateString = format.format(new Date());
		logger.info(dateString+"进件状态更新开始（还款结束）**********");
		//查询已经审核通过的进件信息
		List<IntoPieces> intoPiecesList = intoPiecesService.findCustomerApplicationInfo();
		for(IntoPieces intoPieces:intoPiecesList){
			//更新进件申请表 进件状态 status
			IntoPieces  pieces = new IntoPieces();
			pieces.setCustomerId(intoPieces.getCustomerId());
			pieces.setStatus(Constant.END);//放款成功
			pieces.setProductId(intoPieces.getProductId());
			intoPiecesService.updateCustomerApplicationInfo(pieces);
		}
		logger.info(dateString+"进件状态更新结束（还款结束）**********");
	}*/
	
	/*
	 * 每月定时计算客户经理管户、主调、辅调等信息
	 */
	private void monthParmter() throws Exception{
		//获取所有客户经理
		List<AccountManagerParameterForm> accountList = accountManagerParameterService.findAccountManagerParameterAll();
	      //yyyyMMdd格式
		DateFormat format = new SimpleDateFormat("yyyyMM");
		Calendar c = Calendar.getInstance(); //获得当前时间
		c.add(Calendar.MONTH, -1); //减一,就是上一个月
		//获得上月时间（yyyyMM格式）
		String month = format.format(c.getTime());
		//获取上月进件总数
		String sql = "select a.id,b.user_id ,a.actual_quote  from customer_application_info a left join basic_customer_information b on a.customer_id=b.id where SUBSTR(a.jkrq, 1, 6)='"+month+"'";
		List<IntoPieces> appList = commonDao.queryBySql(IntoPieces.class, sql, null);
		//获取上月进件余额不为0（管户数）
		String tubesSql = "select a.id,c.user_id  from CUSTOMER_APPLICATION_INFO a LEFT JOIN TY_REPAY_YEHZ b on a.JJH=b.JJH  LEFT JOIN BASIC_CUSTOMER_INFORMATION c on a.customer_id=c.id where a.STATUS='end' and b.dkye!='0.00'";
		List<IntoPieces> tubesList = commonDao.queryBySql(IntoPieces.class, tubesSql, null);
		//循环计算客户经理当月工资
		for(int i=0;i<accountList.size();i++){
			//客户经理id
			String userId = accountList.get(i).getUserId();
			//主调数
			int zdCount =0;
			//管户数
			int tubesCount =0;
			//辅调数
			int fdCount =0;
			//审批数
			int spCount =0;
			//完成折算笔数(主调笔数折算)
			int competerCount=0;
			for(int j=0;j<appList.size();j++){
				if(appList.get(j).getUserId().equals(userId)){
					zdCount++;
					competerCount+=countCompeter(appList.get(j).getActualQuote());
				}
			}
			for(int j=0;j<tubesList.size();j++){
				if(tubesList.get(j).getUserId().equals(userId)){
					tubesCount++;
				}
			}
			//获取辅调数
			List<TyApplicationLog> fdList = commonDao.queryBySql(TyApplicationLog.class, "select * from TY_APPLICATION_LOG  where type='2' and APPLICATION_ID in (select id from CUSTOMER_APPLICATION_INFO where jkrq='"+month+"')", null);
			fdCount = fdList.size();
			//获取审批数
			List<TyApplicationLog> spList = commonDao.queryBySql(TyApplicationLog.class, "select * from TY_APPLICATION_LOG  where type='1' and APPLICATION_ID in (select id from CUSTOMER_APPLICATION_INFO where jkrq='"+month+"')", null);
			spCount = spList.size();
			
			//保存
			ManagerSalaryParameter parameter = new ManagerSalaryParameter();
			parameter.setId(IDGenerator.generateID());
			parameter.setMonth(month);
			parameter.setUserId(userId);
			parameter.setZdCount(competerCount+"");//主调折算
			parameter.setFdCount(fdCount+"");
			parameter.setSpCount(spCount+"");
			parameter.setTubes(tubesCount+"");
			parameter.setCompeterCount(zdCount+"");//完成笔数不折算，按实际笔数
			commonDao.insertObject(parameter);
		}
}
	
	/*
	 * 笔数折算
	 */
	private int countCompeter(String quote){
		Double amount = Double.parseDouble(quote);
		if(5000<amount&&amount<100000){
			return 1;
		}else if(amount>=100000&&amount<300000){
			return 2;
		}else if(amount>=300000&&amount<500000){
			return 3;
		}else if(amount>=500000&&amount<1000000){
			return 4;
		}else{
			return 6;
		}
	}
}
