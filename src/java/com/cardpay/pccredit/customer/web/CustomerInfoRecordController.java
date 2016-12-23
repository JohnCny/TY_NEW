package com.cardpay.pccredit.customer.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cardpay.pccredit.common.IdCardValidate;
import com.cardpay.pccredit.customer.constant.CustomerInforConstant;
import com.cardpay.pccredit.customer.filter.CustomerInforFilter;
import com.cardpay.pccredit.customer.filter.CustomerRecordFilter;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.model.CustomerRecord;
import com.cardpay.pccredit.customer.service.CustomerInforMoveService;
import com.cardpay.pccredit.customer.service.CustomerInforRecordService;
import com.cardpay.pccredit.datapri.constant.DataPriConstants;
import com.cardpay.pccredit.intopieces.constant.Constant;
import com.cardpay.pccredit.riskControl.model.RiskCustomer;
import com.cardpay.pccredit.system.model.SystemUser;
import com.sun.org.apache.xpath.internal.operations.Bool;
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
import com.wicresoft.jrad.modules.privilege.model.User;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;

/**
 * 
 * @author 贺珈
 *客户档案申请
 */
@Controller
@RequestMapping("/customer/record/*")
@JRadModule("customer.record")
public class CustomerInfoRecordController extends BaseController{
	
	
	@Autowired
	private CustomerInforRecordService customerInforRecordService;

	/**
	 * 申请页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	
*/
	@ResponseBody
	@RequestMapping(value = "applyBrowse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView applyBrowse(@ModelAttribute CustomerRecordFilter filter, HttpServletRequest request) {
		filter.setRequest(request);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		filter.setUserId(user.getId());
		
		QueryResult<CustomerRecord> result = customerInforRecordService.findCustomerRecordByFilter(filter);	
		JRadPagedQueryResult<CustomerRecord> pagedResult = new JRadPagedQueryResult<CustomerRecord>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/customer/customerRecord/record_apply_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);
		return mv;
	}

	/**
	 * 申请借阅
	 * @param customerinfoForm
	 * @param request
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "apply.json")
	@JRadOperation(JRadOperation.CREATE)
	public JRadReturnMap apply(@ModelAttribute CustomerRecordFilter filter, HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			String id = request.getParameter(ID);
			customerInforRecordService.apply(id);
			returnMap.setSuccess(true);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnMap.setSuccess(false);
			returnMap.addGlobalError(CustomerInforConstant.CREATEERROR);
		}
		return returnMap;
	}
	
	/**
	 * 审批页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	
*/
	@ResponseBody
	@RequestMapping(value = "approveBrowse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView approveBrowse(@ModelAttribute CustomerRecordFilter filter, HttpServletRequest request) {
		filter.setRequest(request);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		QueryResult<CustomerRecord> result = customerInforRecordService.findCustomerRecordByFilter(filter);	
		JRadPagedQueryResult<CustomerRecord> pagedResult = new JRadPagedQueryResult<CustomerRecord>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/customer/customerRecord/record_approve_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);
		return mv;
	}
	
	
	/**
	 * 同意借阅
	 * @param customerinfoForm
	 * @param request
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "approve.json")
	@JRadOperation(JRadOperation.CREATE)
	public JRadReturnMap approve(@ModelAttribute CustomerRecordFilter filter, HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			String id = request.getParameter(ID);
			customerInforRecordService.approve(id);
			returnMap.setSuccess(true);
		} catch (Exception e) {
			// TODO: handle exception
			returnMap.setSuccess(false);
			returnMap.addGlobalError(CustomerInforConstant.CREATEERROR);
		}
		return returnMap;
	}
	/**
	 * 确定归还
	 * @param customerinfoForm
	 * @param request
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "approveOut.json")
	@JRadOperation(JRadOperation.CREATE)
	public JRadReturnMap approveOut(@ModelAttribute CustomerRecordFilter filter, HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			String id = request.getParameter(ID);
			customerInforRecordService.approveOut(id);
			returnMap.setSuccess(true);
		} catch (Exception e) {
			// TODO: handle exception
			returnMap.setSuccess(false);
			returnMap.addGlobalError(CustomerInforConstant.CREATEERROR);
		}
		return returnMap;
	}
	
	/**
	 * 编号录入页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	
*/
	@ResponseBody
	@RequestMapping(value = "enterBrowse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView enterBrowse(@ModelAttribute CustomerRecordFilter filter, HttpServletRequest request) {
		filter.setRequest(request);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		QueryResult<CustomerRecord> result = customerInforRecordService.findCustomerRecordEnterByFilter(filter);	
		JRadPagedQueryResult<CustomerRecord> pagedResult = new JRadPagedQueryResult<CustomerRecord>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/customer/customerRecord/record_enter_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);
		return mv;
	}
	
	/**
	 * 保存编号
	 * @param customerinfoForm
	 * @param request
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "enterSave.json")
	@JRadOperation(JRadOperation.CREATE)
	public JRadReturnMap enterSave(@ModelAttribute CustomerRecordFilter filter, HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			String recordNo = request.getParameter("recordNo");
			String appId = request.getParameter(ID);
			String result = customerInforRecordService.enterSave(recordNo, appId);
			if(result==null){
				returnMap.setSuccess(true);
			}else{
				returnMap.setSuccess(false);
				returnMap.addGlobalMessage(result);
			}
		} catch (Exception e) {
			// TODO: handle exception
			returnMap.setSuccess(false);
			returnMap.addGlobalError(CustomerInforConstant.CREATEERROR);
		}
		return returnMap;
	}
	
	/**
	 * 批量导入
	 * @param customerinfoForm
	 * @param request
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "enterFileSave.page")
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView enterFileSave(@ModelAttribute CustomerRecordFilter filter,@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request,HttpServletResponse response) {
		String message = null;
		try {
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			Boolean result = customerInforRecordService.enterFileSave(file,user.getId());
			if(!result){
				message="文件格式不匹配!";
			}else{
				message="批量导入成功!";
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		filter.setRequest(request);
		QueryResult<CustomerRecord> result = customerInforRecordService.findCustomerRecordEnterByFilter(filter);	
		JRadPagedQueryResult<CustomerRecord> pagedResult = new JRadPagedQueryResult<CustomerRecord>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/customer/customerRecord/record_enter_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);
		mv.addObject("importResult", message);
		return mv;
	}
	
	/**
	 * 查看是否7天内未归档
	 * @param customerinfoForm
	 * @param request
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "delay.json")
	@JRadOperation(JRadOperation.CREATE)
	public JRadReturnMap delay(@ModelAttribute CustomerRecordFilter filter, HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			Boolean result = customerInforRecordService.delay(user.getId());
			returnMap.setSuccess(result);
		} catch (Exception e) {
			// TODO: handle exception
			returnMap.setSuccess(false);
			returnMap.addGlobalError(CustomerInforConstant.CREATEERROR);
		}
		return returnMap;
	}
}
