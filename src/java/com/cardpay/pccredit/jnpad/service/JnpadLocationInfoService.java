package com.cardpay.pccredit.jnpad.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.jnpad.dao.JnpadLocationInfoDao;
import com.cardpay.pccredit.jnpad.model.LocationInfoForm;
import com.cardpay.pccredit.jnpad.model.ManagerInfoForm;

@Service 
public class JnpadLocationInfoService {
	
	@Autowired
	private JnpadLocationInfoDao jnpadLocationInfoDao;

	//查询表中该客户经理信息包含条数
	public int managerCount(String managerId) {
		// TODO Auto-generated method stub
		return jnpadLocationInfoDao.managerCount(managerId);
	}
	//插入客户经理信息
	public void insertManagerLocation(LocationInfoForm locationInfoForm) {
		// TODO Auto-generated method stub
		
		jnpadLocationInfoDao.insertManagerLocation(locationInfoForm);
	}
	
	//更新客户经理信息
	public void updateManagerLocation(LocationInfoForm locationInfoForm) {
		// TODO Auto-generated method stub
		
		jnpadLocationInfoDao.updateManagerLocation(locationInfoForm);
	}
	//根据Id查询客户经理信息
	public ManagerInfoForm selectManagerInforById(String managerId) {
		// TODO Auto-generated method stub
		
		return jnpadLocationInfoDao.selectManagerInforById(managerId);
	}
	public List<LocationInfoForm> selectManagerLocationById(String userId) {
		// TODO Auto-generated method stub
		
		
		return jnpadLocationInfoDao.selectManagerLocationById(userId);
	}
	
	public LocationInfoForm selectlastManagerLocationById(String managerId) {
		// TODO Auto-generated method stub
		return jnpadLocationInfoDao.selectlastManagerLocationById(managerId);
	}

}
