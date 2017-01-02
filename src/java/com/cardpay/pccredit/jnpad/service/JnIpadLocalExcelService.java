package com.cardpay.pccredit.jnpad.service;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.intopieces.model.LocalExcel;
import com.cardpay.pccredit.jnpad.dao.JnIpadLocalExcelDao;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
@Service
public class JnIpadLocalExcelService {
	@Autowired
	private JnIpadLocalExcelDao LocalExcelDao;
	
	public LocalExcel findByApplication(@Param("customerId") String customerId,@Param("productId") String productId){
		return LocalExcelDao.findByApplication(customerId, productId);
		
	}
}
