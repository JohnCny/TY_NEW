package com.cardpay.pccredit.report.web;


import com.cardpay.pccredit.customer.constant.CustomerInforConstant;
import com.cardpay.pccredit.jnpad.model.CustomerInfo;
import com.cardpay.pccredit.report.model.CustomerHmd;
import com.cardpay.pccredit.report.service.InputHmdService;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.constant.JRadConstants;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;

import java.io.FileInputStream;  
import java.io.FileNotFoundException;   
import java.io.IOException;   
  
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;  
import org.apache.poi.ss.usermodel.Row;  
import org.apache.poi.ss.usermodel.Sheet;  
import org.apache.poi.ss.usermodel.Workbook;  
import org.apache.poi.xssf.usermodel.XSSFWorkbook;  
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
@Controller
@RequestMapping("/report/inputhmdcomtroller/*")
@JRadModule("report.inputhmdcomtroller")
public class InputHmdController extends BaseController  {
	@Autowired 
	private InputHmdService hmdService;
	
	/**
	 * 
	 * 黑名单导入显示
	 * @param filter
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@ResponseBody
	@RequestMapping(value = "hmdbrowse.page", method = {RequestMethod.GET})
	public AbstractModelAndView queryHmd(@ModelAttribute  CustomerHmd filter,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
		filter.setRequest(request);
		response.setContentType("text/html;charset=utf-8");
		String name=request.getParameter("name");
		if(null!=name&&""!=name){
			filter.setName(name);
			QueryResult<CustomerHmd> result = hmdService.queryAll(filter);
			JRadPagedQueryResult<CustomerHmd> pagedResult = new JRadPagedQueryResult<CustomerHmd>(filter, result);
			JRadModelAndView mv = new JRadModelAndView("/riskcontrol/riskCustomer/hmdInput_browse", request);
			mv.addObject(PAGED_RESULT, pagedResult);
			return mv;
		}else{
			QueryResult<CustomerHmd> result = hmdService.queryAll(filter);
			JRadPagedQueryResult<CustomerHmd> pagedResult = new JRadPagedQueryResult<CustomerHmd>(filter, result);
			JRadModelAndView mv = new JRadModelAndView("/riskcontrol/riskCustomer/hmdInput_browse", request);
			mv.addObject(PAGED_RESULT, pagedResult);
			return mv;
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "inputHmd.json")
	public Map<String, Object> reportImport_json(@RequestParam(value = "file", required = false) MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws Exception {        
		response.setContentType("text/html;charset=utf-8");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(file==null||file.isEmpty()){
				map.put(JRadConstants.SUCCESS, false);
				map.put(JRadConstants.MESSAGE, CustomerInforConstant.IMPORTEMPTY);
				return map;
			}
			hmdService.importhmd(file);
			map.put(JRadConstants.SUCCESS, true);
			map.put(JRadConstants.MESSAGE, "导入成功");
			JSONObject obj = JSONObject.fromObject(map);
			response.getWriter().print(obj.toString());
		} catch (Exception e) {
			e.printStackTrace();
			map.put(JRadConstants.SUCCESS, false);
			map.put(JRadConstants.MESSAGE, "导入失败:"+e.getMessage());
			JSONObject obj = JSONObject.fromObject(map);
			response.getWriter().print(obj.toString());
		}
		return null;
	}
	
	}  

