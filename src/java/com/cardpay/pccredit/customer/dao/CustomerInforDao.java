package com.cardpay.pccredit.customer.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.Sx.model.SxOutputData;
import com.cardpay.pccredit.customer.filter.CustomerInfoLszFilter;
import com.cardpay.pccredit.customer.filter.CustomerInforFilter;
import com.cardpay.pccredit.customer.model.CUSTORMERINFOUPDATE;
import com.cardpay.pccredit.customer.model.CustomerCareersInformation;
import com.cardpay.pccredit.customer.model.CustomerFirsthendBase;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.model.CustomerInforWeb;
import com.cardpay.pccredit.customer.model.MaintenanceLog;
import com.cardpay.pccredit.customer.model.TyMibusidataForm;
import com.cardpay.pccredit.customer.model.TyRepayLsz;
import com.cardpay.pccredit.customer.model.TyRepayYehz;
import com.cardpay.pccredit.customer.model.TyRepayYehzVo;
import com.cardpay.pccredit.intopieces.filter.IntoPiecesFilter;
import com.cardpay.pccredit.system.model.Dict;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.util.annotation.Mapper;
/**
 * 
 * @author shaoming
 *  
 */
@Mapper
public interface CustomerInforDao {
	/**
	 * 获得国籍
	 * @return
	 */
	public List<Dict> findNationality();
	/**
	 * 获得证件类型
	 * @return
	 */
	public List<Dict> findCardType();
	/**
	 * 获得婚姻状况
	 * @return
	 */
    public List<Dict> findMaritalStatus();
    /** 
     * 获得住宅性质
     * @return
     */
    public List<Dict> findResidentialPropertie();
    
    /** 
     * 获得性别
     * @return
     */
    public List<Dict> findSex();
    
    /** 
     * 获得单位性质
     * @return
     */
    public List<Dict> findUnitPropertis();
    /**
     * 获得行业类型
     * @return
     */
    public List<Dict> findIndustryType();
    /**
     * 催收,维护方式
     * @return
     */
    public List<Dict> findCollectionMethod();
    /**
     * 职务
     * @return
     */
    public List<Dict> findPositio();
    /**
     * 职称
     * @return
     */
    public List<Dict> findTitle();
    /**
     * 通过证件类型代码得到证件类型名
     * @param typecode
     * @return
     */
    public String findTypeNameByTypeCode(String typecode);
    /**
     * 通过Name得到客户信息
     * @param name
     * @return
     */
    public List<CustomerInfor> findCustomerInforByName(@Param("userId") String userId,@Param("name") String name);
    /**
     * 修改客户信息的客户经理id和移交状态
     * @param id
     * @param customerManagerId
     * @return
     */
    public int updateCustomerInfor(@Param("id")String id,@Param("customerManagerId")String customerManagerId,@Param("status") String status);
    /**
     * 得到客户信息中的客户经理id
     * @param customerId
     * @return
     */
    public String findCustomerManagerIdById(@Param("customerId")String customerId);
    /**
     * 得到页面显示的客户信息
     * @param id
     * @return
     */
	public CustomerInforWeb findCustomerInforWebById(@Param("id") String id);
	
	/**
	 * 修改客户信息移交状态
	 * @param id
	 * @param status
	 */
	public int updateCustomerInforDivisionalStatus(@Param("id") String id,@Param("status") String status);
	/**
	 * 得到客户信息移交状态
	 * @param id
	 * @return
	 */
	public String getCustomerInforDivisionalStatus(@Param("id") String id);
	
	
	/**
	 * 进件申请提交时 客户维护资料做快照
	 * @param customerId
	 * @param applicationId
	 */
	public void cloneBasicCustomerInfo(@Param("customerId") String customerId, @Param("applicationId") String applicationId);
	/**
	 * 进件申请提交时 客户维护资料做快照
	 * @param customerId
	 * @param applicationId
	 */
	public void cloneCustomerCareersInf(@Param("customerId") String customerId, @Param("applicationId") String applicationId);
	/**
	 * 进件申请提交时 客户维护资料做快照
	 * @param customerId
	 * @param applicationId
	 */
	
