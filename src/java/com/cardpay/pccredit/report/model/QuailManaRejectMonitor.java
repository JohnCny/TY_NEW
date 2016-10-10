package com.cardpay.pccredit.report.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;

/**
 * manaProceMonitor类的描述
 * 
 * @author 王海东
 * @created on 2014-12-19
 * 
 * @version $Id:$
 */
public class QuailManaRejectMonitor extends BusinessModel {

	private static final long serialVersionUID = 1L;

	private String displayName; // 客户经理名称
	private String rowindex; // 列号
	// 被拒绝的进件
	private String rejectcount;
	private String percent;
	private String jinjian;
	
	
	public String getJinjian() {
		return jinjian;
	}

	public void setJinjian(String jinjian) {
		this.jinjian = jinjian;
	}
	public String getPercent() {
		return percent;
	}
	public void setPercent(String percent) {
		this.percent = percent;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getRowindex() {
		return rowindex;
	}
	public void setRowindex(String rowindex) {
		this.rowindex = rowindex;
	}
	public String getRejectcount() {
		return rejectcount;
	}
	public void setRejectcount(String rejectcount) {
		this.rejectcount = rejectcount;
	}

	

}
