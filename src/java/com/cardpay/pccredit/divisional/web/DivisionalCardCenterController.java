package com.cardpay.pccredit.divisional.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.common.PccOrganizationService;
import com.cardpay.pccredit.customer.model.CustomerInforWeb;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.datapri.constant.DataPriConstants;
import com.cardpay.pccredit.divisional.constant.DivisionalConstant;
import com.cardpay.pccredit.divisional.constant.DivisionalProgressEnum;
import com.cardpay.pccredit.divisional.filter.DivisionalFilter;
import com.cardpay.pccredit.divisional.model.DivisionalWeb;
import com.cardpay.pccredit.divisional.service.DivisionalCardCenterService;
import com.cardpay.pccredit.divisional.service.DivisionalService;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.constant.JRadConstants;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;
import com.wicresoft.util.web.RequestHelper;
/**
 * 
 * @author 姚绍明
 *
 * 2014年10月24日 上午10:25:19
 */
@Controller
@RequestMapping("/divisional/cfccllot/*")
@JRadModule("divisional.cfccllot")
public class DivisionalCardCenterController extends BaseController{
	
	@Autowired
	private DivisionalService divisionalservice;
	@Autowired
	private DivisionalCardCenterService divisionalcardcenterService;
	
	@Autowired
	private CustomerInforService customerInforservice;
	
	@Autowired
	private PccOrganizationService pccorganizationService;
	
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
		filter.setDivisionalProgress(DivisionalConstant.CFCC);
		QueryResult<DivisionalWeb> result = divisionalcardcenterService.findDivisional(filter);
		JRadPagedQueryResult<DivisionalWeb> pagedResult = new JRadPagedQueryResult<DivisionalWeb>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/divisional/cfccllot/cfccllot_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);
		return mv;
	}
	
	/**
	 * 
	 * 机构分配
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "display.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.DISTRIBUTION)
	public AbstractModelAndView browse(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/divisional/cfccllot/cfccllot_display", request);
		String divisionalId = RequestHelper.getStringValue(request, ID);
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
	@RequestMapping(value = "cfccllot.json", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.DISTRIBUTION)
	public Map<String, Object> divisional(@ModelAttribute DivisionalForm form,HttpServletRequest request) {
		Map<String, Object> returnMap = new HashMap<String,Object>();
		String orgId = form.getOrgId();
		String id = form.getId();
		try{
			if(StringUtils.isNotEmpty(orgId)){
				int i = divisionalcardcenterService.updateDivisionalProcessAndOrg(id, orgId, DivisionalProgressEnum.charge);
				if(i>0){
					returnMap.put(JRadConstants.SUCCESS, true);
					returnMap.put(JRadConstants.MESSAGE,DivisionalConstant.DIVISIONALSUCCESS);
				}else{
					returnMap.put(JRadConstants.SUCCESS, false);
					returnMap.put(JRadConstants.MESSAGE,DivisionalConstant.DIVISIONALERROR);
				}
			}else{
				returnMap.put(JRadConstants.SUCCESS, false);
				returnMap.put(JRadConstants.MESSAGE,DivisionalConstant.DIVISIONALERROR+DivisionalConstant.ORGIDNOTNULL);
			}
		}catch(Exception e){
			returnMap.put(JRadConstants.MESSAGE, DataPriConstants.SYS_EXCEPTION_MSG);
			returnMap.put(JRadConstants.SUCCESS, false);
			return WebRequestHelper.processException(e);
		}
		return returnMap;
	}
}
