package com.cardpay.pccredit.product.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.product.model.ManagerProductsConfiguration;
import com.wicresoft.util.annotation.Mapper;

/**
 * ManagerProductsConfigurationDao类的描述
 * 
 * @author 王海东
 * @created on 2014-11-10
 * 
 * @version $Id:$
 */
@Mapper
public interface ManagerProductsConfigurationDao {

	// 根据产品productId查询客户经理-产品-参数配置
	public List<ManagerProductsConfiguration> findManagerProductsConfigurationByProductId(String productId);

	// 根据产品Id删除客户经理-产品-参数配置
	public void deleteManagerProductsConfigurationByProductId(@Param("productId") String productId);
	
	//根据产品Id和客户经理层级查询
	public ManagerProductsConfiguration findConfigurationByProductIdAndLevel(@Param("productId") String productId,
			@Param("customerManagerLevel") String customerManagerLevel);

}
