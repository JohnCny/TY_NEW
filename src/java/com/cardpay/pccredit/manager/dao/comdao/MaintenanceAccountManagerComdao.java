package com.cardpay.pccredit.manager.dao.comdao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.manager.model.MaintenanceAccountManager;
import com.cardpay.pccredit.riskControl.model.ManagerCustomerType;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
/**
 * 
 * @author 季东晓
 *
 * 2014-11-20 下午2:21:59
 */
@Service
public class MaintenanceAccountManagerComdao {
	

	@Autowired
	private CommonDao commonDao;
	/**
	 * 查询客户经理参数维护
	 * @return
	 */
	public List<MaintenanceAccountManager> getMaintenanceAccountManager(){
		String sql = "select t.*,c.customer_type_code from maintenance_account_manager t,(select m.level_id,wm_concat(m.customer_type) as customer_type_code from manager_customer_type m group by m.level_id) c where t.hierarchy=c.level_id order by t.hierarchy asc";
		List<MaintenanceAccountManager> list = commonDao.queryBySql(MaintenanceAccountManager.class, sql, null);
			return list;	
		
	}
	
	/**
	 * 查询客户层级对应的类型
	 * @return
	 */
	public List<ManagerCustomerType> getManagerCustomerType(){
		String sql = "select m.* from manager_customer_type m where 1=1 order by m.level_id asc";
		List<ManagerCustomerType> list = commonDao.queryBySql(ManagerCustomerType.class, sql, null);
			return list;	
		
	}

}
