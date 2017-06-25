package com.cardpay.pccredit.jnpad.service;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.jnpad.dao.JnpadSpUserDao;
import com.cardpay.pccredit.jnpad.model.CustomerSpUser;
import com.cardpay.pccredit.system.service.NodeAuditService;
import com.cardpay.workflow.models.WfProcessRecord;
import com.cardpay.workflow.models.WfStatusQueueRecord;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;

@Service
public class JnpadSpUserService {
	@Autowired
	private CommonDao commonDao;
	@Autowired
	private JnpadSpUserDao UserDao;
	
	public int addSpUser(CustomerSpUser CustomerSpUser){
		return UserDao.addSpUser(CustomerSpUser);
	}
	public int addSpUser1(CustomerSpUser CustomerSpUser){
		return UserDao.addSpUser1(CustomerSpUser);
	}
	public List<CustomerSpUser>findspUser(){
		return UserDao.findspUser();
	}
	public List<CustomerSpUser>findspUser2(@Param("capid") String capid){
		return UserDao.findspUser2(capid);
	}
	public CustomerSpUser selectUser(@Param("id") String id){
		return UserDao.selectUser(id);
	}
	public List<CustomerSpUser>findspUser3(@Param("capid") String capid){
		return UserDao.findspUser3(capid);
	}
	public List<CustomerSpUser>selectUser1(@Param("id") String id){
		return UserDao.selectUser1(id);
	}
	public void examine(String appId,String wfProcessRecordID,String exUserID,String exResult,String exAmount,String productId,String auditType){
		//进入下一流转之前 先更新当前流转
		WfProcessRecord wfProcessRecord = commonDao.findObjectById(WfProcessRecord.class, wfProcessRecordID);
		WfStatusQueueRecord wfStatusQueueRecord = commonDao.findObjectById(WfStatusQueueRecord.class,wfProcessRecord.getWfStatusQueueRecord());
		wfStatusQueueRecord.setExamineUser(exUserID);
		wfStatusQueueRecord.setExamineResult(exResult);
		wfStatusQueueRecord.setExamineAmount(exAmount);
		wfStatusQueueRecord.setStartExamineTime(new Date());
		wfStatusQueueRecord.setId(wfProcessRecord.getWfStatusQueueRecord());
		commonDao.updateObject(wfStatusQueueRecord);
		//UserDao.updateObject(wfStatusQueueRecord);
	}
	public void  deleteSpUser(@Param("capid") String capid){
		UserDao.deleteSpUser(capid);
	}
	public List<CustomerSpUser>findSpHjy(@Param("capid") String capid,@Param("userid") String userid){
		return UserDao.findSpHjy(capid,userid);
	}
	public List<CustomerSpUser>findUserResult(@Param("capid") String capid){
		return UserDao.findUserResult(capid);
	}
	
	public List<CustomerSpUser>findUserResult1(@Param("capid") String capid){
		return UserDao.findUserResult1(capid);
	}
}
