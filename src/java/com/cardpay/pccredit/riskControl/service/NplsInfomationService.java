package com.cardpay.pccredit.riskControl.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.customer.filter.CustomerInforFilter;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.service.CardInfomationService;
import com.cardpay.pccredit.customer.service.CustomerAccountInfoService;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.customer.web.CardInfomationFrom;
import com.cardpay.pccredit.customer.web.CustomerAccountInfoForm;
import com.cardpay.pccredit.intopieces.constant.Constant;
import com.cardpay.pccredit.manager.dao.comdao.ManagerBelongMapComdao;
import com.cardpay.pccredit.manager.service.AccountManagerParameterService;
import com.cardpay.pccredit.manager.web.AccountManagerParameterForm;
import com.cardpay.pccredit.product.dao.ManagerProductsConfigurationDao;
import com.cardpay.pccredit.product.model.ManagerProductsConfiguration;
import com.cardpay.pccredit.riskControl.constant.RiskAttributeEnum;
import com.cardpay.pccredit.riskControl.constant.RiskConstants;
import com.cardpay.pccredit.riskControl.constant.RiskCreateTypeEnum;
import com.cardpay.pccredit.riskControl.constant.RiskCustomerStatusEnum;
import com.cardpay.pccredit.riskControl.constant.RiskType;
import com.cardpay.pccredit.riskControl.dao.NplsInfomationDao;
import com.cardpay.pccredit.riskControl.dao.Comdao.NplsInfomationConfigurationComdao;
import com.cardpay.pccredit.riskControl.filter.NplsInfomationFilter;
import com.cardpay.pccredit.riskControl.model.NplsInfomation;
import com.cardpay.pccredit.riskControl.model.NplsInfomationConfiguration;
import com.cardpay.pccredit.riskControl.model.RiskCustomer;
import com.cardpay.pccredit.riskControl.web.NplsInfomationForm;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.modules.privilege.model.User;

/**
 * 不良资产service
 * 描述 ：
 * @author 张石树
 *
 * 2014-11-4 上午10:02:28
 */
@Service
public class NplsInfomationService {
	
	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private NplsInfomationDao nplsInfomationDao;
	
	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private RiskCustomerService riskCustomerService;
	
	@Autowired
	private CustomerInforService customerInforService;
	
	@Autowired
	private CustomerAccountInfoService customerAccountInfoService;
	
	@Autowired
	private ManagerBelongMapComdao managerBelongMapComdao;
	
	@Autowired
	private AccountabilityService accountabilityService;
	
	@Autowired
	private AccountManagerParameterService accountManagerParameterService;
	
	@Autowired
	private CardInfomationService cardInfomationService;
	
	@Autowired
	private ManagerProductsConfigurationDao managerProductsConfigurationDao;
	
	@Autowired
	private NplsInfomationConfigurationComdao nplsInfomationConfigurationComdao;
	
	/**
	 * 浏览页查询
	 * @param filter
	 * @return
	 */
	public QueryResult<NplsInfomationForm> findNplsInfomationByFilter( NplsInfomationFilter filter) {
		List<NplsInfomationForm> riskCustomers = nplsInfomationDao.findNplsInfomationByFilter(filter);
		int size = nplsInfomationDao.findNplsInfomationCountByFilter(filter);
		QueryResult<NplsInfomationForm> qs = new QueryResult<NplsInfomationForm>(size, riskCustomers);
		return qs;
	}

	/**
	 * 根据Id查找
	 * @param id
	 * @return
	 */
	public NplsInfomationForm findNplsInfomationById(String id) {
		return nplsInfomationDao.findNplsInfomationById(id);
	}

	/**
	 * 插入
	 * @param nplsInfomation
	 */
	public void insertNplsInfomaton(NplsInfomation nplsInfomation) {
		commonDao.insertObject(nplsInfomation);
	}

	/**
	 * 修改
	 * @param nplsInfomation
	 */
	public void updateNplsInfomation(NplsInfomation nplsInfomation) {
		commonDao.updateObject(nplsInfomation);
	}

	/**
	 * 根据Id删除
	 * @param id
	 */
	public void deleteNplsInfomation(String id) {
		commonDao.deleteObject(NplsInfomation.class, id);
	}

