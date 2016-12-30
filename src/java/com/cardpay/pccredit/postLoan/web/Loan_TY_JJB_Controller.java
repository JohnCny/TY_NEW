package com.cardpay.pccredit.postLoan.web;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cardpay.pccredit.customer.constant.CustomerInforConstant;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.model.CustomerParameter;
import com.cardpay.pccredit.customer.model.ParameterInformaion;
import com.cardpay.pccredit.customer.service.CustomerParameterService;
import com.cardpay.pccredit.customer.service.MaintenanceService;
import com.cardpay.pccredit.intopieces.filter.IntoPiecesFilter;
import com.cardpay.pccredit.intopieces.model.DhApplnAttachmentBatch;
import com.cardpay.pccredit.intopieces.model.DhApplnAttachmentDetail;
import com.cardpay.pccredit.intopieces.model.DhApplnAttachmentList;
import com.cardpay.pccredit.intopieces.model.IntoPieces;
import com.cardpay.pccredit.intopieces.model.QzApplnAttachmentBatch;
import com.cardpay.pccredit.intopieces.model.QzApplnAttachmentDetail;
import com.cardpay.pccredit.intopieces.model.QzApplnAttachmentList;
import com.cardpay.pccredit.intopieces.service.AddIntoPiecesService;
import com.cardpay.pccredit.intopieces.service.IntoPiecesService;
import com.cardpay.pccredit.ipad.model.UserIpad;
import com.cardpay.pccredit.manager.web.SysOrganizationForm;
import com.cardpay.pccredit.postLoan.dao.PostLoanDao;
import com.cardpay.pccredit.postLoan.filter.FcloaninfoFilter;
import com.cardpay.pccredit.postLoan.filter.PostLoanFilter;
import com.cardpay.pccredit.postLoan.model.CreditProcess;
import com.cardpay.pccredit.postLoan.model.Fcloaninfo;
import com.cardpay.pccredit.postLoan.model.MibusidataForm;
import com.cardpay.pccredit.postLoan.model.Rarepaylist;
import com.cardpay.pccredit.postLoan.model.RarepaylistForm;
import com.cardpay.pccredit.postLoan.service.PostLoanService;
import com.cardpay.pccredit.product.model.ProductAttribute;
import com.cardpay.pccredit.report.filter.CustomerMoveFilter;
import com.cardpay.pccredit.report.model.CustomerMoveForm;
import com.cardpay.pccredit.riskControl.model.RiskCustomer;
import com.cardpay.pccredit.zrrtz.Util.ExportExcel;
import com.cardpay.pccredit.zrrtz.model.IncomingData;
import com.cardpay.pccredit.zrrtz.model.ZrrtzFilter;
import com.cardpay.workflow.constant.ApproveOperationTypeEnum;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.constant.JRadConstants;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
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

@Controller
@RequestMapping("/postLoan/post/*")
@JRadModule("postLoan.post")
public class Loan_TY_JJB_Controller extends BaseController {
	
	final public static String AREA_SEPARATOR  = "_";

	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private PostLoanService postLoanService;
	
	@Autowired
	private MaintenanceService maintenanceService;
	
	@Autowired
	private IntoPiecesService intoPiecesService;
	
	@Autowired
	private AddIntoPiecesService addIntoPiecesService;
	
	@Autowired
	private CommonDao commonDao;
	/**
	 * 借据表
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "browse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browse(@ModelAttribute PostLoanFilter filter,HttpServletRequest request) {
		filter.setRequest(request);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String userId = user.getId();
	/*	QueryResult<TyRepayTkmxForm> result = postLoanService.findListByFilter(filter);
		JRadPagedQueryResult<TyRepayTkmxForm> pagedResult = new JRadPagedQueryResult<TyRepayTkmxForm>(filter, result);*/
		QueryResult<Fcloaninfo> result = postLoanService.findJJJnListByFilter(filter);
		JRadPagedQueryResult<Fcloaninfo> pagedResult = new JRadPagedQueryResult<Fcloaninfo>(filter, result);

		JRadModelAndView mv = new JRadModelAndView("/postLoan/jjb_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);

		return mv;
	}
	
