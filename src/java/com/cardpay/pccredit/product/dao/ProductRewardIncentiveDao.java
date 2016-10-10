package com.cardpay.pccredit.product.dao;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.product.model.ProductRewardIncentive;
import com.wicresoft.util.annotation.Mapper;

/**
 * ProductRewardIncentiveDao类的描述
 * 
 * @author 王海东
 * @created on 2014-11-10
 * 
 * @version $Id:$
 */
@Mapper
public interface ProductRewardIncentiveDao {

	// 根据产品productId查询产品奖励激励参数表
	public ProductRewardIncentive findProductRewardIncentiveByProductId(String productId);

	// 根据产品Id删除产品奖励激励参数表
	public void deleteProductRewardIncentiveByProductId(@Param("productId") String productId);

}
