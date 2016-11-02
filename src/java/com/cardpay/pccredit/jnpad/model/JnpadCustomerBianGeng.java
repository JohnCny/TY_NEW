package com.cardpay.pccredit.jnpad.model;

import java.math.BigDecimal;

import com.wicresoft.jrad.base.database.id.IDType;
import com.wicresoft.jrad.base.database.model.BaseModel;
import com.wicresoft.jrad.base.database.model.ModelParam;


@ModelParam(table = "T_CIPERSONBASINFO_COPY",generator=IDType.assigned)
public class JnpadCustomerBianGeng extends BaseModel{


	private static final long serialVersionUID = 1L;
	private String	id;                  
	private String  initid;              
	private String  custid;              
	private String  typeid;              
	private String  custtype;            
	private String  cname;               
	private String  sex;                 
	private String  cardtype;            
	private String  cardnum;             
	private String  ethical;             
	private String  country;             
	private String  birthday;            
	private String  syaddr;              
	private String  usefullife;          
	private String  signorg;             
	private String  custproperty;        
	private String  employer;            
	private String  employeraddr;        
	private String  employerpostcode;    
	private String  employertime;        
	private String  categoryid;          
	private String  title;               
	private String  vocation;            
	private String  duty;                
	private String  registercode;        
	private String  codename;            
	private String  managername;         
	private String  manageplace;         
	private String  manageextent;        
	private String  founddate;           
	private String  annucheck;           
	private String  school;              
	private String  schooladdr;          
	private String  schoolattrib;        
	private String  schooltel;           
	private String  marrige;             
	private String  isreferagricultural; 
	private String  city;                
	private String  districtcounty;      
	private String  town;                
	private String  community;           
	private String  contactmobiletel;    
	private String  contacttel;          
	private String  address;             
	private String  postcode;            
	private String  messageaddr;         
	private String  commpostcode;        
	private String  educationlevel;      
	private String  degree;              
	private String  industry;            
	private String  reside;              
	private String  levelcal;            
	private String  joinring;            
	private BigDecimal  famasset;            
	private BigDecimal  famdebt;             
	private BigDecimal  famannuincome;       
	private BigDecimal  famannupayout;       
	private String  relaman;             
	private String  email;               
	private String  incomeaccount;       
	private String  incomebank;         
	private String  busimanager;         
	private String  instcode;            
	private String  deptcode;            
	private String  instcitycode;        
	private String  istmp;               
	private Integer  version;             
	private String  disposalstatus;      
	private String  status;              
	private String  remark;              
	private String  inputman;            
	private String  inputdate;           
	private String  groupid;             
	private String  operator;            
	private String  operdatetime;        
	private String  custstatus;          
	private String  assistbusimanage;    
	private Integer  famnum;              
	private String  firstmanager;        
	private String  governmanager;       
	private String  istrans;             
	private String  hukou;               
	private String  backoperator;        
	private String  busiflag;            
	private String  create_time;
	
	private int islook;
	
