package com.cardpay.pccredit.intopieces.dao;

import com.cardpay.pccredit.intopieces.model.CustomerApplicationCom;
import com.wicresoft.util.annotation.Mapper;

@Mapper
public interface CustomerApplicationComDao {
    int deleteByPrimaryKey(Long id);

    int insert(CustomerApplicationCom record);

    int insertSelective(CustomerApplicationCom record);

    CustomerApplicationCom selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerApplicationCom record);

    int updateByPrimaryKey(CustomerApplicationCom record);
}