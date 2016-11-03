package com.cardpay.pccredit.jnpad.dao;

import com.cardpay.pccredit.jnpad.model.CustomerApprais;
import com.wicresoft.util.annotation.Mapper;
@Mapper
public interface JnpadCustomerAppraisDao {
	/**
	 * 添加客户评估信息
	 * @param ca
	 * @return
	 */
	int addCustomerApprais(CustomerApprais ca);
	/**
	 * 查看该客户是否已经评估
	 * @param ca
	 * @return
	 */
	int selectCustomerAppraisCount(CustomerApprais ca);
	
	
	
	CustomerApprais selectCustomerApprais(CustomerApprais ca);
	
	/**
	 * 修改评估信息
	 * @param ca
	 * @return
	 */
	int updateCustomerApprais(CustomerApprais ca);
	
	/**
	 * 查看指定客户的评估信息
	 * @param c
	 * @return
	 */
	CustomerApprais selectAllCustomerApprais(CustomerApprais c);
}
