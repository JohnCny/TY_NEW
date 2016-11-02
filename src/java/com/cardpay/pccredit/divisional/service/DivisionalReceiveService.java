package com.cardpay.pccredit.divisional.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.customer.constant.CustomerInforDStatusEnum;
import com.cardpay.pccredit.customer.dao.CustomerInforDao;
import com.cardpay.pccredit.customer.service.CardInfomationService;
import com.cardpay.pccredit.customer.web.CardInfomationFrom;
import com.cardpay.pccredit.divisional.dao.DivisionalDao;
import com.cardpay.pccredit.divisional.dao.comdao.DivisionalCommDao;
import com.cardpay.pccredit.divisional.filter.DivisionalFilter;
import com.cardpay.pccredit.divisional.model.DivisionalWeb;
import com.cardpay.pccredit.riskControl.constant.RiskType;
import com.cardpay.pccredit.riskControl.service.AccountabilityService;
import com.wicresoft.jrad.base.database.model.QueryResult;

/**
 * 
 * @author 姚绍明
 *
 * 
 */
@Service
public class DivisionalReceiveService {
	
	@Autowired
	private DivisionalCommDao divisionalcommDao;
	
	@Autowired
	private DivisionalDao divisionalDao;
	
	@Autowired
	private CustomerInforDao customerinforDao;
	
	@Autowired
	private AccountabilityService accountabilityService;
	
	@Autowired
	private CardInfomationService cardInfomationService;
	
	/**
	 * 获得分案申请信息 
	 * @param filter
	 * @return
	 */
	public QueryResult<DivisionalWeb> findDivisionalByCustomerManager(DivisionalFilter filter){
		return divisionalcommDao.findDivisionalByCustomerManager(filter);
	}
	/**
	 * 接受客户,修改客户基本信息表客户经理id与分案申请表的分案进度
	 * @param customerId
	 * @param userId
	 * @param id
	 * @param result 分案进度
	 * @param process 分案结果
	 * @return
	 */
	public boolean updateCustomerInforAndDivisional(String customerId,String userId,CustomerInforDStatusEnum status,String id,String result,String process){
		int i = customerinforDao.updateCustomerInfor(customerId, userId,status.toString());
		if(i>0){
			int j = divisionalDao.updateDivisionalResultAndProcess(id, result, process);
			if(j>0){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	/**
	 * 拒绝客户,修改分案申请表分案结果为reject
	 * @param id
	 * @param result
	 * @return
	 */
	public boolean updateDivisionalResult(String id,String result){
		int i = divisionalDao.updateDivisionalResultAndProcess(id,result,null);
		if(i>0){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 得到用户名
	 * @param id
	 * @return
	 */
	public String findUserNameByUserId(String id){
		return divisionalDao.getUserNameByUserId(id);
	}
}
