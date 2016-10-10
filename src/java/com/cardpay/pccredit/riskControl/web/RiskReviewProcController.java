package com.cardpay.pccredit.riskControl.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.riskControl.model.RiskConsiderations;
import com.cardpay.pccredit.riskControl.model.RiskCustomer;
import com.cardpay.pccredit.riskControl.model.RiskReviewProcess;
import com.cardpay.pccredit.riskControl.service.RiskConsiderationsService;
import com.cardpay.pccredit.riskControl.service.RiskReviewProcessService;
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
import com.wicresoft.util.web.RequestHelper;

/**
 * @author chenzhifang
 *
 * 2014-11-5下午4:40:35
 */
@Controller
@RequestMapping("/riskcontrol/riskreviewprocess/*")
@JRadModule("riskcontrol.riskreviewprocess")
public class RiskReviewProcController extends BaseController {
	
	@Autowired
	private RiskReviewProcessService riskReviewProcessService;
	
	@Autowired
	private RiskConsiderationsService riskConsiderationsService;
	
	/**
	 * 确认风险事项审核流程(卡中心或主管)
	 * 
	 * @param riskCustomerForm
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "confirmed.json")
	@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap confirmed(@ModelAttribute RiskReviewProcForm riskReviewProcForm, HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			RiskReviewProcess riskReviewProcess = riskReviewProcessService.findRiskReviewProcessById(riskReviewProcForm.getId());
			User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
			riskReviewProcess.setAuditPeople(user.getId());
			riskReviewProcess.setModifiedBy(user.getId());
			riskReviewProcess.setNote(riskReviewProcForm.getNote());
			// 确认风险事项审核流程
			riskReviewProcessService.confirmedRiskReviewProcess(riskReviewProcess);
			returnMap.put(RECORD_ID, riskReviewProcess.getId());
			
			returnMap.put(JRadConstants.SUCCESS, true);
			returnMap.put(JRadConstants.MESSAGE, Beans.get(I18nHelper.class).getMessageNotNull(JRadConstants.CHANGE_SUCCESS));
		}
		catch (Exception e) {
			return WebRequestHelper.processException(e);
		}
		return returnMap;
	}
	
	/**
	 * 拒绝风险事项审核流程(卡中心或主管)
	 * 
	 * @param riskCustomerForm
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "reject.json")
	@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap reject(@ModelAttribute RiskReviewProcForm riskReviewProcForm, HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			RiskReviewProcess riskReviewProcess = riskReviewProcessService.findRiskReviewProcessById(riskReviewProcForm.getId());
			User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
			riskReviewProcess.setAuditPeople(user.getId());
			riskReviewProcess.setModifiedBy(user.getId());
			riskReviewProcess.setNote(riskReviewProcForm.getNote());
			// 拒绝风险事项审核流程
			riskReviewProcessService.rejectRiskReviewProcess(riskReviewProcess);
			returnMap.put(RECORD_ID, riskReviewProcess.getId());
			
			returnMap.put(JRadConstants.SUCCESS, true);
			returnMap.put(JRadConstants.MESSAGE, Beans.get(I18nHelper.class).getMessageNotNull(JRadConstants.CHANGE_SUCCESS));
		}
		catch (Exception e) {
			return WebRequestHelper.processException(e);
		}
		return returnMap;
	}
	
	/**
	 * 发布页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "publish.page")
	@JRadOperation(JRadOperation.CHANGE)
	public AbstractModelAndView publish(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/riskcontrol/riskreviewprocess/riskconsiderations_publish", request);

		String riskConsiderationsId = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(riskConsiderationsId)) {
			RiskConsiderations riskConsiderations = riskConsiderationsService.findRiskConsiderationsById(riskConsiderationsId);
			mv.addObject("riskConsiderations", riskConsiderations);
			mv.addObject(RECORD_ID, riskConsiderationsId);
			
		}

		return mv;
	}
	
	/**
	 * 发布
	 * 
	 * @param riskConsiderationsForm
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "publish.json")
	@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap publish(@ModelAttribute RiskConsiderationsForm riskConsiderationsForm, HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		returnMap.setSuccess(true);
		if (returnMap.isSuccess()) {
			try {
				RiskConsiderations riskConsiderations = riskConsiderationsForm.createModel(RiskConsiderations.class);
				riskConsiderationsService.updateRiskConsiderations(riskConsiderations);
				returnMap.put(RECORD_ID, riskConsiderations.getId());
				returnMap.addGlobalMessage(CHANGE_SUCCESS);
			}
			catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}

		return returnMap;
	}
}
