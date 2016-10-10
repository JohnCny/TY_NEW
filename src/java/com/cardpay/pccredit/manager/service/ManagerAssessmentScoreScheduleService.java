/**
 * 
 */
package com.cardpay.pccredit.manager.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.customer.dao.comdao.CustomerInforCommDao;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.model.CustomerManagerTarget;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.intopieces.constant.Constant;
import com.cardpay.pccredit.manager.constant.ManagerAdjustAdviceEnum;
import com.cardpay.pccredit.manager.constant.ManagerLevelAdjustmentConstant;
import com.cardpay.pccredit.manager.constant.ManagerLevelConstant;
import com.cardpay.pccredit.manager.constant.ManagerLevelEnum;
import com.cardpay.pccredit.manager.constant.ManagerTargetType;
import com.cardpay.pccredit.manager.dao.ManagerAssessmentScoreDao;
import com.cardpay.pccredit.manager.dao.comdao.ManagerAssessmentScoreCommDao;
import com.cardpay.pccredit.manager.dao.comdao.ManagerDownRuleComdao;
import com.cardpay.pccredit.manager.dao.comdao.ManagerPromotionRuleComdao;
import com.cardpay.pccredit.manager.filter.AccountManagerParameterFilter;
import com.cardpay.pccredit.manager.model.DownGradeRule;
import com.cardpay.pccredit.manager.model.ManagerAssessmentScore;
import com.cardpay.pccredit.manager.model.ManagerLevelAdjustment;
import com.cardpay.pccredit.manager.model.ManagerMonthTargetData;
import com.cardpay.pccredit.manager.model.ManagerPromotionDownRule;
import com.cardpay.pccredit.manager.model.ManagerPromotionRule;
import com.cardpay.pccredit.manager.model.MangerMonthAssessment;
import com.cardpay.pccredit.manager.model.PromotionRules;
import com.cardpay.pccredit.manager.model.TyManagerAssessment;
import com.cardpay.pccredit.manager.web.AccountManagerParameterForm;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.id.IDGenerator;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.modules.privilege.filter.UserFilter;
import com.wicresoft.jrad.modules.privilege.model.User;

/**
 * 描述 ：客户经理评估标准 定时执行service
 * @author 张石树
 *
 * 2014-11-12 下午3:37:19
 */
@Service
public class ManagerAssessmentScoreScheduleService {
	
	private Logger logger = Logger.getLogger(ManagerAssessmentScoreScheduleService.class);

	@Autowired
	private AccountManagerParameterService accountManagerParameterService;
	
	@Autowired
	private CustomerInforCommDao customerInforCommDao;
	
	@Autowired
	private ManagerAssessmentScoreCommDao managerAssessmentScoreCommDao;
	
	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private ManagerAssessmentScoreDao managerAssessmentScoreDao;
	
	@Autowired
	private ManagerBelongMapService managerBelongMapService;
	
	@Autowired
	private ManagerDownRuleComdao managerDownRuleComdao;
	
	@Autowired
	private ManagerPromotionRuleComdao managerPromotionRuleComdao;
	
