package com.cardpay.pccredit.product.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.customer.model.CIPERSONBASINFOCOPY;
import com.cardpay.pccredit.customer.model.TyProductType;
import com.cardpay.pccredit.intopieces.model.AppManagerAuditLog;
import com.cardpay.pccredit.intopieces.model.AppManagerAuditLogForm;
import com.cardpay.pccredit.product.constant.ProductStatusEnum;
import com.cardpay.pccredit.product.dao.AccessoriesListDao;
import com.cardpay.pccredit.product.dao.AppendixDictDao;
import com.cardpay.pccredit.product.dao.ManagerProductsConfigurationDao;
import com.cardpay.pccredit.product.dao.ProductAccountabilityDao;
import com.cardpay.pccredit.product.dao.ProductCollectionRulesDao;
import com.cardpay.pccredit.product.dao.ProductDao;
import com.cardpay.pccredit.product.dao.ProductMaintainDao;
import com.cardpay.pccredit.product.dao.ProductMarketingRulesDao;
import com.cardpay.pccredit.product.dao.ProductRewardIncentiveDao;
import com.cardpay.pccredit.product.dao.comdao.ProductComDao;
import com.cardpay.pccredit.product.filter.ProductFilter;
import com.cardpay.pccredit.product.model.AccessoriesList;
import com.cardpay.pccredit.product.model.AppendixDict;
import com.cardpay.pccredit.product.model.ManagerProductsConfiguration;
import com.cardpay.pccredit.product.model.ProductAccountability;
import com.cardpay.pccredit.product.model.ProductAttribute;
import com.cardpay.pccredit.product.model.ProductCollectionRules;
import com.cardpay.pccredit.product.model.ProductMaintain;
import com.cardpay.pccredit.product.model.ProductMarketingRules;
import com.cardpay.pccredit.product.model.ProductRewardIncentive;
import com.cardpay.pccredit.product.model.ProductsAgencyAssociation;
import com.cardpay.pccredit.product.model.ScreeningResults;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.modules.privilege.model.Organization;
import com.wicresoft.jrad.modules.privilege.service.OrganizationService;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.web.RequestHelper;

/**
 * Description of ProductService
 * 
 * @author 王海东
 * @created on 2014-10-11
 * 
 * @version $Id:$
 */
@Service
public class ProductService {

	@Autowired
	private CommonDao commonDao;

	@Autowired
	private ProductDao productDao;

	@Autowired
	private ProductComDao productComDao;

	@Autowired
	private AccessoriesListDao accessoriesListDao;

	@Autowired
	private AppendixDictDao appendixDictDao;

	@Autowired
	private ProductMarketingRulesDao productMarketingRulesDao;

	@Autowired
	private ManagerProductsConfigurationDao managerProductsConfigurationDao;

	@Autowired
	private ProductCollectionRulesDao productCollectionRulesDao;

	@Autowired
	private ProductRewardIncentiveDao productRewardIncentiveDao;

	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private ProductMaintainDao productMaintainDao; // 产品维护规则

	@Autowired
	private ProductAccountabilityDao productAccountabilityDao; // 产品问责规则

	/**
	 * 通过productId查找 筛选规则。匹配客户。
	 * 
	 * @param productId
	 * @return
	 */
	public void insertScreeningResults(String productId, String cresteUser) {
		ProductAttribute productAttribute = commonDao.findObjectById(ProductAttribute.class, productId);
		if (productAttribute.getStatus().equals(ProductStatusEnum.Published.toString())) {
			List<String> customerIdlist = productComDao.findCustomerIdByProductId(productId);
			// 插入筛选的Id
			for (String customerId : customerIdlist) {
				ScreeningResults sr = new ScreeningResults();
				sr.setCreatedTime(new Date());
				sr.setCreatedBy(cresteUser);
				sr.setCustomerId(customerId);
				sr.setProductId(productId);
				commonDao.insertObject(sr);
			}
			productDao.updateProductStatus(productId, ProductStatusEnum.Screen.toString());
		}

	}

	// 创建产品营销
	public void insertMarketingPlan() {

	}

