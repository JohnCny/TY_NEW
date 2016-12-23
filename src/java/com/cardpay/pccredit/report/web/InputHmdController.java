package com.cardpay.pccredit.report.web;


import com.cardpay.pccredit.jnpad.model.CustomerInfo;
import com.cardpay.pccredit.report.model.CustomerHmd;
import com.cardpay.pccredit.report.service.InputHmdService;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;

import java.io.FileInputStream;  
import java.io.FileNotFoundException;   
import java.io.IOException;   
  
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;  
import org.apache.poi.ss.usermodel.Row;  
import org.apache.poi.ss.usermodel.Sheet;  
import org.apache.poi.ss.usermodel.Workbook;  
import org.apache.poi.xssf.usermodel.XSSFWorkbook;  
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@RequestMapping("/report/inputhmdcomtroller/*")
@JRadModule("report.inputhmdcomtroller")
public class InputHmdController extends BaseController  {
	@Autowired 
	private InputHmdService hmdService;
	
	/**
	 * 
	 * 黑名单导入显示
	 * @param filter
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@ResponseBody
	@RequestMapping(value = "hmdbrowse.page", method = {RequestMethod.GET})
	public AbstractModelAndView queryHmd(@ModelAttribute  CustomerHmd filter,HttpServletRequest request) {
		filter.setRequest(request);
		String name=request.getParameter("name");
		if(null!=name&&""!=name){
			filter.setName(name);
			QueryResult<CustomerHmd> result = hmdService.queryAll(filter);
			JRadPagedQueryResult<CustomerHmd> pagedResult = new JRadPagedQueryResult<CustomerHmd>(filter, result);
			JRadModelAndView mv = new JRadModelAndView("/riskcontrol/riskCustomer/hmdInput_browse", request);
			mv.addObject(PAGED_RESULT, pagedResult);
			return mv;
		}else{
			QueryResult<CustomerHmd> result = hmdService.queryAll(filter);
			JRadPagedQueryResult<CustomerHmd> pagedResult = new JRadPagedQueryResult<CustomerHmd>(filter, result);
			JRadModelAndView mv = new JRadModelAndView("/riskcontrol/riskCustomer/hmdInput_browse", request);
			mv.addObject(PAGED_RESULT, pagedResult);
			return mv;
		}
	}
	
	/** 
	 * 导入和导出Excel文件类 
	 * 支持2003(xls)和2007(xlsx)版本的Excel文件 
	 *  
	 * @author 周文杰
	 * @throws UnsupportedEncodingException 
	 */ 
	@ResponseBody
	@RequestMapping(value = "inputHmd.json",method = {RequestMethod.GET})
	@JRadOperation(JRadOperation.BROWSE)
	public JRadReturnMap insertHMD(
			HttpServletRequest request,
			@ModelAttribute CustomerHmd hmd) 
		{
			hmd.setRequest(request);
			JRadReturnMap returnMap = new JRadReturnMap();
			List<CustomerHmd> list=new ArrayList<CustomerHmd>();
			String execelFile=request.getParameter("urls");
			System.out.println("这是路径："+execelFile);
			 // 导入Excel  
			//系统内部   
			list=impExcels(execelFile,list);
			//全国
			list=impExcel(execelFile,list);
			if(list.size()>0){
				returnMap.put("mes","导入成功");
			}else{
				returnMap.put("mes","导入失败,请检查是否与模板相同");
			}
			for(int i=0;i<list.size()-1;i++){
				CustomerHmd hmds=hmdService.queryByCardId(list.get(i).getCardId());
				if(null!=hmds){
					continue;
				}else{
					hmd.setName(list.get(i).getName());
					hmd.setCardId(list.get(i).getCardId());
					hmd.setComefrom(list.get(i).getComefrom());
					hmdService.insetHmd(hmd);
				}
			}
			return returnMap;
		}
	
	
	    /** 
	     * 导入Excel 
	     * @param execelFile 
	     * @param list 
	     */  
	//全国
	    static  public  List<CustomerHmd> impExcel(String execelFile, List<CustomerHmd> list){  
	        try {  
	            // 构造 Workbook 对象，execelFile 是传入文件路径(获得Excel工作区)  
	            Workbook book = null;  
	            try {  
	                // Excel 2007获取方法  
	                book = new XSSFWorkbook(new FileInputStream(execelFile));  
	            } catch (Exception ex) {
	                // Excel 2003获取方法  
	                book = new HSSFWorkbook(new FileInputStream(execelFile));  
	            }  
	            // 读取表格的第一个sheet页  
	            Sheet sheet = book.getSheetAt(0);
	            // 定义 row、cell  
	            Row row;  
	            String cell;  
	            String name=null,cardId=null,comefrom=null;
	            // 总共有多少行,从0开始  
	            int totalRows = sheet.getLastRowNum();  
	            // 循环输出表格中的内容,首先循环取出行,再根据行循环取出列  
	            for (int i =2; i <= totalRows; i++) {  
	                row = sheet.getRow(i);  
	                // 处理空行  
	                if(row == null){  
	                    continue;
	                }  
	                // 总共有多少列,从0开始  
	                int totalCells = row.getLastCellNum() ;  
	                for (int j = row.getFirstCellNum(); j < totalCells; j++) {  
	                    // 处理空列  
	                    if(row.getCell(j) == null){  
	                        continue;  
	                    }  
	                    // 通过 row.getCell(j).toString() 获取单元格内容  
	                    cell = row.getCell(j).toString(); 
	                    if(j==1){
	                    name=cell;
	                    }
	                    if(j==2){
	                     cardId=cell;
	                    }
	                    if(j==3){
	                    	comefrom="全国";
	                    }
	                    //System.out.print(cell + "\t");
	                    
	            }  
	                list.add(new CustomerHmd(name, cardId,comefrom));
	                
	            }  
	        } catch (FileNotFoundException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }
			return list; 
	    }  
	//系统内部黑名单
	    static  public  List<CustomerHmd> impExcels(String execelFile, List<CustomerHmd> list){  
	        try {  
	            // 构造 Workbook 对象，execelFile 是传入文件路径(获得Excel工作区)  
	            Workbook book = null;  
	            try {  
	                // Excel 2007获取方法  
	                book = new XSSFWorkbook(new FileInputStream(execelFile));  
	            } catch (Exception ex) {
	                // Excel 2003获取方法  
	                book = new HSSFWorkbook(new FileInputStream(execelFile));  
	            }  
	            // 读取表格的第三个sheet页  
	            Sheet sheet = book.getSheetAt(2);  
	            // 定义 row、cell  
	            Row row;  
	            String cell;  
	            String name=null,cardId=null,comefrom=null;
	            // 总共有多少行,从0开始  
	            int totalRows = sheet.getLastRowNum();  
	            // 循环输出表格中的内容,首先循环取出行,再根据行循环取出列  
	            for (int i =2; i <= totalRows-1; i++) {  
	                row = sheet.getRow(i);  
	                // 处理空行  
	                if(row == null){  
	                    continue;
	                }  
	                // 总共有多少列,从0开始  
	                int totalCells = row.getLastCellNum() ;  
	                for (int j = row.getFirstCellNum(); j < totalCells; j++) {  
	                    // 处理空列  
	                    if(row.getCell(j) == null){  
	                        continue;  
	                    }  
	                    // 通过 row.getCell(j).toString() 获取单元格内容  
	                    cell = row.getCell(j).toString(); 
	                    if(j==2){
	                    	name=cell;
	                    }
	                    if(j==3){
	                     cardId=cell;
	                    }
	                    if(j==4){
	                    	comefrom="系统内部";
	                    }
	                    //System.out.print(cell + "\t");
	                    
	            }  
	                list.add(new CustomerHmd(name, cardId,comefrom));
	                
	            }  
	        } catch (FileNotFoundException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }
			return list; 
	    }  
	}  

