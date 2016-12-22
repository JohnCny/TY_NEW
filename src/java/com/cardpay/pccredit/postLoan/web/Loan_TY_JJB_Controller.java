package com.cardpay.pccredit.postLoan.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.cardpay.pccredit.customer.constant.CustomerInforConstant;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.model.TyRepayTkmxForm;
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
import com.cardpay.pccredit.postLoan.dao.PostLoanDao;
import com.cardpay.pccredit.postLoan.filter.FcloaninfoFilter;
import com.cardpay.pccredit.postLoan.filter.PostLoanFilter;
import com.cardpay.pccredit.postLoan.model.Fcloaninfo;
import com.cardpay.pccredit.postLoan.model.MibusidataForm;
import com.cardpay.pccredit.postLoan.model.MibusidateView;
import com.cardpay.pccredit.postLoan.model.Rarepaylist;
import com.cardpay.pccredit.postLoan.model.RarepaylistForm;
import com.cardpay.pccredit.postLoan.service.PostLoanService;
import com.cardpay.pccredit.riskControl.model.RiskCustomer;
import com.cardpay.pccredit.zrrtz.Util.ExportExcel;
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
		filter.setUserid(userId);
		QueryResult<TyRepayTkmxForm> result = postLoanService.findJJJnListByFilter(filter);
		JRadPagedQueryResult<TyRepayTkmxForm> pagedResult = new JRadPagedQueryResult<TyRepayTkmxForm>(filter, result);

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
		filter.setUserid(userId);
		QueryResult<MibusidateView> result = postLoanService.findTzJnListByFilter(filter);
		JRadPagedQueryResult<MibusidateView> pagedResult = new JRadPagedQueryResult<MibusidateView>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/postLoan/tz_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);

		return mv;
	}
	
	
	//使用poi方法 导出excel
			@ResponseBody
			@RequestMapping(value = "tzexport.json", method = { RequestMethod.GET })
			@JRadOperation(JRadOperation.BROWSE)
			public JRadReturnMap zrrtzexport(@ModelAttribute  PostLoanFilter filter, HttpServletRequest request,HttpServletResponse response) {
				filter.setRequest(request);
				String busicode=RequestHelper.getStringValue(request, ID);
				IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
				String userId = user.getId();
				JRadReturnMap returnMap = new JRadReturnMap();
				if (returnMap.isSuccess()) {
				String title="MIBUSIDATA台帐表";
				String[] rowName={"业务编号","客户名称","客户证件号(核心)"
						,"授信金额","发放日期(核心)","发放金额(核心)","贷款余额(核心)"
						,"账户状态(核心) "};
				filter.setUserid(userId);
				List<MibusidataForm>plans=postLoanService.findtzList(filter);
				List<Object[]>  dataList=new ArrayList<Object[]>();
				for (int i=0;i<plans.size();i++) {
					Object[] obj={
							plans.get(i).getBusicode(),
							plans.get(i).getCname(),
							plans.get(i).getCustidno(),
							plans.get(i).getReqlmt(),
							plans.get(i).getLoandate(),
							plans.get(i).getMoney(),
							plans.get(i).getBalamt(),
							plans.get(i).getAccountstate()
							
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
				return returnMap;
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
		List<MibusidateView> result = postLoanService.selectTz(filter);
		MibusidateView form = result.get(0);
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
			List<TyRepayTkmxForm> result = postLoanService.selectfcloanifoInfoByBusicode(filter);
			TyRepayTkmxForm fcloanifo = result.get(0);
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
		
		
		
}




	