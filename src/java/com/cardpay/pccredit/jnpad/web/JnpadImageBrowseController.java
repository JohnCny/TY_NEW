package com.cardpay.pccredit.jnpad.web;

import java.io.File;
import java.io.IOException;
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
import com.jcraft.jsch.SftpException;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
public class JnpadImageBrowseController {
	
	@Autowired
	private JnpadImageBrowseService jnpadImageBrowseService ;
	
	
	/**
	 * stfp分类查询照片
	 * @param request
	 * @return
	 * @throws SftpException 
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/JnpadImageBrowse/StfpfindLocalImageByType.json", method = { RequestMethod.GET })
	public String StfpfindLocalImageByType(HttpServletRequest request) throws IOException, SftpException {
		List<LocalImageForm> imagerList = jnpadImageBrowseService.findLocalImageByType1(request.getParameter("customerId"),request.getParameter("productId")
				,request.getParameter("phone_type"));
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		map.put("imagerList",imagerList);
		map.put("size",imagerList.size());
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
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
	/**
	 * 分类查询照片
	 * @param request
	 * @return
	 * @throws SftpException 
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/JnpadImageBrowse/findLocalImageByType.json", method = { RequestMethod.GET })
	public String findLocalImageByType(HttpServletRequest request) throws IOException, SftpException {
		List<LocalImageForm> imagerList = jnpadImageBrowseService.findLocalImageByType1(request.getParameter("customerId"),request.getParameter("productId")
				,request.getParameter("phone_type"));
		
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		map.put("imagerList",imagerList);
		map.put("size",imagerList.size());
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
	
	
	/**
	 * base64分类查询照片
	 * @param request
	 * @return
	 * @throws SftpException 
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/JnpadImageBrowse/findLocalImageByType2.json", method = { RequestMethod.GET })
	public String findLocalImageByType2(HttpServletRequest request) throws IOException, SftpException {
		List<LocalImageForm> imagerList = jnpadImageBrowseService.findLocalImageByType(request.getParameter("customerId"),request.getParameter("productId")
				,request.getParameter("phone_type"));
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		map.put("result",imagerList);
		map.put("size",imagerList.size());
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
	/**
	 * 分类查询确认照片
	 * @param request
	 * @return
	 * @throws SftpException 
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/JnpadImageBrowse/findLocalImageByType1.json", method = { RequestMethod.GET })
	public String findLocalImageByType1(HttpServletRequest request) throws IOException, SftpException {
		List<LocalImageForm> imagerList1 = jnpadImageBrowseService.findLocalImageByType1(request.getParameter("customerId"),request.getParameter("productId")
				,"1");
		List<LocalImageForm> imagerList2 = jnpadImageBrowseService.findLocalImageByType1(request.getParameter("customerId"),request.getParameter("productId")
				,"2");
		List<LocalImageForm> imagerList3 = jnpadImageBrowseService.findLocalImageByType1(request.getParameter("customerId"),request.getParameter("productId")
				,"3");
		List<LocalImageForm> imagerList4 = jnpadImageBrowseService.findLocalImageByType1(request.getParameter("customerId"),request.getParameter("productId")
				,"4");
		List<LocalImageForm> imagerList5 = jnpadImageBrowseService.findLocalImageByType1(request.getParameter("customerId"),request.getParameter("productId")
				,"5");
		List<LocalImageForm> imagerList6 = jnpadImageBrowseService.findLocalImageByType1(request.getParameter("customerId"),request.getParameter("productId")
				,"6");
		List<LocalImageForm> imagerList7 = jnpadImageBrowseService.findLocalImageByType1(request.getParameter("customerId"),request.getParameter("productId")
				,"7");
		List<LocalImageForm> imagerList8 = jnpadImageBrowseService.findLocalImageByType1(request.getParameter("customerId"),request.getParameter("productId")
				,"8");
		List<LocalImageForm> imagerList9 = jnpadImageBrowseService.findLocalImageByType1(request.getParameter("customerId"),request.getParameter("productId")
				,"9");
		List<LocalImageForm> imagerList10 = jnpadImageBrowseService.findLocalImageByType1(request.getParameter("customerId"),request.getParameter("productId")
				,"10");
		
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		map.put("size1",imagerList1.size());
		map.put("size2",imagerList2.size());
		map.put("size3",imagerList3.size());
		map.put("size4",imagerList4.size());
		map.put("size5",imagerList5.size());
		map.put("size6",imagerList6.size());
		map.put("size7",imagerList7.size());
		map.put("size8",imagerList8.size());
		map.put("size9",imagerList9.size());
		map.put("size10",imagerList10.size());
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
	
	@ResponseBody
	@RequestMapping(value = "/ipad/JnpadImageBrowse/downLoadYxzlJn.json",method = { RequestMethod.GET })
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
