package com.cardpay.pbccrcReport.pojo;

import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 居住信息
 * @author chenzhifang
 *
 * 2014-12-24下午3:51:30
 */
// RH_JZ_INFO为表格名
@ModelParam(table = "RH_JZ_INFO")
public class JZ_Info extends AbstractCustomerInfo{
	private static final long serialVersionUID = 1L;
	private String index_;//编号
	private String address;//居住地址
	private String condition;//居住状况
	private String updateDate;//信息更新日期
	
	
	public String getIndex_() {
		return index_;
	}
	public void setIndex_(String index_) {
		this.index_ = index_;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
}
