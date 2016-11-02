package com.cardpay.pccredit.riskControl.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.customer.dao.CardInfomationDao;
import com.cardpay.pccredit.customer.dao.CustomerAccountInfoDao;
import com.cardpay.pccredit.customer.dao.CustomerOverdueHistoryDao;
import com.cardpay.pccredit.customer.dao.comdao.CustomerOverdueComDao;
import com.cardpay.pccredit.customer.filter.CustomerOverdueHistoryFilter;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.model.CustomerOverdue;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.customer.web.CardInfomationFrom;
import com.cardpay.pccredit.customer.web.CustomerAccountInfoForm;
import com.cardpay.pccredit.manager.model.AccountManagerParameter;
import com.cardpay.pccredit.riskControl.constant.AuditStatusEnum;
import com.cardpay.pccredit.riskControl.constant.RiskType;
import com.cardpay.pccredit.riskControl.dao.Comdao.AccountabilityComDao;
import com.cardpay.pccredit.riskControl.filter.AccountabilityRecordFilter;
import com.cardpay.pccredit.riskControl.model.AccountabilityRecord;
import com.cardpay.pccredit.riskControl.model.ProductAccountability;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.model.QueryResult;

/**
 * 
 * @author 季东晓
 *
 * 2014-11-5 下午2:22:52
 */
 
@Service
public class AccountabilityService {
	
	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private AccountabilityComDao accountabilityDao;
	
	@Autowired
	private CustomerInforService customerInforService;
	
	@Autowired
	private CardInfomationDao cardInfomationDao;
	
	@Autowired
	private CustomerAccountInfoDao customerAccountInfoDao;
	
	@Autowired
	private CustomerOverdueHistoryDao customerOverdueHistoryDao;
	
	@Autowired
	private CustomerOverdueComDao  customerOverdueComDao;
	
	
	final public static String SYSTEM_MESSAGE  = "系统强制问责：";
	final public static String AGING  = "账龄超过  ";
	final public static String OVERDUETIME  = "逾期天数超过  ";
	final public static String OVERDUEAMOUNT  = "逾期金额超过  ";
	final public static String OVERDUETOTALTIME  = "逾期总次数超过  ";
	final public static String OVERDUETOTALAMOUNT  = "逾期总金额超过  ";
	
	
	/**
	 * 根据客户Id和产品Id 查询问责记录
	 * @return List
	 */
	
	public  AccountabilityRecord findAccountabilityRecordBy(String customerId,String productId){
		
		return accountabilityDao.findAccountabilityRecordBy(customerId, productId);
	}
	
	
	/**
	 * 根据客户Id和产品Id和客户经理id 查询问责记录
	 * @return List
	 */
	
	public  AccountabilityRecord findAccountabilityRecordByIds(String customerId,String productId,String customerManagerId){
		
		return accountabilityDao.findAccountabilityRecordByIds(customerId, productId, customerManagerId);
	}
	/**
	 * 删除问责记录
	 * @param id
	 * @return
	 */
	
	public int deleteAccountabilityRecord(String id) {
		return commonDao.deleteObject(AccountabilityRecord.class, id);
	}

	
	/**
	 * 
	 * @param filter
	 * @return
	 */
	
	public QueryResult<AccountabilityRecord> findAccountabilityRecordByFilter(AccountabilityRecordFilter filter) {
		
		
		return accountabilityDao.findAccountabilityRecordByFilter(filter);
	}
	
