/**
 * 
 */
package com.cardpay.pccredit.customer.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.aspectj.weaver.patterns.ArgsAnnotationPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.customer.constant.CommonConstant;
import com.cardpay.pccredit.customer.constant.MaintenanceEndResultEnum;
import com.cardpay.pccredit.customer.dao.CustomerLoanDao;
import com.cardpay.pccredit.customer.dao.MaintenanceDao;
import com.cardpay.pccredit.customer.filter.CustomerInforFilter;
import com.cardpay.pccredit.customer.filter.CustomerLoanFilter;
import com.cardpay.pccredit.customer.filter.MaintenanceFilter;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.model.Maintenance;
import com.cardpay.pccredit.customer.model.MaintenanceAction;
import com.cardpay.pccredit.customer.model.RepayCustomerInfor;
import com.cardpay.pccredit.customer.web.CustomerLoanForm;
import com.cardpay.pccredit.customer.web.MaintenanceForm;
import com.cardpay.pccredit.customer.web.MaintenanceWeb;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationInfo;
import com.cardpay.pccredit.manager.dao.ManagerBelongMapDao;
import com.cardpay.pccredit.manager.web.AccountManagerParameterForm;
import com.cardpay.pccredit.manager.web.ManagerBelongMapForm;
import com.cardpay.pccredit.manager.web.SysOrganizationForm;
import com.cardpay.pccredit.system.model.SystemUser;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.util.date.DateHelper;

/**
 * @author shaoming
 *
 * 2014年11月11日   下午3:05:12
 */
@Service
public class CustomerLoanService {
	
	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private CustomerLoanDao customerLoanDao;

	/**
	 * 借款人查询
	 * @param filter
	 * @return
	 */
	public QueryResult<CustomerLoanForm> findCustomerListByFilter(CustomerLoanFilter filter){
		List<CustomerLoanForm> plans = customerLoanDao.queryCustomerLoanList(filter);
		int size = customerLoanDao.queryCustomerLoanCountList(filter);
		QueryResult<CustomerLoanForm> qr = new QueryResult<CustomerLoanForm>(size,plans);
		for(int i=0;i<qr.getItems().size();i++){
			CustomerLoanForm form = qr.getItems().get(i);
			//获取客户经理组长
			String userId = form.getUserId();
			String sql = "select d.* from account_manager_parameter c,sys_user d where c.user_id=d.id and c.id in (select a.parent_id from manager_belong_map a,account_manager_parameter b where a.child_id=b.id and b.user_id='"+userId+"')";
			List<SystemUser> list = commonDao.queryBySql(SystemUser.class, sql, null);
			if(list.size()>0){
				form.setGroupName(list.get(0).getDisplayName());
			}else{
				form.setGroupName(form.getUserName());
			}
			
		}
		return qr;
	}

	/**
	 * 非借款人查询
	 * @param filter
	 * @return
	 */
	public QueryResult<CustomerLoanForm> findNoCustomerListByFilter(CustomerLoanFilter filter){
		List<CustomerLoanForm> plans = customerLoanDao.queryNoCustomerLoanList(filter);
		int size = customerLoanDao.queryNoCustomerLoanCountList(filter);
		QueryResult<CustomerLoanForm> qr = new QueryResult<CustomerLoanForm>(size,plans);
		for(int i=0;i<qr.getItems().size();i++){
			CustomerLoanForm form = qr.getItems().get(i);
			//获取客户经理组长
			String userId = form.getUserId();
			String sql = "select d.* from account_manager_parameter c,sys_user d where c.user_id=d.id and c.id in (select a.parent_id from manager_belong_map a,account_manager_parameter b where a.child_id=b.id and b.user_id='"+userId+"')";
			List<SystemUser> list = commonDao.queryBySql(SystemUser.class, sql, null);
			if(list.size()>0){
				form.setGroupName(list.get(0).getDisplayName());
			}else{
				form.setGroupName(form.getUserName());
			}
			
		}
		return qr;
	}
}
