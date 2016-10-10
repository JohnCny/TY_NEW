/**
 * 
 */
package com.cardpay.pccredit.riskControl.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.customer.constant.MarketingCreateWayEnum;
import com.cardpay.pccredit.customer.dao.CustomerOverdueDao;
import com.cardpay.pccredit.customer.filter.CustomerOverdueFilter;
import com.cardpay.pccredit.customer.service.CustomerMarketingService;
import com.cardpay.pccredit.product.model.ProductCollectionRules;
import com.cardpay.pccredit.product.service.ProductService;
import com.cardpay.pccredit.riskControl.constant.CollectionTypeEnum;
import com.cardpay.pccredit.riskControl.constant.RiskCustomerCollectionEndResultEnum;
import com.cardpay.pccredit.riskControl.dao.RiskCustomerCollectionDao;
import com.cardpay.pccredit.riskControl.filter.RiskCustomerCollectionPlanFilter;
import com.cardpay.pccredit.riskControl.model.RiskCustomerCollectionPlan;
import com.cardpay.pccredit.riskControl.model.RiskCustomerCollectionPlansAction;
import com.cardpay.pccredit.riskControl.web.CustomerOverdueForm;
import com.cardpay.pccredit.riskControl.web.RiskCustomerCollectionPlanForm;
import com.cardpay.pccredit.system.model.Dict;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.util.date.DateHelper;

/**
 * @author shaoming
 * 
 *         2014年11月6日 下午3:33:00
 */
@Service
public class RiskCustomerCollectionService {
	@Autowired
	private CommonDao commonDao;

	@Autowired
	private CustomerOverdueDao customerOverdueDao;
	@Autowired
	private CustomerOverdueService customerOverdueService;

	@Autowired
	private RiskCustomerCollectionDao riskCustomerCollectionDao;

	@Autowired
	private ProductService productService;

	@Autowired
	private CustomerMarketingService customerMarketingService;

	/**
	 * 得到逾期客户催收计划信息
	 * 
	 * @param filter
	 * @return
	 */
	public QueryResult<RiskCustomerCollectionPlanForm> findRiskCustomerCollectionPlansByFilter(RiskCustomerCollectionPlanFilter filter) {
		List<RiskCustomerCollectionPlanForm> riskCustomerCollectionPlanForm = riskCustomerCollectionDao.findRiskCustomerCollectionPlans(filter);
		int size = riskCustomerCollectionDao.findCountByFilter(filter);
		QueryResult<RiskCustomerCollectionPlanForm> qs = new QueryResult<RiskCustomerCollectionPlanForm>(size, riskCustomerCollectionPlanForm);
		return qs;
	}

	/**
	 * 得到逾期客户催收计划信息(下属)
	 * 
	 * @param filter
	 * @return
	 */
	public QueryResult<RiskCustomerCollectionPlanForm> findRiskViewSubCollectionPlansByFilter(RiskCustomerCollectionPlanFilter filter) {
		List<RiskCustomerCollectionPlanForm> riskCustomerCollectionPlanForm = riskCustomerCollectionDao.findRiskViewSubCollectionPlansByFilter(filter);
		int size = riskCustomerCollectionDao.findRiskViewSubCollectionPlansCountByFilter(filter);
		QueryResult<RiskCustomerCollectionPlanForm> qs = new QueryResult<RiskCustomerCollectionPlanForm>(size, riskCustomerCollectionPlanForm);
		return qs;
	}

	/**
	 * 通过id得到逾期客户催收计划
	 * 
	 * @param id
	 * @return
	 */
	public RiskCustomerCollectionPlanForm findRiskCustomerCollectionPlanById(String id) {
		return riskCustomerCollectionDao.findRiskCustomerCollectionPlanById(id);
	}

	/**
	 * 得到当前客户经理名下的逾期客户
	 * 
	 * @param userId
	 * @return
	 */
	public List<Dict> findCustomerIdAndName(String userId) {
		return riskCustomerCollectionDao.getCustomerIdAndName(userId);
	}

	/**
	 * 通过客户id得到名下的产品id和name
	 * 
	 * @param customerId
	 * @return
	 */
	public List<Dict> getProductIdAndName(String customerId) {
		return riskCustomerCollectionDao.getProductIdAndName(customerId);
	}

