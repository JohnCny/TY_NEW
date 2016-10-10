/**
 * 
 */
package com.cardpay.pccredit.manager.dao.comdao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.intopieces.constant.Constant;
import com.cardpay.pccredit.manager.model.ManagerAssessmentScore;
import com.cardpay.pccredit.manager.model.ManagerMonthTargetData;
import com.cardpay.pccredit.manager.model.MangerMonthAssessment;
import com.cardpay.pccredit.manager.model.PromotionRules;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;

/**
 * 描述 ：客户账户CommDao
 * @author 张石树
 *
 * 2014-11-13 上午11:25:58
 */
@Service
public class ManagerAssessmentScoreCommDao {

	@Autowired
	private CommonDao commonDao;

	/**
	 * 统计客户的授信额度信息
	 * @param endDate 
	 * @param startDate 
	 * @param customerIds
	 * @return
	 */
	public Double sumAppApplyQuota(List<String> customerIds) {
		StringBuffer sb = new StringBuffer();
		sb.append("(");
		for(String cId : customerIds){
			sb.append("'").append(cId).append("'").append(",");
		}
		sb = sb.deleteCharAt(sb.length() - 1);
		sb.append(")");
		String sql = "select nvl(sum(to_Number(t.actual_quote)), 0) as ACTUALQUOTE from customer_application_info t " +
				"where status=#{appStatus} and customer_id in " + sb.toString();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("appStatus", Constant.SUCCESS_INTOPICES);
		List<HashMap<String, Object>> list = commonDao.queryBySql(sql, params);
		BigDecimal b = (BigDecimal) list.get(0).get("ACTUALQUOTE");
		return b.doubleValue();
	}

	/**
	 * 统计客户的账单相关信息
	 * @param customerIds
	 * @param year
	 * @param month
	 * @return
	 */
	public List<HashMap<String, Object>> sumAccountBill(List<String> customerIds, int year, int month) {
		StringBuffer sb = new StringBuffer();
		sb.append("(");
		for(String cId : customerIds){
			sb.append("'").append(cId).append("'").append(",");
		}
		sb = sb.deleteCharAt(sb.length() - 1);
		sb.append(")");
		String sql = "select nvl(sum(to_Number(t.OVERDRAFT_ACCOUNT)), 0) as OVERDRAFT_ACCOUNT," +
			"nvl(sum(to_Number(t.PAID_INTEREST_ACCOUNT)), 0) as PAID_INTEREST_ACCOUNT," +
			"nvl(sum(to_Number(t.CURRENT_MONTH_INTEREST_ACCOUNT)), 0) as CURRENT_MONTH_INTEREST_ACCOUNT," +
			"nvl(sum(to_Number(t.OVERDUE_AMOUNT)), 0) as OVERDUE_AMOUNT," +
			"nvl(sum(to_Number(t.PAYBACK_ACCOUNT)), 0) as PAYBACK_ACCOUNT," +
			"nvl(sum(to_Number(t.BALANCE_ACCOUNT)), 0) as BALANCE_ACCOUNT" +
			" from customer_account_bill t left join " +
			" customer_account_information cai on t.account_number = cai.account_number" +
			" where t.bill_data_year=" + year + 
			" and t.bill_data_month=" + month +
			" and cai.customer_id in" + sb.toString();
		return commonDao.queryBySql(sql, null);
	}

	/**
	 * 统计客户经理日均透支余额
	 * @param managerIds
	 * @param year
	 * @param month
	 * @return
	 */
	public Double perDayAverageCreditlineAccount(List<String> managerIds, int year, int month) {
		StringBuffer sb = new StringBuffer();
		sb.append("(");
		for(String cId : managerIds){
			sb.append("'").append(cId).append("'").append(",");
		}
		sb = sb.deleteCharAt(sb.length() - 1);
		sb.append(")");
		String sql = "select nvl(sum(to_Number(t.AVERAGE_DAILY_OVERDRAFT)), 0) as AVERAGE_DAILY_OVERDRAFT" +
			" from average_daily_overdraft t left join " +
			" customer_account_information cai on t.account_number = cai.account_number" +
			" where t.year=" + year + 
			" and t.month=" + month +
			" and cai.customer_id in ( select id from basic_customer_information where user_id in" + sb.toString() + ")";
		List<HashMap<String, Object>> list = commonDao.queryBySql(sql, null);
		BigDecimal b = (BigDecimal) list.get(0).get("AVERAGE_DAILY_OVERDRAFT");
		return b.doubleValue();
	}
	
