package com.cardpay.pccredit.report.dao;

import java.util.List;

import com.cardpay.pccredit.report.filter.WorkQualityFilter;
import com.cardpay.pccredit.report.model.WorkQuality;
import com.wicresoft.util.annotation.Mapper;

/**
 * 
 * @author 季东晓
 *
 * 2014-12-22 上午10:51:21
 */
@Mapper
public interface WorkQualityDao {
	
	
	/**
     * 统计信用卡中心辅助岗位人员工作质量检测报告
     * @param filter
     * @return
     */
	public List<WorkQuality> getCountWorkQuality(WorkQualityFilter filter);
}
