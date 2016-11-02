package com.cardpay.pccredit.customer.model;

import com.wicresoft.jrad.base.database.id.IDType;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 
 * @author 贺珈
 * 原始客户信息--家庭成员（太原）
 *
 */
@ModelParam(table = "ty_customer_family_cy",generator=IDType.assigned)
public class CustomerFirsthendFamilyCy extends BusinessModel{
	private static final long serialVersionUID = 1L;
	private String khnm;//客户内码,个人第一位A,法人第一位为B
	private String cyxm;//成员姓名
	private String ykhgx;//与客户关系
	private String zjlx;//证件类型
	private String zjhm;//证件号码
	private String xb;//性别
	private String gxfl;//关系分类  1 家庭关系  2 社会关系
	private String createTime;
	public String getKhnm() {
		return khnm;
	}
	public void setKhnm(String khnm) {
		this.khnm = khnm;
	}
	public String getCyxm() {
		return cyxm;
	}
	public void setCyxm(String cyxm) {
		this.cyxm = cyxm;
	}
	public String getYkhgx() {
		return ykhgx;
	}
	public void setYkhgx(String ykhgx) {
		this.ykhgx = ykhgx;
	}
	public String getZjlx() {
		return zjlx;
	}
	public void setZjlx(String zjlx) {
		this.zjlx = zjlx;
	}
	public String getZjhm() {
		return zjhm;
	}
	public void setZjhm(String zjhm) {
		this.zjhm = zjhm;
	}
	public String getXb() {
		return xb;
	}
	public void setXb(String xb) {
		this.xb = xb;
	}
	public String getGxfl() {
		return gxfl;
	}
	public void setGxfl(String gxfl) {
		this.gxfl = gxfl;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
