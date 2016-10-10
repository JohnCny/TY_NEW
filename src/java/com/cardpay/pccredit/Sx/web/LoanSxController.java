package com.cardpay.pccredit.Sx.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import sun.misc.BASE64Decoder;

import com.cardpay.pccredit.Sx.model.SxInputData;
import com.cardpay.pccredit.Sx.model.SxOutputData;
import com.cardpay.pccredit.Sx.service.SxService;
import com.cardpay.pccredit.customer.service.MaintenanceService;
import com.cardpay.pccredit.postLoan.filter.SxFilter;
import com.cardpay.pccredit.postLoan.service.PostLoanService;
import com.cardpay.pccredit.zrrtz.model.IncomingData;
import com.cardpay.pccredit.zrrtz.service.ZrrtzcService;
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

@Controller
@RequestMapping("/Sx/post/*")
@JRadModule("Sx.post")
public class LoanSxController extends BaseController {
	
	final public static String AREA_SEPARATOR  = "_";

	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private PostLoanService postLoanService;
	@Autowired
	private MaintenanceService maintenanceService;
	@Autowired
	private SxService service;
	@Autowired
	private ZrrtzcService service1;
	// 机构收悉查询
	@ResponseBody
	@RequestMapping(value = "SxBrowse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView sxbrowse(@ModelAttribute SxInputData filter,
			HttpServletRequest request) {
		filter.setRequest(request);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String userId = user.getId();
		// 数据库的起始日期和终止日期都是varchar类型所以要先转换成date类型才能比较
		List<IncomingData> date = service1.finddate();
		String date1 = null;
		String fdate = null;
		// 转换好的
		Date fdate1 = null;
		Date transmissionfdate = null;
		Date transmissionldate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 0; i < date.size(); i++) {
			date1 = date.get(i).getFdate();
		}
		// 如果数据v库里面没有日期就直接跳向叶面
		if (date1 != null) {
			try {
				fdate1 = sdf.parse(date1);
				//如果有传入值则把传入值转换成date类型如果没有传入值就直接查询
				if (filter.getFdate() != null && filter.getLdate() != null) {
					transmissionfdate = sdf.parse(filter.getFdate());
					transmissionldate = sdf.parse(filter.getLdate());
				} else {
					QueryResult<SxOutputData> result = service
							.findSxListByFilter(filter);
					JRadPagedQueryResult<SxOutputData> pagedResult = new JRadPagedQueryResult<SxOutputData>(
							filter, result);
					JRadModelAndView mv = new JRadModelAndView("/SX/sx_browse",
							request);
					// 统计总笔数、总收息
					List<SxOutputData> list = service
							.findSxListByFilterNoPage(filter);
					float total = 0;
					float totalSx = 0; 
					for (int i = 0; i < list.size(); i++) {
						total += Integer.parseInt(list.get(i).getCountSx());
						totalSx += Float.parseFloat(list.get(i).getTotal());
						// jkje+=Integer.parseInt(listje.get(i).getJkje());
					}
					mv.addObject(PAGED_RESULT, pagedResult);
					mv.addObject("total", total);
					mv.addObject("totalSx", totalSx);
					return mv;
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 判断有哪些起始日期是在输入的日期时间段之内的
			if (fdate1.after(transmissionfdate)) {
				if (fdate1.before(transmissionldate)) {
					fdate = sdf.format(fdate1);
				}
			}
			filter.setFdate(fdate);
			QueryResult<SxOutputData> result = service
					.findSxListByFilter(filter);
			JRadPagedQueryResult<SxOutputData> pagedResult = new JRadPagedQueryResult<SxOutputData>(
					filter, result);
			JRadModelAndView mv = new JRadModelAndView("/SX/sx_browse", request);
			// 统计总笔数、总收息
			List<SxOutputData> list = service
					.findSxListByFilterNoPage(filter);
			int total = 0;
			float totalSx = 0;
			for (int i = 0; i < list.size(); i++) {
				total += Integer.parseInt(list.get(i).getCountSx());
				totalSx += Float.parseFloat(list.get(i).getTotal());
				// jkje+=Integer.parseInt(listje.get(i).getJkje());
			}
			mv.addObject(PAGED_RESULT, pagedResult);
			mv.addObject("total", total);
			mv.addObject("totalSx", totalSx);
			return mv;
		} else {
			JRadModelAndView mv = new JRadModelAndView("/SX/sx_browse", request);
			return mv;
		}
	}
	
	// 机构内收息
	@ResponseBody
	@RequestMapping(value = "JgnSxBrowse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView sxbrowseJgn(@ModelAttribute SxInputData filter,
			HttpServletRequest request) {
		filter.setRequest(request);
		// 查询团队
		List<SxOutputData> team = service.findteam();
		// 查询客户经理
		List<SxOutputData> user = service.finduser();
		// 数据库的起始日期和终止日期都是varchar类型所以要先转换成date类型才能比较
		List<IncomingData> date = service1.finddate();
		String date1 = null;
		String fdate = null;
		// 转换好的
		Date fdate1 = null;
		Date transmissionfdate = null;
		Date transmissionldate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 0; i < date.size(); i++) {
			date1 = date.get(i).getFdate();
		}
		// 如果数据库里面date没有数据则直接条向叶面
		if (date1 != null) {
			try {
				fdate1 = sdf.parse(date1);
				if (filter.getFdate() != null && filter.getLdate() != null) {
					transmissionfdate = sdf.parse(filter.getFdate());
					transmissionldate = sdf.parse(filter.getLdate());
				} else {
					QueryResult<SxOutputData> result = service.findje(filter);
					JRadPagedQueryResult<SxOutputData> pagedResult1 = new JRadPagedQueryResult<SxOutputData>(
							filter, result);
					JRadModelAndView mv = new JRadModelAndView(
							"/SX/Jgnsx_browse", request);
					// 统计总笔数、总收息
					List<SxOutputData> list = service
							.findSxListByFilterNoPage(filter);
					int total = 0;
					float totalSx = 0;
					for (int i = 0; i < list.size(); i++) {
						total += Integer.parseInt(list.get(i).getCountSx());
						totalSx += Float.parseFloat(list.get(i).getTotal());
					}
					mv.addObject(PAGED_RESULT, pagedResult1);
					mv.addObject("total", total);
					mv.addObject("totalSx", totalSx);
					mv.addObject("team", team);
					mv.addObject("user", user);
					return mv;
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 判断有哪些起始日期是在输入的日期时间段之内的
			if (fdate1.after(transmissionfdate)) {
				if (fdate1.before(transmissionldate)) {
					fdate = sdf.format(fdate1);
				}
			}
			filter.setFdate(fdate);
			QueryResult<SxOutputData> result = service.findje(filter);
			JRadPagedQueryResult<SxOutputData> pagedResult1 = new JRadPagedQueryResult<SxOutputData>(
					filter, result);
			JRadModelAndView mv = new JRadModelAndView("/SX/Jgnsx_browse",
					request);
			// 统计总笔数、总收息
			List<SxOutputData> list = service
					.findSxListByFilterNoPage(filter);
			int total = 0;
			float totalSx = 0;
			for (int i = 0; i < list.size(); i++) {
				total += Integer.parseInt(list.get(i).getCountSx());
				totalSx += Float.parseFloat(list.get(i).getTotal());
			}
			mv.addObject(PAGED_RESULT, pagedResult1);
			mv.addObject("total", total);
			mv.addObject("totalSx", totalSx);
			mv.addObject("team", team);
			mv.addObject("user", user);
			return mv;
		} else {
			JRadModelAndView mv = new JRadModelAndView("/SX/Jgnsx_browse",
					request);
			return mv;
		}
	}
}

	