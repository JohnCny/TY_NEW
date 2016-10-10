package com.cardpay.pccredit.intopieces.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

@ModelParam(table = "cross_examination")
public class CrossExamination extends BusinessModel {
    /**
	 * 
	 */
	private static final long serialVersionUID = -9217479299174879428L;

    private Long loanApplyId;

    private Long category;

    private Long incrementFrom0;

    private String name;

    private String purchasePrice;

    private String price;

    private String shares;

    private String grossProfit;

    public Long getLoanApplyId() {
        return loanApplyId;
    }

    public void setLoanApplyId(Long loanApplyId) {
        this.loanApplyId = loanApplyId;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public Long getIncrementFrom0() {
        return incrementFrom0;
    }

    public void setIncrementFrom0(Long incrementFrom0) {
        this.incrementFrom0 = incrementFrom0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(String purchasePrice) {
        this.purchasePrice = purchasePrice == null ? null : purchasePrice.trim();
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price == null ? null : price.trim();
    }

    public String getShares() {
        return shares;
    }

    public void setShares(String shares) {
        this.shares = shares == null ? null : shares.trim();
    }

    public String getGrossProfit() {
        return grossProfit;
    }

    public void setGrossProfit(String grossProfit) {
        this.grossProfit = grossProfit == null ? null : grossProfit.trim();
    }
}