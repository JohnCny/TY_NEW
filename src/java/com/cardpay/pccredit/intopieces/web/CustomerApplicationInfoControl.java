package com.cardpay.pccredit.intopieces.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.intopieces.model.CustomerApplicationInfo;
import com.cardpay.pccredit.intopieces.service.CustomerApplicationInfoService;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;

@Controller
@RequestMapping("/intopieces/customerOperation/*")
@JRadModule("intopieces")
public class CustomerApplicationInfoControl extends BaseController {

	@Autowired
	private CustomerApplicationInfoService customerApplicationInfoService;

	/**
	 * 保存客户信息
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
    //TODO save？Insert or Update?
	@ResponseBody
	@RequestMapping(value = "save.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.CREATE)
	public Map<String, Object> save(
			@ModelAttribute CustomerApplicationInfo customerApplicationInfo,HttpServletRequest request) {
        //TODO returnmap or map?
		Map<String, Object> map = new HashMap<String, Object>();

        //TODO 保持统一返回信息,消息内容不要在此出现
		try {
			customerApplicationInfoService.save(customerApplicationInfo);
			map.put(SUCCESS, true);
			map.put(MESSAGE, "保存成功!");
		} catch (Exception e) {
			map.put(SUCCESS, false);
			map.put(MESSAGE, "保存失败!");
			e.printStackTrace();
		}
		return map;
	}

}
