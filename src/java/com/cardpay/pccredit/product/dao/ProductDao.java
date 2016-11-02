package com.cardpay.pccredit.product.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.customer.web.CustomerInforForm;
import com.cardpay.pccredit.product.filter.ProductFilter;
import com.cardpay.pccredit.product.model.FilterDict;
import com.cardpay.pccredit.product.model.ProductAttribute;
import com.cardpay.pccredit.product.model.ProductsAgencyAssociation;
import com.wicresoft.util.annotation.Mapper;

/**
 * Description of ProductDao
 * 
 * @author 王海东
 * @created on 2014-10-11
 * 
 * @version $Id:$
 */
@Mapper
public interface ProductDao {
	
	//查询在产品期限内的产品
	List<ProductAttribute>  findProductsByFilter(ProductFilter filter);
	
	//查询在产品期限内的产品数量
	public int findProductsCountByFilter(ProductFilter filter);
	
	// 根据Filter查询已发布产品
	public List<ProductAttribute> findPublishedProductsByFilter(ProductFilter filter);

	/*
	 * 根据filter查询产品数量
	 */
	public int findPublishedProductsCountByFilter(ProductFilter filter);

	// 查找所有的规则
	public List<FilterDict> findFilterDict(String productId);

	// 筛选出客户
	public List<String> findCustomerByDict(@Param("sql") String sql);

	// 根据productId 查询SCREENING_RESULTS（筛选结果）表中customer
	public List<CustomerInforForm> findScreeningResultsCustomerByProductId(@Param("productId") String productId);

	// 更新产品发布状态
	public int updateProductStatus(@Param("productId") String productId, @Param("status") String status);

	// 通过机构产品中间表 查询出产品 参数为 机构id
	public List<ProductsAgencyAssociation> findProductsAgencyAssociationByOrganizationId(@Param("organizationId") String organizationId);

	// 根据productId只查询已发布产品
	public ProductAttribute findPublishedProductAttributeByProductId(@Param("productId") String productId);

	// 根据productId 修改产品编号
	public int updateProductSeqnoByProductId(@Param("productId") String productId, @Param("seqno") String seqno);

	// 根据产品id查询产品机构表
	public List<ProductsAgencyAssociation> findInstitutionByProductId(@Param("productId") String productId);
	
	public List<ProductAttribute> findProducts(@Param("page") int currentPage,@Param("limit") int limit);
	
	public List<com.cardpay.pccredit.ipad.model.ProductAttribute> findProductsPad(@Param("page") int currentPage,@Param("limit") int limit);
	
	public int findProductsCount();
	
	public List<ProductsAgencyAssociation> findProductsAgencyAssociationByOrganizationIdPage(@Param("organizationId") String organizationId,@Param("page") int currentPage,@Param("limit") int limit);
	
	public int findProductsAgencyAssociationCountByOrganizationId(@Param("organizationId") String organizationId);
	
	public int findProductCountByName(@Param("productName") String productName,@Param("productId") String productId);
}
