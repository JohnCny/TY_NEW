package com.cardpay.pccredit.customeradd.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.customer.dao.MaintenanceDao;
import com.cardpay.pccredit.customer.filter.MaintenanceFilter;
import com.cardpay.pccredit.customer.model.CustomerFirsthendBase;
import com.cardpay.pccredit.customer.service.MaintenanceService;
import com.cardpay.pccredit.customeradd.model.CIPERSONBASINFO;
import com.cardpay.pccredit.customeradd.model.MaintenanceForm;
import com.cardpay.pccredit.customeradd.service.KuglService;
import com.cardpay.pccredit.jnpad.model.CustomerInfo;
import com.cardpay.pccredit.manager.web.AccountManagerParameterForm;
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
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;
import com.wicresoft.util.web.RequestHelper;
/**
 * 
 * 描述 ：管辖客户管理
 * @author  郑博
 *
 * 2014-11-10 下午3:32:01
 */
@Controller
@RequestMapping("/manager/khgl/*")
@JRadModule("manager.khgl")
public class ManagerkhglController extends BaseController{
	
	@Autowired
	private KuglService service;
	@Autowired
	private MaintenanceService maintenanceService;
	@Autowired
	private MaintenanceDao maintenanceDao;
	/**
	 * 通过客户经理查询
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "browse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView query(@ModelAttribute MaintenanceFilter filter,HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/customeradd/maintenance_plan_browse", request);
		filter.setRequest(request);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		List<AccountManagerParameterForm>forms=new ArrayList<AccountManagerParameterForm>();
		//查询客户经理
		//不是客户经理的显示全部客户经理
		if(user.getUserType()!=1){
			forms=service.findcustomermanager();
		}else{
			//客户经理组长显示他及手下客户经理  客户经理则只显示自己
			forms = maintenanceService.findSubListManagerByManagerId(user);
		}
		String customerManagerId = filter.getCustomerManagerId();
		QueryResult<MaintenanceForm> result = null;
		//如果页面 传过来有信息怎显示客户经理手下信息
		if(customerManagerId!=null && !customerManagerId.equals("")){
			result = service.findMaintenancePlansList(filter);
		}else{
			//如果页面穿过来没有信息则显示所有客户经理手下信息
			if(forms.size()>0){
				//filter.setCustomerManagerIds(forms);
				result = service.findMaintenancePlansList(filter);
			}else{
				//直接返回页面
				return mv;
			}
		}
		JRadPagedQueryResult<MaintenanceForm> pagedResult = new JRadPagedQueryResult<MaintenanceForm>(filter, result);
		mv.addObject(PAGED_RESULT, pagedResult);
		mv.addObject("forms", forms);
		return mv;
	}
	
	/**
	 * 
	 * 客户详情
	 * */
	@ResponseBody
	@RequestMapping(value = "xs.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView xs(@ModelAttribute MaintenanceFilter filter,HttpServletRequest request) {
		JRadModelAndView mv = null;
		String productId[] = RequestHelper.getStringValue(request, ID).split("/");
		QueryResult<MaintenanceForm> result = null;
			String cardid=productId[2];
			//通过页面取得cardid查询用户的具体信息
			List<CustomerFirsthendBase>customerinfo=service.findcustomerinfo(cardid);
			mv = new JRadModelAndView("/customeradd/findcustomerinfo", request);
			mv.addObject("customerInfor", customerinfo);
		return mv;
	}
	
	//====================================================================================================================//
		@ResponseBody
		@RequestMapping(value = "dimensionsCreditModel.page", method = { RequestMethod.GET })
		@JRadOperation(JRadOperation.BROWSE)
		public AbstractModelAndView xs(HttpServletRequest request) {
			JRadModelAndView mv = new JRadModelAndView("/static/score", request);
			return mv;
		}
}
