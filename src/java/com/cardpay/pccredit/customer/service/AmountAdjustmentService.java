package com.cardpay.pccredit.customer.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.customer.constant.AmountAdjustmentApproveStatusEnum;
import com.cardpay.pccredit.customer.constant.WfProcessInfoType;
import com.cardpay.pccredit.customer.dao.AmountAdjustmentDao;
import com.cardpay.pccredit.customer.dao.comdao.AmountAdjustmentComdao;
import com.cardpay.pccredit.customer.filter.AmountAdjustmentFilter;
import com.cardpay.pccredit.customer.model.AmountAdjustment;
import com.cardpay.pccredit.customer.model.AmountAdjustmentProcess;
import com.cardpay.pccredit.customer.web.AmountAdjustmentForm;
import com.cardpay.pccredit.customer.web.AmountAdjustmentProcessForm;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationInfo;
import com.cardpay.pccredit.system.constants.NodeAuditTypeEnum;
import com.cardpay.pccredit.system.constants.YesNoEnum;
import com.cardpay.pccredit.system.model.NodeAudit;
import com.cardpay.pccredit.system.model.NodeControl;
import com.cardpay.pccredit.system.service.NodeAuditService;
import com.cardpay.workflow.constant.ApproveOperationTypeEnum;
import com.cardpay.workflow.models.WfProcessInfo;
import com.cardpay.workflow.models.WfStatusInfo;
import com.cardpay.workflow.models.WfStatusResult;
import com.cardpay.workflow.service.ProcessService;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.modules.privilege.model.User;
import com.wicresoft.util.spring.Beans;

/**
 * 
 * 描述 ：客户调额service
 * @author 张石树
 *
 * 2014-12-2 下午3:28:12
 */
@Service
public class AmountAdjustmentService {
	
	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private AmountAdjustmentDao amountAdjustmentDao;
	
	@Autowired
	private NodeAuditService nodeAuditService;

	@Autowired
	private ProcessService processService;
	
	@Autowired
	private AmountAdjustmentComdao amountAdjustmentComdao;
	
