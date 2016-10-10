/**
 * 
 */
package com.cardpay.pccredit.manager.service;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.axis.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.manager.dao.ManagerBelongMapDao;
import com.cardpay.pccredit.manager.dao.comdao.ManagerBelongMapComdao;
import com.cardpay.pccredit.manager.filter.ManagerBelongMapFilter;
import com.cardpay.pccredit.manager.model.ManagerBelongMap;
import com.cardpay.pccredit.manager.web.AccountManagerParameterForm;
import com.cardpay.pccredit.manager.web.ManagerBelongMapForm;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.modules.privilege.model.TreeNode;

/**
 * 描述 ：客户经理从属关系service
 * @author 张石树
 *
 * 2014-11-10 下午3:05:58
 */
@Service
public class ManagerBelongMapService {
	
	public static String IMAGEURL="manager.png";
	
	public static String HEADIMAGEURL="head_organization.gif";
	
	@Autowired
	private ManagerBelongMapDao managerBelongMapDao;
	
	@Autowired
	private ManagerBelongMapComdao managerBelongMapComdao;
	
	@Autowired
	private CommonDao commonDao;

	/**
	 * 根节点开始获取tree json
	 * @return
	 */
	public String getManagerBelongTree() {
		ManagerBelongMap root  = managerBelongMapDao.getManagerBelongRoot();
		boolean focus = true;
		TreeNode rootNode = new TreeNode(root.getChildId(), root.getParentId(), "Root", "", "", "",
				HEADIMAGEURL, focus, focus, true, focus, focus);
		
		this.managerBelongTree(rootNode, root.getChildId());
		
		JSONObject jsonStr = JSONObject.fromObject(rootNode);
		if (jsonStr == null) {
			return "{}";
		}
		return jsonStr.toString();
	}

	/**
	 * 递归 tree
	 * @param treeNode
	 * @param childId
	 */
	private void managerBelongTree(TreeNode treeNode, String childId) {
		List<ManagerBelongMapForm> childBelongMapList = managerBelongMapDao.findByParentId(childId);
		boolean focus = false;
		if(childBelongMapList != null && childBelongMapList.size() > 0){
			for(ManagerBelongMapForm belongMapForm : childBelongMapList){
				TreeNode childNode = new TreeNode(belongMapForm.getChildId(), belongMapForm.getParentId(), belongMapForm.getUserName(), "", "", "",
						IMAGEURL, focus, focus, true, focus, focus);
				this.managerBelongTree(childNode ,belongMapForm.getChildId());
				treeNode.getChildren().add(childNode);
			}
		} else {
			treeNode.setIsFolder(false);
		}
	}

	/**
	 * 根据某个节点局部加载 tree
	 * @param parentId
	 * @return
	 */
	public String getManagerBelongTreeById(String parentId) {
		TreeNode treeNode = null;
		if(StringUtils.isEmpty(parentId)){
			ManagerBelongMap root  = managerBelongMapDao.getManagerBelongRoot();
			boolean focus = true;
			treeNode = new TreeNode(root.getChildId(), root.getParentId(), "Root", "", "", "",
					HEADIMAGEURL, focus, focus, true, focus, focus);
		} else {
			ManagerBelongMapForm belongMapForm  = managerBelongMapDao.findByChildId(parentId);
			boolean focus = true;
			treeNode = new TreeNode(belongMapForm.getChildId(), belongMapForm.getParentId(), belongMapForm.getUserName(), "", "", "",
					HEADIMAGEURL, focus, focus, true, focus, focus);
		}
		
		this.managerBelongTree(treeNode, treeNode.getKey());
		
		JSONObject jsonStr = JSONObject.fromObject(treeNode);
		if (jsonStr == null) {
			return "{}";
		}
		return jsonStr.toString();
	}

	/**
	 * 分页查询
	 * @param filter
	 * @return
	 */
	public QueryResult<ManagerBelongMapForm> findManagerBelongMapByFilter(ManagerBelongMapFilter filter) {
		List<ManagerBelongMapForm> riskCustomers = managerBelongMapDao.findManagerBelongMapByFilter(filter);
		int size = managerBelongMapDao.findManagerBelongMapCountByFilter(filter);
		QueryResult<ManagerBelongMapForm> qs = new QueryResult<ManagerBelongMapForm>(size, riskCustomers);
		return qs;
	}

	/**
	 * 插入操作
	 * @param managerBelongMap
	 */
	public void insertBelongMap(ManagerBelongMap managerBelongMap) {
		commonDao.insertObject(managerBelongMap);
	}

