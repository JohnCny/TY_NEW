package com.cardpay.pccredit.jnpad.dao;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.intopieces.model.LocalExcel;
import com.wicresoft.util.annotation.Mapper;
@Mapper
public interface JnIpadLocalExcelDao {
	public LocalExcel findByApplication(@Param("customerId") String customerId,@Param("productId") String productId);
	void updateLo(@Param("applicationId") String applicationId,@Param("id") String id);
}
