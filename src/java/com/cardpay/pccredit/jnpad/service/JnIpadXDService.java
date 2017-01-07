package com.cardpay.pccredit.jnpad.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.jnpad.dao.JnpadSelectDao;
import com.cardpay.pccredit.jnpad.model.JnIpadXDModel;

@Service
public class JnIpadXDService {
	@Autowired
	private JnpadSelectDao IpadXDDao;
	
	
	public List<JnIpadXDModel> selectUserXD(@Param("userId") String userId){
		return IpadXDDao.selectUserXDGZ(userId);
		
	}

}
