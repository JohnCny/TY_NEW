package com.cardpay.pccredit.manager.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.manager.model.MaintenanceAccountManager;
import com.cardpay.pccredit.manager.service.MaintenanceAccountManagerService;
import com.cardpay.pccredit.riskControl.model.ManagerCustomerType;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.modules.dictionary.DictionaryManager;
import com.wicresoft.jrad.modules.dictionary.model.Dictionary;
import com.wicresoft.jrad.modules.dictionary.model.DictionaryItem;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;

@Controller
@RequestMapping("/manager/manageraccountmaintenance/*")
@JRadModule("manager.manageraccountmaintenance")
public class MaintenanceAccountManagerController {
	
	final public static String SEPARATOR  = ",";
	
	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private MaintenanceAccountManagerService maintenanceAccountManagerService;
	
	/**
	 * 客户经理参数维护参数显示
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "update.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browse(HttpServletRequest request) {
		DictionaryManager dictMgr = Beans.get(DictionaryManager.class);
		//查询层级信息 
		List<MaintenanceAccountManager> maintenanceAccountManagerlist = maintenanceAccountManagerService.getMaintenanceAccountManager();
		List<MaintenanceAccountManager> nplsInfomationConfigurationlist = new ArrayList<MaintenanceAccountManager>();
		for(MaintenanceAccountManager maintenanceAccountManager : maintenanceAccountManagerlist){
		
			  String  customerTypeCode = maintenanceAccountManager.getCustomerTypeCode();
			  String  customerType ="";
			   if(customerTypeCode !="" && customerTypeCode != null){
			   String[] result = customerTypeCode.split(SEPARATOR);
			    for(int i=0;i < result.length ;i++){
				
				Dictionary dictionary = dictMgr.getDictionaryByName("KHZZ");
				List<DictionaryItem> dictItems = dictionary.getItems();
				
				for (DictionaryItem dictItem : dictItems) {
					if (dictItem.getName().equals(result[i])) {
						customerType += dictItem.getTitle() + ",";
						break;
					}
				}
			}
			}
			
			if(customerType !=""){
				customerType =	customerType.substring(0, customerType.length() - 1);
			}
				
				maintenanceAccountManager.setCustomerType(customerType);
				nplsInfomationConfigurationlist.add(maintenanceAccountManager);
		
			
		
		}
		JRadModelAndView mv = new JRadModelAndView("/manager/managermaintenance/manager_maintenance_update", request);
		
		// 根据指定名得到字典值列表
		Dictionary dictionary = dictMgr.getDictionaryByName("CUSTOMERMANAGERLEVEL");
		List<DictionaryItem> dictItems = dictionary.getItems();
		Dictionary customerTypeDictionary = dictMgr.getDictionaryByName("KHZZ");
		List<DictionaryItem> customerTypeDictItems = customerTypeDictionary.getItems();
		mv.addObject("nplsInfomationConfigurationlist",nplsInfomationConfigurationlist);
		mv.addObject("dictItems",dictItems);
		mv.addObject("customerTypeDictItems",customerTypeDictItems);
		return mv;
	}
	/**
	 * 客户经理参数维护保存
	 * 
	 * @param request
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "save.page", method = { RequestMethod.POST })
	@JRadOperation(JRadOperation.CHANGE)
	public AbstractModelAndView maintenanceAccountManagerchange(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/manager/managermaintenance/manager_maintenance_update", request);
		try {
			maintenanceAccountManagerService.updateMaintenanceAccountManager(request);
			DictionaryManager dictMgr = Beans.get(DictionaryManager.class);
			//查询层级信息 
			List<MaintenanceAccountManager> maintenanceAccountManagerlist = maintenanceAccountManagerService.getMaintenanceAccountManager();
			List<MaintenanceAccountManager> nplsInfomationConfigurationlist = new ArrayList<MaintenanceAccountManager>();
			for(MaintenanceAccountManager maintenanceAccountManager : maintenanceAccountManagerlist){
			
				  String  customerTypeCode = maintenanceAccountManager.getCustomerTypeCode();
				  String  customerType ="";
				   if(customerTypeCode !="" && customerTypeCode != null){
				   String[] result = customerTypeCode.split(SEPARATOR);
				    for(int i=0;i < result.length ;i++){
					
					Dictionary dictionary = dictMgr.getDictionaryByName("KHZZ");
					List<DictionaryItem> dictItems = dictionary.getItems();
					
					for (DictionaryItem dictItem : dictItems) {
						if (dictItem.getName().equals(result[i])) {
							customerType += dictItem.getTitle() + ",";
							break;
						}
					}
				}
				}
				
				if(customerType !=""){
					customerType =	customerType.substring(0, customerType.length() - 1);
				}
					
					maintenanceAccountManager.setCustomerType(customerType);
					nplsInfomationConfigurationlist.add(maintenanceAccountManager);
			
				
			
			}
			Dictionary customerTypeDictionary = dictMgr.getDictionaryByName("KHZZ");
			List<DictionaryItem> customerTypeDictItems = customerTypeDictionary.getItems();
			mv.addObject("nplsInfomationConfigurationlist",nplsInfomationConfigurationlist);
			mv.addObject("customerTypeDictItems",customerTypeDictItems);
		} catch (Exception e) {
            //TODO 有日志功能，在这一步应保持返回统一，出错以后查看日志
			logger.error("执行修改客户经理参数维护错误"+e.getMessage());
			e.printStackTrace();
		}
		return mv;
	}

}
