package com.cardpay.pccredit.customeradd.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.customer.dao.MaintenanceDao;
import com.cardpay.pccredit.customer.filter.MaintenanceFilter;
import com.cardpay.pccredit.customer.service.MaintenanceService;
import com.cardpay.pccredit.customeradd.model.CIPERSONBASINFO;
import com.cardpay.pccredit.customeradd.model.JxglForm;
import com.cardpay.pccredit.customeradd.model.JxglpmForm;
import com.cardpay.pccredit.customeradd.model.MaintenanceForm;
import com.cardpay.pccredit.customeradd.service.KuglService;
import com.cardpay.pccredit.customeradd.service.JxglService;
import com.cardpay.pccredit.jnpad.model.CustomerInfo;
import com.cardpay.pccredit.manager.web.AccountManagerParameterForm;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.constant.JRadConstants;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;
import com.wicresoft.util.web.RequestHelper;
/**
 * 
 * 描述 ：绩效核算
 * @author  郑博
 *
 * 2014-11-10 下午3:32:01
 */
@Controller
@RequestMapping("/manager/jxgl/*")
@JRadModule("manager.jxgl")
public class ManagerJxglController extends BaseController{
	
	@Autowired
	private JxglService service;
	@Autowired
	private MaintenanceService maintenanceService;
	/**
	 * 通过客户经理查询每月绩效
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "browse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView query(@ModelAttribute MaintenanceFilter filter,HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/customeradd/Jxglbrowse", request);
		filter.setRequest(request);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		List<AccountManagerParameterForm>forms=new ArrayList<AccountManagerParameterForm>();
		//查询客户经理
		//不是客户经理的显示全部客户经理
		if(user.getUserType()!=1){
			forms=service.findcustomermanager();
		}else{
			//客户经理组长显示他及手下客户经理  客户经理则只显示自己
			forms = maintenanceService.findSubListManagerByManagerId(user);
		}
		String customerManagerId = filter.getCustomerManagerId();
		QueryResult<JxglForm> result = null;
		//如果页面 传过来有信息怎显示客户经理手下信息
		if(customerManagerId!=null && !customerManagerId.equals("")){
			result = service.findAlljxglList(filter);
		}else{
			//如果页面穿过来没有信息则显示所有客户经理手下信息
			if(forms.size()>0){
				filter.setCustomerManagerIds(forms);
				result = service.findAlljxglList(filter);
			}else{
				//直接返回页面
				return mv;
			}
		}
		
		//当月绩效排名功能  
		int  ffjew = 0;
		int i0=0; 
		JxglpmForm e=new JxglpmForm();
		//lists是档次  lists1是人数
		List<JxglpmForm>lists=new ArrayList<JxglpmForm>();
		List<String>lists1=new ArrayList<String>();
		List<JxglForm>ffje1=result.getItems();
		for (JxglForm ffjeq : ffje1) {
			ffjew=Integer.parseInt(ffjeq.getFfje().trim());
			e.setFpm(ffjew);
			e.setLpm(ffjew+1000);
		//取模用来区分是什么档次      80
			int results=ffjew/1000;
			e.setResults(results);
			if(lists.isEmpty()){
				lists.add(e);
				i0++;
			}else{
				for(JxglpmForm a:lists){
					if(a.getResults()==results){
						//因为ffjew 可能会有多个值 所以这里要给一个循环 用I0来统计人数
						i0++; 
					}
				}
			}
			lists.add(e);
		}
		lists1.add(Integer.toString(i0));
		JRadPagedQueryResult<JxglForm> pagedResult = new JRadPagedQueryResult<JxglForm>(filter, result);
		mv.addObject(PAGED_RESULT, pagedResult);
		mv.addObject("forms", forms);
		mv.addObject("lists", lists);
		mv.addObject("lists1", lists1);
		return mv;
	}

}
