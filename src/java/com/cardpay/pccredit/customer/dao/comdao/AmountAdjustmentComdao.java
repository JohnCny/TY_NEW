package com.cardpay.pccredit.customer.dao.comdao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.customer.model.AmountAdjustmentProcess;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;

/**
 * 
 * 描述 ：额度调整dao
 * @author 张石树
 *
 * 2014-12-5 上午10:31:49
 */
@Service
public class AmountAdjustmentComdao {
	
	@Autowired
	private CommonDao commonDao;

	public int getWaitProcessAmountAdjustmentCountByUserId(String userId) {
		String sql = "SELECT count(1) as COUNT FROM amount_adjustment t left join amount_adjustment_process pr " +
				" on t.id=pr.amount_adjustment_id " +
				" where pr.delay_audit_user = #{userId}";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		List<HashMap<String, Object>> list = commonDao.queryBySql(sql, params);
		if(list != null && list.size() > 0){
			BigDecimal b = (BigDecimal) list.get(0).get("COUNT");
			return b.intValue();
		}
		return 0;
	}

	public AmountAdjustmentProcess findAmountAdjustmentProcess(String amountAdjustmentId, String serialNumber) {
		String sql = "select * from amount_adjustment_process " +
				" where amount_adjustment_id=#{amountAdjustmentId} and serial_number = #{serialNumber}";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("amountAdjustmentId", amountAdjustmentId);
		params.put("serialNumber", serialNumber);
		List<AmountAdjustmentProcess> processes = commonDao.queryBySql(AmountAdjustmentProcess.class , sql, params);
		if(processes != null && processes.size() > 0){
			return processes.get(0);
		} 
		return null;
	}
	
}
