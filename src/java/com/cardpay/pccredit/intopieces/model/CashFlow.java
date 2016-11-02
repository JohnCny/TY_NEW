package com.cardpay.pccredit.intopieces.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

@ModelParam(table = "cash_flow")
public class CashFlow extends BusinessModel {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8860955438817002631L;

	private Long loanApplyId;

    private String businessType;

    private String month;

    private String earlyCash;

    private String cashSale;

    private String accountsReceivableRecovery;

    private String customerPrepayment;

    private String totalCashInflow;

    private String buyRawMaterial;

    private String paymentAccountsPayable;

    private String purchasesAdvance;

    private String totalOperatingCashOutflow;

    private String wageLabor;

    private String tax;

    private String transportationCost;

    private String rent;

    private String maintenanceFee;

    private String waterPowerCost;

    private String advertisingCost;

    private String expense;

    private String fixedCost;

    private String fixedAssetInvestment;

    private String saleFixedAsset;

    private String totalInvestmentCashFlow;

    private String bankLoan;

    private String repaymentPrincipalNumber;

    private String totalCashFlowFinancing;

    private String householdExpenditure;

    private String privateFund;

    private String privateTotalCashFlow;

    private String cuativeCash;

    private String endingCash;

    public Long getLoanApplyId() {
        return loanApplyId;
    }

    public void setLoanApplyId(Long loanApplyId) {
        this.loanApplyId = loanApplyId;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType == null ? null : businessType.trim();
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month == null ? null : month.trim();
    }

    public String getEarlyCash() {
        return earlyCash;
    }

    public void setEarlyCash(String earlyCash) {
        this.earlyCash = earlyCash == null ? null : earlyCash.trim();
    }

    public String getCashSale() {
        return cashSale;
    }

    public void setCashSale(String cashSale) {
        this.cashSale = cashSale == null ? null : cashSale.trim();
    }

    public String getAccountsReceivableRecovery() {
        return accountsReceivableRecovery;
    }

    public void setAccountsReceivableRecovery(String accountsReceivableRecovery) {
        this.accountsReceivableRecovery = accountsReceivableRecovery == null ? null : accountsReceivableRecovery.trim();
    }

    public String getCustomerPrepayment() {
        return customerPrepayment;
    }

    public void setCustomerPrepayment(String customerPrepayment) {
        this.customerPrepayment = customerPrepayment == null ? null : customerPrepayment.trim();
    }

    public String getTotalCashInflow() {
        return totalCashInflow;
    }

    public void setTotalCashInflow(String totalCashInflow) {
        this.totalCashInflow = totalCashInflow == null ? null : totalCashInflow.trim();
    }

    public String getBuyRawMaterial() {
        return buyRawMaterial;
    }

    public void setBuyRawMaterial(String buyRawMaterial) {
        this.buyRawMaterial = buyRawMaterial == null ? null : buyRawMaterial.trim();
    }

    public String getPaymentAccountsPayable() {
        return paymentAccountsPayable;
    }

    public void setPaymentAccountsPayable(String paymentAccountsPayable) {
        this.paymentAccountsPayable = paymentAccountsPayable == null ? null : paymentAccountsPayable.trim();
    }

    public String getPurchasesAdvance() {
        return purchasesAdvance;
    }

    public void setPurchasesAdvance(String purchasesAdvance) {
        this.purchasesAdvance = purchasesAdvance == null ? null : purchasesAdvance.trim();
    }

    public String getTotalOperatingCashOutflow() {
        return totalOperatingCashOutflow;
    }

    public void setTotalOperatingCashOutflow(String totalOperatingCashOutflow) {
        this.totalOperatingCashOutflow = totalOperatingCashOutflow == null ? null : totalOperatingCashOutflow.trim();
    }

    public String getWageLabor() {
        return wageLabor;
    }

    public void setWageLabor(String wageLabor) {
        this.wageLabor = wageLabor == null ? null : wageLabor.trim();
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax == null ? null : tax.trim();
    }

    public String getTransportationCost() {
        return transportationCost;
    }

