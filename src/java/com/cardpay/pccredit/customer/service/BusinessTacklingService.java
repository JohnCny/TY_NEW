package com.cardpay.pccredit.customer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.customer.dao.IBusinessTacklingDao;
import com.cardpay.pccredit.customer.model.BusinessTackling;
import com.cardpay.pccredit.jnpad.model.CustomerInfo;

/**
 * 
 * 描述 ：业务核查service
 * @author 周文杰
 *
 * 2016年10月24日15:03:56
 */
@Service
public class BusinessTacklingService {
	@Autowired
	private IBusinessTacklingDao btDao;

	public List<CustomerInfo>  queryById(String cardId) {
		// TODO Auto-generated method stub
		return btDao.queryById(cardId);
	}

	public List<BusinessTackling> queryByIdCard(String idcard) {
		// TODO Auto-generated method stub
		return btDao.queryByIdCard(idcard);
	}
}
