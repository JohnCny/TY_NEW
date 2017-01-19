package com.cardpay.pccredit.intopieces.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.common.UploadFileTool;
import com.cardpay.pccredit.customer.dao.CustomerInforDao;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.intopieces.constant.ApplicationStatusEnum;
import com.cardpay.pccredit.intopieces.constant.Constant;
import com.cardpay.pccredit.intopieces.dao.CustomerApplicationInfoDao;
import com.cardpay.pccredit.intopieces.dao.CustomerApplicationIntopieceWaitDao;
import com.cardpay.pccredit.intopieces.filter.CustomerApplicationProcessFilter;
import com.cardpay.pccredit.intopieces.filter.IntoPiecesFilter;
import com.cardpay.pccredit.intopieces.model.AppManagerAuditLog;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationInfo;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationProcess;
import com.cardpay.pccredit.intopieces.model.IntoPiecesFilters;
import com.cardpay.pccredit.intopieces.web.CustomerApplicationIntopieceWaitForm;
import com.cardpay.pccredit.riskControl.constant.RiskCreateTypeEnum;
import com.cardpay.pccredit.riskControl.filter.RiskCustomerFilter;
import com.cardpay.pccredit.riskControl.model.RiskCustomer;
import com.cardpay.pccredit.system.constants.NodeAuditTypeEnum;
import com.cardpay.pccredit.system.model.NodeAudit;
import com.cardpay.pccredit.system.service.NodeAuditService;
import com.cardpay.workflow.constant.ApproveOperationTypeEnum;
import com.cardpay.workflow.service.ProcessService;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.id.IDGenerator;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.modules.log.model.OperationLog;
import com.wicresoft.jrad.modules.log.service.UserLogService;
import com.wicresoft.util.spring.Beans;

/**
 * CustomerApplicationIntopieceWaitService类的描述
 * 
 * @author 王海东
 * @created on 2014-11-28
 * 
 * @version $Id:$
 */
@Service
public class CustomerApplicationIntopieceWaitService {

	@Autowired
	private CommonDao commonDao;

	@Autowired
	private CustomerApplicationIntopieceWaitDao customerApplicationIntopieceWaitDao;

	@Autowired
	private CustomerApplicationInfoDao customerApplicationInfoDao;

	@Autowired
	private NodeAuditService nodeAuditService;

	@Autowired
	private ProcessService processService;
	
	@Autowired
	private CustomerInforService customerInforService;
	
	@Autowired
	private IntoPiecesService intoPiecesService;
	
	@Autowired
	private CustomerInforDao customerInforDao;
	
	@Autowired
	private UserLogService userLogService;

	// 查询所有的进件包括审核的及未审核的
	public QueryResult<CustomerApplicationIntopieceWaitForm> findCustomerApplicationIntopieceWaitForm(CustomerApplicationProcessFilter filter) {
		List<CustomerApplicationIntopieceWaitForm> listCAI = customerApplicationIntopieceWaitDao.findCustomerApplicationIntopieceWaitForm(filter);
		int size = customerApplicationIntopieceWaitDao.findCustomerApplicationIntopieceWaitCountForm(filter);
		QueryResult<CustomerApplicationIntopieceWaitForm> qs = new QueryResult<CustomerApplicationIntopieceWaitForm>(size, listCAI);
		return qs;

	}
	
