package com.cardpay.pccredit.jnpad.web;

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

import com.cardpay.pccredit.common.IdCardValidate;
import com.cardpay.pccredit.customer.constant.CustomerInforConstant;
import com.cardpay.pccredit.customer.filter.CustomerInforFilter;
import com.cardpay.pccredit.datapri.constant.DataPriConstants;
import com.cardpay.pccredit.intopieces.filter.IntoPiecesFilter;
import com.cardpay.pccredit.intopieces.model.IntoPieces;
import com.cardpay.pccredit.ipad.util.JsonDateValueProcessor;
import com.cardpay.pccredit.jnpad.model.CustomerInfo;
import com.cardpay.pccredit.jnpad.model.CustomerInfoForm;
import com.cardpay.pccredit.jnpad.service.JnpadCustomerInfoInsertServ‎ice;
import com.cardpay.pccredit.jnpad.service.JnpadCustomerSelectService;
import com.cardpay.pccredit.jnpad.service.JnpadZongBaoCustomerInsertService;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.constant.JRadConstants;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.modules.privilege.model.User;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
public class JnpadZongBaoCustomerInsertController  extends BaseController{

	
	@Autowired
	private JnpadCustomerInfoInsertServ‎ice JnpadCustomerInfoInsertServ‎ice;
	@Autowired
	private JnpadCustomerSelectService jnpadCustomerSelectService; 
	@Autowired
	private JnpadZongBaoCustomerInsertService jnpadZongBaoCustomerInsertService;
	
	@ResponseBody
	@RequestMapping(value="/ipad/product/zhongbaocustomerInsert.json" ,method = { RequestMethod.GET })
	public String customerInsert(HttpServletRequest request){
		CustomerInfoForm customerinfoForm = new CustomerInfoForm();
		customerinfoForm.setChineseName(request.getParameter("chineseName"));
		customerinfoForm.setCardType(request.getParameter("cardType"));
		customerinfoForm.setCardId(request.getParameter("cardId"));
		customerinfoForm.setSpmc(request.getParameter("spmc"));
		customerinfoForm.setTelephone(request.getParameter("phoneNumber"));
		JRadReturnMap returnMap = new JRadReturnMap();
		if (returnMap.isSuccess()) {
			try {
				CustomerInforFilter filter = new CustomerInforFilter();
				filter.setCardId(customerinfoForm.getCardId());
				//身份证号码验证
				if(customerinfoForm.getCardType()=="0"||customerinfoForm.getCardType().equals("0")){
				String msg = IdCardValidate.IDCardValidate(customerinfoForm.getCardId());
				if(msg !="" && msg != null){
					returnMap.put(JRadConstants.MESSAGE, msg);
					returnMap.put(JRadConstants.SUCCESS, false);
					
					JsonConfig jsonConfig = new JsonConfig();
					jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
					JSONObject json = JSONObject.fromObject(returnMap, jsonConfig);
					return json.toString();
				}
				}
				CustomerInfo ls = jnpadCustomerSelectService.selectCustomerInfoByCardId(filter.getCardId());
				if(ls != null ){
					String message = "";
					String gId = ls.getUserId();
					String managerName = jnpadCustomerSelectService.selectManagerNameById(gId);
					if(gId==null){
						message = "保存失败，此客户已存在，请线下及时联系!";
					}else{
						message = "保存失败，此客户已挂在客户经理【"+managerName+"】名下!";
					}
					returnMap.put(JRadConstants.MESSAGE, message);
					}else{
					returnMap.put(JRadConstants.SUCCESS, true);

				
				CustomerInfo customerinfor = customerinfoForm.createModel(CustomerInfo.class);
//				User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
				User user = new User();
				
				user.setId(request.getParameter("userId"));
				customerinfor.setCreatedBy(user.getId());
//				customerinfor.setUserId(user.getId());
				String id =  JnpadCustomerInfoInsertServ‎ice.customerInforInsert(customerinfor);
				returnMap.put(RECORD_ID, id);
				returnMap.put(JRadConstants.MESSAGE, "添加成功");
					}
			}catch (Exception e) {
				returnMap.put(JRadConstants.MESSAGE, DataPriConstants.SYS_EXCEPTION_MSG);
				returnMap.put(JRadConstants.SUCCESS, false);
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
				JSONObject json = JSONObject.fromObject(returnMap, jsonConfig);
				return json.toString();			
				}
		}else{
			returnMap.setSuccess(false);
			returnMap.addGlobalError(CustomerInforConstant.CREATEERROR);
		}
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(returnMap, jsonConfig);
		return json.toString();
	}
	
	//查询众包进件列表
	@ResponseBody
	@RequestMapping(value="/ipad/product/zhongbaocustomerbrower.json" ,method = { RequestMethod.GET })
	public String customerBrower(HttpServletRequest request){
		
//		String userId =request.getParameter("userId");
		String userId =null;
		List<CustomerInfo> customerList = jnpadZongBaoCustomerInsertService.selectCustomerInfo(userId);
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		Map<String,Object> result = new LinkedHashMap<String,Object>();
	
	    result.put("list", customerList);
	    JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
		
		
	}
	
	//抢单
	@ResponseBody
	@RequestMapping(value="/ipad/product/getcustomerbrower.json" ,method = { RequestMethod.GET })
	public String getCustomer(HttpServletRequest request){
		Map<String,Object> result = new LinkedHashMap<String,Object>();
		try {
			String customerId =request.getParameter("customerId");
			String userId =request.getParameter("userId");
			jnpadZongBaoCustomerInsertService.updateCustomerInfo(customerId,userId);
		} catch (Exception e) {
			// TODO: handle exception
			result.put("mess", "抢单失败！");
		}

	    result.put("mess", "抢单成功！");
	    JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
	    JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
		
		
	}
	
	//查看已发布客户信息
	
	@ResponseBody
	@RequestMapping(value = "/ipad/zhongbaocustomerIntopiece/browse.json", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public String browse( HttpServletRequest request) {
		IntoPiecesFilter filter=new IntoPiecesFilter();
//		filter.setRequest(request);
		String userId = request.getParameter("userId");
		String userType = request.getParameter("userType");
		Integer s =new Integer(userType);
		QueryResult<IntoPieces> result=null;
		//客户经理
		if(s==1){
			filter.setUserId(userId);
		}
		result = jnpadZongBaoCustomerInsertService.findCustomerInfor(filter);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}
	
}