	/**
	 * 添加催收计划
	 * 
	 * @param riskCustomerCollectionPlan
	 * @return
	 */
	public String insertRiskCustomerCollectionPlan(RiskCustomerCollectionPlan riskCustomerCollectionPlan) {
		Date createdTime = new Date();
		String collectionTime = riskCustomerCollectionPlan.getCollectionTime();
		Date collectionEndtime = DateHelper.shiftDay(createdTime, Integer.parseInt(StringUtils.isEmpty(collectionTime) ? "0" : collectionTime));
		riskCustomerCollectionPlan.setCreatedTime(createdTime);
		riskCustomerCollectionPlan.setCollectionEndtime(collectionEndtime);
		commonDao.insertObject(riskCustomerCollectionPlan);
		return riskCustomerCollectionPlan.getId();
	}

	/**
	 * 通过催收计划id得到该计划下的所有实施信息
	 * 
	 * @param collectionPlanId
	 * @return
	 */
	public List<RiskCustomerCollectionPlansAction> findRiskCustomerCollectionPlansActionByCollectionPlanId(String collectionPlanId) {
		return riskCustomerCollectionDao.findRiskCustomerCollectionPlansActionByCollectionPlanId(collectionPlanId);
	}

	/**
	 * 添加催收计划实施信息
	 * 
	 * @param riskCustomerCollectionPlansAction
	 * @return
	 */
	public String insertRiskCustomerCollectionPlansAction(RiskCustomerCollectionPlansAction riskCustomerCollectionPlansAction) {
		riskCustomerCollectionPlansAction.setCreatedTime(new Date());
		commonDao.insertObject(riskCustomerCollectionPlansAction);
		return riskCustomerCollectionPlansAction.getId();
	}

	/**
	 * 通过id查找催收计划实施信息
	 * 
	 * @param id
	 * @return
	 */
	public RiskCustomerCollectionPlansAction findRiskCustomerCollectionPlansActionById(String id) {
		return commonDao.findObjectById(RiskCustomerCollectionPlansAction.class, id);
	}

	/**
	 * 主动更新催收计划实施信息
	 * 
	 * @param riskCustomerCollectionPlansAction
	 * @return
	 */
	public boolean updateRiskCustomerCollectionPlansAction(RiskCustomerCollectionPlansAction riskCustomerCollectionPlansAction) {
		riskCustomerCollectionPlansAction.setModifiedTime(new Date());
		int i = commonDao.updateObject(riskCustomerCollectionPlansAction);
		return i > 0 ? true : false;
	}

	/**
	 * 检查催收计划是否存在重复
	 * 
	 * @param customerId
	 * @param productId
	 * @param endResult
	 * @return
	 */
	public boolean checkCollectionPlan(String customerId, String productId, RiskCustomerCollectionEndResultEnum endResult1, RiskCustomerCollectionEndResultEnum endResult2) {
		int i = riskCustomerCollectionDao.checkCollectionPlan(customerId, productId, endResult1 == null ? "" : endResult1.toString(), endResult2 == null ? "" : endResult2.toString());
		return i > 0 ? true : false;
	}

	/**
	 * 主动更新催收计划
	 * 
	 * @param riskCustomerCollectionPlan
	 * @return
	 */
	public boolean updateRiskCustomerCollectionPlan(RiskCustomerCollectionPlan riskCustomerCollectionPlan) {
		Date createdTime = commonDao.findObjectById(RiskCustomerCollectionPlan.class, riskCustomerCollectionPlan.getId()).getCreatedTime();
		String collectionTime = riskCustomerCollectionPlan.getCollectionTime();
		Date collectionEndtime = DateHelper.shiftDay(createdTime, Integer.parseInt(collectionTime.equals("") ? "0" : collectionTime));
		riskCustomerCollectionPlan.setCollectionEndtime(collectionEndtime);
		riskCustomerCollectionPlan.setModifiedTime(new Date());
		int i = commonDao.updateObject(riskCustomerCollectionPlan);
		return i > 0 ? true : false;
	}

	/**
	 * 被动修改催收计划
	 * 
	 * @param riskCustomerCollectionPlan
	 * @return
	 */
	public boolean updateRiskCustomerCollectionPlanPassive(RiskCustomerCollectionPlan riskCustomerCollectionPlan) {
		riskCustomerCollectionPlan.setModifiedTime(new Date());
		int i = commonDao.updateObject(riskCustomerCollectionPlan);
		return i > 0 ? true : false;
	}

