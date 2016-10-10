/**
 * 
 */
package com.cardpay.pccredit.customer.service;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.common.Arith;
import com.cardpay.pccredit.customer.constant.BalanceSheetConstant;

import org.apache.commons.lang.StringUtils;

import com.cardpay.pccredit.customer.dao.CustomerInforUpdateDao;
import com.cardpay.pccredit.customer.filter.CustomerInforUpdateBalanceSheetFilter;
import com.cardpay.pccredit.customer.filter.CustomerInforUpdateWorshipFilter;
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
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.model.QueryResult;

/**
 * 客户维护信息
 * 
 * @author Evans zhang
 * 
 *         2014-10-13 下午2:37:07
 */
@Service
public class CustomerInforUpdateService {
	
	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private CustomerInforUpdateDao customerInforUpdateDao;
	
	
	/*TODO
	1.注释需要明晰，如：修改客户维护信息-损益表，获取客户维护信息-损益表
	2.所有新增，修改，删除操作增加返回结果
	*/
	/**
	 * 获取维护客户信息
	 * 
	 * @param string id
	 * 	id 客户Id
	 * 
	 * @return CustomerInforUpdateBalanceSheet
	 */
	public List<CustomerInforUpdateBalanceSheet> getCustomerInforUpdateBalanceSheetById(String id){
		return customerInforUpdateDao.getCustomerInforUpdateBalanceSheetById(id);
	}
	
	public List<CustomerInforUpdateBalanceSheet> getCustomerInforUpdateBalanceSheetByCustIdAndProdId(String customerId,String productId){
		return customerInforUpdateDao.getCustomerInforUpdateBalanceSheetByCustIdAndProdId(customerId,productId);
	}
	
	/**
	 * 通过客户ID获取资产负债表的“所有者权益”
	 * @param filter
	 * @return  COURTFORENFORCEMENT
	 */
	public CustomerInforUpdateBalanceSheet findOwnershipFilter(String customerId) {
		List<CustomerInforUpdateBalanceSheet> list = getCustomerInforUpdateBalanceSheetById(customerId);
		CustomerInforUpdateBalanceSheet customerBalanceSheet = null;
		for(CustomerInforUpdateBalanceSheet balanceSheet : list){
			if(BalanceSheetConstant.OWNERSHIP == balanceSheet.getLoanType()){
				customerBalanceSheet = balanceSheet;
			}
		}
		return customerBalanceSheet;
	}
	
	public CustomerInforUpdateBalanceSheet findOwnershipFilter1(String customerId,String productId) {
		List<CustomerInforUpdateBalanceSheet> list = getCustomerInforUpdateBalanceSheetByCustIdAndProdId(customerId,productId);
		CustomerInforUpdateBalanceSheet customerBalanceSheet = null;
		for(CustomerInforUpdateBalanceSheet balanceSheet : list){
			if(BalanceSheetConstant.OWNERSHIP == balanceSheet.getLoanType()){
				customerBalanceSheet = balanceSheet;
			}
		}
		return customerBalanceSheet;
	}
	
	/**
	 * 过滤查询资产负债表
	 * @param filter
	 * @return
	 */
	public QueryResult<CustomerInforUpdateBalanceSheet> findCustomerInforUpdateBalanceSheetFilter(CustomerInforUpdateBalanceSheetFilter filter) {
		
		return commonDao.findObjectsByFilter(CustomerInforUpdateBalanceSheet.class, filter);
	}
	
	/**
	 * 根据客户Id查询陌拜信息
	 * @param filter
	 * @return CustomerinforUpdateWorship
	 */
	public CustomerinforUpdateWorship getCustomerinforUpdateWorshipById(String id){
		
		CustomerInforUpdateWorshipFilter filter =  new CustomerInforUpdateWorshipFilter();
		filter.setCustomerId(id);
		QueryResult<CustomerinforUpdateWorship> result = commonDao.findObjectsByFilter(CustomerinforUpdateWorship.class, filter);
		List<CustomerinforUpdateWorship> customerinforUpdateWorshiplist = result.getItems();
        if(customerinforUpdateWorshiplist.size() > 0){
        	CustomerinforUpdateWorship customerinforUpdateWorship = customerinforUpdateWorshiplist.get(0);
        	return customerinforUpdateWorship; 
		}else{
			
			return null;
			
		}
		
	}
	/**
	 * 根据进件Id查询陌拜信息
	 * @param filter
	 * @return CustomerinforUpdateWorship
	 */
	public CustomerinforUpdateWorship getCustomerinforUpdateWorshipByIntoId(String id){
		
		
		CustomerinforUpdateWorship result = customerInforUpdateDao.getCustomerinforUpdateWorshipByIntoId(id);
		return result;
		
	}
	