	// 插入产品
	public String insertProduct(ProductAttribute productAttribute) {
		productAttribute.setCreatedTime(Calendar.getInstance().getTime());
		productAttribute.setModifiedTime(Calendar.getInstance().getTime());
		productAttribute.setStatus(ProductStatusEnum.Unpublished.toString());
		commonDao.insertObject(productAttribute);

		return productAttribute.getId();
	}

	// 根据过滤条件查询产品（进件新增）
	public QueryResult<ProductAttribute> findProductsByFilter(ProductFilter filter) {
		//return commonDao.findObjectsByFilter(ProductAttribute.class, filter);
		filter.setStatus("Published");
		List<ProductAttribute> productAttribute = productDao.findProductsByFilter(filter);
		int size = productDao.findProductsCountByFilter(filter);
		QueryResult<ProductAttribute> qs = new QueryResult<ProductAttribute>(size, productAttribute);
		return qs;
	}
	
	// 根据过滤条件查询产品（产品发布）
    public QueryResult<ProductAttribute> findProductsByNameFilter(ProductFilter filter) {
		//return commonDao.findObjectsByFilter(ProductAttribute.class, filter);
    	List<ProductAttribute> productAttribute = productDao.findProductsByFilter(filter);
		int size = productDao.findProductsCountByFilter(filter);
		QueryResult<ProductAttribute> qs = new QueryResult<ProductAttribute>(size, productAttribute);
		return qs;
    }
    
    //产品信息
    public QueryResult<ProductAttribute> findProductsFilter(ProductFilter filter) {
  		return commonDao.findObjectsByFilter(ProductAttribute.class, filter);
      }

	/*
	 * 根据filter查询已发布产品
	 */
	public QueryResult<ProductAttribute> findPublishedProductsByFilter(ProductFilter filter) {
		List<ProductAttribute> productAttribute = productDao.findPublishedProductsByFilter(filter);
		int size = productDao.findPublishedProductsCountByFilter(filter);
		QueryResult<ProductAttribute> qs = new QueryResult<ProductAttribute>(size, productAttribute);
		return qs;
	}
	
	// 查询所有产品附件清单
	public List<AppendixDict> findAllAppendix() {
		return appendixDictDao.findAllAppendix();
	}

	// 查询所有产品催收规则
	public List<ProductCollectionRules> findAllProductCollectionRules() {
		return productCollectionRulesDao.findAllProductCollectionRules();
	}

	// 根据产品id查询产品
	public ProductAttribute findProductAttributeById(String productId) {
		return commonDao.findObjectById(ProductAttribute.class, productId);

	}

	// 根据产品id查询已经发布产品
	public ProductAttribute findPublishedProductAttributeByProductId(String productId) {
		return productDao.findPublishedProductAttributeByProductId(productId);
	}

	// 插入产品附件清单
	public int insertAccessoriesList(AccessoriesList accessoriesList) {
		return commonDao.insertObject(accessoriesList);
	}

	// 插入产品催收规则
	public void insertProductCollectionRules(List<ProductCollectionRules> productCollectionRules) {
		for (ProductCollectionRules productCollectionRules2 : productCollectionRules) {
			productCollectionRules2.setCreatedTime(new Date());
			commonDao.insertObject(productCollectionRules2);
		}
	}

	// 插入产品营销规则
	public String insertProductMarketingRules(ProductMarketingRules productMarketingRules) {
		// productMarketingRules.setMarketingMethod(CustomerMarketingEndResultEnum.marketing.toString());
		commonDao.insertObject(productMarketingRules);
		return productMarketingRules.getProductId();

	}

