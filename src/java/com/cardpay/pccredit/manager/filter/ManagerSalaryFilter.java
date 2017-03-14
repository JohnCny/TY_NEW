package com.cardpay.pccredit.manager.filter;

import java.util.List;

import com.cardpay.pccredit.manager.web.AccountManagerParameterForm;
import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;

/**
 * @author chenzhifang
 *
 * 2014-11-14下午5:54:55
 */
public class ManagerSalaryFilter extends BaseQueryFilter {

	private String customerId;
	// 客户经理姓名
	private String managerName;
	
	private String rewardIncentiveInformation;
	
	private String deductAmount;
	
	private String basePay;
	
	private String allowance;
	
	private String dutySalary;
	
	private String returnPrepareAmount;
	
	private String insertPrepareAmount;
	
	private String fine;
	
	private String year;
	
	private String month;
	
	private String describe;
	
	private String customerManagerId;
	private List<AccountManagerParameterForm> customerManagerIds;
	
	private String organName;
	
	private String date;
	
	private String managerType;
	
	//太原添加部分
	//基本工资
	private String zhbasepay;
	
	//当月贷款绩效
	private String MonthPerformance;
	
	//主办
	private String zb;
	
	//协办
	private String xb;
	
	//当月放款绩效
	private String dyffdkjx;
	
	//当月管户绩效
	private String gh;
	
	//当月承担审批的贷款笔数绩效
	private String sp;
	
	//客户经理等级岗位绩效
	private String dj;
	
	//理辅助调查绩效
	private String fd;
	
	//任务完成度绩效
	private String rw;
	
	//不良贷款比率超过容忍度的处罚标准blje-被扣掉的绩效
	private String bl;
	
	//客户经理主管加成绩效
	private String zg;
	
	public String getZb() {
		return zb;
	}

	public void setZb(String zb) {
		this.zb = zb;
	}

	public String getXb() {
		return xb;
	}

	public void setXb(String xb) {
		this.xb = xb;
	}

	public String getZhbasepay() {
		return zhbasepay;
	}

	public void setZhbasepay(String zhbasepay) {
		this.zhbasepay = zhbasepay;
	}

	public String getMonthPerformance() {
		return MonthPerformance;
	}

	public void setMonthPerformance(String monthPerformance) {
		MonthPerformance = monthPerformance;
	}

	public String getDyffdkjx() {
		return dyffdkjx;
	}

	public void setDyffdkjx(String dyffdkjx) {
		this.dyffdkjx = dyffdkjx;
	}

	public String getGh() {
		return gh;
	}

	public void setGh(String gh) {
		this.gh = gh;
	}

	public String getSp() {
		return sp;
	}

	public void setSp(String sp) {
		this.sp = sp;
	}

	public String getDj() {
		return dj;
	}

	public void setDj(String dj) {
		this.dj = dj;
	}

	public String getFd() {
		return fd;
	}

	public void setFd(String fd) {
		this.fd = fd;
	}

	public String getRw() {
		return rw;
	}

	public void setRw(String rw) {
		this.rw = rw;
	}

	public String getBl() {
		return bl;
	}

	public void setBl(String bl) {
		this.bl = bl;
	}

	public String getZg() {
		return zg;
	}

	public void setZg(String zg) {
		this.zg = zg;
	}

	public String getManagerType() {
		return managerType;
	}

	public void setManagerType(String managerType) {
		this.managerType = managerType;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public String getCustomerManagerId() {
		return customerManagerId;
	}

	public void setCustomerManagerId(String customerManagerId) {
		this.customerManagerId = customerManagerId;
	}

	public List<AccountManagerParameterForm> getCustomerManagerIds() {
		return customerManagerIds;
	}

	public void setCustomerManagerIds(
			List<AccountManagerParameterForm> customerManagerIds) {
		this.customerManagerIds = customerManagerIds;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getRewardIncentiveInformation() {
		return rewardIncentiveInformation;
	}

	public void setRewardIncentiveInformation(String rewardIncentiveInformation) {
		this.rewardIncentiveInformation = rewardIncentiveInformation;
	}

	public String getDeductAmount() {
		return deductAmount;
	}

	public void setDeductAmount(String deductAmount) {
		this.deductAmount = deductAmount;
	}

	public String getBasePay() {
		return basePay;
	}

	public void setBasePay(String basePay) {
		this.basePay = basePay;
	}

	public String getAllowance() {
		return allowance;
	}

	public void setAllowance(String allowance) {
		this.allowance = allowance;
	}

	public String getDutySalary() {
		return dutySalary;
	}

	public void setDutySalary(String dutySalary) {
		this.dutySalary = dutySalary;
	}

	public String getReturnPrepareAmount() {
		return returnPrepareAmount;
	}

	public void setReturnPrepareAmount(String returnPrepareAmount) {
		this.returnPrepareAmount = returnPrepareAmount;
	}

	public String getInsertPrepareAmount() {
		return insertPrepareAmount;
	}

	public void setInsertPrepareAmount(String insertPrepareAmount) {
		this.insertPrepareAmount = insertPrepareAmount;
	}

	public String getFine() {
		return fine;
	}

	public void setFine(String fine) {
		this.fine = fine;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
}
