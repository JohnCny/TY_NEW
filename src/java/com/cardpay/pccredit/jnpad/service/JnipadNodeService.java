package com.cardpay.pccredit.jnpad.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.jnpad.dao.JnipadNodeDao;
import com.cardpay.pccredit.jnpad.model.NODEAUDIT;

@Service
public class JnipadNodeService {
	@Autowired
	private JnipadNodeDao nodao;;
	public List<NODEAUDIT> selectProductUser(NODEAUDIT n){
		return nodao.selectProductUser(n);
	}
	public int selectProductUserCount(NODEAUDIT n){
		return nodao.selectProductUserCount(n);
	}
	public List<NODEAUDIT> selectAllProductUser(NODEAUDIT n){
		return nodao.selectAllProductUser(n);
	}
	public int selectAllProductUserCount(NODEAUDIT n){
		return nodao.selectAllProductUserCount(n);
	}
}
