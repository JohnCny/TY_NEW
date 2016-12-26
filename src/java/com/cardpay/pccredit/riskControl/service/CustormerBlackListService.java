package com.cardpay.pccredit.riskControl.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.jnpad.dao.InputHmdDao1;
import com.cardpay.pccredit.jnpad.dao.JnpadCustomerSelectDao;
import com.cardpay.pccredit.jnpad.model.CustomerInfo;
import com.cardpay.pccredit.report.model.CustomerHmd;
import com.cardpay.pccredit.riskControl.dao.CustormerBlackListDao;
import com.cardpay.pccredit.riskControl.filter.RiskCustomerFilter;
import com.cardpay.pccredit.riskControl.model.CUSTOMERBLACKLIST;
import com.cardpay.pccredit.riskControl.model.RiskCustomer;
import com.wicresoft.jrad.base.database.model.QueryResult;
@Service
public class CustormerBlackListService {
	@Autowired
	private CustormerBlackListDao cdao;
	@Autowired
	private InputHmdDao1 inputhmddao;
	@Autowired
	private JnpadCustomerSelectDao selectDao;
	/**\
	 *筛选黑名单客户
	 * @param cl
	 * @return
	 */
	public QueryResult<CUSTOMERBLACKLIST> findCustormerBlackList1(CUSTOMERBLACKLIST cl) {
		List<CUSTOMERBLACKLIST> riskCustomers = cdao.findCustormerBlackList(cl);
		int size = cdao.findCustormerBlackListCount(cl);
		QueryResult<CUSTOMERBLACKLIST> qs = new QueryResult<CUSTOMERBLACKLIST>( size,riskCustomers);
		return qs;
	}
	/**
	 * 添加黑名单客户
	 * @param cl
	 * @return
	 */
	public int addCustomerBlackList(String id,String userid,String custormerid,String reason ){
		return cdao.addCustomerBlackList(id,userid,custormerid,reason);
	}
	/**
	 * 查看当前客户经理的黑名单客户数量
	 * @param cl
	 * @return
	 */
	public int findAllCustormerBlackListCount(CUSTOMERBLACKLIST cl){
		return cdao.findAllCustormerBlackListCount(cl);
	}
	/**
	 * 当前客户经理详细黑名单信息
	 * @param cl
	 * @return
	 */
	public CustomerHmd findCustormerBlackList(String cardId){
		return inputhmddao.selectByCardId(cardId);
	}
	
	/**
	 * 当前客户经理的黑名单客户
	 * @param cl
	 * @return
	 */
	public QueryResult<CUSTOMERBLACKLIST> findAllCustormerBlackList1(CUSTOMERBLACKLIST cl) {
		List<CUSTOMERBLACKLIST> riskCustomers = cdao.findAllCustormerBlackList(cl);
		int size = cdao.findAllCustormerBlackListCount(cl);
		QueryResult<CUSTOMERBLACKLIST> qs = new QueryResult<CUSTOMERBLACKLIST>( size,riskCustomers);
		return qs;
	}
	
	public int deleteByCoustorId(@Param("cardId")String cardId){
		return inputhmddao.deleteByCardId(cardId);
	}
	public QueryResult<CUSTOMERBLACKLIST> findAllNoCustormerBlackList(CUSTOMERBLACKLIST cl) {
		List <CUSTOMERBLACKLIST> result=cdao.findAllNoCustormerBlackList(cl);
		int size=cdao.findAllNoCustormerBlackListCount( cl);
		
		QueryResult<CUSTOMERBLACKLIST> qs = new QueryResult<CUSTOMERBLACKLIST>( size,result);
		return qs;
	}
	
	
	public int selectCount(@Param("customerid")String customerid){
		return cdao.selectCount( customerid);
	}
	public List<CustomerInfo> selectCusByUser(String userId){
		return selectDao.selectCusByUser(userId);
		
	}


}
