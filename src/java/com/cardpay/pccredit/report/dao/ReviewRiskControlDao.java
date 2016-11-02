package com.cardpay.pccredit.report.dao;

import java.util.List;

import com.cardpay.pccredit.report.filter.StatisticalFilter;
import com.cardpay.pccredit.report.model.ReviewRiskControl;
import com.wicresoft.util.annotation.Mapper;

/**
 * @author chenzhifang
 *
 * 2014-12-22下午5:48:58
 */
@Mapper
public interface ReviewRiskControlDao {
	/**
     * 统计评审岗位人员风控状况
     * @param filter
     * @return
     */
	public List<ReviewRiskControl> getReviewRiskControl(StatisticalFilter filter);
}
