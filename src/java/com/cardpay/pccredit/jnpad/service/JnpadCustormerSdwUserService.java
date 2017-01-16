package com.cardpay.pccredit.jnpad.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.intopieces.model.AppManagerAuditLog;
import com.cardpay.pccredit.intopieces.model.IntoPieces;
import com.cardpay.pccredit.jnpad.dao.JnpadCustomerSelectDao;
import com.cardpay.pccredit.jnpad.dao.JnpadCustormerSdwUserDao;
import com.cardpay.pccredit.jnpad.model.CustormerSdwUser;
import com.cardpay.pccredit.jnpad.model.JnpadCsSdModel;
import com.cardpay.pccredit.riskControl.model.RiskCustomer;

@Service
public class JnpadCustormerSdwUserService {
	@Autowired
	private JnpadCustormerSdwUserDao SdwUserDao;
	
	
	public int insertCustormerSdwUser(CustormerSdwUser sdwuser){
		return SdwUserDao.insertCustormerSdwUser(sdwuser);
	}
	
	public int insertCsJl(AppManagerAuditLog sdwuser){
		return SdwUserDao.insertCsJl(sdwuser);
	}
	
	public AppManagerAuditLog selectaId(@Param(value = "cid")String cid,@Param(value = "pid")String pid){
		return SdwUserDao.selectaId(cid,pid);
	}
	
	public List<IntoPieces> selectSDH(@Param(value = "userId")String userId){
		return SdwUserDao.selectSDH(userId);
	}
	
	public AppManagerAuditLog selectCSJLA(@Param(value = "id")String id){
		return SdwUserDao.selectCSJLA(id);
	}
	
	public int updateCustormerSdwUser(CustormerSdwUser sdwuser){
		return SdwUserDao.updateCustormerSdwUser(sdwuser);
	}
	
	public int updateCustormerInfoSdwUser(IntoPieces sdwuser){
		return SdwUserDao.updateCustormerInfoSdwUser(sdwuser);
	}
	public int updateCustormerProSdwUser(IntoPieces sdwuser){
		return SdwUserDao.updateCustormerProSdwUser(sdwuser);
	}
	
	public int insertRiskSdwUser(RiskCustomer sdwuser){
		return SdwUserDao.insertRiskSdwUser(sdwuser);
	}
	public int deleteCustormerSdwUser(@Param(value = "CAPID")String CAPID){
		return SdwUserDao.deleteCustormerSdwUser(CAPID);
	}
	public int deleteCsJl(@Param(value = "applicationId")String applicationId){
		return SdwUserDao.deleteCsJl(applicationId);
	}
	
	public JnpadCsSdModel findCsSd(@Param(value = "id")String id){
		return SdwUserDao.findCsSd(id);
	}
	
	public JnpadCsSdModel findCsSdRefuse(@Param(value = "id")String id){
		return SdwUserDao.findCsSdRefuse(id);
	}
	
	public JnpadCsSdModel findUser(@Param(value = "id")String id){
		return SdwUserDao.findUser(id);
	}
	
	public JnpadCsSdModel findCsSdBlack(@Param(value = "id")String id){
		return SdwUserDao.findCsSdBlack(id);
	}
	
	public int selectCount(@Param(value = "id")String id){
		return SdwUserDao.selectCount(id);
	}
}