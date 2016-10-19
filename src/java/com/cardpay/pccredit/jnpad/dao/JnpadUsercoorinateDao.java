package com.cardpay.pccredit.jnpad.dao;

import java.util.List;

import com.cardpay.pccredit.jnpad.model.usercoorinate;
import com.wicresoft.util.annotation.Mapper;

@Mapper
public interface JnpadUsercoorinateDao {
	/**
	 * 当前客户经理添加客户地理信息
	 * @param u
	 * @return
	 */
	int addusercoorinate(usercoorinate u);
	/**
	 * 通过userid查询该客户经理的地理信息
	 * @param u
	 * @return
	 */
	List<usercoorinate> selectUserByUserid(usercoorinate u);
	/**
	 * 通过userid查询该客户经理的地理信息数量
	 * @param u
	 * @return
	 */
	int selectUserByUseridCount(usercoorinate u);
	/**
	 * 当前客户位置信息超过8条时 ,清除当前客户经理位置信息
	 * @param u
	 * @return
	 */
	int updateUser(usercoorinate u);
}
