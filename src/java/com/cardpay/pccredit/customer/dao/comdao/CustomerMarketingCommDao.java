package com.cardpay.pccredit.customer.dao.comdao;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.customer.filter.CustomerMarketingFilter;
import com.cardpay.pccredit.customer.model.MarketingPlanWeb;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.model.QueryResult;

@Service
public class CustomerMarketingCommDao {
	@Autowired
	private CommonDao commonDao;
	/**
	 * 得到营销计划页面信息
	 * @param filter
	 * @return
	 */
	public QueryResult<MarketingPlanWeb> findMarketingPlansByFilter(CustomerMarketingFilter filter) {
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("customerManagerId", filter.getCustomerManagerId());
		StringBuffer sql = new StringBuffer();
		sql.append("select m.id,b.chinese_name,p.product_name,m.marketing_time,m.marketing_method,m.marketing_endtime,m.end_result,m.create_way,");
		sql.append("(select count(*) from marketing_plans_action a where a.marketing_plan_id=m.id) as count_action ");
		sql.append("from marketing_plan m ");
		sql.append("left join product_attribute p ");
		sql.append("on m.product_id=p.id ");
		sql.append("left join basic_customer_information b ");
		sql.append("on m.customer_id=b.id where b.user_id=#{customerManagerId} order by m.create_way,m.created_time desc");
		return commonDao.queryBySqlInPagination(MarketingPlanWeb.class, sql.toString(), params,
				filter.getStart(), filter.getLimit());
	}
}
