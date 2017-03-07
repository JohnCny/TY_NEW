package com.cardpay.pccredit.intopieces.dao;

import java.util.List;

import com.cardpay.pccredit.intopieces.filter.CustomerApplicationProcessFilter;
import com.cardpay.pccredit.intopieces.filter.IntoPiecesFilter;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationInfo;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationProcess;
import com.cardpay.pccredit.intopieces.model.IntoPiecesFilters;
import com.cardpay.pccredit.intopieces.web.CustomerApplicationIntopieceWaitForm;
import com.wicresoft.util.annotation.Mapper;

/**
 * CustomerApplicationIntopieceWaitDao类的描述
 * 
 * @author 王海东
 * @created on 2014-11-28
 * 
 * @version $Id:$
 */
@Mapper
public interface CustomerApplicationIntopieceWaitDao {

	// 查询所有客户进件信息
	public List<CustomerApplicationIntopieceWaitForm> findCustomerApplicationIntopieceWaitForm(CustomerApplicationProcessFilter filter);

	// 查询所有客户进件数量
	public int findCustomerApplicationIntopieceWaitCountForm(CustomerApplicationProcessFilter filter);

	// 根据serialNumber更新中间表customer_application_process
	public int updateCustomerApplicationProcessBySerialNumber(CustomerApplicationProcess customerApplicationProcess);

	public int updateCustomerApplicationInfo(CustomerApplicationInfo customerApplicationInfo);

	// 根据客户信息查询有没有审核的进件
	public Integer getCustomerApplicationInfoByUserId(String userId);

	public List<CustomerApplicationProcess> findCustomerApplicationInfoAll();

	public int updateCustomerApplicationProcess(CustomerApplicationProcess customerApplicationProcess);

	// 根据serialNumber查询CUSTOMER_APPLICATION_PROCESS表
	public CustomerApplicationIntopieceWaitForm findCustomerApplicationProcessBySerialNumber(String serialNumber);

	/**
	 * 申请审批之后超过riskReviewProcessMaxDay天未审批 释放重新申请
	 * @param riskReviewProcessMaxDay
	 */
	public void autoAfterApplyTimeReleaseApply(String riskReviewProcessMaxDay);
	
	public List<CustomerApplicationIntopieceWaitForm> findNotEqualsActualAndFinalAmount();
	
	
	public List<CustomerApplicationIntopieceWaitForm> findCustomerApplicationIntopieceDecisionForm(IntoPiecesFilter filter);
	public int findCustomerApplicationIntopieceDecisionCountForm(IntoPiecesFilter filter);
	
	public List<IntoPiecesFilters> findCustomerApplicationIntopieceDecisionForms(IntoPiecesFilters filter);
	public int findCustomerApplicationIntopieceDecisionCountForms(IntoPiecesFilters filter);
	
	public List<IntoPiecesFilters> findCustomerApplicationIntopieceDecisionFormes(IntoPiecesFilters filter);
	public int findCustomerApplicationIntopieceDecisionCountFormes(IntoPiecesFilters filter);
	
	public CustomerApplicationProcess findByAppId(String appId);
}
