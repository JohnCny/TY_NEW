package com.cardpay.pccredit.manager.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

@ModelParam(table = "manager_performmance")
public class ManagerPerformmance extends BusinessModel{
	
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	  private String manager_id;
	  private int visitcount;
	  private int applycount;
	  private int applyrefuse;
	  private int creditcount;
	  private int creditrefuse;
	  private int realycount;
	  private int reportcount;
	  private int internalcount;
	  private int meetingcout;
	  private int passcount;
	  private int signcount;
	  private int givemoneycount;
	  private Date crateday;
	  

	  
	
	public Date getCrateday() {
		return crateday;
	}
	public void setCrateday(Date crateday) {
		this.crateday = crateday;
	}
	public String getManager_id() {
		return manager_id;
	}
	public void setManager_id(String manager_id) {
		this.manager_id = manager_id;
	}
	public int getVisitcount() {
		return visitcount;
	}
	public void setVisitcount(int visitcount) {
		this.visitcount = visitcount;
	}
	public int getApplycount() {
		return applycount;
	}
	public void setApplycount(int applycount) {
		this.applycount = applycount;
	}
	public int getApplyrefuse() {
		return applyrefuse;
	}
	public void setApplyrefuse(int applyrefuse) {
		this.applyrefuse = applyrefuse;
	}
	public int getCreditcount() {
		return creditcount;
	}
	public void setCreditcount(int creditcount) {
		this.creditcount = creditcount;
	}
	public int getCreditrefuse() {
		return creditrefuse;
	}
	public void setCreditrefuse(int creditrefuse) {
		this.creditrefuse = creditrefuse;
	}
	public int getRealycount() {
		return realycount;
	}
	public void setRealycount(int realycount) {
		this.realycount = realycount;
	}
	public int getReportcount() {
		return reportcount;
	}
	public void setReportcount(int reportcount) {
		this.reportcount = reportcount;
	}
	public int getInternalcount() {
		return internalcount;
	}
	public void setInternalcount(int internalcount) {
		this.internalcount = internalcount;
	}
	public int getMeetingcout() {
		return meetingcout;
	}
	public void setMeetingcout(int meetingcout) {
		this.meetingcout = meetingcout;
	}
	public int getPasscount() {
		return passcount;
	}
	public void setPasscount(int passcount) {
		this.passcount = passcount;
	}
	public int getSigncount() {
		return signcount;
	}
	public void setSigncount(int signcount) {
		this.signcount = signcount;
	}
	public int getGivemoneycount() {
		return givemoneycount;
	}
	public void setGivemoneycount(int givemoneycount) {
		this.givemoneycount = givemoneycount;
	}
	

}