	@Autowired
	private CustomerInforService customerInforService;
//	/**
//	 * 增加上一个月的客户经理评估信息
//	 */
//	public void addManagerAssessmentScore(){
//		Calendar calendar = Calendar.getInstance();
//		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1, 0, 0, 0);
//		calendar.add(Calendar.MONTH, -1);
//		int year = calendar.get(Calendar.YEAR);
//		int month = calendar.get(Calendar.MONTH) + 1;
//		//每月客户经理评估
//		this.addManagerMonthAssessmentScore(year, month);
//		
//		//根据规则 生成 晋级
//		List<PromotionRules> promotionRules = managerAssessmentScoreCommDao.findAllPromotionRules();
//		Map<String, List<PromotionRules>> proRulesMap = new HashMap<String, List<PromotionRules>>();
//		for(PromotionRules proRule : promotionRules){
//			if(proRulesMap.containsKey(proRule.getInitialLevel())){
//				List<PromotionRules> rules = proRulesMap.get(proRule.getInitialLevel());
//				rules.add(proRule);
//			} else {
//				List<PromotionRules> rules = new ArrayList<PromotionRules>();
//				rules.add(proRule);
//				proRulesMap.put(proRule.getInitialLevel(), rules);
//			}
//		}
//		//客户经理级别月度考核指标
//		Map<String, MangerMonthAssessment> managerTargetAssessmentMap = new HashMap<String, MangerMonthAssessment>();
//		List<MangerMonthAssessment> mangerMonthAssessments = managerAssessmentScoreCommDao.findManagerMonthAssessmentAll();
//		for(MangerMonthAssessment monthAssessment : mangerMonthAssessments){
//			managerTargetAssessmentMap.put(monthAssessment.getCustomerManagerLevel(), monthAssessment);
//		}
//		//晋级 降级生成
//		try{	
//			AccountManagerParameterFilter filter = new AccountManagerParameterFilter();
//			// 设置每次最大查询记录数
//			filter.setLimit(50);
//			// 查询页码
//			filter.setPage(0);
//			QueryResult<AccountManagerParameterForm> qs = accountManagerParameterService.findAccountManagerParametersByFilter(filter);
//			while(qs.getItems().size() != 0){
//				for(AccountManagerParameterForm accountManager : qs.getItems()){
//					//判断这个月的指标是否生成
//					ManagerMonthTargetData monthTargetData = managerAssessmentScoreCommDao.findManangerMonthTargetDataBy(year, month, accountManager.getUserId(), accountManager.getLevelInformation());
//					if(monthTargetData != null){
//						continue;
//					}
//					//客户经理目标
//					CustomerManagerTarget customerManagerTarget = accountManagerParameterService.getcustomerManagerTargetBymanagerIdDate(accountManager.getUserId(), ManagerTargetType.month.name());
//					
//					List<CustomerInfor> customerInfors = customerInforCommDao.findCustomerByManagerId(accountManager.getUserId());
//					if(customerInfors != null && customerInfors.size() > 0){
//						//下属的经理级别统计Map
//					    Map<String, Integer> subLevelCountMap = new HashMap<String, Integer>();
//						List<String> customerIds = new ArrayList<String>();
//						for(CustomerInfor infor : customerInfors){
//							customerIds.add(infor.getId());
//						}
//						ManagerMonthTargetData managerMonthTargetData = new ManagerMonthTargetData();
//						//管户数（户）
//						managerMonthTargetData.setTubeNumber(customerInfors.size());
//						//激活率
//						Integer activateCardCounts = managerAssessmentScoreCommDao.countActivateCard(year, month, customerIds);
//						if(customerManagerTarget.getActivationNumber() != null && customerManagerTarget.getActivationNumber() > 0){
//							double activateCardCountsD = activateCardCounts;
//							double activationNumberD = customerManagerTarget.getActivationNumber();
//							BigDecimal bg = new BigDecimal(activateCardCountsD/activationNumberD * 100);
//							Double activateCardRate = bg.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
//							managerMonthTargetData.setActivationRate(activateCardRate+"");
//						} else {
//							managerMonthTargetData.setActivationRate(100.0 + "");
//						}
//						//活跃率
//						Integer activeCardCounts = managerAssessmentScoreCommDao.countActiveCard(year, month, customerIds);
//						if(customerManagerTarget.getActiveNumber() != null && customerManagerTarget.getActiveNumber() > 0){
//							double activeCardCountsD = activeCardCounts;
//							double ActiveNumberD = customerManagerTarget.getActiveNumber();
//							BigDecimal bg = new BigDecimal(activeCardCountsD/ActiveNumberD * 100);
//							Double activeCardRate = bg.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
//							managerMonthTargetData.setActiveRate(activeCardRate + "");
//						} else {
//							managerMonthTargetData.setActiveRate(100.0 + "");
//						}
//						//月度 日均透支余额（万）
//						List<String> managerIds = new ArrayList<String>();
//						managerIds.add(accountManager.getUserId());
//						Double perDayAverageCreditlineAccount = managerAssessmentScoreCommDao.perDayAverageCreditlineAccount(managerIds, year, month);
//						managerMonthTargetData.setMonthPerdayCreditline(perDayAverageCreditlineAccount + "");
//						//团队月度日均透支余额 （万）
//					    List<AccountManagerParameterForm> subManagerParams = managerBelongMapService.findSubListManagerByManagerId(accountManager.getUserId());
//					    if(subManagerParams.size() > 0){
//							managerIds.clear();
//							for(AccountManagerParameterForm parameterForm : subManagerParams){
//								managerIds.add(parameterForm.getUserId());
//								if(subLevelCountMap.containsKey(parameterForm.getLevelInformation())){
//									subLevelCountMap.put(parameterForm.getLevelInformation(), 
//											subLevelCountMap.get(parameterForm.getLevelInformation()) + 1);
//								} else {
//									subLevelCountMap.put(parameterForm.getLevelInformation(), 1);
//								}
//							}
//							Double teamPerDayAverageCreditlineAccount = managerAssessmentScoreCommDao.perDayAverageCreditlineAccount(managerIds, year, month);
//							managerMonthTargetData.setMonthPerdayTeamCreditline(teamPerDayAverageCreditlineAccount + "");
//							managerMonthTargetData.setSubManangerCount(subManagerParams.size());
//						} else {
//							managerMonthTargetData.setMonthPerdayTeamCreditline(0 + "");
//							managerMonthTargetData.setSubManangerCount(0);
//						}
//					    //月度考核是否达标
//					    MangerMonthAssessment monthAssessment = managerTargetAssessmentMap.get(accountManager.getLevelInformation());
//					    if(monthAssessment != null){
//					    	//管理高级 需要判断团队
//					    	if(ManagerLevelEnum.MANA005.name().equals(accountManager.getLevelInformation())){
//								if (Double.parseDouble(managerMonthTargetData.getMonthPerdayCreditline()) > 
//										Double.parseDouble(monthAssessment.getMonthdayAvgCreditline())
//										&& Double.parseDouble(managerMonthTargetData.getMonthPerdayTeamCreditline()) > 
//										Double.parseDouble(monthAssessment.getMonthdayTeamAvgCreditline())
//										&& managerMonthTargetData.getTubeNumber() > monthAssessment.getTubeNumber()
//										&& Double.parseDouble(managerMonthTargetData.getActiveRate()) > 
//										Double.parseDouble(monthAssessment.getActiveRate())
//										&& Double.parseDouble(managerMonthTargetData.getActivationRate()) >
//										Double.parseDouble(monthAssessment.getActivationRate())) {
//									managerMonthTargetData.setIfPassStandard(ManagerLevelAdjustmentConstant.IF_PASS_STANDARD_1);
//					    		} else {
//					    			managerMonthTargetData.setIfPassStandard(ManagerLevelAdjustmentConstant.IF_PASS_STANDARD_0);
//					    		}
//								//见习
//					    	} else if(ManagerLevelEnum.MANA001.name().equals(accountManager.getLevelInformation())){
//					    		if (Double.parseDouble(managerMonthTargetData.getMonthPerdayCreditline()) > 
//								Double.parseDouble(monthAssessment.getMonthdayAvgCreditline())
//								&& managerMonthTargetData.getTubeNumber() > monthAssessment.getTubeNumber()) {
//									managerMonthTargetData.setIfPassStandard(ManagerLevelAdjustmentConstant.IF_PASS_STANDARD_1);
//					    		} else {
//					    			managerMonthTargetData.setIfPassStandard(ManagerLevelAdjustmentConstant.IF_PASS_STANDARD_0);
//					    		}
//					    	} else {
//					    		if (Double.parseDouble(managerMonthTargetData.getMonthPerdayCreditline()) > 
//										Double.parseDouble(monthAssessment.getMonthdayAvgCreditline())
//										&& managerMonthTargetData.getTubeNumber() > monthAssessment.getTubeNumber()
//										&& Double.parseDouble(managerMonthTargetData.getActiveRate()) >
//										Double.parseDouble(monthAssessment.getActiveRate())
//										&& Double.parseDouble(managerMonthTargetData.getActivationRate()) >
//										Double.parseDouble(monthAssessment.getActivationRate())) {
//									managerMonthTargetData.setIfPassStandard(ManagerLevelAdjustmentConstant.IF_PASS_STANDARD_1);
//					    		} else {
//					    			managerMonthTargetData.setIfPassStandard(ManagerLevelAdjustmentConstant.IF_PASS_STANDARD_0);
//					    		}
//					    	}
//					    } else {
//					    	managerMonthTargetData.setIfPassStandard(ManagerLevelAdjustmentConstant.IF_PASS_STANDARD_0);
//					    }
//					    
//						//客户每月指标统计数据
//						managerMonthTargetData.setCustomerManagerId(accountManager.getUserId());
//						managerMonthTargetData.setDataYear(year);
//						managerMonthTargetData.setDataMonth(month);
//						managerMonthTargetData.setCustomerManagerLevel(accountManager.getLevelInformation());
//						managerMonthTargetData.setCreatedBy(Constant.SCHEDULES_SYSTEM_USER);
//						managerMonthTargetData.setCreatedTime(new Date());
//						managerMonthTargetData.setModifiedBy(Constant.SCHEDULES_SYSTEM_USER);
//						managerMonthTargetData.setModifiedTime(new Date());
//						commonDao.insertObject(managerMonthTargetData);
//						
//						//晋级规则判断
//						if(proRulesMap.get(accountManager.getLevelInformation()) != null){
//							//季度日均透支余额（万）
//							Double quarterAverageCreditLineAccount = 0.0;
//							List<ManagerMonthTargetData> monthTargetDatas = managerAssessmentScoreCommDao
//									.findQuarterPerCreditlineAccount(accountManager.getUserId(), accountManager.getLevelInformation(), 3);
//							if(monthTargetDatas.size() < 3){
//								continue;
//							}
//							for(ManagerMonthTargetData targetData : monthTargetDatas){
//								quarterAverageCreditLineAccount += Double.parseDouble(targetData.getMonthPerdayCreditline());
//							}
//							quarterAverageCreditLineAccount = quarterAverageCreditLineAccount / 3;
//							
//							List<PromotionRules> proRules = proRulesMap.get(accountManager.getLevelInformation());
//							//见习	初级	200	-	-	-	40	80%	60%
//							//业务中级	业务高级	1300	-	-	-	160	80%	70%
//							if(ManagerLevelEnum.MANA001.name().equals(accountManager.getLevelInformation())
//									|| ManagerLevelEnum.MANA004.name().equals(accountManager.getLevelInformation())){
//								PromotionRules rule = proRules.get(0);
//								if (quarterAverageCreditLineAccount > Double.parseDouble(rule.getQuarterAverageOverBalance())
//										&& managerMonthTargetData.getTubeNumber() > rule.getTubeNumber()
//										&& Double.parseDouble(managerMonthTargetData.getActivationRate()) > Double
//												.parseDouble(rule.getActivationRate())
//										&& Double.parseDouble(managerMonthTargetData.getActiveRate()) > Double
//												.parseDouble(rule.getActiveRate())) {
//									this.insertManagerLevelAdjustment(accountManager, rule, year, month);
//								}
//							}
//							if(ManagerLevelEnum.MANA002.name().equals(accountManager.getLevelInformation())){
//								for(PromotionRules rule : proRules){
//									boolean promotionFlag = false;
//									//管理中级 初级	管理中级	500	-	初级	2	100	80%	70%
//									//              业务中级	650	-	-	-	100	80%	70%
//									if(rule.getPromotionLevel().equals(ManagerLevelEnum.MANA003.name())){
//										Integer subLevelCount = subLevelCountMap.get(ManagerLevelEnum.MANA002.name()) != null ? 
//												subLevelCountMap.get(ManagerLevelEnum.MANA002.name()) : 0;
//										if (quarterAverageCreditLineAccount > Double.parseDouble(rule.getQuarterAverageOverBalance()) 
//												&& subLevelCount >rule.getSubMangerNumber() 
//												&& managerMonthTargetData.getTubeNumber() > rule.getTubeNumber()
//												&& Double.parseDouble(managerMonthTargetData.getActivationRate()) > Double
//														.parseDouble(rule.getActivationRate())
//												&& Double.parseDouble(managerMonthTargetData.getActiveRate()) > Double
//														.parseDouble(rule.getActiveRate())) {
//											promotionFlag = true;
//										}
//									} else {
//										if (quarterAverageCreditLineAccount > Double.parseDouble(rule.getQuarterAverageOverBalance()) 
//												&& managerMonthTargetData.getTubeNumber() > rule.getTubeNumber()
//												&& Double.parseDouble(managerMonthTargetData.getActivationRate()) > Double
//														.parseDouble(rule.getActivationRate())
//												&& Double.parseDouble(managerMonthTargetData.getActiveRate()) > Double
//														.parseDouble(rule.getActiveRate())) {
//											promotionFlag = true;
//										}
//									}
//									if(promotionFlag){
//										this.insertManagerLevelAdjustment(accountManager, rule, year, month);
//										break;
//									}
//								}
//							}
//							//管理中级	管理高级	1000	-	初级	4	160	80%	70%
//							//管理中级	管理高级	1000	-	中级	2	160	80%	70%
//							if(ManagerLevelEnum.MANA003.name().equals(accountManager.getLevelInformation())){
//								PromotionRules rule = proRules.get(0);
//								Integer subLevelCount = subLevelCountMap.get(rule.getLevelTeamMember()) != null ? 
//										subLevelCountMap.get(rule.getLevelTeamMember()) : 0;
//								if (quarterAverageCreditLineAccount > Double.parseDouble(rule.getQuarterAverageOverBalance()) 
//										&& subLevelCount >rule.getSubMangerNumber() 
//										&& managerMonthTargetData.getTubeNumber() > rule.getTubeNumber()
//										&& Double.parseDouble(managerMonthTargetData.getActivationRate()) > Double
//											.parseDouble(rule.getActivationRate())
//										&& Double.parseDouble(managerMonthTargetData.getActiveRate()) > Double
//											.parseDouble(rule.getActiveRate())) {
//									this.insertManagerLevelAdjustment(accountManager, rule, year, month);
//								}
//							}
//						}
//					}
//				}
//				// 设置查询的页码
//				filter.setPage(filter.getPage() + 1);
//				qs = accountManagerParameterService.findAccountManagerParametersByFilter(filter);
//			}
//		}catch(Exception e){
//			logger.error("客户经理评估信息定时生成错误");
//			logger.error(e.getMessage(), e);
//			throw new RuntimeException(e.getMessage());
//		}
//	}

