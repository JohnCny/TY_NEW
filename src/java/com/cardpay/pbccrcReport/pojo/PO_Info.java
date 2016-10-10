package com.cardpay.pbccrcReport.pojo;

import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 配偶信息
 * @author chenzhifang
 *
 * 2014-12-24下午3:51:30
 */
@ModelParam(table = "RH_PO_INFO")
public class PO_Info extends AbstractCustomerInfo{
	private static final long serialVersionUID = 1L;
	private String name;//姓名
	private String creditType;//证件类型
	private String creditNO;//证件号码
	private String workUnit;//工作单位
	private String phone;//联系电话
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCreditType() {
		return creditType;
	}
	public void setCreditType(String creditType) {
		this.creditType = creditType;
	}
	public String getCreditNO() {
		return creditNO;
	}
	public void setCreditNO(String creditNO) {
		this.creditNO = creditNO;
	}
	public String getWorkUnit() {
		return workUnit;
	}
	public void setWorkUnit(String workUnit) {
		this.workUnit = workUnit;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
}
