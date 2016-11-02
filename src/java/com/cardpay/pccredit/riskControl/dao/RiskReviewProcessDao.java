package com.cardpay.pccredit.riskControl.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.riskControl.filter.RiskReviewProcessFilter;
import com.cardpay.pccredit.riskControl.model.RiskReviewProcess;
import com.wicresoft.util.annotation.Mapper;

/**
 * @author chenzhifang
 *
 * 2014-11-5下午4:34:53
 */
@Mapper
public interface RiskReviewProcessDao {

	/**
	 * 过滤查询
	 * @param filter
	 * @return
	 */
	public List<RiskReviewProcess> findRiskReviewProcessByFilter(RiskReviewProcessFilter filter);
	
	/**
	 * 统计风险事项审核流程表记录数
	 * @param filter
	 * @return
	 */
	public int findRiskReviewProcessCountByFilter(RiskReviewProcessFilter filter);
	
	/**
	 * 把机构主管拒绝后超过指定天数的记录改为可直接上报卡中心
	 * @return int
	 */
	public int updateStatusToUnreportedCardcenter(@Param("maxDay")String maxDay);
}
