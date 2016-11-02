package test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cardpay.pccredit.riskControl.service.AccountabilityService;


/**
 * 
 * @author 季东晓
 *
 * 2014-11-13 下午5:14:20
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-junit.xml")
public class AccountabilityServiceTest {
	
	@Autowired
	private  AccountabilityService accountabilityService;

	@Test
	public void insertAccountabilityRecordSystem() {
		
		accountabilityService.insertAccountabilityRecordSystem();
	}
	
	@Test
	public void findAccountabilityRecordBy() {
		String customerId ="0000000049f416eb014a03efa25559c2";
		String productId ="402880f04955718b0149558a5717000f";
		accountabilityService.findAccountabilityRecordBy(customerId,productId);
	}

	@Test 
	public void test() {
		String aa="1.5E7";
		Double bb= Double.parseDouble(aa);
		System.out.println(bb);
	}

}
