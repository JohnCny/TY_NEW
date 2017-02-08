package com.cardpay.pccredit.jnpad.service;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.jnpad.dao.JnpadDailyAccountManagerDao;
import com.cardpay.pccredit.jnpad.dao.JnpadLoginDao;
import com.cardpay.pccredit.jnpad.model.LoginInfo;

@Service
public class JnpadLoginService {
	@Autowired
	private JnpadLoginDao padLoginDao;
	
	public LoginInfo selecTime(@Param("login") String login){
		return padLoginDao.selecTime(login);
		
	}
	public int insetTime(LoginInfo LoginInfo){
		return padLoginDao.insetTime(LoginInfo);
	}
}
