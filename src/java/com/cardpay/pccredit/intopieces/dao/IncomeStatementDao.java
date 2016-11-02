package com.cardpay.pccredit.intopieces.dao;

import com.cardpay.pccredit.intopieces.model.IncomeStatement;
import com.wicresoft.util.annotation.Mapper;

@Mapper
public interface IncomeStatementDao {
    int deleteByPrimaryKey(Long id);

    int insert(IncomeStatement record);

    int insertSelective(IncomeStatement record);

    IncomeStatement selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(IncomeStatement record);

    int updateByPrimaryKey(IncomeStatement record);
}