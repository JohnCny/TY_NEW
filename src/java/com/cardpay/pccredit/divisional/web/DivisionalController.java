package com.cardpay.pccredit.divisional.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.customer.model.CustomerInforWeb;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.datapri.constant.DataPriConstants;
import com.cardpay.pccredit.divisional.constant.DivisionalConstant;
import com.cardpay.pccredit.divisional.filter.DivisionalFilter;
import com.cardpay.pccredit.divisional.model.DivisionalWeb;
import com.cardpay.pccredit.divisional.service.DivisionalService;
import com.cardpay.pccredit.system.model.Dict;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.constant.JRadConstants;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.jrad.modules.privilege.model.User;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;
import com.wicresoft.util.web.RequestHelper;

/**
 * 
 * @author shaoming
 *
 */
@Controller
@RequestMapping("/divisional/customerallot/*")
@JRadModule("divisional.customerallot")
public class DivisionalController extends BaseController{

	@Autowired
	private DivisionalService divisionalservice;

	@Autowired
	private CustomerInforService customerInforservice;
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
	public AbstractModelAndView browse(@ModelAttribute DivisionalFilter filter, HttpServletRequest request) {
		filter.setRequest(request);
		IUser user =Beans.get(LoginManager.class).getLoggedInUser(request);
		String orgId = user.getOrganization().getId();
		filter.setCurrentOrganizationId(orgId);
		filter.setDivisionalProgress(DivisionalConstant.CHARGE);
		QueryResult<DivisionalWeb> result = divisionalservice.findDivisional(filter);
		JRadPagedQueryResult<DivisionalWeb> pagedResult = new JRadPagedQueryResult<DivisionalWeb>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/divisional/customerallot/customerallot_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);
		return mv;
	}
	/**
	 * 
	 * 客户经理分配
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "divisional.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.DISTRIBUTION)
	public AbstractModelAndView browse(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/divisional/customerallot/customerallot_divisional", request);
		String divisionalId = RequestHelper.getStringValue(request, ID);
		String divisionalResult = divisionalservice.findDivisionalResultById(divisionalId);
		IUser user = (User)Beans.get(LoginManager.class).getLoggedInUser(request);
		if(divisionalResult==null || !divisionalResult.equals(DivisionalConstant.DISTRIBUTION)){
			List<Dict> customerManagers = divisionalservice.findCustomerManagers(user.getOrganization().getId());
			mv.addObject(DivisionalConstant.CUSTOMERMANAGERS,customerManagers);
		}
		CustomerInforWeb customerInforWeb = null;
		if (StringUtils.isNotEmpty(divisionalId)) {
			String customerInforId = divisionalservice.findCustomerIdById(divisionalId);
			customerInforWeb = customerInforservice.findCustomerInforWebById(customerInforId);
		}
		mv.addObject(DivisionalConstant.CUSTOMERINFOR, customerInforWeb);
		mv.addObject(DivisionalConstant.ID,divisionalId);
		return mv;
	}
	/**
	 * 提交分配结果 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "divisional.json", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.DISTRIBUTION)
	public Map<String, Object> divisional(@ModelAttribute DivisionalForm form,HttpServletRequest request) {
		Map<String, Object> returnMap = new HashMap<String,Object>();
		IUser user =(User)Beans.get(LoginManager.class).getLoggedInUser(request);
		String orgId = user.getOrganization().getId();
		String id = form.getId();
		String customerManagerId = form.getCustomerManagerId();
		try{
			int i = divisionalservice.updateDivisional(id, customerManagerId, orgId,DivisionalConstant.DISTRIBUTION);
			if(i>0){
				returnMap.put(JRadConstants.SUCCESS, true);
				returnMap.put(JRadConstants.MESSAGE,DivisionalConstant.DIVISIONALSUCCESS);
			}else{
				returnMap.put(JRadConstants.SUCCESS, false);
				returnMap.put(JRadConstants.MESSAGE,DivisionalConstant.DIVISIONALERROR);
			}
		}catch(Exception e){
			return WebRequestHelper.processException(e);
		}
		returnMap.put(RECORD_ID, id);
		return returnMap;
	}	
	/**
	 * 上传到卡中心 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "upload.json", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.DISTRIBUTION)
	public Map<String, Object> upload(HttpServletRequest request) {
		Map<String, Object> returnMap = new HashMap<String,Object>();
		String id = RequestHelper.getStringValue(request, ID);
		try {
			String process = divisionalservice.findDivisionalProcessById(id);
			if(process.equals(DivisionalConstant.CFCC)){
				returnMap.put(JRadConstants.SUCCESS, false);
				returnMap.put(JRadConstants.MESSAGE,DivisionalConstant.UPLOADERRORC);
				return returnMap;
			}
			boolean flag = divisionalservice.uploadToCardCenter(id,DivisionalConstant.CFCC);
			if(flag){
				returnMap.put(JRadConstants.SUCCESS, true);
				returnMap.put(JRadConstants.MESSAGE,DivisionalConstant.UPLOADSUCCESS);
			}else{
				returnMap.put(JRadConstants.SUCCESS, false);
				returnMap.put(JRadConstants.MESSAGE,DivisionalConstant.UPLOADERROR);
			}
		} catch (Exception e) {
			returnMap.put(JRadConstants.MESSAGE, DataPriConstants.SYS_EXCEPTION_MSG);
			returnMap.put(JRadConstants.SUCCESS, false);
			return WebRequestHelper.processException(e);
		}
		return returnMap;
	}
}
