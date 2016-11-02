
package com.cardpay.pccredit.datapri.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.datapri.constant.DataPriConstants;
import com.cardpay.pccredit.datapri.constant.ResourceTableEnum;
import com.cardpay.pccredit.datapri.filter.ResourceTableFilter;
import com.cardpay.pccredit.datapri.model.ResourceTable;
import com.cardpay.pccredit.datapri.model.ResourceTableDataRule;
import com.cardpay.pccredit.datapri.model.ResourceTableField;
import com.cardpay.pccredit.datapri.service.DataAccessSqlService;
import com.cardpay.pccredit.datapri.service.ResourceTableService;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.constant.JRadConstants;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.i18n.I18nHelper;
import com.wicresoft.jrad.base.web.DataBindHelper;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.modules.privilege.business.RoleManager;
import com.wicresoft.jrad.modules.privilege.constant.PrivilegeConstants;
import com.wicresoft.jrad.modules.privilege.model.Organization;
import com.wicresoft.jrad.modules.privilege.model.Role;
import com.wicresoft.jrad.modules.privilege.model.User;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;

/**
 * 
 * 描述 ： 数据权限controller
 * @author 张石树
 *
 * 2014-10-20 下午4:36:10
 */
@Controller
@RequestMapping("/system/datapri/*")
@JRadModule("system.datapri")
public class ResourceTableController extends BaseController {

	@Autowired
	private RoleManager roleManager;
	
	@Autowired
	private ResourceTableService resourceTableService;

	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private DataAccessSqlService dataAccessSqlService;
	
