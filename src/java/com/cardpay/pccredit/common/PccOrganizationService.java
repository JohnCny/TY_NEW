package com.cardpay.pccredit.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.datapri.constant.DataPriConstants;
import com.cardpay.pccredit.datapri.web.FlatTreeNode;
import com.wicresoft.jrad.modules.privilege.model.Organization;
import com.wicresoft.jrad.modules.privilege.service.OrganizationService;

/**
 * 
 * 描述 ：机构信息相关
 * @author 张石树
 *
 * 2014-10-24 下午2:48:14
 */
@Service
public class PccOrganizationService {
	
	@Autowired
	private OrganizationService organizationService;

	/**
	 * 不包含跟节点的扁平结构  
	 * @param parentId 跟节点的ID //总行的根节点为获取方式 PrivilegeConstants.INIT_ID
	 * @return
	 */
	public List<FlatTreeNode> queryCurrentSubTreeList(String parentId){
		return queryAllOrgTreeList(parentId, false);
	}
	
	/**
	 * 包含跟节点的扁平结构 
	 * @param parentId 跟节点的ID //总行的根节点为获取方式 PrivilegeConstants.INIT_ID
	 * @return
	 */
	public List<FlatTreeNode> queryAllOrgTreeList(String parentId){
		return queryAllOrgTreeList(parentId, true);
	}
	
	/**
	 * 组织树形的扁平结构
	 * @param parentId
	 * @return
	 */
	private List<FlatTreeNode> queryAllOrgTreeList(String parentId, boolean flag) {
		List<FlatTreeNode> list = new ArrayList<FlatTreeNode>();
		int level = 0;
		if(flag){
			level = 1;
			Organization rootOrganization = organizationService.getOrganizationById(parentId);
			FlatTreeNode rootNode = new FlatTreeNode(rootOrganization.getId(), rootOrganization.getName(), DataPriConstants.ORG_TYPE, level);
			list.add(rootNode);
			this.recursiveTree(rootOrganization.getId(), list, level);
		} else {
			this.recursiveTree(parentId, list, level);
		}
		return list;
	}

	/**
	 * 递归算法解析成树形结构
	 * 
	 */
	private void recursiveTree(String parentId, List<FlatTreeNode> list, int level) {
		
		level++;
		List<Organization> childrenResources = organizationService.getOrganizationByParentId(parentId);
		// 递归遍历子节点
		for (int i = 0; i < childrenResources.size(); i++) {
			Organization currentOrg = childrenResources.get(i);
			FlatTreeNode currentNode = new FlatTreeNode(currentOrg.getId(), currentOrg.getName(), DataPriConstants.ORG_TYPE, level);
			list.add(currentNode);
			
			this.recursiveTree(currentOrg.getId(), list, level);
			
		}
	}
}
