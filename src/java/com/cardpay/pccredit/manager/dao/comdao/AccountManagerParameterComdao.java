package com.cardpay.pccredit.manager.dao.comdao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.customer.model.CustomerManagerTarget;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
/**
 * 
 * @author 季东晓
 *
 * 2014-11-19 下午3:28:59
 */
@Service
public class AccountManagerParameterComdao {
	
	@Autowired
	private CommonDao commonDao;
	/**
	 * 获取客户经理的属性参数根据Id
	 * @return
	 */
	public List<CustomerManagerTarget> getcustomerManagerTargetBymanagerId(String hierarchy){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("hierarchy", hierarchy);
		String sql = "select t.* from customer_manager_target t where t.hierarchy=#{hierarchy} order by t.id asc";
		List<CustomerManagerTarget> list = commonDao.queryBySql(CustomerManagerTarget.class, sql, params);
			return list;	
		
	}
	
	/**
	 * 获取所有客户经理的属性参数
	 * @return
	 */
	public List<CustomerManagerTarget> getcustomerManagerTarget(){
		String sql = "select t.* from customer_manager_target t where 1=1 order by t.hierarchy asc";
		List<CustomerManagerTarget> list = commonDao.queryBySql(CustomerManagerTarget.class, sql, null);
			return list;	
		
	}
	
	/**
	 * 获取客户经理的属性参数根据周期
	 * @return
	 */
	public List<CustomerManagerTarget> getcustomerManagerTargetBytargetDate(String targetDate){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("targetDate", targetDate);
		String sql = "select t.* from customer_manager_target t where t.target_date=#{targetDate} order by t.hierarchy asc";
		List<CustomerManagerTarget> list = commonDao.queryBySql(CustomerManagerTarget.class, sql, params);
			return list;	
		
	}
	/**
	 * 获取客户经理的属性参数根据Id和周期
	 * @return
	 */
	public CustomerManagerTarget getcustomerManagerTargetBymanagerIdDate(String hierarchy,String targetDate){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("hierarchy", hierarchy);
		params.put("targetDate", targetDate);
		String sql = "select t.* from customer_manager_target t where t.hierarchy=#{hierarchy} and t.target_date=#{targetDate} order by t.id asc";
		List<CustomerManagerTarget> list = commonDao.queryBySql(CustomerManagerTarget.class, sql, params);
		if(list.size()>0)
		{	return list.get(0);	
		
		}else{
			
			return null;
		}
		
		
	}
	
	public void selectInfoTaskLog(){
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "insert into t_batch_task_log  (select * from t_batch_task where status ='100')";
		commonDao.queryBySql(CustomerManagerTarget.class, sql, params);
	}

}
