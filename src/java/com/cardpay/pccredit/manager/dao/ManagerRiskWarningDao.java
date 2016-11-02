package com.cardpay.pccredit.manager.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.manager.filter.ManagerRiskWarningFilter;
import com.cardpay.pccredit.manager.web.ManagerRiskWarningForm;
import com.wicresoft.util.annotation.Mapper;

/**
 * ManagerRiskWarningDao类的描述
 * 
 * @author 王海东
 * @created on 2014-11-12
 * 
 * @version $Id:$
 */
@Mapper
public interface ManagerRiskWarningDao {

	/*
	 * 根据filter查询自己创建的客户经理警示书
	 */
	List<ManagerRiskWarningForm> findCreateManagerRiskWarningByFilter(ManagerRiskWarningFilter filter);
	/*
	 * 根据filter查询分配给自己的客户经理警示书
	 */
	List<ManagerRiskWarningForm> findReciveManagerRiskWarningByFilter(ManagerRiskWarningFilter filter);
	

	/*
	 * 根据filter查询自己创建以的客户经理警示书数量
	 */
	public int findCreateManagerRiskWarningCountByFilter(ManagerRiskWarningFilter filter);
	
	/*
	 * 根据filter查询分配给自己的客户经理警示书数量
	 */
	public int findReciveManagerRiskWarningCountByFilter(ManagerRiskWarningFilter filter);


	/*
	 * 根据id修改反馈内容
	 */
	public int updateFeedBackById(@Param("riskId")String riskId,@Param("feedback")String feedback);

}
