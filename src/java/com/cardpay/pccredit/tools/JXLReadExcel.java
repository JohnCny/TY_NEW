package com.cardpay.pccredit.tools;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.cardpay.pccredit.common.SFTPUtil;

import sun.misc.BASE64Encoder;
/**
 * @功能描述 POI 读取 Excel 转 HTML 支持 03xls 和 07xlsx 版本  包含样式
 */
public class JXLReadExcel {
	/*public static void main(String[] args) {
		JXLReadExcel jxl = new JXLReadExcel();
		String path = "C:\\Users\\Administrator\\Desktop\\小微贷标准版0806模板---周素平12万.xlsx";
		jxl.readExcelToHtml(path, 0, true);
	}*/
	
	
	
    /**
     * 程序入口方法
     * @param filePath 文件的路径
     * @param isWithStyle 是否需要表格样式 包含 字体 颜色 边框 对齐方式
     * @param fileName 
     * @return <table>...</table> 字符串
     */
    public String[] readExcelToHtml(String filePath, boolean isWithStyle, String fileName){
    	String sheet[] = new String[13];
        InputStream is = null;
        Map<String, String> map = new HashMap<String, String>();
        try {
            File sourcefile = new File(filePath);
            is = new FileInputStream(sourcefile);
            Workbook wb = WorkbookFactory.create(is);
            for(int i=0;i<wb.getNumberOfSheets();i++)
            {
            	
            	if(wb.getSheetAt(i).getSheetName().indexOf("同级互评表1")>=0){
            		if (wb instanceof XSSFWorkbook) {
                        XSSFWorkbook xWb = (XSSFWorkbook) wb;
                        map = getExcelInfo(xWb,i,isWithStyle,ImportParameter.tjhpb,ImportParameter.editAble_tjhpb,false);
                    }else if(wb instanceof HSSFWorkbook){
                        HSSFWorkbook hWb = (HSSFWorkbook) wb;
                        map = getExcelInfo(hWb,i,isWithStyle,ImportParameter.tjhpb,ImportParameter.editAble_tjhpb,false);
                    }
                	String content_base64 = getBASE64(map.get("computerData").toString());
            		sheet[0] = content_base64;
            	}
            	
            	if(wb.getSheetAt(i).getSheetName().indexOf("基层员工上级评估")>=0){
            		if (wb instanceof XSSFWorkbook) {
                        XSSFWorkbook xWb = (XSSFWorkbook) wb;
                        map = getExcelInfo(xWb,i,isWithStyle,ImportParameter.tjhpb,ImportParameter.editAble_tjhpb,false);
                    }else if(wb instanceof HSSFWorkbook){
                        HSSFWorkbook hWb = (HSSFWorkbook) wb;
                        map = getExcelInfo(hWb,i,isWithStyle,ImportParameter.tjhpb,ImportParameter.editAble_tjhpb,false);
                    }
                	String content_base64 = getBASE64(map.get("computerData").toString());
            		sheet[1] = content_base64;
            	}
            	
            	if(wb.getSheetAt(i).getSheetName().indexOf("中层员工下级评估")>=0){
            		if (wb instanceof XSSFWorkbook) {
                        XSSFWorkbook xWb = (XSSFWorkbook) wb;
                        map = getExcelInfo(xWb,i,isWithStyle,ImportParameter.tjhpb,ImportParameter.editAble_tjhpb,false);
                    }else if(wb instanceof HSSFWorkbook){
                        HSSFWorkbook hWb = (HSSFWorkbook) wb;
                        map = getExcelInfo(hWb,i,isWithStyle,ImportParameter.tjhpb,ImportParameter.editAble_tjhpb,false);
                    }
                	String content_base64 = getBASE64(map.get("computerData").toString());
            		sheet[2] = content_base64;
            	}
            	
            	if(wb.getSheetAt(i).getSheetName().indexOf("中层员工上级评估")>=0){
            		if (wb instanceof XSSFWorkbook) {
                        XSSFWorkbook xWb = (XSSFWorkbook) wb;
                        map = getExcelInfo(xWb,i,isWithStyle,ImportParameter.tjhpb,ImportParameter.editAble_tjhpb,true);
                    }else if(wb instanceof HSSFWorkbook){
                        HSSFWorkbook hWb = (HSSFWorkbook) wb;
                        map = getExcelInfo(hWb,i,isWithStyle,ImportParameter.tjhpb,ImportParameter.editAble_tjhpb,true);
                    }
                	String content_base64 = getBASE64(map.get("computerData").toString());
            		sheet[3] = content_base64;
            	}
            	if(wb.getSheetAt(i).getSheetName().indexOf("同级互评表2")>=0){
            		if (wb instanceof XSSFWorkbook) {
                        XSSFWorkbook xWb = (XSSFWorkbook) wb;
                        map = getExcelInfo(xWb,i,isWithStyle,ImportParameter.tjhpb,ImportParameter.editAble_tjhpb,false);
                    }else if(wb instanceof HSSFWorkbook){
                        HSSFWorkbook hWb = (HSSFWorkbook) wb;
                        map = getExcelInfo(hWb,i,isWithStyle,ImportParameter.tjhpb,ImportParameter.editAble_tjhpb,false);
                    }
                	String content_base64 = getBASE64(map.get("computerData").toString());
            		sheet[4] = content_base64;
            	}
            	
            	if(wb.getSheetAt(i).getSheetName().indexOf("同级互评表3")>=0){
            		if (wb instanceof XSSFWorkbook) {
                        XSSFWorkbook xWb = (XSSFWorkbook) wb;
                        map = getExcelInfo(xWb,i,isWithStyle,ImportParameter.tjhpb,ImportParameter.editAble_tjhpb,false);
                    }else if(wb instanceof HSSFWorkbook){
                        HSSFWorkbook hWb = (HSSFWorkbook) wb;
                        map = getExcelInfo(hWb,i,isWithStyle,ImportParameter.tjhpb,ImportParameter.editAble_tjhpb,false);
                    }
                	String content_base64 = getBASE64(map.get("computerData").toString());
            		sheet[5] = content_base64;
            	}
            	if(wb.getSheetAt(i).getSheetName().indexOf("同级互评表4")>=0){
            		if (wb instanceof XSSFWorkbook) {
                        XSSFWorkbook xWb = (XSSFWorkbook) wb;
                        map = getExcelInfo(xWb,i,isWithStyle,ImportParameter.tjhpb,ImportParameter.editAble_tjhpb,false);
                    }else if(wb instanceof HSSFWorkbook){
                        HSSFWorkbook hWb = (HSSFWorkbook) wb;
                        map = getExcelInfo(hWb,i,isWithStyle,ImportParameter.tjhpb,ImportParameter.editAble_tjhpb,false);
                    }
                	String content_base64 = getBASE64(map.get("computerData").toString());
            		sheet[6] = content_base64;
            	}
            	if(wb.getSheetAt(i).getSheetName().indexOf("同级互评表5")>=0){
            		if (wb instanceof XSSFWorkbook) {
                        XSSFWorkbook xWb = (XSSFWorkbook) wb;
                        map = getExcelInfo(xWb,i,isWithStyle,ImportParameter.tjhpb,ImportParameter.editAble_tjhpb,false);
                    }else if(wb instanceof HSSFWorkbook){
                        HSSFWorkbook hWb = (HSSFWorkbook) wb;
                        map = getExcelInfo(hWb,i,isWithStyle,ImportParameter.tjhpb,ImportParameter.editAble_tjhpb,false);
                    }
                	String content_base64 = getBASE64(map.get("computerData").toString());
            		sheet[7] = content_base64;
            	}
            	if(wb.getSheetAt(i).getSheetName().indexOf("同级互评表6")>=0){
            		if (wb instanceof XSSFWorkbook) {
                        XSSFWorkbook xWb = (XSSFWorkbook) wb;
                        map = getExcelInfo(xWb,i,isWithStyle,ImportParameter.tjhpb,ImportParameter.editAble_tjhpb,false);
                    }else if(wb instanceof HSSFWorkbook){
                        HSSFWorkbook hWb = (HSSFWorkbook) wb;
                        map = getExcelInfo(hWb,i,isWithStyle,ImportParameter.tjhpb,ImportParameter.editAble_tjhpb,false);
                    }
                	String content_base64 = getBASE64(map.get("computerData").toString());
            		sheet[8] = content_base64;
            	}
            	if(wb.getSheetAt(i).getSheetName().indexOf("同级互评表7")>=0){
            		if (wb instanceof XSSFWorkbook) {
                        XSSFWorkbook xWb = (XSSFWorkbook) wb;
                        map = getExcelInfo(xWb,i,isWithStyle,ImportParameter.tjhpb,ImportParameter.editAble_tjhpb,false);
                    }else if(wb instanceof HSSFWorkbook){
                        HSSFWorkbook hWb = (HSSFWorkbook) wb;
                        map = getExcelInfo(hWb,i,isWithStyle,ImportParameter.tjhpb,ImportParameter.editAble_tjhpb,false);
                    }
                	String content_base64 = getBASE64(map.get("computerData").toString());
            		sheet[9] = content_base64;
            	}
            	if(wb.getSheetAt(i).getSheetName().indexOf("同级互评表8")>=0){
            		if (wb instanceof XSSFWorkbook) {
                        XSSFWorkbook xWb = (XSSFWorkbook) wb;
                        map = getExcelInfo(xWb,i,isWithStyle,ImportParameter.tjhpb,ImportParameter.editAble_tjhpb,false);
                    }else if(wb instanceof HSSFWorkbook){
                        HSSFWorkbook hWb = (HSSFWorkbook) wb;
                        map = getExcelInfo(hWb,i,isWithStyle,ImportParameter.tjhpb,ImportParameter.editAble_tjhpb,false);
                    }
                	String content_base64 = getBASE64(map.get("computerData").toString());
            		sheet[10] = content_base64;
            	}
            	if(wb.getSheetAt(i).getSheetName().indexOf("同级互评表9")>=0){
            		if (wb instanceof XSSFWorkbook) {
                        XSSFWorkbook xWb = (XSSFWorkbook) wb;
                        map = getExcelInfo(xWb,i,isWithStyle,ImportParameter.tjhpb,ImportParameter.editAble_tjhpb,false);
                    }else if(wb instanceof HSSFWorkbook){
                        HSSFWorkbook hWb = (HSSFWorkbook) wb;
                        map = getExcelInfo(hWb,i,isWithStyle,ImportParameter.tjhpb,ImportParameter.editAble_tjhpb,false);
                    }
                	String content_base64 = getBASE64(map.get("computerData").toString());
            		sheet[11] = content_base64;
            	}
            	if(wb.getSheetAt(i).getSheetName().indexOf("同级互评表10")>=0){
            		if (wb instanceof XSSFWorkbook) {
                        XSSFWorkbook xWb = (XSSFWorkbook) wb;
                        map = getExcelInfo(xWb,i,isWithStyle,ImportParameter.tjhpb,ImportParameter.editAble_tjhpb,false);
                    }else if(wb instanceof HSSFWorkbook){
                        HSSFWorkbook hWb = (HSSFWorkbook) wb;
                        map = getExcelInfo(hWb,i,isWithStyle,ImportParameter.tjhpb,ImportParameter.editAble_tjhpb,false);
                    }
                	String content_base64 = getBASE64(map.get("computerData").toString());
            		sheet[12] = content_base64;
            	}
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sheet;
    }
    
    
    public Map<String, String> getExcelInfo(Workbook wb,int index,boolean isWithStyle,String[] rowAndcol,String[] editAble,boolean averageWidth) throws Exception{
       try {
    	   int maxRow = Integer.parseInt(rowAndcol[0])-1;//最大行
    	   String maxCol = rowAndcol[1];//最大列
    	   
    	   StringBuffer sb = new StringBuffer();
    	   Sheet sheet = wb.getSheetAt(index);//获取第一个Sheet的内容
    	   int lastRowNum = sheet.getLastRowNum();
    	   if(lastRowNum>maxRow){
    		   lastRowNum = maxRow;
    	   }
    	   Map<String, String> map[] = getRowSpanColSpanMap(sheet);
    	   sb.append("<table id='MyTable' style='border-collapse:collapse;' width='100%'>");
    	   Row row = null;        //兼容
    	   Cell cell = null;    //兼容
    	   
    	   Map<String, String> resultMap = new HashMap<String, String>();
    	   for (int rowNum = sheet.getFirstRowNum(); rowNum <= lastRowNum; rowNum++) {
    		   row = sheet.getRow(rowNum);
    		   if (row == null) {
    			   sb.append("<tr><td > &nbsp;</td></tr>");
    			   continue;
    		   }
    		   sb.append("<tr>");
    		   int lastColNum = row.getLastCellNum();
    		   if(lastColNum > columnToIndex(maxCol)){
    			   lastColNum = columnToIndex(maxCol);
    		   }
    		   for (int colNum = 0; colNum < lastColNum; colNum++) {
    			   cell = row.getCell(colNum);
    			   if (cell == null) {    //特殊情况 空白的单元格会返回null
    				   sb.append("<td>&nbsp;</td>");
    				   continue;
    			   }
    			   cell.setCellType(Cell.CELL_TYPE_STRING);
					String	stringValue = getCellValue(cell);
					System.out.println(stringValue);
    			   if (map[0].containsKey(rowNum + "," + colNum)) {
    				   String pointString = map[0].get(rowNum + "," + colNum);
    				   map[0].remove(rowNum + "," + colNum);
    				   int bottomeRow = Integer.valueOf(pointString.split(",")[0]);
    				   int bottomeCol = Integer.valueOf(pointString.split(",")[1]);
    				   int rowSpan = bottomeRow - rowNum + 1;
    				   int colSpan = bottomeCol - colNum + 1;
    				   sb.append("<td rowspan= '" + rowSpan + "' colspan= '"+ colSpan + "' ");
    			   } else if (map[1].containsKey(rowNum + "," + colNum)) {
    				   map[1].remove(rowNum + "," + colNum);
    				   continue;
    			   } else {
    				   sb.append("<td ");
    			   }
    			   
    			   String tmp = indexToColumn(colNum+1) +(rowNum+1);
    			   sb.append("name='"+tmp+"' ");
    			   if(editAble != null && Arrays.asList(editAble).contains(tmp)){//判断是否可编辑
    				   sb.append("onclick='return EditCell();' ");
    			   }
    			   
    			   //判断是否需要样式
    			   if(isWithStyle){
    				   dealExcelStyle(wb, sheet, cell, sb,averageWidth);//处理单元格样式
    			   }
    			   
    			   sb.append(">");
    			   
    			   if (stringValue == null || "".equals(stringValue.trim())) {
    				   sb.append("&nbsp;");
    			   } else {
    				   // 将ascii码为160的空格转换为html下的空格（&nbsp;）
    				   stringValue = stringValue.replaceAll(",", "");
    				   sb.append(stringValue.replace(String.valueOf((char) 160),"&nbsp;"));
    			   }
//    			   if(padAble != null && Arrays.asList(padAble).contains(tmp)){//生成pad展示数据string
//    				   padString+=stringValue+"@@";
//    			   }
    			   sb.append("</td>");
    		   }
    		   sb.append("</tr>");
    	   }
//    	   padString=padString.substring(0, padString.length()-2);
//    	   resultMap.put("padData", padString);
    	   
    	   sb.append("</table>");
    	   resultMap.put("computerData", sb.toString());
    	   return resultMap;
		
	} catch (Exception e) {
		e.printStackTrace();
		return null;
	}
    }
    
    private Map<String, String>[] getRowSpanColSpanMap(Sheet sheet) {

        Map<String, String> map0 = new HashMap<String, String>();
        Map<String, String> map1 = new HashMap<String, String>();
        int mergedNum = sheet.getNumMergedRegions();
        CellRangeAddress range = null;
        for (int i = 0; i < mergedNum; i++) {
            range = sheet.getMergedRegion(i);
            int topRow = range.getFirstRow();
            int topCol = range.getFirstColumn();
            int bottomRow = range.getLastRow();
            int bottomCol = range.getLastColumn();
            map0.put(topRow + "," + topCol, bottomRow + "," + bottomCol);
            // System.out.println(topRow + "," + topCol + "," + bottomRow + "," + bottomCol);
            int tempRow = topRow;
            while (tempRow <= bottomRow) {
                int tempCol = topCol;
                while (tempCol <= bottomCol) {
                    map1.put(tempRow + "," + tempCol, "");
                    tempCol++;
                }
                tempRow++;
            }
            map1.remove(topRow + "," + topCol);
        }
        Map[] map = { map0, map1 };
        return map;
    }
    
    
    /**
     * 获取表格单元格Cell内容
     * @param cell
     * @return
     */
    public String getCellValue(Cell cell) {
	        String result = new String();  
	        switch (cell.getCellType()) {  
	        case Cell.CELL_TYPE_NUMERIC:// 数字类型  
	        case Cell.CELL_TYPE_FORMULA:
        		if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式  
        			SimpleDateFormat sdf = null;  
        			if (cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("h:mm")) {  
        				sdf = new SimpleDateFormat("HH:mm");  
        			} else {// 日期  
        				sdf = new SimpleDateFormat("yyyy-MM-dd");  
        			}  
        			Date date = cell.getDateCellValue();  
        			result = sdf.format(date);  
        		} else if (cell.getCellStyle().getDataFormat() == 14 
        				|| cell.getCellStyle().getDataFormat() == 20
        				|| cell.getCellStyle().getDataFormat() == 31 
        				|| cell.getCellStyle().getDataFormat() == 32 
        				|| cell.getCellStyle().getDataFormat() == 57 
        				|| cell.getCellStyle().getDataFormat() == 58) {  
        			// 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)  
        			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");  
        			double value = cell.getNumericCellValue();  
        			Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);  
        			result = sdf.format(date);  
        		} else {  
        			double value = cell.getNumericCellValue();  
        			CellStyle style = cell.getCellStyle();  
        			DecimalFormat format = new DecimalFormat();  
        			String temp = style.getDataFormatString();  
        			// 单元格设置成常规  
        			if (temp.equals("General")) {  
        				format.applyPattern("#");  
        			}  
        			result = format.format(value);  
        		}  
        		break;  
			
	        case Cell.CELL_TYPE_STRING:// String类型  
	            result = cell.getStringCellValue().toString(); 
	            break;  
	        case Cell.CELL_TYPE_BLANK:  
	            result = "";  
	            break; 
	        default:  
	            result = "";  
	            break;  
	        }  
	        return result;  
    }
    
