package com.cardpay.pccredit.riskControl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.customer.dao.AmountAdjustmentDao;
import com.cardpay.pccredit.customer.dao.CardInformationDao;
import com.cardpay.pccredit.customer.dao.CustomerOverdueDao;
import com.cardpay.pccredit.customer.filter.CardInformationFilter;
import com.cardpay.pccredit.customer.filter.CustomerInforFilter;
import com.cardpay.pccredit.customer.filter.CustomerOverdueFilter;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.intopieces.dao.CustomerApplicationInfoDao;
import com.cardpay.pccredit.intopieces.dao.CustomerApplicationIntopieceWaitDao;
import com.cardpay.pccredit.intopieces.filter.CustomerApplicationInfoFilter;
import com.cardpay.pccredit.intopieces.web.CustomerApplicationIntopieceWaitForm;
import com.cardpay.pccredit.notification.constant.NotificationEnum;
import com.cardpay.pccredit.notification.service.NotificationService;
import com.cardpay.pccredit.riskControl.constant.RiskAttributeEnum;
import com.cardpay.pccredit.riskControl.constant.RiskCreateTypeEnum;
import com.cardpay.pccredit.riskControl.constant.RiskCustomerStatusEnum;
import com.cardpay.pccredit.riskControl.dao.AccountalilityDao;
import com.cardpay.pccredit.riskControl.filter.RiskCustomerFilter;
import com.cardpay.pccredit.riskControl.model.RiskAttribute;
import com.cardpay.pccredit.riskControl.model.RiskCustomer;
import com.wicresoft.jrad.base.database.model.QueryResult;

/**
 * @author chenzhifang
 *
 * 2014-11-3下午3:22:08
 */
@Service
public class RiskControlSchedulesService {
	
	@Autowired
	private CustomerApplicationInfoDao customerApplicationInfoDao;
	
	@Autowired
	private CardInformationDao cardInformationDao;
	
	@Autowired
	private CustomerOverdueDao customerOverdueDao;
	
	@Autowired
	private RiskAttributeService riskAttributeService;
	
	@Autowired
	private CustomerInforService customerInforService;
	
	@Autowired
	private RiskCustomerService riskCustomerService;
	
	@Autowired
	private RiskReviewProcessService riskReviewProcessService;
	
	@Autowired
	private AccountalilityDao accountalilityDao;
	
	@Autowired
	private AmountAdjustmentDao amountAdjustmentDao;
	
	@Autowired
	private CustomerApplicationIntopieceWaitDao customerApplicationIntopieceWaitDao;
	
	@Autowired
	private NotificationService notificationService;
	
	// 最大天数
	@Value("${riskReviewProcess.max.day}")
	private String riskReviewProcessMaxDay;
	
	/**
	 * 把机构主管拒绝后超过指定天数的记录改为可直接上报卡中心
	 * @return   
	 */
	public void updateStatusToUnreportedCardcenter(){
		//System.out.println(riskReviewProcessMaxDay+"  == rrtt");
		riskReviewProcessService.updateStatusToUnreportedCardcenter(riskReviewProcessMaxDay);
		accountalilityDao.autoAccountalilityEnd(riskReviewProcessMaxDay);
		//调额申请到的单据超过时间未审批释放掉
		amountAdjustmentDao.autoAfterApplyTimeReleaseApply(riskReviewProcessMaxDay);
		//进件申请审批单据超过时间未审批释放掉
		customerApplicationIntopieceWaitDao.autoAfterApplyTimeReleaseApply(riskReviewProcessMaxDay);
		
		List<CustomerApplicationIntopieceWaitForm> intopieceWaitForms = customerApplicationIntopieceWaitDao.findNotEqualsActualAndFinalAmount();
		for(CustomerApplicationIntopieceWaitForm waitForm : intopieceWaitForms){
			String content = "客户[" + waitForm.getChineseName() + "]对应产品 [" + waitForm.getProductName() + "]的进件,审批额度为["+ 
					(Double.parseDouble(waitForm.getFinalApproval())/100) +"]，但实际额度为["+ 
					(Double.parseDouble(waitForm.getActualQuote())/100) +"],两者不同请确认.";
			notificationService.insertNotification(NotificationEnum.qita, waitForm.getUserId(), "进件审批额度和实际额度不同", content, null);
		}
	}
	
