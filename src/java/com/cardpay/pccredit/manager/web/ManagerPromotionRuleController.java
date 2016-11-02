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

import com.cardpay.pccredit.customer.model.CustomerManagerTarget;
import com.cardpay.pccredit.manager.constant.ManagerTargetType;
import com.cardpay.pccredit.manager.model.DownGradeRule;
import com.cardpay.pccredit.manager.model.MaintenanceAccountManager;
import com.cardpay.pccredit.manager.model.ManagerPromotionDownRule;
import com.cardpay.pccredit.manager.model.ManagerPromotionRule;
import com.cardpay.pccredit.manager.model.MangerMonthAssessment;
import com.cardpay.pccredit.manager.model.TPerformanceParameters;
import com.cardpay.pccredit.manager.model.TyPerformanceCenter;
import com.cardpay.pccredit.manager.model.TyPerformanceParameters;
import com.cardpay.pccredit.manager.service.AccountManagerParameterService;
import com.cardpay.pccredit.manager.service.MaintenanceAccountManagerService;
import com.cardpay.pccredit.manager.service.ManagerDownRuleService;
import com.cardpay.pccredit.manager.service.ManagerMonthAssessmentService;
import com.cardpay.pccredit.manager.service.ManagerPerformanceParametersService;
import com.cardpay.pccredit.manager.service.ManagerPromotionRuleService;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.modules.dictionary.DictionaryManager;
import com.wicresoft.jrad.modules.dictionary.model.Dictionary;
import com.wicresoft.jrad.modules.dictionary.model.DictionaryItem;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;

@Controller
@RequestMapping("/manager/managerPromotionRule/*")
@JRadModule("manager.managerPromotionRule")
public class ManagerPromotionRuleController {
	
	Logger logger = Logger.getLogger(this.getClass());
	
	final public static String SEPARATOR  = ",";
	
	@Autowired
	private ManagerPromotionRuleService managerPromotionRuleService;
	
	@Autowired
	private ManagerMonthAssessmentService managerMonthAssessmentService;
	
	@Autowired
	private AccountManagerParameterService accountManagerParameterService;
	
	@Autowired
	private MaintenanceAccountManagerService maintenanceAccountManagerService;
	
	@Autowired
	private ManagerDownRuleService managerDownRuleService;
	
