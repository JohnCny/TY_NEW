package com.cardpay.pccredit.zrrtz.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.customer.model.CustomerParameter;
import com.cardpay.pccredit.intopieces.filter.IntoPiecesFilter;
import com.cardpay.pccredit.intopieces.model.IntoPieces;
import com.cardpay.pccredit.zrrtz.model.OutcomingData;
import com.cardpay.pccredit.zrrtz.model.ZrrtzFilter;
import com.cardpay.pccredit.zrrtz.model.IncomingData;
import com.wicresoft.util.annotation.Mapper;
@Mapper
public interface ZrrtzDao {

	List<IncomingData> findIntoPiecesList(ZrrtzFilter filter);

	int findIntoPiecesCountList(ZrrtzFilter filter);


	List<OutcomingData> findpiecesList(@Param("id")String id, @Param("fdate")String fdate, @Param("ldate")String ldate);

	List<IncomingData> finddate(@Param("id")String id);
	
	List<IncomingData> findIntoPiecesLists(ZrrtzFilter filter);

	int findIntoPiecesCountLists(ZrrtzFilter filter);
}
