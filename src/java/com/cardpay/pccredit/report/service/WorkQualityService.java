package com.cardpay.pccredit.report.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.report.dao.WorkQualityDao;
import com.cardpay.pccredit.report.filter.WorkQualityFilter;
import com.cardpay.pccredit.report.model.WorkQuality;
/**
 * 
 * @author 季东晓
 *
 * 2014-12-22 上午11:20:37
 */
@Service
public class WorkQualityService {
	
	@Autowired
	private WorkQualityDao workQualityDao;
	
	/**
     * 统计信用卡中心辅助岗位人员工作质量检测报告
     * @param filter
     * @return
     */
	public List<WorkQuality> getCountWorkQuality(WorkQualityFilter filter){
		
		return workQualityDao.getCountWorkQuality(filter);
	}

}
