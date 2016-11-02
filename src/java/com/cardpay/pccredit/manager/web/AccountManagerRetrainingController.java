package com.cardpay.pccredit.manager.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cardpay.pccredit.common.PccOrganizationService;
import com.cardpay.pccredit.manager.service.AccountManagerRetrainingService;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.web.controller.BaseController;

/**
 * @author chenzhifang
 *
 * 2014-11-11下午3:20:39
 */
@Controller
@RequestMapping("/manager/accountmanagerretraining/*")
@JRadModule("manager.accountmanagerretraining")
public class AccountManagerRetrainingController extends BaseController {
	@Autowired
	private AccountManagerRetrainingService accountManagerRetrainingService;

	@Autowired
	private PccOrganizationService pccOrganizationService;
	
}
