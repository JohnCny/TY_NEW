package com.cardpay.pccredit.jnpad.web;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cardpay.pccredit.customer.model.CIPERSONFAMILY;
import com.cardpay.pccredit.customer.model.CustomerInforUpdateBalanceSheet;
import com.cardpay.pccredit.customer.model.CustomerInforUpdateCashFlow;
import com.cardpay.pccredit.customer.service.CustomerInforUpdateService;
import com.cardpay.pccredit.intopieces.model.Dcbzlr;
import com.cardpay.pccredit.intopieces.model.DcbzlrForm;
import com.cardpay.pccredit.intopieces.model.Dclrjb;
import com.cardpay.pccredit.intopieces.model.DclrjbForm;
import com.cardpay.pccredit.intopieces.service.AddIntoPiecesService;
import com.cardpay.pccredit.ipad.util.JsonDateValueProcessor;
import com.cardpay.pccredit.jnpad.service.JnIpadCustAppInfoXxService;

/**
 * ipad interface
 * 3.3.1客户信息资料采集
 * songchen
 * 2016年05月09日   下午1:54:18
 */
@Controller
public class JnIpadCustAppInfoXxCollectController {
	
	@Autowired
	private JnIpadCustAppInfoXxService appInfoXxService;
	
	@Autowired
	private CustomerInforUpdateService customerInforUpdateService;

	@Autowired
	private AddIntoPiecesService addIntoPiecesService;
	
	/**
	 * 上传影像资料
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/custAppInfo/uploadYx.json", method = { RequestMethod.GET })
	public String browse(@RequestParam(value = "file", required = false) MultipartFile file,HttpServletRequest request) {
		Map<String,Object> result = new LinkedHashMap<String,Object>();
		
		String productId=request.getParameter("productId");
		String customerId=request.getParameter("customerId");
		String applicationId=request.getParameter("applicationId");
		try {
			appInfoXxService.importImage(file,productId,customerId,applicationId);
			result.put("result", "success");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "上传失败:"+e.getMessage());
		}
		//response
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}
	
	
	/**
	 * 按id查找相应的客户基本信息（包含居住信息)
	 * 已有
	 */
	
	
	/**
	 * 按客户内码id查找相应的客户基本信息（包含居住信息)
	 * 已有
	 */
	
	
	/**
	 * 按客户内码id查找相应的客户家庭信息
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/custAppInfo/findFamilyXx.json", method = { RequestMethod.GET })
	public String returnPrepareAmount(HttpServletRequest request) {
		//当前登录用户ID
		String userId=request.getParameter("userId");
		String custId = request.getParameter("custId");
		List<CIPERSONFAMILY> list =  appInfoXxService.findFamilyByNm(custId,userId);
		//response
		Map<String,Object> result = new LinkedHashMap<String,Object>();
		result.put("result", list);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}
	
	
	/**
	 * 资产负债表
	 * 通过客户ID获取资产负债表的“所有者权益”
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/custAppInfo/findFz.json", method = { RequestMethod.GET })
	public String findFz(HttpServletRequest request) {
		//当前登录用户ID
		String customerId = request.getParameter("customerId");
		String productId = request.getParameter("productId");
		List<CustomerInforUpdateBalanceSheet> customerInforUpdateBalanceSheet =
                customerInforUpdateService.getCustomerInforUpdateBalanceSheetByCustIdAndProdId(customerId,productId);
		//response
		Map<String,Object> result = new LinkedHashMap<String,Object>();
		result.put("result", customerInforUpdateBalanceSheet);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}
	
	/**
	 * 资产负债表
	 * 过滤查询资产负债表
	 */
	
