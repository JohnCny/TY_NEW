/**
 * 
 */
package com.cardpay.pccredit.manager.model;

import com.wicresoft.jrad.base.database.id.IDType;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 描述 ：客户经理绩效参数 jn
 * @author songchen
 *
 * 2014-11-20 下午5:31:52
 */
@ModelParam(table="t_performance_parameters",generator=IDType.uuid32)
public class TPerformanceParameters extends BusinessModel {

	private static final long serialVersionUID = 1L;
	/**
	 * 主办客户经理计提单价(元/户)
	 */
	private String  A;
	/**
	 * 协办客户经理计提单价(元/户)
	 */
	private String  B;
	/**
	 * 管户维护奖金(元/户)
	 */
	private String  D;
	/**
	 * FTP价格
	 */
	private String  E;
	/**
	 * 利润提成系数(A)
	 */
	private String  F;
	/**
	 * 风险保障系数(R-信用类)
	 */
	private String  G;
	/**
	 * 风险保障系数(R-保证类)
	 */
	private String  H;
	/**
	 * 风险保障系数(R-抵押类)
	 */
	private String  I;
	/**
	 * 逾期扣款(元/笔/天)
	 */
	private String  J;
	
	private String K;
	
	private String L;
	
	
	public String getL() {
		return L;
	}
	public void setL(String l) {
		L = l;
	}
	public String getK() {
		return K;
	}
	public void setK(String k) {
		K = k;
	}
	public String getA() {
		return A;
	}
	public void setA(String a) {
		A = a;
	}
	public String getB() {
		return B;
	}
	public void setB(String b) {
		B = b;
	}
	public String getD() {
		return D;
	}
	public void setD(String d) {
		D = d;
	}
	public String getE() {
		return E;
	}
	public void setE(String e) {
		E = e;
	}
	public String getF() {
		return F;
	}
	public void setF(String f) {
		F = f;
	}
	public String getG() {
		return G;
	}
	public void setG(String g) {
		G = g;
	}
	public String getH() {
		return H;
	}
	public void setH(String h) {
		H = h;
	}
	public String getI() {
		return I;
	}
	public void setI(String i) {
		I = i;
	}
	public String getJ() {
		return J;
	}
	public void setJ(String j) {
		J = j;
	}
	
	
	
	
	 
}
