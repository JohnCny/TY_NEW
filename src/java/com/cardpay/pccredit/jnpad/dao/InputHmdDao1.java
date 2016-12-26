package com.cardpay.pccredit.jnpad.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.report.model.CustomerHmd;
import com.wicresoft.util.annotation.Mapper;

@Mapper
public interface InputHmdDao1 {
	public CustomerHmd selectByCardId(@Param("cardId") String cardId);
	public int deleteByCardId(@Param("cardId") String cardId);
}
