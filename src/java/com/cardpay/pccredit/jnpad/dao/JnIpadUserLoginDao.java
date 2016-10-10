package com.cardpay.pccredit.jnpad.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.system.model.SystemUser;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.modules.privilege.dao.AccessRightMapper;
import com.wicresoft.jrad.modules.privilege.model.User;
import com.wicresoft.util.encrypt.EncryptHelper;

@Service
public class JnIpadUserLoginDao {

	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private AccessRightMapper accessRightMapper;
	
	public User find(String login, String password){
		User user = accessRightMapper.authUserByLogin(login);
		if(user != null){
			if(EncryptHelper.md5(password).equals(user.getPassword())){
				return user;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	
	public List<HashMap<String, Object>> findOrg(String id){
		String sql = " select org.name from sys_organization org where id in(select t.org_id from sys_department t "
				+ "where t.id in (select tt.dept_id from sys_dept_user tt where tt.user_id = '"+id+"')) ";
		return commonDao.queryBySql(sql, null);
	}
	
	public SystemUser findUser(String userId){
		return commonDao.findObjectById(SystemUser.class, userId);
	}
}
