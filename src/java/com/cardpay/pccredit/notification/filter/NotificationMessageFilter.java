/*
 * Copyright 2014 Wicresoft, Inc. All rights reserved.
 */

package com.cardpay.pccredit.notification.filter;

import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;

/**
 * Description of NotificationMessageFilter
 * 
 * @author lzf
 * @created on 2014-10-15
 * 
 * @version $Id:$
 */
public class NotificationMessageFilter extends BaseQueryFilter {

	private String userId;
	private String noticeType;
	private String noticeContent;
	private String isCheck;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}

	public String getNoticeContent() {
		return noticeContent;
	}

	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}

	public String getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}

}
