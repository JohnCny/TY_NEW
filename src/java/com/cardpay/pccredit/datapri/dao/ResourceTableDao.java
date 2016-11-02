package com.cardpay.pccredit.datapri.dao;

import java.util.List;

import com.cardpay.pccredit.datapri.model.ResourceTableDataRule;
import com.cardpay.pccredit.datapri.model.ResourceTableField;
import com.wicresoft.util.annotation.Mapper;

/**
 * 
 * 数据库操作 
 * @author 张石树
 *
 * 2014-10-20 下午4:33:51
 */
@Mapper
public interface ResourceTableDao {

	/**
	 * 插入
	 * @param dataRule
	 */
	void insert(ResourceTableDataRule dataRule);
	
	/**
	 * 根据资源表ID查找
	 * @param resTblId
	 * @return
	 */
	ResourceTableDataRule findDataRuleByTblId(String resTblId);
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 */
	ResourceTableDataRule findDataRuleById(String id);
	
	/**
	 * 根据资源表查找
	 * @param resTbl
	 * @return
	 */
	ResourceTableDataRule findDataRuleByTbl(String resTbl);

	/**
	 * 修改
	 * @param dbdataRule
	 */
	void update(ResourceTableDataRule dbdataRule);
	
	/**
	 * 根据资源表查找ruleSql
	 * @param resTbl
	 * @return
	 */
	String findDataRuleSqlByTbl(String resTbl);
	
	/**
	 * 根据资源表ID查找资源列表
	 * @param resTblId
	 * @return
	 */
	List<ResourceTableField> findResTblFieldByTblId(String resTblId);
}