	/**
	 * 调额插入客户调额信息
	 * @param appId
	 * @param adjustmentAccount
	 * @param adjustmentType 
	 * @param user
	 * @throws SQLException 
	 */
	public void insertAmountAdjustment(String appId, String adjustmentAccount,
			String adjustmentType, User user) {
		//进件信息
		CustomerApplicationInfo applicationInfo = commonDao.findObjectById(CustomerApplicationInfo.class, appId);
		
		AmountAdjustment amountAdjustment = new AmountAdjustment();
		amountAdjustment.setCustomerId(applicationInfo.getCustomerId());
		amountAdjustment.setProductId(applicationInfo.getProductId());
		amountAdjustment.setApproval(AmountAdjustmentApproveStatusEnum.Audit.name());
		amountAdjustment.setActualAmount(applicationInfo.getActualQuote());
		amountAdjustment.setApprovalLimit(adjustmentAccount);
		amountAdjustment.setOriginalAmount(applicationInfo.getApplyQuota());
		amountAdjustment.setAdjustAmountTime(new Date());
		amountAdjustment.setCreatedBy(user.getId());
		amountAdjustment.setCreatedTime(new Date());
		amountAdjustment.setModifiedBy(user.getId());
		amountAdjustment.setModifiedTime(new Date());
		amountAdjustment.setAdjustmentType(adjustmentType);
		commonDao.insertObject(amountAdjustment);
		
		//添加额度调整申请流程
		WfProcessInfo wf=new WfProcessInfo();
		wf.setProcessType(WfProcessInfoType.amount_adjustment_type);//TODO
		wf.setVersion("1");
		commonDao.insertObject(wf);
		List<NodeAudit> list = nodeAuditService.findByNodeTypeAndProductId(
				NodeAuditTypeEnum.ProductAmountAdjust.name(), applicationInfo.getProductId());
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
					AmountAdjustmentProcess amountAdjustmentProcess =new AmountAdjustmentProcess();
					String serialNumber = processService.start(wf.getId());
					amountAdjustmentProcess.setSerialNumber(serialNumber);
					amountAdjustmentProcess.setNextNodeId(nodeAudit.getId());
					amountAdjustmentProcess.setAmountAdjustmentId(amountAdjustment.getId());
					commonDao.insertObject(amountAdjustmentProcess);
					
					AmountAdjustment adjustment = commonDao.findObjectById(AmountAdjustment.class, amountAdjustment.getId());
					adjustment.setSerialNumber(serialNumber);
					commonDao.updateObject(adjustment);
				}
			}
			
			if(nodeAudit.getIsend().equals(YesNoEnum.YES.name())){
				endBool=true;
			}
		}
		
		//节点关系
		List<NodeControl> nodeControls = nodeAuditService.findNodeControlByNodeTypeAndProductId(NodeAuditTypeEnum.ProductAmountAdjust.name(), applicationInfo.getProductId());
		for(NodeControl control : nodeControls){
			WfStatusResult wfStatusResult = new WfStatusResult();
			wfStatusResult.setCurrentStatus(nodeWfStatusMap.get(control.getCurrentNode()));
			wfStatusResult.setNextStatus(nodeWfStatusMap.get(control.getNextNode()));
			wfStatusResult.setExamineResult(control.getCurrentStatus());
			commonDao.insertObject(wfStatusResult);
		}
		
	}

	/**
	 * 分页查询
	 * @param filter
	 * @return
	 */
	public QueryResult<AmountAdjustmentForm> findAmountAdjustmentFilter(
			AmountAdjustmentFilter filter) {
		List<AmountAdjustmentForm> riskCustomers = amountAdjustmentDao.findAmountAdjustmentByFilter(filter);
		int size = amountAdjustmentDao.findAmountAdjustmentCountByFilter(filter);
		QueryResult<AmountAdjustmentForm> qs = new QueryResult<AmountAdjustmentForm>(size, riskCustomers);
		return qs;
	}

	/**
	 * 待审核查询分页
	 * @param filter
	 * @return
	 */
	public QueryResult<AmountAdjustmentForm> findAmountAdjustmentWaitApproveFilter(
			AmountAdjustmentFilter filter) {
		List<AmountAdjustmentForm> riskCustomers = amountAdjustmentDao.findAmountAdjustmentWaitApproveByFilter(filter);
		int size = amountAdjustmentDao.findAmountAdjustmentWaitApproveCountByFilter(filter);
		QueryResult<AmountAdjustmentForm> qs = new QueryResult<AmountAdjustmentForm>(size, riskCustomers);
		return qs;
	}

	/**
	 * 申请审核额度调整
	 * @param userId
	 * @return
	 */
	public int applayApproveAmountAdjustment(String userId) {
		//判断自己是否有审核任务
		int i=0;
		if(amountAdjustmentComdao.getWaitProcessAmountAdjustmentCountByUserId(userId)!=0){
			return 0;
		}
		//是否有审核的进件
		List<AmountAdjustmentProcessForm> adjustmentProcessForms=amountAdjustmentDao.findWaitProcessAmountAdjustmentAll();
		for(AmountAdjustmentProcessForm adjustmentProcessForm:adjustmentProcessForms){
			List<NodeAudit> listNode=nodeAuditService.findByNodeAuditByUserId(
					NodeAuditTypeEnum.ProductAmountAdjust.toString(), userId, adjustmentProcessForm.getProductId());
			boolean flag = false;
			for(NodeAudit nodeAudit:listNode){
				if(adjustmentProcessForm.getProductId().equals(nodeAudit.getProductId())
						&&adjustmentProcessForm.getNextNodeId().equals(nodeAudit.getId())){
					adjustmentProcessForm.setDelayAuditUser(userId);
					i=amountAdjustmentDao.updateWaitProcessAmountAdjustment(adjustmentProcessForm);
					
					AmountAdjustment adjustment = commonDao.findObjectById(AmountAdjustment.class, adjustmentProcessForm.getAmountAdjustmentId());
					adjustment.setModifiedBy(userId);
					adjustment.setModifiedTime(new Date());
					commonDao.updateObject(adjustment);
					flag = true;
					break;
				}
			}
			if(flag){
				break;
			}
		}
		return i;
	}

	/**
	 * 审核额度调整
	 * @param amountAdjustmentForm
	 * @param request
	 */
	public void approveAmountAdjustment(
			AmountAdjustmentForm amountAdjustmentForm,
			HttpServletRequest request) {
		User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
		String approveStatus = amountAdjustmentForm.getApproveStatus(); 
		AmountAdjustment amountAdjustment = commonDao.findObjectById(AmountAdjustment.class, amountAdjustmentForm.getId());
		
		AmountAdjustmentProcess amountAdjustmentProcess = amountAdjustmentComdao.findAmountAdjustmentProcess(amountAdjustmentForm.getId(), amountAdjustmentForm.getSerialNumber());
		
		if (StringUtils.isNotEmpty(approveStatus)&&approveStatus.equals(ApproveOperationTypeEnum.RETURNAPPROVE)) {
			String fallbackReason = request.getParameter("reason");
			amountAdjustmentProcess.setFallbackReason(fallbackReason);
		}else if(StringUtils.isNotEmpty(approveStatus)&&approveStatus.equals(ApproveOperationTypeEnum.REJECTAPPROVE)){
			amountAdjustmentProcess.setRefusalReason(amountAdjustmentForm.getReason());
		}
	    String examineResutl = processService.examine("",amountAdjustmentForm.getSerialNumber(), user.getId(), approveStatus, amountAdjustmentForm.getApprovalLimit(),"","");
		
	    //更新单据状态
	    if (examineResutl.equals(ApproveOperationTypeEnum.REJECTAPPROVE.toString()) ||
	    		examineResutl.equals(ApproveOperationTypeEnum.RETURNAPPROVE.toString()) ||
	    		examineResutl.equals(ApproveOperationTypeEnum.NORMALEND.toString())) {
			if(examineResutl.equals(ApproveOperationTypeEnum.REJECTAPPROVE.toString())){
				amountAdjustment.setApproval(AmountAdjustmentApproveStatusEnum.Refuse.toString());
			}
			if(examineResutl.equals(ApproveOperationTypeEnum.RETURNAPPROVE.toString())){
				amountAdjustment.setApproval(AmountAdjustmentApproveStatusEnum.Back.toString());
			}
			if(examineResutl.equals(ApproveOperationTypeEnum.NORMALEND.toString())){
				amountAdjustment.setApproval(AmountAdjustmentApproveStatusEnum.Success.toString());
			}
			amountAdjustment.setId(amountAdjustmentForm.getId());
			amountAdjustment.setModifiedBy(user.getId());
			amountAdjustment.setModifiedTime(new Date());
			commonDao.updateObject(amountAdjustment);
			
			amountAdjustmentProcess.setNextNodeId(null);
		} else {
			amountAdjustment.setApproval(AmountAdjustmentApproveStatusEnum.Audit.toString());
			amountAdjustment.setId(amountAdjustmentForm.getId());
			amountAdjustment.setModifiedBy(user.getId());
			amountAdjustment.setModifiedTime(new Date());
			commonDao.updateObject(amountAdjustment);
			
			amountAdjustmentProcess.setNextNodeId(examineResutl);
		}
	    
	    amountAdjustmentProcess.setProcessOpStatus(approveStatus);
		amountAdjustmentProcess.setExamineAmount(amountAdjustmentForm.getApprovalLimit());
		amountAdjustmentProcess.setAuditUser(user.getId());
		amountAdjustmentProcess.setAuditTime(new Date());
		
		amountAdjustmentDao.updateAmountAdjustmentProcess(amountAdjustmentProcess);
	}

	public AmountAdjustmentForm findWaitApproveAmountAdjustById(String id) {
		return amountAdjustmentDao.findWaitApproveAmountAdjustById(id);
	}

}