	public List<CustomerInfor> findCustomerInforByFilterAndProductId1(CustomerInforFilter filter);
	public List<CustomerInfor> findCustomerInforByFilterAndProductId2(CustomerInforFilter filter);
	public void cloneCustomerMainManager(@Param("customerId") String customerId, @Param("applicationId") String applicationId);
	/**
	 * 进件申请提交时 客户维护资料做快照
	 * @param customerId
	 * @param applicationId
	 */
	public void cloneCustomerQuestionInfo(@Param("customerId") String customerId, @Param("applicationId") String applicationId);
	/**
	 * 进件申请提交时 客户维护资料做快照
	 * @param customerId
	 * @param applicationId
	 */
	public void cloneCustomerWorkshipInfo(@Param("customerId") String customerId, @Param("applicationId") String applicationId);
	/**
	 * 进件申请提交时 客户维护资料做快照
	 * @param customerId
	 * @param applicationId
	 */
	public void cloneDimensionalModelCredit(@Param("customerId") String customerId, @Param("applicationId") String applicationId);
	/**
	 * 进件申请提交时 客户维护资料做快照
	 * @param customerId
	 * @param applicationId
	 */
	public void cloneCustomerVideoAccessories(@Param("customerId") String customerId, @Param("applicationId") String applicationId);
	
	/**
	 * 根据进件申请Id查询客户维护资料快照
	 * @param applicationId
	 */
	public CustomerInfor findCloneCustomerInforByAppId(@Param("applicationId") String applicationId);
	
	/**
	 * 根据客户Id查询客户职业资料维护资料快照
	 * @param customerId
	 */
	public CustomerCareersInformation findcloneCustomerCareersByCustomerId(@Param("customerId") String customerId, @Param("applicationId") String applicationId);
	/**
	 * 得到该客户经理下的客户数量
	 * @param userId
	 * @return
	 */
	public int findCustomerInforCountByUserId(@Param("userId") String userId);
	
	public void deleteCloneBasicCustomerInfo(@Param("customerId") String customerId, @Param("applicationId") String applicationId);
	
	public void deleteCloneCustomerCareersInf(@Param("customerId") String customerId, @Param("applicationId") String applicationId);
	
	public void deleteCloneCustomerQuestionInfo(@Param("customerId") String customerId, @Param("applicationId") String applicationId);
	
	public void deleteCloneCustomerMainManager(@Param("customerId") String customerId, @Param("applicationId") String applicationId);
	
	public void deleteCloneCustomerWorkshipInfo(@Param("customerId") String customerId, @Param("applicationId") String applicationId);
	
	public void deleteCloneDimensionalModelCredit(@Param("customerId") String customerId, @Param("applicationId") String applicationId);
	
	public void deleteCloneCustomerVideoAccessories(@Param("customerId") String customerId, @Param("applicationId") String applicationId);
	
	public CustomerFirsthendBase findBankCustomerByNm(@Param("khmn") String khmn);
	
	public int insertCustomerBase(Map<String, Object> map);
	public void updateCustomerBase(Map<String, Object> map);
	public int insertCustomerBaseLocal(Map<String, Object> map);
	
	public int insertCustomerRygl(Map<String, Object> map);
	
	public int insertCustomerFamilyCy(Map<String, Object> map);
	public void updateCustomerFamilyCy(Map<String, Object> map);
	
	public int insertCustomerFamilyCc(Map<String, Object> map);
	public void updateCustomerFamilyCc(Map<String, Object> map);
	
	public int insertCustomerStudy(Map<String, Object> map);
	public void updateCustomerStudy(Map<String, Object> map);
	
	public int insertCustomerWork(Map<String, Object> map);
	public void updateCustomerWork(Map<String, Object> map);
	
	public int insertCustomerManage(Map<String, Object> map);
	public void updateCustomerManage(Map<String, Object> map);
	
