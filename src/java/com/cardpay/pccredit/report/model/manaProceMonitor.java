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
public class manaProceMonitor extends BusinessModel {

	private static final long serialVersionUID = 1L;

	private String bank;
	private String displayName;
	private String rowindex;
	private String jinjian;
	private String chushen;
	private String luru;
	private String fushen;
	private String chushenapprove;
	private String luruapprove;
	private String fushenapprove;

	public String getRowindex() {
		return rowindex;
	}

	public void setRowindex(String rowindex) {
		this.rowindex = rowindex;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getJinjian() {
		return jinjian;
	}

	public void setJinjian(String jinjian) {
		this.jinjian = jinjian;
	}

	public String getChushen() {
		return chushen;
	}

	public void setChushen(String chushen) {
		this.chushen = chushen;
	}

	public String getLuru() {
		return luru;
	}

	public void setLuru(String luru) {
		this.luru = luru;
	}

	public String getFushen() {
		return fushen;
	}

	public void setFushen(String fushen) {
		this.fushen = fushen;
	}

	public String getChushenapprove() {
		return chushenapprove;
	}

	public void setChushenapprove(String chushenapprove) {
		this.chushenapprove = chushenapprove;
	}

	public String getLuruapprove() {
		return luruapprove;
	}

	public void setLuruapprove(String luruapprove) {
		this.luruapprove = luruapprove;
	}

	public String getFushenapprove() {
		return fushenapprove;
	}

	public void setFushenapprove(String fushenapprove) {
		this.fushenapprove = fushenapprove;
	}

}
