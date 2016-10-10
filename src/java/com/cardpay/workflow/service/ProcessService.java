package com.cardpay.workflow.service;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.intopieces.constant.Constant;
import com.cardpay.pccredit.intopieces.dao.CustomerApplicationIntopieceWaitDao;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationInfo;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationProcess;
import com.cardpay.pccredit.product.model.ProductAttribute;
import com.cardpay.pccredit.system.model.NodeControl;
import com.cardpay.workflow.constant.ApproveOperationTypeEnum;
import com.cardpay.workflow.dao.WfStatusInfoDao;
import com.cardpay.workflow.dao.WfStatusQueueRecordDao;
import com.cardpay.workflow.dao.WfStatusResultDao;
import com.cardpay.workflow.models.WfProcessRecord;
import com.cardpay.workflow.models.WfStatusInfo;
import com.cardpay.workflow.models.WfStatusQueueRecord;
import com.cardpay.workflow.models.WfStatusResult;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;

/**   
 * @Title: ProcessService.java 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author 谭文华   
 * @date 2014-11-25 下午3:24:59
*/
@Service
public class ProcessService {
	//单例获得数据库操作类
	@Autowired
	private CommonDao commonDao;
	
	//实例化WfStatusResultDao
	@Autowired
	private WfStatusResultDao wfStatusResultDao;
	
	//实例化WfStatusResultDao
	@Autowired
	private WfStatusInfoDao wfStatusInfoDao;
	
	//实例化WfStatusResultDao
	@Autowired
	private WfStatusQueueRecordDao wfStatusQueueRecordDao;
	
	@Autowired
	private CustomerApplicationIntopieceWaitDao customerApplicationIntopieceWaitDao;
	
	/**
	 * 流程开始,根据状态流转表取得该下一状态信息，同时流程记录表中新增一条记录
	 * @param wfProcessInfoID
	 * @return WfProcessRecord的ID
	 * @throws SQLException
	 */
	public String start(String wfProcessInfoID){
		
		//新建流程记录表的第一条记录
		WfProcessRecord wfProcessRecord = new WfProcessRecord();
		wfProcessRecord.setCreateDate(new Timestamp(System.currentTimeMillis()));
		wfProcessRecord.setIsClosed("0");
		wfProcessRecord.setWfProcessInfo(wfProcessInfoID);
		commonDao.insertObject(wfProcessRecord);
		
		//根据流程记录ID查找WfStatusInfo表 找到该流程的起始状态
		WfStatusInfo wfStatusInfo = wfStatusInfoDao.getStartStatus(wfProcessInfoID);
		
		//根据上一步查找到的起始状态 新建流转表的第一条记录
		WfStatusQueueRecord wfStatusQueueRecord = new WfStatusQueueRecord();
		wfStatusQueueRecord.setCurrentStatus(wfStatusInfo.getId());
		//将流转表关联到流程表
		wfStatusQueueRecord.setCurrentProcess(wfProcessRecord.getId());
		commonDao.insertObject(wfStatusQueueRecord);
		
		//将流程表关联到流转表
		wfProcessRecord.setWfStatusQueueRecord(wfStatusQueueRecord.getId());
		commonDao.updateObject(wfProcessRecord);
		
		//返回流程表ID
		return wfProcessRecord.getId();
	}
	
	/**
	 * 流程关闭。暂不考虑子流程等。
	 * @param wfProcessRecordID
	 * @return 影响记录条数
	 * @throws SQLException
	 */
	public int close(String wfProcessRecordID) {
		WfProcessRecord wfProcessRecord = commonDao.findObjectById(WfProcessRecord.class, wfProcessRecordID);
		wfProcessRecord.setIsClosed("1");
		return commonDao.updateObject(wfProcessRecord);
	}

