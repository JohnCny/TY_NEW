package com.cardpay.pccredit.manager.dao;

import java.util.List;

import com.cardpay.pccredit.manager.filter.LedgerAccountManagerFilter;
import com.cardpay.pccredit.manager.web.LedgerAccountManagerForm;
import com.wicresoft.util.annotation.Mapper;


/**
 * 客户经理台账Dao类的描述
 *
 * @author 王海东
 * @created on 2014-11-19
 * 
 * @version $Id:$
 */
@Mapper
public interface LedgerAccountManagerDao {
	
	/*
	 * 根据filter查询客户经理台账
	 */
	List<LedgerAccountManagerForm> findLedgerAccountManagersByFilter(LedgerAccountManagerFilter filter);
	
	/*
	 * 根据filter查询客户经理台账条数
	 */
	public int findLedgerAccountManagersCountByFilter(LedgerAccountManagerFilter filter);


}
