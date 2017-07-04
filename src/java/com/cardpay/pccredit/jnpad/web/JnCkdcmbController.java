package com.cardpay.pccredit.jnpad.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.model.PgUser;
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
		String tableContentyfysb=null;
		String tableContentxjlb =null;
		String tableContentxgzb =null;
		String tableContentjyb =null;
		String tableContentjbzkb =null;
		String tableContentdhd =null;
		String tableContentysyfb=null;
		String Zyxzcb =null;
		String Tzxzcb =null;
		String Fzjsb =null;
			LocalExcel localExcel = LocalExcelService.findByApplication(customerId,productId);
			if(localExcel.getSheetYfys()!="" && localExcel.getSheetYfys()!=null){
				tableContentyfysb = getFromBASE64(localExcel.getSheetYfys()).replaceAll("\n", "<br>").replace("><br><", "><");
			}
			if(localExcel.getSheetYsyf()!="" && localExcel.getSheetYsyf()!=null){
				tableContentysyfb = getFromBASE64(localExcel.getSheetYsyf()).replaceAll("\n", "<br>").replace("><br><", "><");
			}
			if(localExcel.getSheetXjllb()=="" || localExcel.getSheetXjllb()==null){
				 tableContentxjlb = getFromBASE64(localExcel.getHkjhb()).replaceAll("\n", "<br>").replace("><br><", "><");
				 
			}else{
				 tableContentxjlb = getFromBASE64(localExcel.getSheetXjllb()).replaceAll("\n", "<br>").replace("><br><", "><");
			}
			if(localExcel.getSheetGdzc()!="" && localExcel.getSheetGdzc()!=null){
				tableContentxgzb = getFromBASE64(localExcel.getSheetGdzc()).replaceAll("\n", "<br>").replace("><br><", "><");
			}
			if(localExcel.getSheetJy()!="" && localExcel.getSheetJy()!=null){
				tableContentjyb = getFromBASE64(localExcel.getSheetJy()).replaceAll("\n", "<br>").replace("><br><", "><");
			}
			
			if(localExcel.getSheetJbzk()!="" && localExcel.getSheetJbzk()!=null){
				tableContentjbzkb = getFromBASE64(localExcel.getSheetJbzk()).replaceAll("\n", "<br>").replace("><br><", "><");
			}
			
			if(localExcel.getSheetDhd()!="" && localExcel.getSheetDhd()!=null){
				tableContentdhd = getFromBASE64(localExcel.getSheetDhd()).replaceAll("\n", "<br>").replace("><br><", "><");
			}
			
			
			if(localExcel.getZyxzcb()!="" && localExcel.getZyxzcb()!=null){
				Zyxzcb = getFromBASE64(localExcel.getZyxzcb()).replaceAll("\n", "<br>").replace("><br><", "><");
			}
			
			if(localExcel.getTzxzcb()!="" && localExcel.getTzxzcb()!=null){
				Tzxzcb = getFromBASE64(localExcel.getTzxzcb()).replaceAll("\n", "<br>").replace("><br><", "><");
			}
			
			if(localExcel.getFzjsb()!="" && localExcel.getFzjsb()!=null){
				Fzjsb = getFromBASE64(localExcel.getFzjsb()).replaceAll("\n", "<br>").replace("><br><", "><");
			}
			
			
			
			map.put("Fzjsb", Fzjsb);
			map.put("Tzxzcb", Tzxzcb);
			map.put("Zyxzcb", Zyxzcb);
			
			
			map.put("tableContentxjlb", tableContentxjlb);
			map.put("tableContentxgzb", tableContentxgzb);
			map.put("tableContentyfysb", tableContentyfysb);
			map.put("tableContentysyfb", tableContentysyfb);
			map.put("tableContentjyb", tableContentjyb);
			map.put("tableContentjbzkb", tableContentjbzkb);
			map.put("tableContentdhd", tableContentdhd);
			if(tableContentyfysb==null){
				map.put("result", "0");	
			}else{
				map.put("result", "1");	
			}
			
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
	
	
   	
   	
   	
   	
   	/**
   	 * 查询单个客户经理提交的表格
   	 * @param request
   	 * @return
   	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/user/cktjhpb.json")
	public String cktjhpb(HttpServletRequest request) {
		Map<Object,Object> map =new LinkedHashMap<Object, Object>();
		String id = request.getParameter("userId");
		String tablejcb=null;
		String tablezxb=null;
		String tablezsb=null;
		String tabletjb =null;
		String tabletjb1 =null;
		String tabletjb2 =null;
		String tabletjb3 =null;
		String tabletjb4 =null;
		String tabletjb5 =null;
		String tabletjb6 =null;
		String tabletjb7 =null;
		String tabletjb8 =null;
		String tabletjb9 =null;
		List<PgUser> PgUser = LocalExcelService.selectPgUser(id,"");
		if(PgUser.get(0).getJc()!="" && PgUser.get(0).getJc()!=null){
			tablejcb=getFromBASE64(PgUser.get(0).getJc()).replaceAll("\n", "<br>").replace("><br><", "><");
		}
		if(PgUser.get(0).getZcs()!="" && PgUser.get(0).getZcs()!=null){
			tablezsb=getFromBASE64(PgUser.get(0).getZcs()).replaceAll("\n", "<br>").replace("><br><", "><");
		}
		if(PgUser.get(0).getZcx()!="" && PgUser.get(0).getZcx()!=null){
			tablezxb=getFromBASE64(PgUser.get(0).getZcx()).replaceAll("\n", "<br>").replace("><br><", "><");
		}
		if(PgUser.get(0).getTj()!="" && PgUser.get(0).getTj()!=null){
			tabletjb=getFromBASE64(PgUser.get(0).getTj()).replaceAll("\n", "<br>").replace("><br><", "><");
		}	
		
		if(PgUser.get(0).getTj2()!="" && PgUser.get(0).getTj2()!=null){
			tabletjb1=getFromBASE64(PgUser.get(0).getTj2()).replaceAll("\n", "<br>").replace("><br><", "><");
		}	
		if(PgUser.get(0).getTj3()!="" && PgUser.get(0).getTj3()!=null){
			tabletjb2=getFromBASE64(PgUser.get(0).getTj3()).replaceAll("\n", "<br>").replace("><br><", "><");
		}	
		if(PgUser.get(0).getTj4()!="" && PgUser.get(0).getTj4()!=null){
			tabletjb3=getFromBASE64(PgUser.get(0).getTj4()).replaceAll("\n", "<br>").replace("><br><", "><");
		}	
		
		if(PgUser.get(0).getTj5()!="" && PgUser.get(0).getTj5()!=null){
			tabletjb4=getFromBASE64(PgUser.get(0).getTj5()).replaceAll("\n", "<br>").replace("><br><", "><");
		}	
		if(PgUser.get(0).getTj6()!="" && PgUser.get(0).getTj6()!=null){
			tabletjb5=getFromBASE64(PgUser.get(0).getTj6()).replaceAll("\n", "<br>").replace("><br><", "><");
		}	
		if(PgUser.get(0).getTj7()!="" && PgUser.get(0).getTj7()!=null){
			tabletjb6=getFromBASE64(PgUser.get(0).getTj7()).replaceAll("\n", "<br>").replace("><br><", "><");
		}	
		if(PgUser.get(0).getTj8()!="" && PgUser.get(0).getTj8()!=null){
			tabletjb7=getFromBASE64(PgUser.get(0).getTj8()).replaceAll("\n", "<br>").replace("><br><", "><");
		}
		if(PgUser.get(0).getTj9()!="" && PgUser.get(0).getTj9()!=null){
			tabletjb8=getFromBASE64(PgUser.get(0).getTj9()).replaceAll("\n", "<br>").replace("><br><", "><");
		}	
		if(PgUser.get(0).getTj10()!="" && PgUser.get(0).getTj10()!=null){
			tabletjb9=getFromBASE64(PgUser.get(0).getTj10()).replaceAll("\n", "<br>").replace("><br><", "><");
		}	
			map.put("tablejcb", tablejcb);
			map.put("tablezxb", tablezxb);
			map.put("tablezsb", tablezsb);
			map.put("tabletjb", tabletjb);
			map.put("tabletjb1", tabletjb1);
			map.put("tabletjb2", tabletjb2);
			map.put("tabletjb3", tabletjb3);
			map.put("tabletjb4", tabletjb4);
			map.put("tabletjb5", tabletjb5);
			map.put("tabletjb6", tabletjb6);
			map.put("tabletjb7", tabletjb7);
			map.put("tabletjb8", tabletjb8);
			map.put("tabletjb9", tabletjb9);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
	
   	
   	/**
   	 * 区域经理查看
   	 * @param request
   	 * @return
   	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/user/qyjlck.json")
	public String qyjlck(HttpServletRequest request) {
		String userId=request.getParameter("userId");
		String name="";
		if(request.getParameter("name")!=null && request.getParameter("name")!=""){
			name="%"+request.getParameter("name")+"%";
		}
		String id="";
		String userId1="";
		String pid="";
		List<PgUser> PgUser=new ArrayList<PgUser>();
		List<CustomerInfor>result=LocalExcelService.selectTeamOrg(id, userId);
		pid=result.get(0).getOid();
		List<CustomerInfor>result1=LocalExcelService.selectTeamOrg(pid, userId1);
		Integer a=0;
		for(int i=result1.size()-1;i>=0;i--){
			if(i!=result1.size()-1){
				a+=1;
			}
			List<PgUser> result2 = LocalExcelService.selectPgUser(result1.get(i).getUserId(),name);
			if(result2.size()>0){
				result2.get(0).setName(result1.get(i).getChineseName());
				result2.get(0).setTeamname(result1.get(i).getTeamname());
				PgUser.add(a, result2.get(0));
			}else{
				result1.remove(i);
			}
		}
		Map<Object,Object> map =new LinkedHashMap<Object, Object>();
		map.put("PgUser", PgUser);
		map.put("size", PgUser.size());
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
