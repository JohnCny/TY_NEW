package com.cardpay.pccredit.jnpad.model;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.cardpay.pccredit.common.CreateThumbnail;
import com.cardpay.pccredit.intopieces.constant.Constant;
import com.cardpay.pccredit.product.model.AddressAccessories;
import com.wicresoft.jrad.base.database.id.IDGenerator;

public class JNPAD_UploadFileTool {

	public static String upload(MultipartFile file) {
		String newFileName = null;
		File tempDir = new File(Constant.FILE_PATH);
		if (!tempDir.isDirectory()) {
			tempDir.mkdirs();
		}
		/* 将文件保存到文件夹中 */
		if (file != null && !file.isEmpty()) {
			try {
				/* 每次读8K字节 */
				byte[] buffer = new byte[8192];
				int count = 0;
				InputStream is = file.getInputStream();
				/* 如果服务器已经存在和上传文件同名的文件，先删除原来的文件 */
				File tempFile = new File(Constant.FILE_PATH
						+ file.getOriginalFilename());
				if (tempFile.exists()) {
					newFileName = IDGenerator.generateID() + "."
							+ file.getOriginalFilename().split("\\.")[1];
					/* tempFile.delete(); */
				} else {
					newFileName = file.getOriginalFilename();
				}
				/* 开始保存文件到服务器 */
				FileOutputStream fos = new FileOutputStream(Constant.FILE_PATH
						+ newFileName);
				/* 开始读取上传文件的字节，并将其输出到服务端的上传文件输出流中 */
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
				}
				fos.close();
				is.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return newFileName;
	}

	/* 上传产品附件专用 */
	public static String uploadFileBySpring(HttpServletRequest request,
			AddressAccessories addressAccessories) {
		String newFileName = null;
		File tempDir = new File(Constant.FILE_PATH);
		if (!tempDir.isDirectory()) {
			tempDir.mkdirs();
		}
		// 创建一个通用的多部分解析器
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 判断 request 是否有文件上传,即多部分请求
		try {
			if (multipartResolver.isMultipart(request)) {
				// 转换成多部分request
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
				// 取得request中的所有文件名
				Map<String, MultipartFile> fileMap = multiRequest.getFileMap();
				for (String key : fileMap.keySet()) {
					if (key.split("_")[1].equals(addressAccessories
							.getAppendixTypeCode())) {
						// 取得上传文件
						MultipartFile file = multiRequest.getFile(key);
						if (file != null && !file.isEmpty()) {
							File tempFile = new File(Constant.FILE_PATH
									+ file.getOriginalFilename());
							if (tempFile.exists()) {
								newFileName = addressAccessories.getId()
										+ "_"
										+ key
										+ "_"
										+ "."
										+ file.getOriginalFilename().split(
												"\\.")[1];
							} else {
								newFileName = addressAccessories.getId() + "_"
										+ key + "_"
										+ file.getOriginalFilename();
							}
							File localFile = new File(Constant.FILE_PATH
									+ newFileName);
							file.transferTo(localFile);
						}
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newFileName;
	}

	/* 上传影像资料专用 */
	public static Map<String, String> uploadYxzlFileBySpring(MultipartFile file) {
		String newFileName = null;
		String fileName = null;
		Map<String, String> map = new HashMap<String, String>();
		File tempDir = new File(Constant.FILE_PATH);
		if (!tempDir.isDirectory()) {
			tempDir.mkdirs();
		}
		try {
			// 取得上传文件
			if (file != null && !file.isEmpty()) {
				fileName = file.getOriginalFilename();
				File tempFile = new File(Constant.FILE_PATH
						+ file.getOriginalFilename());
				if (tempFile.exists()) {
					newFileName = IDGenerator.generateID() + "."
							+ file.getOriginalFilename().split("\\.")[1];
				} else {
					newFileName = file.getOriginalFilename();
				}
				File localFile = new File(Constant.FILE_PATH + newFileName);
				file.transferTo(localFile);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("fileName", fileName);
		map.put("url", Constant.FILE_PATH + newFileName);
		return map;
	}

	/* 上传影像资料专用 */
	public static Map<String, String> uploadYxzlFileBySpring(MultipartFile file,String customerId,String fileName_1) {
		String newFileName = null;
		String fileName = null;
		Map<String, String> map = new HashMap<String, String>();
		String path = Constant.FILE_PATH + customerId + File.separator;
		File tempDir = new File(path);
		if (!tempDir.isDirectory()) {
			tempDir.mkdirs();
		}
		try {
			// 取得上传文件
			if (file != null && !file.isEmpty()) {
//				fileName = file.getOriginalFilename();
				fileName = fileName_1;
				File tempFile = new File(path
						+ fileName_1);
				if (tempFile.exists()) {
					newFileName = IDGenerator.generateID() + "."
							+ fileName_1.split("\\.")[1];
				} else {
					newFileName = fileName_1;
				}
				File localFile = new File(path + newFileName);
				file.transferTo(localFile);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("fileName", fileName);
		map.put("url", path + newFileName);
		return map;
	}
	
	/* 删除资料 */
	public static void deleteFile(String filePath) {
		File tempFile = new File(filePath);
		if (tempFile.exists()) {
			tempFile.delete();
		}
	}

	/* 下载资料 */
	public static void downLoadFile(HttpServletResponse response,
			String filePath, String fileName) throws Exception {
		byte[] buff = new byte[2048];
		int bytesRead;
		response.setHeader("Content-Disposition", "attachment; filename="
				+ java.net.URLEncoder.encode(fileName, "UTF-8"));
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(
				filePath));
		BufferedOutputStream bos = new BufferedOutputStream(
				response.getOutputStream());
		while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
			bos.write(buff, 0, bytesRead);
		}
		bos.flush();
		if (bis != null) {
			bis.close();
		}
		if (bos != null) {
			bos.close();
		}
	}

	/* 拼接字符串导出报文格式 */
	public static StringBuffer getContent(StringBuffer allContent,
			String appendContent, int length) {
		StringBuffer sb = new StringBuffer();
		if (appendContent != null) {
			if (appendContent.length() == length) {
				sb.append(appendContent);
			} else if (appendContent.length() < length) {
				sb.append(appendContent);
				for (int i = 0; i < length - appendContent.length(); i++) {
					sb.append(" ");
				}
			} else {
				sb.append(appendContent.substring(0, length));
			}
		} else {
			for (int i = 0; i < length; i++) {
				sb.append(" ");
			}
		}
		return allContent.append(sb);
	}

	/* 导出文本格式数据接口 */
	public static void exportTextFile(HttpServletResponse response,
			String fileContext, String fileName) throws Exception {
		byte[] buff = new byte[2048];
		int bytesRead;
		response.setHeader("Content-Disposition", "attachment; filename="
				+ java.net.URLEncoder.encode(fileName, "UTF-8"));
		InputStream bis = new ByteArrayInputStream(fileContext.getBytes());
		BufferedOutputStream bos = new BufferedOutputStream(
				response.getOutputStream());
		while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
			bos.write(buff, 0, bytesRead);
		}
		bos.flush();
		if (bis != null) {
			bis.close();
		}
		if (bos != null) {
			bos.close();
		}
	}

	/* ftp文件上传 */
	public static void uploadFileToFtp(String url, int port, String username,
			String password, String path, String fileName, String fileContext) {
		FTPClient ftp = new FTPClient();
		InputStream input = null;
		try {
			int reply;
			input = new ByteArrayInputStream(fileContext.getBytes());
			ftp.connect(url, port);
			ftp.login(username, password);
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
			}
			ftp.changeWorkingDirectory(path);
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftp.storeFile(new String(fileName.getBytes("GBK"), "iso-8859-1"),
					input);
			input.close();
			ftp.logout();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
	}

	/* 生成缩略图 */
	public static Map<String, String> CreateThumbnail(String path, String url,
			String fileName) {
		Map<String, String> map = new HashMap<String, String>();
		File tempDir = new File(path);
		if (!tempDir.isDirectory()) {
			tempDir.mkdirs();
		}
		/* 如果服务器已经存在和上传文件同名的文件，则直接输出该缩略图 */
		File tempFile = new File(path + "new_" + fileName);
		if (tempFile.exists()) {
			map.put("new", path + "new_" + fileName);
			map.put("old", path + fileName);
			return map;
		}
		CreateThumbnail createThumbnail = new CreateThumbnail();
		try {
			createThumbnail.SetScale(0.5);
			createThumbnail.SetSmallHeight(60);
			createThumbnail.doFinal(path + fileName, path + "new_" + fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("new", path + "new_" + fileName);
		map.put("old", path + fileName);
		return map;
	}
	//读取本地图片
	public void showPicture(HttpServletResponse response,String filePath){
		FileInputStream is;
		try {
			is = new FileInputStream(filePath);
			int i = is.available(); // 得到文件大小
			byte data[] = new byte[i];
			is.read(data); // 读数据
			response.setContentType("image/*"); // 设置返回的文件类型
			OutputStream toClient = response.getOutputStream(); // 得到向客户端输出二进制数据的对象
			toClient.write(data); // 输出数据
			IOUtils.copy(is, toClient);
			is.close();
			toClient.flush();
			toClient.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
}
	
	public static Map<String, String> uploadTxtFileBySpring(MultipartFile file) {
		String newFileName = null;
		String fileName = null;
		Map<String, String> map = new HashMap<String, String>();
		String path = Constant.FILE_PATH  + File.separator;
		File tempDir = new File(path);
		if (!tempDir.isDirectory()) {
			tempDir.mkdirs();
		}
		try {
			// 取得上传文件
			if (file != null && !file.isEmpty()) {
				fileName = file.getOriginalFilename();
				File tempFile = new File(path
						+ file.getOriginalFilename());
				if (tempFile.exists()) {
					newFileName = IDGenerator.generateID() + "."
							+ file.getOriginalFilename().split("\\.")[1];
				} else {
					newFileName = file.getOriginalFilename();
				}
				File localFile = new File(path + newFileName);
				file.transferTo(localFile);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("fileName", fileName);
		map.put("url", path + newFileName);
		return map;
	}

	
	
	//泉州影像上传
	public static Map<String, String>  uploadYxzlFileBySpring_qz(MultipartFile file,String batch_id) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		String newFileName = null;
		String fileName = null;
		String serverPath = Constant.FILE_PATH_BS + batch_id + File.separator;
		File tempDir = new File(serverPath);
		if (!tempDir.isDirectory()) {
			tempDir.mkdirs();
		}
		// 取得上传文件
		if (file != null && !file.isEmpty()) {
			fileName = file.getOriginalFilename();
			File tempFile = new File(serverPath + fileName);
			if (tempFile.exists()) {
				newFileName = IDGenerator.generateID() + "." + fileName.split("\\.")[1];
			} else {
				newFileName = fileName;
			}
			File localFile = new File(serverPath + newFileName);
			file.transferTo(localFile);
		}
		
		map.put("fileName", newFileName);
		map.put("url", serverPath + newFileName);
		return map;
	}
	



}
