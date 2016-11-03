package com.cardpay.pccredit.jnpad.web;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.ipad.util.JsonDateValueProcessor;
import com.cardpay.pccredit.jnpad.model.CustomerApprais;
import com.cardpay.pccredit.jnpad.service.JnpadCustomerAppraisService;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;


@Controller
public class JnpadCustomerAppraisController {
	@Autowired 
	private JnpadCustomerAppraisService tycaservice;
	
	/**
	 * 添加当前客户的评估信息
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/addCustomerApprais.json", method = { RequestMethod.GET })
	public String addCustomerApprais(@ModelAttribute CustomerApprais filter, HttpServletRequest request) {
		
		String id=null;
			if(null==id){
				id=UUID.randomUUID().toString();
			}
			filter.setId(id);
			filter.setChinesename(request.getParameter("chinesename"));
			filter.setCardid(request.getParameter("cardid"));
			filter.setAge(request.getParameter("age"));
			filter.setZfqk(request.getParameter("zfqk"));
			filter.setZcqk(request.getParameter("zcqk"));
			filter.setYyqk(request.getParameter("yyqk"));
			filter.setDwxz(request.getParameter("dwxz"));
			filter.setDwgl(request.getParameter("dwgl"));
			filter.setJzsj(request.getParameter("jzsj"));
			filter.setHyzk(request.getParameter("hyzk"));
			filter.setHjzk(request.getParameter("hjzk"));
			filter.setJycd(request.getParameter("jycd"));
			filter.setZgzs(request.getParameter("zgzs"));
			filter.setZc(request.getParameter("zc"));
			filter.setJkqk(request.getParameter("jkqk"));
			filter.setGgjl(request.getParameter("ggjl"));
			filter.setZw(request.getParameter("zw"));
			filter.setGrsr(request.getParameter("grsr"));
			filter.setZwsrb(request.getParameter("zwsrb"));
			filter.setSyrk(request.getParameter("syrk"));
			filter.setTjr(request.getParameter("tjr"));
			filter.setKhjlzgyx(request.getParameter("khjlzgyx"));
			filter.setKhdysr(request.getParameter("khdysr"));
			filter.setCykh(request.getParameter("cykh"));
			filter.setZf(request.getParameter("zf"));
			filter.setJyed(request.getParameter("jyed"));
			filter.setTime(new Date());
			filter.setPfdj(request.getParameter("pfdj"));
			CustomerApprais b=tycaservice.selectCustomerApprais(filter);
			int a=0;
			if(b==null){
				 a=tycaservice.addCustomerApprais(filter);
			}else{
				a=tycaservice.updateCustomerApprais(filter);
			}
			Map<String, Object> map =new LinkedHashMap<String, Object>();
			map.put("a", a);
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
			JSONObject json = JSONObject.fromObject(map, jsonConfig);
			return json.toString();
	}
	
	/**
	 * 查看指定客户的额度评估信息
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/selectAllCustomerApprais.json", method = { RequestMethod.GET })
	public String selectAllCustomerApprais(@ModelAttribute CustomerApprais filter, HttpServletRequest request) {
		filter.setCardid(request.getParameter("cardid"));
		CustomerApprais result =tycaservice.selectAllCustomerApprais(filter);
		Map<String, Object> map =new LinkedHashMap<String, Object>();
		map.put("result", result);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
}
