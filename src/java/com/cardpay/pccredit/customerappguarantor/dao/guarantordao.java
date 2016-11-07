package com.cardpay.pccredit.customerappguarantor.dao;

import java.util.List;

import com.cardpay.pccredit.Sx.model.SxInputData;
import com.cardpay.pccredit.Sx.model.SxOutputData;
import com.cardpay.pccredit.customer.model.TyRepaySxForm;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationGuarantor;
import com.cardpay.pccredit.postLoan.filter.SxFilter;
import com.wicresoft.util.annotation.Mapper;
@Mapper
public interface guarantordao {

	List<CustomerApplicationGuarantor> findguarantor();
	
}
