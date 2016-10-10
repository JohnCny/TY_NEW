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
import com.wicresoft.util.web.RequestHelper;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import sun.misc.BASE64Decoder;

@Controller
public class JnpadInquiryModelController {

	@Autowired
	private AddIntoPiecesService addIntoPiecesService;
	
	
	@ResponseBody
	@RequestMapping(value = "/ipad/product/browerModel.json")
	public String browerModel(HttpServletRequest request) {
		Map<Object,Object> map =new LinkedHashMap<Object, Object>();
		String appId = RequestHelper.getStringValue(request, "appId");
		if (StringUtils.isNotEmpty(appId)) {
			try {
				
			
			LocalExcel localExcel = addIntoPiecesService.findLocalEXcelByApplication(appId);
			String tableContentjyb = getFromBASE64(localExcel.getSheetJy()).replaceAll("\n", "<br>").replace("><br><", "><");
			String tableContentjbzkb = getFromBASE64(localExcel.getSheetJbzk()).replaceAll("\n", "<br>").replace("><br><", "><");
			String tableContentzcfzb = getFromBASE64(localExcel.getSheetFz()).replaceAll("\n", "<br>").replace("><br><", "><");
			String tableContentbzlrb = getFromBASE64(localExcel.getSheetBzlrb()).replaceAll("\n", "<br>").replace("><br><", "><");
			String tableContentxjlb = getFromBASE64(localExcel.getSheetXjllb()).replaceAll("\n", "<br>").replace("><br><", "><");
			String tableContentxjXb = getFromBASE64(localExcel.getSheetJc()).replaceAll("\n", "<br>").replace("><br><", "><");
			String tableContentxgzb = getFromBASE64(localExcel.getSheetGdzc()).replaceAll("\n", "<br>").replace("><br><", "><");
			String tableContentyfysb = getFromBASE64(localExcel.getSheetYfys()).replaceAll("\n", "<br>").replace("><br><", "><");
			String tableContentysyfb = getFromBASE64(localExcel.getSheetYsyf()).replaceAll("\n", "<br>").replace("><br><", "><");
			String tableContentjueyb = getFromBASE64(localExcel.getJyb()).replaceAll("\n", "<br>").replace("><br><", "><");
			
			map.put("tableContentjyb", tableContentjyb);
			map.put("tableContentjbzkb", tableContentjbzkb);
			map.put("tableContentzcfzb", tableContentzcfzb);
			map.put("tableContentbzlrb", tableContentbzlrb);
			map.put("tableContentxjlb", tableContentxjlb);
			map.put("tableContentxjXb", tableContentxjXb);
			map.put("tableContentxgzb", tableContentxgzb);
			map.put("tableContentyfysb", tableContentyfysb);
			map.put("tableContentysyfb", tableContentysyfb);
			map.put("tableContentjueyb", tableContentjueyb);
			} catch (Exception e) {
				map.put("mess", e.getMessage());
			}
		}
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
