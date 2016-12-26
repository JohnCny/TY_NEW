package com.cardpay.pccredit.jnpad.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.jnpad.model.JBUser;
import com.wicresoft.util.annotation.Mapper;
@Mapper
public interface JnIpadJBUserDao {
	/**
	 * 查询该区域经理在那个区域，以及区域下面多少组
	 * @param ID
	 * @return
	 */
	public List<JBUser> selectDepartUser(@Param("ID") String ID);
	
	/**
	 * 查询该组长在那个组，组员
	 * @param ID
	 * @return
	 */
	public List<JBUser> selectDepart1(@Param("ID") String ID);
	
	
	/**
	 * 查询该小组的组员
	 * @param ID
	 * @return
	 */
	public List<JBUser> findDe(@Param("ID") String ID);

}
