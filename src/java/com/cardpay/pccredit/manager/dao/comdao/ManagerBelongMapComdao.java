/**
 * 
 */
package com.cardpay.pccredit.manager.dao.comdao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.manager.model.ManagerBelongMap;
import com.cardpay.pccredit.riskControl.model.NplsInfomationConfiguration;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;

/**
 * 
 * 描述 ：
 * @author 张石树
 *
 * 2014-11-12 下午4:59:00
 */
@Service
public class ManagerBelongMapComdao {

	@Autowired
	private CommonDao commonDao;
	
	/**
	 * 根据用户Id查询客户经理从属关系
	 * @param userId
	 * @return
	 */
	public ManagerBelongMap findManagerBelongMapByUserId(String userId){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		String sql = "select t.* from manager_belong_map t left join account_manager_parameter amp on amp.id = t.child_id where amp.user_id = #{userId}";
		List<ManagerBelongMap> tempList = commonDao.queryBySql(ManagerBelongMap.class, sql, params);
		if(tempList != null && tempList.size() > 0){
			return tempList.get(0);
		} else {
			return null;
		}
	}
	
	/**
	 * 获取不良资产配置信息
	 * @return
	 */
	public NplsInfomationConfiguration findNplsInfomationConfiguration(){
		String sql = "select * from npls_information_configuration";
		List<NplsInfomationConfiguration> tempList = commonDao.queryBySql(NplsInfomationConfiguration.class, sql, null);
		if(tempList != null && tempList.size() > 0){
			return tempList.get(0);
		} else {
			return null;
		}
	}
}
