package com.cardpay.pccredit.intopieces.dao;

import com.cardpay.pccredit.intopieces.model.CustomerApplicationGuarantor;
import com.wicresoft.util.annotation.Mapper;

@Mapper
public interface CustomerApplicationGuarantorDao {
    int deleteByPrimaryKey(Long id);

    int insert(CustomerApplicationGuarantor record);

    int insertSelective(CustomerApplicationGuarantor record);

    CustomerApplicationGuarantor selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerApplicationGuarantor record);

    int updateByPrimaryKey(CustomerApplicationGuarantor record);
}