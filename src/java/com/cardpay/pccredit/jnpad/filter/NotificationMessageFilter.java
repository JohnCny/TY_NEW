package com.cardpay.pccredit.jnpad.filter;

import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;

public class NotificationMessageFilter extends BaseQueryFilter{
    private String noticeType;
    private String userId;
    
    

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
}
