package com.cardpay.pccredit.jnpad.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.common.SFTPUtil;
import com.cardpay.pccredit.intopieces.model.LocalImage;
import com.cardpay.pccredit.intopieces.model.QzApplnAttachmentDetail;
import com.cardpay.pccredit.intopieces.web.LocalImageForm;
import com.cardpay.pccredit.jnpad.dao.JnpadImageBrowseDao;
import com.cardpay.pccredit.jnpad.model.FwqUtils;
import com.cardpay.pccredit.jnpad.model.JNPAD_SFTPUtil;
import com.cardpay.pccredit.jnpad.model.JNPAD_UploadFileTool;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.SftpException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;

@Service
public class JnpadImageBrowseService {

	@Autowired
	private JnpadImageBrowseDao jnpadImageBrowseDao;
	
	@Autowired
	private CommonDao commonDao;
	
	
	
	public List<LocalImageForm> findLocalImage(String customerId,String productId ) {
		// TODO Auto-generated method stub
		return jnpadImageBrowseDao.findLocalImage(customerId,productId);
	}
	
	public List<LocalImageForm> findLocalImageByType1(String customerId,
			 String productId, String phone_type) throws IOException, SftpException{
		return jnpadImageBrowseDao.findLocalImageByType(customerId,productId,phone_type);
		
	
	}
	public List<LocalImageForm> findLocalImageByType(String customerId,
			 String productId, String phone_type) throws IOException, SftpException{
		List<LocalImageForm> result= jnpadImageBrowseDao.findLocalImageByType(customerId,productId,phone_type);
		List<LocalImageForm> list=null;
		Map<String, String> map=null;
		if(FwqUtils.typeCode==0){
			//本地
			list=SFTPUtil.TestImageBinary1(result);
		}else{
			//服务器
			list=JNPAD_SFTPUtil.TestImageBinary(result);
		}
	
		return list;
	}
	

	
	
	public void downLoadYxzlJn(HttpServletResponse response,String id) throws Exception{
		LocalImage v = commonDao.findObjectById(LocalImage.class, id);
		if(v!=null){
			if(FwqUtils.typeCode==0){
				//本地
				this.downLoadFile(response,v);
			}else{
				//服务器
				JNPAD_SFTPUtil.downloadjn(response,v.getUri(), v.getAttachment());
			}
		}
	}
	
	
	
	public void downLoadFile(HttpServletResponse response,LocalImage v)throws Exception{
		String GIF = "image/gif;charset=GB2312";// 设定输出的类型
		String JPG = "image/jpeg;charset=GB2312";
		String BMP = "image/bmp";
	    String PNG = "image/png";
	    
		String imagePath = v.getUri();
		OutputStream output = response.getOutputStream();// 得到输出流
		if (imagePath.toLowerCase().endsWith(".jpg"))// 使用编码处理文件流的情况：
		{
			response.setContentType(JPG);// 设定输出的类型
			// 得到图片的真实路径
	
			// 得到图片的文件流
			InputStream imageIn = new FileInputStream(new File(imagePath));
	
			// 得到输入的编码器，将文件流进行jpg格式编码
			JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(imageIn);
			// 得到编码后的图片对象
			BufferedImage image = decoder.decodeAsBufferedImage();
			System.out.println(image);
			// 得到输出的编码器
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(output);
			encoder.encode(image);// 对图片进行输出编码
			imageIn.close();// 关闭文件流
		} 

		else if (imagePath.toLowerCase().endsWith(".png")
				|| imagePath.toLowerCase().endsWith(".bmp")) {
			
			BufferedImage bi = ImageIO.read(new File(imagePath));
			
			if(imagePath.toLowerCase().endsWith(".png")){
				response.setContentType(PNG);
				ImageIO.write(bi, "png", response.getOutputStream());
			}else{
				response.setContentType(BMP);
				ImageIO.write(bi, "bmp", response.getOutputStream());
			}
			
		}
		output.close();
	}




	public void deleteImage(String imageId) {
		
		jnpadImageBrowseDao.deleteImage(imageId);
	}
	
	
	
	
	
	
	
}
