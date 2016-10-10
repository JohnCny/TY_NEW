package com.cardpay.pccredit.customer.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.customer.model.CustomerInforUpdateBalanceSheet;
import com.cardpay.pccredit.customer.model.CustomerInforUpdateCashFlow;
import com.cardpay.pccredit.customer.model.CustomerInforUpdateCrossExamination;
import com.cardpay.pccredit.customer.model.CustomerInforUpdateIncomeStatement;
import com.cardpay.pccredit.customer.model.CustomerinforUpdateWorship;
import com.cardpay.pccredit.intopieces.model.Dcdhd;
import com.cardpay.pccredit.intopieces.model.Dcgdzc;
import com.cardpay.pccredit.intopieces.model.Dclsfx;
import com.cardpay.pccredit.intopieces.model.Dcyfys;
import com.cardpay.pccredit.intopieces.model.Dcysyf;
import com.wicresoft.util.annotation.Mapper;

@Mapper
public interface CustomerInforUpdateDao {
	
	/**
	 * 删除维护客户信息
	 * 
	 * 
	 */
	public int deleteCustomerInforUpdateCrossExamination(String id);
	public int deleteCustomerInforUpdateCrossExamination1(@Param("customerId")String customerId,@Param("productId")String productId);
	
	/**
	 * 删除维护客户信息
	 * 
	 * 
	 */
	public int deleteCustomerInforUpdateCashFlow(String id);
	public int deleteCustomerInforUpdateCashFlow1(@Param("customerId")String customerId,@Param("productId")String productId);
	public int deleteDhd(@Param("customerId")String customerId,@Param("productId")String productId);
	
	public int deleteGdzc(@Param("customerId")String customerId,@Param("productId")String productId);
	
	public int deleteYfys(@Param("customerId")String customerId,@Param("productId")String productId);
	public int deleteYsyf(@Param("customerId")String customerId,@Param("productId")String productId);
	
	/**
	 * 删除维护客户信息
	 * 
	 * 
	 */
	public int deleteCustomerInforUpdateIncomeStatement(String id);
	
	/**
	 * 删除维护客户信息
	 * 
	 * 
	 */
	public int deleteCustomerInforUpdateBalanceSheet(String id);
	
	public int deleteCustomerInforUpdateBalanceSheetById(@Param("customerId")String customerId,@Param("productId")String productId);
	
	/**
	 * 获取维护客户信息
	 * 
	 * @param string id
	 * 	id 客户Id
	 * 
	 * @return CustomerInforUpdateBalanceSheet
	 */
	public List<CustomerInforUpdateBalanceSheet> getCustomerInforUpdateBalanceSheetById(String id);
	public List<CustomerInforUpdateBalanceSheet> getCustomerInforUpdateBalanceSheetByCustIdAndProdId(@Param("customerId")String customerId,@Param("productId")String productId);
	
	
	/**
	 * 获取维护客户信息
	 * 
	 * @param string id
	 * 	id 客户Id
	 * 
	 * @return CustomerInforUpdateIncomeStatement
	 */
	public List<CustomerInforUpdateIncomeStatement> getCustomerInforUpdateIncomeStatementById(String id);
	
	/**
	 * 获取维护客户信息
	 * 
	 * @param string id
	 * 	id 客户Id
	 * 
	 * @return CustomerInforUpdateIncomeStatement
	 */
	public List<CustomerInforUpdateCashFlow> getCustomerInforUpdateCashFlowById(String id);
	public List<CustomerInforUpdateCashFlow> getCustomerInforUpdateCashFlowById1(@Param("customerId")String customerId,@Param("productId")String productId);
	public List<Dcdhd> getDcdhd(@Param("customerId")String customerId,@Param("productId")String productId);
	
	public List<Dcgdzc> getDcgdzc(@Param("customerId")String customerId,@Param("productId")String productId);
	public List<Dcyfys> getDcyfys(@Param("customerId")String customerId,@Param("productId")String productId);
	public List<Dcysyf> getDcysyf(@Param("customerId")String customerId,@Param("productId")String productId);
	
	public List<Dclsfx> getDclsfx(@Param("customerId")String customerId,@Param("productId")String productId);
	
	/**
	 * 获取维护客户信息
	 * 
	 * @param string id
	 * 	id 客户Id
	 * 
	 * @return CustomerInforUpdateIncomeStatement
	 */
	public List<CustomerInforUpdateCrossExamination> getCustomerInforUpdateCrossExaminationById(String id);
	
	public List<CustomerInforUpdateCrossExamination> getCustomerInforUpdateCrossExaminationById1(@Param("customerId")String customerId,@Param("productId")String productId);
	
	
	/**
	 *根据进件ID获得客户陌拜信息快照
	 * 
	 * @param string id
	 * 	id 进件Id
	 * 
	 * @return CustomerInforUpdateIncomeStatement
	 */
	public CustomerinforUpdateWorship getCustomerinforUpdateWorshipByIntoId(@Param("id") String id);
	
}
