package com.cardpay.pccredit.rongyaoka.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.intopieces.filter.IntoPiecesFilter;
import com.cardpay.pccredit.intopieces.model.AppManagerAuditLog;
import com.cardpay.pccredit.intopieces.model.IntoPieces;
import com.cardpay.pccredit.intopieces.web.CustomerApplicationIntopieceWaitForm;
import com.wicresoft.util.annotation.Mapper;

@Mapper
public interface RyIntoPiecesComDao {

	public void updatecapnextnodeid(@Param("nodeName")String nodeName, @Param("applicationId")String applicationId);



	public List<CustomerApplicationIntopieceWaitForm> findCustomerApplicationIntopieceDecisionForm(
			@Param("userId")String userId, @Param("cardId")String cardId, @Param("chineseName")String chineseName,
			@Param("nextNodeName")String nextNodeName);

	public int findCustomerApplicationIntopieceDecisionCountForm(@Param("userId")String userId, @Param("cardId")String cardId, @Param("chineseName")String chineseName,
			@Param("nextNodeName")String nextNodeName);



	public void updateCsjl(AppManagerAuditLog appManagerAuditLog);



	public void updateCSZT(@Param("userId")String userId, @Param("times")Date times, @Param("money")String money,
			@Param("applicationId")String applicationId);



	public List<IntoPieces> findintoPiecesByFilters(IntoPiecesFilter filter);



	public int findintoPiecesByFilterCount(IntoPiecesFilter filter);

}
