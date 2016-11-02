package com.cardpay.pccredit.riskControl.xml.singleresult;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author chenzhifang
 *
 * 2014-10-30下午6:09:22
 */
public class SingleCheckResultMessage0002 {
	// 身份证号
	@XStreamAlias("ID")
	private String id;
	
	// 姓名
	@XStreamAlias("Name")
	private String name;
	
	// 回执结果
	@XStreamAlias("CheckResult")
	private String checkResult;
	
	// 签发机关
	@XStreamAlias("IssueOffice")
	private String issueOffice;
	
	// 照片
	@XStreamAlias("Photo")
	private String photo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}

	public String getIssueOffice() {
		return issueOffice;
	}

	public void setIssueOffice(String issueOffice) {
		this.issueOffice = issueOffice;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
}