	/**
	 * 催收后的还款总额
	 * @param customerIds
	 */
	public Double sumOverduePaybackAll(List<String> customerIds) {
		StringBuffer sb = new StringBuffer();
		sb.append("(");
		for(String cId : customerIds){
			sb.append("'").append(cId).append("'").append(",");
		}
		sb = sb.deleteCharAt(sb.length() - 1);
		sb.append(")");
		String sql = "select nvl(sum(to_Number(t.OVERDUE_PAYBACK_ALL)), 0) as OVERDUE_PAYBACK_ALL from customer_overdue t " +
				"where customer_id in " + sb.toString();
		List<HashMap<String, Object>> list = commonDao.queryBySql(sql, null);
		BigDecimal b = (BigDecimal) list.get(0).get("OVERDUE_PAYBACK_ALL");
		return b.doubleValue();
	}

	/**
	 * 激活卡统计
	 * @param endDateStr 
	 * @param startDateStr 
	 * @param customerIds
	 * @return
	 */
	public Integer countActivateCard(int year, int month, List<String> customerIds) {
		StringBuffer sb = new StringBuffer();
		sb.append("(");
		for(String cId : customerIds){
			sb.append("'").append(cId).append("'").append(",");
		}
		sb = sb.deleteCharAt(sb.length() - 1);
		sb.append(")");
		String sql = "select count(1) as JHL from customer_card_information t "+
				" left join card_information ci on ci.id = t.card_id "+
				" where t.activation_status='0' and t.year=#{year} and t.month=#{month}" +
				" and customer_id in " + sb.toString();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", "0");
		params.put("year", year);
		params.put("month", month);
		List<HashMap<String, Object>> list = commonDao.queryBySql(sql, params);
		BigDecimal b = (BigDecimal) list.get(0).get("JHL");
		return b.intValue();
	}

	/**
	 * 活跃卡统计
	 * @param month 
	 * @param year 
	 * @param customerIds
	 * @return
	 */
	public Integer countActiveCard(int year, int month, List<String> customerIds) {
		StringBuffer sb = new StringBuffer();
		sb.append("(");
		for(String cId : customerIds){
			sb.append("'").append(cId).append("'").append(",");
		}
		sb = sb.deleteCharAt(sb.length() - 1);
		sb.append(")");
		String sql = "select count(1) as HYK from customer_card_information t "+
			" left join card_information ci on ci.id = t.card_id "+
			" where t.active_status=#{status} and t.year=#{year} and t.month=#{month}" +
			" and customer_id in " + sb.toString();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", "0");
		params.put("year", year);
		params.put("month", month);
		List<HashMap<String, Object>> list = commonDao.queryBySql(sql, params);
		BigDecimal b = (BigDecimal) list.get(0).get("HYK");
		return b.intValue();
	}

	/**
	 * 获取所有的晋级规则
	 * @return
	 */
	public List<PromotionRules> findAllPromotionRules() {
		String sql = "select * from promotion_rules";
		return commonDao.queryBySql(PromotionRules.class, sql, null);
	}

	/**
	 * 根据年，月 获取客户经理的指标数据
	 * @param year
	 * @param month
	 * @param customerManagerId
	 * @return
	 */
	public ManagerMonthTargetData findManangerMonthTargetDataBy(int year,
			int month, String customerManagerId, String level) {
		String sql = "select * from manager_month_target_data "+
			" where data_year=#{year} and data_month=#{month} " +
			" and customer_manager_id = #{customerManagerId} " +
			" and customer_manager_level = #{level}";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerManagerId", customerManagerId);
		params.put("year", year);
		params.put("month", month);
		params.put("level", level);
		List<ManagerMonthTargetData> list = commonDao.queryBySql(ManagerMonthTargetData.class, sql, params);
		if(list != null && list.size() > 0){
			return list.get(0);
		} else {
			return null;
		}
	}
	
	/**
	 * 根据年，月 获取客户经理的指标数据
	 * @param year
	 * @param month
	 * @param customerManagerId
	 * @return
	 */
	public List<ManagerMonthTargetData> findManangerMonthTargetData(String customerManagerId, String level, int topItem) {
		String sql = "SELECT * FROM "+
				"  (SELECT ROW_.*,ROWNUM ROWNUM_ "+
				"   FROM ( "+
				"    select * from manager_month_target_data "+
	            "     where customer_manager_id = #{customerManagerId} " +
	            "     and customer_manager_level = #{level}" +
	            "     order by data_year desc, data_month desc   "+
	            "  ) ROW_ "+
	            "   WHERE ROWNUM <= #{topItem})  "+
	            " WHERE ROWNUM_ > 0";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerManagerId", customerManagerId);
		params.put("level", level);
		return commonDao.queryBySql(ManagerMonthTargetData.class, sql, params);
	}