	/**
	 * 获取维护客户信息
	 * 
	 * @param string id
	 * 	id 客户Id
	 * 
	 * @return CustomerInforUpdateIncomeStatement
	 */
	public List<CustomerInforUpdateIncomeStatement> getCustomerInforUpdateIncomeStatementById(String id){
		return customerInforUpdateDao.getCustomerInforUpdateIncomeStatementById(id);
	}
	
	/**
	 * 获取维护客户信息
	 * 
	 * @param string id
	 * 	id 客户Id
	 * 
	 * @return CustomerInforUpdateIncomeStatement
	 */
	public List<CustomerInforUpdateCashFlow> getCustomerInforUpdateCashFlowById(String id){
		return customerInforUpdateDao.getCustomerInforUpdateCashFlowById(id);
	}
	public List<CustomerInforUpdateCashFlow> getCustomerInforUpdateCashFlowById1(String customerId,String productId){
		return customerInforUpdateDao.getCustomerInforUpdateCashFlowById1(customerId,productId);
	}
	
	public List<Dcdhd> getDcdhd(String customerId,String productId){
		return customerInforUpdateDao.getDcdhd(customerId,productId);
	}
	
	public List<Dcgdzc> getDcgdzc(String customerId,String productId){
		return customerInforUpdateDao.getDcgdzc(customerId,productId);
	}
	
	public List<Dcyfys> getDcyfys(String customerId,String productId){
		return customerInforUpdateDao.getDcyfys(customerId,productId);
	}
	
	public List<Dcysyf> getDcysyf(String customerId,String productId){
		return customerInforUpdateDao.getDcysyf(customerId,productId);
	}
	
	public List<Dclsfx> getDclsfx(String customerId,String productId){
		return customerInforUpdateDao.getDclsfx(customerId,productId);
	}
	
	/**
	 * 获取维护客户信息
	 * 
	 * @param string id
	 * 	id 客户Id
	 * 
	 * @return CustomerInforUpdateIncomeStatement
	 */
	public List<CustomerInforUpdateCrossExamination> getCustomerInforUpdateCrossExaminationById(String id){
		List<CustomerInforUpdateCrossExamination> list = customerInforUpdateDao.getCustomerInforUpdateCrossExaminationById(id);
		CustomerInforUpdateCrossExamination crossExamination = null;
		for(int i = 0; i < list.size(); i++){
			crossExamination = list.get(i);
			if(isMul100(list.get(i))){
				String textNumbers = crossExamination.getContentsTextNumbers();
				if(StringUtils.isNotEmpty(textNumbers)){
					// 值除以100
					crossExamination.setContentsTextNumbers(Arith.divReturnStr(textNumbers, "100", 2));
				}
			}
		}
		return list;
	}
	
	public List<CustomerInforUpdateCrossExamination> getCustomerInforUpdateCrossExaminationById1(String customerId,String productId){
		List<CustomerInforUpdateCrossExamination> list = customerInforUpdateDao.getCustomerInforUpdateCrossExaminationById1(customerId,productId);
		CustomerInforUpdateCrossExamination crossExamination = null;
		for(int i = 0; i < list.size(); i++){
			crossExamination = list.get(i);
			if(isMul100(list.get(i))){
				String textNumbers = crossExamination.getContentsTextNumbers();
				if(StringUtils.isNotEmpty(textNumbers)){
					// 值除以100
					crossExamination.setContentsTextNumbers(Arith.divReturnStr(textNumbers, "100", 2));
				}
			}
		}
		return list;
	}
	
