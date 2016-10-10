package com.cardpay.pccredit.system.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.common.PccOrganizationService;
import com.cardpay.pccredit.customer.web.AmountAdjustmentForm;
import com.cardpay.pccredit.datapri.web.FlatTreeNode;
import com.cardpay.pccredit.intopieces.constant.Constant;
import com.cardpay.pccredit.system.filter.SystemChargeFilter;
import com.cardpay.pccredit.system.filter.SystemUserFilter;
import com.cardpay.pccredit.system.model.Choujiang;
import com.cardpay.pccredit.system.model.SystemUser;
import com.cardpay.pccredit.system.service.SystemChargeService;
import com.cardpay.pccredit.system.service.SystemUserService;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.constant.JRadConstants;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.jrad.modules.privilege.business.DepartmentManager;
import com.wicresoft.jrad.modules.privilege.constant.PrivilegeConstants;
import com.wicresoft.jrad.modules.privilege.filter.DepartmentFilter;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;

@Controller
@RequestMapping("/system/charge/*")
@JRadModule("system.charge")
public class SystemChargeController extends BaseController{
	
	@Autowired
	private SystemUserService systemUserService;
	
	@Autowired
	private DepartmentManager departmentManager;
	
	@Autowired
	private SystemChargeService systemChargeService;
	/**
	 * 用户显示
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */

	//负责人管理
	@ResponseBody
	@RequestMapping(value = "browse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browse(@ModelAttribute DepartmentFilter filter, HttpServletRequest request) {
		String departmentJson = this.departmentManager.getAllOrganizationAndDepartment(filter);
		JRadModelAndView mv = new JRadModelAndView("/system/charge/charge_browse", request);
		mv.addObject("children", departmentJson);
		return mv;
	}
	
	//负责人列表
	@ResponseBody
	@RequestMapping(value = { "displayChargeList.page" }, method = { RequestMethod.GET })
	public AbstractModelAndView displayChargeList(@ModelAttribute SystemChargeFilter filter, HttpServletRequest request) {
		filter.setRequest(request);
		QueryResult<SystemUser> result = this.systemChargeService.findChargeByFilter(filter);
		JRadPagedQueryResult<SystemUser> pagedResult = new JRadPagedQueryResult<SystemUser>(filter,result);
		JRadModelAndView mv = new JRadModelAndView("/system/charge/charge_list", request);
		mv.addObject("result", pagedResult);
		mv.addObject("orgId", filter.getOrgId());
		mv.addObject("deptId", filter.getDeptId());
		return mv;
	}
	
	//增加负责人
	@ResponseBody
	@RequestMapping(value = "addCharge.json")
	public JRadReturnMap addCharge(@ModelAttribute SystemChargeForm systemChargeForm,HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		if (returnMap.isSuccess()) {
			try {
				systemChargeService.addCharge(systemChargeForm);
				returnMap.put(JRadConstants.SUCCESS, true);
			}
			catch (Exception e) {
				returnMap.put(JRadConstants.SUCCESS, false);
				return WebRequestHelper.processException(e);
			}
		}

		return returnMap;
	}
	
	//删除负责人
	@ResponseBody
	@RequestMapping(value = "deleteCharge.json")
	public JRadReturnMap deleteCharge(@ModelAttribute SystemChargeForm systemChargeForm,HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		if (returnMap.isSuccess()) {
			try {
				systemChargeService.deleteCharge(systemChargeForm);
				returnMap.put(JRadConstants.SUCCESS, true);
			}
			catch (Exception e) {
				returnMap.put(JRadConstants.SUCCESS, false);
				return WebRequestHelper.processException(e);
			}
		}

		return returnMap;
	}
		
	//显示用户列表
	@ResponseBody
	@RequestMapping(value = "charge.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView charge(@ModelAttribute SystemUserFilter filter, HttpServletRequest request) {
		filter.setRequest(request);
		String selectval = request.getParameter("selectval");
		List<String> selectlist = new  ArrayList<String>();
		String [] selectvalarry =null;
		if(selectval != null){
		 selectvalarry= selectval.split(",");
		 for(int i=0;i<selectvalarry.length;i++){ 
			 selectlist.add(selectvalarry[i]);
		 }
	    }
		QueryResult<SystemUser> result = systemUserService.findSystemUserByFilterAndUserType(filter,request.getParameter("type").equals("org")?"3":"2");
		JRadPagedQueryResult<SystemUser> pagedResult = new JRadPagedQueryResult<SystemUser>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/system/nodeaudit/system_user_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);
		mv.addObject("selectvalList", selectlist);
		return mv;
	}
	
	//显示抽奖页面
	@ResponseBody
	@RequestMapping(value = "choujiang.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView choujiang(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/home/choujiang", request);
		List<Choujiang> listNo = systemChargeService.query();
		List<Choujiang> listYes = systemChargeService.query1();
		String result = "";
		for(int i=0;i<listNo.size();i++){
			result+=listNo.get(i).getName()+"-"+listNo.get(i).getExentId()+",";
		}
		if(result!=""){
			result=result.substring(0,result.length()-1);
		}
		mv.addObject("users", result);
		mv.addObject("listYes", listYes);
		return mv;
	}
	//保存
	@ResponseBody
	@RequestMapping(value = "choujiangSave.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView choujiangSave(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/home/choujiang", request);
		
		String choujiangResult = request.getParameter("result");
		String level = request.getParameter("level");
		
		systemChargeService.save(choujiangResult, level);
		List<Choujiang> listNo = systemChargeService.query();
		List<Choujiang> listYes = systemChargeService.query1();
		String result = "";
		for(int i=0;i<listNo.size();i++){
			result+=listNo.get(i).getName()+"-"+listNo.get(i).getExentId()+",";
		}
		if(result!=""){
			result=result.substring(0,result.length()-1);
		}
		mv.addObject("users", result);
		mv.addObject("listYes", listYes);
		return mv;
	}
}
