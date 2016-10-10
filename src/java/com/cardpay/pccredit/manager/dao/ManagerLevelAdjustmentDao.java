package com.cardpay.pccredit.manager.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.manager.filter.BatchRunFilter;
import com.cardpay.pccredit.manager.filter.ManagerLevelAdjustmentFilter;
import com.cardpay.pccredit.manager.model.AccountManagerParameter;
import com.cardpay.pccredit.manager.model.BatchTask;
import com.cardpay.pccredit.manager.web.ManagerLevelAdjustmentForm;
import com.wicresoft.util.annotation.Mapper;

/**
 * 
 * 描述 ：客户经理评估Dao
 * @author 张石树
 *
 * 2014-11-13 下午2:37:17
 */
@Mapper
public interface ManagerLevelAdjustmentDao {

	/**
	 * 分页查询信息
	 * @param filter
	 * @return
	 */
	List<ManagerLevelAdjustmentForm> findManagerLevelAdjustmentByFilter(ManagerLevelAdjustmentFilter filter);

	/**
	 * 分页查询统计 
	 * @param filter
	 * @return
	 */
	int findManagerLevelAdjustmentCountByFilter(ManagerLevelAdjustmentFilter filter);

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	ManagerLevelAdjustmentForm findManagerLevelAdjustmentById(@Param("id") String id);
	
	
	/**
	 * 根据user_id
	 * @param userId
	 * @return 
	 */
	AccountManagerParameter findAccountManagerByUserId(@Param("userId") String userId);
	
	List<BatchTask> findBatchListFilter(BatchRunFilter filter);
	int findBatchCountFilter(BatchRunFilter filter);
}