	/**
	 * 资产负债表
	 * 添加资产负债
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/custAppInfo/insertBalanceSheet.json", method = { RequestMethod.GET })
	public String insertBalanceSheet(HttpServletRequest request) {
		Map<String,Object> result = new LinkedHashMap<String,Object>();
		//当前登录用户ID
		String customerId = request.getParameter("customerId");
		String productId = request.getParameter("productId");
		String balanceSheet = request.getParameter("balanceSheet");
		try {
			customerInforUpdateService.insertCustomerInforUpdateBalanceSheet1(customerId,balanceSheet,productId);
			
			List<CustomerInforUpdateBalanceSheet> customerInforUpdateBalanceSheet =
                    customerInforUpdateService.getCustomerInforUpdateBalanceSheetByCustIdAndProdId(customerId,productId);
			
			result = new LinkedHashMap<String,Object>();
			result.put("result", customerInforUpdateBalanceSheet);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "fail");
		}
		//response
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}
	
	
	/**
	 * 利润简表
	 * 查询利润简表
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/custAppInfo/findLrjb.json", method = { RequestMethod.GET })
	public String findLrjb(HttpServletRequest request) {
		String customerId = request.getParameter("customerId");
		String productId = request.getParameter("productId");
		
		Dclrjb lrjb = addIntoPiecesService.findDclrjb(customerId,productId);
		
		//response
		Map<String,Object> result = new LinkedHashMap<String,Object>();
		result.put("result", lrjb);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}
	
	/**
	 * 利润简表
	 * 保存或更新利润简表
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/custAppInfo/savAndUpdateLrjb.json", method = { RequestMethod.GET })
	public String addLrjb(HttpServletRequest request,@ModelAttribute DclrjbForm form) {
		Map<String,Object> result = new LinkedHashMap<String,Object>();
		Dclrjb lrjb = form.createModel(Dclrjb.class);
		//查询是否exist
		Dclrjb dclrjb = addIntoPiecesService.findDclrjb(form.getCustomer_id(),form.getProduct_id());
		try{
		if(dclrjb!=null){
			dclrjb.setRq(lrjb.getRq());     
			dclrjb.setUnit(lrjb.getUnit());     
			dclrjb.setA1(lrjb.getA1());     
			dclrjb.setA2(lrjb.getA2());     
			dclrjb.setA3(lrjb.getA3());     
			dclrjb.setA4(lrjb.getA4());     
			dclrjb.setA5(lrjb.getA5());     
			dclrjb.setA6(lrjb.getA6());     
			dclrjb.setA7(lrjb.getA7());     
			dclrjb.setA8(lrjb.getA8());     
			dclrjb.setA10(lrjb.getA10());     
			dclrjb.setA11(lrjb.getA11());     
			dclrjb.setA12(lrjb.getA12());     
			dclrjb.setA13(lrjb.getA13());     
			dclrjb.setA14(lrjb.getA14());     
			dclrjb.setA15(lrjb.getA15());     
			dclrjb.setA16(lrjb.getA16());     
			dclrjb.setA17(lrjb.getA17());     
			dclrjb.setA18(lrjb.getA18());     
			dclrjb.setA19(lrjb.getA19());     
			dclrjb.setA20(lrjb.getA20());     
			dclrjb.setA21(lrjb.getA21());     
			dclrjb.setA22(lrjb.getA22());     
			dclrjb.setA23(lrjb.getA23());     
			dclrjb.setA24(lrjb.getA24());     
			dclrjb.setA25(lrjb.getA25());     
			dclrjb.setA26(lrjb.getA26());     
			dclrjb.setA27(lrjb.getA27());     
			dclrjb.setA28(lrjb.getA28());     
			dclrjb.setA29(lrjb.getA29());     
			dclrjb.setA30(lrjb.getA30());     
			dclrjb.setA31(lrjb.getA31());     
			dclrjb.setA32(lrjb.getA32());     
			dclrjb.setA33(lrjb.getA33());     
			dclrjb.setA34(lrjb.getA34());     
			dclrjb.setA35(lrjb.getA35());     
			dclrjb.setA36(lrjb.getA36());     
			dclrjb.setA37(lrjb.getA37());     
			dclrjb.setA38(lrjb.getA38());     
			dclrjb.setA39(lrjb.getA39());     
			dclrjb.setA40(lrjb.getA40());     
			dclrjb.setA41(lrjb.getA41());     
			dclrjb.setA42(lrjb.getA42());     
			dclrjb.setA43(lrjb.getA43());     
			dclrjb.setA44(lrjb.getA44());     
			dclrjb.setA45(lrjb.getA45());     
			dclrjb.setA46(lrjb.getA46());     
			dclrjb.setA47(lrjb.getA47());     
			dclrjb.setA48(lrjb.getA48());     
			dclrjb.setA49(lrjb.getA49());     
			dclrjb.setA50(lrjb.getA50());     
			dclrjb.setA51(lrjb.getA51());     
			dclrjb.setA52(lrjb.getA52());     
			dclrjb.setA53(lrjb.getA53());     
			dclrjb.setA54(lrjb.getA54());     
			dclrjb.setA55(lrjb.getA55());     
			dclrjb.setA56(lrjb.getA56());     
			dclrjb.setA57(lrjb.getA57());     
			dclrjb.setA58(lrjb.getA58());     
			dclrjb.setA59(lrjb.getA59());     
			dclrjb.setA60(lrjb.getA60());     
			dclrjb.setA61(lrjb.getA61());     
			dclrjb.setA62(lrjb.getA62());     
			dclrjb.setA63(lrjb.getA63());     
			dclrjb.setA64(lrjb.getA64());     
			dclrjb.setA65(lrjb.getA65());     
			dclrjb.setA66(lrjb.getA66());     
			dclrjb.setA67(lrjb.getA67());     
			dclrjb.setA68(lrjb.getA68());     
			dclrjb.setA69(lrjb.getA69());     
			dclrjb.setA70(lrjb.getA70());     
			dclrjb.setA71(lrjb.getA71());     
			dclrjb.setA72(lrjb.getA72());     
			dclrjb.setA73(lrjb.getA73());     
			dclrjb.setA74(lrjb.getA74());     
			dclrjb.setA75(lrjb.getA75());     
			dclrjb.setA76(lrjb.getA76());     
			dclrjb.setA77(lrjb.getA77());     
			dclrjb.setA78(lrjb.getA78());     
			dclrjb.setA79(lrjb.getA79());     
			dclrjb.setA80(lrjb.getA80());     
			dclrjb.setA81(lrjb.getA81());     
			dclrjb.setA82(lrjb.getA82());     
			dclrjb.setA83(lrjb.getA83());     
			dclrjb.setA84(lrjb.getA84());     
			dclrjb.setA85(lrjb.getA85());     
			dclrjb.setA86(lrjb.getA86());     
			dclrjb.setA87(lrjb.getA87());     
			dclrjb.setA88(lrjb.getA88());     
			dclrjb.setA89(lrjb.getA89());     
			dclrjb.setA90(lrjb.getA90());     
			dclrjb.setA91(lrjb.getA91());     
			dclrjb.setA92(lrjb.getA92());     
			dclrjb.setA93(lrjb.getA93());     
			dclrjb.setA94(lrjb.getA94());     
			dclrjb.setA95(lrjb.getA95());     
			dclrjb.setA96(lrjb.getA96());     
			dclrjb.setA97(lrjb.getA97());     
			dclrjb.setA98(lrjb.getA98());     
			dclrjb.setA99(lrjb.getA99());     
			dclrjb.setA100(lrjb.getA100());     
			dclrjb.setA101(lrjb.getA101());     
			dclrjb.setA102(lrjb.getA102());     
			dclrjb.setA103(lrjb.getA103());     
			dclrjb.setA104(lrjb.getA104());     
			dclrjb.setA105(lrjb.getA105());     
			dclrjb.setA106(lrjb.getA106());     
			dclrjb.setA107(lrjb.getA107());     
			dclrjb.setA108(lrjb.getA108());     
			dclrjb.setA109(lrjb.getA109());     
			dclrjb.setA110(lrjb.getA110());     
			dclrjb.setA111(lrjb.getA111());     
			dclrjb.setA112(lrjb.getA112());     
			dclrjb.setA113(lrjb.getA113());     
			dclrjb.setA114(lrjb.getA114());     
			dclrjb.setA115(lrjb.getA115());     
			dclrjb.setA116(lrjb.getA116());     
			dclrjb.setA117(lrjb.getA117());     
			dclrjb.setA118(lrjb.getA118());     
			dclrjb.setA119(lrjb.getA119());     
			dclrjb.setA120(lrjb.getA120());     
			dclrjb.setA121(lrjb.getA121());     
			dclrjb.setA122(lrjb.getA122());     
			dclrjb.setA123(lrjb.getA123());     
			dclrjb.setA124(lrjb.getA124());     
			dclrjb.setA125(lrjb.getA125());     
			dclrjb.setA126(lrjb.getA126());     
			dclrjb.setA127(lrjb.getA127());     
			dclrjb.setA128(lrjb.getA128());     
			dclrjb.setA129(lrjb.getA129());     
			dclrjb.setA130(lrjb.getA130());     
			dclrjb.setA131(lrjb.getA131());     
			dclrjb.setA132(lrjb.getA132());     
			dclrjb.setA133(lrjb.getA133());     
			dclrjb.setA134(lrjb.getA134());     
			dclrjb.setA135(lrjb.getA135());     
			dclrjb.setA136(lrjb.getA136());     
			dclrjb.setA137(lrjb.getA137());     
			dclrjb.setA138(lrjb.getA138());     
			dclrjb.setA139(lrjb.getA139());     
			dclrjb.setA140(lrjb.getA140());     
			dclrjb.setA141(lrjb.getA141());     
			dclrjb.setA142(lrjb.getA142());     
			dclrjb.setA143(lrjb.getA143());     
			dclrjb.setA144(lrjb.getA144());     
			dclrjb.setA145(lrjb.getA145());     
			dclrjb.setA146(lrjb.getA146());     
			dclrjb.setA147(lrjb.getA147());     
			dclrjb.setA148(lrjb.getA148());     
			dclrjb.setA149(lrjb.getA149());     
			dclrjb.setA150(lrjb.getA150());     
			dclrjb.setA151(lrjb.getA151());     
			dclrjb.setA152(lrjb.getA152());     
			dclrjb.setA153(lrjb.getA153());     
			dclrjb.setA154(lrjb.getA154());     
			dclrjb.setA155(lrjb.getA155());     
			dclrjb.setA156(lrjb.getA156());     
			dclrjb.setA157(lrjb.getA157());     
			dclrjb.setA158(lrjb.getA158());     
			dclrjb.setA159(lrjb.getA159());     
			dclrjb.setA160(lrjb.getA160());     
			dclrjb.setA161(lrjb.getA161());     
			dclrjb.setA162(lrjb.getA162());     
			dclrjb.setA163(lrjb.getA163());     
			dclrjb.setA164(lrjb.getA164());     
			dclrjb.setA165(lrjb.getA165());     
			dclrjb.setA166(lrjb.getA166());     
			dclrjb.setA167(lrjb.getA167());     
			dclrjb.setA168(lrjb.getA168());     
			dclrjb.setA169(lrjb.getA169());     
			dclrjb.setA170(lrjb.getA170());     
			dclrjb.setA171(lrjb.getA171());     
			dclrjb.setA172(lrjb.getA172());     
			dclrjb.setA173(lrjb.getA173());     
			dclrjb.setA174(lrjb.getA174());     
			dclrjb.setA175(lrjb.getA175());     
			dclrjb.setA176(lrjb.getA176());     
			dclrjb.setA177(lrjb.getA177());     
			dclrjb.setA178(lrjb.getA178());     
			dclrjb.setA179(lrjb.getA179());     
			dclrjb.setA180(lrjb.getA180());     
			dclrjb.setA181(lrjb.getA181());     
			dclrjb.setSrjh(lrjb.getSrjh());     
			dclrjb.setSrxs(lrjb.getSrxs());     
			dclrjb.setKbcbxs(lrjb.getKbcbxs());     
			dclrjb.setQtsrfx(lrjb.getQtsrfx());     
			dclrjb.setKz(lrjb.getKz());     
			dclrjb.setLrjs(lrjb.getLrjs());     
			addIntoPiecesService.updateDclrjb(dclrjb);
		}else{
			addIntoPiecesService.saveDclrjb(lrjb);
		}
			result.put("result", "success");
		} catch (Exception e) {
			result.put("result", "fail");
		}
		//response
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}
	
	/**
	 * 标准利润表
	 * 查询标准利润表
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/custAppInfo/findbzlr.json", method = { RequestMethod.GET })
	public String findbzlr(HttpServletRequest request) {
		String customerId = request.getParameter("customerId");
		String productId = request.getParameter("productId");
		
		Dcbzlr bzlr = addIntoPiecesService.findDcbzlr(customerId,productId);
		//response
		Map<String,Object> result = new LinkedHashMap<String,Object>();
		result.put("result", bzlr);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}
	
	/**
	 * 标准利润表
	 * 保存/更新标准利润表
	 */
	
