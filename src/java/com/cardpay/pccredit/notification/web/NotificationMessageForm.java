/*
 * Copyright 2014 Wicresoft, Inc. All rights reserved.
 */

package com.cardpay.pccredit.notification.web;

import com.wicresoft.jrad.base.web.form.BaseForm;

/**
 * Description of NotificationMessageForm
 * 
 * @author lzf
 * @created on 2014-10-15
 * 
 * @version $Id:$
 */
public class NotificationMessageForm extends BaseForm {

	private static final long serialVersionUID = 1L;
	private String userId;
	private String noticeType;
	private String noticeContent;
	private boolean isCheck;

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

	public boolean isCheck() {
		return isCheck;
	}

	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}

}
