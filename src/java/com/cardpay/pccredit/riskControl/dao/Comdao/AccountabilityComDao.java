package com.cardpay.pccredit.riskControl.dao.Comdao;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.riskControl.filter.AccountabilityRecordFilter;
import com.cardpay.pccredit.riskControl.model.AccountabilityRecord;
import com.cardpay.pccredit.riskControl.model.ProductAccountability;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.util.date.DateHelper;
/**
 * 
 * @author 季东晓
 *
 * 2014-11-5 下午2:32:59
 */
@Service
public class AccountabilityComDao {
	
	@Autowired
	private CommonDao commonDao;

	/* 问责记录查询 */
	public QueryResult<AccountabilityRecord> findAccountabilityRecordByFilter(
			AccountabilityRecordFilter filter) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		String chineseName = filter.getChineseName();
		String cardType = filter.getCardType();
		String cardId = filter.getCardId();
		String customerManagerId = filter.getCustomerManagerId();
		String auditStatus = filter.getAuditStatus();
		String sql = "select a.*, b.chinese_name,b.card_type,b.card_id,s.display_name from accountability_record a, basic_customer_information b,sys_user s where a.customer_manager_id=s.id  and a.customer_id=b.id ";
		if(StringUtils.trimToNull(customerManagerId)!=null){
			params.put("customerManagerId", customerManagerId);
			String sqlCustomerManagerId = "and a.customer_manager_id=#{customerManagerId}";
			 sql = sql + sqlCustomerManagerId;
			}
		if(StringUtils.trimToNull(auditStatus)!=null){
			params.put("auditStatus", auditStatus);
			String sqlAuditStatus = "and a.audit_status <> #{auditStatus}";
			sql = sql + sqlAuditStatus;
			}
		if(StringUtils.trimToNull(chineseName)!=null){
		params.put("chineseName", chineseName);
		String sqlname = "and b.chinese_name like '%'||#{chineseName}||'%'";
		  sql = sql + sqlname;
		}
		if(StringUtils.trimToNull(cardType)!=null){
		params.put("cardType", cardType);
		String sqlType = "and  b.card_type=#{cardType}";
		 sql = sql + sqlType;
		}
		if(StringUtils.trimToNull(cardId)!=null){
		params.put("cardId", cardId);
		String sqlId =  " and b.card_id=#{cardId}";
		 sql = sql + sqlId;
		}
		
		String sqlend = "order by a.modified_time desc";
		sql = sql + sqlend;
		return commonDao.queryBySqlInPagination(AccountabilityRecord.class, sql, params,
				filter.getStart(), filter.getLimit());
	}
	
	/**
	 * 查询所有产品的问责规则
	 * @return List
	 */
	public  List<ProductAccountability> findProductAccountability(){
		
		String sql = "select * from product_accountability where 1=1";
		return commonDao.queryBySql(ProductAccountability.class, sql, null);
	}
	
	/**
	 * 根据客户Id和产品Id 查询问责记录
	 * @return List
	 */
	public  AccountabilityRecord findAccountabilityRecordBy(String customerId,String productId){
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("customerId", customerId);
		params.put("productId", productId);
		String sql = "select * from accountability_record a where a.customer_id=#{customerId} and a.product_id =#{productId}";
		List<AccountabilityRecord> list= commonDao.queryBySql(AccountabilityRecord.class, sql, params);
		if(list.size() > 0){
		AccountabilityRecord accountabilityRecord = list.get(0);
		return accountabilityRecord;
		}else{
			
			return null;
			
		}
	}
	/**
	 * 根据客户Id和产品Id和客户经理 Id  查询问责记录
	 * @return List
	 */
	public  AccountabilityRecord findAccountabilityRecordByIds(String customerId,String productId,String customerManagerId){
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("customerId", customerId);
		params.put("productId", productId);
		params.put("customerManagerId", customerManagerId);
		String sql = "select * from accountability_record a where a.customer_id=#{customerId} and a.product_id =#{productId} and a.customer_manager_id =#{customerManagerId}";
		List<AccountabilityRecord> list= commonDao.queryBySql(AccountabilityRecord.class, sql, params);
		if(list.size() > 0){
		AccountabilityRecord accountabilityRecord = list.get(0);
		return accountabilityRecord;
		}else{
			
			return null;
			
		}
	}
	
}
