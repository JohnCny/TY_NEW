package com.cardpay.pccredit.customer.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
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

import com.cardpay.pccredit.customer.constant.CustomerInforConstant;
import com.cardpay.pccredit.customer.model.BusinessTackling;
import com.cardpay.pccredit.customer.model.CustomerFirsthendBaseLocal;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.model.CustomerMarketing;
import com.cardpay.pccredit.customer.service.BusinessTacklingService;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.customer.service.MaintenanceService;
import com.cardpay.pccredit.intopieces.filter.AddIntoPiecesFilter;
import com.cardpay.pccredit.intopieces.filter.IntoPiecesFilter;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationInfo;
import com.cardpay.pccredit.intopieces.model.IntoPieces;
import com.cardpay.pccredit.intopieces.model.LocalExcel;
import com.cardpay.pccredit.intopieces.model.LocalImage;
import com.cardpay.pccredit.intopieces.service.AddIntoPiecesService;
import com.cardpay.pccredit.intopieces.service.IntoPiecesService;
import com.cardpay.pccredit.intopieces.web.AddIntoPiecesForm;
import com.cardpay.pccredit.intopieces.web.LocalImageForm;
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

@Controller
@RequestMapping("/customer/customerInfor_wh_ty/*")
@JRadModule("customer.customerInfor_wh_ty")
public class CustomerInfo_wh_TY_Controller extends BaseController {
	
	final public static String AREA_SEPARATOR  = "_";

	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private CustomerInforService customerInforService;

	@Autowired
	private IntoPiecesService intoPiecesService;

	@Autowired
	private AddIntoPiecesService addIntoPiecesService;
	
	@Autowired
	private MaintenanceService maintenanceService;
	
	@Autowired
	private BusinessTacklingService btService;
	//太原维护信息
	@ResponseBody
	@RequestMapping(value = "browse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browse(@ModelAttribute IntoPiecesFilter filter,HttpServletRequest request) {
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
		QueryResult<IntoPieces> result = intoPiecesService.findintoPiecesByFilter(filter);
		JRadPagedQueryResult<IntoPieces> pagedResult = new JRadPagedQueryResult<IntoPieces>(filter, result);

		JRadModelAndView mv = new JRadModelAndView("/customer/customerInfor_wh_ty/intopieces_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);

		return mv;
	}
	
	//显示原始信息
	@ResponseBody
	@RequestMapping(value = "showInfo.page")
	public AbstractModelAndView showInfo(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/customer/customerInfor_wh_ty/customer_iframe_1_change", request);
		String customerInforId = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(customerInforId)) {
			CustomerInfor info = customerInforService.findCustomerInforById(customerInforId);
			CustomerFirsthendBaseLocal baseLocal = customerInforService.findCustomerFirsthendLocalByNm(info.getTyCustomerId());
			mv.addObject("customerInfor", baseLocal);
			if(baseLocal!=null){
				mv.addObject("customerNm", baseLocal.getKhnm());
			}
			mv.addObject("customerInforId", customerInforId);
			
			//查询权限 非本人只能查看 不能操作
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			String userId = user.getId();
			boolean lock = false;
			if(!customerInforService.findCustomerInforById(customerInforId).getUserId().equals(userId)){
				lock = true;
			}
			mv.addObject("lock", lock);
		}
		return mv;
	}
	
	//显示原始信息
	@ResponseBody
	@RequestMapping(value = "saveTyLocal.json")
	public JRadReturnMap saveTyLocal(@ModelAttribute CustomerFirsthendBaseLocalForm form,HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			CustomerFirsthendBaseLocal customerFirsthendBaseLocal = form.createModel(CustomerFirsthendBaseLocal.class);
			customerInforService.updateCustomerFirsthendLocal(customerFirsthendBaseLocal);
			returnMap.addGlobalMessage(CHANGE_SUCCESS);
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}

