package com.cardpay.pccredit.jnpad.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.customer.dao.CustomerInforDao;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.model.PgUser;
import com.cardpay.pccredit.intopieces.model.LocalExcel;
import com.cardpay.pccredit.jnpad.dao.JnIpadLocalExcelDao;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
@Service
public class JnIpadLocalExcelService {
	@Autowired
	private CustomerInforDao InforDao;
	@Autowired
	private JnIpadLocalExcelDao LocalExcelDao;
	public LocalExcel findByApplication(@Param("customerId") String customerId,@Param("productId") String productId){
		return LocalExcelDao.findByApplication(customerId, productId);
		
	}
	public List<PgUser>selectPgUser(@Param("id")String id,@Param("name")String name){
		return InforDao.selectPgUser(id,name);
	}
	public List<CustomerInfor>selectTeamOrg(@Param("id")String id,@Param("userId")String userId){
		return InforDao.selectTeamOrg(id,userId);
	}
}
