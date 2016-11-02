package com.cardpay.pccredit.manager.dao.comdao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.manager.model.DownGradeRule;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
/**
 * 
 * @author 季东晓
 *
 * 2014-11-21 下午4:28:09
 */
@Service
public class ManagerDownRuleComdao {
	

	@Autowired
	private CommonDao commonDao;
	/**
	 * 查询客户经理降级规则
	 * @return
	 */
	public List<DownGradeRule> getDownGradeRule(){
		String sql = "select m.* from downgrade_rule m where 1=1 order by m.current_level asc";
		List<DownGradeRule> list = commonDao.queryBySql(DownGradeRule.class, sql, null);
			return list;	
		
	}

}
