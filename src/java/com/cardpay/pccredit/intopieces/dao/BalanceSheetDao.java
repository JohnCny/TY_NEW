package com.cardpay.pccredit.intopieces.dao;

import com.cardpay.pccredit.intopieces.model.BalanceSheet;
import com.wicresoft.util.annotation.Mapper;

@Mapper
public interface BalanceSheetDao {
    int deleteByPrimaryKey(Long id);

    int insert(BalanceSheet record);

    int insertSelective(BalanceSheet record);

    BalanceSheet selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BalanceSheet record);

    int updateByPrimaryKey(BalanceSheet record);
}