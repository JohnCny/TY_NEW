package com.cardpay.pccredit.system.service;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.system.dao.comdao.SystemComDao;
import com.cardpay.pccredit.system.filter.SystemUserFilter;
import com.cardpay.pccredit.system.model.SystemUser;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.model.QueryResult;
@Service
public class SystemUserService {
	
	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private SystemComDao systemComDao;
	/**
	 * 按id查找相应的客户基本信息
	 * @param customerInforId
	 * @return
	 */
	public SystemUser findCustomerManagerInforById(String id){
		return commonDao.findObjectById(SystemUser.class, id);
		
		/* 客户证件ID模糊匹配 */
		/*
		 * TODO 1.注释标明输入输出，异常抛出类型 2.SQL写进DAO层
		 */
		
	}
	public void selectLikeByDisplayName(HttpServletResponse response,
			String tempParam) throws Exception {
		systemComDao.selectLikeByDisplayName(response, tempParam);
	}

	/**
	 * 根据组织Id查找用户
	 * @param filter
	 * @return
	 */
	
	public QueryResult<SystemUser> findSystemUserByFilter(SystemUserFilter filter) {
		
		
		return systemComDao.findSystemUserByFilter(filter);
	}
	
	public QueryResult<SystemUser> findSystemUserByFilterAndUserType(SystemUserFilter filter,String userType) {
		return systemComDao.findSystemUserByFilterAndUserType(filter,userType);
	}
}