	// 插入客户经理-产品-参数配置
	public String insertManagerProductsConfiguration(ManagerProductsConfiguration managerProductsConfiguration) {
		commonDao.insertObject(managerProductsConfiguration);
		return managerProductsConfiguration.getProductId();

	}
	// 插入客户经理-产品-参数配置多条
	public String updateManagerProductsConfigurationDT(HttpServletRequest request) {
			Calendar calendar = Calendar.getInstance();
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			String userId = user.getId();
			//经理产品参数配置
			String index = request.getParameter("totalnum");
			String productId = request.getParameter("id");
			int indexnum =0;
			if(index != "")
			{
				indexnum = Integer.parseInt(index)	;
				
			}
			for(int i=1;i<=indexnum;i++){
				ManagerProductsConfiguration managerProductsConfiguration= new ManagerProductsConfiguration();
				String customerManagerLevel = request.getParameter("customerManagerLevel"+i);
				String managerProductsConfigurationid = request.getParameter("managerProductsConfigurationid"+i);
				String creditLine = request.getParameter("creditLine"+i);
				if(creditLine !=null && creditLine !=""){
					Double creditLineDouble = Double.parseDouble(creditLine) * 100;
					String creditLineValue = creditLineDouble.toString();
					managerProductsConfiguration.setCreditLine(creditLineValue);
				}
				String marginExtractInfo = request.getParameter("marginExtractInfo"+i);
				String riskToleranceInformation = request.getParameter("riskToleranceInformation"+i);
				managerProductsConfiguration.setId(managerProductsConfigurationid);
				managerProductsConfiguration.setCustomerManagerLevel(customerManagerLevel);
				
				managerProductsConfiguration.setMarginExtractInfo(marginExtractInfo);
				managerProductsConfiguration.setRiskToleranceInformation(riskToleranceInformation);
				managerProductsConfiguration.setProductId(productId);
				managerProductsConfiguration.setModifiedBy(userId);
				managerProductsConfiguration.setModifiedTime(calendar.getTime());
				commonDao.updateObject(managerProductsConfiguration);
			}
			
			return productId;

		}

	// 插入产品奖励激励参数表
	public String insertProductRewardIncentive(ProductRewardIncentive productRewardIncentive) {
		commonDao.insertObject(productRewardIncentive);
		return productRewardIncentive.getProductId();

	}

	// 插入产品维护规则表
	public String insertProductMaintain(ProductMaintain productMaintain) {
		commonDao.insertObject(productMaintain);
		return productMaintain.getProductId();

	}

	// 插入产品-问责规则表
	public String insertProductAccountability(ProductAccountability productAccountability) {
		commonDao.insertObject(productAccountability);
		return productAccountability.getProductId();
	}

	// 插入产品机构中间表
	public int insertProductsAgencyAssociation(ProductsAgencyAssociation productsAgencyAssociation) {
		return commonDao.insertObject(productsAgencyAssociation);
	}

	// 根据产品id查询产品机构表
	public List<ProductsAgencyAssociation> findInstitutionByProductId(String productId) {
		return productDao.findInstitutionByProductId(productId);
	}

	// 根据productId 修改产品状态为已发布
	public int updateProductPublished(String productId) {
		return productDao.updateProductStatus(productId, ProductStatusEnum.Published.toString());

	}

	// 根据productId 修改产品编号
	public int updateProductSeqnoByProductId(String productId, String seqno) {
		return productDao.updateProductSeqnoByProductId(productId, seqno);
	}

	// 根据productId 修改产品状态为审核通过
	public int updateProductApproved(String productId) {
		return productDao.updateProductStatus(productId, ProductStatusEnum.Approved.toString());
	}

	// 根据产品ID查询附件清单Code
	public List<AccessoriesList> findAppendixTypeCodeByProductId(String productId) {
		return accessoriesListDao.findAppendixTypeCodeByProductId(productId);

	}

	// 根据产品Id查询产品营销规则
	public ProductMarketingRules findProductMarketingRulesByProductId(String productId) {
		return productMarketingRulesDao.findProductMarketingRulesByProductId(productId);

	}

	// 根据产品Id查询客户经理-产品-参数配置
	public List<ManagerProductsConfiguration> findManagerProductsConfigurationByProductId(String productId) {
		return managerProductsConfigurationDao.findManagerProductsConfigurationByProductId(productId);

	}

	// 根据产品productId查询产品催收规则
	public List<ProductCollectionRules> findProductCollectionRulesByProductId(String productId) {
		return productCollectionRulesDao.findProductCollectionRulesByProductId(productId);

	}

	// 根据产品productId查询产品奖励激励参数表
	public ProductRewardIncentive findProductRewardIncentiveByProductId(String productId) {
		return productRewardIncentiveDao.findProductRewardIncentiveByProductId(productId);
	}

