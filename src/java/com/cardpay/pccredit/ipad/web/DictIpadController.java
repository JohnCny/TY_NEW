/**
 * 
 */
package com.cardpay.pccredit.ipad.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.ipad.model.DataDictionary;
import com.cardpay.pccredit.system.model.Dict;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.util.web.RequestHelper;

/**
 * ipad 数据字典接口
 * @author shaoming
 *
 * 2014年11月25日   下午5:54:50
 */
@Controller
public class DictIpadController extends BaseController{
	@Autowired
	private CustomerInforService customerInforService;
	
	@ResponseBody
	@RequestMapping(value = "/ipad/dict/browse.json", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public String Dict(HttpServletRequest request) {
		String dictName = RequestHelper.getStringValue(request, "dict");
		List<DataDictionary> dicts = new ArrayList<DataDictionary>();
		Map<String,Object> result = new HashMap<String,Object>();
		List<Dict> kt = null;
		if(dictName!=null && (dictName.equalsIgnoreCase("nationality") || dictName.equals("1"))){
			/*国籍*/
			kt = customerInforService.findNationality();
		}else if(dictName!=null && (dictName.equalsIgnoreCase("cardType") || dictName.equals("2"))){
			/*证件类型*/
			kt = customerInforService.findCardType();
		}else if(dictName!=null && (dictName.equalsIgnoreCase("residentialPropertie") || dictName.equals("3"))){
			/*住宅性质*/
			kt = customerInforService.findResidentialPropertie();
		}else if(dictName!=null && (dictName.equalsIgnoreCase("maritalStatus") || dictName.equals("4"))){
			/*婚姻状况*/
			kt = customerInforService.findMaritalStatus();
		}else if(dictName!=null && (dictName.equalsIgnoreCase("oaccountMybankList") || dictName.equals("5"))){
			/*客户账户信息中的在我行开户情况*/
			kt = customerInforService.findOaccountMybankList();
		}else if(dictName!=null && (dictName.equalsIgnoreCase("creditCardList") || dictName.equals("6"))){
			/*客户账户持卡情况*/
			kt = customerInforService.findCreditCardList();
		}else if(dictName!=null && (dictName.equalsIgnoreCase("maritalStatus") || dictName.equals("7"))){
			/*客户在我行发工资情况*/
			kt = customerInforService.findPayMybankList();
		}else if(dictName!=null && (dictName.equalsIgnoreCase("positio") || dictName.equals("8"))){
			/*职务*/
			kt = customerInforService.findPositio();
		}else if(dictName!=null && (dictName.equalsIgnoreCase("title") || dictName.equals("9"))){
			/*职称*/
			kt = customerInforService.findTitle();
		}else if(dictName!=null && (dictName.equalsIgnoreCase("unitPropertis") || dictName.equals("10"))){
			/*单位性质*/
			kt = customerInforService.findUnitPropertis();
		}else if(dictName!=null && (dictName.equalsIgnoreCase("IndustryType") || dictName.equals("11"))){
			/*行业类型*/
			kt = customerInforService.findIndustryType();
		}else if(dictName!=null && (dictName.equalsIgnoreCase("CollectionMethod") || dictName.equals("12"))){
			/*催收方式*/
			kt = customerInforService.findCollectionMethod();
		}
		if(kt!=null){
			Iterator<Dict> it = kt.iterator();
			DataDictionary dd = null;
			while(it.hasNext()){
				Dict dict = it.next();
				dd = new DataDictionary();
				dd.setCode(dict.getTypeCode());
				dd.setName(dict.getTypeName());
				dicts.add(dd);
			}
		}
		result.put("result", dicts);
		JSONObject json = JSONObject.fromObject(result);
		return json.toString();
	}
}
