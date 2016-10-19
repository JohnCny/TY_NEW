package com.cardpay.pccredit.jnpad.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.jnpad.dao.JnpadUsercoorinateDao;
import com.cardpay.pccredit.jnpad.model.usercoorinate;


@Service
public class JnpadUsercoorinateService {
	@Autowired
	private JnpadUsercoorinateDao dao;
	
	/**
	 * 当前客户经理添加客户地理信息
	 * @param u
	 * @return
	 */
	public int addusercoorinate(usercoorinate u){
		return dao.addusercoorinate(u);
		
	}
	/**
	 * 通过userid查询该客户经理的地理信息
	 * @param u
	 * @return
	 */
	public List<usercoorinate> selectUserByUserid(usercoorinate u){
		return dao.selectUserByUserid(u);
		
	}
	/**
	 * 通过userid查询该客户经理的地理信息数量
	 * @param u
	 * @return
	 */
	public int selectUserByUseridCount(usercoorinate u){
		return dao.selectUserByUseridCount(u);
		
	}
	public int updateUser(usercoorinate u){
		return dao.updateUser(u);
	}
}
