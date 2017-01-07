package com.cardpay.pccredit.jnpad.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.jnpad.model.JnIpadXDModel;
import com.wicresoft.util.annotation.Mapper;

@Mapper
public interface JnpadSelectDao {
	public List<JnIpadXDModel> selectUserXDGZ(@Param("userId") String userId);
}
