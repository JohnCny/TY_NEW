package com.wicresoft.jrad.modules.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.modules.privilege.constant.PrivilegeConstants;
import com.wicresoft.jrad.modules.privilege.dao.AccessRightMapper;
import com.wicresoft.jrad.modules.privilege.model.User;
import com.wicresoft.util.encrypt.EncryptHelper;

/**
 * 
 * 描述 ：jrad—base扩充
 * @author 张石树
 *
 * 2014-11-10 上午9:57:06
 */
@Service
public class modulesComdao {

	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private AccessRightMapper accessRightMapper;
	
	public List<User> checkExternalIdDupUpdate(String id, String externalId){
		String sql = "select * from sys_user where id <> #{id} and external_id = #{externalId}";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("externalId", externalId);
		return commonDao.queryBySql(User.class, sql, params);	
	}
	
	public List<User> checkLoginDupInsert(String login){
		String sql = "select * from sys_user where login = #{login}";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("login", login);
		return commonDao.queryBySql(User.class, sql, params);	
	}
	
	public List<User> checkExternalIdDupInsert(String externalId){
		String sql = "select * from sys_user where external_id = #{externalId}";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("externalId", externalId);
		return commonDao.queryBySql(User.class, sql, params);	
	}
	
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
}
