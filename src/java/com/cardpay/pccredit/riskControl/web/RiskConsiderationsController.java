package com.cardpay.pccredit.riskControl.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.common.PccOrganizationService;
import com.cardpay.pccredit.datapri.web.FlatTreeNode;
import com.cardpay.pccredit.riskControl.constant.RiskControlRole;
import com.cardpay.pccredit.riskControl.filter.RiskConsiderationsFilter;
import com.cardpay.pccredit.riskControl.model.RiskConsiderations;
import com.cardpay.pccredit.riskControl.model.RiskReviewProcess;
import com.cardpay.pccredit.riskControl.service.RiskConsiderationsService;
import com.cardpay.pccredit.riskControl.service.RiskReviewProcessService;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.constant.JRadConstants;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.i18n.I18nHelper;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.jrad.modules.privilege.constant.PrivilegeConstants;
import com.wicresoft.jrad.modules.privilege.model.User;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;
import com.wicresoft.util.web.RequestHelper;

/**
 * @author chenzhifang
 *
 * 2014-11-5上午11:21:14
 */
@Controller
@RequestMapping("/riskcontrol/riskconsiderations/*")
@JRadModule("riskcontrol.riskconsiderations")
public class RiskConsiderationsController extends BaseController {
	@Autowired
	private RiskConsiderationsService riskConsiderationsService;
	
	@Autowired
	private PccOrganizationService pccOrganizationService;
	
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
	@RequestMapping(value = "browse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browse(@ModelAttribute RiskConsiderationsFilter filter,HttpServletRequest request) {
        filter.setRequest(request);
        User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
        user.getRoles();
        
        // 客户经理Id
        //filter.setReportedIdManager(user.getId());
        // 风险类型
        //filter.setRiskCategories(RiskCategoriesEnum.FXMD.toString());
        // 客户经理
        filter.setRole(RiskControlRole.manager.toString());
		QueryResult<RiskConsiderations> result = riskConsiderationsService.findRiskConsiderationsByFilter(filter);
		JRadPagedQueryResult<RiskConsiderations> pagedResult = new JRadPagedQueryResult<RiskConsiderations>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/riskcontrol/riskconsiderations/riskconsiderations_browse",request);
		mv.addObject(PAGED_RESULT, pagedResult);
		List<FlatTreeNode> orgList = pccOrganizationService.queryAllOrgTreeList(PrivilegeConstants.INIT_ID);
		mv.addObject("orgList", orgList);
		return mv;
	}
	
	/**
	 * 增加页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "create.page")
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView create(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/riskcontrol/riskconsiderations/riskconsiderations_create", request);
		List<FlatTreeNode> orgList = pccOrganizationService.queryAllOrgTreeList(PrivilegeConstants.INIT_ID);
		mv.addObject("orgList", orgList);
		return mv;
	}
	
	/**
	 * 修改页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "change.page")
	@JRadOperation(JRadOperation.CHANGE)
	public AbstractModelAndView change(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/riskcontrol/riskconsiderations/riskconsiderations_change", request);

		String riskConsiderationsId = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(riskConsiderationsId)) {
			RiskConsiderations riskConsiderations = riskConsiderationsService.findRiskConsiderationsById(riskConsiderationsId);
			mv.addObject("riskConsiderations", riskConsiderations);
			mv.addObject(RECORD_ID, riskConsiderationsId);
			List<FlatTreeNode> orgList = pccOrganizationService.queryAllOrgTreeList(PrivilegeConstants.INIT_ID);
			mv.addObject("orgList", orgList);
		}

		return mv;
	}

	/**
	 * 显示页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "display.page")
	@JRadOperation(JRadOperation.DISPLAY)
	public AbstractModelAndView display(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/riskcontrol/riskconsiderations/riskconsiderations_display", request);

		String riskConsiderationsId = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(riskConsiderationsId)) {
			RiskConsiderations riskConsiderations = riskConsiderationsService.findRiskConsiderationsById(riskConsiderationsId);
			mv.addObject("riskConsiderations", riskConsiderations);
			List<FlatTreeNode> orgList = pccOrganizationService.queryAllOrgTreeList(PrivilegeConstants.INIT_ID);
			mv.addObject("orgList", orgList);
		}

		return mv;
	}

	/**
	 * 执行添加
	 * 
	 * @param riskConsiderationsForm
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insert.json")
	@JRadOperation(JRadOperation.CREATE)
	public JRadReturnMap insert(@ModelAttribute RiskConsiderationsForm riskConsiderationsForm, HttpServletRequest request) {
		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), riskConsiderationsForm);
		if (returnMap.isSuccess()) {
			try {
				RiskConsiderations riskConsiderations = riskConsiderationsForm.createModel(RiskConsiderations.class);
		        User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
				riskConsiderations.setReleaseInstitution(user.getOrganization()!=null?user.getOrganization().getId():"");
				String id = riskConsiderationsService.insertRiskConsiderations(riskConsiderations);
				returnMap.put(RECORD_ID, id);
				returnMap.addGlobalMessage(CREATE_SUCCESS);
			}
			catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}

		return returnMap;
	}

	/**
	 * 执行修改
	 * 
	 * @param riskConsiderationsForm
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "update.json")
	@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap update(@ModelAttribute RiskConsiderationsForm riskConsiderationsForm, HttpServletRequest request) {

		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), riskConsiderationsForm);
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

	/**
	 * 执行删除
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "delete.json")
	@JRadOperation(JRadOperation.DELETE)
	public JRadReturnMap delete(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();

		try {
			String riskConsiderationsId = RequestHelper.getStringValue(request, ID);
			riskConsiderationsService.deleteRiskConsiderations(riskConsiderationsId);
			returnMap.addGlobalMessage(DELETE_SUCCESS);
		}
		catch (Exception e) {
			return WebRequestHelper.processException(e);
		}

		return returnMap;
	}
	
	/**
	 * 上报风险属性
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "report.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView report(@ModelAttribute RiskConsiderationsFilter filter,HttpServletRequest request) {
		AbstractModelAndView mv = browse(filter, request);
		mv.setViewName("/riskcontrol/riskconsiderations/riskconsiderations_choose");
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
