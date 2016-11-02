package com.cardpay.pccredit.dao.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cardpay.pccredit.manager.dao.comdao.ManagerAssessmentScoreCommDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-junit.xml")
public class ManagerAssessmentScoreCommDaoTest {

	@Autowired
	private ManagerAssessmentScoreCommDao managerAssessmentScoreCommDao;
	
	@Test
	public void findCardsByCustomerIdTest(){
		List<String> customerIds = new ArrayList<String>();
		customerIds.add("11");
		Double sum = managerAssessmentScoreCommDao.sumAppApplyQuota(customerIds);
		System.out.println(sum);
	}
}
