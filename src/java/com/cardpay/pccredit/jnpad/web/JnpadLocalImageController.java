package com.cardpay.pccredit.jnpad.web;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cardpay.pccredit.common.UploadFileTool;
import com.cardpay.pccredit.customer.constant.CustomerInforConstant;
import com.cardpay.pccredit.intopieces.model.LocalExcel;
import com.cardpay.pccredit.intopieces.model.LocalImage;
import com.cardpay.pccredit.intopieces.service.AddIntoPiecesService;
import com.cardpay.pccredit.intopieces.web.AddIntoPiecesForm;
import com.cardpay.pccredit.intopieces.web.LocalImageForm;
import com.cardpay.pccredit.ipad.util.JsonDateValueProcessor;
import com.cardpay.pccredit.jnpad.service.JnpadAddIntoPiecesService;
import com.cardpay.pccredit.jnpad.service.JnpadLocalImageService;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.constant.JRadConstants;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.util.spring.Beans;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
@Controller
public class JnpadLocalImageController {
	@Autowired
	private CommonDao commonDao;
	@Autowired
	private JnpadLocalImageService addIntoPiecesService;
	@Autowired
	private AddIntoPiecesService addservice;
	@Autowired
	private JnpadAddIntoPiecesService jnpadaddIntoPiecesService;
	/**
	 * 上传影像资料
	 * @param file
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/TyaddIntopieces/imageImport.json")
	public Map<String, Object> imageImport(@RequestParam(value = "file", required = false) MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws Exception {        
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(file==null||file.isEmpty()){
				map.put(JRadConstants.SUCCESS, false);
				map.put(JRadConstants.MESSAGE, CustomerInforConstant.IMPORTEMPTY);
				return map;
			}
			String fileName =request.getParameter("fileName");
			String productId = request.getParameter("productId");
			String customerId = request.getParameter("customerId");
			String applicationId = request.getParameter("applicationId");
			addIntoPiecesService.importImage(file,productId,customerId,applicationId,fileName);
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
	
	//提交融耀卡申请
	@ResponseBody
	@RequestMapping(value = "/ipad/jnnaddIntopieces/imageImport.json", method = { RequestMethod.GET })
	public String addIntopieces(@ModelAttribute AddIntoPiecesForm addIntoPiecesForm,HttpServletRequest request) {
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		String userId=request.getParameter("userId");
		addIntoPiecesForm.setCustomerId(request.getParameter("customerId"));
		addIntoPiecesForm.setProductId(request.getParameter("productId"));
		addIntoPiecesService.addIntopieces(addIntoPiecesForm,userId);
		map.put("message", "进件上传成功");
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
	
	//提申请
	@ResponseBody
	@RequestMapping(value = "/ipad/jnnaddIntopieces/tjsq.json", method = { RequestMethod.GET })
	public String tjsq(@ModelAttribute AddIntoPiecesForm addIntoPiecesForm,HttpServletRequest request) {
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		String userId=request.getParameter("userId");
		addIntoPiecesForm.setCustomerId(request.getParameter("customerId"));
		addIntoPiecesForm.setProductId(request.getParameter("productId"));
		String customerId=request.getParameter("customerId");
		String productId=request.getParameter("productId");
		LocalImageForm addinform=jnpadaddIntoPiecesService.findLocalId(customerId, productId);
		addIntoPiecesForm.setExcelId(addinform.getId());
		addservice.addIntopieces(addIntoPiecesForm,userId);
		map.put("message", "进件上传成功");
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
}

