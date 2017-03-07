package com.cardpay.pccredit.manager.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.customer.model.TyMibusidataForm;
import com.cardpay.pccredit.manager.filter.ManagerSalaryFilter;
import com.cardpay.pccredit.manager.model.ManagerSalary;
import com.cardpay.pccredit.manager.model.ManagerSalaryForm;
import com.cardpay.pccredit.manager.model.SalaryParameter;
import com.cardpay.pccredit.manager.model.TJxParameters;
import com.cardpay.pccredit.manager.web.ManagerBelongMapForms;
import com.wicresoft.util.annotation.Mapper;

/**
 * @author chenzhifang
 *
 * 2014-11-14下午5:55:52
 */
@Mapper
public interface ManagerSalaryDao {
	/**
	 * 过滤查询
	 * @param filter
	 * @return
	 */
	public List<ManagerSalary> findManagerSalarysByFilter(ManagerSalaryFilter filter);
	/**
	 * 导出excel
	 * @param filter
	 * @return
	 */
	public List<ManagerSalary> findManagerSalarys(ManagerSalaryFilter filter);
	
	/**
	 * 统计记录数
	 * @param filter
	 * @return
	 */
	public int findManagerSalarysCountByFilter(ManagerSalaryFilter filter);
	
	/**
	 * 获取客户经理最大层数
	 * @return
	 */
	public int getMaxManagerLevel();
	
	/**
	 * 过滤查询
	 * @param filter
	 * @return
	 */
	public List<SalaryParameter> findSalaryParametersByFilter(@Param("level")int level,@Param("year")int year, @Param("month")int month);
	
	/**
	 * 获取客户经理管理绩效
	 * @param managerId
	 * @param year
	 * @param month
	 * @return
	 */
	public String getManagePerformance(@Param("managerId")String managerId, @Param("year")int year, @Param("month")int month);
	
	/**
	 * 通过年份和月份删除客户经理工资信息
	 * @param year
	 * @param month
	 * @return
	 */
	public int deleteManagerSalaryByYearAndMonth(@Param("year")int year, @Param("month")int month);
	/**
	 * 批量插入客户经理薪资
	 * @param list
	 * @return
	 */
	public int batchInsertManagerSalary(List<ManagerSalary> list);
	/**
	 * 得到风险保证余额
	 * @param year
	 * @param month
	 * @param id
	 * @return
	 */
	public String getReturnPrepareAmountById(@Param("year") int year,@Param("month") int month,@Param("id") String id);
	/**
	 * 得到客户经理绩效工资
	 * @param year
	 * @param month
	 * @param id
	 * @return
	 */
	public String getRewardIncentiveInformation(@Param("year") int year,@Param("month") int month,@Param("id") String id);
	
	
	//济南绩效
	public List<ManagerSalaryForm> findManagerSalarysByFilterJn(ManagerSalaryFilter filter);
	public int findManagerSalarysCountByFilterJn(ManagerSalaryFilter filter);
	public String getOrganName(@Param("managerId") String managerId);
	public int findManagerSalaryCount(@Param("year") String  year, @Param("month") String month);
	
	public int findXbCountByManagerId(@Param("managerId") String  managerId,
									  @Param("year")String year,
									  @Param("month")String month);
	
	
	List<Map<String, Object>> findProdLimitAndType(@Param("customerId")String customerId,
												   @Param("year")String year,
												   @Param("month")String month);
	
	List<Map<String, Object>> findDistinctBusicode(@Param("tyCustomerId")String tyCustomerId,
												   @Param("year")String year,
												   @Param("month")String month);
	
	public List<ManagerSalaryForm> findManagerSalaryForm(ManagerSalaryFilter filter);
	
	
	/**
	 * 风险岗
	 * 查询当月参与审贷会审议通过笔数
	 */
	public int findSdAprovedCountByManagerId(@Param("managerId") String  managerId,
									  		 @Param("year")String year,
									  		 @Param("month")String month);
	public void insertjx(TJxParameters jxParameters);
	
	//太原绩效
		/**
		 *根据managerId查询客户经理手下放款的情况
		 * @param month 
		 * @param year 
		 */
		public List<TyMibusidataForm> findffjebyManagerId(@Param("managerId") String  managerId,
													  		 @Param("year")String year,
													  		 @Param("month")String month);
		
		public void updateTJxParameters(@Param("zhmonthLoanNum")String zhmonthLoanNum,@Param("managerId") String managerId);
		
		public String findrwjebyManagerId(@Param("managerId") String  managerId,
												  		 @Param("year")String year,
												  		 @Param("month")String month);
		
		public void deletejxparametersbyuserid(@Param("userId")String userId);
		
		public int insertjxs(TJxParameters jxParameters);
		
		public String findblsum(@Param("managerId")String managerId);
		
		public String findDkyesum(@Param("managerId")String managerId);
		
		public List<ManagerBelongMapForms> findchildrenbymanagerid(@Param("managerId")String managerId);
		
		public int findchildrenbyparentid(@Param("childId")String childId);
		public void deleteSalarybyuserid(@Param("managerId")String managerId);
}
