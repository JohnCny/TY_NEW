package com.cardpay.pccredit.manager.model;

import com.wicresoft.jrad.base.database.id.IDType;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/*
 * 每月客户经理获得指标
 */
@ModelParam(table = "manager_salary_parameter",generator=IDType.uuid32)
public class ManagerSalaryParameter extends BusinessModel{
	
	private static final long serialVersionUID = 1L;
	private String month;//统计月份
	private String userId	;//客户经理id
	private String zdCount;//主调笔数	
	private String fdCount;//辅调笔数	
	private String spCount;//审批笔数	
	private String tubes;//管户数	
	private String competerCount;//完成笔数
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getZdCount() {
		return zdCount;
	}
	public void setZdCount(String zdCount) {
		this.zdCount = zdCount;
	}
	public String getFdCount() {
		return fdCount;
	}
	public void setFdCount(String fdCount) {
		this.fdCount = fdCount;
	}
	public String getSpCount() {
		return spCount;
	}
	public void setSpCount(String spCount) {
		this.spCount = spCount;
	}
	public String getTubes() {
		return tubes;
	}
	public void setTubes(String tubes) {
		this.tubes = tubes;
	}
	public String getCompeterCount() {
		return competerCount;
	}
	public void setCompeterCount(String competerCount) {
		this.competerCount = competerCount;
	}

}
