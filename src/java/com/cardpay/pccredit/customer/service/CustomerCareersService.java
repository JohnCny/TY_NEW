package com.cardpay.pccredit.customer.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.customer.dao.CustomerCareersInformationDao;
import com.cardpay.pccredit.customer.model.CustomerCareersInformation;
import com.cardpay.pccredit.customer.model.CustomerCareersWeb;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
/**
 * 
 * @author shaoming
 *
 * 2014年10月30日   下午4:46:18
 */
@Service
public class CustomerCareersService {
	
	@Autowired
	private CustomerCareersInformationDao customerCareersInformationDao;
	
	@Autowired
	private CommonDao commonDao;
	/**
	 * 得到客户职业信息
	 * @param id
	 * @return
	 */
	public CustomerCareersWeb findCustomerCareersByCustomerId(String id){
		return customerCareersInformationDao.getCustomerCareersByCustomerId(id);
	}
	/**
	 * 保存客户职业信息
	 * @param cutomerCareersInformation
	 * @return
	 */
	public boolean updateCustomerCareersInformation(CustomerCareersInformation cutomerCareersInformation){
		String customerId = cutomerCareersInformation.getCustomerId();
		/*通过客户id查询该客户的职业信息*/
		CustomerCareersInformation customerCareersInfor = customerCareersInformationDao.getCustomerCareersInformationByCustomerId(customerId);
		/*不存在该客户的职业信息,则执行插入操作*/
		if(customerCareersInfor==null){
			cutomerCareersInformation.setCreatedTime(new Date());
			cutomerCareersInformation.setModifiedBy(null);
			int i = commonDao.insertObject(cutomerCareersInformation);
			return i>0?true:false;
		}else{
			 /*存在该客户的职业信息,则执行更新操作*/
			cutomerCareersInformation.setModifiedTime(new Date());
			cutomerCareersInformation.setCreatedBy(null);
			int i = commonDao.updateObject(cutomerCareersInformation);
			return i>0?true:false;
		}
	}
	
	/**
	 * 根据进件申请id获取快照信息
	 * @param applicationId
	 * @return
	 */
	public CustomerCareersWeb findCustomerCareersByAppId(String applicationId) {
		return customerCareersInformationDao.findCustomerCareersByAppId(applicationId);
	}
}