	/**
	 * 新增一条新的计划，计划内容与当前计划相同,最终结果为催收中
	 * 
	 * @param collectionPlanId
	 * @param endResultEnum
	 * @param createdBy
	 * @return
	 */
	public String copyRiskCustomerCollectionPlan(String collectionPlanId, RiskCustomerCollectionEndResultEnum endResultEnum, String createdBy) {
		RiskCustomerCollectionPlan riskCustomerCollectionPlan = commonDao.findObjectById(RiskCustomerCollectionPlan.class, collectionPlanId);
		Date createdTime = new Date();
		String collectionTime = riskCustomerCollectionPlan.getCollectionTime();
		Date collectionEndtime = DateHelper.shiftDay(createdTime, Integer.parseInt(collectionTime.equals("") ? "0" : collectionTime));
		riskCustomerCollectionPlan.setCollectionEndtime(collectionEndtime);
		riskCustomerCollectionPlan.setCreatedTime(createdTime);
		riskCustomerCollectionPlan.setCreatedBy(createdBy);
		riskCustomerCollectionPlan.setModifiedBy(null);
		riskCustomerCollectionPlan.setModifiedTime(null);
		riskCustomerCollectionPlan.setEndResult(endResultEnum.toString());
		commonDao.insertObject(riskCustomerCollectionPlan);
		return riskCustomerCollectionPlan.getId();
	}

	/*
	 * public boolean deleteRiskCustomerCollectionPlan(String id){ int i =
	 * commonDao.deleteObject(RiskCustomerCollectionPlan.class, id); return
	 * i>0?true:false; }
	 * 
	 * public boolean batchDeleteRiskCustomerCollectionPlan(List<String> ids){
	 * int i = commonDao.batchDeleteObjects(RiskCustomerCollectionPlan.class,
	 * ids); return i>0?true:false; }
	 */

	/**
	 * 系统筛选催收客户 根据产品的催收规则生成
	 */
	public void insertConectionSchedulesService() {
		// 先查出所有产品催收规则
		List<ProductCollectionRules> pcrList = productService.findAllProductCollectionRules();
		Map<String, List<ProductCollectionRules>> pcrMap = new HashMap<String, List<ProductCollectionRules>>();
		for(ProductCollectionRules pcr : pcrList){
			if(pcrMap.containsKey(pcr.getProductId())){ 
				List<ProductCollectionRules> tempPcrList = pcrMap.get(pcr.getProductId());
				tempPcrList.add(pcr);
				pcrMap.put(pcr.getProductId(), tempPcrList);
			} else {
				List<ProductCollectionRules> tempPcrList = new ArrayList<ProductCollectionRules>();
				tempPcrList.add(pcr);
				pcrMap.put(pcr.getProductId(), tempPcrList);
			}
		}
		// 查出产品下需要催收的客户
		CustomerOverdueFilter filter = new CustomerOverdueFilter();
		List<CustomerOverdueForm> cof = customerOverdueDao.findCustomerOverdue(filter);
		
		for (CustomerOverdueForm customerOverdueForm : cof) {
			List<ProductCollectionRules> tempPcrList = pcrMap.get(customerOverdueForm.getProductId());
			Boolean b = checkCollectionPlan(customerOverdueForm.getCustomerId(), customerOverdueForm.getProductId(), null, null);
			if(b){
				continue;
			}
			for(ProductCollectionRules tempPcr : tempPcrList){
				String numberDaysOverdueString = customerOverdueForm.getNumberDaysOverdue();
				String overdueDayStartString = tempPcr.getOverdueDayStart();
				String overdueDayEndString = tempPcr.getOverdueDayEnd();
				String agingString = customerOverdueForm.getAging();
				String agingRuleString = tempPcr.getAging();
				//账龄
				if(tempPcr.getCollectionType().equals(CollectionTypeEnum.age.toString())){
					if (StringUtils.isNotEmpty(agingString) && StringUtils.isNotEmpty(agingRuleString)) {
						// 账龄
						int aging = Integer.parseInt(agingString);
						int agingRule = Integer.parseInt(agingRuleString);
						if (aging == agingRule) {
							String collectionTime = null;
							String collectionWay = null;
							String currentBalanceString = customerOverdueForm.getCurrentBalance();
							String collectionRulesMoneyString = tempPcr.getMoney();
							if (StringUtils.isNotEmpty(currentBalanceString) && StringUtils.isNotEmpty(collectionRulesMoneyString)) {
								int currentBalance = Integer.parseInt(currentBalanceString);
								int collectionRulesMoney = Integer.parseInt(collectionRulesMoneyString);
								if (currentBalance > collectionRulesMoney && tempPcr.getOperation().equals(">")) {
									collectionTime = tempPcr.getCollectionTime();
									collectionWay = tempPcr.getCollectionWay();
									
									this.insertCollectionPlan(customerOverdueForm, collectionTime, collectionWay);
								}
								if (currentBalance <= collectionRulesMoney && tempPcr.getOperation().equals("<=")) {
									collectionTime = tempPcr.getCollectionTime();
									collectionWay = tempPcr.getCollectionWay();
									
									this.insertCollectionPlan(customerOverdueForm, collectionTime, collectionWay);
								}
							}
						}
					}
				} //天数
				else{
					if (StringUtils.isNotEmpty(numberDaysOverdueString) 
							&& StringUtils.isNotEmpty(overdueDayStartString) 
							&& StringUtils.isNotEmpty(overdueDayEndString)) {
						// 逾期天数
						int overDueDay = Integer.parseInt(numberDaysOverdueString);
						int overdueDayStart = Integer.parseInt(overdueDayStartString);
						int overdueDayEnd = Integer.parseInt(overdueDayEndString);

						if (overDueDay > overdueDayStart && overDueDay <= overdueDayEnd) {
							String collectionTime = null;
							String collectionWay = null;
							String currentBalanceString = customerOverdueForm.getCurrentBalance();
							String collectionRulesMoneyString = tempPcr.getMoney();
							if (StringUtils.isNotEmpty(currentBalanceString) && StringUtils.isNotEmpty(collectionRulesMoneyString)) {
								int currentBalance = Integer.parseInt(currentBalanceString);
								int collectionRulesMoney = Integer.parseInt(collectionRulesMoneyString);
								if (currentBalance > collectionRulesMoney && tempPcr.getOperation().equals(">")) {
									collectionTime = tempPcr.getCollectionTime();
									collectionWay = tempPcr.getCollectionWay();
									this.insertCollectionPlan(customerOverdueForm, collectionTime, collectionWay);
								}
								if (currentBalance <= collectionRulesMoney && tempPcr.getOperation().equals("<=")) {
									collectionTime = tempPcr.getCollectionTime();
									collectionWay = tempPcr.getCollectionWay();
									this.insertCollectionPlan(customerOverdueForm, collectionTime, collectionWay);
								}
								
							}
						}
					}
				}
			}
		}
	}

