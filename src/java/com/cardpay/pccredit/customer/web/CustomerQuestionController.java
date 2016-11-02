package com.cardpay.pccredit.customer.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.customer.constant.QuestionConstant;
import com.cardpay.pccredit.customer.filter.CustomerQuestionInfoFilter;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.model.CustomerQuestionInfo;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.customer.service.CustomerQuestionService;
import com.cardpay.pccredit.system.model.Dict;
import com.cardpay.pccredit.system.service.DictService;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;

/**
 * CustomerQuestionController类的描述
 * 
 * @author 王海东
 * @created on 2014-10-31
 * 
 * @version $Id:$
 */

@Controller
@RequestMapping("/customer/basiccustomerQuestion/*")
@JRadModule("customer.basiccustomerQuestion")
public class CustomerQuestionController extends BaseController {

	@Autowired
	private CustomerQuestionService customerQuestionService;

	@Autowired
	private CustomerInforService customerInforService;

	@Autowired
	private DictService dictService;
	/**
	 * 浏览问卷调查页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "create_wjdc.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browse(@ModelAttribute CustomerQuestionInfoFilter filter, HttpServletRequest request) {
		filter.setRequest(request);
		String customerId = request.getParameter("id");
		CustomerInfor customer = customerInforService.findCustomerInforById(customerId);
		List<Dict> questionDict = dictService.findDictByDictType(QuestionConstant.QuestionType);
		List<CustomerQuestionInfoForm> customerQuestionInfo = customerQuestionService.findCustomerQuestionByCustomerId(customerId);
		Map<String, String> codeAnswer = new HashMap<String, String>();
		for (Dict qd : questionDict) {
			codeAnswer.put(qd.getTypeCode(), qd.getTypeName());
		}
        if (customerQuestionInfo !=null && customerQuestionInfo.size()>0) {
        	for (CustomerQuestionInfoForm cq : customerQuestionInfo) {
				cq.setQuestionName(codeAnswer.get(cq.getQuestionCode()));
			}
		}else{
			 customerQuestionInfo = new ArrayList<CustomerQuestionInfoForm>();
			 for (Dict qD : questionDict) {
				CustomerQuestionInfoForm cqi = new CustomerQuestionInfoForm();
				cqi.setQuestionName(qD.getTypeName());
				cqi.setQuestionCode(qD.getTypeCode());
				customerQuestionInfo.add(cqi);
			}
			
		}
		JRadModelAndView mv = new JRadModelAndView("/customer/customerQuestion/customerQuestion", request);
		mv.addObject("customerQuestionInfo", customerQuestionInfo);
		mv.addObject("customer", customer);
		mv.addObject("customerId", customerId);
		return mv;
	}

	
	/**
	 * 显示问卷调查页面快照
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "wjdcclone.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView wjdcclone(@ModelAttribute CustomerQuestionInfoFilter filter, HttpServletRequest request) {
		filter.setRequest(request);
		String customerId = request.getParameter("id");
		String applicationId = request.getParameter("applicationId");
		CustomerInfor customer = customerInforService.findCustomerInforById(customerId);
		List<Dict> questionDict = dictService.findDictByDictType(QuestionConstant.QuestionType);
		List<CustomerQuestionInfoForm> customerQuestionInfo = customerQuestionService.findCustomerQuestionByapplicationId(applicationId);
		Map<String, String> codeAnswer = new HashMap<String, String>();
		for (Dict qd : questionDict) {
			codeAnswer.put(qd.getTypeCode(), qd.getTypeName());
		}
        if (customerQuestionInfo !=null && customerQuestionInfo.size()>0) {
        	for (CustomerQuestionInfoForm cq : customerQuestionInfo) {
				cq.setQuestionName(codeAnswer.get(cq.getQuestionCode()));
			}
		}else{
			 customerQuestionInfo = new ArrayList<CustomerQuestionInfoForm>();
			 for (Dict qD : questionDict) {
				CustomerQuestionInfoForm cqi = new CustomerQuestionInfoForm();
				cqi.setQuestionName(qD.getTypeName());
				cqi.setQuestionCode(qD.getTypeCode());
				customerQuestionInfo.add(cqi);
			}
			
		}
		JRadModelAndView mv = new JRadModelAndView("/customer/customerInforDisplay/customerQuestion", request);
		mv.addObject("customerQuestionInfo", customerQuestionInfo);
		mv.addObject("customer", customer);
		mv.addObject("applicationId", applicationId);
		return mv;
	}

	/**
	 * 问卷调查修改或增加
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insert.json")
	@JRadOperation(JRadOperation.CREATE)
	public JRadReturnMap insert(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			String loginId = user.getId();
			String customerId = request.getParameter("customerId");
			Map<String, String[]> paraMap = request.getParameterMap();
			Iterator<String> it = paraMap.keySet().iterator();
			List<String> qsCodeList = new ArrayList<String>();
			Map<String, String> codeAnswerMap = new HashMap<String, String>();
			while (it.hasNext()) {
				String key = it.next();
				if (key.contains("questionCode_")) {
					String[] quCodes = paraMap.get(key);
					if (quCodes.length > 0) {
						qsCodeList.add(quCodes[0]);
					}
				}
				if (key.contains("_questionAnswer")) {
					String quCode = key.split("_")[0];
					String[] avArr = paraMap.get(key);
					if (avArr.length > 0) {
						codeAnswerMap.put(quCode, avArr[0]);
					}
				}
			}

			List<CustomerQuestionInfo> customerQuestionInfos = new ArrayList<CustomerQuestionInfo>();
			for (String qcode : qsCodeList) {
				CustomerQuestionInfo info = new CustomerQuestionInfo();
				info.setCustomerId(customerId);
				info.setQuestionCode(qcode);
				info.setQuestionAnswer(codeAnswerMap.get(qcode));
				customerQuestionInfos.add(info);

			}
			customerQuestionService.insertOrUpdateCustomerQusetion(customerQuestionInfos, loginId);

			returnMap.addGlobalMessage("修改成功");
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}

		return returnMap;
	}

}
