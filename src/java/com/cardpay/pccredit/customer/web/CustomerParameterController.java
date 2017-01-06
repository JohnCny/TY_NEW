package com.cardpay.pccredit.customer.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.customer.model.CustomerParameter;
import com.cardpay.pccredit.customer.model.ParameterInformaion;
import com.cardpay.pccredit.customer.service.CustomerParameterService;
import com.cardpay.pccredit.ipad.model.UserIpad;
import com.cardpay.pccredit.product.model.ProductAttribute;
import com.cardpay.pccredit.product.web.ProductAttributeForm;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;

/**
 * 
 * 描述 ：自然人台账，实现客户经理对客户资料的补填
 * @author 周文杰
 * 2016-8-15 09:40:15
 */
@Controller
@RequestMapping("/customer/customer_parameter/*")
@JRadModule("customer.customer_parameter")
public class CustomerParameterController extends BaseController{
	@Autowired 
	private CustomerParameterService cpService;
	//添加前的查询
	@ResponseBody
	@RequestMapping(value = "addCustomerParameter.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView change( 
			HttpServletRequest request
			) {
		JRadModelAndView mv =null;
		String id=request.getParameter("id");
		mv = new JRadModelAndView("/customer/customerInforInsert/CustomerParameter_create", request);
		List<UserIpad> managers=cpService.queryAllManager();
		List<ParameterInformaion> pInfor=cpService.query(id);
		//List<ProductAttribute> product=cpService.queryProduct();
		mv.addObject("managers", managers);
		//mv.addObject("product", product);
		mv.addObject("pInfor",pInfor);
		return mv;
	}
	
	//添加
	@ResponseBody
	@RequestMapping(value = "addCustomerParameter.json",method = {RequestMethod.POST})
	@JRadOperation(JRadOperation.CREATE)
	public JRadReturnMap addCommity(
		@ModelAttribute ProductAttributeForm productAttributeForm,
		CustomerParameter cp,
		HttpServletRequest request)
	{
		JRadReturnMap returnMap = new JRadReturnMap();
		if (returnMap.isSuccess()) {
			try {	
				String idcard=request.getParameter("idcard");
				List<CustomerParameter> cParameters=cpService.queryByIdCard(idcard);
				if(cParameters.size()>0){
					returnMap.put(MESSAGE, "该数据也被填补");
					return returnMap;
				}
					cpService.addCustomerParameter(cp);
					returnMap.put(MESSAGE, "添加成功");
					
			} catch (Exception e) {
				// TODO: handle exception
				return WebRequestHelper.processException(e);
			}	
	}
		return returnMap;
	}

}
