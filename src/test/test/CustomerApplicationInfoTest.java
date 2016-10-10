package test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cardpay.pccredit.intopieces.model.CustomerApplicationInfo;
import com.cardpay.pccredit.intopieces.service.CustomerApplicationInfoService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-junit.xml")
public class CustomerApplicationInfoTest {

	@Resource
	private CustomerApplicationInfoService customerApplicationInfoService;

	@Test
	public void saveCustomerApplicationInfo() {
		CustomerApplicationInfo customerApplicationInfo = new CustomerApplicationInfo();
		customerApplicationInfo.setStatus("审核不通过");
		customerApplicationInfoService.save(customerApplicationInfo);
	}

	@Test
	public void deleteCustomerApplicationInfo() {
		customerApplicationInfoService.delete("402881e64907861c014907861c6c0000");
	}

}
