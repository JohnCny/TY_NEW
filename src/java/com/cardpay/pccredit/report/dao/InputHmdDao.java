package com.cardpay.pccredit.report.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.report.model.CustomerHmd;
import com.wicresoft.util.annotation.Mapper;

@Mapper
public interface InputHmdDao {
	public void insetHmd(CustomerHmd hmd);

	List<CustomerHmd> queryAll(CustomerHmd filter);
	int querySize(CustomerHmd filter);

	public CustomerHmd queryByCardId(String cardId);
}
