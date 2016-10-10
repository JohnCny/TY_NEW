/**
 * 
 */
package com.cardpay.pccredit.ipad.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.ipad.model.CustomerInforIpad;
import com.wicresoft.util.annotation.Mapper;

/**
 * @author shaoming
 *
 * 2014年12月2日   下午3:01:20
 */
@Mapper
public interface CustomerInforIpadDao {
	List<CustomerInforIpad> getCustomerInforByUserId(@Param("userId") String userId,@Param("page") int currentPage,@Param("limit") int pageSize);
	
	int getCustomerInforCountByUserId(@Param("userId") String userId);
	
	CustomerInforIpad findCustomerInforById(@Param("id") String id);
	
	CustomerInforIpad findCustomerInforByCardId(@Param("id") String id);
}
