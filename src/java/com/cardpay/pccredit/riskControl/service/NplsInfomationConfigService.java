package com.cardpay.pccredit.riskControl.service;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.riskControl.dao.NplsInfomationConfigDao;
import com.cardpay.pccredit.riskControl.dao.Comdao.NplsInfomationConfigurationComdao;
import com.cardpay.pccredit.riskControl.model.NplsInfomationConfiguration;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;

@Service
public class NplsInfomationConfigService {
	
	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private  NplsInfomationConfigDao nplsInfomationConfigDao;
	
	
	@Autowired
	private NplsInfomationConfigurationComdao nplsInfomationConfigurationComdao;
	/**
	 * 显示不良资产规则配置页面
	 * @return
	 */
	public NplsInfomationConfiguration getNplsInfomationConfig(){
		
		return nplsInfomationConfigurationComdao.getNplsInfomationConfig();
	}
	
	/**
	 * 修改不良资产配置
	 * @param customerinforUpdateWorship id
	 */
	public void updateNplsInfomationConfig(NplsInfomationConfiguration nplsInfomationConfiguration,String id) {
		
		String nplsid =  nplsInfomationConfiguration.getId();
		Calendar calendar = Calendar.getInstance();
		NplsInfomationConfiguration result =  getNplsInfomationConfigById(nplsid);
		if(result != null){
			String indexId = result.getId();
			nplsInfomationConfiguration.setId(indexId);
			nplsInfomationConfiguration.setModifiedBy(id);
			nplsInfomationConfiguration.setModifiedTime(calendar.getTime());
			nplsInfomationConfigDao.updateNplsInfomationConfig(nplsInfomationConfiguration);
			
		}else{
			nplsInfomationConfiguration.setCreatedBy(id);
			nplsInfomationConfiguration.setCreatedTime(calendar.getTime());
			commonDao.insertObject(nplsInfomationConfiguration);
		}
		
		
	}
	/**
	 * 根据Id查询不良资产规则
	 * @return
	 */
	public NplsInfomationConfiguration getNplsInfomationConfigById(String id){
		
		
		NplsInfomationConfiguration nplsInfomationConfiguration = commonDao.findObjectById(NplsInfomationConfiguration.class, id);
		return nplsInfomationConfiguration;
	}
}
