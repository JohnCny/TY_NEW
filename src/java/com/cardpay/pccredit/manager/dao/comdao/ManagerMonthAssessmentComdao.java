package com.cardpay.pccredit.manager.dao.comdao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.manager.model.MangerMonthAssessment;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
/**
 * 
 * @author 季东晓
 *
 * 2014-11-25 上午11:03:35
 */
@Service
public class ManagerMonthAssessmentComdao {
	

	@Autowired
	private CommonDao commonDao;
	/**
	 * 查询客户经理层级月度考核指标配置
	 * @return
	 */
	public List<MangerMonthAssessment> getMangerMonthAssessment(){
		String sql = "select m.* from manger_month_assessment m where 1=1 order by m.customer_manager_level asc";
		List<MangerMonthAssessment> list = commonDao.queryBySql(MangerMonthAssessment.class, sql, null);
			return list;	
		
	}

}