    /**
     * 处理表格样式
     * @param wb
     * @param sheet
     * @param cell
     * @param sb
     * @throws Exception 
     */
    private void dealExcelStyle(Workbook wb,Sheet sheet,Cell cell,StringBuffer sb,boolean averageWidth) throws Exception{
        
        CellStyle cellStyle = cell.getCellStyle();
        if (cellStyle != null) {
            short alignment = cellStyle.getAlignment();
            sb.append("align='" + convertAlignToHtml(alignment) + "' ");//单元格内容的水平对齐方式
            short verticalAlignment = cellStyle.getVerticalAlignment();
            sb.append("valign='"+ convertVerticalAlignToHtml(verticalAlignment)+ "' ");//单元格中内容的垂直排列方式
            
            if (wb instanceof XSSFWorkbook) {
                            
                XSSFFont xf = ((XSSFCellStyle) cellStyle).getFont(); 
                short boldWeight = xf.getBoldweight();
                sb.append("style='");
                sb.append("font-weight:" + boldWeight + ";"); // 字体加粗
                sb.append("font-size: " + xf.getFontHeight() / 2 + "%;"); // 字体大小
                if(averageWidth){
                	sb.append("width:" + 10 + "%;");
                }
                else{
                	int columnWidth = sheet.getColumnWidth(cell.getColumnIndex()) ;
                	sb.append("width:" + columnWidth + "px;");
                }
                
                XSSFColor xc = xf.getXSSFColor();
                if (xc != null && !"".equals(xc)) {
                    sb.append("color:#000000;"); // 字体颜色
                }
                
                XSSFColor bgColor = (XSSFColor) cellStyle.getFillForegroundColorColor();
                //System.out.println("BackgroundColorColor: "+cellStyle.getFillBackgroundColorColor());
                //System.out.println("ForegroundColor: "+cellStyle.getFillForegroundColor());//0
                //System.out.println("BackgroundColorColor: "+cellStyle.getFillBackgroundColorColor());
                //System.out.println("ForegroundColorColor: "+cellStyle.getFillForegroundColorColor());
                if (bgColor != null && !"".equals(bgColor)) {
                    sb.append("background-color:#C0C0C0;"); // 背景颜色
                }
                sb.append("border-top:solid #000000 1px;");
                sb.append("border-right:solid #000000 1px;");
                sb.append("border-bottom:solid #000000 1px;");
                sb.append("border-left:solid #000000 1px;");
                    
            }else if(wb instanceof HSSFWorkbook){
                
                HSSFFont hf = ((HSSFCellStyle) cellStyle).getFont(wb);
                short boldWeight = hf.getBoldweight();
                short fontColor = hf.getColor();
                sb.append("style='");
                HSSFPalette palette = ((HSSFWorkbook) wb).getCustomPalette(); // 类HSSFPalette用于求的颜色的国际标准形式
                HSSFColor hc = palette.getColor(fontColor);
                sb.append("font-weight:" + boldWeight + ";"); // 字体加粗
                sb.append("font-size: " + hf.getFontHeight() / 2 + "%;"); // 字体大小
                String fontColorStr = convertToStardColor(hc);
                if (fontColorStr != null && !"".equals(fontColorStr.trim())) {
                    sb.append("color:" + fontColorStr + ";"); // 字体颜色
                }
                
                if(averageWidth){
                	sb.append("width:" + 10 + "%;");
                }
                else{
                	int columnWidth = sheet.getColumnWidth(cell.getColumnIndex()) ;
                	sb.append("width:" + columnWidth + "px;");
                }
                short bgColor = cellStyle.getFillForegroundColor();
                hc = palette.getColor(bgColor);
                String bgColorStr = convertToStardColor(hc);
                if (bgColorStr != null && !"".equals(bgColorStr.trim())) {
                    sb.append("background-color:" + bgColorStr + ";"); // 背景颜色
                }
                sb.append( getBorderStyle(palette,0,cellStyle.getBorderTop(),cellStyle.getTopBorderColor()));
                sb.append( getBorderStyle(palette,1,cellStyle.getBorderRight(),cellStyle.getRightBorderColor()));
                sb.append( getBorderStyle(palette,3,cellStyle.getBorderLeft(),cellStyle.getLeftBorderColor()));
                sb.append( getBorderStyle(palette,2,cellStyle.getBorderBottom(),cellStyle.getBottomBorderColor()));
            }

            sb.append("' ");
        }
    }
    
