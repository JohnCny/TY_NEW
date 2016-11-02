package com.cardpay.pccredit.manager.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.manager.dao.AccountManagerRetrainingDao;
import com.cardpay.pccredit.manager.filter.AccountManagerRetrainingFilter;
import com.cardpay.pccredit.manager.model.AccountManagerRetraining;
import com.cardpay.pccredit.manager.model.Retraining;
import com.cardpay.pccredit.notification.constant.NotificationConstant;
import com.cardpay.pccredit.notification.constant.NotificationEnum;
import com.cardpay.pccredit.notification.model.NotificationMessage;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.modules.privilege.model.User;
import com.wicresoft.util.spring.Beans;

/**
 * 客户经理再培训计划服务类
 * @author chenzhifang
 *
 * 2014-11-11下午3:27:41
 */
@Service
public class AccountManagerRetrainingService {
	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private AccountManagerRetrainingDao accountManagerRetrainingDao;
	
   /**
	 * 通过在培训id查询客户经理
	 * @param retrainId
	 * @return
	 */
	public List<User> findUserListByRetrainId(String retrainId){
		return accountManagerRetrainingDao.findUserListByRetrainId(retrainId);
	}
	
	
	/**
	 * 通过机构id查询客户经理
	 * @param orgId 机构id
	 * @return
	 */
	public List<User> findUserListByOrgId(String orgId){
		return accountManagerRetrainingDao.findUserListByOrgId(orgId);
	}
	
	/**
	 * 过滤查询
	 * @param filter
	 * @return
	 */
	public QueryResult<AccountManagerRetraining> findAccountManagerRetrainingByFilter(AccountManagerRetrainingFilter filter) {
		List<AccountManagerRetraining> list = accountManagerRetrainingDao.findAccountManagerRetrainingsByFilter(filter);
		int size = accountManagerRetrainingDao.findAccountManagerRetrainingsCountByFilter(filter);
		QueryResult<AccountManagerRetraining> qs = new QueryResult<AccountManagerRetraining>(size, list);
		return qs;
	}
	
	/**
	 * 更新客户经理再培训计划
	 * @param riskCustomer
	 * @return
	 */
	public int updateAccountManagerRetraining(AccountManagerRetraining AccountManagerRetraining) {
		AccountManagerRetraining.setModifiedTime(Calendar.getInstance().getTime());
		
		return commonDao.updateObject(AccountManagerRetraining);
	}
	
	/**
	 * 插入客户经理再培训计划
	 * @param riskCustomer
	 * @return
	 */
	public String insertAccountManagerRetraining(AccountManagerRetraining accountManagerRetraining) {
		if(accountManagerRetraining.getCreatedTime() != null){
			accountManagerRetraining.setCreatedTime(Calendar.getInstance().getTime());
		}
		if(accountManagerRetraining.getModifiedTime() != null){
			accountManagerRetraining.setModifiedTime(Calendar.getInstance().getTime());
		}
		commonDao.insertObject(accountManagerRetraining);
		return accountManagerRetraining.getId();
	}

	/**
	 * 删除客户经理再培训计划
	 * @param riskCustomerId
	 * @return
	 */
	public int deleteAccountManagerRetraining(String accountManagerRetrainingId) {
		return commonDao.deleteObject(AccountManagerRetraining.class, accountManagerRetrainingId);
	}

	/**
	 * 通过ID查找客户经理再培训计划
	 * @param filter
	 * @return
	 */
	public AccountManagerRetraining findAccountManagerRetrainingById(String accountManagerRetrainingId) {
		return commonDao.findObjectById(AccountManagerRetraining.class, accountManagerRetrainingId);
	}
	
	public boolean saveManagers(String retrainId, String deleteManagerIds, String newAddManagerIds,String currentUserId){
		boolean flag = false;
		try{
			// 判断是否删除了客户经理
			if(StringUtils.isNotEmpty(retrainId)){
				String[] ids = null;
				if(StringUtils.isNotEmpty(deleteManagerIds)){
					ids = deleteManagerIds.split(",");
					for(int i = 0; i< ids.length; i++){
						if(StringUtils.isNotEmpty(ids[i])){
							accountManagerRetrainingDao.deleteAccountManagerRetraining(retrainId, ids[i]);
						}
					}
				}
				// 判断是否新增了客户经理
				if(StringUtils.isNotEmpty(newAddManagerIds)){
					ids = newAddManagerIds.split(",");
					for(int i = 0; i< ids.length; i++){
						if(StringUtils.isNotEmpty(ids[i])){
							AccountManagerRetraining managerRetraining = new AccountManagerRetraining();
							managerRetraining.setRetrainId(retrainId);
							managerRetraining.setCustomerManagerId(ids[i]);
							insertAccountManagerRetraining(managerRetraining);
							
							//同时插入培训通知信息 20150928
							Retraining retraining = commonDao.findObjectById(Retraining.class, retrainId);
							NotificationMessage  msg = new NotificationMessage();
							msg.setUserId(ids[i]);
							msg.setNoticeType(NotificationEnum.peixun.toString());
							msg.setNoticeContent(retraining.getTrainingContent());
							msg.setIsCheck(NotificationConstant.no_read);
							msg.setNoticeTitle(retraining.getTrainingObjective());
							msg.setCreatedTime(new Date());
							msg.setCreatedBy(currentUserId);
							commonDao.insertObject(msg);
						}
					}
				}
				flag = true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}
}
