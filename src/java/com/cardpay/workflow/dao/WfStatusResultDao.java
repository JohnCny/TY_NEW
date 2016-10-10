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

import com.cardpay.workflow.models.WfStatusResult;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;

/**   
 * @Title: WfStatusResultDao.java 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author 谭文华   
 * @date 2014-11-25 下午1:33:37
 */
@Service
public class WfStatusResultDao {

	@Autowired
	private CommonDao commonDao;
	
	/**
	 * 根据当前审批结果 查找审批结果表 获取下一个审批状态
	 * @param currentStatus
	 * @param exUserID
	 * @param exResult
	 * @return
	 * @throws SQLException
	 */
	public WfStatusResult getNextStatus(String currentStatus,String exUserID,String exResult) {
		String sql = "select * from WF_STATUS_RESULT where CURRENT_STATUS = #{currentStatus} and EXAMINE_RESULT = #{exResult}";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("currentStatus", currentStatus);
		params.put("exResult", exResult);
		List<WfStatusResult> list = commonDao.queryBySql(WfStatusResult.class, sql, params);
		if(list != null && list.size() > 0){
			return list.get(0);
		} else {
			return null;
		}
	}
}
