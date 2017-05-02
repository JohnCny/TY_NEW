/**
 * 
 */
package com.cardpay.pccredit.customeradd.model;


import java.util.List;

import com.wicresoft.jrad.base.web.form.BaseForm;

/**
 * @author zhengbo
 *
 * 2014年11月11日   下午2:58:41
 */
public class JxglpmForm extends BaseForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private int fpm;
	private int lpm;
	private int result; //档次
	private int pnum;
	private String displayname;
	
	
	public String getDisplayname() {
		return displayname;
	}
	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public int getPnum() {
		return pnum;
	}
	public void setPnum(int pnum) {
		this.pnum = pnum;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getFpm() {
		return fpm;
	}
	public void setFpm(int fpm) {
		this.fpm = fpm;
	}
	public int getLpm() {
		return lpm;
	}
	public void setLpm(int lpm) {
		this.lpm = lpm;
	}
	
	
}
