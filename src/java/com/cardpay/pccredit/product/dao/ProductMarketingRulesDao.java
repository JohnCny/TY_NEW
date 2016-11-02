package com.cardpay.pccredit.product.dao;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.product.model.ProductMarketingRules;
import com.wicresoft.util.annotation.Mapper;


/**
 * Description of ProductMarketingRulesDao
 *
 * @author 王海东
 * @created on 2014-10-17
 * 
 * @version $Id:$
 */
@Mapper
public interface ProductMarketingRulesDao {
	//根据产品productId查询营销规则
	public ProductMarketingRules findProductMarketingRulesByProductId(String productId);
	
	//根据产品Id删除营销规则
	public void deleteProductMarketingRulesByProductId(@Param("productId") String productId);
	
}
