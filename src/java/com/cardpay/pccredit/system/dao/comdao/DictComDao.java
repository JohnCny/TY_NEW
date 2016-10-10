package com.cardpay.pccredit.system.dao.comdao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.system.model.Dict;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;

/**
 * 
 * 描述 ：数据字典sql dao
 * @author 张石树
 *
 * 2014-11-5 下午5:25:02
 */
@Service
public class DictComDao {

	@Autowired
	private CommonDao commonDao;

	/**
	 * 根据字典类别和字典代码查询
	 * @param dictType
	 * @param typeCode
	 * @return
	 */
	public List<Dict> findByDictTypeAndTypeCode(String dictType, String typeCode) {
		String sql = "select * from dict where dict_type=#{dictType} and type_code=#{typeCode}";
		Map<String, Object>  params = new HashMap<String, Object>();
		params.put("dictType", dictType);
		params.put("typeCode", typeCode);
		return commonDao.queryBySql(Dict.class, sql, params);
	}

	/**
	 * 根据字典类别查dict
	 * @param dictType
	 * @return
	 */
	public List<Dict> findDictByDictType(String dictType) {
		String sql = "select * from dict where dict_type=#{dictType} order by type_code asc ";
		Map<String, Object>  params = new HashMap<String, Object>();
		params.put("dictType", dictType);
		return commonDao.queryBySql(Dict.class, sql, params);
	}
	
	
}
