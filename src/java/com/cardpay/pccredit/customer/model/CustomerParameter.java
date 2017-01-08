package com.cardpay.pccredit.customer.model;


import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

@ModelParam(table = "CUSTOMER_PARAMETER")
public class CustomerParameter extends BusinessModel {
	
	  private static final long serialVersionUID = 1L;
	  private String ywbh;//业务编号
	  private String customerParameterId;      
	  private String customerManagerId;//客户经理ID 
	  private String customername;//客户名称
	  private String managername;//客户经理名称
	  private String customerId;//客户ID     
	  private String idcard;//身份证号
	  private String productname;//产品名称
	  private String money;//金额
	  private String deadline;//期限
	  private String interstrate;//利率
	  private String loantype;//贷款类型
	  private String providedate;//发放日期
	  private String expiredate;//到期日期
	  private String bondsman;//担保人
	  private String classification;//行业分类
	  private String scopeoperation;//经营内容 
	  private String operationaddress;//经营地址
	  private String principal;//主调
	  private String assist;//辅调          
	  private String groupes;//组别         
	  private String members; //审贷会成员          
	  private String patternslend;//贷款方式
	  private String ratepaying;//是否纳税      
	  private String giveback;  //归还情况     
	  private String batchs;//是否批量           
	  private String phonenumber;//电话号码
	  private String enlending;//是否转贷
	  private String remark;//备注
	public String getCustomerParameterId() {
		return customerParameterId;
	}
	public void setCustomerParameterId(String customerParameterId) {
		this.customerParameterId = customerParameterId;
	}
	public String getCustomerManagerId() {
		return customerManagerId;
	}
	public void setCustomerManagerId(String customerManagerId) {
		this.customerManagerId = customerManagerId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public String getLoantype() {
		return loantype;
	}
	public void setLoantype(String loantype) {
		this.loantype = loantype;
	}
	public String getBondsman() {
		return bondsman;
	}
	public void setBondsman(String bondsman) {
		this.bondsman = bondsman;
	}
	public String getScopeoperation() {
		return scopeoperation;
	}
	public void setScopeoperation(String scopeoperation) {
		this.scopeoperation = scopeoperation;
	}
	public String getOperationaddress() {
		return operationaddress;
	}
	public void setOperationaddress(String operationaddress) {
		this.operationaddress = operationaddress;
	}
	public String getAssist() {
		return assist;
	}
	public void setAssist(String assist) {
		this.assist = assist;
	}
	public String getGroupes() {
		return groupes;
	}
	public void setGroupes(String groupes) {
		this.groupes = groupes;
	}
	public String getPatternslend() {
		return patternslend;
	}
	public void setPatternslend(String patternslend) {
		this.patternslend = patternslend;
	}
	public String getRatepaying() {
		return ratepaying;
	}
	public void setRatepaying(String ratepaying) {
		this.ratepaying = ratepaying;
	}
	public String getBatchs() {
		return batchs;
	}
	public void setBatchs(String batchs) {
		this.batchs = batchs;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public String getEnlending() {
		return enlending;
	}
	public void setEnlending(String enlending) {
		this.enlending = enlending;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	  public String getMembers() {
		return members;
	}
	public void setMembers(String members) {
		this.members = members;
	}
	 public String getCustomername() {
			return customername;
		}
		public void setCustomername(String customername) {
			this.customername = customername;
		}
		public String getManagername() {
			return managername;
		}
		public void setManagername(String managername) {
			this.managername = managername;
		}
		public String getIdcard() {
			return idcard;
		}
		public void setIdcard(String idcard) {
			this.idcard = idcard;
		}
		public String getMoney() {
			return money;
		}
		public void setMoney(String money) {
			this.money = money;
		}
		public String getDeadline() {
			return deadline;
		}
		public void setDeadline(String deadline) {
			this.deadline = deadline;
		}
		public String getInterstrate() {
			return interstrate;
		}
		public void setInterstrate(String interstrate) {
			this.interstrate = interstrate;
		}
	
		public String getProvidedate() {
			return providedate;
		}
		public void setProvidedate(String providedate) {
			this.providedate = providedate;
		}
		public String getExpiredate() {
			return expiredate;
		}
		public void setExpiredate(String expiredate) {
			this.expiredate = expiredate;
		}
		public String getClassification() {
			return classification;
		}
		public void setClassification(String classification) {
			this.classification = classification;
		}
		public String getPrincipal() {
			return principal;
		}
		public void setPrincipal(String principal) {
			this.principal = principal;
		}
		public String getGiveback() {
			return giveback;
		}
		public void setGiveback(String giveback) {
			this.giveback = giveback;
		}

		  public String getYwbh() {
			return ywbh;
		}
		public void setYwbh(String ywbh) {
			this.ywbh = ywbh;
		}
}
