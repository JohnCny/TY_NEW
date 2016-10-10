package test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cardpay.pccredit.riskControl.service.RiskCustomerCollectionService;


/**
 * RiskCustomerCollectionServiceTest类的描述
 *
 * @author 王海东
 * @created on 2014-11-21
 * 
 * @version $Id:$
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-junit.xml")
public class RiskCustomerCollectionServiceTest {
	
	@Autowired
	private RiskCustomerCollectionService riskCustomerCollectionService;
	
	@Test
	public void test() {
		riskCustomerCollectionService.insertConectionSchedulesService();
	}
	}
	

