/**   
* 
* 
*/
package com.cardpay.workflow.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.intopieces.model.CustomerApplicationProcess;
import com.cardpay.pccredit.system.model.NodeControl;
import com.cardpay.workflow.models.WfStatusQueueRecord;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;

/**   
 * @Title: WfStatusQueueRecordDao.java 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author 谭文华   
 * @date 2014-11-25 下午1:33:13
 */
@Service
public class WfStatusQueueRecordDao {
	
	@Autowired
	private CommonDao commonDao;
	
	/**
	 * 获得当前审批状态
	 * @param wfProcessInfoID
	 * @return
	 * @throws SQLException
	 */
	public WfStatusQueueRecord getCurrentQueueRecord(String wfProcessInfoID) {
		String sql = "select * from WF_STATUS_QUEUE_RECORD where CURRENT_PROCESS = #{wfProcessInfoID}";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("wfProcessInfoID", wfProcessInfoID);
		List<WfStatusQueueRecord> list = commonDao.queryBySql(WfStatusQueueRecord.class, sql, params);
		if(list != null && list.size() > 0){
			return list.get(0);
		} else {
			return null;
		}
	}
	
	/**
	 * 获得某用户的审批历史
	 * @param exUserID
	 * @return
	 * @throws SQLException
	 */
	public List<WfStatusQueueRecord> getExamineHistory(String exUserID) {
		String sql = "select * from WF_STATUS_QUEUE_RECORD where EXAMINE_USER = #{exUserID}";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("exUserID", exUserID);
		return commonDao.queryBySql(WfStatusQueueRecord.class, sql, params);
	}
	
	
	public CustomerApplicationProcess findByAppId(String appId){
		String sql ="select * from customer_application_process where APPLICATION_ID = #{appId}";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("appId", appId);
		List<CustomerApplicationProcess> list = commonDao.queryBySql(CustomerApplicationProcess.class, sql, params);
		if(list != null && list.size() > 0){
			return list.get(0);
		} else {
			return null;
		}
	}
	
	public NodeControl getLastStatus(String id) {
		String sql = "select * from node_control where next_node = #{id}";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		List<NodeControl> list = commonDao.queryBySql(NodeControl.class, sql, params);
		if(list != null && list.size() > 0){
			return list.get(0);
		} else {
			return null;
		}
	}
	
	public WfStatusQueueRecord getLastStatus1(String beforeStatus) {
		String sql = "select * from WF_STATUS_QUEUE_RECORD where CURRENT_STATUS = #{beforeStatus}";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("beforeStatus", beforeStatus);
		List<WfStatusQueueRecord> list = commonDao.queryBySql(WfStatusQueueRecord.class, sql, params);
		if(list != null && list.size() > 0){
			return list.get(0);
		} else {
			return null;
		}
	}
}
