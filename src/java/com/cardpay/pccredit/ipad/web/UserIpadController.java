/**
 * 
 */
package com.cardpay.pccredit.ipad.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.ipad.constant.IpadConstant;
import com.cardpay.pccredit.ipad.model.WatingData;
import com.cardpay.pccredit.ipad.model.LoginResult;
import com.cardpay.pccredit.ipad.model.Result;
import com.cardpay.pccredit.ipad.model.UserIpad;
import com.cardpay.pccredit.ipad.service.UserForIpadService;
import com.cardpay.pccredit.main.MainService;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.util.web.RequestHelper;

/**
 * @author shaoming
 *
 * 2014年11月28日   下午1:54:18
 */
@Controller
public class UserIpadController {
	
	@Autowired
	private UserForIpadService userService;
	
	@Autowired
	private MainService mainService;
	
	@ResponseBody
	@RequestMapping(value = "/ipad/user/login.json")
	@JRadOperation(JRadOperation.BROWSE)
	public String login(HttpServletRequest request) {
		String login = RequestHelper.getStringValue(request, "login");
		String passwd = RequestHelper.getStringValue(request, "password");
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		Result result = null;
		LoginResult loginResult = null;
		if(StringUtils.isEmpty(login) || StringUtils.isEmpty(passwd)){
			result = new Result();
			result.setStatus(IpadConstant.FAIL);
			result.setReason(IpadConstant.LOGINNOTNULL);
			map.put("result",result);
		}else{
			loginResult = new LoginResult();
			UserIpad user = userService.login(login, passwd);
			if(user!=null){
				loginResult.setUser(user);
				loginResult.setStatus(IpadConstant.SUCCESS);
				loginResult.setReason(IpadConstant.LOGINSUCCESS);
			}else{
				loginResult.setStatus(IpadConstant.FAIL);
				loginResult.setReason(IpadConstant.LOGINFAIL);
			}
			map.put("result",loginResult);
		}
		JSONObject json = JSONObject.fromObject(map);
		return String.valueOf(json);
	}
	
	@ResponseBody
	@RequestMapping(value = "/ipad/user/homedata.json")
	public String homeData(HttpServletRequest request) {
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		List<WatingData> dicts = new ArrayList<WatingData>();
		String userId = request.getParameter("userId");
		if(StringUtils.isNotEmpty(userId)){
			HashMap<String,Integer> homeData = mainService.getHomeData(userId,0);
			WatingData marketingDic = new WatingData("1", "营销客户计划", homeData.get("marketing") + "");
			dicts.add(marketingDic);
			WatingData divisionalDic = new WatingData("2", "进件分配", homeData.get("divisional") + "");
			dicts.add(divisionalDic);
			WatingData applicationRejectDic = new WatingData("3", "拒绝进件", homeData.get("applicationReject") + "");
			dicts.add(applicationRejectDic);
			WatingData applicationInfoDic = new WatingData("4", "补充调查进件", homeData.get("applicationInfo") + "");
			dicts.add(applicationInfoDic);
			WatingData maintenanceDic = new WatingData("5", "客户维护计划 ", homeData.get("maintenance") + "");
			dicts.add(maintenanceDic);
			WatingData officerChannelsDic = new WatingData("6", "客户信息渠道维护计划", homeData.get("officerChannels") + "");
			dicts.add(officerChannelsDic);
			WatingData collectionDic = new WatingData("7", "客户催收计划", homeData.get("collection") + "");
			dicts.add(collectionDic);
			WatingData riskNumberDic = new WatingData("8", "风险事项警示", homeData.get("riskNumber") + "");
			dicts.add(riskNumberDic);
			WatingData abilityNumberDic = new WatingData("9", "问责", homeData.get("abilityNumber") + "");
			dicts.add(abilityNumberDic);
			WatingData productNumberDic = new WatingData("10", "产品发布", homeData.get("productNumber") + "");
			dicts.add(productNumberDic);
			
			map.put("result",dicts);
		}
		JSONObject json = JSONObject.fromObject(map);
		return String.valueOf(json);
	}
}
