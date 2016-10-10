/*
 * Copyright 2014 Wicresoft, Inc. All rights reserved.
 */

package com.wicresoft.jrad.modules.privilege.business.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.wicresoft.jrad.base.auth.AuthResult;
import com.wicresoft.jrad.base.auth.AuthResult.AuthResultType;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.auth.JRadOperationHelper;
import com.wicresoft.jrad.base.web.menu.UIMenuMgr;
import com.wicresoft.jrad.base.web.menu.UiMenu;
import com.wicresoft.jrad.modules.privilege.business.AccessRightManager;
import com.wicresoft.jrad.modules.privilege.model.AccessRight;
import com.wicresoft.jrad.modules.privilege.model.Department;
import com.wicresoft.jrad.modules.privilege.model.Organization;
import com.wicresoft.jrad.modules.privilege.model.Resource;
import com.wicresoft.jrad.modules.privilege.model.Role;
import com.wicresoft.jrad.modules.privilege.model.User;
import com.wicresoft.jrad.modules.privilege.service.AccessRightService;
import com.wicresoft.jrad.modules.privilege.service.DepartmentService;
import com.wicresoft.jrad.modules.privilege.service.DeptUserService;
import com.wicresoft.jrad.modules.privilege.service.OrganizationService;
import com.wicresoft.jrad.modules.privilege.service.RoleService;
import com.wicresoft.jrad.modules.privilege.service.impl.DeptUserServiceImpl;
import com.wicresoft.util.encrypt.EncryptHelper;
import com.wicresoft.util.spring.Beans;

/**
 * Description of AccessRightManagerImpl
 * 
 * @author Evans
 * @created on 2014-6-18
 * 
 * @version $Id:$
 */

public class AccessRightManagerImpl implements AccessRightManager {

	private static int right_sign = 6;
	
	private boolean authOperation=true;

	@Autowired
	private AccessRightService accessRightService;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private RoleService roleService;
	
	@Override
	public List<Resource> getResourceListByRoleId(String roleId) {
		List<Resource> list = accessRightService.findResourceByRoleId(roleId);
		for (Resource resource : list) {
			resource.setOperationList(JRadOperationHelper.getOperationNameByoper(resource.getOperations()));
		}
		return list;
	}

	@Override
	public int insertAccessRight(List<AccessRight> accessRightList) {
		return accessRightService.insertAccessRight(accessRightList);
	}

	@Override
	public int deleteAccessRightByRoleId(String roleId) {
		return accessRightService.deleteAccessRightByRoleId(roleId);
	}

	@Override
	public int saveRoleAndResource(String roleId, String resourceIds) {
		if (StringUtils.isNotEmpty(roleId) && StringUtils.isNotEmpty(resourceIds)) {
			String[] arrResourceId = StringUtils.trim(resourceIds).split(",");

			Map<String, Long> countPower = new HashMap<String, Long>();
			for (int i = 0; i < arrResourceId.length; i++) {
				String realResourceId = arrResourceId[i].substring(0, right_sign);
				String power = arrResourceId[i].substring(right_sign, arrResourceId[i].length());
				Long operation = StringUtils.isNotEmpty(power) ? Long.parseLong(power) : 0L;
				if (countPower.containsKey(realResourceId)) {
					Long powerLong = countPower.get(realResourceId) + operation;
					countPower.put(realResourceId, powerLong);
				} else {
					countPower.put(realResourceId, operation);
				}
			}

		
			List<AccessRight> accessRightList = new ArrayList<AccessRight>();
			for (Entry<String, Long> entry : countPower.entrySet()) {
				AccessRight accessRight = new AccessRight();
				accessRight.setRoleId(roleId);
				accessRight.setResourceId(entry.getKey());
				accessRight.setOperations(entry.getValue());
				accessRightList.add(accessRight);
			}
			
			this.deleteAccessRightByRoleId(roleId);
			this.insertAccessRight(accessRightList);
		}
		return 1;
	}

	@Override
	public AuthResult authUserByLogin(String login, String password) {
		User user = accessRightService.findUserByLogin(login);
		AuthResult authResult = null;
		if (user != null) {
//			if (user.getUserType() == IUser.USER_TYPE_LOCAL) {
				if (user.getPassword().equals(EncryptHelper.md5(password))) {
					authResult = new AuthResult(user, AuthResultType.AUTH_OK);
					
					//登入设置当前机构
					Organization organization = organizationService.findOrgByUserId(user.getId());
					user.setOrganization(organization);
					//登入设置当前部门
					Department department = departmentService.findDeptByUserId(user.getId());
					user.setDepartment(department);
					//登入设置当前角色
					List<Role> roles = roleService.getRoleByUserId(user.getId());
					user.setRoles(roles);
					
				} else {
					authResult = new AuthResult(user, AuthResultType.AUTH_ACCOUNT_PASSWORD_ERROR);
				}
//			} else {
//				authResult = new AuthResult(user, AuthResultType.AUTH_ACCOUNT_NOT_LOCAL);
//			}
		}
		else {
			authResult = new AuthResult(user, AuthResultType.AUTH_ACCOUNT_NOT_EXIST);
		}
		return authResult;
	}

	@Override
	public AuthResult authUserByEmail(String email, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AuthResult authUserByMobile(String mobile, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasAccessRight(String userId, String resource, long operation) {
		List<AccessRight> accessRightList = accessRightService.findAccessRightByUserId(userId);

		if (accessRightList != null) {
			UiMenu menu = Beans.get(UIMenuMgr.class).getResourceBySystemName(resource);
			if (menu != null) {
				for (AccessRight accessRight : accessRightList) {
					if (menu.getId().equals(accessRight.getResourceId())) {
						if (accessRight.getOperations() != 0) {
							List<Long> operationList = JRadOperationHelper.getOperationNameByoper(accessRight
									.getOperations());
							if (operationList.contains(operation)) {
								return true;
							}
						}
						else {
							return true;
						}
					}
				}
			}
			else {
				// 黑盒法：只有设置了菜单权限的才去判断权限
				return true;
			}
		}
		return false;
	}

	@Override
	public long getAccessRights(String userId, String resource) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<AccessRight> findAccessRightByUserId(String userId) {
		return accessRightService.findAccessRightByUserId(userId);
	}

	@Override
	public boolean isAuthOperation() {
		return authOperation;
	}

	public void setAuthOperation(boolean authOperation) {
		this.authOperation = authOperation;
	}

	
}
