package com.cardpay.pccredit.report.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.manager.model.AccountManagerParameter;
import com.cardpay.pccredit.report.dao.CustomerTransferFlowDao;
import com.cardpay.pccredit.report.filter.AccLoanCollectFilter;
import com.cardpay.pccredit.report.filter.CustomerMoveFilter;
import com.cardpay.pccredit.report.filter.ReportFilter;
import com.cardpay.pccredit.report.model.AccLoanCollectInfo;
import com.cardpay.pccredit.report.model.BjjdktjbbForm;
import com.cardpay.pccredit.report.model.CustomerMoveForm;
import com.cardpay.pccredit.report.model.DkyetjbbForm;
import com.cardpay.pccredit.report.model.DqzzdktjbbForm;
import com.cardpay.pccredit.report.model.TkmxLszBasic;
import com.cardpay.pccredit.report.model.XdlctjbbForm;
import com.cardpay.pccredit.report.model.YffdktjbbForm;
import com.cardpay.pccredit.report.model.YqdktjbbForm;
import com.cardpay.pccredit.report.model.YqhkdktjbbForm;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.model.QueryResult;

@Service
public class CustomerTransferFlowService {

	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private CustomerTransferFlowDao customerTransferFlowDao;
	/**
	 * 客户经理移交流水查询
	 * @param filter
	 * @return
	 */
	public QueryResult<CustomerMoveForm> findCustomerMoveList(CustomerMoveFilter filter){
		List<CustomerMoveForm> list = customerTransferFlowDao.findCustomerTransferList(filter);
		int size = customerTransferFlowDao.findCustomerMoveCountList(filter);
		QueryResult<CustomerMoveForm> result = new QueryResult<CustomerMoveForm>(size,list);
		return result;
	} 
	
	public List<CustomerMoveForm> getCustomerMovelList(CustomerMoveFilter filter){
		return customerTransferFlowDao.getCustomerMovelList(filter);
	}
	
	/**
	 *  已发放贷款统计
	 */
	public QueryResult<YffdktjbbForm> findYffdktjbbFormList(ReportFilter filter){
		List<YffdktjbbForm> list = customerTransferFlowDao.findYffdktjbbFormList(filter);
		int size = customerTransferFlowDao.findYffdktjbbFormCountList(filter);
		QueryResult<YffdktjbbForm> result = new QueryResult<YffdktjbbForm>(size,list);
		return result;
	} 
	
	
	public List<YffdktjbbForm> getYffdktjbbFormlList(ReportFilter filter){
		return  customerTransferFlowDao.getYffdktjbbFormlList(filter);
	}
	/**
	 *	被拒绝贷款统计
	 */
	public QueryResult<BjjdktjbbForm> findbjjdktjbbFormList(ReportFilter filter){
		List<BjjdktjbbForm> list = customerTransferFlowDao.findbjjdktjbbFormList(filter);
		int size = customerTransferFlowDao.findbjjdktjbbFormCountList(filter);
		QueryResult<BjjdktjbbForm> result = new QueryResult<BjjdktjbbForm>(size,list);
		return result;
	} 
	
	public List<BjjdktjbbForm> getbjjdktjbbFormList(ReportFilter filter){
		return customerTransferFlowDao.getbjjdktjbbFormList(filter);
	}
	
	/**
	 *	到期终止贷款统计
	 * @throws ParseException 
	 */
	public QueryResult<DqzzdktjbbForm> findDqzzdktjbbFormList(ReportFilter filter) throws ParseException{
		//从数据库里得到贷款的到期日起和系统现在日期相比 在系统现在日起之前的可视为已到期贷款
		List<DqzzdktjbbForm> list = customerTransferFlowDao.findDqzzdktjbbFormList(filter);
		int size = customerTransferFlowDao.findDqzzdktjbbFormCountList(filter);
		QueryResult<DqzzdktjbbForm> result = new QueryResult<DqzzdktjbbForm>(size,list);
		return result;
	} 
	
	public List<DqzzdktjbbForm> getDqzzdktjbbFormList(ReportFilter filter){
		return customerTransferFlowDao.getDqzzdktjbbFormList(filter);
	}
	
	
	/**
	 *	贷款余额统计
	 */
	public QueryResult<DkyetjbbForm> findDkyetjbbFormList(ReportFilter filter){
		List<DkyetjbbForm> list = customerTransferFlowDao.findDkyetjbbFormList(filter);
		int size = customerTransferFlowDao.findDkyetjbbFormCountList(filter);
		QueryResult<DkyetjbbForm> result = new QueryResult<DkyetjbbForm>(size,list);
		return result;
	} 
	
	public List<DkyetjbbForm> getDkyetjbbFormList(ReportFilter filter){
		return customerTransferFlowDao.getDkyetjbbFormList(filter);
	}
	
	/**
	 * 逾期贷款统计
	 */
	public QueryResult<YqdktjbbForm> findYqdktjbbFormList(ReportFilter filter){
		List<YqdktjbbForm> list = customerTransferFlowDao.findYqdktjbbFormList(filter);
		int size = customerTransferFlowDao.findYqdktjbbFormCountList(filter);
		QueryResult<YqdktjbbForm> result = new QueryResult<YqdktjbbForm>(size,list);
		return result;
	} 
	
	public List<YqdktjbbForm> getYqdktjbbFormList(ReportFilter filter){
		return customerTransferFlowDao.getYqdktjbbFormList(filter);
	}
	
	/**
	 *  预期还款贷款统计
	 */
	public QueryResult<YqhkdktjbbForm> findYqhkdktjbbFormList(ReportFilter filter){
		List<YqhkdktjbbForm> list = customerTransferFlowDao.findYqhkdktjbbFormList(filter);
		int size = customerTransferFlowDao.findYqhkdktjbbFormCountList(filter);
		QueryResult<YqhkdktjbbForm> result = new QueryResult<YqhkdktjbbForm>(size,list);
		return result;
	} 
	
	public List<TkmxLszBasic> getYqhkdktjbbFormList(ReportFilter filter){
		return customerTransferFlowDao.getYqhkdktjbbFormList(filter);
	}
	
	
	/**
	 *	信贷流程统计
	 */
	public QueryResult<XdlctjbbForm> findXdlctjbbFormList(ReportFilter filter){
		List<XdlctjbbForm> list = customerTransferFlowDao.findXdlctjbbFormList(filter);
		int size = customerTransferFlowDao.findXdlctjbbFormCountList(filter);
		QueryResult<XdlctjbbForm> result = new QueryResult<XdlctjbbForm>(size,list);
		return result;
	} 
	
	public List<XdlctjbbForm> getXdlctjbbFormList(ReportFilter filter){
		return customerTransferFlowDao.getXdlctjbbFormList(filter);
	}
	
	/**
	 * 贷款汇总统计
	 */
	public List<AccLoanCollectInfo> getAccLoanCollect(AccLoanCollectFilter filter){
		return customerTransferFlowDao.getAccLoanCollect(filter);
	}
	
	public List<AccountManagerParameter> findManager(String userId){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		String sql = "select * from account_manager_parameter where user_id = #{userId}";
		List<AccountManagerParameter> list = commonDao.queryBySql(AccountManagerParameter.class, sql, params);
		return list;
	}
	
}
