package com.cardpay.pccredit.system.dao.comdao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.system.filter.SystemUserFilter;
import com.cardpay.pccredit.system.model.SystemUser;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.model.QueryResult;
@Service
public class SystemComDao {
	
	@Autowired
	private CommonDao commonDao;
	
	
	/* 客户经理查询用户名模糊匹配 */
	public void selectLikeByDisplayName(HttpServletResponse response,
			String tempParam) throws Exception {
		String country = null;
		String sql = "select t.* from sys_user t,account_manager_parameter a where a.user_id =t.id  order by a.id asc";
		List<SystemUser> allList = commonDao.queryBySql(SystemUser.class,
				sql,null);
		List<SystemUser> matched = new ArrayList<SystemUser>();
		for (SystemUser systemUser : allList) {
			if (systemUser.getDisplayName() != null) {
				country = systemUser.getDisplayName().toLowerCase();
				if (tempParam != null
						&& country.startsWith(tempParam.toLowerCase())) {
					matched.add(systemUser);
				}
			}
		}
		if (!matched.isEmpty()) {
			JSONArray obj = JSONArray.fromObject(matched);
			response.getWriter().println(obj.toString());
		}
	}
	
	/* 问责记录用户 */
	public QueryResult<SystemUser> findSystemUserByFilter(
			SystemUserFilter filter) {
		HashMap<String, Object> params = new HashMap<String, Object>();
	String orgId = filter.getOrgId();
		
		String sql = "select o.name as oname ,d.name,t.* from sys_user t left join sys_dept_user sd on t.id = sd.user_id left join sys_department d on sd.dept_id = d.id  left join sys_organization o on d.org_id = o.id where 1=1 ";
		if(StringUtils.trimToNull(orgId) !=null){
			params.put("orgId", orgId);
			String sqlorgId = "and d.org_id=#{orgId}";
			 sql = sql + sqlorgId;
			}
		return commonDao.queryBySqlInPagination(SystemUser.class, sql, params,
				filter.getStart(), filter.getLimit());
	}
	
	public QueryResult<SystemUser> findSystemUserByFilterAndUserType(SystemUserFilter filter,String userType) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		String orgId = filter.getOrgId();
		
		String sql = "select o.name as oname ,d.name,t.* from sys_user t "
				+ "left join sys_dept_user sd on t.id = sd.user_id "
				+ "left join sys_department d on sd.dept_id = d.id "
				+ "left join sys_organization o on d.org_id = o.id "
				+ "where 1=1 and t.user_type = "+userType + " ";
		if(StringUtils.trimToNull(orgId) !=null){
			params.put("orgId", orgId);
			String sqlorgId = "and d.org_id=#{orgId}";
			 sql = sql + sqlorgId;
			}
		return commonDao.queryBySqlInPagination(SystemUser.class, sql, params,
				filter.getStart(), filter.getLimit());
	}
}
