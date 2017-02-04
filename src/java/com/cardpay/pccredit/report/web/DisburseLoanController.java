package com.cardpay.pccredit.report.web;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
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

import com.cardpay.pccredit.Sx.model.SxOutputData;
import com.cardpay.pccredit.Sx.service.SxService;
import com.cardpay.pccredit.common.FormatTool;
import com.cardpay.pccredit.report.filter.CustomerMoveFilter;
import com.cardpay.pccredit.report.filter.ReportFilter;
import com.cardpay.pccredit.report.model.CustomerMoveForm;
import com.cardpay.pccredit.report.model.YffdktjbbForm;
import com.cardpay.pccredit.report.service.CustomerTransferFlowService;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;

@Controller
@RequestMapping("/disburse/loan/*")
@JRadModule("disburse.loan")
public class DisburseLoanController extends BaseController{
	@Autowired
	private SxService services;
	@Autowired
	private CustomerTransferFlowService customerTransferFlowService;
	
	/**
	 * 已发放贷款统计查询
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryHaveBeenLoan.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView queryHaveBeenLoan(@ModelAttribute ReportFilter filter,HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/report/disburseLoan/disburseLoan", request);
		filter.setRequest(request);
		String certiCode=request.getParameter("certiCode");
		String custManagerName=request.getParameter("custManagerName");
		String organName=request.getParameter("organName");
		 try {
			 if(certiCode!=null){ certiCode=new String(certiCode.getBytes("iso-8859-1"),"utf-8");}
			 if(custManagerName!=null){ custManagerName=new String(custManagerName.getBytes("iso-8859-1"),"utf-8");}
			 if(organName!=null){ organName=new String(organName.getBytes("iso-8859-1"),"utf-8");}
			filter.setCertiCode(certiCode);
			filter.setCustManagerName(custManagerName);
			filter.setOrganName(organName);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 查询团队
		List<SxOutputData> team = services.findteam();
	    QueryResult<YffdktjbbForm> result =  customerTransferFlowService.findYffdktjbbFormList(filter);
		JRadPagedQueryResult<YffdktjbbForm> pagedResult = new JRadPagedQueryResult<YffdktjbbForm>(filter, result);
		mv.addObject(PAGED_RESULT, pagedResult);
		mv.addObject("team", team);
		return mv;
	}
	
	
	/**
	 * 导出已发放贷款统计
	 */
	@ResponseBody
	@RequestMapping(value = "exportAll.page", method = { RequestMethod.GET })
	public void exportAll(@ModelAttribute ReportFilter filter, HttpServletRequest request,HttpServletResponse response){
		filter.setRequest(request);
		List<YffdktjbbForm> list = customerTransferFlowService.getYffdktjbbFormlList(filter);
		create(list,response);
	}
	
	public void create(List<YffdktjbbForm> list,HttpServletResponse response){
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("已发放贷款统计报表");
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
		cell.setCellValue("序号");
		cell.setCellStyle(style);
		
		cell = row.createCell((short) 1);
		cell.setCellValue("客户名称");
		cell.setCellStyle(style);
		
		cell = row.createCell((short) 2);
		cell.setCellValue("客户证件号码");
		cell.setCellStyle(style);
		
		cell = row.createCell((short) 3);
		cell.setCellValue("所属产品");
		cell.setCellStyle(style);
		
		cell = row.createCell((short) 4);
		cell.setCellValue("贷款金额");
		cell.setCellStyle(style);
		
		cell = row.createCell((short) 5);
		cell.setCellValue("利率");
		cell.setCellStyle(style);
		
		cell = row.createCell((short) 6);
		cell.setCellValue("放款日期");
		cell.setCellStyle(style);
		
		cell = row.createCell((short) 7);
		cell.setCellValue("贷款状态");
		cell.setCellStyle(style);
		
		cell = row.createCell((short) 8);
		cell.setCellValue("所属客户经理");
		cell.setCellStyle(style);
		
		cell = row.createCell((short) 9);
		cell.setCellValue("所属机构");
		cell.setCellStyle(style);

		
		for(int i = 0; i < list.size(); i++){
			YffdktjbbForm move = list.get(i);
			row = sheet.createRow((int) i+1);
			row.createCell((short) 0).setCellValue(move.getRowIndex());
			row.createCell((short) 1).setCellValue(move.getCname());
			row.createCell((short) 2).setCellValue(move.getCardtype());
			row.createCell((short) 3).setCellValue(move.getProdName());
			row.createCell((short) 4).setCellValue(move.getMoney());
			row.createCell((short) 5).setCellValue(FormatTool.formatNumber(move.getBaserate(), 5, 1));
			row.createCell((short) 6).setCellValue(move.getInterest());
			row.createCell((short) 7).setCellValue(move.getState());
			row.createCell((short) 8).setCellValue(move.getBusimanager());
			row.createCell((short) 9).setCellValue(move.getName());
		}
		
		String fileName = "已发放贷款统计报表";
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
	
	
}


