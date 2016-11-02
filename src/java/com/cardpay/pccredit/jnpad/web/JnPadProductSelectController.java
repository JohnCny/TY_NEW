package com.cardpay.pccredit.jnpad.web;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.ipad.util.JsonDateValueProcessor;
import com.cardpay.pccredit.jnpad.filter.JnpadProductFilter;
import com.cardpay.pccredit.jnpad.model.ProductAttributes;
import com.cardpay.pccredit.jnpad.service.JnPadProductSelectService;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.util.web.RequestHelper;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;


/**
 * 
 * 产品查询
 * 
 */


@Controller
public class JnPadProductSelectController extends BaseController {
	
	@Autowired
	private JnPadProductSelectService productService;
	/**
	 * 根据ID查询
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/product/selectProductById.json", method = { RequestMethod.GET })
	public String selectProductById(HttpServletRequest request){

		String id = RequestHelper.getStringValue(request, "id");
		List<ProductAttributes> products = productService.selectProductById(id);
		
		Map<String,Object> result = new LinkedHashMap<String,Object>();
		result.put("result", products);
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
		
		
	}
	
	
	/**
	 * 
	 * 根据过滤条件查询期限内产品
	 * @param products
	 * @param totalCount
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	
	@ResponseBody
	@RequestMapping(value ="/ipad/product/selectProductByFilter.json",method = RequestMethod.GET )
	public String selectProductByFilter(@ModelAttribute JnpadProductFilter filter, HttpServletRequest request) throws UnsupportedEncodingException{
		filter.setRequest(request);
		
//		filter.setProductName(RequestHelper.getStringValue(request, "productName"));
//		filter.setProductName(request.getParameter("productName"));
//		filter.setStatus(RequestHelper.getStringValue(request, "status"));
//		filter.setType(RequestHelper.getStringValue(request, "type"));
		Map<String,Object> result = new LinkedHashMap<String,Object>();
		
		List<ProductAttributes> products = productService.selectProductByFilter(filter);
		result.put("result", products);
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}
		
}

