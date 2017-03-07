package com.cardpay.pccredit.manager.dao.comdao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.manager.model.TPerformanceParameters;
import com.cardpay.pccredit.manager.model.TyPerformanceCenter;
import com.cardpay.pccredit.manager.model.TyPerformanceParameters;
import com.cardpay.pccredit.manager.model.TyTPerformanceParameters;
import com.cardpay.pccredit.system.model.Dict;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
/**
 * 
 * @author 季东晓
 *
 * 2014-11-21 下午4:28:09
 */
@Service
public class ManagerPerformanceParamersComdao {
	

	@Autowired
	private CommonDao commonDao;
	/**
	 * 查询客户经理绩效参数
	 * @return
	 */
	public List<TyPerformanceParameters> getManagerPerformanceParamers(){
		String sql = "select * from ty_performance_parameters";
		List<TyPerformanceParameters> list = commonDao.queryBySql(TyPerformanceParameters.class, sql, null);
			return list;	
	}
	/**
	 * 保存前先删除记录
	 * @return
	 */
	public void deleteList(){
		String sql = "delete from ty_performance_parameters";
		commonDao.queryBySql(TyPerformanceParameters.class, sql, null);
	}

	/**
	 * 查询中心人员绩效参数
	 * @return
	 */
	public List<TyPerformanceCenter> getManagerPerformanceCenter(){
		String sql = "select * from ty_performance_center";
		List<TyPerformanceCenter> list = commonDao.queryBySql(TyPerformanceCenter.class, sql, null);
			return list;	
	}
	
	//===================================================济南绩效==============================================//
	public List<TyTPerformanceParameters> getTManagerPerformanceParamers(){
		String sql = "select * from t_performance_parameters";
		List<TyTPerformanceParameters> list = commonDao.queryBySql(TyTPerformanceParameters.class, sql, null);
			return list;	
	}
	
	public void deleteTList(){
		String sql = "delete from t_performance_parameters";
		commonDao.queryBySql(TyTPerformanceParameters.class, sql, null);
	}
	public List<Dict> findPostAllowancedict() {
		// TODO Auto-generated method stub
		String sql = "select  * from dict where DICT_TYPE='PostAllowance'";
		List<Dict> list = commonDao.queryBySql(Dict.class, sql, null);
			return list;
	}
	public List<Dict> findTaskSuccessdict() {
		// TODO Auto-generated method stub
		String sql = "select  * from dict where DICT_TYPE='TaskSuccess'";
		List<Dict> list = commonDao.queryBySql(Dict.class, sql, null);
			return list;
	}
	public List<Dict> findToleratedict() {
		// TODO Auto-generated method stub
		String sql = "select  * from dict where DICT_TYPE='Tolerate'";
		List<Dict> list = commonDao.queryBySql(Dict.class, sql, null);
			return list;
	}
}
