/**
 * 
 */
package com.cardpay.pccredit.datapri.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.datapri.constant.DataPriConstants;
import com.cardpay.pccredit.datapri.dao.ResourceTableDao;
import com.cardpay.pccredit.datapri.dao.comdao.ResourceTableCommonDao;
import com.cardpay.pccredit.datapri.filter.ResourceTableFilter;
import com.cardpay.pccredit.datapri.model.ResourceTable;
import com.cardpay.pccredit.datapri.model.ResourceTableDataRule;
import com.cardpay.pccredit.datapri.model.ResourceTableField;
import com.cardpay.pccredit.datapri.web.FlatTreeNode;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.modules.privilege.model.Department;
import com.wicresoft.jrad.modules.privilege.model.Organization;
import com.wicresoft.jrad.modules.privilege.model.User;
import com.wicresoft.jrad.modules.privilege.service.DepartmentService;
import com.wicresoft.jrad.modules.privilege.service.OrganizationService;

/**
 * 数据权限service
 * @author steven Zhang
 *
 * 2014-10-13 下午3:35:39
 */
@Service
public class ResourceTableService {

	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private ResourceTableDao resourceTableDao;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private ResourceTableCommonDao resourceTableCommonDao;
	
	/**
	 * 根据资源主体id获取资源主体数据规则
	 * @param resTblId
	 * @return
	 */
	public ResourceTableDataRule findResTblRule2ByTblId(String resTblId) {
		return resourceTableDao.findDataRuleByTblId(resTblId);
	}
	
	/**
	 * 根据资源主体名称获取资源主体数据规则
	 * @param resTblId
	 * @return
	 */
	public ResourceTableDataRule findResTblRule2ByTbl(String resTbl) {
		return resourceTableDao.findDataRuleByTbl(resTbl);
	}

	/**
	 * 获取资源主体
	 * @param id
	 * @return
	 */
	public ResourceTable findResTblById(String id) {
		return commonDao.findObjectById(ResourceTable.class, id);
	}
	
	/**
	 * 获取资源主体列表
	 * @param filter
	 * @return
	 */
	public QueryResult<ResourceTable> findResTblByFilter(ResourceTableFilter filter) {
		return commonDao.findObjectsByFilter(ResourceTable.class, filter);
	}
	
	/**
	 * 根据资源表获取sql片段
	 * @param resTbl
	 * @return
	 */
	public String findDataRuleSqlByTbl(String resTbl) {
		return resourceTableDao.findDataRuleSqlByTbl(resTbl);
	}
	
	
	
	/**
	 * 报存或修改数据规则
	 * @param dataRule
	 * @param user
	 */
	public void saveOrUpdateResTblDataRule(ResourceTableDataRule dataRule, User user){
		if(StringUtils.isNotEmpty(dataRule.getId())){
			ResourceTableDataRule dbdataRule = resourceTableDao.findDataRuleById(dataRule.getId());
			dbdataRule.setDivHtml(dataRule.getDivHtml());
			dbdataRule.setRuleSql(dataRule.getRuleSql());
			dbdataRule.setModifiedBy(user.getId());
			dbdataRule.setModifiedTime(new Date());
			resourceTableDao.update(dbdataRule);
		} else {
			dataRule.setCreatedBy(user.getId());
			dataRule.setCreatedTime(new Date());
			resourceTableDao.insert(dataRule);
		}
	}
	
	/**
	 * 根据资源主体id获取资源主体字段列表
	 * @param resTblId
	 * @return List<ResourceTableField>
	 */
	public List<ResourceTableField> findResTblFieldByTblId(String resTblId) {
		return resourceTableDao.findResTblFieldByTblId(resTblId);
	}
	
	/**
	 * 组织树形的扁平结构
	 * @param parentId
	 * @return
	 */
	public List<Organization> queryAllOrgTreeList(String parentId) {
		List<Organization> list = new ArrayList<Organization>();
		int level = 1;
		Organization rootOrganization = organizationService.getOrganizationById(parentId);
		rootOrganization.setSeqNo(level);
		list.add(rootOrganization);
		this.recursiveTree(rootOrganization.getId(), list, level);
		
		return list;
	}

	/**
	 * 递归算法解析成树形结构
	 * 
	 */
	public void recursiveTree(String parentId, List<Organization> list, int level) {
		
		level++;
		List<Organization> childrenResources = organizationService.getOrganizationByParentId(parentId);
		// 递归遍历子节点
		for (int i = 0; i < childrenResources.size(); i++) {
			Organization currentOrg = childrenResources.get(i);
			currentOrg.setSeqNo(level);
			list.add(currentOrg);
			
			this.recursiveTree(currentOrg.getId(), list, level);
			
		}
	}
	
	/**
	 * 组织和部门树形的扁平结构
	 * @param parentId
	 * @return
	 */
	public List<FlatTreeNode> queryAllOrgAndDepartment(String parentId){
		List<FlatTreeNode> list = new ArrayList<FlatTreeNode>();
		int level = 1;
		Organization rootOrganization = organizationService.getOrganizationById(parentId);

		FlatTreeNode rootNode = new FlatTreeNode(rootOrganization.getId(), rootOrganization.getName(), DataPriConstants.ORG_TYPE, level);
		list.add(rootNode);
		this.recursiveOrgTree(rootOrganization.getId(), list, level);
		
		queryAllDepartment(parentId, list, level);
		
		return list;
	}
	
	/**
	 * 递归算法解析成树形结构
	 * 
	 */
	public void recursiveOrgTree(String parentId, List<FlatTreeNode> list, int level) {
		level++;
		List<Organization> childrenOrganizations = organizationService.getOrganizationByParentId(parentId);
		// 递归遍历子节点
		for (Organization organization : childrenOrganizations) {
		
			FlatTreeNode rootNode = new FlatTreeNode(organization.getId(), organization.getName(), DataPriConstants.ORG_TYPE, level);
			list.add(rootNode);
			
			this.queryAllDepartment(organization.getId(), list, level);
			
			this.recursiveOrgTree(organization.getId(), list, level);
			
		}
	}
	
	/**
	 * 根据组织ID获取组织下的所有部门的扁平list
	 * @param parentId 机构Id
	 * @param list 存储List
	 * @param level 级别
	 */
	public void queryAllDepartment(String parentId, List<FlatTreeNode> list, int level){
		level++;
		List<Department> departments = departmentService.getDepartmentByOrgId(parentId);
		for(Department department : departments){
			FlatTreeNode rootNode = new FlatTreeNode(department.getId(), department.getName(), DataPriConstants.DEPT_TYPE, level);
			list.add(rootNode);
			
			this.recursiveDeptTree(department.getId(), list, level);
		}
	}
	
	/**
	 * 递归部门
	 * @param parentId 父节点id
	 * @param list 存储List
	 * @param level 级别
	 */
	public void recursiveDeptTree(String parentId, List<FlatTreeNode> list, int level){
		level++;
		List<Department> departments = departmentService.getDepartmentByParentId(parentId);
		// 递归遍历子节点
		for(Department department : departments){
			FlatTreeNode rootNode = new FlatTreeNode(department.getId(), department.getName(), DataPriConstants.DEPT_TYPE, level);
			list.add(rootNode);
			
			this.recursiveDeptTree(department.getId(), list, level);
		}
	}
	
	/**
	 * 根据部门ID获取用户部门下用户列表
	 * @param deptId 部门ID
	 * @return 用户列表
	 */
	public List<User> findUserByDeptId(String deptId){
		return resourceTableCommonDao.findUserByDeptId(deptId);
	}
	

}
