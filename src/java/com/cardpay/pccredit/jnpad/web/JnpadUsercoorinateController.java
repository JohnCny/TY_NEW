package com.cardpay.pccredit.jnpad.web;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.ipad.util.JsonDateValueProcessor;
import com.cardpay.pccredit.jnpad.model.usercoorinate;
import com.cardpay.pccredit.jnpad.service.JnpadUsercoorinateService;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
public class JnpadUsercoorinateController {
	@Autowired
	private JnpadUsercoorinateService uservice;
	
	/**
	 * 当前客户经理发送他的地理位置
	 * @param u
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/addusercoorinate.json")
	public String addusercoorinate(@ModelAttribute usercoorinate u,HttpServletRequest request){
		String cid=null;
		if(null==cid){
			cid=UUID.randomUUID().toString();
		}
		u.setId(cid);
		u.setUserid(request.getParameter("userId"));
		u.setLon(request.getParameter("lon"));
		u.setLat(request.getParameter("lat"));
		u.setCreate_time(new Date());
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		int size=uservice.selectUserByUseridCount(u);
		if(size>=5){
			int b=uservice.updateUser(u);
			if(b>0){
				map.put("message", "位置发送成功!!");
			}else{
				map.put("message", "位置发送失败!!");
			}
		}else{
			int a=uservice.addusercoorinate(u);
			if(a>0){
				map.put("message", "位置发送成功!!");
			}else{
				map.put("message", "位置发送失败!!");
			}
		}
		
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
	/**
	 * 根据userid查看该客户经理的地理位置
	 * @param u
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/selectUserByUserid.json")
	public String selectUserByUserid(@ModelAttribute usercoorinate u,HttpServletRequest request){
		u.setUserid(request.getParameter("userId"));
		List<usercoorinate> result=uservice.selectUserByUserid(u);
		int size=uservice.selectUserByUseridCount(u);
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		map.put("result",result);
		map.put("size",size);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
}
