package com.cardpay.pccredit.jnpad.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.jnpad.dao.JnIpadJBUserDao;
import com.cardpay.pccredit.jnpad.model.JBUser;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;

@Service
public class JnIpadJBUserService {
	@Autowired
	private JnIpadJBUserDao JnIpadJBUser;
	
	
	public List<JBUser> selectDepartUser(@Param("ID") String ID){
		return JnIpadJBUser.selectDepartUser(ID);
	}
	public List<JBUser> selectDepart1(@Param("ID") String ID){
		return JnIpadJBUser.selectDepart1(ID);
	}
	
	
	public List<JBUser> findDe(@Param("ID") String ID){
		return JnIpadJBUser.findDe(ID);
	}

}
