package com.cardpay.pccredit.customer.service;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cardpay.pccredit.common.Dictionary;
import com.cardpay.pccredit.common.UploadFileTool;
import com.cardpay.pccredit.customer.constant.CustomerInforConstant;
import com.cardpay.pccredit.customer.constant.CustomerInforDStatusEnum;
import com.cardpay.pccredit.customer.constant.WfProcessInfoType;
import com.cardpay.pccredit.customer.dao.CustomerInforDao;
import com.cardpay.pccredit.customer.dao.comdao.CustomerInforCommDao;
import com.cardpay.pccredit.customer.filter.CustomerInfoLszFilter;
import com.cardpay.pccredit.customer.filter.CustomerInforFilter;
import com.cardpay.pccredit.customer.filter.VideoAccessoriesFilter;
import com.cardpay.pccredit.customer.model.CIPERSONBASINFO;
import com.cardpay.pccredit.customer.model.CIPERSONBASINFOCOPY;
import com.cardpay.pccredit.customer.model.CustomerCareersInformation;
import com.cardpay.pccredit.customer.model.CustomerFirsthendBase;
import com.cardpay.pccredit.customer.model.CustomerFirsthendBaseLocal;
import com.cardpay.pccredit.customer.model.CustomerFirsthendFamilyCc;
import com.cardpay.pccredit.customer.model.CustomerFirsthendFamilyCy;
import com.cardpay.pccredit.customer.model.CustomerFirsthendManage;
import com.cardpay.pccredit.customer.model.CustomerFirsthendRygl;
import com.cardpay.pccredit.customer.model.CustomerFirsthendSafe;
import com.cardpay.pccredit.customer.model.CustomerFirsthendStudy;
import com.cardpay.pccredit.customer.model.CustomerFirsthendWork;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.model.CustomerInforWeb;
import com.cardpay.pccredit.customer.model.MaintenanceLog;
import com.cardpay.pccredit.customer.model.TyProductType;
import com.cardpay.pccredit.customer.model.TyRepayLsz;
import com.cardpay.pccredit.customer.model.TyRepayYehzVo;
import com.cardpay.pccredit.datapri.service.DataAccessSqlService;
import com.cardpay.pccredit.intopieces.constant.Constant;
import com.cardpay.pccredit.intopieces.constant.IntoPiecesException;
import com.cardpay.pccredit.intopieces.filter.IntoPiecesFilter;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationContact;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationGuarantor;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationInfo;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationOther;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationProcess;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationRecom;
import com.cardpay.pccredit.intopieces.model.IntoPieces;
import com.cardpay.pccredit.intopieces.model.VideoAccessories;
import com.cardpay.pccredit.intopieces.service.IntoPiecesService;
import com.cardpay.pccredit.ipad.model.ProductAttribute;
import com.cardpay.pccredit.manager.service.CustomerApplicationInfoSynchScheduleService;
import com.cardpay.pccredit.riskControl.model.RiskCustomer;
import com.cardpay.pccredit.system.constants.NodeAuditTypeEnum;
import com.cardpay.pccredit.system.constants.YesNoEnum;
import com.cardpay.pccredit.system.model.Dict;
import com.cardpay.pccredit.system.model.NodeAudit;
import com.cardpay.pccredit.system.model.NodeControl;
import com.cardpay.pccredit.system.model.SystemUser;
import com.cardpay.pccredit.system.service.NodeAuditService;
import com.cardpay.pccredit.tools.CardFtpUtils;
import com.cardpay.pccredit.tools.DataFileConf;
import com.cardpay.pccredit.tools.ImportBankDataFileTools;
import com.cardpay.pccredit.tools.SPTxt;
import com.cardpay.pccredit.tools.UpdateCustomerTool;
import com.cardpay.workflow.models.WfProcessInfo;
import com.cardpay.workflow.models.WfStatusInfo;
import com.cardpay.workflow.models.WfStatusResult;
import com.cardpay.workflow.service.ProcessService;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.id.IDGenerator;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.QueryResult;

/**
 * 
 * @author shaoming
 *
 */
@Service
public class CustomerInforService {
	public Logger log = Logger.getLogger(UpdateCustomerTool.class);
	@Autowired
	private DataAccessSqlService dataAccessSqlService;
	
	@Autowired
	private ReadWholeAndIncrementService readWholeAndIncrementService;

	@Autowired
	private CommonDao commonDao;

	@Autowired
	private CustomerInforDao customerInforDao;
	
	@Autowired
	private CustomerInforCommDao customerinforcommDao;
	
	@Autowired
	private NodeAuditService nodeAuditService;
	
	@Autowired
	private ProcessService processService;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;  
	//客户原始信息
    //todo:文件换成济南的
	private String[] fileTxt = {"kkh_grxx.txt","kkh_grjtcy.txt","kkh_grjtcc.txt","kkh_grscjy.txt","kkh_grxxll.txt","kkh_grgzll.txt","kkh_grrbxx.txt","cxd_dkcpmc.txt","kkh_hmdgl.txt","cxd_rygl.txt"};
	//流水账、余额汇总表、借据表
    //todo:文件换成济南的
	private String[] fileTxtRepay ={"kdk_yehz.txt","kdk_lsz.txt","kdk_tkmx.txt","kdk_jh.txt"};
	/**
	 * 得到该客户经理下的客户数量
	 * @param userId
	 * @return
	 */
	public int findCustomerInforCountByUserId(String userId){
		return customerInforDao.findCustomerInforCountByUserId(userId);
	}
	/**
	 * 查询name
	 * 
	 */
	
	public List<CustomerInfor> findCustomerInforByName(String userId,String name){
		return customerInforDao.findCustomerInforByName(userId,name);
	}
	
	/**
	 * 根据证件号码查询
	 * 
	 */
	public CustomerInfor findCustomerInforByCardId(String cardId){
		return customerinforcommDao.findCustomerInforByCardId(cardId);
	}
	/**
	 * 插入数据
	 * @param customerinfo
	 * @return
	 */
	public String insertCustomerInfor(CustomerInfor customerinfor) {
		String id = IDGenerator.generateID();
		customerinfor.setId(id);
		customerinfor.setCreatedTime(new Date());
		commonDao.insertObject(customerinfor);
		return customerinfor.getId();
	}
	/**
	 * 过滤查询
	 * @param filter
	 * @return
	 */
	public QueryResult<CustomerInfor> findCustomerInforByFilter(CustomerInforFilter filter) {
		/*filter.setSqlString(dataAccessSqlService.getSqlByResTbl(filter.getRequest(), ResourceTableEnum.KEHU));*/
		List<CustomerInfor> ls = customerInforDao.findCustomerOriginaList(filter);
		int size = customerInforDao.findCustomerOriginaCountList(filter);
		QueryResult<CustomerInfor> qr = new QueryResult<CustomerInfor>(size,ls);
		return qr;
	}
	
	public int findCustomerOriginaCountList(CustomerInforFilter filter){
		return customerInforDao.findCustomerOriginaCountList(filter);
	}
	
	//查询未办理过该产品的客户
	public QueryResult<CustomerInfor> findCustomerInforByFilterAndProductId(CustomerInforFilter filter) {
		List<CustomerInfor> ls = customerInforDao.findCustomerInforByFilterAndProductId(filter);
		int size = customerInforDao.findCustomerInforCountByFilterAndProductId(filter);
		QueryResult<CustomerInfor> qr = new QueryResult<CustomerInfor>(size,ls);
		return qr;
	}
	
	/**
	 * 过滤查询影像资料
	 * @param filter
	 * @return
	 */
	public QueryResult<VideoAccessories> findCustomerVideoAccessoriesByFilter(VideoAccessoriesFilter filter) {
		return commonDao.findObjectsByFilter(VideoAccessories.class, filter);
	}
	
	
	
	/**
	 * 按id查找相应的客户基本信息
	 * @param customerInforId
	 * @return
	 */
	public CustomerInfor findCustomerInforById(String customerInforId){
		return commonDao.findObjectById(CustomerInfor.class, customerInforId);
	}
	
	/**
	 * 按经理Id查找相应的客户基本信息
	 * @param customerInforId
	 * @return
	 */
	public  List<CustomerInfor> findCustomerByManagerId(String managerId){
		
		List<CustomerInfor> customerInfors = customerinforcommDao.findCustomerByManagerId(managerId);
           return customerInfors;
	}
	
	/**
	 * 修改客户信息
	 * @param customerInfor
	 * @return
	 */
	public int updateCustomerInfor(CustomerInfor customerInfor) {
		customerInfor.setModifiedTime(Calendar.getInstance().getTime());
		return commonDao.updateObject(customerInfor);
	}
	/**
	 * 删除所属id的客户基本信息
	 * @param customerinforId
	 * @return
	 */
	public int deleteCustomerInfor(String customerinforId){
		return commonDao.deleteObject(CustomerInfor.class, customerinforId);
	}
    //TODO 以下数条获得单一属性方法建议可重构，保留也可
	/**
	 * 获取国籍
	 * @return
	 */
	public List<Dict> findNationality(){
		List<Dict> nationalities = customerInforDao.findNationality();
		return nationalities;
	}
	/**
	 * 获取证件类型
	 * @return
	 */
	public List<Dict> findCardType(){
		List<Dict> cardtypes = customerInforDao.findCardType();
		return cardtypes;
	}
	public String findTypeNameByTypeCode(String typecode){
		return customerInforDao.findTypeNameByTypeCode(typecode);
	}
	/**
	 * 获取婚姻状况
	 * @return
	 */
	public List<Dict> findMaritalStatus(){
		List<Dict> maritalstatuses = customerInforDao.findMaritalStatus();
		return maritalstatuses;
	}
	/**
	 * 获取住宅类型
	 * @return
	 */
	public List<Dict> findResidentialPropertie(){
		List<Dict> residentialproperties = customerInforDao.findResidentialPropertie();
		return residentialproperties;
	}
	
	/**
	 * 获取客户账户信息中的在我行开户情况
	 * @return
	 */
	public List<Dict> findOaccountMybankList(){
		List<Dict> oaccountMybanks = customerinforcommDao.findOaccountMybankList();
		return oaccountMybanks;
	}
	
	/**
	 * 获取客户账户持卡情况
	 * @return
	 */
	public List<Dict> findCreditCardList(){
		List<Dict> oaccountMybanks = customerinforcommDao.findCreditCardList();
		return oaccountMybanks;
	}
	
	/**
	 * 获取客户在我行发工资情况
	 * @return
	 */
	public List<Dict> findPayMybankList(){
		List<Dict> oaccountMybanks = customerinforcommDao.findPayMybankList();
		return oaccountMybanks;
	}
	
	/**
	 * 获取教育程度
	 * @return
	 */
	public List<Dict> findDegreeeducationList(){
		List<Dict> degreeeducationList = customerinforcommDao.findDegreeeducationList();
		return degreeeducationList;
	}
	
	/**
	 * 获取扣款方式
	 * @return
	 */
	public List<Dict> debitWayList(){
		List<Dict> debitWayList = customerinforcommDao.findDegreeeducationList();
		return debitWayList;
	}
	
	/**
	 * 获取性别
	 * @return
	 */
	public List<Dict> findSex(){
		List<Dict> sexs = customerInforDao.findSex();
		return sexs;
	}
	/**
	 * 获取职务
	 * @return
	 */
	public List<Dict> findPositio(){
		return customerInforDao.findPositio();
	}
	/**
	 * 获取职称
	 * @return
	 */
	public List<Dict> findTitle(){
		return customerInforDao.findTitle();
	}
	/**
	 * 获取单位性质
	 * @return
	 */
	public List<Dict> findUnitPropertis(){
		List<Dict> unitPropertis = customerInforDao.findUnitPropertis();
		return unitPropertis;
	}
	/**
	 * 获得行业类型
	 * @return
	 */
	public List<Dict> findIndustryType(){
		List<Dict> industryTypes = customerInforDao.findIndustryType();
		return industryTypes;
	}
	/**
	 * 获得催收方式
	 * @return
	 */
	public List<Dict> findCollectionMethod(){
		List<Dict> collectionMethods = customerInforDao.findCollectionMethod();
		return collectionMethods;
	}
	/**
	 * 获取国籍、证件类型、婚姻状况、住宅类型字段
	 * @return
	 */
	public HashMap<String,List<Dict>> findDict(){
		HashMap<String,List<Dict>> dicts = new HashMap<String,List<Dict>>();
		List<Dict> nationalities = findNationality();
		List<Dict> cardtypes = findCardType();
		List<Dict> maritalstatuses = findMaritalStatus();
		List<Dict> residentialproperties = findResidentialPropertie();
		dicts.put("nationality", nationalities);
		dicts.put("cardtype", cardtypes);
		dicts.put("maritalstatus", maritalstatuses);
		dicts.put("residentialpropertie", residentialproperties);
		dicts.put("sex", this.findSex());
		dicts.put("unitPropertis", this.findUnitPropertis());
		return dicts;
	}	
	/**
	 * 得到页面客户显示信息
	 * @param id
	 * @return
	 */
	public CustomerInforWeb findCustomerInforWebById(String id){
		return customerInforDao.findCustomerInforWebById(id);
	}
	
