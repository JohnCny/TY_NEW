package com.cardpay.pccredit.manager.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.wicresoft.util.annotation.Mapper;

@Mapper
public interface StatisticsManagerDao {

	//统计客户经理下的客户所有授信额度
	Integer findCustomerApplyQuota(@Param("startTime")String startTime,@Param("endTime")String endTime,@Param("customerManagerId")String customerManagerId);
	
	Integer findCountCustomer(@Param("startTime")String startTime,@Param("endTime")String endTime,@Param("customerManagerId")String customerManagerId);
	
	List<Map<String, Object>> findVisitCustomerActionCount(@Param("startTime")String startTime,@Param("endTime")String endTime,@Param("customerManagerId")String customerManagerId);

	Integer findCountVisitCustomer(@Param("startTime")String startTime,@Param("endTime")String endTime,@Param("customerManagerId")String customerManagerId);
	
	Integer findCountVisitCustomerYw(@Param("startTime")String startTime,@Param("endTime")String endTime,@Param("customerManagerId")String customerManagerId);

	/** 统计风险警示书*/
	Integer findRiskWarningByManagerId(@Param("customerManagerId")String customerManagerId);
	
	/** 统计问责信息*/
	Integer findAccountabilityRecordByManagerId(@Param("customerManagerId")String customerManagerId);
	
	/** 产品发布*/
	Integer findProductByManagerId(@Param("customerManagerId")String customerManagerId);

}
