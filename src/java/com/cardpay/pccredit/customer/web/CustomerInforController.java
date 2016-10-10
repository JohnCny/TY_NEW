package com.cardpay.pccredit.customer.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cardpay.pccredit.customer.constant.CustomerInforConstant;
import com.cardpay.pccredit.customer.filter.VideoAccessoriesFilter;
import com.cardpay.pccredit.customer.model.CustomerCareersInformation;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.service.CardInfomationService;
import com.cardpay.pccredit.customer.service.CustomerAccountInfoService;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.datapri.constant.DataPriConstants;
import com.cardpay.pccredit.divisional.constant.DivisionalProgressEnum;
import com.cardpay.pccredit.divisional.constant.DivisionalTypeEnum;
import com.cardpay.pccredit.divisional.service.DivisionalService;
import com.cardpay.pccredit.intopieces.constant.Constant;
import com.cardpay.pccredit.intopieces.filter.IntoPiecesFilter;
import com.cardpay.pccredit.intopieces.model.CustomerAccountData;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationCom;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationContact;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationGuarantor;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationInfo;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationOther;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationRecom;
import com.cardpay.pccredit.intopieces.model.IntoPieces;
import com.cardpay.pccredit.intopieces.model.VideoAccessories;
import com.cardpay.pccredit.intopieces.service.IntoPiecesService;
import com.cardpay.pccredit.product.model.AddressAccessories;
import com.cardpay.pccredit.product.model.AppendixDict;
import com.cardpay.pccredit.product.model.ProductAttribute;
import com.cardpay.pccredit.product.service.ProductService;
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
import com.wicresoft.jrad.modules.privilege.model.User;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;
import com.wicresoft.util.web.RequestHelper;

/**
 * 
 * @author shaoming
 *
 */
@Controller
@RequestMapping("/customer/basiccustomerinfor/*")
@JRadModule("customer.basiccustomerinfor")
public class CustomerInforController extends BaseController{

	@Autowired
	private CustomerInforService customerInforservice;
	
	@Autowired
	private DivisionalService divisionalservice;
	
	@Autowired
	private CardInfomationService cardInfomationService;
	
	@Autowired
	private CustomerAccountInfoService customerAccountInfoService;
	
	@Autowired
	private IntoPiecesService intoPiecesService;

	@Autowired
	private CustomerInforService customerInforService;
	
	@Autowired
	private ProductService productService;
	/**
	 * 浏览页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	

	@ResponseBody
	@RequestMapping(value = "browse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browse(@ModelAttribute CustomerInforFilter filter, HttpServletRequest request) {
		filter.setRequest(request);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		filter.setUserId(user.getId());
		QueryResult<CustomerInfor> result = customerInforservice.findCustomerInforByFilter(filter);		
		JRadPagedQueryResult<CustomerInfor> pagedResult = new JRadPagedQueryResult<CustomerInfor>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/customer/customerInfor/customerinfor_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);
		return mv;
	}
	 */
	
