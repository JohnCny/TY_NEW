package com.cardpay.pccredit.intopieces.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cardpay.pccredit.Sx.service.SxService;
import com.cardpay.pccredit.common.UploadFileTool;
import com.cardpay.pccredit.customer.constant.CustomerInforConstant;
import com.cardpay.pccredit.customer.filter.CustomerInforFilter;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.model.CustomerInforUpdateBalanceSheet;
import com.cardpay.pccredit.customer.model.CustomerInforUpdateCashFlow;
import com.cardpay.pccredit.customer.model.CustomerInforUpdateCrossExamination;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.customer.service.CustomerInforUpdateService;
import com.cardpay.pccredit.customerappguarantor.model.CustomerApplicationGuarantor;
import com.cardpay.pccredit.customerappguarantor.service.guarantorservice;
import com.cardpay.pccredit.intopieces.filter.AddIntoPiecesFilter;
import com.cardpay.pccredit.intopieces.model.Dcbzlr;
import com.cardpay.pccredit.intopieces.model.DcbzlrForm;
import com.cardpay.pccredit.intopieces.model.Dcddpz;
import com.cardpay.pccredit.intopieces.model.DcddpzForm;
import com.cardpay.pccredit.intopieces.model.Dcdhd;
import com.cardpay.pccredit.intopieces.model.Dcgdzc;
import com.cardpay.pccredit.intopieces.model.Dclrjb;
import com.cardpay.pccredit.intopieces.model.DclrjbForm;
import com.cardpay.pccredit.intopieces.model.Dclsfx;
import com.cardpay.pccredit.intopieces.model.DclsfxForm;
import com.cardpay.pccredit.intopieces.model.Dcsczt;
import com.cardpay.pccredit.intopieces.model.DcscztForm;
import com.cardpay.pccredit.intopieces.model.Dcyfys;
import com.cardpay.pccredit.intopieces.model.Dcysyf;
import com.cardpay.pccredit.intopieces.model.Dzjbzk;
import com.cardpay.pccredit.intopieces.model.Dzjy;
import com.cardpay.pccredit.intopieces.model.Dzjyzt;
import com.cardpay.pccredit.intopieces.model.DzjyztForm;
import com.cardpay.pccredit.intopieces.model.IntoPieces;
import com.cardpay.pccredit.intopieces.model.LocalExcel;
import com.cardpay.pccredit.intopieces.model.MakeCard;
import com.cardpay.pccredit.intopieces.service.AddIntoPiecesService;
import com.cardpay.pccredit.intopieces.service.IntoPiecesService;
import com.cardpay.pccredit.product.filter.ProductFilter;
import com.cardpay.pccredit.product.model.AddressAccessories;
import com.cardpay.pccredit.product.model.AppendixDict;
import com.cardpay.pccredit.product.model.ProductAttribute;
import com.cardpay.pccredit.product.service.ProductService;
import com.cardpay.pccredit.product.web.ProductAttributeForm;
import com.cardpay.pccredit.riskControl.model.RiskCustomer;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.constant.JRadConstants;
import com.wicresoft.jrad.base.database.id.IDGenerator;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.i18n.I18nHelper;
import com.wicresoft.jrad.base.web.DataBindHelper;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;
import com.wicresoft.jrad.base.web.message.Messages;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.jrad.modules.log.model.OperationLog;
import com.wicresoft.jrad.modules.log.service.UserLogService;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;
import com.wicresoft.util.web.RequestHelper;

@Controller
@RequestMapping("/intopieces/addIntopieces/*")
@JRadModule("intopieces.addIntopieces")
public class AddIntoPiecesControl extends BaseController {

	@Autowired
	private IntoPiecesService intoPiecesService;

	@Autowired
	private CustomerInforService customerInforService;

	@Autowired
	private ProductService productService;
	
	@Autowired
	private CustomerInforService customerInforservice;
	
	@Autowired
	private AddIntoPiecesService addIntoPiecesService;
	
	@Autowired
	private CustomerInforUpdateService customerInforUpdateService;
	
	@Autowired
	private UserLogService userLogService;
	
	@Autowired
	private guarantorservice services;
	