	/**
	 * 流水表
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "lshbrowse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView lshbrowse(@ModelAttribute PostLoanFilter filter,HttpServletRequest request) {
		filter.setRequest(request);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String userId = user.getId();

	/*	QueryResult<TyRepayLshForm> result = postLoanService.findLshListByFilter(filter);
		JRadPagedQueryResult<TyRepayLshForm> pagedResult = new JRadPagedQueryResult<TyRepayLshForm>(filter, result);
		*/
		QueryResult<RarepaylistForm> result = postLoanService.findLshJnListByFilter(filter);
		JRadPagedQueryResult<RarepaylistForm> pagedResult = new JRadPagedQueryResult<RarepaylistForm>(filter, result);

		JRadModelAndView mv = new JRadModelAndView("/postLoan/lsh_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);

		return mv;
	}
	
	
	/**
	 * 台帐表
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "tzbrowse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView tzbrowse(@ModelAttribute PostLoanFilter filter,HttpServletRequest request) {
		filter.setRequest(request);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String userId = user.getId();
		QueryResult<MibusidataForm> result = postLoanService.findTzJnListByFilter(filter);
		JRadPagedQueryResult<MibusidataForm> pagedResult = new JRadPagedQueryResult<MibusidataForm>(filter, result);

		JRadModelAndView mv = new JRadModelAndView("/postLoan/tz_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);

		return mv;
	}
	
	
	/**
	 * 
	 * 根据busicode查询台帐表信息
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "tz_information.page")
	public AbstractModelAndView tz_information(@ModelAttribute PostLoanFilter filter ,HttpServletRequest request) {
		String busicode=request.getParameter("busicode");
		filter.setBusiCode(busicode);
		JRadModelAndView mv = new JRadModelAndView("/postLoan/tz_info_browse", request);
		List<MibusidataForm> result = postLoanService.selectTz(filter);
		MibusidataForm form = result.get(0);
		mv.addObject("fcloanifo", form);
		return mv;
	
	}
	
	/**
	 * 被拒绝台帐
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "refuse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView tzrefuse(@ModelAttribute PostLoanFilter filter,HttpServletRequest request) {
		filter.setRequest(request);
		QueryResult<MibusidataForm> result = postLoanService.findrefusedMibusidata(filter);
		JRadPagedQueryResult<MibusidataForm> pagedResult = new JRadPagedQueryResult<MibusidataForm>(filter, result);

		JRadModelAndView mv = new JRadModelAndView("/postLoan/tz_refusedbrowse", request);
		mv.addObject(PAGED_RESULT, pagedResult);

		return mv;
	}
	
	/**
	 * 浏览页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "dhbrowse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView dhbrowse(@ModelAttribute IntoPiecesFilter filter,
			HttpServletRequest request) {
		
		filter.setRequest(request);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		JRadModelAndView mv = new JRadModelAndView("/intopieces/dh_dc_browse", request);
		QueryResult<IntoPieces> result=null;
		String userId = user.getId();
		//客户经理
		if(user.getUserType() ==1){
			filter.setUserId(userId);
			mv.addObject("type", "1");//客户经理
		}
		filter.setStatus("end");
		result = intoPiecesService.findintoPiecesByFilter(filter);
		JRadPagedQueryResult<IntoPieces> pagedResult = new JRadPagedQueryResult<IntoPieces>(filter, result);
		mv.addObject(PAGED_RESULT, pagedResult);
		return mv;
	}
	
	//影像
	@ResponseBody
	@RequestMapping(value = "sunds_ocx.page")
	public AbstractModelAndView sunds_ocx(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/intopieces/sunds_ocx1", request);
		String appId = RequestHelper.getStringValue(request, "appId");
		String custId = RequestHelper.getStringValue(request, "custId");
		mv.addObject("appId", appId);
		
		DhApplnAttachmentList att = addIntoPiecesService.findDhAttachmentListByAppId(appId);
		if(att==null){
			DhApplnAttachmentList attlist = new DhApplnAttachmentList();
			attlist.setApplicationId(appId);
			attlist.setCustomerId(custId);
			attlist.setChkValue("19");
			//attlist.setBussType("1");
			commonDao.insertObject(attlist);
		}
		//查找sunds_ocx信息
		List<DhApplnAttachmentBatch> batch_ls = addIntoPiecesService.findDhAttachmentBatchByAppId(appId);
		//如果batch_ls为空 说明这是以前录得件 根据chk_value增加batch记录
		if(batch_ls == null || batch_ls.size() == 0){
			addIntoPiecesService.addDhBatchInfo(appId,custId);
			batch_ls = addIntoPiecesService.findDhAttachmentBatchByAppId(appId);
		}
		//查询客户信息
		CustomerInfor vo = addIntoPiecesService.findBasicCustomerInfor(custId);
		mv.addObject("batch_ls", batch_ls);
		mv.addObject("customerInfor",vo);
		return mv;
	}
	
	
			
		//跳转到选择图片页面
		@ResponseBody
		@RequestMapping(value = "browse_folder.page")
		public AbstractModelAndView browse_folder_page(HttpServletRequest request) {
			JRadModelAndView mv = new JRadModelAndView("/intopieces/sunds_browse_folder1", request);
			String batch_id = RequestHelper.getStringValue(request, "batch_id");
			String custId = RequestHelper.getStringValue(request, "custId");
			mv.addObject("batch_id", batch_id);
			mv.addObject("custId", custId);
			mv.addObject("bussType", RequestHelper.getStringValue(request, "bussType"));
			String appId = addIntoPiecesService.findDhBatchId(batch_id);
			mv.addObject("appId", appId);
			return mv;
		}	
		
		//浏览图片
		@ResponseBody
		@RequestMapping(value = "browse_folder.json")
		public void browse_folder_json(@RequestParam(value = "file", required = false) MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws Exception{
			String batch_id = RequestHelper.getStringValue(request, "batch_id");
			//更新batch
			addIntoPiecesService.browse_folder_dh(file,batch_id);
			response.getWriter().write("true");
		}
		
		
		//浏览图片完毕  开始通知后台上传影像平台
		@ResponseBody
		@RequestMapping(value = "browse_folder_complete.json")
		public JRadReturnMap browse_folder_complete(HttpServletRequest request) {
			JRadReturnMap returnMap = new JRadReturnMap();
			try {
				String batch_id = RequestHelper.getStringValue(request, "batch_id");
				
				addIntoPiecesService.browse_folder_dh_complete(batch_id,request);
				
				returnMap.put(JRadConstants.SUCCESS, true);
				returnMap.addGlobalMessage(CHANGE_SUCCESS);
			} catch (Exception e) {
				returnMap.addGlobalMessage("保存失败");
				returnMap.put(JRadConstants.SUCCESS, false);
				e.printStackTrace();
			}
			return returnMap;
			
		}	
		
		//查看缓存的图片列表
		@ResponseBody
		@RequestMapping(value = "display_detail.page")
		public AbstractModelAndView diaplsy_detail(@ModelAttribute IntoPiecesFilter filter,HttpServletRequest request) {
			filter.setRequest(request);
			JRadModelAndView mv = new JRadModelAndView("/intopieces/sunds_display_detail1", request);
			QueryResult<DhApplnAttachmentDetail> result = addIntoPiecesService.display_detail_dh(filter);
			JRadPagedQueryResult<DhApplnAttachmentDetail> pagedResult = new JRadPagedQueryResult<DhApplnAttachmentDetail>(filter, result);
			mv.addObject(PAGED_RESULT, pagedResult);
			
			return mv;
		}	
			
		//查看已上传的图片列表
		@ResponseBody
		@RequestMapping(value = "display_server.page")
		public AbstractModelAndView display_server(@ModelAttribute IntoPiecesFilter filter,HttpServletRequest request) {
			filter.setRequest(request);
			filter.setIsUpload("1");
			String batchId = request.getParameter("batchId");
			String currentPage=request.getParameter("currentPage");
			String pageSize=request.getParameter("pageSize");
			int page = 0;//rowNum
			int limit = 1;//每页显示图片数
			if(StringUtils.isNotEmpty(currentPage)){
				page = Integer.parseInt(currentPage);
			}
			if(StringUtils.isNotEmpty(pageSize)){
				limit = Integer.parseInt(pageSize);
			}
			List<DhApplnAttachmentDetail> detaillist = addIntoPiecesService.findDhApplnDetail(page,limit,batchId);
			int totalCount = addIntoPiecesService.findDhApplnDetailCount(batchId);
			
			JRadModelAndView mv = null;
			mv = new JRadModelAndView("/intopieces/sunds_display_server_page1", request);
	
			mv.addObject("Id",detaillist.get(0).getId());
			mv.addObject("rowNum", page);
			mv.addObject("rowNum1", page+1);
			mv.addObject("totalCount",totalCount);
			mv.addObject("batchId", batchId);
			return mv;
		}
		
		
		//删除影像平台上的文件
		@ResponseBody
		@RequestMapping(value = "delete_batch.json")
		public JRadReturnMap delete_batch(HttpServletRequest request) {
			JRadReturnMap returnMap = new JRadReturnMap();
			try {
				String batchId = RequestHelper.getStringValue(request, "batchId");
				
				addIntoPiecesService.delete_batch_dh(batchId,request);
				
				returnMap.put(JRadConstants.SUCCESS, true);
				returnMap.addGlobalMessage(CHANGE_SUCCESS);
			} catch (Exception e) {
				returnMap.addGlobalMessage("删除失败");
				returnMap.put(JRadConstants.SUCCESS, false);
				e.printStackTrace();
			}
			return returnMap;
			
		}
		
		
		
		/**
		 * 首页查看已上传的图片列表
		 * @param filter
		 * @param request
		 * @return
		 */
		@ResponseBody
		@RequestMapping(value = "display_server_page.page")
		public AbstractModelAndView display_server_page(@ModelAttribute IntoPiecesFilter filter,HttpServletRequest request) {
			filter.setRequest(request);
			filter.setIsUpload("1");
			String appId = request.getParameter("appId");
			String currentPage=request.getParameter("currentPage");
			String pageSize=request.getParameter("pageSize");
			int page = 0;//rowNum
			int limit = 1;//每页显示图片数
			if(StringUtils.isNotEmpty(currentPage)){
				page = Integer.parseInt(currentPage);
			}
			if(StringUtils.isNotEmpty(pageSize)){
				limit = Integer.parseInt(pageSize);
			}
			List<DhApplnAttachmentDetail> detaillist = addIntoPiecesService.findDhApplnDetailPage(page,limit,appId);
			
			int totalCount = addIntoPiecesService.findDhApplnDetailPageCount(appId);
			
			JRadModelAndView mv = null;
			mv = new JRadModelAndView("/intopieces/sunds_display_server_page1", request);
	
			mv.addObject("Id",detaillist.get(0).getId());
			mv.addObject("rowNum", page);
			mv.addObject("totalCount",totalCount);
			mv.addObject("batchId", detaillist.get(0).getBatchId());
			return mv;
		}
		
		
		@ResponseBody
		@RequestMapping(value = "isInUpload.json")
		public JRadReturnMap isInUpload(HttpServletRequest request) {
			String appId = request.getParameter(ID);
			int page = 0;//rowNum
			int limit = 1;//每页显示图片数
			JRadReturnMap returnMap = new JRadReturnMap();
			if (returnMap.isSuccess()) {
				try {
					List<DhApplnAttachmentDetail> detaillist = addIntoPiecesService.findDhApplnDetailPage(page,limit,appId);
					if(detaillist.size()==0){
						returnMap.put("isInUpload", true);
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
		
		/**
		 * 
		 * 根据busicode查询借据表信息信息
		 * @param filter
		 * @param request
		 * @return
		 */
		@ResponseBody
		@RequestMapping(value = "jieju_information.page")
		public AbstractModelAndView jieju_information(@ModelAttribute PostLoanFilter filter ,HttpServletRequest request) {
			String busicode=request.getParameter("busicode");
			filter.setBusiCode(busicode);
			JRadModelAndView mv = new JRadModelAndView("/postLoan/jj_info_browse", request);
			List<Fcloaninfo> result = postLoanService.selectfcloanifoInfoByBusicode(filter);
			Fcloaninfo fcloanifo = result.get(0);
			mv.addObject("fcloanifo", fcloanifo);
			return mv;
		
		}
		
		
		/**
		 * 
		 * 根据busicode查询流水表信息信息
		 * @param filter
		 * @param request
		 * @return
		 */
		@ResponseBody
		@RequestMapping(value = "liushui_information.page")
		public AbstractModelAndView liushui_information(@ModelAttribute FcloaninfoFilter filter ,HttpServletRequest request) {
			String busicode=request.getParameter("busicode");
			String rapayinterest=request.getParameter("rapayinterest");
			String repayamt=request.getParameter("repayamt");
			if(rapayinterest.trim().isEmpty()){
				rapayinterest="";
			}
			if(repayamt.trim().isEmpty()){
				repayamt="";
			}
			filter.setBusiCode(busicode);
			filter.setRapayinterest(rapayinterest);
			filter.setRepayamt(repayamt);
			JRadModelAndView mv = new JRadModelAndView("/postLoan/lsh_info_browse", request);
			List<RarepaylistForm> result = postLoanService.selectRarepaylistfoInfoByBusicode(filter);
			RarepaylistForm rarepaylist = result.get(0);
			mv.addObject("rarepaylist", rarepaylist);
			return mv;
			
		}
		
		/**
		 * 
		 * 信贷流程跟踪表
		 * @param filter
		 * @param request
		 * @return
		 */
		@ResponseBody
		@RequestMapping(value = "creditProcess.page")
		public AbstractModelAndView creditProcess(@ModelAttribute  CreditProcess filter,HttpServletRequest request) {
			filter.setRequest(request);
			String customername=request.getParameter("customername");
			if(null!=customername&&""!=customername){
				filter.setCustomername(customername);
				QueryResult<CreditProcess> result = postLoanService.queryCreditProcess(filter);
				JRadPagedQueryResult<CreditProcess> pagedResult = new JRadPagedQueryResult<CreditProcess>(filter, result);
				JRadModelAndView mv = new JRadModelAndView("/postLoan/creditProcess_browse", request);
				mv.addObject(PAGED_RESULT, pagedResult);
				return mv;
			}else{
			QueryResult<CreditProcess> result = postLoanService.queryCreditProcess(filter);
			JRadPagedQueryResult<CreditProcess> pagedResult = new JRadPagedQueryResult<CreditProcess>(filter, result);
			JRadModelAndView mv = new JRadModelAndView("/postLoan/creditProcess_browse", request);
			mv.addObject(PAGED_RESULT, pagedResult);
			return mv;
			}
		}
		/**
		 * 显示详情
		 * @throws ParseException 
		 */
		@ResponseBody
		@RequestMapping(value = "creditProcessQueryAll.json")
		@JRadOperation(JRadOperation.BROWSE)
		public AbstractModelAndView queryAll(HttpServletRequest request) throws ParseException {
			JRadModelAndView mv =null;
			String id=RequestHelper.getStringValue(request, ID);
			List<CreditProcess> cplist=postLoanService.queryAll(id);
			for(CreditProcess cc:cplist){
				if(null!=cc.getStatus()){
					if(cc.getStatus().equals("audit")){
						cc.setStatus("已申请 ");
					}else if(cc.getStatus().equals("refuse")){
						cc.setStatus("被拒绝");
					}else if(cc.getStatus().equals("approved")){
						cc.setStatus("审批结束");
					}else if(cc.getStatus().equals("returnedToFirst")){
						cc.setStatus("退回至客户经理");
					}else if(cc.getStatus().equals("end")){
						cc.setStatus("放款成功");
					}
				}
			}
			mv = new JRadModelAndView("/postLoan/creditProcess_queryAll", request);
			mv.addObject("cplist",cplist);
			return mv;
		}
		
		/**
		 * 导出信贷流程跟踪表
		 * @throws Exception 
		 */
		@ResponseBody
		@RequestMapping(value = "creditProcessExport.json", method = { RequestMethod.GET })
		public void exportAll(@ModelAttribute  CreditProcess filter,HttpServletRequest request,HttpServletResponse response) throws Exception{
			filter.setRequest(request);
			List<CreditProcess> list = postLoanService.creditProcessExportQueryAll(filter);
			create(list,response);
		}
		
		public void create(List<CreditProcess> list,HttpServletResponse response) throws Exception{
			DateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("信贷流程跟踪表");
			HSSFCellStyle style = wb.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			//第一行  合并单元格 并且设置标题
			/*HSSFRow row0 = sheet.createRow((int) 0);
			row0.createCell(0).setCellValue("基本资料");
			row0.createCell(0).setCellStyle(style);
			sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) 6));*/
			sheet.setColumnWidth(0, 2000);
			sheet.setColumnWidth(1, 2500);
			sheet.setColumnWidth(2, 2500);
			sheet.setColumnWidth(3, 2500);
			sheet.setColumnWidth(4, 2500);
			sheet.setColumnWidth(5, 4000);
			sheet.setColumnWidth(6, 2500);
			sheet.setColumnWidth(7, 2500);
			sheet.setColumnWidth(8, 2500);
			sheet.setColumnWidth(9, 4000);
			sheet.setColumnWidth(10, 4000);
			sheet.setColumnWidth(11, 3500);
			sheet.setColumnWidth(12, 3500);
			sheet.setColumnWidth(13, 2500);
			sheet.setColumnWidth(14, 4000);
			sheet.setColumnWidth(15, 2500);
			sheet.setColumnWidth(16, 4000);
			sheet.setColumnWidth(17, 2500);
			sheet.setColumnWidth(18, 4000);
			sheet.setColumnWidth(19, 4000);
			sheet.setColumnWidth(20, 2500);
			sheet.setColumnWidth(21, 3500);
			sheet.setColumnWidth(22, 2500);
			sheet.setColumnWidth(23, 4000);
			sheet.setColumnWidth(24, 2500);
			sheet.setColumnWidth(25, 2500);
			sheet.setColumnWidth(26, 3500);
			sheet.setColumnWidth(27, 4500);
			sheet.setColumnWidth(28, 4000);
			sheet.setColumnWidth(29, 4000);
			sheet.setColumnWidth(30, 2500);
			sheet.setColumnWidth(31, 8000);
			
			//==========================
			HSSFRow row = sheet.createRow((int) 0);
			HSSFCell cell = row.createCell((short) 0);
			cell.setCellValue("序号");
			cell.setCellStyle(style);
			cell = row.createCell((short) 1);
			cell.setCellValue("企业名称");
			cell.setCellStyle(style);
			cell = row.createCell((short) 2);
			cell.setCellValue("借款人姓名");
			cell.setCellStyle(style);
			cell = row.createCell((short) 3);
			cell.setCellValue("所属团队");
			cell.setCellStyle(style);
			cell = row.createCell((short) 4);
			cell.setCellValue("所属区域");
			cell.setCellStyle(style);
			cell = row.createCell((short) 5);
			cell.setCellValue("专业市场分布");
			cell.setCellStyle(style);
			cell = row.createCell((short) 6);
			cell.setCellValue("贷款种类");
			cell.setCellStyle(style);
			cell = row.createCell((short) 7);
			cell.setCellValue("担保类型");
			cell.setCellStyle(style);
			cell = row.createCell((short) 8);
			cell.setCellValue("行业类型");
			cell.setCellStyle(style);
			cell = row.createCell((short) 9);
			cell.setCellValue("营销客户经理");
			cell.setCellStyle(style);
			cell = row.createCell((short) 10);
			cell.setCellValue("管户客户经理");
			cell.setCellStyle(style);
			cell = row.createCell((short) 11);
			cell.setCellValue("申请时间");
			cell.setCellStyle(style);
			cell = row.createCell((short) 12);
			cell.setCellValue("申请金额");
			cell.setCellStyle(style);
			cell = row.createCell((short) 13);
			cell.setCellValue("等待天数");
			cell.setCellStyle(style);
			cell = row.createCell((short) 14);
			cell.setCellValue("申请分配时间");
			cell.setCellStyle(style);
			cell = row.createCell((short) 15);
			cell.setCellValue("等待天数");
			cell.setCellStyle(style);
			cell = row.createCell((short) 16);
			cell.setCellValue("上门调查时间");
			cell.setCellStyle(style);
			cell = row.createCell((short) 17);
			cell.setCellValue("等待天数");
			cell.setCellStyle(style);
			cell = row.createCell((short) 18);
			cell.setCellValue("补充调查时间");
			cell.setCellStyle(style);
			cell = row.createCell((short) 19);
			cell.setCellValue("首次上会时间");
			cell.setCellStyle(style);
			cell = row.createCell((short) 20);
			cell.setCellValue("等待天数");
			cell.setCellStyle(style);
			cell = row.createCell((short) 21);
			cell.setCellValue("决议时间");
			cell.setCellStyle(style);
			cell = row.createCell((short) 22);
			cell.setCellValue("决议结果");
			cell.setCellStyle(style);
			cell = row.createCell((short) 23);
			cell.setCellValue("批准金额(万元)");
			cell.setCellStyle(style);
			cell = row.createCell((short) 24);
			cell.setCellValue("等待天数");
			cell.setCellStyle(style);
			cell = row.createCell((short) 25);
			cell.setCellValue("放款时间");
			cell.setCellStyle(style);
			cell = row.createCell((short) 26);
			cell.setCellValue("合同年利率%");
			cell.setCellStyle(style);
			cell = row.createCell((short) 27);
			cell.setCellValue("周末及假日时间调整");
			cell.setCellStyle(style);
			cell = row.createCell((short) 28);
			cell.setCellValue("办理时间总计(天)");
			cell.setCellStyle(style);
			cell = row.createCell((short) 29);
			cell.setCellValue("当前状态");
			cell.setCellStyle(style);
			cell = row.createCell((short) 30);
			cell.setCellValue("是否转贷");
			cell.setCellStyle(style);
			cell = row.createCell((short) 31);
			cell.setCellValue("说明");
			cell.setCellStyle(style);
			
			for(int i = 0; i < list.size(); i++){
				CreditProcess move = list.get(i);
				row = sheet.createRow((int) i+1);
				row.createCell((short) 0).setCellValue(i+1);//序号
				if(""!=move.getSpmc()&&null!=move.getSpmc()){
				row.createCell((short) 1).setCellValue(move.getSpmc());//企业名称
				}else{
					row.createCell((short) 1).setCellValue(move.getSpmc());//企业名称
				}
				row.createCell((short) 2).setCellValue(move.getCustomername());//借款人姓名
				row.createCell((short) 3).setCellValue(move.getTeamname());//所属团队
				row.createCell((short) 4).setCellValue(move.getBljg());//所属区域
				row.createCell((short) 5).setCellValue("");//专业市场分类
				row.createCell((short) 6).setCellValue(move.getDklx());//贷款类型
				row.createCell((short) 7).setCellValue(move.getMainassure());//担保类型
				row.createCell((short) 8).setCellValue(move.getIndustry());//行业分类
				row.createCell((short) 9).setCellValue(move.getMananger());//营销客户经理
				row.createCell((short) 10).setCellValue(move.getMananger());//管户客户经理
				if(move.getApplytime()!=null){
					row.createCell((short) 11).setCellValue(sdf.format(move.getApplytime()));//申请时间
				}else{
					row.createCell((short) 11).setCellValue("");//申请时间
				}
				row.createCell((short) 12).setCellValue(move.getApplymoney());//申请金额
				row.createCell((short) 13).setCellValue("");//等待天数
				if(move.getApplytime()!=null){
					row.createCell((short) 14).setCellValue(sdf.format(move.getApplytime()));//申请分配时间
				}else{
					row.createCell((short) 14).setCellValue("");//申请分配时间
				}
				row.createCell((short) 15).setCellValue("");//等待天数
				row.createCell((short) 16).setCellValue("");//上门调查时间
				row.createCell((short) 17).setCellValue("");//等待天数
				row.createCell((short) 18).setCellValue("");//补充调查时间
				if(move.getWilltime()!=null){
					row.createCell((short) 19).setCellValue(sdf.format(move.getWilltime()));//上会时间
				}else{
					row.createCell((short) 19).setCellValue("");//上会时间
				}
				row.createCell((short) 20).setCellValue("");//等待天数
				if(move.getWilltime()!=null){
					row.createCell((short) 21).setCellValue(sdf.format(move.getWilltime()));//决议时间
				}else{
					row.createCell((short) 21).setCellValue("");//决议时间
				}
				if(move.getAuditopinion()!=null){
					if(move.getAuditopinion().equals("REJECTAPPROVE")){
						row.createCell((short) 22).setCellValue("审核拒绝");//决议结果
					}else if(move.getAuditopinion().equals("APPROVE")){
						row.createCell((short) 22).setCellValue("审核通过");//决议结果
					}else if(move.getAuditopinion().equals("RETURNAPPROVE")){
						row.createCell((short) 22).setCellValue("审核退回");//决议结果
					}
				}
				if(move.getAuditopinion()==null){
					row.createCell((short) 22).setCellValue("");//决议结果
				}
				row.createCell((short) 23).setCellValue(move.getExamineamount());//批注金额
				row.createCell((short) 24).setCellValue("");//等待天数
				if(move.getAudittime()!=null){
					Date a=sdf.parse(move.getAudittime());
					String audittime=sdf.format(a);
					row.createCell((short) 25).setCellValue(audittime);//放款时间
				}else{
					row.createCell((short) 25).setCellValue("");//放款时间
				}
				row.createCell((short) 26).setCellValue(move.getExamine());//合同年利率
				if(move.getAudittime()!=null&&move.getApplytime()!=null){
					Date audittime=sdf.parse(move.getAudittime());
					Date applytime=move.getApplytime();
					int days=sumdays(audittime,applytime);
					int alldays=sumalldays(audittime, applytime);
					int day=alldays-days;
					row.createCell((short) 27).setCellValue(day);//周末及假日调整
				}
				if(move.getAudittime()!=null&&move.getApplytime()!=null){
					Date audittime=sdf.parse(move.getAudittime());
					Date applytime=move.getApplytime();
					int days=sumdays(audittime,applytime);
					row.createCell((short) 28).setCellValue(days);//办理时间总计
				}
				if(move.getStatus()!=null){
					if(move.getStatus().equals("audit")){
						row.createCell((short) 29).setCellValue("已申请");//当前状态
					}else if(move.getStatus().equals("refuse")){
						row.createCell((short) 29).setCellValue("被拒绝");//当前状态
					}else if(move.getStatus().equals("approved")){
						row.createCell((short) 29).setCellValue("审批结束");//当前状态
					}else if(move.getStatus().equals("returnedToFirst")){
						row.createCell((short) 29).setCellValue("退回至客户经理");//当前状态
					}else if(move.getStatus().equals("end")){
						row.createCell((short) 29).setCellValue("放款成功");//当前状态
					}
				}
				 if(move.getStatus()==null){
						row.createCell((short) 29).setCellValue("");//当前状态
					}
				
				if(move.getCardId()!=null){
					String cardId=move.getCardId();
					List<CreditProcess> cc=postLoanService.queryByCardId(cardId);
					if(cc.size()==0){
						row.createCell((short) 30).setCellValue("否");//是否转贷
					}else{
						row.createCell((short) 30).setCellValue("是");//是否转贷
					}
				}
				
				row.createCell((short) 31).setCellValue("");//说明
			}
			String fileName = "信贷流程跟踪表";
			try{
				response.setHeader("Content-Disposition", "attachment;fileName="+new String(fileName.getBytes("gbk"),"iso8859-1")+".xls");
				response.setHeader("Connection", "close");
				response.setHeader("Content-Type", "application/vnd.ms-excel");
				OutputStream os = response.getOutputStream();
				wb.write(os);
				os.flush();
				os.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		//计算工作日
		 static int sumdays(Date audittime, Date applytime) throws Exception {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			/*Date begDate = sdf.parse("2016-12-22");
			Date endDate = sdf.parse("2016-12-31");*/
			Date begDate =applytime;
			Date endDate =audittime;
			if (begDate.after(endDate))throw new Exception("日期范围非法");
			// 总天数
			int days = (int) ((endDate.getTime() - begDate.getTime()) / (24 * 60 * 60 * 1000)) + 1;
			// 总周数，
			int weeks = days / 7;int rs = 0;
			// 整数周
			if (days % 7 == 0) {
				rs = days - 2 * weeks;
				}else {
					Calendar begCalendar = Calendar.getInstance();
					Calendar endCalendar = Calendar.getInstance();
					begCalendar.setTime(begDate);endCalendar.setTime(endDate);
					// 周日为1，周六为7
					int beg = begCalendar.get(Calendar.DAY_OF_WEEK);
					int end = endCalendar.get(Calendar.DAY_OF_WEEK);
					if (beg > end) {rs = days - 2 * (weeks + 1);
					} else if (beg < end) {
						if (end == 7) {
							rs = days - 2 * weeks - 1;
							} else {
								rs = days - 2 * weeks;
							}
						} else {
							if (beg == 1 || beg == 7) {
								rs = days - 2 * weeks - 1;  
								} else {
									rs = days - 2 * weeks;
									}
							}
					}
					System.out.println(sdf.format(begDate)+"到"+sdf.format(endDate)+"中间有"+rs+"个工作日");
					return rs;
					}
		 //计算两个日期之间的总天数
		 static int sumalldays(Date audittime, Date applytime) throws Exception {
				int days=(int) ((audittime.getTime()-applytime.getTime())/(1000*3600*24));
				return days+1;
		 }
}

	