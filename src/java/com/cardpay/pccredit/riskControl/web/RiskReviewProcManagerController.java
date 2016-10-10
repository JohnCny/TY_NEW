package com.cardpay.pccredit.riskControl.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.riskControl.constant.RiskControlRole;
import com.cardpay.pccredit.riskControl.filter.RiskReviewProcessFilter;
import com.cardpay.pccredit.riskControl.model.RiskReviewProcess;
import com.cardpay.pccredit.riskControl.service.RiskReviewProcessService;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.constant.JRadConstants;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.i18n.I18nHelper;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.jrad.modules.privilege.model.User;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;

/**
 * 风险事项审核流程(客户经理)
 * @author chenzhifang
 *
 * 2014-11-5下午4:56:09
 */
@Controller
@RequestMapping("/riskcontrol/riskreviewprocessmanager/*")
@JRadModule("riskcontrol.riskreviewprocessmanager")
public class RiskReviewProcManagerController extends RiskReviewProcController {
	@Autowired
	private RiskReviewProcessService riskReviewProcessService;
	
	/**
	 * 客户经理浏览页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "managerbrowse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView managerBrowse(@ModelAttribute RiskReviewProcessFilter filter,HttpServletRequest request) {
        filter.setRequest(request);
        User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
        // 客户经理Id
        filter.setReportedManagerId(user.getId());
        // 阶段
        filter.setPhase(RiskControlRole.manager.toString());
		QueryResult<RiskReviewProcess> result = riskReviewProcessService.findRiskReviewProcessByFilter(filter);
		JRadPagedQueryResult<RiskReviewProcess> pagedResult = new JRadPagedQueryResult<RiskReviewProcess>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/riskcontrol/riskreviewprocess/riskreviewprocess_manager_browse",request);
		mv.addObject(PAGED_RESULT, pagedResult);
		return mv;
	}
	
	/**
	 * 客户经理上报风险事项
	 * 
	 * 
	 * @param riskCustomerForm
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "report.json")
	@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap report(@ModelAttribute RiskReviewProcForm riskReviewProcForm, HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			RiskReviewProcess riskReviewProcess = null;
			if(StringUtils.isNotEmpty(riskReviewProcForm.getId())){
				riskReviewProcess = riskReviewProcessService.findRiskReviewProcessById(riskReviewProcForm.getId());
			}else{
				riskReviewProcess = riskReviewProcForm.createModel(RiskReviewProcess.class);
			}
			User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
			// 设置上报客户经理
			riskReviewProcess.setReportedManagerId(user.getId());
			// 上报上报风险事项
			boolean flag = riskReviewProcessService.reportRiskReviewProcess(riskReviewProcess);
			returnMap.put(RECORD_ID, riskReviewProcess.getId());
			if(flag){
				returnMap.put(JRadConstants.SUCCESS, true);
				returnMap.put(JRadConstants.MESSAGE, Beans.get(I18nHelper.class).getMessageNotNull(JRadConstants.CHANGE_SUCCESS));
			}
		}
		catch (Exception e) {
			return WebRequestHelper.processException(e);
		}
		return returnMap;
	}
}
