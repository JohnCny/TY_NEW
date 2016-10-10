package test;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cardpay.pccredit.intopieces.service.CustomerApplicationIntopieceWaitService;


/**
 * CustomerApplicationIntopieceWaitServiceTest类的描述
 *
 * @author 王海东
 * @created on 2014-11-28
 * 
 * @version $Id:$
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-junit.xml")
public class CustomerApplicationIntopieceWaitServiceTest {

	@Autowired
	private CustomerApplicationIntopieceWaitService customerApplicationIntopieceWaitService;
	@Test
	public void test() throws SQLException {
		//customerApplicationIntopieceWaitService.("000001");
	}

}
