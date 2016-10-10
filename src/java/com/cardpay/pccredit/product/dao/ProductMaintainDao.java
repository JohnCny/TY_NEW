package com.cardpay.pccredit.product.dao;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.product.model.ProductMaintain;
import com.wicresoft.util.annotation.Mapper;


/**
 * ProductMaintainDao类的描述
 *
 * @author 王海东
 * @created on 2014-11-18
 * 
 * @version $Id:$
 */
@Mapper
public interface ProductMaintainDao {
	
	   //根据产品productId查询产品维护规则
		public ProductMaintain findProductMaintainByProductId(@Param("productId")String productId);
		
		//根据产品Id删除营销规则
		public void deleteProductMaintainByProductId(@Param("productId") String productId);

}
