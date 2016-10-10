package com.cardpay.pccredit.product.dao;

import java.util.List;

import com.cardpay.pccredit.product.model.AppendixDict;
import com.wicresoft.util.annotation.Mapper;

/**
 * Description of AppendixDictDao
 * 
 * @author 王海东
 * @created on 2014-10-14
 * 
 * @version $Id:$
 */
@Mapper
public interface AppendixDictDao {
	//查询产品附件数据字典
	public List<AppendixDict> findAllAppendix();

}
