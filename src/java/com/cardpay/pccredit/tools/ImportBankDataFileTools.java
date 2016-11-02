package com.cardpay.pccredit.tools;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.wicresoft.jrad.base.database.id.IDGenerator;
import com.wicresoft.util.database.convert.SqlJavaNameUtil;
import com.wicresoft.util.date.DateHelper;

/**
 * @author chenzhifang
 *
 * 2014-12-5下午5:51:05
 */
public class ImportBankDataFileTools {
	// 分隔符
	public static final String SPLITE_CHARS = "\\,";
	
	public static final String DECIMAL = "DECIMAL";
	public static final String VARCHAR = "VARCHAR";
	public static final String DATE_STRING = "DATE_STRING";
	public static final String DATE_NOW = "DATE_NOW";
	
	public Logger log = Logger.getLogger(ImportBankDataFileTools.class);
	
	/**
	 *  解析数据文件配置
	 * @param fileName
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public List<DataFileConf> parseDataFileConf(String fileName) throws Exception {
		List<DataFileConf> confList = new ArrayList<DataFileConf>();
		//创建一个读取工具
	    SAXReader xmlReader = new SAXReader();
	    //获取xml文档实例 
	    InputStream is = ImportBankDataFileTools.class.getResourceAsStream(fileName);
	    Document xmlDocument = (Document) xmlReader.read(is);
	    List<Element> elements = xmlDocument.getRootElement().elements();
	    String column, jdbcType, index, style;
	    for(Element element : elements){
	    	column = element.attributeValue("column");
	    	jdbcType = element.attributeValue("jdbcType");
	    	index = element.attributeValue("index");
	    	style = element.attributeValue("style");
	    	if(StringUtils.isNotEmpty(index)){
	    		confList.add(new DataFileConf(column, jdbcType, Integer.valueOf(index), style));
	    	}else{
	    		confList.add(new DataFileConf(column, jdbcType, -1, style));
	    	}
	    }
	    return confList;
	}
	
	/**
	 * 解析”存贷记卡台帐“数据文件
	 * @param fileName
	 * @param confMap
	 * @return
	 * @throws Exception 
	 */
	public List<Map<String, Object>> parseDataFile(String fileName, List<DataFileConf> confList,String date) throws Exception{
		List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		//ReadLine br =null;
		String value = null;
		try{
			fis=new FileInputStream(fileName);
		    isr=new InputStreamReader(fis, "utf-8");
		    br = new BufferedReader(isr,10*1024*1024);// 用10M的缓冲读取文本文件  
		   // br = new ReadLine(isr,10*1024*1024);// 用10M的缓冲读取文本文件  
			String line="", type = null, column = null;
	        String[] dataArrs=null;
	        Map<String, Object> map = null;
	        int count=0;
	        while ((line=br.readLine())!=null) {
	        	count++;
	        	dataArrs = StringUtils.splitPreserveAllTokens(line.replaceAll(SPLITE_CHARS, "±"),"±");
	            map = new HashMap<String, Object>();
	            boolean flag = true;
	            if(dataArrs.length<=1){
	        		flag = false;
					break;
	        	}
	            for(DataFileConf dataFileConf : confList){
	            	type = dataFileConf.getJdbcType();
	            	column = dataFileConf.getColumn();
	            	value ="";
	            	
	            	
	            	if(dataFileConf.getIndex() >= 1 && dataFileConf.getIndex() <= dataArrs.length){
	            		value = dataArrs[dataFileConf.getIndex()-1].trim();
	            	}
	            	
	            	if(DECIMAL.equals(type) || VARCHAR.equals(type)){
	            		if(!StringUtils.isEmpty(value)){
	            			value = value.trim();
	            		}
											
						if(DECIMAL.equals(type) && StringUtils.isNotEmpty(value) && !NumberUtils.isNumber(value)){
							log.info(value + " is not number, line string : " + line);
							flag = false;
							break;
						}
	            	}else if(DATE_STRING.equals(type)){
	            		value = value.replaceAll("-", "");
	            	}else if(DATE_NOW.equals(type)){
	            		value = DateHelper.getDateFormat(new Date(), dataFileConf.getStyle());
	            	}
	            	if(!StringUtils.isEmpty(value)){
		            	value=value.replaceAll(" ","");
	            		value=value.replace("\"", "");
	            	}
	            	if("$".equals(value)){
	            		value ="";
	            	}
					map.put(SqlJavaNameUtil.getVariableName(column, false),value);
	            }
	            //map.put("id", IDGenerator.generateID());
	            if(flag){
	            	 map.put("createTime", date);
	            	datas.add(map);
	            }
	        }
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			if(br != null){
				 br.close();
			}
			if(isr != null){
				isr.close();
			}
			if(fis != null){
				fis.close();
			}
		}
		File file = new File(fileName);
		file.delete();
        return datas;
       
	}
	
