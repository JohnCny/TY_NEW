package com.cardpay.pccredit.postLoan.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;
/**
 * 借据表
 * @author sc
 */
@ModelParam(table = "T_FCLOANINFO")
public class Fcloaninfo extends BusinessModel {
	
	private String  keycode;
	private String  year;
	private String  times;
	private String  busicode;
	private String  busitype;
	private String  deptcode;
	private String  instcitycode;
	private String  contractcode;
	private String  custid;
	private String  cname;
	private String  typeid;
	private String  custtype;
	private String  custproperty;
	private String  orgcertcode;
	private String  unitcusttype;
	private String  cardtype;
	private String  cardnum;
	private String  city;
	private String  districtcounty;
	private String  town;
	private String  community;
	private String  instcode;
	private String  currency;
	private String  money;
	private String  balamt;
	private String  debtinterest;
	private String  reqlmt;
	private String  loandate;
	private String  enddate;
	private String  busistate;
	private String  creditlevel;
	private String  industry;
	private String  loanpurpose;
	private String  mainassure;
	private String  busimanager;
	private String  isback;
	private String  isbigcompany;
	private String  state;
	private String  islowrisk;
	private String  validtime;
	private String  sortresult;
	private String  sortresultfive;
	private String  presortresult;
	private String  presortfive;
	private String  autosortresult;
	private String  autosortremark;
	private String  autosortfive;
	private String  isadm;
	private String  custscale;
	private String  overtimes;
	private String  delayamtdays;
	private String  delayinterestdays;
	private String  changeflag;
	private String  categorytype;
	private String  operdatetime;
	private String  operator;
	private String  isworkmanu;
	private String  admdate;
	private String  ajuststate;
	private String  istrans;
	private String  lastyearsortresult;
	private String  processid;
	private String  tobadloanreason;
	private String  badloandate;
	private String  create_time;
	public String getKeycode() {
		return keycode;
	}
	public void setKeycode(String keycode) {
		this.keycode = keycode;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getTimes() {
		return times;
	}
	public void setTimes(String times) {
		this.times = times;
	}
	public String getBusicode() {
		return busicode;
	}
	public void setBusicode(String busicode) {
		this.busicode = busicode;
	}
	public String getBusitype() {
		return busitype;
	}
	public void setBusitype(String busitype) {
		this.busitype = busitype;
	}
	public String getDeptcode() {
		return deptcode;
	}
	public void setDeptcode(String deptcode) {
		this.deptcode = deptcode;
	}
	public String getInstcitycode() {
		return instcitycode;
	}
	public void setInstcitycode(String instcitycode) {
		this.instcitycode = instcitycode;
	}
	public String getContractcode() {
		return contractcode;
	}
	public void setContractcode(String contractcode) {
		this.contractcode = contractcode;
	}
	public String getCustid() {
		return custid;
	}
	public void setCustid(String custid) {
		this.custid = custid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getTypeid() {
		return typeid;
	}
	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}
	public String getCusttype() {
		return custtype;
	}
	public void setCusttype(String custtype) {
		this.custtype = custtype;
	}
	public String getCustproperty() {
		return custproperty;
	}
	public void setCustproperty(String custproperty) {
		this.custproperty = custproperty;
	}
	public String getOrgcertcode() {
		return orgcertcode;
	}
	public void setOrgcertcode(String orgcertcode) {
		this.orgcertcode = orgcertcode;
	}
	public String getUnitcusttype() {
		return unitcusttype;
	}
	public void setUnitcusttype(String unitcusttype) {
		this.unitcusttype = unitcusttype;
	}
	public String getCardtype() {
		return cardtype;
	}
	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}
	public String getCardnum() {
		return cardnum;
	}
	public void setCardnum(String cardnum) {
		this.cardnum = cardnum;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDistrictcounty() {
		return districtcounty;
	}
	public void setDistrictcounty(String districtcounty) {
		this.districtcounty = districtcounty;
	}
	public String getTown() {
		return town;
	}
	public void setTown(String town) {
		this.town = town;
	}
	public String getCommunity() {
		return community;
	}
	public void setCommunity(String community) {
		this.community = community;
	}
	public String getInstcode() {
		return instcode;
	}
	public void setInstcode(String instcode) {
		this.instcode = instcode;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getBalamt() {
		return balamt;
	}
	public void setBalamt(String balamt) {
		this.balamt = balamt;
	}
	public String getDebtinterest() {
		return debtinterest;
	}
	public void setDebtinterest(String debtinterest) {
		this.debtinterest = debtinterest;
	}
	public String getReqlmt() {
		return reqlmt;
	}
	public void setReqlmt(String reqlmt) {
		this.reqlmt = reqlmt;
	}
	public String getLoandate() {
		return loandate;
	}
	public void setLoandate(String loandate) {
		this.loandate = loandate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public String getBusistate() {
		return busistate;
	}
	public void setBusistate(String busistate) {
		this.busistate = busistate;
	}
	public String getCreditlevel() {
		return creditlevel;
	}
	public void setCreditlevel(String creditlevel) {
		this.creditlevel = creditlevel;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getLoanpurpose() {
		return loanpurpose;
	}
	public void setLoanpurpose(String loanpurpose) {
		this.loanpurpose = loanpurpose;
	}
	public String getMainassure() {
		return mainassure;
	}
	public void setMainassure(String mainassure) {
		this.mainassure = mainassure;
	}
	public String getBusimanager() {
		return busimanager;
	}
	public void setBusimanager(String busimanager) {
		this.busimanager = busimanager;
	}
	public String getIsback() {
		return isback;
	}
	public void setIsback(String isback) {
		this.isback = isback;
	}
	public String getIsbigcompany() {
		return isbigcompany;
	}
	public void setIsbigcompany(String isbigcompany) {
		this.isbigcompany = isbigcompany;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getIslowrisk() {
		return islowrisk;
	}
	public void setIslowrisk(String islowrisk) {
		this.islowrisk = islowrisk;
	}
	public String getValidtime() {
		return validtime;
	}
	public void setValidtime(String validtime) {
		this.validtime = validtime;
	}
	public String getSortresult() {
		return sortresult;
	}
	public void setSortresult(String sortresult) {
		this.sortresult = sortresult;
	}
	public String getSortresultfive() {
		return sortresultfive;
	}
	public void setSortresultfive(String sortresultfive) {
		this.sortresultfive = sortresultfive;
	}
	public String getPresortresult() {
		return presortresult;
	}
	public void setPresortresult(String presortresult) {
		this.presortresult = presortresult;
	}
	public String getPresortfive() {
		return presortfive;
	}
	public void setPresortfive(String presortfive) {
		this.presortfive = presortfive;
	}
	public String getAutosortresult() {
		return autosortresult;
	}
	public void setAutosortresult(String autosortresult) {
		this.autosortresult = autosortresult;
	}
	public String getAutosortremark() {
		return autosortremark;
	}
	public void setAutosortremark(String autosortremark) {
		this.autosortremark = autosortremark;
	}
	public String getAutosortfive() {
		return autosortfive;
	}
	public void setAutosortfive(String autosortfive) {
		this.autosortfive = autosortfive;
	}
	public String getIsadm() {
		return isadm;
	}
	public void setIsadm(String isadm) {
		this.isadm = isadm;
	}
	public String getCustscale() {
		return custscale;
	}
	public void setCustscale(String custscale) {
		this.custscale = custscale;
	}
	public String getOvertimes() {
		return overtimes;
	}
	public void setOvertimes(String overtimes) {
		this.overtimes = overtimes;
	}
	public String getDelayamtdays() {
		return delayamtdays;
	}
	public void setDelayamtdays(String delayamtdays) {
		this.delayamtdays = delayamtdays;
	}
	public String getDelayinterestdays() {
		return delayinterestdays;
	}
	public void setDelayinterestdays(String delayinterestdays) {
		this.delayinterestdays = delayinterestdays;
	}
	public String getChangeflag() {
		return changeflag;
	}
	public void setChangeflag(String changeflag) {
		this.changeflag = changeflag;
	}
	public String getCategorytype() {
		return categorytype;
	}
	public void setCategorytype(String categorytype) {
		this.categorytype = categorytype;
	}
	public String getOperdatetime() {
		return operdatetime;
	}
	public void setOperdatetime(String operdatetime) {
		this.operdatetime = operdatetime;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getIsworkmanu() {
		return isworkmanu;
	}
	public void setIsworkmanu(String isworkmanu) {
		this.isworkmanu = isworkmanu;
	}
	public String getAdmdate() {
		return admdate;
	}
	public void setAdmdate(String admdate) {
		this.admdate = admdate;
	}
	public String getAjuststate() {
		return ajuststate;
	}
	public void setAjuststate(String ajuststate) {
		this.ajuststate = ajuststate;
	}
	public String getIstrans() {
		return istrans;
	}
	public void setIstrans(String istrans) {
		this.istrans = istrans;
	}
	public String getLastyearsortresult() {
		return lastyearsortresult;
	}
	public void setLastyearsortresult(String lastyearsortresult) {
		this.lastyearsortresult = lastyearsortresult;
	}
	public String getProcessid() {
		return processid;
	}
	public void setProcessid(String processid) {
		this.processid = processid;
	}
	public String getTobadloanreason() {
		return tobadloanreason;
	}
	public void setTobadloanreason(String tobadloanreason) {
		this.tobadloanreason = tobadloanreason;
	}
	public String getBadloandate() {
		return badloandate;
	}
	public void setBadloandate(String badloandate) {
		this.badloandate = badloandate;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	
	
	
	  
}
