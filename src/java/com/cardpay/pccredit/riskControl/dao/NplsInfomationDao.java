package com.cardpay.pccredit.riskControl.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.riskControl.filter.NplsInfomationFilter;
import com.cardpay.pccredit.riskControl.web.NplsInfomationForm;
import com.wicresoft.util.annotation.Mapper;

/**
 * 
 * 描述 ：不良资产dao
 * @author 张石树
 *
 * 2014-11-4 上午9:48:07
 */
@Mapper
public interface NplsInfomationDao {

	/**
	 * 分页查询
	 * @param filter
	 * @return
	 */
	List<NplsInfomationForm> findNplsInfomationByFilter(NplsInfomationFilter filter);

	/**
	 * 统计
	 * @param filter
	 * @return
	 */
	int findNplsInfomationCountByFilter(NplsInfomationFilter filter);

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	NplsInfomationForm findNplsInfomationById(@Param("id") String id);
	
	/**
	 * 统计 状态为创建或确认
	 * @param customerId
	 * @param accountId
	 * @return
	 */
	int findNplsInfomationCountBy(@Param("customerId")String customerId, @Param("accountId")String accountId);
	/**
	 * 得到客户经理名下核销客户数量
	 * @param id
	 * @return
	 */
	int findNplsInformationCountById(@Param("id") String id);
}
