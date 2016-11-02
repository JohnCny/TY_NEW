/**
 * 
 */
package com.cardpay.pccredit.manager.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 
 * 描述 ：客户经理从属关系
 * @author 张石树
 *
 * 2014-11-10 下午2:07:01
 */
@ModelParam(table = "manager_belong_map")
public class ManagerBelongMap extends BusinessModel{
	
	private String parentId;
	
	private String childId;
	
	private Boolean isLeaf;

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
}
