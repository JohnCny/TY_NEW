package com.cardpay.pccredit.customer.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.customer.filter.CustomerOverdueHistoryFilter;
import com.cardpay.pccredit.customer.model.CustomerOverdueHistory;
import com.wicresoft.util.annotation.Mapper;

/**
 * 
 * @author 季东晓
 *
 * 2014-11-13 下午3:36:39
 */
@Mapper
public interface CustomerOverdueHistoryDao {
	
  /**
  * 根据客户Id和产品Id查询逾期总次数
  * @param filter
  * @return
  */
    public int findCountById(CustomerOverdueHistoryFilter filter);
    
    /**
     * 根据客户Id和产品Id查询逾期总金额
     * @param filter
     * @return
     */
    public String findOverdueAmountById(CustomerOverdueHistoryFilter filter);
    
    /**
     * 半年内逾期三次的客户
     * @param productId
     * @return
     */
    public List<CustomerOverdueHistory> findHalfYearOverdueByProductId(@Param("productId")String productId);
    
    /**
     * 半年内逾期两次的客户
     * @param productId
     * @return
     */
    public List<CustomerOverdueHistory> findHalfYearOverdueTwoByProductId(@Param("productId")String productId);
    
    
    /**
     * 半年内逾期天数大于60天的
     * @param productId
     * @return
     */
    public List<CustomerOverdueHistory> findHalfYearOverdueDayByProductId(@Param("productId")String productId);
    
    
 
}
