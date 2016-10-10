package com.cardpay.pccredit.intopieces.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.intopieces.filter.AddIntoPiecesFilter;
import com.cardpay.pccredit.intopieces.filter.IntoPiecesFilter;
import com.cardpay.pccredit.intopieces.model.Dcbzlr;
import com.cardpay.pccredit.intopieces.model.Dcddpz;
import com.cardpay.pccredit.intopieces.model.Dclrjb;
import com.cardpay.pccredit.intopieces.model.Dclsfx;
import com.cardpay.pccredit.intopieces.model.Dcsczt;
import com.cardpay.pccredit.intopieces.model.DhApplnAttachmentBatch;
import com.cardpay.pccredit.intopieces.model.DhApplnAttachmentDetail;
import com.cardpay.pccredit.intopieces.model.DhApplnAttachmentList;
import com.cardpay.pccredit.intopieces.model.Dzjbzk;
import com.cardpay.pccredit.intopieces.model.Dzjy;
import com.cardpay.pccredit.intopieces.model.Dzjyzt;
import com.cardpay.pccredit.intopieces.model.LocalImage;
import com.cardpay.pccredit.intopieces.model.QzApplnAttachmentBatch;
import com.cardpay.pccredit.intopieces.model.QzApplnAttachmentDetail;
import com.cardpay.pccredit.intopieces.model.QzApplnAttachmentList;
import com.cardpay.pccredit.intopieces.web.LocalImageForm;
import com.wicresoft.util.annotation.Mapper;

@Mapper
public interface LocalImageDao {
	public List<LocalImageForm> findByProductAndCustomer(AddIntoPiecesFilter filter);
	public List<LocalImage> findAllByProductAndCustomer(@Param("productId") String productId,@Param("customerId") String customerId);
	public LocalImage findById(@Param("id") String id);
	public int findCountByProductAndCustomer(AddIntoPiecesFilter filter);
	public List<LocalImageForm> findByApplication(AddIntoPiecesFilter filter);
	public int findCountByApplication(AddIntoPiecesFilter filter);
	public void deleteByProductIdAndCustomerId(@Param("productId") String productId,@Param("customerId") String customerId);
	public void updateCustomerInfoStatus(@Param("appId") String appId);
	
	public Dzjy findJy(@Param("customerId") String customerId,@Param("productId") String productId);
	public Dzjbzk  findDzjbzk(@Param("customerId") String customerId,@Param("productId") String productId);
	public Dzjyzt  findDzjyzt(@Param("customerId") String customerId,@Param("productId") String productId);
	public Dcsczt findDcsczt(@Param("customerId") String customerId,@Param("productId") String productId);
	public Dcddpz findDcddpz(@Param("customerId") String customerId,@Param("productId") String productId);
	public Dclrjb findDclrjb(@Param("customerId") String customerId,@Param("productId") String productId);
	public Dcbzlr findDcbzlr(@Param("customerId") String customerId,@Param("productId") String productId);
	
	public Dclsfx findDclsfx(@Param("customerId") String customerId,@Param("productId") String productId);
	
	
	public QzApplnAttachmentList findAttachmentListByAppId(@Param("applicationId")String applicationId);
	public DhApplnAttachmentList findDhAttachmentListByAppId(@Param("applicationId")String applicationId);
	public List<QzApplnAttachmentBatch> findAttachmentBatchByAppId(@Param("applicationId")String applicationId);
	public List<DhApplnAttachmentBatch> findDhAttachmentBatchByAppId(@Param("applicationId")String applicationId);
	
	
	public List<QzApplnAttachmentDetail> findDetailByFilter(IntoPiecesFilter filter);
	public int findDetailCountByFilter(IntoPiecesFilter filter);
	
	public List<DhApplnAttachmentDetail> findDhDetailByFilter(IntoPiecesFilter filter);
	public int findDhDetailCountByFilter(IntoPiecesFilter filter);
	
	public List<QzApplnAttachmentDetail> findQzApplnDetail(@Param("page") int currentPage,@Param("limit") int limit,@Param("batchId") String batchId);
	
	public List<DhApplnAttachmentDetail> findDhApplnDetail(@Param("page") int currentPage,@Param("limit") int limit,@Param("batchId") String batchId);
	public int findDhApplnDetailCount(@Param("batchId") String batchId);
	
	public int findQzApplnDetailCount(@Param("batchId") String batchId);
	
	public List<DhApplnAttachmentDetail> findDhApplnDetailPage(@Param("page") int currentPage,@Param("limit") int limit,@Param("appId") String appId);
	public int findDhApplnDetailPageCount(@Param("appId") String appId);
	
}