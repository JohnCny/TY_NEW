package com.cardpay.pccredit.customeradd.web;

import java.util.ArrayList;
import java.util.Arrays;
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
		JRadPagedQueryResult<JxglForm> pagedResult = new JRadPagedQueryResult<JxglForm>(filter, result);
		mv.addObject(PAGED_RESULT, pagedResult);
		mv.addObject("forms", forms);
		return mv;
	}
	
	/**
	 *察看绩效排名
	 * @param request
	 * @return
	 */
	/*@ResponseBody
	@RequestMapping(value = "xspm.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browse(@ModelAttribute MaintenanceFilter filter,HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/customeradd/xspm", request);
		//当月绩效排名功能  
		int  ffjew = 0;
		int pnum=0;
		//用于循环结束判断
		int pnum1=0;
		//lists是档次  lists1是人数
		List<JxglpmForm>lists=new ArrayList<JxglpmForm>();
		
		List<JxglForm>ffje=service.findalljxgllists(filter);
		for (int i=0;i<ffje.size();i++) {
			JxglpmForm e=new JxglpmForm();
			e.setId(Integer.toString(i));
			String money=ffje.get(i).getFfje();
			String displayname=ffje.get(i).getDisplayname();
			if(ffje.get(i).getFfje()==null){ //如果是后台等人员则处理空为零
				money="0";
			}
			ffjew=Integer.parseInt(money.trim());
		    List<String>name=new ArrayList<String>(); //因为一个档次可能有很多人 故用list存储名字
		    name.add(displayname);
		    e.setDisplayname(name);
			e.setFpm(ffjew);
			e.setLpm(ffjew+10000);
			//取模用来区分是什么档次      80 
			int results=ffjew/10000;
			e.setResult(results);
			if(i!=0){
				for(int ii=0;ii<lists.size();ii++){
					if(lists.get(ii).getResult()==results){ //验证这个档次是否已经存在
						pnum=lists.get(ii).getPnum();
						e.setId(lists.get(ii).getId());
						e.setPnum(pnum+1);
						lists.remove(ii);
						lists.add(e);
						System.out.println(lists);
						System.out.println(e);
						pnum1=pnum+1;
						break;
					}else{
						pnum1=0;
						pnum=0;
						continue;
					}
				}
					if(pnum1==pnum){
						e.setPnum(pnum+1);
						lists.add(e);
					}
				}else{
				e.setPnum(pnum+1);
				lists.add(e);
			}
		}
		mv.addObject("lists", lists);
		return mv;
		
	}*/
	
	
	
	
	/**
	 *察看绩效排名
	 * @param request
	 * @return
	 * 更换档次 206  208
	 * 更换排名根据   195
	 */
	@ResponseBody
	@RequestMapping(value = "xspm.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browse(@ModelAttribute MaintenanceFilter filter,HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/customeradd/xspm", request);
		service.deletexspm();
		//当月绩效排名功能  
		int  ffjew = 0;
		int pnum=0;
		//用于循环结束判断
		int pnum1=0;
		//lists是档次  lists1是人数
		List<JxglpmForm>lists=new ArrayList<JxglpmForm>();
		
		List<JxglForm>ffje=service.findalljxgllist1(filter);
		//List<JxglForm>ffje=service.findalljxgllists(filter);
		for (int i=0;i<ffje.size();i++) {
			JxglpmForm e=new JxglpmForm();
			e.setId(Integer.toString(i));
			String money=ffje.get(i).getFfje();
			String displayname=ffje.get(i).getDisplayname();
			if(ffje.get(i).getFfje()==null){ //如果是后台等人员则处理空为零
				money="0";
			}
			ffjew=Integer.parseInt(money.trim());
		    e.setDisplayname(displayname);
			e.setFpm(ffjew);
			e.setLpm(ffjew+10000);        
			//取模用来区分是什么档次      80 
			int results=ffjew/10000;
			e.setResult(results);
			if(i!=0){
				for(int ii=0;ii<lists.size();ii++){
					if(lists.get(ii).getResult()==results){ //验证这个档次是否已经存在
						pnum=lists.get(ii).getPnum();
						e.setId(lists.get(ii).getId());
						 //当验证成功这个档次存在，名字将在list中得到更新此时要把已经转化成String类型转换回list类型
						//然后再转换回String类型 存储到list中
						
						String linshiname=lists.get(ii).getDisplayname();
						linshiname=StringUtils.strip(linshiname,"[]");
						//因为一个档次可能有很多人 故用list存储名字
						List<String>name=new ArrayList<String>();
						name.add(linshiname);
						name.add(e.getDisplayname()); //这里e.getdisplayname是在循环外面赋值的
						e.setDisplayname(name.toString());
						e.setPnum(pnum+1);
						lists.remove(ii);
						service.updatexspm(e);
						lists.add(e);
						pnum1=pnum+1;
						break;
					}else{
						pnum1=0;
						pnum=0;
						continue;
					}
				}
					if(pnum1==pnum){
						e.setPnum(pnum+1);
						service.insertxspm(e);
						lists.add(e);
					}
				}else{
				e.setPnum(pnum+1);
				service.insertxspm(e);
				lists.add(e);
			}
		}
		List<JxglpmForm>findjxglpmform=service.findJxglpmformOrderbyResult();
		mv.addObject("lists", findjxglpmform);
		return mv;
		
	}
	
}
