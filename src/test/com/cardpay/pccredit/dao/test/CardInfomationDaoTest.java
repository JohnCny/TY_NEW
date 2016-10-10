package com.cardpay.pccredit.dao.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cardpay.pccredit.customer.dao.CardInfomationDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-junit.xml")
public class CardInfomationDaoTest {

	@Autowired
	private CardInfomationDao cardInfomationDao;
	
	@Test
	public void findCardsByCustomerIdTest(){
		cardInfomationDao.findCardsByCustomerId("0001");
	}
}
