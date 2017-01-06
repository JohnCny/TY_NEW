package com.cardpay.pccredit.customer.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.customer.model.CustomerParameter;
import com.cardpay.pccredit.customer.model.ParameterInformaion;
import com.cardpay.pccredit.intopieces.model.IntoPieces;
import com.cardpay.pccredit.ipad.model.UserIpad;
import com.cardpay.pccredit.product.model.ProductAttribute;
import com.wicresoft.util.annotation.Mapper;

@Mapper
public interface ICustomerParameterDao {

	public void addCustomerParameter(CustomerParameter cp);
	
	public List<IntoPieces> query();

	public List<ProductAttribute> queryProduct();

	public List<ParameterInformaion> query(@Param("id")String id);

	public List<UserIpad> queryAllManager();

	public List<CustomerParameter> queryByIdCard(@Param("idcard")String idcard);


}
