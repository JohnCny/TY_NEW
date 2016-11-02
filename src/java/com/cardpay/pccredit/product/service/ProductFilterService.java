package com.cardpay.pccredit.product.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.product.constant.DictTypeConstant;
import com.cardpay.pccredit.product.dao.FilterDictDao;
import com.cardpay.pccredit.product.filter.FilterDictFilter;
import com.cardpay.pccredit.product.model.FilterDict;
import com.cardpay.pccredit.product.model.ProductFilterMap;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.model.QueryResult;

/**
 * ProductFilterService类的描述
 * 
 * @author 王海东
 * @created on 2014-11-24
 * 
 * @version $Id:$
 */
@Service
public class ProductFilterService {
	@Autowired
	private CommonDao commonDao;

	@Autowired
	private FilterDictDao filterDictDao;

	public List<FilterDict> findAllFilterDict() {
		return filterDictDao.findAllFilterDict();
	}

	public String insertFilterDict(FilterDict filterDict) {
		filterDict.setCreatedTime(Calendar.getInstance().getTime());
		filterDict.setModifiedTime(Calendar.getInstance().getTime());
		commonDao.insertObject(filterDict);

		return filterDict.getId();
	}

	public int updateFilterDict(FilterDict filterDict) {
		filterDict.setModifiedTime(Calendar.getInstance().getTime());

		return commonDao.updateObject(filterDict);
	}

	public int deleteFilterDict(String filterId) {
		return commonDao.deleteObject(FilterDict.class, filterId);
	}

	public FilterDict findFilterDictById(String filterId) {
		return commonDao.findObjectById(FilterDict.class, filterId);
	}

	public QueryResult<FilterDict> findFilterDictsByFilter(FilterDictFilter filter) {
		QueryResult<FilterDict> list =commonDao.findObjectsByFilter(FilterDict.class, filter);
		for(FilterDict filterDict:list.getItems()){
			 Map<String,String> map=((Map<String, String>)DictTypeConstant.TypeMap.get(filterDict.getColumnName().toUpperCase()));
			 filterDict.setValue(map.get(filterDict.getValue()));
		}
		return list;
	}

	public List<ProductFilterMap> findProductFilterMapByProductId(String productId) {
		return filterDictDao.findProductFilterMapByProductId(productId);
	}

	public int deleteProductFilterMapByProductId(String productId) {
		return filterDictDao.deleteProductFilterMapByProductId(productId);
	}

	// 插入产品筛选准入规则清单
	public int insertProductFilterMap(ProductFilterMap productFilterMap) {
		productFilterMap.setCreatedTime(new Date());
		return commonDao.insertObject(productFilterMap);
	}
}
