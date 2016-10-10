package com.cardpay.pccredit.customer.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.id.IDType;
import com.wicresoft.jrad.base.database.model.BaseModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 客户维护信息
 * 
 * @author Evans zhang
 * 
 *         2014-10-13 下午2:37:07
 */
@ModelParam(table = "balance_sheet", generator = IDType.uuid32)
public class CustomerInforUpdateBalanceSheet extends BaseModel {
	private static final long serialVersionUID = 1L;
	private String customerId;
	private int loanType;
	private String names;
	private int no;
	private String contentsTextNumbers;
	private String createUser;
	private Date createdTime;
	private String modifyUser;
	private Date modifyDate;
	
	private String productId;
	

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public int getLoanType() {
		return loanType;
	}

	public void setLoanType(int loanType) {
		this.loanType = loanType;
	}

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getContentsTextNumbers() {
		return contentsTextNumbers;
	}

	public void setContentsTextNumbers(String contentsTextNumbers) {
		this.contentsTextNumbers = contentsTextNumbers;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

}
