package com.cardpay.pccredit.manager.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.manager.filter.ManagerBelongMapFilter;
import com.cardpay.pccredit.manager.model.ManagerBelongMap;
import com.cardpay.pccredit.manager.web.AccountManagerParameterForm;
import com.cardpay.pccredit.manager.web.ManagerBelongMapForm;
import com.cardpay.pccredit.manager.web.SysOrganizationForm;
import com.wicresoft.util.annotation.Mapper;

/**
 * 
 * 描述 ：客户从属关系Dao
 * @author 张石树
 *
 * 2014-11-10 下午4:39:24
 */
@Mapper
public interface ManagerBelongMapDao {

	/**
	 * 获取跟节点
	 */
	ManagerBelongMap getManagerBelongRoot();

	/**
	 * 根据parentId查询子节点信息
	 * @param parentId
	 * @return
	 */
	List<ManagerBelongMapForm> findByParentId(@Param("parentId") String parentId);

	/**
	 * 根据childId查询节点信息
	 * @param childId
	 * @return
	 */
	ManagerBelongMapForm findByChildId(@Param("childId") String childId);

	/**
	 * 分页查询信息
	 * @param filter
	 * @return
	 */
	List<ManagerBelongMapForm> findManagerBelongMapByFilter(ManagerBelongMapFilter filter);

	/**
	 * 分页查询统计 
	 * @param filter
	 * @return
	 */
	int findManagerBelongMapCountByFilter(ManagerBelongMapFilter filter);

	/**
	 * 根据客户经理从属的客户经理参数id查询客户经理信息
	 * @param childIdInStr
	 * @return
	 */
	List<AccountManagerParameterForm> findAccountManagerParameterByChildIds(@Param("childIdInStr") String childIdInStr);
	
	/**
	 * 机构主管logon 查询该机构下所有的客户经理信息
	 * @param userId
	 * @return
	 */
	List<AccountManagerParameterForm> findOrgManagerById(@Param("userId") String userId);
	

	/**
	 * 部门主管logon 查询所有机构下所有的客户经理信息
	 * @param userId
	 * @return
	 */
	List<AccountManagerParameterForm> findDeptManagerById(@Param("userId") String userId);
	
	/**
	 * 部门主管logon 查询 所属机构
	 * @param userId
	 * @return
	 */
	List<SysOrganizationForm> findDeptOrgan(@Param("userId") String userId);
	/**
	 * 机构主管logon 查询 所属机构
	 * @param userId
	 * @return
	 */
	List<SysOrganizationForm> findOrgOrgan(@Param("userId") String userId);
	
	/**
	 * 客户经理或客户经理主管logon 查询所属机构
	 */
	List<SysOrganizationForm> findManagerOrgan(@Param("userId") String userId);
	
	
	/**
	 * 找子节点
	 */
	public List<ManagerBelongMapForm> findChildId(@Param("id") String userId);
	
	List<AccountManagerParameterForm> findAllManager();
}
