package com.cardpay.pccredit.riskControl.service;

import java.io.File;
import java.net.URL;
import java.util.Date;

import org.apache.log4j.Logger;

import com.cardpay.pccredit.riskControl.constant.CheckResultEnum;
import com.cardpay.pccredit.riskControl.xml.XmlUtil;
import com.cardpay.pccredit.riskControl.xml.singlerequest.SingleRequestCfx;
import com.cardpay.pccredit.riskControl.xml.singleresult.SingleResultCfx;
import com.wicresoft.util.date.DateHelper;

/**
 * 获取“联网核查公民身份信息系统”信息
 * Networking verification citizen identity information system
 * @author chenzhifang
 *
 * 2014-11-4上午11:07:34
 */
public class VerificationCitizenIdentityService {
	private Logger log = Logger.getLogger(VerificationCitizenIdentityService.class);
	
	// 请求xml校验文件URL
	public static final URL requestXsdFileURL = XmlUtil.class.getResource("singlerequest/0001.xsd");
	// 回执xml校验文件URL
	public static final URL resultXsdFileURL = XmlUtil.class.getResource("singleresult/0002.xsd");
	
	/*
	 * 验证并处理公民身份信息
	 * @param id 身份证号
	 * @param name 姓名
	 * @return
	 */
	public void processAndVerificationId(String id, String name){
		CheckResultEnum checkResult = verificationCitizenIdentity(id, name);
		// 公民身份号码与姓名一致，但不存在照片
		if(CheckResultEnum.ConformityNoPhoto.equals(checkResult)){
			
		// 公民身份号码存在，但与姓名不匹配
		}else if(CheckResultEnum.IdExistNameNotMatch.equals(checkResult)){
			
		// 公民身份号码不存在
		}else if(CheckResultEnum.IdNonexistence.equals(checkResult)){
			
		}
	}
	
	/*
	 * 单笔核对公民身份信息
	 * @param id 身份证号
	 * @param name 姓名
	 * @return
	 */
	public CheckResultEnum verificationCitizenIdentity(String id, String name){
		SingleRequestCfx requestCfx = new SingleRequestCfx();
		requestCfx.initHead();
		// 核对机构代码
		requestCfx.getMsg().getHead().setBankCode("100000000000");
		// 设置发送时间
		requestCfx.getMsg().getHead().setEntrustDate(DateHelper.getDateFormat(new Date(), "yyyyMMddhhmmss"));
		// 业务种类
		requestCfx.getMsg().getHead().setBusinessCode("01");
		// 操作用户
		requestCfx.getMsg().getHead().setUserCode("0000000000000000000000000000000000000001");
		// 设置身份证号
		requestCfx.getMsg().getMessage().setId(id);
		// 设置姓名
		requestCfx.getMsg().getMessage().setName(name);
		
		// 把Java对象转换xml字符串
		String xmlStr = XmlUtil.toXml(requestCfx);
		log.debug(xmlStr);
		
		CheckResultEnum checkResult = CheckResultEnum.OtherError;
		
		// 发送单笔核对公民身份信息
		String returnXmlStr = sendVerificationRequest(xmlStr);
		// 验证回执xml是否正确
		boolean isValidate = XmlUtil.validateXMLByXSD(returnXmlStr, resultXsdFileURL.getPath());
		if(isValidate){
			// 把xml字符串转换成java对象
			SingleResultCfx cfx = XmlUtil.toBean(returnXmlStr, SingleResultCfx.class);
			// 获取回执结果代码
			String resultCode = cfx.getMsg().getMessage().getCheckResult();
			checkResult = CheckResultEnum.getCheckResultEnumByCode(resultCode);
		}
		return checkResult;
	}
	
	/*
	 * 发送单笔核对公民身份信息
	 * @param xmlStr 
	 * @return String
	 */
	public String sendVerificationRequest(String xmlStr){
		// 构造个假的xml返回
		URL url = XmlUtil.class.getResource("singleresult/0002.xml");
		File xmlFile = new File(url.getPath());
		return XmlUtil.parseXMLFileToString(xmlFile);
	}
	
}
