package com.cardpay.pccredit.customer.model;

import com.wicresoft.jrad.base.database.id.IDType;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;
/**
 * 
 * @author 季东晓
 *
 * 2014-11-19 上午11:36:40
 */
@ModelParam(table = "customer_manager_target",generator=IDType.uuid32)
public class CustomerManagerTarget extends BusinessModel {
	
	private static final long serialVersionUID = 1L;
	
	private String 	hierarchy	;//	经理层级
	
	private String 	targetDate	;//年/月/周
	
	private String 	targetCredit	;//	目标授信额度
	
	private Integer 	targetNumber	;//	目标进件数
	
	private Integer 	targetNumberVisit	;//	目标拜访次数
	
	private Integer 	targetNumberCustomers	;//	目标拜访客户数
	
	private Integer 	activeNumber	;//活跃数
	
	private Integer 	activationNumber	;//激活数

	private Integer    tubeNumber; //管户数

	public String getTargetDate() {
		return targetDate;
	}

	public void setTargetDate(String targetDate) {
		this.targetDate = targetDate;
	}

	public String getTargetCredit() {
		return targetCredit;
	}

	public void setTargetCredit(String targetCredit) {
		this.targetCredit = targetCredit;
	}

	public Integer getTargetNumber() {
		return targetNumber;
	}

	public void setTargetNumber(Integer targetNumber) {
		this.targetNumber = targetNumber;
	}

	public Integer getTargetNumberVisit() {
		return targetNumberVisit;
	}

	public void setTargetNumberVisit(Integer targetNumberVisit) {
		this.targetNumberVisit = targetNumberVisit;
	}

	public Integer getTargetNumberCustomers() {
		return targetNumberCustomers;
	}

	public void setTargetNumberCustomers(Integer targetNumberCustomers) {
		this.targetNumberCustomers = targetNumberCustomers;
	}

	public Integer getActiveNumber() {
		return activeNumber;
	}

	public void setActiveNumber(Integer activeNumber) {
		this.activeNumber = activeNumber;
	}

	public Integer getActivationNumber() {
		return activationNumber;
	}

	public void setActivationNumber(Integer activationNumber) {
		this.activationNumber = activationNumber;
	}

	public String getHierarchy() {
		return hierarchy;
	}

	public void setHierarchy(String hierarchy) {
		this.hierarchy = hierarchy;
	}

	public Integer getTubeNumber() {
		return tubeNumber;
	}

	public void setTubeNumber(Integer tubeNumber) {
		this.tubeNumber = tubeNumber;
	}

	
	

}
