package com.cardpay.pccredit.system.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 
 * 描述 ：数据字典model
 * @author 张石树
 *
 * 2014-11-5 下午3:56:43
 */
@ModelParam(table="dict")
public class Dict extends BusinessModel{
	private String dictType;
	private String TypeCode;
	private String TypeName;
	private String bankCode;
	private String yqMoney;
	private String cardId;
	private String tel;
	private String productId;
	private String actual_quote;
    private String dkye;
    private String bnqx;
    private String bwqx;
	
	public String getDkye() {
		return dkye;
	}
	public void setDkye(String dkye) {
		this.dkye = dkye;
	}
	public String getBnqx() {
		return bnqx;
	}
	public void setBnqx(String bnqx) {
		this.bnqx = bnqx;
	}
	public String getBwqx() {
		return bwqx;
	}
	public void setBwqx(String bwqx) {
		this.bwqx = bwqx;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getActual_quote() {
		return actual_quote;
	}
	public void setActual_quote(String actual_quote) {
		this.actual_quote = actual_quote;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getYqMoney() {
		return yqMoney;
	}
	public void setYqMoney(String yqMoney) {
		this.yqMoney = yqMoney;
	}
	public String getTypeCode() {
		return TypeCode;
	}
	public void setTypeCode(String typeCode) {
		TypeCode = typeCode;
	}
	public String getTypeName() {
		return TypeName;
	}
	public void setTypeName(String typeName) {
		TypeName = typeName;
	}
	public String getDictType() {
		return dictType;
	}
	public void setDictType(String dictType) {
		this.dictType = dictType;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
}
