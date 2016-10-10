package com.cardpay.pccredit.customer.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.customer.filter.CustomerInforFilter;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.service.CustomerInforMoveService;
import com.cardpay.pccredit.system.model.SystemUser;
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
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;

/**
 * 
 * @author 贺珈
 *客户转交
 */
@Controller
@RequestMapping("/customer/basiccustomerinforMove/*")
@JRadModule("customer.basiccustomerinforMove")
public class CustomerInfoMoveController extends BaseController{
	
	
	@Autowired
	private CustomerInforMoveService customerInforMoveService;

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
	public AbstractModelAndView browse(@ModelAttribute CustomerInforFilter filter, HttpServletRequest request) {
		filter.setRequest(request);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		filter.setUserId(user.getId());
		
		QueryResult<CustomerInfoMoveForm> result = customerInforMoveService.findCustomerMoveByFilter(filter);	
		JRadPagedQueryResult<CustomerInfoMoveForm> pagedResult = new JRadPagedQueryResult<CustomerInfoMoveForm>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/customer/customerInfoMove/customerinfo_move_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);
		return mv;
	}

	/**
	 * 转交页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	
*/
	@ResponseBody
	@RequestMapping(value = "move.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView move(HttpServletRequest request) {
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String customerId = request.getParameter(ID);
		//获取客户信息
		CustomerInfor info =customerInforMoveService.queryCustomerById(customerId);
		//获取所有客户经理
		List<SystemUser> Userlist = customerInforMoveService.queryCustomerAll();
		JRadModelAndView mv = new JRadModelAndView("/customer/customerInfoMove/customerinfo_move_create", request);
		mv.addObject(PAGED_RESULT, info);
		mv.addObject("user", user);
		mv.addObject("Userlist", Userlist);
		return mv;
	}
	/**
	 * 保存申请转交
	 * 
	 * @param request
	 * @return
	 * @throws IOException 
	*/
	@ResponseBody
	@RequestMapping(value = "save.json")
	@JRadOperation(JRadOperation.CREATE)
	public JRadReturnMap save(HttpServletRequest request)  {        
		JRadReturnMap returnMap = new JRadReturnMap();  
		try {
			String customerId = request.getParameter("customerId");
			String approveId = request.getParameter("approveId");
			String moveId = request.getParameter("moveId");
			customerInforMoveService.approve(customerId, approveId, moveId);
			returnMap.addGlobalMessage(CHANGE_SUCCESS);
		} catch (Exception e) {

			returnMap.put(JRadConstants.SUCCESS, false);
			}
		return returnMap ;
	}
	
	/**
	 *审批 浏览页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	
*/
	@ResponseBody
	@RequestMapping(value = "examineBrowse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView examineBrowse(@ModelAttribute CustomerInforFilter filter, HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/customer/customerInfoMove/customerinfo_examine_browse", request);
		filter.setRequest(request);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		int userType = user.getUserType();
		//部门主管、机构主管
		if(userType==2||userType==3){
			//申请状态
			filter.setMoveStatus("1");
			QueryResult<CustomerInfoMoveForm> result = customerInforMoveService.findCustomerMoveByFilter(filter);
			
			JRadPagedQueryResult<CustomerInfoMoveForm> pagedResult = new JRadPagedQueryResult<CustomerInfoMoveForm>(filter, result);
			mv.addObject(PAGED_RESULT, pagedResult);
			return mv;
		}else{
			mv.addObject(PAGED_RESULT, null);
			return mv;
		}
	}
	/**
	 * 
	 * 通过
	 * @param request
	 * @return
	 * @throws IOException 
	*/
	@ResponseBody
	@RequestMapping(value = "yes.json")
	@JRadOperation(JRadOperation.CREATE)
	public JRadReturnMap yes(HttpServletRequest request)  {        
		JRadReturnMap returnMap = new JRadReturnMap();  
		try {
			String type = request.getParameter("type");
			String id = request.getParameter(ID);
			customerInforMoveService.examine(id,type);
			returnMap.addGlobalMessage(CHANGE_SUCCESS);
		} catch (Exception e) {

			returnMap.put(JRadConstants.SUCCESS, false);
			}
		return returnMap ;
	}
}
