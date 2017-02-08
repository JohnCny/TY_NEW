package com.cardpay.pccredit.jnpad.dao;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.jnpad.model.LoginInfo;
import com.wicresoft.util.annotation.Mapper;
@Mapper
public interface JnpadLoginDao {
	//上次登陆时间
	LoginInfo selecTime(@Param("login") String login);
	
	//添加本次登入时间
	int insetTime(LoginInfo LoginInfo);

}
