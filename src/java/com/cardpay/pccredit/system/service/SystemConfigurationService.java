package com.cardpay.pccredit.system.service;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.system.dao.comdao.SystemConfigurationComDao;
import com.cardpay.pccredit.system.filter.SystemConfigurationFilter;
import com.cardpay.pccredit.system.model.SystemConfiguration;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.model.QueryResult;

@Service
public class SystemConfigurationService {
	
	@Autowired
	private SystemConfigurationComDao systemConfigurationComDao;
	
	@Autowired
	private CommonDao commonDao;
	
	public List<SystemConfiguration> findSystemConfigurationByCode(String code){
		return systemConfigurationComDao.findValuesByCode(code);
	}
	
	public QueryResult<SystemConfiguration> findSystemConfigurationsByFilter(SystemConfigurationFilter filter) {
		return commonDao.findObjectsByFilter(SystemConfiguration.class, filter);
	}
	
	public int updateSystemConfiguration(SystemConfiguration systemConfiguration) {
		systemConfiguration.setModifiedTime(Calendar.getInstance().getTime());
		return commonDao.updateObject(systemConfiguration);
	}
	
	public SystemConfiguration findSystemConfigurationById(String paraId) {
		return commonDao.findObjectById(SystemConfiguration.class, paraId);
	}


}
