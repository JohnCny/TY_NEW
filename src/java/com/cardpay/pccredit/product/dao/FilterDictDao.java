package com.cardpay.pccredit.product.dao;

import java.util.List;

import com.cardpay.pccredit.product.model.AccessoriesList;
import com.cardpay.pccredit.product.model.FilterDict;
import com.cardpay.pccredit.product.model.ProductFilterMap;
import com.wicresoft.util.annotation.Mapper;


/**
 * FilterDictDao类的描述
 *
 * @author 王海东
 * @created on 2014-11-26
 * 
 * @version $Id:$
 */
@Mapper
public interface FilterDictDao {
	//查询所有产品筛选规则
	public List<FilterDict> findAllFilterDict();
	
	//根据产品Id查询产品筛选规则清单
	public List<ProductFilterMap> findProductFilterMapByProductId(String productId);
	//根据产品id删除产品筛选准入规则中间表
	public int deleteProductFilterMapByProductId(String productId);
}


