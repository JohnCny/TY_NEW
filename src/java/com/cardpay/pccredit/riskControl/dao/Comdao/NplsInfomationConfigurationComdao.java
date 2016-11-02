package com.cardpay.pccredit.riskControl.dao.Comdao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.riskControl.model.NplsInfomation;
import com.cardpay.pccredit.riskControl.model.NplsInfomationConfiguration;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
/**
 * 
 * @author 季东晓
 *
 * 2014-11-20 下午2:34:11
 */
@Service
public class NplsInfomationConfigurationComdao {
	
	@Autowired
	private CommonDao commonDao;
	/**
	 * 显示不良资产规则配置页面
	 * @return
	 */
	public NplsInfomationConfiguration getNplsInfomationConfig(){
		
		String sql = "select * from npls_information_configuration where 1=1";
		List<NplsInfomationConfiguration> list = commonDao.queryBySql(NplsInfomationConfiguration.class, sql, null);
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;	
		}
	}

	/**
	 * 获取不良资产已确认的信息
	 * @return
	 */
	public NplsInfomation getConfirmNplsInfomation(String customerId, String accountId, String verificationStatus){
		String sql = "select * from npls_information where customer_id = #{customerId} and account_id=#{accountId}" +
				" and verification_status=#{verificationStatus}";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerId", customerId);
		params.put("accountId", accountId);
		params.put("verificationStatus", verificationStatus);
		List<NplsInfomation> list = commonDao.queryBySql(NplsInfomation.class, sql, params);
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;	
		}
	}
}
