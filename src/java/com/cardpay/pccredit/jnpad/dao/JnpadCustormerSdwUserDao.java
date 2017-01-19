package com.cardpay.pccredit.jnpad.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.intopieces.model.AppManagerAuditLog;
import com.cardpay.pccredit.intopieces.model.IntoPieces;
import com.cardpay.pccredit.intopieces.model.IntoPiecesFilters;
import com.cardpay.pccredit.jnpad.model.CustormerSdwUser;
import com.cardpay.pccredit.jnpad.model.JnpadCsSdModel;
import com.cardpay.pccredit.riskControl.model.RiskCustomer;
import com.wicresoft.util.annotation.Mapper;
@Mapper
public interface JnpadCustormerSdwUserDao {
	//添加审贷委
	int insertCustormerSdwUser(CustormerSdwUser sdwuser);
	//添加初审信息
	int insertCsJl(AppManagerAuditLog sdwuser);
	//查询进件ID
	AppManagerAuditLog selectaId(@Param(value = "cid")String cid,@Param(value = "pid")String pid);
	//查询当前客户经理审贷信息
	List<IntoPieces> selectSDH(@Param(value = "userId")String userId);
	
	//查询初审信息
	AppManagerAuditLog selectCSJLA(@Param(value = "id")String id);
	//审贷决议
	int updateCustormerSdwUser(CustormerSdwUser sdwuser);
	
	//进件表注入进件状态
	int updateCustormerInfoSdwUser(IntoPieces sdwuser);
	int updateCustormerProSdwUser(IntoPieces sdwuser);
	//添加风险客户
	int insertRiskSdwUser(RiskCustomer sdwuser);
	
	//退回进件重新申请时删除初审以及审贷记录。
	int deleteCustormerSdwUser(CustormerSdwUser sdwuser);
	int deleteCsJl(AppManagerAuditLog sdwuser);
	
	
	//查询进件的审贷记录
	JnpadCsSdModel findCsSd(@Param(value = "id")String id);
	List<IntoPiecesFilters> selectSDHLists(IntoPiecesFilters filter);
	int selectSDHCountLists(IntoPiecesFilters filter);

}
