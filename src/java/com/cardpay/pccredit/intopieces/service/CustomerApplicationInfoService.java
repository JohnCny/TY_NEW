package com.cardpay.pccredit.intopieces.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.customer.filter.CustomerInforFilter;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.intopieces.constant.Constant;
import com.cardpay.pccredit.intopieces.dao.CustomerApplicationInfoDao;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationInfo;
import com.cardpay.pccredit.intopieces.model.TyApplicationLog;
import com.cardpay.pccredit.riskControl.constant.RiskCreateTypeEnum;
import com.cardpay.pccredit.riskControl.filter.RiskCustomerFilter;
import com.cardpay.pccredit.riskControl.model.RiskCustomer;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.id.IDGenerator;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.util.spring.Beans;

@Service
public class CustomerApplicationInfoService {

	@Autowired
	private CommonDao commonDao;

	@Autowired
	private CustomerApplicationInfoDao customerApplicationInfoDao;

	/*
	 * 保存申请客户信息
	 */
	public void save(CustomerApplicationInfo customerApplicationInfo) {
		commonDao.insertObject(customerApplicationInfo);
	}
	
	public void update(CustomerApplicationInfo customerApplicationInfo,HttpServletRequest request) {
		String cyUser1 = request.getParameter("cyUser1");
		String cyUser2 = request.getParameter("cyUser2");
		String fdUser = request.getParameter("fdUser");
		//更新进件
		commonDao.updateObject(customerApplicationInfo);
		CustomerApplicationInfo applicationInfo = commonDao.findObjectById(CustomerApplicationInfo.class, customerApplicationInfo.getId());
		//获取客户信息
		CustomerInfor info = commonDao.findObjectById(CustomerInfor.class, applicationInfo.getCustomerId());
		String status = customerApplicationInfo.getStatus();
		//拒绝，加入风险名单(没有则添加到风险名单)
		if(status.equals("refuse")){
			RiskCustomerFilter filter = new RiskCustomerFilter();
			filter.setCustomerId(info.getId());
			List<RiskCustomer> oldRisk = commonDao.findObjectsByFilter(RiskCustomer.class, filter).getItems();
			if(oldRisk.size()<1){
				//拒绝原因
				String refuseReason = request.getParameter("decisionRefusereason");
				IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
				RiskCustomer riskCustomer = new RiskCustomer();
				riskCustomer.setId(IDGenerator.generateID());
				riskCustomer.setCustomerId(info.getId());
				riskCustomer.setRefuseReason(refuseReason);
				riskCustomer.setCreatedTime(new Date());
				riskCustomer.setReportedIdManager(user.getId());
				riskCustomer.setCreatedBy(user.getId());
				riskCustomer.setRiskCreateType(RiskCreateTypeEnum.manual.toString());
				commonDao.insertObject(riskCustomer);
			}
		}
		//添加审批客户经理记录
		if(!cyUser1.equals("0")){
			TyApplicationLog log = new TyApplicationLog();
			log.setId(IDGenerator.generateID());
			log.setApplicationId(customerApplicationInfo.getId());
			log.setUserId(cyUser1);
			log.setType(Constant.APPSP);
			commonDao.insertObject(log);
		}
		if(!cyUser2.equals("0")){
			TyApplicationLog log = new TyApplicationLog();
			log.setId(IDGenerator.generateID());
			log.setApplicationId(customerApplicationInfo.getId());
			log.setUserId(cyUser2);
			log.setType(Constant.APPSP);
			commonDao.insertObject(log);
		}
		//审批通过，添加辅调客户经理
		if(status.equals("approved")){
			TyApplicationLog log = new TyApplicationLog();
			log.setId(IDGenerator.generateID());
			log.setApplicationId(customerApplicationInfo.getId());
			log.setUserId(fdUser);
			log.setType(Constant.APPFD);
			commonDao.insertObject(log);
		}
	}

	/*
	 * 删除客户信息
	 */
	public void delete(String id) {
		commonDao.deleteObject(CustomerApplicationInfo.class, id);
	}
	/**
	 * 统计拒绝进件条数
	 * @return
	 */
	public int findCustomerApplicationInfoCount(String userId,String status1,String status2){
		return customerApplicationInfoDao.findCustomerApplicationInfoCount(userId,status1,status2);
	}
}
