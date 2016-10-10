package com.cardpay.pccredit.manager.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.manager.filter.AccountManagerRetrainingFilter;
import com.cardpay.pccredit.manager.model.AccountManagerRetraining;
import com.wicresoft.jrad.modules.privilege.model.User;
import com.wicresoft.util.annotation.Mapper;

/**
 * 客户经理再培训计划dao
 * @author chenzhifang
 *
 * 2014-11-11下午3:28:24
 */
@Mapper
public interface AccountManagerRetrainingDao {
	
	/**
	 * 通过在培训id查询客户经理
	 * @param retrainId
	 * @return
	 */
	public List<User> findUserListByRetrainId(@Param("retrainId")String retrainId);
	
	/**
	 * 通过机构id查询客户经理
	 * @param orgId 机构id
	 * @return
	 */
	public List<User> findUserListByOrgId(@Param("orgId")String orgId);
	
	/**
	 * 过滤查询
	 * @param filter
	 * @return
	 */
	public List<AccountManagerRetraining> findAccountManagerRetrainingsByFilter(AccountManagerRetrainingFilter filter);
	
	/**
	 * 统计客户经理再培训计划记录数
	 * @param filter
	 * @return
	 */
	public int findAccountManagerRetrainingsCountByFilter(AccountManagerRetrainingFilter filter);
	
	public int deleteAccountManagerRetraining(@Param("retrainId")String retrainId, @Param("customerManagerId")String customerManagerId);
}
