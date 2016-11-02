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

import com.cardpay.workflow.models.WfStatusInfo;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;


/**   
 * @Title: WfStatusInfoDao.java 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author 谭文华   
 * @date 2014-11-25 下午1:32:32
 */
@Service
public class WfStatusInfoDao {
	
	@Autowired
	private CommonDao commonDao;
	
	/**
	 * 根据流程信息ID查找WfStatusInfo表 找到该流程的起始状态
	 * @param wfProcessInfoID
	 * @return
	 * @throws SQLException
	 */
	public WfStatusInfo getStartStatus(String wfProcessInfoID) {
		String sql = "select * from WF_STATUS_INFO where RELATIONED_PROCESS = #{wfProcessInfoID} and is_start = '1'";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("wfProcessInfoID", wfProcessInfoID);
		List<WfStatusInfo> list = commonDao.queryBySql(WfStatusInfo.class, sql, params);
		if(list != null && list.size() > 0){
			return list.get(0);
		} else {
			return null;
		}
	}
}