		return returnMap;
	}
	//显示业务核查信息
			@ResponseBody
			@RequestMapping(value = "report_business.page")
			public AbstractModelAndView report_business(HttpServletRequest request) {
				JRadModelAndView mv = new JRadModelAndView("/customer/customerInfor_wh_ty/report_business", request);
				String appId = RequestHelper.getStringValue(request, "appId");
				String urlType = RequestHelper.getStringValue(request, "urlType");
				String idcard=RequestHelper.getStringValue(request, "cardId");
				if (StringUtils.isNotEmpty(appId)) {
					List<BusinessTackling> btlist=btService.queryByIdCard(idcard);
					mv.addObject("btlist",btlist);
					mv.addObject("appId", appId);
					mv.addObject("urlType", urlType);
					mv.addObject("cardId", idcard);
				}
				return mv;
			}
	//显示维护信息--建议
	@ResponseBody
	@RequestMapping(value = "report_jy.page")
	public AbstractModelAndView report_jy(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/customer/customerInfor_wh_ty/report_jy", request);
		String appId = RequestHelper.getStringValue(request, "appId");
		String urlType = RequestHelper.getStringValue(request, "urlType");
		String cardId=RequestHelper.getStringValue(request, "cardId");
		if (StringUtils.isNotEmpty(appId)) {
			LocalExcel localExcel = addIntoPiecesService.findLocalEXcelByApplication(appId);
			String tableContent = getFromBASE64(localExcel.getSheetJy()).replaceAll("\n", "<br>").replace("><br><", "><");
			mv.addObject("tableContent", tableContent);
			mv.addObject("appId", appId);
			mv.addObject("urlType", urlType);
			mv.addObject("cardId",cardId);
			//查询权限 非本人只能查看 不能操作
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			String userId = user.getId();
			boolean lock = false;
			if(!customerInforService.findCustomerInforById(localExcel.getCustomerId()).getUserId().equals(userId)){
				lock = true;
			}
			mv.addObject("lock", lock);
		}
		return mv;
	}
	
	//修改--建议
	@ResponseBody
	@RequestMapping(value = "change_report_jy.json")
	public JRadReturnMap change_report_jy(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		String appId = RequestHelper.getStringValue(request, "appId");
		String applyQuota = RequestHelper.getStringValue(request, "applyQuota");
		try {
			LocalExcel localExcel = addIntoPiecesService.findLocalEXcelByApplication(appId);
			localExcel.setSheetJy(request.getParameter("content"));
			addIntoPiecesService.change_localExcel(localExcel);
			CustomerApplicationInfo app = new CustomerApplicationInfo();
			app.setId(appId);
			app.setApplyQuota(applyQuota);
			addIntoPiecesService.changeApproveValue(app);
			returnMap.addGlobalMessage(CHANGE_SUCCESS);
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}

		return returnMap;
	}
	
	//显示维护信息--进件标识
	@ResponseBody
	@RequestMapping(value = "report_jjbs.page")
	public AbstractModelAndView report_jjbs(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/customer/customerInfor_wh_ty/report_jjbs", request);
		String appId = RequestHelper.getStringValue(request, "appId");
		String urlType = RequestHelper.getStringValue(request, "urlType");
		String cardId=RequestHelper.getStringValue(request, "cardId");
		if (StringUtils.isNotEmpty(appId)) {
			LocalExcel localExcel = addIntoPiecesService.findLocalEXcelByApplication(appId);
			String tableContent = getFromBASE64(localExcel.getSheetJjbs()).replaceAll("\n", "<br>").replace("><br><", "><");
			mv.addObject("tableContent", tableContent);
			mv.addObject("appId", appId);
			mv.addObject("urlType", urlType);
			mv.addObject("cardId",cardId);
			//查询权限 非本人只能查看 不能操作
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			String userId = user.getId();
			boolean lock = false;
			if(!customerInforService.findCustomerInforById(localExcel.getCustomerId()).getUserId().equals(userId)){
				lock = true;
			}
			mv.addObject("lock", lock);
		}
		return mv;
	}
	
	//修改--进件标识
	@ResponseBody
	@RequestMapping(value = "change_report_jjbs.json")
	public JRadReturnMap change_report_jjbs(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		String appId = RequestHelper.getStringValue(request, "appId");
		try {
			LocalExcel localExcel = addIntoPiecesService.findLocalEXcelByApplication(appId);
			localExcel.setSheetJjbs(request.getParameter("content"));
			addIntoPiecesService.change_localExcel(localExcel);
			
			returnMap.addGlobalMessage(CHANGE_SUCCESS);
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}

		return returnMap;
	}
		
	//显示维护信息--基本状况
	@ResponseBody
	@RequestMapping(value = "report_jbzk.page")
	public AbstractModelAndView report_jbzk(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/customer/customerInfor_wh_ty/report_jbzk", request);
		String appId = RequestHelper.getStringValue(request, "appId");
		String urlType = RequestHelper.getStringValue(request, "urlType");
		String cardId=RequestHelper.getStringValue(request, "cardId");
		if (StringUtils.isNotEmpty(appId)) {
			LocalExcel localExcel = addIntoPiecesService.findLocalEXcelByApplication(appId);
			String tableContent = getFromBASE64(localExcel.getSheetJbzk()).replaceAll("\n", "<br>").replace("><br><", "><");
			mv.addObject("tableContent", tableContent);
			mv.addObject("appId", appId);
			mv.addObject("urlType", urlType);
			mv.addObject("cardId",cardId);
			//查询权限 非本人只能查看 不能操作
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			String userId = user.getId();
			boolean lock = false;
			if(!customerInforService.findCustomerInforById(localExcel.getCustomerId()).getUserId().equals(userId)){
				lock = true;
			}
			mv.addObject("lock", lock);
		}
		return mv;
	}
	
	//修改--基本状况
	@ResponseBody
	@RequestMapping(value = "change_report_jbzk.json")
	public JRadReturnMap change_report_jbzk(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		String appId = RequestHelper.getStringValue(request, "appId");
		try {
			LocalExcel localExcel = addIntoPiecesService.findLocalEXcelByApplication(appId);
			localExcel.setSheetJbzk(request.getParameter("content"));
			addIntoPiecesService.change_localExcel(localExcel);
			
			returnMap.addGlobalMessage(CHANGE_SUCCESS);
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}

		return returnMap;
	}
	
	//显示维护信息--经营状态
	@ResponseBody
	@RequestMapping(value = "report_jyzt.page")
	public AbstractModelAndView report_jyzt(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/customer/customerInfor_wh_ty/report_jyzt", request);
		String appId = RequestHelper.getStringValue(request, "appId");
		String urlType = RequestHelper.getStringValue(request, "urlType");
		String cardId=RequestHelper.getStringValue(request, "cardId");
		if (StringUtils.isNotEmpty(appId)) {
			LocalExcel localExcel = addIntoPiecesService.findLocalEXcelByApplication(appId);
			String tableContent = getFromBASE64(localExcel.getSheetJyzt()).replaceAll("\n", "<br>").replace("><br><", "><");
			mv.addObject("tableContent", tableContent);
			mv.addObject("appId", appId);
			mv.addObject("urlType", urlType);
			mv.addObject("cardId",cardId);
			//查询权限 非本人只能查看 不能操作
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			String userId = user.getId();
			boolean lock = false;
			if(!customerInforService.findCustomerInforById(localExcel.getCustomerId()).getUserId().equals(userId)){
				lock = true;
			}
			mv.addObject("lock", lock);
		}
		return mv;
	}
	
	//修改--经营状态
	@ResponseBody
	@RequestMapping(value = "change_report_jyzt.json")
	public JRadReturnMap change_report_jyzt(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		String appId = RequestHelper.getStringValue(request, "appId");
		try {
			LocalExcel localExcel = addIntoPiecesService.findLocalEXcelByApplication(appId);
			localExcel.setSheetJyzt(request.getParameter("content"));
			addIntoPiecesService.change_localExcel(localExcel);
			
			returnMap.addGlobalMessage(CHANGE_SUCCESS);
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}

		return returnMap;
	}
		
	//显示维护信息--生存状态
	@ResponseBody
	@RequestMapping(value = "report_sczt.page")
	public AbstractModelAndView report_sczt(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/customer/customerInfor_wh_ty/report_sczt", request);
		String appId = RequestHelper.getStringValue(request, "appId");
		String urlType = RequestHelper.getStringValue(request, "urlType");
		String cardId=RequestHelper.getStringValue(request, "cardId");
		if (StringUtils.isNotEmpty(appId)) {
			LocalExcel localExcel = addIntoPiecesService.findLocalEXcelByApplication(appId);
			String tableContent = getFromBASE64(localExcel.getSheetSczt()).replaceAll("\n", "<br>").replace("><br><", "><");
			mv.addObject("tableContent", tableContent);
			mv.addObject("appId", appId);
			mv.addObject("urlType", urlType);
			mv.addObject("cardId",cardId);
			//查询权限 非本人只能查看 不能操作
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			String userId = user.getId();
			boolean lock = false;
			if(!customerInforService.findCustomerInforById(localExcel.getCustomerId()).getUserId().equals(userId)){
				lock = true;
			}
			mv.addObject("lock", lock);
		}
		return mv;
	}
	
	//修改--生存状态
	@ResponseBody
	@RequestMapping(value = "change_report_sczt.json")
	public JRadReturnMap change_report_sczt(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		String appId = RequestHelper.getStringValue(request, "appId");
		try {
			LocalExcel localExcel = addIntoPiecesService.findLocalEXcelByApplication(appId);
			localExcel.setSheetSczt(request.getParameter("content"));
			addIntoPiecesService.change_localExcel(localExcel);
			
			returnMap.addGlobalMessage(CHANGE_SUCCESS);
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}

		return returnMap;
	}
	
	//显示维护信息--道德品质
	@ResponseBody
	@RequestMapping(value = "report_ddpz.page")
	public AbstractModelAndView report_ddpz(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/customer/customerInfor_wh_ty/report_ddpz", request);
		String appId = RequestHelper.getStringValue(request, "appId");
		String urlType = RequestHelper.getStringValue(request, "urlType");
		String cardId=RequestHelper.getStringValue(request, "cardId");
		if (StringUtils.isNotEmpty(appId)) {
			LocalExcel localExcel = addIntoPiecesService.findLocalEXcelByApplication(appId);
			String tableContent = getFromBASE64(localExcel.getSheetDdpz()).replaceAll("\n", "<br>").replace("><br><", "><");
			mv.addObject("tableContent", tableContent);
			mv.addObject("appId", appId);
			mv.addObject("urlType", urlType);
			mv.addObject("cardId",cardId);
			//查询权限 非本人只能查看 不能操作
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			String userId = user.getId();
			boolean lock = false;
			if(!customerInforService.findCustomerInforById(localExcel.getCustomerId()).getUserId().equals(userId)){
				lock = true;
			}
			mv.addObject("lock", lock);
		}
		return mv;
	}
	
	//修改--道德品质
	@ResponseBody
	@RequestMapping(value = "change_report_ddpz.json")
	public JRadReturnMap change_report_ddpz(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		String appId = RequestHelper.getStringValue(request, "appId");
		try {
			LocalExcel localExcel = addIntoPiecesService.findLocalEXcelByApplication(appId);
			localExcel.setSheetDdpz(request.getParameter("content"));
			addIntoPiecesService.change_localExcel(localExcel);
			
			returnMap.addGlobalMessage(CHANGE_SUCCESS);
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}

		return returnMap;
	}
		
	//显示维护信息--资产负债
	@ResponseBody
	@RequestMapping(value = "report_fz.page")
	public AbstractModelAndView report_fz(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/customer/customerInfor_wh_ty/report_fz", request);
		String appId = RequestHelper.getStringValue(request, "appId");
		String urlType = RequestHelper.getStringValue(request, "urlType");
		String cardId=RequestHelper.getStringValue(request, "cardId");
		if (StringUtils.isNotEmpty(appId)) {
			LocalExcel localExcel = addIntoPiecesService.findLocalEXcelByApplication(appId);
			String tableContent = getFromBASE64(localExcel.getSheetFz()).replaceAll("\n", "<br>").replace("><br><", "><");
			mv.addObject("tableContent", tableContent);
			mv.addObject("appId", appId);
			mv.addObject("urlType", urlType);
			mv.addObject("cardId",cardId);
			//查询权限 非本人只能查看 不能操作
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			String userId = user.getId();
			boolean lock = false;
			if(!customerInforService.findCustomerInforById(localExcel.getCustomerId()).getUserId().equals(userId)){
				lock = true;
			}
			mv.addObject("lock", lock);
		}
		return mv;
	}
	
	//修改--资产负债
	@ResponseBody
	@RequestMapping(value = "change_report_fz.json")
	public JRadReturnMap change_report_fz(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		String appId = RequestHelper.getStringValue(request, "appId");
		try {
			LocalExcel localExcel = addIntoPiecesService.findLocalEXcelByApplication(appId);
			localExcel.setSheetFz(request.getParameter("content"));
			addIntoPiecesService.change_localExcel(localExcel);
			
			returnMap.addGlobalMessage(CHANGE_SUCCESS);
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}

		return returnMap;
	}
	
	//显示维护信息--利润简表
	@ResponseBody
	@RequestMapping(value = "report_lrjb.page")
	public AbstractModelAndView report_lrjb(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/customer/customerInfor_wh_ty/report_lrjb", request);
		String appId = RequestHelper.getStringValue(request, "appId");
		String urlType = RequestHelper.getStringValue(request, "urlType");
		String cardId=RequestHelper.getStringValue(request, "cardId");
		if (StringUtils.isNotEmpty(appId)) {
			LocalExcel localExcel = addIntoPiecesService.findLocalEXcelByApplication(appId);
			String tableContent = getFromBASE64(localExcel.getSheetLrjb()).replaceAll("\n", "<br>").replace("><br><", "><");
			mv.addObject("tableContent", tableContent);
			mv.addObject("appId", appId);
			mv.addObject("urlType", urlType);
			mv.addObject("cardId",cardId);
			//查询权限 非本人只能查看 不能操作
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			String userId = user.getId();
			boolean lock = false;
			if(!customerInforService.findCustomerInforById(localExcel.getCustomerId()).getUserId().equals(userId)){
				lock = true;
			}
			mv.addObject("lock", lock);
		}
		return mv;
	}
	
	//修改--利润简表
	@ResponseBody
	@RequestMapping(value = "change_report_lrjb.json")
	public JRadReturnMap change_report_lrjb(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		String appId = RequestHelper.getStringValue(request, "appId");
		try {
			LocalExcel localExcel = addIntoPiecesService.findLocalEXcelByApplication(appId);
			localExcel.setSheetLrjb(request.getParameter("content"));
			addIntoPiecesService.change_localExcel(localExcel);
			
			returnMap.addGlobalMessage(CHANGE_SUCCESS);
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}

		return returnMap;
	}
	
	//显示维护信息--标准利润表
	@ResponseBody
	@RequestMapping(value = "report_bzlrb.page")
	public AbstractModelAndView report_bzlrb(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/customer/customerInfor_wh_ty/report_bzlrb", request);
		String appId = RequestHelper.getStringValue(request, "appId");
		String urlType = RequestHelper.getStringValue(request, "urlType");
		String cardId=RequestHelper.getStringValue(request, "cardId");
		if (StringUtils.isNotEmpty(appId)) {
			LocalExcel localExcel = addIntoPiecesService.findLocalEXcelByApplication(appId);
			String tableContent = getFromBASE64(localExcel.getSheetBzlrb()).replaceAll("\n", "<br>").replace("><br><", "><");
			mv.addObject("tableContent", tableContent);
			mv.addObject("appId", appId);
			mv.addObject("urlType", urlType);
			mv.addObject("cardId",cardId);
			//查询权限 非本人只能查看 不能操作
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			String userId = user.getId();
			boolean lock = false;
			if(!customerInforService.findCustomerInforById(localExcel.getCustomerId()).getUserId().equals(userId)){
				lock = true;
			}
			mv.addObject("lock", lock);
		}
		return mv;
	}
	
	//修改--标准利润表
	@ResponseBody
	@RequestMapping(value = "change_report_bzlrb.json")
	public JRadReturnMap change_report_bzlrb(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		String appId = RequestHelper.getStringValue(request, "appId");
		try {
			LocalExcel localExcel = addIntoPiecesService.findLocalEXcelByApplication(appId);
			localExcel.setSheetBzlrb(request.getParameter("content"));
			addIntoPiecesService.change_localExcel(localExcel);
			
			returnMap.addGlobalMessage(CHANGE_SUCCESS);
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}

		return returnMap;
	}
	//显示维护信息--主营业务
	@ResponseBody
	@RequestMapping(value = "report_zyyw.page")
	public AbstractModelAndView report_zyyw(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/customer/customerInfor_wh_ty/report_zyyw", request);
		String appId = RequestHelper.getStringValue(request, "appId");
		String urlType = RequestHelper.getStringValue(request, "urlType");
		String cardId=RequestHelper.getStringValue(request, "cardId");
		if (StringUtils.isNotEmpty(appId)) {
			LocalExcel localExcel = addIntoPiecesService.findLocalEXcelByApplication(appId);
			String tableContent = getFromBASE64(localExcel.getZyyw()).replaceAll("\n", "<br>").replace("><br><", "><");
			mv.addObject("tableContent", tableContent);
			mv.addObject("appId", appId);
			mv.addObject("urlType", urlType);
			mv.addObject("cardId",cardId);
			//查询权限 非本人只能查看 不能操作
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			String userId = user.getId();
			boolean lock = false;
			if(!customerInforService.findCustomerInforById(localExcel.getCustomerId()).getUserId().equals(userId)){
				lock = true;
			}
			mv.addObject("lock", lock);
		}
		return mv;
	}
	
	//修改--主营业务
	@ResponseBody
	@RequestMapping(value = "change_report_zyyw.json")
	public JRadReturnMap change_report_zyyw(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		String appId = RequestHelper.getStringValue(request, "appId");
		try {
			LocalExcel localExcel = addIntoPiecesService.findLocalEXcelByApplication(appId);
			localExcel.setZyyw(request.getParameter("content"));
			addIntoPiecesService.change_localExcel(localExcel);
			
			returnMap.addGlobalMessage(CHANGE_SUCCESS);
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}

		return returnMap;
	}
	//显示维护信息--现金流量表
	@ResponseBody
	@RequestMapping(value = "report_xjllb.page")
	public AbstractModelAndView report_xjllb(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/customer/customerInfor_wh_ty/report_xjllb", request);
		String appId = RequestHelper.getStringValue(request, "appId");
		String urlType = RequestHelper.getStringValue(request, "urlType");
		String cardId=RequestHelper.getStringValue(request, "cardId");
		if (StringUtils.isNotEmpty(appId)) {
			LocalExcel localExcel = addIntoPiecesService.findLocalEXcelByApplication(appId);
			String tableContent = getFromBASE64(localExcel.getSheetXjllb()).replaceAll("\n", "<br>").replace("><br><", "><");
			mv.addObject("tableContent", tableContent);
			mv.addObject("appId", appId);
			mv.addObject("urlType", urlType);
			mv.addObject("cardId",cardId);
			//查询权限 非本人只能查看 不能操作
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			String userId = user.getId();
			boolean lock = false;
			if(!customerInforService.findCustomerInforById(localExcel.getCustomerId()).getUserId().equals(userId)){
				lock = true;
			}
			mv.addObject("lock", lock);
		}
		return mv;
	}
	
	//修改--现金流量表
	@ResponseBody
	@RequestMapping(value = "change_report_xjllb.json")
	public JRadReturnMap change_report_xjllb(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		String appId = RequestHelper.getStringValue(request, "appId");
		try {
			LocalExcel localExcel = addIntoPiecesService.findLocalEXcelByApplication(appId);
			localExcel.setSheetXjllb(request.getParameter("content"));
			addIntoPiecesService.change_localExcel(localExcel);
			
			returnMap.addGlobalMessage(CHANGE_SUCCESS);
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}

		return returnMap;
	}
	
	//显示维护信息--交叉
	@ResponseBody
	@RequestMapping(value = "report_jc.page")
	public AbstractModelAndView report_jc(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/customer/customerInfor_wh_ty/report_jc", request);
		String appId = RequestHelper.getStringValue(request, "appId");
		String urlType = RequestHelper.getStringValue(request, "urlType");
		String cardId=RequestHelper.getStringValue(request, "cardId");
		if (StringUtils.isNotEmpty(appId)) {
			LocalExcel localExcel = addIntoPiecesService.findLocalEXcelByApplication(appId);
			String tableContent = getFromBASE64(localExcel.getSheetJc()).replaceAll("\n", "<br>").replace("><br><", "><");
			mv.addObject("tableContent", tableContent);
			mv.addObject("appId", appId);
			mv.addObject("urlType", urlType);
			mv.addObject("cardId",cardId);
			//查询权限 非本人只能查看 不能操作
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			String userId = user.getId();
			boolean lock = false;
			if(!customerInforService.findCustomerInforById(localExcel.getCustomerId()).getUserId().equals(userId)){
				lock = true;
			}
			mv.addObject("lock", lock);
		}
		return mv;
	}
	
	//修改--交叉
	@ResponseBody
	@RequestMapping(value = "change_report_jc.json")
	public JRadReturnMap change_report_jc(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		String appId = RequestHelper.getStringValue(request, "appId");
		try {
			LocalExcel localExcel = addIntoPiecesService.findLocalEXcelByApplication(appId);
			localExcel.setSheetJc(request.getParameter("content"));
			addIntoPiecesService.change_localExcel(localExcel);
			
			returnMap.addGlobalMessage(CHANGE_SUCCESS);
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}

		return returnMap;
	}
	
	//显示维护信息--点货单
	@ResponseBody
	@RequestMapping(value = "report_dhd.page")
	public AbstractModelAndView report_dhd(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/customer/customerInfor_wh_ty/report_dhd", request);
		String appId = RequestHelper.getStringValue(request, "appId");
		String urlType = RequestHelper.getStringValue(request, "urlType");
		String cardId=RequestHelper.getStringValue(request, "cardId");
		if (StringUtils.isNotEmpty(appId)) {
			LocalExcel localExcel = addIntoPiecesService.findLocalEXcelByApplication(appId);
			String tableContent = getFromBASE64(localExcel.getSheetDhd()).replaceAll("\n", "<br>").replace("><br><", "><");
			mv.addObject("tableContent", tableContent);
			mv.addObject("appId", appId);
			mv.addObject("urlType", urlType);
			mv.addObject("cardId",cardId);
			//查询权限 非本人只能查看 不能操作
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			String userId = user.getId();
			boolean lock = false;
			if(!customerInforService.findCustomerInforById(localExcel.getCustomerId()).getUserId().equals(userId)){
				lock = true;
			}
			mv.addObject("lock", lock);
		}
		return mv;
	}
	
	//修改--点货单
	@ResponseBody
	@RequestMapping(value = "change_report_dhd.json")
	public JRadReturnMap change_report_dhd(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		String appId = RequestHelper.getStringValue(request, "appId");
		try {
			LocalExcel localExcel = addIntoPiecesService.findLocalEXcelByApplication(appId);
			localExcel.setSheetDhd(request.getParameter("content"));
			addIntoPiecesService.change_localExcel(localExcel);
			
			returnMap.addGlobalMessage(CHANGE_SUCCESS);
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}

		return returnMap;
	}
		
	//显示维护信息--固定资产
	@ResponseBody
	@RequestMapping(value = "report_gdzc.page")
	public AbstractModelAndView report_gdzc(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/customer/customerInfor_wh_ty/report_gdzc", request);
		String appId = RequestHelper.getStringValue(request, "appId");
		String urlType = RequestHelper.getStringValue(request, "urlType");
		String cardId=RequestHelper.getStringValue(request, "cardId");
		if (StringUtils.isNotEmpty(appId)) {
			LocalExcel localExcel = addIntoPiecesService.findLocalEXcelByApplication(appId);
			String tableContent = getFromBASE64(localExcel.getSheetGdzc()).replaceAll("\n", "<br>").replace("><br><", "><");
			mv.addObject("tableContent", tableContent);
			mv.addObject("appId", appId);
			mv.addObject("urlType", urlType);
			mv.addObject("cardId",cardId);
			//查询权限 非本人只能查看 不能操作
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			String userId = user.getId();
			boolean lock = false;
			if(!customerInforService.findCustomerInforById(localExcel.getCustomerId()).getUserId().equals(userId)){
				lock = true;
			}
			mv.addObject("lock", lock);
		}
		return mv;
	}
	
	//修改--固定资产
	@ResponseBody
	@RequestMapping(value = "change_report_gdzc.json")
	public JRadReturnMap change_report_gdzc(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		String appId = RequestHelper.getStringValue(request, "appId");
		try {
			LocalExcel localExcel = addIntoPiecesService.findLocalEXcelByApplication(appId);
			localExcel.setSheetGdzc(request.getParameter("content"));
			addIntoPiecesService.change_localExcel(localExcel);
			
			returnMap.addGlobalMessage(CHANGE_SUCCESS);
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}

		return returnMap;
	}
	
	//显示维护信息--应付预收
	@ResponseBody
	@RequestMapping(value = "report_yfys.page")
	public AbstractModelAndView report_yfys(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/customer/customerInfor_wh_ty/report_yfys", request);
		String appId = RequestHelper.getStringValue(request, "appId");
		String urlType = RequestHelper.getStringValue(request, "urlType");
		String cardId=RequestHelper.getStringValue(request, "cardId");
		if (StringUtils.isNotEmpty(appId)) {
			LocalExcel localExcel = addIntoPiecesService.findLocalEXcelByApplication(appId);
			String tableContent = getFromBASE64(localExcel.getSheetYfys()).replaceAll("\n", "<br>").replace("><br><", "><");
			mv.addObject("tableContent", tableContent);
			mv.addObject("appId", appId);
			mv.addObject("urlType", urlType);
			mv.addObject("cardId",cardId);
			//查询权限 非本人只能查看 不能操作
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			String userId = user.getId();
			boolean lock = false;
			if(!customerInforService.findCustomerInforById(localExcel.getCustomerId()).getUserId().equals(userId)){
				lock = true;
			}
			mv.addObject("lock", lock);
		}
		return mv;
	}
	
	//修改--应付预收
	@ResponseBody
	@RequestMapping(value = "change_report_yfys.json")
	public JRadReturnMap change_report_yfys(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		String appId = RequestHelper.getStringValue(request, "appId");
		try {
			LocalExcel localExcel = addIntoPiecesService.findLocalEXcelByApplication(appId);
			localExcel.setSheetYfys(request.getParameter("content"));
			addIntoPiecesService.change_localExcel(localExcel);
			
			returnMap.addGlobalMessage(CHANGE_SUCCESS);
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}

		return returnMap;
	}
	
	//显示维护信息--应收预付
	@ResponseBody
	@RequestMapping(value = "report_ysyf.page")
	public AbstractModelAndView report_ysyf(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/customer/customerInfor_wh_ty/report_ysyf", request);
		String appId = RequestHelper.getStringValue(request, "appId");
		String urlType = RequestHelper.getStringValue(request, "urlType");
		String cardId=RequestHelper.getStringValue(request, "cardId");
		if (StringUtils.isNotEmpty(appId)) {
			LocalExcel localExcel = addIntoPiecesService.findLocalEXcelByApplication(appId);
			String tableContent = getFromBASE64(localExcel.getSheetYsyf()).replaceAll("\n", "<br>").replace("><br><", "><");
			mv.addObject("tableContent", tableContent);
			mv.addObject("appId", appId);
			mv.addObject("urlType", urlType);
			mv.addObject("cardId",cardId);
			//查询权限 非本人只能查看 不能操作
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			String userId = user.getId();
			boolean lock = false;
			if(!customerInforService.findCustomerInforById(localExcel.getCustomerId()).getUserId().equals(userId)){
				lock = true;
			}
			mv.addObject("lock", lock);
		}
		return mv;
	}
	
	//修改--应收预付
	@ResponseBody
	@RequestMapping(value = "change_report_ysyf.json")
	public JRadReturnMap change_report_ysyf(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		String appId = RequestHelper.getStringValue(request, "appId");
		try {
			LocalExcel localExcel = addIntoPiecesService.findLocalEXcelByApplication(appId);
			localExcel.setSheetYsyf(request.getParameter("content"));
			addIntoPiecesService.change_localExcel(localExcel);
			
			returnMap.addGlobalMessage(CHANGE_SUCCESS);
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}

		return returnMap;
	}
		
	//显示维护信息--流水分析
	@ResponseBody
	@RequestMapping(value = "report_lsfx.page")
	public AbstractModelAndView report_lsfx(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/customer/customerInfor_wh_ty/report_lsfx", request);
		String appId = RequestHelper.getStringValue(request, "appId");
		String urlType = RequestHelper.getStringValue(request, "urlType");
		String cardId=RequestHelper.getStringValue(request, "cardId");
		if (StringUtils.isNotEmpty(appId)) {
			LocalExcel localExcel = addIntoPiecesService.findLocalEXcelByApplication(appId);
			String tableContent = getFromBASE64(localExcel.getSheetLsfx()).replaceAll("\n", "<br>").replace("><br><", "><");
			mv.addObject("tableContent", tableContent);
			mv.addObject("appId", appId);
			mv.addObject("urlType", urlType);
			mv.addObject("cardId",cardId);
			//查询权限 非本人只能查看 不能操作
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			String userId = user.getId();
			boolean lock = false;
			if(!customerInforService.findCustomerInforById(localExcel.getCustomerId()).getUserId().equals(userId)){
				lock = true;
			}
			mv.addObject("lock", lock);
		}
		return mv;
	}
	
	//修改--流水分析
	@ResponseBody
	@RequestMapping(value = "change_report_lsfx.json")
	public JRadReturnMap change_report_lsfx(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		String appId = RequestHelper.getStringValue(request, "appId");
		try {
			LocalExcel localExcel = addIntoPiecesService.findLocalEXcelByApplication(appId);
			localExcel.setSheetLsfx(request.getParameter("content"));
			addIntoPiecesService.change_localExcel(localExcel);
			
			returnMap.addGlobalMessage(CHANGE_SUCCESS);
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}

		return returnMap;
	}
	//显示维护信息--流水分析
	@ResponseBody
	@RequestMapping(value = "report_jyb.page")
	public AbstractModelAndView report_jyb(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/customer/customerInfor_wh_ty/report_jyb", request);
		String appId = RequestHelper.getStringValue(request, "appId");
		String urlType = RequestHelper.getStringValue(request, "urlType");
		String cardId=RequestHelper.getStringValue(request, "cardId");
		if (StringUtils.isNotEmpty(appId)) {
			LocalExcel localExcel = addIntoPiecesService.findLocalEXcelByApplication(appId);
			String tableContent = getFromBASE64(localExcel.getJyb()).replaceAll("\n", "<br>").replace("><br><", "><");
			mv.addObject("tableContent", tableContent);
			mv.addObject("appId", appId);
			mv.addObject("urlType", urlType);
			mv.addObject("cardId",cardId);
			//查询权限 非本人只能查看 不能操作
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			String userId = user.getId();
			boolean lock = false;
			if(customerInforService.findCustomerInforById(localExcel.getCustomerId()).getUserId().equals(userId)){
				lock = true;
			}
			mv.addObject("lock", lock);
		}
		return mv;
	}
	
	//修改--流水分析
	@ResponseBody
	@RequestMapping(value = "change_report_jyb.json")
	public JRadReturnMap change_report_jyb(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		String appId = RequestHelper.getStringValue(request, "appId");
		try {
			LocalExcel localExcel = addIntoPiecesService.findLocalEXcelByApplication(appId);
			localExcel.setJyb(request.getParameter("content"));
			addIntoPiecesService.change_localExcel(localExcel);
			
			returnMap.addGlobalMessage(CHANGE_SUCCESS);
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}

		return returnMap;
	}
	//修改影像资料
	@ResponseBody
	@RequestMapping(value = "imageImport.page")
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView imageImport(@ModelAttribute AddIntoPiecesFilter filter,HttpServletRequest request) {
		filter.setRequest(request);
		QueryResult<LocalImageForm> result = addIntoPiecesService.findLocalImageByApplication(filter);
		JRadPagedQueryResult<LocalImageForm> pagedResult = new JRadPagedQueryResult<LocalImageForm>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/customer/customerInfor_wh_ty/image_import",request);
		mv.addObject(PAGED_RESULT, pagedResult);
		
		//查询权限 非本人只能查看 不能操作
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String userId = user.getId();
		boolean lock = false;
		CustomerApplicationInfo customerApplicationInfo = intoPiecesService.findCustomerApplicationInfoByApplicationId(filter.getApplicationId());
		CustomerInfor infor = customerInforService.findCustomerInforById(customerApplicationInfo.getCustomerId());
		if(infor.getUserId().equals(userId)){
			lock = true;
		}
		mv.addObject("lock", lock);
		
		return mv;
	}
	
	//修改影像资料
	@ResponseBody
	@RequestMapping(value = "imageImportJy.page")
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView imageImportJy(@ModelAttribute AddIntoPiecesFilter filter,HttpServletRequest request) {
		filter.setRequest(request);
		QueryResult<LocalImageForm> result = addIntoPiecesService.findLocalImageByApplication(filter);
		JRadPagedQueryResult<LocalImageForm> pagedResult = new JRadPagedQueryResult<LocalImageForm>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/customer/customerInfor_wh_ty/image_import_jy",request);
		mv.addObject(PAGED_RESULT, pagedResult);
		
		//查询权限 非本人只能查看 不能操作
//		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
//		String userId = user.getId();
//		boolean lock = false;
//		CustomerApplicationInfo customerApplicationInfo = intoPiecesService.findCustomerApplicationInfoByApplicationId(filter.getApplicationId());
//		CustomerInfor infor = customerInforService.findCustomerInforById(customerApplicationInfo.getCustomerId());
//		if(infor.getUserId().equals(userId)){
//			lock = true;
//		}
//		mv.addObject("lock", lock);
		
		return mv;
	}
	
	//上传影像资料
	@ResponseBody
	@RequestMapping(value = "imageImport.json")
	public Map<String, Object> imageImport(@RequestParam(value = "file", required = false) MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws Exception {        
		response.setContentType("text/html;charset=utf-8");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(file==null||file.isEmpty()){
				map.put(JRadConstants.SUCCESS, false);
				map.put(JRadConstants.MESSAGE, CustomerInforConstant.IMPORTEMPTY);
				return map;
			}
			String applicationId = request.getParameter("applicationId");
			CustomerApplicationInfo customerApplicationInfo = intoPiecesService.findCustomerApplicationInfoById(applicationId);
			addIntoPiecesService.importImage(file,customerApplicationInfo.getProductId(),customerApplicationInfo.getCustomerId(),applicationId);
			map.put(JRadConstants.SUCCESS, true);
			map.put(JRadConstants.MESSAGE, CustomerInforConstant.IMPORTSUCCESS);
			JSONObject obj = JSONObject.fromObject(map);
			response.getWriter().print(obj.toString());
		} catch (Exception e) {
			e.printStackTrace();
			map.put(JRadConstants.SUCCESS, false);
			map.put(JRadConstants.MESSAGE, "上传失败:"+e.getMessage());
			JSONObject obj = JSONObject.fromObject(map);
			response.getWriter().print(obj.toString());
		}
		return null;
	}
	
	//审贷纪要
	@ResponseBody
	@RequestMapping(value = "imageImportJy.json")
	public Map<String, Object> imageImportJy(@RequestParam(value = "file", required = false) MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws Exception {        
		response.setContentType("text/html;charset=utf-8");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(file==null||file.isEmpty()){
				map.put(JRadConstants.SUCCESS, false);
				map.put(JRadConstants.MESSAGE, CustomerInforConstant.IMPORTEMPTY);
				return map;
			}
			String applicationId = request.getParameter("applicationId");
			CustomerApplicationInfo customerApplicationInfo = intoPiecesService.findCustomerApplicationInfoById(applicationId);
			addIntoPiecesService.importImage(file,customerApplicationInfo.getProductId(),customerApplicationInfo.getCustomerId(),applicationId);
			map.put(JRadConstants.SUCCESS, true);
			map.put(JRadConstants.MESSAGE, CustomerInforConstant.IMPORTSUCCESS);
			JSONObject obj = JSONObject.fromObject(map);
			response.getWriter().print(obj.toString());
		} catch (Exception e) {
			e.printStackTrace();
			map.put(JRadConstants.SUCCESS, false);
			map.put(JRadConstants.MESSAGE, "上传失败:"+e.getMessage());
			JSONObject obj = JSONObject.fromObject(map);
			response.getWriter().print(obj.toString());
		}
		return null;
	}
	
	//删除影像资料
	@ResponseBody
	@RequestMapping(value = "deleteImage.json")
	public JRadReturnMap deleteImage(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			String id = request.getParameter("id");
			addIntoPiecesService.deleteImage(id);
			
			returnMap.addGlobalMessage(CHANGE_SUCCESS);
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}

		return returnMap;
	}

	//下载影像资料
	@ResponseBody
	@RequestMapping(value = "downLoadYxzl.json",method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.EXPORT)
	public AbstractModelAndView downLoadYxzlById(HttpServletRequest request,HttpServletResponse response){
		try {
			addIntoPiecesService.downLoadYxzlById(response,request.getParameter(ID));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	//下载影像资料
	@ResponseBody
	@RequestMapping(value = "downLoadYxzlJn.json",method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.EXPORT)
	public AbstractModelAndView downLoadYxzlJn(HttpServletRequest request,HttpServletResponse response){
		try {
			addIntoPiecesService.downLoadYxzlJn(response,request.getParameter(ID));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//下载贷后影像
	@ResponseBody
	@RequestMapping(value = "downLoadDh.json",method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.EXPORT)
	public AbstractModelAndView downLoadDh(HttpServletRequest request,HttpServletResponse response){
		try {
			addIntoPiecesService.downLoadDh(response,request.getParameter(ID));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//下载影像资料
	@ResponseBody
	@RequestMapping(value = "downLoadYxzlJns.json",method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.EXPORT)
	public AbstractModelAndView downLoadYxzlJns(HttpServletRequest request,HttpServletResponse response){
		try {
			addIntoPiecesService.downLoadYxzlJns(response,request.getParameter(ID));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//下载影像资料
	@ResponseBody
	@RequestMapping(value = "downLoadYxzlDh.json",method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.EXPORT)
	public AbstractModelAndView downLoadYxzlDh(HttpServletRequest request,HttpServletResponse response){
		try {
			addIntoPiecesService.downLoadYxzlDh(response,request.getParameter(ID));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
		
	//base64解码
	public static String getFromBASE64(String s) { 
    	if (s == null) return null; 
    	BASE64Decoder decoder = new BASE64Decoder(); 
    	try { 
    	byte[] b = decoder.decodeBuffer(s); 
    	return new String(b); 
    	} catch (Exception e) { 
    	return null; 
    	} 
	} 
}

	