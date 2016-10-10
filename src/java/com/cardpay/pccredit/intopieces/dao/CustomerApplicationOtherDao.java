package com.cardpay.pccredit.intopieces.dao;

import com.cardpay.pccredit.intopieces.model.CustomerApplicationOther;
import com.wicresoft.util.annotation.Mapper;

@Mapper
public interface CustomerApplicationOtherDao {
    int deleteByPrimaryKey(Long id);

    int insert(CustomerApplicationOther record);

    int insertSelective(CustomerApplicationOther record);

    CustomerApplicationOther selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerApplicationOther record);

    int updateByPrimaryKey(CustomerApplicationOther record);
}