	/**
	 * 审批,返回下一状态信息
	 * @param wfProcessRecordID
	 * @param exUserID
	 * @param exResult  必须是ApproveOperationTypeEnum中的类型
	 * @param exAmount
	 * @return 
	 * 	下一流程状态ID; 
	 * 	ApproveOperationTypeEnum.NORMALEND时为流程正常结束;
	 * 	ApproveOperationTypeEnum.RETURNAPPROVE时为退回;
	 * 	ApproveOperationTypeEnum.REJECTAPPROVE时为拒绝;
	 * 
	 * @throws SQLException
	 */
	public String examine(String appId,String wfProcessRecordID,String exUserID,String exResult,String exAmount,String productId,String auditType){
		//查找当前所处流转状态
		WfProcessRecord wfProcessRecord = commonDao.findObjectById(WfProcessRecord.class, wfProcessRecordID);
		WfStatusQueueRecord wfStatusQueueRecord = commonDao.findObjectById(WfStatusQueueRecord.class,wfProcessRecord.getWfStatusQueueRecord());
		
		//进入下一流转之前 先更新当前流转
		wfStatusQueueRecord.setExamineUser(exUserID);
		wfStatusQueueRecord.setExamineResult(exResult);
		wfStatusQueueRecord.setExamineAmount(exAmount);
		wfStatusQueueRecord.setStartExamineTime(new Date());
		commonDao.updateObject(wfStatusQueueRecord);
		
		//退回
		if(exResult.equalsIgnoreCase(ApproveOperationTypeEnum.RETURNAPPROVE.toString())){
			wfProcessRecord.setIsClosed("1");
			commonDao.updateObject(wfProcessRecord);
			if(auditType.equals("1")){
				//退回到客户经理
				returnToFirst(appId);
			}else{
				//审批退回进件到录入
				refuse(appId);
			}
			return ApproveOperationTypeEnum.RETURNAPPROVE.toString();
		} //拒绝 
		else if(exResult.equalsIgnoreCase(ApproveOperationTypeEnum.REJECTAPPROVE.toString())){
			wfProcessRecord.setIsClosed("1");
			commonDao.updateObject(wfProcessRecord);
			return ApproveOperationTypeEnum.REJECTAPPROVE.toString();
		} //通过 
		else {
			/*//根据当前审批结果 查找审批结果表 获取下一个审批状态
			WfStatusResult wfStatusResult = wfStatusResultDao.getNextStatus(wfStatusQueueRecord.getCurrentStatus(), exUserID, exResult);
			
			//判断下一个审批批状态是否为结束状态
			WfStatusInfo wfStatusInfo = commonDao.findObjectById(WfStatusInfo.class, wfStatusResult.getNextStatus());
			if(wfStatusInfo.getIsClosed().equals("1")){//标示下一状态为结束
				//将流程记录表标识为结束
				wfProcessRecord.setIsClosed("1");
				commonDao.updateObject(wfProcessRecord);
				return ApproveOperationTypeEnum.NORMALEND.toString();
			}
			
			//流转表新增一条记录
			String beforeStatus = wfStatusQueueRecord.getCurrentStatus();
			wfStatusQueueRecord = new WfStatusQueueRecord();
			wfStatusQueueRecord.setBeforeStatus(beforeStatus);
			wfStatusQueueRecord.setCurrentProcess(wfProcessRecordID);
			wfStatusQueueRecord.setCurrentStatus(wfStatusResult.getNextStatus());
			commonDao.insertObject(wfStatusQueueRecord);
			
			//流程表关联到新的流转
			wfProcessRecord.setWfStatusQueueRecord(wfStatusQueueRecord.getId());
			commonDao.updateObject(wfProcessRecord);
			
			String wfStatusInfoId = wfStatusResult.getNextStatus();
			WfStatusInfo nextStatusInfo = commonDao.findObjectById(WfStatusInfo.class, wfStatusInfoId);
			//返回节点的id
			return nextStatusInfo.getStatusCode();*/
			
			//根据金额和产品类型判断段是否继续流程 isContinue
			if(!isContinue(productId,exAmount,auditType)){
				wfProcessRecord.setIsClosed("1");
				commonDao.updateObject(wfProcessRecord);
				return ApproveOperationTypeEnum.NORMALEND.toString();
			}else{
				//根据当前审批结果 查找审批结果表 获取下一个审批状态
				WfStatusResult wfStatusResult = wfStatusResultDao.getNextStatus(wfStatusQueueRecord.getCurrentStatus(), exUserID, exResult);
				
				//判断下一个审批批状态是否为结束状态
				WfStatusInfo wfStatusInfo = commonDao.findObjectById(WfStatusInfo.class, wfStatusResult.getNextStatus());
				if(wfStatusInfo.getIsClosed().equals("1")){//标示下一状态为结束
					//将流程记录表标识为结束
					wfProcessRecord.setIsClosed("1");
					commonDao.updateObject(wfProcessRecord);
					return ApproveOperationTypeEnum.NORMALEND.toString();
				}
				
				//流转表新增一条记录
				String beforeStatus = wfStatusQueueRecord.getCurrentStatus();
				wfStatusQueueRecord = new WfStatusQueueRecord();
				wfStatusQueueRecord.setBeforeStatus(beforeStatus);
				wfStatusQueueRecord.setCurrentProcess(wfProcessRecordID);
				wfStatusQueueRecord.setCurrentStatus(wfStatusResult.getNextStatus());
				commonDao.insertObject(wfStatusQueueRecord);
				
				//流程表关联到新的流转
				wfProcessRecord.setWfStatusQueueRecord(wfStatusQueueRecord.getId());
				commonDao.updateObject(wfProcessRecord);
				
				String wfStatusInfoId = wfStatusResult.getNextStatus();
				WfStatusInfo nextStatusInfo = commonDao.findObjectById(WfStatusInfo.class, wfStatusInfoId);
				//返回节点的id
				return nextStatusInfo.getStatusCode();
			}
		}
	}
	
