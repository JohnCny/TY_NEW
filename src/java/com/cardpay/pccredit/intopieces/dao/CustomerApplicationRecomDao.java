package com.cardpay.pccredit.intopieces.dao;

import com.cardpay.pccredit.intopieces.model.CustomerApplicationRecom;
import com.wicresoft.util.annotation.Mapper;

@Mapper
public interface CustomerApplicationRecomDao {
    int deleteByPrimaryKey(Long id);

    int insert(CustomerApplicationRecom record);

    int insertSelective(CustomerApplicationRecom record);

    CustomerApplicationRecom selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerApplicationRecom record);

    int updateByPrimaryKey(CustomerApplicationRecom record);
}