	/**
	 * 确认
	 * @param id
	 * @param user 
	 * @param verificationAccount 
	 */
	public void confirm(String id, User user, String verificationAccount) {
		NplsInfomation nplsInfomation = commonDao.findObjectById(NplsInfomation.class, id);
		nplsInfomation.setReviewResults(RiskConstants.NPLS_INFO_CONFIRM);
		nplsInfomation.setModifiedBy(user.getId());
		nplsInfomation.setModifiedTime(new Date());
		nplsInfomation.setVerificationAccount((Double.parseDouble(verificationAccount) * 100) + "");
		commonDao.updateObject(nplsInfomation);
		
//		verificationNplsInfomation(user, verificationAccount, nplsInfomation);
	}

	/**
	 * 确认核销后的操作
	 * @param nplsInfomation
	 */
	private void verificationNplsInfomation(NplsInfomation nplsInfomation) {
		//风险名单
		RiskCustomer riskCustomer = new RiskCustomer();
		riskCustomer.setReportedIdManager(null);
		riskCustomer.setCustomerId(nplsInfomation.getCustomerId());
		riskCustomer.setRiskLevel(RiskConstants.RISK_LEVEL_ONE);
		riskCustomer.setRiskCreateType(RiskCreateTypeEnum.system.name());
		riskCustomer.setRiskDes(RiskConstants.NPLS_CONFIRM_TO_RISKCUSTOMER);
		riskCustomer.setRiskAttribute(RiskAttributeEnum.BLZCKX.name());
		riskCustomer.setStatus(RiskCustomerStatusEnum.CONFIRMED_CARDCENTER.name());
		riskCustomer.setCreatedBy(nplsInfomation.getModifiedBy());
		riskCustomer.setCreatedTime(new Date());
		riskCustomer.setModifiedBy(nplsInfomation.getModifiedBy());
		riskCustomer.setModifiedTime(new Date());
		riskCustomerService.insertRiskCustomer(riskCustomer);
		
		CustomerInfor customerInfor = customerInforService.findCustomerInforById(nplsInfomation.getCustomerId());
		AccountManagerParameterForm accountManagerParameterForm = 
				accountManagerParameterService.findAccountManagerParameterByUserId(customerInfor.getUserId());
		CustomerAccountInfoForm accountInfoForm = customerAccountInfoService.findCustomerAccountById(nplsInfomation.getAccountId());
		CardInfomationFrom cardInfomationFrom = cardInfomationService.findCardInfoByCustomerIdAndCardNumber(nplsInfomation.getCustomerId(), accountInfoForm.getCardNumber());
		Double creditAmount =  Double.parseDouble(accountInfoForm.getCreditAmount());
		Double dverificationAccount = Double.parseDouble(nplsInfomation.getVerificationAccount());
		//查询客户经理层级容忍度
		ManagerProductsConfiguration managerProductsConfiguration = 
				managerProductsConfigurationDao.findConfigurationByProductIdAndLevel(cardInfomationFrom.getProductId(), accountManagerParameterForm.getLevelInformation());
		if(managerProductsConfiguration == null){
			//问责信息
			accountabilityService.insertAccountAbility(customerInfor.getUserId(), nplsInfomation.getCustomerId(), 
					cardInfomationFrom.getProductId(), RiskType.cancel, RiskConstants.NPLS_CONFIRM_TO_ABILITY, nplsInfomation.getModifiedBy());
		} else {
			Double riskTolerance = Double.parseDouble(managerProductsConfiguration.getRiskToleranceInformation());
			//大于客户经理风险容忍度
			if((dverificationAccount/creditAmount * 100) > riskTolerance){
				//问责信息
				accountabilityService.insertAccountAbility(customerInfor.getUserId(), nplsInfomation.getCustomerId(), 
						cardInfomationFrom.getProductId(), RiskType.cancel, RiskConstants.NPLS_CONFIRM_TO_ABILITY, nplsInfomation.getModifiedBy());
			}
		}
	}

	/**
	 * 拒绝
	 * @param id
	 * @param user 
	 */
	public void refuse(String id, User user) {
		NplsInfomation nplsInfomation = commonDao.findObjectById(NplsInfomation.class, id);
		nplsInfomation.setReviewResults(RiskConstants.NPLS_INFO_REFUSE);
		nplsInfomation.setModifiedBy(user.getId());
		nplsInfomation.setModifiedTime(new Date());
		commonDao.updateObject(nplsInfomation);
	}
	