	public boolean isContinue(String productId,String exAmount,String auditType){
		//查询产品担保方式
		ProductAttribute product = commonDao.findObjectById(ProductAttribute.class, productId);
		if("LNM00000000003".equals(product.getAssureMeans())){//信用
			if(Integer.parseInt(auditType)==3){
				if(Float.parseFloat(exAmount)<=100000){
					return false;
				}else{
					return true;
				}
			}
			if(Integer.parseInt(auditType)==4){
				if(Float.parseFloat(exAmount)<=200000){//&&Float.parseFloat(exAmount)>100000
					return false;
				}else{
					return true;
				}
			}
			
		}else if("LNM00000000004".equals(product.getAssureMeans())){//担保
			if(Integer.parseInt(auditType)==3){
				if(Float.parseFloat(exAmount)<=200000){
					return false;
				}else{
					return true;
				}
			}
			if(Integer.parseInt(auditType)==4){
				if(Float.parseFloat(exAmount)<=800000){//&&Float.parseFloat(exAmount)>200000
					return false;
				}else{
					return true;
				}
			}
		}else if("LNM00000000001".equals(product.getAssureMeans())){//抵押
			if(Integer.parseInt(auditType)==3){
				if(Float.parseFloat(exAmount)<=500000){
					return false;
				}else{
					return true;
				}
			}
			if(Integer.parseInt(auditType)==4){
				if(Float.parseFloat(exAmount)<=2000000){//&&Float.parseFloat(exAmount)>500000
					return false;
				}else{
					return true;
				}
			}
		}
		return true;
	}
	