	/**
	 * 进件浏览页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "intopiecesbrowse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView intopiecesbrowse(@ModelAttribute IntoPiecesFilter filter,
			HttpServletRequest request) {
		filter.setRequest(request);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String userId = user.getId();
		filter.setUserId(userId);
		QueryResult<IntoPieces> result = intoPiecesService.findintoPiecesByFilter(filter);
		JRadPagedQueryResult<IntoPieces> pagedResult = new JRadPagedQueryResult<IntoPieces>(
				filter, result);

		JRadModelAndView mv = new JRadModelAndView("/customer/customerInfor/intopieces_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);

		return mv;
	}
	
	
	
	
	/**
	 * 增加页面
	 * 
	 * @param request
	 * @return
	
	@ResponseBody
	@RequestMapping(value = "create.page")
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView create(HttpServletRequest request) {        
		JRadModelAndView mv = new JRadModelAndView("/customer/customerInfor/customerinfor_create", request);
		return mv;
	}
	
	 */
	/**
	 * 批量导入客户基本信息页面
	 * 
	 * @param request
	 * @return
	
	@ResponseBody
	@RequestMapping(value = "importCustomerInfo.page")
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView importCustomerInfo(HttpServletRequest request) {        
		JRadModelAndView mv = new JRadModelAndView("/customer/customerInfor/customer_import", request);
		return mv;
	}
	 */
	/**
	 * 批量导入客户基本信息提交
	 * 
	 * @param request
	 * @return
	 * @throws IOException 
	
	@ResponseBody
	@RequestMapping(value = "importSubmit.json")
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView importSubmit(@RequestParam(value = "file", required = false) MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws IOException {        
		response.setContentType("text/html;charset=utf-8");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(file==null||file.isEmpty()){
				map.put(JRadConstants.SUCCESS, false);
				map.put(JRadConstants.MESSAGE, CustomerInforConstant.IMPORTEMPTY);
				JSONObject obj = JSONObject.fromObject(map);
				response.getWriter().print(obj.toString());
				return null;
			}
			customerInforservice.importBatchCustomerInfoByExcel(file);
			map.put(JRadConstants.SUCCESS, true);
			map.put(JRadConstants.MESSAGE, CustomerInforConstant.IMPORTSUCCESS);
			JSONObject obj = JSONObject.fromObject(map);
			response.getWriter().print(obj.toString());
		} catch (Exception e) {

			if(e instanceof  UploadCustomerInfoException){
				map.put(JRadConstants.SUCCESS, false);
				map.put(JRadConstants.MESSAGE, e.getMessage());
				JSONObject obj = JSONObject.fromObject(map);
				response.getWriter().print(obj.toString());
			}else{
				e.printStackTrace();
				map.put(JRadConstants.SUCCESS, false);
				map.put(JRadConstants.MESSAGE, CustomerInforConstant.IMPORTERROR);
				JSONObject obj = JSONObject.fromObject(map);
				response.getWriter().print(obj.toString());
			}
		}
		return null;
	}
	 */
	
	 
	/**
	 * 显示页面
	 * 
	 * @param request
	 * @return

		
	@ResponseBody
	@RequestMapping(value = "display.page")
	@JRadOperation(JRadOperation.DISPLAY)
	public AbstractModelAndView display(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/customer/customerInfor/customerinfor_display", request);

		String customerInforId = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(customerInforId)) {
			CustomerInfor customerInfor = customerInforservice.findCustomerInforById(customerInforId);
			mv.addObject(CustomerInforConstant.CUSTOMERINFOR, customerInfor);
		}

		return mv;
	}
	
		 */
		
	/**
	 * 显示页面(进件提交的快照信息)
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "displayclone.page")
	@JRadOperation(JRadOperation.DISPLAY)
	public AbstractModelAndView displayClone(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/customer/customerInforDisplay/customerinfor_display", request);
		String applicationId = request.getParameter("applicationId");
		String customerInforId = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(customerInforId) && StringUtils.isNotEmpty(applicationId)) {
			CustomerInfor customerInfor = customerInforservice.findCloneCustomerInforByAppId(applicationId);
			mv.addObject(CustomerInforConstant.CUSTOMERINFOR, customerInfor);
			mv.addObject("applicationId", applicationId);
		}

		return mv;
	}
	
	
	
	/**
	 * 执行删除
	 * 
	 * @param request
	 * @return
	
	@ResponseBody
	@RequestMapping(value = "delete.json")
	@JRadOperation(JRadOperation.DELETE)
	public JRadReturnMap delete(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			String customerinforId = RequestHelper.getStringValue(request, ID);
			customerInforservice.deleteCustomerInfor(customerinforId);
			returnMap.addGlobalMessage(DELETE_SUCCESS);
		}catch (Exception e) {
			returnMap.put(JRadConstants.MESSAGE, DataPriConstants.SYS_EXCEPTION_MSG);
			returnMap.put(JRadConstants.SUCCESS, false);
			return WebRequestHelper.processException(e);
		}
		return returnMap;
	}
	
	 */
	/**
	 * 移交客户
	 * 
	 * @param request
	 * @return
	
	@ResponseBody
	@RequestMapping(value = "transfer.json",method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public Map<String,Object> transfer(HttpServletRequest request) {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		try {
			String customerId = RequestHelper.getStringValue(request, ID);
			
			Boolean flag = divisionalservice.insertDivisionalCustomer(customerId,DivisionalTypeEnum.initiative,DivisionalProgressEnum.charge);
			if(flag){
				returnMap.put(JRadConstants.SUCCESS,true);
				returnMap.put(JRadConstants.MESSAGE,CustomerInforConstant.TRANSFERSUCCESS);
			}else{
				returnMap.put(JRadConstants.SUCCESS,false);
				returnMap.put(JRadConstants.MESSAGE,CustomerInforConstant.TRANSFERSUCCESS);
			}
		}catch (Exception e) {
			returnMap.put(JRadConstants.MESSAGE, DataPriConstants.SYS_EXCEPTION_MSG);
			returnMap.put(JRadConstants.SUCCESS, false);
			return WebRequestHelper.processException(e);
		}
		return returnMap;
	}
	
	 */
	/**
	 * 修改页面
	 * 
	 * @param request
	 * @return
	
	@ResponseBody
	@RequestMapping(value = "change.page")
	@JRadOperation(JRadOperation.CHANGE)
	public AbstractModelAndView change(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/customer/customerInfor/customerinfor_change", request);
		String customerInforId = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(customerInforId)) {
			CustomerInfor customerInfor = customerInforservice.findCustomerInforById(customerInforId);
			mv.addObject("customerInfor", customerInfor);
		}
		return mv;
	}
	
	 */
	
