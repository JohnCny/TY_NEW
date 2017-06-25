package com.cardpay.pccredit.jnpad.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.intopieces.model.AppManagerAuditLog;
import com.cardpay.pccredit.intopieces.model.IntoPieces;
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
	IntoPieces selectSDH(@Param(value = "userId")String userId,@Param(value = "capid")String capid);
	//查询当前客户经理审贷通知
		List<IntoPieces> selectSDH1(@Param(value = "userId")String userId);
		//审贷委成员
		IntoPieces sdwcy(@Param(value = "id")String id);
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
	int deleteCustormerSdwUser(@Param(value = "CAPID")String CAPID);
	int deleteCsJl(@Param(value = "applicationId")String applicationId);
	
	
	//查询进件的审贷记录
	JnpadCsSdModel findCsSd(@Param(value = "id")String id);
	
	//查询审贷后拒绝的进件信息
	JnpadCsSdModel findCsSdRefuse(@Param(value = "id")String id);
	//查询审贷后回退的进件信息
	JnpadCsSdModel findCsSdBlack(@Param(value = "id")String id);
	//查询审批人员
	JnpadCsSdModel findUser(@Param(value = "id")String id);
	
	int selectCount(@Param(value = "id")String id);
	
	int selectSDH1Count(@Param(value = "userId")String userId);
	
	int insertCustormerSdwUser1(CustormerSdwUser CustormerSdwUser);
	
	CustormerSdwUser selectSpJy(@Param(value = "id")String id);
	
	//PC
			//查询初审信息
			AppManagerAuditLog selectCSJLAPC(@Param(value = "appId")String appId,@Param(value = "uId")String uId);
			JnpadCsSdModel findCsSds(@Param(value = "appId")String appId,@Param(value ="uId")String uId);
			//查询审贷后拒绝的进件信息
			JnpadCsSdModel findCsSdRefuses(@Param(value = "appId")String appId);
			//查询审贷后回退的进件信息
			JnpadCsSdModel findCsSdBlacks(@Param(value = "appId")String appId);
			//退回进件重新申请时删除初审以及审贷记录。
			int deletePCCustormerSdwUser(@Param(value = "applicationId")String applicationId);
			int deletePCCsJl(@Param(value = "applicationId")String applicationId);
			void deleteByApplicationId(@Param(value = "applicationId")String applicationId);
			//退回
			void updateHistory(@Param(value = "userId")String userId,@Param(value = "times")Date times, @Param(value = "applicationId")String applicationId);
			//拒绝
			void updateHistorys(@Param(value = "userId")String userId,@Param(value = "times")Date times, @Param(value = "applicationId")String applicationId);
			//通过
			void updateCSZTs(@Param(value = "userId")String userId, @Param(value = "times")Date times, @Param(value = "money")String money, @Param(value = "applicationId")String applicationId);
			//初审通过
			void updateCSZT(@Param(value = "userId")String userId, @Param(value = "times")Date times, @Param(value = "money")String money, @Param(value = "applicationId")String applicationId);
			List<JnpadCsSdModel> findCsSdId(String appId);
			JnpadCsSdModel findBySdwId(@Param(value = "sdwId")String sdwId,@Param(value = "appId")String appId);
			JnpadCsSdModel findZzCsSds(@Param(value = "appId")String appId);
			
}			
