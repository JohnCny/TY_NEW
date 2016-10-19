package com.cardpay.pccredit.jnpad.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.jnpad.dao.JnpadCustomerAppraisDao;
import com.cardpay.pccredit.jnpad.model.CustomerApprais;


@Service
public class JnpadCustomerAppraisService {
	@Autowired
	private JnpadCustomerAppraisDao tycadao;
	
	public int addCustomerApprais(CustomerApprais ca){
		return tycadao.addCustomerApprais(ca);
	}
	public int selectCustomerAppraisCount(CustomerApprais ca){
		return tycadao.selectCustomerAppraisCount(ca);
	}
	
	public CustomerApprais selectCustomerApprais(CustomerApprais ca){
		return tycadao.selectCustomerApprais(ca);
	}
	public int updateCustomerApprais(CustomerApprais ca){
		return tycadao.updateCustomerApprais(ca);
	}
	public CustomerApprais selectAllCustomerApprais(CustomerApprais c){
		return tycadao.selectAllCustomerApprais(c);
	}
}
