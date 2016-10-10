package com.cardpay.pccredit.customer.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.customer.web.CardInfomationFrom;
import com.wicresoft.util.annotation.Mapper;

/**
 * 
 * 描述 ：卡片信息Dao
 * @author 张石树
 *
 * 2014-11-4 上午11:17:18
 */
@Mapper
public interface CardInfomationDao {
    /**
     * 得到客户卡片信息根据产品id
     * @param productId
     * @return
     */
    public List<CardInfomationFrom> findCardsByProductId(@Param("productId")String productId);

    
    /**
     * 得到客户卡片信息
     * @param customerId
     * @return
     */
    public List<CardInfomationFrom> findCardsByCustomerId(@Param("customerId")String customerId);
    /**
     * 根据id获取卡片信息
     * @param id
     * @return
     */
	public CardInfomationFrom findCardInfoId(@Param("id") String id);

	/**
	 * 根据客户id和卡号查询卡片信息
	 * @param customerId
	 * @param cardNumber
	 * @return
	 */
	public CardInfomationFrom findCardInfoByCustomerIdAndCardNumber(@Param("customerId")String customerId, @Param("cardNumber")String cardNumber);
	
}