	// 根据产品productId查询附件清单
	public List<AppendixDict> findAppendixByProductId(String productId) {
		return accessoriesListDao.findAppendixByProductId(productId);

	}

	// 根据productId查询产品维护规则
	public ProductMaintain findProductMaintainByProductId(String productId) {
		return productMaintainDao.findProductMaintainByProductId(productId);
	}

	// 根据productId查询产品-问责规则
	public ProductAccountability findProductAccountabilityByProductId(String productId) {
		return productAccountabilityDao.findProductAccountabilityByProductId(productId);
	}

	public List<ProductsAgencyAssociation> findProductsAgencyAssociationByOrganizationId(String organizationId) {
		return productDao.findProductsAgencyAssociationByOrganizationId(organizationId);

	}

	// 根据产品经理id获得产品属性
	public List<ProductAttribute> findProductAttributeByuserId(String userId) {
		Organization organization = organizationService.findOrgByUserId(userId);
		String organizationId = organization.getId();
		List<ProductsAgencyAssociation> productsAgencyAssociation = productDao.findProductsAgencyAssociationByOrganizationId(organizationId);
		List<ProductAttribute> list = new ArrayList<ProductAttribute>();
		for (ProductsAgencyAssociation productsAgencyAssociation2 : productsAgencyAssociation) {
			String proId = productsAgencyAssociation2.getProductId();
			ProductAttribute pa = findPublishedProductAttributeByProductId(proId);
			if (pa != null) {
				list.add(pa);
			}
		}
		return list;

	}

	// 修改产品属性
	public int updateProductAttribute(ProductAttribute productAttribute) {
		productAttribute.setModifiedTime(Calendar.getInstance().getTime());
		return commonDao.updateObject(productAttribute);
	}
	
	// 修改客户原始信息
	public int updateCustomerBase(CIPERSONBASINFOCOPY copy) {
		return commonDao.updateObject(copy);
	}

	// 根据productId删除产品附件清单
	public void deleteAccessoriesListByProductId(String productId) {
		accessoriesListDao.deleteAppendixByProductId(productId);
	}

	// 根据产品Id删除营销规则
	public void deleteProductMarketingRulesByProductId(String productId) {
		productMarketingRulesDao.deleteProductMarketingRulesByProductId(productId);
	}

	// 根据产品Id删除客户经理-产品-参数配置
	public void deleteManagerProductsConfigurationByProductId(String productId) {
		managerProductsConfigurationDao.deleteManagerProductsConfigurationByProductId(productId);
	}

	// 根据产品Id删除产品催收规则
	public void deleteProductCollectionRulesByProductId(String productId) {
		productCollectionRulesDao.deleteProductCollectionRulesByProductId(productId);
	}

	// 根据产品Id删除产品奖励激励参数表
	public void deleteProductRewardIncentiveByProductId(String productId) {
		productRewardIncentiveDao.deleteProductRewardIncentiveByProductId(productId);
	}

	// 根据产品Id删除产品维护规则
	public void deleteProductMaintainByProductId(String productId) {
		productMaintainDao.deleteProductMaintainByProductId(productId);
	}

	// 根据产品Id删除产品问责规则
	public void deleteProductAccountabilityByProductId(String productId) {
		productAccountabilityDao.deleteProductAccountabilityByProductId(productId);
	}

