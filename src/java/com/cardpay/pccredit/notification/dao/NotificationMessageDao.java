/*
 * Copyright 2014 Wicresoft, Inc. All rights reserved.
 */

package com.cardpay.pccredit.notification.dao;

import java.util.List;

import com.cardpay.pccredit.notification.model.NotificationMessage;
import com.wicresoft.jrad.base.database.dao.query.QueryContext;
import com.wicresoft.util.annotation.Mapper;

/**
 * Description of NotificationMessageDao
 * 
 * @author smithw
 * @created on 2014-10-15
 * 
 * @version $Id:$
 */
@Mapper
public interface NotificationMessageDao {
	
	public List<NotificationMessage> findNotificationMessageByFilter(QueryContext qc);

	public int deleteNotificationMessage(String messageId);
}
