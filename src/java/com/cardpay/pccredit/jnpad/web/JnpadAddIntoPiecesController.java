package com.cardpay.pccredit.jnpad.web;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cardpay.pccredit.customer.constant.CustomerInforConstant;
import com.cardpay.pccredit.customer.filter.CustomerInforFilter;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.intopieces.service.AddIntoPiecesService;
import com.cardpay.pccredit.ipad.util.JsonDateValueProcessor;
import com.cardpay.pccredit.jnpad.service.JnpadAddIntoPiecesService;
import com.cardpay.pccredit.manager.filter.AccountManagerParameterFilter;
import com.cardpay.pccredit.manager.service.AccountManagerParameterService;
import com.cardpay.pccredit.manager.web.AccountManagerParameterForm;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.constant.JRadConstants;
import com.wicresoft.jrad.base.database.model.QueryResult;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;


@Controller
public class JnpadAddIntoPiecesController {
	@Autowired
	private AccountManagerParameterService accountManagerParameterService;
	@Autowired
	private CustomerInforService customerInforservice;
	
	@Autowired
	private AddIntoPiecesService addIntoPiecesService;
	
	@Autowired
	private JnpadAddIntoPiecesService jnpadaddIntoPiecesService;
	
	//选择客户
	@ResponseBody
	@RequestMapping(value = "/ipad/addIntoPieces/browseCustomer.json", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public String browseCustomer(@ModelAttribute CustomerInforFilter filter,HttpServletRequest request) {
      
		filter.setUserId(request.getParameter("userId"));
		filter.setProductId(request.getParameter("productId"));
		QueryResult<CustomerInfor> result = customerInforservice.findCustomerInforByFilterAndProductId(filter);
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}
	
	//上传影像资料
		@ResponseBody
		@RequestMapping(value = "/ipad/addIntopieces/imageImport.json")
		public Map<String, Object> imageImport(@RequestParam(value = "file", required = false) MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws Exception {        
			response.setContentType("text/html;charset=utf-8");
			Map<String, Object> map = new HashMap<String, Object>();
			try {
				if(file==null||file.isEmpty()){
					map.put(JRadConstants.SUCCESS, false);
					map.put(JRadConstants.MESSAGE, CustomerInforConstant.IMPORTEMPTY);
					JSONObject obj = JSONObject.fromObject(map);
					response.getWriter().print(obj.toString());
				}
				String fileName =request.getParameter("fileName");
				String productId = request.getParameter("productId");
				String customerId = request.getParameter("customerId");
				String applicationId = request.getParameter("applicationId");
				jnpadaddIntoPiecesService.importImage(file,productId,customerId,applicationId,fileName);
				map.put(JRadConstants.SUCCESS, true);
				map.put(JRadConstants.MESSAGE, CustomerInforConstant.IMPORTSUCCESS);
				JSONObject obj = JSONObject.fromObject(map);
				response.getWriter().print(obj.toString());
			} catch (Exception e) {
				e.printStackTrace();
				map.put(JRadConstants.SUCCESS, false);
				map.put(JRadConstants.MESSAGE, "上传失败:"+e.getMessage());
				JSONObject obj = JSONObject.fromObject(map);
				response.getWriter().print(obj.toString());
			}
			return null;
		}
		
		
		//导入调查报告
		@ResponseBody
		@RequestMapping(value = "/ipad/addIntopieces/reportImport.json")
		public Map<String, Object> reportImport_json(@RequestParam(value = "file", required = false) MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws Exception {        
			response.setContentType("text/html;charset=utf-8");
			Map<String, Object> map = new HashMap<String, Object>();
			try {
				if(file==null||file.isEmpty()){
					map.put(JRadConstants.SUCCESS, false);
					map.put(JRadConstants.MESSAGE, CustomerInforConstant.IMPORTEMPTY);
					JSONObject obj = JSONObject.fromObject(map);
					response.getWriter().print(obj.toString());
				}
				String fileName =request.getParameter("fileName");
				String productId = request.getParameter("productId");
				String customerId = request.getParameter("customerId");
				jnpadaddIntoPiecesService.importExcel(file,productId,customerId,fileName);
				map.put(JRadConstants.SUCCESS, true);
				map.put(JRadConstants.MESSAGE, CustomerInforConstant.IMPORTSUCCESS);
				JSONObject obj = JSONObject.fromObject(map);
				response.getWriter().print(obj.toString());
			} catch (Exception e) {
				e.printStackTrace();
				map.put(JRadConstants.SUCCESS, false);
				map.put(JRadConstants.MESSAGE, "上传失败:"+e.getMessage());
				JSONObject obj = JSONObject.fromObject(map);
				response.getWriter().print(obj.toString());
			}
			return null;
		}
		/**
		 * 查看所有客户经理信息
		 * @param filter
		 * @param request
		 * @return
		 */
		@ResponseBody
		@RequestMapping(value = "/ipad/custAppInfo/TySelectAll.json", method = { RequestMethod.GET })
		public String TySelectAll(@ModelAttribute AccountManagerParameterFilter filter,HttpServletRequest request) {
			List<AccountManagerParameterForm> result = accountManagerParameterService.findAccountManagerParametersByAllUserId(filter);
			int size=accountManagerParameterService.findAccountManagerParametersCountByFilter(filter);
			Map<String,Object> query = new LinkedHashMap<String,Object>();
			query.put("result", result);
			query.put("size", size);
		 	JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
			JSONObject json = JSONObject.fromObject(query, jsonConfig);
			return json.toString();	    
				}

		
}
