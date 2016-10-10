/**
 * 
 */
package com.cardpay.pccredit.manager.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.customer.dao.comdao.CustomerInforCommDao;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.manager.dao.ManagerAssessmentScoreDao;
import com.cardpay.pccredit.manager.dao.comdao.ManagerAssessmentScoreCommDao;
import com.cardpay.pccredit.manager.filter.ManagerAssessmentScoreFilter;
import com.cardpay.pccredit.manager.model.ManagerAssessmentScore;
import com.cardpay.pccredit.manager.model.TyManagerAssessment;
import com.cardpay.pccredit.manager.web.ManagerAssessmentScoreForm;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.util.format.DateFormat;

/**
 * 
 * 描述 ：客户经理评估标准service
 * @author 张石树
 *
 * 2014-11-13 下午2:29:45
 */
@Service
public class ManagerAssessmentScoreService {
	
	private Logger logger = Logger.getLogger(ManagerAssessmentScoreService.class);

	@Autowired
	private ManagerAssessmentScoreDao managerAssessmentScoreDao;
	
	@Autowired
	private CommonDao commonDao;
	
	@Autowired 
	private CustomerInforCommDao customerInforCommDao;
	
	@Autowired
	private ManagerAssessmentScoreCommDao managerAssessmentScoreCommDao;

	/**
	 * 分页查询
	 * @param filter
	 * @return
	 */
	public QueryResult<TyManagerAssessment> findManagerAssessmentScoreByFilter(
			ManagerAssessmentScoreFilter filter) {
		List<TyManagerAssessment> assessmentScoreForms = managerAssessmentScoreDao.findManagerAssessmentScoreByFilter(filter);
		int size = managerAssessmentScoreDao.findManagerAssessmentScoreCountByFilter(filter);
		QueryResult<TyManagerAssessment> qs = new QueryResult<TyManagerAssessment>(size, assessmentScoreForms);
		return qs;
	}
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public TyManagerAssessment findManagerAssessmentScoreById(String id){
		return managerAssessmentScoreDao.findManagerAssessmentScoreById(id);
	}

	/**
	 * 更新评估信息
	 * @param managerAssessmentScore
	 */
	public void updateManagerAssessmentScore(
			ManagerAssessmentScore managerAssessmentScore) {
		commonDao.updateObject(managerAssessmentScore);
	}
	
	/**
	 * 统计客户经理已授信额度
	 * @param managerId
	 * @return
	 */
	public Double getManagerApplyQuota(String managerId){
		List<CustomerInfor> customerInfors = customerInforCommDao.findCustomerByManagerId(managerId);
		if(customerInfors != null && customerInfors.size() > 0){
			List<String> customerIds = new ArrayList<String>();
			for(CustomerInfor infor : customerInfors){
				customerIds.add(infor.getId());
			}
			return managerAssessmentScoreCommDao.sumAppApplyQuota(customerIds);
		} else {
			return 0.0;
		}
	}
	/**
	 * 保存评估
	 * @param managerAssessmentScore
	 * @throws ParseException 
	 */
	public void saveAssessmentScore(HttpServletRequest request) throws ParseException {
		String assessId = request.getParameter("assessId");
		String remark = request.getParameter("remark");
		String totalScore = request.getParameter("totalScore");
		String score = request.getParameter("avgScore");
		String createTime = request.getParameter("createTime");
		String createUser = request.getParameter("createUser");
		TyManagerAssessment assessment = commonDao.findObjectById(TyManagerAssessment.class, assessId);
		assessment.setRemark(remark);
		assessment.setTotalScore(totalScore);
		assessment.setScore(score);
		assessment.setCreateUser(createUser);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟  
		java.util.Date date=sdf.parse(createTime); 
		assessment.setCreateTime(date);
		commonDao.updateObject(assessment);
	}
	
	/*
	 * 根据年月获取评估结果
	 */
	public List<TyManagerAssessment> getAssessByMonth(int year,int month){
		String sql = "select * from ty_manager_assessment where year='"+year+"' and month='"+month+"'";
		return commonDao.queryBySql(TyManagerAssessment.class, sql, null);
	}
}
