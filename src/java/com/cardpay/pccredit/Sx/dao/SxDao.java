package com.cardpay.pccredit.Sx.dao;

import java.util.List;

import com.cardpay.pccredit.Sx.model.SxInputData;
import com.cardpay.pccredit.Sx.model.SxOutputData;
import com.cardpay.pccredit.customer.model.TyRepaySxForm;
import com.cardpay.pccredit.postLoan.filter.SxFilter;
import com.wicresoft.util.annotation.Mapper;
@Mapper
public interface SxDao {

	List<SxOutputData> findSxListByFilter(SxInputData filter);

	int findSxListCountByFilter(SxInputData filter);

	List<SxOutputData> findSxListByFilterNoPage(SxInputData filter);

	List<SxOutputData> findje(SxInputData filter);

	int findSxListCountByFilterje(SxInputData filter);

	List<SxOutputData> findteam();

	List<SxOutputData> finduser();


}
