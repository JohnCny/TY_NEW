package com.cardpay.pccredit.riskControl.web;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.customer.constant.CustomerInforConstant;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.riskControl.constant.RiskAttributeEnum;
import com.cardpay.pccredit.riskControl.constant.RiskAttributeTypeEnum;
import com.cardpay.pccredit.riskControl.constant.RiskCreateTypeEnum;
import com.cardpay.pccredit.riskControl.constant.RiskCustomerStatusEnum;
import com.cardpay.pccredit.riskControl.filter.RiskAttributeFilter;
import com.cardpay.pccredit.riskControl.filter.RiskCustomerFilter;
import com.cardpay.pccredit.riskControl.model.RiskAttribute;
import com.cardpay.pccredit.riskControl.model.RiskCustomer;
import com.cardpay.pccredit.riskControl.service.RiskAttributeService;
import com.cardpay.pccredit.riskControl.service.RiskCustomerService;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.constant.JRadConstants;
import com.wicresoft.jrad.base.i18n.I18nHelper;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.jrad.modules.privilege.model.User;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;

/**
 * @author chenzhifang
 * 
 * 2014-10-24下午5:35:11
 */
@Controller
@RequestMapping("/riskcontrol/riskcustomer/*")
@JRadModule("riskcontrol.riskcustomer")
public class RiskCustomerController extends BaseController {
	
	@Autowired
	private RiskCustomerService riskCustomerService;
	
	@Autowired
	private CustomerInforService customerInforservice;
	
	@Autowired
	private RiskAttributeService riskAttributeService;
	

