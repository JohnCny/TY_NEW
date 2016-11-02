package com.cardpay.pccredit.riskControl.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.customer.model.Maintenance;
import com.cardpay.pccredit.customer.service.MaintenanceService;
import com.cardpay.pccredit.notification.constant.NotificationEnum;
import com.cardpay.pccredit.notification.service.NotificationService;
import com.cardpay.pccredit.riskControl.constant.CreateWayEnum;
import com.cardpay.pccredit.riskControl.constant.RiskControlRole;
import com.cardpay.pccredit.riskControl.constant.RiskReviewProcessAuditResults;
import com.cardpay.pccredit.riskControl.dao.RiskReviewProcessDao;
import com.cardpay.pccredit.riskControl.filter.RiskCustomerFilter;
import com.cardpay.pccredit.riskControl.filter.RiskReviewProcessFilter;
import com.cardpay.pccredit.riskControl.model.RiskConsiderations;
import com.cardpay.pccredit.riskControl.model.RiskCustomer;
import com.cardpay.pccredit.riskControl.model.RiskReviewProcess;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.model.QueryResult;

/**
 * @author chenzhifang
 *
 * 2014-11-5下午4:40:22
 */
@Service
public class RiskReviewProcessService {
	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private RiskReviewProcessDao riskReviewProcessDao;
	
	@Autowired
	private MaintenanceService maintenanceService;
	
	@Autowired
	private RiskConsiderationsService riskConsiderationsService;
	
	@Autowired
	private NotificationService notificationService;
	
	/**
	 * 过滤查询
	 * @param filter
	 * @return
	 */
	public QueryResult<RiskReviewProcess> findRiskReviewProcessByFilter(RiskReviewProcessFilter filter) {
		List<RiskReviewProcess> RiskReviewProcess = riskReviewProcessDao.findRiskReviewProcessByFilter(filter);
		int size = riskReviewProcessDao.findRiskReviewProcessCountByFilter(filter);
		QueryResult<RiskReviewProcess> qs = new QueryResult<RiskReviewProcess>(size, RiskReviewProcess);
		return qs;
	}
	
	/**
	 * 更新风险事项审核流程
	 * @param riskCustomer
	 * @return
	 */
	public int updateRiskReviewProcess(RiskReviewProcess riskReviewProcess) {
		riskReviewProcess.setModifiedTime(Calendar.getInstance().getTime());
		
		return commonDao.updateObject(riskReviewProcess);
	}
	
	/**
	 * 插入风险事项审核流程
	 * @param riskCustomer
	 * @return
	 */
	public int insertRiskReviewProcess(RiskReviewProcess riskReviewProcess) {
		if(riskReviewProcess.getCreatedTime() != null){
			riskReviewProcess.setCreatedTime(Calendar.getInstance().getTime());
		}
		if(riskReviewProcess.getModifiedTime() != null){
			riskReviewProcess.setModifiedTime(Calendar.getInstance().getTime());
		}
		return commonDao.insertObject(riskReviewProcess);
	}

	/**
	 * 删除风险事项审核流程
	 * @param riskCustomerId
	 * @return
	 */
	public int deleteRiskReviewProcess(String riskReviewProcessId) {
		return commonDao.deleteObject(RiskReviewProcess.class, riskReviewProcessId);
	}

	/**
	 * 把机构主管拒绝后超过指定天数的记录改为可直接上报卡中心
	 * @return
	 */
	public int updateStatusToUnreportedCardcenter(String maxDay){
		return riskReviewProcessDao.updateStatusToUnreportedCardcenter(maxDay);
	}
	
	/**
	 * 通过ID查找风险事项审核流程
	 * @param filter
	 * @return
	 */
	public RiskReviewProcess findRiskReviewProcessById(String riskReviewProcessId) {
		return commonDao.findObjectById(RiskReviewProcess.class, riskReviewProcessId);
	}
	
