/**
 * 
 */
package com.cardpay.pccredit.manager.filter;

import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;

/**
 * 
 * 描述 ：客户经理从属过滤
 * @author 张石树
 *
 * 2014-11-10 下午3:53:13
 */
public class ManagerBelongMapFilter extends BaseQueryFilter{
	
	private String parentId;
	
	private String userName;
	
	private String levelInformation;

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
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
