package com.cardpay.pccredit.customer.service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.customer.constant.CustomerMarketingEndResultEnum;
import com.cardpay.pccredit.customer.constant.MarketingPlanTypeEnum;
import com.cardpay.pccredit.customer.dao.CustomerMarketingDao;
import com.cardpay.pccredit.customer.dao.comdao.CustomerMarketingCommDao;
import com.cardpay.pccredit.customer.filter.CustomerMarketingFilter;
import com.cardpay.pccredit.customer.model.CustomerMarketing;
import com.cardpay.pccredit.customer.model.CustomerMarketingPlan;
import com.cardpay.pccredit.customer.model.CustomerMarketingWeb;
import com.cardpay.pccredit.customer.model.MarketingPlanWeb;
import com.cardpay.pccredit.product.model.ProductMarketingRules;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.id.IDGenerator;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.util.date.DateHelper;

/**
 * 
 * @author 姚绍明
 *
 */
@Service
public class CustomerMarketingService {
	
	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private CustomerMarketingDao customerMarketingDao;
	
	@Autowired
	private CustomerMarketingCommDao customerMarketingCommDao;
	
	
	
	/**
	 * 添加营销计划
	 * @param 
	 * @return
	 */

	public int insertMarketingPlans(String customerId,String productId,MarketingPlanTypeEnum  marketingPlanTypeEnum,String createUser) {
		ProductMarketingRules productMarketingRules=customerMarketingDao.findProductMarketingRulesByProductId(productId);
		
		CustomerMarketing customerMarketing=new CustomerMarketing();
		customerMarketing.setCreateWay(marketingPlanTypeEnum.toString());
		customerMarketing.setCustomerId(customerId);
		customerMarketing.setProductId(productId);
		customerMarketing.setMarketingTime(productMarketingRules.getMarketingTime());
		customerMarketing.setMarketingMethod(productMarketingRules.getMarketingMethod());
		customerMarketing.setCreatedTime(new Date());
		customerMarketing.setCreatedBy(createUser);
		return commonDao.insertObject(customerMarketing);
	}
	public String insertCustomerMarketing(CustomerMarketing customerMarketing){
		Date createdTime = new Date();
		customerMarketing.setCreatedTime(createdTime);
		String marketingTime = customerMarketing.getMarketingTime();
		customerMarketing.setMarketingEndtime(DateHelper.shiftDay(createdTime, Integer.parseInt(marketingTime.equals("")?"0":marketingTime)));
		commonDao.insertObject(customerMarketing);
		return customerMarketing.getId();
	}
	/**
	 * 过滤查询
	 * @param filter
	 * @return
	 */