	/**
	 * 插入客户经理级别调整
	 * @param accountManager
	 * @param rule
	 */
	private void insertManagerLevelAdjustment(
			AccountManagerParameterForm accountManager, PromotionRules rule, int year, int month) {
		int count = managerAssessmentScoreCommDao.findManagerLevelAdjustment(accountManager.getUserId(), year, month);
		if(count == 0){
			ManagerLevelAdjustment managerLevelAdjustment = new ManagerLevelAdjustment();
			managerLevelAdjustment.setIfHandled(ManagerLevelAdjustmentConstant.IFHANDLE_0);
			managerLevelAdjustment.setSystemAdvice(ManagerAdjustAdviceEnum.up.name());
			managerLevelAdjustment.setOriginalLevel(rule.getInitialLevel());
			managerLevelAdjustment.setAdjustAfterLevel(rule.getPromotionLevel());
			managerLevelAdjustment.setCustomerManagerId(accountManager.getUserId());
			managerLevelAdjustment.setCreatedBy(Constant.SCHEDULES_SYSTEM_USER);
			managerLevelAdjustment.setCreatedTime(new Date());
			managerLevelAdjustment.setModifiedBy(Constant.SCHEDULES_SYSTEM_USER);
			managerLevelAdjustment.setModifiedTime(new Date()); 
			managerLevelAdjustment.setDataYear(year);
			managerLevelAdjustment.setDataMonth(month);
			commonDao.insertObject(managerLevelAdjustment);
		}
	}
	
