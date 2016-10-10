package com.cardpay.pccredit.system.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.system.dao.comdao.NodeAuditComDao;
import com.cardpay.pccredit.system.filter.NodeAuditFilter;
import com.cardpay.pccredit.system.model.NodeAudit;
import com.cardpay.pccredit.system.model.NodeAuditUser;
import com.cardpay.pccredit.system.model.NodeControl;
import com.cardpay.pccredit.system.web.NodeAuditForm;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.modules.privilege.model.User;

/**
 * 
 * 描述 ：流程节点配置信息
 * @author 张石树
 *
 * 2014-11-27 下午3:34:09
 */
@Service
public class NodeAuditService {

	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private NodeAuditComDao nodeAuditComDao;

	/**
	 * 流程节点分页查询
	 * @param filter
	 * @return
	 */
	public QueryResult<NodeAuditForm> findProductsNodeAuditByFilter(NodeAuditFilter filter) {
		QueryResult<NodeAudit> result = commonDao.findObjectsByFilter(NodeAudit.class, filter);
		
		List<NodeAuditForm> items = new ArrayList<NodeAuditForm>();
		for(NodeAudit audit : result.getItems()){
			NodeAuditForm nodeAuditForm = new NodeAuditForm();
			try {
				PropertyUtils.copyProperties(nodeAuditForm, audit);
			} catch (Exception e) {e.printStackTrace();}
			List<User> nodeAuditUsers = nodeAuditComDao.findNodeAuditUserByNodeId(audit.getId());
			if(nodeAuditUsers != null && nodeAuditUsers.size() > 0){
				StringBuffer userIds = new StringBuffer();
				StringBuffer userNames = new StringBuffer();
				for(User user : nodeAuditUsers){
					userIds.append(user.getId()).append(",");
					userNames.append(user.getDisplayName()).append(",");
				}
				nodeAuditForm.setAuditUserIds(userIds.deleteCharAt(userIds.length() - 1).toString());
				nodeAuditForm.setAuditUserNames(userNames.deleteCharAt(userNames.length() - 1).toString());
			}
			items.add(nodeAuditForm);
		}
		
		return new QueryResult<NodeAuditForm>(result.getTotalCount(), items);
	}

	/**
	 * 根据ID获取
	 * @param id
	 * @return
	 */
	public NodeAuditForm findNodeAuditById(String id) {
		NodeAudit audit = commonDao.findObjectById(NodeAudit.class, id);
		NodeAuditForm nodeAuditForm = new NodeAuditForm();
		try {
			PropertyUtils.copyProperties(nodeAuditForm, audit);
		} catch (Exception e) {e.printStackTrace();}
		List<User> nodeAuditUsers = nodeAuditComDao.findNodeAuditUserByNodeId(audit.getId());
		if(nodeAuditUsers != null && nodeAuditUsers.size() > 0){
			StringBuffer userIds = new StringBuffer();
			StringBuffer userNames = new StringBuffer();
			for(User user : nodeAuditUsers){
				userIds.append(user.getId()).append(",");
				userNames.append(user.getDisplayName()).append(",");
			}
			nodeAuditForm.setAuditUserIds(userIds.deleteCharAt(userIds.length() - 1).toString());
			nodeAuditForm.setAuditUserNames(userNames.deleteCharAt(userNames.length() - 1).toString());
		}
		return nodeAuditForm;
	}

	/**
	 * 插入节点信息
	 * @param nodeAudit
	 */
	public void insertNodeAudit(NodeAuditForm nodeAuditForm) {
		NodeAudit nodeAudit = nodeAuditForm.createModel(NodeAudit.class);
		nodeAudit.setIsDeleted(false);
		commonDao.insertObject(nodeAudit);
		if(StringUtils.isNotEmpty(nodeAuditForm.getAuditUserIds())){
			for(String userId : nodeAuditForm.getAuditUserIds().split(",")){
				NodeAuditUser auditUser = new NodeAuditUser();
				auditUser.setNodeId(nodeAudit.getId());
				auditUser.setUserId(userId);
				commonDao.insertObject(auditUser);
			}
		}
	}

