/**
 * 
 */
package com.cardpay.pccredit.manager.model;

import com.wicresoft.jrad.base.database.id.IDType;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 描述 ：客户经理绩效参数
 * @author 贺珈
 *
 * 2014-11-20 下午5:31:52
 */
@ModelParam(table="ty_performance_parameters",generator=IDType.uuid32)
public class TyPerformanceParameters extends BusinessModel {

	private static final long serialVersionUID = 1L;
	
	private String levelCode;
	
	private String managerLevel;
	
	private String basicPerformance;
	
	private String A;//主调/每笔（元）
	
	private String B;//管户/每笔（元）
	
	private String D;//审批/每笔（元）
	
	private String E;//岗位绩效（元）
	
	private String F;//辅调/每笔（元）
	
	private String objectCount;//每月目标笔数

	public String getManagerLevel() {
		return managerLevel;
	}

	public void setManagerLevel(String managerLevel) {
		this.managerLevel = managerLevel;
	}

	public String getBasicPerformance() {
		return basicPerformance;
	}

	public void setBasicPerformance(String basicPerformance) {
		this.basicPerformance = basicPerformance;
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

	public String getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}

	public String getObjectCount() {
		return objectCount;
	}

	public void setObjectCount(String objectCount) {
		this.objectCount = objectCount;
	}
	
}
