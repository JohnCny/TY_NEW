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
	private int results;
	
	public JxglpmForm(){}
	public JxglpmForm(int fpm, int lpm, int results) {
		this.fpm = fpm;
		this.lpm = lpm;
		this.results = results;
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
	public int getResults() {
		return results;
	}
	public void setResults(int results) {
		this.results = results;
	}
	
}