	/**
	 * 上报风险客户
	 * 
	 * 
	 * @param riskCustomerForm
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "report.json")
	@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap report(@ModelAttribute RiskCustomerForm riskCustomerForm, HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			RiskCustomer riskCustomer = riskCustomerForm.createModel(RiskCustomer.class);
			// 上报风险名单
			riskCustomerService.reportRiskCustomer(riskCustomer.getId());
			returnMap.put(RECORD_ID, riskCustomer.getId());
			
			returnMap.put(JRadConstants.SUCCESS, true);
			returnMap.put(JRadConstants.MESSAGE, Beans.get(I18nHelper.class).getMessageNotNull(JRadConstants.CHANGE_SUCCESS));
		}
		catch (Exception e) {
			return WebRequestHelper.processException(e);
		}
		return returnMap;
	}
	
	/**
	 * 确认风险客户(卡中心或主管)
	 * 
	 * @param riskCustomerForm
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "confirmed.json")
	@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap confirmed(@ModelAttribute RiskCustomerForm riskCustomerForm, HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			RiskCustomer riskCustomer = riskCustomerForm.createModel(RiskCustomer.class);
			// 上报风险名单
			riskCustomerService.confirmedRiskCustomer(riskCustomer.getId());
			returnMap.put(RECORD_ID, riskCustomer.getId());
			
			returnMap.put(JRadConstants.SUCCESS, true);
			returnMap.put(JRadConstants.MESSAGE, Beans.get(I18nHelper.class).getMessageNotNull(JRadConstants.CHANGE_SUCCESS));
		}
		catch (Exception e) {
			return WebRequestHelper.processException(e);
		}
		return returnMap;
	}
	
	/**
	 * 拒绝风险客户(卡中心或主管)
	 * 
	 * @param riskCustomerForm
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "reject.json")
	@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap reject(@ModelAttribute RiskCustomerForm riskCustomerForm, HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			RiskCustomer riskCustomer = riskCustomerForm.createModel(RiskCustomer.class);
			// 上报风险名单
			riskCustomerService.rejectRiskCustomer(riskCustomer);
			returnMap.put(RECORD_ID, riskCustomer.getId());
			
			returnMap.put(JRadConstants.SUCCESS, true);
			returnMap.put(JRadConstants.MESSAGE, Beans.get(I18nHelper.class).getMessageNotNull(JRadConstants.CHANGE_SUCCESS));
		}
		catch (Exception e) {
			return WebRequestHelper.processException(e);
		}
		return returnMap;
	}
	
	/**
	 * 加入黑名单页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "blacklistcreate.page")
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView blacklistCreate(@ModelAttribute RiskCustomerFilter filter, HttpServletRequest request) {        
		JRadModelAndView mv = new JRadModelAndView("/riskcontrol/riskCustomer/riskcustomer_create", request);
		RiskAttributeFilter rafilter = new RiskAttributeFilter();
		// 查询线下风险属性
		rafilter.setType(RiskAttributeTypeEnum.OFFLINE.toString());
		List<RiskAttribute> ralist = riskAttributeService.findRiskAttributeListByFilter(rafilter);
		RiskAttribute riskAttribute = null;
		Iterator<RiskAttribute> iterator = ralist.iterator();
		while(iterator.hasNext()){
			riskAttribute = iterator.next();
			if(!(RiskAttributeEnum.QZ.toString().equals(riskAttribute.getRiskAttribute())
					|| RiskAttributeEnum.FYQZZX.toString().equals(riskAttribute.getRiskAttribute()))){
				iterator.remove();
			}
		}
		mv.addObject("ralist", ralist);
		return mv;
	}
	
	/**
	 * 新增风险客户添加
	 * @param customerinfoForm
	 * @param request
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "insert.json")
	@JRadOperation(JRadOperation.CREATE)
	public JRadReturnMap insert(@ModelAttribute RiskCustomerForm riskCustomerForm, HttpServletRequest request) {
		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), riskCustomerForm);
		
		RiskCustomerFilter filter = new RiskCustomerFilter();
		filter.setCustomerId(riskCustomerForm.getCustomerId());
		filter.setRiskAttribute(riskCustomerForm.getRiskAttribute());
		// 判断是否在风险名单中
		//returnMap = isInBlacklist(filter, request);
		returnMap.setSuccess(true);
		if(returnMap.isSuccess()){
			try {
				RiskCustomer riskCustomer = riskCustomerForm.createModel(RiskCustomer.class);
				User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
				riskCustomer.setCreatedBy(user.getId());
				riskCustomer.setModifiedBy(user.getId());
				// 风险类型
				riskCustomer.setRiskCreateType(RiskCreateTypeEnum.manual.toString());
				riskCustomer.setStatus(RiskCustomerStatusEnum.UNREPORTED.toString());
				riskCustomer.setReportedIdManager(user.getId());
				
				riskCustomerService.insertRiskCustomer(riskCustomer);
				returnMap.put(RECORD_ID, riskCustomer.getId());
				returnMap.addGlobalMessage(CREATE_SUCCESS);
			} catch (Exception e) {
	            //TODO 有日志功能，在这一步应保持返回统一，出错以后查看日志
				return WebRequestHelper.processException(e);
			}
		}else{
			returnMap.setSuccess(false);
			returnMap.addGlobalError(CustomerInforConstant.CREATEERROR);
			if(returnMap.containsKey(JRadConstants.MESSAGE)){
				returnMap.addGlobalError((String) returnMap.get(JRadConstants.MESSAGE));
			}
		}
		return returnMap;
	}
	
	/**
	 * 判断客户的风险属性是否在风险名单中
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "isInBlacklist.json")
	@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap isInBlacklist(@ModelAttribute RiskCustomerFilter filter, HttpServletRequest request) {
		filter.setRequest(request);		
		JRadReturnMap returnMap = new JRadReturnMap();
		returnMap.put(JRadConstants.SUCCESS, true);
		boolean isInBlacklist = riskCustomerService.isInBlacklist(filter);
		if(isInBlacklist){
			returnMap.put(JRadConstants.SUCCESS, false);
			returnMap.put(JRadConstants.MESSAGE, Beans.get(I18nHelper.class).getMessageNotNull("riskcontrol.riskcustomer.isInBlacklist"));
		}
		return returnMap;
	}
}
