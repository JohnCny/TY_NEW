package com.cardpay.pccredit.riskControl.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.riskControl.filter.AgrCrdXykCunegFilter;
import com.cardpay.pccredit.riskControl.model.AgrCrdXykCuneg;
import com.cardpay.pccredit.riskControl.service.AgrCrdXykCunegService;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;

/**
 * 黑名单数据资料
 * @author chenzhifang
 *
 * 2014-12-22下午4:22:04
 */
@Controller
@RequestMapping("/riskcontrol/agrcrdxykcuneg/*")
@JRadModule("riskcontrol.agrcrdxykcuneg")
public class AgrCrdXykCunegController extends BaseController {
	
	@Autowired
	private AgrCrdXykCunegService agrCrdXykCunegService;
	
	/**
	 * 浏览页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "browse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browse(@ModelAttribute AgrCrdXykCunegFilter filter,HttpServletRequest request) {
        filter.setRequest(request);
        
		QueryResult<AgrCrdXykCuneg> result = agrCrdXykCunegService.findAgrCrdXykCuneg(filter);
		JRadPagedQueryResult<AgrCrdXykCuneg> pagedResult = new JRadPagedQueryResult<AgrCrdXykCuneg>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/riskcontrol/agrcrdxykcuneg/agrcrdxykcuneg_browse",request);
		mv.addObject(PAGED_RESULT, pagedResult);
		return mv;
	}
}
