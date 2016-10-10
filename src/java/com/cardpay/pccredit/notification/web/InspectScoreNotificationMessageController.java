/*
 * Copyright 2014 Wicresoft, Inc. All rights reserved.
 */

package com.cardpay.pccredit.notification.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.notification.constant.NotificationConstant;
import com.cardpay.pccredit.notification.constant.NotificationEnum;
import com.cardpay.pccredit.notification.filter.NotificationMessageFilter;
import com.cardpay.pccredit.notification.model.NotificationMessage;
import com.cardpay.pccredit.notification.service.NotificationService;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.jrad.modules.privilege.model.User;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;
import com.wicresoft.util.web.RequestHelper;

/**
 * Description of NotificationMessageController
 * 
 * @author 王海东
 * @created on 2014-10-15
 * 
 * @version $Id:$
 */
@Controller
@RequestMapping("/notificationmessage/inspectscoremessage/*")
@JRadModule("notificationmessage.inspectscoremessage")
public class InspectScoreNotificationMessageController extends BaseController {


	@Autowired
	private NotificationService notificationService;
	
	
	
	/**
	 * 考察成绩通知
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "browse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browse(@ModelAttribute NotificationMessageFilter filter, HttpServletRequest request) {
		filter.setRequest(request);
		User u = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
		String userId= u.getId();
		filter.setIsCheck(NotificationConstant.no_read);
		filter.setUserId(userId);
		filter.setNoticeType(NotificationEnum.kaocha.toString());
		QueryResult<NotificationMessage> result = notificationService.findNotificationMessageByFilter(filter);
		JRadPagedQueryResult<NotificationMessage> pagedResult = new JRadPagedQueryResult<NotificationMessage>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/notification/notification_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);

		return mv;
	}
	
	/**
	 * 修改为已查看
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "delete.json")
	@JRadOperation(JRadOperation.CHECKED)
	public JRadReturnMap delete(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();

		try {
			String messageId = RequestHelper.getStringValue(request, ID);
			notificationService.deleteMessage(messageId);
            //TODO 保持统一返回
			returnMap.addGlobalMessage("已阅读");
		}
		catch (Exception e) {
            //TODO 有日志功能，在这一步应保持返回统一，出错以后查看日志
			return WebRequestHelper.processException(e);
		}

		return returnMap ;
	}
	
	/**
	 * 显示页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "display.page")
	@JRadOperation(JRadOperation.DISPLAY)
	public AbstractModelAndView display(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/notification/notification_display", request);
		String messageId = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(messageId)) {
			
			NotificationMessage notificationMessage =notificationService.findNotificationMessageById(messageId);
			mv.addObject("notificationMessage", notificationMessage);
		}

		return mv;
	}
}
