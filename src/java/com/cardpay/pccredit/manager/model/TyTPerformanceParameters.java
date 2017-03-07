/**
 * 
 */
package com.cardpay.pccredit.manager.model;

import com.wicresoft.jrad.base.database.id.IDType;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 描述 ：客户经理绩效参数 ty
 * @author zhengbo
 *
 * 2014-11-20 下午5:31:52
 */
@ModelParam(table="t_performance_parameters",generator=IDType.uuid32)
public class TyTPerformanceParameters extends BusinessModel {

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
	 * 客户经理当月承担审批的贷款笔数单价(元/户)
	 */
	private String  C;
	
	/**
	 * 管户维护奖金(元/户)
	 */
	private String  D;
	
	/**
	 * 等级岗位
	 */
	private String E;
	
	/**
	 * 等级岗位经贴(元)
	 */
	private String F;
	

	/**
	 * 任务完成度(%)
	 */
	private String G;
	
	/**
	 * 任务完成度绩效(元)
	 */
	private String H;
	
	/**
	 * 不良贷款容忍度(%)
	 */
	private String I;
	
	/**
	 * 不良贷款容忍度扣款(按百分比扣款)
	 */
	private String J;
	
	/**
	 * 辅助调查(元/笔)
	 */
	private String K;

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

	public String getC() {
		return C;
	}

	public void setC(String c) {
		C = c;
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

	public String getK() {
		return K;
	}

	public void setK(String k) {
		K = k;
	}
	
	
	
	
	 
}