	/**
	 * 定时执行生成不良资产信息
	 */
	public void addNplsInfomation(){
		
		NplsInfomationConfiguration configuration = managerBelongMapComdao.findNplsInfomationConfiguration();
		if(configuration != null){
			Integer aging = configuration.getAging();
			Double overDueVal = null;
			if(StringUtils.isNotEmpty(configuration.getOverdueMoney())){
				overDueVal = Double.parseDouble(configuration.getOverdueMoney());
			}
			Double orerDueDayVal = null;
			if(StringUtils.isNotEmpty(configuration.getNumberDaysOverdue())){
				orerDueDayVal = Double.parseDouble(configuration.getNumberDaysOverdue());
			}
			CustomerInforFilter filter = new CustomerInforFilter();
			// 设置每次最大查询记录数
			filter.setLimit(50);
			// 查询页码
			filter.setPage(0);
			// 查询客户信息
			QueryResult<CustomerInfor> qs = customerInforService.findCustomerInforByFilter(filter);
			try{
				while(qs.getItems().size() != 0){
					for(CustomerInfor customerInfor : qs.getItems()){
						List<CustomerAccountInfoForm> accountInfoForms = 
								customerAccountInfoService.findCustomerAccountByCustomerId(customerInfor.getId());
						for(CustomerAccountInfoForm accountInfoForm : accountInfoForms){
							boolean falg = false;
							//TODO 先判断是否核销标志
							if("1".equals(accountInfoForm.getChargeLogo())){
								//已核销
								NplsInfomation nplsInfomation = nplsInfomationConfigurationComdao.getConfirmNplsInfomation(customerInfor.getId(), accountInfoForm.getId(), 
										RiskConstants.NPLS_INFO_CONFIRM);
								if(nplsInfomation != null){
									try {
										this.verificationNplsInfomation(nplsInfomation);
									} catch (Exception e) {
										logger.error("verificationNplsInfomation error,不良资产核销生成风险名单，问责信息出错", e);
										e.printStackTrace();
									}
									nplsInfomation.setVerificationStatus(RiskConstants.NPLS_INFO_VERIFICATION);
									commonDao.updateObject(nplsInfomation);
								}
								//verification
							} else {
								if(StringUtils.isNotEmpty(accountInfoForm.getAging())){
									if(aging != null && Long.parseLong(accountInfoForm.getAging()) > aging){
										falg = true;
									}
								}
								if(StringUtils.isNotEmpty(accountInfoForm.getOverdueAmount())){
									if(overDueVal != null && Double.parseDouble(accountInfoForm.getOverdueAmount()) > overDueVal){
										falg = true;
									}
								}
								if(StringUtils.isNotEmpty(accountInfoForm.getOverdueTime())){
									if(orerDueDayVal != null && Double.parseDouble(accountInfoForm.getOverdueTime()) > orerDueDayVal){
										falg = true;
									}
								}
								if(falg){
									//插入不良资产信息 不存在创建或确认
									int count = nplsInfomationDao.findNplsInfomationCountBy(accountInfoForm.getCustomerId(), accountInfoForm.getId());
									if(count == 0){
										NplsInfomation infomation = new NplsInfomation();
										infomation.setCustomerId(accountInfoForm.getCustomerId());
										infomation.setAccountId(accountInfoForm.getId());
										infomation.setReviewResults(RiskConstants.NPLS_INFO_NEW);
										infomation.setCreateMethod(RiskConstants.NPLS_CREATE_METHOD_AUTO);
										infomation.setVerificationType(RiskConstants.NPLS_INFO_CONDITION_TYPE_8);
										infomation.setCreatedBy(Constant.SCHEDULES_SYSTEM_USER);
										infomation.setCreatedTime(new Date());
										infomation.setModifiedBy(Constant.SCHEDULES_SYSTEM_USER);
										infomation.setModifiedTime(new Date());
										commonDao.insertObject(infomation);
									}
								}
							}
						}
					}
					// 设置查询的页码
					filter.setPage(filter.getPage() + 1);
					qs = customerInforService.findCustomerInforByFilter(filter);
				}
			}catch(Exception e){
				logger.error("system auto check nplsinfomation everyday schedules job error " ,e);
				throw new RuntimeException(e.getMessage());
			}
		}
	}
	/**
	 * 得到客户经理名下核销客户数量
	 * @param id
	 * @return
	 */
	public int findNplsInformationCountById(String id){
		return nplsInfomationDao.findNplsInformationCountById(id);
	}
}