	/**
	 * 修改节点信息
	 * @param nodeAudit
	 */
	public void updateNodeAudit(NodeAuditForm nodeAuditForm) {
		List<NodeAuditUser> auditUsers = nodeAuditComDao.findAuditUserByNodeId(nodeAuditForm.getId());
		if(auditUsers.size() > 0){
			List<String> ids = new ArrayList<String>();
			for(NodeAuditUser auditUser : auditUsers){
				ids.add(auditUser.getId());
			}
			commonDao.batchDeleteObjects(NodeAuditUser.class, ids);
		}
		if(StringUtils.isNotEmpty(nodeAuditForm.getAuditUserIds())){
			for(String userId : nodeAuditForm.getAuditUserIds().split(",")){
				NodeAuditUser auditUser = new NodeAuditUser();
				auditUser.setNodeId(nodeAuditForm.getId());
				auditUser.setUserId(userId);
				commonDao.insertObject(auditUser);
			}
		} 
		NodeAudit nodeAudit = nodeAuditForm.createModel(NodeAudit.class);
		commonDao.updateObject(nodeAudit);
	}

	/**
	 * 删除节点
	 * @param id
	 */
	public void deleteNodeAuditById(String id) {
//		List<NodeAuditUser> auditUsers = nodeAuditComDao.findAuditUserByNodeId(id);
//		if(auditUsers.size() > 0){
//			List<String> ids = new ArrayList<String>();
//			for(NodeAuditUser auditUser : auditUsers){
//				ids.add(auditUser.getId());
//			}
//			commonDao.batchDeleteObjects(NodeAuditUser.class, ids);
//		}
//		commonDao.deleteObject(NodeAudit.class, id);
		
		NodeAudit nodeAudit = commonDao.findObjectById(NodeAudit.class, id);
		nodeAudit.setIsDeleted(true);
		commonDao.updateObject(nodeAudit);
	}
	
	/**
	 * 根据节点类型 和产品id获取流程节点信息
	 * @param nodeType
	 * @param productId
	 * @return
	 */
	public List<NodeAudit> findByNodeTypeAndProductId(String nodeType, String productId){
		return nodeAuditComDao.findByNodeTypeAndProductId(nodeType, productId);
	}
	
	/**
	 * 根据节点类型 和产品id获取流程节点关系信息
	 * @param nodeType
	 * @param productId
	 * @return
	 */
	public List<NodeControl> findNodeControlByNodeTypeAndProductId(String nodeType, String productId){
		return nodeAuditComDao.findNodeControlByNodeTypeAndProductId(nodeType, productId);
	}
	
	/**
	 * 根据节点类型 和 用户 返回这个用户需要审核的节点
	 * @param nodeType
	 * @param userId
	 * @return
	 */
	public List<NodeAudit> findByNodeAuditByUserId(String nodeType, String userId, String productId){
		return nodeAuditComDao.findByNodeAuditByUserId(nodeType, userId, productId);
	}

	/**
	 * 保存节点关系
	 * @param productId
	 * @param nodeType
	 * @param nodeControls
	 */
	public void insertNodeControl(String productId, String nodeType,
			List<NodeControl> nodeControls) {
		List<NodeControl>  controls = this.findNodeControlByNodeTypeAndProductId(nodeType, productId);
		if(controls != null && controls.size() > 0){
			List<String> ids = new ArrayList<String>();
			for(NodeControl control : controls){
				ids.add(control.getId());
			}
			commonDao.batchDeleteObjects(NodeControl.class, ids);
		}
		for(NodeControl control : nodeControls){
			commonDao.insertObject(control);
		}
		 
	}

	/**
	 * 根据当前节点查找下个节点关系
	 * @param currentNodeId
	 * @return
	 */
	public List<NodeControl> findNodeControlByCurrentNodeId(String currentNodeId) {
		return nodeAuditComDao.findNodeControlByCurrentNodeId(currentNodeId);
	}
}
