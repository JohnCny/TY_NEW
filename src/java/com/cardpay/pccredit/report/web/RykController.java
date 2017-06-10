package com.cardpay.pccredit.report.web;

import java.io.IOException;
import java.io.OutputStream;
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
import com.cardpay.pccredit.intopieces.model.CustomerApplicationInfo;
import com.cardpay.pccredit.postLoan.model.MibusidateView;
import com.cardpay.pccredit.report.filter.CustomerMoveFilter;
import com.cardpay.pccredit.report.filter.ReportFilter;
import com.cardpay.pccredit.report.model.CustomerMoveForm;
import com.cardpay.pccredit.report.model.TkmxLszBasic;
import com.cardpay.pccredit.report.model.YqhkdktjbbForm;
import com.cardpay.pccredit.report.service.CustomerTransferFlowService;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;

@Controller
@RequestMapping("/Ryk/loan/*")
@JRadModule("Ryk.loan")
public class RykController extends BaseController{
	@Autowired
	private SxService service;
	@Autowired
	private CustomerTransferFlowService customerTransferFlowService;
	
	/**
	 * 荣耀卡统计报表
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "ryktj.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView ryktj(@ModelAttribute ReportFilter filter,HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/report/expect/ryktj", request);
		filter.setRequest(request);
		// 查询团队
		List<SxOutputData> team = service.findteam();
		//因为在信贷系统中 荣耀卡信息和标微信息下发数据相同
		//所以根据该客户经理手下的荣耀卡进件 来获取下发数据的荣耀卡信息
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		//匹配荣耀卡信息
	    QueryResult<MibusidateView> result =  customerTransferFlowService.findrykapplicationinfobyuserid(filter);
		JRadPagedQueryResult<MibusidateView> pagedResult = new JRadPagedQueryResult<MibusidateView>(filter, result);
		mv.addObject(PAGED_RESULT, pagedResult);
		mv.addObject("team", team);
		return mv;
	}
	
	
	
	/**
	 * 导出预期还款贷款统计查询
	 */
	@ResponseBody
	@RequestMapping(value = "exportAll.page", method = { RequestMethod.GET })
	public void exportAll(@ModelAttribute ReportFilter filter, HttpServletRequest request,HttpServletResponse response){
		filter.setRequest(request);
		List<TkmxLszBasic> list = customerTransferFlowService.getYqhkdktjbbFormList(filter);
		create(list,response);
	}
	
	public void create(List<TkmxLszBasic> list,HttpServletResponse response){
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("预期还款贷款统计报表");
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
		cell.setCellValue("距离还款日(日)");
		cell.setCellStyle(style);
		cell = row.createCell((short) 6);
		cell.setCellValue("应还本金");
		cell.setCellStyle(style);
		cell = row.createCell((short) 7);
		cell.setCellValue("应还利息");
		cell.setCellStyle(style);
		cell = row.createCell((short) 8);
		cell.setCellValue("所属客户经理");
		cell.setCellStyle(style);
		cell = row.createCell((short) 9);
		cell.setCellValue("所属机构");
		cell.setCellStyle(style);
		
		for(int i = 0; i < list.size(); i++){
			TkmxLszBasic move = list.get(i);
			row = sheet.createRow((int) i+1);
			row.createCell((short) 0).setCellValue((int) i+1);
			row.createCell((short) 1).setCellValue(move.getCHINESE_NAME());
			row.createCell((short) 2).setCellValue(move.getCARD_ID());
			row.createCell((short) 3).setCellValue(move.getCPMC());
			row.createCell((short) 4).setCellValue(move.getDF());
			row.createCell((short) 5).setCellValue(move.getHkr());
			row.createCell((short) 6).setCellValue(move.getJE());
			row.createCell((short) 7).setCellValue(move.getYHLX());
			row.createCell((short) 8).setCellValue(move.getKHJL());
			row.createCell((short) 9).setCellValue(move.getYWJG());
		}
		String fileName = "预期还款贷款统计报表";
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


