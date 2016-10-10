package com.cardpay.pccredit.intopieces.dao;

import com.cardpay.pccredit.intopieces.model.AdditionalCashFlow;
import com.wicresoft.util.annotation.Mapper;

@Mapper
public interface AdditionalCashFlowDao {
    int deleteByPrimaryKey(Long id);

    int insert(AdditionalCashFlow record);

    int insertSelective(AdditionalCashFlow record);

    AdditionalCashFlow selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AdditionalCashFlow record);

    int updateByPrimaryKey(AdditionalCashFlow record);
}