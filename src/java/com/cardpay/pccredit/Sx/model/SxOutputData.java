package com.cardpay.pccredit.Sx.model;

/**
 * 
 * @author 郑博
 * 客户经理收息（太原）
 *
 */
public class SxOutputData{
	private String userId;//客户经理id
	private String month;//月份
	private String Name;//小微团队
	private String displayName;//客户经理名称
	private String total;//收息额度
	private String countSx;//收息笔数
	private String totalSX;//收息总额度
	private String jkje;//贷款额度
	private String productName;//产品名称
	public SxOutputData(){}
	public SxOutputData(String userId, String month, String name,
			String displayName, String total, String countSx, String totalSX,
			String jkje, String productName) {
		this.userId = userId;
		this.month = month;
		Name = name;
		this.displayName = displayName;
		this.total = total;
		this.countSx = countSx;
		this.totalSX = totalSX;
		this.jkje = jkje;
		this.productName = productName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getCountSx() {
		return countSx;
	}
	public void setCountSx(String countSx) {
		this.countSx = countSx;
	}
	public String getTotalSX() {
		return totalSX;
	}
	public void setTotalSX(String totalSX) {
		this.totalSX = totalSX;
	}
	public String getJkje() {
		return jkje;
	}
	public void setJkje(String jkje) {
		this.jkje = jkje;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	
	
}