	/**
	 * 客户经理上报风险事项审核流程
	 * @param filter
	 * @return
	 */
	public boolean reportRiskReviewProcess(RiskReviewProcess riskReviewProcess){
		boolean flag = true;
		try{
			if (StringUtils.isEmpty(riskReviewProcess.getId())
					&& StringUtils.isNotEmpty(riskReviewProcess.getReportedManagerId())
					&& StringUtils.isNotEmpty(riskReviewProcess.getRiskIssuesId())) {
				// 设置阶段为客户经理上报
				riskReviewProcess.setPhase(RiskControlRole.manager.toString());
				riskReviewProcess.setCreatedBy(riskReviewProcess.getReportedManagerId());
				riskReviewProcess.setModifiedBy(riskReviewProcess.getCreatedBy());
				riskReviewProcess.setCreatedTime(new Date());
				riskReviewProcess.setModifiedTime(riskReviewProcess.getCreatedTime());
				riskReviewProcess.setResultsOfAudit(RiskReviewProcessAuditResults.PASS.toString());
				// 插入风险事项审核流程（客户经理）
				insertRiskReviewProcess(riskReviewProcess);
				
				// 主管风险事项审核流程
				RiskReviewProcess supervisorProcess = new RiskReviewProcess();
				supervisorProcess.setRiskIssuesId(riskReviewProcess.getRiskIssuesId());
				supervisorProcess.setReportedManagerId(riskReviewProcess.getReportedManagerId());
				// 设置创建时间
				supervisorProcess.setCreatedTime(riskReviewProcess.getCreatedTime());
				// 设置修改时间
				supervisorProcess.setModifiedTime(riskReviewProcess.getCreatedTime());
				// 设置阶段为主管审批
				supervisorProcess.setPhase(RiskControlRole.supervisor.toString());
				// 创建人为客户经理
				supervisorProcess.setCreatedBy(riskReviewProcess.getReportedManagerId());
				supervisorProcess.setModifiedBy(supervisorProcess.getCreatedBy());
				// 插入风险事项审核流程(主管)
				insertRiskReviewProcess(supervisorProcess);
				
				// 卡中心风险事项审核流程
				RiskReviewProcess cardcenterProcess = new RiskReviewProcess();
				cardcenterProcess.setRiskIssuesId(riskReviewProcess.getRiskIssuesId());
				cardcenterProcess.setReportedManagerId(riskReviewProcess.getReportedManagerId());
				// 设置创建时间
				cardcenterProcess.setCreatedTime(riskReviewProcess.getCreatedTime());
				// 设置修改时间
				cardcenterProcess.setModifiedTime(riskReviewProcess.getCreatedTime());
				// 设置阶段为主管审批
				cardcenterProcess.setPhase(RiskControlRole.cardcenter.toString());
				// 创建人为空， 等主管审批通过后为主管id
				cardcenterProcess.setCreatedBy(null);
				cardcenterProcess.setModifiedBy(cardcenterProcess.getCreatedBy());
				// 插入风险事项审核流程(卡中心)
				insertRiskReviewProcess(cardcenterProcess);
			}else{
				RiskReviewProcessFilter filter = new RiskReviewProcessFilter();
				filter.setPhase(RiskControlRole.cardcenter.toString());
				filter.setReportedManagerId(riskReviewProcess.getReportedManagerId());
				filter.setRiskIssuesId(riskReviewProcess.getRiskIssuesId());
				filter.setCreatedTime(riskReviewProcess.getCreatedTime());
				QueryResult<RiskReviewProcess> qr = commonDao.findObjectsByFilter(RiskReviewProcess.class, filter);
				
				if(!qr.getItems().isEmpty()){
					RiskReviewProcess process = qr.getItems().get(0);
					process.setCreatedBy(riskReviewProcess.getCreatedBy());
					updateRiskReviewProcess(process);
				}
				
				riskReviewProcess.setResultsOfAudit(RiskReviewProcessAuditResults.PASS.toString());
				updateRiskReviewProcess(riskReviewProcess);
			}
		} catch(Exception e){
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 确认风险事项审核流程
	 * @param filter
	 * @return
	 */
	public boolean confirmedRiskReviewProcess(RiskReviewProcess riskReviewProcess){
		boolean flag = false;
		try{
			if(StringUtils.isNotEmpty(riskReviewProcess.getCreatedBy())){
				riskReviewProcess.setAuditTime(Calendar.getInstance().getTime());
				riskReviewProcess.setResultsOfAudit(RiskReviewProcessAuditResults.PASS.toString());
				if(RiskControlRole.supervisor.toString().equals(riskReviewProcess.getPhase())){
					RiskReviewProcessFilter filter = new RiskReviewProcessFilter();
					filter.setPhase(RiskControlRole.cardcenter.toString());
					filter.setReportedManagerId(riskReviewProcess.getReportedManagerId());
					filter.setRiskIssuesId(riskReviewProcess.getRiskIssuesId());
					filter.setCreatedTime(riskReviewProcess.getCreatedTime());
					// 获取卡中心审批记录
					QueryResult<RiskReviewProcess> qr = commonDao.findObjectsByFilter(RiskReviewProcess.class, filter);
					
					if(!qr.getItems().isEmpty()){
						RiskReviewProcess process = qr.getItems().get(0);
						process.setCreatedBy(riskReviewProcess.getAuditPeople());
						updateRiskReviewProcess(process);
					}
				// 卡中心确认
				}else{
					RiskReviewProcess firstProcess = getFirstRiskReviewProcess(riskReviewProcess);
					firstProcess.setResultsOfAudit(RiskReviewProcessAuditResults.FINISH.toString());
					updateRiskReviewProcess(firstProcess);
					
					RiskConsiderations riskConsiderations = riskConsiderationsService.findRiskConsiderationsById(firstProcess.getRiskIssuesId());
					String area = riskConsiderations.getArea();
					if(StringUtils.isNotEmpty(area)){
						area = area.replace(",", "_");
						List<HashMap<String, Object>> customerList = findCustomerManagers(area);
						// 插入维护计划信息
						insertMaintenanceSchedule(customerList, riskConsiderations);
						// 插入通知信息
						insertNotificationMessage(customerList);
					}
				}
				// 更新风险事项审核流程
				updateRiskReviewProcess(riskReviewProcess);
				flag = true;
			}
		} catch(Exception e){
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 通过区域获取客户经理ID和客户ID
	 * @param area
	 * @return
	 */
	public List<HashMap<String, Object>> findCustomerManagers(String area) {
		HashMap<String,Object> params = new HashMap<String,Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("select b.id CUSTOMER_ID, b.user_id CUSTOMER_MANAGER_ID");
		sql.append("  from CUSTOMER_WORSHIP_INFORMATION a");
		sql.append(" inner join basic_customer_information b");
		sql.append("    on a.customer_id = b.id");
		sql.append("   and a.area = '" + area + "'");
		sql.append(" group by b.id, b.user_id");
		
		return commonDao.queryBySql(sql.toString(), params);
	}
	
	/**
	 * 插入维护计划信息
	 * @param filter
	 * @return
	 */
	public void insertMaintenanceSchedule(List<HashMap<String, Object>> customerList, RiskConsiderations riskConsiderations){
		for(HashMap<String, Object> hm : customerList){
			Maintenance maintenance = new Maintenance();
			maintenance.setCustomerId(hm.get("CUSTOMER_ID")+"");
			maintenance.setCustomerManagerId(hm.get("CUSTOMER_MANAGER_ID")+"");
			// 维护目标
			maintenance.setMaintenanceGoal(riskConsiderations.getMaintenanceTarget());
			maintenance.setMaintenanceDay(riskConsiderations.getMaintenanceDay()+"");
			// 计划开始时间
			//maintenance.setPlannedStartTime(Calendar.getInstance().getTime());
			//Date endDate = DateHelper.shiftDay(Calendar.getInstance().getTime(), riskConsiderations.getMaintenanceDay());
			// 计划结束时间
			//maintenanceSchedule.setPlannedEndTime(endDate);
			
			maintenance.setMaintenanceWay(CreateWayEnum.system.toString());
			// 创建方式
			maintenance.setCreateWay(CreateWayEnum.system.toString());
			// 备注创建原因
			maintenance.setRemarksCreateReason(riskConsiderations.getRiskIssuesDescribed());
			maintenanceService.insertMaintenance(maintenance);
		}
	}
	
	/**
	 * 插入通知信息
	 * @param filter
	 * @return
	 */
	public void insertNotificationMessage(List<HashMap<String, Object>> customerList){
		String customerId = "";
		for(HashMap<String, Object> hm : customerList){
			customerId = (String) hm.get("CUSTOMER_ID");
			notificationService.insertNotification(NotificationEnum.qita, customerId, "风险事项", "风险事项", null);
		}
	}
	
	/*
	 * 判断是否在风险名单中
	 */
	public boolean isInBlacklist(RiskCustomerFilter riskCustomerFilter){
		boolean flag = false;
		try{
			QueryResult<RiskCustomer> qr = commonDao.findObjectsByFilter(RiskCustomer.class, riskCustomerFilter);
			flag = qr.getItems().isEmpty() ? false : true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 拒绝风险事项审核流程
	 * @param filter
	 * @return
	 */
	public boolean rejectRiskReviewProcess(RiskReviewProcess riskReviewProcess){
		boolean flag = false;
		try{
			if(StringUtils.isNotEmpty(riskReviewProcess.getCreatedBy())){
				// 获取客户经理流程记录
				RiskReviewProcess firstProcess = getFirstRiskReviewProcess(riskReviewProcess);
				// 设置为流程结束
				firstProcess.setResultsOfAudit(RiskReviewProcessAuditResults.FINISH.toString());
				updateRiskReviewProcess(firstProcess);
				
				riskReviewProcess.setAuditTime(Calendar.getInstance().getTime());
				riskReviewProcess.setResultsOfAudit(RiskReviewProcessAuditResults.REJECT.toString());
				// 更新风险事项审核流程
				updateRiskReviewProcess(riskReviewProcess);
				flag = true;
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}

	/*
	 * 获取客户经理流程记录
	 */
	public RiskReviewProcess getFirstRiskReviewProcess(RiskReviewProcess riskReviewProcess){
		RiskReviewProcessFilter filter = new RiskReviewProcessFilter();
		filter.setPhase(RiskControlRole.manager.toString());
		filter.setReportedManagerId(riskReviewProcess.getReportedManagerId());
		filter.setRiskIssuesId(riskReviewProcess.getRiskIssuesId());
		filter.setCreatedTime(riskReviewProcess.getCreatedTime());
		QueryResult<RiskReviewProcess> qr = commonDao.findObjectsByFilter(RiskReviewProcess.class, filter);
		
		if(!qr.getItems().isEmpty()){
			return qr.getItems().get(0);
		}
		return null;
	}
}
