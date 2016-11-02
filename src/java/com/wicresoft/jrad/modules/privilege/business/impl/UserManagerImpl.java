/*
 * Copyright 2014 Wicresoft, Inc. All rights reserved.
 */

package com.wicresoft.jrad.modules.privilege.business.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.modules.privilege.business.RoleManager;
import com.wicresoft.jrad.modules.privilege.business.UserManager;
import com.wicresoft.jrad.modules.privilege.constant.PrivilegeConstants;
import com.wicresoft.jrad.modules.privilege.filter.UserFilter;
import com.wicresoft.jrad.modules.privilege.model.DeptUser;
import com.wicresoft.jrad.modules.privilege.model.RolePerson;
import com.wicresoft.jrad.modules.privilege.model.TreeNode;
import com.wicresoft.jrad.modules.privilege.model.User;
import com.wicresoft.jrad.modules.privilege.service.UserService;
import com.wicresoft.util.encrypt.EncryptHelper;

/**
 * Description of PersonManagerImpl
 * 
 * @author Administrator
 * @created on Jul 7, 2014
 * 
 * @version $Id:$
 */
public class UserManagerImpl implements UserManager {

	private static final boolean deleted = false;

	@Autowired
	private UserService userService;
	@Autowired
	private RoleManager roleManager;

	@Override
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}

	@Override
	public QueryResult<User> findUserByFilter(UserFilter userFilter) {
		return userService.findUserByFilter(userFilter);
	}

	@Override
	public String insertUser(User user, String roleIds) {

		user.setCreatedTime(Calendar.getInstance().getTime());
		user.setIsDeleted(deleted);
		user.setPassword(EncryptHelper.md5(PrivilegeConstants.DEFAULT_PWD));
//		user.setUserType(IUser.USER_TYPE_LOCAL);
		userService.insertUser(user);
		String userId =user.getId();
		if (StringUtils.trimToNull(roleIds) != null) {
			String[] temp = roleIds.split(",");
			if (temp != null && temp.length > 0) {
				for (String id : temp) {
					if (StringUtils.trimToNull(id) != null) {
						RolePerson rolePerson = new RolePerson();
						rolePerson.setRoleId(id);
						rolePerson.setUserId(user.getId());
						roleManager.insertRoleUser(rolePerson);
					}
				}
			}
		}

		return userId;
	}

	@Override
	public User getUserById(String userId) {
		return userService.getUserById(userId);
	}

	@Override
	public int updateUser(User user, String roleIds) {
		user.setModifiedTime(Calendar.getInstance().getTime());
//		user.setPassword(EncryptHelper.md5(user.getPassword()));
		roleManager.deleteRoleUser(user.getId());
		if (StringUtils.trimToNull(roleIds) != null) {
			String[] temp = roleIds.split(",");
			if (temp != null && temp.length > 0) {
				for (String id : temp) {
					if (StringUtils.trimToNull(id) != null) {
						RolePerson rolePerson = new RolePerson();
						rolePerson.setRoleId(id);
						rolePerson.setUserId(user.getId());
						roleManager.insertRoleUser(rolePerson);
					}
				}
			}
		}
		return userService.updateUser(user);
	}

	@Override
	public int deleteUser(String userId) {
		roleManager.deleteRoleUser(userId);
		return userService.deleteUser(userId);
	}

	@Override
	public List<User> getUserByGroupId(String groupId) {
		return userService.getUserByGroupId(groupId);
	}

	@Override
	public String queryAllUserToJson() {
		List<User> list=userService.getUserRoot();
		StringBuffer sb=new StringBuffer();
		for(User user:list){
			sb.append(JSONObject.fromObject(this.recursiveTree(user.getId())).toString());
		}
		JSONObject obj = JSONObject.fromObject(this.recursiveTree(null));
		if (obj == null) {
			return "{}";
		}
		return obj.toString();
	}

	/**
	 * 递归算法解析成树形结构
	 * 
	 */
	public TreeNode recursiveTree(String managerId) {
		User root = userService.getUserById(managerId);
		TreeNode rootNode = new TreeNode(root.getId(), root.getManagerId(), root.getDisplayName(), "", "", "", "",
				false, false, true, true);
		List<User> childrenUsers = userService.getUserByManagerId(managerId);

		List<User> childUser = new ArrayList<User>();
		for (User r : childrenUsers) {
			// TreeNode treeNode = new TreeNode(r.getId(), r.getParentId(),
			// r.getDisplayName(), "", "", "", true);
			childUser.add(r);
		}

		// 递归遍历子节点
		for (int i = 0; i < childUser.size(); i++) {
			User currentUser = childUser.get(i);
			TreeNode n = recursiveTree(currentUser.getId());
			rootNode.getChildren().add(n);// 添加下一个节点

		}
		return rootNode;
	}

	@Override
	public List<User> getUserByManagerId(String managerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getUserStringById(String userId) {
		List<String> list = new ArrayList<String>();
		while (true) {
			User user = userService.getUserById(userId);
			if (user != null) {
				userId = user.getId();
				if (StringUtils.isNotEmpty(userId)) {
					list.add(userId);
				} else {
					break;
				}
			} else {
				break;
			}
		}
		return list;
	}

	@Override
	public List<User> getUserByRoleId(String roleId) {
		return userService.getUserByRoleId(roleId);
	}

	@Override
	public List<User> getADUsers() {
		return userService.getADUsers();
	}

	@Override
	public int insertDeptUser(DeptUser deptUser) {
		return userService.insertDeptUser(deptUser);
	}

	@Override
	public void setUserPwd(String userId, String password) {
		User user = userService.getUserById(userId);
		if(StringUtils.isNotEmpty(password)){
			user.setPassword(EncryptHelper.md5(password));
		} else {
			user.setPassword(EncryptHelper.md5(PrivilegeConstants.DEFAULT_PWD));
		}
		userService.updateUser(user);
	}

}
