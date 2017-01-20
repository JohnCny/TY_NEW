package com.cardpay.pccredit.jnpad.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.intopieces.model.AppManagerAuditLog;
import com.cardpay.pccredit.intopieces.model.IntoPieces;
import com.cardpay.pccredit.intopieces.model.IntoPiecesFilters;
import com.cardpay.pccredit.jnpad.dao.JnpadCustomerSelectDao;
import com.cardpay.pccredit.jnpad.dao.JnpadCustormerSdwUserDao;
import com.cardpay.pccredit.jnpad.model.CustormerSdwUser;
import com.cardpay.pccredit.jnpad.model.JnpadCsSdModel;
import com.cardpay.pccredit.riskControl.model.RiskCustomer;
import com.cardpay.pccredit.zrrtz.model.IncomingData;
import com.wicresoft.jrad.base.database.model.QueryResult;

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
	//PC
	public AppManagerAuditLog selectCSJLAPC(@Param(value = "appId")String appId){
		return SdwUserDao.selectCSJLAPC(appId);
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
	public int deleteCustormerSdwUser(CustormerSdwUser sdwuser){
		return SdwUserDao.deleteCustormerSdwUser(sdwuser);
	}
	public int deleteCsJl(AppManagerAuditLog sdwuser){
		return SdwUserDao.deleteCsJl(sdwuser);
	}
	
	public JnpadCsSdModel findCsSd(@Param(value = "id")String id){
		return SdwUserDao.findCsSd(id);
	}
	
	public QueryResult<IntoPiecesFilters> selectSDHs(IntoPiecesFilters filter) {
		// TODO Auto-generated method stub
		List<IntoPiecesFilters> plans = SdwUserDao.selectSDHLists(filter);
		int size = SdwUserDao.selectSDHCountLists(filter);
		QueryResult<IntoPiecesFilters> queryResult = new QueryResult<IntoPiecesFilters>(size,plans);
		return queryResult;
	}
	public JnpadCsSdModel findCsSds(@Param(value ="appId")String appId){
		return SdwUserDao.findCsSds(appId);
	}
}
