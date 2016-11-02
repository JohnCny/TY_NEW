package com.cardpay.pccredit.riskControl.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.riskControl.dao.CustormerBlackListDao;
import com.cardpay.pccredit.riskControl.filter.RiskCustomerFilter;
import com.cardpay.pccredit.riskControl.model.CUSTOMERBLACKLIST;
import com.cardpay.pccredit.riskControl.model.RiskCustomer;
import com.wicresoft.jrad.base.database.model.QueryResult;
@Service
public class CustormerBlackListService {
	@Autowired
	private CustormerBlackListDao cdao;
	
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
	public List <CUSTOMERBLACKLIST> findCustormerBlackList(CUSTOMERBLACKLIST cl){
		return cdao.findAllCustormerBlackList(cl);
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
	
	public int deleteByCoustorId(@Param("customerid")String customerid,@Param("userid")String userid){
		return cdao.deleteByCoustorId(customerid,userid);
	}
	public QueryResult<CUSTOMERBLACKLIST> findAllNoCustormerBlackList(CUSTOMERBLACKLIST cl) {
		List <CUSTOMERBLACKLIST> result=cdao.findAllNoCustormerBlackList(cl);
		int size=cdao.findAllNoCustormerBlackListCount( cl);
		String card="身份证";
		for(int a=0;a<size;a++){
			if(result.get(a).getCard_type().equals("0")){
				result.get(a).setCard_type(card);
			}
		}
		QueryResult<CUSTOMERBLACKLIST> qs = new QueryResult<CUSTOMERBLACKLIST>( size,result);
		return qs;
	}
	
	
	public int selectCount(@Param("customerid")String customerid){
		return cdao.selectCount( customerid);
	}


}
