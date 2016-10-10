package com.cardpay.pccredit.customer.dao.comdao;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.customer.filter.CustomerInforFilter;
import com.cardpay.pccredit.customer.filter.CustomerMaintenanceLogFilter;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.model.CustomerMaintenanceLog;
import com.cardpay.pccredit.intopieces.model.VideoAccessories;
import com.cardpay.pccredit.system.model.Dict;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.model.QueryResult;
@Service
public class CustomerInforCommDao {
	
	@Autowired
	private CommonDao commonDao;
	public CustomerInfor findCustomerInforByCardId(String cardId){
		List<CustomerInfor> list = commonDao.queryBySql(CustomerInfor.class, "select * from basic_customer_information where card_id='"+cardId+"'", null);
		if(list!=null&&!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	
	public List<Dict> findOaccountMybankList(){
		List<Dict> list = commonDao.queryBySql(Dict.class, "select * from DICT t where 1=2", null);
		return list;
	}
	
	public List<Dict> findCreditCardList(){
		List<Dict> list = commonDao.queryBySql(Dict.class, "select * from DICT t where 1=2", null);
		return list;
	}
	
	public List<Dict> findPayMybankList(){
		List<Dict> list = commonDao.queryBySql(Dict.class, "select * from DICT t where 1=2", null);
		return list;
	}
	
	public List<Dict> findDegreeeducationList(){
		List<Dict> list = commonDao.queryBySql(Dict.class, "select * from dict f where f.dict_type='DEGREEEDUCATION'", null);
		return list;
	}
	
	public List<Dict> debitWayList(){
		List<Dict> list = commonDao.queryBySql(Dict.class, "select * from dict f where f.dict_type='CMMHKFS-信用还款方式'", null);
		return list;
	}
	
	
	/**
	 * 根据条件查询调查大纲维护日
	 * @param filter
	 * @return
	 */
	public  List<CustomerMaintenanceLog> findCustomerInforLogMbxxByFilter(
			CustomerMaintenanceLogFilter filter) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("modifyTableName", filter.getModifyTableName());
		params.put("customerId", filter.getCustomerId());
		String sql = "select t.*,q.type_name as question_name from customer_maintenancee_log t,dict q where t.modify_table_name=#{modifyTableName} and t.customer_id = #{customerId} and t.modify_field_name = q.type_code  order by t.modify_field_name asc ,t.created_time asc";
		return commonDao.queryBySql(CustomerMaintenanceLog.class,sql, params);
	}

	
	public List<String> checkCustomerInfo(){
		List<String> resultList = null;
		List<CustomerInfor> list = commonDao.queryBySql(CustomerInfor.class, "select * from basic_customer_information ", null);
		if(list!=null&&!list.isEmpty()){
			resultList = new ArrayList<String>();
			for(CustomerInfor customerInfor:list){
				resultList.add(StringUtils.trim(customerInfor.getCardType())+StringUtils.trim(customerInfor.getCardId()));
			}
		}
		return resultList;
	}
	
	/**
	 * 根据条件更新维护日志对应的表
	 * @param  modifyTableName,customerId, modifyTablevalue
	 * @return
	 */
	public  void updateCustomerinforUpdateLog(String modifyTableName,String customerId,String modifyTablevalue) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("customerId", customerId);
		String sql = "update " + modifyTableName +" set " + modifyTablevalue +" where customer_id=#{customerId}";
		commonDao.queryBySql(sql, params);
	}
	
	/**
	 * 根据条件更新基本信息维护日志对应的表
	 * @param  modifyTableName,customerId, modifyTablevalue
	 * @return
	 */
	public  void updateCustomerinforUpdatejbxxLog(String modifyTableName,String customerId,String modifyTablevalue) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("customerId", customerId);
		String sql = "update " + modifyTableName +" set " + modifyTablevalue +" where id=#{customerId}";
		commonDao.queryBySql(sql, params);
	}

	
	/**
	 * 更新調查大綱维护日志对应的表
	 * @param  modifyTableName,customerId, modifyTablevalue,
	 * @return
	 */
	public  void updateCustomerinforUpdatedcdgLog(String modifyTableName,String customerId,String modifyTablevalue,String questionCode) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("customerId", customerId);
		params.put("questionCode", questionCode);
		String sql = "update " + modifyTableName +" set " + modifyTablevalue +" where customer_id=#{customerId} and  question_code=#{questionCode}";
		commonDao.queryBySql(sql, params);
	}
	/**
	 * 根据客户经理Id获取以下的客户
	 * @param managerId
	 * @return
	 */
	public  List<CustomerInfor> findCustomerByManagerId(String managerId){
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("userId", managerId);
		String sql = "select * from basic_customer_information where USER_ID=#{userId}";
		return commonDao.queryBySql(CustomerInfor.class, sql, params);
	}
	/**
	 * 根据客户id查询客户影像附件
	 * @param  customerId,
	 * @return
	 */
	public List<VideoAccessories> findCustomerVideoAccessoriesByCustomerId(String customerId) {
		List<VideoAccessories> list = commonDao.queryBySql(VideoAccessories.class, "select * from video_accessories t where t.customer_id='"+customerId+"'", null);
		return list;
	}

}
