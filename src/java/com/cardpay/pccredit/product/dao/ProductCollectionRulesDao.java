package com.cardpay.pccredit.product.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.product.model.ProductCollectionRules;
import com.cardpay.pccredit.product.web.ProductCollectionRulesForm;
import com.wicresoft.util.annotation.Mapper;

/**
 * ProductCollectionRulesDao类的描述
 * 
 * @author 王海东
 * @created on 2014-11-10
 * 
 * @version $Id:$
 */
@Mapper
public interface ProductCollectionRulesDao {
	// 根据产品productId查询产品催收规则
	public List<ProductCollectionRules> findProductCollectionRulesByProductId(String productId);

	// 根据产品Id删除产品催收规则
	public void deleteProductCollectionRulesByProductId(@Param("productId") String productId);
	
	//查询所有产品营销规则
	public List<ProductCollectionRules> findAllProductCollectionRules();

}
