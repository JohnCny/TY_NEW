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
public class JxglpmForm extends BaseForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int fpm;
	private int lpm;
	public JxglpmForm(){}
	
	public JxglpmForm(int fpm, int lpm) {
		this.fpm = fpm;
		this.lpm = lpm;
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
