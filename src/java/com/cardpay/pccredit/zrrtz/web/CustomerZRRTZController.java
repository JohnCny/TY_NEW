/**
 * 
 */
package com.cardpay.pccredit.zrrtz.web;

import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.common.FormatTool;
import com.cardpay.pccredit.customer.filter.CustomerLoanFilter;
import com.cardpay.pccredit.customer.model.CustomerParameter;
import com.cardpay.pccredit.customer.model.ParameterInformaion;
import com.cardpay.pccredit.customer.service.CustomerLoanService;
import com.cardpay.pccredit.customer.service.CustomerParameterService;
import com.cardpay.pccredit.customer.service.MaintenanceService;
import com.cardpay.pccredit.customer.web.CustomerLoanForm;
import com.cardpay.pccredit.intopieces.service.IntoPiecesService;
import com.cardpay.pccredit.ipad.model.UserIpad;
import com.cardpay.pccredit.manager.web.AccountManagerParameterForm;
import com.cardpay.pccredit.product.web.ProductAttributeForm;
import com.cardpay.pccredit.report.filter.ReportFilter;
import com.cardpay.pccredit.report.model.YffdktjbbForm;
import com.cardpay.pccredit.zrrtz.Util.ExportExcel;
import com.cardpay.pccredit.zrrtz.dao.ZrrtzDao;
import com.cardpay.pccredit.zrrtz.model.OutcomingData;
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
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.util.date.DateHelper;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;
import com.wicresoft.util.web.RequestHelper;

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
	private CustomerParameterService cpService;
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
		String startdate=request.getParameter("fdate");
		String enddate=request.getParameter("ldate");
		if(startdate!=null && enddate!=null){
			filter.setFdate(startdate);
			filter.setLdate(enddate);
		}
			filter.setUserId(user.getId());
		QueryResult<IncomingData> result = service.findintoPiecesByFilter(filter,user);
		JRadPagedQueryResult<IncomingData> pagedResult = new JRadPagedQueryResult<IncomingData>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/customer/customerZRRTZ/zrrtz_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);
		return mv;
	}
	
	
	/**
	 * 导出
	 */
	@ResponseBody
	@RequestMapping(value = "export.json", method = { RequestMethod.GET })
	public void exportAll(@ModelAttribute ReportFilter filter,ZrrtzFilter filter1, HttpServletRequest request,HttpServletResponse response){
		filter.setRequest(request);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String id=user.getId();
	//	String id=RequestHelper.getStringValue(request, ID);
		String fdate=request.getParameter("fdate");
		String ldate=request.getParameter("ldate");
		List<OutcomingData>list=dao.findpiecesList(id,fdate,ldate);
		create(list,response);
	}
	
	public void create(List<OutcomingData> list,HttpServletResponse response){
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("自然人台帐");
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		//第一行  合并单元格 并且设置标题
		/*HSSFRow row0 = sheet.createRow((int) 0);
		row0.createCell(0).setCellValue("基本资料");
		row0.createCell(0).setCellStyle(style);
		sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) 6));*/
		sheet.setColumnWidth(0, 3500);
		sheet.setColumnWidth(1, 8000);
		sheet.setColumnWidth(2, 8000);
		sheet.setColumnWidth(3, 8000);
		sheet.setColumnWidth(4, 8000);
		sheet.setColumnWidth(5, 5000);
		//==========================
		HSSFRow row = sheet.createRow((int) 0);
		
		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("业务编号");
		cell.setCellStyle(style);
		
		cell = row.createCell((short) 1);
		cell.setCellValue("客户名称");
		cell.setCellStyle(style);
		
		cell = row.createCell((short) 2);
		cell.setCellValue("客户经理名称");
		cell.setCellStyle(style);
		
		cell = row.createCell((short) 3);
		cell.setCellValue("身份证号");
		cell.setCellStyle(style);
		
		cell = row.createCell((short) 4);
		cell.setCellValue("产品名称");
		cell.setCellStyle(style);
		
		cell = row.createCell((short) 5);
		cell.setCellValue("金额");
		cell.setCellStyle(style);
		
		cell = row.createCell((short) 6);
		cell.setCellValue("计划期数");
		cell.setCellStyle(style);
		
		cell = row.createCell((short) 7);
		cell.setCellValue("发放日期");
		cell.setCellStyle(style);
		
		cell = row.createCell((short) 8);
		cell.setCellValue("到期日期");
		cell.setCellStyle(style);
		
		cell = row.createCell((short) 9);
		cell.setCellValue("行业分类");
		cell.setCellStyle(style);
		cell = row.createCell((short) 10);
		cell.setCellValue("经营内容");
		cell.setCellStyle(style);
		cell = row.createCell((short) 11);
		cell.setCellValue("经营地址");
		cell.setCellStyle(style);
		cell = row.createCell((short) 12);
		cell.setCellValue("主调");
		cell.setCellStyle(style);
		cell = row.createCell((short) 13);
		cell.setCellValue("辅调");
		cell.setCellStyle(style);
		cell = row.createCell((short) 14);
		cell.setCellValue("组别");
		cell.setCellStyle(style);
		cell = row.createCell((short) 15);
		cell.setCellValue("审贷会成员");
		cell.setCellStyle(style);
		cell = row.createCell((short) 16);
		cell.setCellValue("贷款方式");
		cell.setCellStyle(style);
		cell = row.createCell((short) 17);
		cell.setCellValue("是否纳税");
		cell.setCellStyle(style);
		cell = row.createCell((short) 18);
		cell.setCellValue("归还情况");
		cell.setCellStyle(style);
		cell = row.createCell((short) 19);
		cell.setCellValue("联系方式");
		cell.setCellStyle(style);
		cell = row.createCell((short) 20);
		cell.setCellValue("联系方式");
		cell.setCellStyle(style);
		

		
		for(int i = 0; i < list.size(); i++){
			OutcomingData move = list.get(i);
			row = sheet.createRow((int) i+1);
			row.createCell((short) 0).setCellValue(move.getYwbh());
			row.createCell((short) 1).setCellValue(move.getCustomername());
			row.createCell((short) 2).setCellValue(move.getManagername());
			row.createCell((short) 3).setCellValue(move.getIdcard());
			row.createCell((short) 4).setCellValue(move.getProductname());
			row.createCell((short) 5).setCellValue(FormatTool.formatNumber(move.getActual_quote(), 5, 1));
			row.createCell((short) 6).setCellValue(move.getDeadline());
			row.createCell((short) 7).setCellValue(move.getProvidedate());
			row.createCell((short) 8).setCellValue(move.getExpiredate());
			row.createCell((short) 9).setCellValue(move.getClassification());
			row.createCell((short) 10).setCellValue(move.getScopeoperation());
			row.createCell((short) 11).setCellValue(move.getOperationaddress());
			row.createCell((short) 12).setCellValue(move.getPrincipal());
			row.createCell((short) 13).setCellValue(move.getAssist());
			row.createCell((short) 14).setCellValue(move.getGroupes());
			row.createCell((short) 15).setCellValue(move.getMembers());
			row.createCell((short) 16).setCellValue(move.getPatternslend());
			row.createCell((short) 17).setCellValue(move.getRatepaying());
			row.createCell((short) 18).setCellValue(move.getGiveback());
			row.createCell((short) 19).setCellValue(move.getSJ());
			row.createCell((short) 20).setCellValue(move.getRemark());
			
		}
		
		String fileName = "自然人台帐";
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
	/**
	 * 
	 * 描述 ：自然人台账，实现客户经理对客户资料的补填
	 * @author 周文杰
	 * 2016-8-15 09:40:15
	 */
	//添加前的查询
		@ResponseBody
		@RequestMapping(value = "addCustomerParameter.page", method = { RequestMethod.GET })
		@JRadOperation(JRadOperation.BROWSE)
		public AbstractModelAndView change( 
				HttpServletRequest request
				) {
			JRadModelAndView mv =null;
			String id=request.getParameter("id");
			mv = new JRadModelAndView("/customer/customerInforInsert/CustomerParameter_create", request);
			List<UserIpad> managers=cpService.queryAllManager();
			List<ParameterInformaion> pInfor=cpService.query(id);
			//List<ProductAttribute> product=cpService.queryProduct();
			mv.addObject("managers", managers);
			//mv.addObject("product", product);
			mv.addObject("pInfor",pInfor);
			return mv;
		}
		
		//添加
		@ResponseBody
		@RequestMapping(value = "addCustomerParameter.json",method = {RequestMethod.POST})
		@JRadOperation(JRadOperation.CREATE)
		public JRadReturnMap addCommity(
			@ModelAttribute ProductAttributeForm productAttributeForm,
			CustomerParameter cp,
			HttpServletRequest request)
		{
			JRadReturnMap returnMap = new JRadReturnMap();
			if (returnMap.isSuccess()) {
				try {	
					String ywbh=request.getParameter("ywbh");
					List<CustomerParameter> cParameters=cpService.queryByIdCard(ywbh);
					if(cParameters.size()>0){
						returnMap.put(MESSAGE, "该数据也被填补");
						return returnMap;
					}
						cpService.addCustomerParameter(cp);
						returnMap.put(MESSAGE, "添加成功");
						
				} catch (Exception e) {
					// TODO: handle exception
					return WebRequestHelper.processException(e);
				}	
		}
			return returnMap;
		}

	
	
}
