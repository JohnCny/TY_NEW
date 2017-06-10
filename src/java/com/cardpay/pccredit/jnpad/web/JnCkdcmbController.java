package com.cardpay.pccredit.jnpad.web;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.intopieces.model.LocalExcel;
import com.cardpay.pccredit.intopieces.service.AddIntoPiecesService;
import com.cardpay.pccredit.ipad.util.JsonDateValueProcessor;
import com.cardpay.pccredit.jnpad.service.JnIpadLocalExcelService;
import com.wicresoft.util.web.RequestHelper;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import sun.misc.BASE64Decoder;
@Controller
public class JnCkdcmbController {
	@Autowired
	private AddIntoPiecesService addIntoPiecesService;
	@Autowired
	private JnIpadLocalExcelService LocalExcelService;
	
	
	@ResponseBody
	@RequestMapping(value = "/ipad/user/ckdcmb.json")
	public String browerModel(HttpServletRequest request) {
		Map<Object,Object> map =new LinkedHashMap<Object, Object>();
		String customerId = request.getParameter("customerId");
		String productId = request.getParameter("productId");
		String tableContentxjlb =null;
			LocalExcel localExcel = LocalExcelService.findByApplication(customerId,productId);
			String tableContentyfysb = getFromBASE64(localExcel.getSheetYfys()).replaceAll("\n", "<br>").replace("><br><", "><");
			String tableContentysyfb = getFromBASE64(localExcel.getSheetYsyf()).replaceAll("\n", "<br>").replace("><br><", "><");
			if(localExcel.getSheetXjllb()=="" || localExcel.getSheetXjllb()==null){
				 tableContentxjlb = getFromBASE64(localExcel.getHkjhb()).replaceAll("\n", "<br>").replace("><br><", "><");
				 
			}else{
				 tableContentxjlb = getFromBASE64(localExcel.getSheetXjllb()).replaceAll("\n", "<br>").replace("><br><", "><");
			}
			String tableContentxgzb = getFromBASE64(localExcel.getSheetGdzc()).replaceAll("\n", "<br>").replace("><br><", "><");
			String tableContentjyb=getFromBASE64(localExcel.getSheetJy()).replaceAll("\n", "<br>").replace("><br><", "><");
			String tableContentjbzkb=getFromBASE64(localExcel.getSheetJbzk()).replaceAll("\n", "<br>").replace("><br><", "><");
			String tableContentdhd=getFromBASE64(localExcel.getSheetDhd()).replaceAll("\n", "<br>").replace("><br><", "><");
			map.put("tableContentxjlb", tableContentxjlb);
			map.put("tableContentxgzb", tableContentxgzb);
			map.put("tableContentyfysb", tableContentyfysb);
			map.put("tableContentysyfb", tableContentysyfb);
			map.put("tableContentjyb", tableContentjyb);
			map.put("tableContentjbzkb", tableContentjbzkb);
			map.put("tableContentdhd", tableContentdhd);
			
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
	
	
	//base64解码
	public static String getFromBASE64(String s) { 
    	if (s == null) return null; 
    	BASE64Decoder decoder = new BASE64Decoder(); 
    	try { 
    	byte[] b = decoder.decodeBuffer(s); 
    	return new String(b); 
    	} catch (Exception e) { 
    	return null; 
    	} 
	} 

}
