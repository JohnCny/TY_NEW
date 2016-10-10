package com.cardpay.pccredit.jnpad.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.intopieces.filter.IntoPiecesFilter;
import com.cardpay.pccredit.intopieces.model.AppManagerAuditLog;
import com.cardpay.pccredit.intopieces.web.CustomerApplicationIntopieceWaitForm;
import com.cardpay.pccredit.jnpad.model.ManagerInfoForm;
import com.cardpay.pccredit.jnpad.model.ProductAttributes;
import com.wicresoft.util.annotation.Mapper;
@Mapper
public interface JnpadIntopiecesDecisionDao {
	//查询当前客户经理进件初审任务
	public List<CustomerApplicationIntopieceWaitForm> findCustomerApplicationIntopieceDecisionForm(
			IntoPiecesFilter filter) ;
	//查询当前客户经理进件初审任务数量
	public int findCustomerApplicationIntopieceDecisionCountForm(IntoPiecesFilter filter);

	//查询客户经理姓名、id、和工号
	public List<ManagerInfoForm> findManagerInfo();
	
	//查询参与初审客户经理
	public AppManagerAuditLog findAppManagerAuditLog(@Param("appId") String appId,@Param("auditType") String auditType);

	//查询产品列表
	public List<ProductAttributes> findProductList();
	
	//查询审批老师列表
	public List<ManagerInfoForm> findteacherInfo();

}
