package com.cardpay.pccredit.customer.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.axis.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.customer.constant.QuotaAdjustTypeEnum;
import com.cardpay.pccredit.customer.dao.comdao.CustomerQuotaComdao;
import com.cardpay.pccredit.customer.model.AmountAdjustment;
import com.cardpay.pccredit.customer.model.AverageDailyOverdraft;
import com.cardpay.pccredit.customer.service.AmountAdjustmentService;
import com.cardpay.pccredit.customer.service.CustomerQuotaService;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.jrad.modules.dictionary.DictionaryManager;
import com.wicresoft.jrad.modules.dictionary.model.Dictionary;
import com.wicresoft.jrad.modules.privilege.model.User;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;

/**
 * 
 * 描述 ：客户调额controller
 * @author 张石树
 *
 * 2014-12-1 下午2:01:02
 */
@Controller
@RequestMapping("/customer/adjustmentquota/*")
@JRadModule("customer.adjustmentquota")
public class CustomerAdjustmentQuotaController extends BaseController{

	/*
	 * 存放 查询时刻时间 查询的内容
	 */
	private static ConcurrentHashMap<String, Map<String, Object>> custAdjustMap = new ConcurrentHashMap<String, Map<String, Object>>();
	
	@Autowired
	private CustomerQuotaService customerQuotaService;
	
	@Autowired
	private CustomerQuotaComdao customerQuotaComdao;
	
	@Autowired
	private AmountAdjustmentService amountAdjustmentService;
	
	private final static Long BET_SEARCH_TIME = 1000L;
	
	/**
	 * 浏览页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "browse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browse(@ModelAttribute BaseQueryFilter filter, HttpServletRequest request) {
		filter.setRequest(request);
		
		String adjustType = request.getParameter("adjustType");
		String productId = request.getParameter("productId");
		if(StringUtils.isEmpty(adjustType)){
			adjustType = QuotaAdjustTypeEnum.up.name();
		} 
		if(StringUtils.isEmpty(productId)){
			DictionaryManager dictMgr = Beans.get(DictionaryManager.class);
			Dictionary dictionary = dictMgr.getDictionaryByName("ProductName");
			if(dictionary != null && dictionary.getItems() != null && dictionary.getItems().size() > 0){
				productId = dictionary.getItems().get(0).getName();
			}
		}
		
		QueryResult<CustomerAdjustmentQuotaForm> result = new QueryResult<CustomerAdjustmentQuotaForm>(0, new ArrayList<CustomerAdjustmentQuotaForm>());
		if(QuotaAdjustTypeEnum.up.name().equals(adjustType)){
			List<String> upCustIds = this.getUpQuotaList(productId);
			List<CustomerAdjustmentQuotaForm> items = new ArrayList<CustomerAdjustmentQuotaForm>();
			if(upCustIds != null && upCustIds.size() > 0){
				int start = filter.getStart();
				int end = filter.getStart() + filter.getLimit();
				if(end > upCustIds.size()){
					end = upCustIds.size();
				}
				List<String> itemCustIds = upCustIds.subList(start, end);
				items = customerQuotaComdao.findQutoaAdjustInfo(productId, itemCustIds);
				for(CustomerAdjustmentQuotaForm item : items){
					item.setAdjustmentType(QuotaAdjustTypeEnum.up.name());
				}
			}
			result = new QueryResult<CustomerAdjustmentQuotaForm>(upCustIds.size(), items);
		}
		if (QuotaAdjustTypeEnum.down.name().equals(adjustType)){
			List<String> downCustIds = this.getDownQuotaList(productId);
			List<CustomerAdjustmentQuotaForm> items = new ArrayList<CustomerAdjustmentQuotaForm>();
			if(downCustIds != null && downCustIds.size() > 0){
				int start = filter.getStart();
				int end = filter.getStart() + filter.getLimit();
				if(end > downCustIds.size()){
					end = downCustIds.size();
				}
				List<String> itemCustIds = downCustIds.subList(start, end);
				items = customerQuotaComdao.findQutoaAdjustInfo(productId, itemCustIds);
				for(CustomerAdjustmentQuotaForm item : items){
					item.setAdjustmentType(QuotaAdjustTypeEnum.down.name());
				}
			}
			result = new QueryResult<CustomerAdjustmentQuotaForm>(downCustIds.size(), items);
		}
		JRadPagedQueryResult<CustomerAdjustmentQuotaForm> pagedResult = new JRadPagedQueryResult<CustomerAdjustmentQuotaForm>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/customer/adjustmentquota/customer_adjustmentquota_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);
		mv.addObject("adjustType", adjustType);
		mv.addObject("productId", productId);
		return mv;
	}

	/**
	 * 降额
	 * @param productId
	 * @return
	 */
	private List<String> getDownQuotaList(String productId) {
		Map<String, Object> map = custAdjustMap.get(QuotaAdjustTypeEnum.down.name());
		if(map == null){
			map = new HashMap<String, Object>();
		}
		Long lastTime = map.get("lastTime") != null ? (Long)map.get("lastTime") : 0L;
		if((System.currentTimeMillis() - lastTime) > BET_SEARCH_TIME){
			Set<String> downCustIds = customerQuotaService.downQuotaCustomer(productId);
			List<String> customerIds = new ArrayList<String>();
			for(String cId : downCustIds){
				if(!customerIds.contains(cId)){
					customerIds.add(cId);
				}
			}
			if(customerIds.size() > 0){
				List<AmountAdjustment> approvalNoAmountAdjustList = customerQuotaComdao.findApprovalNoList(productId, customerIds);
				for(AmountAdjustment adjustment : approvalNoAmountAdjustList){
					if(customerIds.contains(adjustment.getCustomerId())){
						customerIds.remove(adjustment.getCustomerId());
					}
				}
			}
			synchronized (this) {
				map.put("lastTime", System.currentTimeMillis());
				map.put(QuotaAdjustTypeEnum.down.name(), customerIds);
				custAdjustMap.put(QuotaAdjustTypeEnum.down.name(), map);
			}
			return customerIds;
		} else {
			Map<String, Object> tempMap = custAdjustMap.get(QuotaAdjustTypeEnum.down.name());
			List<String> upList = (List<String>) tempMap.get(QuotaAdjustTypeEnum.down.name());
			return upList;
		}
	}

