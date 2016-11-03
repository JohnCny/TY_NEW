package com.cardpay.pccredit.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.jfree.util.Log;

public class SPTxt {
	/**
	 * 把一个txt分成几等分
	 * 
	 * @param cnt需要分成几等分
	 */
	public static void splitTxt(String fileName,int count) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName),"utf-8"));
			//ReadLine br = new ReadLine(new InputStreamReader(new FileInputStream(fileName),"utf-8"));
			int to = fileName.lastIndexOf('.');
	    	fileName = fileName.substring(0, to);
			String row;
			List<Writer> flist = new ArrayList<Writer>();
			for (int i = 0; i < count; i++) {
				Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName + "_" + i + ".txt"), "utf-8"));
				flist.add(writer);
			}
			int rownum = 1;// 计数器
			while ((row = br.readLine()) != null) {
				flist.get(rownum % count).append(row + "\r\n");
				rownum++;
			}
			for (int i = 0; i < flist.size(); i++) {
				flist.get(i).close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