	// 查询所有的进件包括审核的及未审核的
	public QueryResult<CustomerApplicationIntopieceWaitForm> findCustomerApplicationIntopieceDecison(IntoPiecesFilter filter) {
		List<CustomerApplicationIntopieceWaitForm> listCAI = customerApplicationIntopieceWaitDao.findCustomerApplicationIntopieceDecisionForm(filter);
		int size = customerApplicationIntopieceWaitDao.findCustomerApplicationIntopieceDecisionCountForm(filter);
		QueryResult<CustomerApplicationIntopieceWaitForm> qs = new QueryResult<CustomerApplicationIntopieceWaitForm>(size, listCAI);
		return qs;

	}
	// 查询所有的进件通过审核的
	public QueryResult<IntoPiecesFilters> findCustomerApplicationIntopieceDecisons(IntoPiecesFilters filter) {
		List<IntoPiecesFilters> listCAI = customerApplicationIntopieceWaitDao.findCustomerApplicationIntopieceDecisionForms(filter);
		int size = customerApplicationIntopieceWaitDao.findCustomerApplicationIntopieceDecisionCountForms(filter);
		QueryResult<IntoPiecesFilters> qs = new QueryResult<IntoPiecesFilters>(size, listCAI);
		return qs;

	}
	// 根据serialNumber查询CUSTOMER_APPLICATION_PROCESS表
	public CustomerApplicationIntopieceWaitForm findCustomerApplicationProcessBySerialNumber(String serialNumber) {
		return customerApplicationIntopieceWaitDao.findCustomerApplicationProcessBySerialNumber(serialNumber);
	}

	// 申请进件审核
	public int updateCustomerApplicationProcess(String userId) {
		// 判断自己是否有审核任务
		int i = 0;
		if (customerApplicationIntopieceWaitDao.getCustomerApplicationInfoByUserId(userId) != 0) {
			return 0;
		}
		// 是否有审核的进件
		List<CustomerApplicationProcess> listNodeId = customerApplicationIntopieceWaitDao.findCustomerApplicationInfoAll();
		for (CustomerApplicationProcess customerApplicationProcess : listNodeId) {
			List<NodeAudit> listNode = nodeAuditService.findByNodeAuditByUserId(NodeAuditTypeEnum.Product.toString(), userId, customerApplicationProcess.getProductId());
			boolean falg = false;
			for (NodeAudit nodeAudit : listNode) {
				if (customerApplicationProcess.getProductId().equals(nodeAudit.getProductId()) && customerApplicationProcess.getNextNodeId().equals(nodeAudit.getId())) {
					customerApplicationProcess.setDelayAuditUser(userId);
					customerApplicationProcess.setAuditTime(new Date());
					i = customerApplicationIntopieceWaitDao.updateCustomerApplicationProcess(customerApplicationProcess);
					
					CustomerApplicationInfo applicationInfo = commonDao.findObjectById(CustomerApplicationInfo.class, customerApplicationProcess.getApplicationId());
					applicationInfo.setModifiedBy(userId);
					applicationInfo.setModifiedTime(new Date());
					commonDao.updateObject(applicationInfo);
					
					falg = true;
					break;
				}
			}
			if(falg){
				break;
			}
		}
		return i;
		// 循环所有的节点
	}

