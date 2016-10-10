package com.cardpay.pbccrcReport;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;

import com.cardpay.pbccrcReport.util.PropertyUtil;
import com.cardpay.pbccrcReport.util.ReporterUtil;

/**   
 * @Title: PbccrcReport.java 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author 谭文华   
 * @date 2014年12月8日 上午10:56:51
*/
public class PbccrcReport {

	//private static final String cLoginSuccess = "欢迎 汇丰银行";
	//修改江南农村商业银行登录人民银行的欢迎语 updated by jinping.chen 20140423
	private static final String cLoginSuccess = "欢迎 江苏江南农村商业银行股份有限公司";
	private static final String cLogonPageInfo = "请输入用户ID和密码登录系统";
	private static final String cDownloadedPageSuccessFlag1 = "个人信用报告";
	private static final String cDownloadedPageSuccessFlag2 = "信用提示";
	private static final String cDownloadedPageSuccessFlag3 = "报告结束";
	private static final String cNoRecordFlag = "查无此人"; // 无法在PBOC数据库中查找到此人信息
	private static final String cNoRecordFlag1 = "个人征信系统中没有此人的征信记录"; // 无法在PBOC数据库中查找到此人信息
	private static final String cSessionTimeOut = "会话超时"; // "会话超时,请从新登陆"
	private static final String hasReportNo = "报告编号"; // "是否有报告编号"
	private static final String hasQueryName = "被查询者姓名"; // "被查询者姓名"
	private static final String hasQueryNo = "被查询者证件号码"; // "会话超时,请从新登陆"
	
	private static final Logger logger = Logger.getLogger(PbccrcReport.class);
	
	private static boolean pbocLogin(HttpClient httpClient) throws Exception {
		NameValuePair ie = new NameValuePair("User-Agent",
				"Mozilla/4.0 (compatible; MSIE 6.0; Windows 2000)");
		boolean loginFlag = false;
		PropertyUtil.reLoad();
		NameValuePair username = new NameValuePair("userid", PropertyUtil.getPropertyByKey("userid"));
		NameValuePair password = new NameValuePair("password", PropertyUtil.getPropertyByKey("passwd"));
		String loginUrl = PropertyUtil.getPropertyByKey("pbocloginUrl");

		PostMethod method = new PostMethod(loginUrl);
		method.setRequestBody(new NameValuePair[] { ie, username, password });
		Cookie[] cookies = httpClient.getState().getCookies();
		httpClient.getState().addCookies(cookies);
		int statusCode = httpClient.executeMethod(method);
		//method.releaseConnection();
		String buffer = "";
		//新增日志 updated by jinping.chen 2014-04-23
		logger.info("登陆 statusCode" + statusCode);
			
		if ( statusCode == HttpStatus.SC_OK){
			 /**排错*/
			 byte[] resp = method.getResponseBody();
		     buffer = new String(resp, "GBK");
		     //logger.info(buffer);
		}
			
		if (checkLoginIsSucc(buffer.toString())) {
			loginFlag = true;
		} else {
//			OperationContext context = new OperationContext();
//			context.setAttribute(CreditReporterOperation.CMD,
//					CreditReporterOperation.UPDATE_PBOC_CREDIT_USER_INFO);
//			context.setAttribute(CreditReporterOperation.CREDIT_USER_NAME,
//					logininfo[0]);// 设置征信用户变量
//			SingleOPCaller.call(CreditReporterOperation.ID, context);// 将征信用户置为无效
		}
		method.releaseConnection();

		logger.info("登录成功???------------>" + loginFlag);
		
		return loginFlag;
	}
	