	/* 批量导入客户数据 excel2003或者excel2007格式*/
	public void importBatchCustomerInfoByExcel(MultipartFile file,String userId) throws IOException,IntoPiecesException, ParseException {
		String fileName = file.getOriginalFilename();
		List<BusinessModel> customerInforList = new ArrayList<BusinessModel>();
		List<BusinessModel> customerCareersInformationList = new ArrayList<BusinessModel>();
		List<BusinessModel> customerApplicationInfoList = new ArrayList<BusinessModel>();
		List<BusinessModel> customerApplicationGuarantorList = new ArrayList<BusinessModel>();
		List<BusinessModel> customerApplicationContactList = new ArrayList<BusinessModel>();
		List<BusinessModel> customerApplicationRecomList = new ArrayList<BusinessModel>();
		List<BusinessModel> customerApplicationOtherList = new ArrayList<BusinessModel>();
		HashMap<String,List<Dict>> dicts  = this.findDict();
		List<ProductAttribute> productAttributeList = commonDao.queryBySql(ProductAttribute.class, "select * from PRODUCT_ATTRIBUTE", null);
		if(fileName.endsWith(CustomerInforConstant.EXCEL_2003)){
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(file.getInputStream());
			/*循环工作表Sheet*/
			for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
				HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
				if (hssfSheet == null) {
					continue;
				}
				switch(numSheet){
					case 0:this.getExcel2003Content(hssfSheet,numSheet,customerInforList);break;
					case 1:this.getExcel2003Content(hssfSheet,numSheet,customerCareersInformationList);break;
					case 2:this.getExcel2003Content(hssfSheet,numSheet,customerApplicationInfoList);break;
					case 3:this.getExcel2003Content(hssfSheet,numSheet,customerApplicationGuarantorList);break;
					case 4:this.getExcel2003Content(hssfSheet,numSheet,customerApplicationContactList);break;
					case 5:this.getExcel2003Content(hssfSheet,numSheet,customerApplicationRecomList);break;
					case 6:this.getExcel2003Content(hssfSheet,numSheet,customerApplicationOtherList);break;
					default:break;
				}
			}
		}else if(fileName.endsWith(CustomerInforConstant.EXCEL_2007)){
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file.getInputStream());
			/*循环工作表Sheet*/
			for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
				XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
				if (xssfSheet == null) {
					continue;
				}
				switch(numSheet){
				case 0:this.getExcel2007Content(xssfSheet,numSheet,customerInforList);break;
				case 1:this.getExcel2007Content(xssfSheet,numSheet,customerCareersInformationList);break;
				case 2:this.getExcel2007Content(xssfSheet,numSheet,customerApplicationInfoList);break;
				case 3:this.getExcel2007Content(xssfSheet,numSheet,customerApplicationGuarantorList);break;
				case 4:this.getExcel2007Content(xssfSheet,numSheet,customerApplicationContactList);break;
				case 5:this.getExcel2007Content(xssfSheet,numSheet,customerApplicationRecomList);break;
				case 6:this.getExcel2007Content(xssfSheet,numSheet,customerApplicationOtherList);break;
				default:break;
			   }
			}
		}else{
			throw new IntoPiecesException(CustomerInforConstant.EXCEL_FORMAT_ERROR);
		}
		
		/*如果excel中有重复的数据抛出提示信息*/ 
		for(int i=0;i<customerInforList.size();i++){
			CustomerInfor customerInforOuter = (CustomerInfor)customerInforList.get(i);
			if(StringUtils.trimToNull(customerInforOuter.getCode())==null){
				throw new IntoPiecesException("客户基本信息中编号不能为空");
			}
			if(StringUtils.trimToNull(customerInforOuter.getChineseName())==null){
				throw new IntoPiecesException("客户基本信息中姓名不能为空");
			}
			if(StringUtils.trimToNull(customerInforOuter.getCardType())==null){
				throw new IntoPiecesException("客户基本信息中证件类型不能为空");
			}
			if(StringUtils.trimToNull(customerInforOuter.getCardId())==null){
				throw new IntoPiecesException("客户基本信息中证件号码不能为空");
			}
			String outerString = StringUtils.trim(customerInforOuter.getCardType())+StringUtils.trim(customerInforOuter.getCardId());
			for(int j=i+1;j<customerInforList.size();j++){
				CustomerInfor customerInforInner = (CustomerInfor)customerInforList.get(j);
				String innerString = StringUtils.trim(customerInforInner.getCardType())+StringUtils.trim(customerInforInner.getCardId());
				if(outerString.equalsIgnoreCase(innerString)){
					throw new IntoPiecesException("客户基本信息中："+customerInforInner.getChineseName()+"的证件类型和证件号码与别人重复!");
				}
			}
		}
		
		/*验证申请表编号是否为空*/
		for(int i=0;i<customerInforList.size();i++){
			CustomerInfor customerInfor = (CustomerInfor)customerInforList.get(i);
			if(StringUtils.trimToNull(customerInfor.getCode())==null){
				throw new IntoPiecesException("客户申请信息中编号不能为空!");
			}
			CustomerApplicationInfo customerApplicationInfo = (CustomerApplicationInfo)customerApplicationInfoList.get(i);
			customerApplicationInfo.setCustomerId(customerInfor.getId());
		}
		
		/*剔除职业表中客户编号跟客户基本信息表中编号不匹配的数据*/
		for(int i=0;i<customerCareersInformationList.size();i++){
			int count = 0;
			String customerId = null;
			CustomerCareersInformation customerCareersInformation = (CustomerCareersInformation)customerCareersInformationList.get(i);
			if(StringUtils.trimToNull(customerCareersInformation.getCustomerCode())==null){
				throw new IntoPiecesException("客户职业资料中客户编号不能为空 !");
			}
			for(int j=0;j<customerInforList.size();j++){
				CustomerInfor customerInfor =(CustomerInfor)customerInforList.get(j);
				if(!customerCareersInformation.getCustomerCode().equalsIgnoreCase(customerInfor.getCode())){
					count++;
				}else{
					customerId = customerInfor.getId();
			    	break;
			    }
			}
			if(count==customerInforList.size()){
				throw new IntoPiecesException("客户职业信息表中客户编号为"+customerCareersInformation.getCustomerCode()+"与客户信息表的编号匹配不上请检查!");
			}else{
				customerCareersInformation.setCustomerId(customerId);
				customerCareersInformation.setCustomerCode(null);
			}
		}
		/*剔除担保人,联系人,推荐人,其他资料中进件编号跟申请表信息不相符合的数据*/
		for(int i=0;i<customerApplicationGuarantorList.size();i++){
			int count = 0;
			String applicationId = null;
			CustomerApplicationGuarantor customerApplicationGuarantor = (CustomerApplicationGuarantor)customerApplicationGuarantorList.get(i);
			if(StringUtils.trimToNull(customerApplicationGuarantor.getMainApplicationFormCode())==null){
				throw new IntoPiecesException("担保人资料申请表编号不能为空 !");
			}
			for(int j=0;j<customerApplicationInfoList.size();j++){
				CustomerApplicationInfo customerApplicationInfo = (CustomerApplicationInfo)customerApplicationInfoList.get(j);
			    if(!customerApplicationGuarantor.getMainApplicationFormCode().equalsIgnoreCase(customerApplicationInfo.getCode())){
			    	count++;
			    }else{
			    	applicationId = customerApplicationInfo.getId();
			    	break;
			    }
			}
			if(count==customerApplicationInfoList.size()){
				throw new IntoPiecesException("担保人信息表中申请件编号为:"+customerApplicationGuarantor.getMainApplicationFormCode()+"与申请信息表中的编号匹配不上请检查!");
			}else{
				customerApplicationGuarantor.setMainApplicationFormId(applicationId);
				customerApplicationGuarantor.setMainApplicationFormCode(null);
			}
		}
		
		/*联系人*/
		for(int i=0;i<customerApplicationContactList.size();i++){
			int count = 0;
			String applicationId = null;
			CustomerApplicationContact customerApplicationContact = (CustomerApplicationContact)customerApplicationContactList.get(i);
			if(StringUtils.trimToNull(customerApplicationContact.getMainApplicationFormCode())==null){
				throw new IntoPiecesException("联系人资料申请表编号不能为空 !");
			}
			for(int j=0;j<customerApplicationInfoList.size();j++){
				CustomerApplicationInfo customerApplicationInfo = (CustomerApplicationInfo)customerApplicationInfoList.get(j);
			    if(!customerApplicationContact.getMainApplicationFormCode().equalsIgnoreCase(customerApplicationInfo.getCode())){
			    	count++;
			    }else{
			    	applicationId = customerApplicationInfo.getId();
			    	break;
			    }
			}
			if(count==customerApplicationInfoList.size()){
				throw new IntoPiecesException("联系人信息表中申请件编号为:"+customerApplicationContact.getMainApplicationFormCode()+"与申请信息表中的编号匹配不上请检查!");
			}else{
				customerApplicationContact.setMainApplicationFormId(applicationId);
				customerApplicationContact.setMainApplicationFormCode(null);
			}
		}
		
		/*推荐人*/
		for(int i=0;i<customerApplicationRecomList.size();i++){
			int count = 0;
			String applicationId = null;
			CustomerApplicationRecom customerApplicationRecom = (CustomerApplicationRecom)customerApplicationRecomList.get(i);
			if(StringUtils.trimToNull(customerApplicationRecom.getMainApplicationFormCode())==null){
				throw new IntoPiecesException("推荐人资料申请表编号不能为空 !");
			}
			for(int j=0;j<customerApplicationInfoList.size();j++){
				CustomerApplicationInfo customerApplicationInfo = (CustomerApplicationInfo)customerApplicationInfoList.get(j);
			    if(!customerApplicationRecom.getMainApplicationFormCode().equalsIgnoreCase(customerApplicationInfo.getCode())){
			    	count++;
			    }else{
			    	applicationId = customerApplicationInfo.getId();
			    	break;
			    }
			}
			if(count==customerApplicationInfoList.size()){
				throw new IntoPiecesException("推荐人信息表中申请件编号为:"+customerApplicationRecom.getMainApplicationFormCode()+"与申请信息表中的编号匹配不上请检查!");
			}else{
				customerApplicationRecom.setMainApplicationFormId(applicationId);
				customerApplicationRecom.setMainApplicationFormCode(null);
			}
		}
		
		/*其他*/
		for(int i=0;i<customerApplicationOtherList.size();i++){
			int count = 0;
			String applicationId = null;
			CustomerApplicationOther customerApplicationOther = (CustomerApplicationOther)customerApplicationOtherList.get(i);
			if(StringUtils.trimToNull(customerApplicationOther.getMainApplicationFormCode())==null){
				throw new IntoPiecesException("其他资料申请表编号不能为空 !");
			}
			for(int j=0;j<customerApplicationInfoList.size();j++){
				CustomerApplicationInfo customerApplicationInfo = (CustomerApplicationInfo)customerApplicationInfoList.get(j);
			    if(!customerApplicationOther.getMainApplicationFormCode().equalsIgnoreCase(customerApplicationInfo.getCode())){
			    	count++;
			    }else{
			    	applicationId = customerApplicationInfo.getId();
			    	break;
			    }
			}
			if(count==customerApplicationInfoList.size()){
				throw new IntoPiecesException("其他信息表中申请件编号为:"+customerApplicationOther.getMainApplicationFormCode()+"与申请信息表中的编号匹配不上请检查!");
			}else{
				customerApplicationOther.setMainApplicationFormId(applicationId);
				customerApplicationOther.setMainApplicationFormCode(null);
			}
		}
		
		/*检查重复上传的数据(与数据库对比)*/
		List<String> allCustorInfo = this.checkCustomerInfo();
		for(int i=0;i<customerInforList.size();i++){
			boolean isRemove = false;
			CustomerInfor obj = (CustomerInfor)customerInforList.get(i);
			if(allCustorInfo!=null&&!allCustorInfo.isEmpty()){
				for(String str :allCustorInfo){
					if(str.equalsIgnoreCase(StringUtils.trim(obj.getCardType())+StringUtils.trim(obj.getCardId()))){
						isRemove = true;
						break;
					}
				}
			}
			if(isRemove){
				throw new IntoPiecesException("系统检测到"+obj.getChineseName()+"的证件类型和证件号码已经在系统中存在,请勿重复上传!");
			}
		}
		
		for(BusinessModel obj : customerInforList){
			CustomerInfor customerInfor = (CustomerInfor)obj;
			customerInfor.setCreatedBy(userId);
			customerInfor.setCreatedTime(new Date());
			if(customerInfor!=null&&StringUtils.trimToNull(customerInfor.getBirthday())!=null){
				customerInfor.setBirthday(customerInfor.getBirthday().replaceAll("/", "-"));
			}
			for(String key:dicts.keySet()){
				List<Dict> list = dicts.get(key);
				for(Dict dict:list){
					if(dict.getTypeName().equalsIgnoreCase(customerInfor.getNationality())){
						customerInfor.setNationality(dict.getTypeCode());
						break;
					}else if(dict.getTypeName().equalsIgnoreCase(customerInfor.getCardType())){
						customerInfor.setCardType(dict.getTypeCode());
						break;
					}else if(dict.getTypeName().equalsIgnoreCase(customerInfor.getResidentialPropertie())){
						customerInfor.setResidentialPropertie(dict.getTypeCode());
						break;
					}else if(dict.getTypeName().equalsIgnoreCase(customerInfor.getMaritalStatus())){
						customerInfor.setMaritalStatus(dict.getTypeCode());
						break;
					}else if(dict.getTypeName().equalsIgnoreCase(customerInfor.getSex())){
						customerInfor.setSex(dict.getTypeCode());
						break;
					}
				}
			}
			
			for(String key:Dictionary.degreeeducationList.keySet()){
				if(Dictionary.degreeeducationList.get(key).equalsIgnoreCase(customerInfor.getDegreeEducation())){
					customerInfor.setDegreeEducation(key);
					break;
				}
			}
			/*验证身份证号码是否正确的代码
			if(CustomerInforConstant.ID_CODE.equals(customerInfor.getCardType())){
				String errorMessage = IdCardValidate.IDCardValidate(customerInfor.getCardId());
				if(StringUtils.trimToNull(errorMessage)!=null){
					throw new IntoPiecesException(customerInfor.getChineseName()+":"+errorMessage);
				}
			}*/
			/*根据客户经理编号查找客户经理id*/
			List<SystemUser> userList = commonDao.queryBySql(SystemUser.class, "select * from sys_user s where s.external_id='"+customerInfor.getUserId()+"'", null);
			if(userList!=null&&!userList.isEmpty()){
				customerInfor.setUserId(userList.get(0).getId());
			}else{
				customerInfor.setUserId(null);
			}
			commonDao.insertObject(obj);
		}
		
		for(BusinessModel obj : customerCareersInformationList){
			CustomerCareersInformation customerCareersInformation = (CustomerCareersInformation)obj;
			customerCareersInformation.setCreatedBy(userId);
			customerCareersInformation.setCreatedTime(new Date());
			/*单位性质*/
			for(String key:Dictionary.unitPropertisList.keySet()){
				if(Dictionary.unitPropertisList.get(key).equalsIgnoreCase(customerCareersInformation.getUnitNature())){
					customerCareersInformation.setUnitNature(key);
					break;
				}
			}
			/*行业类别*/
			for(String key:Dictionary.industryTypeList.keySet()){
				if(Dictionary.industryTypeList.get(key).equalsIgnoreCase(customerCareersInformation.getIndustryType())){
					customerCareersInformation.setIndustryType(key);
					break;
				}
			}
			/*职务*/
			for(String key:Dictionary.positioList.keySet()){
				if(Dictionary.positioList.get(key).equalsIgnoreCase(customerCareersInformation.getPositio())){
					customerCareersInformation.setPositio(key);
					break;
				}
			}
			/*职称*/
			for(String key:Dictionary.titleList.keySet()){
				if(Dictionary.titleList.get(key).equalsIgnoreCase(customerCareersInformation.getTitle())){
					customerCareersInformation.setTitle(key);
					break;
				}
			}
			commonDao.insertObject(obj);
		}
		
		for(BusinessModel obj : customerApplicationInfoList){
			CustomerApplicationInfo customerApplicationInfo = (CustomerApplicationInfo)obj;
			customerApplicationInfo.setCreatedBy(userId);
			customerApplicationInfo.setCreatedTime(new Date());
			for(String key:Dictionary.debitWayList.keySet()){
				if(Dictionary.debitWayList.get(key).equalsIgnoreCase(customerApplicationInfo.getDebitWay())){
					customerApplicationInfo.setDebitWay(key);
				}
			}
			if(productAttributeList!=null&&!productAttributeList.isEmpty()){
				int i =0;
				for(ProductAttribute productAttribute:productAttributeList){
					if(productAttribute.getProductName().equalsIgnoreCase(customerApplicationInfo.getProductId())){
						customerApplicationInfo.setProductId(productAttribute.getId());
						break;
					}else{
						i++;
					}
				}
				if(i==productAttributeList.size()){
					throw new IntoPiecesException(customerApplicationInfo.getProductId()+"不是合法的产品,请检查!");
				}
			}
			commonDao.insertObject(obj);
		}
		
		for(BusinessModel obj : customerApplicationGuarantorList){
			CustomerApplicationGuarantor customerApplicationGuarantor = (CustomerApplicationGuarantor)obj;
			customerApplicationGuarantor.setCreatedBy(userId);
			customerApplicationGuarantor.setCreatedTime(new Date());
			for(String key:dicts.keySet()){
				List<Dict> list = dicts.get(key);
				for(Dict dict:list){
					if(dict.getTypeName().equalsIgnoreCase(customerApplicationGuarantor.getSex())){
						customerApplicationGuarantor.setSex(dict.getTypeCode());
						break;
					}
				}
			}
			commonDao.insertObject(obj);
		}
		
		for(BusinessModel obj : customerApplicationContactList){
			CustomerApplicationContact customerApplicationContact = (CustomerApplicationContact)obj;
			customerApplicationContact.setCreatedBy(userId);
			customerApplicationContact.setCreatedTime(new Date());
			for(String key:dicts.keySet()){
				List<Dict> list = dicts.get(key);
				for(Dict dict:list){
					if(dict.getTypeName().equalsIgnoreCase(customerApplicationContact.getSex())){
						customerApplicationContact.setSex(dict.getTypeCode());
						break;
					}
				}
			}
			commonDao.insertObject(obj);
		}
		
		for(BusinessModel obj : customerApplicationRecomList){
			CustomerApplicationRecom customerApplicationRecom = (CustomerApplicationRecom)obj;
			customerApplicationRecom.setCreatedBy(userId);
			customerApplicationRecom.setCreatedTime(new Date());
			commonDao.insertObject(obj);
		}
		
		for(BusinessModel obj : customerApplicationOtherList){
			CustomerApplicationOther customerApplicationOther = (CustomerApplicationOther)obj;
			customerApplicationOther.setCreatedBy(userId);
			customerApplicationOther.setCreatedTime(new Date());
			if("使用密码".equalsIgnoreCase(customerApplicationOther.getConsumptionUsePassword())){
				customerApplicationOther.setConsumptionUsePassword("1");
			}else{
				customerApplicationOther.setConsumptionUsePassword("0");
			}
			if("开通".equalsIgnoreCase(customerApplicationOther.getSmsOpeningTrading())){
				customerApplicationOther.setSmsOpeningTrading("1");
			}else{
				customerApplicationOther.setSmsOpeningTrading("0");
			}
			
			if("纸质账单".equalsIgnoreCase(customerApplicationOther.getBillingMethod())){
				customerApplicationOther.setBillingMethod("0");
			}else if("电子账单".equalsIgnoreCase(customerApplicationOther.getBillingMethod())){
				customerApplicationOther.setBillingMethod("1");
			}
			if("到网点领取".equalsIgnoreCase(customerApplicationOther.getCollarCardMode())){
				customerApplicationOther.setCollarCardMode("0");
			}else if("普通邮寄".equalsIgnoreCase(customerApplicationOther.getCollarCardMode())){
				customerApplicationOther.setCollarCardMode("1");
			}else if("快递".equalsIgnoreCase(customerApplicationOther.getCollarCardMode())){
				customerApplicationOther.setCollarCardMode("2");
			}
			if("是".equalsIgnoreCase(customerApplicationOther.getUseSecondCard())){
				customerApplicationOther.setUseSecondCard("1");
			}else{
				customerApplicationOther.setUseSecondCard("0");
			}
			commonDao.insertObject(obj);
		}
	}
	
	/*读取excel2003*/
	private void getExcel2003Content(HSSFSheet hssfSheet, int i,List<BusinessModel> list) {
		if (i == 0) {
			/* 第一行是表头, 所以从第二行开始遍历 */
			for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
				HSSFRow hssfRow = hssfSheet.getRow(rowNum);
				if (hssfRow == null) {
					continue;
				}
				CustomerInfor customerInfor = new CustomerInfor();
				customerInfor.setId(IDGenerator.generateID());
                for(int cellNum=0;cellNum<=18;cellNum++){
                	switch(cellNum){
                	   case 0:customerInfor.setCode(this.getExcel2003Value(hssfRow.getCell(0))); break;
                	   case 1:customerInfor.setChineseName(this.getExcel2003Value(hssfRow.getCell(1))); break;
                	   case 2:customerInfor.setNationality(this.getExcel2003Value(hssfRow.getCell(2))); break;
                	   case 3:customerInfor.setSex(this.getExcel2003Value(hssfRow.getCell(3))); break;
                	   case 4:customerInfor.setPinyinenglishName(this.getExcel2003Value(hssfRow.getCell(4))); break;
                	   case 5:customerInfor.setBirthday(this.getExcel2003Value(hssfRow.getCell(5))); break;
                	   case 6:customerInfor.setCardType(this.getExcel2003Value(hssfRow.getCell(6))); break;
                	   case 7:customerInfor.setCardId(this.getExcel2003Value(hssfRow.getCell(7))); break;
                	   case 8:customerInfor.setResidentialAddress(this.getExcel2003Value(hssfRow.getCell(8))); break;
                	   case 9:customerInfor.setZipCode(this.getExcel2003Value(hssfRow.getCell(9))); break;
                	   case 10:customerInfor.setHomePhone(this.getExcel2003Value(hssfRow.getCell(10))); break;
                	   case 11:customerInfor.setTelephone(this.getExcel2003Value(hssfRow.getCell(11))); break;
                	   case 12:customerInfor.setMail(this.getExcel2003Value(hssfRow.getCell(12))); break;
                	   case 13:customerInfor.setResidentialPropertie(this.getExcel2003Value(hssfRow.getCell(13))); break;
                	   case 14:customerInfor.setMaritalStatus(this.getExcel2003Value(hssfRow.getCell(14))); break;
                	   case 15:customerInfor.setDegreeEducation(this.getExcel2003Value(hssfRow.getCell(15))); break;
                	   case 16:customerInfor.setHouseholdAddress(this.getExcel2003Value(hssfRow.getCell(16))); break;
                	   case 17:customerInfor.setZipCodeAddress(this.getExcel2003Value(hssfRow.getCell(17))); break;
                	   case 18:customerInfor.setUserId(this.getExcel2003Value(hssfRow.getCell(18))); break;
                	   default:break;
                	}
                }
                list.add(customerInfor);
			}
		}else if(i == 1){
			/* 第一行是表头, 所以从第二行开始遍历 */
			for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
				HSSFRow hssfRow = hssfSheet.getRow(rowNum);
				if (hssfRow == null) {
					continue;
				}
				CustomerCareersInformation customerCareersInformation = new CustomerCareersInformation();
                for(int cellNum=0;cellNum<=11;cellNum++){
                	switch(cellNum){
                	   case 0:customerCareersInformation.setCustomerCode(this.getExcel2003Value(hssfRow.getCell(0))); break;
                	   case 1:customerCareersInformation.setNameUnit(this.getExcel2003Value(hssfRow.getCell(1))); break;
                	   case 2:customerCareersInformation.setDepartmentName(this.getExcel2003Value(hssfRow.getCell(2))); break;
                	   case 3:customerCareersInformation.setUnitAddress(this.getExcel2003Value(hssfRow.getCell(3))); break;
                	   case 4:customerCareersInformation.setZipCode(this.getExcel2003Value(hssfRow.getCell(4))); break;
                	   case 5:customerCareersInformation.setUnitPhone(this.getExcel2003Value(hssfRow.getCell(5))); break;
                	   case 6:customerCareersInformation.setUnitNature(this.getExcel2003Value(hssfRow.getCell(6))); break;
                	   case 7:customerCareersInformation.setIndustryType(this.getExcel2003Value(hssfRow.getCell(7))); break;
                	   case 8:customerCareersInformation.setPositio(this.getExcel2003Value(hssfRow.getCell(8))); break;
                	   case 9:customerCareersInformation.setTitle(this.getExcel2003Value(hssfRow.getCell(9))); break;
                	   case 10:customerCareersInformation.setAnnualIncome(this.getExcel2003Value(hssfRow.getCell(10))); break;
                	   case 11:customerCareersInformation.setYearWorkUnit(this.getExcel2003Value(hssfRow.getCell(11))); break;
                	   default:break;
                	}
                }
                list.add(customerCareersInformation);
			}
		}else if(i==2){
			/* 第一行是表头, 所以从第二行开始遍历 */
			for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
				HSSFRow hssfRow = hssfSheet.getRow(rowNum);
				if (hssfRow == null) {
					continue;
				}
				CustomerApplicationInfo customerApplicationInfo = new CustomerApplicationInfo();
				customerApplicationInfo.setId(IDGenerator.generateID());
				customerApplicationInfo.setStatus(Constant.SAVE_INTOPICES);
                for(int cellNum=0;cellNum<=5;cellNum++){
                	switch(cellNum){
                	   case 0:customerApplicationInfo.setCode(this.getExcel2003Value(hssfRow.getCell(0))); break;
                	   case 1:customerApplicationInfo.setProductId(this.getExcel2003Value(hssfRow.getCell(1))); break;
                	   case 2:customerApplicationInfo.setApplyQuota(this.getExcel2003Value(hssfRow.getCell(2))); break;
                	   case 3:customerApplicationInfo.setDebitWay(this.getExcel2003Value(hssfRow.getCell(3))); break;
                	   case 4:customerApplicationInfo.setAutomaticRepaymentAgreement(this.getExcel2003Value(hssfRow.getCell(4))); break;
                	   case 5:customerApplicationInfo.setRepaymentCardNumber(this.getExcel2003Value(hssfRow.getCell(5))); break;
                	   default:break;
                	}
                }
                list.add(customerApplicationInfo);
			}
		}else if(i==3){
			/* 第一行是表头, 所以从第二行开始遍历 */
			for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
				HSSFRow hssfRow = hssfSheet.getRow(rowNum);
				if (hssfRow == null) {
					continue;
				}
				CustomerApplicationGuarantor customerApplicationGuarantor = new CustomerApplicationGuarantor();
                for(int cellNum=0;cellNum<=8;cellNum++){
                	switch(cellNum){
                	   case 0:customerApplicationGuarantor.setMainApplicationFormCode(this.getExcel2003Value(hssfRow.getCell(0))); break;
                	   case 1:customerApplicationGuarantor.setGuarantorMortgagorPledge(this.getExcel2003Value(hssfRow.getCell(1))); break;
                	   case 2:customerApplicationGuarantor.setSex(this.getExcel2003Value(hssfRow.getCell(2))); break;
                	   case 3:customerApplicationGuarantor.setRelationshipWithApplicant(this.getExcel2003Value(hssfRow.getCell(3))); break;
                	   case 4:customerApplicationGuarantor.setUnitName(this.getExcel2003Value(hssfRow.getCell(4))); break;
                	   case 5:customerApplicationGuarantor.setDepartment(this.getExcel2003Value(hssfRow.getCell(5))); break;
                	   case 6:customerApplicationGuarantor.setContactPhone(this.getExcel2003Value(hssfRow.getCell(6))); break;
                	   case 7:customerApplicationGuarantor.setCellPhone(this.getExcel2003Value(hssfRow.getCell(7))); break;
                	   case 8:customerApplicationGuarantor.setDocumentNumber(this.getExcel2003Value(hssfRow.getCell(8))); break;
                	   default:break;
                	}
                }
                list.add(customerApplicationGuarantor);
			}
		}else if(i==4){
			/* 第一行是表头, 所以从第二行开始遍历 */
			for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
				HSSFRow hssfRow = hssfSheet.getRow(rowNum);
				if (hssfRow == null) {
					continue;
				}
				CustomerApplicationContact customerApplicationContact = new CustomerApplicationContact();
                for(int cellNum=0;cellNum<=7;cellNum++){
                	switch(cellNum){
                	   case 0:customerApplicationContact.setMainApplicationFormCode(this.getExcel2003Value(hssfRow.getCell(0))); break;
                	   case 1:customerApplicationContact.setContactName(this.getExcel2003Value(hssfRow.getCell(1))); break;
                	   case 2:customerApplicationContact.setSex(this.getExcel2003Value(hssfRow.getCell(2))); break;
                	   case 3:customerApplicationContact.setRelationshipWithApplicant(this.getExcel2003Value(hssfRow.getCell(3))); break;
                	   case 4:customerApplicationContact.setUnitName(this.getExcel2003Value(hssfRow.getCell(4))); break;
                	   case 5:customerApplicationContact.setDepartment(this.getExcel2003Value(hssfRow.getCell(5))); break;
                	   case 6:customerApplicationContact.setContactPhone(this.getExcel2003Value(hssfRow.getCell(6))); break;
                	   case 7:customerApplicationContact.setCellPhone(this.getExcel2003Value(hssfRow.getCell(7))); break;
                	   default:break;
                	}
                }
                list.add(customerApplicationContact);
			}
		}else if(i==5){
			/* 第一行是表头, 所以从第二行开始遍历 */
			for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
				HSSFRow hssfRow = hssfSheet.getRow(rowNum);
				if (hssfRow == null) {
					continue;
				}
				CustomerApplicationRecom customerApplicationRecom = new CustomerApplicationRecom();
                for(int cellNum=0;cellNum<=4;cellNum++){
                	switch(cellNum){
                	   case 0:customerApplicationRecom.setMainApplicationFormCode(this.getExcel2003Value(hssfRow.getCell(0))); break;
                	   case 1:customerApplicationRecom.setName(this.getExcel2003Value(hssfRow.getCell(1))); break;
                	   case 2:customerApplicationRecom.setOutlet(this.getExcel2003Value(hssfRow.getCell(2))); break;
                	   case 3:customerApplicationRecom.setContactPhone(this.getExcel2003Value(hssfRow.getCell(3))); break;
                	   case 4:customerApplicationRecom.setRecommendedIdentityCardNumb(this.getExcel2003Value(hssfRow.getCell(4))); break;
                	   default:break;
                	}
                }
                list.add(customerApplicationRecom);
			}
		}else if(i==6){
			/* 第一行是表头, 所以从第二行开始遍历 */
			for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
				HSSFRow hssfRow = hssfSheet.getRow(rowNum);
				if (hssfRow == null) {
					continue;
				}
				CustomerApplicationOther customerApplicationOther = new CustomerApplicationOther();
                for(int cellNum=0;cellNum<=5;cellNum++){
                	switch(cellNum){
                	   case 0:customerApplicationOther.setMainApplicationFormCode(this.getExcel2003Value(hssfRow.getCell(0))); break;
                	   case 1:customerApplicationOther.setBillingMethod(this.getExcel2003Value(hssfRow.getCell(1))); break;
                	   case 2:customerApplicationOther.setPaperBillingShippingAddress(this.getExcel2003Value(hssfRow.getCell(2))); break;
                	   case 3:customerApplicationOther.setCollarCardMode(this.getExcel2003Value(hssfRow.getCell(3))); break;
                	   case 4:customerApplicationOther.setConsumptionUsePassword(this.getExcel2003Value(hssfRow.getCell(4))); break;
                	   case 5:customerApplicationOther.setSmsOpeningTrading(this.getExcel2003Value(hssfRow.getCell(5))); break;
                	   default:break;
                	}
                }
                list.add(customerApplicationOther);
			}
		}
	}
	
	/*读取excel2007*/
	private void getExcel2007Content(XSSFSheet xssfSheet, int i,List<BusinessModel> list) {
		if (i == 0) {
			/* 第一行是表头, 所以从第二行开始遍历 */
			for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				XSSFRow hssfRow = xssfSheet.getRow(rowNum);
				if (hssfRow == null) {
					continue;
				}
				CustomerInfor customerInfor = new CustomerInfor();
				customerInfor.setId(IDGenerator.generateID());
                for(int cellNum=0;cellNum<=18;cellNum++){
                	switch(cellNum){
                	   case 0:customerInfor.setCode(this.getExcel2007Value(hssfRow.getCell(0))); break;
                	   case 1:customerInfor.setChineseName(this.getExcel2007Value(hssfRow.getCell(1))); break;
                	   case 2:customerInfor.setNationality(this.getExcel2007Value(hssfRow.getCell(2))); break;
                	   case 3:customerInfor.setSex(this.getExcel2007Value(hssfRow.getCell(3))); break;
                	   case 4:customerInfor.setPinyinenglishName(this.getExcel2007Value(hssfRow.getCell(4))); break;
                	   case 5:customerInfor.setBirthday(this.getExcel2007Value(hssfRow.getCell(5))); break;
                	   case 6:customerInfor.setCardType(this.getExcel2007Value(hssfRow.getCell(6))); break;
                	   case 7:customerInfor.setCardId(this.getExcel2007Value(hssfRow.getCell(7))); break;
                	   case 8:customerInfor.setResidentialAddress(this.getExcel2007Value(hssfRow.getCell(8))); break;
                	   case 9:customerInfor.setZipCode(this.getExcel2007Value(hssfRow.getCell(9))); break;
                	   case 10:customerInfor.setHomePhone(this.getExcel2007Value(hssfRow.getCell(10))); break;
                	   case 11:customerInfor.setTelephone(this.getExcel2007Value(hssfRow.getCell(11))); break;
                	   case 12:customerInfor.setMail(this.getExcel2007Value(hssfRow.getCell(12))); break;
                	   case 13:customerInfor.setResidentialPropertie(this.getExcel2007Value(hssfRow.getCell(13))); break;
                	   case 14:customerInfor.setMaritalStatus(this.getExcel2007Value(hssfRow.getCell(14))); break;
                	   case 15:customerInfor.setDegreeEducation(this.getExcel2007Value(hssfRow.getCell(15))); break;
                	   case 16:customerInfor.setHouseholdAddress(this.getExcel2007Value(hssfRow.getCell(16))); break;
                	   case 17:customerInfor.setZipCodeAddress(this.getExcel2007Value(hssfRow.getCell(17))); break;
                	   case 18:customerInfor.setUserId(this.getExcel2007Value(hssfRow.getCell(18))); break;
                	   default:break;
                	}
                }
                list.add(customerInfor);
			}
		}else if(i == 1){
			/* 第一行是表头, 所以从第二行开始遍历 */
			for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				XSSFRow hssfRow = xssfSheet.getRow(rowNum);
				if (hssfRow == null) {
					continue;
				}
				CustomerCareersInformation customerCareersInformation = new CustomerCareersInformation();
                for(int cellNum=0;cellNum<=11;cellNum++){
                	switch(cellNum){
                	   case 0:customerCareersInformation.setCustomerCode(this.getExcel2007Value(hssfRow.getCell(0))); break;
                	   case 1:customerCareersInformation.setNameUnit(this.getExcel2007Value(hssfRow.getCell(1))); break;
                	   case 2:customerCareersInformation.setDepartmentName(this.getExcel2007Value(hssfRow.getCell(2))); break;
                	   case 3:customerCareersInformation.setUnitAddress(this.getExcel2007Value(hssfRow.getCell(3))); break;
                	   case 4:customerCareersInformation.setZipCode(this.getExcel2007Value(hssfRow.getCell(4))); break;
                	   case 5:customerCareersInformation.setUnitPhone(this.getExcel2007Value(hssfRow.getCell(5))); break;
                	   case 6:customerCareersInformation.setUnitNature(this.getExcel2007Value(hssfRow.getCell(6))); break;
                	   case 7:customerCareersInformation.setIndustryType(this.getExcel2007Value(hssfRow.getCell(7))); break;
                	   case 8:customerCareersInformation.setPositio(this.getExcel2007Value(hssfRow.getCell(8))); break;
                	   case 9:customerCareersInformation.setTitle(this.getExcel2007Value(hssfRow.getCell(9))); break;
                	   case 10:customerCareersInformation.setAnnualIncome(this.getExcel2007Value(hssfRow.getCell(10))); break;
                	   case 11:customerCareersInformation.setYearWorkUnit(this.getExcel2007Value(hssfRow.getCell(11))); break;
                	   default:break;
                	}
                }
                list.add(customerCareersInformation);
			}
		}else if(i==2){
			/* 第一行是表头, 所以从第二行开始遍历 */
			for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				XSSFRow hssfRow = xssfSheet.getRow(rowNum);
				if (hssfRow == null) {
					continue;
				}
				CustomerApplicationInfo customerApplicationInfo = new CustomerApplicationInfo();
				customerApplicationInfo.setId(IDGenerator.generateID());
				customerApplicationInfo.setStatus(Constant.SAVE_INTOPICES);
                for(int cellNum=0;cellNum<=5;cellNum++){
                	switch(cellNum){
                	   case 0:customerApplicationInfo.setCode(this.getExcel2007Value(hssfRow.getCell(0))); break;
                	   case 1:customerApplicationInfo.setProductId(this.getExcel2007Value(hssfRow.getCell(1))); break;
                	   case 2:customerApplicationInfo.setApplyQuota(this.getExcel2007Value(hssfRow.getCell(2))); break;
                	   case 3:customerApplicationInfo.setDebitWay(this.getExcel2007Value(hssfRow.getCell(3))); break;
                	   case 4:customerApplicationInfo.setAutomaticRepaymentAgreement(this.getExcel2007Value(hssfRow.getCell(4))); break;
                	   case 5:customerApplicationInfo.setRepaymentCardNumber(this.getExcel2007Value(hssfRow.getCell(5))); break;
                	   default:break;
                	}
                }
                list.add(customerApplicationInfo);
			}
		}else if(i==3){
			/* 第一行是表头, 所以从第二行开始遍历 */
			for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				XSSFRow hssfRow = xssfSheet.getRow(rowNum);
				if (hssfRow == null) {
					continue;
				}
				CustomerApplicationGuarantor customerApplicationGuarantor = new CustomerApplicationGuarantor();
                for(int cellNum=0;cellNum<=8;cellNum++){
                	switch(cellNum){
                	   case 0:customerApplicationGuarantor.setMainApplicationFormCode(this.getExcel2007Value(hssfRow.getCell(0))); break;
                	   case 1:customerApplicationGuarantor.setGuarantorMortgagorPledge(this.getExcel2007Value(hssfRow.getCell(1))); break;
                	   case 2:customerApplicationGuarantor.setSex(this.getExcel2007Value(hssfRow.getCell(2))); break;
                	   case 3:customerApplicationGuarantor.setRelationshipWithApplicant(this.getExcel2007Value(hssfRow.getCell(3))); break;
                	   case 4:customerApplicationGuarantor.setUnitName(this.getExcel2007Value(hssfRow.getCell(4))); break;
                	   case 5:customerApplicationGuarantor.setDepartment(this.getExcel2007Value(hssfRow.getCell(5))); break;
                	   case 6:customerApplicationGuarantor.setContactPhone(this.getExcel2007Value(hssfRow.getCell(6))); break;
                	   case 7:customerApplicationGuarantor.setCellPhone(this.getExcel2007Value(hssfRow.getCell(7))); break;
                	   case 8:customerApplicationGuarantor.setDocumentNumber(this.getExcel2007Value(hssfRow.getCell(8))); break;
                	   default:break;
                	}
                }
                list.add(customerApplicationGuarantor);
			}
		}else if(i==4){
			/* 第一行是表头, 所以从第二行开始遍历 */
			for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				XSSFRow hssfRow = xssfSheet.getRow(rowNum);
				if (hssfRow == null) {
					continue;
				}
				CustomerApplicationContact customerApplicationContact = new CustomerApplicationContact();
                for(int cellNum=0;cellNum<=7;cellNum++){
                	switch(cellNum){
                	   case 0:customerApplicationContact.setMainApplicationFormCode(this.getExcel2007Value(hssfRow.getCell(0))); break;
                	   case 1:customerApplicationContact.setContactName(this.getExcel2007Value(hssfRow.getCell(1))); break;
                	   case 2:customerApplicationContact.setSex(this.getExcel2007Value(hssfRow.getCell(2))); break;
                	   case 3:customerApplicationContact.setRelationshipWithApplicant(this.getExcel2007Value(hssfRow.getCell(3))); break;
                	   case 4:customerApplicationContact.setUnitName(this.getExcel2007Value(hssfRow.getCell(4))); break;
                	   case 5:customerApplicationContact.setDepartment(this.getExcel2007Value(hssfRow.getCell(5))); break;
                	   case 6:customerApplicationContact.setContactPhone(this.getExcel2007Value(hssfRow.getCell(6))); break;
                	   case 7:customerApplicationContact.setCellPhone(this.getExcel2007Value(hssfRow.getCell(7))); break;
                	   default:break;
                	}
                }
                list.add(customerApplicationContact);
			}
		}else if(i==5){
			/* 第一行是表头, 所以从第二行开始遍历 */
			for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				XSSFRow hssfRow = xssfSheet.getRow(rowNum);
				if (hssfRow == null) {
					continue;
				}
				CustomerApplicationRecom customerApplicationRecom = new CustomerApplicationRecom();
                for(int cellNum=0;cellNum<=4;cellNum++){
                	switch(cellNum){
                	   case 0:customerApplicationRecom.setMainApplicationFormCode(this.getExcel2007Value(hssfRow.getCell(0))); break;
                	   case 1:customerApplicationRecom.setName(this.getExcel2007Value(hssfRow.getCell(1))); break;
                	   case 2:customerApplicationRecom.setOutlet(this.getExcel2007Value(hssfRow.getCell(2))); break;
                	   case 3:customerApplicationRecom.setContactPhone(this.getExcel2007Value(hssfRow.getCell(3))); break;
                	   case 4:customerApplicationRecom.setRecommendedIdentityCardNumb(this.getExcel2007Value(hssfRow.getCell(4))); break;
                	   default:break;
                	}
                }
                list.add(customerApplicationRecom);
			}
		}else if(i==6){
			/* 第一行是表头, 所以从第二行开始遍历 */
			for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				XSSFRow hssfRow = xssfSheet.getRow(rowNum);
				if (hssfRow == null) {
					continue;
				}
				CustomerApplicationOther customerApplicationOther = new CustomerApplicationOther();
                for(int cellNum=0;cellNum<=5;cellNum++){
                	switch(cellNum){
                	   case 0:customerApplicationOther.setMainApplicationFormCode(this.getExcel2007Value(hssfRow.getCell(0))); break;
                	   case 1:customerApplicationOther.setBillingMethod(this.getExcel2007Value(hssfRow.getCell(1))); break;
                	   case 2:customerApplicationOther.setPaperBillingShippingAddress(this.getExcel2007Value(hssfRow.getCell(2))); break;
                	   case 3:customerApplicationOther.setCollarCardMode(this.getExcel2007Value(hssfRow.getCell(3))); break;
                	   case 4:customerApplicationOther.setConsumptionUsePassword(this.getExcel2007Value(hssfRow.getCell(4))); break;
                	   case 5:customerApplicationOther.setSmsOpeningTrading(this.getExcel2007Value(hssfRow.getCell(5))); break;
                	   default:break;
                	}
                }
                list.add(customerApplicationOther);
			}
		}
	}
	
	
	private String getExcel2003Value(HSSFCell hssfCell) {
		if(hssfCell==null){
			return null;
		}else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
            // 返回布尔类型的值
            return String.valueOf(hssfCell.getBooleanCellValue());
        } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
            // 返回数值类型的值
            return String.valueOf(hssfCell.getNumericCellValue());
        } else {
            // 返回字符串类型的值
            return String.valueOf(hssfCell.getStringCellValue());
        }
    }
	
	private String getExcel2007Value(XSSFCell hssfCell) {
		if(hssfCell==null){
			return null;
		}else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
            // 返回布尔类型的值
            return String.valueOf(hssfCell.getBooleanCellValue());
        } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
            // 返回数值类型的值
            return String.valueOf(hssfCell.getNumericCellValue());
        } else {
            // 返回字符串类型的值
            return String.valueOf(hssfCell.getStringCellValue());
        }
    }

	/**
	 * 修改客户信息移交状态
	 * @param id
	 * @param status
	 * @return
	 */
	public boolean updateCustomerInforDivisionalStatus(String id,CustomerInforDStatusEnum status){
		int i = customerInforDao.updateCustomerInforDivisionalStatus(id, status.toString());
		return i>0?true:false;
	}
	/**
	 * 得到客户信息移交状态
	 * @param id
	 * @return
	 */
	public boolean findCustomerInforDivisionalStatus(String id){
		String result = customerInforDao.getCustomerInforDivisionalStatus(id);
		if(result.equals(CustomerInforDStatusEnum.turn)){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 进件申请提交时 客户维护资料做快照  7张表对应客户的数据快照
	 * @param customerId 客户id
	 * @param applicationId 进件申请id
	 * @throws SQLException 
	 */
	public void insertCustomerInfoBySubmitApp(String customerId, String applicationId,String productId){
		customerInforDao.cloneBasicCustomerInfo(customerId, applicationId);
		customerInforDao.cloneCustomerCareersInf(customerId, applicationId);
		customerInforDao.cloneCustomerMainManager(customerId, applicationId);
		customerInforDao.cloneCustomerQuestionInfo(customerId, applicationId);
		customerInforDao.cloneCustomerWorkshipInfo(customerId, applicationId);
		customerInforDao.cloneDimensionalModelCredit(customerId, applicationId);
		customerInforDao.cloneCustomerVideoAccessories(customerId, applicationId);
		//添加申请件流程
		WfProcessInfo wf=new WfProcessInfo();
		wf.setProcessType(WfProcessInfoType.process_type);
		wf.setVersion("1");
		commonDao.insertObject(wf);
		List<NodeAudit> list=nodeAuditService.findByNodeTypeAndProductId(NodeAuditTypeEnum.Product.name(),productId);
		boolean startBool=false;
		boolean endBool=false;
		//节点id和WfStatusInfo id的映射
		Map<String, String> nodeWfStatusMap = new HashMap<String, String>();
		for(NodeAudit nodeAudit:list){
			if(nodeAudit.getIsstart().equals(YesNoEnum.YES.name())){
				startBool=true;
			}
			
			if(startBool&&!endBool){
				WfStatusInfo wfStatusInfo=new WfStatusInfo();
				wfStatusInfo.setIsStart(nodeAudit.getIsstart().equals(YesNoEnum.YES.name())?"1":"0");
				wfStatusInfo.setIsClosed(nodeAudit.getIsend().equals(YesNoEnum.YES.name())?"1":"0");
				wfStatusInfo.setRelationedProcess(wf.getId());
				wfStatusInfo.setStatusName(nodeAudit.getNodeName());
				wfStatusInfo.setStatusCode(nodeAudit.getId());
				commonDao.insertObject(wfStatusInfo);
				
				nodeWfStatusMap.put(nodeAudit.getId(), wfStatusInfo.getId());
				
				if(nodeAudit.getIsstart().equals(YesNoEnum.YES.name())){
					//添加初始审核
					CustomerApplicationProcess customerApplicationProcess=new CustomerApplicationProcess();
					String serialNumber = processService.start(wf.getId());
					customerApplicationProcess.setSerialNumber(serialNumber);
					customerApplicationProcess.setNextNodeId(nodeAudit.getId()); 
					customerApplicationProcess.setApplicationId(applicationId);
					commonDao.insertObject(customerApplicationProcess);
					
					CustomerApplicationInfo applicationInfo = commonDao.findObjectById(CustomerApplicationInfo.class, applicationId);
					applicationInfo.setSerialNumber(serialNumber);
					commonDao.updateObject(applicationInfo);
				}
			}
			
			if(nodeAudit.getIsend().equals(YesNoEnum.YES.name())){
				endBool=true;
			}
		}
		//节点关系
		List<NodeControl> nodeControls = nodeAuditService.findNodeControlByNodeTypeAndProductId(NodeAuditTypeEnum.Product.name(), productId);
		for(NodeControl control : nodeControls){
			WfStatusResult wfStatusResult = new WfStatusResult();
			wfStatusResult.setCurrentStatus(nodeWfStatusMap.get(control.getCurrentNode()));
			wfStatusResult.setNextStatus(nodeWfStatusMap.get(control.getNextNode()));
			wfStatusResult.setExamineResult(control.getCurrentStatus());
			commonDao.insertObject(wfStatusResult);
		}
	}
	
	/**
	 * 进件申请退回时 删除备份表中该单据的信息
	 * @param customerId
	 * @param applicationId
	 */
	public void deleteCloneSubmitAppByReturn(String customerId, String applicationId){
		customerInforDao.deleteCloneBasicCustomerInfo(customerId, applicationId);
		customerInforDao.deleteCloneCustomerCareersInf(customerId, applicationId);
		customerInforDao.deleteCloneCustomerMainManager(customerId, applicationId);
		customerInforDao.deleteCloneCustomerQuestionInfo(customerId, applicationId);
		customerInforDao.deleteCloneCustomerWorkshipInfo(customerId, applicationId);
		customerInforDao.deleteCloneDimensionalModelCredit(customerId, applicationId);
		customerInforDao.deleteCloneCustomerVideoAccessories(customerId, applicationId);
	}
	
	/**
	 * 生成流水
	 */
	
	/**
	 * 客户信息批量上传校验不能上传重复的客户信息,判断条件为证件类型和证件号码组合
	 */
	private List<String> checkCustomerInfo(){
		return customerinforcommDao.checkCustomerInfo();
	}

	/**
	 * 根据进件申请Id查询客户维护资料快照
	 * @param applicationId
	 */
	public CustomerInfor findCloneCustomerInforByAppId(String applicationId) {
		return customerInforDao.findCloneCustomerInforByAppId(applicationId);
	}
	
	/**
	 * 保存客户影像资料
	 * @param customerId
	 */
	public void saveYxzlByCustomerId(String customerId,String remark, MultipartFile file) {
		Map<String, String> map = UploadFileTool.uploadYxzlFileBySpring(file);
		String fileName = map.get("fileName");
		String url = map.get("url");
		VideoAccessories videoAccessories = new VideoAccessories();
		videoAccessories.setId(IDGenerator.generateID());
		videoAccessories.setCustomerId(customerId);
		videoAccessories.setRemark(remark);
		videoAccessories.setCreatedTime(new Date());
		if (StringUtils.trimToNull(url) != null) {
			videoAccessories.setServerUrlPath(url);
		}
		if (StringUtils.trimToNull(fileName) != null) {
			videoAccessories.setFileName(fileName);
		}
		commonDao.insertObject(videoAccessories);
	}
	
	/**
	 * 删除客户影像资料
	 * @param id
	 */
	public void deleteYxzlById(String id){
		VideoAccessories v = commonDao.findObjectById(VideoAccessories.class, id);
		if(v!=null){
			UploadFileTool.deleteFile(v.getServerUrlPath());
		}
		commonDao.deleteObject(VideoAccessories.class, id);
	}
	
	/**
	 * 下载客户影像资料
	 * @param id
	 * @throws Exception 
	 */
	public void downLoadYxzlById(HttpServletResponse response,String id) throws Exception{
		VideoAccessories v = commonDao.findObjectById(VideoAccessories.class, id);
		if(v!=null){
			UploadFileTool.downLoadFile(response, v.getServerUrlPath(), v.getFileName());
		}
	}
	
	
	/**
	 * 生成影像资料缩略图,返回缩略图和原始图的图片的url
	 * @param id
	 * @throws Exception 
	 */
	public Map<String,String> createThumbnail(String id) throws Exception{
		Map<String,String> map = null;
		VideoAccessories v = commonDao.findObjectById(VideoAccessories.class, id);
		if(v!=null){
			map = UploadFileTool.CreateThumbnail(Constant.FILE_PATH, v.getServerUrlPath(), v.getFileName());
		}
		return map;
	}
	
	
	/**
	 * 按申请表id查找相应的客户拍照信息
	 * @param applicationId
	 * @return CustomerInfor
	 */
	public CustomerInfor findCustomerInforsById(String applicationId){
		return customerInforDao.findCloneCustomerInforByAppId(applicationId);
	}
	
	/**
	 * 按客户id查找相应的客户置业拍照信息
	 * @param customerId
	 * @return CustomerCareersInformation
	 */
	public CustomerCareersInformation findCustomerCareersByCustomerId(String customerId, String applicationId){
		return customerInforDao.findcloneCustomerCareersByCustomerId(customerId, applicationId);
	}
	
	/**
	 * 按客户id查找其对应的影像附件资料
	 * @param customerId
	 * @return List<CustomerCareersInformation>
	 */
	public List<VideoAccessories> findCustomerVideoAccessoriesByCustomerId(String customerId){
		return customerinforcommDao.findCustomerVideoAccessoriesByCustomerId(customerId);
	}

	
	/**
	 * 按id查找相应的客户基本信息（太原）
	 * @param customerInforId
	 * @return
	 */
	public CustomerFirsthendBase findCustomerFirsthendById(String customerInforId){
		return commonDao.findObjectById(CustomerFirsthendBase.class,customerInforId);
	}
	//CustomerFirsthendBase
	public CIPERSONBASINFO findCIPERSONBASINFO(String customerInforId){
		return commonDao.findObjectById(CIPERSONBASINFO.class,customerInforId);
	}
	//CustomerFirsthendBase
	public CustomerFirsthendBase findCustomerFirsthendBase(
			String customerInforId) {
		// TODO Auto-generated method stub
		return commonDao.findObjectById(CustomerFirsthendBase.class,customerInforId);
	}
	
	//COPY
	public CIPERSONBASINFOCOPY findCIPERSONBASINFOCOPY(String customerInforId){
		return commonDao.findObjectById(CIPERSONBASINFOCOPY.class,customerInforId);
	}
	
	//copy
	public void findCIPERSONBASINFOCOPYISEXIT(String id){
		String sql = "insert into t_cipersonbasinfo_copy value (select * from t_cipersonbasinfo where id='"+id+"')";
		commonDao.queryBySql(CIPERSONBASINFOCOPY.class,sql , null);
	}
	
	/**
	 * 按客户内码id查找相应的客户基本信息（太原）
	 * @param customerInforId
	 * @return
	 */
	public CustomerFirsthendBase findCustomerFirsthendByNm(String nmId){
		String sql = "select * from ty_customer_base where khnm='"+nmId+"'";
		CustomerFirsthendBase info = commonDao.queryBySql(CustomerFirsthendBase.class,sql , null).get(0);
		return info;
	}
	
	public CustomerFirsthendBaseLocal findCustomerFirsthendLocalByNm(String nmId){
		String sql = "select * from ty_customer_base_local where khnm='"+nmId+"'";
		List<CustomerFirsthendBaseLocal> infolist = commonDao.queryBySql(CustomerFirsthendBaseLocal.class,sql , null);
		if(infolist.size()>0){
			return infolist.get(0);
		}
		return null;
	}
	
	public void updateCustomerFirsthendLocal(CustomerFirsthendBaseLocal baseLocal){
		commonDao.updateObject(baseLocal);
	}
	
	/**
	 * 获取指定客户经理的客户列表
	 * @param user
	 * @return
	 */
	public QueryResult<MaintenanceLog> findCustomerByFilter(CustomerInforFilter filter){
		List<MaintenanceLog> plans = customerInforDao.findCustomerByFilter(filter);
		int size = customerInforDao.findCustomerCountByFilter(filter);
		QueryResult<MaintenanceLog> qr = new QueryResult<MaintenanceLog>(size,plans);
		return qr;
	}
	
	
	
	
	
	/**
	 * 按客户内码id查找相应的客户家庭信息（太原）
	 * @param customerInforId
	 * @return
	 */
	public List<CustomerFirsthendFamilyCy> findFamilyByNm(String nmId,String type){
		String sql = "select * from ty_customer_family_cy where khnm='"+nmId+"' and gxfl='"+type+"'";

		List<CustomerFirsthendFamilyCy> info = commonDao.queryBySql(CustomerFirsthendFamilyCy.class,sql , null);
		return info;
	}
	/**
	 * 按客户内码id查找相应的客户家庭财产信息（太原）
	 * @param customerInforId
	 * @return
	 */
	public List<CustomerFirsthendFamilyCc> findFamilyCcByNm(String nmId){
		String sql = "select * from ty_customer_family_cc where khnm='"+nmId+"'";

		List<CustomerFirsthendFamilyCc> info = commonDao.queryBySql(CustomerFirsthendFamilyCc.class,sql , null);
		return info;
	}
	/**
	 * 按客户内码id查找相应的客户生产经营（太原）
	 * @param customerInforId
	 * @return
	 */
	public List<CustomerFirsthendManage> findManageByNm(String nmId){
		String sql = "select * from ty_customer_manage where khnm='"+nmId+"'";

		List<CustomerFirsthendManage> info = commonDao.queryBySql(CustomerFirsthendManage.class,sql , null);
		return info;
	}
	/**
	 * 按客户内码id查找相应的客户学校情况（太原）
	 * @param customerInforId
	 * @return
	 */
	public List<CustomerFirsthendStudy> findStudyByNm(String nmId){
		String sql = "select * from ty_customer_study where khnm='"+nmId+"'";

		List<CustomerFirsthendStudy> info = commonDao.queryBySql(CustomerFirsthendStudy.class,sql , null);
		return info;
	}
	/**
	 * 按客户内码id查找相应的客户工作情况（太原）
	 * @param customerInforId
	 * @return
	 */
	public List<CustomerFirsthendWork> findWorkByNm(String nmId){
		String sql = "select * from ty_customer_work where khnm='"+nmId+"'";

		List<CustomerFirsthendWork> info = commonDao.queryBySql(CustomerFirsthendWork.class,sql , null);
		return info;
	}
	/**
	 * 按客户内码id查找相应的入保信息（太原）
	 * @param customerInforId
	 * @return
	 */
	public List<CustomerFirsthendSafe> findSafeByNm(String nmId){
		String sql = "select * from ty_customer_safe where khnm='"+nmId+"'";

		List<CustomerFirsthendSafe> info = commonDao.queryBySql(CustomerFirsthendSafe.class,sql , null);
		return info;
	}
	
	/**
	 * 保数据文件到”人员管理参数“表
	 * @param fileName
	 * @return
	 * @throws Exception 
	 */
	public void saveRyglDataFile(String fileName,String date) {
		try {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/tyCustomerRygl.xml");

			// 解析”帐单记录表“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			int count=0;
			for(Map<String, Object> map : datas){
				count++;
//				System.out.println(count);
				// 保存数据
				//先查询人员管理参数表，存在则更新否则插入
				String sql = "select * from ty_customer_rygl where dm='"+map.get("dm").toString().trim()+"'";
				List<CustomerFirsthendRygl> list = commonDao.queryBySql(CustomerFirsthendRygl.class, sql, null);
				if(list.size()>0){
				}else{
					customerInforDao.insertCustomerRygl(map);
				}
				
			}
			//释放空间
			datas=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 保数据文件到”台帐“表
	 * @param fileName
	 * @return
	 * @throws Exception 
	 */
	public void saveMibusidata(String fileName,String date) {
		try {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/T_MIBUSIDATA.xml");

			// 解析”帐单记录表“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			int count=0;
			for(Map<String, Object> map : datas){
				count++;
				//System.out.println(count);
				// 保存数据
				//先查询人员管理参数表，存在则更新否则插入
				/*String sql = "select * from T_MIBUSIDATA where dm='"+map.get("dm").toString().trim()+"'";
				List<CustomerFirsthendRygl> list = commonDao.queryBySql(CustomerFirsthendRygl.class, sql, null);
				if(list.size()>0){
				}else{
				}*/
				//insertmibusidata
					customerInforDao.insertMIBUSIDATA(map);
				
				
			}
			//释放空间
			datas=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 保数据文件到”客户原始资料“表
	 * @param fileName
	 * @return
	 * @throws Exception 
	 */
	public void saveBaseDataFile(String fileName,String date) {
		try {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/tyCustomerBase.xml");

			// 解析”客户基础表“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			int count=0;
			for(Map<String, Object> map : datas){
				count++;
//				System.out.println(count);
				// 保存数据
				//先查询客户原始表，存在则更新否则插入(更新原始信息表)
				String sql = "select * from ty_customer_base where khnm='"+map.get("khnm").toString()+"'";
				List<CustomerFirsthendBase> list = commonDao.queryBySql(CustomerFirsthendBase.class, sql, null);
				if(list.size()>0){
					customerInforDao.updateCustomerBase(map);
					//同步系统客户主表(由于存在客户经理更改情况，所以更新原始表同时，更新主表客户经理)
					String card_id = map.get("zjhm").toString();
					String name = map.get("khmc").toString();
					String id = map.get("id").toString();
					//客户经理标识
					String khjl = map.get("khjl").toString();
					//先通过标识获取柜员号
					List<CustomerFirsthendRygl> rygl = commonDao.queryBySql(CustomerFirsthendRygl.class, "select * from ty_customer_rygl where dm='"+khjl.trim()+"'", null);
					String gyh1 = "";
					String gyh2 = "";
					if(rygl.size()>0){
						gyh1 = rygl.get(0).getDdrq();
						gyh2 = rygl.get(0).getDldm();
						if(gyh1==null){
							gyh1="";
						}
						if(gyh2==null){
							gyh2="";
						}
					}
					//获取客户经理id(由于两个字段都有可能为柜员号，所以两次判断)
					String user_id=null;
					List<SystemUser> users = commonDao.queryBySql(SystemUser.class, "select * from sys_user where external_id='"+gyh1+"'", null);
					//银行工号匹配本系统uuid，存在则替换，不存在则插入银行工号
					if(users.size()>0){
						user_id = users.get(0).getId();
					}else{
						List<SystemUser> users1 = commonDao.queryBySql(SystemUser.class, "select * from sys_user where external_id='"+gyh2+"'", null);
						if(users1.size()>0){
							user_id = users1.get(0).getId();
						}else{
							if(!gyh2.equals("")){
								user_id=gyh2;
							}else{
								user_id = khjl.trim();
							}
						}
					}
					List<CustomerInfor> infoList = commonDao.queryBySql(CustomerInfor.class, "select * from basic_customer_information where card_id='"+card_id+"'", null);
					//存在则更新客户经理id
					if(infoList.size()>0){
						CustomerInfor info = infoList.get(0);
						info.setUserId(user_id);
						commonDao.updateObject(info);
					}
				}else{
					customerInforDao.insertCustomerBase(map);
					//同步系统客户主表
					String card_id = map.get("zjhm").toString();
					String name = map.get("khmc").toString();
					String id = map.get("id").toString();
					//客户经理标识
					String khjl = map.get("khjl").toString();
					//先通过标识获取柜员号
					List<CustomerFirsthendRygl> rygl = commonDao.queryBySql(CustomerFirsthendRygl.class, "select * from ty_customer_rygl where dm='"+khjl.trim()+"'", null);
					String gyh1 = "";
					String gyh2 = "";
					if(rygl.size()>0){
						gyh1 = rygl.get(0).getDdrq();
						gyh2 = rygl.get(0).getDldm();
						if(gyh1==null){
							gyh1="";
						}
						if(gyh2==null){
							gyh2="";
						}
					}
					//获取客户经理id(由于两个字段都有可能为柜员号，所以两次判断)
					String user_id=null;
					List<SystemUser> users = commonDao.queryBySql(SystemUser.class, "select * from sys_user where external_id='"+gyh1+"'", null);
					//银行工号匹配本系统uuid，存在则替换，不存在则插入银行工号
					if(users.size()>0){
						user_id = users.get(0).getId();
					}else{
						List<SystemUser> users1 = commonDao.queryBySql(SystemUser.class, "select * from sys_user where external_id='"+gyh2+"'", null);
						if(users1.size()>0){
							user_id = users1.get(0).getId();
						}else{
							if(!gyh2.equals("")){
								user_id=gyh2;
							}else{
								user_id = khjl.trim();
							}
						}
					}
					List<CustomerInfor> infoList = commonDao.queryBySql(CustomerInfor.class, "select * from basic_customer_information where card_id='"+card_id+"'", null);
					//不存在则插入
					if(infoList.size()==0){
						CustomerInfor info = new CustomerInfor();
						info.setCardId(card_id);
						info.setChineseName(name);
						info.setTyCustomerId(id);
						info.setId(IDGenerator.generateID());
						info.setUserId(user_id);
						//身份证
						info.setCardType("CST0000000000A");
						commonDao.insertObject(info);
					}else{
						//存在则作关联
						CustomerInfor info = infoList.get(0);
						info.setTyCustomerId(id);
						commonDao.updateObject(info);
					}
				}
				//先查询客户维护表，存在不操作否则插入(新增原始信息维护表)(触发器执行)
//				String sql1 = "select * from ty_customer_base_local where khnm='"+map.get("khnm").toString()+"'";
//				List<CustomerFirsthendBase> list1 = commonDao.queryBySql(CustomerFirsthendBase.class, sql1, null);
//				if(list1.size()==0){
//					customerInforDao.insertCustomerBaseLocal(map);
//				}
				
			}
			//释放空间
			datas=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 保数据文件到”客户家庭关系“表
	 * @param fileName
	 * @return
	 * @throws Exception 
	 */
	public void saveCyDataFile(String fileName,String date) {
		try {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/tyCustomerFamilyCy.xml");

			// 解析”帐单记录表“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			int count=0;
			for(Map<String, Object> map : datas){
				count++;
//				System.out.println(count);
				// 保存数据
				//先查询家庭关系表，存在则更新否则插入
				String sql = "select * from ty_customer_family_cy where khnm='"+map.get("khnm").toString()+"'";
				List<CustomerFirsthendFamilyCy> list = commonDao.queryBySql(CustomerFirsthendFamilyCy.class, sql, null);
				if(list.size()>0){
					customerInforDao.updateCustomerFamilyCy(map);
				}else{
					customerInforDao.insertCustomerFamilyCy(map);
				}

				
			}
			//释放空间
			datas=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 保数据文件到”客户家庭关系“表
	 * @param fileName
	 * @return
	 * @throws Exception 
	 */
	public void saveCcDataFile(String fileName,String date) {
		try {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/tyCustomerFamilyCc.xml");

			// 解析”帐单记录表“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			int count=0;
			for(Map<String, Object> map : datas){
				count++;
//				System.out.println(count);
				// 保存数据
				//先查询家庭关系表，存在则更新否则插入
				String sql = "select * from ty_customer_family_cc where khnm='"+map.get("khnm").toString()+"'";
				List<CustomerFirsthendFamilyCc> list = commonDao.queryBySql(CustomerFirsthendFamilyCc.class, sql, null);
				if(list.size()>0){
					customerInforDao.updateCustomerFamilyCc(map);
				}else{
					customerInforDao.insertCustomerFamilyCc(map);
				}

				
			}
			//释放空间
			datas=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 保数据文件到”客户学习履历“表
	 * @param fileName
	 * @return
	 * @throws Exception 
	 */
	public void saveStudyDataFile(String fileName,String date) {
		try {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/tyCustomerStudy.xml");

			// 解析”帐单记录表“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			int count=0;
			for(Map<String, Object> map : datas){
				count++;
//				System.out.println(count);
				// 保存数据
				//先查询家庭关系表，存在则更新否则插入
				String sql = "select * from ty_customer_study where khnm='"+map.get("khnm").toString()+"'";
				List<CustomerFirsthendStudy> list = commonDao.queryBySql(CustomerFirsthendStudy.class, sql, null);
				if(list.size()>0){
					customerInforDao.updateCustomerStudy(map);
				}else{
					customerInforDao.insertCustomerStudy(map);
				}

				
			}
			//释放空间
			datas=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 保数据文件到”客户工作履历“表
	 * @param fileName
	 * @return
	 * @throws Exception 
	 */
	public void saveWorkDataFile(String fileName,String date) {
		try {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/tyCustomerWork.xml");

			// 解析”帐单记录表“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			int count=0;
			for(Map<String, Object> map : datas){
				count++;
//				System.out.println(count);
				// 保存数据
				//先查询工作履历表，存在则更新否则插入
				String sql = "select * from ty_customer_work where khnm='"+map.get("khnm").toString()+"'";
				List<CustomerFirsthendWork> list = commonDao.queryBySql(CustomerFirsthendWork.class, sql, null);
				if(list.size()>0){
					customerInforDao.updateCustomerWork(map);
				}else{
					customerInforDao.insertCustomerWork(map);
				}

				
			}
			//释放空间
			datas=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 保数据文件到”客户工作履历“表
	 * @param fileName
	 * @return
	 * @throws Exception 
	 */
	public void saveManageDataFile(String fileName,String date) {
		try {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/tyCustomerManage.xml");

			// 解析”帐单记录表“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			int count=0;
			for(Map<String, Object> map : datas){
				count++;
//				System.out.println(count);
				// 保存数据
				//先查询生产经营表，存在则更新否则插入
				String sql = "select * from ty_customer_manage where khnm='"+map.get("khnm").toString()+"'";
				List<CustomerFirsthendManage> list = commonDao.queryBySql(CustomerFirsthendManage.class, sql, null);
				if(list.size()>0){
					customerInforDao.updateCustomerManage(map);
				}else{
					customerInforDao.insertCustomerManage(map);
				}

				
			}
			//释放空间
			datas=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 保数据文件到”客户工作履历“表
	 * @param fileName
	 * @return
	 * @throws Exception 
	 */
	public void saveSafeDataFile(String fileName,String date) {
		try {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/tyCustomerSafe.xml");

			// 解析”帐单记录表“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			for(Map<String, Object> map : datas){
				// 保存数据
				//先查询生产经营表，存在则更新否则插入
				String sql = "select * from ty_customer_safe where khnm='"+map.get("khnm").toString()+"'";
				List<CustomerFirsthendSafe> list = commonDao.queryBySql(CustomerFirsthendSafe.class, sql, null);
				if(list.size()>0){
					customerInforDao.updateCustomerSafe(map);
				}else{
					customerInforDao.insertCustomerSafe(map);
				}

				
			}
			//释放空间
			datas=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 保数据文件到”流水账“表
	 * @param fileName
	 * @return
	 * @throws Exception 
	 */
	public void saveLSZDataFile(String fileName,String date) {
		try {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/tyRepayLSZ.xml");

			// 解析”流水号“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			
			//批量插入
			insertLsh(datas);
			//释放空间
			datas=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 保数据文件到”余额“表
	 * @param fileName
	 * @return
	 * @throws Exception 
	 */
	public void saveYEHZDataFile(String fileName,String date) {
		try {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/tyRepayYEHZ.xml");

			// 解析”余额“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			//批量插入
			insertYe(datas);
			//释放空间
			datas=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 保数据文件到”借据表“表
	 * @param fileName
	 * @return
	 * @throws Exception 
	 */
	public void saveTKMXDataFile(String fileName,String date) {
		try {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/tyRepayTKMX.xml");

			// 解析”流水号“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			//批量插入
			insertJjb(datas);
			//释放空间
			datas=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 保数据文件到”产品信息“表
	 * @param fileName
	 * @return
	 * @throws Exception 
	 */
	public void saveProductDataFile(String fileName,String date) {
		try {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/tyRepayProduct.xml");
			
			// 解析”流水号“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			int count=0;
			for(Map<String, Object> map : datas){
				count++;
//				System.out.println(count);
				// 保存数据
				String sql = "select * from ty_product_type where product_code='"+map.get("productCode").toString()+"'";
				List<TyProductType> list = commonDao.queryBySql(TyProductType.class, sql, null);
				if(list.size()==0){
					customerInforDao.insertProduct(map);
				}
			}
			//释放空间
			datas=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 保数据文件到”黑名单“表
	 * @param fileName
	 * @return
	 * @throws Exception 
	 */
	public void saveHMDDataFile(String fileName,String date) {
		try {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/tyRepayHMD.xml");

			// 解析”黑名单“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			int count=0;
			//删除历史数据
			String sql = "delete from f_agr_crd_xyk_cuneg where created_time !='"+date+"'";
			commonDao.queryBySql(sql, null);
			for(Map<String, Object> map : datas){
				count++;
//				System.out.println(count);
				// 保存数据
				customerInforDao.insertHmd(map);
			}
			//释放空间
			datas=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
/*	*//**
	 * 解析（原始信息）
	 * @throws IOException 
	 *//*
	public void readFile() throws IOException{
		//获取今日日期
	      //yyyyMMdd格式
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		String dateString = format.format(new Date());
		log.info(dateString+"******************开始读取原始信息文件********************");  
	        String gzFile = CardFtpUtils.bank_ftp_down_path;
	        for(int i=0;i<fileTxt.length;i++){
				String url = gzFile+fileTxt[i];
				log.info(url);
				File f = new File(url);
				if(f.exists()){
					log.info("fsuccess");
						List<String> spFile = new ArrayList<String>();
						String fileN = "";
						//判断文件大小，超过50M的先分割
						if (f.exists() && f.isFile()){
							if(f.length()>20000000){
								int spCount = (int) (f.length()/20000000);
								SPTxt.splitTxt(url,spCount);
								int to = fileTxt[i].lastIndexOf('.');
						    	fileN = fileTxt[i].substring(0, to);
								for(int j=0;j<spCount;j++){
									spFile.add(fileN+"_"+j+".txt");
								}
							}else{
								int to = fileTxt[i].lastIndexOf('.');
						    	fileN = fileTxt[i].substring(0, to);
								spFile.add(fileN+".txt");
							}
						}
						for(String fn : spFile){
							try{
								if(fn.contains(fileN)) {
									if(fn.startsWith("cxd_rygl")){
										log.info("*****************人员管理参数表********************");  
										saveRyglDataFile(gzFile+File.separator+fn,dateString);
										System.gc();
									}
									if(fn.startsWith("kkh_grxx")){
										log.info("*****************客户基本表********************");  
										saveBaseDataFile(gzFile+File.separator+fn,dateString);
										System.gc();
									}
									if(fn.startsWith("kkh_grjtcy")){
										log.info("*****************客户家庭关系表********************");  
										saveCyDataFile(gzFile+File.separator+fn,dateString);
									}
									if(fn.startsWith("kkh_grjtcc")){
										log.info("*****************客户家庭财产表********************");  
										saveCcDataFile(gzFile+File.separator+fn,dateString);
									}
									if(fn.startsWith("kkh_grxxll")){
										log.info("*****************客户学习表********************");  
										saveStudyDataFile(gzFile+File.separator+fn,dateString);
									}
									if(fn.startsWith("kkh_grgzll")){
										log.info("*****************客户工作履历表********************");  
										saveWorkDataFile(gzFile+File.separator+fn,dateString);
									}
									if(fn.startsWith("kkh_grscjy")){
										log.info("*****************客户生产经营表********************");  
									saveManageDataFile(gzFile+File.separator+fn,dateString);
								}
									if(fn.startsWith("kkh_grrbxx")){
										log.info("*****************客户入保信息表********************");  
										saveSafeDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("cxd_dkcpmc")){
										log.info("*****************产品信息********************");  
										saveProductDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("kkh_hmdgl")){
										log.info("*****************黑名单********************");  
										saveHMDDataFile(gzFile+File.separator+fn,dateString);
									}
								} 
							}catch(Exception e){
								e.printStackTrace();
								throw new RuntimeException(e);
							}
						}
						f.delete();
				}
	        }
	        log.info(dateString+"******************完成读取原始信息文件********************");

	}*/
	
	/**
	 *解析贷款信息
	 * @throws IOException 
	 *//*
	public void readFileRepay() throws IOException{
		//获取今日日期
	      //yyyyMMdd格式
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		String dateString = format.format(new Date());
		String gzFile = CardFtpUtils.bank_ftp_down_path+dateString;

		log.info(dateString+"******************开始读取贷款文件********************");  
        for(int i=0;i<fileTxtRepay.length;i++){
			String url = gzFile+File.separator+fileTxtRepay[i];
			File f = new File(url);
			if(f.exists()){
					List<String> spFile = new ArrayList<String>();
					String fileN = "";
					//判断文件大小，超过50M的先分割
					if (f.exists() && f.isFile()){
						if(f.length()>20000000){
							int spCount = (int) (f.length()/20000000);
							SPTxt.splitTxt(url,spCount);
							int to = fileTxtRepay[i].lastIndexOf('.');
					    	fileN = fileTxtRepay[i].substring(0, to);
							for(int j=0;j<spCount;j++){
								spFile.add(fileN+"_"+j+".txt");
							}
						}else{
							int to = fileTxtRepay[i].lastIndexOf('.');
					    	fileN = fileTxtRepay[i].substring(0, to);
							spFile.add(fileN+".txt");
						}
					}
					
					if(fileN.startsWith("kdk_lsz")){
						//删除流水号历史数据
						String sql = " truncate   table   ty_repay_lsz";
						commonDao.queryBySql(sql, null);
					}
					if(fileN.startsWith("kdk_yehz")){
						//删除余额汇总历史数据
						String sql = " truncate   table   ty_repay_yehz";
						commonDao.queryBySql(sql, null);
					}
					if(fileN.startsWith("kdk_tkmx")){
						//删除借据表历史数据
						String sql = " truncate   table   ty_repay_tkmx";
						commonDao.queryBySql(sql, null);
					}
					for(String fn : spFile){
						try{
							if(fn.contains(fileN)) {
								if(fn.startsWith("kdk_lsz")){
									log.info("*****************流水账信息********************");  
									saveLSZDataFile(gzFile+File.separator+fn,dateString);
								}else if(fn.startsWith("kdk_yehz")){
									log.info("*****************余额汇总信息********************");  
									saveYEHZDataFile(gzFile+File.separator+fn,dateString);
								}else if(fn.startsWith("kdk_tkmx")){
									log.info("*****************借据表信息********************");  
									saveTKMXDataFile(gzFile+File.separator+fn,dateString);
								}
							} 
						}catch(Exception e){
							e.printStackTrace();
							throw new RuntimeException(e);
						}
					}
					f.delete();
			}
        }
        log.info(dateString+"******************完成读取贷款文件********************");

	}*/
	
	/*
	 * 根据cardId获取user
	 */
	public List<CustomerInfor> findCustomerManagerIdById(String cardId){
		String sql = "select * from basic_customer_information where card_id='"+cardId+"'";
		List<CustomerInfor> list = commonDao.queryBySql(CustomerInfor.class, sql, null);
		return list;
	}
	/*
	 * 根据userid获取客户经理
	 */
	public String getUserInform(String id){
		SystemUser user = commonDao.findObjectById(SystemUser.class, id);
		if(user==null){
			return null;
		}else{
//			return user.getExternalId();
			return user.getDisplayName();
		}
	}
	/*
	 * 根据cardId获取风险名单
	 */
	public List<RiskCustomer> findRiskByCardId(String cardId){
		String sql = "select * from risk_list where CUSTOMER_ID in (select id from BASIC_CUSTOMER_INFORMATION where card_id='"+cardId+"')";
		List<RiskCustomer> list = commonDao.queryBySql(RiskCustomer.class, sql, null);
		return list;
	}
	/*
	 * 根据userid获取user
	 */
	public SystemUser getUseById(String id){
		SystemUser user = commonDao.findObjectById(SystemUser.class, id);
		if(user==null){
			return null;
		}else{
			return user;
		}
	}
	
	/**
	 * 查询贷款信息
	 * @param filter
	 * @return
	 */
	public QueryResult<TyRepayYehzVo> findCustomerYexxByFilter(IntoPiecesFilter filter) {
		List<TyRepayYehzVo> plans = customerInforDao.findCustomerYexxList(filter);
		int size = customerInforDao.findCustomerYexxCountList(filter);
		QueryResult<TyRepayYehzVo> queryResult = new QueryResult<TyRepayYehzVo>(size,plans);
		return queryResult;
	}
	
	
	/**
	 * 查询流水信息
	 */
	public QueryResult<TyRepayLsz> findRepayLszByFilter(CustomerInfoLszFilter filter) {
		List<TyRepayLsz> plans = customerInforDao.findRepayLszList(filter);
		int size = customerInforDao.findRepayLszCountList(filter);
		QueryResult<TyRepayLsz> queryResult = new QueryResult<TyRepayLsz>(size,plans);
		return queryResult;
	}
	
//============================================================太原====================================================
	/*
	 * 批量插入还款计划
	 */
	public void insertkdkjh(List<Map<String, Object>> list){
		 final List<Map<String, Object>> shopsList = list;
	        String sql =    "  insert into ty_kdk_jh (YWBH,  JHLX,QS,TS,RQ,JE,YHLX,BXHJ,DKYE,BZ )  values    (?,  ? ,  ? ,  ? ,  ? ,  ? ,  ? ,  ? ,  ? ,  ? )";
	          jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){
	            public void setValues(PreparedStatement ps,int i)throws SQLException
	               {
	                ps.setString(1, ((Map<String, Object>)shopsList.get(i)).get("ywbh").toString());
	                ps.setString(2, ((Map<String, Object>)shopsList.get(i)).get("jhlx").toString());
	                ps.setString(3, ((Map<String, Object>)shopsList.get(i)).get("qs").toString());
	                ps.setString(4, ((Map<String, Object>)shopsList.get(i)).get("ts").toString());
	                ps.setString(5, ((Map<String, Object>)shopsList.get(i)).get("rq").toString());
	                ps.setString(6, ((Map<String, Object>)shopsList.get(i)).get("je").toString());
	                ps.setString(7, ((Map<String, Object>)shopsList.get(i)).get("yhlx").toString());
	                ps.setString(8, ((Map<String, Object>)shopsList.get(i)).get("bxhj").toString());
	                ps.setString(9, ((Map<String, Object>)shopsList.get(i)).get("dkye").toString());
	                ps.setString(10, ((Map<String, Object>)shopsList.get(i)).get("bz").toString());
	               }
	               public int getBatchSize()
	               {
	                return shopsList.size();
	               }
	        });
	}
	
	/*
	 * 批量插入机构表
	 */
	public void insertjggl(List<Map<String, Object>> list){
		 final List<Map<String, Object>> shopsList = list;
	        String sql =    "  insert into ty_org (jgh,  jgmc )  values    (?,  ? )";
	          jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){
	            public void setValues(PreparedStatement ps,int i)throws SQLException
	               {
	                ps.setString(1, ((Map<String, Object>)shopsList.get(i)).get("jgh").toString());
	                ps.setString(2, ((Map<String, Object>)shopsList.get(i)).get("jgmc").toString());

	               }
	               public int getBatchSize()
	               {
	                return shopsList.size();
	               }
	        });
	}
	/**
	 * 保数据文件到”机构表“表
	 * @param fileName
	 * @return
	 * @throws Exception 
	 */
	public void saveJGDataFile(String fileName,String date) {
		try {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/tyJGGL.xml");

			// 解析”流水号“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			//批量插入
			insertkdkjh(datas);
			//释放空间
			datas=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 保数据文件到”还款计划“表
	 * @param fileName
	 * @return
	 * @throws Exception 
	 */
	public void saveJHDataFile(String fileName,String date) {
		try {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/tyKdkJh.xml");

			// 解析”还款计划“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			//批量插入insertjggl 
			insertkdkjh(datas);
			//释放空间
			datas=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//=============跑批
	/**
	 * 解析（原始信息）
	 * @throws IOException 
	 */
	public void readFile() throws IOException{
		log.info("yousuccess");
		//获取今日日期
	      //yyyyMMdd格式
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		String dateString = format.format(new Date());
		log.info(dateString+"******************开始读取原始信息文件********************"); 
		//+dateString
	        String gzFile = CardFtpUtils.bank_ftp_down_path+File.separator+dateString;
	        for(int i=0;i<fileTxt.length;i++){
	        	//File.separator
				String url = gzFile+File.separator+fileTxt[i];
				log.info(url);
				File f = new File(url);
				if(f.exists()){
					log.info("fsuccess======================");
						List<String> spFile = new ArrayList<String>();
						String fileN = "";
						//判断文件大小，超过50M的先分割
						if (f.exists() && f.isFile()){
							if(f.length()>10000000){
								int spCount = (int) (f.length()/10000000);
								SPTxt.splitTxt(url,spCount);
								int to = fileTxt[i].lastIndexOf('.');
						    	fileN = fileTxt[i].substring(0, to);
								for(int j=0;j<spCount;j++){
									spFile.add(fileN+"_"+j+".txt");
								}
							}else{
								int to = fileTxt[i].lastIndexOf('.');
						    	fileN = fileTxt[i].substring(0, to);
								spFile.add(fileN+".txt");
							}
						}
						for(String fn : spFile){
							try{
								if(fn.contains(fileN)) {
									/*log.info("*****************台帐表/gc合同表********************");  
									saveMibusidata(gzFile+fn,dateString);
									      readWholeAndIncrementService.saveGCCONTRACTMAINDataFile(gzFile+fn,dateString);
									readWholeAndIncrementService.saveGCCONTRACTMULTICLIENTDataFile(gzFile+fn,dateString);
									System.gc();*/
									
									if(fn.startsWith("cxd_rygl")){
										log.info("*****************人员管理参数表********************");  
										System.out.println(1111);
										//+File.separator
										saveRyglDataFile(gzFile+File.separator+fn,dateString);
										System.gc();
									}
									if(fn.startsWith("kkh_grxx")){
										log.info("*****************客户基本表********************");  
										saveBaseDataFile(gzFile+File.separator+fn,dateString);
										System.gc();
									}
									if(fn.startsWith("kkh_grjtcy")){
										log.info("*****************客户家庭关系表********************");  
										saveCyDataFile(gzFile+File.separator+fn,dateString);
									}
									if(fn.startsWith("kkh_grjtcc")){
										log.info("*****************客户家庭财产表********************");  
										saveCcDataFile(gzFile+File.separator+fn,dateString);
									}
									if(fn.startsWith("kkh_grxxll")){
										log.info("*****************客户学习表********************");  
										saveStudyDataFile(gzFile+File.separator+fn,dateString);
									}
									if(fn.startsWith("kkh_grgzll")){
										log.info("*****************客户工作履历表********************");  
										saveWorkDataFile(gzFile+File.separator+fn,dateString);
									}
									if(fn.startsWith("kkh_grscjy")){
										log.info("*****************客户生产经营表********************");  
									saveManageDataFile(gzFile+File.separator+fn,dateString);
								}
									if(fn.startsWith("kkh_grrbxx")){
										log.info("*****************客户入保信息表********************");  
										saveSafeDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("cxd_dkcpmc")){
										log.info("*****************产品信息********************");  
										saveProductDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("kkh_hmdgl")){
										log.info("*****************黑名单********************");  
										saveHMDDataFile(gzFile+File.separator+fn,dateString);
									}
								} 
							}catch(Exception e){
								e.printStackTrace();
								throw new RuntimeException(e);
							}
						}
						f.delete();
				}
	        }
	        log.info(dateString+"******************完成读取原始信息文件********************");

	}
	
	/**
	 *解析贷款信息
	 * @throws IOException 
	 */
	public void readFileRepay() throws IOException{
		//获取今日日期
	      //yyyyMMdd格式
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		String dateString = format.format(new Date());
		//+dateString
		String gzFile = CardFtpUtils.bank_ftp_down_path+File.separator+dateString;

		log.info(dateString+"******************开始读取贷款文件********************");  
        for(int i=0;i<fileTxtRepay.length;i++){
        	//+File.separator
			String url = gzFile+File.separator+fileTxtRepay[i];
			log.info(url);
			File f = new File(url);
			if(f.exists()){
				log.info("fsuccess");
					List<String> spFile = new ArrayList<String>();
					String fileN = "";
					//判断文件大小，超过50M的先分割
					if (f.exists() && f.isFile()){
						if(f.length()>10000000){
							int spCount = (int) (f.length()/10000000);
							SPTxt.splitTxt(url,spCount);
							int to = fileTxtRepay[i].lastIndexOf('.');
					    	fileN = fileTxtRepay[i].substring(0, to);
							for(int j=0;j<spCount;j++){
								spFile.add(fileN+"_"+j+".txt");
							}
						}else{
							int to = fileTxtRepay[i].lastIndexOf('.');
					    	fileN = fileTxtRepay[i].substring(0, to);
							spFile.add(fileN+".txt");
						}
					}
					
					if(fileN.startsWith("kdk_lsz")){
						//删除流水号历史数据
						String sql = " truncate   table   ty_repay_lsz";
						commonDao.queryBySql(sql, null);
					}
					if(fileN.startsWith("kdk_yehz")){
						//删除余额汇总历史数据
						String sql = " truncate   table   ty_repay_yehz";
						commonDao.queryBySql(sql, null);
					}
					if(fileN.startsWith("kdk_tkmx")){
						//删除借据表历史数据
						String sql = " truncate   table   ty_repay_tkmx";
						commonDao.queryBySql(sql, null);
					}
					if(fileN.startsWith("cxd_jggl")){
						//删除行内机构表历史数据
						String sql = " truncate   table   ty_org";
						commonDao.queryBySql(sql, null);
					}
					if(fileN.startsWith("kdk_jh")){
						//删除还款计划历史数据
						String sql = " truncate   table   ty_kdk_jh";
						commonDao.queryBySql(sql, null);
					}
					for(String fn : spFile){
						try{
							if(fn.contains(fileN)) {
								if(fn.startsWith("kdk_lsz")){
									log.info("*****************流水账信息********************");
									//+File.separator
									saveLSZDataFile(gzFile+File.separator+fn,dateString);
								}else if(fn.startsWith("kdk_yehz")){
									log.info("*****************余额汇总信息********************");  
									saveYEHZDataFile(gzFile+File.separator+fn,dateString);
								}else if(fn.startsWith("kdk_tkmx")){
									log.info("*****************借据表信息********************");  
									saveTKMXDataFile(gzFile+File.separator+fn,dateString);
								}else if(fn.startsWith("cxd_jggl")){
									log.info("*****************机构信息********************");  
									saveJGDataFile(gzFile+File.separator+File.separator+fn,dateString);
								}else if(fn.startsWith("kdk_jh")){
									log.info("*****************还款计划信息********************");  
									saveJHDataFile(gzFile+File.separator+fn,dateString);
								}
							} 
						}catch(Exception e){
							e.printStackTrace();
							throw new RuntimeException(e);
						}
					}
					f.delete();
			}
        }
        log.info(dateString+"******************完成读取贷款文件********************");

	}
	
	
	/*private Logger logger = Logger.getLogger(CustomerApplicationInfoSynchScheduleService.class);
	@Autowired
	private IntoPiecesService intoService;
	*//**
	 * 同步进件状态(更新为已放款)
	 * @throws IOException 
	 *//*
	private void dosynchMethod() throws IOException{
		//获取今日日期
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		String dateString = format.format(new Date());
		logger.info(dateString+"进件状态更新开始（已放款）**********");
		//查询已经审核通过的进件信息
		List<IntoPieces> intoPiecesList = intoService.findCustomerApplicationInfo();
		for(IntoPieces intoPieces:intoPiecesList){
			Boolean jjhExist = intoService.IfJjhExist(intoPieces.getJjh());
			if(!jjhExist){
				//更新进件申请表 进件状态 status、借据号关联
				IntoPieces  pieces = new IntoPieces();
				pieces.setStatus(Constant.END);//放款成功
				pieces.setId(intoPieces.getId());
				pieces.setJjh(intoPieces.getJjh());
				pieces.setJkrq(intoPieces.getJkrq());
				intoService.updateCustomerApplicationInfo(pieces);
			}
		}
		logger.info(dateString+"进件状态更新结束（已放款）**********");
		
		logger.info(dateString+"进件状态更新开始（还款结束）**********");
		//查询已经审核通过的进件信息
		List<CustomerApplicationInfo> intoPiecesEndList = intoService.findCustomerApplicationInfoEnd();
		for(CustomerApplicationInfo intoPieces:intoPiecesEndList){
			//更新进件申请表
			intoPieces.setStatus(Constant.REPAYEND);//还款结束
			commonDao.updateObject(intoPieces);
		}
		logger.info(dateString+"进件状态更新结束（还款结束）**********");
	}*/
	
	
//===============================================================JN================================================================//	
	//TODO：1、建表 2、根据表改SQL,需要改成济南的
	
	/**
	 * 授信申请基本信息
	 * @param fileName
	 * @param date
	 * @throws Exception 
	 */
	public void saveCCLMTAPPLYINFODataFile(String fileName,String date) throws Exception  {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/T_CCLMTAPPLYINFO.xml");
			// 解析”帐单记录表“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			//save
			insertCCLMTAPPLYINFO(datas);
			//释放空间
			datas=null;
	}
	
	/**
	 * 授信申请基本信息
	 * @param list
	 */
    public void insertCCLMTAPPLYINFO(List<Map<String, Object>> list){
        final List<Map<String, Object>> shopsList = list;
        String sql =    "insert into T_CCLMTAPPLYINFO  (APPCODE,							"+
                                                    "	PROCESSID,           			    "+
                                                    "	CUSTID,								"+
                                                    "	BUSISTATE,						    "+
                                                    "	CCTYPE,								"+
                                                    "	BUSITYPE,							"+
                                                    "	LOANPURPOSE,					    "+
                                                    "	LOWRISKFLAG,					    "+
                                                    "	LOANPURPOSEREMARK,		            "+
                                                    "	REQLMT,								"+
                                                    "	CURRENCY,							"+
                                                    "	BUSIMANAGER,					    "+
                                                    "	ASSISTBUSIMANAGER,		            "+
                                                    "	APPDATE,							"+
                                                    "	STATE,								"+
                                                    "	IFCORPLOAN,							"+
                                                    "	AUDITDATE,							"+
                                                    "	OPERDATETIME,						"+
                                                    "	OPERATOR,							"+
                                                    "	INSTCITYCODE,						"+
                                                    "	INSTCODE,							"+
                                                    "	DEPTCODE,							"+
                                                    "	LOANCARDFLAG,						"+
                                                    "	CREDITMODEL,						"+
                                                    "	ACCANTDEPT,							"+
                                                    "	IFFNONGCARD,CREATE_TIME)			"+
                "values                         (?,                              					"+
                                                    "    		    ?,                      		"+
                                                    "    		    ?,                              "+
                                                    "    		    ?,                              "+
                                                    "    		    ?,                              "+
                                                    "    		    ?,                              "+
                                                    "    		    ?,                              "+
                                                    "    		    ?,                              "+
                                                    "    		    ?,                              "+
                                                    "    		    ?,                              "+
                                                    "    		    ?,                              "+
                                                    "    		    ?,                              "+
                                                    "    		    ?,                              "+
                                                    "    		    ?,                              "+
                                                    "    		    ?,                              "+
                                                    "    		    ?,                              "+
                                                    "    		    ?,                              "+
                                                    "    		    ?,                              "+
                                                    "    		    ?,                              "+
                                                    "    		    ?,                              "+
                                                    "    		    ?,                              "+
                                                    "    		    ?,                              "+
                                                    "    		    ?,                              "+
                                                    "    		    ?,                              "+
                                                    "    		    ?,                              "+
                                                    "    		    ?,?)							";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){
            public void setValues(PreparedStatement ps,int i)throws SQLException
            {
                ps.setString(1, ((Map<String, Object>)shopsList.get(i)).get("appcode").toString());
                ps.setString(2, ((Map<String, Object>)shopsList.get(i)).get("processid").toString());
                ps.setString(3, ((Map<String, Object>)shopsList.get(i)).get("custid").toString());
                ps.setString(4, ((Map<String, Object>)shopsList.get(i)).get("busistate").toString());
                ps.setString(5, ((Map<String, Object>)shopsList.get(i)).get("cctype").toString());
                ps.setString(6, ((Map<String, Object>)shopsList.get(i)).get("busitype").toString());
                ps.setString(7, ((Map<String, Object>)shopsList.get(i)).get("loanpurpose").toString());
                ps.setString(8, ((Map<String, Object>)shopsList.get(i)).get("lowriskflag").toString());
                ps.setString(9, ((Map<String, Object>)shopsList.get(i)).get("loanpurposeremark").toString());
                ps.setString(10, ((Map<String, Object>) shopsList.get(i)).get("reqlmt").toString());
                ps.setString(11,((Map<String, Object>)shopsList.get(i)).get("currency").toString());
                ps.setString(12,((Map<String, Object>)shopsList.get(i)).get("busimanager").toString());
                ps.setString(13,((Map<String, Object>)shopsList.get(i)).get("assistbusimanager").toString());
                ps.setString(14, ((Map<String, Object>) shopsList.get(i)).get("appdate").toString());
                ps.setString(15,((Map<String, Object>)shopsList.get(i)).get("state").toString());
                ps.setString(16,((Map<String, Object>)shopsList.get(i)).get("ifcorploan").toString());
                ps.setString(17,((Map<String, Object>)shopsList.get(i)).get("auditdate").toString());
                ps.setString(18,((Map<String, Object>)shopsList.get(i)).get("operdatetime").toString());
                ps.setString(19,((Map<String, Object>)shopsList.get(i)).get("operator").toString());
                ps.setString(20,((Map<String, Object>)shopsList.get(i)).get("instcitycode").toString());
                ps.setString(21,((Map<String, Object>)shopsList.get(i)).get("instcode").toString());
                ps.setString(22,((Map<String, Object>)shopsList.get(i)).get("deptcode").toString());
                ps.setString(23,((Map<String, Object>)shopsList.get(i)).get("loancardflag").toString());
                ps.setString(24,((Map<String, Object>)shopsList.get(i)).get("creditmodel").toString());
                ps.setString(25,((Map<String, Object>)shopsList.get(i)).get("accantdept").toString());
                ps.setString(26,((Map<String, Object>)shopsList.get(i)).get("iffnongcard").toString());
                ps.setString(27,((Map<String, Object>)shopsList.get(i)).get("createTime").toString());
            }
            public int getBatchSize()
            {
                return shopsList.size();
            }
        });
    }
    
    
    
    /**
     *对私客户不良记录 
     */
    public void saveCIPERSONBADRECORDDataFile(String fileName,String date) throws Exception {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/T_CIPERSONBADRECORD.xml");
			// 解析”帐单记录表“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			//save
			insertCIPERSONBADRECORD(datas);
			//释放空间
			datas=null;
	}
    
    /**
     * 对私客户不良记录
     * @param list 
     */
    public void insertCIPERSONBADRECORD(List<Map<String, Object>> list){
        final List<Map<String, Object>> shopsList = list;
        String sql =    "  insert into T_CIPERSONBADRECORD (    ID,"+
                                                            "CUSTID,"+
                                                            "SUBJECT,"+
                                                            "RECORDDATE,"+
                                                            "BADDESCRIPTION,"+
                                                            "OPERATOR,"+
                                                            "INSTCITYCODE,"+
                                                            "REFERAOMUNT,"+
                                                            "OPERDATETIME,CREATE_TIME)"+
                "    values                        (?,                               "+
                                    "    		    ?,                               "+
                                    "    		    ?,                               "+
                                    "    		    ?,                               "+
                                    "    		    ?,                               "+
                                    "    		    ?,                               "+
                                    "    		    ?,                               "+
                                    "    		    ?,                               "+
                                    "    		    ?,?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){
            public void setValues(PreparedStatement ps,int i)throws SQLException
            {
                ps.setString(1, ((Map<String, Object>)shopsList.get(i)).get("id").toString());
                ps.setString(2, ((Map<String, Object>)shopsList.get(i)).get("custid").toString());
                ps.setString(3, ((Map<String, Object>)shopsList.get(i)).get("subject").toString());
                ps.setString(4, ((Map<String, Object>)shopsList.get(i)).get("recorddate").toString());
                ps.setString(5, ((Map<String, Object>)shopsList.get(i)).get("baddescription").toString());
                ps.setString(6, ((Map<String, Object>)shopsList.get(i)).get("operator").toString());
                ps.setString(7, ((Map<String, Object>)shopsList.get(i)).get("instcitycode").toString());
                ps.setString(8, ((Map<String, Object>)shopsList.get(i)).get("referaomunt").toString());
                ps.setString(9, ((Map<String, Object>)shopsList.get(i)).get("operdatetime").toString());
                ps.setString(10, ((Map<String, Object>)shopsList.get(i)).get("createTime").toString());
            }
            public int getBatchSize()
            {
                return shopsList.size();
            }
        });
    }
    
    /**
     *对私客户基本信息
     * @param list
     */
    public void saveCIPERSONBASINFODataFile(String fileName,String date) throws Exception{
			//add
			List<Map<String, Object>> insertdatas = new ArrayList<Map<String,Object>>();
			//update
			List<Map<String, Object>> updatedatas = new ArrayList<Map<String,Object>>();
			
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/T_CIPERSONBASINFO.xml");
			// 解析”帐单记录表“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			
			//for
			for(Map<String, Object> map : datas){
				int count = customerInforDao.findCIPERSONBASINFOCount(map);
				if(count >0){
					log.info("*************************updatedatas*************************");
					//put updateMap
					updatedatas.add(map);
					//变更 同步本系统
					List<SystemUser> user = commonDao.queryBySql(SystemUser.class, 
							"select id from sys_user where external_id='"+map.get("busimanager").toString()+"'", null);
					List<CustomerInfor> infoList = commonDao.queryBySql(CustomerInfor.class, 
							"select * from basic_customer_information where card_id='"+map.get("cardnum").toString()+"' and CARD_TYPE='"+map.get("cardtype").toString()+"'", null);
					//存在则更新客户经理id
					if(infoList.size()>0){
						CustomerInfor info = infoList.get(0);
						info.setUserId(user.size() > 0?user.get(0).getId():map.get("busimanager").toString());
						commonDao.updateObject(info);
					}
				}else{
					log.info("*************************insertdatas*************************");
					//put insertMap
					insertdatas.add(map);
					//和本系统作关联
					List<SystemUser> user = commonDao.queryBySql(SystemUser.class, 
							"select id from sys_user where external_id='"+map.get("busimanager").toString()+"'", null);
					List<CustomerInfor> infoList = commonDao.queryBySql(CustomerInfor.class, 
							"select * from basic_customer_information where card_id='"+map.get("cardnum").toString()+"' and CARD_TYPE='"+map.get("cardtype").toString()+"'", null);
					//不存在则插入
					if(infoList.size()==0){
						CustomerInfor info = new CustomerInfor();
						info.setId(IDGenerator.generateID());
						info.setCardId(map.get("cardnum").toString());
						info.setCardType(map.get("cardtype").toString());
						info.setChineseName(map.get("cname").toString());
						info.setUserId(user.size() > 0?user.get(0).getId():map.get("busimanager").toString());
						info.setTyCustomerId(map.get("id").toString());
						commonDao.insertObject(info);
					}else{
						//存在则作关联
						CustomerInfor info = infoList.get(0);
						info.setTyCustomerId(map.get("id").toString());
						commonDao.updateObject(info);
					}
				}
			}
			//save
			insertCIPERSONBASINFO(insertdatas);
			//update
			updateCIPERSONBASINFO(updatedatas);
			//释放空间
			insertdatas=null;
			updatedatas=null;
			datas=null;
	}

    /**
     * 对私客户基本信息
     * @param list
     * insert
     */
    public void insertCIPERSONBASINFO(List<Map<String, Object>> list){
        final List<Map<String, Object>> shopsList = list;
        String sql =    "  insert into T_CIPERSONBASINFO (   ID,"+
                                                            "INITID,"+
                                                            "CUSTID,"+
                                                            "TYPEID,"+
                                                            "CUSTTYPE,"+
                                                            "CNAME,"+
                                                            "SEX,"+
                                                            "CARDTYPE,"+
                                                            "CARDNUM,"+
                                                            "ETHICAL,"+
                                                            "COUNTRY,"+
                                                            "BIRTHDAY,"+
                                                            "SYADDR,"+
                                                            "USEFULLIFE,"+
                                                            "SIGNORG,"+
                                                            "CUSTPROPERTY,"+
                                                            "EMPLOYER,"+
                                                            "EMPLOYERADDR,"+
                                                            "EMPLOYERPOSTCODE,"+
                                                            "EMPLOYERTIME,"+
                                                            "CATEGORYID,"+
                                                            "TITLE,"+
                                                            "VOCATION,"+
                                                            "DUTY,"+
                                                            "REGISTERCODE,"+
                                                            "CODENAME,"+
                                                            "MANAGERNAME,"+
                                                            "MANAGEPLACE,"+
                                                            "MANAGEEXTENT,"+
                                                            "FOUNDDATE,"+
                                                            "ANNUCHECK,"+
                                                            "SCHOOL,"+
                                                            "SCHOOLADDR,"+
                                                            "SCHOOLATTRIB,"+
                                                            "SCHOOLTEL,"+
                                                            "MARRIGE,"+
                                                            "ISREFERAGRICULTURAL,"+
                                                            "CITY,"+
                                                            "DISTRICTCOUNTY,"+
                                                            "TOWN,"+
                                                            "COMMUNITY,"+
                                                            "CONTACTMOBILETEL,"+
                                                            "CONTACTTEL,"+
                                                            "ADDRESS,"+
                                                            "POSTCODE,"+
                                                            "MESSAGEADDR,"+
                                                            "COMMPOSTCODE,"+
                                                            "EDUCATIONLEVEL,"+
                                                            "DEGREE,"+
                                                            "INDUSTRY,"+
                                                            "RESIDE,"+
                                                            "LEVELCAL,"+
                                                            "JOINRING,"+
                                                            "FAMASSET,"+
                                                            "FAMDEBT,"+
                                                            "FAMANNUINCOME,"+
                                                            "FAMANNUPAYOUT,"+
                                                            "RELAMAN,"+
                                                            "EMAIL,"+
                                                            "INCOMEACCOUNT,"+
                                                            "INCOMEBANK,"+
                                                            "BUSIMANAGER,"+
                                                            "INSTCODE,"+
                                                            "DEPTCODE,"+
                                                            "INSTCITYCODE,"+
                                                            "ISTMP,"+
                                                            "VERSION,"+
                                                            "DISPOSALSTATUS,"+
                                                            "STATUS,"+
                                                            "REMARK,"+
                                                            "INPUTMAN,"+
                                                            "INPUTDATE,"+
                                                            "GROUPID,"+
                                                            "OPERATOR,"+
                                                            "OPERDATETIME,"+
                                                            "CUSTSTATUS,"+
                                                            "ASSISTBUSIMANAGE,"+
                                                            "FAMNUM,"+
                                                            "FIRSTMANAGER,"+
                                                            "GOVERNMANAGER,"+
                                                            "ISTRANS,"+
                                                            "HUKOU,"+
                                                            "BACKOPERATOR,"+
                                                            "BUSIFLAG,CREATE_TIME)"+
                "    values                         (?,                                 "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,                              "+
                                        "    		    ?,?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){
            public void setValues(PreparedStatement ps,int i)throws SQLException
            {
                ps.setString(1, ((Map<String, Object>)shopsList.get(i)).get("id").toString());
                ps.setString(2, ((Map<String, Object>)shopsList.get(i)).get("initid").toString());
                ps.setString(3, ((Map<String, Object>)shopsList.get(i)).get("custid").toString());
                ps.setString(4, ((Map<String, Object>)shopsList.get(i)).get("typeid").toString());
                ps.setString(5, ((Map<String, Object>)shopsList.get(i)).get("custtype").toString());
                ps.setString(6, ((Map<String, Object>)shopsList.get(i)).get("cname").toString());
                ps.setString(7, ((Map<String, Object>)shopsList.get(i)).get("sex").toString());
                ps.setString(8, ((Map<String, Object>)shopsList.get(i)).get("cardtype").toString());
                ps.setString(9, ((Map<String, Object>)shopsList.get(i)).get("cardnum").toString());
                ps.setString(10, ((Map<String, Object>)shopsList.get(i)).get("ethical").toString());
                ps.setString(11, ((Map<String, Object>)shopsList.get(i)).get("country").toString());
                ps.setString(12, ((Map<String, Object>)shopsList.get(i)).get("birthday").toString());
                ps.setString(13, ((Map<String, Object>)shopsList.get(i)).get("syaddr").toString());
                ps.setString(14, ((Map<String, Object>)shopsList.get(i)).get("usefullife").toString());
                ps.setString(15, ((Map<String, Object>)shopsList.get(i)).get("signorg").toString());
                ps.setString(16, ((Map<String, Object>)shopsList.get(i)).get("custproperty").toString());
                ps.setString(17, ((Map<String, Object>)shopsList.get(i)).get("employer").toString());
                ps.setString(18, ((Map<String, Object>)shopsList.get(i)).get("employeraddr").toString());
                ps.setString(19, ((Map<String, Object>)shopsList.get(i)).get("employerpostcode").toString());
                ps.setString(20, ((Map<String, Object>)shopsList.get(i)).get("employertime").toString());
                ps.setString(21, ((Map<String, Object>)shopsList.get(i)).get("categoryid").toString());
                ps.setString(22, ((Map<String, Object>)shopsList.get(i)).get("title").toString());
                ps.setString(23, ((Map<String, Object>)shopsList.get(i)).get("vocation").toString());
                ps.setString(24, ((Map<String, Object>)shopsList.get(i)).get("duty").toString());
                ps.setString(25, ((Map<String, Object>)shopsList.get(i)).get("registercode").toString());
                ps.setString(26, ((Map<String, Object>)shopsList.get(i)).get("codename").toString());
                ps.setString(27, ((Map<String, Object>)shopsList.get(i)).get("managername").toString());
                ps.setString(28, ((Map<String, Object>)shopsList.get(i)).get("manageplace").toString());
                ps.setString(29, ((Map<String, Object>)shopsList.get(i)).get("manageextent").toString());
                ps.setString(30, ((Map<String, Object>)shopsList.get(i)).get("founddate").toString());
                ps.setString(31, ((Map<String, Object>)shopsList.get(i)).get("annucheck").toString());
                ps.setString(32, ((Map<String, Object>)shopsList.get(i)).get("school").toString());
                ps.setString(33, ((Map<String, Object>)shopsList.get(i)).get("schooladdr").toString());
                ps.setString(34, ((Map<String, Object>)shopsList.get(i)).get("schoolattrib").toString());
                ps.setString(35, ((Map<String, Object>)shopsList.get(i)).get("schooltel").toString());
                ps.setString(36, ((Map<String, Object>)shopsList.get(i)).get("marrige").toString());
                ps.setString(37, ((Map<String, Object>)shopsList.get(i)).get("isreferagricultural").toString());
                ps.setString(38, ((Map<String, Object>)shopsList.get(i)).get("city").toString());
                ps.setString(39, ((Map<String, Object>)shopsList.get(i)).get("districtcounty").toString());
                ps.setString(40, ((Map<String, Object>)shopsList.get(i)).get("town").toString());
                ps.setString(41, ((Map<String, Object>)shopsList.get(i)).get("community" ).toString());
                ps.setString(42, ((Map<String, Object>)shopsList.get(i)).get("contactmobiletel").toString());
                ps.setString(43, ((Map<String, Object>)shopsList.get(i)).get("contacttel").toString());
                ps.setString(44, ((Map<String, Object>)shopsList.get(i)).get("address").toString());
                ps.setString(45, ((Map<String, Object>)shopsList.get(i)).get("postcode").toString());
                ps.setString(46, ((Map<String, Object>)shopsList.get(i)).get("messageaddr").toString());
                ps.setString(47, ((Map<String, Object>)shopsList.get(i)).get("commpostcode").toString());
                ps.setString(48, ((Map<String, Object>)shopsList.get(i)).get("educationlevel").toString());
                ps.setString(49, ((Map<String, Object>)shopsList.get(i)).get("degree").toString());
                ps.setString(50, ((Map<String, Object>)shopsList.get(i)).get("industry").toString());
                ps.setString(51, ((Map<String, Object>)shopsList.get(i)).get("reside").toString());
                ps.setString(52, ((Map<String, Object>)shopsList.get(i)).get("levelcal").toString());
                ps.setString(53, ((Map<String, Object>)shopsList.get(i)).get("joinring").toString());
                ps.setString(54, ((Map<String, Object>)shopsList.get(i)).get("famasset").toString());
                ps.setString(55, ((Map<String, Object>)shopsList.get(i)).get("famdebt").toString());
                ps.setString(56, ((Map<String, Object>)shopsList.get(i)).get("famannuincome").toString());
                ps.setString(57, ((Map<String, Object>)shopsList.get(i)).get("famannupayout").toString());
                ps.setString(58, ((Map<String, Object>)shopsList.get(i)).get("relaman").toString());
                ps.setString(59, ((Map<String, Object>)shopsList.get(i)).get("email").toString());
                ps.setString(60, ((Map<String, Object>)shopsList.get(i)).get("incomeaccount").toString());
                ps.setString(61, ((Map<String, Object>)shopsList.get(i)).get("incomebank").toString());
                ps.setString(62, ((Map<String, Object>)shopsList.get(i)).get("busimanager").toString());
                ps.setString(63, ((Map<String, Object>)shopsList.get(i)).get("instcode").toString());
                ps.setString(64, ((Map<String, Object>)shopsList.get(i)).get("deptcode").toString());
                ps.setString(65, ((Map<String, Object>)shopsList.get(i)).get("instcitycode").toString());
                ps.setString(66, ((Map<String, Object>)shopsList.get(i)).get("istmp").toString());
                ps.setString(67, ((Map<String, Object>)shopsList.get(i)).get("version").toString());
                ps.setString(68, ((Map<String, Object>)shopsList.get(i)).get("disposalstatus").toString());
                ps.setString(69, ((Map<String, Object>)shopsList.get(i)).get("status").toString());
                ps.setString(70, ((Map<String, Object>)shopsList.get(i)).get("remark").toString());
                ps.setString(71, ((Map<String, Object>)shopsList.get(i)).get("inputman").toString());
                ps.setString(72, ((Map<String, Object>)shopsList.get(i)).get("inputdate").toString());
                ps.setString(73, ((Map<String, Object>)shopsList.get(i)).get("groupid").toString());
                ps.setString(74, ((Map<String, Object>)shopsList.get(i)).get("operator").toString());
                ps.setString(75, ((Map<String, Object>)shopsList.get(i)).get("operdatetime").toString());
                ps.setString(76, ((Map<String, Object>)shopsList.get(i)).get("custstatus").toString());
                ps.setString(77, ((Map<String, Object>)shopsList.get(i)).get("assistbusimanage").toString());
                ps.setString(78, ((Map<String, Object>)shopsList.get(i)).get("famnum").toString());
                ps.setString(79, ((Map<String, Object>)shopsList.get(i)).get("firstmanager").toString());
                ps.setString(80, ((Map<String, Object>)shopsList.get(i)).get("governmanager").toString());
                ps.setString(81, ((Map<String, Object>)shopsList.get(i)).get("istrans").toString());
                ps.setString(82, ((Map<String, Object>)shopsList.get(i)).get("hukou").toString());
                ps.setString(83, ((Map<String, Object>)shopsList.get(i)).get("backoperator").toString());
                ps.setString(84, ((Map<String, Object>)shopsList.get(i)).get("busiflag").toString());
                ps.setString(85, ((Map<String, Object>)shopsList.get(i)).get("createTime").toString());
            }
            public int getBatchSize()
            {
                return shopsList.size();
            }
        });
    }
    
    
    
    /**
     * 对私客户基本信息
     * @param list
     * update
     */
    public void updateCIPERSONBASINFO(List<Map<String, Object>> list){
        final List<Map<String, Object>> shopsList = list;
        String sql =    " update T_CIPERSONBASINFO set    "+
		        		"  id 				  = ?         "+         
		        		"  ,initid 	          = ? 		  "+		              
		        		"  ,custid  		  = ?         "+    
		        		"  ,typeid  		  = ?         "+     
		        		"  ,custtype		  = ?         "+   
		        		"  ,cname          	  = ?         "+  
		        		"  ,sex               = ?         "+   
		        		"  ,cardtype          = ?         "+ 
		        		"  ,cardnum           = ?         "+ 
		        		"  ,ethical           = ?         "+
		        		"  ,country           = ?         "+
		        		"  ,birthday          = ?         "+
		        		"  ,syaddr            = ?         "+
		        		"  ,usefullife        = ?         "+
		        		"  ,signorg           = ?         "+
		        		"  ,custproperty      = ?         "+
		        		"  ,employer          = ?         "+
		        		"  ,employeraddr      = ?         "+
		        		"  ,employerpostcode  = ?         "+
		        		"  ,employertime      = ?         "+
		        		"  ,categoryid        = ?         "+
		        		"  ,title             = ?         "+ 
		        		"  ,vocation          = ?         "+
		        		"  ,duty              = ?         "+
		        		"  ,registercode      = ?         "+
		        		"  ,codename          = ?         "+
		        		"  ,managername       = ?         "+
		        		"  ,manageplace       = ?         "+
		        		"  ,manageextent      = ?         "+
		        		"  ,founddate         = ?         "+
		        		"  ,annucheck         = ?         "+
		        		"  ,school            = ?         "+
		        		"  ,schooladdr        = ?         "+
		        		"  ,schoolattrib      = ?         "+
		        		"  ,schooltel         = ?         "+
		        		"  ,marrige           = ?         "+
		        		"  ,isreferagricultural = ?       "+
		        		"  ,city              = ?         "+
		        		"  ,districtcounty    = ?         "+
		        		"  ,town              = ?         "+
		        		"  ,community         = ?         "+
		        		"  ,contactmobiletel  = ?         "+
		        		"  ,contacttel        = ?         "+
		        		"  ,address           = ?         "+
		        		"  ,postcode          = ?         "+
		        		"  ,messageaddr       = ?         "+
		        		"  ,commpostcode      = ?         "+
		        		"  ,educationlevel    = ?         "+
		        		"  ,degree            = ?         "+
		        		"  ,industry          = ?         "+
		        		"  ,reside            = ?         "+
		        		"  ,levelcal          = ?         "+
		        		"  ,joinring          = ?         "+
		        		"  ,famasset          = ?         "+
		        		"  ,famdebt           = ?         "+
		        		"  ,famannuincome     = ?         "+
		        		"  ,famannupayout     = ?         "+
		        		"  ,relaman           = ?         "+
		        		"  ,email             = ?         "+
		        		"  ,incomeaccount     = ?         "+
		        		"  ,incomebank        = ?         "+
		        		"  ,busimanager       = ?         "+
		        		"  ,instcode          = ?         "+
		        		"  ,deptcode          = ?         "+
		        		"  ,instcitycode      = ?         "+
		        		"  ,istmp             = ?         "+
		        		"  ,version           = ?         "+
		        		"  ,disposalstatus    = ?         "+
		        		"  ,status            = ?         "+
		        		"  ,remark            = ?         "+
		        		"  ,inputman          = ?         "+
		        		"  ,inputdate         = ?         "+
		        		"  ,groupid           = ?         "+
		        		"  ,operator          = ?         "+
		        		"  ,operdatetime      = ?         "+
		        		"  ,custstatus        = ?         "+
		        		"  ,assistbusimanage  = ?         "+
		        		"  ,famnum            = ?         "+
		        		"  ,firstmanager      = ?         "+
		        		"  ,governmanager     = ?         "+
		        		"  ,istrans           = ?         "+
		        		"  ,hukou             = ?         "+
		        		"  ,backoperator      = ?         "+
		        		"  ,busiflag          = ?         "+
		        		"  ,CREATE_TIME       = ?         "+
		        		"  where  custid 	  = ?         ";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){
            public void setValues(PreparedStatement ps,int i)throws SQLException
            {
            	ps.setString(1, ((Map<String, Object>)shopsList.get(i)).get("id").toString());
                ps.setString(2, ((Map<String, Object>)shopsList.get(i)).get("initid").toString());
                ps.setString(3, ((Map<String, Object>)shopsList.get(i)).get("custid").toString());
                ps.setString(4, ((Map<String, Object>)shopsList.get(i)).get("typeid").toString());
                ps.setString(5, ((Map<String, Object>)shopsList.get(i)).get("custtype").toString());
                ps.setString(6, ((Map<String, Object>)shopsList.get(i)).get("cname").toString());
                ps.setString(7, ((Map<String, Object>)shopsList.get(i)).get("sex").toString());
                ps.setString(8, ((Map<String, Object>)shopsList.get(i)).get("cardtype").toString());
                ps.setString(9, ((Map<String, Object>)shopsList.get(i)).get("cardnum").toString());
                ps.setString(10, ((Map<String, Object>)shopsList.get(i)).get("ethical").toString());
                ps.setString(11, ((Map<String, Object>)shopsList.get(i)).get("country").toString());
                ps.setString(12, ((Map<String, Object>)shopsList.get(i)).get("birthday").toString());
                ps.setString(13, ((Map<String, Object>)shopsList.get(i)).get("syaddr").toString());
                ps.setString(14, ((Map<String, Object>)shopsList.get(i)).get("usefullife").toString());
                ps.setString(15, ((Map<String, Object>)shopsList.get(i)).get("signorg").toString());
                ps.setString(16, ((Map<String, Object>)shopsList.get(i)).get("custproperty").toString());
                ps.setString(17, ((Map<String, Object>)shopsList.get(i)).get("employer").toString());
                ps.setString(18, ((Map<String, Object>)shopsList.get(i)).get("employeraddr").toString());
                ps.setString(19, ((Map<String, Object>)shopsList.get(i)).get("employerpostcode").toString());
                ps.setString(20, ((Map<String, Object>)shopsList.get(i)).get("employertime").toString());
                ps.setString(21, ((Map<String, Object>)shopsList.get(i)).get("categoryid").toString());
                ps.setString(22, ((Map<String, Object>)shopsList.get(i)).get("title").toString());
                ps.setString(23, ((Map<String, Object>)shopsList.get(i)).get("vocation").toString());
                ps.setString(24, ((Map<String, Object>)shopsList.get(i)).get("duty").toString());
                ps.setString(25, ((Map<String, Object>)shopsList.get(i)).get("registercode").toString());
                ps.setString(26, ((Map<String, Object>)shopsList.get(i)).get("codename").toString());
                ps.setString(27, ((Map<String, Object>)shopsList.get(i)).get("managername").toString());
                ps.setString(28, ((Map<String, Object>)shopsList.get(i)).get("manageplace").toString());
                ps.setString(29, ((Map<String, Object>)shopsList.get(i)).get("manageextent").toString());
                ps.setString(30, ((Map<String, Object>)shopsList.get(i)).get("founddate").toString());
                ps.setString(31, ((Map<String, Object>)shopsList.get(i)).get("annucheck").toString());
                ps.setString(32, ((Map<String, Object>)shopsList.get(i)).get("school").toString());
                ps.setString(33, ((Map<String, Object>)shopsList.get(i)).get("schooladdr").toString());
                ps.setString(34, ((Map<String, Object>)shopsList.get(i)).get("schoolattrib").toString());
                ps.setString(35, ((Map<String, Object>)shopsList.get(i)).get("schooltel").toString());
                ps.setString(36, ((Map<String, Object>)shopsList.get(i)).get("marrige").toString());
                ps.setString(37, ((Map<String, Object>)shopsList.get(i)).get("isreferagricultural").toString());
                ps.setString(38, ((Map<String, Object>)shopsList.get(i)).get("city").toString());
                ps.setString(39, ((Map<String, Object>)shopsList.get(i)).get("districtcounty").toString());
                ps.setString(40, ((Map<String, Object>)shopsList.get(i)).get("town").toString());
                ps.setString(41, ((Map<String, Object>)shopsList.get(i)).get("community" ).toString());
                ps.setString(42, ((Map<String, Object>)shopsList.get(i)).get("contactmobiletel").toString());
                ps.setString(43, ((Map<String, Object>)shopsList.get(i)).get("contacttel").toString());
                ps.setString(44, ((Map<String, Object>)shopsList.get(i)).get("address").toString());
                ps.setString(45, ((Map<String, Object>)shopsList.get(i)).get("postcode").toString());
                ps.setString(46, ((Map<String, Object>)shopsList.get(i)).get("messageaddr").toString());
                ps.setString(47, ((Map<String, Object>)shopsList.get(i)).get("commpostcode").toString());
                ps.setString(48, ((Map<String, Object>)shopsList.get(i)).get("educationlevel").toString());
                ps.setString(49, ((Map<String, Object>)shopsList.get(i)).get("degree").toString());
                ps.setString(50, ((Map<String, Object>)shopsList.get(i)).get("industry").toString());
                ps.setString(51, ((Map<String, Object>)shopsList.get(i)).get("reside").toString());
                ps.setString(52, ((Map<String, Object>)shopsList.get(i)).get("levelcal").toString());
                ps.setString(53, ((Map<String, Object>)shopsList.get(i)).get("joinring").toString());
                ps.setString(54, ((Map<String, Object>)shopsList.get(i)).get("famasset").toString());
                ps.setString(55, ((Map<String, Object>)shopsList.get(i)).get("famdebt").toString());
                ps.setString(56, ((Map<String, Object>)shopsList.get(i)).get("famannuincome").toString());
                ps.setString(57, ((Map<String, Object>)shopsList.get(i)).get("famannupayout").toString());
                ps.setString(58, ((Map<String, Object>)shopsList.get(i)).get("relaman").toString());
                ps.setString(59, ((Map<String, Object>)shopsList.get(i)).get("email").toString());
                ps.setString(60, ((Map<String, Object>)shopsList.get(i)).get("incomeaccount").toString());
                ps.setString(61, ((Map<String, Object>)shopsList.get(i)).get("incomebank").toString());
                ps.setString(62, ((Map<String, Object>)shopsList.get(i)).get("busimanager").toString());
                ps.setString(63, ((Map<String, Object>)shopsList.get(i)).get("instcode").toString());
                ps.setString(64, ((Map<String, Object>)shopsList.get(i)).get("deptcode").toString());
                ps.setString(65, ((Map<String, Object>)shopsList.get(i)).get("instcitycode").toString());
                ps.setString(66, ((Map<String, Object>)shopsList.get(i)).get("istmp").toString());
                ps.setString(67, ((Map<String, Object>)shopsList.get(i)).get("version").toString());
                ps.setString(68, ((Map<String, Object>)shopsList.get(i)).get("disposalstatus").toString());
                ps.setString(69, ((Map<String, Object>)shopsList.get(i)).get("status").toString());
                ps.setString(70, ((Map<String, Object>)shopsList.get(i)).get("remark").toString());
                ps.setString(71, ((Map<String, Object>)shopsList.get(i)).get("inputman").toString());
                ps.setString(72, ((Map<String, Object>)shopsList.get(i)).get("inputdate").toString());
                ps.setString(73, ((Map<String, Object>)shopsList.get(i)).get("groupid").toString());
                ps.setString(74, ((Map<String, Object>)shopsList.get(i)).get("operator").toString());
                ps.setString(75, ((Map<String, Object>)shopsList.get(i)).get("operdatetime").toString());
                ps.setString(76, ((Map<String, Object>)shopsList.get(i)).get("custstatus").toString());
                ps.setString(77, ((Map<String, Object>)shopsList.get(i)).get("assistbusimanage").toString());
                ps.setString(78, ((Map<String, Object>)shopsList.get(i)).get("famnum").toString());
                ps.setString(79, ((Map<String, Object>)shopsList.get(i)).get("firstmanager").toString());
                ps.setString(80, ((Map<String, Object>)shopsList.get(i)).get("governmanager").toString());
                ps.setString(81, ((Map<String, Object>)shopsList.get(i)).get("istrans").toString());
                ps.setString(82, ((Map<String, Object>)shopsList.get(i)).get("hukou").toString());
                ps.setString(83, ((Map<String, Object>)shopsList.get(i)).get("backoperator").toString());
                ps.setString(84, ((Map<String, Object>)shopsList.get(i)).get("busiflag").toString());
                ps.setString(85, ((Map<String, Object>)shopsList.get(i)).get("createTime").toString());
                ps.setString(86, ((Map<String, Object>)shopsList.get(i)).get("custid").toString());
            }
            public int getBatchSize()
            {
                return shopsList.size();
            }
        });
    }
    
    
    /**
     * 对私家庭成员信息
     * @param list
     */
    public void saveCIPERSONFAMILYDataFile(String fileName,String date)throws Exception {
			//add
			List<Map<String, Object>> insertdatas = new ArrayList<Map<String,Object>>();
			//update
			List<Map<String, Object>> updatedatas = new ArrayList<Map<String,Object>>();
			
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/T_CIPERSONFAMILY.xml");
			
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			//for
			for(Map<String, Object> map : datas){
				int count = customerInforDao.findCipersonfamilyCount(map);
				if(count >0){
					updatedatas.add(map);
				}else{
					insertdatas.add(map);
				}
			}
			//save
			insertCIPERSONFAMILY(insertdatas);
			//update
			updateCIPERSONFAMILY(updatedatas);
			//释放空间
			updatedatas = null;
			insertdatas =null;
			datas=null;
	}

   /**
    * 对私家庭成员信息
    * @param list
    * insert
    */
    public void insertCIPERSONFAMILY(List<Map<String, Object>> list){
        final List<Map<String, Object>> shopsList = list;
        String sql =    "  insert into T_CIPERSONFAMILY (    ID,"+
                                                            "PARTYID,"+
                                                            "PARTYINITID,"+
                                                            "CUSTID,"+
                                                            "TYPEID,"+
                                                            "NAME,"+
                                                            "SEX,"+
                                                            "FAMILYMBERCCTION,"+
                                                            "PAPERTYPE,"+
                                                            "PAPERCODE,"+
                                                            "MOVEPHONE,"+
                                                            "FIXATIONPHONE,"+
                                                            "JOBUNIT,"+
                                                            "CREATEDUSER,"+
                                                            "CREATEDTIME,"+
                                                            "GROUPID,"+
                                                            "INSTCITYCODE,"+
                                                            "DELETEREASON,"+
                                                            "STATUS,"+
                                                            "OPERATOR,"+
                                                            "OPERDATETIME,"+
                                                            "ISTRANS,CREATE_TIME)"+
                "    values                        (?,                              "+
                                    "    		    ?,                              "+
                                    "    		    ?,                              "+
                                    "    		    ?,                              "+
                                    "    		    ?,                              "+
                                    "    		    ?,                              "+
                                    "    		    ?,                              "+
                                    "    		    ?,                              "+
                                    "    		    ?,                              "+
                                    "    		    ?,                              "+
                                    "    		    ?,                              "+
                                    "    		    ?,                              "+
                                    "    		    ?,                              "+
                                    "    		    ?,                              "+
                                    "    		    ?,                              "+
                                    "    		    ?,                              "+
                                    "    		    ?,                              "+
                                    "    		    ?,                              "+
                                    "    		    ?,                              "+
                                    "    		    ?,                              "+
                                    "    		    ?,                              "+
                                    "    		    ?,?)								";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){
            public void setValues(PreparedStatement ps,int i)throws SQLException
            {
                ps.setString(1, ((Map<String, Object>)shopsList.get(i)).get("id").toString());
                ps.setString(2, ((Map<String, Object>)shopsList.get(i)).get("partyid").toString());
                ps.setString(3, ((Map<String, Object>)shopsList.get(i)).get("partyinitid").toString());
                ps.setString(4, ((Map<String, Object>)shopsList.get(i)).get("custid").toString());
                ps.setString(5, ((Map<String, Object>)shopsList.get(i)).get("typeid").toString());
                ps.setString(6, ((Map<String, Object>)shopsList.get(i)).get("name").toString());
                ps.setString(7, ((Map<String, Object>)shopsList.get(i)).get("sex").toString());
                ps.setString(8, ((Map<String, Object>)shopsList.get(i)).get("familymbercction").toString());
                ps.setString(9, ((Map<String, Object>)shopsList.get(i)).get("papertype").toString());
                ps.setString(10, ((Map<String, Object>)shopsList.get(i)).get("papercode").toString());
                ps.setString(11, ((Map<String, Object>)shopsList.get(i)).get("movephone").toString());
                ps.setString(12, ((Map<String, Object>)shopsList.get(i)).get("fixationphone").toString());
                ps.setString(13, ((Map<String, Object>)shopsList.get(i)).get("jobunit").toString());
                ps.setString(14, ((Map<String, Object>)shopsList.get(i)).get("createduser").toString());
                ps.setString(15, ((Map<String, Object>)shopsList.get(i)).get("createdtime").toString());
                ps.setString(16, ((Map<String, Object>)shopsList.get(i)).get("groupid").toString());
                ps.setString(17, ((Map<String, Object>)shopsList.get(i)).get("instcitycode").toString());
                ps.setString(18, ((Map<String, Object>)shopsList.get(i)).get("deletereason").toString());
                ps.setString(19, ((Map<String, Object>)shopsList.get(i)).get("status").toString());
                ps.setString(20, ((Map<String, Object>)shopsList.get(i)).get("operator").toString());
                ps.setString(21, ((Map<String, Object>)shopsList.get(i)).get("operdatetime").toString());
                ps.setString(22, ((Map<String, Object>)shopsList.get(i)).get("istrans").toString());
                ps.setString(23, ((Map<String, Object>)shopsList.get(i)).get("createTime").toString());
            }
            public int getBatchSize()
            {
                return shopsList.size();
            }
        });
    }
    
    
    /**
     * 对私家庭成员信息
     * @param list
     * insert
     */
     public void updateCIPERSONFAMILY(List<Map<String, Object>> list){
         final List<Map<String, Object>> shopsList = list;
         String sql =    " update T_CIPERSONFAMILY set "+
		        		 "	  id = ?,                  "+
		        		 "	  partyid= ?,              "+
		        		 "	  partyinitid= ?,          "+
		        		 "	  custid= ?,               "+
		        		 "	  typeid= ?,               "+
		        		 "	  name= ?,                 "+
		        		 "	  sex= ?,                  "+
		        		 "	  familymbercction= ?,     "+
		        		 "	  papertype= ?,            "+
		        		 "	  papercode= ?,            "+
		        		 "	  movephone= ?,            "+
		        		 "	  fixationphone= ?,        "+
		        		 "	  jobunit= ?,              "+
		        		 "	  createduser= ?,          "+
		        		 "	  createdtime= ?,          "+
		        		 "	  groupid= ?,              "+
		        		 "	  instcitycode= ?,         "+
		        		 "	  deletereason= ?,         "+
		        		 "	  status= ?,               "+
		        		 "	  operator= ?,             "+
		        		 "	  operdatetime= ?,         "+
		        		 "	  istrans= ?,              "+
		        		 "	  create_time =?           "+
		        		 "  where custid = ?           ";
         jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){
             public void setValues(PreparedStatement ps,int i)throws SQLException
             {
                 ps.setString(1, ((Map<String, Object>)shopsList.get(i)).get("id").toString());
                 ps.setString(2, ((Map<String, Object>)shopsList.get(i)).get("partyid").toString());
                 ps.setString(3, ((Map<String, Object>)shopsList.get(i)).get("partyinitid").toString());
                 ps.setString(4, ((Map<String, Object>)shopsList.get(i)).get("custid").toString());
                 ps.setString(5, ((Map<String, Object>)shopsList.get(i)).get("typeid").toString());
                 ps.setString(6, ((Map<String, Object>)shopsList.get(i)).get("name").toString());
                 ps.setString(7, ((Map<String, Object>)shopsList.get(i)).get("sex").toString());
                 ps.setString(8, ((Map<String, Object>)shopsList.get(i)).get("familymbercction").toString());
                 ps.setString(9, ((Map<String, Object>)shopsList.get(i)).get("papertype").toString());
                 ps.setString(10, ((Map<String, Object>)shopsList.get(i)).get("papercode").toString());
                 ps.setString(11, ((Map<String, Object>)shopsList.get(i)).get("movephone").toString());
                 ps.setString(12, ((Map<String, Object>)shopsList.get(i)).get("fixationphone").toString());
                 ps.setString(13, ((Map<String, Object>)shopsList.get(i)).get("jobunit").toString());
                 ps.setString(14, ((Map<String, Object>)shopsList.get(i)).get("createduser").toString());
                 ps.setString(15, ((Map<String, Object>)shopsList.get(i)).get("createdtime").toString());
                 ps.setString(16, ((Map<String, Object>)shopsList.get(i)).get("groupid").toString());
                 ps.setString(17, ((Map<String, Object>)shopsList.get(i)).get("instcitycode").toString());
                 ps.setString(18, ((Map<String, Object>)shopsList.get(i)).get("deletereason").toString());
                 ps.setString(19, ((Map<String, Object>)shopsList.get(i)).get("status").toString());
                 ps.setString(20, ((Map<String, Object>)shopsList.get(i)).get("operator").toString());
                 ps.setString(21, ((Map<String, Object>)shopsList.get(i)).get("operdatetime").toString());
                 ps.setString(22, ((Map<String, Object>)shopsList.get(i)).get("istrans").toString());
                 ps.setString(23, ((Map<String, Object>)shopsList.get(i)).get("createTime").toString());
                 ps.setString(24, ((Map<String, Object>)shopsList.get(i)).get("custid").toString());
             }
             public int getBatchSize()
             {
                 return shopsList.size();
             }
         });
     }

    
    /**
     * 借据月末余额表（结果表）
     * @param list
     * @throws Exception 
     */
    public void saveFCLOANINFODataFile(String fileName,String date) throws Exception {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			/*//add                                                                        
			List<Map<String, Object>> insertdatas = new ArrayList<Map<String,Object>>(); 
			//update                                                                     
			List<Map<String, Object>> updatedatas = new ArrayList<Map<String,Object>>(); */

			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/T_FCLOANINFO.xml");
			// 解析”帐单记录表“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
//			for(Map<String, Object> map :datas){
//				customerInforDao.insertFCLOANINFO(map);
//			}
			//save
			insertFCLOANINFO(datas);
			/*for(Map<String, Object> map : datas){                                               
				int count = customerInforDao.findFCLOANINFOCount(map);                            
				if(count >0){                                                                     
					log.info("*************************updatedatas*************************");      
					//put updateMap                                                                 
					updatedatas.add(map);                                                           
				}else{                                                                            
					log.info("*************************insertdatas*************************");      
					//put insertMap                                                                 
					insertdatas.add(map);                                                           
				}                                                                                 
			}                                                                                   
			//save                                                                              
			insertFCLOANINFO(insertdatas);                                   
			//update                                                                            
			updateFCLOANINFO(updatedatas);                                   
			//释放空间                                                                          
			insertdatas=null;                                                                   
			updatedatas=null;          */                                                         
			//释放空间
			datas=null;
	}
    /**
     * 借据月末余额表（结果表）
     * @param list
     */
    public void insertFCLOANINFO(List<Map<String, Object>> list){
        final List<Map<String, Object>> shopsList = list;
        String sql =    "  insert into T_FCLOANINFO (    KEYCODE,"+
                                                            "YEAR,"+
                                                            "TIMES,"+
                                                            "BUSICODE,"+
                                                            "BUSITYPE,"+
                                                            "DEPTCODE,"+
                                                            "INSTCITYCODE,"+
                                                            "CONTRACTCODE,"+
                                                            "CUSTID,"+
                                                            "CNAME,"+
                                                            "TYPEID,"+
                                                            "CUSTTYPE,"+
                                                            "CUSTPROPERTY,"+
                                                            "ORGCERTCODE,"+
                                                            "UNITCUSTTYPE,"+
                                                            "CARDTYPE,"+
                                                            "CARDNUM,"+
                                                            "CITY,"+
                                                            "DISTRICTCOUNTY,"+
                                                            "TOWN,"+
                                                            "COMMUNITY,"+
                                                            "INSTCODE,"+
                                                            "CURRENCY,"+
                                                            "MONEY,"+
                                                            "BALAMT,"+
                                                            "DEBTINTEREST,"+
                                                            "REQLMT,"+
                                                            "LOANDATE,"+
                                                            "ENDDATE,"+
                                                            "BUSISTATE,"+
                                                            "CREDITLEVEL,"+
                                                            "INDUSTRY,"+
                                                            "LOANPURPOSE,"+
                                                            "MAINASSURE,"+
                                                            "BUSIMANAGER,"+
                                                            "ISBACK,"+
                                                            "ISBIGCOMPANY,"+
                                                            "STATE,"+
                                                            "ISLOWRISK,"+
                                                            "VALIDTIME,"+
                                                            "SORTRESULT,"+
                                                            "SORTRESULTFIVE,"+
                                                            "PRESORTRESULT,"+
                                                            "PRESORTFIVE,"+
                                                            "AUTOSORTRESULT,"+
                                                            "AUTOSORTREMARK,"+
                                                            "AUTOSORTFIVE,"+
                                                            "ISADM,"+
                                                            "CUSTSCALE,"+
                                                            "OVERTIMES,"+
                                                            "DELAYAMTDAYS,"+
                                                            "DELAYINTERESTDAYS,"+
                                                            "CHANGEFLAG,"+
                                                            "CATEGORYTYPE,"+
                                                            "OPERDATETIME,"+
                                                            "OPERATOR,"+
                                                            "ISWORKMANU,"+
                                                            "ADMDATE,"+
                                                            "AJUSTSTATE,"+
                                                            "ISTRANS,"+
                                                            "LASTYEARSORTRESULT,"+
                                                            "PROCESSID,"+
                                                            "TOBADLOANREASON,"+
                                                            "BADLOANDATE,CREATE_TIME)"+
                "    values                         (?,                              "+
                                            "    		    ?,                              "+
                                            "    		    ?,                              "+
                                            "    		    ?,                              "+
                                            "    		    ?,                              "+
                                            "    		    ?,                              "+
                                            "    		    ?,                              "+
                                            "    		    ?,                              "+
                                            "    		    ?,                              "+
                                            "    		    ?,                              "+
                                            "    		    ?,                              "+
                                            "    		    ?,                              "+
                                            "    		    ?,                              "+
                                            "    		    ?,                              "+
                                            "    		    ?,                              "+
                                            "    		    ?,                              "+
                                            "    		    ?,                              "+
                                            "    		    ?,                              "+
                                            "    		    ?,                              "+
                                            "    		    ?,                              "+
                                            "    		    ?,                              "+
                                            "    		    ?,                              "+
                                            "    		    ?,                              "+
                                            "    		    ?,                              "+
                                            "    		    ?,                              "+
                                            "    		    ?,                              "+
                                            "    		    ?,                              "+
                                            "    		    ?,                              "+
                                            "    		    ?,                              "+
                                            "    		    ?,                              "+
                                            "    		    ?,                              "+
                                            "    		    ?,                              "+
                                            "    		    ?,                              "+
                                            "    		    ?,                              "+
                                            "    		    ?,                              "+
                                            "    		    ?,                              "+
                                            "    		    ?,                              "+
                                            "    		    ?,                              "+
                                            "    		    ?,                              "+
                                            "    		    ?,                              "+
                                            "    		    ?,                              "+
                                            "    		    ?,                              "+
                                            "    		    ?,                              "+
                                            "    		    ?,                              "+
                                            "    		    ?,                              "+
                                            "    		    ?,                              "+
                                            "    		    ?,                              "+
                                            "    		    ?,                              "+
                                            "    		    ?,                              "+
                                            "    		    ?,                              "+
                                            "    		    ?,                              "+
                                            "    		    ?,                              "+
                                            "    		    ?,                              "+
                                            "    		    ?,                              "+
                                            "    		    ?,                              "+
                                            "    		    ?,                              "+
                                            "    		    ?,                              "+
                                            "    		    ?,                              "+
                                            "    		    ?,                              "+
                                            "    		    ?,                              "+
                                            "    		    ?,                              "+
                                            "    		    ?,                              "+
                                            "    		    ?,                              "+
                                            "    		    ?,?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){
            public void setValues(PreparedStatement ps,int i)throws SQLException
            {
                ps.setString(1, ((Map<String, Object>)shopsList.get(i)).get("keycode").toString());
                ps.setString(2, ((Map<String, Object>)shopsList.get(i)).get("year").toString());
                ps.setString(3, ((Map<String, Object>)shopsList.get(i)).get("times").toString());
                ps.setString(4, ((Map<String, Object>)shopsList.get(i)).get("busicode").toString());
                ps.setString(5, ((Map<String, Object>)shopsList.get(i)).get("busitype").toString());
                ps.setString(6, ((Map<String, Object>)shopsList.get(i)).get("deptcode").toString());
                ps.setString(7, ((Map<String, Object>)shopsList.get(i)).get("instcitycode").toString());
                ps.setString(8, ((Map<String, Object>)shopsList.get(i)).get("contractcode").toString());
                ps.setString(9, ((Map<String, Object>)shopsList.get(i)).get("custid").toString());
                ps.setString(10, ((Map<String, Object>)shopsList.get(i)).get("cname").toString());
                ps.setString(11, ((Map<String, Object>)shopsList.get(i)).get("typeid").toString());
                ps.setString(12, ((Map<String, Object>)shopsList.get(i)).get("custtype").toString());
                ps.setString(13, ((Map<String, Object>)shopsList.get(i)).get("custproperty").toString());
                ps.setString(14, ((Map<String, Object>)shopsList.get(i)).get("orgcertcode").toString());
                ps.setString(15, ((Map<String, Object>)shopsList.get(i)).get("unitcusttype").toString());
                ps.setString(16, ((Map<String, Object>)shopsList.get(i)).get("cardtype").toString());
                ps.setString(17, ((Map<String, Object>)shopsList.get(i)).get("cardnum").toString());
                ps.setString(18, ((Map<String, Object>)shopsList.get(i)).get("city").toString());
                ps.setString(19, ((Map<String, Object>)shopsList.get(i)).get("districtcounty").toString());
                ps.setString(20, ((Map<String, Object>)shopsList.get(i)).get("town").toString());
                ps.setString(21, ((Map<String, Object>)shopsList.get(i)).get("community").toString());
                ps.setString(22, ((Map<String, Object>)shopsList.get(i)).get("instcode").toString());
                ps.setString(23, ((Map<String, Object>)shopsList.get(i)).get("currency").toString());
                ps.setString(24, ((Map<String, Object>)shopsList.get(i)).get("money").toString());
                ps.setString(25, ((Map<String, Object>)shopsList.get(i)).get("balamt").toString());
                ps.setString(26, ((Map<String, Object>)shopsList.get(i)).get("debtinterest").toString());
                ps.setString(27, ((Map<String, Object>)shopsList.get(i)).get("reqlmt").toString());
                ps.setString(28, ((Map<String, Object>)shopsList.get(i)).get("loandate").toString());
                ps.setString(29, ((Map<String, Object>)shopsList.get(i)).get("enddate").toString());
                ps.setString(30, ((Map<String, Object>)shopsList.get(i)).get("busistate").toString());
                ps.setString(31, ((Map<String, Object>)shopsList.get(i)).get("creditlevel").toString());
                ps.setString(32, ((Map<String, Object>)shopsList.get(i)).get("industry").toString());
                ps.setString(33, ((Map<String, Object>)shopsList.get(i)).get("loanpurpose").toString());
                ps.setString(34, ((Map<String, Object>)shopsList.get(i)).get("mainassure").toString());
                ps.setString(35, ((Map<String, Object>)shopsList.get(i)).get("busimanager" ).toString());
                ps.setString(36, ((Map<String, Object>)shopsList.get(i)).get("isback").toString());
                ps.setString(37, ((Map<String, Object>)shopsList.get(i)).get("isbigcompany").toString());
                ps.setString(38, ((Map<String, Object>)shopsList.get(i)).get("state").toString());
                ps.setString(39, ((Map<String, Object>)shopsList.get(i)).get("islowrisk").toString());
                ps.setString(40, ((Map<String, Object>)shopsList.get(i)).get("validtime").toString());
                ps.setString(41, ((Map<String, Object>)shopsList.get(i)).get("sortresult").toString());
                ps.setString(42, ((Map<String, Object>)shopsList.get(i)).get("sortresultfive").toString());
                ps.setString(43, ((Map<String, Object>)shopsList.get(i)).get("presortresult").toString());
                ps.setString(44, ((Map<String, Object>)shopsList.get(i)).get("presortfive").toString());
                ps.setString(45, ((Map<String, Object>)shopsList.get(i)).get("autosortresult").toString());
                ps.setString(46, ((Map<String, Object>)shopsList.get(i)).get("autosortremark").toString());
                ps.setString(47, ((Map<String, Object>)shopsList.get(i)).get("autosortfive").toString());
                ps.setString(48, ((Map<String, Object>)shopsList.get(i)).get("isadm").toString());
                ps.setString(49, ((Map<String, Object>)shopsList.get(i)).get("custscale").toString());
                ps.setString(50, ((Map<String, Object>)shopsList.get(i)).get("overtimes").toString());
                ps.setString(51, ((Map<String, Object>)shopsList.get(i)).get("delayamtdays").toString());
                ps.setString(52, ((Map<String, Object>)shopsList.get(i)).get("delayinterestdays").toString());
                ps.setString(53, ((Map<String, Object>)shopsList.get(i)).get("changeflag").toString());
                ps.setString(54, ((Map<String, Object>)shopsList.get(i)).get("categorytype").toString());
                ps.setString(55, ((Map<String, Object>)shopsList.get(i)).get("operdatetime").toString());
                ps.setString(56, ((Map<String, Object>) shopsList.get(i)).get("operator").toString());
                ps.setString(57, ((Map<String, Object>)shopsList.get(i)).get("isworkmanu").toString());
                ps.setString(58, ((Map<String, Object>)shopsList.get(i)).get("admdate").toString());
                ps.setString(59, ((Map<String, Object>)shopsList.get(i)).get("ajuststate").toString());
                ps.setString(60, ((Map<String, Object>)shopsList.get(i)).get("istrans").toString());
                ps.setString(61, ((Map<String, Object>)shopsList.get(i)).get("lastyearsortresult").toString());
                ps.setString(62, ((Map<String, Object>)shopsList.get(i)).get("processid").toString());
                ps.setString(63, ((Map<String, Object>)shopsList.get(i)).get("tobadloanreason").toString());
                ps.setString(64, ((Map<String, Object>)shopsList.get(i)).get("badloandate").toString());
                ps.setString(65, ((Map<String, Object>)shopsList.get(i)).get("createTime").toString());
            }
            public int getBatchSize()
            {
                return shopsList.size();
            }
        });
    }
    
    
    /**
     * 借据月末余额表（结果表）
     * @param list
     */
    public void updateFCLOANINFO(List<Map<String, Object>> list){
        final List<Map<String, Object>> shopsList = list;
        String sql =   " update  t_fcloaninfo set			"+
        		"   keycode             = ?     "+
        		"   ,year               = ?     "+
        		"   ,times              = ?     "+
        		"   ,busicode           = ?     "+
        		"   ,busitype           = ?     "+
        		"   ,deptcode           = ?     "+
        		"   ,instcitycode       = ?     "+
        		"   ,contractcode       = ?     "+
        		"   ,custid             = ?     "+
        		"   ,cname              = ?     "+
        		"   ,typeid             = ?     "+
        		"   ,custtype           = ?     "+
        		"   ,custproperty       = ?     "+
        		"   ,orgcertcode        = ?     "+
        		"   ,unitcusttype       = ?     "+
        		"   ,cardtype           = ?     "+
        		"   ,cardnum            = ?     "+
        		"   ,city               = ?     "+
        		"   ,districtcounty     = ?     "+
        		"   ,town               = ?     "+
        		"   ,community          = ?     "+
        		"   ,instcode           = ?     "+
        		"   ,currency           = ?     "+
        		"   ,money              = ?     "+
        		"   ,balamt             = ?     "+
        		"   ,debtinterest       = ?     "+
        		"   ,reqlmt             = ?     "+
        		"   ,loandate           = ?     "+
        		"   ,enddate            = ?     "+
        		"   ,busistate          = ?     "+
        		"   ,creditlevel        = ?     "+
        		"   ,industry           = ?     "+
        		"   ,loanpurpose        = ?     "+
        		"   ,mainassure         = ?     "+
        		"   ,busimanager        = ?     "+
        		"   ,isback             = ?     "+
        		"   ,isbigcompany       = ?     "+
        		"   ,state              = ?     "+
        		"   ,islowrisk          = ?     "+
        		"   ,validtime          = ?     "+
        		"   ,sortresult         = ?     "+
        		"   ,sortresultfive     = ?     "+
        		"   ,presortresult      = ?     "+
        		"   ,presortfive        = ?     "+
        		"   ,autosortresult     = ?     "+
        		"   ,autosortremark     = ?     "+
        		"   ,autosortfive       = ?     "+
        		"   ,isadm              = ?     "+
        		"   ,custscale          = ?     "+
        		"   ,overtimes          = ?     "+
        		"   ,delayamtdays       = ?     "+
        		"   ,delayinterestdays  = ?     "+
        		"   ,changeflag         = ?     "+
        		"   ,categorytype       = ?     "+
        		"   ,operdatetime       = ?     "+
        		"   ,operator           = ?     "+
        		"   ,isworkmanu         = ?     "+
        		"   ,admdate            = ?     "+
        		"   ,ajuststate         = ?     "+
        		"   ,istrans            = ?     "+
        		"   ,lastyearsortresult = ?     "+
        		"   ,processid          = ?     "+
        		"   ,tobadloanreason    = ?     "+
        		"   ,badloandate        = ?     "+
        		"   ,create_time        = ?     "+
        		"where  trim(BUSICODE)   = ?     ";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){
            public void setValues(PreparedStatement ps,int i)throws SQLException
            {
                ps.setString(1, ((Map<String, Object>)shopsList.get(i)).get("keycode").toString());
                ps.setString(2, ((Map<String, Object>)shopsList.get(i)).get("year").toString());
                ps.setString(3, ((Map<String, Object>)shopsList.get(i)).get("times").toString());
                ps.setString(4, ((Map<String, Object>)shopsList.get(i)).get("busicode").toString());
                ps.setString(5, ((Map<String, Object>)shopsList.get(i)).get("busitype").toString());
                ps.setString(6, ((Map<String, Object>)shopsList.get(i)).get("deptcode").toString());
                ps.setString(7, ((Map<String, Object>)shopsList.get(i)).get("instcitycode").toString());
                ps.setString(8, ((Map<String, Object>)shopsList.get(i)).get("contractcode").toString());
                ps.setString(9, ((Map<String, Object>)shopsList.get(i)).get("custid").toString());
                ps.setString(10, ((Map<String, Object>)shopsList.get(i)).get("cname").toString());
                ps.setString(11, ((Map<String, Object>)shopsList.get(i)).get("typeid").toString());
                ps.setString(12, ((Map<String, Object>)shopsList.get(i)).get("custtype").toString());
                ps.setString(13, ((Map<String, Object>)shopsList.get(i)).get("custproperty").toString());
                ps.setString(14, ((Map<String, Object>)shopsList.get(i)).get("orgcertcode").toString());
                ps.setString(15, ((Map<String, Object>)shopsList.get(i)).get("unitcusttype").toString());
                ps.setString(16, ((Map<String, Object>)shopsList.get(i)).get("cardtype").toString());
                ps.setString(17, ((Map<String, Object>)shopsList.get(i)).get("cardnum").toString());
                ps.setString(18, ((Map<String, Object>)shopsList.get(i)).get("city").toString());
                ps.setString(19, ((Map<String, Object>)shopsList.get(i)).get("districtcounty").toString());
                ps.setString(20, ((Map<String, Object>)shopsList.get(i)).get("town").toString());
                ps.setString(21, ((Map<String, Object>)shopsList.get(i)).get("community").toString());
                ps.setString(22, ((Map<String, Object>)shopsList.get(i)).get("instcode").toString());
                ps.setString(23, ((Map<String, Object>)shopsList.get(i)).get("currency").toString());
                ps.setString(24, ((Map<String, Object>)shopsList.get(i)).get("money").toString());
                ps.setString(25, ((Map<String, Object>)shopsList.get(i)).get("balamt").toString());
                ps.setString(26, ((Map<String, Object>)shopsList.get(i)).get("debtinterest").toString());
                ps.setString(27, ((Map<String, Object>)shopsList.get(i)).get("reqlmt").toString());
                ps.setString(28, ((Map<String, Object>)shopsList.get(i)).get("loandate").toString());
                ps.setString(29, ((Map<String, Object>)shopsList.get(i)).get("enddate").toString());
                ps.setString(30, ((Map<String, Object>)shopsList.get(i)).get("busistate").toString());
                ps.setString(31, ((Map<String, Object>)shopsList.get(i)).get("creditlevel").toString());
                ps.setString(32, ((Map<String, Object>)shopsList.get(i)).get("industry").toString());
                ps.setString(33, ((Map<String, Object>)shopsList.get(i)).get("loanpurpose").toString());
                ps.setString(34, ((Map<String, Object>)shopsList.get(i)).get("mainassure").toString());
                ps.setString(35, ((Map<String, Object>)shopsList.get(i)).get("busimanager" ).toString());
                ps.setString(36, ((Map<String, Object>)shopsList.get(i)).get("isback").toString());
                ps.setString(37, ((Map<String, Object>)shopsList.get(i)).get("isbigcompany").toString());
                ps.setString(38, ((Map<String, Object>)shopsList.get(i)).get("state").toString());
                ps.setString(39, ((Map<String, Object>)shopsList.get(i)).get("islowrisk").toString());
                ps.setString(40, ((Map<String, Object>)shopsList.get(i)).get("validtime").toString());
                ps.setString(41, ((Map<String, Object>)shopsList.get(i)).get("sortresult").toString());
                ps.setString(42, ((Map<String, Object>)shopsList.get(i)).get("sortresultfive").toString());
                ps.setString(43, ((Map<String, Object>)shopsList.get(i)).get("presortresult").toString());
                ps.setString(44, ((Map<String, Object>)shopsList.get(i)).get("presortfive").toString());
                ps.setString(45, ((Map<String, Object>)shopsList.get(i)).get("autosortresult").toString());
                ps.setString(46, ((Map<String, Object>)shopsList.get(i)).get("autosortremark").toString());
                ps.setString(47, ((Map<String, Object>)shopsList.get(i)).get("autosortfive").toString());
                ps.setString(48, ((Map<String, Object>)shopsList.get(i)).get("isadm").toString());
                ps.setString(49, ((Map<String, Object>)shopsList.get(i)).get("custscale").toString());
                ps.setString(50, ((Map<String, Object>)shopsList.get(i)).get("overtimes").toString());
                ps.setString(51, ((Map<String, Object>)shopsList.get(i)).get("delayamtdays").toString());
                ps.setString(52, ((Map<String, Object>)shopsList.get(i)).get("delayinterestdays").toString());
                ps.setString(53, ((Map<String, Object>)shopsList.get(i)).get("changeflag").toString());
                ps.setString(54, ((Map<String, Object>)shopsList.get(i)).get("categorytype").toString());
                ps.setString(55, ((Map<String, Object>)shopsList.get(i)).get("operdatetime").toString());
                ps.setString(56, ((Map<String, Object>) shopsList.get(i)).get("operator").toString());
                ps.setString(57, ((Map<String, Object>)shopsList.get(i)).get("isworkmanu").toString());
                ps.setString(58, ((Map<String, Object>)shopsList.get(i)).get("admdate").toString());
                ps.setString(59, ((Map<String, Object>)shopsList.get(i)).get("ajuststate").toString());
                ps.setString(60, ((Map<String, Object>)shopsList.get(i)).get("istrans").toString());
                ps.setString(61, ((Map<String, Object>)shopsList.get(i)).get("lastyearsortresult").toString());
                ps.setString(62, ((Map<String, Object>)shopsList.get(i)).get("processid").toString());
                ps.setString(63, ((Map<String, Object>)shopsList.get(i)).get("tobadloanreason").toString());
                ps.setString(64, ((Map<String, Object>)shopsList.get(i)).get("badloandate").toString());
                ps.setString(65, ((Map<String, Object>)shopsList.get(i)).get("createTime").toString());
                ps.setString(66, ((Map<String, Object>)shopsList.get(i)).get("busicode").toString());
            }
            public int getBatchSize()
            {
                return shopsList.size();
            }
        });
    }

    
    /**
     * 认定结果表（历史表）
     * @param list
     * @throws Exception 
     */
    public void saveFCRESULTHISDataFile(String fileName,String date) throws Exception {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/T_FCRESULTHIS.xml");
			// 解析”帐单记录表“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
//			for(Map<String, Object> map : datas){
//				customerInforDao.insertFCRESULTHIS(map);
//			}
			//save
			insertFCRESULTHIS(datas);
			//释放空间
			datas=null;
	}
    /**
     * 认定结果表（历史表）
     * @param list
     */
    public void insertFCRESULTHIS(List<Map<String, Object>> list){
        final List<Map<String, Object>> shopsList = list;
        String sql =    "  insert into T_FCRESULTHIS ( KEYCODE,"+
                                                            "TYPEID,"+
                                                            "CUSTTYPE,"+
                                                            "CUSTID,"+
                                                            "YEAR,"+
                                                            "TIMES,"+
                                                            "CONTRACTCODE,"+
                                                            "BUSICODE,"+
                                                            "SORTINSTCODE,"+
                                                            "SORTDEPTCODE,"+
                                                            "BUSISTATE,"+
                                                            "BUSITYPE,"+
                                                            "MONEY,"+
                                                            "LOANDATE,"+
                                                            "ENDDATE,"+
                                                            "LOANPURPOSE,"+
                                                            "DELAYAMTDAYS,"+
                                                            "DELAYINTERESTDAYS,"+
                                                            "DEBTINTEREST,"+
                                                            "CREDITLEVEL,"+
                                                            "INITSORTINSTCODE,"+
                                                            "INITSORTDEPTCODE,"+
                                                            "CREATOR,"+
                                                            "CREATORTIMES,"+
                                                            "APPROVER,"+
                                                            "BUSIMANAGER,"+
                                                            "SORTDATE,"+
                                                            "SORTRESULT,"+
                                                            "TOBADLOANREASON,"+
                                                            "REASONSTATUS,"+
                                                            "PRESORTRESULT,"+
                                                            "REMARK,"+
                                                            "STATE,"+
                                                            "INSTCODE,"+
                                                            "DEPTCODE,"+
                                                            "INSTCITYCODE,"+
                                                            "OPERDATETIME,"+
                                                            "OPERATOR,"+
                                                            "ISTRANS,CREATE_TIME)"+
                "    values                         (?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){
            public void setValues(PreparedStatement ps,int i)throws SQLException
            {
                ps.setString(1, ((Map<String, Object>)shopsList.get(i)).get("keycode").toString());
                ps.setString(2, ((Map<String, Object>)shopsList.get(i)).get("typeid").toString());
                ps.setString(3, ((Map<String, Object>)shopsList.get(i)).get("custtype").toString());
                ps.setString(4, ((Map<String, Object>)shopsList.get(i)).get("custid").toString());
                ps.setString(5, ((Map<String, Object>)shopsList.get(i)).get("year").toString());
                ps.setString(6, ((Map<String, Object>)shopsList.get(i)).get("times").toString());
                ps.setString(7, ((Map<String, Object>)shopsList.get(i)).get("contractcode").toString());
                ps.setString(8, ((Map<String, Object>)shopsList.get(i)).get("busicode").toString());
                ps.setString(9, ((Map<String, Object>)shopsList.get(i)).get("sortinstcode").toString());
                ps.setString(10, ((Map<String, Object>)shopsList.get(i)).get("sortdeptcode").toString());
                ps.setString(11, ((Map<String, Object>)shopsList.get(i)).get("busistate").toString());
                ps.setString(12, ((Map<String, Object>)shopsList.get(i)).get("busitype").toString());
                ps.setString(13, ((Map<String, Object>)shopsList.get(i)).get("money").toString());
                ps.setString(14, ((Map<String, Object>)shopsList.get(i)).get("loandate").toString());
                ps.setString(15, ((Map<String, Object>)shopsList.get(i)).get("enddate").toString());
                ps.setString(16, ((Map<String, Object>)shopsList.get(i)).get("loanpurpose").toString());
                ps.setString(17, ((Map<String, Object>)shopsList.get(i)).get("delayamtdays").toString());
                ps.setString(18, ((Map<String, Object>)shopsList.get(i)).get("delayinterestdays").toString());
                ps.setString(19, ((Map<String, Object>)shopsList.get(i)).get("debtinterest").toString());
                ps.setString(20, ((Map<String, Object>)shopsList.get(i)).get("creditlevel").toString());
                ps.setString(21, ((Map<String, Object>)shopsList.get(i)).get("initsortinstcode").toString());
                ps.setString(22, ((Map<String, Object>)shopsList.get(i)).get("initsortdeptcode").toString());
                ps.setString(23, ((Map<String, Object>)shopsList.get(i)).get("creator").toString());
                ps.setString(24, ((Map<String, Object>)shopsList.get(i)).get("creatortimes").toString());
                ps.setString(25, ((Map<String, Object>)shopsList.get(i)).get("approver").toString());
                ps.setString(26, ((Map<String, Object>)shopsList.get(i)).get("busimanager").toString());
                ps.setString(27, ((Map<String, Object>)shopsList.get(i)).get("sortdate").toString());
                ps.setString(28, ((Map<String, Object>)shopsList.get(i)).get("sortresult").toString());
                ps.setString(29, ((Map<String, Object>)shopsList.get(i)).get("tobadloanreason").toString());
                ps.setString(30, ((Map<String, Object>)shopsList.get(i)).get("reasonstatus").toString());
                ps.setString(31, ((Map<String, Object>)shopsList.get(i)).get("presortresult").toString());
                ps.setString(32, ((Map<String, Object>)shopsList.get(i)).get("remark").toString());
                ps.setString(33, ((Map<String, Object>)shopsList.get(i)).get("state").toString());
                ps.setString(34, ((Map<String, Object>)shopsList.get(i)).get("instcode").toString());
                ps.setString(35, ((Map<String, Object>)shopsList.get(i)).get("deptcode").toString());
                ps.setString(36, ((Map<String, Object>)shopsList.get(i)).get("instcitycode").toString());
                ps.setString(37, ((Map<String, Object>)shopsList.get(i)).get("operdatetime").toString());
                ps.setString(38, ((Map<String, Object>)shopsList.get(i)).get("operator").toString());
                ps.setString(39, ((Map<String, Object>)shopsList.get(i)).get("istrans").toString());
                ps.setString(40, ((Map<String, Object>)shopsList.get(i)).get("createTime").toString());
            }
            public int getBatchSize()
            {
                return shopsList.size();
            }
        });
    }
    
    
    /**
     * 五级分类统计表
     * @throws Exception 
     */
    public void saveFCSTATISTICSDataFile(String fileName,String date) throws Exception {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/T_FCSTATISTICSDATA.xml");
			// 解析”帐单记录表“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			//save
			insertFCSTATISTICSDATA(datas);
			//释放空间
			datas=null;
	}
    /**
     * 五级分类统计表
     */
    public void insertFCSTATISTICSDATA(List<Map<String, Object>> list){
        final List<Map<String, Object>> shopsList = list;
        String sql =    "  insert into T_FCSTATISTICSDATA ( DT,"+
                                                            "BRHID,"+
                                                            "FTYPE,"+
                                                            "DIM1,"+
                                                            "DIM2,"+
                                                            "DIM3,"+
                                                            "AMT1,"+
                                                            "AMT2,"+
                                                            "AMT3,"+
                                                            "LASTMODIFIED,"+
                                                            "INSTCITYCODE,"+
                                                            "ISTRANS,CREATE_TIME)"+
                "    values                         (?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){
            public void setValues(PreparedStatement ps,int i)throws SQLException
            {
                ps.setString(1, ((Map<String, Object>)shopsList.get(i)).get("dt").toString());
                ps.setString(2, ((Map<String, Object>)shopsList.get(i)).get("brhid").toString());
                ps.setString(3, ((Map<String, Object>)shopsList.get(i)).get("ftype").toString());
                ps.setString(4, ((Map<String, Object>)shopsList.get(i)).get("dim1").toString());
                ps.setString(5, ((Map<String, Object>)shopsList.get(i)).get("dim2").toString());
                ps.setString(6, ((Map<String, Object>)shopsList.get(i)).get("dim3").toString());
                ps.setString(7, ((Map<String, Object>)shopsList.get(i)).get("amt1").toString());
                ps.setString(8, ((Map<String, Object>)shopsList.get(i)).get("amt2").toString());
                ps.setString(9, ((Map<String, Object>)shopsList.get(i)).get("amt3").toString());
                ps.setString(10, ((Map<String, Object>)shopsList.get(i)).get("lastmodified").toString());
                ps.setString(11, ((Map<String, Object>)shopsList.get(i)).get("instcitycode").toString());
                ps.setString(12, ((Map<String, Object>)shopsList.get(i)).get("istrans").toString());
                ps.setString(13, ((Map<String, Object>)shopsList.get(i)).get("createTime").toString());
            }
            public int getBatchSize()
            {
                return shopsList.size();
            }
        });
    }

    /**
     * GC担保对应表
     * @param list
     * @throws Exception 
     */
    public void saveGCASSURECORRESPONDDataFile(String fileName,String date) throws Exception {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/T_GCASSURECORRESPOND.xml");
			// 解析”帐单记录表“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList,date);
			//save
			insertGCASSURECORRESPOND(datas);
			//释放空间
			datas=null;
	}
    /**
     * GC担保对应表
     * @param list
     */
    public void insertGCASSURECORRESPOND(List<Map<String, Object>> list){
        final List<Map<String, Object>> shopsList = list;
        String sql =    "  insert into T_GCASSURECORRESPOND (KEYCODE,"+
                                                            "KEYTYPE,"+
                                                            "GCBUSINESSLEVEL,"+
                                                            "GCSYSLEVEL,"+
                                                            "UPKEYCODE,"+
                                                            "UPKEYTYPE,"+
                                                            "UPGCBUSINESSLEVEL,"+
                                                            "UPGCSYSLEVEL,"+
                                                            "SEQUENCENO,"+
                                                            "ACONTKEYCODE,"+
                                                            "ACONTKEYTYPE,"+
                                                            "ACONTGCBUSINESSLEVEL,"+
                                                            "ACONTGCSYSLEVEL,"+
                                                            "CONFIRMMETHOD,"+
                                                            "USEASSUREMONEY,"+
                                                            "USEASSUREMONEYCUR,"+
                                                            "KEYEFFECTEDSTATE,"+
                                                            "KEYDATESTATE,"+
                                                            "EDITIONNO,"+
                                                            "INSTCITYCODE,"+
                                                            "OPERDATETIME,"+
                                                            "ISTRANS,CREATE_TIME)"+
                "    values                         (?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,                              "+
                "    		    ?,?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){
            public void setValues(PreparedStatement ps,int i)throws SQLException
            {
                ps.setString(1, ((Map<String, Object>)shopsList.get(i)).get("keycode").toString());
                ps.setString(2, ((Map<String, Object>)shopsList.get(i)).get("keytype").toString());
                ps.setString(3, ((Map<String, Object>)shopsList.get(i)).get("gcbusinesslevel").toString());
                ps.setString(4, ((Map<String, Object>)shopsList.get(i)).get("gcsyslevel").toString());
                ps.setString(5, ((Map<String, Object>)shopsList.get(i)).get("upkeycode").toString());
                ps.setString(6, ((Map<String, Object>)shopsList.get(i)).get("upkeytype").toString());
                ps.setString(7, ((Map<String, Object>)shopsList.get(i)).get("upgcbusinesslevel").toString());
                ps.setString(8, ((Map<String, Object>)shopsList.get(i)).get("upgcsyslevel").toString());
                ps.setString(9, ((Map<String, Object>)shopsList.get(i)).get("sequenceno").toString());
                ps.setString(10, ((Map<String, Object>)shopsList.get(i)).get("acontkeycode").toString());
                ps.setString(11, ((Map<String, Object>)shopsList.get(i)).get("acontkeytype").toString());
                ps.setString(12, ((Map<String, Object>)shopsList.get(i)).get("acontgcbusinesslevel").toString());
                ps.setString(13, ((Map<String, Object>)shopsList.get(i)).get("acontgcsyslevel").toString());
                ps.setString(14, ((Map<String, Object>)shopsList.get(i)).get("confirmmethod").toString());
                ps.setString(15, ((Map<String, Object>)shopsList.get(i)).get("useassuremoney").toString());
                ps.setString(16, ((Map<String, Object>)shopsList.get(i)).get("useassuremoneycur").toString());
                ps.setString(17, ((Map<String, Object>)shopsList.get(i)).get("keyeffectedstate").toString());
                ps.setString(18, ((Map<String, Object>)shopsList.get(i)).get("keydatestate").toString());
                ps.setString(19, ((Map<String, Object>)shopsList.get(i)).get("editionno").toString());
                ps.setString(20, ((Map<String, Object>)shopsList.get(i)).get("instcitycode").toString());
                ps.setString(21, ((Map<String, Object>)shopsList.get(i)).get("operdatetime").toString());
                ps.setString(22, ((Map<String, Object>)shopsList.get(i)).get("istrans").toString());
                ps.setString(23, ((Map<String, Object>)shopsList.get(i)).get("createTime").toString());
            }
            public int getBatchSize()
            {
                return shopsList.size();
            }
        });
    }

//===================================================================JN END========================================================//
    //TODO:以下为太原使用，可以准备删除
	/*
	 * 批量插入流水号
	 */


	public void insertLsh(List<Map<String, Object>> list){
		 final List<Map<String, Object>> shopsList = list;
	        String sql =    "  insert into ty_repay_lsz (CREATE_TIME,    "+   
					        "    						   YWBH,              "+
					        "    						   KMH,              "+
					        "    						   KHJL,              "+
					        "     						   JJH,              "+
					        "     						   YWJG,              "+
					        "      						   YWJGH,              "+
					        "      						   DKZH,             "+
					        "      						   FZH,             "+
					        "      						   LSH,             "+
					        "      						   YWDM,             "+
					        "      						   JZRQ,             "+
					        "      						   YWRQ,             "+
					        "      						   ZY,             "+
					        "      						   PZH,             "+
					        "      						   JF,             "+
					        "      						   DF,             "+
					        "      						   YE,             "+
					        "      						   QXRX,             "+
					        "      						   ZXRQ,             "+
					        "      						   SFMZ,             "+
					        "      						   MZYH,             "+
					        "      						   MZSJ,             "+
					        "      						   SFCL             )"+
					        "    values    (?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?                              )";
	          jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){
	            public void setValues(PreparedStatement ps,int i)throws SQLException
	               {
	                ps.setString(1, ((Map<String, Object>)shopsList.get(i)).get("createTime").toString());
	                ps.setString(2, ((Map<String, Object>)shopsList.get(i)).get("ywbh").toString());
	                ps.setString(3, ((Map<String, Object>)shopsList.get(i)).get("kmh").toString());
	                ps.setString(4, ((Map<String, Object>)shopsList.get(i)).get("khjl").toString());
	                ps.setString(5, ((Map<String, Object>)shopsList.get(i)).get("jjh").toString());
	                ps.setString(6, ((Map<String, Object>)shopsList.get(i)).get("ywjg").toString());
	                ps.setString(7, ((Map<String, Object>)shopsList.get(i)).get("ywjgh").toString());
	                ps.setString(8, ((Map<String, Object>)shopsList.get(i)).get("dkzh").toString());
	                ps.setString(9, ((Map<String, Object>)shopsList.get(i)).get("fzh").toString());
	                ps.setString(10,((Map<String, Object>)shopsList.get(i)).get("lsh").toString());
	                ps.setString(11,((Map<String, Object>)shopsList.get(i)).get("ywdm").toString());
	                ps.setString(12,((Map<String, Object>)shopsList.get(i)).get("jzrq").toString());
	                ps.setString(13,((Map<String, Object>)shopsList.get(i)).get("ywrq").toString());
	                ps.setString(14,((Map<String, Object>)shopsList.get(i)).get("zy").toString());
	                ps.setString(15,((Map<String, Object>)shopsList.get(i)).get("pzh").toString());
	                ps.setString(16,((Map<String, Object>)shopsList.get(i)).get("jf").toString());
	                ps.setString(17,((Map<String, Object>)shopsList.get(i)).get("df").toString());
	                ps.setString(18,((Map<String, Object>)shopsList.get(i)).get("ye").toString());
	                ps.setString(19,((Map<String, Object>)shopsList.get(i)).get("qxrx").toString());
	                ps.setString(20,((Map<String, Object>)shopsList.get(i)).get("zxrq").toString());
	                ps.setString(21,((Map<String, Object>)shopsList.get(i)).get("sfmz").toString());
	                ps.setString(22,((Map<String, Object>)shopsList.get(i)).get("mzyh").toString());
	                ps.setString(23,((Map<String, Object>)shopsList.get(i)).get("mzsj").toString());
	                ps.setString(24,((Map<String, Object>)shopsList.get(i)).get("sfcl").toString());
	                
	               }
	               public int getBatchSize()
	               {
	                return shopsList.size();
	               }
	        });
	}
	/*
	 * 批量插入余额信息
	 */
	public void insertYe(List<Map<String, Object>> list){
		 final List<Map<String, Object>> shopsList = list;
	        String sql =    "insert into ty_repay_yehz (CREATE_TIME,JJH, YWBH, KHH, ZHTBH,JGDM,KMH,  HBZL,JKJE, QXRQ,DKYE, BNQX,  BWQX,WJFL1,WJFL2,WJFL3,WJFL4,WJFL5, SHBJ, SHLX, LXJS,  KSQXRQ,YHXBJ,YHXLX,  ZHHKR, BQLL) values    (   ?,  ?,  ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
					        
	          jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){
	            public void setValues(PreparedStatement ps,int i)throws SQLException
	               {
	            	ps.setString(1, ((Map<String, Object>)shopsList.get(i)).get("createTime").toString());
	                ps.setString(2, ((Map<String, Object>)shopsList.get(i)).get("jjh").toString());
	                ps.setString(3, ((Map<String, Object>)shopsList.get(i)).get("ywbh").toString());
	                ps.setString(4, ((Map<String, Object>)shopsList.get(i)).get("khh").toString());
	                ps.setString(5, ((Map<String, Object>)shopsList.get(i)).get("zhtbh").toString());
	                ps.setString(6, ((Map<String, Object>)shopsList.get(i)).get("jgdm").toString());
	                ps.setString(7, ((Map<String, Object>)shopsList.get(i)).get("kmh").toString());
	                ps.setString(8, ((Map<String, Object>)shopsList.get(i)).get("hbzl").toString());
	                ps.setString(9, ((Map<String, Object>)shopsList.get(i)).get("jkje").toString());
	                ps.setString(10,((Map<String, Object>)shopsList.get(i)).get("qxrq").toString());
	                ps.setString(11,((Map<String, Object>)shopsList.get(i)).get("dkye").toString());
	                ps.setString(12,((Map<String, Object>)shopsList.get(i)).get("bnqx").toString());
	                ps.setString(13,((Map<String, Object>)shopsList.get(i)).get("bwqx").toString());
	                ps.setString(14,((Map<String, Object>)shopsList.get(i)).get("wjfl1").toString());
	                ps.setString(15,((Map<String, Object>)shopsList.get(i)).get("wjfl2").toString());
	                ps.setString(16,((Map<String, Object>)shopsList.get(i)).get("wjfl3").toString());
	                ps.setString(17,((Map<String, Object>)shopsList.get(i)).get("wjfl4").toString());
	                ps.setString(18,((Map<String, Object>)shopsList.get(i)).get("wjfl5").toString());
	                ps.setString(19,((Map<String, Object>)shopsList.get(i)).get("shbj").toString());
	                ps.setString(20,((Map<String, Object>)shopsList.get(i)).get("shlx").toString());
	                ps.setString(21,((Map<String, Object>)shopsList.get(i)).get("lxjs").toString());
	                ps.setString(22,((Map<String, Object>)shopsList.get(i)).get("ksqxrq").toString());
	                ps.setString(23,((Map<String, Object>)shopsList.get(i)).get("yhxbj").toString());
	                ps.setString(24,((Map<String, Object>)shopsList.get(i)).get("yhxlx").toString());
	                ps.setString(25,((Map<String, Object>)shopsList.get(i)).get("zhhkr").toString());
	                ps.setString(26,((Map<String, Object>)shopsList.get(i)).get("bqll").toString());
	                
	               }
	               public int getBatchSize()
	               {
	                return shopsList.size();
	               }
	        });
	}
	/*
	 * 批量插入借据表
	 */
	public void insertJjb(List<Map<String, Object>> list){
		 final List<Map<String, Object>> shopsList = list;
	        String sql =    "  insert into ty_repay_tkmx (CREATE_TIME,    "+   
					        "    						   YWBH,              "+
					        "    						   KHH,              "+
					        "    						   ZHTBH,              "+
					        "     						   JJH,              "+
					        "     						   JZLL,              "+
					        "      						   SFBL,              "+
					        "      						   HTLL,             "+
					        "      						   TKBH,             "+
					        "      						   FFJE,             "+
					        "      						   TKYT,             "+
					        "      						   JKRQ,             "+
					        "      						   DQRQ,             "+
					        "      						   DKKM,             "+
					        "      						   XGRY,             "+
					        "      						   XGSJ,             "+
					        "      						   DKQX,             "+
					        "      						   ZQDQR,             "+
					        "      						   SFZQ,             "+
					        "      						   SFJQ,             "+
					        "      						   DKZL,             "+
					        "      						   DKFS,             "+
					        "      						   BLJG,             "+
					        "      						   SFZF,             "+
					        "      						   KHJL,             "+
					        "      						   ZQHTH,             "+
					        "      						   DKZH,             "+
					        "      						   SFJZ,             "+
					        "      						   DKFL,             "+
					        "      						   DKLX,             "+
					        "      						   JJBH,             "+
					        "      						   CPMC,             "+
					        "      						   DKXZ,             "+
					        "      						   DKYT,             "+
					        "      						   DKTX,             "+
					        "      						   LLZL,             "+
					        "      						   JXFS,             "+
					        "      						   HKLY,             "+
					        "      						   HKFS,             "+
					        "      						   JQRQ,             "+
					        "      						   YWLX,             "+
					        "      						   ZQRQ,             "+
					        "      						   HBZL,             "+
					        "      						   YTMX,             "+
					        "      						   SFXEDK,             "+
					        "      						   YQSF,             "+
					        "      						   SHYJ,             "+
					        "      						   SHRY,             "+
					        "      						   SHSJ,             "+
					        "      						   TXLX,             "+
					        "      						   YQLL,             "+
					        "      						   SFTXJE,             "+
					        "      						   NYJFBL,             "+
					        "      						   BLRY,             "+
					        "      						   YJJH,             "+
					        "      						   SFSTDK,             "+
					        "      						   YSTXYH,             "+
					        "      						   SFYXDK,             "+
					        "      						   YXR,             "+
					        "      						   FLCORZLC             )"+
					        "    values    (?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?,                              "+
					        "    		    ?                              )";
	          jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){
	            public void setValues(PreparedStatement ps,int i)throws SQLException
	               {
	                ps.setString(1, ((Map<String, Object>)shopsList.get(i)).get("createTime").toString());
	                ps.setString(2, ((Map<String, Object>)shopsList.get(i)).get("ywbh").toString());
	                ps.setString(3, ((Map<String, Object>)shopsList.get(i)).get("khh").toString());
	                ps.setString(4, ((Map<String, Object>)shopsList.get(i)).get("zhtbh").toString());
	                ps.setString(5, ((Map<String, Object>)shopsList.get(i)).get("jjh").toString());
	                ps.setString(6, ((Map<String, Object>)shopsList.get(i)).get("jzll").toString());
	                ps.setString(7, ((Map<String, Object>)shopsList.get(i)).get("sfbl").toString());
	                ps.setString(8, ((Map<String, Object>)shopsList.get(i)).get("htll").toString());
	                ps.setString(9, ((Map<String, Object>)shopsList.get(i)).get("tkbh").toString());
	                ps.setString(10,((Map<String, Object>)shopsList.get(i)).get("ffje").toString());
	                ps.setString(11,((Map<String, Object>)shopsList.get(i)).get("tkyt").toString());
	                ps.setString(12,((Map<String, Object>)shopsList.get(i)).get("jkrq").toString());
	                ps.setString(13,((Map<String, Object>)shopsList.get(i)).get("dqrq").toString());
	                ps.setString(14,((Map<String, Object>)shopsList.get(i)).get("dkkm").toString());
	                ps.setString(15,((Map<String, Object>)shopsList.get(i)).get("xgry").toString());
	                ps.setString(16,((Map<String, Object>)shopsList.get(i)).get("xgsj").toString());
	                ps.setString(17,((Map<String, Object>)shopsList.get(i)).get("dkqx").toString());
	                ps.setString(18,((Map<String, Object>)shopsList.get(i)).get("zqdqr").toString());
	                ps.setString(19,((Map<String, Object>)shopsList.get(i)).get("sfzq").toString());
	                ps.setString(20,((Map<String, Object>)shopsList.get(i)).get("sfjq").toString());
	                ps.setString(21,((Map<String, Object>)shopsList.get(i)).get("dkzl").toString());
	                ps.setString(22,((Map<String, Object>)shopsList.get(i)).get("dkfs").toString());
	                ps.setString(23,((Map<String, Object>)shopsList.get(i)).get("bljg").toString());
	                ps.setString(24,((Map<String, Object>)shopsList.get(i)).get("sfzf").toString());
	                ps.setString(25,((Map<String, Object>)shopsList.get(i)).get("khjl").toString());
	                ps.setString(26,((Map<String, Object>)shopsList.get(i)).get("zqhth").toString());
	                ps.setString(27,((Map<String, Object>)shopsList.get(i)).get("dkzh").toString());
	                ps.setString(28,((Map<String, Object>)shopsList.get(i)).get("sfjz").toString());
	                ps.setString(29,((Map<String, Object>)shopsList.get(i)).get("dkfl").toString());
	                ps.setString(30,((Map<String, Object>)shopsList.get(i)).get("dklx").toString());
	                ps.setString(31,((Map<String, Object>)shopsList.get(i)).get("jjbh").toString());
	                ps.setString(32,((Map<String, Object>)shopsList.get(i)).get("cpmc").toString());
	                ps.setString(33,((Map<String, Object>)shopsList.get(i)).get("dkxz").toString());
	                ps.setString(34,((Map<String, Object>)shopsList.get(i)).get("dkyt").toString());
	                ps.setString(35,((Map<String, Object>)shopsList.get(i)).get("dktx").toString());
	                ps.setString(36,((Map<String, Object>)shopsList.get(i)).get("llzl").toString());
	                ps.setString(37,((Map<String, Object>)shopsList.get(i)).get("jxfs").toString());
	                ps.setString(38,((Map<String, Object>)shopsList.get(i)).get("hkly").toString());
	                ps.setString(39,((Map<String, Object>)shopsList.get(i)).get("hkfs").toString());
	                ps.setString(40,((Map<String, Object>)shopsList.get(i)).get("jqrq").toString());
	                ps.setString(41,((Map<String, Object>)shopsList.get(i)).get("ywlx").toString());
	                ps.setString(42,((Map<String, Object>)shopsList.get(i)).get("zqrq").toString());
	                ps.setString(43,((Map<String, Object>)shopsList.get(i)).get("hbzl").toString());
	                ps.setString(44,((Map<String, Object>)shopsList.get(i)).get("ytmx").toString());
	                ps.setString(45,((Map<String, Object>)shopsList.get(i)).get("sfxedk").toString());
	                ps.setString(46,((Map<String, Object>)shopsList.get(i)).get("yqsf").toString());
	                ps.setString(47,((Map<String, Object>)shopsList.get(i)).get("shyj").toString());
	                ps.setString(48,((Map<String, Object>)shopsList.get(i)).get("shry").toString());
	                ps.setString(49,((Map<String, Object>)shopsList.get(i)).get("shsj").toString());
	                ps.setString(50,((Map<String, Object>)shopsList.get(i)).get("txlx").toString());
	                ps.setString(51,((Map<String, Object>)shopsList.get(i)).get("yqll").toString());
	                ps.setString(52,((Map<String, Object>)shopsList.get(i)).get("sftxje").toString());
	                ps.setString(53,((Map<String, Object>)shopsList.get(i)).get("nyjfbl").toString());
	                ps.setString(54,((Map<String, Object>)shopsList.get(i)).get("blry").toString());
	                ps.setString(55,((Map<String, Object>)shopsList.get(i)).get("yjjh").toString());
	                ps.setString(56,((Map<String, Object>)shopsList.get(i)).get("sfstdk").toString());
	                ps.setString(57,((Map<String, Object>)shopsList.get(i)).get("ystxyh").toString());
	                ps.setString(58,((Map<String, Object>)shopsList.get(i)).get("sfyxdk").toString());
	                ps.setString(59,((Map<String, Object>)shopsList.get(i)).get("yxr").toString());
	                ps.setString(60,((Map<String, Object>)shopsList.get(i)).get("flcorzlc").toString());
	               }
	               public int getBatchSize()
	               {
	                return shopsList.size();
	               }
	        });
	}
	
	public QueryResult<CustomerInfor> findCustomerInforByFilterAndProductId1(CustomerInforFilter filter) {
		List<CustomerInfor> ls = customerInforDao.findCustomerInforByFilterAndProductId1(filter);
		int size = customerInforDao.findCustomerInforCountByFilterAndProductId(filter);
		QueryResult<CustomerInfor> qr = new QueryResult<CustomerInfor>(size,ls);
		return qr;
	}
	public List<CustomerInfor> findCustomerInforByFilterAndProductId2(CustomerInforFilter filter){
		return customerInforDao.findCustomerInforByFilterAndProductId2(filter);
	}
	public List<CustomerFirsthendFamilyCc> ffcustomerfirsthendccbyid(String khnm) {
		// TODO Auto-generated method stub
		return  commonDao.queryBySql(CustomerFirsthendFamilyCc.class, "select * from ty_customer_family_cc s where s.khnm='"+khnm+"'", null);
	}
	
}
