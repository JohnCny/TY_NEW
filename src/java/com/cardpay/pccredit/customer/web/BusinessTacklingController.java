package com.cardpay.pccredit.customer.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.customer.model.BusinessTackling;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.model.CustomerParameter;
import com.cardpay.pccredit.customer.model.ParameterInformaion;
import com.cardpay.pccredit.customer.service.BusinessTacklingService;
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
import com.cardpay.pccredit.jnpad.model.CustomerInfo;

/**
 * 业务核查
 * 描述 ： 行内现有业务核查
 * @author 周文杰
 *
 * 2016年10月24日14:59:36
 */
@Controller 
@RequestMapping("/customer/amountadjustmentviews/*")
@JRadModule("customer.amountadjustmentview")
public class BusinessTacklingController extends BaseController{
	@Autowired
	private BusinessTacklingService btService;
	@ResponseBody
	@RequestMapping(value = "queryByBusinessTackling.page", method = { RequestMethod.GET})
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView change( 
			HttpServletRequest request
			) {
		JRadModelAndView mv =null;
		String customerId=request.getParameter("customerId");
		CustomerInfo customer=btService.queryById(customerId); 
		String idcard=customer.getCardId();
		List<BusinessTackling> btlist=btService.queryByIdCard(idcard);
		mv = new JRadModelAndView("/customer/customerInfor/BusinessTacking_browse", request);
		mv.addObject("btlist",btlist);
		return mv;
	}
	
	@ResponseBody
	@RequestMapping(value = "queryByIdCard.json", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public JRadReturnMap queryBusinessTackling(HttpServletRequest request)
		{
			JRadReturnMap returnMap = new JRadReturnMap();
			if (returnMap.isSuccess()) {
				try {
					String customerId=request.getParameter("customerId");
					CustomerInfo customer=btService.queryById(customerId); 
					if(null!=customer){
						returnMap.put(MESSAGE, "1");
						return returnMap;
					}
					return returnMap;
				} catch (Exception e) {
					// TODO: handle exception
					return WebRequestHelper.processException(e);
				}	
		}
			return returnMap;
}
}