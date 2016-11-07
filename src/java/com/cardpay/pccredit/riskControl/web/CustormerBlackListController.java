package com.cardpay.pccredit.riskControl.web;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.customer.constant.CustomerInforConstant;
import com.cardpay.pccredit.manager.constant.ManagerLevelAdjustmentConstant;
import com.cardpay.pccredit.riskControl.constant.RiskCreateTypeEnum;
import com.cardpay.pccredit.riskControl.constant.RiskControlRole;
import com.cardpay.pccredit.riskControl.filter.RiskCustomerFilter;
import com.cardpay.pccredit.riskControl.model.CUSTOMERBLACKLIST;
import com.cardpay.pccredit.riskControl.model.RiskCustomer;
import com.cardpay.pccredit.riskControl.service.CustormerBlackListService;
import com.cardpay.pccredit.riskControl.service.RiskCustomerService;
import com.wicresoft.jrad.base.auth.IUser;
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
import com.wicresoft.jrad.modules.privilege.model.User;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;
import com.wicresoft.util.web.RequestHelper;

/**
 * @author chenzhifang
 *
 * 2014-11-4上午10:17:57
 */
@Controller
@RequestMapping("/riskcontrol/custormerblacklist/*")
@JRadModule("riskcontrol.custormerblacklist")
public class CustormerBlackListController extends BaseController {
	
	@Autowired
	private CustormerBlackListService cblservice;
	
	/**
	 * 客户经理浏览黑名单客户界面
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "custormerblacklist11.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView managerBrowse(@ModelAttribute CUSTOMERBLACKLIST filter,HttpServletRequest request) {
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		CUSTOMERBLACKLIST filter1=new CUSTOMERBLACKLIST();
		filter1.setRequest(request);
		filter.setUserid(user.getId());
		QueryResult<CUSTOMERBLACKLIST> result = cblservice.findAllCustormerBlackList1(filter);
		JRadPagedQueryResult<CUSTOMERBLACKLIST> pagedResult = new JRadPagedQueryResult<CUSTOMERBLACKLIST>(filter1, result);
		JRadModelAndView mv = new JRadModelAndView("/riskcontrol/riskCustomer/customer_black_list",request);
		mv.addObject(PAGED_RESULT, pagedResult);
		return mv;
	}
	
	/**
	 * 移除黑名单
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteByCoustorId.json", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public JRadReturnMap deleteByCoustorId(@ModelAttribute CUSTOMERBLACKLIST filter,HttpServletRequest request) {
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		JRadReturnMap returnMap = new JRadReturnMap();
		filter.setUserid(user.getId());
		filter.setCustormerid(request.getParameter("customer_id"));
		int a=cblservice.deleteByCoustorId(filter.getCustormerid(),filter.getUserid());
		if(a>0){
			returnMap.put(MESSAGE,"删除成功");
		}else{
			returnMap.put(MESSAGE,"删除失败");
		}
	
		return returnMap;
	}
	/**
	 * 当前客户经理的非黑名单客户
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "findAllNoCustormerBlackList.json", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView findAllNoCustormerBlackList(@ModelAttribute CUSTOMERBLACKLIST filter,HttpServletRequest request) {
		
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		CUSTOMERBLACKLIST filter1=new CUSTOMERBLACKLIST();
		filter1.setRequest(request);
		filter.setUserid(user.getId());
		QueryResult<CUSTOMERBLACKLIST> result = cblservice.findAllNoCustormerBlackList(filter);
		JRadPagedQueryResult<CUSTOMERBLACKLIST> pagedResult = new JRadPagedQueryResult<CUSTOMERBLACKLIST>(filter1, result);
		JRadModelAndView mv = new JRadModelAndView("/riskcontrol/riskCustomer/customer_noblack_list",request);
		mv.addObject(PAGED_RESULT, pagedResult);
		return mv;
	}
	
	/**
	 * 跳转到增加黑名单客户信息页面
	 * 
	 * @param request
	 * @return
	*/
	@ResponseBody
	@RequestMapping(value = "insertBlacklist.json", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView create(@ModelAttribute CUSTOMERBLACKLIST filter,HttpServletRequest request) {  
		filter.setChinese_name(request.getParameter("customer_name"));
		filter.setCustormerid(request.getParameter("customerid"));
		filter.setCard_id(request.getParameter("card_id"));
		JRadModelAndView mv = new JRadModelAndView("/riskcontrol/riskCustomer/customerinfor_create_black_list", request);
		mv.addObject("result", filter);
		return mv;
	}
	/**
	 * 添加黑名单客户
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "addCustomerBlackList.json", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public JRadReturnMap addCustomerBlackList(@ModelAttribute CUSTOMERBLACKLIST filter,HttpServletRequest request) {
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		JRadReturnMap returnMap = new JRadReturnMap();
		String sid=null;
		if(null==sid){
			sid=UUID.randomUUID().toString();
		}
		filter.setId(sid);
		filter.setUserid(user.getId());
		filter.setCustormerid(request.getParameter("customerid"));
		filter.setReason(request.getParameter("reason"));
		int a=cblservice.addCustomerBlackList(filter.getId(),filter.getUserid(),filter.getCustormerid(),filter.getReason());
		if(a>0){
			returnMap.put(MESSAGE,"操作成功");
		}else{
			returnMap.put(MESSAGE,"操作失败");
		}
	
		return returnMap;
	}
	
	/**
	 * 查询当前客户是否在黑名单中
	 * @param request
	 * @return
	 */
	 @ResponseBody
		@RequestMapping(value = "isonBlackList.json")
		public JRadReturnMap isonBlackList(HttpServletRequest request) {
			String customerId = request.getParameter("customerId");
			JRadReturnMap returnMap = new JRadReturnMap();
			if (returnMap.isSuccess()) {
				try {
					int a = cblservice.selectCount(customerId);
					if(a>0){
						returnMap.put("isonBlackList", true);
					}
					
				}catch (Exception e) {
					returnMap.put(JRadConstants.MESSAGE,"系统异常");
					returnMap.put(JRadConstants.SUCCESS, false);
					return WebRequestHelper.processException(e);
				}
			}else{
				returnMap.setSuccess(false);
				returnMap.addGlobalError(CustomerInforConstant.CREATEERROR);
			}
			return returnMap;
		}
	
}
