package com.cardpay.pccredit.customer.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.customer.model.BusinessTackling;
import com.cardpay.pccredit.jnpad.model.CustomerInfo;
import com.wicresoft.util.annotation.Mapper;

/**
 * 
 * 描述 ：业务核查信息
 * @author 周文杰
 *
 * 2016年10月24日15:07:46
 */
@Mapper
public interface IBusinessTacklingDao {

	List<CustomerInfo>  queryById(@Param("cardId")String cardId);

	List<BusinessTackling> queryByIdCard(@Param("idcard")String idcard);


}
