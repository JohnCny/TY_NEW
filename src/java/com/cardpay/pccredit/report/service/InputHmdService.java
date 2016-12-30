package com.cardpay.pccredit.report.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cardpay.pccredit.common.UploadFileTool;
import com.cardpay.pccredit.intopieces.model.LocalExcel;
import com.cardpay.pccredit.manager.model.BatchTask;
import com.cardpay.pccredit.report.dao.InputHmdDao;
import com.cardpay.pccredit.report.model.CustomerHmd;
import com.cardpay.pccredit.tools.DataFileConf;
import com.cardpay.pccredit.tools.ImportBankDataFileTools;
import com.cardpay.pccredit.tools.JXLReadExcel;
import com.wicresoft.jrad.base.database.model.QueryResult;

@Service
public class InputHmdService {

	@Autowired
	private InputHmdDao hmdDao;

	public void insetHmd(CustomerHmd hmd) {
		// TODO Auto-generated method stub
		hmdDao.insetHmd(hmd);
	}

	public QueryResult<CustomerHmd> queryAll(CustomerHmd filter) {
		// TODO Auto-generated method stub
		List<CustomerHmd> cplist = hmdDao.queryAll(filter);
		int size = hmdDao.querySize(filter);
		QueryResult<CustomerHmd> queryResult = new QueryResult<CustomerHmd>(size,cplist);
		return queryResult;
	}

	public CustomerHmd queryByCardId(String cardId) {
		// TODO Auto-generated method stub
		return hmdDao.queryByCardId(cardId);
	}
	
	//导入黑名单
		public void importhmd(MultipartFile file) {
			// TODO Auto-generated method stub
			System.out.println("这是路径："+file);
			//本地测试
			Map<String, String> map = UploadFileTool.uploadYxzlFileBySpring(file);
			String execelFile = map.get("url");
			List<CustomerHmd> list=new ArrayList<CustomerHmd>();
			CustomerHmd hmd=new CustomerHmd();
			System.out.println("这是路径："+execelFile);
			 // 导入Excel  
			//系统内部   
			list=impExcels(execelFile,list);
			//全国
			list=impExcel(execelFile,list);
			for(int i=0;i<list.size()-1;i++){
				CustomerHmd hmds=hmdDao.queryByCardId(list.get(i).getCardId());
				if(null!=hmds){
					continue;
				}else{
					hmd.setName(list.get(i).getName());
					hmd.setCardId(list.get(i).getCardId());
					hmd.setComefrom(list.get(i).getComefrom());
					hmdDao.insetHmd(hmd);
				}
			}
			
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
