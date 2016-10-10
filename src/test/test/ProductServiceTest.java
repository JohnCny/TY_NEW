package test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cardpay.pccredit.product.service.ProductService;


/**
 * ProductServiceTest类的描述
 *
 * @author 王海东
 * @created on 2014-11-4
 * 
 * @version $Id:$
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-junit.xml")
public class ProductServiceTest {
	
	@Autowired
	private  ProductService productService;

	@Test
	public void findProductAttributeByuserIdTest() {
		String user = "00000000494f70560149503eaad700bd";
		productService.findProductAttributeByuserId(user);
	}

}