	/**
	 * 
	 * @param customerManagerId 客户经理Id
	 * @param customerId 客户
	 * @param productId 产品
	 * @param riskType 类型
	 * @param createReason 原因
	 * 
	 * 
	 */
	public int insertAccountAbility(String customerManagerId,String customerId,String productId,RiskType riskType,String createReason,String createUser){
		if(accountabilityDao.findAccountabilityRecordBy(customerId, productId)==null){
		AccountabilityRecord accountabilityRecord=new AccountabilityRecord();
		accountabilityRecord.setCustomerManagerId(customerManagerId);
		accountabilityRecord.setCustomerId(customerId);
		accountabilityRecord.setReconsideration("0");
		accountabilityRecord.setCreateWay(riskType.toString());
		accountabilityRecord.setProductId(productId);
		accountabilityRecord.setCreateReason(createReason);
		accountabilityRecord.setCreatedBy(createUser);
		accountabilityRecord.setCreatedTime(new Date());
		return commonDao.insertObject(accountabilityRecord);
		}else{
			return 0;
		}
	}
	/**
	 * 根据id查询问责记录
	 * @param id
	 * @return
	 */
    public AccountabilityRecord findAccountabilityRecordByID(String id) {
		
		return commonDao.findObjectById(AccountabilityRecord.class, id);
	}
    /**
     * 更新问责记录
     * @param accountabilityRecord
     * @return
     */
    public int updateAccountabilityRecord(AccountabilityRecord accountabilityRecord,String userId) {
    	Calendar calendar = Calendar.getInstance();
    	accountabilityRecord.setModifiedBy(userId);
    	accountabilityRecord.setModifiedTime(calendar.getTime());
    	if(accountabilityRecord != null ){
    		String money = accountabilityRecord.getMoney();
    		if(money !=null ){
    			Double moneyDouble = Double.parseDouble(money) * 100;
    			String moneyValue = moneyDouble.toString();
    			accountabilityRecord.setMoney(moneyValue);
    		}
    		}
		return commonDao.updateObject(accountabilityRecord);
	}
    /**
     * 结束问责记录
     * @param accountabilityRecord
     * @return
     */
    public int updateAccountabilityRecordEnd(AccountabilityRecord accountabilityRecord) {
    	Calendar calendar = Calendar.getInstance();
    	accountabilityRecord.setModifiedTime(calendar.getTime());
    	accountabilityRecord.setAuditStatus(AuditStatusEnum.endaudit.name());
		return commonDao.updateObject(accountabilityRecord);
	}
    
