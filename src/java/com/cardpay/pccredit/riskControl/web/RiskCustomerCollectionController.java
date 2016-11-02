/**
 * 
 */
package com.cardpay.pccredit.riskControl.web;

import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.common.Arith;
import com.cardpay.pccredit.customer.constant.CustomerInforConstant;
import com.cardpay.pccredit.customer.constant.MarketingCreateWayEnum;
import com.cardpay.pccredit.customer.model.CustomerOverdue;
import com.cardpay.pccredit.datapri.constant.DataPriConstants;
import com.cardpay.pccredit.manager.service.ManagerBelongMapService;
import com.cardpay.pccredit.manager.web.AccountManagerParameterForm;
import com.cardpay.pccredit.riskControl.constant.RiskCustomerCollectionConstant;
import com.cardpay.pccredit.riskControl.constant.RiskCustomerCollectionEndResultEnum;
import com.cardpay.pccredit.riskControl.constant.RiskCustomerCollectionEnum;
import com.cardpay.pccredit.riskControl.filter.RiskCustomerCollectionPlanFilter;
import com.cardpay.pccredit.riskControl.model.RiskCustomerCollectionPlan;
import com.cardpay.pccredit.riskControl.model.RiskCustomerCollectionPlansAction;
import com.cardpay.pccredit.riskControl.service.CustomerOverdueService;
import com.cardpay.pccredit.riskControl.service.RiskCustomerCollectionService;
import com.cardpay.pccredit.system.model.Dict;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.constant.JRadConstants;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.util.date.DateHelper;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;
import com.wicresoft.util.web.RequestHelper;

/**
 * 催收计划信息
 * @author shaoming
 *
 * 2014年11月6日   下午3:17:04
 */
@Controller
@RequestMapping("/riskcontrol/riskcustomercollection/*")
@JRadModule("riskcontrol.riskcustomercollection")
public class RiskCustomerCollectionController extends BaseController{
	
	@Autowired
	private RiskCustomerCollectionService riskCustomerCollectionService;
	
	@Autowired
	private ManagerBelongMapService managerBelongMapService;
	
