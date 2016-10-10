package com.cardpay.pccredit.manager.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cardpay.pccredit.report.service.StatisticalTableScheduleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-junit.xml")
public class ManagerAssessmentScoreScheduleServiceTest {

	@Autowired
	private ManagerAssessmentScoreScheduleService managerAssessmentScoreScheduleService;
	
	@Autowired
	private StatisticsScheduleService statisticsScheduleService;
	
	@Autowired
	private StatisticalTableScheduleService statisticalTableScheduleService;
	
	@Test
	public void addManagerAssessmentScoreTest(){
		//managerAssessmentScoreScheduleService.addManagerAssessmentScore();
	}
	
	@Test
	public void downLevelSchedulesDayTest(){
		managerAssessmentScoreScheduleService.downLevelSchedulesDay();
	}
	
	@Test
	public void StatisticsScheduleServicer(){
		statisticsScheduleService.insertWeekSchedule();
	}
	
	@Test
	public void addStatisticalTableTest(){
		statisticalTableScheduleService.addStatisticalTable();
	}
}