	//选择产品
	@ResponseBody
	@RequestMapping(value = "browseProduct.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browseProduct(@ModelAttribute ProductFilter filter, HttpServletRequest request) {
		filter.setRequest(request);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		int customer=productService.findsfjq(user.getId());
		QueryResult<ProductAttribute> result = productService.findProductsByFilter(filter);
		JRadPagedQueryResult<ProductAttribute> pagedResult = new JRadPagedQueryResult<ProductAttribute>(filter, result);

		JRadModelAndView mv = new JRadModelAndView("/intopieces/product_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);
		if(customer>0){
			mv.addObject("mes", "尚有贷款未结清");
			}
		return mv;
	}

	/**
	 * 查看是否7天内未归档
	 * @param customerinfoForm
	 * @param request
	 * @RequestParam
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "delay.json")
	@JRadOperation(JRadOperation.CREATE)
	public JRadReturnMap delay(@ModelAttribute ProductFilter filter, HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			Boolean result = productService.delay(user.getId());
			returnMap.setSuccess(result);
		} catch (Exception e) {
			// TODO: handle exception
			returnMap.setSuccess(false);
			returnMap.addGlobalError(CustomerInforConstant.CREATEERROR);
		}
		return returnMap;
	}
	
	//选择客户
	@ResponseBody
	@RequestMapping(value = "browseCustomer.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browseCustomer(@ModelAttribute CustomerInforFilter filter,HttpServletRequest request) {
        filter.setRequest(request);
        IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		filter.setUserId(user.getId());
		QueryResult<CustomerInfor> result = customerInforservice.findCustomerInforByFilterAndProductId(filter);
		JRadPagedQueryResult<CustomerInfor> pagedResult = new JRadPagedQueryResult<CustomerInfor>(filter, result);
		//    /intopieces/customer_browse
		//    /intopieces/customerappguarantor/customerappguarantorhtm
		JRadModelAndView mv = new JRadModelAndView("/intopieces/customer_browse",request);
		mv.addObject(PAGED_RESULT, pagedResult);

		return mv;
	}
	
	//导入调查报告
	@ResponseBody
	@RequestMapping(value = "reportImport.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView reportImport(@ModelAttribute AddIntoPiecesFilter filter,HttpServletRequest request) {
		filter.setRequest(request);
		QueryResult<LocalExcelForm> result = addIntoPiecesService.findLocalExcelByProductAndCustomer1(filter);
		JRadPagedQueryResult<LocalExcelForm> pagedResult = new JRadPagedQueryResult<LocalExcelForm>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/intopieces/report_import",request);
		mv.addObject(PAGED_RESULT, pagedResult);
		
		return mv;
	}
	
	//导入调查报告
	@ResponseBody
	@RequestMapping(value = "reportImport.json")
	public Map<String, Object> reportImport_json(@RequestParam(value = "file", required = false) MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws Exception {        
		response.setContentType("text/html;charset=utf-8");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(file==null||file.isEmpty()){
				map.put(JRadConstants.SUCCESS, false);
				map.put(JRadConstants.MESSAGE, CustomerInforConstant.IMPORTEMPTY);
				return map;
			}
			String productId = request.getParameter("productId");
			String customerId = request.getParameter("customerId");
			addIntoPiecesService.importExcel(file,productId,customerId);
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
	
	//上传影像资料
	@ResponseBody
	@RequestMapping(value = "imageImport.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView imageImport(@ModelAttribute AddIntoPiecesFilter filter,HttpServletRequest request) {
		filter.setRequest(request);
		QueryResult<LocalImageForm> result = addIntoPiecesService.findLocalImageByProductAndCustomer(filter);
		JRadPagedQueryResult<LocalImageForm> pagedResult = new JRadPagedQueryResult<LocalImageForm>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/intopieces/image_import",request);
		mv.addObject(PAGED_RESULT, pagedResult);
		return mv;
	}
	
	//上传影像资料
	@ResponseBody
	@RequestMapping(value = "imageImport.json")
	public Map<String, Object> imageImport(@RequestParam(value = "file", required = false) MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws Exception {        
		response.setContentType("text/html;charset=gbk");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(file==null||file.isEmpty()){
				map.put(JRadConstants.SUCCESS, false);
				map.put(JRadConstants.MESSAGE, CustomerInforConstant.IMPORTEMPTY);
				return map;
			}
			String productId = request.getParameter("productId");
			String customerId = request.getParameter("customerId");
			addIntoPiecesService.importImage(file,productId,customerId,null);
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
	
	//提交申请
	@ResponseBody
	@RequestMapping(value = "addIntopieces.json", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	//JRadReturnMap
	public JRadReturnMap addIntopieces(@ModelAttribute AddIntoPiecesForm addIntoPiecesForm,HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String loginId = user.getId();
		try {
			String m= addIntoPiecesService.addIntopieces(addIntoPiecesForm,loginId);
			//日志记录
			OperationLog ol = new OperationLog();
			ol.setUser_id(loginId);
		    ol.setUser_login(user.getDisplayName());
		    ol.setModule("进件新增");
		    ol.setOperation_result("SUCCESS");
		    ol.setOperation_name("ADD");
		    ol.setIp_address(request.getRemoteAddr());
			userLogService.addUserLog(ol);
			returnMap.put(JRadConstants.MESSAGE, m);
			returnMap.addGlobalMessage(m);
		} catch (Exception e) {
			//日志记录
			OperationLog ol = new OperationLog();
			ol.setUser_id(loginId);
		    ol.setUser_login(user.getDisplayName());
		    ol.setModule("进件新增");
		    ol.setOperation_result("FAIL");
		    ol.setOperation_name("ADD");
		    ol.setIp_address(request.getRemoteAddr());
			userLogService.addUserLog(ol);
			return WebRequestHelper.processException(e);
		}
		return returnMap;
	}
	
	
	@Autowired
	private SxService service;
		//进入担保人信息页面
			@ResponseBody
			@RequestMapping(value = "customerappguarantor.page", method = { RequestMethod.GET })
			@JRadOperation(JRadOperation.BROWSE)
			public AbstractModelAndView customerappguarantorimport(@ModelAttribute AddIntoPiecesFilter Applicationid,CustomerApplicationGuarantor filter,HttpServletRequest request) {
				Applicationid.setRequest(request);
			 //services 因为银行客户担保连一条不能超过5 个人 所以 到五终止
				//显示当前为当前客户担保的担保信息 (根据当前的进件表id)
			 String infoid= Applicationid.getApplicationId();
			 	List<CustomerApplicationGuarantor>lists=service.findguarantor(infoid);
			 	//显示担保连上的信息   Applicationid.getCustomerId();   根据传过来的customerid
			 	String customerid=Applicationid.getCustomerId();
			 	List<CustomerApplicationGuarantor>lists1=service.findguarantorcustomer(customerid);
			 	List<CustomerApplicationGuarantor>lists2=null;
			 	List<CustomerApplicationGuarantor>lists3=null;
			 	List<CustomerApplicationGuarantor>lists4=null;
			 	int a=lists1.size();
			 	//根据查出来的customerid
			 	if(lists1!=null&&lists1.size()!=0){
			 		customerid=lists1.get(0).getId();
			 		lists2=service.findguarantorcustomer(customerid);
			 	}
			 	//4
			 	if(lists2!=null&&lists2.size()!=0){
			 		customerid=lists2.get(0).getId();
			 		lists3=service.findguarantorcustomer(customerid);
			 	}
			 	//5
			 	if(lists2!=null&&lists3.size()!=0){
			 		customerid=lists3.get(0).getId();
			 		lists4=service.findguarantorcustomer(customerid);
			 	}
				
			 	QueryResult<LocalImageForm> result =  addIntoPiecesService.findLocalImageByProductAndCustomer(Applicationid);
				JRadPagedQueryResult<LocalImageForm> pagedResult = new JRadPagedQueryResult<LocalImageForm>(Applicationid, result);
				JRadModelAndView mv = new JRadModelAndView("/intopieces/customerappguarantor/customerappguarantorhtm",request);
				mv.addObject(PAGED_RESULT, pagedResult);
				mv.addObject("lists", lists);
				mv.addObject("lists1", lists1);
				mv.addObject("lists2", lists2);
				mv.addObject("lists3", lists3);
				mv.addObject("lists4", lists4);
				return mv;
			}
			
			//导入担保人信息
			@ResponseBody
			@RequestMapping(value = "insertguarantor.json", method = { RequestMethod.POST })
			@JRadOperation(JRadOperation.BROWSE)
			public JRadReturnMap addguarantor(@ModelAttribute AddIntoPiecesFilter Applicationid,@ModelAttribute CustomerApplicationGuarantor filter,HttpServletRequest request) {
				String name=request.getParameter("guarantorMortgagorPledge");
				JRadReturnMap returnMap = new JRadReturnMap();
				Applicationid.setRequest(request);
				if(null==filter.getId()){
					filter.setId(UUID.randomUUID().toString());
				}
				Log.info(Applicationid.getApplicationId());
				filter.setMainApplicationFormId(Applicationid.getApplicationId());
				//判断此申请主表有多少个 担保人 如果超过五人就提醒客户    少于五人就可以继续添加
				String infoid= Applicationid.getApplicationId();
				int guarantorcount=service.guarantorcount(infoid);
				String message="";
				if(guarantorcount<5){
					service.insertguarantor(filter);
					returnMap.put(JRadConstants.SUCCESS, true);
				}else{
					message="已经超过5个人 ，不能再加担保人了";
					returnMap.put(JRadConstants.MESSAGE, message);
					returnMap.put(JRadConstants.SUCCESS, false);
				}
				return returnMap;
			}
			
	/**
	 * 查询 建议
	 * @param request
	 * @return
	*/
	@ResponseBody
	@RequestMapping(value = "changewh.page")
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView changewh(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/intopieces/cframe", request);
		String customerId = request.getParameter("customerId");
		String productId = request.getParameter("productId");
		Dzjy  dzjy=  addIntoPiecesService.findLocalImageByApplication(customerId,productId);
		mv.addObject("dzjy", dzjy);
		mv.addObject("productId", productId);
		mv.addObject("customerId",customerId);
		return mv;
	}
	
	
	//保存建议
	@ResponseBody
	@RequestMapping(value = "addJy.json", method = { RequestMethod.GET })
	public JRadReturnMap addJy(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			String loginId = user.getId();
			
			String customerId = request.getParameter("customerId");//客户 id
			String productId = request.getParameter("productId");//产品id
			String sqrxm = request.getParameter("sqrxm");
			String sqje = request.getParameter("sqje");
			String dkyt = request.getParameter("dkyt");
			String sqqx = request.getParameter("sqqx");
			String xmzje = request.getParameter("xmzje");
			String zyzj = request.getParameter("zyzj");
			String zyzjly = request.getParameter("zyzjly");
			String change = request.getParameter("change");
			String fxfx_ys = request.getParameter("fxfx_ys");
			String fxfx_ns = request.getParameter("fxfx_ns");
			String fxfx_lxfx = request.getParameter("fxfx_lxfx");
			String khjljy = request.getParameter("khjljy");
			String reason1 = request.getParameter("reason1");
			String jytg_je = request.getParameter("jytg_je");
			String jytg_qx = request.getParameter("jytg_qx");
			String jytg_cp = request.getParameter("jytg_cp");
			String jytg_lv = request.getParameter("jytg_lv");
			String jytg_myhk = request.getParameter("jytg_myhk");
			String jytg_bl = request.getParameter("jytg_bl");
			String jytg_jkr = request.getParameter("jytg_jkr");
			String jytg_khgx = request.getParameter("jytg_khgx");
			String jytg_dbr = request.getParameter("jytg_dbr");
			String jytg_dbrgx = request.getParameter("jytg_dbrgx");
			String jytg_dy = request.getParameter("jytg_dy");
			String jytg_wq = request.getParameter("jytg_wq");
			String zbkhjl_sign = request.getParameter("zbkhjl_sign");
			String xbkhjl_sign = request.getParameter("xbkhjl_sign");
			String rq = request.getParameter("rq");
			String sqr_xm = request.getParameter("sqr_xm");
			String sqr_sex = request.getParameter("sqr_sex");
			String sqr_zjhm = request.getParameter("sqr_zjhm");
			String sqr_hy = request.getParameter("sqr_hy");
			String sqr_hjd = request.getParameter("sqr_hjd");
			String sqr_hjxx = request.getParameter("sqr_hjxx");
			String sqr_xl = request.getParameter("sqr_xl");
			String sqr_mobile = request.getParameter("sqr_mobile");
			String sqr_address = request.getParameter("sqr_address");
			String jzhj_type = request.getParameter("jzhj_type");
			String jzhj_type4_qx = request.getParameter("jzhj_type4_qx");
			String jzhj_type4_zj = request.getParameter("jzhj_type4_zj");
			String jzhj_type_other = request.getParameter("jzhj_type_other");
			String jzhj_mj1 = request.getParameter("jzhj_mj1");
			String jzhj_mj2 = request.getParameter("jzhj_mj2");
			String jzhj_mj3 = request.getParameter("jzhj_mj3");
			String jzhj_zf = request.getParameter("jzhj_zf");
			String jzhj_zf_select = request.getParameter("jzhj_zf_select");
			String jzhj_gj = request.getParameter("jzhj_gj");
			String jzhj_gj_room = request.getParameter("jzhj_gj_room");
			String jzhj_gj_ting = request.getParameter("jzhj_gj_ting");
			String jzhj_jzrq = request.getParameter("jzhj_jzrq");
			String jzhj_dzfs = request.getParameter("jzhj_dzfs");
			String jzhj_select = request.getParameter("jzhj_select");
			String zyfc_num = request.getParameter("zyfc_num");
			String aj_num = request.getParameter("aj_num");
			String fcgmrq = request.getParameter("fcgmrq");
			String fcgmjg = request.getParameter("fcgmjg");
			String fcmj = request.getParameter("fcmj");
			String ajdkye = request.getParameter("ajdkye");
			String address = request.getParameter("address");
			String zycl_num = request.getParameter("zycl_num");
			String dk_num = request.getParameter("dk_num");
			String gmrq = request.getParameter("gmrq");
			String gmjg = request.getParameter("gmjg");
			String cp = request.getParameter("cp");
			String other_work = request.getParameter("other_work");
			String other_income = request.getParameter("other_income");
			String po_name = request.getParameter("po_name");
			String po_code = request.getParameter("po_code");
			String po_mobile = request.getParameter("po_mobile");
			String po_yicome = request.getParameter("po_yicome");
			String po_unit = request.getParameter("po_unit");
			String p_bank_numm = request.getParameter("p_bank_numm");
			String khh_1 = request.getParameter("khh_1");
			String zh1 = request.getParameter("zh1");
			String fzc = request.getParameter("fzc");
			String khh_2 = request.getParameter("khh_2");
			String zh2 = request.getParameter("zh2");
			String fzfz = request.getParameter("fzfz");
			
			String jzhj_type_select = request.getParameter("jzhj_type_select");
			
			//查询建议是否存在customerId和productId
			Dzjy dzjy =  addIntoPiecesService.findLocalImageByApplication(customerId,productId);
			
			if(dzjy != null){
//				dzjy.setCustomer_id(request.getParameter("customerId"));
//				dzjy.setProduct_id(request.getParameter("productId"));
				dzjy.setSqrxm(sqrxm);
				dzjy.setSqje(sqje);
				dzjy.setDkyt(dkyt);
				dzjy.setSqqx(sqqx);
				dzjy.setXmzje(xmzje);
				dzjy.setZyzj(zyzj);
				dzjy.setZyzjly(zyzjly);
				dzjy.setChange(change);
				dzjy.setFxfx_ys(fxfx_ys);
				dzjy.setFxfx_ns(fxfx_ns);
				dzjy.setFxfx_lxfx(fxfx_lxfx);
				dzjy.setKhjljy(khjljy);
				dzjy.setReason1(reason1);
				dzjy.setJytg_je(jytg_je);
				dzjy.setJytg_qx(jytg_qx);
				dzjy.setJytg_cp(jytg_cp);
				dzjy.setJytg_lv(jytg_lv);
				dzjy.setJytg_myhk(jytg_myhk);
				dzjy.setJytg_bl(jytg_bl);
				dzjy.setJytg_jkr(jytg_jkr);
				dzjy.setJytg_khgx(jytg_khgx);
				dzjy.setJytg_dbr(jytg_dbr);
				dzjy.setJytg_dbrgx(jytg_dbrgx);
				dzjy.setJytg_dy(jytg_dy);
				dzjy.setJytg_wq(jytg_wq);
				dzjy.setZbkhjl_sign(zbkhjl_sign);
				dzjy.setXbkhjl_sign(xbkhjl_sign);
				dzjy.setRq(rq);
				dzjy.setSqr_xm(sqr_xm);
				dzjy.setSqr_sex(sqr_sex);
				dzjy.setSqr_zjhm(sqr_zjhm);
				dzjy.setSqr_hy(sqr_hy);
				dzjy.setSqr_hjd(sqr_hjd);
				dzjy.setSqr_hjxx(sqr_hjxx);
				dzjy.setSqr_xl(sqr_xl);
				dzjy.setSqr_mobile(sqr_mobile);
				dzjy.setSqr_address(sqr_address);
				dzjy.setJzhj_type(jzhj_type);
				dzjy.setJzhj_type4_qx(jzhj_type4_qx);
				dzjy.setJzhj_type4_zj(jzhj_type4_zj);
				dzjy.setJzhj_type_other(jzhj_type_other);
				dzjy.setJzhj_mj1(jzhj_mj1);
				dzjy.setJzhj_mj2(jzhj_mj2);
				dzjy.setJzhj_mj3(jzhj_mj3);
				dzjy.setJzhj_zf(jzhj_zf);
				dzjy.setJzhj_zf_select(jzhj_zf_select);
				dzjy.setJzhj_gj(jzhj_gj);
				dzjy.setJzhj_gj_room(jzhj_gj_room);
				dzjy.setJzhj_gj_ting(jzhj_gj_ting);
				dzjy.setJzhj_jzrq(jzhj_jzrq);
				dzjy.setJzhj_dzfs(jzhj_dzfs);
				dzjy.setJzhj_select(jzhj_select);
				dzjy.setZyfc_num(zyfc_num);
				dzjy.setAj_num(aj_num);
				dzjy.setFcgmrq(fcgmrq);
				dzjy.setFcgmjg(fcgmjg);
				dzjy.setFcmj(fcmj);
				dzjy.setAjdkye(ajdkye);
				dzjy.setAddress(address);
				dzjy.setZycl_num(zycl_num);
				dzjy.setDk_num(dk_num);
				dzjy.setGmrq(gmrq);
				dzjy.setGmjg(gmjg);
				dzjy.setCp(cp);
				dzjy.setOther_work(other_work);
				dzjy.setOther_income(other_income);
				dzjy.setPo_name(po_name);
				dzjy.setPo_code(po_code);
				dzjy.setPo_mobile(po_mobile);
				dzjy.setPo_yicome(po_yicome);
				dzjy.setPo_unit(po_unit);
				dzjy.setP_bank_numm(p_bank_numm);
				dzjy.setKhh_1(khh_1);
				dzjy.setZh1(zh1);
				dzjy.setFzc(fzc);
				dzjy.setKhh_2(khh_2);
				dzjy.setZh2(zh2);
				dzjy.setFzfz(fzfz);
				dzjy.setJzhj_type_select(jzhj_type_select);
				//update
				addIntoPiecesService.updateJy(dzjy);
			}else{
				Dzjy dzjy1 = new Dzjy();
				dzjy1.setCustomer_id(request.getParameter("customerId"));
				dzjy1.setProduct_id(request.getParameter("productId"));
				dzjy1.setSqrxm(sqrxm);
				dzjy1.setSqje(sqje);
				dzjy1.setDkyt(dkyt);
				dzjy1.setSqqx(sqqx);
				dzjy1.setXmzje(xmzje);
				dzjy1.setZyzj(zyzj);
				dzjy1.setZyzjly(zyzjly);
				dzjy1.setChange(change);
				dzjy1.setFxfx_ys(fxfx_ys);
				dzjy1.setFxfx_ns(fxfx_ns);
				dzjy1.setFxfx_lxfx(fxfx_lxfx);
				dzjy1.setKhjljy(khjljy);
				dzjy1.setReason1(reason1);
				dzjy1.setJytg_je(jytg_je);
				dzjy1.setJytg_qx(jytg_qx);
				dzjy1.setJytg_cp(jytg_cp);
				dzjy1.setJytg_lv(jytg_lv);
				dzjy1.setJytg_myhk(jytg_myhk);
				dzjy1.setJytg_bl(jytg_bl);
				dzjy1.setJytg_jkr(jytg_jkr);
				dzjy1.setJytg_khgx(jytg_khgx);
				dzjy1.setJytg_dbr(jytg_dbr);
				dzjy1.setJytg_dbrgx(jytg_dbrgx);
				dzjy1.setJytg_dy(jytg_dy);
				dzjy1.setJytg_wq(jytg_wq);
				dzjy1.setZbkhjl_sign(zbkhjl_sign);
				dzjy1.setXbkhjl_sign(xbkhjl_sign);
				dzjy1.setRq(rq);
				dzjy1.setSqr_xm(sqr_xm);
				dzjy1.setSqr_sex(sqr_sex);
				dzjy1.setSqr_zjhm(sqr_zjhm);
				dzjy1.setSqr_hy(sqr_hy);
				dzjy1.setSqr_hjd(sqr_hjd);
				dzjy1.setSqr_hjxx(sqr_hjxx);
				dzjy1.setSqr_xl(sqr_xl);
				dzjy1.setSqr_mobile(sqr_mobile);
				dzjy1.setSqr_address(sqr_address);
				dzjy1.setJzhj_type(jzhj_type);
				dzjy1.setJzhj_type4_qx(jzhj_type4_qx);
				dzjy1.setJzhj_type4_zj(jzhj_type4_zj);
				dzjy1.setJzhj_type_other(jzhj_type_other);
				dzjy1.setJzhj_mj1(jzhj_mj1);
				dzjy1.setJzhj_mj2(jzhj_mj2);
				dzjy1.setJzhj_mj3(jzhj_mj3);
				dzjy1.setJzhj_zf(jzhj_zf);
				dzjy1.setJzhj_zf_select(jzhj_zf_select);
				dzjy1.setJzhj_gj(jzhj_gj);
				dzjy1.setJzhj_gj_room(jzhj_gj_room);
				dzjy1.setJzhj_gj_ting(jzhj_gj_ting);
				dzjy1.setJzhj_jzrq(jzhj_jzrq);
				dzjy1.setJzhj_dzfs(jzhj_dzfs);
				dzjy1.setJzhj_select(jzhj_select);
				dzjy1.setZyfc_num(zyfc_num);
				dzjy1.setAj_num(aj_num);
				dzjy1.setFcgmrq(fcgmrq);
				dzjy1.setFcgmjg(fcgmjg);
				dzjy1.setFcmj(fcmj);
				dzjy1.setAjdkye(ajdkye);
				dzjy1.setAddress(address);
				dzjy1.setZycl_num(zycl_num);
				dzjy1.setDk_num(dk_num);
				dzjy1.setGmrq(gmrq);
				dzjy1.setGmjg(gmjg);
				dzjy1.setCp(cp);
				dzjy1.setOther_work(other_work);
				dzjy1.setOther_income(other_income);
				dzjy1.setPo_name(po_name);
				dzjy1.setPo_code(po_code);
				dzjy1.setPo_mobile(po_mobile);
				dzjy1.setPo_yicome(po_yicome);
				dzjy1.setPo_unit(po_unit);
				dzjy1.setP_bank_numm(p_bank_numm);
				dzjy1.setKhh_1(khh_1);
				dzjy1.setZh1(zh1);
				dzjy1.setFzc(fzc);
				dzjy1.setKhh_2(khh_2);
				dzjy1.setZh2(zh2);
				dzjy1.setFzfz(fzfz);
				dzjy1.setJzhj_type_select(jzhj_type_select);
				//save
				addIntoPiecesService.saveJy(dzjy1);
			}
			returnMap.addGlobalMessage(CHANGE_SUCCESS);
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}

		return returnMap;
	}
	
	
	/**
	 * 查询  基本状况
	 * @param request
	 * @return
	*/
	@ResponseBody
	@RequestMapping(value = "findJbzk.page")
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView findJbzk(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/intopieces/dc_jbzk", request);
		String customerId = request.getParameter("customerId");
		String productId = request.getParameter("productId");
		Dzjbzk jbzk = addIntoPiecesService.findDzjbzk(customerId,productId);
		mv.addObject("jbzk", jbzk);
		mv.addObject("productId", productId);
		mv.addObject("customerId",customerId);
		return mv;
	}
	
	
	/**
	 * add 基本状况
	 */
	@ResponseBody
	@RequestMapping(value = "addJbzk.json", method = { RequestMethod.GET })
	public JRadReturnMap addJbzk(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			String loginId = user.getId();
			String customerId = request.getParameter("customerId");//客户 id
			String productId = request.getParameter("productId");// 产品Id
			

			 String mx                  = request.getParameter("mx");
			 String zj_type             = request.getParameter("zj_type");
			 String mj                  = request.getParameter("mj");
			 String fr                  = request.getParameter("fr");
			 String kzr                 = request.getParameter("kzr");
			 String zb                  = request.getParameter("zb");
			 String gy                  = request.getParameter("gy");
			 String ywfw                = request.getParameter("ywfw");
			 String yezz                = request.getParameter("yezz");
			 String yezz_select         = request.getParameter("yezz_select");
			 String zm                  = request.getParameter("zm");
			 String ksrq                = request.getParameter("ksrq");
			 String address             = request.getParameter("address");
			 String phone               = request.getParameter("phone");
			 String dp_type             = request.getParameter("dp_type");
			 String dp_select           = request.getParameter("dp_select");
			 String dp_qx               = request.getParameter("dp_qx");
			 String dp_zj               = request.getParameter("dp_zj");
			 String dp_other            = request.getParameter("dp_other");
			 String dp_zx               = request.getParameter("dp_zx");
			 String dp_zx_select        = request.getParameter("dp_zx_select");
			 String dp_qy_rq            = request.getParameter("dp_qy_rq");
			 String zh                  = request.getParameter("zh");
			 String m_bank              = request.getParameter("m_bank");
			 String m_zh                = request.getParameter("m_zh");
			 String bank2               = request.getParameter("bank2");
			 String zh2                 = request.getParameter("zh2");
			 String jl                  = request.getParameter("jl");
			 String ms                  = request.getParameter("ms");
			 String zx_zk               = request.getParameter("zx_zk");
			 String zx_select           = request.getParameter("zx_select");
			 String zx_sm               = request.getParameter("zx_sm");
			 String zx_card_num         = request.getParameter("zx_card_num");
			 String zx_creddit          = request.getParameter("zx_creddit");
			 String zx_yy               = request.getParameter("zx_yy");
			 String zx_tz               = request.getParameter("zx_tz");
			 String zx_yq               = request.getParameter("zx_yq");
			 String zx_ls_num           = request.getParameter("zx_ls_num");
			 String zx_wdq_num          = request.getParameter("zx_wdq_num");
			 String zx_dk_yy            = request.getParameter("zx_dk_yy");
			 String zx_yq_qk            = request.getParameter("zx_yq_qk");
			 String zx_dbr_ornot        = request.getParameter("zx_dbr_ornot");
			 String zx_db_ye            = request.getParameter("zx_db_ye");
			 String zx_db_yt            = request.getParameter("zx_db_yt");
			 String zx_db_qx            = request.getParameter("zx_db_qx");
			 String zx_zx_seach_num     = request.getParameter("zx_zx_seach_num");
			 String sqr_family          = request.getParameter("sqr_family");
			 String sqr_family_relation = request.getParameter("sqr_family_relation");
			 String sqr_eco_num         = request.getParameter("sqr_eco_num");
			 String sqr_bad_hobby       = request.getParameter("sqr_bad_hobby");
			 String sqr_bad_habit       = request.getParameter("sqr_bad_habit");
			 String sqr_work_pl         = request.getParameter("sqr_work_pl");
			 String sqr_zz_change_pl    = request.getParameter("sqr_zz_change_pl");
			 String sqr_mark            = request.getParameter("sqr_mark");
			 String sqr_zz_qk           = request.getParameter("sqr_zz_qk");
			 String sqr_bx              = request.getParameter("sqr_bx");
			 String sqr_child_qk        = request.getParameter("sqr_child_qk");
			 String sqr_child_edu       = request.getParameter("sqr_child_edu");
			 String sqr_child_unit      = request.getParameter("sqr_child_unit");
			 String sqr_society_gx      = request.getParameter("sqr_society_gx");
			 String sqr_soc_gy          = request.getParameter("sqr_soc_gy");
			 String sqr_wf              = request.getParameter("sqr_wf");
			 String sqr_syfm            = request.getParameter("sqr_syfm");
			 String sqr_qq_zk           = request.getParameter("sqr_qq_zk");
			 String sqr_jr_pj           = request.getParameter("sqr_jr_pj");
			 String sqr_lj_pj           = request.getParameter("sqr_lj_pj");
			 String sqr_lxr_pj          = request.getParameter("sqr_lxr_pj");
			 String sqr_sy_pj           = request.getParameter("sqr_sy_pj");
			 String sqr_q_person        = request.getParameter("sqr_q_person");
			 String sqr_q_bus           = request.getParameter("sqr_q_bus");
			 String sqr_w_person        = request.getParameter("sqr_w_person");
			 String sqr_w_bus           = request.getParameter("sqr_w_bus");
			 String sqr_ztpj            = request.getParameter("sqr_ztpj");
			 String hy_work             = request.getParameter("hy_work");
			 String hy_xz               = request.getParameter("hy_xz");
			 String hy_fx               = request.getParameter("hy_fx");
			 String hy_qs               = request.getParameter("hy_qs");
			 String hy_zz               = request.getParameter("hy_zz");
			 String hy_ys_gl            = request.getParameter("hy_ys_gl");
			 String hy_gd_gl            = request.getParameter("hy_gd_gl");
			 String hy_pj_sale          = request.getParameter("hy_pj_sale");
			 String hy_pj_jl            = request.getParameter("hy_pj_jl");
			 String hy_gz               = request.getParameter("hy_gz");
			 String hy_fy               = request.getParameter("hy_fy");
			 String hy_yclje            = request.getParameter("hy_yclje");
			 String hy_policy           = request.getParameter("hy_policy");
			 String hy_desc             = request.getParameter("hy_desc");
			 String hy_pj_ml            = request.getParameter("hy_pj_ml");
			 
			 //查询是否存在
			 Dzjbzk jbzk = addIntoPiecesService.findDzjbzk(customerId,productId);
			 if(jbzk!=null){
				 //update
				 jbzk.setMx(mx);                 
				 jbzk.setZj_type(zj_type);             
				 jbzk.setMj(mj);                  
				 jbzk.setFr(fr);                  
				 jbzk.setKzr(kzr);                 
				 jbzk.setZb(zb);                  
				 jbzk.setGy(sqr_soc_gy);                  
				 jbzk.setYwfw(ywfw);                
				 jbzk.setYezz(yezz_select);                
				 jbzk.setYezz_select(yezz_select);         
				 jbzk.setZm(zm);                  
				 jbzk.setKsrq(ksrq);               
				 jbzk.setAddress(address);             
				 jbzk.setPhone(phone);              
				 jbzk.setDp_type(dp_type);             
				 jbzk.setDp_select(dp_select);           
				 jbzk.setDp_qx(dp_qx);               
				 jbzk.setDp_zj(dp_zj);               
				 jbzk.setDp_other(dp_other);            
				 jbzk.setDp_zx(dp_zx_select);             
				 jbzk.setDp_zx_select(dp_zx_select);       
				 jbzk.setDp_qy_rq(dp_qy_rq);           
				 jbzk.setZh(zh);                  
				 jbzk.setM_bank(m_bank);              
				 jbzk.setM_zh(m_zh);                
				 jbzk.setBank2(bank2);               
				 jbzk.setZh2(zh2);                
				 jbzk.setJl(jl);                  
				 jbzk.setMs(ms);                  
				 jbzk.setZx_zk(zx_zk);               
				 jbzk.setZx_select(zx_select);           
				 jbzk.setZx_sm(zx_sm);               
				 jbzk.setZx_card_num(zx_card_num);         
				 jbzk.setZx_creddit(zx_creddit);         
				 jbzk.setZx_yy(zx_yy);               
				 jbzk.setZx_tz(zx_tz);               
				 jbzk.setZx_yq(zx_yq_qk);               
				 jbzk.setZx_ls_num(zx_ls_num);          
				 jbzk.setZx_wdq_num(zx_wdq_num);          
				 jbzk.setZx_dk_yy(zx_dk_yy);           
				 jbzk.setZx_yq_qk(zx_yq_qk);            
				 jbzk.setZx_dbr_ornot(zx_dbr_ornot);        
				 jbzk.setZx_db_ye(zx_db_ye);            
				 jbzk.setZx_db_yt(zx_db_yt);            
				 jbzk.setZx_db_qx(zx_db_qx);            
				 jbzk.setZx_zx_seach_num(zx_zx_seach_num);     
				 jbzk.setSqr_family(sqr_family);          
				 jbzk.setSqr_family_relation(sqr_family_relation);
				 jbzk.setSqr_eco_num(sqr_eco_num);         
				 jbzk.setSqr_bad_hobby(sqr_bad_hobby);       
				 jbzk.setSqr_bad_habit(sqr_bad_habit);       
				 jbzk.setSqr_work_pl(sqr_work_pl);         
				 jbzk.setSqr_zz_change_pl(sqr_zz_change_pl);    
				 jbzk.setSqr_mark(sqr_mark);            
				 jbzk.setSqr_zz_qk(sqr_zz_qk);           
				 jbzk.setSqr_bx(sqr_bx);              
				 jbzk.setSqr_child_qk(sqr_child_qk);        
				 jbzk.setSqr_child_edu(sqr_child_edu);       
				 jbzk.setSqr_child_unit(sqr_child_unit);     
				 jbzk.setSqr_society_gx(sqr_society_gx);      
				 jbzk.setSqr_soc_gy(sqr_soc_gy);          
				 jbzk.setSqr_wf(sqr_wf);              
				 jbzk.setSqr_syfm(sqr_syfm);            
				 jbzk.setSqr_qq_zk(sqr_qq_zk);           
				 jbzk.setSqr_jr_pj(sqr_jr_pj);           
				 jbzk.setSqr_lj_pj(sqr_lj_pj);           
				 jbzk.setSqr_lxr_pj(sqr_lxr_pj);          
				 jbzk.setSqr_sy_pj(sqr_sy_pj);           
				 jbzk.setSqr_q_person(sqr_q_person);        
				 jbzk.setSqr_q_bus(sqr_q_bus);           
				 jbzk.setSqr_w_person(sqr_w_person);        
				 jbzk.setSqr_w_bus(sqr_w_bus);           
				 jbzk.setSqr_ztpj(sqr_ztpj);            
				 jbzk.setHy_work(hy_work);             
				 jbzk.setHy_xz(hy_xz);               
				 jbzk.setHy_fx(hy_fx);               
				 jbzk.setHy_qs(hy_qs);               
				 jbzk.setHy_zz(hy_zz);               
				 jbzk.setHy_ys_gl(hy_ys_gl);            
				 jbzk.setHy_gd_gl(hy_gd_gl);            
				 jbzk.setHy_pj_sale(hy_pj_sale);          
				 jbzk.setHy_pj_jl(hy_pj_jl);            
				 jbzk.setHy_gz(hy_gz);               
				 jbzk.setHy_fy(hy_fy);               
				 jbzk.setHy_yclje(hy_yclje);            
				 jbzk.setHy_policy(hy_policy);           
				 jbzk.setHy_desc(hy_desc);             
				 jbzk.setHy_pj_ml(hy_pj_ml); 
				//save
				 addIntoPiecesService.updateJbzk(jbzk);
			 }else {
				 //save
				 Dzjbzk jbzk1 = new Dzjbzk();
				 jbzk1.setCustomer_id(customerId);
				 jbzk1.setProduct_id(productId);
				 jbzk1.setMx(mx);                 
				 jbzk1.setZj_type(zj_type);             
				 jbzk1.setMj(mj);                  
				 jbzk1.setFr(fr);                  
				 jbzk1.setKzr(kzr);                 
				 jbzk1.setZb(zb);                  
				 jbzk1.setGy(sqr_soc_gy);                  
				 jbzk1.setYwfw(ywfw);                
				 jbzk1.setYezz(yezz_select);                
				 jbzk1.setYezz_select(yezz_select);         
				 jbzk1.setZm(zm);                  
				 jbzk1.setKsrq(ksrq);               
				 jbzk1.setAddress(address);             
				 jbzk1.setPhone(phone);              
				 jbzk1.setDp_type(dp_type);             
				 jbzk1.setDp_select(dp_select);           
				 jbzk1.setDp_qx(dp_qx);               
				 jbzk1.setDp_zj(dp_zj);               
				 jbzk1.setDp_other(dp_other);            
				 jbzk1.setDp_zx(dp_zx_select);             
				 jbzk1.setDp_zx_select(dp_zx_select);       
				 jbzk1.setDp_qy_rq(dp_qy_rq);           
				 jbzk1.setZh(zh);                  
				 jbzk1.setM_bank(m_bank);              
				 jbzk1.setM_zh(m_zh);                
				 jbzk1.setBank2(bank2);               
				 jbzk1.setZh2(zh2);                
				 jbzk1.setJl(jl);                  
				 jbzk1.setMs(ms);                  
				 jbzk1.setZx_zk(zx_zk);               
				 jbzk1.setZx_select(zx_select);           
				 jbzk1.setZx_sm(zx_sm);               
				 jbzk1.setZx_card_num(zx_card_num);         
				 jbzk1.setZx_creddit(zx_creddit);         
				 jbzk1.setZx_yy(zx_yy);               
				 jbzk1.setZx_tz(zx_tz);               
				 jbzk1.setZx_yq(zx_yq_qk);               
				 jbzk1.setZx_ls_num(zx_ls_num);          
				 jbzk1.setZx_wdq_num(zx_wdq_num);          
				 jbzk1.setZx_dk_yy(zx_dk_yy);           
				 jbzk1.setZx_yq_qk(zx_yq_qk);            
				 jbzk1.setZx_dbr_ornot(zx_dbr_ornot);        
				 jbzk1.setZx_db_ye(zx_db_ye);            
				 jbzk1.setZx_db_yt(zx_db_yt);            
				 jbzk1.setZx_db_qx(zx_db_qx);            
				 jbzk1.setZx_zx_seach_num(zx_zx_seach_num);     
				 jbzk1.setSqr_family(sqr_family);          
				 jbzk1.setSqr_family_relation(sqr_family_relation);
				 jbzk1.setSqr_eco_num(sqr_eco_num);         
				 jbzk1.setSqr_bad_hobby(sqr_bad_hobby);       
				 jbzk1.setSqr_bad_habit(sqr_bad_habit);       
				 jbzk1.setSqr_work_pl(sqr_work_pl);         
				 jbzk1.setSqr_zz_change_pl(sqr_zz_change_pl);    
				 jbzk1.setSqr_mark(sqr_mark);            
				 jbzk1.setSqr_zz_qk(sqr_zz_qk);           
				 jbzk1.setSqr_bx(sqr_bx);              
				 jbzk1.setSqr_child_qk(sqr_child_qk);        
				 jbzk1.setSqr_child_edu(sqr_child_edu);       
				 jbzk1.setSqr_child_unit(sqr_child_unit);     
				 jbzk1.setSqr_society_gx(sqr_society_gx);      
				 jbzk1.setSqr_soc_gy(sqr_soc_gy);          
				 jbzk1.setSqr_wf(sqr_wf);              
				 jbzk1.setSqr_syfm(sqr_syfm);            
				 jbzk1.setSqr_qq_zk(sqr_qq_zk);           
				 jbzk1.setSqr_jr_pj(sqr_jr_pj);           
				 jbzk1.setSqr_lj_pj(sqr_lj_pj);           
				 jbzk1.setSqr_lxr_pj(sqr_lxr_pj);          
				 jbzk1.setSqr_sy_pj(sqr_sy_pj);           
				 jbzk1.setSqr_q_person(sqr_q_person);        
				 jbzk1.setSqr_q_bus(sqr_q_bus);           
				 jbzk1.setSqr_w_person(sqr_w_person);        
				 jbzk1.setSqr_w_bus(sqr_w_bus);           
				 jbzk1.setSqr_ztpj(sqr_ztpj);            
				 jbzk1.setHy_work(hy_work);             
				 jbzk1.setHy_xz(hy_xz);               
				 jbzk1.setHy_fx(hy_fx);               
				 jbzk1.setHy_qs(hy_qs);               
				 jbzk1.setHy_zz(hy_zz);               
				 jbzk1.setHy_ys_gl(hy_ys_gl);            
				 jbzk1.setHy_gd_gl(hy_gd_gl);            
				 jbzk1.setHy_pj_sale(hy_pj_sale);          
				 jbzk1.setHy_pj_jl(hy_pj_jl);            
				 jbzk1.setHy_gz(hy_gz);               
				 jbzk1.setHy_fy(hy_fy);               
				 jbzk1.setHy_yclje(hy_yclje);            
				 jbzk1.setHy_policy(hy_policy);           
				 jbzk1.setHy_desc(hy_desc);             
				 jbzk1.setHy_pj_ml(hy_pj_ml); 
				 //save
				 addIntoPiecesService.saveJbzk(jbzk1);
			 }
			 
			returnMap.addGlobalMessage(CHANGE_SUCCESS);
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}
		return returnMap;
	}
	
	
	
	/**
	 * 查询  经营状态
	 * @param request
	 * @return
	*/
	@ResponseBody
	@RequestMapping(value = "findJyzt.page")
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView findJyzt(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/intopieces/dc_jyzt", request);
		String customerId = request.getParameter("customerId");
		String productId = request.getParameter("productId");
		Dzjyzt jyzt = addIntoPiecesService.findDzjyzt(customerId,productId);
		mv.addObject("jyzt", jyzt);
		mv.addObject("productId", productId);
		mv.addObject("customerId",customerId);
		return mv;
	}
	
	
	/**
	 * save 经营状态
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "addJyzt.json", method = { RequestMethod.GET })
	public JRadReturnMap addJyzt(@ModelAttribute DzjyztForm form,HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			String loginId = user.getId();
			Dzjyzt jyzt = form.createModel(Dzjyzt.class);
			Dzjyzt dzjyzt = addIntoPiecesService.findDzjyzt(form.getCustomer_id(),form.getProduct_id());
			if(dzjyzt!= null){
				//update
				dzjyzt.setHyzc_jrhb(jyzt.getHyzc_jrhb());       
				dzjyzt.setHyzc_gjcy(jyzt.getHyzc_gjcy());       
				dzjyzt.setHyzc_ss(jyzt.getHyzc_ss());       
				dzjyzt.setHyxz_gqr(jyzt.getHyxz_gqr());       
				dzjyzt.setHyxz_zqxfx(jyzt.getHyxz_zqxfx());       
				dzjyzt.setHyxz_zcjg(jyzt.getHyxz_zcjg());       
				dzjyzt.setHyxz_ylnl_jl(jyzt.getHyxz_ylnl_jl());       
				dzjyzt.setHyxz_ylnl_szzzts(jyzt.getHyxz_ylnl_szzzts());       
				dzjyzt.setHyxz_ylnl_chzzts(jyzt.getHyxz_ylnl_chzzts());       
				dzjyzt.setHyxz_ylx_sysup(jyzt.getHyxz_ylx_sysup());       
				dzjyzt.setHyxz_ylx_xysup(jyzt.getHyxz_ylx_xysup());       
				dzjyzt.setHyxz_tdx(jyzt.getHyxz_tdx());       
				dzjyzt.setHyxz_fzqs_salerase(jyzt.getHyxz_fzqs_salerase());       
				dzjyzt.setHyxz_fzqs_jlrase(jyzt.getHyxz_fzqs_jlrase());       
				dzjyzt.setHyxz_fxfx(jyzt.getHyxz_fxfx());       
				dzjyzt.setJy_schcp_cy(jyzt.getJy_schcp_cy());       
				dzjyzt.setJy_schcp_xqzq(jyzt.getJy_schcp_xqzq());       
				dzjyzt.setJy_schcp_qbl(jyzt.getJy_schcp_qbl());       
				dzjyzt.setJy_schcp_tysl(jyzt.getJy_schcp_tysl());       
				dzjyzt.setJy_schcp_salebl(jyzt.getJy_schcp_salebl());       
				dzjyzt.setJy_schcp_method(jyzt.getJy_schcp_method());       
				dzjyzt.setJy_schj_zlnum(jyzt.getJy_schj_zlnum());       
				dzjyzt.setJy_schj_jslv(jyzt.getJy_schj_jslv());       
				dzjyzt.setJy_schj_zhyx(jyzt.getJy_schj_zhyx());       
				dzjyzt.setJy_schj_hj(jyzt.getJy_schj_hj());       
				dzjyzt.setJy_schj_lz(jyzt.getJy_schj_lz());       
				dzjyzt.setJy_sxhj_cb(jyzt.getJy_sxhj_cb());       
				dzjyzt.setJy_sxhj_bl(jyzt.getJy_sxhj_bl());       
				dzjyzt.setGy_glwd_bl(jyzt.getGy_glwd_bl());       
				dzjyzt.setGy_glwd_ls(jyzt.getGy_glwd_ls());       
				dzjyzt.setGy_sz_edu(jyzt.getGy_sz_edu());       
				dzjyzt.setGy_sz_aveage(jyzt.getGy_sz_aveage());       
				dzjyzt.setGy_sz_qycysj(jyzt.getGy_sz_qycysj());       
				dzjyzt.setGy_sz_hycysj(jyzt.getGy_sz_hycysj());       
				dzjyzt.setGy_sz_kt(jyzt.getGy_sz_kt());       
				dzjyzt.setGy_sz_td(jyzt.getGy_sz_td());       
				dzjyzt.setGy_sz_dd(jyzt.getGy_sz_dd());       
				dzjyzt.setGy_sx_ty(jyzt.getGy_sx_ty());       
				dzjyzt.setGy_sx_js(jyzt.getGy_sx_js());       
				dzjyzt.setGy_jy_yyqk(jyzt.getGy_jy_yyqk());       
				dzjyzt.setGy_jy_supnum(jyzt.getGy_jy_supnum());       
				dzjyzt.setGy_jy_jxnum(jyzt.getGy_jy_jxnum());       
				dzjyzt.setGy_js_cysj(jyzt.getGy_js_cysj());       
				dzjyzt.setGy_js_lsl(jyzt.getGy_js_lsl());       
				dzjyzt.setGy_team_dzzb(jyzt.getGy_team_dzzb());       
				dzjyzt.setGy_team_cysj(jyzt.getGy_team_cysj());       
				dzjyzt.setGy_kz_jg(jyzt.getGy_kz_jg());       
				dzjyzt.setGy_kz_jc(jyzt.getGy_kz_jc());       
				dzjyzt.setGy_kz_wzjl(jyzt.getGy_kz_wzjl());       
				dzjyzt.setGy_kz_zp(jyzt.getGy_kz_zp());       
				dzjyzt.setGy_kz_jsjl(jyzt.getGy_kz_jsjl());       
				dzjyzt.setGy_kz_rxgl(jyzt.getGy_kz_rxgl());       
				dzjyzt.setGy_kz_ttzd(jyzt.getGy_kz_ttzd());       
				dzjyzt.setGy_kz_fzzl(jyzt.getGy_kz_fzzl());       
				dzjyzt.setGy_cw_cb(jyzt.getGy_cw_cb());       
				dzjyzt.setGy_cw_kz(jyzt.getGy_cw_kz());       
				addIntoPiecesService.updateJyzt(dzjyzt);
			}else{
				//save 
				addIntoPiecesService.saveJyzt(jyzt);
			}
			returnMap.addGlobalMessage(CHANGE_SUCCESS);
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}
		return returnMap;
	}
	
	
	/**
	 * 查看生存状态
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "findSczt.page")
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView findSczt(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/intopieces/dc_sczt", request);
		String customerId = request.getParameter("customerId");
		String productId = request.getParameter("productId");
		Dcsczt sczt = addIntoPiecesService.findDcsczt(customerId, productId);
		mv.addObject("sczt", sczt);
		mv.addObject("productId", productId);
		mv.addObject("customerId",customerId);
		return mv;
	}
	
	//保存建议
	@ResponseBody
	@RequestMapping(value = "addSczt.json", method = { RequestMethod.GET })
	public JRadReturnMap addSczt(@ModelAttribute DcscztForm form,HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			String loginId = user.getId();
			Dcsczt dcsczt = form.createModel(Dcsczt.class);
			
			//查询是否exist
			Dcsczt sczt = addIntoPiecesService.findDcsczt(form.getCustomer_id(),form.getProduct_id());
			if(sczt!=null){
				//update
				 sczt.setJc_fcsl_aj(dcsczt.getJc_fcsl_aj());
				 sczt.setJc_fcsj_aj(dcsczt.getJc_fcsj_aj());
				 sczt.setJc_pjmj_aj(dcsczt.getJc_pjmj_aj());
				 sczt.setJc_zxqk_aj(dcsczt.getJc_zxqk_aj());
				 sczt.setJc_jznx_aj(dcsczt.getJc_jznx_aj());
				 sczt.setJc_fcsl_qe(dcsczt.getJc_fcsl_qe());
				 sczt.setJc_fcsj_qe(dcsczt.getJc_fcsj_qe());
				 sczt.setJc_pjmj_qe(dcsczt.getJc_pjmj_qe());
				 sczt.setJc_zxqk_qe(dcsczt.getJc_zxqk_qe());
				 sczt.setJc_jznx_qe(dcsczt.getJc_jznx_qe());
				 sczt.setJc_zlfc_sl(dcsczt.getJc_zlfc_sl());
				 sczt.setJc_zlfc_mj(dcsczt.getJc_zlfc_mj());
				 sczt.setJc_zlfc_zj(dcsczt.getJc_zlfc_zj());
				 sczt.setJc_cl_sl(dcsczt.getJc_cl_sl());
				 sczt.setJc_cl_gmze(dcsczt.getJc_cl_gmze());
				 sczt.setJc_cl_pjsysj(dcsczt.getJc_cl_pjsysj());
				 sczt.setJc_cl_gmfs(dcsczt.getJc_cl_gmfs());
				 sczt.setJc_zhje(dcsczt.getJc_zhje());
				 sczt.setJc_lcje(dcsczt.getJc_lcje());
				 sczt.setJc_sz_qx(dcsczt.getJc_sz_qx());
				 sczt.setJc_sz_zb(dcsczt.getJc_sz_zb());
				 sczt.setJc_sz_sx(dcsczt.getJc_sz_sx());
				 sczt.setHj_aq(dcsczt.getHj_aq());
				 sczt.setHj_jk(dcsczt.getHj_jk());
				 sczt.setHj_bl(dcsczt.getHj_bl());
				 sczt.setHj_ss(dcsczt.getHj_ss());
				 sczt.setHj_ljyh(dcsczt.getHj_ljyh());
				 sczt.setHj_ljpjedu(dcsczt.getHj_ljpjedu());
				 sczt.setHj_ljzy(dcsczt.getHj_ljzy());
				 sczt.setHj_ljeco(dcsczt.getHj_ljeco());
				 sczt.setSj_work(dcsczt.getSj_work());
				 sczt.setSj_rest_yl(dcsczt.getSj_rest_yl());
				 sczt.setSj_rest_hw(dcsczt.getSj_rest_hw());
				 sczt.setWork_income(dcsczt.getWork_income());
				 sczt.setBusiness_income(dcsczt.getBusiness_income());
				 sczt.setProperty_income(dcsczt.getProperty_income());
				 sczt.setSocial_je(dcsczt.getSocial_je());
				 sczt.setSocial_nx(dcsczt.getSocial_nx());
				 sczt.setAccufund_je(dcsczt.getAccufund_je());
				 sczt.setAccufund_nx(dcsczt.getAccufund_nx());
				 sczt.setDistr_sh(dcsczt.getDistr_sh());
				 sczt.setDistr_lc(dcsczt.getDistr_lc());
				 sczt.setDistr_qt(dcsczt.getDistr_qt());
				 sczt.setXfxg(dcsczt.getXfxg());
				 sczt.setLcjg(dcsczt.getLcjg());
				 sczt.setJk_sy(dcsczt.getJk_sy());
				 sczt.setJk_sm(dcsczt.getJk_sm());
				 sczt.setJk_tj(dcsczt.getJk_tj());
				 sczt.setJk_jy(dcsczt.getJk_jy());
				 sczt.setJk_xjgs(dcsczt.getJk_xjgs());
				 sczt.setJt_poqg(dcsczt.getJt_poqg());
				 sczt.setJt_gx(dcsczt.getJt_gx());
				 sczt.setJt_jcnl(dcsczt.getJt_jcnl());
				 sczt.setSj_jhcs(dcsczt.getSj_jhcs());
				 sczt.setSj_pygz(dcsczt.getSj_pygz());
				 sczt.setSj_pyxl(dcsczt.getSj_pyxl());
				 sczt.setFz_hkjh(dcsczt.getFz_hkjh());
				 sczt.setFz_srzb(dcsczt.getFz_srzb());
				 sczt.setFz_xyzb(dcsczt.getFz_xyzb());
				 sczt.setFz_ly(dcsczt.getFz_ly());
				 sczt.setFz_lyhkjh(dcsczt.getFz_lyhkjh());
				 sczt.setFz_lyzb(dcsczt.getFz_lyzb());
				 sczt.setFz_db_je(dcsczt.getFz_db_je());
				 sczt.setFz_db_yt(dcsczt.getFz_db_yt());
				 sczt.setFz_db_qx(dcsczt.getFz_db_qx());
				 addIntoPiecesService.updateSczt(sczt);
			}else{
				//save
				addIntoPiecesService.saveSczt(dcsczt);
			}

			returnMap.addGlobalMessage(CHANGE_SUCCESS);
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}

		return returnMap;
	}
	
	
	/**
	 * 查看道德品质
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "findDdpz.page")
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView findDdpz(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/intopieces/dc_ddpz", request);
		String customerId = request.getParameter("customerId");
		String productId = request.getParameter("productId");
		Dcddpz ddpz = addIntoPiecesService.findDcddpz(customerId, productId);
		mv.addObject("ddpz", ddpz);
		mv.addObject("productId", productId);
		mv.addObject("customerId",customerId);
		return mv;
	}
	
	
	//保存建议
	@ResponseBody
	@RequestMapping(value = "addDdpz.json", method = { RequestMethod.GET })
	public JRadReturnMap addDdpz(@ModelAttribute DcddpzForm form,HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			String loginId = user.getId();
			Dcddpz dcddpz = form.createModel(Dcddpz.class);
			//查询是否exist
			Dcddpz ddpz = addIntoPiecesService.findDcddpz(form.getCustomer_id(),form.getProduct_id());
			
			if(ddpz!=null){
				//update
				ddpz.setSzpg_gzxx(dcddpz.getSzpg_gzxx());      
				ddpz.setSzpg_rhjl(dcddpz.getSzpg_rhjl());      
				ddpz.setSzpg_syfm(dcddpz.getSzpg_syfm());      
				ddpz.setSzpg_gyhd(dcddpz.getSzpg_gyhd());      
				ddpz.setSzpg_blsh(dcddpz.getSzpg_blsh());      
				ddpz.setXyjl_wffz(dcddpz.getXyjl_wffz());      
				ddpz.setXyjl_zxbg(dcddpz.getXyjl_zxbg());      
				ddpz.setXyjl_tqfy(dcddpz.getXyjl_tqfy());      
				ddpz.setXyjl_ggsstp(dcddpz.getXyjl_ggsstp());  
				ddpz.setXyjl_jtwz(dcddpz.getXyjl_jtwz());      
				ddpz.setXyjl_dhqf(dcddpz.getXyjl_dhqf());      
				ddpz.setXyjl_qzjq(dcddpz.getXyjl_qzjq());      
				ddpz.setZjxy_zczj(dcddpz.getZjxy_zczj());      
				ddpz.setZjxy_wfzj(dcddpz.getZjxy_wfzj());      
				addIntoPiecesService.updateDdpz(ddpz);
			}else{
				//save
				addIntoPiecesService.saveDdpz(dcddpz);
			}

			returnMap.addGlobalMessage(CHANGE_SUCCESS);
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}

		return returnMap;
	}
	
	
	/**
	 * 显示资产负债
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "create_zcfz.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView create_zcfz(HttpServletRequest request) {
		String customerId = request.getParameter("customerId");
		String productId = request.getParameter("productId");
		List<CustomerInforUpdateBalanceSheet> customerInforUpdateBalanceSheet =
                customerInforUpdateService.getCustomerInforUpdateBalanceSheetByCustIdAndProdId(customerId,productId);

		JRadModelAndView mv = new JRadModelAndView("/intopieces/dc_zcfz",request);
		mv.addObject("customerInforUpdateBalanceSheet",	customerInforUpdateBalanceSheet);
		mv.addObject("customerId",customerId);
		mv.addObject("productId",productId);
		return mv;
	}
	
	
	/**
	 * 执行添加 资产负债
	 * 
	 * @param customerinfoForm
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "balanceSheetInsert.json")
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView balanceSheetInsert(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/intopieces/dc_zcfz",request);
		try {
			String balanceSheet = request.getParameter("balanceSheet");
			String customerId=request.getParameter("customerId");
			String productId = request.getParameter("productId");
			customerInforUpdateService.insertCustomerInforUpdateBalanceSheet1(customerId,balanceSheet,productId);

			List<CustomerInforUpdateBalanceSheet> customerInforUpdateBalanceSheet =
                    customerInforUpdateService.getCustomerInforUpdateBalanceSheetByCustIdAndProdId(customerId,productId);

			mv.addObject("customerInforUpdateBalanceSheet",	customerInforUpdateBalanceSheet);
			mv.addObject("customerId",customerId);
			mv.addObject("productId",productId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	
	/**
	 * 显示利润简表
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "create_lrjb.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView create_lrjb(HttpServletRequest request) {
		String customerId = request.getParameter("customerId");
		String productId = request.getParameter("productId");
		Dclrjb lrjb = addIntoPiecesService.findDclrjb(customerId,productId);
		JRadModelAndView mv = new JRadModelAndView("/intopieces/dc_lrjb",request);
		mv.addObject("customerId",customerId);
		mv.addObject("productId",productId);
		mv.addObject("lrjb",lrjb);
		return mv;
	}
	
	
	//保存利润简表
	@ResponseBody
	@RequestMapping(value = "addlrjb.json", method = { RequestMethod.GET })
	public JRadReturnMap addlrjb(@ModelAttribute DclrjbForm form,HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			String loginId = user.getId();
			Dclrjb lrjb = form.createModel(Dclrjb.class);
			//查询是否exist
			Dclrjb dclrjb = addIntoPiecesService.findDclrjb(form.getCustomer_id(),form.getProduct_id());
			if(dclrjb!=null){
				dclrjb.setRq(lrjb.getRq());     
				dclrjb.setUnit(lrjb.getUnit());     
				dclrjb.setA1(lrjb.getA1());     
				dclrjb.setA2(lrjb.getA2());     
				dclrjb.setA3(lrjb.getA3());     
				dclrjb.setA4(lrjb.getA4());     
				dclrjb.setA5(lrjb.getA5());     
				dclrjb.setA6(lrjb.getA6());     
				dclrjb.setA7(lrjb.getA7());     
				dclrjb.setA8(lrjb.getA8());     
				dclrjb.setA10(lrjb.getA10());     
				dclrjb.setA11(lrjb.getA11());     
				dclrjb.setA12(lrjb.getA12());     
				dclrjb.setA13(lrjb.getA13());     
				dclrjb.setA14(lrjb.getA14());     
				dclrjb.setA15(lrjb.getA15());     
				dclrjb.setA16(lrjb.getA16());     
				dclrjb.setA17(lrjb.getA17());     
				dclrjb.setA18(lrjb.getA18());     
				dclrjb.setA19(lrjb.getA19());     
				dclrjb.setA20(lrjb.getA20());     
				dclrjb.setA21(lrjb.getA21());     
				dclrjb.setA22(lrjb.getA22());     
				dclrjb.setA23(lrjb.getA23());     
				dclrjb.setA24(lrjb.getA24());     
				dclrjb.setA25(lrjb.getA25());     
				dclrjb.setA26(lrjb.getA26());     
				dclrjb.setA27(lrjb.getA27());     
				dclrjb.setA28(lrjb.getA28());     
				dclrjb.setA29(lrjb.getA29());     
				dclrjb.setA30(lrjb.getA30());     
				dclrjb.setA31(lrjb.getA31());     
				dclrjb.setA32(lrjb.getA32());     
				dclrjb.setA33(lrjb.getA33());     
				dclrjb.setA34(lrjb.getA34());     
				dclrjb.setA35(lrjb.getA35());     
				dclrjb.setA36(lrjb.getA36());     
				dclrjb.setA37(lrjb.getA37());     
				dclrjb.setA38(lrjb.getA38());     
				dclrjb.setA39(lrjb.getA39());     
				dclrjb.setA40(lrjb.getA40());     
				dclrjb.setA41(lrjb.getA41());     
				dclrjb.setA42(lrjb.getA42());     
				dclrjb.setA43(lrjb.getA43());     
				dclrjb.setA44(lrjb.getA44());     
				dclrjb.setA45(lrjb.getA45());     
				dclrjb.setA46(lrjb.getA46());     
				dclrjb.setA47(lrjb.getA47());     
				dclrjb.setA48(lrjb.getA48());     
				dclrjb.setA49(lrjb.getA49());     
				dclrjb.setA50(lrjb.getA50());     
				dclrjb.setA51(lrjb.getA51());     
				dclrjb.setA52(lrjb.getA52());     
				dclrjb.setA53(lrjb.getA53());     
				dclrjb.setA54(lrjb.getA54());     
				dclrjb.setA55(lrjb.getA55());     
				dclrjb.setA56(lrjb.getA56());     
				dclrjb.setA57(lrjb.getA57());     
				dclrjb.setA58(lrjb.getA58());     
				dclrjb.setA59(lrjb.getA59());     
				dclrjb.setA60(lrjb.getA60());     
				dclrjb.setA61(lrjb.getA61());     
				dclrjb.setA62(lrjb.getA62());     
				dclrjb.setA63(lrjb.getA63());     
				dclrjb.setA64(lrjb.getA64());     
				dclrjb.setA65(lrjb.getA65());     
				dclrjb.setA66(lrjb.getA66());     
				dclrjb.setA67(lrjb.getA67());     
				dclrjb.setA68(lrjb.getA68());     
				dclrjb.setA69(lrjb.getA69());     
				dclrjb.setA70(lrjb.getA70());     
				dclrjb.setA71(lrjb.getA71());     
				dclrjb.setA72(lrjb.getA72());     
				dclrjb.setA73(lrjb.getA73());     
				dclrjb.setA74(lrjb.getA74());     
				dclrjb.setA75(lrjb.getA75());     
				dclrjb.setA76(lrjb.getA76());     
				dclrjb.setA77(lrjb.getA77());     
				dclrjb.setA78(lrjb.getA78());     
				dclrjb.setA79(lrjb.getA79());     
				dclrjb.setA80(lrjb.getA80());     
				dclrjb.setA81(lrjb.getA81());     
				dclrjb.setA82(lrjb.getA82());     
				dclrjb.setA83(lrjb.getA83());     
				dclrjb.setA84(lrjb.getA84());     
				dclrjb.setA85(lrjb.getA85());     
				dclrjb.setA86(lrjb.getA86());     
				dclrjb.setA87(lrjb.getA87());     
				dclrjb.setA88(lrjb.getA88());     
				dclrjb.setA89(lrjb.getA89());     
				dclrjb.setA90(lrjb.getA90());     
				dclrjb.setA91(lrjb.getA91());     
				dclrjb.setA92(lrjb.getA92());     
				dclrjb.setA93(lrjb.getA93());     
				dclrjb.setA94(lrjb.getA94());     
				dclrjb.setA95(lrjb.getA95());     
				dclrjb.setA96(lrjb.getA96());     
				dclrjb.setA97(lrjb.getA97());     
				dclrjb.setA98(lrjb.getA98());     
				dclrjb.setA99(lrjb.getA99());     
				dclrjb.setA100(lrjb.getA100());     
				dclrjb.setA101(lrjb.getA101());     
				dclrjb.setA102(lrjb.getA102());     
				dclrjb.setA103(lrjb.getA103());     
				dclrjb.setA104(lrjb.getA104());     
				dclrjb.setA105(lrjb.getA105());     
				dclrjb.setA106(lrjb.getA106());     
				dclrjb.setA107(lrjb.getA107());     
				dclrjb.setA108(lrjb.getA108());     
				dclrjb.setA109(lrjb.getA109());     
				dclrjb.setA110(lrjb.getA110());     
				dclrjb.setA111(lrjb.getA111());     
				dclrjb.setA112(lrjb.getA112());     
				dclrjb.setA113(lrjb.getA113());     
				dclrjb.setA114(lrjb.getA114());     
				dclrjb.setA115(lrjb.getA115());     
				dclrjb.setA116(lrjb.getA116());     
				dclrjb.setA117(lrjb.getA117());     
				dclrjb.setA118(lrjb.getA118());     
				dclrjb.setA119(lrjb.getA119());     
				dclrjb.setA120(lrjb.getA120());     
				dclrjb.setA121(lrjb.getA121());     
				dclrjb.setA122(lrjb.getA122());     
				dclrjb.setA123(lrjb.getA123());     
				dclrjb.setA124(lrjb.getA124());     
				dclrjb.setA125(lrjb.getA125());     
				dclrjb.setA126(lrjb.getA126());     
				dclrjb.setA127(lrjb.getA127());     
				dclrjb.setA128(lrjb.getA128());     
				dclrjb.setA129(lrjb.getA129());     
				dclrjb.setA130(lrjb.getA130());     
				dclrjb.setA131(lrjb.getA131());     
				dclrjb.setA132(lrjb.getA132());     
				dclrjb.setA133(lrjb.getA133());     
				dclrjb.setA134(lrjb.getA134());     
				dclrjb.setA135(lrjb.getA135());     
				dclrjb.setA136(lrjb.getA136());     
				dclrjb.setA137(lrjb.getA137());     
				dclrjb.setA138(lrjb.getA138());     
				dclrjb.setA139(lrjb.getA139());     
				dclrjb.setA140(lrjb.getA140());     
				dclrjb.setA141(lrjb.getA141());     
				dclrjb.setA142(lrjb.getA142());     
				dclrjb.setA143(lrjb.getA143());     
				dclrjb.setA144(lrjb.getA144());     
				dclrjb.setA145(lrjb.getA145());     
				dclrjb.setA146(lrjb.getA146());     
				dclrjb.setA147(lrjb.getA147());     
				dclrjb.setA148(lrjb.getA148());     
				dclrjb.setA149(lrjb.getA149());     
				dclrjb.setA150(lrjb.getA150());     
				dclrjb.setA151(lrjb.getA151());     
				dclrjb.setA152(lrjb.getA152());     
				dclrjb.setA153(lrjb.getA153());     
				dclrjb.setA154(lrjb.getA154());     
				dclrjb.setA155(lrjb.getA155());     
				dclrjb.setA156(lrjb.getA156());     
				dclrjb.setA157(lrjb.getA157());     
				dclrjb.setA158(lrjb.getA158());     
				dclrjb.setA159(lrjb.getA159());     
				dclrjb.setA160(lrjb.getA160());     
				dclrjb.setA161(lrjb.getA161());     
				dclrjb.setA162(lrjb.getA162());     
				dclrjb.setA163(lrjb.getA163());     
				dclrjb.setA164(lrjb.getA164());     
				dclrjb.setA165(lrjb.getA165());     
				dclrjb.setA166(lrjb.getA166());     
				dclrjb.setA167(lrjb.getA167());     
				dclrjb.setA168(lrjb.getA168());     
				dclrjb.setA169(lrjb.getA169());     
				dclrjb.setA170(lrjb.getA170());     
				dclrjb.setA171(lrjb.getA171());     
				dclrjb.setA172(lrjb.getA172());     
				dclrjb.setA173(lrjb.getA173());     
				dclrjb.setA174(lrjb.getA174());     
				dclrjb.setA175(lrjb.getA175());     
				dclrjb.setA176(lrjb.getA176());     
				dclrjb.setA177(lrjb.getA177());     
				dclrjb.setA178(lrjb.getA178());     
				dclrjb.setA179(lrjb.getA179());     
				dclrjb.setA180(lrjb.getA180());     
				dclrjb.setA181(lrjb.getA181());     
				dclrjb.setSrjh(lrjb.getSrjh());     
				dclrjb.setSrxs(lrjb.getSrxs());     
				dclrjb.setKbcbxs(lrjb.getKbcbxs());     
				dclrjb.setQtsrfx(lrjb.getQtsrfx());     
				dclrjb.setKz(lrjb.getKz());     
				dclrjb.setLrjs(lrjb.getLrjs());     
				addIntoPiecesService.updateDclrjb(dclrjb);
			}else{
				addIntoPiecesService.saveDclrjb(lrjb);
			}
			
			

			returnMap.addGlobalMessage(CHANGE_SUCCESS);
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}

		return returnMap;
	}
	
	
	/**
	 * 显示标准利润表
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "create_bzlr.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView create_bzhlr(HttpServletRequest request) {
		String customerId = request.getParameter("customerId");
		String productId = request.getParameter("productId");
		Dcbzlr bzlr = addIntoPiecesService.findDcbzlr(customerId,productId);
		JRadModelAndView mv = new JRadModelAndView("/intopieces/dc_bzlr",request);
		mv.addObject("customerId",customerId);
		mv.addObject("productId",productId);
		mv.addObject("bzlr",bzlr);
		return mv;
	}
	
	
	//保存标准利润表
	@ResponseBody
	@RequestMapping(value = "addbzlr.json", method = { RequestMethod.GET })
	public JRadReturnMap addbzlr(@ModelAttribute DcbzlrForm form,HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			String loginId = user.getId();
			Dcbzlr bzlr = form.createModel(Dcbzlr.class);
			//查询是否exist
			Dcbzlr dcbzlr = addIntoPiecesService.findDcbzlr(form.getCustomer_id(),form.getProduct_id());
			if(dcbzlr!=null){
			  dcbzlr.setBbzq(dcbzlr.getBbzq());          
			  dcbzlr.setDczq(dcbzlr.getDczq());          
			  dcbzlr.setUnit(dcbzlr.getUnit());         
			  dcbzlr.setC4 (bzlr.getC4());            
			  dcbzlr.setD4 (bzlr.getD4());            
			  dcbzlr.setE4 (bzlr.getE4());            
			  dcbzlr.setF4 (bzlr.getF4());            
			  dcbzlr.setG4 (bzlr.getG4());            
			  dcbzlr.setH4 (bzlr.getH4());            
			  dcbzlr.setI4 (bzlr.getI4());            
			  dcbzlr.setJ4 (bzlr.getJ4());            
			  dcbzlr.setK4 (bzlr.getK4());            
			  dcbzlr.setL4 (bzlr.getL4());            
			  dcbzlr.setM4 (bzlr.getM4());            
			  dcbzlr.setN4 (bzlr.getN4());            
			  dcbzlr.setO4 (bzlr.getO4());            
			  dcbzlr.setP4 (bzlr.getP4());            
			  dcbzlr.setQ4 (bzlr.getQ4());            
			  dcbzlr.setC5 (bzlr.getC5());            
			  dcbzlr.setD5 (bzlr.getD5());            
			  dcbzlr.setE5 (bzlr.getE5());            
			  dcbzlr.setF5 (bzlr.getF5());            
			  dcbzlr.setG5 (bzlr.getG5());            
			  dcbzlr.setH5 (bzlr.getH5());            
			  dcbzlr.setI5 (bzlr.getI5());            
			  dcbzlr.setJ5 (bzlr.getJ5());            
			  dcbzlr.setK5 (bzlr.getK5());            
			  dcbzlr.setL5 (bzlr.getL5());            
			  dcbzlr.setM5 (bzlr.getM5());            
			  dcbzlr.setN5 (bzlr.getN5());            
			  dcbzlr.setO5 (bzlr.getO5());            
			  dcbzlr.setP5 (bzlr.getP5());            
			  dcbzlr.setQ5 (bzlr.getQ5());            
			  dcbzlr.setC6 (bzlr.getC6());            
			  dcbzlr.setD6 (bzlr.getD6());            
			  dcbzlr.setE6 (bzlr.getE6());            
			  dcbzlr.setF6 (bzlr.getF6());            
			  dcbzlr.setG6 (bzlr.getG6());            
			  dcbzlr.setH6 (bzlr.getH6());            
			  dcbzlr.setI6 (bzlr.getI6());            
			  dcbzlr.setJ6 (bzlr.getJ6());            
			  dcbzlr.setK6 (bzlr.getK6());            
			  dcbzlr.setL6 (bzlr.getL6());            
			  dcbzlr.setM6 (bzlr.getM6());            
			  dcbzlr.setN6 (bzlr.getN6());            
			  dcbzlr.setO6 (bzlr.getO6());            
			  dcbzlr.setP6 (bzlr.getP6());            
			  dcbzlr.setQ6 (bzlr.getQ6());            
			  dcbzlr.setC7 (bzlr.getC7());            
			  dcbzlr.setD7 (bzlr.getD7());            
			  dcbzlr.setE7 (bzlr.getE7());            
			  dcbzlr.setF7 (bzlr.getF7());            
			  dcbzlr.setG7 (bzlr.getG7());            
			  dcbzlr.setH7 (bzlr.getH7());            
			  dcbzlr.setI7 (bzlr.getI7());            
			  dcbzlr.setJ7 (bzlr.getJ7());            
			  dcbzlr.setK7 (bzlr.getK7());            
			  dcbzlr.setL7 (bzlr.getL7());            
			  dcbzlr.setM7 (bzlr.getM7());            
			  dcbzlr.setN7 (bzlr.getN7());            
			  dcbzlr.setO7 (bzlr.getO7());            
			  dcbzlr.setP7 (bzlr.getP7());            
			  dcbzlr.setQ7 (bzlr.getQ7());            
			  dcbzlr.setC8 (bzlr.getC8());            
			  dcbzlr.setD8 (bzlr.getD8());            
			  dcbzlr.setE8 (bzlr.getE8());            
			  dcbzlr.setF8 (bzlr.getF8());            
			  dcbzlr.setG8 (bzlr.getG8());            
			  dcbzlr.setH8 (bzlr.getH8());            
			  dcbzlr.setI8 (bzlr.getI8());            
			  dcbzlr.setJ8 (bzlr.getJ8());            
			  dcbzlr.setK8 (bzlr.getK8());            
			  dcbzlr.setL8 (bzlr.getL8());            
			  dcbzlr.setM8 (bzlr.getM8());            
			  dcbzlr.setN8 (bzlr.getN8());            
			  dcbzlr.setO8 (bzlr.getO8());            
			  dcbzlr.setP8 (bzlr.getP8());            
			  dcbzlr.setQ8 (bzlr.getQ8());            
			  dcbzlr.setC9 (bzlr.getC9());            
			  dcbzlr.setD9 (bzlr.getD9());            
			  dcbzlr.setE9 (bzlr.getE9());            
			  dcbzlr.setF9 (bzlr.getF9());            
			  dcbzlr.setG9 (bzlr.getG9());            
			  dcbzlr.setH9 (bzlr.getH9());            
			  dcbzlr.setI9 (bzlr.getI9());            
			  dcbzlr.setJ9 (bzlr.getJ9());            
			  dcbzlr.setK9 (bzlr.getK9());            
			  dcbzlr.setL9 (bzlr.getL9());            
			  dcbzlr.setM9 (bzlr.getM9());            
			  dcbzlr.setN9 (bzlr.getN9());            
			  dcbzlr.setO9 (bzlr.getO9());            
			  dcbzlr.setP9 (bzlr.getP9());            
			  dcbzlr.setQ9 (bzlr.getQ9());            
			  dcbzlr.setC10(bzlr.getC10());            
			  dcbzlr.setD10(bzlr.getD10());            
			  dcbzlr.setE10(bzlr.getE10());            
			  dcbzlr.setF10(bzlr.getF10());            
			  dcbzlr.setG10(bzlr.getG10());            
			  dcbzlr.setH10(bzlr.getH10());            
			  dcbzlr.setI10(bzlr.getI10());            
			  dcbzlr.setJ10(bzlr.getJ10());            
			  dcbzlr.setK10(bzlr.getK10());            
			  dcbzlr.setL10(bzlr.getL10());            
			  dcbzlr.setM10(bzlr.getM10());            
			  dcbzlr.setN10(bzlr.getN10());            
			  dcbzlr.setO10(bzlr.getO10());            
			  dcbzlr.setP10(bzlr.getP10());            
			  dcbzlr.setQ10(bzlr.getQ10());            
			  dcbzlr.setC11(bzlr.getC11());            
			  dcbzlr.setD11(bzlr.getD11());            
			  dcbzlr.setE11(bzlr.getE11());            
			  dcbzlr.setF11(bzlr.getF11());            
			  dcbzlr.setG11(bzlr.getG11());            
			  dcbzlr.setH11(bzlr.getH11());            
			  dcbzlr.setI11(bzlr.getI11());            
			  dcbzlr.setJ11(bzlr.getJ11());            
			  dcbzlr.setK11(bzlr.getK11());            
			  dcbzlr.setL11(bzlr.getL11());            
			  dcbzlr.setM11(bzlr.getM11());            
			  dcbzlr.setN11(bzlr.getN11());            
			  dcbzlr.setO11(bzlr.getO11());            
			  dcbzlr.setP11(bzlr.getP11());            
			  dcbzlr.setQ11(bzlr.getQ11());            
			  dcbzlr.setC12(bzlr.getC12());            
			  dcbzlr.setD12(bzlr.getD12());            
			  dcbzlr.setE12(bzlr.getE12());            
			  dcbzlr.setF12(bzlr.getF12());            
			  dcbzlr.setG12(bzlr.getG12());            
			  dcbzlr.setH12(bzlr.getH12());            
			  dcbzlr.setI12(bzlr.getI12());            
			  dcbzlr.setJ12(bzlr.getJ12());            
			  dcbzlr.setK12(bzlr.getK12());            
			  dcbzlr.setL12(bzlr.getL12());            
			  dcbzlr.setM12(bzlr.getM12());            
			  dcbzlr.setN12(bzlr.getN12());            
			  dcbzlr.setO12(bzlr.getO12());            
			  dcbzlr.setP12(bzlr.getP12());            
			  dcbzlr.setQ12(bzlr.getQ12());            
			  dcbzlr.setC13(bzlr.getC13());            
			  dcbzlr.setD13(bzlr.getD13());            
			  dcbzlr.setE13(bzlr.getE13());            
			  dcbzlr.setF13(bzlr.getF13());            
			  dcbzlr.setG13(bzlr.getG13());            
			  dcbzlr.setH13(bzlr.getH13());            
			  dcbzlr.setI13(bzlr.getI13());            
			  dcbzlr.setJ13(bzlr.getJ13());            
			  dcbzlr.setK13(bzlr.getK13());            
			  dcbzlr.setL13(bzlr.getL13());            
			  dcbzlr.setM13(bzlr.getM13());            
			  dcbzlr.setN13(bzlr.getN13());            
			  dcbzlr.setO13(bzlr.getO13());            
			  dcbzlr.setP13(bzlr.getP13());            
			  dcbzlr.setQ13(bzlr.getQ13());            
			  dcbzlr.setC14(bzlr.getC14());            
			  dcbzlr.setD14(bzlr.getD14());            
			  dcbzlr.setE14(bzlr.getE14());            
			  dcbzlr.setF14(bzlr.getF14());            
			  dcbzlr.setG14(bzlr.getG14());            
			  dcbzlr.setH14(bzlr.getH14());            
			  dcbzlr.setI14(bzlr.getI14());            
			  dcbzlr.setJ14(bzlr.getJ14());            
			  dcbzlr.setK14(bzlr.getK14());            
			  dcbzlr.setL14(bzlr.getL14());            
			  dcbzlr.setM14(bzlr.getM14());            
			  dcbzlr.setN14(bzlr.getN14());            
			  dcbzlr.setO14(bzlr.getO14());            
			  dcbzlr.setP14(bzlr.getP14());            
			  dcbzlr.setQ14(bzlr.getQ14());            
			  dcbzlr.setC15(bzlr.getC15());            
			  dcbzlr.setD15(bzlr.getD15());            
			  dcbzlr.setE15(bzlr.getE15());            
			  dcbzlr.setF15(bzlr.getF15());            
			  dcbzlr.setG15(bzlr.getG15());            
			  dcbzlr.setH15(bzlr.getH15());            
			  dcbzlr.setI15(bzlr.getI15());            
			  dcbzlr.setJ15(bzlr.getJ15());            
			  dcbzlr.setK15(bzlr.getK15());            
			  dcbzlr.setL15(bzlr.getL15());            
			  dcbzlr.setM15(bzlr.getM15());            
			  dcbzlr.setN15(bzlr.getN15());            
			  dcbzlr.setO15(bzlr.getO15());            
			  dcbzlr.setP15(bzlr.getP15());            
			  dcbzlr.setQ15(bzlr.getQ15());            
			  dcbzlr.setC16(bzlr.getC16());            
			  dcbzlr.setD16(bzlr.getD16());            
			  dcbzlr.setE16(bzlr.getE16());            
			  dcbzlr.setF16(bzlr.getF16());            
			  dcbzlr.setG16(bzlr.getG16());            
			  dcbzlr.setH16(bzlr.getH16());            
			  dcbzlr.setI16(bzlr.getI16());            
			  dcbzlr.setJ16(bzlr.getJ16());            
			  dcbzlr.setK16(bzlr.getK16());            
			  dcbzlr.setL16(bzlr.getL16());            
			  dcbzlr.setM16(bzlr.getM16());            
			  dcbzlr.setN16(bzlr.getN16());            
			  dcbzlr.setO16(bzlr.getO16());            
			  dcbzlr.setP16(bzlr.getP16());            
			  dcbzlr.setQ16(bzlr.getQ16());            
			  dcbzlr.setC17(bzlr.getC17());            
			  dcbzlr.setD17(bzlr.getD17());            
			  dcbzlr.setE17(bzlr.getE17());            
			  dcbzlr.setF17(bzlr.getF17());            
			  dcbzlr.setG17(bzlr.getG17());            
			  dcbzlr.setH17(bzlr.getH17());            
			  dcbzlr.setI17(bzlr.getI17());            
			  dcbzlr.setJ17(bzlr.getJ17());            
			  dcbzlr.setK17(bzlr.getK17());            
			  dcbzlr.setL17(bzlr.getL17());            
			  dcbzlr.setM17(bzlr.getM17());            
			  dcbzlr.setN17(bzlr.getN17());            
			  dcbzlr.setO17(bzlr.getO17());            
			  dcbzlr.setP17(bzlr.getP17());            
			  dcbzlr.setQ17(bzlr.getQ17());            
			  dcbzlr.setC18(bzlr.getC18());            
			  dcbzlr.setD18(bzlr.getD18());            
			  dcbzlr.setE18(bzlr.getE18());            
			  dcbzlr.setF18(bzlr.getF18());            
			  dcbzlr.setG18(bzlr.getG18());            
			  dcbzlr.setH18(bzlr.getH18());            
			  dcbzlr.setI18(bzlr.getI18());            
			  dcbzlr.setJ18(bzlr.getJ18());            
			  dcbzlr.setK18(bzlr.getK18());            
			  dcbzlr.setL18(bzlr.getL18());            
			  dcbzlr.setM18(bzlr.getM18());            
			  dcbzlr.setN18(bzlr.getN18());            
			  dcbzlr.setO18(bzlr.getO18());            
			  dcbzlr.setP18(bzlr.getP18());            
			  dcbzlr.setQ18(bzlr.getQ18());            
			  dcbzlr.setC19(bzlr.getC19());            
			  dcbzlr.setD19(bzlr.getD19());            
			  dcbzlr.setE19(bzlr.getE19());            
			  dcbzlr.setF19(bzlr.getF19());            
			  dcbzlr.setG19(bzlr.getG19());            
			  dcbzlr.setH19(bzlr.getH19());            
			  dcbzlr.setI19(bzlr.getI19());            
			  dcbzlr.setJ19(bzlr.getJ19());            
			  dcbzlr.setK19(bzlr.getK19());            
			  dcbzlr.setL19(bzlr.getL19());            
			  dcbzlr.setM19(bzlr.getM19());            
			  dcbzlr.setN19(bzlr.getN19());            
			  dcbzlr.setO19(bzlr.getO19());            
			  dcbzlr.setP19(bzlr.getP19());            
			  dcbzlr.setQ19(bzlr.getQ19());            
			  dcbzlr.setC20(bzlr.getC20());            
			  dcbzlr.setD20(bzlr.getD20());            
			  dcbzlr.setE20(bzlr.getE20());            
			  dcbzlr.setF20(bzlr.getF20());            
			  dcbzlr.setG20(bzlr.getG20());            
			  dcbzlr.setH20(bzlr.getH20());            
			  dcbzlr.setI20(bzlr.getI20());            
			  dcbzlr.setJ20(bzlr.getJ20());            
			  dcbzlr.setK20(bzlr.getK20());            
			  dcbzlr.setL20(bzlr.getL20());            
			  dcbzlr.setM20(bzlr.getM20());            
			  dcbzlr.setN20(bzlr.getN20());            
			  dcbzlr.setO20(bzlr.getO20());            
			  dcbzlr.setP20(bzlr.getP20());            
			  dcbzlr.setQ20(bzlr.getQ20());            
			  dcbzlr.setC21(bzlr.getC21());            
			  dcbzlr.setD21(bzlr.getD21());            
			  dcbzlr.setE21(bzlr.getE21());            
			  dcbzlr.setF21(bzlr.getF21());            
			  dcbzlr.setG21(bzlr.getG21());            
			  dcbzlr.setH21(bzlr.getH21());            
			  dcbzlr.setI21(bzlr.getI21());            
			  dcbzlr.setJ21(bzlr.getJ21());            
			  dcbzlr.setK21(bzlr.getK21());            
			  dcbzlr.setL21(bzlr.getL21());            
			  dcbzlr.setM21(bzlr.getM21());            
			  dcbzlr.setN21(bzlr.getN21());            
			  dcbzlr.setO21(bzlr.getO21());            
			  dcbzlr.setP21(bzlr.getP21());            
			  dcbzlr.setQ21(bzlr.getQ21());            
			  dcbzlr.setC22(bzlr.getC22());            
			  dcbzlr.setD22(bzlr.getD22());            
			  dcbzlr.setE22(bzlr.getE22());            
			  dcbzlr.setF22(bzlr.getF22());            
			  dcbzlr.setG22(bzlr.getG22());            
			  dcbzlr.setH22(bzlr.getH22());            
			  dcbzlr.setI22(bzlr.getI22());            
			  dcbzlr.setJ22(bzlr.getJ22());            
			  dcbzlr.setK22(bzlr.getK22());            
			  dcbzlr.setL22(bzlr.getL22());            
			  dcbzlr.setM22(bzlr.getM22());            
			  dcbzlr.setN22(bzlr.getN22());            
			  dcbzlr.setO22(bzlr.getO22());            
			  dcbzlr.setP22(bzlr.getP22());            
			  dcbzlr.setQ22(bzlr.getQ22());            
			  dcbzlr.setC23(bzlr.getC23());            
			  dcbzlr.setD23(bzlr.getD23());            
			  dcbzlr.setE23(bzlr.getE23());            
			  dcbzlr.setF23(bzlr.getF23());            
			  dcbzlr.setG23(bzlr.getG23());            
			  dcbzlr.setH23(bzlr.getH23());            
			  dcbzlr.setI23(bzlr.getI23());            
			  dcbzlr.setJ23(bzlr.getJ23());            
			  dcbzlr.setK23(bzlr.getK23());            
			  dcbzlr.setL23(bzlr.getL23());            
			  dcbzlr.setM23(bzlr.getM23());            
			  dcbzlr.setN23(bzlr.getN23());            
			  dcbzlr.setO23(bzlr.getO23());            
			  dcbzlr.setP23(bzlr.getP23());            
			  dcbzlr.setQ23(bzlr.getQ23());            
			  dcbzlr.setC24(bzlr.getC24());            
			  dcbzlr.setD24(bzlr.getD24());            
			  dcbzlr.setE24(bzlr.getE24());            
			  dcbzlr.setF24(bzlr.getF24());            
			  dcbzlr.setG24(bzlr.getG24());            
			  dcbzlr.setH24(bzlr.getH24());            
			  dcbzlr.setI24(bzlr.getI24());            
			  dcbzlr.setJ24(bzlr.getJ24());            
			  dcbzlr.setK24(bzlr.getK24());            
			  dcbzlr.setL24(bzlr.getL24());            
			  dcbzlr.setM24(bzlr.getM24());            
			  dcbzlr.setN24(bzlr.getN24());            
			  dcbzlr.setO24(bzlr.getO24());            
			  dcbzlr.setP24(bzlr.getP24());            
			  dcbzlr.setQ24(bzlr.getQ24());            
			  dcbzlr.setC25(bzlr.getC25());            
			  dcbzlr.setD25(bzlr.getD25());            
			  dcbzlr.setE25(bzlr.getE25());            
			  dcbzlr.setF25(bzlr.getF25());            
			  dcbzlr.setG25(bzlr.getG25());            
			  dcbzlr.setH25(bzlr.getH25());            
			  dcbzlr.setI25(bzlr.getI25());            
			  dcbzlr.setJ25(bzlr.getJ25());            
			  dcbzlr.setK25(bzlr.getK25());            
			  dcbzlr.setL25(bzlr.getL25());            
			  dcbzlr.setM25(bzlr.getM25());            
			  dcbzlr.setN25(bzlr.getN25());            
			  dcbzlr.setO25(bzlr.getO25());            
			  dcbzlr.setP25(bzlr.getP25());            
			  dcbzlr.setQ25(bzlr.getQ25());            
			  dcbzlr.setC26(bzlr.getC26());            
			  dcbzlr.setD26(bzlr.getD26());            
			  dcbzlr.setE26(bzlr.getE26());            
			  dcbzlr.setF26(bzlr.getF26());            
			  dcbzlr.setG26(bzlr.getG26());            
			  dcbzlr.setH26(bzlr.getH26());            
			  dcbzlr.setI26(bzlr.getI26());            
			  dcbzlr.setJ26(bzlr.getJ26());            
			  dcbzlr.setK26(bzlr.getK26());            
			  dcbzlr.setL26(bzlr.getL26());            
			  dcbzlr.setM26(bzlr.getM26());            
			  dcbzlr.setN26(bzlr.getN26());            
			  dcbzlr.setO26(bzlr.getO26());            
			  dcbzlr.setP26(bzlr.getP26());            
			  dcbzlr.setQ26(bzlr.getQ26());            
			  dcbzlr.setC27(bzlr.getC27());            
			  dcbzlr.setD27(bzlr.getD27());            
			  dcbzlr.setE27(bzlr.getE27());            
			  dcbzlr.setF27(bzlr.getF27());            
			  dcbzlr.setG27(bzlr.getG27());            
			  dcbzlr.setH27(bzlr.getH27());            
			  dcbzlr.setI27(bzlr.getI27());            
			  dcbzlr.setJ27(bzlr.getJ27());            
			  dcbzlr.setK27(bzlr.getK27());            
			  dcbzlr.setL27(bzlr.getL27());            
			  dcbzlr.setM27(bzlr.getM27());            
			  dcbzlr.setN27(bzlr.getN27());            
			  dcbzlr.setO27(bzlr.getO27());            
			  dcbzlr.setP27(bzlr.getP27());            
			  dcbzlr.setQ27(bzlr.getQ27());            
			  dcbzlr.setC28(bzlr.getC28());            
			  dcbzlr.setD28(bzlr.getD28());            
			  dcbzlr.setE28(bzlr.getE28());            
			  dcbzlr.setF28(bzlr.getF28());            
			  dcbzlr.setG28(bzlr.getG28());            
			  dcbzlr.setH28(bzlr.getH28());            
			  dcbzlr.setI28(bzlr.getI28());            
			  dcbzlr.setJ28(bzlr.getJ28());            
			  dcbzlr.setK28(bzlr.getK28());            
			  dcbzlr.setL28(bzlr.getL28());            
			  dcbzlr.setM28(bzlr.getM28());            
			  dcbzlr.setN28(bzlr.getN28());            
			  dcbzlr.setO28(bzlr.getO28());            
			  dcbzlr.setP28(bzlr.getP28());            
			  dcbzlr.setQ28(bzlr.getQ28());            
			  dcbzlr.setC29(bzlr.getC29());            
			  dcbzlr.setD29(bzlr.getD29());            
			  dcbzlr.setE29(bzlr.getE29());            
			  dcbzlr.setF29(bzlr.getF29());            
			  dcbzlr.setG29(bzlr.getG29());            
			  dcbzlr.setH29(bzlr.getH29());            
			  dcbzlr.setI29(bzlr.getI29());            
			  dcbzlr.setJ29(bzlr.getJ29());            
			  dcbzlr.setK29(bzlr.getK29());            
			  dcbzlr.setL29(bzlr.getL29());            
			  dcbzlr.setM29(bzlr.getM29());            
			  dcbzlr.setN29(bzlr.getN29());            
			  dcbzlr.setO29(bzlr.getO29());            
			  dcbzlr.setP29(bzlr.getP29());            
			  dcbzlr.setQ29(bzlr.getQ29());
			  dcbzlr.setPro_1(bzlr.getPro_1());		 
			  dcbzlr.setPro_2(bzlr.getPro_2());   
			  dcbzlr.setPro_3(bzlr.getPro_3());   
			  dcbzlr.setPro_4(bzlr.getPro_4());   
			  dcbzlr.setPro_5(bzlr.getPro_5());            
			  dcbzlr.setPro_6(bzlr.getPro_6());   
			  dcbzlr.setPro_7(bzlr.getPro_7());   
			  dcbzlr.setPro_8(bzlr.getPro_8());   
			  dcbzlr.setPro_9(bzlr.getPro_9());   
			  dcbzlr.setPro_10(bzlr.getPro_10());   
			  dcbzlr.setPro_11(bzlr.getPro_11());   
			  dcbzlr.setPro_12(bzlr.getPro_12());   
			  dcbzlr.setPro_13(bzlr.getPro_13());   
			  dcbzlr.setYefy_1(bzlr.getYefy_1());   
			  dcbzlr.setYefy_2(bzlr.getYefy_2());   
			  dcbzlr.setYefy_3(bzlr.getYefy_3());   
			  dcbzlr.setYefy_4(bzlr.getYefy_4());   
			  dcbzlr.setYefy_5(bzlr.getYefy_5());   
			  dcbzlr.setYefy_6(bzlr.getYefy_6());   
			  dcbzlr.setYefy_7(bzlr.getYefy_7());   
			  dcbzlr.setYefy_8(bzlr.getYefy_8());   
			  dcbzlr.setYefy_9(bzlr.getYefy_9());  
			  dcbzlr.setYefy_10(bzlr.getYefy_10());   
			  dcbzlr.setYefy_11(bzlr.getYefy_11());   
			  dcbzlr.setYefy_12(bzlr.getYefy_12());   
			  dcbzlr.setYefy_13(bzlr.getYefy_13());   
			  dcbzlr.setYefy_14(bzlr.getYefy_14());   
			  dcbzlr.setYefy_15(bzlr.getYefy_15()); 
				addIntoPiecesService.updateDcbzlr(dcbzlr);
			}else{
				addIntoPiecesService.saveDcbzlr(bzlr);
			}
			returnMap.addGlobalMessage(CHANGE_SUCCESS);
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}

		return returnMap;
	}
	
	
	/**
	 * 显示现金流表
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "create_xjl.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView create_xjl( HttpServletRequest request) {
		String customerId = request.getParameter("customerId");
		String productId = request.getParameter("productId");
		List<CustomerInforUpdateCashFlow> customerInforUpdateCashFlow =
                customerInforUpdateService.getCustomerInforUpdateCashFlowById1(customerId,productId);

		JRadModelAndView mv = new JRadModelAndView("/intopieces/dc_xjl",request);
		mv.addObject("customerInforUpdateCashFlow",	customerInforUpdateCashFlow);
		mv.addObject("customerId",customerId);
		mv.addObject("productId",productId);
		return mv;
	}
	
	/**
	 * 执行添加现金流
	 * 
	 * @param customerinfoForm
	 * @param request
	 * @return
	 */
	
	@ResponseBody
	@RequestMapping(value = "cashFlowInsert.page", method = { RequestMethod.POST })
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView cashFlowInsert(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/intopieces/dc_xjl",request);
		try {
			String customerId=request.getParameter("customerId");
			String productId=request.getParameter("productId");
			customerInforUpdateService.insertCustomerInforUpdateCashFlow1(customerId, request,productId);

			List<CustomerInforUpdateCashFlow> customerInforUpdateCashFlow =
                    customerInforUpdateService.getCustomerInforUpdateCashFlowById1(customerId,productId);

			mv.addObject("customerInforUpdateCashFlow",customerInforUpdateCashFlow);
			mv.addObject("customerId",customerId);
			mv.addObject("productId",productId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	
	
	/**
	 * 显示交叉检验
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "create_jcjy.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView create_jcjy(@ModelAttribute CustomerInforFilter filter, HttpServletRequest request) {
		filter.setRequest(request);
		String customerId = request.getParameter("customerId");
		String productId = request.getParameter("productId");

		List<CustomerInforUpdateCrossExamination> crossExaminationList =
                customerInforUpdateService.getCustomerInforUpdateCrossExaminationById1(customerId,productId);

		JRadModelAndView mv = new JRadModelAndView("/intopieces/dc_jcjy",request);
		if(crossExaminationList.size() != 0){
			mv.setViewName("/intopieces/dc_jcjy_update");
		}
		// 获取资产负载表的“所有者权益”
		CustomerInforUpdateBalanceSheet balanceSheet = customerInforUpdateService.findOwnershipFilter1(customerId,productId);

		mv.addObject("sjsy", balanceSheet != null ? balanceSheet.getContentsTextNumbers() : "");
		mv.addObject("crossExaminationList",	crossExaminationList);
		mv.addObject("customerId",customerId);
		mv.addObject("productId",productId);
		return mv;
	}
	
	
	/**
	 * 执行添加交叉检验
	 * @param customerinfoForm
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "saveCrossExamination.page")
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView saveCrossExamination(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/intopieces/dc_jcjy_update",
                                                    request);
		try{
		String jsonStr = RequestHelper.getStringValue(request, "crossExaminationInfo");
		String customerId = RequestHelper.getStringValue(request, "customerId");
		String productId = RequestHelper.getStringValue(request, "productId");
        IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		customerInforUpdateService.insertCustomerInforUpdateCrossExamination1(customerId, user.getId(), jsonStr,productId);

		List<CustomerInforUpdateCrossExamination> crossExaminationList =
                customerInforUpdateService.getCustomerInforUpdateCrossExaminationById(customerId);
		mv.addObject("customerId",customerId);
		mv.addObject("productId",productId);
		mv.addObject("crossExaminationList",	crossExaminationList);
		// 获取资产负载表的“所有者权益”
		CustomerInforUpdateBalanceSheet balanceSheet = customerInforUpdateService.findOwnershipFilter1(customerId,productId);

		mv.addObject("sjsy", balanceSheet != null ? balanceSheet.getContentsTextNumbers() : "");
		}catch(Exception e){
			e.printStackTrace();
		}
		return mv;
	}
	
	
	/**
	 * 显示点货单
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "create_dhd.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView create_dhd( HttpServletRequest request) {
		String customerId = request.getParameter("customerId");
		String productId = request.getParameter("productId");
		List<Dcdhd> dhd = customerInforUpdateService.getDcdhd(customerId,productId);

		JRadModelAndView mv = new JRadModelAndView("/intopieces/dc_dhd",request);
		mv.addObject("dhd",	dhd);
		mv.addObject("customerId",customerId);
		mv.addObject("productId",productId);
		return mv;
	}
	
	
	/**
	 * 执行添加点货单
	 * 
	 * @param customerinfoForm
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "addDhd.page", method = { RequestMethod.POST })
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView addDhd(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/intopieces/dc_dhd",request);
		try {
			String customerId=request.getParameter("customerId");
			String productId=request.getParameter("productId");
			customerInforUpdateService.insertDhd(customerId, request,productId);
			List<Dcdhd> dhd = customerInforUpdateService.getDcdhd(customerId,productId);

			mv.addObject("dhd",dhd);
			mv.addObject("customerId",customerId);
			mv.addObject("productId",productId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	
	/**
	 * 显示固定资产
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "create_gdzc.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView create_gdzc( HttpServletRequest request) {
		String customerId = request.getParameter("customerId");
		String productId = request.getParameter("productId");
		List<Dcgdzc> gdzc = customerInforUpdateService.getDcgdzc(customerId,productId);

		JRadModelAndView mv = new JRadModelAndView("/intopieces/dc_gdzc",request);
		mv.addObject("gdzc",	gdzc);
		mv.addObject("customerId",customerId);
		mv.addObject("productId",productId);
		return mv;
	}
	
	@ResponseBody
	@RequestMapping(value = "addGdzc.page", method = { RequestMethod.POST })
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView addGdzc(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/intopieces/dc_gdzc",request);
		try {
			String customerId=request.getParameter("customerId");
			String productId=request.getParameter("productId");
			customerInforUpdateService.insertGdzc(customerId, request,productId);
			List<Dcgdzc> gdzc = customerInforUpdateService.getDcgdzc(customerId,productId);

			mv.addObject("gdzc",gdzc);
			mv.addObject("customerId",customerId);
			mv.addObject("productId",productId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	
	/**
	 * 显示应付预收
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "create_yfys.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView create_yfys( HttpServletRequest request) {
		String customerId = request.getParameter("customerId");
		String productId = request.getParameter("productId");
		List<Dcyfys> yfys = customerInforUpdateService.getDcyfys(customerId,productId);

		JRadModelAndView mv = new JRadModelAndView("/intopieces/dc_yfys",request);
		mv.addObject("yfys",yfys);
		mv.addObject("customerId",customerId);
		mv.addObject("productId",productId);
		return mv;
	}
	
	@ResponseBody
	@RequestMapping(value = "addYfys.page", method = { RequestMethod.POST })
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView addYfys(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/intopieces/dc_yfys",request);
		try {
			String customerId=request.getParameter("customerId");
			String productId=request.getParameter("productId");
			customerInforUpdateService.insertYsyf(customerId, request,productId);
			List<Dcyfys> yfys = customerInforUpdateService.getDcyfys(customerId,productId);

			mv.addObject("yfys",yfys);
			mv.addObject("customerId",customerId);
			mv.addObject("productId",productId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	
	/**
	 * 显示应收预付
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "create_ysyf.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView create_ysyf( HttpServletRequest request) {
		String customerId = request.getParameter("customerId");
		String productId = request.getParameter("productId");
		List<Dcysyf> ysyf = customerInforUpdateService.getDcysyf(customerId,productId);

		JRadModelAndView mv = new JRadModelAndView("/intopieces/dc_ysyf",request);
		mv.addObject("ysyf",ysyf);
		mv.addObject("customerId",customerId);
		mv.addObject("productId",productId);
		return mv;
	}
	
	@ResponseBody
	@RequestMapping(value = "addYsyf.page", method = { RequestMethod.POST })
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView addYsyf(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/intopieces/dc_ysyf",request);
		try {
			String customerId=request.getParameter("customerId");
			String productId=request.getParameter("productId");
			customerInforUpdateService.insertYsyf(customerId, request,productId);
			List<Dcysyf> ysyf = customerInforUpdateService.getDcysyf(customerId,productId);

			mv.addObject("ysyf",ysyf);
			mv.addObject("customerId",customerId);
			mv.addObject("productId",productId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	
	/**
	 * 显示流水分析
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "create_lsfx.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView create_lsfx( HttpServletRequest request) {
		String customerId = request.getParameter("customerId");
		String productId = request.getParameter("productId");
		Dclsfx lsfx = addIntoPiecesService.findDclsfx(customerId,productId);
		JRadModelAndView mv = new JRadModelAndView("/intopieces/dc_lsfx",request);
		mv.addObject("lsfx",lsfx);
		mv.addObject("customerId",customerId);
		mv.addObject("productId",productId);
		return mv;
	}
	
	
	//保存流水分析
	@ResponseBody
	@RequestMapping(value = "addLsfx.json", method = { RequestMethod.GET })
	public JRadReturnMap addLsfx(@ModelAttribute DclsfxForm form,HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			Dclsfx lsfx = form.createModel(Dclsfx.class);
			//查询是否exist
			Dclsfx dclsfx = addIntoPiecesService.findDclsfx(form.getCustomer_id(),form.getProduct_id());
			if(dclsfx!=null){
				dclsfx.setB2(lsfx.getB2());              
				dclsfx.setF2(lsfx.getF2());            
				dclsfx.setP2(lsfx.getP2());            
				dclsfx.setS2(lsfx.getS2());            
				dclsfx.setC3(lsfx.getC3());            
				dclsfx.setI3(lsfx.getI3());            
				dclsfx.setM3(lsfx.getM3());            
				dclsfx.setQ3(lsfx.getQ3());            
				dclsfx.setS3(lsfx.getS3());            
				dclsfx.setC4(lsfx.getC4());            
				dclsfx.setD4(lsfx.getD4());            
				dclsfx.setE4(lsfx.getE4());            
				dclsfx.setF4(lsfx.getF4());            
				dclsfx.setG4(lsfx.getG4());            
				dclsfx.setH4(lsfx.getH4());            
				dclsfx.setI4(lsfx.getI4());            
				dclsfx.setJ4(lsfx.getJ4());            
				dclsfx.setK4(lsfx.getK4());            
				dclsfx.setL4(lsfx.getL4());            
				dclsfx.setM4(lsfx.getM4());            
				dclsfx.setN4(lsfx.getN4());            
				dclsfx.setC5(lsfx.getC5());            
				dclsfx.setD5(lsfx.getD5());            
				dclsfx.setE5(lsfx.getE5());            
				dclsfx.setF5(lsfx.getF5());            
				dclsfx.setG5(lsfx.getG5());            
				dclsfx.setH5(lsfx.getH5());            
				dclsfx.setI5(lsfx.getI5());            
				dclsfx.setJ5(lsfx.getJ5());            
				dclsfx.setK5(lsfx.getK5());           
				dclsfx.setL5(lsfx.getL5());            
				dclsfx.setM5(lsfx.getM5());            
				dclsfx.setN5(lsfx.getN5());            
				dclsfx.setO5(lsfx.getO5());            
				dclsfx.setP5(lsfx.getP5());            
				dclsfx.setC6(lsfx.getC6());            
				dclsfx.setD6(lsfx.getD6());            
				dclsfx.setE6(lsfx.getE6());            
				dclsfx.setF6(lsfx.getF6());            
				dclsfx.setG6(lsfx.getG6());            
				dclsfx.setH6(lsfx.getH6());            
				dclsfx.setI6(lsfx.getI6());            
				dclsfx.setJ6(lsfx.getJ6());            
				dclsfx.setK6(lsfx.getK6());            
				dclsfx.setL6(lsfx.getL6());            
				dclsfx.setM6(lsfx.getM6());            
				dclsfx.setN6(lsfx.getN6());            
				dclsfx.setO6(lsfx.getO6());            
				dclsfx.setP6(lsfx.getP6());            
				dclsfx.setC7(lsfx.getC7());            
				dclsfx.setD7(lsfx.getD7());            
				dclsfx.setE7(lsfx.getE7());            
				dclsfx.setF7(lsfx.getF7());            
				dclsfx.setG7(lsfx.getG7());            
				dclsfx.setH7(lsfx.getH7());            
				dclsfx.setI7(lsfx.getI7());            
				dclsfx.setJ7(lsfx.getJ7());            
				dclsfx.setK7(lsfx.getK7());            
				dclsfx.setL7(lsfx.getL7());            
				dclsfx.setM7(lsfx.getM7());            
				dclsfx.setN7(lsfx.getN7());            
				dclsfx.setO7(lsfx.getO7());            
				dclsfx.setP7(lsfx.getP7());            
				dclsfx.setC8(lsfx.getC8());            
				dclsfx.setD8(lsfx.getD8());            
				dclsfx.setE8(lsfx.getE8());            
				dclsfx.setF8(lsfx.getF8());            
				dclsfx.setG8(lsfx.getG8());            
				dclsfx.setH8(lsfx.getH8());            
				dclsfx.setI8(lsfx.getI8());            
				dclsfx.setJ8(lsfx.getJ8());            
				dclsfx.setK8(lsfx.getK8());            
				dclsfx.setL8(lsfx.getL8());            
				dclsfx.setM8(lsfx.getM8());            
				dclsfx.setN8(lsfx.getN8());            
				dclsfx.setO8(lsfx.getO8());            
				dclsfx.setP8(lsfx.getP8());            
				dclsfx.setC10(lsfx.getC10());            
				dclsfx.setD10(lsfx.getD10());            
				dclsfx.setE10(lsfx.getE10());            
				dclsfx.setF10(lsfx.getF10());            
				dclsfx.setG10(lsfx.getG10());            
				dclsfx.setH10(lsfx.getH10());            
				dclsfx.setI10(lsfx.getI10());            
				dclsfx.setJ10(lsfx.getJ10());            
				dclsfx.setK10(lsfx.getK10());            
				dclsfx.setL10(lsfx.getL10());            
				dclsfx.setM10(lsfx.getM10());            
				dclsfx.setN10(lsfx.getN10());            
				dclsfx.setC11(lsfx.getC11());            
				dclsfx.setD11(lsfx.getD11());            
				dclsfx.setE11(lsfx.getE11());            
				dclsfx.setF11(lsfx.getF11());            
				dclsfx.setG11(lsfx.getG11());            
				dclsfx.setH11(lsfx.getH11());            
				dclsfx.setI11(lsfx.getI11());            
				dclsfx.setJ11(lsfx.getJ11());            
				dclsfx.setK11(lsfx.getK11());            
				dclsfx.setL11(lsfx.getL11());            
				dclsfx.setM11(lsfx.getM11());            
				dclsfx.setN11(lsfx.getN11());            
				dclsfx.setO11(lsfx.getO11());            
				dclsfx.setP11(lsfx.getP11());            
				dclsfx.setC12(lsfx.getC12());            
				dclsfx.setD12(lsfx.getD12());            
				dclsfx.setE12(lsfx.getE12());            
				dclsfx.setF12(lsfx.getF12());            
				dclsfx.setG12(lsfx.getG12());            
				dclsfx.setH12(lsfx.getH12());            
				dclsfx.setI12(lsfx.getI12());            
				dclsfx.setJ12(lsfx.getJ12());            
				dclsfx.setK12(lsfx.getK12());            
				dclsfx.setL12(lsfx.getL12());            
				dclsfx.setM12(lsfx.getM12());            
				dclsfx.setN12(lsfx.getN12());            
				dclsfx.setO12(lsfx.getO12());            
				dclsfx.setC13(lsfx.getC13());            
				dclsfx.setD13(lsfx.getD13());            
				dclsfx.setE13(lsfx.getE13());            
				dclsfx.setF13(lsfx.getF13());            
				dclsfx.setG13(lsfx.getG13());            
				dclsfx.setH13(lsfx.getH13());            
				dclsfx.setI13(lsfx.getI13());            
				dclsfx.setJ13(lsfx.getJ13());            
				dclsfx.setK13(lsfx.getK13());            
				dclsfx.setL13(lsfx.getL13());            
				dclsfx.setM13(lsfx.getM13());            
				dclsfx.setN13(lsfx.getN13());            
				dclsfx.setO13(lsfx.getO13());            
				dclsfx.setC14(lsfx.getC14());            
				dclsfx.setD14(lsfx.getD14());            
				dclsfx.setE14(lsfx.getE14());            
				dclsfx.setF14(lsfx.getF14());            
				dclsfx.setG14(lsfx.getG14());            
				dclsfx.setH14(lsfx.getH14());            
				dclsfx.setI14(lsfx.getI14());            
				dclsfx.setJ14(lsfx.getJ14());            
				dclsfx.setK14(lsfx.getK14());            
				dclsfx.setL14(lsfx.getL14());            
				dclsfx.setM14(lsfx.getM14());            
				dclsfx.setN14(lsfx.getN14());            
				dclsfx.setO14(lsfx.getO14());            
				dclsfx.setP14(lsfx.getP14());            
				dclsfx.setC15(lsfx.getC15());            
				dclsfx.setD15(lsfx.getD15());            
				dclsfx.setE15(lsfx.getE15());            
				dclsfx.setF15(lsfx.getF15());            
				dclsfx.setG15(lsfx.getG15());            
				dclsfx.setH15(lsfx.getH15());            
				dclsfx.setI15(lsfx.getI15());            
				dclsfx.setJ15(lsfx.getJ15());            
				dclsfx.setK15(lsfx.getK15());            
				dclsfx.setL15(lsfx.getL15());            
				dclsfx.setM15(lsfx.getM15());            
				dclsfx.setN15(lsfx.getN15());            
				dclsfx.setO15(lsfx.getO15());            
				dclsfx.setC16(lsfx.getC16());            
				dclsfx.setD16(lsfx.getD16());            
				dclsfx.setE16(lsfx.getE16());            
				dclsfx.setF16(lsfx.getF16());            
				dclsfx.setG16(lsfx.getG16());            
				dclsfx.setH16(lsfx.getH16());            
				dclsfx.setI16(lsfx.getI16());            
				dclsfx.setJ16(lsfx.getJ16());            
				dclsfx.setK16(lsfx.getK16());            
				dclsfx.setL16(lsfx.getL16());            
				dclsfx.setM16(lsfx.getM16());            
				dclsfx.setN16(lsfx.getN16());            
				dclsfx.setO16(lsfx.getO16());            
				dclsfx.setC17(lsfx.getC17());            
				dclsfx.setD17(lsfx.getD17());            
				dclsfx.setE17(lsfx.getE17());            
				dclsfx.setF17(lsfx.getF17());            
				dclsfx.setG17(lsfx.getG17());            
				dclsfx.setH17(lsfx.getH17());            
				dclsfx.setI17(lsfx.getI17());            
				dclsfx.setJ17(lsfx.getJ17());            
				dclsfx.setK17(lsfx.getK17());            
				dclsfx.setL17(lsfx.getL17());            
				dclsfx.setM17(lsfx.getM17());            
				dclsfx.setN17(lsfx.getN17());            
				dclsfx.setO17(lsfx.getO17());            
				dclsfx.setP17(lsfx.getP17());            
				dclsfx.setC18(lsfx.getC18());            
				dclsfx.setD18(lsfx.getD18());            
				dclsfx.setE18(lsfx.getE18());            
				dclsfx.setF18(lsfx.getF18());            
				dclsfx.setG18(lsfx.getG18());            
				dclsfx.setH18(lsfx.getH18());            
				dclsfx.setI18(lsfx.getI18());            
				dclsfx.setJ18(lsfx.getJ18());            
				dclsfx.setK18(lsfx.getK18());            
				dclsfx.setL18(lsfx.getL18());            
				dclsfx.setM18(lsfx.getM18());            
				dclsfx.setN18(lsfx.getN18());            
				dclsfx.setO18(lsfx.getO18());            
				dclsfx.setC19(lsfx.getC19());            
				dclsfx.setD19(lsfx.getD19());            
				dclsfx.setE19(lsfx.getE19());            
				dclsfx.setF19(lsfx.getF19());            
				dclsfx.setG19(lsfx.getG19());            
				dclsfx.setH19(lsfx.getH19());            
				dclsfx.setI19(lsfx.getI19());            
				dclsfx.setJ19(lsfx.getJ19());            
				dclsfx.setK19(lsfx.getK19());            
				dclsfx.setL19(lsfx.getL19());            
				dclsfx.setM19(lsfx.getM19());            
				dclsfx.setN19(lsfx.getN19());            
				dclsfx.setO19(lsfx.getO19());            
				dclsfx.setC20(lsfx.getC20());            
				dclsfx.setD20(lsfx.getD20());            
				dclsfx.setE20(lsfx.getE20());            
				dclsfx.setF20(lsfx.getF20());            
				dclsfx.setG20(lsfx.getG20());            
				dclsfx.setH20(lsfx.getH20());            
				dclsfx.setI20(lsfx.getI20());            
				dclsfx.setJ20(lsfx.getJ20());            
				dclsfx.setK20(lsfx.getK20());            
				dclsfx.setL20(lsfx.getL20());            
				dclsfx.setM20(lsfx.getM20());            
				dclsfx.setN20(lsfx.getN20());            
				dclsfx.setO20(lsfx.getO20());            
				dclsfx.setP20(lsfx.getP20());            
				dclsfx.setC21(lsfx.getC21());            
				dclsfx.setD21(lsfx.getD21());            
				dclsfx.setE21(lsfx.getE21());            
				dclsfx.setF21(lsfx.getF21());            
				dclsfx.setG21(lsfx.getG21());            
				dclsfx.setH21(lsfx.getH21());            
				dclsfx.setI21(lsfx.getI21());            
				dclsfx.setJ21(lsfx.getJ21());            
				dclsfx.setK21(lsfx.getK21());            
				dclsfx.setL21(lsfx.getL21());            
				dclsfx.setM21(lsfx.getM21());            
				dclsfx.setN21(lsfx.getN21());            
				dclsfx.setO21(lsfx.getO21());            
				dclsfx.setC22(lsfx.getC22());            
				dclsfx.setD22(lsfx.getD22());            
				dclsfx.setE22(lsfx.getE22());            
				dclsfx.setF22(lsfx.getF22());            
				dclsfx.setG22(lsfx.getG22());            
				dclsfx.setH22(lsfx.getH22());            
				dclsfx.setI22(lsfx.getI22());            
				dclsfx.setJ22(lsfx.getJ22());            
				dclsfx.setK22(lsfx.getK22());            
				dclsfx.setL22(lsfx.getL22());            
				dclsfx.setM22(lsfx.getM22());            
				dclsfx.setN22(lsfx.getN22());            
				dclsfx.setO22(lsfx.getO22());            
				dclsfx.setC23(lsfx.getC23());            
				dclsfx.setD23(lsfx.getD23());            
				dclsfx.setE23(lsfx.getE23());            
				dclsfx.setF23(lsfx.getF23());            
				dclsfx.setG23(lsfx.getG23());            
				dclsfx.setH23(lsfx.getH23());            
				dclsfx.setI23(lsfx.getI23());            
				dclsfx.setJ23(lsfx.getJ23());            
				dclsfx.setK23(lsfx.getK23());            
				dclsfx.setL23(lsfx.getL23());            
				dclsfx.setM23(lsfx.getM23());            
				dclsfx.setN23(lsfx.getN23());            
				dclsfx.setO23(lsfx.getO23());            
				dclsfx.setP23(lsfx.getP23());            
				dclsfx.setC24(lsfx.getC24());            
				dclsfx.setD24(lsfx.getD24());            
				dclsfx.setE24(lsfx.getE24());            
				dclsfx.setF24(lsfx.getF24());            
				dclsfx.setG24(lsfx.getG24());            
				dclsfx.setH24(lsfx.getH24());            
				dclsfx.setI24(lsfx.getI24());            
				dclsfx.setJ24(lsfx.getJ24());            
				dclsfx.setK24(lsfx.getK24());            
				dclsfx.setL24(lsfx.getL24());            
				dclsfx.setM24(lsfx.getM24());            
				dclsfx.setN24(lsfx.getN24());            
				dclsfx.setO24(lsfx.getO24());            
				dclsfx.setC25(lsfx.getC25());            
				dclsfx.setD25(lsfx.getD25());            
				dclsfx.setE25(lsfx.getE25());            
				dclsfx.setF25(lsfx.getF25());            
				dclsfx.setG25(lsfx.getG25());            
				dclsfx.setH25(lsfx.getH25());            
				dclsfx.setI25(lsfx.getI25());            
				dclsfx.setJ25(lsfx.getJ25());            
				dclsfx.setK25(lsfx.getK25());            
				dclsfx.setL25(lsfx.getL25());            
				dclsfx.setM25(lsfx.getM25());            
				dclsfx.setN25(lsfx.getN25());            
				dclsfx.setO25(lsfx.getO25());
				
				dclsfx.setKyfx(lsfx.getKyfx()); 
				dclsfx.setSjfy(lsfx.getSjfy()); 
				dclsfx.setQtfy(lsfx.getQtfy()); 
				
				addIntoPiecesService.updateDclsfx(dclsfx);
			}else{
				addIntoPiecesService.saveDclsfx(lsfx);
			}
			returnMap.addGlobalMessage(CHANGE_SUCCESS);
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}

		return returnMap;
	}
	
	/**
	 * 校验客户是否为风险客户
	 * 当客户处于风险名单中时，无法进行新增进件操作
	 * @return
	 */
	 @ResponseBody
		@RequestMapping(value = "isInRiskList.json")
		public JRadReturnMap isInRiskList(HttpServletRequest request) {
			String customerId = request.getParameter(ID);
			JRadReturnMap returnMap = new JRadReturnMap();
			if (returnMap.isSuccess()) {
				try {
					RiskCustomer riskCustomer = intoPiecesService.findRiskListByCustomerId(customerId);
					if(riskCustomer != null){
						returnMap.put("isInList", true);
					}
					
				}catch (Exception e) {
					returnMap.put(JRadConstants.MESSAGE,"系统异常");
					returnMap.put(JRadConstants.SUCCESS, false);
					return WebRequestHelper.processException(e);
				}
			}else{
				returnMap.setSuccess(false);
				returnMap.addGlobalError(CustomerInforConstant.CREATEERROR);
			}
			return returnMap;
		}
	
	
	
}
