package com.cardpay.pbccrcReport.pojo;

import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 
 * @author chenzhifang
 *
 * 2014-12-24下午3:51:30
 */
@ModelParam(table = "RH_YH_INFO")
public class YH_Info extends AbstractCustomerInfo {
	private static final long serialVersionUID = -6134254114800999200L;
	private String userName;//被查询者姓名
	private String creditType;//被查询者证件类型
	private String creditNO;//被查询者证件号码
	private String queryOperator;//查询操作员
	private String queryReason;//查询原因
	private String sex;//性别
	private String birth;//出生日期
	private String marriage;//婚姻状况
	private String cellPhone;//手机号码
	private String workPhone;//单位电话
	private String homePhone;//住宅电话
	private String education;//学历
	private String degree;//学位
	private String mailingAddress;//通讯地址
	private String residenceAddress;//户籍地址
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCreditType() {
		return creditType;
	}
	public void setCreditType(String creditType) {
		this.creditType = creditType;
	}
	public String getCreditNO() {
		return creditNO;
	}
	public void setCreditNO(String creditNO) {
		this.creditNO = creditNO;
	}
	public String getQueryOperator() {
		return queryOperator;
	}
	public void setQueryOperator(String queryOperator) {
		this.queryOperator = queryOperator;
	}
	public String getQueryReason() {
		return queryReason;
	}
	public void setQueryReason(String queryReason) {
		this.queryReason = queryReason;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getMarriage() {
		return marriage;
	}
	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}
	public String getCellPhone() {
		return cellPhone;
	}
	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}
	public String getWorkPhone() {
		return workPhone;
	}
	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}
	public String getHomePhone() {
		return homePhone;
	}
	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getMailingAddress() {
		return mailingAddress;
	}
	public void setMailingAddress(String mailingAddress) {
		this.mailingAddress = mailingAddress;
	}
	public String getResidenceAddress() {
		return residenceAddress;
	}
	public void setResidenceAddress(String residenceAddress) {
		this.residenceAddress = residenceAddress;
	}
}
