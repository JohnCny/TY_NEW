package com.cardpay.pccredit.customer.dao;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.customer.model.CustomerCareersInformation;
import com.cardpay.pccredit.customer.model.CustomerCareersWeb;
import com.wicresoft.util.annotation.Mapper;
@Mapper
public interface CustomerCareersInformationDao {
    int deleteByPrimaryKey(Long id);

    int insert(CustomerCareersInformation record);

    int insertSelective(CustomerCareersInformation record);

    CustomerCareersInformation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerCareersInformation record);

    int updateByPrimaryKey(CustomerCareersInformation record);
    /**
     * 得到客户职业页面信息
     * @param id
     * @return
     */
    public CustomerCareersWeb getCustomerCareersByCustomerId(@Param("id") String id);
    /**
     * 得到客户职业信息
     * @param id
     * @return
     */
    public CustomerCareersInformation getCustomerCareersInformationByCustomerId(@Param("id") String id);
    /**
	 * 根据进件申请id获取快照信息
	 * @param applicationId
	 * @return
	 */
    public CustomerCareersWeb findCustomerCareersByAppId(@Param("applicationId") String applicationId);
}