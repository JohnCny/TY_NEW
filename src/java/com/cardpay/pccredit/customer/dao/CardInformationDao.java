package com.cardpay.pccredit.customer.dao;

import com.cardpay.pccredit.customer.filter.CardInformationFilter;
import com.wicresoft.util.annotation.Mapper;

/**
 * @author chenzhifang
 *
 * 2014-11-3下午4:05:40
 */
@Mapper
public interface CardInformationDao {

	/*
     * 查询符合条件的记录数
     */
    public int findCountByFilter(CardInformationFilter filter);
    
}
