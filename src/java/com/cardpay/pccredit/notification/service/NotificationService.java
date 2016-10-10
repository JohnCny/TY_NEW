/*
 * Copyright 2014 Wicresoft, Inc. All rights reserved.
 */

package com.cardpay.pccredit.notification.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.notification.constant.NotificationConstant;
import com.cardpay.pccredit.notification.constant.NotificationEnum;
import com.cardpay.pccredit.notification.dao.NotificationMessageDao;
import com.cardpay.pccredit.notification.filter.NotificationMessageFilter;
import com.cardpay.pccredit.notification.model.NotificationMessage;
import com.cardpay.pccredit.riskControl.filter.RiskCustomerFilter;
import com.cardpay.pccredit.riskControl.model.RiskCustomer;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.model.QueryResult;

/**
 * Description of NotificationService
 * 
 * @author lzf
 * @created on 2014-10-15
 * 
 * @version $Id:$
 */
//TODO 方法注释
@Service
public class NotificationService {
	@Autowired
	private CommonDao commonDao;

	@Autowired
	private NotificationMessageDao notificationMessageDao;
	
	/**
	 * 通知新增接口
	 * 
	 * @param filter
	 * @return
	 */
	public int insertNotification(NotificationEnum notificationEnum,String userId,String title,String context,String createUser){
		NotificationMessage notificationMessage=new NotificationMessage();
		notificationMessage.setCreatedTime(new Date());
		notificationMessage.setCreatedBy(createUser);
		notificationMessage.setIsCheck(NotificationConstant.no_read);
		notificationMessage.setNoticeContent(context);
		notificationMessage.setNoticeType(notificationEnum.toString());
		notificationMessage.setUserId(userId);
	    return	commonDao.insertObject(notificationMessage);
	}

	public QueryResult<NotificationMessage> findNotificationMessageByFilter(NotificationMessageFilter filter) {
		return commonDao.findObjectsByFilter(NotificationMessage.class, filter);
	}

	public int deleteMessage(String messageId) {

		return notificationMessageDao.deleteNotificationMessage(messageId);

	}

	public int batchDeleteMessages(List<String> messageIds) {
		return commonDao.batchDeleteObjects(NotificationMessage.class,messageIds);
	}

	public NotificationMessage findNotificationMessageById(String messageId) {
		return commonDao.findObjectById(NotificationMessage.class, messageId);

	}
	
}