	public int insertCustomerSafe(Map<String, Object> map);
	public void updateCustomerSafe(Map<String, Object> map);
	
	public List<CustomerInfor> findCustomerInforByFilterAndProductId(CustomerInforFilter filter);
	public int findCustomerInforCountByFilterAndProductId(CustomerInforFilter filter);
	
	public List<MaintenanceLog> findCustomerByFilter(CustomerInforFilter filter);
	public int findCustomerCountByFilter(CustomerInforFilter filter);
	
	
	public List<CustomerInfor> findCustomerOriginaList(CustomerInforFilter filter);
	public int findCustomerOriginaCountList(CustomerInforFilter filter);
	
	public int deleteRepayLSZ();
	public int insertRepayLSZ(Map<String, Object> map);
	
	public int updateRepayYEHZ(Map<String, Object> map);
	public int insertRepayYEHZ(Map<String, Object> map);
	public int insertRepayYEHZHistory(Map<String, Object> map);
	
	public int updateRepayTKMX(Map<String, Object> map);
	public int insertRepayTKMX(Map<String, Object> map);
	
	public int insertProduct(Map<String, Object> map);
	
	public int insertHmd(Map<String, Object> map);
	
	
	public List<TyRepayYehzVo>  findCustomerYexxList(IntoPiecesFilter filter);
	public int  findCustomerYexxCountList(IntoPiecesFilter filter);
	
	
	public List<TyRepayLsz>  findRepayLszList(CustomerInfoLszFilter filter);
	public int  findRepayLszCountList(CustomerInfoLszFilter filter);
	
	
	public int insertBatchTask(Map<String, Object> map);
	
	public int findCipersonfamilyCount(Map<String, Object> map);
	public int findCIPERSONBASINFOCount(Map<String, Object> map);
	/**
	 * 台帐查询
	 * @param map
	 * @return
	 */
	public int findMIBUSIDATACount(Map<String, Object> map);
	
	/**
	 * 借据查询
	 * @param param1
	 * @param param2
	 * @return
	 */
	public int findFCLOANINFOCount(Map<String, Object> map);
	
	public int findAppAuditLog(@Param("param1") String param1,
							   @Param("param2") String param2);
	
	public void updateAppAuditLog(@Param("param1") String param1,
								  @Param("param2") String param2,
								  @Param("user1")  String user1,
								  @Param("user2")  String user2,
								  @Param("user3")  String user3,
								  @Param("examineAmount")  String examineAmount,
								  @Param("lv")  String lv,
								  @Param("qx")  String qx,
								  @Param("sdUser") String sdUser,
								  @Param("hkfs") String hkfs,
								  @Param("beiZhu") String beiZhu);
	
	//测试 insertMain
	public int insertMain(Map<String, Object> map);
	public int insertFCLOANINFO(Map<String, Object> map);
	public int insertFCRESULTHIS(Map<String, Object> map);
	public int insertMIBUSIDATA(Map<String, Object> map);
	public int insertCipersonBasinfo(Map<String, Object> map);

	public int inserTyMIBUSIDATA(TyMibusidataForm tyMibusidataForm);
	public List<TyMibusidataForm> findmibusidata();
	public List<CustomerInforFilter> findUpdateCustormer(CustomerInforFilter filter);
	public List<CustomerInforFilter> findUpdateCustormer1(CustomerInforFilter filter);
	public int updateCustormerBytyId(CustomerFirsthendBase filter);
	
	public List<CustomerInforFilter> padfindUpdateCustormer(@Param("userId") String userId);
	
	
	

	
	
	public int insertCustormerUpdate(CUSTORMERINFOUPDATE CUSTORMERINFOUPDATE);
	public List<CUSTORMERINFOUPDATE> findCustormerUpdate(@Param("cardid") String cardid);
	public int findCustormerUpdateCount(@Param("cardid") String cardid);
	public void deletelastmibusidata();
	public void deletelastsx();
	public void insertsxOoutputDate(SxOutputData sxOutputData);
	public List<SxOutputData> findSxListByFilter();

	
}
