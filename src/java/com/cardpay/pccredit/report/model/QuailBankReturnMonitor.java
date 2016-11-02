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
public class QuailBankReturnMonitor extends BusinessModel {

	private static final long serialVersionUID = 1L;

	private String displayName; // 银行名称
	private String rowindex; // 列号
	private String returncount;// 退回数
	private String percent;
	// 退回原因请参照DICT表dict_type='APPRETURESION'
	private String APPRETURESION06;
	private String APPRETURESION01;
	private String APPRETURESION02;
	private String APPRETURESION03;
	private String APPRETURESION04;
	private String APPRETURESION05;
	private String APPRETURESION07;
	private String APPRETURESION08;
	private String APPRETURESION09;
	private String APPRETURESION10;
	private String APPRETURESION11;
	private String APPRETURESION12;
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

	public String getReturncount() {
		return returncount;
	}

	public void setReturncount(String returncount) {
		this.returncount = returncount;
	}

	public String getAPPRETURESION06() {
		return APPRETURESION06;
	}

	public void setAPPRETURESION06(String aPPRETURESION06) {
		APPRETURESION06 = aPPRETURESION06;
	}

	public String getAPPRETURESION01() {
		return APPRETURESION01;
	}

	public void setAPPRETURESION01(String aPPRETURESION01) {
		APPRETURESION01 = aPPRETURESION01;
	}

	public String getAPPRETURESION02() {
		return APPRETURESION02;
	}

	public void setAPPRETURESION02(String aPPRETURESION02) {
		APPRETURESION02 = aPPRETURESION02;
	}

	public String getAPPRETURESION03() {
		return APPRETURESION03;
	}

	public void setAPPRETURESION03(String aPPRETURESION03) {
		APPRETURESION03 = aPPRETURESION03;
	}

	public String getAPPRETURESION04() {
		return APPRETURESION04;
	}

	public void setAPPRETURESION04(String aPPRETURESION04) {
		APPRETURESION04 = aPPRETURESION04;
	}

	public String getAPPRETURESION05() {
		return APPRETURESION05;
	}

	public void setAPPRETURESION05(String aPPRETURESION05) {
		APPRETURESION05 = aPPRETURESION05;
	}

	public String getAPPRETURESION07() {
		return APPRETURESION07;
	}

	public void setAPPRETURESION07(String aPPRETURESION07) {
		APPRETURESION07 = aPPRETURESION07;
	}

	public String getAPPRETURESION08() {
		return APPRETURESION08;
	}

	public void setAPPRETURESION08(String aPPRETURESION08) {
		APPRETURESION08 = aPPRETURESION08;
	}

	public String getAPPRETURESION09() {
		return APPRETURESION09;
	}

	public void setAPPRETURESION09(String aPPRETURESION09) {
		APPRETURESION09 = aPPRETURESION09;
	}

	public String getAPPRETURESION10() {
		return APPRETURESION10;
	}

	public void setAPPRETURESION10(String aPPRETURESION10) {
		APPRETURESION10 = aPPRETURESION10;
	}

	public String getAPPRETURESION11() {
		return APPRETURESION11;
	}

	public void setAPPRETURESION11(String aPPRETURESION11) {
		APPRETURESION11 = aPPRETURESION11;
	}

	public String getAPPRETURESION12() {
		return APPRETURESION12;
	}

	public void setAPPRETURESION12(String aPPRETURESION12) {
		APPRETURESION12 = aPPRETURESION12;
	}

}
