package com.cardpay.pccredit.rongyaoka.dao;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.rongyaoka.model.rymodel;
import com.wicresoft.util.annotation.Mapper;

@Mapper
public interface ryDao {
void insertRyCs(rymodel model);
rymodel selectTime(@Param("id")String id);
rymodel selectTime1(@Param("id")String id);
rymodel  selectryFs(@Param("id")String id);
rymodel  selectryCs(@Param("id")String id);
void insertRyFs(rymodel model);
}
