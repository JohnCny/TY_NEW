package com.cardpay.pccredit.report.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.report.dao.ReviewRiskControlDao;
import com.cardpay.pccredit.report.filter.StatisticalFilter;
import com.cardpay.pccredit.report.model.ReviewRiskControl;

/**
 * @author chenzhifang
 *
 * 2014-12-22下午5:48:18
 */
@Service
public class ReviewRiskControlService {
	@Autowired
	private ReviewRiskControlDao reviewRiskControlDao;
	
	/**
     * 统计评审岗位人员风控状况
     * @param filter
     * @return
     */
	public List<ReviewRiskControl> getReviewRiskControl(StatisticalFilter filter){
		return reviewRiskControlDao.getReviewRiskControl(filter);
	}
}