    /**
     * 新增问责记录
     * @param accountabilityRecord
     * @return
     */
	public String insertAccountabilityRecord(AccountabilityRecord accountabilityRecord) {
		accountabilityRecord.setCreatedTime(Calendar.getInstance().getTime());
		accountabilityRecord.setModifiedTime(Calendar.getInstance().getTime());
		accountabilityRecord.setReconsideration("0");
		accountabilityRecord.setCreateWay(RiskType.Manual.name());
		if(accountabilityRecord != null ){
		String money = accountabilityRecord.getMoney();
		if(money !=null ){
			Double moneyDouble = Double.parseDouble(money) * 100;
			String moneyValue = moneyDouble.toString();
			accountabilityRecord.setMoney(moneyValue);
		}
		}
		commonDao.insertObject(accountabilityRecord);

		return accountabilityRecord.getId();
	}

	
	/**
	 * 系统筛选问责客户经理
	 */
	public void insertAccountabilityRecordSystem(){
		
		//通过产品-问责规则查出要筛选的产品和规则
		
		//通过产品查询该客户的账龄、逾期天数、逾期金额、逾期次数、逾期总金额
				
		//通过客户Id查询客户经理Id
				
		//插入问责信息
		
		List<ProductAccountability> productAccountabilityList = accountabilityDao.findProductAccountability();
		for (ProductAccountability productAccountability : productAccountabilityList) {
			 String productId = productAccountability.getProductId();
			 String agingA = productAccountability.getAging();//账龄
			 String overdueTimeA = productAccountability.getOverdueDay();//逾期天数
			 String overdueAmountA = productAccountability.getOverdueAmount();//逾期金额
			 String overdueTotalTimeA = productAccountability.getOverdueTotalTime();//逾期总次数
			 String overdueTotalAmountA = productAccountability.getOverdueTotalAmount();//逾期总金额
		//获取规则
			 List<CardInfomationFrom> cardInfomationFromList = cardInfomationDao.findCardsByProductId(productId);
			 for(CardInfomationFrom cardInfomationFrom: cardInfomationFromList){
				boolean flag = false;
				String customerId = cardInfomationFrom.getCustomerId();
				String createReason = SYSTEM_MESSAGE;
				String cardNumber = cardInfomationFrom.getCardNumber();
				//通过卡号找客户的账户信息
				CustomerAccountInfoForm customerAccountInfoForm =customerAccountInfoDao.findCustomerAccountByCardNumber(cardNumber);
				if(customerAccountInfoForm!=null){
					String  aging = customerAccountInfoForm.getAging();//账龄
					String overdueTime = customerAccountInfoForm.getOverdueTime();//逾期天数
					//String overdueAmount =  customerAccountInfoForm.getOverdueAmount();//逾期金额
					//逾期客户
					CustomerOverdue customerOverdue =customerOverdueComDao.findCustomerOverdueBycustomerIdAndproductId(customerId, productId);
					if(customerOverdue!=null){
						String overdueAmount = customerOverdue.getCurrentBalance();//逾期金额
						if(StringUtils.isNotEmpty(overdueAmount)&&StringUtils.isNotEmpty(overdueAmountA)&&Double.parseDouble(overdueAmount) >= Double.parseDouble(overdueAmountA)){
							createReason = createReason + OVERDUEAMOUNT;
							flag = true;
						}
					}
						CustomerOverdueHistoryFilter filter = new CustomerOverdueHistoryFilter(); 
						filter.setCustomerId(customerId);
						filter.setProductId(productId);
						int overdueTotalTime =  customerOverdueHistoryDao.findCountById(filter);
						String overdueTotalAmount = customerOverdueHistoryDao.findOverdueAmountById(filter);
						
						
						if(overdueTotalTime != 0 &&StringUtils.isNotEmpty(overdueTotalTimeA)&& overdueTotalTime >= Double.parseDouble(overdueTotalTimeA)){
							createReason = createReason + OVERDUETOTALTIME;
							flag = true;
						}
						if(StringUtils.isNotEmpty(overdueTotalAmount)&&StringUtils.isNotEmpty(overdueTotalAmountA)&&Double.parseDouble(overdueTotalAmount) >= Double.parseDouble(overdueTotalAmountA)){
							createReason = createReason + OVERDUETOTALAMOUNT;
							flag = true;
						}
					
					if(StringUtils.isNotEmpty(aging)&&StringUtils.isNotEmpty(agingA)&&Double.parseDouble(aging) >= Double.parseDouble(agingA)){
						createReason = createReason + AGING;
						flag = true;
					}
					
					if( StringUtils.isNotEmpty(overdueTime)&&StringUtils.isNotEmpty(overdueTimeA)&&Double.parseDouble(overdueTime) >= Double.parseDouble(overdueTimeA) ){
						createReason = createReason + OVERDUETIME;
						flag = true;
					}
						if(flag){
							
							CustomerInfor customerInfor = customerInforService.findCustomerInforById(customerId);
							String customerManagerId = customerInfor.getUserId();
							AccountabilityRecord accountabilityRecordf= findAccountabilityRecordByIds(customerId,productId,customerManagerId);
							if(accountabilityRecordf == null){
							AccountabilityRecord accountabilityRecord = new AccountabilityRecord();
							accountabilityRecord.setProductId(productId);
							accountabilityRecord.setCreatedTime(Calendar.getInstance().getTime());
							accountabilityRecord.setModifiedTime(Calendar.getInstance().getTime());
							accountabilityRecord.setCustomerId(customerId);
							accountabilityRecord.setCustomerManagerId(customerManagerId);
							accountabilityRecord.setReconsideration("0");
							accountabilityRecord.setCreateWay(RiskType.system.name());
							accountabilityRecord.setAuditStatus(AuditStatusEnum.notaudit.name());
							accountabilityRecord.setCreateReason(createReason);
							commonDao.insertObject(accountabilityRecord);
							}
						}
					}
					
				
			 }
		 }
		
	 }

}
