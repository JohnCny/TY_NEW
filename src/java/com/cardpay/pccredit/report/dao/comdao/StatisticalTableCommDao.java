package com.cardpay.pccredit.report.dao.comdao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.report.model.StatisticalTable;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;

@Service
public class StatisticalTableCommDao {

	@Autowired
	private CommonDao commonDao;
	
	public List<HashMap<String, Object>> getManagerStatisticalData(){
		String sql = " select bci.user_id as MANAGERID, ci.product_id as PRODUCTID, " +
				" nvl(sum(t.principal_overdraft), 0) as PRINCIPALOVERDRAFT, nvl(sum(t.total_amount_overdraft), 0) as TOTALAMOUNTOVERDRAFT" + 
				" from customer_account_information t left join card_information ci " + 
				" on t.customer_id = ci.customer_id left join basic_customer_information bci " + 
				" on ci.customer_id = bci.id group by bci.user_id, ci.product_id";
		return commonDao.queryBySql(sql, null);
	}
	
	public List<HashMap<String, Object>> getManagerAverageDailyOverdraft(int year, String managerId){
		String sql = " select nvl(sum(t.principal_overdraft), 0) as PRINCIPALOVERDRAFT, " +
				" nvl(sum(t.total_amount_overdraft), 0) as TOTALAMOUNTOVERDRAFT " +
				" from average_daily_overdraft t left join customer_account_information cai" +
				" on t.account_number = cai.account_number left join card_information ci" +
				" on cai.customer_id = ci.customer_id left join basic_customer_information bci" +
				" on ci.customer_id = bci.id where t.year=#{year} and bci.user_id=#{managerId}";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("year", year);
		params.put("managerId", managerId);
		return commonDao.queryBySql(sql, null);
	}
	
	
	
	public StatisticalTable getStatisticalTable(String createDateStr, String managerId, String productId){
		String sql = "select * from statistical_table where to_char(created_date, 'yyyy-MM-dd') = #{createDateStr} and " +
				" customer_manager_id = #{managerId} and product_id = #{productId}";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("createDateStr", createDateStr);
		params.put("managerId", managerId);
		params.put("productId", productId);
		List<StatisticalTable> list = commonDao.queryBySql(StatisticalTable.class, sql, params);
		if(list != null && list.size() > 0){
			return list.get(0);
		} else {
			return null;
		}
	}
}
