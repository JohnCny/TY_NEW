package com.cardpay.pccredit.product.service.test;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cardpay.pccredit.customer.model.CustomerCareersWeb;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.service.CustomerCareersService;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.dimensional.model.Dimensional;
import com.cardpay.pccredit.dimensional.service.DimensionalService;
import com.cardpay.pccredit.manager.service.ManagerBelongMapService;
import com.cardpay.pccredit.manager.web.AccountManagerParameterForm;
import com.cardpay.pccredit.product.service.ProductService;
import com.cardpay.pccredit.riskControl.service.NplsInfomationService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-junit.xml")
public class ProductTest {
	
	@Resource
	private ProductService productService;
	
	@Autowired
	private CustomerInforService customerInforService;
	
	@Autowired
	private DimensionalService dimensionalService;
	
	@Autowired
	private CustomerCareersService customerCareersService;
	
	@Autowired
	private ManagerBelongMapService managerBelongMapService;
	
	@Autowired
	private NplsInfomationService nplsInfomationService;
	
	@Test
	public void insertScreeningResults(){
		productService.insertScreeningResults("402881e749126cba0149126d5cc40001", null);
	}
	
	@Test
	public void cloneCustomerInfoBySubmitApp(){
		customerInforService.insertCustomerInfoBySubmitApp("40288169493b1c0c01493b853abf000a", "00000","");
	}
	
	@Test
	public void findCloneCustomerInfoByAppId(){
		CustomerInfor customerInfor = customerInforService.findCloneCustomerInforByAppId("1000");
		System.out.println(customerInfor);
	}
	
	@Test
	public void findDimensionalByAppId(){
		Dimensional dimensional = dimensionalService.findDimensionalByAppId("1000");
		System.out.println(dimensional);
	}
	
	@Test
	public void findCustomerCareersByAppId(){
		CustomerCareersWeb dimensional = customerCareersService.findCustomerCareersByAppId("1000");
		System.out.println(dimensional);
	}
	
	@Test
	public void managerBelongMapService(){
		List<AccountManagerParameterForm> accountManagerParameterForms =
				managerBelongMapService.findSubManagerBelongMapByManagerId("000000004978caec014979962f160030");
		System.out.println(accountManagerParameterForms.size());
	}
	
	@Test
	public void addNplsInfomation(){
		nplsInfomationService.addNplsInfomation();
	}
	
}
