package com.cardpay.pccredit.customer.service;

import java.io.IOException;
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
import com.cardpay.pccredit.zrrtz.dao.ZrrtzDao;
import com.cardpay.pccredit.zrrtz.model.IncomingData;
import com.cardpay.pccredit.zrrtz.model.Pigeonhole;
import com.cardpay.pccredit.zrrtz.model.ZrrtzFilter;


@Service
public class CustomerParameterService {
	@Autowired
	private ICustomerParameterDao cpDao;
	
	@Autowired
	private ZrrtzDao dao;

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
	//手动纸质归档
	public void addCustomerPigeonhole(Pigeonhole pig) {
		// TODO Auto-generated method stub
		cpDao.addCustomerPigeonhole(pig);
	}
	
		//归档所有档案
	public void addAllCustomerPigeonhole() throws IOException{
		ZrrtzFilter filter = new ZrrtzFilter();
		Pigeonhole pig = new Pigeonhole();
		List<IncomingData> pigs=dao.findIntoPiecesListes(filter);
		System.out.println(pigs.size());
		for(IncomingData datas:pigs){
		pig.setYwbh(datas.getYwbh());
		pig.setUserId(datas.getUserId());
		pig.setPigeonhole("0");
		cpDao.addCustomerPigeonhole(pig);
		}
}

}
