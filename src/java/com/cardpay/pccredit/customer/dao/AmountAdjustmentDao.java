package com.cardpay.pccredit.customer.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.customer.filter.AmountAdjustmentFilter;
import com.cardpay.pccredit.customer.model.AmountAdjustmentProcess;
import com.cardpay.pccredit.customer.web.AmountAdjustmentForm;
import com.cardpay.pccredit.customer.web.AmountAdjustmentProcessForm;
import com.wicresoft.util.annotation.Mapper;
/**
 * 
 * 描述 ：客户账户信息
 * @author 张石树
 *
 * 2014-11-5 下午1:50:50
 */
@Mapper
public interface AmountAdjustmentDao {
	
	/**
	 * 根据过滤条件分页
	 * @param filter
	 * @return
	 */
	public List<AmountAdjustmentForm> findAmountAdjustmentByFilter(AmountAdjustmentFilter filter);

	/**
	 * 根据过滤条件统计
	 * @param filter
	 * @return
	 */
	public int findAmountAdjustmentCountByFilter(AmountAdjustmentFilter filter);
	
	/**
	 * 根据过滤条件待审核分页
	 * @param filter
	 * @return
	 */
	public List<AmountAdjustmentForm> findAmountAdjustmentWaitApproveByFilter(AmountAdjustmentFilter filter);

	/**
	 * 根据过滤条件待审核统计
	 * @param filter
	 * @return
	 */
	public int findAmountAdjustmentWaitApproveCountByFilter(AmountAdjustmentFilter filter);

	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	public AmountAdjustmentForm findAmountAdjustmentById(@Param("id") String id);

	/**
	 * 查询待申请的调额信息
	 * @return
	 */
	public List<AmountAdjustmentProcessForm> findWaitProcessAmountAdjustmentAll();

	/**
	 * 更新已申请的调额信息
	 * @param adjustmentProcessForm
	 * @return
	 */
	public int updateWaitProcessAmountAdjustment(
			AmountAdjustmentProcessForm adjustmentProcessForm);

	/**
	 * 查询审批的调额信息
	 * @param id
	 * @return
	 */
	public AmountAdjustmentForm findWaitApproveAmountAdjustById(@Param("id") String id);

	/**
	 * 审批后 修改amountAdjustmentProcess
	 * @param amountAdjustmentProcess
	 */
	public void updateAmountAdjustmentProcess(
			AmountAdjustmentProcess amountAdjustmentProcess);

	/**
	 * 申请审批之后超过riskReviewProcessMaxDay天未审批 释放重新申请
	 * @param riskReviewProcessMaxDay
	 */
	public void autoAfterApplyTimeReleaseApply(String riskReviewProcessMaxDay);
	
}