	public void updateCustomerApplicationProcessBySerialNumberApplicationInfo(HttpServletRequest request) throws Exception {
		CustomerApplicationInfo customerApplicationInfo = new CustomerApplicationInfo();
		CustomerApplicationProcess customerApplicationProcess = new CustomerApplicationProcess();
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String loginId = user.getId();
		String serialNumber = request.getParameter("serialNumber");
		String examineAmount = request.getParameter("examineAmount");
		String applicationStatus = request.getParameter("applicationStatus");
		String applicationId = request.getParameter("applicationId");
		String customerId = request.getParameter("customerId");
		String objection = request.getParameter("objection");
		if(objection.equals("true")){
			applicationStatus = ApproveOperationTypeEnum.OBJECTION.toString();
		}
		if(StringUtils.isNotEmpty(examineAmount)){
			examineAmount = (Double.parseDouble(examineAmount) * 100) + "";
		}
		//applicationStatus 必须是ApproveOperationTypeEnum中的通过，退回，拒绝三个类型
		String examineResutl = processService.examine(applicationId,serialNumber, loginId, applicationStatus, examineAmount,"","");
		//更新单据状态
	    if (examineResutl.equals(ApproveOperationTypeEnum.REJECTAPPROVE.toString()) ||
	    		examineResutl.equals(ApproveOperationTypeEnum.RETURNAPPROVE.toString()) ||
	    		examineResutl.equals(ApproveOperationTypeEnum.NORMALEND.toString())) {
			if(examineResutl.equals(ApproveOperationTypeEnum.REJECTAPPROVE.toString())){
				customerApplicationInfo.setStatus(Constant.REFUSE_INTOPICES);
			}
			if(examineResutl.equals(ApproveOperationTypeEnum.RETURNAPPROVE.toString())){
				customerApplicationInfo.setStatus(Constant.NOPASS_REPLENISH_INTOPICES);
				//退回时 删除提交申请备份的信息
				CustomerApplicationInfo returnApp = commonDao.findObjectById(CustomerApplicationInfo.class, applicationId);
				customerInforService.deleteCloneSubmitAppByReturn(returnApp.getCustomerId(), applicationId);
			}
			if(examineResutl.equals(ApproveOperationTypeEnum.NORMALEND.toString())){
				customerApplicationInfo.setFinalApproval(examineAmount);
				customerApplicationInfo.setStatus(Constant.APPROVED_INTOPICES);
			}
			customerApplicationInfo.setId(applicationId);
			customerApplicationInfo.setModifiedBy(user.getId());
			customerApplicationInfo.setModifiedTime(new Date());
			commonDao.updateObject(customerApplicationInfo);
			
			customerApplicationProcess.setNextNodeId(null);
		} else {
			customerApplicationInfo.setStatus(Constant.APPROVE_INTOPICES);
			customerApplicationInfo.setId(applicationId);
			customerApplicationInfo.setModifiedBy(user.getId());
			customerApplicationInfo.setModifiedTime(new Date());
			commonDao.updateObject(customerApplicationInfo);
			
			customerApplicationProcess.setNextNodeId(examineResutl);
		}
	    if(Constant.APPROVED_INTOPICES.equalsIgnoreCase(customerApplicationInfo.getStatus())){
	    	intoPiecesService.exportData(applicationId, customerId, null);
	    }
		if (StringUtils.isNotEmpty(applicationStatus) && applicationStatus.equals(ApplicationStatusEnum.RETURNAPPROVE)) {
			String fallbackReason = request.getParameter("reason");
			customerApplicationProcess.setFallbackReason(fallbackReason);
		} else if (StringUtils.isNotEmpty(applicationStatus) && applicationStatus.equals(ApplicationStatusEnum.REJECTAPPROVE)) {
			String refusalReason = request.getParameter("reason");
			customerApplicationProcess.setRefusalReason(refusalReason);
		}
		customerApplicationProcess.setProcessOpStatus(applicationStatus);
		customerApplicationProcess.setSerialNumber(serialNumber);
		customerApplicationProcess.setExamineAmount(examineAmount);
		customerApplicationProcess.setAuditUser(loginId);
		customerApplicationProcess.setCreatedTime(new Date());
		customerApplicationProcess.setExamineAmount(examineAmount);
//		customerApplicationProcess.setDelayAuditUser(user.getId());//清空字段值 
		customerApplicationIntopieceWaitDao.updateCustomerApplicationProcessBySerialNumber(customerApplicationProcess);

	}
	