	@Autowired
	private CustomerOverdueService customerOverdueService;
	/**
	 * 浏览催收计划信息页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "browse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browse(@ModelAttribute RiskCustomerCollectionPlanFilter filter, HttpServletRequest request) {
		filter.setRequest(request);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String userId = user.getId(); 
		filter.setCustomerManagerId(userId);
		QueryResult<RiskCustomerCollectionPlanForm> result = riskCustomerCollectionService.findRiskCustomerCollectionPlansByFilter(filter);
		JRadPagedQueryResult<RiskCustomerCollectionPlanForm> pagedResult = new JRadPagedQueryResult<RiskCustomerCollectionPlanForm>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/riskcontrol/riskCustomerCollection/collection_plan_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);
		
		return mv;
	}
	
	/**
	 * 增加催收计划信息页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "create.page")
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView create(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/riskcontrol/riskCustomerCollection/collection_plan_create", request);
		return mv;
	}
	
	/**
	 * 修改催收计划信息页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "change.page")
	@JRadOperation(JRadOperation.CHANGE)
	public AbstractModelAndView change(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/riskcontrol/riskCustomerCollection/collection_plan_change", request);

		String collectionPlanId = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(collectionPlanId)) {
			RiskCustomerCollectionPlanForm collectionplan = riskCustomerCollectionService.findRiskCustomerCollectionPlanById(collectionPlanId);
			mv.addObject("collectionplan", collectionplan);
		}
		return mv;
	}
	
	/**
	 * 显示催收计划信息页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "display.page")
	@JRadOperation(JRadOperation.DISPLAY)
	public AbstractModelAndView display(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/riskcontrol/riskCustomerCollection/collection_plan_display", request);
		String collectionPlanId = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(collectionPlanId)) {
			RiskCustomerCollectionPlanForm collectionplan = riskCustomerCollectionService.findRiskCustomerCollectionPlanById(collectionPlanId);
			List<RiskCustomerCollectionPlansAction> collectionActions = riskCustomerCollectionService.findRiskCustomerCollectionPlansActionByCollectionPlanId(collectionPlanId);
			mv.addObject("collectionPlan", collectionplan);
			mv.addObject("collectionActions",collectionActions);
		}
		return mv;
	}
	/**
	 * 添加催收计划
	 * @param form
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insert.json")
	@JRadOperation(JRadOperation.CREATE)
	public JRadReturnMap insert(@ModelAttribute RiskCustomerCollectionPlanForm form, HttpServletRequest request) {
		boolean flag = riskCustomerCollectionService.checkCollectionPlan(form.getCustomerId(),form.getProductId(),RiskCustomerCollectionEndResultEnum.collection,RiskCustomerCollectionEndResultEnum.repaymentcommitments);
		JRadReturnMap returnMap = new JRadReturnMap();//WebRequestHelper.requestValidation(getModuleName(), form);
		if(!flag){
			if (returnMap.isSuccess()) {
				try {
					RiskCustomerCollectionPlan riskCustomerCollectionPlan = form.createModel(RiskCustomerCollectionPlan.class);
					IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
					String createdBy = user.getId();
					String customerManagerId = riskCustomerCollectionPlan.getCustomerManagerId();
					if(createdBy!=null && createdBy.equals(customerManagerId)){
						riskCustomerCollectionPlan.setCreateWay(MarketingCreateWayEnum.myself.toString());
					}else{
						riskCustomerCollectionPlan.setCreateWay(MarketingCreateWayEnum.manager.toString());
					}
					riskCustomerCollectionPlan.setCreatedBy(createdBy);
					String id = riskCustomerCollectionService.insertRiskCustomerCollectionPlan(riskCustomerCollectionPlan);
					returnMap.put(RECORD_ID, id);
					returnMap.addGlobalMessage(CREATE_SUCCESS);
				}catch (Exception e) {
					returnMap.put(JRadConstants.MESSAGE, DataPriConstants.SYS_EXCEPTION_MSG);
					returnMap.put(JRadConstants.SUCCESS, false);
					return WebRequestHelper.processException(e);
				}
			}
		}else{
			returnMap.setSuccess(false);
			returnMap.addGlobalError(RiskCustomerCollectionConstant.alreadyExists);
		}
		return returnMap;
	}
	/**
	 * 修改催收计划
	 * @param form
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "update.json")
	@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap update(@ModelAttribute RiskCustomerCollectionPlanForm form, HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();//WebRequestHelper.requestValidation(getModuleName(), form);
		if (returnMap.isSuccess()) {
			try {
				String createWay = riskCustomerCollectionService.findRiskCustomerCollectionPlanById(form.getId()).getCreateWay();
				if(createWay!=null && !createWay.equals(RiskCustomerCollectionEnum.myself.toString())){
					returnMap.setSuccess(false);
					returnMap.addGlobalError(CustomerInforConstant.UPDATEERROR);
					return returnMap;
				}
				IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
				String modifiedBy = user.getId(); 
				
				RiskCustomerCollectionPlan riskCustomerCollectionPlan = form.createModel(RiskCustomerCollectionPlan.class);
				riskCustomerCollectionPlan.setModifiedBy(modifiedBy);
				boolean flag=riskCustomerCollectionService.updateRiskCustomerCollectionPlan(riskCustomerCollectionPlan);
				if(flag){
					returnMap.put(RECORD_ID, riskCustomerCollectionPlan.getId());
					returnMap.addGlobalMessage(CHANGE_SUCCESS);
				}else{
					returnMap.setSuccess(false);
					returnMap.addGlobalError(CustomerInforConstant.UPDATEERROR);
				}
			}catch (Exception e) {
				returnMap.put(JRadConstants.MESSAGE, DataPriConstants.SYS_EXCEPTION_MSG);
				returnMap.put(JRadConstants.SUCCESS, false);
				return WebRequestHelper.processException(e);
			}
		}
		return returnMap;
	}
	/**
	 * 增加催收实施计划信息页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "createAction.page")
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView createAction(HttpServletRequest request) {
		String collectionPlanId = RequestHelper.getStringValue(request, ID);
		JRadModelAndView mv = new JRadModelAndView("/riskcontrol/riskCustomerCollection/collection_plan_action_create", request);
		mv.addObject("collectionPlanId",collectionPlanId);
		return mv;
	}
	/**
	 * 添加催收计划实施信息
	 * @param form
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertAction.json")
	@JRadOperation(JRadOperation.CREATE)
	public JRadReturnMap insertAction(@ModelAttribute RiskCustomerCollectionPlansActionForm form, HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();//WebRequestHelper.requestValidation(getModuleName(), form);
		if (returnMap.isSuccess()) {
			try {
				IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
				RiskCustomerCollectionPlan riskCustomerCollectionPlan = new RiskCustomerCollectionPlan();
				String endResult= form.getEndResult();
				if(endResult!=null && endResult.equals(RiskCustomerCollectionEndResultEnum.repaymentcommitments.toString())){
					String collectionPromiseDate = form.getCollectionPromiseDate();
					String collectionAmount = form.getCollectionAmount();
					Date date = DateHelper.getDateFormat(collectionPromiseDate, "yyyy-MM-dd HH:mm:ss");
					
					riskCustomerCollectionPlan.setCollectionPromiseDate(date);
					riskCustomerCollectionPlan.setCollectionAmount(collectionAmount);
				}
				
				String collectionPlanId = form.getCollectionPlanId();
				/**
				 * 传入最终结果为完成时,查询当前最终结果是否为承诺还款
				 */
				if(endResult!=null && endResult.equals(RiskCustomerCollectionEndResultEnum.complete.toString())){
					if(StringUtils.isNotEmpty(collectionPlanId)){
						RiskCustomerCollectionPlanForm riskForm = riskCustomerCollectionService.findRiskCustomerCollectionPlanById(collectionPlanId);
						if(riskForm!=null){
							/*得到承诺还款金额*/
							String money = riskForm.getCollectionAmount();
							/*存在还款金额*/
							if(StringUtils.isNotEmpty(money)){
								/*更新相对应的逾期客户信息中的还款总额*/
								/*得到逾期客户信息*/
								String customerId = riskForm.getCustomerId();
								String productId = riskForm.getProductId();
								CustomerOverdue customerOverdue = customerOverdueService.findCustomerOverdueCountByCustomerIdAndProductId(customerId, productId);
								if(customerOverdue!=null){
									String promiseAmount = customerOverdue.getOverduePaybackAll();
									double account = Arith.add(promiseAmount,money);
									customerOverdue.setOverduePaybackAll(String.valueOf(account));
									customerOverdue.setModifiedBy(user!=null?user.getId():"");
								}
								/*执行逾期客户更新操作*/
								customerOverdueService.updateCustomerOverdue(customerOverdue);
							}
						}
					}
				}
				riskCustomerCollectionPlan.setId(collectionPlanId);
				riskCustomerCollectionPlan.setEndResult(endResult);
				/*修改催收计划最终结果*/
				boolean flag = riskCustomerCollectionService.updateRiskCustomerCollectionPlanPassive(riskCustomerCollectionPlan);
				if(flag){
					/*在当前计划下添加一条新的实施信息记录*/
					RiskCustomerCollectionPlansAction riskCustomerCollectionPlansAction = new RiskCustomerCollectionPlansAction();
					String createdBy = user.getId();
					String collectionStartTime = form.getCollectionStartTime();
					String collectionEndTime = form.getCollectionEndTime();
					if(collectionStartTime!=null && !collectionStartTime.equals("")){
						Date start = DateHelper.getDateFormat(collectionStartTime, "yyyy-MM-dd HH:mm:ss");
						riskCustomerCollectionPlansAction.setCollectionStartTime(start);
					}
					if(collectionEndTime!=null && !collectionEndTime.equals("")){
					    Date end = DateHelper.getDateFormat(collectionEndTime, "yyyy-MM-dd HH:mm:ss");
					    riskCustomerCollectionPlansAction.setCollectionEndTime(end);
					}
					riskCustomerCollectionPlansAction.setCreatedBy(createdBy);
					riskCustomerCollectionPlansAction.setCollectionPlanId(form.getCollectionPlanId());
					riskCustomerCollectionPlansAction.setCollection(form.getCollection());
					riskCustomerCollectionPlansAction.setCollectionResult(form.getCollectionResult());
					riskCustomerCollectionPlansAction.setWhetherImplement(form.getWhetherImplement());
					String id = riskCustomerCollectionService.insertRiskCustomerCollectionPlansAction(riskCustomerCollectionPlansAction);
					/*最终结果为继续催收*/
					String riskCollectionPlanId = "";
					if(endResult.equals(RiskCustomerCollectionEndResultEnum.continuecollection.toString())){
						/*新增一条新的计划，计划内容与当前计划相同,最终结果为催收中*/
						riskCollectionPlanId = riskCustomerCollectionService.copyRiskCustomerCollectionPlan(collectionPlanId, RiskCustomerCollectionEndResultEnum.collection, createdBy); ;
						/*返回最初催收计划页面*/
						returnMap.put(RECORD_ID,riskCollectionPlanId);
						/*最终结果为承诺还款*/
					}else if(endResult.equals(RiskCustomerCollectionEndResultEnum.repaymentcommitments.toString())){
						/*新增一条新的计划，计划内容与当前计划相同,最终结果为承诺还款*/
						riskCollectionPlanId = riskCustomerCollectionService.copyRiskCustomerCollectionPlan(collectionPlanId, RiskCustomerCollectionEndResultEnum.repaymentcommitments, createdBy); ;
						/*返回最初催收计划页面*/
						returnMap.put(RECORD_ID,riskCollectionPlanId);
					}else{
						/*返回催收计划详细信息页面*/
						returnMap.put(ID, collectionPlanId);
						returnMap.put(RECORD_ID, id);
					}
					returnMap.put(JRadConstants.MESSAGE, endResult);
					returnMap.addGlobalMessage(CREATE_SUCCESS);
				}else{
					returnMap.setSuccess(false);
					returnMap.addGlobalError(CustomerInforConstant.UPDATEERROR);
				}
			}catch (Exception e) {
				returnMap.put(JRadConstants.MESSAGE, DataPriConstants.SYS_EXCEPTION_MSG);
				returnMap.put(JRadConstants.SUCCESS, false);
				return WebRequestHelper.processException(e);
			}
		}
		return returnMap;
	}
	/**
	 * 修改催收实施计划信息页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "changeAction.page")
	@JRadOperation(JRadOperation.CHANGE)
	public AbstractModelAndView changeAction(HttpServletRequest request) {
		String id = RequestHelper.getStringValue(request, ID);
		RiskCustomerCollectionPlansAction riskCustomerCollectionPlansAction = riskCustomerCollectionService.findRiskCustomerCollectionPlansActionById(id);
		JRadModelAndView mv = new JRadModelAndView("/riskcontrol/riskCustomerCollection/collection_plan_action_change", request);
		mv.addObject("collectionPlansAction",riskCustomerCollectionPlansAction);
		return mv;
	}
	/**
	 * 修改催收计划实施信息
	 * @param form
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateAction.json")
	@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap updateAction(@ModelAttribute RiskCustomerCollectionPlansActionForm form, HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();//WebRequestHelper.requestValidation(getModuleName(), form);
		if (returnMap.isSuccess()) {
			try {
				IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
				String modifiedBy = user.getId(); 
				RiskCustomerCollectionPlansAction riskCustomerCollectionPlansAction = new RiskCustomerCollectionPlansAction();
				String collectionStartTime = form.getCollectionStartTime();
				String collectionEndTime = form.getCollectionEndTime();
				if(collectionStartTime!=null && !collectionStartTime.equals("")){
					Date start = DateHelper.getDateFormat(collectionStartTime, "yyyy-MM-dd HH:mm:ss");
					riskCustomerCollectionPlansAction.setCollectionStartTime(start);
				}
				if(collectionEndTime!=null && !collectionEndTime.equals("")){
				    Date end = DateHelper.getDateFormat(collectionEndTime, "yyyy-MM-dd HH:mm:ss");
				    riskCustomerCollectionPlansAction.setCollectionEndTime(end);
				}
				riskCustomerCollectionPlansAction.setId(form.getId());
				riskCustomerCollectionPlansAction.setCollectionPlanId(form.getCollectionPlanId());
				riskCustomerCollectionPlansAction.setCollection(form.getCollection());
				riskCustomerCollectionPlansAction.setCollectionResult(form.getCollectionResult());
				riskCustomerCollectionPlansAction.setModifiedBy(modifiedBy);
				boolean flag=riskCustomerCollectionService.updateRiskCustomerCollectionPlansAction(riskCustomerCollectionPlansAction);
				if(flag){
					returnMap.put(ID, riskCustomerCollectionPlansAction.getCollectionPlanId());
					returnMap.put(RECORD_ID, riskCustomerCollectionPlansAction.getId());
					returnMap.addGlobalMessage(CHANGE_SUCCESS);
				}else{
					returnMap.setSuccess(false);
					returnMap.addGlobalError(CustomerInforConstant.UPDATEERROR);
				}
			}catch (Exception e) {
				returnMap.put(JRadConstants.MESSAGE, DataPriConstants.SYS_EXCEPTION_MSG);
				returnMap.put(JRadConstants.SUCCESS, false);
				return WebRequestHelper.processException(e);
			}
		}
		return returnMap;
	}
	@RequestMapping(value = "getManager.json",method = RequestMethod.GET)
	public void getManager(HttpServletRequest request,PrintWriter printWriter){
		try {
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			String userId = user.getId();
			List<AccountManagerParameterForm> accountManagerParameterForms = managerBelongMapService.findSubManagerBelongMapByManagerId(userId);
			JSONArray json = new JSONArray();
			json = JSONArray.fromObject(accountManagerParameterForms);
			printWriter.write(json.toString());
			printWriter.flush();
			printWriter.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	@RequestMapping(value = "getCustomer.json",method = RequestMethod.GET)
	public void getCustomer(HttpServletRequest request,PrintWriter printWriter){
		try {
		String userId = RequestHelper.getStringValue(request, ID);
		List<Dict> riskCustomers = riskCustomerCollectionService.findCustomerIdAndName(userId);
		JSONArray json = new JSONArray();
		json = JSONArray.fromObject(riskCustomers);
		printWriter.write(json.toString());
		printWriter.flush();
		printWriter.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	@RequestMapping(value = "getProduct.json",method = RequestMethod.GET)
	public void getProductIdAndName(HttpServletRequest request,PrintWriter printWriter) {
		 try {
			 String customerId = RequestHelper.getStringValue(request, ID);
			 List<Dict> products = riskCustomerCollectionService.getProductIdAndName(customerId);
			 JSONArray json = new JSONArray();
			 json = JSONArray.fromObject(products);
			 printWriter.write(json.toString());
			 printWriter.flush();
			 printWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
