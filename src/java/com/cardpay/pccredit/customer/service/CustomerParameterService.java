package com.cardpay.pccredit.customer.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.customer.dao.ICustomerParameterDao;
import com.cardpay.pccredit.customer.model.CustomerParameter;
import com.cardpay.pccredit.customer.model.ParameterInformaion;
import com.cardpay.pccredit.intopieces.model.IntoPieces;
import com.cardpay.pccredit.ipad.model.UserIpad;
import com.cardpay.pccredit.product.model.ProductAttribute;


@Service
public class CustomerParameterService {
	@Autowired
	private ICustomerParameterDao cpDao;

	public void addCustomerParameter(CustomerParameter cp) {
		// TODO Auto-generated method stub
		if(null==cp.getCustomerParameterId()){
			cp.setCustomerParameterId(UUID.randomUUID().toString());
		}
		cpDao.addCustomerParameter(cp);
	}
	
	public List<ProductAttribute> queryProduct() {
		// TODO Auto-generated method stub
		return cpDao.queryProduct();
	}


	public List<ParameterInformaion> query(String id) {
		// TODO Auto-generated method stub
		return cpDao.query(id);
	}

	public List<UserIpad> queryAllManager() {
		// TODO Auto-generated method stub
		return cpDao.queryAllManager();
	}

	public List<CustomerParameter> queryByIdCard(String idcard) {
		// TODO Auto-generated method stub
		return cpDao.queryByIdCard(idcard);
	}

}
