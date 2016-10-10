package com.cardpay.pbccrcReport.pojo;

import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 
 * @author chenzhifang
 *
 * 2014-12-24下午3:51:30
 */
@ModelParam(table = "RH_WXHDJK_INFO")
public class WXHDJK_Info extends AbstractCustomerInfo {
	private static final long serialVersionUID = 1L;
	private String institutionOrgNum;//发卡法人机构数
	private String orgNum;//发卡机构数
	private String accountNum;//账户数
	private String awardingTotal;//授信总额
	private String maxAwarding;//单家行最高授信额
	private String minAwarding;//单家行最低授信额
	private String used;//已用额度
	private String avgUsed;//最近6个月平均使用额度
	
	public String getInstitutionOrgNum() {
		return institutionOrgNum;
	}
	public void setInstitutionOrgNum(String institutionOrgNum) {
		this.institutionOrgNum = institutionOrgNum;
	}
	public String getOrgNum() {
		return orgNum;
	}
	public void setOrgNum(String orgNum) {
		this.orgNum = orgNum;
	}
	public String getAccountNum() {
		return accountNum;
	}
	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}
	public String getAwardingTotal() {
		return awardingTotal;
	}
	public void setAwardingTotal(String awardingTotal) {
		this.awardingTotal = awardingTotal;
	}
	public String getMaxAwarding() {
		return maxAwarding;
	}
	public void setMaxAwarding(String maxAwarding) {
		this.maxAwarding = maxAwarding;
	}
	public String getMinAwarding() {
		return minAwarding;
	}
	public void setMinAwarding(String minAwarding) {
		this.minAwarding = minAwarding;
	}
	public String getUsed() {
		return used;
	}
	public void setUsed(String used) {
		this.used = used;
	}
	public String getAvgUsed() {
		return avgUsed;
	}
	public void setAvgUsed(String avgUsed) {
		this.avgUsed = avgUsed;
	}
}
