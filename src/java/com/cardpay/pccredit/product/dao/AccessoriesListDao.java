package com.cardpay.pccredit.product.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.intopieces.model.AppManagerAuditLog;
import com.cardpay.pccredit.product.model.AccessoriesList;
import com.cardpay.pccredit.product.model.AppendixDict;
import com.wicresoft.util.annotation.Mapper;

/**
 * AppendixDictDao
 * 
 * @author 王海东
 * @created on 2014-10-14
 * 
 * @version $Id:$
 */
@Mapper
public interface AccessoriesListDao {
	//根据产品Id查询产品附件清单
	public List<AccessoriesList> findAppendixTypeCodeByProductId(String productId);
	
	//根据产品productId查询产品附件
	public List<AppendixDict> findAppendixByProductId(@Param("productId") String productId);
	
	//根据productId删除产品附件
	public void deleteAppendixByProductId(@Param("productId") String productId);
	
	public List<AppManagerAuditLog> findAppManagerAuditLog(@Param("appId") String appId,@Param("auditType") String auditType);
	
	public List<AppManagerAuditLog> findAppManagerAuditById(@Param("appId") String appId);
	

}
