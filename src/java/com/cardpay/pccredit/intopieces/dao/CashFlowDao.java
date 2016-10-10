package com.cardpay.pccredit.intopieces.dao;

import com.cardpay.pccredit.intopieces.model.CashFlow;
import com.wicresoft.util.annotation.Mapper;

@Mapper
public interface CashFlowDao {
    int deleteByPrimaryKey(Long id);

    int insert(CashFlow record);

    int insertSelective(CashFlow record);

    CashFlow selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CashFlow record);

    int updateByPrimaryKey(CashFlow record);
}