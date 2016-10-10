package com.cardpay.pccredit.jnpad.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.customer.dao.CustomerInforDao;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.intopieces.constant.ApplicationStatusEnum;
import com.cardpay.pccredit.intopieces.constant.Constant;
import com.cardpay.pccredit.intopieces.dao.CustomerApplicationIntopieceWaitDao;
import com.cardpay.pccredit.intopieces.filter.IntoPiecesFilter;
import com.cardpay.pccredit.intopieces.model.AppManagerAuditLog;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationInfo;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationProcess;
import com.cardpay.pccredit.intopieces.model.TyApplicationLog;
import com.cardpay.pccredit.intopieces.web.CustomerApplicationIntopieceWaitForm;
import com.cardpay.pccredit.jnpad.dao.JnpadIntopiecesDecisionDao;
import com.cardpay.pccredit.jnpad.model.ManagerInfoForm;
import com.cardpay.pccredit.jnpad.model.ProductAttributes;
import com.cardpay.pccredit.riskControl.constant.RiskCreateTypeEnum;
import com.cardpay.pccredit.riskControl.filter.RiskCustomerFilter;
import com.cardpay.pccredit.riskControl.model.RiskCustomer;
import com.cardpay.workflow.constant.ApproveOperationTypeEnum;
import com.cardpay.workflow.service.ProcessService;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.id.IDGenerator;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.util.spring.Beans;

@Service
public class JnpadIntopiecesDecisionService {
	@Autowired
	JnpadIntopiecesDecisionDao jnpadIntopiecesDecisionDao;

	@Autowired
	private CommonDao commonDao;

	@Autowired
	private ProcessService processService;

	@Autowired
	private CustomerApplicationIntopieceWaitDao customerApplicationIntopieceWaitDao;

	@Autowired
	private CustomerInforDao customerInforDao;

	//查询当前客户经理进件初审信息
	public QueryResult<CustomerApplicationIntopieceWaitForm> findCustomerApplicationIntopieceDecison(IntoPiecesFilter filter) {
		List<CustomerApplicationIntopieceWaitForm> listCAI = jnpadIntopiecesDecisionDao.findCustomerApplicationIntopieceDecisionForm(filter);
		int size = jnpadIntopiecesDecisionDao.findCustomerApplicationIntopieceDecisionCountForm(filter);
		QueryResult<CustomerApplicationIntopieceWaitForm> qs = new QueryResult<CustomerApplicationIntopieceWaitForm>(size, listCAI);
		return qs;
	}

	public List<ManagerInfoForm> findManagerInfo() {

		return jnpadIntopiecesDecisionDao.findManagerInfo();
	}
	//提交结论

	public void updateCustomerApplicationProcessBySerialNumber(HttpServletRequest request) {


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

		String prodId = request.getParameter("proodId");
		String custManagerId = request.getParameter("custManagerId");

		CustomerApplicationInfo customerApplicationInfo = new CustomerApplicationInfo();
		CustomerApplicationProcess customerApplicationProcess = new CustomerApplicationProcess();
		String loginId = request.getParameter("userId");
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
			customerApplicationInfo.setModifiedBy(loginId);
			customerApplicationInfo.setModifiedTime(new Date());
			commonDao.updateObject(customerApplicationInfo);
			customerApplicationProcess.setNextNodeId(null);
		} else {
			customerApplicationInfo.setStatus(Constant.APPROVE_INTOPICES);
			customerApplicationInfo.setId(applicationId);
			customerApplicationInfo.setModifiedBy(loginId);
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
				riskCustomer.setReportedIdManager(loginId);
				riskCustomer.setCreatedBy(loginId);
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
			//产品种类修改
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
				log.setUserId_3(fdUser);
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



	}
	//查询参与初审客户经理
	public AppManagerAuditLog findAppManagerAuditLog(String appId,String auditType){
		
		return jnpadIntopiecesDecisionDao.findAppManagerAuditLog(appId,auditType);
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

	public List<ProductAttributes> findProductList() {
		// TODO Auto-generated method stub
		return jnpadIntopiecesDecisionDao.findProductList();
	}
//	//提交审贷决议
//	public void update(CustomerApplicationInfo customerApplicationInfo,HttpServletRequest request) {
//		String cyUser1 = request.getParameter("cyUser1");
//		String cyUser2 = request.getParameter("cyUser2");
//		String fdUser = request.getParameter("fdUser");
//		String userId =request.getParameter("userId");
//		//更新进件
//		commonDao.updateObject(customerApplicationInfo);
//		CustomerApplicationInfo applicationInfo = commonDao.findObjectById(CustomerApplicationInfo.class, customerApplicationInfo.getId());
//		//获取客户信息
//		CustomerInfor info = commonDao.findObjectById(CustomerInfor.class, applicationInfo.getCustomerId());
//		String status = customerApplicationInfo.getStatus();
//		//拒绝，加入风险名单(没有则添加到风险名单)
//		if(status.equals("refuse")){
//			RiskCustomerFilter filter = new RiskCustomerFilter();
//			filter.setCustomerId(info.getId());
//			List<RiskCustomer> oldRisk = commonDao.findObjectsByFilter(RiskCustomer.class, filter).getItems();
//			if(oldRisk.size()<1){
//				//拒绝原因
//				String refuseReason = request.getParameter("decisionRefusereason");
//				IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
//				RiskCustomer riskCustomer = new RiskCustomer();
//				riskCustomer.setId(IDGenerator.generateID());
//				riskCustomer.setCustomerId(info.getId());
//				riskCustomer.setRefuseReason(refuseReason);
//				riskCustomer.setCreatedTime(new Date());
//				riskCustomer.setReportedIdManager(userId);
//				riskCustomer.setCreatedBy(userId);
//				riskCustomer.setRiskCreateType(RiskCreateTypeEnum.manual.toString());
//				commonDao.insertObject(riskCustomer);
//			}
//		}
//		//添加审批客户经理记录
//		if(!cyUser1.equals("0")){
//			TyApplicationLog log = new TyApplicationLog();
//			log.setId(IDGenerator.generateID());
//			log.setApplicationId(customerApplicationInfo.getId());
//			log.setUserId(cyUser1);
//			log.setType(Constant.APPSP);
//			commonDao.insertObject(log);
//		}
//		if(!cyUser2.equals("0")){
//			TyApplicationLog log = new TyApplicationLog();
//			log.setId(IDGenerator.generateID());
//			log.setApplicationId(customerApplicationInfo.getId());
//			log.setUserId(cyUser2);
//			log.setType(Constant.APPSP);
//			commonDao.insertObject(log);
//		}
//		//审批通过，添加辅调客户经理
//		if(status.equals("approved")){
//			TyApplicationLog log = new TyApplicationLog();
//			log.setId(IDGenerator.generateID());
//			log.setApplicationId(customerApplicationInfo.getId());
//			log.setUserId(fdUser);
//			log.setType(Constant.APPFD);
//			commonDao.insertObject(log);
//		}
//	}

	public List<ManagerInfoForm> findteacherInfo() {
		// TODO Auto-generated method stub
		return jnpadIntopiecesDecisionDao.findteacherInfo();
	}
}

