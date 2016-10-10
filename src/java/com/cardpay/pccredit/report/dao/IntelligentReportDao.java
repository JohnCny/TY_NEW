/**
 * 
 */
package com.cardpay.pccredit.report.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.report.model.IntelligentAccountReport;
import com.cardpay.pccredit.report.model.IntelligentCustomerReport;
import com.cardpay.pccredit.report.model.PostLoanManagementData;
import com.wicresoft.util.annotation.Mapper;

/**
 * @author shaoming
 *
 * 2014年12月22日   下午3:28:30
 */
@Mapper
public interface IntelligentReportDao {
	
	List<IntelligentCustomerReport> findIntelligentCustomerReport();
	
	List<IntelligentAccountReport> findIntelligentAccountReport(@Param("year") int year,@Param("month") int month,@Param("lastYear") int lastYear,@Param("lastMonth") int lastMonth);
	
	PostLoanManagementData findPostLoanManagementData();
}