	/**
	 * 退回到客户经理
	 */
	public void returnToFirst(String applicationId){
		//获取进件信息
		CustomerApplicationInfo applicationInfo= commonDao.findObjectById(CustomerApplicationInfo.class, applicationId);
		//获取进件流程信息
		CustomerApplicationProcess process =  customerApplicationIntopieceWaitDao.findByAppId(applicationId);
	
		String nodeSql = "select n.id from ("
				+" select t.*,connect_by_isleaf isLeaf from node_control t"
				+" start with current_node=#{currentNode}"
				+" connect by next_node= prior current_node"
				+") m,node_audit n where m.current_node=n.id"
				+ " and m.isLeaf=1";
		
		Map<String, Object> nodeparams = new HashMap<String, Object>();
		nodeparams.put("currentNode", process.getNextNodeId());
		List<HashMap<String, Object>> nodeList = commonDao.queryBySql(nodeSql, nodeparams);
		//更新业务流程表
		process.setNextNodeId((String)nodeList.get(0).get("ID"));
		process.setCreatedTime(new Date());
		customerApplicationIntopieceWaitDao.updateCustomerApplicationProcessBySerialNumber(process);
		//更新状态为--退件到申请状态
		applicationInfo.setStatus("returnedToFirst");
		commonDao.updateObject(applicationInfo);
		//查找当前所处流转状态
  		WfProcessRecord wfProcessRecord = commonDao.findObjectById(WfProcessRecord.class, process.getSerialNumber());
		WfStatusQueueRecord wfStatusQueueRecord = commonDao.findObjectById(WfStatusQueueRecord.class,wfProcessRecord.getWfStatusQueueRecord());
		String currentProcess = wfStatusQueueRecord.getCurrentProcess();
		String sql = "select t.* from wf_status_queue_record t " 
					+ "where t.current_process =  #{currentProcess}"
					+ "and t.before_status is null";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("currentProcess", currentProcess);
		List<WfStatusQueueRecord> list = commonDao.queryBySql(WfStatusQueueRecord.class, sql, params);

		wfProcessRecord.setWfStatusQueueRecord(list.get(0).getId());
		commonDao.updateObject(wfProcessRecord);
	}
	
	/**
	 * 获得某用户的审批历史
	 * @param exUserID
	 * @return
	 * @throws SQLException
	 */
	public List<WfStatusQueueRecord> getExamineHistory(String exUserID) {
		return wfStatusQueueRecordDao.getExamineHistory(exUserID);
	}
	
	
	public void refuse(String applicationId) {
		try {
			//更新申请表
			CustomerApplicationInfo customerApplicationInfo = new CustomerApplicationInfo();
			customerApplicationInfo.setStatus(Constant.APPROVE_INTOPICES);
			customerApplicationInfo.setId(applicationId);
			customerApplicationInfo.setModifiedTime(new Date());
			commonDao.updateObject(customerApplicationInfo);
			
			//通过申请表ID获取流程表
			CustomerApplicationProcess process =  wfStatusQueueRecordDao.findByAppId(applicationId);
			//通过流程表的当前节点获取上一节点--复核 modify 接收
			NodeControl nodeControl = wfStatusQueueRecordDao.getLastStatus(process.getNextNodeId());
			//通过流程表的当前节点获取上一节点--录入
			//NodeControl nodeControl1 = customerApplicationProcessService.getLastStatus(nodeControl.getCurrentNode());
			//更新业务流程表
			process.setNextNodeId(nodeControl.getCurrentNode());
			//process.setFuheUser(null);
			process.setCreatedTime(new Date());
			customerApplicationIntopieceWaitDao.updateCustomerApplicationProcessBySerialNumber(process);
			
			//更新流程备份表
			
			//查找当前所处流转状态
			WfProcessRecord wfProcessRecord = commonDao.findObjectById(WfProcessRecord.class, process.getSerialNumber());
			WfStatusQueueRecord wfStatusQueueRecord = commonDao.findObjectById(WfStatusQueueRecord.class,wfProcessRecord.getWfStatusQueueRecord());
			//查找上一节点为复核
			String beforeStatus = wfStatusQueueRecord.getBeforeStatus();
			//通过复核节点获取复核流程
			WfStatusQueueRecord befoRecord = wfStatusQueueRecordDao.getLastStatus1(beforeStatus);
			//获取录入标记
			//String lastLastNode = befoRecord.getBeforeStatus();
			//通过录入节点获取录入流程
			//WfStatusQueueRecord lastlastRecord = wfStatusResultDao.getLastStatus(lastLastNode);
			//设置节点为录入
			wfProcessRecord.setWfStatusQueueRecord(befoRecord.getId());
			commonDao.updateObject(wfProcessRecord);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
