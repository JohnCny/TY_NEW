package com.cardpay.pccredit.intopieces.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.intopieces.filter.IntoPiecesFilter;
import com.cardpay.pccredit.intopieces.model.IntoPieces;
import com.wicresoft.util.annotation.Mapper;

@Mapper
public interface IntoPiecesDao {
	public int checkValue(@Param("userId")String userId,@Param("valueType")String valueType);
	
	/**
	 * 查询进件信息
	 * @param filter
	 * @return
	 */
	public List<IntoPieces> findIntoPiecesList(IntoPiecesFilter filter);
	
	/**
	 * 查询进件数量
	 * @return
	 */
	public int findIntoPiecesCountList(IntoPiecesFilter filter);
	
	public void updateCustomerApplicationInfo(IntoPieces  pieces);
	public void updateCustomerApplicationInfoJn(IntoPieces  pieces);
}