	public int getIslook() {
		return islook;
	}
	public void setIslook(int islook) {
		this.islook = islook;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getInitid() {
		return initid;
	}
	public void setInitid(String initid) {
		this.initid = initid;
	}
	public String getCustid() {
		return custid;
	}
	public void setCustid(String custid) {
		this.custid = custid;
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
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
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
	public String getEthical() {
		return ethical;
	}
	public void setEthical(String ethical) {
		this.ethical = ethical;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getSyaddr() {
		return syaddr;
	}
	public void setSyaddr(String syaddr) {
		this.syaddr = syaddr;
	}
	public String getUsefullife() {
		return usefullife;
	}
	public void setUsefullife(String usefullife) {
		this.usefullife = usefullife;
	}
	public String getSignorg() {
		return signorg;
	}
	public void setSignorg(String signorg) {
		this.signorg = signorg;
	}
	public String getCustproperty() {
		return custproperty;
	}
	public void setCustproperty(String custproperty) {
		this.custproperty = custproperty;
	}
	public String getEmployer() {
		return employer;
	}
	public void setEmployer(String employer) {
		this.employer = employer;
	}
	public String getEmployeraddr() {
		return employeraddr;
	}
	public void setEmployeraddr(String employeraddr) {
		this.employeraddr = employeraddr;
	}
	public String getEmployerpostcode() {
		return employerpostcode;
	}
	public void setEmployerpostcode(String employerpostcode) {
		this.employerpostcode = employerpostcode;
	}
	public String getEmployertime() {
		return employertime;
	}
	public void setEmployertime(String employertime) {
		this.employertime = employertime;
	}
	public String getCategoryid() {
		return categoryid;
	}
	public void setCategoryid(String categoryid) {
		this.categoryid = categoryid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getVocation() {
		return vocation;
	}
	public void setVocation(String vocation) {
		this.vocation = vocation;
	}
	public String getDuty() {
		return duty;
	}
	public void setDuty(String duty) {
		this.duty = duty;
	}
	public String getRegistercode() {
		return registercode;
	}
	public void setRegistercode(String registercode) {
		this.registercode = registercode;
	}
	public String getCodename() {
		return codename;
	}
	public void setCodename(String codename) {
		this.codename = codename;
	}
	public String getManagername() {
		return managername;
	}
	public void setManagername(String managername) {
		this.managername = managername;
	}
	public String getManageplace() {
		return manageplace;
	}
	public void setManageplace(String manageplace) {
		this.manageplace = manageplace;
	}
	public String getManageextent() {
		return manageextent;
	}
	public void setManageextent(String manageextent) {
		this.manageextent = manageextent;
	}
	public String getFounddate() {
		return founddate;
	}
	public void setFounddate(String founddate) {
		this.founddate = founddate;
	}
	public String getAnnucheck() {
		return annucheck;
	}
	public void setAnnucheck(String annucheck) {
		this.annucheck = annucheck;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getSchooladdr() {
		return schooladdr;
	}
	public void setSchooladdr(String schooladdr) {
		this.schooladdr = schooladdr;
	}
	public String getSchoolattrib() {
		return schoolattrib;
	}
	public void setSchoolattrib(String schoolattrib) {
		this.schoolattrib = schoolattrib;
	}
	public String getSchooltel() {
		return schooltel;
	}
	public void setSchooltel(String schooltel) {
		this.schooltel = schooltel;
	}
	public String getMarrige() {
		return marrige;
	}
	public void setMarrige(String marrige) {
		this.marrige = marrige;
	}
	public String getIsreferagricultural() {
		return isreferagricultural;
	}
	public void setIsreferagricultural(String isreferagricultural) {
		this.isreferagricultural = isreferagricultural;
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
	public String getContactmobiletel() {
		return contactmobiletel;
	}
	public void setContactmobiletel(String contactmobiletel) {
		this.contactmobiletel = contactmobiletel;
	}
	public String getContacttel() {
		return contacttel;
	}
	public void setContacttel(String contacttel) {
		this.contacttel = contacttel;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getMessageaddr() {
		return messageaddr;
	}
	public void setMessageaddr(String messageaddr) {
		this.messageaddr = messageaddr;
	}
	public String getCommpostcode() {
		return commpostcode;
	}
	public void setCommpostcode(String commpostcode) {
		this.commpostcode = commpostcode;
	}
	public String getEducationlevel() {
		return educationlevel;
	}
	public void setEducationlevel(String educationlevel) {
		this.educationlevel = educationlevel;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getReside() {
		return reside;
	}
	public void setReside(String reside) {
		this.reside = reside;
	}
	public String getLevelcal() {
		return levelcal;
	}
	public void setLevelcal(String levelcal) {
		this.levelcal = levelcal;
	}
	public String getJoinring() {
		return joinring;
	}
	public void setJoinring(String joinring) {
		this.joinring = joinring;
	}
	public BigDecimal getFamasset() {
		return famasset;
	}
	public void setFamasset(BigDecimal famasset) {
		this.famasset = famasset;
	}
	public BigDecimal getFamdebt() {
		return famdebt;
	}
	public void setFamdebt(BigDecimal famdebt) {
		this.famdebt = famdebt;
	}
	public BigDecimal getFamannuincome() {
		return famannuincome;
	}
	public void setFamannuincome(BigDecimal famannuincome) {
		this.famannuincome = famannuincome;
	}
	public BigDecimal getFamannupayout() {
		return famannupayout;
	}
	public void setFamannupayout(BigDecimal famannupayout) {
		this.famannupayout = famannupayout;
	}
	public String getRelaman() {
		return relaman;
	}
	public void setRelaman(String relaman) {
		this.relaman = relaman;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIncomeaccount() {
		return incomeaccount;
	}
	public void setIncomeaccount(String incomeaccount) {
		this.incomeaccount = incomeaccount;
	}
	public String getIncomebank() {
		return incomebank;
	}
	public void setIncomebank(String incomebank) {
		this.incomebank = incomebank;
	}
	public String getBusimanager() {
		return busimanager;
	}
	public void setBusimanager(String busimanager) {
		this.busimanager = busimanager;
	}
	public String getInstcode() {
		return instcode;
	}
	public void setInstcode(String instcode) {
		this.instcode = instcode;
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
	public String getIstmp() {
		return istmp;
	}
	public void setIstmp(String istmp) {
		this.istmp = istmp;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public String getDisposalstatus() {
		return disposalstatus;
	}
	public void setDisposalstatus(String disposalstatus) {
		this.disposalstatus = disposalstatus;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getInputman() {
		return inputman;
	}
	public void setInputman(String inputman) {
		this.inputman = inputman;
	}
	public String getInputdate() {
		return inputdate;
	}
	public void setInputdate(String inputdate) {
		this.inputdate = inputdate;
	}
	public String getGroupid() {
		return groupid;
	}
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getOperdatetime() {
		return operdatetime;
	}
	public void setOperdatetime(String operdatetime) {
		this.operdatetime = operdatetime;
	}
	public String getCuststatus() {
		return custstatus;
	}
	public void setCuststatus(String custstatus) {
		this.custstatus = custstatus;
	}
	public String getAssistbusimanage() {
		return assistbusimanage;
	}
	public void setAssistbusimanage(String assistbusimanage) {
		this.assistbusimanage = assistbusimanage;
	}
	public Integer getFamnum() {
		return famnum;
	}
	public void setFamnum(Integer famnum) {
		this.famnum = famnum;
	}
	public String getFirstmanager() {
		return firstmanager;
	}
	public void setFirstmanager(String firstmanager) {
		this.firstmanager = firstmanager;
	}
	public String getGovernmanager() {
		return governmanager;
	}
	public void setGovernmanager(String governmanager) {
		this.governmanager = governmanager;
	}
	public String getIstrans() {
		return istrans;
	}
	public void setIstrans(String istrans) {
		this.istrans = istrans;
	}
	public String getHukou() {
		return hukou;
	}
	public void setHukou(String hukou) {
		this.hukou = hukou;
	}
	public String getBackoperator() {
		return backoperator;
	}
	public void setBackoperator(String backoperator) {
		this.backoperator = backoperator;
	}
	public String getBusiflag() {
		return busiflag;
	}
	public void setBusiflag(String busiflag) {
		this.busiflag = busiflag;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	



}
