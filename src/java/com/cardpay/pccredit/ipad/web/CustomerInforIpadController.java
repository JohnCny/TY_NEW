/**
 * 
 */
package com.cardpay.pccredit.ipad.web;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.web.CustomerInforForm;
import com.cardpay.pccredit.intopieces.model.Dzjy;
import com.cardpay.pccredit.ipad.constant.IpadConstant;
import com.cardpay.pccredit.ipad.model.CustomerInforIpad;
import com.cardpay.pccredit.ipad.model.Result;
import com.cardpay.pccredit.ipad.service.CustomerInforForIpadService;
import com.cardpay.pccredit.ipad.util.JsonDateValueProcessor;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.web.controller.BaseController;

/**
 * @author shaoming
 *
 * 2014年11月27日   下午4:14:28
 */
@Controller
public class CustomerInforIpadController extends BaseController{

	@Autowired
	private CustomerInforForIpadService customerInforService;

	@ResponseBody
	@RequestMapping(value = "/ipad/customerInfor/insert.json")
	@JRadOperation(JRadOperation.CREATE)
	public String insert(@ModelAttribute CustomerInforForm customerinfoForm, HttpServletRequest request) {
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		String name = request.getParameter("name");
		String cardId = request.getParameter("cardId");
		String userId = request.getParameter("userId");
		map = customerInforService.addCustomer(name,cardId,null,userId);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
	/**
	 * 提供userId得到该客户经理名下所有客户接口
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/customerInfor/getCustomer.json")
	@JRadOperation(JRadOperation.CREATE)
	public String getCustomerInforByUserId(HttpServletRequest request) {
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		String userId = request.getParameter("userId");
		if(StringUtils.isNotEmpty(userId)){
			String currentPage=request.getParameter("currentPage");
			String pageSize=request.getParameter("pageSize");
			int page = 1;
			int limit = 10;
			if(StringUtils.isNotEmpty(currentPage)){
				page = Integer.parseInt(currentPage);
			}
			if(StringUtils.isNotEmpty(pageSize)){
				limit = Integer.parseInt(pageSize);
			}
			try{
				List<CustomerInforIpad> customerInfors = customerInforService.getCustomerInforByUserId(userId,page,limit);
				int totalCount = customerInforService.getCustomerInforCountByUserId(userId);
				map.put("totalCount", totalCount);
				map.put("result",customerInfors);	
			}catch(Exception e){
				Result result = new Result();
				result.setReason(IpadConstant.SYSTEMERROR);
				result.setStatus(IpadConstant.FAIL);
				map.put("result", result);
			}
		}
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();

	}
	
	/*
	 * 个人信息（新增）
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/customerInfor/grxx_add.json")
	@JRadOperation(JRadOperation.CREATE)
	public String grxx( HttpServletRequest request) {
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		String customerId = request.getParameter("customerId");
		String prodectId = request.getParameter("prodectId");
		String sqr_sex = request.getParameter("sqr_sex");
		String sqr_hy = request.getParameter("sqr_hy");
		String sqr_hjd = request.getParameter("sqr_hjd");
		String sqr_hjxx = request.getParameter("sqr_hjxx");
		String sqr_xl = request.getParameter("sqr_xl");
		String sqr_mobile = request.getParameter("sqr_mobile");
		Dzjy dzjy = new Dzjy();
		dzjy.setCustomer_id(customerId);
		dzjy.setProduct_id(prodectId);
		dzjy.setSqr_sex(sqr_sex);
		dzjy.setSqr_hy(sqr_hy);
		dzjy.setSqr_hjd(sqr_hjd);
		dzjy.setSqr_hjxx(sqr_hjxx);
		dzjy.setSqr_xl(sqr_xl);
		dzjy.setSqr_mobile(sqr_mobile);
		Boolean result = customerInforService.addGrxx(dzjy);
		map.put("result", result);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
	
	/*
	 * 个人信息（查询）
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/customerInfor/grxx_query.json")
	@JRadOperation(JRadOperation.CREATE)
	public String grxx_query( HttpServletRequest request) {
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		String customerId = request.getParameter("customerId");
		String prodectId = request.getParameter("prodectId");
		Dzjy result = customerInforService.queryGrxx(customerId,prodectId);
		map.put("result", result);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
}