	@Autowired
	private ManagerPerformanceParametersService managerPerformanceParametersService;
	
	
	
	
	/**
	 * 客户经理晋级规则显示
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updatejigz.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browsejjgz(HttpServletRequest request) {
		List<ManagerPromotionRule> managerPromotionRulelist = managerPromotionRuleService.getManagerPromotionRule();
		JRadModelAndView mv = new JRadModelAndView("/manager/managerpromotionrule/manager_promotion_update", request);
		DictionaryManager dictMgr = Beans.get(DictionaryManager.class);
		// 根据指定名得到字典值列表
		Dictionary dictionary = dictMgr.getDictionaryByName("CUSTOMERMANAGERLEVEL");
		List<DictionaryItem> dictItems = dictionary.getItems();
		List<ManagerPromotionRule> managerPromotionRulel = new ArrayList<ManagerPromotionRule>();
		if(dictItems.size() > 0){
		   for(int i=0;i<dictItems.size() - 2;i++){
			   ManagerPromotionRule managerPromotionRule = new ManagerPromotionRule();
			   managerPromotionRule.setInitialLevel(dictItems.get(i).getName());
			   if(i == 0){
			   managerPromotionRule.setPromotionLevel(dictItems.get(i+1).getName());
			   }else if(i == 1){
				   ManagerPromotionRule managerPromotionRule1 = new ManagerPromotionRule();
				   managerPromotionRule1.setInitialLevel(dictItems.get(i).getName());
				   managerPromotionRule1.setPromotionLevel(dictItems.get(i+1).getName());
				   managerPromotionRulel.add(managerPromotionRule1);
				   managerPromotionRule.setPromotionLevel(dictItems.get(i+2).getName());
				  
				}else{
			   managerPromotionRule.setPromotionLevel(dictItems.get(i+2).getName());
			  }
			   managerPromotionRulel.add(managerPromotionRule); 
		   }
		
		}
		mv.addObject("managerPromotionRulelist",managerPromotionRulelist);
		mv.addObject("managerPromotionRulel",managerPromotionRulel);
		return mv;
	}
	/**
	 * 客户经理晋级规则保存
	 * 
	 * @param request
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "savejjgz.page", method = { RequestMethod.POST })
	@JRadOperation(JRadOperation.CHANGE)
	public AbstractModelAndView changejigz(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/manager/managerpromotionrule/manager_promotion_update", request);
		try {
			managerPromotionRuleService.updateManagerPromotionRule(request);
			List<ManagerPromotionRule> managerPromotionRulelist = managerPromotionRuleService.getManagerPromotionRule();
			mv.addObject("managerPromotionRulelist",managerPromotionRulelist);
		} catch (Exception e) {
            //TODO 有日志功能，在这一步应保持返回统一，出错以后查看日志
			logger.error("执行修改客户经理参数维护错误"+e.getMessage());
		}
		return mv;
	}


	
	/**
	 * 客户经理层级月度考核指标配置显示
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateydkh.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView browseydkh(HttpServletRequest request) {
		List<MangerMonthAssessment> mangerMonthAssessmentlist = managerMonthAssessmentService.getMangerMonthAssessment();
		JRadModelAndView mv = new JRadModelAndView("/manager/mangermonthassessment/manager_assessment_update", request);
		DictionaryManager dictMgr = Beans.get(DictionaryManager.class);
		// 根据指定名得到字典值列表
		Dictionary dictionary = dictMgr.getDictionaryByName("CUSTOMERMANAGERLEVEL");
		List<DictionaryItem> dictItems = dictionary.getItems();
		mv.addObject("mangerMonthAssessmentlist",mangerMonthAssessmentlist);
		mv.addObject("dictItems",dictItems);
		return mv;
	}
	/**
	 * 客户经理层级月度考核指标配置保存
	 * 
	 * @param request
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "saveydkh.page", method = { RequestMethod.POST })
	@JRadOperation(JRadOperation.CHANGE)
	public AbstractModelAndView changeydkh(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/manager/mangermonthassessment/manager_assessment_update", request);
		try {
			managerMonthAssessmentService.updateMangerMonthAssessment(request);
			List<MangerMonthAssessment> mangerMonthAssessmentlist = managerMonthAssessmentService.getMangerMonthAssessment();
			mv.addObject("mangerMonthAssessmentlist",mangerMonthAssessmentlist);
		} catch (Exception e) {
            //TODO 有日志功能，在这一步应保持返回统一，出错以后查看日志
			logger.error("执行修改客户经理层级月度考核指标配置错误"+e.getMessage());
		}
		return mv;
	}
	
	
	/**
	 * 客户经理层级业绩目标显示页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateyjmb.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView browseyjmb(HttpServletRequest request) {
		
		JRadModelAndView mv = new JRadModelAndView("/manager/managerleveltarget/manager_leveltarget_update", request);
		List<CustomerManagerTarget> customerManagerTargetList = accountManagerParameterService.getcustomerManagerTarget();
		
		List<CustomerManagerTarget> weeklist = new ArrayList<CustomerManagerTarget>();
		List<CustomerManagerTarget> monthlist = new ArrayList<CustomerManagerTarget>();
		List<CustomerManagerTarget> yearlist = new ArrayList<CustomerManagerTarget>();
		for(CustomerManagerTarget kv: customerManagerTargetList){
			String datestr = kv.getTargetDate();
			if(datestr.equalsIgnoreCase(ManagerTargetType.weekly.name())){
				weeklist.add(kv);
			}
			if(datestr.equalsIgnoreCase(ManagerTargetType.month.name())){
				monthlist.add(kv);
			}
			if(datestr.equalsIgnoreCase(ManagerTargetType.year.name())){
				yearlist.add(kv);
			}
			
		}
		
		DictionaryManager dictMgr = Beans.get(DictionaryManager.class);
		// 根据指定名得到字典值列表
		Dictionary dictionary = dictMgr.getDictionaryByName("CUSTOMERMANAGERLEVEL");
		List<DictionaryItem> dictItems = dictionary.getItems();
		mv.addObject("dictItems",dictItems);
		mv.addObject("weekly", weeklist);
		mv.addObject("month", monthlist);
		mv.addObject("year", yearlist);
		return mv;
	}
	/**
	 *  客户经理层级业绩目标修改
	 * 
	 * @param request
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "saveyjmb.page", method = { RequestMethod.POST })
	@JRadOperation(JRadOperation.CHANGE)
	public AbstractModelAndView changeyjmb(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/manager/managerleveltarget/manager_leveltarget_update", request);
		try {
			accountManagerParameterService.updatecustomerManagerTarget(request);
			List<CustomerManagerTarget> customerManagerTargetList = accountManagerParameterService.getcustomerManagerTarget();
			
			List<CustomerManagerTarget> weeklist = new ArrayList<CustomerManagerTarget>();
			List<CustomerManagerTarget> monthlist = new ArrayList<CustomerManagerTarget>();
			List<CustomerManagerTarget> yearlist = new ArrayList<CustomerManagerTarget>();
			for(CustomerManagerTarget kv: customerManagerTargetList){
				String datestr = kv.getTargetDate();
				if(datestr.equalsIgnoreCase(ManagerTargetType.weekly.name())){
					weeklist.add(kv);
				}
				if(datestr.equalsIgnoreCase(ManagerTargetType.month.name())){
					monthlist.add(kv);
				}
				if(datestr.equalsIgnoreCase(ManagerTargetType.year.name())){
					yearlist.add(kv);
				}
				
			}
			mv.addObject("weekly", weeklist);
			mv.addObject("month", monthlist);
			mv.addObject("year", yearlist);
			
		} catch (Exception e) {
            //TODO 有日志功能，在这一步应保持返回统一，出错以后查看日志
			logger.error("执行修改客户经理参数维护错误"+e.getMessage());
		}
		return mv;
	}

	
	
	/**
	 * 客户经理参数维护参数显示
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updatecswh.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView browsecswh(HttpServletRequest request) {
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
	@RequestMapping(value = "savecswh.page", method = { RequestMethod.POST })
	@JRadOperation(JRadOperation.CHANGE)
	public AbstractModelAndView changecswh(HttpServletRequest request) {
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
	
	/**
	 * 客户经理降级规则显示
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updatedown.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView browsedown(HttpServletRequest request) {
		List<DownGradeRule> downGradeRulelist = managerDownRuleService.getDownGradeRule();
		JRadModelAndView mv = new JRadModelAndView("/manager/managerdownrule/manager_down_update", request);
		DictionaryManager dictMgr = Beans.get(DictionaryManager.class);
		// 根据指定名得到字典值列表
		Dictionary dictionary = dictMgr.getDictionaryByName("CUSTOMERMANAGERLEVEL");
		List<DictionaryItem> dictItems = dictionary.getItems();
		List<DownGradeRule> downGradeRulel = new ArrayList<DownGradeRule>();
		if(dictItems.size() > 0){
		   for(int i=1;i<dictItems.size();i++){
			   DownGradeRule downGradeRule = new DownGradeRule();
			   downGradeRule.setCurrentLevel(dictItems.get(i).getName());
			   if(i == 1 || i == 2){
				   downGradeRule.setDowngradeLevel(dictItems.get(i-1).getName());
			   }else{
				   downGradeRule.setDowngradeLevel(dictItems.get(i-2).getName());
				  
				}
			   downGradeRulel.add(downGradeRule); 
		   }
		
		}
		mv.addObject("downGradeRulelist",downGradeRulelist);
		mv.addObject("downGradeRulel",downGradeRulel);
		return mv;
	}
	/**
	 * 客户经降级级规则保存
	 * 
	 * @param request
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "savedown.page", method = { RequestMethod.POST })
	@JRadOperation(JRadOperation.CHANGE)
	public AbstractModelAndView changedown(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/manager/managerdownrule/manager_down_update", request);
		try {
			managerDownRuleService.updateDownGradeRule(request);
			List<DownGradeRule> downGradeRulelist = managerDownRuleService.getDownGradeRule();
			mv.addObject("downGradeRulelist",downGradeRulelist);
		} catch (Exception e) {
            //TODO 有日志功能，在这一步应保持返回统一，出错以后查看日志
			logger.error("执行修改客户经理客户经降级级规则"+e.getMessage());
		}
		return mv;
	}
	/**
	 * 客户经理绩效参数配置
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updatePerformance.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView updatePerformance(HttpServletRequest request) {
		List<TyPerformanceParameters> parmeters = managerPerformanceParametersService.getManagerPerformanceParamers();
		JRadModelAndView mv = new JRadModelAndView("/manager/performanceParameters/manager_performance_update", request);
		DictionaryManager dictMgr = Beans.get(DictionaryManager.class);
		// 根据指定名得到字典值列表
		Dictionary dictionary = dictMgr.getDictionaryByName("CUSTOMERMANAGERLEVEL");
		List<DictionaryItem> dictItems = dictionary.getItems();
		mv.addObject("parmeters",parmeters);
		mv.addObject("dictItems",dictItems);
		return mv;
	}
	/**
	 * 客户经理绩效参数配置保存
	 * 
	 * @param request
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "savePerformance.page", method = { RequestMethod.POST })
	@JRadOperation(JRadOperation.CHANGE)
	public AbstractModelAndView savePerformance(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/manager/performanceParameters/manager_performance_update", request);
		try {
			managerPerformanceParametersService.updateManagerPerformanceParamers(request);
			List<TyPerformanceParameters> parmeters = managerPerformanceParametersService.getManagerPerformanceParamers();
			mv.addObject("parmeters",parmeters);
		} catch (Exception e) {
            //TODO 有日志功能，在这一步应保持返回统一，出错以后查看日志
			logger.error("执行修改客户经理绩效参数"+e.getMessage());
		}
		return mv;
	}
	
	
	
	/**
	 * ty
	 * 客户经理晋降级规则显示
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "displayjjgz.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView displayjjgz(HttpServletRequest request) {
		List<ManagerPromotionDownRule> managerPromotionRulelist = managerPromotionRuleService.getManagerPromotionDownRule();
		JRadModelAndView mv = new JRadModelAndView("/manager/managerpromoteordowngraderule/manager_promotion_down_update", request);
		DictionaryManager dictMgr = Beans.get(DictionaryManager.class);
		// 根据指定名得到字典值列表
		Dictionary dictionary = dictMgr.getDictionaryByName("CUSTOMERMANAGERLEVEL");
		List<DictionaryItem> dictItems = dictionary.getItems();
		List<ManagerPromotionRule> managerPromotionRulel = new ArrayList<ManagerPromotionRule>();
		if(dictItems.size() > 0){
		   for(int i=0;i<dictItems.size();i++){
			   ManagerPromotionRule managerPromotionRule = new ManagerPromotionRule();
			   managerPromotionRule.setInitialLevel(dictItems.get(i).getName());
			   managerPromotionRulel.add(managerPromotionRule); 
		   }
		}
		mv.addObject("managerPromotionRulelist",managerPromotionRulelist);
		mv.addObject("managerPromotionRulel",managerPromotionRulel);
		return mv;
	}
	
	
	/**
	 * 客户经理晋级规则保存
	 * 
	 * @param request
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "insertOrUpdatejjgz.page", method = { RequestMethod.POST })
	@JRadOperation(JRadOperation.CHANGE)
	public AbstractModelAndView changejjgz(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/manager/managerpromoteordowngraderule/manager_promotion_down_update", request);
		try {
			managerPromotionRuleService.updateManagerPromotionDownRule(request);
			List<ManagerPromotionDownRule> managerPromotionRulelist = managerPromotionRuleService.getManagerPromotionDownRule();
			mv.addObject("managerPromotionRulelist",managerPromotionRulelist);
		} catch (Exception e) {
            //TODO 有日志功能，在这一步应保持返回统一，出错以后查看日志
			logger.error("执行修改客户经理参数维护错误"+e.getMessage());
		}
		return mv;
	}
	
	/**
	 * 中心人员绩效参数配置
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updatePerformanceCenter.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView updatePerformanceCenter(HttpServletRequest request) {
		List<TyPerformanceCenter> parmeters = managerPerformanceParametersService.getManagerPerformanceCenter();
		JRadModelAndView mv = new JRadModelAndView("/manager/performanceParameters/center_performance_update", request);
		DictionaryManager dictMgr = Beans.get(DictionaryManager.class);
		// 根据指定名得到字典值列表
		Dictionary dictionary = dictMgr.getDictionaryByName("CENTERMANAGERLEVEL");
		List<DictionaryItem> dictItems = dictionary.getItems();
		mv.addObject("parmeters",parmeters);
		mv.addObject("dictItems",dictItems);
		return mv;
	}
	
	//=====================================济南绩效=======================================//
	/**
	 * 济南绩效参数配置
	 */
	@ResponseBody
	@RequestMapping(value = "updateJxParam.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView updateJxParam(HttpServletRequest request) {
		List<TPerformanceParameters> parmeters = managerPerformanceParametersService.getTManagerPerformanceParamers();
		JRadModelAndView mv = new JRadModelAndView("/manager/performanceParameters/manager_jx_param_actor", request);
		mv.addObject("parmeters",parmeters);
		return mv;
	}
	
	
	/**
	 * 客户经理绩效参数配置保存
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "saveJxParam.page", method = { RequestMethod.POST })
	@JRadOperation(JRadOperation.CHANGE)
	public AbstractModelAndView saveJxParam(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/manager/performanceParameters/manager_jx_param_actor", request);
		try {
			managerPerformanceParametersService.updateTManagerPerformanceParamers(request);
			List<TPerformanceParameters> parmeters = managerPerformanceParametersService.getTManagerPerformanceParamers();
			mv.addObject("parmeters",parmeters);
		} catch (Exception e) {
			logger.error("执行修改客户经理绩效参数"+e.getMessage());
		}
		return mv;
	}
}
