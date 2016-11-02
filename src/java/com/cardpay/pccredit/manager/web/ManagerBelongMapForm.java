package com.cardpay.pccredit.manager.web;

import com.wicresoft.jrad.base.web.form.BaseForm;

/**
 * 
 * 描述 ：客户经理从属Form
 * @author 张石树
 *
 * 2014-11-10 下午2:54:40
 */
public class ManagerBelongMapForm extends BaseForm{

	private String parentId;
	
	private String childId;
	
	private Boolean isLeaf;
	
	private String userName;
	
	private String levelInformation;
	
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getChildId() {
		return childId;
	}

	public void setChildId(String childId) {
		this.childId = childId;
	}

	public Boolean getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(Boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLevelInformation() {
		return levelInformation;
	}

	public void setLevelInformation(String levelInformation) {
		this.levelInformation = levelInformation;
	}
}
