package com.cardpay.pccredit.jnpad.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.cardpay.pccredit.jnpad.model.NODEAUDIT;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.util.annotation.Mapper;

@Mapper
public interface JnipadNodeDao {
	/**
	 * 查看当前客户经理负责产品审批的详情
	 * @param n
	 * @return
	 */
	List<NODEAUDIT> selectProductUser(NODEAUDIT n);
	/**
	 * 查看当前客户经理负责产品审批的详情数量
	 * @param n
	 * @return
	 */
	int selectProductUserCount(NODEAUDIT n);
	
	/**
	 * 查看当前产品的审批流程
	 * @param n
	 * @return
	 */
	List<NODEAUDIT> selectAllProductUser(NODEAUDIT n);
	/**
	 * 查看当前产品的审批数量
	 * @param n
	 * @return
	 */
	int selectAllProductUserCount(NODEAUDIT n);

}
