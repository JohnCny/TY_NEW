package com.cardpay.pccredit.riskControl.dao;

import com.cardpay.pccredit.riskControl.model.NplsInfomationConfiguration;
import com.wicresoft.util.annotation.Mapper;

/**
 * 
 * @author 季东晓
 *
 * 2014-11-24 下午6:01:10
 */
@Mapper
public interface NplsInfomationConfigDao {
	
	
	
	/**
	 * 修改不良资产配置
	 * @return int
	 */
	public void updateNplsInfomationConfig(NplsInfomationConfiguration nplsInfomationConfiguration);
}
