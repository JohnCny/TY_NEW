package com.cardpay.pccredit.intopieces.dao;

import com.cardpay.pccredit.intopieces.model.CustomerApplicationContact;
import com.wicresoft.util.annotation.Mapper;

@Mapper
public interface CustomerApplicationContactDao {
    int deleteByPrimaryKey(Long id);

    int insert(CustomerApplicationContact record);

    int insertSelective(CustomerApplicationContact record);

    CustomerApplicationContact selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerApplicationContact record);

    int updateByPrimaryKey(CustomerApplicationContact record);
}