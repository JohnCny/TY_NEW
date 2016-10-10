/**
 * 
 */
package com.cardpay.pccredit.zrrtz.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.customer.filter.CustomerLoanFilter;
import com.cardpay.pccredit.customer.model.CustomerParameter;
import com.cardpay.pccredit.customer.service.CustomerLoanService;
import com.cardpay.pccredit.customer.service.MaintenanceService;
import com.cardpay.pccredit.customer.web.CustomerLoanForm;
import com.cardpay.pccredit.intopieces.service.IntoPiecesService;
import com.cardpay.pccredit.manager.web.AccountManagerParameterForm;
import com.cardpay.pccredit.zrrtz.Util.ExportExcel;
import com.cardpay.pccredit.zrrtz.dao.ZrrtzDao;
import com.cardpay.pccredit.zrrtz.model.ZrrtzFilter;
import com.cardpay.pccredit.zrrtz.model.IncomingData;
import com.cardpay.pccredit.zrrtz.service.ZrrtzcService;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;

/**
 * 
 * 描述 ：客户经理察看名下客户及填充信息
 * @author 郑博
 *
 * 2014-12-3 下午1:32:13
 */
@Controller
@RequestMapping("/customer/customer_ZRRTZ/*")
@JRadModule("customer.customer_ZRRTZ")
public class CustomerZRRTZController extends BaseController{
	
	@Autowired
	private CustomerLoanService customerLoanService;
	@Autowired
	private MaintenanceService maintenanceService;
	
	@Autowired
	private IntoPiecesService intoPiecesService;
	
	@Autowired
	private ZrrtzcService service;
	
	@Autowired
	private ZrrtzDao dao;
	/**
	 * 浏览借款人信息信息页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "browse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browse(@ModelAttribute CustomerLoanFilter filter, HttpServletRequest request) {
		filter.setRequest(request);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String userId = user.getId();
		//查询客户经理
		List<AccountManagerParameterForm> forms = maintenanceService.findSubListManagerByManagerId(user);
		if(forms != null && forms.size() > 0){
			StringBuffer userIds = new StringBuffer();
			userIds.append("(");
			for(AccountManagerParameterForm form : forms){
				userIds.append("'").append(form.getUserId()).append("'").append(",");
			}
			userIds = userIds.deleteCharAt(userIds.length() - 1);
			userIds.append(")");
			filter.setCustManagerIds(userIds.toString());
		}else{
			filter.setUserId(userId);
		}
		QueryResult<CustomerLoanForm> result = customerLoanService.findCustomerListByFilter(filter);
		JRadPagedQueryResult<CustomerLoanForm> pagedResult = new JRadPagedQueryResult<CustomerLoanForm>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/customer/customerLoan/customer_loan_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);
		return mv;
	}
	
	//查询
	@ResponseBody
	@RequestMapping(value = "zrrtz.json", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView zrrtz(@ModelAttribute  ZrrtzFilter filter, HttpServletRequest request) {
		filter.setRequest(request);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			//数据库的起始日期和终止日期都是varchar类型所以要先转换成date类型才能比较
			List<IncomingData>date= service.finddate();
			String date1 =null;   
			String fdate=null;
			//查出来的date
			Date fdate1 = null;
			//传入的date
			Date transmissionfdate = null;
			Date transmissionldate=null;
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			for (int i = 0; i < date.size(); i++) {
				date1=date.get(i).getFdate();
			}
			//如果数据库里面date不为空则做后面的查询操作如果没有则说明数据库里面没有数据v直接跳向叶面
			if(date1!=null){
			try {
				fdate1=sdf.parse(date1);
				//判断传入的时间是否为空 如果不为空则将其转换为date类型
				if(filter.getFdate()!=null && filter.getLdate()!=null && filter.getFdate()!="" && filter.getLdate()!=""){
					transmissionfdate=sdf.parse(filter.getFdate());
					transmissionldate=sdf.parse(filter.getLdate());
				}
				else{
					QueryResult<IncomingData> result = service.findintoPiecesByFilter(filter,user);
					JRadPagedQueryResult<IncomingData> pagedResult = new JRadPagedQueryResult<IncomingData>(filter, result);
					JRadModelAndView mv = new JRadModelAndView("/customer/customerZRRTZ/zrrtz_browse", request);
					mv.addObject(PAGED_RESULT, pagedResult);
					return mv;
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//判断有哪些起始日期是在输入的日期时间段之内的
			if(fdate1.after(transmissionfdate)){
				if(fdate1.before(transmissionldate)){
					fdate=sdf.format(fdate1);
				}
			}
			filter.setFdate(fdate);
		QueryResult<IncomingData> result = service.findintoPiecesByFilter(filter,user);
		JRadPagedQueryResult<IncomingData> pagedResult = new JRadPagedQueryResult<IncomingData>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/customer/customerZRRTZ/zrrtz_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);
		return mv;
			}
			else{
				JRadModelAndView mv = new JRadModelAndView("/customer/customerZRRTZ/zrrtz_browse", request);
				return mv;
			}
	}
	
	//使用poi方法 导出excel
	@ResponseBody
	@RequestMapping(value = "export.json", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public void zrrtzexport(@ModelAttribute  ZrrtzFilter filter, HttpServletRequest request,HttpServletResponse response) {
		filter.setRequest(request);
		String title="CUSTOMER_PARAMETER信息表";
		String[] rowName={"id","客户经理id","客户名称","客户经理名称","客户id"
				,"身份证号","产品名称","金额","期限"
				,"利率","贷款类型","发放日期","到期日期"
				,"担保人","行业分类","经营内容","经营地址"
				,"主调","辅调","组别","审贷会成员"
				,"贷款方式","是否纳税","归还情况","是否批量"
				,"电话号码","是否转贷","备注"};
		//List<IncomingData> plans = dao.findIntoPiecesList(filter);
		List<CustomerParameter>plans=dao.findpiecesList();
		List<Object[]>  dataList=new ArrayList<Object[]>();
		for (int i=0;i<plans.size();i++) {
			Object[] obj={
					plans.get(i).getCustomerParameterId(),
					plans.get(i).getCustomerManagerId(),
					plans.get(i).getCustomername(),
					plans.get(i).getManagername(),
					plans.get(i).getCustomerId(),
					plans.get(i).getIdcard(),
					plans.get(i).getProductname(),
					plans.get(i).getMoney(),
					plans.get(i).getDeadline(),
					plans.get(i).getInterstrate(),
					plans.get(i).getLoantype(),
					plans.get(i).getProvidedate(),
					plans.get(i).getExpiredate(),
					plans.get(i).getBondsman(),
					plans.get(i).getClassification(),
					plans.get(i).getScopeoperation(),
					plans.get(i).getOperationaddress(),
					plans.get(i).getPrincipal(),
					plans.get(i).getAssist(),
					plans.get(i).getGroupes(),
					plans.get(i).getMembers(),
					plans.get(i).getPatternslend(),
					plans.get(i).getRatepaying(),
					plans.get(i).getGiveback(),
					plans.get(i).getBatchs(),
					plans.get(i).getPhonenumber(),
					plans.get(i).getEnlending(),
					plans.get(i).getRemark()
			};
			dataList.add(obj);
		}
		ExportExcel excel=new ExportExcel(title, rowName, dataList, response);
		try {
			excel.export();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
