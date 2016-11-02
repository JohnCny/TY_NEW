package com.cardpay.pccredit.customer.service;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.customer.model.MaintenanceSchedule;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;

/**
 * @author chenzhifang
 *
 * 2014-11-11上午9:44:52
 */
@Service
public class MaintenanceScheduleService {
	@Autowired
	private CommonDao commonDao;
	
	public String insertMaintenanceSchedule(MaintenanceSchedule maintenanceSchedule) {
		maintenanceSchedule.setCreatedTime(Calendar.getInstance().getTime());
		maintenanceSchedule.setModifiedTime(maintenanceSchedule.getCreatedTime());
		maintenanceSchedule.setCreateWay("");
		commonDao.insertObject(maintenanceSchedule);

		return maintenanceSchedule.getId();
	}
	
}