    /**
     * 单元格内容的水平对齐方式
     * @param alignment
     * @return
     */
    private String convertAlignToHtml(short alignment) {

        String align = "left";
        switch (alignment) {
        case CellStyle.ALIGN_LEFT:
            align = "left";
            break;
        case CellStyle.ALIGN_CENTER:
            align = "center";
            break;
        case CellStyle.ALIGN_RIGHT:
            align = "right";
            break;
        default:
            break;
        }
        return align;
    }

    /**
     * 单元格中内容的垂直排列方式
     * @param verticalAlignment
     * @return
     */
    private String convertVerticalAlignToHtml(short verticalAlignment) {

        String valign = "middle";
        switch (verticalAlignment) {
        case CellStyle.VERTICAL_BOTTOM:
            valign = "bottom";
            break;
        case CellStyle.VERTICAL_CENTER:
            valign = "center";
            break;
        case CellStyle.VERTICAL_TOP:
            valign = "top";
            break;
        default:
            break;
        }
        return valign;
    }
    
    private String convertToStardColor(HSSFColor hc) {

        StringBuffer sb = new StringBuffer("");
        if (hc != null) {
            if (HSSFColor.AUTOMATIC.index == hc.getIndex()) {
                return null;
            }
            sb.append("#");
            for (int i = 0; i < hc.getTriplet().length; i++) {
                sb.append(fillWithZero(Integer.toHexString(hc.getTriplet()[i])));
            }
        }

        return sb.toString();
    }
    