	//RandomAccessFile 效率太低
	public List<Map<String, Object>> largeFileIO(String fileName, List<DataFileConf> confList) {
		List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
		try {
			RandomAccessFile br=new RandomAccessFile(fileName,"r");//这里rw看你了。要是之都就只写r
			String line = "", app = null;
			String value = null, type = null, column = null;
	        String[] dataArrs=null;
	        Map<String, Object> map = null;
			int count=0;int i=0;
			while ((line = br.readLine()) != null) {
				i++;
				count++;
				System.out.println(count);
				String tmpLine = new String(line.getBytes("8859_1"),"gbk");
				System.out.println(tmpLine);
				app=app+tmpLine+"$";
				
				if(i>=500){//假设读取100行
					i=0;
					//这里你先对这100行操作，然后继续读
					String[] lineArr = app.split("$");
					for(String obj:lineArr){
						dataArrs = StringUtils.splitPreserveAllTokens(obj.replaceAll(SPLITE_CHARS, "±"),"±");
			            map = new HashMap<String, Object>();
			            boolean flag = true;
			            for(DataFileConf dataFileConf : confList){
			            	type = dataFileConf.getJdbcType();
			            	column = dataFileConf.getColumn();
			            	
			            	if(dataFileConf.getIndex() >= 1){
			            		value = dataArrs[dataFileConf.getIndex()-1].trim();
			            	}
			            	
			            	if(DECIMAL.equals(type) || VARCHAR.equals(type)){
								value = value.trim();
								
								if(DECIMAL.equals(type) || NumberUtils.isNumber(value)){
									value = NumberUtils.createBigDecimal(value).toString();
								}
													
								if(DECIMAL.equals(type) && StringUtils.isNotEmpty(value) && !NumberUtils.isNumber(value)){
									log.info(value + " is not number, line string : " + obj);
									flag = false;
									break;
								}
			            	}else if(DATE_STRING.equals(type)){
			            		value = value.replaceAll("-", "");
			            	}else if(DATE_NOW.equals(type)){
			            		value = DateHelper.getDateFormat(new Date(), dataFileConf.getStyle());
			            	}
			            	
							map.put(SqlJavaNameUtil.getVariableName(column, false),value);
			            }
			            if(flag){
			            	datas.add(map);
			            }
					}
					
					app=null;
				}
			}
			if(app != null){
				//这里你先对这100行操作，然后继续读
				String[] lineArr = app.split("$");
				for(String obj:lineArr){
					dataArrs = StringUtils.splitPreserveAllTokens(obj.replaceAll(SPLITE_CHARS, "±"),"±");
		            map = new HashMap<String, Object>();
		            boolean flag = true;
		            for(DataFileConf dataFileConf : confList){
		            	type = dataFileConf.getJdbcType();
		            	column = dataFileConf.getColumn();
		            	
		            	if(dataFileConf.getIndex() >= 1){
		            		value = dataArrs[dataFileConf.getIndex()-1].trim();
		            	}
		            	
		            	if(DECIMAL.equals(type) || VARCHAR.equals(type)){
							value = value.trim();
							
							if(DECIMAL.equals(type) || NumberUtils.isNumber(value)){
								value = NumberUtils.createBigDecimal(value).toString();
							}
												
							if(DECIMAL.equals(type) && StringUtils.isNotEmpty(value) && !NumberUtils.isNumber(value)){
								log.info(value + " is not number, line string : " + obj);
								flag = false;
								break;
							}
		            	}else if(DATE_STRING.equals(type)){
		            		value = value.replaceAll("-", "");
		            	}else if(DATE_NOW.equals(type)){
		            		value = DateHelper.getDateFormat(new Date(), dataFileConf.getStyle());
		            	}
		            	
						map.put(SqlJavaNameUtil.getVariableName(column, false),value);
		            }
		            if(flag){
		            	datas.add(map);
		            }
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return datas;
	}
	
	//Scanner 耗时89s
	public List<Map<String, Object>> largeFileIO2(String fileName, List<DataFileConf> confList) {
		List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
		try {  
			FileInputStream fis=new FileInputStream(fileName);
			InputStreamReader isr=new InputStreamReader(fis, "gbk");
            Scanner sc = new Scanner(isr);  
            String line = "", app = "";
			String value = null, type = null, column = null;
	        String[] dataArrs=null;
	        Map<String, Object> map = null;
            int count = 0;  
            int i = 0;
            while(sc.hasNextLine()){  
            	i++;
            	count++;
            	System.out.println(count);
            	line = sc.nextLine();  
                String tmpLine = new String(line.getBytes("8859_1"),"gbk");
				System.out.println(tmpLine);
				app=app+tmpLine+"$";
				
				if(i>=500){//假设读取100行
					i=0;
					//这里你先对这100行操作，然后继续读
					String[] lineArr = app.split("$");
					for(String obj:lineArr){
						dataArrs = StringUtils.splitPreserveAllTokens(obj.replaceAll(SPLITE_CHARS, "±"),"±");
			            map = new HashMap<String, Object>();
			            boolean flag = true;
			            for(DataFileConf dataFileConf : confList){
			            	type = dataFileConf.getJdbcType();
			            	column = dataFileConf.getColumn();
			            	if(dataFileConf.getIndex() >= 1){
			            		value = dataArrs[dataFileConf.getIndex()-1].trim();
			            	}
			            	
			            	if(DECIMAL.equals(type) || VARCHAR.equals(type)){
								value = value.trim();
								
								if(DECIMAL.equals(type) || NumberUtils.isNumber(value)){
									value = NumberUtils.createBigDecimal(value).toString();
								}
													
								if(DECIMAL.equals(type) && StringUtils.isNotEmpty(value) && !NumberUtils.isNumber(value)){
									log.info(value + " is not number, line string : " + obj);
									flag = false;
									break;
								}
			            	}else if(DATE_STRING.equals(type)){
			            		value = value.replaceAll("-", "");
			            	}else if(DATE_NOW.equals(type)){
			            		value = DateHelper.getDateFormat(new Date(), dataFileConf.getStyle());
			            	}
			            	
							map.put(SqlJavaNameUtil.getVariableName(column, false),value);
			            }
			            if(flag){
			            	datas.add(map);
			            }  
					}
					app="";
				}
            }  
            
            if(!app.equals("")){
            	//这里你先对这100行操作，然后继续读
				String[] lineArr = app.split("$");
				for(String obj:lineArr){
					dataArrs = StringUtils.splitPreserveAllTokens(obj.replaceAll(SPLITE_CHARS, "±"),"±");
		            map = new HashMap<String, Object>();
		            boolean flag = true;
		            for(DataFileConf dataFileConf : confList){
		            	type = dataFileConf.getJdbcType();
		            	column = dataFileConf.getColumn();
		            	if(dataFileConf.getIndex() >= 1){
		            		value = dataArrs[dataFileConf.getIndex()-1].trim();
		            	}
		            	
		            	if(DECIMAL.equals(type) || VARCHAR.equals(type)){
							value = value.trim();
							
							if(DECIMAL.equals(type) || NumberUtils.isNumber(value)){
								value = NumberUtils.createBigDecimal(value).toString();
							}
												
							if(DECIMAL.equals(type) && StringUtils.isNotEmpty(value) && !NumberUtils.isNumber(value)){
								log.info(value + " is not number, line string : " + obj);
								flag = false;
								break;
							}
		            	}else if(DATE_STRING.equals(type)){
		            		value = value.replaceAll("-", "");
		            	}else if(DATE_NOW.equals(type)){
		            		value = DateHelper.getDateFormat(new Date(), dataFileConf.getStyle());
		            	}
		            	
						map.put(SqlJavaNameUtil.getVariableName(column, false),value);
		            }
		            if(flag){
		            	datas.add(map);
		            }  
				}
            }
            sc.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } 
		return datas;
	}
	
	//NIO
	public List<Map<String, Object>> largeFileIO3(String fileName, List<DataFileConf> confList) {  
		List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
		
		int bufSize = 100*1024*1024;
		ByteBuffer rBuffer = ByteBuffer.allocate(bufSize);
		FileChannel fcin = null;
		try {
			fcin = new RandomAccessFile(fileName, "r").getChannel();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
        String enterStr = "\n";  
        try {  
        	Map<String, Object> map = null;
            String value = null, type = null, column = null;
	        String[] dataArrs=null;
	        
            byte[] bs = new byte[bufSize];  
            StringBuilder strBuf = new StringBuilder("");  
            String tempString = null;
            
            int count = 0;
            while (fcin.read(rBuffer) != -1) {  
                int rSize = rBuffer.position();  
                rBuffer.rewind();  
                rBuffer.get(bs);  
                rBuffer.clear();  
                tempString = new String(bs, 0, rSize);  
                int fromIndex = 0;  
                int endIndex = 0;  
    	        
                while ((endIndex = tempString.indexOf(enterStr, fromIndex)) != -1) {  
                    String line = tempString.substring(fromIndex, endIndex);  
                    line = strBuf.toString() + line;
                    //line = new String(line.getBytes("8859_1"),"utf8");
                    System.out.println(line);
                    //writeFileByLine(fcout, wBuffer, line); 
                    count++;
                    System.out.println(count);
                    dataArrs = StringUtils.splitPreserveAllTokens(line.replaceAll(SPLITE_CHARS, "±"),"±");
		            map = new HashMap<String, Object>();
		            boolean flag = true;
		            for(DataFileConf dataFileConf : confList){
		            	type = dataFileConf.getJdbcType();
		            	column = dataFileConf.getColumn();
		            	if(dataFileConf.getIndex() >= 1){
		            		value = dataArrs[dataFileConf.getIndex()-1].trim();
		            	}
		            	
		            	if(DECIMAL.equals(type) || VARCHAR.equals(type)){
							value = value.trim();
							
							if(DECIMAL.equals(type) || NumberUtils.isNumber(value)){
								value = NumberUtils.createBigDecimal(value).toString();
							}
												
							if(DECIMAL.equals(type) && StringUtils.isNotEmpty(value) && !NumberUtils.isNumber(value)){
								log.info(value + " is not number, line string : " + line);
								flag = false;
								break;
							}
		            	}else if(DATE_STRING.equals(type)){
		            		value = value.replaceAll("-", "");
		            	}else if(DATE_NOW.equals(type)){
		            		value = DateHelper.getDateFormat(new Date(), dataFileConf.getStyle());
		            	}
		            	
						map.put(SqlJavaNameUtil.getVariableName(column, false),value);
		            }
		            if(flag){
		            	datas.add(map);
		            }
                    
                    strBuf.delete(0, strBuf.length());  
                    fromIndex = endIndex + 1;  
                }  
  
                if (rSize > tempString.length()) {  
                    strBuf.append(tempString.substring(fromIndex,  
                            tempString.length()));  
                } else {  
                    strBuf.append(tempString.substring(fromIndex, rSize));  
                }  
            }  
  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return null;
    } 
	
	
	public List<Map<String, Object>> parseTextDataFile(String fileName, List<DataFileConf> confList,String date) throws Exception{
		List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		String value = null;
		try{
			fis=new FileInputStream(fileName);
		    isr=new InputStreamReader(fis, "utf-8");
		    br = new BufferedReader(isr,10*1024*1024);// 用10M的缓冲读取文本文件  
		      
			String line="", type = null, column = null;
	        String[] dataArrs=null;
	        Map<String, Object> map = null;
	        int count=0;
	        while ((line=br.readLine())!=null) {
	        	count++;
	        	//System.out.println(count);
	        	dataArrs = StringUtils.splitPreserveAllTokens(line.replaceAll(SPLITE_CHARS, "±"),"±");
	            map = new HashMap<String, Object>();
	            boolean flag = true;
	            for(DataFileConf dataFileConf : confList){
	            	type = dataFileConf.getJdbcType();
	            	column = dataFileConf.getColumn();
	            	
	            	if(dataFileConf.getIndex() >= 1){
	            		value = dataArrs[dataFileConf.getIndex()-1].trim();
	            	}
	            	
	            	if(DECIMAL.equals(type) || VARCHAR.equals(type)){
						value = value.trim();
						
						/*if(DECIMAL.equals(type) || NumberUtils.isNumber(value)){
							try {
								value = NumberUtils.createBigDecimal(value).toString();
							} catch (Exception e) {
								// TODO Auto-generated catch block
								//e.printStackTrace();
								log.error("处理数据出错，保留原值");
							}
						}*/
											
						if(DECIMAL.equals(type) && StringUtils.isNotEmpty(value) && !NumberUtils.isNumber(value)){
							log.info(value + " is not number, line string : " + line);
							flag = false;
							break;
						}
	            	}else if(DATE_STRING.equals(type)){
	            		value = value.replaceAll("-", "");
	            	}else if(DATE_NOW.equals(type)){
	            		value = DateHelper.getDateFormat(new Date(), dataFileConf.getStyle());
	            	}
	            	value=value.replaceAll(" ","");
            		value=value.replace("\"", "");
					map.put(SqlJavaNameUtil.getVariableName(column, false),value);
	            }
	            map.put("id", IDGenerator.generateID());
	            map.put("createTime", date);
	            if(flag){
	            	datas.add(map);
	            }
	        }
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(br != null){
				 br.close();
			}
			if(isr != null){
				isr.close();
			}
			if(fis != null){
				fis.close();
			}
		}
		File file = new File(fileName);
		file.delete();
        return datas;
	}
	
	
	/**
	 * jn 解决换行问题
	 */
	public List<Map<String, Object>> parseDataFileJN(String fileName, List<DataFileConf> confList,String date) {  
		List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
		
		int bufSize = 100*1024*1024;
		ByteBuffer rBuffer = ByteBuffer.allocate(bufSize);
		FileChannel fcin = null;
		try {
			fcin = new RandomAccessFile(fileName, "r").getChannel();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} 
        String enterStr = "$\"\n";  
        try {  
        	Map<String, Object> map = null;
            String value = null, type = null, column = null;
	        String[] dataArrs=null;
	        
            byte[] bs = new byte[bufSize];  
            StringBuilder strBuf = new StringBuilder();  
            String tempString = null;
            
            int count = 0;
            while (fcin.read(rBuffer) != -1) {  
                int rSize = rBuffer.position();  
                rBuffer.rewind();  
                rBuffer.get(bs);  
                rBuffer.clear();  
                tempString = new String(bs, 0, rSize);  
                int fromIndex = 0;  
                int endIndex = 0;  
    	        
                while ((endIndex = tempString.indexOf(enterStr, fromIndex)) != -1) {  
                    String line = tempString.substring(fromIndex, endIndex);  
                    line = strBuf.toString() + line;
                    //line = new String(line.getBytes("8859_1"),"utf8");
                    System.out.println(line);
                    count++;
                    System.out.println(count);
                    dataArrs = StringUtils.splitPreserveAllTokens(line.replaceAll(SPLITE_CHARS, "±"),"±");
		            map = new HashMap<String, Object>();
		            boolean flag = true;
		            for(DataFileConf dataFileConf : confList){
		            	type = dataFileConf.getJdbcType();
		            	column = dataFileConf.getColumn();
		            	value ="";
		            	
		            	if(dataFileConf.getIndex() >= 1 && dataFileConf.getIndex() <= dataArrs.length){
		            		value = dataArrs[dataFileConf.getIndex()-1].trim();
		            	}
		            	
		            	if(DECIMAL.equals(type) || VARCHAR.equals(type)){
		            		if(!StringUtils.isEmpty(value)){
		            			value = value.trim();
		            		}
												
							if(DECIMAL.equals(type) && StringUtils.isNotEmpty(value) && !NumberUtils.isNumber(value)){
								log.info(value + " is not number, line string : " + line);
								flag = false;
								break;
							}
		            	}else if(DATE_STRING.equals(type)){
		            		value = value.replaceAll("-", "");
		            	}else if(DATE_NOW.equals(type)){
		            		value = DateHelper.getDateFormat(new Date(), dataFileConf.getStyle());
		            	}
		            	if(!StringUtils.isEmpty(value)){
			            	value=value.replaceAll(" ","");
		            		value=value.replace("\"", "");
		            	}
						map.put(SqlJavaNameUtil.getVariableName(column, false),value);
		            }
		            if(flag){
		            	map.put("createTime", date);
		            	datas.add(map);
		            }
                    
                    strBuf.delete(0, strBuf.length());  
                    fromIndex = endIndex + 1;  
                }  
  
                if (rSize > tempString.length()) {  
                    strBuf.append(tempString.substring(fromIndex,  
                            tempString.length()));  
                } else {  
                    strBuf.append(tempString.substring(fromIndex, rSize));  
                }  
            }  
  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return datas;
    } 
}