	/**
	 * 客户经理降级每日执行
	 */
	public void downLevelSchedulesDay(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1, 0, 0, 0);
		calendar.add(Calendar.MONTH, -1);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		
		//降级规则
		List<DownGradeRule> downGradeRules =  managerDownRuleComdao.getDownGradeRule();
		Map<String, DownGradeRule> downGradeRulesMap = new HashMap<String, DownGradeRule>();
		for(DownGradeRule downGradeRule : downGradeRules){
			downGradeRulesMap.put(downGradeRule.getCurrentLevel(), downGradeRule);
		}
		
		//根据规则  晋级规则 
		List<PromotionRules> promotionRules = managerAssessmentScoreCommDao.findAllPromotionRules();
		Map<String, PromotionRules> proRulesMap = new HashMap<String, PromotionRules>();
		for(PromotionRules proRule : promotionRules){
			proRulesMap.put(proRule.getPromotionLevel(), proRule);
		}
		//客户经理级别月度考核指标
		Map<String, MangerMonthAssessment> managerTargetAssessmentMap = new HashMap<String, MangerMonthAssessment>();
		List<MangerMonthAssessment> mangerMonthAssessments = managerAssessmentScoreCommDao.findManagerMonthAssessmentAll();
		for(MangerMonthAssessment monthAssessment : mangerMonthAssessments){
			managerTargetAssessmentMap.put(monthAssessment.getCustomerManagerLevel(), monthAssessment);
		}
		try {
			AccountManagerParameterFilter filter = new AccountManagerParameterFilter();
			// 设置每次最大查询记录数
			filter.setLimit(50);
			// 查询页码
			filter.setPage(0);
			QueryResult<AccountManagerParameterForm> qs = accountManagerParameterService.findAccountManagerParametersByFilter(filter);
			while(qs.getItems().size() != 0){
				for(AccountManagerParameterForm accountManager : qs.getItems()){
					//客户经理月度指标
					ManagerMonthTargetData managerMonthTargetData =  
							managerAssessmentScoreCommDao.findManangerMonthTargetDataBy(year, month, accountManager.getUserId(), accountManager.getLevelInformation());
					if(managerMonthTargetData != null){
						//降级规则判断 
						DownGradeRule downGradeRule = downGradeRulesMap.get(accountManager.getLevelInformation());
						if(downGradeRule == null){
							continue;
						}
						//a.初级微贷经理的降级：考核期末业绩指标未达标或综合评估得分连续两期低于60分。
						if(ManagerLevelEnum.MANA002.name().equals(accountManager.getLevelInformation())){
							//指标未达标 或 综合评估得分连续两期低于60分
							List<ManagerAssessmentScore> assessmentScores = managerAssessmentScoreCommDao.
									findManagerAssessmentScoreByYearAndMonth(accountManager.getUserId(), accountManager.getLevelInformation(), 2);
							if(managerMonthTargetData.getIfPassStandard().equals(ManagerLevelAdjustmentConstant.IF_PASS_STANDARD_0) ||
									(assessmentScores.size() == 2 && assessmentScores.get(0).getTotalScore() != null &&
									assessmentScores.get(0).getTotalScore() !=null &&
									assessmentScores.get(0).getTotalScore() < Double.parseDouble(downGradeRule.getEvaluationScore()) &&
									assessmentScores.get(1).getTotalScore() < Double.parseDouble(downGradeRule.getEvaluationScore()))){
								//生成降级信息
								this.addInsertDownLevelAdjustment(accountManager, ManagerLevelEnum.MANA001.name(), year, month);
							}
						}
						//b.	管理中级微贷经理的降级：考核期末业绩指标未达标；考核期末业绩达标但团队规模连续两期未达标；
						//综合评估得分连续两期低于70分。
						if(ManagerLevelEnum.MANA003.name().equals(accountManager.getLevelInformation())){
							//指标未达标 或 综合评估得分连续两期低于70分
							List<ManagerAssessmentScore> assessmentScores = managerAssessmentScoreCommDao.
									findManagerAssessmentScoreByYearAndMonth(accountManager.getUserId(), accountManager.getLevelInformation(), 2);
							List<ManagerMonthTargetData> targetDatas = 
									managerAssessmentScoreCommDao.findManangerMonthTargetData(accountManager.getUserId(), accountManager.getLevelInformation(), 2);
							PromotionRules rule = proRulesMap.get(accountManager.getLevelInformation());
							if(managerMonthTargetData.getIfPassStandard().equals(ManagerLevelAdjustmentConstant.IF_PASS_STANDARD_0) ||
									(managerMonthTargetData.getIfPassStandard().equals(ManagerLevelAdjustmentConstant.IF_PASS_STANDARD_1)
											&& targetDatas.size() == 2 && targetDatas.get(0).getSubManangerCount() < rule.getSubMangerNumber()
											&& targetDatas.get(1).getSubManangerCount() < rule.getSubMangerNumber()) ||
											(assessmentScores.size() == 2 && assessmentScores.get(0).getTotalScore() != null &&
											assessmentScores.get(0).getTotalScore() !=null && assessmentScores.get(0).getTotalScore() < Double.parseDouble(downGradeRule.getEvaluationScore()) &&
											assessmentScores.get(1).getTotalScore() < Double.parseDouble(downGradeRule.getEvaluationScore()))){
								//生成降级信息
								this.addInsertDownLevelAdjustment(accountManager, ManagerLevelEnum.MANA002.name(), year, month);
							}
						}
						//c.	业务中级微贷经理的降级：考核期末业绩指标未达标或综合评估得分连续两期低于70分。
						if(ManagerLevelEnum.MANA004.name().equals(accountManager.getLevelInformation())){
							List<ManagerAssessmentScore> assessmentScores = managerAssessmentScoreCommDao.
									findManagerAssessmentScoreByYearAndMonth(accountManager.getUserId(), accountManager.getLevelInformation(), 2);
							if(managerMonthTargetData.getIfPassStandard().equals(ManagerLevelAdjustmentConstant.IF_PASS_STANDARD_0) ||
									(assessmentScores.size() == 2 && assessmentScores.get(0).getTotalScore() != null &&
									assessmentScores.get(0).getTotalScore() !=null &&
									assessmentScores.get(0).getTotalScore() < Double.parseDouble(downGradeRule.getEvaluationScore()) &&
									assessmentScores.get(1).getTotalScore() < Double.parseDouble(downGradeRule.getEvaluationScore()))){
								//生成降级信息
								this.addInsertDownLevelAdjustment(accountManager, ManagerLevelEnum.MANA002.name(), year, month);
							}
						}
						//d.	管理高级微贷经理的降级：考核期末团队业绩指标完成率低于80%；考核期末团队业绩完成80%以上，
						//但连续两期未达100%；考核期末团队业绩达标但团队规模连续两期未达标；综合评估得分连续两期低于80分。
						if(ManagerLevelEnum.MANA005.name().equals(accountManager.getLevelInformation())){
							//综合评估得分连续
							List<ManagerAssessmentScore> assessmentScores = managerAssessmentScoreCommDao.
									findManagerAssessmentScoreByYearAndMonth(accountManager.getUserId(), accountManager.getLevelInformation(), 2);
							List<ManagerMonthTargetData> targetDatas = 
									managerAssessmentScoreCommDao.findManangerMonthTargetData(accountManager.getUserId(), accountManager.getLevelInformation(), 2);
							//月度考核指标
							MangerMonthAssessment assessment = managerTargetAssessmentMap.get(accountManager.getLevelInformation());
							double kaoHeYeJiZhiBiao = Double.parseDouble(managerMonthTargetData.getMonthPerdayTeamCreditline())/
									Double.parseDouble(assessment.getMonthdayTeamAvgCreditline()) * 100;
							PromotionRules rule = proRulesMap.get(accountManager.getLevelInformation());
							if(kaoHeYeJiZhiBiao < 80 ||
									(Double.parseDouble(managerMonthTargetData.getMonthPerdayTeamCreditline()) 
											>= Double.parseDouble(assessment.getMonthdayTeamAvgCreditline())
									 && targetDatas.size() == 2
									 && targetDatas.get(0).getSubManangerCount() < rule.getSubMangerNumber()
									 && targetDatas.get(1).getSubManangerCount() < rule.getSubMangerNumber()) ||
									 (assessmentScores.size() == 2 && assessmentScores.get(0).getTotalScore() != null &&
										assessmentScores.get(0).getTotalScore() !=null &&
										assessmentScores.get(0).getTotalScore() < Double.parseDouble(downGradeRule.getEvaluationScore()) &&
										assessmentScores.get(1).getTotalScore() < Double.parseDouble(downGradeRule.getEvaluationScore()))){
								//生成降级信息
								this.addInsertDownLevelAdjustment(accountManager, ManagerLevelEnum.MANA003.name(), year, month);
							}
						}
						
						//e.	业务高级微贷经理的降级：自然年内季度业绩指标未达标满二次；自然年内综合评估得分低于80分满二次。
						if(ManagerLevelEnum.MANA006.name().equals(accountManager.getLevelInformation())){
							//自然年内指标数据
							List<ManagerMonthTargetData> targetDatas = 
									managerAssessmentScoreCommDao.findManangerMonthTargetDataByYear(accountManager.getUserId(), accountManager.getLevelInformation(), year);
							//自然年内综合评估得分低于80分满二次
							List<ManagerAssessmentScore> assessmentScores = managerAssessmentScoreCommDao.
									findManagerAssessmentScoreByYear(accountManager.getUserId(), accountManager.getLevelInformation(), year);
							MangerMonthAssessment assessment = managerTargetAssessmentMap.get(accountManager.getLevelInformation());
							int lowEightyCount = 0;
							for(int i = 0; i < assessmentScores.size(); i++){
								if(assessmentScores.get(i).getTotalScore() != null &&
										assessmentScores.get(i).getTotalScore() < Double.parseDouble(downGradeRule.getEvaluationScore())){
									lowEightyCount ++;
								}
							}
							int quarterYeJiZhiBiao = 0;
							if(targetDatas.size() >= 6){
								int quarterSize = targetDatas.size()/3; 
								for(int i = 0; i < quarterSize; i++){
									Double quarterCreditAccount = 0.0;
									for(int j = 1; j <= 3; j++){
										quarterCreditAccount += Double.parseDouble(targetDatas.get(i * 3 + j).getMonthPerdayCreditline());
									}
									if(quarterCreditAccount/3 < Double.parseDouble(assessment.getMonthdayAvgCreditline())){
										quarterYeJiZhiBiao ++;
									}
								}
							}
							if(lowEightyCount >= 2 || quarterYeJiZhiBiao >= 2){
								//生成降级信息
								this.addInsertDownLevelAdjustment(accountManager, ManagerLevelEnum.MANA004.name(), year, month);
							}
						}
					}
				}
				// 设置查询的页码
				filter.setPage(filter.getPage() + 1);
				qs = accountManagerParameterService.findAccountManagerParametersByFilter(filter);
			}
		} catch (Exception e) {
			logger.error("客户经理降级定时生成错误");
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 生成降级信息
	 * @param accountManager
	 * @param afterLevel
	 */
	private void addInsertDownLevelAdjustment(AccountManagerParameterForm accountManager, String afterLevel, int year, int month) {
		
		int count = managerAssessmentScoreCommDao.findManagerLevelAdjustment(accountManager.getUserId(), year, month);
		if(count == 0){
			ManagerLevelAdjustment managerLevelAdjustment = new ManagerLevelAdjustment();
			managerLevelAdjustment.setIfHandled(ManagerLevelAdjustmentConstant.IFHANDLE_0);
			managerLevelAdjustment.setSystemAdvice(ManagerAdjustAdviceEnum.down.name());
			managerLevelAdjustment.setOriginalLevel(accountManager.getLevelInformation());
			managerLevelAdjustment.setAdjustAfterLevel(afterLevel);
			managerLevelAdjustment.setCustomerManagerId(accountManager.getUserId());
			managerLevelAdjustment.setCreatedBy(Constant.SCHEDULES_SYSTEM_USER);
			managerLevelAdjustment.setCreatedTime(new Date());
			managerLevelAdjustment.setModifiedBy(Constant.SCHEDULES_SYSTEM_USER);
			managerLevelAdjustment.setModifiedTime(new Date());
			managerLevelAdjustment.setDataYear(year);
			managerLevelAdjustment.setDataMonth(month);
			commonDao.insertObject(managerLevelAdjustment);
		}
	}

	/**
	 * 客户经理每月评估
	 * @param year
	 * @param month
	 */
	private void addManagerMonthAssessmentScore(int year, int month) {
		try{	
			AccountManagerParameterFilter filter = new AccountManagerParameterFilter();
			// 设置每次最大查询记录数
			filter.setLimit(50);
			// 查询页码
			filter.setPage(0);
			QueryResult<AccountManagerParameterForm> qs = accountManagerParameterService.findAccountManagerParametersByFilter(filter);
			while(qs.getItems().size() != 0){
				for(AccountManagerParameterForm accountManager : qs.getItems()){
					//如果已经存在则不需要重新计算评估信息
					ManagerAssessmentScore score = managerAssessmentScoreCommDao.findManagerAssessmentScoreByYearAndMonth(
							year, month, accountManager.getUserId(),accountManager.getLevelInformation());
					if(score != null){
						continue;
					}
					List<CustomerInfor> customerInfors = customerInforCommDao.findCustomerByManagerId(accountManager.getUserId());
					if(customerInfors != null && customerInfors.size() > 0){
						List<String> customerIds = new ArrayList<String>();
						for(CustomerInfor infor : customerInfors){
							customerIds.add(infor.getId());
						}
						this.addManagerAssessmentScore(year, month, accountManager, customerIds);
					}
				}
				// 设置查询的页码
				filter.setPage(filter.getPage() + 1);
				qs = accountManagerParameterService.findAccountManagerParametersByFilter(filter);
			}
		}catch(Exception e){
			logger.error("客户经理评估信息定时生成错误");
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 生成客户经理评估信息
	 * @param year
	 * @param month
	 * @param accountManager
	 * @param customerIds
	 */
	private void addManagerAssessmentScore(int year, int month,
			AccountManagerParameterForm accountManager, List<String> customerIds) {
		//合同金额 (申请表 实际额度)
		Double heTongJE = managerAssessmentScoreCommDao.sumAppApplyQuota(customerIds);
		//账单统计信息
		List<HashMap<String, Object>> billList = managerAssessmentScoreCommDao.sumAccountBill(customerIds, year, month);
		HashMap<String, Object> acBilMap = billList.get(0);
		// 贷款余额  透支余额
		BigDecimal touZhiYEBig = (BigDecimal) acBilMap.get("OVERDRAFT_ACCOUNT");
		Double touZhiYE = touZhiYEBig.doubleValue(); 
		//实收利息
		BigDecimal shiShouLSBig = (BigDecimal) acBilMap.get("PAID_INTEREST_ACCOUNT");
		Double shiShouLS = shiShouLSBig.doubleValue();
		//实收利息
		BigDecimal yingShouLSBig = (BigDecimal) acBilMap.get("CURRENT_MONTH_INTEREST_ACCOUNT");
		Double yingShouLS = yingShouLSBig.doubleValue();
		//逾期金额
		BigDecimal yuQiJEBig = (BigDecimal) acBilMap.get("OVERDUE_AMOUNT");
		Double yuQiJE = yuQiJEBig.doubleValue();
		//还款金额
		BigDecimal huanKuanJEBig = (BigDecimal) acBilMap.get("PAYBACK_ACCOUNT");
		Double huanKuanJE = huanKuanJEBig.doubleValue();
		//逾期后还款总额
		Double yuQiHouHuanZE = managerAssessmentScoreCommDao.sumOverduePaybackAll(customerIds);
		//存款
		BigDecimal cunKuangBig = (BigDecimal) acBilMap.get("PAYBACK_ACCOUNT");
		Double cunKuang = cunKuangBig.doubleValue();
		
		//贷款使用率
		double dksyl = 0;
		if(heTongJE != 0){
			dksyl = (touZhiYE/heTongJE) * 100;
			BigDecimal bg = new BigDecimal(dksyl);
			dksyl = bg.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		//利息收回率
		double lshsl = 0;
		if(yingShouLS != 0){
			lshsl = (shiShouLS/yingShouLS) * 100;
			BigDecimal bg = new BigDecimal(lshsl);
			lshsl = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		//逾期率
		double yql = 0;
		if(touZhiYE != 0){
			yql = (yuQiJE/touZhiYE) * 100;
			BigDecimal bg = new BigDecimal(yql);
			yql = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		//逾期回收率
		double yqhsl = 0;
		if(touZhiYE != 0){
			yqhsl = (huanKuanJE/touZhiYE) * 100;
			BigDecimal bg = new BigDecimal(yqhsl);
			yqhsl = bg.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		//瑕疵贷款率
		double xcdkl = 0;
		if(touZhiYE != 0){
			xcdkl = (yuQiHouHuanZE/touZhiYE) * 100;
			BigDecimal bg = new BigDecimal(xcdkl);
			xcdkl = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		//逾期余额
		double yqye = touZhiYE/10000;
		BigDecimal bg = new BigDecimal(yqye);
		yqye = bg.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
		
		List<ManagerAssessmentScore> assessmentScores = 
				managerAssessmentScoreCommDao.findManagerAssessmentScoreByYearAndMonth(accountManager.getUserId(), accountManager.getLevelInformation(), 1);
		
		//TODO 存款 电子银行产品
		
		ManagerAssessmentScore managerAssessmentScore = new ManagerAssessmentScore();
		managerAssessmentScore.setCreditUtilizationRate(dksyl);
		managerAssessmentScore.setNumbererestRecoveryRate(lshsl);
		managerAssessmentScore.setOverdueRate(yql);
		managerAssessmentScore.setLateRecoveryRate(yqhsl);
		managerAssessmentScore.setOverdueBalanceRate(yqye);
		managerAssessmentScore.setDefectiveLoansRate(xcdkl);
		managerAssessmentScore.setAssessed(accountManager.getUserId());
		managerAssessmentScore.setAssessedConfirmDate(new Date());
		managerAssessmentScore.setCreatedBy(Constant.SCHEDULES_SYSTEM_USER);
		managerAssessmentScore.setCreatedTime(new Date());
		managerAssessmentScore.setModifiedBy(Constant.SCHEDULES_SYSTEM_USER);
		managerAssessmentScore.setModifiedTime(new Date());
		managerAssessmentScore.setDataYear(year);
		managerAssessmentScore.setDataMonth(month);
		managerAssessmentScore.setCustomerManagerLevel(accountManager.getLevelInformation());
		//存款余额每增加10万元加1分，总分10分
		if(assessmentScores.size() > 0 && assessmentScores.get(0).getLastDeposit() != null){
			BigDecimal depositScore = new BigDecimal((cunKuang - assessmentScores.get(0).getLastDeposit()) / (10 * 10000 * 100)); //已分单位
			int depositScoreInt = depositScore.intValue();
			if(depositScoreInt > 10){
				depositScoreInt = 10;
			}
			managerAssessmentScore.setDeposit((double) depositScoreInt);
		} else {
			managerAssessmentScore.setDeposit(0.0);
		}
		
		managerAssessmentScore.setLastDeposit(cunKuang);
		
		commonDao.insertObject(managerAssessmentScore);
	}
	
	/**
	 * 每月初定时生成当月评估表
	 */
	public void addAssessment(){
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) +1;
		//获取所有客户经理
		UserFilter filter = new UserFilter();
		filter.setUserType(1);
		String sql = "select * from sys_user where id in (select user_id from account_manager_parameter)";
		List<User> users = commonDao.queryBySql(User.class, sql, null);
		for(int i=0;i<users.size();i++){
			TyManagerAssessment assessment = new TyManagerAssessment();
			assessment.setId(IDGenerator.generateID());
			assessment.setYear(year+"");
			assessment.setMonth(month+"");
			assessment.setCustomerId(users.get(i).getId());
			assessment.setCustomerName(users.get(i).getDisplayName());
			commonDao.insertObject(assessment);
		}
	}
	
	
	/**
	 * 太原
	 * 客户经理晋降级
	 * 每年 3,6,9,12月生成客户经理升降级记录
	 * 插入客户经理级别调整信息
	 * @param 升降级规则(考核指标)
	 * @param 客户经理实际指数
	 */
	public void generateManagerAdjustment(){
		try{
			Calendar calendar = Calendar.getInstance();
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1, 0, 0, 0);
			calendar.add(Calendar.MONTH, -1);
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH) + 1;
			
			//查询升降级规则
			List<ManagerPromotionDownRule> managerPromotionRulelist = managerPromotionRuleComdao.getManagerPromotionDownRule();
			Map<String, ManagerPromotionDownRule> GradeRulesMap = new HashMap<String, ManagerPromotionDownRule>();
			for(ManagerPromotionDownRule Rule : managerPromotionRulelist){
				GradeRulesMap.put(Rule.getInitialLevel(), Rule);
			}
			
			AccountManagerParameterFilter filter = new AccountManagerParameterFilter();
			// 设置每次最大查询记录数
			filter.setLimit(50);
			// 查询页码
			filter.setPage(0);
			// 查询客户经理
			QueryResult<AccountManagerParameterForm> qs = accountManagerParameterService.findAccountManagerParametersByFilter(filter);
			while(qs.getItems().size() != 0){
				for(AccountManagerParameterForm accountManager : qs.getItems()){
					//TODO 
					//统计客户经理实际指标情况,以下数据仅供测试时使用
					
					//季度日均用信余额
					String	quarterAverageCreditBalance ="1500";
					//统计该客户经理下的客户数目
					List<CustomerInfor> customerList=customerInforService.findCustomerByManagerId(accountManager.getUserId());
					//Integer	tubeNumber = customerList.size();
					//管户数（户）
					Integer	tubeNumber =180;
					//逾期贷款率
					String	overLoanRatio = "0.1";
					//月均KPI得分
					String	kpiScore = "90";
					
					ManagerPromotionDownRule  GradeRule = GradeRulesMap.get(accountManager.getLevelInformation());
					if(GradeRule == null){
						continue;
					}
					// a.见习个人微贷经理  季度日均用信余额:100	 管户数:10	  	逾期贷款率:0   月均KPI得分:80
					if(ManagerLevelConstant.MANAGER_LEVEL_1.equals(accountManager.getLevelInformation())){
						if(Double.parseDouble(quarterAverageCreditBalance)>=Double.parseDouble(GradeRulesMap.get(ManagerLevelConstant.MANAGER_LEVEL_2).getQuarterAverageCreditBalance()) 
								&& tubeNumber >= GradeRulesMap.get(ManagerLevelConstant.MANAGER_LEVEL_2).getTubeNumber()
								&& Double.parseDouble(overLoanRatio) < Double.parseDouble(GradeRulesMap.get(ManagerLevelConstant.MANAGER_LEVEL_2).getOverLoanRatio()) 
								&& Double.parseDouble(kpiScore) >= Double.parseDouble(GradeRulesMap.get(ManagerLevelConstant.MANAGER_LEVEL_2).getKpiScore())){
							//生成升级信息
							this.addInsertLevelAdjustment(accountManager,ManagerLevelConstant.MANAGER_LEVEL_2,ManagerAdjustAdviceEnum.up.name(),year, month);
						}
					}
					// b.初级个人微贷经理 季度日均用信余额:500	 管户数:70	            逾期贷款率:1  月均KPI得分:80
					if(ManagerLevelConstant.MANAGER_LEVEL_2.equals(accountManager.getLevelInformation())){
						if(Double.parseDouble(quarterAverageCreditBalance)>=Double.parseDouble(GradeRulesMap.get(ManagerLevelConstant.MANAGER_LEVEL_3).getQuarterAverageCreditBalance())  
								&& tubeNumber >= GradeRulesMap.get(ManagerLevelConstant.MANAGER_LEVEL_3).getTubeNumber()
								&& Double.parseDouble(overLoanRatio) < Double.parseDouble(GradeRulesMap.get(ManagerLevelConstant.MANAGER_LEVEL_3).getOverLoanRatio()) 
								&& Double.parseDouble(kpiScore) >= Double.parseDouble(GradeRulesMap.get(ManagerLevelConstant.MANAGER_LEVEL_3).getKpiScore())){
							//生成升级信息
							this.addInsertLevelAdjustment(accountManager,ManagerLevelConstant.MANAGER_LEVEL_3,ManagerAdjustAdviceEnum.up.name(),year, month);
						}
						
						if(Double.parseDouble(quarterAverageCreditBalance)<Double.parseDouble(GradeRulesMap.get(ManagerLevelConstant.MANAGER_LEVEL_2).getQuarterAverageCreditBalance()) 
								|| tubeNumber <GradeRulesMap.get(ManagerLevelConstant.MANAGER_LEVEL_2).getTubeNumber()
								|| Double.parseDouble(overLoanRatio) >= Double.parseDouble(GradeRulesMap.get(ManagerLevelConstant.MANAGER_LEVEL_2).getOverLoanRatio())  
								|| Double.parseDouble(kpiScore) < Double.parseDouble(GradeRulesMap.get(ManagerLevelConstant.MANAGER_LEVEL_2).getKpiScore())){
							//生成降级信息
							this.addInsertLevelAdjustment(accountManager,ManagerLevelConstant.MANAGER_LEVEL_1,ManagerAdjustAdviceEnum.down.name(),year, month);
						}
					}	
					// c.中级个人微贷经理 季度日均用信余额:800	 管户数:110	 逾期贷款率:1   月均KPI得分：85
					if(ManagerLevelConstant.MANAGER_LEVEL_3.equals(accountManager.getLevelInformation())){
						if(Double.parseDouble(quarterAverageCreditBalance)>=Double.parseDouble(GradeRulesMap.get(ManagerLevelConstant.MANAGER_LEVEL_4).getQuarterAverageCreditBalance()) 
								&& tubeNumber >= GradeRulesMap.get(ManagerLevelConstant.MANAGER_LEVEL_4).getTubeNumber()  
								&& Double.parseDouble(overLoanRatio) < Double.parseDouble(GradeRulesMap.get(ManagerLevelConstant.MANAGER_LEVEL_4).getOverLoanRatio())
								&& Double.parseDouble(kpiScore) >= Double.parseDouble(GradeRulesMap.get(ManagerLevelConstant.MANAGER_LEVEL_4).getKpiScore())){
							//生成升级信息
							this.addInsertLevelAdjustment(accountManager,ManagerLevelConstant.MANAGER_LEVEL_4,ManagerAdjustAdviceEnum.up.name(),year, month);
						}
						
						if(Double.parseDouble(quarterAverageCreditBalance) < Double.parseDouble(GradeRulesMap.get(ManagerLevelConstant.MANAGER_LEVEL_3).getQuarterAverageCreditBalance()) 
								|| tubeNumber <  GradeRulesMap.get(ManagerLevelConstant.MANAGER_LEVEL_3).getTubeNumber() 
								|| Double.parseDouble(overLoanRatio) >= Double.parseDouble(GradeRulesMap.get(ManagerLevelConstant.MANAGER_LEVEL_3).getOverLoanRatio())
								|| Double.parseDouble(kpiScore) < Double.parseDouble(GradeRulesMap.get(ManagerLevelConstant.MANAGER_LEVEL_3).getKpiScore())){
							//生成降级信息
							this.addInsertLevelAdjustment(accountManager,ManagerLevelConstant.MANAGER_LEVEL_2,ManagerAdjustAdviceEnum.down.name(),year, month);
						}
						
					}
					// d.高级个人微贷经理 季度日均用信余额:1100	 管户数:140	 逾期贷款率:1   月均KPI得分:90
					if(ManagerLevelConstant.MANAGER_LEVEL_4.equals(accountManager.getLevelInformation())){
						if(Double.parseDouble(quarterAverageCreditBalance)>=Double.parseDouble(GradeRulesMap.get(ManagerLevelConstant.MANAGER_LEVEL_5).getQuarterAverageCreditBalance()) 
								&& tubeNumber >=  GradeRulesMap.get(ManagerLevelConstant.MANAGER_LEVEL_5).getTubeNumber() 
								&& Double.parseDouble(overLoanRatio) <Double.parseDouble(GradeRulesMap.get(ManagerLevelConstant.MANAGER_LEVEL_5).getOverLoanRatio())   
								&& Double.parseDouble(kpiScore) >= Double.parseDouble(GradeRulesMap.get(ManagerLevelConstant.MANAGER_LEVEL_5).getKpiScore())){
							//生成升级信息
							this.addInsertLevelAdjustment(accountManager,ManagerLevelConstant.MANAGER_LEVEL_5,ManagerAdjustAdviceEnum.up.name(), year, month);
						}
						
						if(Double.parseDouble(quarterAverageCreditBalance) < Double.parseDouble(GradeRulesMap.get(ManagerLevelConstant.MANAGER_LEVEL_4).getQuarterAverageCreditBalance())  
								|| tubeNumber < GradeRulesMap.get(ManagerLevelConstant.MANAGER_LEVEL_4).getTubeNumber() 
								|| Double.parseDouble(overLoanRatio) >= Double.parseDouble(GradeRulesMap.get(ManagerLevelConstant.MANAGER_LEVEL_4).getOverLoanRatio())  
								|| Double.parseDouble(kpiScore) < Double.parseDouble(GradeRulesMap.get(ManagerLevelConstant.MANAGER_LEVEL_4).getKpiScore())){
							//生成降级信息
							this.addInsertLevelAdjustment(accountManager,ManagerLevelConstant.MANAGER_LEVEL_3, ManagerAdjustAdviceEnum.down.name(),year, month);
						}
					}
					// e.资深个人微贷经理 季度日均用信余额:1500	 管户数:170	 逾期贷款率:1   月均KPI得分:90
					if(ManagerLevelConstant.MANAGER_LEVEL_5.equals(accountManager.getLevelInformation())){
						if(Double.parseDouble(quarterAverageCreditBalance) < Double.parseDouble(GradeRulesMap.get(ManagerLevelConstant.MANAGER_LEVEL_5).getQuarterAverageCreditBalance()) 
								|| tubeNumber < GradeRulesMap.get(ManagerLevelConstant.MANAGER_LEVEL_5).getTubeNumber() 
								|| Double.parseDouble(overLoanRatio) >=  Double.parseDouble(GradeRulesMap.get(ManagerLevelConstant.MANAGER_LEVEL_5).getOverLoanRatio())  
								|| Double.parseDouble(kpiScore) < Double.parseDouble(GradeRulesMap.get(ManagerLevelConstant.MANAGER_LEVEL_5).getKpiScore())){
							//生成降级信息
							this.addInsertLevelAdjustment(accountManager,ManagerLevelConstant.MANAGER_LEVEL_4, ManagerAdjustAdviceEnum.down.name(),year, month);
						}
					}
				}
				// 设置查询的页码
				filter.setPage(filter.getPage() + 1);
				qs = accountManagerParameterService.findAccountManagerParametersByFilter(filter);
			}
		}catch(Exception e){
			logger.error("客户经理升降级定时生成错误");
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * ty
	 * 生成降级信息
	 * @param accountManager
	 * @param afterLevel
	 */
	private void addInsertLevelAdjustment(AccountManagerParameterForm accountManager, String afterLevel,String systemadvice,int year, int month) {
		
		int count = managerAssessmentScoreCommDao.findManagerLevelAdjustment(accountManager.getUserId(), year, month);
		if(count == 0){
			ManagerLevelAdjustment managerLevelAdjustment = new ManagerLevelAdjustment();
			managerLevelAdjustment.setIfHandled(ManagerLevelAdjustmentConstant.IFHANDLE_0);
			managerLevelAdjustment.setSystemAdvice(systemadvice);
			managerLevelAdjustment.setOriginalLevel(accountManager.getLevelInformation());
			managerLevelAdjustment.setAdjustAfterLevel(afterLevel);
			managerLevelAdjustment.setCustomerManagerId(accountManager.getUserId());
			managerLevelAdjustment.setCreatedBy(Constant.SCHEDULES_SYSTEM_USER);
			managerLevelAdjustment.setCreatedTime(new Date());
			managerLevelAdjustment.setModifiedBy(Constant.SCHEDULES_SYSTEM_USER);
			managerLevelAdjustment.setModifiedTime(new Date());
			managerLevelAdjustment.setDataYear(year);
			managerLevelAdjustment.setDataMonth(month);
			commonDao.insertObject(managerLevelAdjustment);
		}
	}
	
}
