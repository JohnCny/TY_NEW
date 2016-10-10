package com.cardpay.pbccrcReport.pojo;

import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 查询记录汇总
 * @author chenzhifang
 *
 * 2014-12-24下午3:51:30
 */
@ModelParam(table = "RH_QUERY_INFO")
public class Query_Info extends AbstractCustomerInfo{
	private static final long serialVersionUID = 1L;
	private String loanApproval1;//贷款审批
	private String creitApproval1;//信用卡审批
	private String loanApproval2;//贷款审批
	private String creitApproval2;//信用卡审批
	private String loanManagement;//贷后管理
	private String guaranteeExamination ;//担保资格审查
	private String realnameReview;//特约商户实名审查
	
	public String getLoanApproval1() {
		return loanApproval1;
	}
	public void setLoanApproval1(String loanApproval1) {
		this.loanApproval1 = loanApproval1;
	}
	public String getCreitApproval1() {
		return creitApproval1;
	}
	public void setCreitApproval1(String creitApproval1) {
		this.creitApproval1 = creitApproval1;
	}
	public String getLoanApproval2() {
		return loanApproval2;
	}
	public void setLoanApproval2(String loanApproval2) {
		this.loanApproval2 = loanApproval2;
	}
	public String getCreitApproval2() {
		return creitApproval2;
	}
	public void setCreitApproval2(String creitApproval2) {
		this.creitApproval2 = creitApproval2;
	}
	public String getLoanManagement() {
		return loanManagement;
	}
	public void setLoanManagement(String loanManagement) {
		this.loanManagement = loanManagement;
	}
	public String getGuaranteeExamination() {
		return guaranteeExamination;
	}
	public void setGuaranteeExamination(String guaranteeExamination) {
		this.guaranteeExamination = guaranteeExamination;
	}
	public String getRealnameReview() {
		return realnameReview;
	}
	public void setRealnameReview(String realnameReview) {
		this.realnameReview = realnameReview;
	}
}
