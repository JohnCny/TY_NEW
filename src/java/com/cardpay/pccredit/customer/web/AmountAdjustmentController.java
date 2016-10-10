/**
 * 
 */
package com.cardpay.pccredit.customer.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.customer.filter.AmountAdjustmentFilter;
import com.cardpay.pccredit.customer.service.AmountAdjustmentService;
import com.cardpay.workflow.service.ProcessService;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.jrad.modules.privilege.model.User;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;
import com.wicresoft.util.web.RequestHelper;

/**
 * 
 * 描述 ：客户额度调整
 * @author 张石树
 *
 * 2014-12-3 下午1:32:13
 */
@Controller
@RequestMapping("/customer/amountadjustment/*")
@JRadModule("customer.amountadjustment")
public class AmountAdjustmentController extends BaseController{
	
	@Autowired
	private AmountAdjustmentService amountAdjustmentService;
	
	@Autowired
	private ProcessService processService;

	/**
	 * 浏览调额待审核页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "browse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browse(@ModelAttribute AmountAdjustmentFilter filter, HttpServletRequest request) {
		filter.setRequest(request);
		User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
		filter.setAuditUser(user.getId());
		QueryResult<AmountAdjustmentForm> result = amountAdjustmentService.findAmountAdjustmentWaitApproveFilter(filter);
		JRadPagedQueryResult<AmountAdjustmentForm> pagedResult = new JRadPagedQueryResult<AmountAdjustmentForm>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/customer/amountadjustment/amountadjustment_approve_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);
		return mv;
	}
	
	/**
	 * 执行申请审核
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "applayApproveAmountAdjustment.json")
	@JRadOperation(JRadOperation.APPLYAPPROVE)
	public JRadReturnMap applayApproveAmountAdjustment(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
			
			int i = amountAdjustmentService.applayApproveAmountAdjustment(user.getId());
			if(i == 0){
				returnMap.setSuccess(false);
				returnMap.addGlobalError("请确认已申请审核先完成或没有需要待审核的调额信息");
			} else {
				returnMap.addGlobalMessage("执行申请审核成功");
			}
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}

		return returnMap;
	}

	/**
	 * 审核页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "apply.page")
	@JRadOperation(JRadOperation.APPROVE)
	public AbstractModelAndView applyPage(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/customer/amountadjustment/amountadjustment_approve", request);
		String id = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(id)) {
			AmountAdjustmentForm adjustmentForm = amountAdjustmentService.findWaitApproveAmountAdjustById(id);
			mv.addObject("adjustmentForm", adjustmentForm);
		}

		return mv;
	}
	
	/**
	 * 审批修改
	 * 
	 * @param amountAdjustmentForm
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "apply.json")
	@JRadOperation(JRadOperation.APPROVE)
	public JRadReturnMap applyJson(@ModelAttribute AmountAdjustmentForm amountAdjustmentForm, HttpServletRequest request) {

		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			amountAdjustmentService.approveAmountAdjustment(amountAdjustmentForm, request);
			returnMap.put(RECORD_ID, amountAdjustmentForm.getId());
			returnMap.addGlobalMessage(CHANGE_SUCCESS);
		}
		catch (Exception e) {
			return WebRequestHelper.processException(e);
		}

		return returnMap;
	}
}
