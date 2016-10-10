package com.cardpay.pccredit.system.dao.comdao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.system.model.SystemConfiguration;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;

/**
 * 
 * 描述 ：数据字典sql dao
 * @author 张石树
 *
 * 2014-11-5 下午5:25:02
 */
@Service
public class SystemConfigurationComDao {

	@Autowired
	private CommonDao commonDao;

	/**
	 * 根据字典类别和字典代码查询
	 * @param dictType
	 * @param typeCode
	 * @return
	 */
	public List<SystemConfiguration> findValuesByCode(String code) {
		String sql = "select sys_values from system_configuration where sys_code=#{code}";
		Map<String, Object>  params = new HashMap<String, Object>();
		params.put("code", code);
		return commonDao.queryBySql(SystemConfiguration.class, sql, params);
	}

	
	
	
}