	/**
	 * 修改页面
	 * 
	 * @param request
	 * @return
	
	@ResponseBody
	@RequestMapping(value = "changewh.page")
	@JRadOperation(JRadOperation.CHANGE)
	public AbstractModelAndView changewh(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/customer/customerInforUpdate/customerinfor_change", request);
		String customerInforId = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(customerInforId)) {
			CustomerInfor customerInfor = customerInforservice.findCustomerInforById(customerInforId);
			mv.addObject("customerInfor", customerInfor);
			mv.addObject("customerId", customerInfor.getId());
		}
		return mv;
	}
	
	 */
	/**
	 * 执行修改
	 * 
	 * @param sample2
	 * @param request
	 * @return
	
	@ResponseBody
	@RequestMapping(value = "update.json")
	@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap update(@ModelAttribute CustomerInforForm form, HttpServletRequest request) {

		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), form);
		if (returnMap.isSuccess()) {
			try {
				CustomerInfor customerInfor = form.createModel(CustomerInfor.class);
				customerInforservice.updateCustomerInfor(customerInfor);
				returnMap.put(RECORD_ID, customerInfor.getId());
				returnMap.put("customerId",customerInfor.getId());
				returnMap.addGlobalMessage(CHANGE_SUCCESS);
			}catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}

		return returnMap;
	}
	
	 */
	
	/**
	 * 查看客户账户信息
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "displayaccountinfolist.page")
	@JRadOperation(JRadOperation.DISPLAYACCOUNT)
	public AbstractModelAndView displayCardInfomation(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/customer/customerInfor/customercardinfor_browse", request);
		String customerId = request.getParameter("customerId");
		if (StringUtils.isNotEmpty(customerId)) {
			List<CustomerAccountInfoForm> customerAccountInfoForms = customerAccountInfoService.findCustomerAccountByCustomerId(customerId);
			mv.addObject("customerAccountInfoForms", customerAccountInfoForms);
		}
		return mv;
	}
	
	
	
	/**
	 * 查看账户信息
	 * 
	 * @param request
	 * @return
	*/
	@ResponseBody
	@RequestMapping(value = "displayaccountinfo.page")
	@JRadOperation(JRadOperation.DISPLAY)
	public AbstractModelAndView displayCardInfo(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/customer/customerInfor/customeraccountinfo_display", request);
		String id = request.getParameter("id");
		if (StringUtils.isNotEmpty(id)) {
			CustomerAccountInfoForm customerAccountInfoForm = customerAccountInfoService.findCustomerAccountById(id);
			mv.addObject("accountInfo", customerAccountInfoForm);
			CardInfomationFrom cardInfomationFrom = cardInfomationService.findCardInfoByCustomerIdAndCardNumber(
					customerAccountInfoForm.getCustomerId(), customerAccountInfoForm.getCardNumber());
			mv.addObject("cardInfo", cardInfomationFrom);
		}
		return mv;
	}
	 
