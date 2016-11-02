package com.cardpay.pccredit.intopieces.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

@ModelParam(table = "additional_cash_flow")
public class AdditionalCashFlow extends BusinessModel{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    private Long loanApplyId;

    private String businessType;

    private String dataType;

    private String costName;

    private String month;

    private String january;

    private String february;

    private String march;

    private String april;

    private String may;

    private String june;

    private String july;

    private String august;

    private String september;

    private String october;

    private String november;

    private String december;

    public Long getLoanApplyId() {
        return loanApplyId;
    }

    public void setLoanApplyId(Long loanApplyId) {
        this.loanApplyId = loanApplyId;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType == null ? null : businessType.trim();
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType == null ? null : dataType.trim();
    }

    public String getCostName() {
        return costName;
    }

    public void setCostName(String costName) {
        this.costName = costName == null ? null : costName.trim();
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month == null ? null : month.trim();
    }

    public String getJanuary() {
        return january;
    }

    public void setJanuary(String january) {
        this.january = january == null ? null : january.trim();
    }

    public String getFebruary() {
        return february;
    }

    public void setFebruary(String february) {
        this.february = february == null ? null : february.trim();
    }

    public String getMarch() {
        return march;
    }

    public void setMarch(String march) {
        this.march = march == null ? null : march.trim();
    }

    public String getApril() {
        return april;
    }

    public void setApril(String april) {
        this.april = april == null ? null : april.trim();
    }

    public String getMay() {
        return may;
    }

    public void setMay(String may) {
        this.may = may == null ? null : may.trim();
    }

    public String getJune() {
        return june;
    }

    public void setJune(String june) {
        this.june = june == null ? null : june.trim();
    }

    public String getJuly() {
        return july;
    }

    public void setJuly(String july) {
        this.july = july == null ? null : july.trim();
    }

    public String getAugust() {
        return august;
    }

    public void setAugust(String august) {
        this.august = august == null ? null : august.trim();
    }

    public String getSeptember() {
        return september;
    }

    public void setSeptember(String september) {
        this.september = september == null ? null : september.trim();
    }

    public String getOctober() {
        return october;
    }

    public void setOctober(String october) {
        this.october = october == null ? null : october.trim();
    }

    public String getNovember() {
        return november;
    }

    public void setNovember(String november) {
        this.november = november == null ? null : november.trim();
    }

    public String getDecember() {
        return december;
    }

    public void setDecember(String december) {
        this.december = december == null ? null : december.trim();
    }
}