	public boolean isMul100(CustomerInforUpdateCrossExamination crossExamination){
		boolean flag = false;
		if(crossExamination.getLoanType() > 3 && !(crossExamination.getLoanType() == 9 && crossExamination.getNo() == 3)){
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 修改客户维护信息
	 * 
	 * @param
	 * 
	 * @return int
	 */
	public void insertCustomerInforUpdateBalanceSheet(String customerId,String balanceSheet) {
		customerInforUpdateDao.deleteCustomerInforUpdateBalanceSheet(customerId);
		JSONArray balanceSheetData = JSONArray.fromObject(balanceSheet);
		for (int i = 0; i < balanceSheetData.size(); i++) {
			CustomerInforUpdateBalanceSheet balanceSheetPojo = (CustomerInforUpdateBalanceSheet) JSONObject
					.toBean((JSONArray.fromObject(balanceSheetData.toString())
							.getJSONObject(i)), CustomerInforUpdateBalanceSheet.class);
			balanceSheetPojo.setCustomerId(customerId);
			commonDao.insertObject(balanceSheetPojo);
		}
	}
	
	public void insertCustomerInforUpdateBalanceSheet1(String customerId,String balanceSheet,String productId) {
		customerInforUpdateDao.deleteCustomerInforUpdateBalanceSheetById(customerId,productId);
		JSONArray balanceSheetData = JSONArray.fromObject(balanceSheet);
		for (int i = 0; i < balanceSheetData.size(); i++) {
			CustomerInforUpdateBalanceSheet balanceSheetPojo = (CustomerInforUpdateBalanceSheet) JSONObject
					.toBean((JSONArray.fromObject(balanceSheetData.toString())
							.getJSONObject(i)), CustomerInforUpdateBalanceSheet.class);
			balanceSheetPojo.setCustomerId(customerId);
			balanceSheetPojo.setProductId(productId);
			if(balanceSheetPojo.getLoanType() !=14){
				commonDao.insertObject(balanceSheetPojo);
			}
		}
	}
	
	
	
	/**
	 * 修改客户维护信息
	 * 
	 * @param
	 * 
	 * @return int
	 */
	public void insertCustomerInforUpdateIncomeStatement(String customerId,HttpServletRequest request ) {
		customerInforUpdateDao.deleteCustomerInforUpdateIncomeStatement(customerId);
		for(int i=0;i<=30;i++){
			String[] items_name = request.getParameterValues("items_name_"+i);
			String[] month_1_1 = request.getParameterValues("month_1_"+i);
			String[] month_2_1 = request.getParameterValues("month_2_"+i);
			String[] month_3_1 = request.getParameterValues("month_3_"+i);
			String[] month_4_1 = request.getParameterValues("month_4_"+i);
			String[] month_5_1 = request.getParameterValues("month_5_"+i);
			String[] month_6_1 = request.getParameterValues("month_6_"+i);
			String[] month_7_1 = request.getParameterValues("month_7_"+i);
			String[] month_8_1 = request.getParameterValues("month_8_"+i);
			String[] month_9_1 = request.getParameterValues("month_9_"+i);
			String[] month_10_1 = request.getParameterValues("month_10_"+i);
			String[] month_11_1 = request.getParameterValues("month_11_"+i);
			String[] month_12_1 = request.getParameterValues("month_12_"+i);
			String[] total = request.getParameterValues("total_"+i);
			String[] pre_month = request.getParameterValues("pre_month_"+i);
			
			if(month_1_1!=null){
			for(int j=0;j<month_1_1.length;j++){
				CustomerInforUpdateIncomeStatement incomeStatementPojo=new CustomerInforUpdateIncomeStatement();
				incomeStatementPojo.setLoanType(i);
				if(items_name!=null){
				incomeStatementPojo.setNames(items_name[j]);
				}
				incomeStatementPojo.setNo(j);
				incomeStatementPojo.setJanuary(StoD(month_1_1[j]));
				incomeStatementPojo.setFebruary(StoD(month_2_1[j]));
				incomeStatementPojo.setMarch(StoD(month_3_1[j]));
				incomeStatementPojo.setApril(StoD(month_4_1[j]));
				incomeStatementPojo.setMay(StoD(month_5_1[j]));
				incomeStatementPojo.setJune(StoD(month_6_1[j]));
				incomeStatementPojo.setJuly(StoD(month_7_1[j]));
				incomeStatementPojo.setAugust(StoD(month_8_1[j]));
				incomeStatementPojo.setSeptember(StoD(month_9_1[j]));
				incomeStatementPojo.setOctober(StoD(month_10_1[j]));
				incomeStatementPojo.setNovember(StoD(month_11_1[j]));
				incomeStatementPojo.setDecember(StoD(month_12_1[j]));
				incomeStatementPojo.setCustomerId(customerId);
				if(total!=null){
				incomeStatementPojo.setTotalAll(StoD(total[j]));
				}
				if(pre_month!=null){
				incomeStatementPojo.setMonthlyAverage(StoD(pre_month[j]));
				}
				commonDao.insertObject(incomeStatementPojo);
			}
			}
		}
	}
	
	
	/**
	 * 修改客户维护信息
	 * 
	 * @param
	 * 
	 * @return int
	 */

	public void insertCustomerInforUpdateCashFlow(String customerId,HttpServletRequest request) {
		customerInforUpdateDao.deleteCustomerInforUpdateCashFlow(customerId);
		for(int i=0;i<=40;i++){
			String[] items_name = request.getParameterValues("items_name_"+i);
			String[] month_1_1 = request.getParameterValues("month_1_"+i);
			String[] month_2_1 = request.getParameterValues("month_2_"+i);
			String[] month_3_1 = request.getParameterValues("month_3_"+i);
			String[] month_4_1 = request.getParameterValues("month_4_"+i);
			String[] month_5_1 = request.getParameterValues("month_5_"+i);
			String[] month_6_1 = request.getParameterValues("month_6_"+i);
			String[] month_7_1 = request.getParameterValues("month_7_"+i);
			String[] month_8_1 = request.getParameterValues("month_8_"+i);
			String[] month_9_1 = request.getParameterValues("month_9_"+i);
			String[] month_10_1 = request.getParameterValues("month_10_"+i);
			String[] month_11_1 = request.getParameterValues("month_11_"+i);
			String[] month_12_1 = request.getParameterValues("month_12_"+i);
			String[] total = request.getParameterValues("total_"+i);
			String[] pre_month = request.getParameterValues("pre_month_"+i);
			
			if(month_1_1!=null){
			for(int j=0;j<month_1_1.length;j++){
				CustomerInforUpdateCashFlow customerInfoCashFlow=new CustomerInforUpdateCashFlow();
					customerInfoCashFlow.setLoanType(i);
				if(items_name!=null){
					customerInfoCashFlow.setNames(items_name[j]);
				}
				customerInfoCashFlow.setNo(j);
				if(i>38){
					customerInfoCashFlow.setJanuary(StoD(month_1_1[j]));
				}else{
				customerInfoCashFlow.setJanuary(StoD(month_1_1[j]));
				customerInfoCashFlow.setApril(StoD(month_4_1[j]));
				customerInfoCashFlow.setAugust(StoD(month_8_1[j]));
				customerInfoCashFlow.setDecember(StoD(month_12_1[j]));
				customerInfoCashFlow.setFebruary(StoD(month_2_1[j]));
				
				customerInfoCashFlow.setJuly(StoD(month_7_1[j]));
				customerInfoCashFlow.setJune(StoD(month_6_1[j]));
				customerInfoCashFlow.setMarch(StoD(month_3_1[j]));
				customerInfoCashFlow.setMay(StoD(month_5_1[j]));
				customerInfoCashFlow.setNovember(StoD(month_11_1[j]));
				customerInfoCashFlow.setOctober(StoD(month_10_1[j]));
				customerInfoCashFlow.setSeptember(StoD(month_9_1[j]));
				}
				customerInfoCashFlow.setCustomerId(customerId);
				if(total!=null){
					customerInfoCashFlow.setTotalAll(StoD(total[j]));
				}
				if(pre_month!=null){
					customerInfoCashFlow.setMonthlyAverage(StoD(pre_month[j]));
				}
				commonDao.insertObject(customerInfoCashFlow);
			}
			}
		}
		
	}
	
	/**
	 * 修改客户维护信息
	 * 
	 * @param
	 * 
	 * @return int
	 */

	public void insertCustomerInforUpdateCashFlow1(String customerId,HttpServletRequest request,String productId) {
		customerInforUpdateDao.deleteCustomerInforUpdateCashFlow1(customerId,productId);
		for(int i=0;i<=40;i++){
			String[] items_name = request.getParameterValues("items_name_"+i);
			String[] month_1_1 = request.getParameterValues("month_1_"+i);
			String[] month_2_1 = request.getParameterValues("month_2_"+i);
			String[] month_3_1 = request.getParameterValues("month_3_"+i);
			String[] month_4_1 = request.getParameterValues("month_4_"+i);
			String[] month_5_1 = request.getParameterValues("month_5_"+i);
			String[] month_6_1 = request.getParameterValues("month_6_"+i);
			String[] month_7_1 = request.getParameterValues("month_7_"+i);
			String[] month_8_1 = request.getParameterValues("month_8_"+i);
			String[] month_9_1 = request.getParameterValues("month_9_"+i);
			String[] month_10_1 = request.getParameterValues("month_10_"+i);
			String[] month_11_1 = request.getParameterValues("month_11_"+i);
			String[] month_12_1 = request.getParameterValues("month_12_"+i);
			String[] total = request.getParameterValues("total_"+i);
			String[] pre_month = request.getParameterValues("pre_month_"+i);
			
			if(month_1_1!=null){
			for(int j=0;j<month_1_1.length;j++){
				CustomerInforUpdateCashFlow customerInfoCashFlow=new CustomerInforUpdateCashFlow();
					customerInfoCashFlow.setLoanType(i);
				if(items_name!=null){
					customerInfoCashFlow.setNames(items_name[j]);
				}
				customerInfoCashFlow.setNo(j);
				if(i>38){
					customerInfoCashFlow.setJanuary(StoD(month_1_1[j]));
				}else{
				customerInfoCashFlow.setJanuary(StoD(month_1_1[j]));
				customerInfoCashFlow.setApril(StoD(month_4_1[j]));
				customerInfoCashFlow.setAugust(StoD(month_8_1[j]));
				customerInfoCashFlow.setDecember(StoD(month_12_1[j]));
				customerInfoCashFlow.setFebruary(StoD(month_2_1[j]));
				
				customerInfoCashFlow.setJuly(StoD(month_7_1[j]));
				customerInfoCashFlow.setJune(StoD(month_6_1[j]));
				customerInfoCashFlow.setMarch(StoD(month_3_1[j]));
				customerInfoCashFlow.setMay(StoD(month_5_1[j]));
				customerInfoCashFlow.setNovember(StoD(month_11_1[j]));
				customerInfoCashFlow.setOctober(StoD(month_10_1[j]));
				customerInfoCashFlow.setSeptember(StoD(month_9_1[j]));
				}
				customerInfoCashFlow.setCustomerId(customerId);
				if(total!=null){
					customerInfoCashFlow.setTotalAll(StoD(total[j]));
				}
				if(pre_month!=null){
					customerInfoCashFlow.setMonthlyAverage(StoD(pre_month[j]));
				}
				customerInfoCashFlow.setProductId(productId);
				commonDao.insertObject(customerInfoCashFlow);
			}
			}
		}
		
	}
	
	
	public void insertDhd(String customerId,HttpServletRequest request,String productId) {
		customerInforUpdateDao.deleteDhd(customerId,productId);
		for(int i=0;i<1;i++){
			String[] items_name = request.getParameterValues("items_name_"+i);
			String[] month_1_1 = request.getParameterValues("month_1_"+i);
			String[] month_2_1 = request.getParameterValues("month_2_"+i);
			String[] month_3_1 = request.getParameterValues("month_3_"+i);
			String[] month_4_1 = request.getParameterValues("month_4_"+i);
			
			if(month_1_1!=null){
				for(int j=0;j<month_1_1.length;j++){
					Dcdhd dhd=new Dcdhd();
					dhd.setLoan_type(i);
					dhd.setNames(items_name[j]);
					dhd.setHwsl(month_1_1[j]);
					dhd.setMjdj(month_2_1[j]);
					dhd.setMjaz(month_3_1[j]);
					dhd.setMcdj(month_4_1[j]);
					dhd.setCustomer_id(customerId);
					dhd.setProduct_id(productId);
					commonDao.insertObject(dhd);
				}
			}
		}
	}
	
	
	public void insertGdzc(String customerId,HttpServletRequest request,String productId) {
		customerInforUpdateDao.deleteGdzc(customerId,productId);
		for(int i=0;i<1;i++){
			String[] items_name = request.getParameterValues("items_name_"+i);
			String[] month_1_1 = request.getParameterValues("month_1_"+i);
			String[] month_2_1 = request.getParameterValues("month_2_"+i);
			String[] month_3_1 = request.getParameterValues("month_3_"+i);
			String[] month_4_1 = request.getParameterValues("month_4_"+i);
			String[] month_5_1 = request.getParameterValues("month_5_"+i);
			String[] month_6_1 = request.getParameterValues("month_6_"+i);
			
			if(month_1_1!=null){
				for(int j=0;j<month_1_1.length;j++){
					Dcgdzc gdzc = new Dcgdzc();
					gdzc.setLoan_type(i);
					gdzc.setNames(items_name[j]);
					gdzc.setGzsj(month_1_1[j]);
					gdzc.setYsdj(month_2_1[j]);
					gdzc.setZjbl(month_3_1[j]);
					gdzc.setSl(month_4_1[j]);
					gdzc.setZjz(month_5_1[j]);
					gdzc.setZhjz(month_6_1[j]);
					gdzc.setCustomer_id(customerId);
					gdzc.setProduct_id(productId);
					commonDao.insertObject(gdzc);
				}
			}
		}
	}
	
	public void insertYfys(String customerId,HttpServletRequest request,String productId) {
		customerInforUpdateDao.deleteYfys(customerId,productId);
		for(int i=0;i<1;i++){
			String[] items_name = request.getParameterValues("items_name_"+i);
			String[] month_1_1 = request.getParameterValues("month_1_"+i);
			String[] month_2_1 = request.getParameterValues("month_2_"+i);
			String[] month_3_1 = request.getParameterValues("month_3_"+i);
			String[] month_4_1 = request.getParameterValues("month_4_"+i);
			String[] month_5_1 = request.getParameterValues("month_5_"+i);
			String[] month_6_1 = request.getParameterValues("month_6_"+i);
			
			if(month_1_1!=null){
				for(int j=0;j<month_1_1.length;j++){
					Dcyfys yfys = new Dcyfys();
					yfys.setLoan_type(i);
					yfys.setNames(items_name[j]);
					yfys.setFsrq(month_1_1[j]);
					yfys.setDqrq(month_2_1[j]);
					yfys.setJe(month_3_1[j]);
					yfys.setReason(month_4_1[j]);
					yfys.setLxfs(month_5_1[j]);
					yfys.setSyms(month_6_1[j]);
					yfys.setCustomer_id(customerId);
					yfys.setProduct_id(productId);
					commonDao.insertObject(yfys);
				}
			}
		}
	}
	
	public void insertYsyf(String customerId,HttpServletRequest request,String productId) {
		customerInforUpdateDao.deleteYsyf(customerId,productId);
		for(int i=0;i<1;i++){
			String[] items_name = request.getParameterValues("items_name_"+i);
			String[] month_1_1 = request.getParameterValues("month_1_"+i);
			String[] month_2_1 = request.getParameterValues("month_2_"+i);
			String[] month_3_1 = request.getParameterValues("month_3_"+i);
			String[] month_4_1 = request.getParameterValues("month_4_"+i);
			String[] month_5_1 = request.getParameterValues("month_5_"+i);
			String[] month_6_1 = request.getParameterValues("month_6_"+i);
			
			if(month_1_1!=null){
				for(int j=0;j<month_1_1.length;j++){
					Dcysyf ysyf = new Dcysyf();
					ysyf.setLoan_type(i);
					ysyf.setNames(items_name[j]);
					ysyf.setFsrq(month_1_1[j]);
					ysyf.setDqrq(month_2_1[j]);
					ysyf.setJe(month_3_1[j]);
					ysyf.setReason(month_4_1[j]);
					ysyf.setLxfs(month_5_1[j]);
					ysyf.setSyms(month_6_1[j]);
					ysyf.setCustomer_id(customerId);
					ysyf.setProduct_id(productId);
					commonDao.insertObject(ysyf);
				}
			}
		}
	}
	/**
	 * 金额转换
	 * 
	 * @param
	 * 
	 * @return String
	 */
    public String StoD(String val){
		
		if(val !=null && val!=""){
			Double monthDouble = Double.parseDouble(val) * 100;
			String monthValue = monthDouble.toString();
			return monthValue;
		}else{
			
		return val;
		
        }
		
		
	}
	/**
	 * 修改客户维护信息
	 * 
	 * @param
	 * 
	 * @return int
	 */
	public void insertCustomerInforUpdateCrossExamination(String customerId,String userId, String crossExamination) {
		customerInforUpdateDao.deleteCustomerInforUpdateCrossExamination(customerId);
		JSONArray crossExaminationData = JSONArray.fromObject(crossExamination);
		for (int i = 0; i < crossExaminationData.size(); i++) {
			CustomerInforUpdateCrossExamination crossExaminationPojo = (CustomerInforUpdateCrossExamination) JSONObject
					.toBean((JSONArray.fromObject(crossExaminationData.toString())
							.getJSONObject(i)), CustomerInforUpdateCrossExamination.class);
			
			if(isMul100(crossExaminationPojo)){
				String textNumbers = crossExaminationPojo.getContentsTextNumbers();
				if(StringUtils.isNotEmpty(textNumbers)){
					crossExaminationPojo.setContentsTextNumbers(Arith.mulReturnStr(textNumbers, "100"));
				}
			}
			crossExaminationPojo.setCustomerId(customerId);
			crossExaminationPojo.setCreatedBy(userId);
			crossExaminationPojo.setCreatedTime(Calendar.getInstance().getTime());
			crossExaminationPojo.setModifiedBy(userId);
			crossExaminationPojo.setModifiedTime(Calendar.getInstance().getTime());
			commonDao.insertObject(crossExaminationPojo);
		}
	}
	
	public void insertCustomerInforUpdateCrossExamination1(String customerId,String userId, String crossExamination,String productId) {
		customerInforUpdateDao.deleteCustomerInforUpdateCrossExamination1(customerId,productId);
		JSONArray crossExaminationData = JSONArray.fromObject(crossExamination);
		for (int i = 0; i < crossExaminationData.size(); i++) {
			CustomerInforUpdateCrossExamination crossExaminationPojo = (CustomerInforUpdateCrossExamination) JSONObject
					.toBean((JSONArray.fromObject(crossExaminationData.toString())
							.getJSONObject(i)), CustomerInforUpdateCrossExamination.class);
			
			if(isMul100(crossExaminationPojo)){
				String textNumbers = crossExaminationPojo.getContentsTextNumbers();
				if(StringUtils.isNotEmpty(textNumbers)){
					crossExaminationPojo.setContentsTextNumbers(Arith.mulReturnStr(textNumbers, "100"));
				}
			}
			crossExaminationPojo.setCustomerId(customerId);
			crossExaminationPojo.setCreatedBy(userId);
			crossExaminationPojo.setCreatedTime(Calendar.getInstance().getTime());
			crossExaminationPojo.setModifiedBy(userId);
			crossExaminationPojo.setModifiedTime(Calendar.getInstance().getTime());
			crossExaminationPojo.setProductId(productId);
			commonDao.insertObject(crossExaminationPojo);
		}
	}
	
	/**
	 * 修改陌拜信息
	 * @param customerinforUpdateWorship id
	 */
	public void updateCustomerinforUpdateWorship(CustomerinforUpdateWorship customerinforUpdateWorship,String id) {
		
		String customerId =  customerinforUpdateWorship.getCustomerId();
		Calendar calendar = Calendar.getInstance();
		CustomerinforUpdateWorship result =  getCustomerinforUpdateWorshipById(customerId);
		if(result != null){
			String indexId = result.getId();
			customerinforUpdateWorship.setId(indexId);
			customerinforUpdateWorship.setModifiedBy(id);
			customerinforUpdateWorship.setModifiedTime(calendar.getTime());
			commonDao.updateObject(customerinforUpdateWorship);
			
		}else{
			customerinforUpdateWorship.setCreatedBy(id);
			customerinforUpdateWorship.setCreatedTime(calendar.getTime());
			commonDao.insertObject(customerinforUpdateWorship);
		}
	}
}