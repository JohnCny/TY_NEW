package com.cardpay.pccredit.jnpad.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.ipad.util.JsonDateValueProcessor;
import com.cardpay.pccredit.jnpad.model.LocationInfoForm;
import com.cardpay.pccredit.jnpad.model.ManagerInfoForm;
import com.cardpay.pccredit.jnpad.service.JnpadLocationInfoService;
import com.wicresoft.util.format.DateFormat;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 
 * 客户经理位置信息
 * @author sealy
 *
 */
@Controller
public class JnpadLocationInfoController {

	@Autowired
	private JnpadLocationInfoService jnpadLocationInfoService;


	/**
	 * 
	 * 更新客户经理位置信息
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "/ipad/intopieces/updateLocation.json", method = { RequestMethod.GET })
	public String updateLocation(HttpServletRequest request){

		Map<String, Object> map = new LinkedHashMap<String, Object>();
		LocationInfoForm locationInfoForm =new LocationInfoForm();
		//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//		String date =sdf.format(new Date());
		locationInfoForm.setUpdateTime(new Date());
		locationInfoForm.setLatitude(request.getParameter("lat"));
		locationInfoForm.setLongitude(request.getParameter("lon"));
		String managerId = request.getParameter("userId");
		locationInfoForm .setUserId(managerId);
		//查询表中是否有该客户经理的信息
		try {
			int num = jnpadLocationInfoService.managerCount(managerId);

			if(num!=0){
				LocationInfoForm lastLocationInfoForm = jnpadLocationInfoService.selectlastManagerLocationById(managerId);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String date1 =sdf.format(new Date());
				String date2 = sdf.format(lastLocationInfoForm.getUpdateTime());
				Date currentDate = sdf.parse(date1);
				Date lastDate = sdf.parse(date2);
				long minutes = (currentDate.getTime()-lastDate.getTime())/(60*1000);
				if(minutes<60){
					
					map.put("message","更新位置信息间隔不能小于一小时");
					JsonConfig jsonConfig = new JsonConfig();
					jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
					JSONObject json = JSONObject.fromObject(map, jsonConfig);
					return json.toString();
				}
				}
			if(num!=5){
				//如果位置信息表中没客户经理信息  插入

				ManagerInfoForm managerInfoForm = jnpadLocationInfoService.selectManagerInforById(managerId);
				locationInfoForm.setUserName(managerInfoForm.getDISPLAY_NAME());
				jnpadLocationInfoService.insertManagerLocation(locationInfoForm);

				map.put("message","提交成功");


			}else{
				//更新位置信息
				jnpadLocationInfoService.updateManagerLocation(locationInfoForm);
				map.put("message","提交位置信息成功");
			}
		} catch (Exception e) {
			map.put("message", "提交位置信息失败");
		}
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
/**
 * 查询位置信息
 * @param request
 * @return
 */
	@ResponseBody
	@RequestMapping(value = "/ipad/intopieces/selectLocation.json", method = { RequestMethod.GET })
	public String selectLocation(HttpServletRequest request){
		Map<String, Object> map = new LinkedHashMap<String, Object>();


		List<LocationInfoForm> locationInfoForm=jnpadLocationInfoService.selectManagerLocationById(request.getParameter("userId"));
		//判断是否查询到位置信息
		if(locationInfoForm.size()==0){
			map.put("success", "false");
		}else{
			map.put("success", "true");
		}
		map.put("LocationInfoForm", locationInfoForm);
		map.put("size",locationInfoForm.size());
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}


}
