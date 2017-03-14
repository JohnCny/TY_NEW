package com.cardpay.pccredit.jnpad.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.jnpad.dao.JnpadSpUserDao;
import com.cardpay.pccredit.jnpad.model.CustomerSpUser;
import com.cardpay.pccredit.system.service.NodeAuditService;

@Service
public class JnpadSpUserService {
	@Autowired
	private JnpadSpUserDao UserDao;
	
	public int addSpUser(CustomerSpUser CustomerSpUser){
		return UserDao.addSpUser(CustomerSpUser);
	}
	public int addSpUser1(CustomerSpUser CustomerSpUser){
		return UserDao.addSpUser1(CustomerSpUser);
	}
	public List<CustomerSpUser>findspUser(){
		return UserDao.findspUser();
	}
	public List<CustomerSpUser>findspUser2(@Param("capid") String capid){
		return UserDao.findspUser2(capid);
	}
	public CustomerSpUser selectUser(@Param("id") String id){
		return UserDao.selectUser(id);
	}
	public List<CustomerSpUser>findspUser3(@Param("capid") String capid){
		return UserDao.findspUser3(capid);
	}
	public List<CustomerSpUser>selectUser1(@Param("id") String id){
		return UserDao.selectUser1(id);
	}
}
