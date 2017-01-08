package com.cardpay.pccredit.jnpad.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.customer.model.CustomerFirsthendBase;
import com.cardpay.pccredit.jnpad.dao.JnpadCustomerSelectDao;
import com.cardpay.pccredit.jnpad.dao.JnpadSelectDao;
import com.cardpay.pccredit.jnpad.model.CIPERSONBASINFO;
import com.cardpay.pccredit.jnpad.model.CustomerInfo;
import com.cardpay.pccredit.jnpad.model.JnIpadXDModel;
import com.cardpay.pccredit.system.model.SystemUser;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;

/**
 * 客户信息查询
 * @author sealy
 *
 */
@Service

public class JnpadCustomerSelectService {
	@Autowired
	private JnpadCustomerSelectDao jnpadCustomerSelectDao;
	
	@Autowired
	private CommonDao commonDao;
	@Autowired
	private JnpadSelectDao selectdao;
	/**
	 * 根据证件号码查询
	 * @return
	 */
	public CustomerInfo selectCustomerInfoByCardId(String cardId){
		
		return jnpadCustomerSelectDao.selectCustomerInforByCardId(cardId);
		
	}
	
	
	/**
	 * 
	 * 按id查找相应的客户基本信息
	 * @return
	 */
	public CIPERSONBASINFO selectCustomerInfoById(String id) {
		// TODO Auto-generated method stub
		return jnpadCustomerSelectDao.selectCustomerInfoById(id);
	}
	
	
	/**
	 * 
	 * 按客户内码id查找相应的客户基本信息
	 * @return
	 */
	public CIPERSONBASINFO selectCustomerByNm(String custid) {
		// TODO Auto-generated method stub
		return jnpadCustomerSelectDao.selectCustomerByNm(custid);
	}


	public CustomerFirsthendBase findCustomerFirsthendById(String customerInforId) {
		// TODO Auto-generated method stub
		 return commonDao.findObjectById(CustomerFirsthendBase.class,customerInforId);
	}

//按身份证号和客户经理ID
	public CustomerInfo selectCustomerInfoByCardIdAndUserId(String cardId, String userId) {
		// TODO Auto-generated method stub
		return jnpadCustomerSelectDao.selectCustomerInforByCardIdAndUserId(cardId, userId);
	}


	public List<CustomerInfo> selectCustomerInfo(String cardId,String chineseName, String userId) {
		// TODO Auto-generated method stub
		return jnpadCustomerSelectDao.selectCustomerInfo(cardId,chineseName, userId);
	}


	public String selectManagerNameById(String gId) {
		// TODO Auto-generated method stub
		SystemUser user = commonDao.findObjectById(SystemUser.class, gId);
		
		
		return user.getDisplayName();
	}
	
	public List<CustomerInfo> selectByserIdOnRy(String userId,String ProductName){
		return jnpadCustomerSelectDao.selectByserIdOnRy(userId,ProductName);
	}
	public int selectByserIdOnRyCount(String userId,String ProductName){
		return jnpadCustomerSelectDao.selectByserIdOnRyCount(userId,ProductName);
	}
	public List<CustomerInfo> selectAllcustormerId(@Param(value = "userId")String userId){
		return jnpadCustomerSelectDao.selectAllcustormerId(userId);
	}
	
	public int findCount(@Param(value = "ID")String ID,@Param(value = "status")String status,@Param(value = "status1")String status1){
		return jnpadCustomerSelectDao.findCount(ID, status,status1);
	}
	public int findNoSQCount(@Param(value = "userId")String userId){
		return jnpadCustomerSelectDao.findNoSQCount(userId);
	}
	public List<JnIpadXDModel> findJqUser(@Param("cardId") String cardId){
		return selectdao.findJqUser(cardId);
		
	}


}
