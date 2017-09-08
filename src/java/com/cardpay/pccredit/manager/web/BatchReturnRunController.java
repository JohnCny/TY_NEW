package com.cardpay.pccredit.manager.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.cardpay.pccredit.customer.constant.CustomerInforConstant;
import com.cardpay.pccredit.customer.service.CustomerInforNewService;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.customer.service.ReadWholeAndIncrementService;
import com.cardpay.pccredit.intopieces.filter.AddIntoPiecesFilter;
import com.cardpay.pccredit.intopieces.service.AddIntoPiecesService;
import com.cardpay.pccredit.manager.constant.ManagerLevelAdjustmentConstant;
import com.cardpay.pccredit.manager.filter.BatchRunFilter;
import com.cardpay.pccredit.manager.model.BatchTask;
import com.cardpay.pccredit.manager.service.DailyReportScheduleService;
import com.cardpay.pccredit.manager.service.ManagerLevelAdjustmentService;
import com.cardpay.pccredit.toolsjn.OdsTools_jn;
import com.cardpay.pccredit.toolsjn.OdsTools_ty;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.constant.JRadConstants;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.message.Messages;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.util.date.DateHelper;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;
import com.wicresoft.util.web.RequestHelper;
/**
 * 
 * 描述 ：批处理重跑controller
 * @author 宋辰
 */
@Controller
@RequestMapping("/batch/returnrun/*")
@JRadModule("batch.returnrun")
public class BatchReturnRunController extends BaseController{
	
	@Autowired
	private ManagerLevelAdjustmentService managerLevelAdjustmentService;

	@Autowired
	DailyReportScheduleService dailyReportScheduleService;
	
	@Autowired
	CustomerInforService customerInforService;
	
	@Autowired
	private AddIntoPiecesService addIntoPiecesService;
	
	@Autowired
	ReadWholeAndIncrementService incrementService;
	
	@Autowired
	OdsTools_jn  odsTools_jn;
	
	@Autowired
	OdsTools_ty  odsTools_ty;
	
	@Autowired
	private CustomerInforNewService customerInforNewService;
	/**
	 * 查看批处理信息
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "browse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browse(@ModelAttribute BatchRunFilter filter,HttpServletRequest request) {
		filter.setRequest(request);
		//
		if(filter.getStartDate() ==null){
			filter.setStartDate(DateHelper.getDateFormat("2016-04-01","yyyy-MM-dd"));
		}
		if(filter.getEndDate()==null){
			filter.setEndDate(new Date());
		}
		QueryResult<BatchTask> result = managerLevelAdjustmentService.findBatchFilter(filter);
		JRadPagedQueryResult<BatchTask> pagedResult = new JRadPagedQueryResult<BatchTask>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/manager/batchreturnrun/batch_return_run", request);
		mv.addObject(PAGED_RESULT, pagedResult);
		return mv;
	}
	
	
	/**
	 * 执行 确认
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "handleAdjustmentLevel.json",method = { RequestMethod.GET })
	public JRadReturnMap handleAdjustmentLevel(@ModelAttribute BatchRunFilter filter,HttpServletRequest request) {
		filter.setRequest(request);
		JRadReturnMap returnMap = new JRadReturnMap();
		String dateString = null;
		try {
			/*String param = RequestHelper.getStringValue(request, ID);
			String batchCode = param.split("@")[1];
			String status=param.split("@")[2];
			String createtime = param.split("@")[3];
			//DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			//String dateString = format.format(new Date(time));
			if("000".equals(status)||"001".equals(status)){
				dateString = createtime.substring(0, 10).replace("-", "");
			}
			if("002".equals(status)){
				if("downLoad".equals(batchCode)){
					dateString=request.getParameter("InputTime0").replace("-", "");
				}
				if("incre".equals(batchCode)){
					dateString=request.getParameter("InputTime1").replace("-", "");
				}
				if(dateString==null||dateString==""){
					returnMap.put(JRadConstants.SUCCESS, false);
					returnMap.put(JRadConstants.MESSAGE, "处理失败!特殊时间搜索时间不可为空");
					return returnMap;
				}
			}
			//下载和解压数据
			if(batchCode.equals("downLoad")){
				odsTools_jn.downloadFilesbyDate(dateString,status);
			//客户经理日报batch
			}else if(batchCode.equals("rb")){
				dailyReportScheduleService.insertWeekScheduleByDate(dateString);
			//ODS增量数据
			}else if(batchCode.equals("incre")){
				incrementService.doReadFileIncrementByDate(dateString,status);
			//ODS全量数据(暂无)
			}else if(batchCode.equals("whole")){
				incrementService.doReadFileWholeByDate(dateString);
			}
			//同步进件状态
			//绩效
			returnMap.addGlobalMessage(ManagerLevelAdjustmentConstant.IF_HANDLE_SUCCESS);*/
			
			String param = RequestHelper.getStringValue(request, ID);
			String batchCode = param.split("@")[1];
			String time = param.split("@")[2];
			