	/**
	 * 浏览页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "browse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browse(@ModelAttribute ResourceTableFilter filter, HttpServletRequest request) {
		filter.setRequest(request);

		System.out.println(dataAccessSqlService.getSqlByResTbl(request, ResourceTableEnum.JINJIAN));
		
		QueryResult<ResourceTable> result = resourceTableService.findResTblByFilter(filter);
		JRadPagedQueryResult<ResourceTable> pagedResult = new JRadPagedQueryResult<ResourceTable>(filter, result);

		JRadModelAndView mv = new JRadModelAndView("/modules/privilege/datapri/resource_table_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);

		return mv;
	}

	/**
	 * 修改页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "change.page")
	@JRadOperation(JRadOperation.CHANGE)
	public AbstractModelAndView change(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/modules/privilege/datapri/resource_table_rule_change", request);
		String resTblId = request.getParameter("id");

		ResourceTable resourceTable = resourceTableService.findResTblById(resTblId);
		
		List<ResourceTableField> tableFields = resourceTableService.findResTblFieldByTblId(resTblId);
		StringBuffer tblFieldOptionHtml = new StringBuffer();
		tblFieldOptionHtml.append("<option value='" + DataPriConstants.CURRENT_ROLE_ID + "'>" + DataPriConstants.CURRENT_ROLE_MSG + "</option>");
		tblFieldOptionHtml.append("<option value='" + DataPriConstants.CURRENT_DEPT_ID + "'>" + DataPriConstants.CURRENT_DEPT_MSG + "</option>");
		tblFieldOptionHtml.append("<option value='" + DataPriConstants.CURRENT_ORG_ID + "'>" + DataPriConstants.CURRENT_ORG_MSG + "</option>");
		tblFieldOptionHtml.append("<option value='" + DataPriConstants.CURRENT_USER_ID + "'>" + DataPriConstants.CURRENT_USER_MSG + "</option>");
		for(ResourceTableField field : tableFields){
			tblFieldOptionHtml.append("<option value='" + field.getResTblField() + "_" + field.getResTblFieldType() + "'>" + field.getResTblFieldCn() + "</option>");
		}
		mv.addObject("tblFieldOptionHtml", tblFieldOptionHtml.toString());
		
		List<Organization> orgList = resourceTableService.queryAllOrgTreeList(PrivilegeConstants.INIT_ID);
		List<FlatTreeNode> deptTreeNodes =  resourceTableService.queryAllOrgAndDepartment(PrivilegeConstants.INIT_ID);
		
		List<FlatTreeNode> orgDeptTreeNodes = new ArrayList<FlatTreeNode>();
		if(orgList != null && orgList.size() > 0){
			resourceTableService.queryAllDepartment(orgList.get(0).getId(), orgDeptTreeNodes, 1);
		}
		mv.addObject("orgDeptTreeNodes", orgDeptTreeNodes);
		List<User> users = new ArrayList<User>();
		if(orgDeptTreeNodes != null && orgDeptTreeNodes.size() > 0){
			users = resourceTableService.findUserByDeptId(orgDeptTreeNodes.get(0).getId());
		}
		mv.addObject("users", users);
		
		mv.addObject("orgList", orgList);
		mv.addObject("deptTreeNodes", deptTreeNodes);
		
		List<Role> list = roleManager.getAllRoles();
		mv.addObject("roles", list);
		
		ResourceTableDataRule dataRule = resourceTableService.findResTblRule2ByTblId(resTblId);
		if(dataRule == null){
			dataRule = new ResourceTableDataRule();
		}
		if(dataRule.getDivHtml() != null){
			dataRule.setDivHtmlStr(new String(dataRule.getDivHtml()));
		} else {
			dataRule.setDivHtmlStr("");
		}
		mv.addObject("dataRule", dataRule);
		mv.addObject("resourceTable", resourceTable);
		
		return mv;
	}

	/**
	 * 选择用户页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "selectuser.page")
	@JRadOperation(JRadOperation.DISPLAY)
	public AbstractModelAndView selectUser(HttpServletRequest request) {
		String selectUserIds = request.getParameter("selectUserIds");
		String selectUserNames = request.getParameter("selectUserNames");
		JRadModelAndView mv = new JRadModelAndView("/modules/privilege/datapri/resource_table_select_user", request);
		List<Organization> orgList = resourceTableService.queryAllOrgTreeList(PrivilegeConstants.INIT_ID);
		
		List<FlatTreeNode> orgDeptTreeNodes = new ArrayList<FlatTreeNode>();
		if(orgList != null && orgList.size() > 0){
			resourceTableService.queryAllDepartment(orgList.get(0).getId(), orgDeptTreeNodes, 1);
		}
		mv.addObject("orgDeptTreeNodes", orgDeptTreeNodes);
		List<User> users = new ArrayList<User>();
		if(orgDeptTreeNodes != null && orgDeptTreeNodes.size() > 0){
			users = resourceTableService.findUserByDeptId(orgDeptTreeNodes.get(0).getId());
		}
		mv.addObject("users", users);
		mv.addObject("orgList", orgList);
		mv.addObject("selectUserIds", selectUserIds);
		mv.addObject("selectUserNames", selectUserNames);
		return mv;
	}
	
	/**
	 * 获取部门和用户
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "querydeptandusers.json")
	@JRadOperation(JRadOperation.DISPLAY)
	public HashMap<String, Object> queryDeptAndUsers(HttpServletRequest request) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			String orgId = request.getParameter("orgId");
			List<FlatTreeNode> orgDeptTreeNodes = new ArrayList<FlatTreeNode>();
			resourceTableService.queryAllDepartment(orgId, orgDeptTreeNodes, 1);
			StringBuffer sb = new StringBuffer();
			for(FlatTreeNode ftn : orgDeptTreeNodes){
				String bankStr = "";
				for(int i = 0; i < ftn.getLevel() - 1; i++){
					bankStr = bankStr + "&nbsp;&nbsp;&nbsp;";
				}
				sb.append(bankStr).append("_").append(ftn.getId()).append("_").append(ftn.getName()).append(":");
			}
			if(sb.length() > 0){
				sb = sb.deleteCharAt(sb.length() - 1);
			}
			map.put("deptListStr", sb.toString());
			List<User> users = new ArrayList<User>();
			if(orgDeptTreeNodes != null && orgDeptTreeNodes.size() > 0){
				users = resourceTableService.findUserByDeptId(orgDeptTreeNodes.get(0).getId());
			}
			StringBuffer usb = new StringBuffer();
			for(User u : users){
				usb.append(u.getId()).append("_").append(u.getDisplayName()).append(":");
			}
			if(usb.length() > 0){
				usb = usb.deleteCharAt(usb.length() - 1);
			}
			map.put("userListStr", usb.toString());
			map.put(JRadConstants.SUCCESS, true);
		} catch (Exception e) {
			map.put(JRadConstants.SUCCESS, true);
			map.put(JRadConstants.MESSAGE, DataPriConstants.SYS_EXCEPTION_MSG);
		}
		
		return map;
	}
	
	/**
	 * 获取用户
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryusers.json")
	@JRadOperation(JRadOperation.DISPLAY)
	public HashMap<String, Object> queryUsers(HttpServletRequest request) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			String deptSelectId = request.getParameter("deptSelectId");
			List<User> users = resourceTableService.findUserByDeptId(deptSelectId);
			StringBuffer usb = new StringBuffer();
			for(User u : users){
				usb.append(u.getId()).append("_").append(u.getDisplayName()).append(":");
			}
			if(usb.length() > 0){
				usb = usb.deleteCharAt(usb.length() - 1);
			}
			map.put("userListStr", usb.toString());
			map.put(JRadConstants.SUCCESS, true);
		} catch (Exception e) {
			map.put(JRadConstants.SUCCESS, true);
			map.put(JRadConstants.MESSAGE, DataPriConstants.SYS_EXCEPTION_MSG);
		}
		return map;
	}
	
	/**
	 * 显示页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "display.page")
	@JRadOperation(JRadOperation.DISPLAY)
	public AbstractModelAndView display(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/modules/privilege/datapri/resource_table_rule_display", request);
		String resTblId = request.getParameter("id");
		ResourceTableDataRule dataRule = resourceTableService.findResTblRule2ByTblId(resTblId);
		if(dataRule != null){
			if(dataRule.getDivHtml() != null){
				dataRule.setDivHtmlStr(new String(dataRule.getDivHtml()));
			} else {
				dataRule.setDivHtmlStr("");
			}
			mv.addObject("dataRule", dataRule);

		}
		ResourceTable resourceTable = resourceTableService.findResTblById(resTblId);
		
		mv.addObject("resourceTable", resourceTable);
		
		return mv;
	}


	/**
	 * 执行修改
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "update.json")
	@JRadOperation(JRadOperation.BROWSE)
    //TODO 以下这段代码可考虑重构，比如测试SQL语句，今后想扩展比较好做
	public HashMap<String, Object> update(HttpServletRequest request) {
		User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
		HashMap<String, Object> returnMap = new HashMap<String, Object>();

		String id = request.getParameter("id");
		String ruleSql = request.getParameter("ruleSql");
		String resTblId = request.getParameter("resTblId");

		try {
			String tempRuleSql = ruleSql;
			//校验sql片段是否正确
			if(StringUtils.isNotEmpty(tempRuleSql)){
				ResourceTable resourceTable = resourceTableService.findResTblById(resTblId);
				
				tempRuleSql = tempRuleSql.replace(DataPriConstants.CURRENT_ROLE_ID, DataPriConstants.TEMP_ROLE_REPLACE_VALUE);
				tempRuleSql = tempRuleSql.replace(DataPriConstants.CURRENT_DEPT_ID, DataPriConstants.TEMP_DEPT_REPLACE_VALUE);
				tempRuleSql = tempRuleSql.replace(DataPriConstants.CURRENT_ORG_ID, DataPriConstants.TEMP_ORG_REPLACE_VALUE);
				tempRuleSql = tempRuleSql.replace(DataPriConstants.CURRENT_USER_ID, DataPriConstants.TEMP_USER_REPLACE_VALUE);
				
				String sqlTempRule = DataPriConstants.TEMP_VALIDATE_SQL.replace("{0}", resourceTable.getResTbl()).replace("{1}", tempRuleSql);
				try {
					commonDao.queryBySql(sqlTempRule, null);
				} catch (Exception e) {
					returnMap.put(JRadConstants.MESSAGE, DataPriConstants.VALIDE_SQL_MSG.replace("{0}", tempRuleSql));
					returnMap.put(JRadConstants.SUCCESS, false);
					return returnMap;
				}
			}
			
			ResourceTableDataRule dataRule = new ResourceTableDataRule();
			dataRule.setId(id);
			dataRule.setResTblId(resTblId);
			dataRule.setRuleSql(ruleSql);
			String divHtml = request.getParameter("divHtmlStr");
			if(StringUtils.isNotEmpty(divHtml)){
				dataRule.setDivHtml(divHtml.getBytes());
			} else {
				dataRule.setDivHtml(null);
			}
			resourceTableService.saveOrUpdateResTblDataRule(dataRule, user);
			
			returnMap.put(JRadConstants.SUCCESS, true);
			returnMap.put(JRadConstants.MESSAGE, Beans.get(I18nHelper.class).getMessageNotNull(JRadConstants.CHANGE_SUCCESS));
			
			return returnMap;
		} catch (Exception e) {
			returnMap.put(JRadConstants.MESSAGE, DataPriConstants.SYS_EXCEPTION_MSG);
			returnMap.put(JRadConstants.SUCCESS, false);
			return returnMap;
		}

	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DataBindHelper.initStandardBinder(binder);
	}

}
