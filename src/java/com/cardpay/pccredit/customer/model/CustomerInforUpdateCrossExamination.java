package com.cardpay.pccredit.customer.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.model.BaseModel;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 客户维护信息
 * 
 * @author Evans zhang
 * 
 *         2014-10-13 下午2:37:07
 */
@ModelParam(table = "cross_examination")
public class CustomerInforUpdateCrossExamination extends BusinessModel {
	private static final long serialVersionUID = 1L;

	private String customerId;

	private Long loanType;

	private Long no;

	private String names;

	private String contentsTextNumbers;
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

	public Long getLoanType() {
		return loanType;
	}

	public void setLoanType(Long loanType) {
		this.loanType = loanType;
	}

	public Long getNo() {
		return no;
	}

	public void setNo(Long no) {
		this.no = no;
	}

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public String getContentsTextNumbers() {
		return contentsTextNumbers;
	}

	public void setContentsTextNumbers(String contentsTextNumbers) {
		this.contentsTextNumbers = contentsTextNumbers;
	}
}