	/**
	 * 根据Id删除
	 * @param id
	 */
	public void deleteById(String id) {
		commonDao.deleteObject(ManagerBelongMap.class, id);
	}

	/**
	 * 根据Id查找
	 * @param id
	 * @return
	 */
	public ManagerBelongMap findById(String id) {
		return commonDao.findObjectById(ManagerBelongMap.class, id);
	}

	/**
	 * 下属客户经理转移
	 * @param childBelongMapList 
	 * @param targetId
	 */
	public void copyXiaShuToTarget(List<ManagerBelongMapForm> childBelongMapList, String targetId) {
		ManagerBelongMap target = this.findById(targetId);
		for(ManagerBelongMapForm belongMapForm : childBelongMapList){
			ManagerBelongMap belongMap = new ManagerBelongMap();
			belongMap.setId(belongMapForm.getId());
			belongMap.setParentId(target.getChildId());
			commonDao.updateObject(belongMap);
		}
	}
	
	/**
	 * 查询客户经理及客户经理的下属客户经理
	 * @param managerId
	 * @return
	 */
	public List<AccountManagerParameterForm> findSubManagerBelongMapByManagerId(String managerId){
		
		ManagerBelongMap managerBelongMap = managerBelongMapComdao.findManagerBelongMapByUserId(managerId);
		if(managerBelongMap != null){
			List<ManagerBelongMapForm> childBelongMapList = managerBelongMapDao.findByParentId(managerBelongMap.getChildId());
			StringBuffer belongChildIds = new StringBuffer();
			belongChildIds.append("('").append(managerBelongMap.getChildId()).append("'");
			for(ManagerBelongMapForm belongMapForm : childBelongMapList){
				belongChildIds.append(",").append("'").append(belongMapForm.getChildId()).append("'");
			}
			belongChildIds.append(")");
			return managerBelongMapDao.findAccountManagerParameterByChildIds(belongChildIds.toString());
		} else{
			return new ArrayList<AccountManagerParameterForm>();
		}
		
	}
	
	/**
	 * 查询客户经理的下属客户经理
	 * @param managerId
	 * @return
	 */
	public List<AccountManagerParameterForm> findSubListManagerByManagerId(String managerId){
		
		ManagerBelongMap managerBelongMap = managerBelongMapComdao.findManagerBelongMapByUserId(managerId);
		if(managerBelongMap != null){
			List<ManagerBelongMapForm> childBelongMapList = managerBelongMapDao.findByParentId(managerBelongMap.getChildId());
			if(childBelongMapList != null && childBelongMapList.size() > 0){
				StringBuffer belongChildIds = new StringBuffer();
				belongChildIds.append("(");
				for(ManagerBelongMapForm belongMapForm : childBelongMapList){
					belongChildIds.append("'").append(belongMapForm.getChildId()).append("'").append(",");
				}
				belongChildIds = belongChildIds.deleteCharAt(belongChildIds.length() - 1);
				belongChildIds.append(")");
				return managerBelongMapDao.findAccountManagerParameterByChildIds(belongChildIds.toString());
			} else {
				return new ArrayList<AccountManagerParameterForm>();
			}
		} else{
			return new ArrayList<AccountManagerParameterForm>();
		}
		
	}
	
	
	//------------------------------------------------------济南绩效相关start-------------------------------------------//
	public List<AccountManagerParameterForm>  findSubListManagerByManagerId(IUser user){
		//客户经理list
		 List<AccountManagerParameterForm>  forms = new ArrayList<AccountManagerParameterForm>();
		 
		 List<ManagerBelongMapForm> childBelongMapList = managerBelongMapDao.findChildId(user.getId());
		 if(childBelongMapList != null && childBelongMapList.size() > 0){
				StringBuffer belongChildIds = new StringBuffer();
				belongChildIds.append("(");
				for(ManagerBelongMapForm belongMapForm : childBelongMapList){
					belongChildIds.append("'").append(belongMapForm.getChildId()).append("'").append(",");
				}
				belongChildIds = belongChildIds.deleteCharAt(belongChildIds.length() - 1);
				belongChildIds.append(")");
				return managerBelongMapDao.findAccountManagerParameterByChildIds(belongChildIds.toString());
		 }
		return forms;
	}
	
	
	public List<AccountManagerParameterForm>  findAllManager(){
		 return managerBelongMapDao.findAllManager();
	}
	//------------------------------------------------------济南绩效相关end --------------------------------------------//

}