	// 插入产品营销规则 & 催收规则 &经理层级参数配置 &产品奖励激励参数 合集
	public String insertRules(ProductMarketingRules productMarketingRules, ProductRewardIncentive productRewardIncentive, ProductAccountability productAccountability, ProductMaintain productMaintain,HttpServletRequest request) {
		
		Calendar calendar = Calendar.getInstance();
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String userId = user.getId();
		insertProductMarketingRules(productMarketingRules);
		
		insertProductRewardIncentive(productRewardIncentive);
		insertProductAccountability(productAccountability);
		insertProductMaintain(productMaintain);
		//经理产品参数配置
		String index = request.getParameter("totalnum");
		String productId = RequestHelper.getStringValue(request, "productId");
		int indexnum =0;
		if(index != "")
		{
			indexnum = Integer.parseInt(index)	;
			
		}
		for(int i=1;i<=indexnum;i++){
			ManagerProductsConfiguration managerProductsConfiguration= new ManagerProductsConfiguration();
			String customerManagerLevel = request.getParameter("customerManagerLevel"+i);
			String creditLine = request.getParameter("creditLine"+i);
			String marginExtractInfo = request.getParameter("marginExtractInfo"+i);
			String riskToleranceInformation = request.getParameter("riskToleranceInformation"+i);
			managerProductsConfiguration.setCustomerManagerLevel(customerManagerLevel);
			managerProductsConfiguration.setCreditLine(creditLine);
			managerProductsConfiguration.setMarginExtractInfo(marginExtractInfo);
			managerProductsConfiguration.setRiskToleranceInformation(riskToleranceInformation);
			managerProductsConfiguration.setProductId(productId);
			managerProductsConfiguration.setCreatedBy(userId);
			managerProductsConfiguration.setCreatedTime(calendar.getTime());
			managerProductsConfiguration.setModifiedBy(userId);
			managerProductsConfiguration.setModifiedTime(calendar.getTime());
			insertManagerProductsConfiguration(managerProductsConfiguration);
		}
		
		return productMarketingRules.getProductId();
	}
	/**
	 * 得到pageSize*(currentPage-1)到pageSize*currentPage行数据 
	 * @param currentPage 
	 * @param pageSize
	 * @return
	 */
	public List<ProductAttribute> findProducts(int currentPage,int pageSize,String userId){
		currentPage = currentPage - 1;
		if(currentPage<0){
			currentPage = 0;
		}
		if(StringUtils.isNotEmpty(userId)){
			Organization organization = organizationService.findOrgByUserId(userId);
			String organizationId = organization.getId();
			List<ProductsAgencyAssociation> productsAgencyAssociation = productDao.findProductsAgencyAssociationByOrganizationIdPage(organizationId,currentPage,pageSize);
			List<ProductAttribute> list = new ArrayList<ProductAttribute>();
			for (ProductsAgencyAssociation productsAgencyAssociation2 : productsAgencyAssociation) {
				String proId = productsAgencyAssociation2.getProductId();
				ProductAttribute pa = findPublishedProductAttributeByProductId(proId);
				if (pa != null) {
					list.add(pa);
				}
			}
			return list;
		}else{
			return productDao.findProducts(currentPage,pageSize);
		}
	}
	/**
	 * 已发布,筛选产品数量
	 * @return
	 */
	public int findProductsCount(String userId){
		if(StringUtils.isNotEmpty(userId)){
			Organization organization = organizationService.findOrgByUserId(userId);
			String organizationId = organization.getId();
			return productDao.findProductsAgencyAssociationCountByOrganizationId(organizationId);
		}else{
			return productDao.findProductsCount();
		}
	}
	public List<TyProductType> getProductType(){
		String sql = "select product_code,product_name from ty_product_type";
		return commonDao.queryBySql(TyProductType.class, sql, null);
	}
	
	
	public int findProductCountByName(String prodName,String productId){
		return productDao.findProductCountByName(prodName,productId);
	}
	
	
	public List<AppManagerAuditLog> findAppManagerAuditLog(String appId,String auditType){
//		String sql = "select s.display_name as USERID_1 ,"
//				+ "d.display_name as USERID_2,"
//				+ "g.display_name as USERID_3 from t_app_manager_audit_log t,sys_user s ,sys_user d,sys_user g "
//				+ "where s.id = t.user_id_1 and d.id = t.user_id_2 and g.id = t.user_id_3 and "
//				+ " AUDIT_TYPE ='1' and APPLICATION_ID ='"+appId+"'";
//		return commonDao.queryBySql(AppManagerAuditLog.class, sql, null);
		return accessoriesListDao.findAppManagerAuditLog(appId,auditType);
	}
	
	public List<AppManagerAuditLog> findAppManagerAuditLogByAppId(String appId){
		/*String sql = "select * from t_app_manager_audit_log t where APPLICATION_ID ='"+appId+"'";
		return commonDao.queryBySql(AppManagerAuditLog.class, sql, null);*/
		return accessoriesListDao.findAppManagerAuditById(appId);
	}
}