    private String fillWithZero(String str) {
        if (str != null && str.length() < 2) {
            return "0" + str;
        }
        return str;
    }
    
    static String[] bordesr={"border-top:","border-right:","border-bottom:","border-left:"};
    static String[] borderStyles={"solid ","solid ","solid ","solid ","solid ","solid ","solid ","solid ","solid ","solid","solid","solid","solid","solid"};

    private String getBorderStyle(  HSSFPalette palette ,int b,short s, short t){
         
        if(s==0)return  bordesr[b]+borderStyles[s]+"#d0d7e5 1px;";;
        String borderColorStr = convertToStardColor( palette.getColor(t));
        borderColorStr=borderColorStr==null|| borderColorStr.length()<1?"#000000":borderColorStr;
        return bordesr[b]+borderStyles[s]+borderColorStr+" 1px;";
        
    }
    
    private String getBorderStyle(int b,short s, XSSFColor xc){
         
         if(s==0)return  bordesr[b]+borderStyles[s]+"#d0d7e5 1px;";;
         if (xc != null && !"".equals(xc)) {
             String borderColorStr = xc.getARGBHex();//t.getARGBHex();
             borderColorStr=borderColorStr==null|| borderColorStr.length()<1?"#000000":borderColorStr.substring(2);
             return bordesr[b]+borderStyles[s]+borderColorStr+" 1px;";
         }
         
         return "";
    }
    
    public static String getBASE64(String s) { 
    	if (s == null) return null; 
    	return (new BASE64Encoder()).encode( s.getBytes() ); 
	} 

    private static int columnToIndex(String column) {
        if (!column.matches("[A-Z]+")) {
                try {
                        throw new Exception("Invalid parameter");
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }
        int index = 0;
        char[] chars = column.toUpperCase().toCharArray();
        for (int i = 0; i < chars.length; i++) {
                index += ((int) chars[i] - (int) 'A' + 1)
                                * (int) Math.pow(26, chars.length - i - 1);
        }
        return index;
    }
    
    private static String indexToColumn(int index) throws Exception {
        if (index <= 0) {                 
        		throw new Exception("Invalid parameter");         
        	}         
        index--;         
        String column = "";         
        do {                 
        	if (column.length() > 0) {
                        index--;
                }
                column = ((char) (index % 26 + (int) 'A')) + column;
                index = (int) ((index - index % 26) / 26);
        } while (index > 0);
        return column;
    }
}