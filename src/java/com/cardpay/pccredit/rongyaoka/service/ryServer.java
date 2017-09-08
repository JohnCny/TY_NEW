package com.cardpay.pccredit.rongyaoka.service;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.rongyaoka.dao.ryDao;
import com.cardpay.pccredit.rongyaoka.model.rymodel;

@Service
public class ryServer {
	@Autowired
	private ryDao rydao;
	
	public void insertRyCs(rymodel model){
		rydao.insertRyCs(model);
	}
	public rymodel selectTime(@Param("id")String id){
		return rydao.selectTime(id);
		
	}
	public rymodel selectTime1(@Param("id")String id){
		return rydao.selectTime1(id);
		
	}
	
	public rymodel selectryCs(@Param("id")String id){
		return rydao.selectryCs(id);
	}
	
	public void insertRyFs(rymodel model){
		 rydao.insertRyFs(model);
	}
	
	public rymodel  selectryFs(@Param("id")String id){
		return rydao.selectryFs(id);
	}
}