	/**
	 * 自动处理客户信息并加入风险名单
	 * @return   
	 */
	public void addRiskCustomerschedu(){
		// 查询风险属性
		List<RiskAttribute> attributeList = riskAttributeService.findRiskOnlineAttributeList();
				
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
					for(RiskAttribute riskAttribute : attributeList){
						// 处理客户信息
						processRisk(customerInfor, riskAttribute);
					}
				}
				// 设置查询的页码
				filter.setPage(filter.getPage() + 1);
				qs = customerInforService.findCustomerInforByFilter(filter);
			}
		}catch(Exception e){
			throw new RuntimeException(e.getMessage());
		}
	}
	
	/**
	 *  处理客户信息
	 * @param customerInfor
	 * @param riskAttribute 
	 * @return   
	 */
	public void processRisk(CustomerInfor customerInfor, RiskAttribute riskAttribute){
		String customerId = customerInfor.getId();
		// 检查多次申请
		if (RiskAttributeEnum.REPEATEDLY.toString().equals(riskAttribute.getRiskAttribute())) {
			CustomerApplicationInfoFilter filter = new CustomerApplicationInfoFilter();
			filter.setCustomerId(customerId);
			// 查询客户申请的卡数
			int count = customerApplicationInfoDao.findCountByFilter(filter);
			// 检查并保存
			checkAndsaveOnlineRiskCustomer(count, customerInfor, riskAttribute);
		// 多张卡
		}else if (RiskAttributeEnum.MANYCARD.toString().equals(riskAttribute.getRiskAttribute())) {
			CardInformationFilter filter = new CardInformationFilter();
			filter.setCustomerId(customerId);
			// 
			int count = cardInformationDao.findCountByFilter(filter);
			// 检查并保存
			checkAndsaveOnlineRiskCustomer(count, customerInfor, riskAttribute);
		// 多次逾期	
		}else if (RiskAttributeEnum.MANYTIMESEXPIRY.toString().equals(riskAttribute.getRiskAttribute())) {
			CustomerOverdueFilter filter = new CustomerOverdueFilter();
			filter.setCustomerId(customerId);
			// 
			int count = customerOverdueDao.findCountByFilter(filter);
			// 检查并保存
			checkAndsaveOnlineRiskCustomer(count, customerInfor, riskAttribute);
		}
	}
	
	/*
	 * 检查并保存
	 * @param filter
	 * @return boolean
	 */
	public void checkAndsaveOnlineRiskCustomer(int count, CustomerInfor customerInfor, RiskAttribute riskAttribute){
		if(isMoreThanNumber(count, riskAttribute)){
			// 保存
			saveOnlineRiskCustomer(customerInfor, riskAttribute);
		}
	}
	
	/*
	 * 是否超过指定的数值
	 * @param filter
	 * @return boolean
	 */
	public boolean isMoreThanNumber(int count, RiskAttribute riskAttribute){
		boolean flag= false;
		try{
			flag = (count > Integer.valueOf(riskAttribute.getValue())) ? true : false;
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 保存
	 * @param customerInfor
	 * @param riskAttribute
	 * @return  
	 */
	public void saveOnlineRiskCustomer(CustomerInfor customerInfor, RiskAttribute riskAttribute){
		RiskCustomerFilter filter = new RiskCustomerFilter();
		filter.setCustomerId(customerInfor.getId());
		filter.setRiskAttribute(riskAttribute.getRiskAttribute());
		// 判断是否在风险名单中
		if(!riskCustomerService.isInBlacklist(filter)){
			RiskCustomer riskCustomer = new RiskCustomer();
			riskCustomer.setCustomerId(customerInfor.getId());
			riskCustomer.setRiskLevel(riskAttribute.getRiskLevel());
			riskCustomer.setRefuseReason(riskAttribute.getRiskDes());
			// 风险类型
			riskCustomer.setRiskCreateType(RiskCreateTypeEnum.system.toString());
			riskCustomer.setStatus(RiskCustomerStatusEnum.CONFIRMED_CARDCENTER.toString());
			riskCustomer.setRiskAttribute(riskAttribute.getRiskAttribute());
			riskCustomer.setReportedIdManager(customerInfor.getUserId());
			// 保存
			riskCustomerService.insertRiskCustomer(riskCustomer);
		}
	}
}
