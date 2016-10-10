package com.cardpay.pccredit.datapri.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.datapri.constant.DataPriConstants;
import com.cardpay.pccredit.datapri.constant.ResourceTableEnum;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.modules.privilege.model.User;
import com.wicresoft.util.spring.Beans;

/**
 * 
 * 提供根据资源枚举类型获取数据权限sql片段
 * @author 张石树
 *
 * 2014-10-20 下午4:34:30
 */
@Service
public class DataAccessSqlService {
	
	@Autowired
	private ResourceTableService resourceTableService;
	
	/**
	 * 根据资源枚举获取数据权限条件sql段
	 * @param request
	 * @param rt
	 * @return 表名或sql的结果语句
	 */
	public String getSqlByResTbl(HttpServletRequest request, ResourceTableEnum rt){
		String resTbl = "";
		if(ResourceTableEnum.JINJIAN.name().equals(rt.name())){
			resTbl = DataPriConstants.JINJIAN_TABLE;
		}
		if(ResourceTableEnum.KEHU.name().equals(rt.name())){
			resTbl = DataPriConstants.KEHU_TABLE;
		}
		if(ResourceTableEnum.KEHUJINGLI.name().equals(rt.name())){
			resTbl = DataPriConstants.KEHUJINGLI_TABLE;
		}
		
		String ruleSql = resourceTableService.findDataRuleSqlByTbl(resTbl);
		if(StringUtils.isNotEmpty(ruleSql)){
			
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			//替换机构
			ruleSql = ruleSql.replace(DataPriConstants.CURRENT_ORG_ID, "'" + user.getOrganization().getId() + "'"); 
			//替换部门
			ruleSql = ruleSql.replace(DataPriConstants.CURRENT_DEPT_ID, "'" + ((User)user).getDepartment().getId() + "'");
			//替换角色
			ruleSql = ruleSql.replace(DataPriConstants.CURRENT_ROLE_ID, "'" + ((User)user).getRoles().get(0).getId() + "'");
			//替换用户
			ruleSql = ruleSql.replace(DataPriConstants.CURRENT_USER_ID, "'" + user.getId() + "'");
			
			return "(select * from  " + resTbl + " where 1=1 " + ruleSql + ")";
		} else {
			return resTbl;
		}
	}
	
	/**
	 * 根据客户经理ID获取进件选择产品的sql片段条件
	 * @param request
	 * @param custManagerID 客户经理ID
	 * @return
	 */
	public String getProductSqlByUserId(HttpServletRequest request, String custManagerID){
		//TODO
		//机构
		
		//角色
		
		//部门
		
		return DataPriConstants.CHANGPIN_TABLE;
	}
}
