package com.cardpay.pccredit.intopieces.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

@ModelParam(table = "income_statement")
public class IncomeStatement extends BusinessModel{
    /**
	 * 
	 */
	private static final long serialVersionUID = -7692935106837282397L;

    private Long loanApplyId;

    private Long type;

    private String name;

    private Long sameCategoryNumber;

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

    private String totalAll;

    private String monthlyAverage;

    private Long founder;

    private Date creationTime;

    private Long modifyPeople;

    private Date modifyTime;

    public Long getLoanApplyId() {
        return loanApplyId;
    }

    public void setLoanApplyId(Long loanApplyId) {
        this.loanApplyId = loanApplyId;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getSameCategoryNumber() {
        return sameCategoryNumber;
    }

    public void setSameCategoryNumber(Long sameCategoryNumber) {
        this.sameCategoryNumber = sameCategoryNumber;
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

    public String getTotalAll() {
        return totalAll;
    }

    public void setTotalAll(String totalAll) {
        this.totalAll = totalAll == null ? null : totalAll.trim();
    }

    public String getMonthlyAverage() {
        return monthlyAverage;
    }

    public void setMonthlyAverage(String monthlyAverage) {
        this.monthlyAverage = monthlyAverage == null ? null : monthlyAverage.trim();
    }

    public Long getFounder() {
        return founder;
    }

    public void setFounder(Long founder) {
        this.founder = founder;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Long getModifyPeople() {
        return modifyPeople;
    }

    public void setModifyPeople(Long modifyPeople) {
        this.modifyPeople = modifyPeople;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}