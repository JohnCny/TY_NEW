package com.cardpay.pccredit.customer.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cardpay.pccredit.customer.dao.comdao.CustomerQuotaComdao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-junit.xml")
public class CustomerDaoTest {

	@Autowired
	private CustomerQuotaComdao customerQuotaComdao;
	
	@Test
	public void findApprovalNoListTest(){
		List<String> customerIds = new ArrayList<String>();
		customerIds.add("sdfsdfsd");
		customerQuotaComdao.findApprovalNoList("65453", customerIds);
	}
}
