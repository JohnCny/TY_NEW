/**
 * 
 */
package com.cardpay.pccredit.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.system.dao.comdao.DictComDao;
import com.cardpay.pccredit.system.filter.DictFilter;
import com.cardpay.pccredit.system.model.Dict;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.dao.query.OrderBy;
import com.wicresoft.jrad.base.database.model.QueryResult;

/**
 * 描述 ：数据字典service
 * @author 张石树
 *
 * 2014-11-5 下午3:57:08
 */
@Service
public class DictService {

	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private DictComDao dictComDao;

	/**
	 * 根据条件分页查询
	 * @param filter
	 * @return
	 */
	public QueryResult<Dict> findDictByFilter(DictFilter filter) {
		filter.getOrderBys().add(new OrderBy("dictType", true));
		filter.getOrderBys().add(new OrderBy("typeCode", true));
		return commonDao.findObjectsByFilter(Dict.class, filter);
	}
	
	/**
	 * 根据ID查询Dict
	 * @param id
	 * @return
	 */
	public Dict findDictById(String id){
		return commonDao.findObjectById(Dict.class, id);
	}
	
	/**
	 * 插入dict
	 * @param dict
	 * @return
	 */
	public int insertDict(Dict dict){
		return commonDao.insertObject(dict);
	}
	
	/**
	 * 修改dict
	 * @param dict
	 * @return
	 */
	public int updateDict(Dict dict){
		return commonDao.updateObject(dict);
	}

	/**
	 * 根据ID删除dict
	 * @param id
	 */
	public int deleteDictById(String id) {
		return commonDao.deleteObject(Dict.class, id);
	}
	
	/**
	 * 根据字典类别和字典代码查询
	 * @param dictType
	 * @param typeCode
	 * @return
	 */
	public List<Dict> findByDictTypeAndTypeCode(String dictType, String typeCode){
		return dictComDao.findByDictTypeAndTypeCode(dictType, typeCode);
	}
	
	/**
	 * 根据字典类别查dict
	 * @param dictType
	 * @return
	 */
	public List<Dict> findDictByDictType(String dictType){
		return dictComDao.findDictByDictType(dictType);
	}
}
