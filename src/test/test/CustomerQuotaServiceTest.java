package test;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cardpay.pccredit.customer.service.CustomerQuotaService;


/**
 * CustomerQuotaServiceTest类的描述
 *
 * @author 王海东
 * @created on 2014-11-27
 * 
 * @version $Id:$
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-junit.xml")
public class CustomerQuotaServiceTest {

	@Autowired
	private CustomerQuotaService customerQuotaService;
	@Test
	public void test() {
		String productId ="402880f04955718b0149558a5717000f";
		customerQuotaService.upQuotaCustomer(productId);
	}
	
	@Test
	public void downQuotaCustomertest() {
		String productId ="000000004a287ced014a28c58ec500a3";
		 customerQuotaService.downQuotaCustomer(productId);
	}

}
