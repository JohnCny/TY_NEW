package com.cardpay.pccredit.Sx.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
			String startdate=request.getParameter("fdate");
			String enddate=request.getParameter("ldate");
			if(startdate!=null && enddate!=null){
				filter.setFdate(startdate);
				filter.setLdate(enddate);
			}
			QueryResult<SxOutputData> result = service.findSxListByFilter(filter);
			JRadPagedQueryResult<SxOutputData> pagedResult = new JRadPagedQueryResult<SxOutputData>(
					filter, result);
			JRadModelAndView mv = new JRadModelAndView("/SX/sx_browse", request);
			// 统计总笔数、总收息
			List<SxOutputData> list = service.findSxListByFilterNoPage(filter);
			mv.addObject(PAGED_RESULT, pagedResult);
			mv.addObject("totalSx", list);
			return mv;
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
		String startdate=request.getParameter("fdate");
		String enddate=request.getParameter("ldate");
		if(startdate!=null && enddate!=null){
			filter.setFdate(startdate);
			filter.setLdate(enddate);
		}
			QueryResult<SxOutputData> result = service.findje(filter);
			JRadPagedQueryResult<SxOutputData> pagedResult1 = new JRadPagedQueryResult<SxOutputData>(
					filter, result);
			JRadModelAndView mv = new JRadModelAndView("/SX/Jgnsx_browse",
					request);
			/*// 统计总笔数、总收息
			List<SxOutputData> list = service
					.findSxListByFilterNoPage(filter);*/
			mv.addObject(PAGED_RESULT, pagedResult1);
			mv.addObject("team", team);
			mv.addObject("user", user);
			return mv;
		
	}
}

	