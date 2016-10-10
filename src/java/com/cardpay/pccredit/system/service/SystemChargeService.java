package com.cardpay.pccredit.system.service;

import java.util.List;

import javax.persistence.criteria.From;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.system.dao.SysChargeDao;
import com.cardpay.pccredit.system.filter.SystemChargeFilter;
import com.cardpay.pccredit.system.model.Choujiang;
import com.cardpay.pccredit.system.model.SystemCharge;
import com.cardpay.pccredit.system.model.SystemUser;
import com.cardpay.pccredit.system.web.SystemChargeForm;
import com.sun.org.apache.bcel.internal.generic.Select;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.model.QueryResult;

@Service
public class SystemChargeService {
	
	@Autowired
	private SysChargeDao sysChargeDao;
	
	@Autowired
	private CommonDao commonDao;
	/**
	 * 过滤查询
	 * @param filter
	 * @return
	 */
	public QueryResult<SystemUser> findChargeByFilter(SystemChargeFilter filter) {
		List<SystemUser> users = sysChargeDao.findUserByFilter(filter);
		int size = sysChargeDao.findUserCountByFilter(filter);
		QueryResult<SystemUser> qs = new QueryResult<SystemUser>(size, users);
		return qs;
	}
	
	public void addCharge(SystemChargeForm systemChargeForm){
		String userIds = systemChargeForm.getUserIds();
		String[] userIdArr = userIds.split(",");
		for(String userId : userIdArr){
			SystemCharge systemCharge = new SystemCharge();
			systemCharge.setUserId(userId);
			systemCharge.setOrgId(systemChargeForm.getOrgId());
			systemCharge.setDeptId(systemChargeForm.getDeptId());
			
			//判断是否存在
			SystemCharge tmp = sysChargeDao.findCharge(systemCharge);
			if(tmp != null){
				continue;
			}
			commonDao.insertObject(systemCharge);
		}
	}
	
	public void deleteCharge(SystemChargeForm systemChargeForm){
		String userIds = systemChargeForm.getUserIds();
		SystemChargeFilter filter = new SystemChargeFilter();
		filter.setUserIds(userIds);
		filter.setDeptId(systemChargeForm.getDeptId());
		filter.setOrgId(systemChargeForm.getOrgId());
		List<SystemCharge> charges = sysChargeDao.findChargeByFilter(filter);
		for(SystemCharge obj : charges){
			commonDao.deleteObject(SystemCharge.class, obj.getId());
		}
	}
	
	/*
	 * 显示未抽奖成员
	 */
	public List<Choujiang> query(){
		List<Choujiang> list = commonDao.queryBySql(Choujiang.class, "select * From ty_tmp where result is null", null);
		return list;
	}
	/*
	 * 显示已抽奖成员
	 */
	public List<Choujiang> query1(){
		List<Choujiang> list = commonDao.queryBySql(Choujiang.class, "select * From ty_tmp where result is not null order by result", null);
		return list;
	}
	/*
	 * 保存结果
	 */
	public void save(String result,String level){
		String name = result.split("-")[0];
		String exentId = result.split("-")[1];
		commonDao.queryBySql(Choujiang.class, "update ty_tmp set result="+level+" where exent_id='"+exentId+"' and name='"+name+"'", null);
	}
}
