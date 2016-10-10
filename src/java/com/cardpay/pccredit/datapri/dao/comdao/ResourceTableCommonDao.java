package com.cardpay.pccredit.datapri.dao.comdao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.modules.privilege.model.User;

/**
 * 
 * 描述 ：数据sql操作Dao
 * @author 张石树
 *
 * 2014-10-24 下午1:57:32
 */
@Service
public class ResourceTableCommonDao {

	@Autowired
	private CommonDao commonDao;
	
	/**
	 * 根据部门ID获取用户部门下用户列表
	 * @param deptId 部门ID
	 * @return 用户列表
	 */
	public List<User> findUserByDeptId(String deptId){
		Map<String, Object> qc = new HashMap<String, Object>();
		qc.put("managerId", deptId);
		List<User> users = commonDao.queryBySql(User.class, "select p.* from sys_user p,sys_dept_user d " +
				"where p.id=d.user_id and p.is_deleted=0 and d.dept_id=#{managerId} order by p.display_name asc", qc);
		return users;
	}
}
