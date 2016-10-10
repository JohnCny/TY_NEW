package com.cardpay.pccredit.jnpad.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.customer.constant.CommonConstant;
import com.cardpay.pccredit.customer.dao.CustomerInforDao;
import com.cardpay.pccredit.customer.dao.MaintenanceDao;
import com.cardpay.pccredit.customer.filter.CustomerInforFilter;
import com.cardpay.pccredit.customer.filter.MaintenanceFilter;
import com.cardpay.pccredit.customer.model.Maintenance;
import com.cardpay.pccredit.customer.model.MaintenanceLog;
import com.cardpay.pccredit.customer.web.MaintenanceForm;
import com.cardpay.pccredit.customer.web.MaintenanceWeb;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationInfo;
import com.cardpay.pccredit.jnpad.dao.JnpadMaintenanceDao;
import com.cardpay.pccredit.manager.dao.ManagerBelongMapDao;
import com.cardpay.pccredit.manager.web.AccountManagerParameterForm;
import com.cardpay.pccredit.manager.web.ManagerBelongMapForm;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.util.date.DateHelper;


@Service
public class JnpadMaintenanceService {
	@Autowired
	private ManagerBelongMapDao managerBelongMapDao;
	
	@Autowired
	private JnpadMaintenanceDao maintenanceDao;
	@Autowired
	private MaintenanceDao mmaintenanceDao;
	
	@Autowired
	private CustomerInforDao customerInforDao;
	
	@Autowired
	private CommonDao commonDao;
	
	public  List<AccountManagerParameterForm> selectSubListManagerByManagerId(String id, int userType) {
		 List<AccountManagerParameterForm>  forms = new ArrayList<AccountManagerParameterForm>();
		 
		//如果是客户经理1
		if(CommonConstant.USER_TYPE.USER_TYPE_1 == userType){
			List<ManagerBelongMapForm> childBelongMapList = maintenanceDao.findChildId(id);
			if(childBelongMapList != null && childBelongMapList.size() > 0){
					StringBuffer belongChildIds = new StringBuffer();
					belongChildIds.append("(");
					for(ManagerBelongMapForm belongMapForm : childBelongMapList){
						belongChildIds.append("'").append(belongMapForm.getChildId()).append("'").append(",");
					}
					belongChildIds = belongChildIds.deleteCharAt(belongChildIds.length() - 1);
					belongChildIds.append(")");
					return maintenanceDao.findAccountManagerParameterByChildIds(belongChildIds.toString());
			}
		}
		//如果是部门主管2
		if(CommonConstant.USER_TYPE.USER_TYPE_2 == userType){
			forms =  managerBelongMapDao.findDeptManagerById(id);
		}
		//如果是机构主管3
		if(CommonConstant.USER_TYPE.USER_TYPE_3 == userType){
			forms =  managerBelongMapDao.findOrgManagerById(id);
		}
		return forms;
	}

	/**
	 * 获取指定客户经理的客户列表
	 * @param filter
	 * @return
	 */

	
	public QueryResult<MaintenanceLog> findCustomerByFilter(CustomerInforFilter filter){
		List<MaintenanceLog> plans = customerInforDao.findCustomerByFilter(filter);
		int size = customerInforDao.findCustomerCountByFilter(filter);
		QueryResult<MaintenanceLog> qr = new QueryResult<MaintenanceLog>(size,plans);
		return qr;
	}
	
	/**
	 * 获取客户维护计划列表
	 */
	
	public List<MaintenanceForm> findMaintenancePlansList(MaintenanceFilter filter){
		List<MaintenanceForm> plans = maintenanceDao.findMaintenancePlansList(filter);
//		int size = maintenanceDao.findMaintenancePlansCountList(filter);
//		QueryResult<MaintenanceForm> qr = new QueryResult<MaintenanceForm>(size,plans);
		return plans;
	}

	/**
	 * 根据customerId、productId获取appId
	 * @param maintenanceId
	 * @param appId
	 * @return
	 */
	public String getAppId(String customerId, String productId) {
		String sql = "select * from customer_application_info where customer_id='"+customerId+"' and product_id='"+productId+"'";
		List<CustomerApplicationInfo> info =   commonDao.queryBySql(CustomerApplicationInfo.class, sql, null);
		return info.get(0).getId();
	}

	/**
	 * 得到维护计划(browse)
	 * @param filter
	 * @return
	 */
	public QueryResult<MaintenanceWeb> findMaintenanceWebPlansByFilter(MaintenanceFilter filter) {
		List<MaintenanceWeb> plans = mmaintenanceDao.findMaintenanceWebPlansByFilter(filter);
		int size = mmaintenanceDao.findMaintenancePlansCountByFilter(filter);
		QueryResult<MaintenanceWeb> qr = new QueryResult<MaintenanceWeb>(size,plans);
		return qr;
	}

	/**
	 * 得到维护计划(change)
	 * @param maintenanceId
	 * @param appId
	 * @return
	 */
	public MaintenanceForm findMaintenance(MaintenanceWeb m) {

		return mmaintenanceDao.findMaintenance(m);
	}

	/**
	 * 通过维护计划id得到实施记录
	 * @param id
	 * @return
	 */
	public List<MaintenanceWeb> findMaintenanceActionByMaintenanceId(String maintenanceId) {

		return mmaintenanceDao.findMaintenanceActionByMaintenanceId(maintenanceId);
	}
	/**
	 * 修改维护计划
	 * @param maintenance
	 * @return
	 */
	public boolean updateMaintenance(Maintenance maintenance){
		Date createdTime = commonDao.findObjectById(Maintenance.class, maintenance.getId()).getCreatedTime();
		String maintenanceDay = maintenance.getMaintenanceDay()==null?"":maintenance.getMaintenanceDay();
		Date maintenanceEndtime = DateHelper.shiftDay(createdTime, Integer.parseInt(maintenanceDay.equals("")?"0":maintenanceDay));
		maintenance.setMaintenanceEndtime(maintenanceEndtime);
		maintenance.setModifiedTime(new Date());
		int i = commonDao.updateObject(maintenance);
		return i>0?true:false;
	}
	
	/**
	 * 得到维护计划(change)
	 * @param maintenanceId
	 * @return
	 */
	public MaintenanceForm findMaintenanceById(String maintenanceId){
		return mmaintenanceDao.findMaintenanceById(maintenanceId);
	}
}
