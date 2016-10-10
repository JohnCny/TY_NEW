package com.cardpay.pccredit.intopieces.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.intopieces.filter.AddIntoPiecesFilter;
import com.cardpay.pccredit.intopieces.model.LocalExcel;
import com.cardpay.pccredit.intopieces.web.LocalExcelForm;
import com.wicresoft.util.annotation.Mapper;

@Mapper
public interface LocalExcelDao {
	public List<LocalExcelForm> findByProductAndCustomer(AddIntoPiecesFilter filter);
	public List<LocalExcelForm> findByProductAndCustomer1(AddIntoPiecesFilter filter);
	public LocalExcel findById(@Param("id") String id);
	public int findCountByProductAndCustomer(AddIntoPiecesFilter filter);
	public LocalExcel findByApplication(@Param("applicationId") String applicationId);
	
}