			dateString = time.substring(0, 10).replace("-", "");
			if(batchCode.equals("xj")){// 下载和解压数据
				odsTools_ty.downloadFilesbyDate(dateString);
			}else if(batchCode.equals("jb")){// 基本信息
				customerInforNewService.readFileByDate(dateString);
			}else if(batchCode.equals("dk")){// 贷款信息
				customerInforNewService.readFileRepayByDate(dateString);
			}
			
			returnMap.addGlobalMessage(ManagerLevelAdjustmentConstant.IF_HANDLE_SUCCESS);
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}

		return returnMap;
	}
	
	@ResponseBody
	@RequestMapping(value = "reportImport.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView reportImport(@ModelAttribute AddIntoPiecesFilter filter,HttpServletRequest request) {
		filter.setRequest(request);
		JRadModelAndView mv = new JRadModelAndView("/manager/batchreturnrun/batch_import",request);
		return mv;
	}
	
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
			addIntoPiecesService.importTxt(file);
			map.put(JRadConstants.SUCCESS, true);
			map.put(JRadConstants.MESSAGE, "导入成功");
			JSONObject obj = JSONObject.fromObject(map);
			response.getWriter().print(obj.toString());
		} catch (Exception e) {
			e.printStackTrace();
			map.put(JRadConstants.SUCCESS, false);
			map.put(JRadConstants.MESSAGE, "导入失败:"+e.getMessage());
			JSONObject obj = JSONObject.fromObject(map);
			response.getWriter().print(obj.toString());
		}
		return null;
	}
	
	private String log_path = "/home/jradbaseweb.log";
//	private String log_path = "E:/jradbaseweb.log";
	private String log_name = "jradbaseweb.log";
	
	@ResponseBody
	@RequestMapping(value = "logBrowse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browse(HttpServletRequest request) throws IOException {
		JRadModelAndView mv = new JRadModelAndView("/manager/batchreturnrun/log_browse",request);
		
		return mv;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "download.json", method = { RequestMethod.GET })
	public AbstractModelAndView download(HttpServletRequest request,HttpServletResponse response) throws Exception {
		JRadModelAndView mav = new JRadModelAndView("/manager/batchreturnrun/log_result", request);
		JRadReturnMap rmp = new JRadReturnMap();
		String result = "";
		String query_date = request.getParameter("query_date");
		String path = log_path;
		String fileName = log_name;
		Date date = new Date();
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		String currentDate =formatDate.format(date);
		
		
		if(StringUtils.isNotEmpty(query_date)){
			if(currentDate==query_date||currentDate.equals(query_date)){
				
			}else{
				
				path = log_path + "." + query_date;
				fileName = log_name + "." + query_date;
			}
		}
		
		File file = new File(path);
		if(file.exists()){
			byte[] buff = new byte[2048];
			int bytesRead;
			response.setHeader("Content-Disposition", "attachment; filename=" + java.net.URLEncoder.encode(fileName, "UTF-8"));
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path));
			BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
			bos.flush();
			if (bis != null) {
				bis.close();
			}
			if (bos != null) {
				bos.close();
			}
			return null;
		}else{
		result = "下载失败，文件不存在！";
		rmp.put("result", result);
		rmp.setSuccess(false);
		}
		mav.addObject("rmp", rmp);
		return mav ;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "dataErrorProcee.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView dataUpdate(HttpServletRequest request) throws IOException {
		JRadModelAndView mv = new JRadModelAndView("/manager/batchreturnrun/dataErrorProcee",request);
		return mv;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "excute.json", method = { RequestMethod.GET })
	public AbstractModelAndView excute(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String sql = request.getParameter("sql");
		addIntoPiecesService.dataErrorProceeExcute(sql);
		return null;
	}
	
	private String errLog_path="/opt/IBM/WebSphere/AppServer/profiles/AppSrv01/logs/server1/SystemErr.log";
//	private String errLog_path="E:/SystemErr.log";
	private String errLog_name="SystemErr.log";
	@ResponseBody
	@RequestMapping(value = "errLogDownload.json", method = { RequestMethod.GET })
	public AbstractModelAndView errLogDownload(HttpServletRequest request,HttpServletResponse response) throws Exception {
		JRadModelAndView mav = new JRadModelAndView("/manager/batchreturnrun/log_result", request);
		JRadReturnMap rmp = new JRadReturnMap();
		String result = "";
		
		File file = new File(errLog_path);
		if(file.exists()){
			byte[] buff = new byte[2048];
			int bytesRead;
			response.setHeader("Content-Disposition", "attachment; filename=" + java.net.URLEncoder.encode(errLog_name, "UTF-8"));
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(errLog_path));
			OutputStream out =response.getOutputStream();
			BufferedOutputStream bos = new BufferedOutputStream(out);
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
			bos.flush();
			if (bis != null) {
				bis.close();
			}
			if (bos != null) {
				bos.close();
			}
			
		
			result = "下载完成！";
			rmp.put("result", result);
			rmp.setSuccess(true);
			return null;
		}else{
		result = "下载失败，文件不存在！";
		rmp.put("result", result);
		rmp.setSuccess(false);
		}
		mav.addObject("rmp", rmp);
		
		return mav ;
	}
	
	
	
}
