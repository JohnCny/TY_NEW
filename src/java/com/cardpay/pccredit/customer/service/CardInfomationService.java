package com.cardpay.pccredit.customer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.customer.dao.CardInfomationDao;
import com.cardpay.pccredit.customer.web.CardInfomationFrom;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;

/**
 * 
 * 描述 ：卡片信息service
 * @author 张石树
 *
 * 2014-11-3 上午9:58:31
 */
@Service
public class CardInfomationService {
	
	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private CardInfomationDao cardInfomationDao;

	/**
     * 得到客户卡片信息
     * @param customerId
     * @return
     */
	public List<CardInfomationFrom> findCardsByCustomerId(String customerId) {
		
		return cardInfomationDao.findCardsByCustomerId(customerId);
	}

	/**
     * 根据id获取卡片信息
     * @param id
     * @return
     */
	public CardInfomationFrom findCardInfoId(String id) {
		
		return cardInfomationDao.findCardInfoId(id);
	}

	/**
	 * 根据客户id和卡号查询卡片信息
	 * @param customerId
	 * @param cardNumber
	 * @return
	 */
	public CardInfomationFrom findCardInfoByCustomerIdAndCardNumber(String customerId, String cardNumber) {
		return cardInfomationDao.findCardInfoByCustomerIdAndCardNumber(customerId, cardNumber);
	}

	
	
}
