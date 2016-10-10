package com.cardpay.pccredit.manager.dao.comdao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.manager.model.ManagerPromotionDownRule;
import com.cardpay.pccredit.manager.model.ManagerPromotionRule;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
/**
 * 
 * @author 季东晓
 *
 * 2014-11-21 下午4:28:09
 */
@Service
public class ManagerPromotionRuleComdao {
	

	@Autowired
	private CommonDao commonDao;
	/**
	 * 查询客户经理晋级规则
	 * @return
	 */
	public List<ManagerPromotionRule> getManagerPromotionRule(){
		String sql = "select m.* from promotion_rules m where 1=1 order by m.initial_level asc";
		List<ManagerPromotionRule> list = commonDao.queryBySql(ManagerPromotionRule.class, sql, null);
			return list;	
		
	}
	
	/**
	 * 查询客户经理晋降级规则
	 * @return
	 */
	public List<ManagerPromotionDownRule> getManagerPromotionDownRule(){
		String sql = "select m.* from TY_PROMOTION_DOWN_RULES m where 1=1 order by m.initial_level asc";
		List<ManagerPromotionDownRule> list = commonDao.queryBySql(ManagerPromotionDownRule.class, sql, null);
	    return list;	
	}

}