	/**
	 * updateCustomerApplicationProcessBySerialNumber
	 * @param request
	 * @throws Exception
	 */
	public void updateCustomerApplicationProcessBySerialNumber(HttpServletRequest request) throws Exception {
		
		String cyUser1 = request.getParameter("cyUser1");
		String cyUser2 = request.getParameter("cyUser2");
		String fdUser = request.getParameter("fdUser");
		String hkfs = request.getParameter("hkfs");
		String beiZhu = request.getParameter("beiZhu");
		String sdUser = request.getParameter("sdUser");
		String decisionTerm = request.getParameter("decisionTerm");
		String auditType = request.getParameter("auditType");
		String lv = request.getParameter("decisionRate");
		String productId = request.getParameter("productId");
		
		//审贷或管理岗支持  修改贷款产品
		String prodId = request.getParameter("prodId");
		
		String custManagerId = request.getParameter("custManagerId");
		
		CustomerApplicationInfo customerApplicationInfo = new CustomerApplicationInfo();
		CustomerApplicationProcess customerApplicationProcess = new CustomerApplicationProcess();
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String loginId = user.getId();
		String serialNumber = request.getParameter("serialNumber");
		String examineAmount = request.getParameter("decisionAmount");
		String applicationStatus = request.getParameter("status");
		String applicationId = request.getParameter("id");
		String customerId = request.getParameter("customerId");
		
		/*if(StringUtils.isNotEmpty(examineAmount)){
			examineAmount = (Double.parseDouble(examineAmount) * 100) + "";
		}*/
		
		//applicationStatus 必须是ApproveOperationTypeEnum中的通过，退回，拒绝三个类型
		String examineResutl = processService.examine(applicationId,serialNumber, loginId, applicationStatus, examineAmount,productId,auditType);
		//更新单据状态
	    if (examineResutl.equals(ApproveOperationTypeEnum.REJECTAPPROVE.toString()) ||
	    		examineResutl.equals(ApproveOperationTypeEnum.RETURNAPPROVE.toString()) ||
	    		examineResutl.equals(ApproveOperationTypeEnum.NORMALEND.toString())) {
	    	
			if(examineResutl.equals(ApproveOperationTypeEnum.REJECTAPPROVE.toString())){
				customerApplicationInfo.setStatus(Constant.REFUSE_INTOPICES);
			}
			
			if(examineResutl.equals(ApproveOperationTypeEnum.RETURNAPPROVE.toString())){
				//customerApplicationInfo.setStatus("nopass");
				//退回时 删除提交申请备份的信息
				//CustomerApplicationInfo returnApp = commonDao.findObjectById(CustomerApplicationInfo.class, applicationId);
				//customerInforService.deleteCloneSubmitAppByReturn(returnApp.getCustomerId(), applicationId);
			}
			
			if(examineResutl.equals(ApproveOperationTypeEnum.NORMALEND.toString())){
				customerApplicationInfo.setFinalApproval(examineAmount);
				customerApplicationInfo.setStatus(Constant.APPROVED_INTOPICES);
			}
			
			customerApplicationInfo.setId(applicationId);
			customerApplicationInfo.setModifiedBy(user.getId());
			customerApplicationInfo.setModifiedTime(new Date());
			commonDao.updateObject(customerApplicationInfo);
			customerApplicationProcess.setNextNodeId(null);
		} else {
			customerApplicationInfo.setStatus(Constant.APPROVE_INTOPICES);
			customerApplicationInfo.setId(applicationId);
			customerApplicationInfo.setModifiedBy(user.getId());
			customerApplicationInfo.setModifiedTime(new Date());
			commonDao.updateObject(customerApplicationInfo);
			customerApplicationProcess.setNextNodeId(examineResutl);
		}
	    
	    
		if (StringUtils.isNotEmpty(applicationStatus) && applicationStatus.equals(ApplicationStatusEnum.RETURNAPPROVE)) {
			String fallbackReason = request.getParameter("decisionRefusereason");
			customerApplicationProcess.setFallbackReason(fallbackReason);
		}else if (StringUtils.isNotEmpty(applicationStatus) && applicationStatus.equals(ApplicationStatusEnum.REJECTAPPROVE)) {
			String refusalReason = request.getParameter("decisionRefusereason");
			customerApplicationProcess.setRefusalReason(refusalReason);
			//添加风险名单
			RiskCustomerFilter filter = new RiskCustomerFilter();
			filter.setCustomerId(customerId);
			List<RiskCustomer> oldRisk = commonDao.findObjectsByFilter(RiskCustomer.class, filter).getItems();
			if(oldRisk.size()<1){
				//拒绝原因
				String refuseReason = request.getParameter("decisionRefusereason");
				RiskCustomer riskCustomer = new RiskCustomer();
				riskCustomer.setId(IDGenerator.generateID());
				riskCustomer.setCustomerId(customerId);
				riskCustomer.setRefuseReason(refuseReason);
				riskCustomer.setCreatedTime(new Date());
				riskCustomer.setReportedIdManager(user.getId());
				riskCustomer.setCreatedBy(user.getId());
				riskCustomer.setRiskCreateType(RiskCreateTypeEnum.manual.toString());
				riskCustomer.setProductId(productId);
				riskCustomer.setCustManagerId(custManagerId);
				commonDao.insertObject(riskCustomer);
			}
		}
		customerApplicationProcess.setProcessOpStatus(applicationStatus);
		customerApplicationProcess.setSerialNumber(serialNumber);
		customerApplicationProcess.setExamineAmount(examineAmount);
		customerApplicationProcess.setAuditUser(loginId);
		customerApplicationProcess.setCreatedTime(new Date());
		customerApplicationProcess.setExamineLv(lv);
		customerApplicationIntopieceWaitDao.updateCustomerApplicationProcessBySerialNumber(customerApplicationProcess);
		
		
		if(applicationStatus.equals("APPROVE")&&!StringUtils.isEmpty(auditType)){
			//修改进件产品
			if(!StringUtils.isEmpty(prodId)){
				updateAppliactionProd(customerApplicationInfo,prodId);
				//修改local_excel 
				updateLocalExcel(prodId,applicationId);
				//修改 local_image
				updateLocalImage(prodId,applicationId);
			}
			//select 
			int count = customerInforDao.findAppAuditLog(applicationId,auditType);
			if(count == 0){
				AppManagerAuditLog log = new AppManagerAuditLog();
				log.setId(IDGenerator.generateID());
				log.setApplicationId(applicationId);
				log.setAuditType(auditType);//1-初审 2-审贷
				log.setUserId_1(cyUser1);
				log.setUserId_2(cyUser2);
				log.setUserId_3(fdUser);//辅调
				log.setExamineAmount(examineAmount);
				log.setExamineLv(lv);
				log.setUserId_4(sdUser);
				log.setQx(decisionTerm);
				log.setHkfs(hkfs);
				log.setBeiZhu(beiZhu);
				commonDao.insertObject(log);
			}else{
				//update
				 customerInforDao.updateAppAuditLog(applicationId,
													auditType,
													cyUser1,
													cyUser2,
													fdUser,examineAmount,lv,decisionTerm,sdUser,hkfs,beiZhu);
			}
		}
		
		//日志记录
		OperationLog ol = new OperationLog();
	    ol.setUser_id(loginId);
	    ol.setUser_login(user.getDisplayName());
	    
	    if(auditType.equals("1")){
	    	ol.setModule("进件初审");
	    }else if(auditType.equals("2")){
	    	ol.setModule("审贷决议");
	    }else if(auditType.equals("3")){
	    	ol.setModule("小微负责人审批");
	    }else if(auditType.equals("4")){
	    	ol.setModule("零售业务部负责人审批");
	    }else if(auditType.equals("5")){
	    	ol.setModule("行长审批");
	    }
	    
	    ol.setOperation_result(applicationStatus);
	    ol.setOperation_name("AUDIT");
	    ol.setIp_address(request.getRemoteAddr());
		userLogService.addUserLog(ol);
	}
	
	public void updateAppliactionProd(CustomerApplicationInfo customerApplicationInfo,String prodId){
		customerApplicationInfo.setProductId(prodId);
		commonDao.updateObject(customerApplicationInfo);
	}
	
	public void updateLocalExcel(String prodId,String applicationId){
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("prodId", prodId);
		params.put("applicationId", applicationId);
		String sql = "update LOCAL_EXCEL set PRODUCT_ID=#{prodId} where APPLICATION_ID=#{applicationId}";
		commonDao.queryBySql(sql, params);
	}
	
	public void updateLocalImage(String prodId,String applicationId){
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("prodId", prodId);
		params.put("applicationId", applicationId);
		String sql = "update LOCAL_IMAGE set PRODUCT_ID=#{prodId} where APPLICATION_ID=#{applicationId}";
		commonDao.queryBySql(sql, params);
	}

}
