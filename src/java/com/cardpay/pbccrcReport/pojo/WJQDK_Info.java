package com.cardpay.pbccrcReport.pojo;

import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 未结清贷款信息汇总
 * @author chenzhifang
 *
 * 2014-12-24下午3:51:30
 */
@ModelParam(table = "RH_WJQDK_INFO")
public class WJQDK_Info extends AbstractCustomerInfo {
	private static final long serialVersionUID = 1L;
	private String legalInstitutionOrgNum;//贷款法人机构数
	private String legalOrgNum;//贷款机构数
	private String num;//笔数
	private String amountTotal;//合同总额
	private String remaining;//余额
	private String avgPer6month;//最近6个月平均应还款
	
	public String getLegalInstitutionOrgNum() {
		return legalInstitutionOrgNum;
	}
	public void setLegalInstitutionOrgNum(String legalInstitutionOrgNum) {
		this.legalInstitutionOrgNum = legalInstitutionOrgNum;
	}
	public String getLegalOrgNum() {
		return legalOrgNum;
	}
	public void setLegalOrgNum(String legalOrgNum) {
		this.legalOrgNum = legalOrgNum;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getAmountTotal() {
		return amountTotal;
	}
	public void setAmountTotal(String amountTotal) {
		this.amountTotal = amountTotal;
	}
	public String getRemaining() {
		return remaining;
	}
	public void setRemaining(String remaining) {
		this.remaining = remaining;
	}
	public String getAvgPer6month() {
		return avgPer6month;
	}
	public void setAvgPer6month(String avgPer6month) {
		this.avgPer6month = avgPer6month;
	}
}
