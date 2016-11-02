package com.cardpay.pccredit.divisional.dao.comdao;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.divisional.filter.DivisionalFilter;
import com.cardpay.pccredit.divisional.model.DivisionalTransfer;
import com.cardpay.pccredit.divisional.model.DivisionalWeb;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.model.QueryResult;

/**
 * 
 * @author 姚绍明
 *
 * 
 */
@Service
public class DivisionalCommDao {
	@Autowired
	private CommonDao commonDao;
	/**
	 * 分案申请信息 主管
	 * @param filter
	 * @return
	 */
	public QueryResult<DivisionalWeb> findDivisional(DivisionalFilter filter){
		HashMap<String, Object> params = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer(); 
		sql.append("select d.id,chinese_name,");
		sql.append("card_type,card_id,residential_address,telephone,divisional_result ");
		sql.append("from basic_customer_information b,divisional_application d ");
		sql.append("where b.id=d.customer_id ");
		sql.append("and d.current_organization_id='"+filter.getCurrentOrganizationId()+"' ");
		sql.append("and d.divisional_progress='"+filter.getDivisionalProgress()+"'");
		return commonDao.queryBySqlInPagination(DivisionalWeb.class, sql.toString(), params,
				filter.getStart(), filter.getLimit());
	}
	/**
	 * 分案申请信息 客户经理
	 * @param filter
	 * @return
	 */
	public QueryResult<DivisionalWeb> findDivisionalByCustomerManager(DivisionalFilter filter){
		HashMap<String, Object> params = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer(); 
		sql.append("select d.id，customer_id,chinese_name,");
		sql.append("card_type,card_id,residential_address,telephone,divisional_result ");
		sql.append("from basic_customer_information b,divisional_application d ");
		sql.append("where b.id=d.customer_id ");
		sql.append("and d.divisional_progress!='"+filter.getDivisionalProgress()+"' ");
		sql.append("and d.customer_manager_id='"+filter.getCustomerManagerId()+"' order by d.divisional_result");
		return commonDao.queryBySqlInPagination(DivisionalWeb.class, sql.toString(), params,
				filter.getStart(), filter.getLimit());
	}
	/**
	 * 分案申请信息  卡中心
	 * @param filter
	 * @return
	 */
	public QueryResult<DivisionalWeb> findDivisionalByCardCenter(DivisionalFilter filter){
		HashMap<String, Object> params = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer(); 
		sql.append("select d.id,chinese_name,");
		sql.append("card_type,card_id,residential_address,telephone,divisional_result ");
		sql.append("from basic_customer_information b,divisional_application d ");
		sql.append("where b.id=d.customer_id ");
		sql.append("and d.divisional_progress='"+filter.getDivisionalProgress()+"'");
		return commonDao.queryBySqlInPagination(DivisionalWeb.class, sql.toString(), params,
				filter.getStart(), filter.getLimit());
	}
	/**
	 * 分案申请信息  移交客户
	 * @param filter
	 * @return
	 */
	public QueryResult<DivisionalTransfer> findDivisionalTransfer(DivisionalFilter filter){
		HashMap<String, Object> params = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer(); 
		sql.append("select b.chinese_name,b.card_type,b.card_id,b.residential_address,b.telephone,b.divisional_status,d.divisional_result");
		sql.append(" from basic_customer_information b,divisional_application d");
		sql.append(" where b.id=d.customer_id");
		sql.append(" and d.original_manager_old='"+filter.getOriginalManagerOld()+"'");
		sql.append(" order by d.created_time desc");
		return commonDao.queryBySqlInPagination(DivisionalTransfer.class, sql.toString(), params,
				filter.getStart(), filter.getLimit());
	}

}