	public QueryResult<MarketingPlanWeb> findMarketingPlansByFilter(CustomerMarketingFilter filter) {
		return customerMarketingCommDao.findMarketingPlansByFilter(filter);
	}
	/**
	 * 通过id获得CustomerMarketingWeb
	 * @param marketingPlanId
	 * @return
	 */
	public CustomerMarketingWeb findCustomerMarketingById(String id) {
		return customerMarketingDao.findCustomerMarketingById(id);
	}
	/**
	 * 通过营销计划id得到客户经理id
	 * @param id
	 * @return
	 */
	public String findCustomerManagerIdByMarketingId(String id){
		return customerMarketingDao.findCustomerManagerIdByMarketingId(id);
	}
	/**
	 * 修改营销计划
	 * @param customerMarketing
	 * @return
	 */
	public String updateCustomerMarketing(CustomerMarketing customerMarketing){
		String id = customerMarketing.getId();
		CustomerMarketing customer = commonDao.findObjectById(CustomerMarketing.class, id);
		Date createdTime = customer.getCreatedTime();
		String marketingTime = customerMarketing.getMarketingTime()==null?"":customerMarketing.getMarketingTime();
		Date marketingEndtime = DateHelper.shiftDay(createdTime, Integer.parseInt(marketingTime.equals("")?"0":marketingTime));
		customerMarketing.setModifiedTime(new Date());
		customerMarketing.setMarketingEndtime(marketingEndtime);
		commonDao.updateObject(customerMarketing);
		return customerMarketing.getId();
	}
	/**
	 * 通过id得到CustomerMarketing
	 * @param id
	 * @return
	 */
	public CustomerMarketing findMarketingPlanById(String id){
		return commonDao.findObjectById(CustomerMarketing.class, id);
	}
	/**
	 * 得到下属客户经理名下客户的营销计划信息
	 * @param ids
	 * @return
	 */
	public QueryResult<MarketingPlanWeb> findMarketingPlansByUserIds(CustomerMarketingFilter filter){
		List<MarketingPlanWeb> plans = customerMarketingDao.findSubMarketingPlan(filter);
		int size = customerMarketingDao.findSubMarketingPlanCountsByIds(filter.getIds());
		QueryResult<MarketingPlanWeb> qr = new QueryResult<MarketingPlanWeb>(size,plans);
		return qr;
	}
	/**
	 * 复制一条新的营销计划
	 * @param customerMarketing
	 * @return
	 */
	public String copyCustomerMarketing(String createdBy,CustomerMarketingEndResultEnum customerMarketingEndResultEnum,String marketingPlanId){
		CustomerMarketing customerMarketing = findMarketingPlanById(marketingPlanId);
		String newId = IDGenerator.generateID();
		Date createdTime = new Date();
		Date marketingEndTime = DateHelper.shiftDay(createdTime, Integer.parseInt(customerMarketing.getMarketingTime().equals("")?"0":customerMarketing.getMarketingTime()));
		customerMarketing.setId(newId);
		customerMarketing.setCreatedTime(createdTime);
		customerMarketing.setCreatedBy(createdBy);
		customerMarketing.setModifiedBy(null);
		customerMarketing.setModifiedTime(null);
		customerMarketing.setMarketingEndtime(marketingEndTime);
		customerMarketing.setEndResult(customerMarketingEndResultEnum.toString());
		commonDao.insertObject(customerMarketing);
		return customerMarketing.getId();
	}
	/**
	 * 通过marketingPlanId获得营销实施记录
	 * @param marketingPlanId
	 * @return
	 */
	public List<CustomerMarketingPlan> findCustomerMarketingPlans(String marketingPlanId){
		return customerMarketingDao.findCustomerMarketingPlansByMarketingPlanId(marketingPlanId);
	}
	/**
	 * 插入营销实施信息
	 * @param customerMarketingPlan
	 * @return
	 */
	public String insertCustomerMarketingPlan(CustomerMarketingPlan customerMarketingPlan){
		customerMarketingPlan.setCreatedTime(new Date());
		
		commonDao.insertObject(customerMarketingPlan);
		return customerMarketingPlan.getId();
	}
	/**
	 * 通过id获得营销实施记录
	 * @param id
	 * @return
	 */
	public CustomerMarketingPlan findCustomerMarketingPlanById(String id){
		return commonDao.findObjectById(CustomerMarketingPlan.class, id);
	}
	/**
	 * 修改营销实施信息
	 * @param customerMarketingPlan
	 * @return
	 */
	public int updateCustomerMarketingPlan(CustomerMarketingPlan customerMarketingPlan){
		customerMarketingPlan.setModifiedTime(new Date());
		return commonDao.updateObject(customerMarketingPlan);
	}
	/**
	 * 统计今日营销计划数量
	 * @return
	 */
	public int findMarketingCountToday(String userId){
		return customerMarketingDao.findMarketingCountToday(userId);
	}
	/**
	 * 统计营销计划数量
	 * @param day
	 * @return
	 */
	public int findMarketingCountByDay(String userId,int day){
		if(day==0){
			return findMarketingCountToday(userId);
		}else{
			Date start = getStartTime(day);
			Date end = getEndTime(day);
			return customerMarketingDao.findMarketingCountByDay(userId,DateHelper.getDateFormat(start, "yyyy-MM-dd HH:mm:ss"), DateHelper.getDateFormat(end, "yyyy-MM-dd HH:mm:ss"));
		}
	}
	/**
	 * 得到指定日当天零点时间
	 * @param day
	 * @return
	 */
	public  Date getStartTime(int day){
		Calendar currentDate = new GregorianCalendar(); 
		currentDate.set(Calendar.HOUR_OF_DAY, 0);
		currentDate.set(Calendar.MINUTE, 0);
		currentDate.set(Calendar.SECOND, 0);
		currentDate.set(Calendar.MILLISECOND, 0);
		Date todayStart = currentDate.getTime();
		todayStart = DateHelper.shiftDay(todayStart, day);
		return todayStart;
   }
	/**
	 * 得到指定日当天终点时间
	 * @param day
	 * @return
	 */
   public  Date getEndTime(int day){
        Calendar currentDate = new GregorianCalendar(); 
		currentDate.set(Calendar.HOUR_OF_DAY, 23);
		currentDate.set(Calendar.MINUTE, 59);
		currentDate.set(Calendar.SECOND, 59);
		currentDate.set(Calendar.MILLISECOND, 999);
		Date todayEnd = currentDate.getTime();
		todayEnd = DateHelper.shiftDay(todayEnd, day);
		return todayEnd;
	}
}
