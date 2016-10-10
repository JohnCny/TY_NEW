package com.cardpay.pccredit.tools;



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

/** 
 * @author 贺珈 
 * @version 创建时间：2015年8月19日 下午4:16:36 
 * 每日更新客户原始数据
 */
@Service
public class UpdateCustomerTool {
	public Logger log = Logger.getLogger(UpdateCustomerTool.class);
	
	public String curRemotePath = "";//本次下载服务器目录
	private String[] fileName = {"kkh_grxx.zip","kkh_grjtcy.zip","kkh_grjtcc.zip","kkh_grscjy.zip","kkh_grxxll.zip","kkh_grgzll.zip","kkh_grrbxx.zip","kdk_yehz.zip","kdk_lsz.zip","kdk_tkmx.zip","cxd_dkcpmc.zip","kkh_hmdgl.zip","cxd_rygl.zip"};
//	//客户原始信息
//	private String[] fileTxt = {"kkh_grxx.txt","kkh_grjtcy.txt","kkh_grjtcc.txt","kkh_grscjy.txt","kkh_grxxll.txt","kkh_grgzll.txt","kkh_grrbxx.txt","kdk_tkmx.txt","kdk_lsz.txt","kdk_yehz.txt","cxd_dkcpmc.txt","kkh_hmdgl.txt"};
//	//流水账、余额汇总表、借据表
//	private String[] fileTxtRepay ={"kdk_yehz.txt","kdk_lsz.txt","kdk_tkmx.txt"};
//	//产品信息、黑名单
//	private String[] fileTxtProduct ={"cxd_dkcpmc.txt","kkh_hmdgl.txt"};
	@Autowired
	private CustomerInforService customerInforService;
	
	public void downloadFiles(){
		log.error("下载文件：");
		CardFtpUtils sftp = new CardFtpUtils();
		try {
			sftp.connect();
			curRemotePath = CardFtpUtils.bank_ftp_path;//上级目录
			//获取今日日期
	      //yyyyMMdd格式
			DateFormat format = new SimpleDateFormat("yyyyMMdd");
			String dateString = format.format(new Date());
			curRemotePath = curRemotePath+File.separator+dateString;
			//获取文件列表
			ArrayList<String> files = sftp.getList(curRemotePath);
			// 处理ftp文件
			processFtpFile(sftp, files);
			
		} catch (JSchException e) {
			e.printStackTrace();
		} catch (SftpException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(sftp != null){
				sftp.close();
				System.out.println("success,,/////");
			}
		}

	}
	
	/**
	 * 处理ftp文件
	 * @return
	 * @throws Exception 
	 */
	public void processFtpFile(CardFtpUtils sftp, ArrayList<String> files) throws Exception{
		Iterator<String> pathIterator = files.iterator();
		//获取今日日期
	      //yyyyMMdd格式
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		String dateString = format.format(new Date());
		// 下载路径
		String downloadPath =  CardFtpUtils.bank_ftp_down_path;
		downloadPath = downloadPath+File.separator+dateString;
		downloadPath = URLDecoder.decode(downloadPath, "utf-8");
		 File url = new File(downloadPath);
		 //本地创建当日数据文件夹
        if(!url.exists()){ 
        	url.mkdirs();  
        }
		while(pathIterator.hasNext()){
			String file = pathIterator.next();
			try{
				for(int i=0;i<fileName.length;i++){
					if(file.indexOf(fileName[i])>-1){
						//下载文件
						if(sftp.download(curRemotePath, file, downloadPath)){
							log.error("下载文件" + file + "成功");
						}else{
							log.error("下载文件" + file + "失败");
						}
						
					}
				}
			}catch(Exception e){
				log.error("处理文件" + file + "出错", e);
			}
		}
		log.error(dateString+"******************开始解压********************");  
		String gzFile = CardFtpUtils.bank_ftp_down_path+dateString;
		for(int i=0;i<fileName.length;i++){
			String url1 = gzFile+File.separator+fileName[i];
			File fileUrl = new File(url1);
			if(fileUrl.exists()){
				ZipFile zip = new ZipFile(url1);  
				for(Enumeration entries = zip.getEntries();entries.hasMoreElements();){
					ZipEntry entry = (ZipEntry)entries.nextElement();  
					String zipEntryName = entry.getName();  
					InputStream in = zip.getInputStream(entry);  
					String outPath = (gzFile+File.separator+zipEntryName).replaceAll("\\*", "/");
					//判断路径是否存在,不存在则创建文件路径  
					File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));  
					if(!file.exists()){  
						file.mkdirs();  
					}  
					//判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压  
					if(new File(outPath).isDirectory()){  
						continue;  
					}  
					
					OutputStream out = new FileOutputStream(outPath);  
					byte[] buf1 = new byte[1024];  
					int len;  
					while((len=in.read(buf1))>0){
						out.write(buf1,0,len);  
					}  
					in.close();  
					out.close();         
					zip.close();
				}
				//删除压缩包
				fileUrl.delete();
			}
			
		}
		log.error(dateString+"******************解压完毕********************");  
	}
	

}
