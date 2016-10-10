package com.cardpay.pccredit.jnpad.web;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.intopieces.service.AddIntoPiecesService;
import com.cardpay.pccredit.intopieces.web.LocalImageForm;
import com.cardpay.pccredit.ipad.util.JsonDateValueProcessor;
import com.cardpay.pccredit.jnpad.service.JnpadImageBrowseService;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
public class JnpadImageBrowseController {
	
	@Autowired
	private JnpadImageBrowseService jnpadImageBrowseService ;
	
	
	
	@ResponseBody
	@RequestMapping(value = "/ipad/JnpadImageBrowse/uploadYx.json", method = { RequestMethod.GET })
	public String display_server(HttpServletRequest request) {
		
		List<LocalImageForm> imagerList = jnpadImageBrowseService.findLocalImage(request.getParameter("customerId"),request.getParameter("productId"));
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		map.put("imagerList",imagerList);
		map.put("size",imagerList.size());
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/ipad/JnpadImageBrowse/downLoadYxzlJn.json",method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.EXPORT)
	public AbstractModelAndView downLoadYxzlJn(HttpServletRequest request,HttpServletResponse response){
		try {
			String s =request.getParameter("id");
			
			jnpadImageBrowseService.downLoadYxzlJn(response,s);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	//删除已上传图片
	@ResponseBody
	@RequestMapping(value = "/ipad/JnpadImageBrowse/deleteImage.json", method = { RequestMethod.GET })
	public String deleteImage(HttpServletRequest request) {
		String imageId =request.getParameter("imageId");
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		try {
		jnpadImageBrowseService.deleteImage(imageId);
		map.put("mess", "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("mess", "删除失败");
		}
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
}