	/**
	 * 升额
	 * @param productId
	 * @return
	 */
	private List<String> getUpQuotaList(String productId) {
		Map<String, Object> map = custAdjustMap.get(QuotaAdjustTypeEnum.up.name());
		if(map == null){
			map = new HashMap<String, Object>();
		}
		Long lastTime = map.get("lastTime") != null ? (Long)map.get("lastTime") : 0L;
		if((System.currentTimeMillis() - lastTime) > BET_SEARCH_TIME){
			List<AverageDailyOverdraft> dailyOverdrafts = customerQuotaService.upQuotaCustomer(productId);
			List<String> customerIds = new ArrayList<String>();
			for(AverageDailyOverdraft overdraft : dailyOverdrafts){
				if(!customerIds.contains(overdraft.getCustomerId())){
					customerIds.add(overdraft.getCustomerId());
				}
			}
			if(customerIds.size() > 0){
				List<AmountAdjustment> approvalNoAmountAdjustList = customerQuotaComdao.findApprovalNoList(productId, customerIds);
				for(AmountAdjustment adjustment : approvalNoAmountAdjustList){
					if(customerIds.contains(adjustment.getCustomerId())){
						customerIds.remove(adjustment.getCustomerId());
					}
				}
			}
			synchronized (this) {
				map.put("lastTime", System.currentTimeMillis());
				map.put(QuotaAdjustTypeEnum.up.name(), customerIds);
				custAdjustMap.put(QuotaAdjustTypeEnum.up.name(), map);
			}
			return customerIds;
		} else {
			Map<String, Object> tempMap = custAdjustMap.get(QuotaAdjustTypeEnum.up.name());
			List<String> upList = (List<String>) tempMap.get(QuotaAdjustTypeEnum.up.name());
			return upList;
		}
	}
	
	/**
	 * 调整额度生成调额信息
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "adjustmentAccount.json")
	@JRadOperation(JRadOperation.AMOUNTADJUSTMENT)
	public JRadReturnMap adjustmentAccount(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			String appId = request.getParameter("appId");
			String adjustmentAccount = request.getParameter("adjustmentAccount");
			String adjustmentType = request.getParameter("adjustmentType");
			User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
			adjustmentAccount = (Double.parseDouble(adjustmentAccount) * 100) + "";
			amountAdjustmentService.insertAmountAdjustment(appId, adjustmentAccount, adjustmentType, user);
			
			returnMap.put(RECORD_ID, appId);
			returnMap.addGlobalMessage(CHANGE_SUCCESS);
		}
		catch (Exception e) {
			return WebRequestHelper.processException(e);
		}
		return returnMap;
	}

}
