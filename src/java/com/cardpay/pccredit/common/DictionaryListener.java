package com.cardpay.pccredit.common;

import java.util.HashMap;
import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.datapri.web.FlatTreeNode;
import com.cardpay.pccredit.system.model.Dict;
import com.wicresoft.jrad.modules.privilege.constant.PrivilegeConstants;
import com.wicresoft.util.spring.Beans;

@Service
public class DictionaryListener {

	@Lazy
	public void init(){
		CustomerInforService customerInforService =Beans.get(CustomerInforService.class);
		PccOrganizationService pccorganizationService =Beans.get(PccOrganizationService.class);
		
		//CustomerInforService customerInforService = (CustomerInforService)context.getBean("customerInforService");
		//PccOrganizationService pccorganizationService = (PccOrganizationService)context.getBean("pccOrganizationService");
		List<FlatTreeNode> flattreeNode = pccorganizationService.queryCurrentSubTreeList(PrivilegeConstants.INIT_ID);
		HashMap<String,List<Dict>> dicts = customerInforService.findDict();
		List<Dict> collectionMethods = customerInforService.findCollectionMethod();
		List<Dict> industryTypes = customerInforService.findIndustryType();
		List<Dict> positios = customerInforService.findPositio();
		List<Dict> titles = customerInforService.findTitle();
		List<Dict> oaccountMybankList = customerInforService.findOaccountMybankList();
		List<Dict> creditCardList = customerInforService.findCreditCardList();
		List<Dict> payMybankList = customerInforService.findPayMybankList();
		List<Dict> degreeeducationList = customerInforService.findDegreeeducationList();
		List<Dict> debitWayList = customerInforService.debitWayList();
		for(String key:dicts.keySet()){
			if("nationality".equalsIgnoreCase(key)){
				for(Dict dict:dicts.get("nationality")){
					Dictionary.nationalityList.put(dict.getTypeCode(), dict.getTypeName());
				}
			}else if("cardtype".equalsIgnoreCase(key)){
				for(Dict dict:dicts.get("cardtype")){
					Dictionary.cardTypeList.put(dict.getTypeCode(), dict.getTypeName());
				}
			}else if("maritalstatus".equalsIgnoreCase(key)){
				for(Dict dict:dicts.get("maritalstatus")){
					Dictionary.maritalStatusList.put(dict.getTypeCode(), dict.getTypeName());
				}
			}else if("residentialpropertie".equalsIgnoreCase(key)){
				for(Dict dict:dicts.get("residentialpropertie")){
					Dictionary.residentialPropertieList.put(dict.getTypeCode(), dict.getTypeName());
				}
			}else if("unitPropertis".equalsIgnoreCase(key)){
				for(Dict dict:dicts.get("unitPropertis")){
					Dictionary.unitPropertisList.put(dict.getTypeCode(), dict.getTypeName());
				}
			}
		}
		for(Dict dict : degreeeducationList){
			Dictionary.degreeeducationList.put(dict.getTypeCode(), dict.getTypeName());
		}
		for(FlatTreeNode dict: flattreeNode){
			Dictionary.orgList.put(dict.getId(),dict.getName());
		}
		for(Dict dict : collectionMethods){
			Dictionary.collectionMethodList.put(dict.getTypeCode(), dict.getTypeName());
		}
		for(Dict dict : industryTypes){
			Dictionary.industryTypeList.put(dict.getTypeCode(), dict.getTypeName());
		}
		for(Dict dict : positios){
			Dictionary.positioList.put(dict.getTypeCode(), dict.getTypeName());
		}
		for(Dict dict : titles){
			Dictionary.titleList.put(dict.getTypeCode(), dict.getTypeName());
		}
		for(Dict dict : oaccountMybankList){
			Dictionary.oaccountMybankList.put(dict.getTypeCode(), dict.getTypeName());
		}
		for(Dict dict : creditCardList){
			Dictionary.creditCardList.put(dict.getTypeCode(), dict.getTypeName());
		}
		for(Dict dict : payMybankList){
			Dictionary.payMybankList.put(dict.getTypeCode(), dict.getTypeName());
		}
		for(Dict dict :debitWayList){
			Dictionary.debitWayList.put(dict.getTypeCode(), dict.getTypeName());
		}
	}
	
}