	public String manuProcessPbocCreditInfo(String customerName,String creditType,String creditNo)
			throws Exception {
		// 查询待征信人行信息
		String rtnstr = "";

		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setCookiePolicy(
				CookiePolicy.BROWSER_COMPATIBILITY);
		if (pbocLogin(httpClient)) {
			/*
			 * 储存cookies
			 * 
			 * **/
			Cookie[] cookies = httpClient.getState().getCookies();
			
			String tempcookies ="";
			for(Cookie c :cookies){
				tempcookies+=c.toString()+";";	
			}
			// 待征信用户名
			NameValuePair username = new NameValuePair("username",customerName);
			// 证件类型
			NameValuePair certype = new NameValuePair("certype","1");//身份证 写死1
			// 证件号码
			NameValuePair cercode = new NameValuePair("cercode", creditNo.toUpperCase());
			NameValuePair queryreason = new NameValuePair("queryreason", "03");
			NameValuePair vertype = new NameValuePair("vertype", "20");
			NameValuePair idauthflag = new NameValuePair("policetype", "0");
			String pbocReportURL = PropertyUtil.getPropertyByKey("pbocReportURL");
			PostMethod postMethod = new PostMethod(pbocReportURL);
			postMethod.addRequestHeader("Content-Type",
					"application/x-www-form-urlencoded;charset=GBK");
			//带上前面的Cookie作为通行证
			postMethod.setRequestHeader("cookie",tempcookies);
			postMethod.setRequestBody(new NameValuePair[] { username, certype,
					cercode, queryreason, vertype, idauthflag });
			int status = httpClient.executeMethod(postMethod);
			
			logger.info("status---->:" + status);
			if ((status == HttpStatus.SC_MOVED_TEMPORARILY)
					|| (status == HttpStatus.SC_MOVED_PERMANENTLY)
					|| (status == HttpStatus.SC_SEE_OTHER)
					|| (status == HttpStatus.SC_TEMPORARY_REDIRECT
							|| (status == HttpStatus.SC_OK))) {
				byte[] responseByte = postMethod.getResponseBody();
				
				String buffer = new String(responseByte,"GBK");
				//logger.info("********************buffer***************:"+buffer);
				postMethod.releaseConnection();
				rtnstr = checkPBOCPageOK(buffer);  //征信结果
				logger.info("********************rtnstr***************:"+rtnstr);
				if (!rtnstr.equals(Constants.PBOC_REQUEST_RESULT_2)) {
					if (rtnstr.equals(Constants.PBOC_REQUEST_RESULT_1)) {
						logger.info("PBOC信息不存在");
						ReporterUtil.createReporterFile(buffer.getBytes(), customerName+"_"+creditNo, "PBOC");
					} else if (rtnstr.equals(Constants.PBOC_REQUEST_RESULT_7)) {
						logger.info("PBOC信息不存在但给出征信报告");
						PbocFileReader.ParserReporter(
								ReporterUtil.createReporterFile(buffer.getBytes(), customerName+"_"+creditNo, "PBOC"));
					} else if (rtnstr.equals(Constants.PBOC_REQUEST_RESULT_0)){//分开处理，页面信息有误的情况不生成报告。
						//logger.info("PBOC信息存在或页面信息有误");
						logger.info("PBOC信息真实存在给出报告");
						PbocFileReader
								.ParserReporter(
										ReporterUtil.createReporterFile(buffer.getBytes(), customerName+"_"+creditNo,
												"PBOC"));
					} else {
						logger.info("PBOC页面信息有误无报告");
					}
				}else{
					logger.info("人行征信会话超时。");
				}
			}
			
			//loginIndex = 0;  loginIndex不需要重新定义 
			return rtnstr;
		} else {
			//loginIndex = 0;   loginIndex不需要重新定义
			return Constants.PBOC_REQUEST_RESULT_4;
		}
		/*
		 * updated by jian.gong 不需要退出登录 finally{ PostMethod postMethod = new
		 * PostMethod(pboclogoutUrl); httpClient.executeMethod(postMethod); }
		 */

	}

	/**
	 * 判断登录人行网站是否成功
	 * 
	 * @return 登录标志
	 */
	private static boolean checkLoginIsSucc(String responseStr) {
		boolean loginFlag = true;
		try {
			int loginResultFlag = responseStr.indexOf(cLoginSuccess);
			int logonPageInfoFlag = responseStr.indexOf(cLogonPageInfo);

			if (loginResultFlag > 0 && logonPageInfoFlag == -1) {
				loginFlag = true;
			} else {
				loginFlag = false;
			}
		} catch (Exception e) {
			loginFlag = false;
			System.err.println("判断是否已经登录人行网站异常");
		}

		return loginFlag;
	}

	/**
	 * 检查征信内容是否完整
	 * @param responseInfo
	 * @return
	 */
	private static String checkPBOCPageOK(String responseInfo) {
		String rtnFlag = Constants.PBOC_REQUEST_RESULT_0;

		int textFlag1 = responseInfo.indexOf(cDownloadedPageSuccessFlag1);
		int textFlag2 = responseInfo.indexOf(cDownloadedPageSuccessFlag2);
		int textFlag3 = responseInfo.indexOf(cDownloadedPageSuccessFlag3);
		int textNoRecord = Math.max(responseInfo.indexOf(cNoRecordFlag),
				responseInfo.indexOf(cNoRecordFlag1));
		int sessionTimeOutFlag = responseInfo.indexOf(cSessionTimeOut);
		int textFlag4 = responseInfo.indexOf(hasReportNo);
		int textFlag5 = responseInfo.indexOf(hasQueryName);
		int textFlag6 = responseInfo.indexOf(hasQueryNo);
//		logger.info("textFlag1:" + textFlag1);
//		logger.info("textFlag2:" + textFlag2);
//		logger.info("textFlag3:" + textFlag3);
//		logger.info("textNoRecord:" + textNoRecord);
//		logger.info("sessionTimeOutFlag:" + sessionTimeOutFlag);
//		logger.info("textFlag4:" + textFlag4);
//		logger.info("textFlag5:" + textFlag5);
//		logger.info("textFlag6:" + textFlag6);
		if (textFlag1 > 0 && textFlag2 > 0 && textFlag3 > 0
				&& textNoRecord == -1 && sessionTimeOutFlag == -1) {
			// PBOC信息真实存在
			rtnFlag = Constants.PBOC_REQUEST_RESULT_0;
		} else {
			if (textFlag4 > 0 && textFlag5 > 0 && textFlag6 > 0
					&& sessionTimeOutFlag == -1) {
				// PBOC信息不存在但给出征信报告
				rtnFlag = Constants.PBOC_REQUEST_RESULT_7;
			} else if (textNoRecord > 0 && sessionTimeOutFlag == -1) {
				// PBOC信息不存在
				rtnFlag = Constants.PBOC_REQUEST_RESULT_1;
			} else {
				if (sessionTimeOutFlag > 0) {
					// 会话超时
					rtnFlag = Constants.PBOC_REQUEST_RESULT_2;
				} else {
					// PBOC页面信息有误
					rtnFlag = Constants.PBOC_REQUEST_RESULT_3;
				}
			}
		}

		return rtnFlag;
	}

}
