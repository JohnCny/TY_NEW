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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.ipad.util.JsonDateValueProcessor;
import com.cardpay.pccredit.product.model.AppendixDict;
import com.cardpay.pccredit.product.model.ProductAttribute;
import com.cardpay.pccredit.product.service.ProductService;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.web.controller.BaseController;

@Controller
public class ProductIpadController extends BaseController{
	
	@Autowired
	private ProductService productService;
	
	/**
	 * 产品属性
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/product/browse.json", method = { RequestMethod.GET })
	public String browse(HttpServletRequest request) {
		String currentPage=request.getParameter("currentPage");
		String pageSize=request.getParameter("pageSize");
		String userId = request.getParameter("userId");
		Map<String,Object> result = new LinkedHashMap<String,Object>();
		int page = 1;
		int limit = 10;
		if(StringUtils.isNotEmpty(currentPage)){
			page = Integer.parseInt(currentPage);
		}
		if(StringUtils.isNotEmpty(pageSize)){
			limit = Integer.parseInt(pageSize);
		}
		List<ProductAttribute> products = productService.findProducts(page,limit,userId);
		int totalCount= 0 ;
		if(StringUtils.isNotEmpty(userId)){
			totalCount=productService.findProductsCount(userId);
		}else{
			totalCount=productService.findProductsCount(null);
		}
		result.put("totalCount", totalCount);
		result.put("result", products);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}
	
	/**
	 * 产品属性
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/product/findAppendixByProductId.json", method = { RequestMethod.GET })
	public String findAppendixByProductId(HttpServletRequest request) {
		String productId = request.getParameter("productId");
		Map<String,Object> result = new LinkedHashMap<String,Object>();
		List<AppendixDict> appendixDicts = productService.findAppendixByProductId(productId);
		result.put("result", appendixDicts);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}
	
	
}
