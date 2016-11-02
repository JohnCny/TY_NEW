package com.cardpay.pccredit.system.dao.comdao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.system.model.NodeAudit;
import com.cardpay.pccredit.system.model.NodeAuditUser;
import com.cardpay.pccredit.system.model.NodeControl;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.modules.privilege.model.User;

/**
 * 
 * 通过产品Id查找筛选规则
 *
 * @author Evans zhang
 *
 * 2014-10-24 下午2:54:08
 */

@Service
public class NodeAuditComDao {
	
	@Autowired
	private CommonDao commonDao;
	/**
	 * 根据节点获取节点配置的用户信息
	 * @param nodeId
	 * @return
	 */
	public List<User> findNodeAuditUserByNodeId(String nodeId){
		String sql = "select * from sys_user where id in(" +
				" select user_id from node_audit_user where node_id = #{nodeId} and is_deleted=0)";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nodeId", nodeId);
		return commonDao.queryBySql(User.class, sql, params);
	}
	
	/**
	 * 根据节点获取节点用户配置
	 * @param nodeId
	 * @return
	 */
	public List<NodeAuditUser> findAuditUserByNodeId(String nodeId){
		String sql = "select * from node_audit_user where node_id = #{nodeId}";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nodeId", nodeId);
		return commonDao.queryBySql(NodeAuditUser.class, sql, params);
	}

	/**
	 * 根据节点类型和产品id获取节点信息
	 * @param nodeType
	 * @param productId
	 * @return
	 */
	public List<NodeAudit> findByNodeTypeAndProductId(String nodeType,
			String productId) {
		String sql = "select * from node_audit where node_type = #{nodeType} and product_id=#{productId} and is_deleted=0 order by seq_no asc";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nodeType", nodeType);
		params.put("productId", productId);
		return commonDao.queryBySql(NodeAudit.class, sql, params);
	}

	/**
	 * 根据节点类型和产品id，用户id获取有权限的节点
	 * @param nodeType
	 * @param userId
	 * @param productId
	 * @return
	 */
	public List<NodeAudit> findByNodeAuditByUserId(String nodeType, String userId, String productId) {
		String sql = "select * from node_audit where node_type = #{nodeType} and product_id=#{productId} and is_deleted=0 " +
				" and id in(select node_id from node_audit_user where user_id=#{userId}) order by seq_no asc";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nodeType", nodeType);
		params.put("productId", productId);
		params.put("userId", userId);
		return commonDao.queryBySql(NodeAudit.class, sql, params);
	}

	/**
	 * 根据节点类型和产品id获取节点关系信息
	 * @param nodeType
	 * @param productId
	 * @return
	 */
	public List<NodeControl> findNodeControlByNodeTypeAndProductId(
			String nodeType, String productId) {
		String sql = "select t.* from node_control t, node_audit n where t.current_node = n.id and n.is_deleted=0 and n.node_type = #{nodeType} and n.product_id=#{productId} order by n.seq_no asc";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nodeType", nodeType);
		params.put("productId", productId);
		return commonDao.queryBySql(NodeControl.class, sql, params);
	}

	/**
	 * 根据当前节点获取 当前节点的节点关系
	 * @param currentNodeId
	 * @return
	 */
	public List<NodeControl> findNodeControlByCurrentNodeId(String currentNodeId) {
		String sql = "select t.* from node_control t, node_audit n where t.current_node = n.id and n.is_deleted=0 and t.current_node=#{currentNodeId} order by n.seq_no asc";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("currentNodeId", currentNodeId);
		return commonDao.queryBySql(NodeControl.class, sql, params);
	}
	
}