    public void setTransportationCost(String transportationCost) {
        this.transportationCost = transportationCost == null ? null : transportationCost.trim();
    }

    public String getRent() {
        return rent;
    }

    public void setRent(String rent) {
        this.rent = rent == null ? null : rent.trim();
    }

    public String getMaintenanceFee() {
        return maintenanceFee;
    }

    public void setMaintenanceFee(String maintenanceFee) {
        this.maintenanceFee = maintenanceFee == null ? null : maintenanceFee.trim();
    }

    public String getWaterPowerCost() {
        return waterPowerCost;
    }

    public void setWaterPowerCost(String waterPowerCost) {
        this.waterPowerCost = waterPowerCost == null ? null : waterPowerCost.trim();
    }

    public String getAdvertisingCost() {
        return advertisingCost;
    }

    public void setAdvertisingCost(String advertisingCost) {
        this.advertisingCost = advertisingCost == null ? null : advertisingCost.trim();
    }

    public String getExpense() {
        return expense;
    }

    public void setExpense(String expense) {
        this.expense = expense == null ? null : expense.trim();
    }

    public String getFixedCost() {
        return fixedCost;
    }

    public void setFixedCost(String fixedCost) {
        this.fixedCost = fixedCost == null ? null : fixedCost.trim();
    }

    public String getFixedAssetInvestment() {
        return fixedAssetInvestment;
    }

    public void setFixedAssetInvestment(String fixedAssetInvestment) {
        this.fixedAssetInvestment = fixedAssetInvestment == null ? null : fixedAssetInvestment.trim();
    }

    public String getSaleFixedAsset() {
        return saleFixedAsset;
    }

    public void setSaleFixedAsset(String saleFixedAsset) {
        this.saleFixedAsset = saleFixedAsset == null ? null : saleFixedAsset.trim();
    }

    public String getTotalInvestmentCashFlow() {
        return totalInvestmentCashFlow;
    }

    public void setTotalInvestmentCashFlow(String totalInvestmentCashFlow) {
        this.totalInvestmentCashFlow = totalInvestmentCashFlow == null ? null : totalInvestmentCashFlow.trim();
    }

    public String getBankLoan() {
        return bankLoan;
    }

    public void setBankLoan(String bankLoan) {
        this.bankLoan = bankLoan == null ? null : bankLoan.trim();
    }

    public String getRepaymentPrincipalNumber() {
        return repaymentPrincipalNumber;
    }

    public void setRepaymentPrincipalNumber(String repaymentPrincipalNumber) {
        this.repaymentPrincipalNumber = repaymentPrincipalNumber == null ? null : repaymentPrincipalNumber.trim();
    }

    public String getTotalCashFlowFinancing() {
        return totalCashFlowFinancing;
    }

    public void setTotalCashFlowFinancing(String totalCashFlowFinancing) {
        this.totalCashFlowFinancing = totalCashFlowFinancing == null ? null : totalCashFlowFinancing.trim();
    }

    public String getHouseholdExpenditure() {
        return householdExpenditure;
    }

    public void setHouseholdExpenditure(String householdExpenditure) {
        this.householdExpenditure = householdExpenditure == null ? null : householdExpenditure.trim();
    }

    public String getPrivateFund() {
        return privateFund;
    }

    public void setPrivateFund(String privateFund) {
        this.privateFund = privateFund == null ? null : privateFund.trim();
    }

    public String getPrivateTotalCashFlow() {
        return privateTotalCashFlow;
    }

    public void setPrivateTotalCashFlow(String privateTotalCashFlow) {
        this.privateTotalCashFlow = privateTotalCashFlow == null ? null : privateTotalCashFlow.trim();
    }

    public String getCuativeCash() {
        return cuativeCash;
    }

    public void setCuativeCash(String cuativeCash) {
        this.cuativeCash = cuativeCash == null ? null : cuativeCash.trim();
    }

    public String getEndingCash() {
        return endingCash;
    }

    public void setEndingCash(String endingCash) {
        this.endingCash = endingCash == null ? null : endingCash.trim();
    }
}