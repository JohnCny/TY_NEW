package com.cardpay.pccredit.jnpad.model;

/**
 * pad 我的首页显示客户经理信息model
 *
 */
public class CustomerManagerVo {
	private String name;//姓名	
	private String sex;//性别
	private String age;//年龄
	private String org;//所属银行
	private String externalId;//客户经理编号
	private String sxqx;//授信权限
	private String fkze; //放款总额
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getOrg() {
		return org;
	}
	public void setOrg(String org) {
		this.org = org;
	}
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public String getSxqx() {
		return sxqx;
	}
	public void setSxqx(String sxqx) {
		this.sxqx = sxqx;
	}
	public String getFkze() {
		return fkze;
	}
	public void setFkze(String fkze) {
		this.fkze = fkze;
	}
	
}