	private void insertCollectionPlan(CustomerOverdueForm customerOverdueForm, String collectionTime, String collectionWay) {
		RiskCustomerCollectionPlan riskCustomerCollectionPlan = new RiskCustomerCollectionPlan();
		riskCustomerCollectionPlan.setCollectionAmount("");
		riskCustomerCollectionPlan.setCollectionMethod(collectionWay);
		riskCustomerCollectionPlan.setCollectionTime(collectionTime);
		riskCustomerCollectionPlan.setProductId(customerOverdueForm.getProductId());
		riskCustomerCollectionPlan.setCreateWay(MarketingCreateWayEnum.system.name());
		riskCustomerCollectionPlan.setCustomerId(customerOverdueForm.getCustomerId());
		riskCustomerCollectionPlan.setCustomerManagerId(customerOverdueForm.getUserId());
		insertRiskCustomerCollectionPlan(riskCustomerCollectionPlan);
	}

	/**
	 * 统计客户催收计划条数
	 * 
	 * @param customerManagerId
	 * @param result
	 * @return
	 */
	public int findCollectionCountToday(String customerManagerId, String result) {
		return riskCustomerCollectionDao.findCollectionCountToday(customerManagerId, result, null, null);
	}

	public int findCollectionCountByDay(String customerManagerId, String result, int day) {
		if (day != 0) {
			Date start = customerMarketingService.getStartTime(day);
			Date end = customerMarketingService.getEndTime(day);
			return riskCustomerCollectionDao.findCollectionCountToday(customerManagerId, result, DateHelper.getDateFormat(start, "yyyy-MM-dd HH:mm:ss"), DateHelper.getDateFormat(end, "yyyy-MM-dd HH:mm:ss"));
		} else {
			return findCollectionCountToday(customerManagerId, result);
		}
	}

	/**
	 * 统计七日预期还款条数
	 * 
	 * @param customerManagerId
	 * @param result
	 * @param day
	 * @return
	 */
	public int findCollectionPromiseCountByDay(String customerManagerId, String result, int day) {
		Date start = customerMarketingService.getStartTime(day);
		Date end = customerMarketingService.getStartTime(day + 7);
		return riskCustomerCollectionDao.findCollectionCountToday(customerManagerId, result, DateHelper.getDateFormat(start, "yyyy-MM-dd HH:mm:ss"), DateHelper.getDateFormat(end, "yyyy-MM-dd HH:mm:ss"));

	}
}
