package com.cardpay.pccredit.customer.dao.comdao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.customer.constant.AmountAdjustmentApproveStatusEnum;
import com.cardpay.pccredit.customer.model.AmountAdjustment;
import com.cardpay.pccredit.customer.web.CustomerAdjustmentQuotaForm;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.util.date.DateHelper;

@Service
public class CustomerQuotaComdao {
	
	@Autowired
	private CommonDao commonDao;
	
	public List<String> getAverageDailyOverdraftAccountNumber(){
	   Calendar calendar = Calendar.getInstance();
	   Date date = calendar.getTime();
	   Date endDate = date;
	   Date startDate = DateHelper.normalizeDate(shiftMonth(date, 6), "yyyy-MM-dd");
	   HashMap<String, Object> params = new HashMap<String, Object>();
	   params.put("startDate", startDate);
	   params.put("endDate", endDate);
	   params.put("lowDueStatus", 1);
	   List<HashMap<String, Object>> accountNumberMap = commonDao.queryBySql("select account_number from average_daily_overdraft where low_due_status=#{lowDueStatus} and created_time>#{startDate} and created_time<#{endDate} group by account_number",params);
	   List<String> accountNumberlist = new ArrayList<String>();
	   for(int i=0;i<accountNumberMap.size();i++){
			
			HashMap<String, Object> ccountNumbertString = accountNumberMap.get(i);
			if(ccountNumbertString != null){
				String accountNumber = ccountNumbertString.values().toString();
				 params.put("accountNumber", accountNumber);
				 List<HashMap<String, Object>> countMap = commonDao.queryBySql("select count(*) from average_daily_overdraft where low_due_status=#{lowDueStatus} and created_time>#{startDate} and created_time<#{endDate} and account_number=#{accountNumber}",params);
				 HashMap<String, Object> countString = countMap.get(0);
					if(countString != null){
						String rankString = countString.values().toString();
						int count = Integer.valueOf(rankString);
						if(count >= 6){
							accountNumberlist.add(accountNumber);	
						}
					}
			}
		}
	return accountNumberlist;
	}
	
	
	/**
	 * 得到基于当前日期并偏移一定月份的的偏移日期
	 * 
	 * @param date
	 * @param shiftMonth
	 * @return
	 */
	public static Date shiftMonth(Date date, int shiftMonth) {
		
		if (date != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.MONTH + 1, -shiftMonth);

			return cal.getTime();
		}
		
		return null;
	}
	
	/**
	 * 比较两个两个日期的月份是否相邻
	 */
	public Boolean compareDate(String dateString1, String dateString2){
		
		
		Date date1 = DateHelper.getDateFormat(dateString1, "yyyy-MM-dd");
		Date date2 = DateHelper.getDateFormat(dateString2, "yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date1);
		int val1 = cal.get(Calendar.MONTH + 1);
		cal.setTime(date2);
		int val2 = cal.get(Calendar.MONTH + 1);
		if(Math.abs(val1 - val2) == 1)
		 {
			return true;
		}else{
			 
			return false; 
		 }
	}


	public List<CustomerAdjustmentQuotaForm> findQutoaAdjustInfo(
			String productId, List<String> itemCustIds) {
		StringBuffer sb = new StringBuffer();
		sb.append("(");
		for(String cId : itemCustIds){
			sb.append("'").append(cId).append("'").append(",");
		}
		sb = sb.deleteCharAt(sb.length() - 1);
		sb.append(")");
		String sql = "select t.id as id, c.card_type, c.card_id, t.actual_quote as actual_amount,t.apply_quota as app_quota_amount," +
				" t.customer_id, c.chinese_name as customer_name, p.product_name, t.product_id" +
				" from customer_application_info t left join basic_customer_information c " +
				" on t.customer_id = c.id left join product_attribute p on t.product_id = p.id " +
				" where t.product_id = #{productId} and t.customer_id in" + sb.toString();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("productId", productId);
		return commonDao.queryBySql(CustomerAdjustmentQuotaForm.class, sql, params);
	}

	/**
	 * 根据产品id和客户id 查询已调额在审批中的调额信息 
	 * @param productId
	 * @param customerIds
	 * @return
	 */
	public List<AmountAdjustment> findApprovalNoList(String productId, List<String> customerIds) {
		StringBuffer sb = new StringBuffer();
		sb.append("(");
		for(String cId : customerIds){
			sb.append("'").append(cId).append("'").append(",");
		}
		sb = sb.deleteCharAt(sb.length() - 1);
		sb.append(")");
		String sql = "select * from amount_adjustment t" +
				" where t.approval =#{approval} " +
				" and t.product_id = #{productId} and t.customer_id in" + sb.toString();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("productId", productId);
		params.put("approval", AmountAdjustmentApproveStatusEnum.Audit.name());
		return commonDao.queryBySql(AmountAdjustment.class, sql, params);
	}
	
}
