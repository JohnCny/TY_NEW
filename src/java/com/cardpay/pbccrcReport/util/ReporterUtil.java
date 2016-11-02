package com.cardpay.pbccrcReport.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Title: ReporterUtil.java
 * @Description: TODO(用一句话描述该文件做什么)
 * @author 谭文华
 * @date 2014-12-2 下午4:17:38
 */
public class ReporterUtil {

	/**
	 * 生成人行征信报告
	 * 
	 * @param buffer
	 * @param creditNo
	 * @param type
	 *            --"PBOC"：人行征信
	 * @return 文件uri
	 */
	public static String createReporterFile(byte[] buffer, String fileName,
			String type) {
		FileOutputStream fop = null;
		File file;
		fileName = fileName + ".html";

		try {
			file = new File(fileName);
			if (!file.exists()) {
				file.createNewFile();
			}
			fop = new FileOutputStream(file);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			// get the content in bytes
			byte[] contentInBytes = buffer;

			fop.write(contentInBytes);
			fop.flush();
			fop.close();

		} catch (IOException e) {
			e.printStackTrace();
			fileName = "";
		} finally {
			try {
				if (fop != null) {
					fop.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return fileName;
	}
}
