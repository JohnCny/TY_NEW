package com.cardpay.pbccrcReport.pojo;

import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 职业信息
 * @author chenzhifang
 *
 * 2014-12-24下午3:51:30
 */
@ModelParam(table = "RH_ZY_INFO")
public class ZY_Info extends AbstractCustomerInfo {
	private static final long serialVersionUID = 1L;
	private String index_;//编号
	private String workUnit;//工作单位
	private String workAddress;//单位地址
	private String job;//职业
	private String industry;//行业
	private String duty;//职务
	private String headship;//职称
	private String joinTime;//进入本单位年份
	private String updateDate;//信息更新日期
	
	
	public String getIndex_() {
		return index_;
	}
	public void setIndex_(String index_) {
		this.index_ = index_;
	}
	public String getWorkUnit() {
		return workUnit;
	}
	public void setWorkUnit(String workUnit) {
		this.workUnit = workUnit;
	}
	public String getWorkAddress() {
		return workAddress;
	}
	public void setWorkAddress(String workAddress) {
		this.workAddress = workAddress;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getDuty() {
		return duty;
	}
	public void setDuty(String duty) {
		this.duty = duty;
	}
	public String getHeadship() {
		return headship;
	}
	public void setHeadship(String headship) {
		this.headship = headship;
	}
	public String getJoinTime() {
		return joinTime;
	}
	public void setJoinTime(String joinTime) {
		this.joinTime = joinTime;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
}