	/**
	 * 链接到影像资料上传页面
	 * 
	 * @param request
	 * @return
	 
	@ResponseBody
	@RequestMapping(value = "create_yxzl.page")
	@JRadOperation(JRadOperation.DISPLAY)
	public AbstractModelAndView createYxzl(@ModelAttribute VideoAccessoriesFilter filter,HttpServletRequest request) {
		filter.setCustomerId(request.getParameter(ID));
		filter.setRequest(request);
		QueryResult<VideoAccessories> result = customerInforservice.findCustomerVideoAccessoriesByFilter(filter);		
		JRadPagedQueryResult<VideoAccessories> pagedResult = new JRadPagedQueryResult<VideoAccessories>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/customer/customerInforUpdate/customerinfoupdate_yxzl", request);
		mv.addObject("customerId",filter.getCustomerId());
		mv.addObject(PAGED_RESULT, pagedResult);
		return mv;
	}
	*/
	/**
	 * 影像资料保存
	 * 
	 * @param request
	 * @return
	 * @throws IOException 
	
	@ResponseBody
	@RequestMapping(value = "saveYxzl.json",method = { RequestMethod.POST })
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView saveYxzl(@RequestParam(value = "file", required = false) MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			if(file==null||file.isEmpty()){
				map.put(JRadConstants.SUCCESS, false);
				map.put(JRadConstants.MESSAGE, Constant.FILE_EMPTY);
				JSONObject obj = JSONObject.fromObject(map);
				response.getWriter().print(obj.toString());
				return null;
			}
			customerInforservice.saveYxzlByCustomerId(request.getParameter("customerId"),request.getParameter("remark"),file);
			map.put(JRadConstants.SUCCESS, true);
			map.put(JRadConstants.MESSAGE, Constant.SUCCESS_MESSAGE);
			JSONObject obj = JSONObject.fromObject(map);
			response.getWriter().print(obj.toString());
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			map.put(JRadConstants.SUCCESS, false);
			map.put(JRadConstants.MESSAGE, Constant.FAIL_MESSAGE);
			JSONObject obj = JSONObject.fromObject(map);
			response.getWriter().print(obj.toString());
		}
		return null;
	}
	 */
	/**
	 * 删除影像资料
	 * 
	 * @param request
	 * @return
	 * @throws IOException 
	
	@ResponseBody
	@RequestMapping(value = "deleteYxzl.json",method = { RequestMethod.POST })
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView deleteYxzlById(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			customerInforservice.deleteYxzlById(request.getParameter(ID));
			map.put(JRadConstants.SUCCESS, true);
			map.put(JRadConstants.MESSAGE, Constant.SUCCESS_MESSAGE);
			JSONObject obj = JSONObject.fromObject(map);
			response.getWriter().print(obj.toString());
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			map.put(JRadConstants.SUCCESS, false);
			map.put(JRadConstants.MESSAGE, Constant.FAIL_MESSAGE);
			JSONObject obj = JSONObject.fromObject(map);
			response.getWriter().print(obj.toString());
		}
		return null;
	}
	 */
	/**
	 * 下载影像资料
	 * 
	 * @param request
	 * @return
	 * @throws IOException 
	
	@ResponseBody
	@RequestMapping(value = "downLoadYxzl.json",method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.EXPORT)
	public AbstractModelAndView downLoadYxzlById(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			customerInforservice.downLoadYxzlById(response,request.getParameter(ID));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	 */
	/**
	 * 下载影像资料
	 * 
	 * @param request
	 * @return
	 * @throws IOException 
	
	@ResponseBody
	@RequestMapping(value = "viewPicture.page",method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.QUERY)
	public AbstractModelAndView viewPicture(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			File file = null;
			Map<String, String> map = customerInforservice
					.createThumbnail(request.getParameter(ID));
			if (Boolean.parseBoolean(request.getParameter("flag"))) {
				// 输出原始图片 
				file = new File(map.get("old"));
			} else {
				// 输出缩略图 
				file = new File(map.get("new"));
			}
			if (file.exists()) {
				response.setContentType("image/*");
				InputStream is = new FileInputStream(file);
				OutputStream os = response.getOutputStream();
				IOUtils.copy(is, os);
				is.close();
				os.flush();
				os.close();
			} else {
				response.setStatus(304);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	 */
}
