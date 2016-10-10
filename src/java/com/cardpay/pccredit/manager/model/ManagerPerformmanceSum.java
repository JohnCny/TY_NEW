package com.cardpay.pccredit.manager.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

@ModelParam(table = "manager_performmance_sum")
public class ManagerPerformmanceSum extends BusinessModel {

	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String manager_id_s;
	  private int visitcount_s;
	  private int applycount_s;
	  private int applyrefuse_s;
	  private int creditcount_s;
	  private int creditrefuse_s;
	  private int realycount_s;
	  private int reportcount_s;
	  private int internalcount_s;
	  private int meetingcout_s;
	  private int passcount_s;
	  private int signcount_s;
	  private int givemoneycount_s;
	  private Date crateday_s;
	  
	  
	public String getManager_id_s() {
		return manager_id_s;
	}
	public void setManager_id_s(String manager_id_s) {
		this.manager_id_s = manager_id_s;
	}
	public int getVisitcount_s() {
		return visitcount_s;
	}
	public void setVisitcount_s(int visitcount_s) {
		this.visitcount_s = visitcount_s;
	}
	public int getApplycount_s() {
		return applycount_s;
	}
	public void setApplycount_s(int applycount_s) {
		this.applycount_s = applycount_s;
	}
	public int getApplyrefuse_s() {
		return applyrefuse_s;
	}
	public void setApplyrefuse_s(int applyrefuse_s) {
		this.applyrefuse_s = applyrefuse_s;
	}
	public int getCreditcount_s() {
		return creditcount_s;
	}
	public void setCreditcount_s(int creditcount_s) {
		this.creditcount_s = creditcount_s;
	}
	public int getCreditrefuse_s() {
		return creditrefuse_s;
	}
	public void setCreditrefuse_s(int creditrefuse_s) {
		this.creditrefuse_s = creditrefuse_s;
	}
	public int getRealycount_s() {
		return realycount_s;
	}
	public void setRealycount_s(int realycount_s) {
		this.realycount_s = realycount_s;
	}
	public int getReportcount_s() {
		return reportcount_s;
	}
	public void setReportcount_s(int reportcount_s) {
		this.reportcount_s = reportcount_s;
	}
	public int getInternalcount_s() {
		return internalcount_s;
	}
	public void setInternalcount_s(int internalcount_s) {
		this.internalcount_s = internalcount_s;
	}
	public int getMeetingcout_s() {
		return meetingcout_s;
	}
	public void setMeetingcout_s(int meetingcout_s) {
		this.meetingcout_s = meetingcout_s;
	}
	public int getPasscount_s() {
		return passcount_s;
	}
	public void setPasscount_s(int passcount_s) {
		this.passcount_s = passcount_s;
	}
	public int getSigncount_s() {
		return signcount_s;
	}
	public void setSigncount_s(int signcount_s) {
		this.signcount_s = signcount_s;
	}
	public int getGivemoneycount_s() {
		return givemoneycount_s;
	}
	public void setGivemoneycount_s(int givemoneycount_s) {
		this.givemoneycount_s = givemoneycount_s;
	}
	public Date getCrateday_s() {
		return crateday_s;
	}
	public void setCrateday_s(Date crateday_s) {
		this.crateday_s = crateday_s;
	}
	
	
	
}