	@ResponseBody
	@RequestMapping(value = "saveAndUpdatebzlr.json", method = { RequestMethod.GET })
	public String saveAndUpdatebzlr(@ModelAttribute DcbzlrForm form,HttpServletRequest request) {
		Map<String,Object> result = new LinkedHashMap<String,Object>();
		Dcbzlr bzlr = form.createModel(Dcbzlr.class);
		//查询是否exist
	    Dcbzlr dcbzlr = addIntoPiecesService.findDcbzlr(form.getCustomer_id(),form.getProduct_id());
		try{
			if(dcbzlr!=null){
			  dcbzlr.setBbzq(dcbzlr.getBbzq());          
			  dcbzlr.setDczq(dcbzlr.getDczq());          
			  dcbzlr.setUnit(dcbzlr.getUnit());         
			  dcbzlr.setC4 (bzlr.getC4());            
			  dcbzlr.setD4 (bzlr.getD4());            
			  dcbzlr.setE4 (bzlr.getE4());            
			  dcbzlr.setF4 (bzlr.getF4());            
			  dcbzlr.setG4 (bzlr.getG4());            
			  dcbzlr.setH4 (bzlr.getH4());            
			  dcbzlr.setI4 (bzlr.getI4());            
			  dcbzlr.setJ4 (bzlr.getJ4());            
			  dcbzlr.setK4 (bzlr.getK4());            
			  dcbzlr.setL4 (bzlr.getL4());            
			  dcbzlr.setM4 (bzlr.getM4());            
			  dcbzlr.setN4 (bzlr.getN4());            
			  dcbzlr.setO4 (bzlr.getO4());            
			  dcbzlr.setP4 (bzlr.getP4());            
			  dcbzlr.setQ4 (bzlr.getQ4());            
			  dcbzlr.setC5 (bzlr.getC5());            
			  dcbzlr.setD5 (bzlr.getD5());            
			  dcbzlr.setE5 (bzlr.getE5());            
			  dcbzlr.setF5 (bzlr.getF5());            
			  dcbzlr.setG5 (bzlr.getG5());            
			  dcbzlr.setH5 (bzlr.getH5());            
			  dcbzlr.setI5 (bzlr.getI5());            
			  dcbzlr.setJ5 (bzlr.getJ5());            
			  dcbzlr.setK5 (bzlr.getK5());            
			  dcbzlr.setL5 (bzlr.getL5());            
			  dcbzlr.setM5 (bzlr.getM5());            
			  dcbzlr.setN5 (bzlr.getN5());            
			  dcbzlr.setO5 (bzlr.getO5());            
			  dcbzlr.setP5 (bzlr.getP5());            
			  dcbzlr.setQ5 (bzlr.getQ5());            
			  dcbzlr.setC6 (bzlr.getC6());            
			  dcbzlr.setD6 (bzlr.getD6());            
			  dcbzlr.setE6 (bzlr.getE6());            
			  dcbzlr.setF6 (bzlr.getF6());            
			  dcbzlr.setG6 (bzlr.getG6());            
			  dcbzlr.setH6 (bzlr.getH6());            
			  dcbzlr.setI6 (bzlr.getI6());            
			  dcbzlr.setJ6 (bzlr.getJ6());            
			  dcbzlr.setK6 (bzlr.getK6());            
			  dcbzlr.setL6 (bzlr.getL6());            
			  dcbzlr.setM6 (bzlr.getM6());            
			  dcbzlr.setN6 (bzlr.getN6());            
			  dcbzlr.setO6 (bzlr.getO6());            
			  dcbzlr.setP6 (bzlr.getP6());            
			  dcbzlr.setQ6 (bzlr.getQ6());            
			  dcbzlr.setC7 (bzlr.getC7());            
			  dcbzlr.setD7 (bzlr.getD7());            
			  dcbzlr.setE7 (bzlr.getE7());            
			  dcbzlr.setF7 (bzlr.getF7());            
			  dcbzlr.setG7 (bzlr.getG7());            
			  dcbzlr.setH7 (bzlr.getH7());            
			  dcbzlr.setI7 (bzlr.getI7());            
			  dcbzlr.setJ7 (bzlr.getJ7());            
			  dcbzlr.setK7 (bzlr.getK7());            
			  dcbzlr.setL7 (bzlr.getL7());            
			  dcbzlr.setM7 (bzlr.getM7());            
			  dcbzlr.setN7 (bzlr.getN7());            
			  dcbzlr.setO7 (bzlr.getO7());            
			  dcbzlr.setP7 (bzlr.getP7());            
			  dcbzlr.setQ7 (bzlr.getQ7());            
			  dcbzlr.setC8 (bzlr.getC8());            
			  dcbzlr.setD8 (bzlr.getD8());            
			  dcbzlr.setE8 (bzlr.getE8());            
			  dcbzlr.setF8 (bzlr.getF8());            
			  dcbzlr.setG8 (bzlr.getG8());            
			  dcbzlr.setH8 (bzlr.getH8());            
			  dcbzlr.setI8 (bzlr.getI8());            
			  dcbzlr.setJ8 (bzlr.getJ8());            
			  dcbzlr.setK8 (bzlr.getK8());            
			  dcbzlr.setL8 (bzlr.getL8());            
			  dcbzlr.setM8 (bzlr.getM8());            
			  dcbzlr.setN8 (bzlr.getN8());            
			  dcbzlr.setO8 (bzlr.getO8());            
			  dcbzlr.setP8 (bzlr.getP8());            
			  dcbzlr.setQ8 (bzlr.getQ8());            
			  dcbzlr.setC9 (bzlr.getC9());            
			  dcbzlr.setD9 (bzlr.getD9());            
			  dcbzlr.setE9 (bzlr.getE9());            
			  dcbzlr.setF9 (bzlr.getF9());            
			  dcbzlr.setG9 (bzlr.getG9());            
			  dcbzlr.setH9 (bzlr.getH9());            
			  dcbzlr.setI9 (bzlr.getI9());            
			  dcbzlr.setJ9 (bzlr.getJ9());            
			  dcbzlr.setK9 (bzlr.getK9());            
			  dcbzlr.setL9 (bzlr.getL9());            
			  dcbzlr.setM9 (bzlr.getM9());            
			  dcbzlr.setN9 (bzlr.getN9());            
			  dcbzlr.setO9 (bzlr.getO9());            
			  dcbzlr.setP9 (bzlr.getP9());            
			  dcbzlr.setQ9 (bzlr.getQ9());            
			  dcbzlr.setC10(bzlr.getC10());            
			  dcbzlr.setD10(bzlr.getD10());            
			  dcbzlr.setE10(bzlr.getE10());            
			  dcbzlr.setF10(bzlr.getF10());            
			  dcbzlr.setG10(bzlr.getG10());            
			  dcbzlr.setH10(bzlr.getH10());            
			  dcbzlr.setI10(bzlr.getI10());            
			  dcbzlr.setJ10(bzlr.getJ10());            
			  dcbzlr.setK10(bzlr.getK10());            
			  dcbzlr.setL10(bzlr.getL10());            
			  dcbzlr.setM10(bzlr.getM10());            
			  dcbzlr.setN10(bzlr.getN10());            
			  dcbzlr.setO10(bzlr.getO10());            
			  dcbzlr.setP10(bzlr.getP10());            
			  dcbzlr.setQ10(bzlr.getQ10());            
			  dcbzlr.setC11(bzlr.getC11());            
			  dcbzlr.setD11(bzlr.getD11());            
			  dcbzlr.setE11(bzlr.getE11());            
			  dcbzlr.setF11(bzlr.getF11());            
			  dcbzlr.setG11(bzlr.getG11());            
			  dcbzlr.setH11(bzlr.getH11());            
			  dcbzlr.setI11(bzlr.getI11());            
			  dcbzlr.setJ11(bzlr.getJ11());            
			  dcbzlr.setK11(bzlr.getK11());            
			  dcbzlr.setL11(bzlr.getL11());            
			  dcbzlr.setM11(bzlr.getM11());            
			  dcbzlr.setN11(bzlr.getN11());            
			  dcbzlr.setO11(bzlr.getO11());            
			  dcbzlr.setP11(bzlr.getP11());            
			  dcbzlr.setQ11(bzlr.getQ11());            
			  dcbzlr.setC12(bzlr.getC12());            
			  dcbzlr.setD12(bzlr.getD12());            
			  dcbzlr.setE12(bzlr.getE12());            
			  dcbzlr.setF12(bzlr.getF12());            
			  dcbzlr.setG12(bzlr.getG12());            
			  dcbzlr.setH12(bzlr.getH12());            
			  dcbzlr.setI12(bzlr.getI12());            
			  dcbzlr.setJ12(bzlr.getJ12());            
			  dcbzlr.setK12(bzlr.getK12());            
			  dcbzlr.setL12(bzlr.getL12());            
			  dcbzlr.setM12(bzlr.getM12());            
			  dcbzlr.setN12(bzlr.getN12());            
			  dcbzlr.setO12(bzlr.getO12());            
			  dcbzlr.setP12(bzlr.getP12());            
			  dcbzlr.setQ12(bzlr.getQ12());            
			  dcbzlr.setC13(bzlr.getC13());            
			  dcbzlr.setD13(bzlr.getD13());            
			  dcbzlr.setE13(bzlr.getE13());            
			  dcbzlr.setF13(bzlr.getF13());            
			  dcbzlr.setG13(bzlr.getG13());            
			  dcbzlr.setH13(bzlr.getH13());            
			  dcbzlr.setI13(bzlr.getI13());            
			  dcbzlr.setJ13(bzlr.getJ13());            
			  dcbzlr.setK13(bzlr.getK13());            
			  dcbzlr.setL13(bzlr.getL13());            
			  dcbzlr.setM13(bzlr.getM13());            
			  dcbzlr.setN13(bzlr.getN13());            
			  dcbzlr.setO13(bzlr.getO13());            
			  dcbzlr.setP13(bzlr.getP13());            
			  dcbzlr.setQ13(bzlr.getQ13());            
			  dcbzlr.setC14(bzlr.getC14());            
			  dcbzlr.setD14(bzlr.getD14());            
			  dcbzlr.setE14(bzlr.getE14());            
			  dcbzlr.setF14(bzlr.getF14());            
			  dcbzlr.setG14(bzlr.getG14());            
			  dcbzlr.setH14(bzlr.getH14());            
			  dcbzlr.setI14(bzlr.getI14());            
			  dcbzlr.setJ14(bzlr.getJ14());            
			  dcbzlr.setK14(bzlr.getK14());            
			  dcbzlr.setL14(bzlr.getL14());            
			  dcbzlr.setM14(bzlr.getM14());            
			  dcbzlr.setN14(bzlr.getN14());            
			  dcbzlr.setO14(bzlr.getO14());            
			  dcbzlr.setP14(bzlr.getP14());            
			  dcbzlr.setQ14(bzlr.getQ14());            
			  dcbzlr.setC15(bzlr.getC15());            
			  dcbzlr.setD15(bzlr.getD15());            
			  dcbzlr.setE15(bzlr.getE15());            
			  dcbzlr.setF15(bzlr.getF15());            
			  dcbzlr.setG15(bzlr.getG15());            
			  dcbzlr.setH15(bzlr.getH15());            
			  dcbzlr.setI15(bzlr.getI15());            
			  dcbzlr.setJ15(bzlr.getJ15());            
			  dcbzlr.setK15(bzlr.getK15());            
			  dcbzlr.setL15(bzlr.getL15());            
			  dcbzlr.setM15(bzlr.getM15());            
			  dcbzlr.setN15(bzlr.getN15());            
			  dcbzlr.setO15(bzlr.getO15());            
			  dcbzlr.setP15(bzlr.getP15());            
			  dcbzlr.setQ15(bzlr.getQ15());            
			  dcbzlr.setC16(bzlr.getC16());            
			  dcbzlr.setD16(bzlr.getD16());            
			  dcbzlr.setE16(bzlr.getE16());            
			  dcbzlr.setF16(bzlr.getF16());            
			  dcbzlr.setG16(bzlr.getG16());            
			  dcbzlr.setH16(bzlr.getH16());            
			  dcbzlr.setI16(bzlr.getI16());            
			  dcbzlr.setJ16(bzlr.getJ16());            
			  dcbzlr.setK16(bzlr.getK16());            
			  dcbzlr.setL16(bzlr.getL16());            
			  dcbzlr.setM16(bzlr.getM16());            
			  dcbzlr.setN16(bzlr.getN16());            
			  dcbzlr.setO16(bzlr.getO16());            
			  dcbzlr.setP16(bzlr.getP16());            
			  dcbzlr.setQ16(bzlr.getQ16());            
			  dcbzlr.setC17(bzlr.getC17());            
			  dcbzlr.setD17(bzlr.getD17());            
			  dcbzlr.setE17(bzlr.getE17());            
			  dcbzlr.setF17(bzlr.getF17());            
			  dcbzlr.setG17(bzlr.getG17());            
			  dcbzlr.setH17(bzlr.getH17());            
			  dcbzlr.setI17(bzlr.getI17());            
			  dcbzlr.setJ17(bzlr.getJ17());            
			  dcbzlr.setK17(bzlr.getK17());            
			  dcbzlr.setL17(bzlr.getL17());            
			  dcbzlr.setM17(bzlr.getM17());            
			  dcbzlr.setN17(bzlr.getN17());            
			  dcbzlr.setO17(bzlr.getO17());            
			  dcbzlr.setP17(bzlr.getP17());            
			  dcbzlr.setQ17(bzlr.getQ17());            
			  dcbzlr.setC18(bzlr.getC18());            
			  dcbzlr.setD18(bzlr.getD18());            
			  dcbzlr.setE18(bzlr.getE18());            
			  dcbzlr.setF18(bzlr.getF18());            
			  dcbzlr.setG18(bzlr.getG18());            
			  dcbzlr.setH18(bzlr.getH18());            
			  dcbzlr.setI18(bzlr.getI18());            
			  dcbzlr.setJ18(bzlr.getJ18());            
			  dcbzlr.setK18(bzlr.getK18());            
			  dcbzlr.setL18(bzlr.getL18());            
			  dcbzlr.setM18(bzlr.getM18());            
			  dcbzlr.setN18(bzlr.getN18());            
			  dcbzlr.setO18(bzlr.getO18());            
			  dcbzlr.setP18(bzlr.getP18());            
			  dcbzlr.setQ18(bzlr.getQ18());            
			  dcbzlr.setC19(bzlr.getC19());            
			  dcbzlr.setD19(bzlr.getD19());            
			  dcbzlr.setE19(bzlr.getE19());            
			  dcbzlr.setF19(bzlr.getF19());            
			  dcbzlr.setG19(bzlr.getG19());            
			  dcbzlr.setH19(bzlr.getH19());            
			  dcbzlr.setI19(bzlr.getI19());            
			  dcbzlr.setJ19(bzlr.getJ19());            
			  dcbzlr.setK19(bzlr.getK19());            
			  dcbzlr.setL19(bzlr.getL19());            
			  dcbzlr.setM19(bzlr.getM19());            
			  dcbzlr.setN19(bzlr.getN19());            
			  dcbzlr.setO19(bzlr.getO19());            
			  dcbzlr.setP19(bzlr.getP19());            
			  dcbzlr.setQ19(bzlr.getQ19());            
			  dcbzlr.setC20(bzlr.getC20());            
			  dcbzlr.setD20(bzlr.getD20());            
			  dcbzlr.setE20(bzlr.getE20());            
			  dcbzlr.setF20(bzlr.getF20());            
			  dcbzlr.setG20(bzlr.getG20());            
			  dcbzlr.setH20(bzlr.getH20());            
			  dcbzlr.setI20(bzlr.getI20());            
			  dcbzlr.setJ20(bzlr.getJ20());            
			  dcbzlr.setK20(bzlr.getK20());            
			  dcbzlr.setL20(bzlr.getL20());            
			  dcbzlr.setM20(bzlr.getM20());            
			  dcbzlr.setN20(bzlr.getN20());            
			  dcbzlr.setO20(bzlr.getO20());            
			  dcbzlr.setP20(bzlr.getP20());            
			  dcbzlr.setQ20(bzlr.getQ20());            
			  dcbzlr.setC21(bzlr.getC21());            
			  dcbzlr.setD21(bzlr.getD21());            
			  dcbzlr.setE21(bzlr.getE21());            
			  dcbzlr.setF21(bzlr.getF21());            
			  dcbzlr.setG21(bzlr.getG21());            
			  dcbzlr.setH21(bzlr.getH21());            
			  dcbzlr.setI21(bzlr.getI21());            
			  dcbzlr.setJ21(bzlr.getJ21());            
			  dcbzlr.setK21(bzlr.getK21());            
			  dcbzlr.setL21(bzlr.getL21());            
			  dcbzlr.setM21(bzlr.getM21());            
			  dcbzlr.setN21(bzlr.getN21());            
			  dcbzlr.setO21(bzlr.getO21());            
			  dcbzlr.setP21(bzlr.getP21());            
			  dcbzlr.setQ21(bzlr.getQ21());            
			  dcbzlr.setC22(bzlr.getC22());            
			  dcbzlr.setD22(bzlr.getD22());            
			  dcbzlr.setE22(bzlr.getE22());            
			  dcbzlr.setF22(bzlr.getF22());            
			  dcbzlr.setG22(bzlr.getG22());            
			  dcbzlr.setH22(bzlr.getH22());            
			  dcbzlr.setI22(bzlr.getI22());            
			  dcbzlr.setJ22(bzlr.getJ22());            
			  dcbzlr.setK22(bzlr.getK22());            
			  dcbzlr.setL22(bzlr.getL22());            
			  dcbzlr.setM22(bzlr.getM22());            
			  dcbzlr.setN22(bzlr.getN22());            
			  dcbzlr.setO22(bzlr.getO22());            
			  dcbzlr.setP22(bzlr.getP22());            
			  dcbzlr.setQ22(bzlr.getQ22());            
			  dcbzlr.setC23(bzlr.getC23());            
			  dcbzlr.setD23(bzlr.getD23());            
			  dcbzlr.setE23(bzlr.getE23());            
			  dcbzlr.setF23(bzlr.getF23());            
			  dcbzlr.setG23(bzlr.getG23());            
			  dcbzlr.setH23(bzlr.getH23());            
			  dcbzlr.setI23(bzlr.getI23());            
			  dcbzlr.setJ23(bzlr.getJ23());            
			  dcbzlr.setK23(bzlr.getK23());            
			  dcbzlr.setL23(bzlr.getL23());            
			  dcbzlr.setM23(bzlr.getM23());            
			  dcbzlr.setN23(bzlr.getN23());            
			  dcbzlr.setO23(bzlr.getO23());            
			  dcbzlr.setP23(bzlr.getP23());            
			  dcbzlr.setQ23(bzlr.getQ23());            
			  dcbzlr.setC24(bzlr.getC24());            
			  dcbzlr.setD24(bzlr.getD24());            
			  dcbzlr.setE24(bzlr.getE24());            
			  dcbzlr.setF24(bzlr.getF24());            
			  dcbzlr.setG24(bzlr.getG24());            
			  dcbzlr.setH24(bzlr.getH24());            
			  dcbzlr.setI24(bzlr.getI24());            
			  dcbzlr.setJ24(bzlr.getJ24());            
			  dcbzlr.setK24(bzlr.getK24());            
			  dcbzlr.setL24(bzlr.getL24());            
			  dcbzlr.setM24(bzlr.getM24());            
			  dcbzlr.setN24(bzlr.getN24());            
			  dcbzlr.setO24(bzlr.getO24());            
			  dcbzlr.setP24(bzlr.getP24());            
			  dcbzlr.setQ24(bzlr.getQ24());            
			  dcbzlr.setC25(bzlr.getC25());            
			  dcbzlr.setD25(bzlr.getD25());            
			  dcbzlr.setE25(bzlr.getE25());            
			  dcbzlr.setF25(bzlr.getF25());            
			  dcbzlr.setG25(bzlr.getG25());            
			  dcbzlr.setH25(bzlr.getH25());            
			  dcbzlr.setI25(bzlr.getI25());            
			  dcbzlr.setJ25(bzlr.getJ25());            
			  dcbzlr.setK25(bzlr.getK25());            
			  dcbzlr.setL25(bzlr.getL25());            
			  dcbzlr.setM25(bzlr.getM25());            
			  dcbzlr.setN25(bzlr.getN25());            
			  dcbzlr.setO25(bzlr.getO25());            
			  dcbzlr.setP25(bzlr.getP25());            
			  dcbzlr.setQ25(bzlr.getQ25());            
			  dcbzlr.setC26(bzlr.getC26());            
			  dcbzlr.setD26(bzlr.getD26());            
			  dcbzlr.setE26(bzlr.getE26());            
			  dcbzlr.setF26(bzlr.getF26());            
			  dcbzlr.setG26(bzlr.getG26());            
			  dcbzlr.setH26(bzlr.getH26());            
			  dcbzlr.setI26(bzlr.getI26());            
			  dcbzlr.setJ26(bzlr.getJ26());            
			  dcbzlr.setK26(bzlr.getK26());            
			  dcbzlr.setL26(bzlr.getL26());            
			  dcbzlr.setM26(bzlr.getM26());            
			  dcbzlr.setN26(bzlr.getN26());            
			  dcbzlr.setO26(bzlr.getO26());            
			  dcbzlr.setP26(bzlr.getP26());            
			  dcbzlr.setQ26(bzlr.getQ26());            
			  dcbzlr.setC27(bzlr.getC27());            
			  dcbzlr.setD27(bzlr.getD27());            
			  dcbzlr.setE27(bzlr.getE27());            
			  dcbzlr.setF27(bzlr.getF27());            
			  dcbzlr.setG27(bzlr.getG27());            
			  dcbzlr.setH27(bzlr.getH27());            
			  dcbzlr.setI27(bzlr.getI27());            
			  dcbzlr.setJ27(bzlr.getJ27());            
			  dcbzlr.setK27(bzlr.getK27());            
			  dcbzlr.setL27(bzlr.getL27());            
			  dcbzlr.setM27(bzlr.getM27());            
			  dcbzlr.setN27(bzlr.getN27());            
			  dcbzlr.setO27(bzlr.getO27());            
			  dcbzlr.setP27(bzlr.getP27());            
			  dcbzlr.setQ27(bzlr.getQ27());            
			  dcbzlr.setC28(bzlr.getC28());            
			  dcbzlr.setD28(bzlr.getD28());            
			  dcbzlr.setE28(bzlr.getE28());            
			  dcbzlr.setF28(bzlr.getF28());            
			  dcbzlr.setG28(bzlr.getG28());            
			  dcbzlr.setH28(bzlr.getH28());            
			  dcbzlr.setI28(bzlr.getI28());            
			  dcbzlr.setJ28(bzlr.getJ28());            
			  dcbzlr.setK28(bzlr.getK28());            
			  dcbzlr.setL28(bzlr.getL28());            
			  dcbzlr.setM28(bzlr.getM28());            
			  dcbzlr.setN28(bzlr.getN28());            
			  dcbzlr.setO28(bzlr.getO28());            
			  dcbzlr.setP28(bzlr.getP28());            
			  dcbzlr.setQ28(bzlr.getQ28());            
			  dcbzlr.setC29(bzlr.getC29());            
			  dcbzlr.setD29(bzlr.getD29());            
			  dcbzlr.setE29(bzlr.getE29());            
			  dcbzlr.setF29(bzlr.getF29());            
			  dcbzlr.setG29(bzlr.getG29());            
			  dcbzlr.setH29(bzlr.getH29());            
			  dcbzlr.setI29(bzlr.getI29());            
			  dcbzlr.setJ29(bzlr.getJ29());            
			  dcbzlr.setK29(bzlr.getK29());            
			  dcbzlr.setL29(bzlr.getL29());            
			  dcbzlr.setM29(bzlr.getM29());            
			  dcbzlr.setN29(bzlr.getN29());            
			  dcbzlr.setO29(bzlr.getO29());            
			  dcbzlr.setP29(bzlr.getP29());            
			  dcbzlr.setQ29(bzlr.getQ29());
			  dcbzlr.setPro_1(bzlr.getPro_1());		 
			  dcbzlr.setPro_2(bzlr.getPro_2());   
			  dcbzlr.setPro_3(bzlr.getPro_3());   
			  dcbzlr.setPro_4(bzlr.getPro_4());   
			  dcbzlr.setPro_5(bzlr.getPro_5());            
			  dcbzlr.setPro_6(bzlr.getPro_6());   
			  dcbzlr.setPro_7(bzlr.getPro_7());   
			  dcbzlr.setPro_8(bzlr.getPro_8());   
			  dcbzlr.setPro_9(bzlr.getPro_9());   
			  dcbzlr.setPro_10(bzlr.getPro_10());   
			  dcbzlr.setPro_11(bzlr.getPro_11());   
			  dcbzlr.setPro_12(bzlr.getPro_12());   
			  dcbzlr.setPro_13(bzlr.getPro_13());   
			  dcbzlr.setYefy_1(bzlr.getYefy_1());   
			  dcbzlr.setYefy_2(bzlr.getYefy_2());   
			  dcbzlr.setYefy_3(bzlr.getYefy_3());   
			  dcbzlr.setYefy_4(bzlr.getYefy_4());   
			  dcbzlr.setYefy_5(bzlr.getYefy_5());   
			  dcbzlr.setYefy_6(bzlr.getYefy_6());   
			  dcbzlr.setYefy_7(bzlr.getYefy_7());   
			  dcbzlr.setYefy_8(bzlr.getYefy_8());   
			  dcbzlr.setYefy_9(bzlr.getYefy_9());  
			  dcbzlr.setYefy_10(bzlr.getYefy_10());   
			  dcbzlr.setYefy_11(bzlr.getYefy_11());   
			  dcbzlr.setYefy_12(bzlr.getYefy_12());   
			  dcbzlr.setYefy_13(bzlr.getYefy_13());   
			  dcbzlr.setYefy_14(bzlr.getYefy_14());   
			  dcbzlr.setYefy_15(bzlr.getYefy_15()); 
				addIntoPiecesService.updateDcbzlr(dcbzlr);
			}else{
				addIntoPiecesService.saveDcbzlr(bzlr);
			}
			result.put("result", "success");
		} catch (Exception e) {
			result.put("result", "fail");
		}
		//response
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}

	
	/**
	 * 现金流表
	 * 查询现金流表
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/custAppInfo/findxjllb.json", method = { RequestMethod.GET })
	public String findxjllb(HttpServletRequest request) {
		String customerId = request.getParameter("customerId");
		String productId = request.getParameter("productId");
		
		List<CustomerInforUpdateCashFlow> customerInforUpdateCashFlow =
                customerInforUpdateService.getCustomerInforUpdateCashFlowById1(customerId,productId);
		//response
		Map<String,Object> result = new LinkedHashMap<String,Object>();
		result.put("result", customerInforUpdateCashFlow);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}
	
	/**
	 * 现金流表
	 * 新增和保存现金流量表
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/custAppInfo/saveOrupdatexjllb.json", method = { RequestMethod.GET })
	public String saveOrupdatexjllb(HttpServletRequest request) {
		Map<String,Object> result = new LinkedHashMap<String,Object>();
		try {
			String customerId=request.getParameter("customerId");
			String productId=request.getParameter("productId");
			customerInforUpdateService.insertCustomerInforUpdateCashFlow1(customerId, request,productId);

			List<CustomerInforUpdateCashFlow> customerInforUpdateCashFlow =
                    customerInforUpdateService.getCustomerInforUpdateCashFlowById1(customerId,productId);
			result.put("result", customerInforUpdateCashFlow);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "fail");
		}
		//response
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}
	
}