	/**
	 * 获取最近的topItem跳数据
	 * @param customerManagerId
	 * @param topItem
	 * @return
	 */
	public List<ManagerAssessmentScore> findManagerAssessmentScoreByYearAndMonth(String customerManagerId, String level, int topItem) {
		String sql = "SELECT * FROM "+
				"  (SELECT ROW_.*,ROWNUM ROWNUM_ "+
				"   FROM ( "+
				"    select * from manager_assessment_score "+
	            "     where assessed = #{customerManagerId} " +
	            "     and customer_manager_level = #{level}" +
	            "     order by data_year desc, data_month desc   "+
	            "  ) ROW_ "+
	            "   WHERE ROWNUM <= #{topItem})  "+
	            " WHERE ROWNUM_ > 0";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerManagerId", customerManagerId);
		params.put("topItem", topItem);
		params.put("level", level);
		return commonDao.queryBySql(ManagerAssessmentScore.class, sql, params);
	}
	
	/**
	 * 获取最近三个月的
	 * @param customerManagerId
	 * @param topItem
	 * @return
	 */
	public List<ManagerMonthTargetData> findQuarterPerCreditlineAccount(String customerManagerId, String level, int topItem) {
		String sql = "SELECT * FROM "+
				"  (SELECT ROW_.*,ROWNUM ROWNUM_ "+
				"   FROM ( "+
				"    select * from manager_month_target_data "+
	            "     where customer_manager_id = #{customerManagerId} " +
	            "     and customer_manager_level = #{level}" +
	            "     order by data_year desc, data_month desc "+
	            "  ) ROW_ "+
	            "   WHERE ROWNUM <= #{topItem})  "+
	            " WHERE ROWNUM_ > 0";
	      
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerManagerId", customerManagerId);
		params.put("topItem", topItem);
		params.put("level", level);
		return commonDao.queryBySql(ManagerMonthTargetData.class, sql, params);
	}

	/**
	 * 获取所有客户经理级别月度考核指标信息
	 * @return
	 */
	public List<MangerMonthAssessment> findManagerMonthAssessmentAll() {
		String sql = "select * from manger_month_assessment";
		return commonDao.queryBySql(MangerMonthAssessment.class, sql, null);
	}

	/**
	 *  查询自然年内
	 * @param customerManagerId
	 * @param levelInformation
	 * @param year
	 * @return
	 */
	public List<ManagerAssessmentScore> findManagerAssessmentScoreByYear(
			String customerManagerId, String levelInformation, int year) {
		String sql = "select * from manager_assessment_score "+
	            "where assessed = #{customerManagerId} " +
	            "and customer_manager_level = #{levelInformation} " +
	            " and data_year=#{year} order by data_month asc  ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerManagerId", customerManagerId);
		params.put("levelInformation", levelInformation);
		params.put("year", year);
		return commonDao.queryBySql(ManagerAssessmentScore.class, sql, params);
	}

	/**
	 * 查询自然年内
	 * @param userId
	 * @param levelInformation
	 * @param year
	 * @return
	 */
	public List<ManagerMonthTargetData> findManangerMonthTargetDataByYear(
			String customerManagerId, String levelInformation, int year) {
		String sql = "select * from manager_month_target_data "+
	            " where customer_manager_id = #{customerManagerId} " +
	            " and customer_manager_level = #{levelInformation}" +
	            " and data_year = #{year}" +
	            " order by data_month asc";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerManagerId", customerManagerId);
		params.put("levelInformation", levelInformation);
		params.put("year", year);
		return commonDao.queryBySql(ManagerMonthTargetData.class, sql, params);
	}

	/**
	 * 是否已经存在
	 * @param customerManagerId
	 * @param year
	 * @param month
	 * @return
	 */
	public int findManagerLevelAdjustment(String customerManagerId, int year, int month) {
		String sql = "select count(1) as applyQuotaSum from manager_level_adjustment " +
				" where customer_manager_id=#{customerManagerId} and data_year = #{year} and data_month=#{month}";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerManagerId", customerManagerId);
		params.put("year", year);
		params.put("month", month);
		List<HashMap<String, Object>> list = commonDao.queryBySql(sql, params);
		BigDecimal b = (BigDecimal) list.get(0).get("APPLYQUOTASUM");
		return b.intValue();
	}

	/**
	 * 根据年,月,客户经理id,级别查询评估信息
	 * @param year
	 * @param month
	 * @param customerManagerId
	 * @param levelInformation
	 * @return
	 */
	public ManagerAssessmentScore findManagerAssessmentScoreByYearAndMonth(int year, int month,
			String customerManagerId, String levelInformation) {
		String sql = "select * from manager_assessment_score "+
	            "where assessed = #{customerManagerId} " +
	            "and customer_manager_level = #{levelInformation} " +
	            " and data_year=#{year} and data_month=#{month}";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerManagerId", customerManagerId);
		params.put("levelInformation", levelInformation);
		params.put("year", year);
		params.put("month", month);
		List<ManagerAssessmentScore> scores = commonDao.queryBySql(ManagerAssessmentScore.class, sql, params);
		if(scores != null && scores.size() > 0){
			return scores.get(0);
		} else {
			return null;
		}
	}
	
}
