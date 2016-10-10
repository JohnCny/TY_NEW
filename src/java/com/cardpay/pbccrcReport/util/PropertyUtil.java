/**   
 * 
 * 
 */
package com.cardpay.pbccrcReport.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Title: PropertiesUtil.java
 * @Description: TODO(用一句话描述该文件做什么)
 * @author 谭文华
 * @date 2014-12-2 下午3:34:42
 */
public class PropertyUtil {
	
	private static InputStream in = null;
	private static Properties props = new Properties();
	
	/**
	 * 构造函数
	 * @throws IOException
	 */
	public PropertyUtil() throws IOException{
		in = new BufferedInputStream(new FileInputStream("pbccrc.properties"));
		props.load(in);
	}
	
	/**
	 * 重新读取
	 * @throws IOException
	 */
	public static void reLoad() throws IOException{
		in = new BufferedInputStream(new FileInputStream("pbccrc.properties"));
		props.load(in);
	}
	
	/**
	 * 读取key的对应值
	 * @param key
	 * @return
	 */
	public static String getPropertyByKey(String key) {
		try {
			String value = props.getProperty(key);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
