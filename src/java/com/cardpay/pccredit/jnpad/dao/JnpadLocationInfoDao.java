package com.cardpay.pccredit.jnpad.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.jnpad.model.LocationInfoForm;
import com.cardpay.pccredit.jnpad.model.ManagerInfoForm;
import com.wicresoft.util.annotation.Mapper;

@Mapper
public interface JnpadLocationInfoDao {

	//查询表中该客户经理信息包含条数
	public int managerCount(@Param(value = "managerId") String managerId);

	//插入客户经理信息
	public void insertManagerLocation(LocationInfoForm locationInfoForm);

	//更新客户经理信息
	public void updateManagerLocation(LocationInfoForm locationInfoForm);

	//根据客户经理Id查询客户经理姓名
	public ManagerInfoForm selectManagerInforById(@Param(value = "managerId") String managerId);

	//查询指定客户经理位置
	public List<LocationInfoForm> selectManagerLocationById(@Param(value = "managerId")String userId);

	//查询指定客户经理最新一条位置信息
		public LocationInfoForm selectlastManagerLocationById(@Param(value = "managerId") String managerId);

	
	
}
