/**
 * 
 */
package com.cardpay.pccredit.customeradd.model;


import com.wicresoft.jrad.base.web.form.BaseForm;

/**
 * @author zhengbo
 *
 * 2014年11月11日   下午2:58:41
 */
public class JxglForm extends BaseForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String displayname;
	private String monthloannum;
	private String montheffectnum;
	private String byffje;
	private String ffje;
	private String zsx;
	
	public JxglForm(){}
	public JxglForm(String displayname, String monthloannum,
			String montheffectnum, String byffje, String ffje, String zsx) {
		this.displayname = displayname;
		this.monthloannum = monthloannum;
		this.montheffectnum = montheffectnum;
		this.byffje = byffje;
		this.ffje = ffje;
		this.zsx = zsx;
	}
	public String getDisplayname() {
		return displayname;
	}
	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}
	public String getMonthloannum() {
		return monthloannum;
	}
	public void setMonthloannum(String monthloannum) {
		this.monthloannum = monthloannum;
	}
	public String getMontheffectnum() {
		return montheffectnum;
	}
	public void setMontheffectnum(String montheffectnum) {
		this.montheffectnum = montheffectnum;
	}
	public String getByffje() {
		return byffje;
	}
	public void setByffje(String byffje) {
		this.byffje = byffje;
	}
	public String getFfje() {
		return ffje;
	}
	public void setFfje(String ffje) {
		this.ffje = ffje;
	}
	public String getZsx() {
		return zsx;
	}
	public void setZsx(String zsx) {
		this.zsx = zsx;
	}
	
	
}
