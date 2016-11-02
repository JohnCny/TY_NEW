package com.cardpay.pccredit.product.web;

import com.wicresoft.jrad.base.web.form.BaseForm;

/**
 * 产品催收规则
 * 
 * @author 王海东
 * @created on 2014-11-10
 * 
 * @version $Id:$
 */

public class ProductCollectionRulesForm extends BaseForm {

	private static final long serialVersionUID = 1L;
	private String productId;
	private String collectionType; //催收类型 date or age
	public String getCollectionType() {
		return collectionType;
	}
	public void setCollectionType(String collectionType) {
		this.collectionType = collectionType;
	}
	private String aging1;
	private String money1;
	private String collectionTime1;
	private String collectionTime11;
	private String collectionWay1;
	private String collectionWay11;
	private String operation1;
	private String operation11;
	private String aging2;
	private String money2;
	private String collectionTime2;
	private String collectionTime22;
	private String collectionWay2;
	private String collectionWay22;
	private String operation2;
	private String operation22;
	private String aging3;
	private String money3;
	private String collectionTime3;
	private String collectionTime33;
	private String collectionWay3;
	private String collectionWay33;
	private String operation3;
	private String operation33;
	private String aging4;
	private String money4;
	private String collectionTime4;
	private String collectionTime44;
	private String collectionWay4;
	private String collectionWay44;
	private String operation4;
	private String operation44;
	private String aging5;
	private String money5;
	private String collectionTime5;
	private String collectionTime55;
	private String collectionWay5;
	private String collectionWay55;
	private String operation5;
	private String operation55;
	private String aging6;
	private String money6;
	private String collectionTime6;
	private String collectionTime66;
	private String collectionWay6;
	private String collectionWay66;
	private String operation6;
	private String operation66;
	private String aging7;
	private String money7;
	private String collectionTime7;
	private String collectionTime77;
	private String collectionWay7;
	private String collectionWay77;
	private String operation7;
	private String operation77;
	private String aging8;
	private String money8;
	private String collectionTime8;
	private String collectionTime88;
	private String collectionWay8;
	private String collectionWay88;
	private String operation8;
	private String operation88;
	private String aging9;
	private String money9;
	private String collectionTime9;
	private String collectionTime99;
	private String collectionWay9;
	private String collectionWay99;
	private String operation9;
	private String operation99;
	private String aging10;
	private String money10;
	private String collectionTime10;
	private String collectionTime110;
	private String collectionWay10;
	private String collectionWay110;
	private String operation10;
	private String operation110;
	private String aging13;
	private String money13;
	private String collectionTime13;
	private String collectionTime113;
	private String collectionWay13;
	private String collectionWay113;
	private String operation13;
	private String operation113;
	private String overdueDayEnd1;
	private String overdueDayStart1;
	private String money1111;
	private String operation1111;
	private String collectionTime1111;
	private String collectionWay1111;
	private String operation11111;
	private String collectionTime11111;
	private String collectionWay11111;
	private String overdueDayEnd2;
	private String overdueDayStart2;
	private String money1112;
	private String operation1112;
	private String collectionTime1112;
	private String collectionWay1112;
	private String operation11112;
	private String collectionTime11112;
	private String collectionWay11112;
	private String overdueDayEnd3;
	private String overdueDayStart3;
	private String money1113;
	private String operation1113;
	private String collectionTime1113;
	private String collectionWay1113;
	private String operation11113;
	private String collectionTime11113;
	private String collectionWay11113;
	private String overdueDayEnd4;
	private String overdueDayStart4;
	private String money1114;
	private String operation1114;
	private String collectionTime1114;
	private String collectionWay1114;
	private String operation11114;
	private String collectionTime11114;
	private String collectionWay11114;
	private String overdueDayEnd5;
	private String overdueDayStart5;
	private String money1115;
	private String operation1115;
	private String collectionTime1115;
	private String collectionWay1115;
	private String operation11115;
	private String collectionTime11115;
	private String collectionWay11115;
	private String overdueDayEnd6;
	private String overdueDayStart6;
	private String money1116;
	private String operation1116;
	private String collectionTime1116;
	private String collectionWay1116;
	private String operation11116;
	private String collectionTime11116;
	private String collectionWay11116;
	private String overdueDayEnd7;
	private String overdueDayStart7;
	private String money1117;
	private String operation1117;
	private String collectionTime1117;
	private String collectionWay1117;
	private String operation11117;
	private String collectionTime11117;
	private String collectionWay11117;
	private String overdueDayEnd8;
	private String overdueDayStart8;
	private String money1118;
	private String operation1118;
	private String collectionTime1118;
	private String collectionWay1118;
	private String operation11118;
	private String collectionTime11118;
	private String collectionWay11118;
	private String overdueDayEnd9;
	private String overdueDayStart9;
	private String money1119;
	private String operation1119;
	private String collectionTime1119;
	private String collectionWay1119;
	private String operation11119;
	private String collectionTime11119;
	private String collectionWay11119;
	private String overdueDayEnd10;
	private String overdueDayStart10;
	private String money11110;
	private String operation11110;
	private String collectionTime11110;
	private String collectionWay11110;
	private String operation111110;
	private String collectionTime111110;
	private String collectionWay111110;
	private String overdueDayEnd14;
	private String overdueDayStart14;
	private String money111114;
	private String operation111114;
	private String collectionTime111114;
	private String collectionWay111114;
	private String operation1111114;
	private String collectionTime1111114;
	private String collectionWay1111114;
	
	
	
	public String getMoney1111() {
		return money1111;
	}
	public void setMoney1111(String money1111) {
		this.money1111 = money1111;
	}
	public String getOperation1111() {
		return operation1111;
	}
	public void setOperation1111(String operation1111) {
		this.operation1111 = operation1111;
	}
	public String getCollectionTime1111() {
		return collectionTime1111;
	}
	public void setCollectionTime1111(String collectionTime1111) {
		this.collectionTime1111 = collectionTime1111;
	}
	public String getCollectionWay1111() {
		return collectionWay1111;
	}
	public void setCollectionWay1111(String collectionWay1111) {
		this.collectionWay1111 = collectionWay1111;
	}
	public String getOperation11111() {
		return operation11111;
	}
	public void setOperation11111(String operation11111) {
		this.operation11111 = operation11111;
	}
	public String getCollectionTime11111() {
		return collectionTime11111;
	}
	public void setCollectionTime11111(String collectionTime11111) {
		this.collectionTime11111 = collectionTime11111;
	}
	public String getCollectionWay11111() {
		return collectionWay11111;
	}
	public void setCollectionWay11111(String collectionWay11111) {
		this.collectionWay11111 = collectionWay11111;
	}
	public String getOverdueDayEnd2() {
		return overdueDayEnd2;
	}
	public void setOverdueDayEnd2(String overdueDayEnd2) {
		this.overdueDayEnd2 = overdueDayEnd2;
	}
	public String getOverdueDayStart2() {
		return overdueDayStart2;
	}
	public void setOverdueDayStart2(String overdueDayStart2) {
		this.overdueDayStart2 = overdueDayStart2;
	}
	public String getMoney1112() {
		return money1112;
	}
	public void setMoney1112(String money1112) {
		this.money1112 = money1112;
	}
	public String getOperation1112() {
		return operation1112;
	}
	public void setOperation1112(String operation1112) {
		this.operation1112 = operation1112;
	}
	public String getCollectionTime1112() {
		return collectionTime1112;
	}
	public void setCollectionTime1112(String collectionTime1112) {
		this.collectionTime1112 = collectionTime1112;
	}
	public String getCollectionWay1112() {
		return collectionWay1112;
	}
	public void setCollectionWay1112(String collectionWay1112) {
		this.collectionWay1112 = collectionWay1112;
	}
	public String getOperation11112() {
		return operation11112;
	}
	public void setOperation11112(String operation11112) {
		this.operation11112 = operation11112;
	}
	public String getCollectionTime11112() {
		return collectionTime11112;
	}
	public void setCollectionTime11112(String collectionTime11112) {
		this.collectionTime11112 = collectionTime11112;
	}
	public String getCollectionWay11112() {
		return collectionWay11112;
	}
	public void setCollectionWay11112(String collectionWay11112) {
		this.collectionWay11112 = collectionWay11112;
	}
	public String getOverdueDayEnd3() {
		return overdueDayEnd3;
	}
	public void setOverdueDayEnd3(String overdueDayEnd3) {
		this.overdueDayEnd3 = overdueDayEnd3;
	}
	public String getOverdueDayStart3() {
		return overdueDayStart3;
	}
	public void setOverdueDayStart3(String overdueDayStart3) {
		this.overdueDayStart3 = overdueDayStart3;
	}
	public String getMoney1113() {
		return money1113;
	}
	public void setMoney1113(String money1113) {
		this.money1113 = money1113;
	}
	public String getOperation1113() {
		return operation1113;
	}
	public void setOperation1113(String operation1113) {
		this.operation1113 = operation1113;
	}
	public String getCollectionTime1113() {
		return collectionTime1113;
	}
	public void setCollectionTime1113(String collectionTime1113) {
		this.collectionTime1113 = collectionTime1113;
	}
	public String getCollectionWay1113() {
		return collectionWay1113;
	}
	public void setCollectionWay1113(String collectionWay1113) {
		this.collectionWay1113 = collectionWay1113;
	}
	public String getOperation11113() {
		return operation11113;
	}
	public void setOperation11113(String operation11113) {
		this.operation11113 = operation11113;
	}
	public String getCollectionTime11113() {
		return collectionTime11113;
	}
	public void setCollectionTime11113(String collectionTime11113) {
		this.collectionTime11113 = collectionTime11113;
	}
	public String getCollectionWay11113() {
		return collectionWay11113;
	}
	public void setCollectionWay11113(String collectionWay11113) {
		this.collectionWay11113 = collectionWay11113;
	}
	public String getOverdueDayEnd4() {
		return overdueDayEnd4;
	}
	public void setOverdueDayEnd4(String overdueDayEnd4) {
		this.overdueDayEnd4 = overdueDayEnd4;
	}
	public String getOverdueDayStart4() {
		return overdueDayStart4;
	}
	public void setOverdueDayStart4(String overdueDayStart4) {
		this.overdueDayStart4 = overdueDayStart4;
	}
	public String getMoney1114() {
		return money1114;
	}
	public void setMoney1114(String money1114) {
		this.money1114 = money1114;
	}
	public String getOperation1114() {
		return operation1114;
	}
	public void setOperation1114(String operation1114) {
		this.operation1114 = operation1114;
	}
	public String getCollectionTime1114() {
		return collectionTime1114;
	}
	public void setCollectionTime1114(String collectionTime1114) {
		this.collectionTime1114 = collectionTime1114;
	}
	public String getCollectionWay1114() {
		return collectionWay1114;
	}
	public void setCollectionWay1114(String collectionWay1114) {
		this.collectionWay1114 = collectionWay1114;
	}
	public String getOperation11114() {
		return operation11114;
	}
	public void setOperation11114(String operation11114) {
		this.operation11114 = operation11114;
	}
	public String getCollectionTime11114() {
		return collectionTime11114;
	}
	public void setCollectionTime11114(String collectionTime11114) {
		this.collectionTime11114 = collectionTime11114;
	}
	public String getCollectionWay11114() {
		return collectionWay11114;
	}
	public void setCollectionWay11114(String collectionWay11114) {
		this.collectionWay11114 = collectionWay11114;
	}
	public String getOverdueDayEnd5() {
		return overdueDayEnd5;
	}
	public void setOverdueDayEnd5(String overdueDayEnd5) {
		this.overdueDayEnd5 = overdueDayEnd5;
	}
	public String getOverdueDayStart5() {
		return overdueDayStart5;
	}
	public void setOverdueDayStart5(String overdueDayStart5) {
		this.overdueDayStart5 = overdueDayStart5;
	}
	public String getMoney1115() {
		return money1115;
	}
	public void setMoney1115(String money1115) {
		this.money1115 = money1115;
	}
	public String getOperation1115() {
		return operation1115;
	}
	public void setOperation1115(String operation1115) {
		this.operation1115 = operation1115;
	}
	public String getCollectionTime1115() {
		return collectionTime1115;
	}
	public void setCollectionTime1115(String collectionTime1115) {
		this.collectionTime1115 = collectionTime1115;
	}
	public String getCollectionWay1115() {
		return collectionWay1115;
	}
	public void setCollectionWay1115(String collectionWay1115) {
		this.collectionWay1115 = collectionWay1115;
	}
	public String getOperation11115() {
		return operation11115;
	}
	public void setOperation11115(String operation11115) {
		this.operation11115 = operation11115;
	}
	public String getCollectionTime11115() {
		return collectionTime11115;
	}
	public void setCollectionTime11115(String collectionTime11115) {
		this.collectionTime11115 = collectionTime11115;
	}
	public String getCollectionWay11115() {
		return collectionWay11115;
	}
	public void setCollectionWay11115(String collectionWay11115) {
		this.collectionWay11115 = collectionWay11115;
	}
	public String getOverdueDayEnd6() {
		return overdueDayEnd6;
	}
	public void setOverdueDayEnd6(String overdueDayEnd6) {
		this.overdueDayEnd6 = overdueDayEnd6;
	}
	public String getOverdueDayStart6() {
		return overdueDayStart6;
	}
	public void setOverdueDayStart6(String overdueDayStart6) {
		this.overdueDayStart6 = overdueDayStart6;
	}
	public String getMoney1116() {
		return money1116;
	}
	public void setMoney1116(String money1116) {
		this.money1116 = money1116;
	}
	public String getOperation1116() {
		return operation1116;
	}
	public void setOperation1116(String operation1116) {
		this.operation1116 = operation1116;
	}
	public String getCollectionTime1116() {
		return collectionTime1116;
	}
	public void setCollectionTime1116(String collectionTime1116) {
		this.collectionTime1116 = collectionTime1116;
	}
	public String getCollectionWay1116() {
		return collectionWay1116;
	}
	public void setCollectionWay1116(String collectionWay1116) {
		this.collectionWay1116 = collectionWay1116;
	}
	public String getOperation11116() {
		return operation11116;
	}
	public void setOperation11116(String operation11116) {
		this.operation11116 = operation11116;
	}
	public String getCollectionTime11116() {
		return collectionTime11116;
	}
	public void setCollectionTime11116(String collectionTime11116) {
		this.collectionTime11116 = collectionTime11116;
	}
	public String getCollectionWay11116() {
		return collectionWay11116;
	}
	public void setCollectionWay11116(String collectionWay11116) {
		this.collectionWay11116 = collectionWay11116;
	}
	public String getOverdueDayEnd7() {
		return overdueDayEnd7;
	}
	public void setOverdueDayEnd7(String overdueDayEnd7) {
		this.overdueDayEnd7 = overdueDayEnd7;
	}
	public String getOverdueDayStart7() {
		return overdueDayStart7;
	}
	public void setOverdueDayStart7(String overdueDayStart7) {
		this.overdueDayStart7 = overdueDayStart7;
	}
	public String getMoney1117() {
		return money1117;
	}
	public void setMoney1117(String money1117) {
		this.money1117 = money1117;
	}
	public String getOperation1117() {
		return operation1117;
	}
	public void setOperation1117(String operation1117) {
		this.operation1117 = operation1117;
	}
	public String getCollectionTime1117() {
		return collectionTime1117;
	}
	public void setCollectionTime1117(String collectionTime1117) {
		this.collectionTime1117 = collectionTime1117;
	}
	public String getCollectionWay1117() {
		return collectionWay1117;
	}
	public void setCollectionWay1117(String collectionWay1117) {
		this.collectionWay1117 = collectionWay1117;
	}
	public String getOperation11117() {
		return operation11117;
	}
	public void setOperation11117(String operation11117) {
		this.operation11117 = operation11117;
	}
	public String getCollectionTime11117() {
		return collectionTime11117;
	}
	public void setCollectionTime11117(String collectionTime11117) {
		this.collectionTime11117 = collectionTime11117;
	}
	public String getCollectionWay11117() {
		return collectionWay11117;
	}
	public void setCollectionWay11117(String collectionWay11117) {
		this.collectionWay11117 = collectionWay11117;
	}
	public String getOverdueDayEnd8() {
		return overdueDayEnd8;
	}
	public void setOverdueDayEnd8(String overdueDayEnd8) {
		this.overdueDayEnd8 = overdueDayEnd8;
	}
	public String getOverdueDayStart8() {
		return overdueDayStart8;
	}
	public void setOverdueDayStart8(String overdueDayStart8) {
		this.overdueDayStart8 = overdueDayStart8;
	}
	public String getMoney1118() {
		return money1118;
	}
	public void setMoney1118(String money1118) {
		this.money1118 = money1118;
	}
	public String getOperation1118() {
		return operation1118;
	}
	public void setOperation1118(String operation1118) {
		this.operation1118 = operation1118;
	}
	public String getCollectionTime1118() {
		return collectionTime1118;
	}
	public void setCollectionTime1118(String collectionTime1118) {
		this.collectionTime1118 = collectionTime1118;
	}
	public String getCollectionWay1118() {
		return collectionWay1118;
	}
	public void setCollectionWay1118(String collectionWay1118) {
		this.collectionWay1118 = collectionWay1118;
	}
	public String getOperation11118() {
		return operation11118;
	}
	public void setOperation11118(String operation11118) {
		this.operation11118 = operation11118;
	}
	public String getCollectionTime11118() {
		return collectionTime11118;
	}
	public void setCollectionTime11118(String collectionTime11118) {
		this.collectionTime11118 = collectionTime11118;
	}
	public String getCollectionWay11118() {
		return collectionWay11118;
	}
	public void setCollectionWay11118(String collectionWay11118) {
		this.collectionWay11118 = collectionWay11118;
	}
	public String getOverdueDayEnd9() {
		return overdueDayEnd9;
	}
	public void setOverdueDayEnd9(String overdueDayEnd9) {
		this.overdueDayEnd9 = overdueDayEnd9;
	}
	public String getOverdueDayStart9() {
		return overdueDayStart9;
	}
	public void setOverdueDayStart9(String overdueDayStart9) {
		this.overdueDayStart9 = overdueDayStart9;
	}
	public String getMoney1119() {
		return money1119;
	}
	public void setMoney1119(String money1119) {
		this.money1119 = money1119;
	}
	public String getOperation1119() {
		return operation1119;
	}
	public void setOperation1119(String operation1119) {
		this.operation1119 = operation1119;
	}
	public String getCollectionTime1119() {
		return collectionTime1119;
	}
	public void setCollectionTime1119(String collectionTime1119) {
		this.collectionTime1119 = collectionTime1119;
	}
	public String getCollectionWay1119() {
		return collectionWay1119;
	}
	public void setCollectionWay1119(String collectionWay1119) {
		this.collectionWay1119 = collectionWay1119;
	}
	public String getOperation11119() {
		return operation11119;
	}
	public void setOperation11119(String operation11119) {
		this.operation11119 = operation11119;
	}
	public String getCollectionTime11119() {
		return collectionTime11119;
	}
	public void setCollectionTime11119(String collectionTime11119) {
		this.collectionTime11119 = collectionTime11119;
	}
	public String getCollectionWay11119() {
		return collectionWay11119;
	}
	public void setCollectionWay11119(String collectionWay11119) {
		this.collectionWay11119 = collectionWay11119;
	}
	public String getOverdueDayEnd10() {
		return overdueDayEnd10;
	}
	public void setOverdueDayEnd10(String overdueDayEnd10) {
		this.overdueDayEnd10 = overdueDayEnd10;
	}
	public String getOverdueDayStart10() {
		return overdueDayStart10;
	}
	public void setOverdueDayStart10(String overdueDayStart10) {
		this.overdueDayStart10 = overdueDayStart10;
	}
	public String getMoney11110() {
		return money11110;
	}
	public void setMoney11110(String money11110) {
		this.money11110 = money11110;
	}
	public String getOperation11110() {
		return operation11110;
	}
	public void setOperation11110(String operation11110) {
		this.operation11110 = operation11110;
	}
	public String getCollectionTime11110() {
		return collectionTime11110;
	}
	public void setCollectionTime11110(String collectionTime11110) {
		this.collectionTime11110 = collectionTime11110;
	}
	public String getCollectionWay11110() {
		return collectionWay11110;
	}
	public void setCollectionWay11110(String collectionWay11110) {
		this.collectionWay11110 = collectionWay11110;
	}
	public String getOperation111110() {
		return operation111110;
	}
	public void setOperation111110(String operation111110) {
		this.operation111110 = operation111110;
	}
	public String getCollectionTime111110() {
		return collectionTime111110;
	}
	public void setCollectionTime111110(String collectionTime111110) {
		this.collectionTime111110 = collectionTime111110;
	}
	public String getCollectionWay111110() {
		return collectionWay111110;
	}
	public void setCollectionWay111110(String collectionWay111110) {
		this.collectionWay111110 = collectionWay111110;
	}
	public String getOverdueDayEnd14() {
		return overdueDayEnd14;
	}
	public void setOverdueDayEnd14(String overdueDayEnd14) {
		this.overdueDayEnd14 = overdueDayEnd14;
	}
	public String getOverdueDayStart14() {
		return overdueDayStart14;
	}
	public void setOverdueDayStart14(String overdueDayStart14) {
		this.overdueDayStart14 = overdueDayStart14;
	}
	public String getMoney111114() {
		return money111114;
	}
	public void setMoney111114(String money111114) {
		this.money111114 = money111114;
	}
	public String getOperation111114() {
		return operation111114;
	}
	public void setOperation111114(String operation111114) {
		this.operation111114 = operation111114;
	}
	public String getCollectionTime111114() {
		return collectionTime111114;
	}
	public void setCollectionTime111114(String collectionTime111114) {
		this.collectionTime111114 = collectionTime111114;
	}
	public String getCollectionWay111114() {
		return collectionWay111114;
	}
	public void setCollectionWay111114(String collectionWay111114) {
		this.collectionWay111114 = collectionWay111114;
	}
	public String getOperation1111114() {
		return operation1111114;
	}
	public void setOperation1111114(String operation1111114) {
		this.operation1111114 = operation1111114;
	}
	public String getCollectionTime1111114() {
		return collectionTime1111114;
	}
	public void setCollectionTime1111114(String collectionTime1111114) {
		this.collectionTime1111114 = collectionTime1111114;
	}
	public String getCollectionWay1111114() {
		return collectionWay1111114;
	}
	public void setCollectionWay1111114(String collectionWay1111114) {
		this.collectionWay1111114 = collectionWay1111114;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getAging1() {
		return aging1;
	}
	public void setAging1(String aging1) {
		this.aging1 = aging1;
	}
	public String getMoney1() {
		return money1;
	}
	public void setMoney1(String money1) {
		this.money1 = money1;
	}
	public String getCollectionTime1() {
		return collectionTime1;
	}
	public void setCollectionTime1(String collectionTime1) {
		this.collectionTime1 = collectionTime1;
	}
	public String getCollectionTime11() {
		return collectionTime11;
	}
	public void setCollectionTime11(String collectionTime11) {
		this.collectionTime11 = collectionTime11;
	}
	public String getCollectionWay1() {
		return collectionWay1;
	}
	public void setCollectionWay1(String collectionWay1) {
		this.collectionWay1 = collectionWay1;
	}
	public String getCollectionWay11() {
		return collectionWay11;
	}
	public void setCollectionWay11(String collectionWay11) {
		this.collectionWay11 = collectionWay11;
	}
	public String getOperation1() {
		return operation1;
	}
	public void setOperation1(String operation1) {
		this.operation1 = operation1;
	}
	public String getOperation11() {
		return operation11;
	}
	public void setOperation11(String operation11) {
		this.operation11 = operation11;
	}
	public String getAging2() {
		return aging2;
	}
	public void setAging2(String aging2) {
		this.aging2 = aging2;
	}
	public String getMoney2() {
		return money2;
	}
	public void setMoney2(String money2) {
		this.money2 = money2;
	}
	public String getCollectionTime2() {
		return collectionTime2;
	}
	public void setCollectionTime2(String collectionTime2) {
		this.collectionTime2 = collectionTime2;
	}
	public String getCollectionTime22() {
		return collectionTime22;
	}
	public void setCollectionTime22(String collectionTime22) {
		this.collectionTime22 = collectionTime22;
	}
	public String getCollectionWay2() {
		return collectionWay2;
	}
	public void setCollectionWay2(String collectionWay2) {
		this.collectionWay2 = collectionWay2;
	}
	public String getCollectionWay22() {
		return collectionWay22;
	}
	public void setCollectionWay22(String collectionWay22) {
		this.collectionWay22 = collectionWay22;
	}
	public String getOperation2() {
		return operation2;
	}
	public void setOperation2(String operation2) {
		this.operation2 = operation2;
	}
	public String getOperation22() {
		return operation22;
	}
	public void setOperation22(String operation22) {
		this.operation22 = operation22;
	}
	public String getAging3() {
		return aging3;
	}
	public void setAging3(String aging3) {
		this.aging3 = aging3;
	}
	public String getMoney3() {
		return money3;
	}
	public void setMoney3(String money3) {
		this.money3 = money3;
	}
	public String getCollectionTime3() {
		return collectionTime3;
	}
	public void setCollectionTime3(String collectionTime3) {
		this.collectionTime3 = collectionTime3;
	}
	public String getCollectionTime33() {
		return collectionTime33;
	}
	public void setCollectionTime33(String collectionTime33) {
		this.collectionTime33 = collectionTime33;
	}
	public String getCollectionWay3() {
		return collectionWay3;
	}
	public void setCollectionWay3(String collectionWay3) {
		this.collectionWay3 = collectionWay3;
	}
	public String getCollectionWay33() {
		return collectionWay33;
	}
	public void setCollectionWay33(String collectionWay33) {
		this.collectionWay33 = collectionWay33;
	}
	public String getOperation3() {
		return operation3;
	}
	public void setOperation3(String operation3) {
		this.operation3 = operation3;
	}
	public String getOperation33() {
		return operation33;
	}
	public void setOperation33(String operation33) {
		this.operation33 = operation33;
	}
	public String getAging4() {
		return aging4;
	}
	public void setAging4(String aging4) {
		this.aging4 = aging4;
	}
	public String getMoney4() {
		return money4;
	}
	public void setMoney4(String money4) {
		this.money4 = money4;
	}
	public String getCollectionTime4() {
		return collectionTime4;
	}
	public void setCollectionTime4(String collectionTime4) {
		this.collectionTime4 = collectionTime4;
	}
	public String getCollectionTime44() {
		return collectionTime44;
	}
	public void setCollectionTime44(String collectionTime44) {
		this.collectionTime44 = collectionTime44;
	}
	public String getCollectionWay4() {
		return collectionWay4;
	}
	public void setCollectionWay4(String collectionWay4) {
		this.collectionWay4 = collectionWay4;
	}
	public String getCollectionWay44() {
		return collectionWay44;
	}
	public void setCollectionWay44(String collectionWay44) {
		this.collectionWay44 = collectionWay44;
	}
	public String getOperation4() {
		return operation4;
	}
	public void setOperation4(String operation4) {
		this.operation4 = operation4;
	}
	public String getOperation44() {
		return operation44;
	}
	public void setOperation44(String operation44) {
		this.operation44 = operation44;
	}
	public String getAging5() {
		return aging5;
	}
	public void setAging5(String aging5) {
		this.aging5 = aging5;
	}
	public String getMoney5() {
		return money5;
	}
	public void setMoney5(String money5) {
		this.money5 = money5;
	}
	public String getCollectionTime5() {
		return collectionTime5;
	}
	public void setCollectionTime5(String collectionTime5) {
		this.collectionTime5 = collectionTime5;
	}
	public String getCollectionTime55() {
		return collectionTime55;
	}
	public void setCollectionTime55(String collectionTime55) {
		this.collectionTime55 = collectionTime55;
	}
	public String getCollectionWay5() {
		return collectionWay5;
	}
	public void setCollectionWay5(String collectionWay5) {
		this.collectionWay5 = collectionWay5;
	}
	public String getCollectionWay55() {
		return collectionWay55;
	}
	public void setCollectionWay55(String collectionWay55) {
		this.collectionWay55 = collectionWay55;
	}
	public String getOperation5() {
		return operation5;
	}
	public void setOperation5(String operation5) {
		this.operation5 = operation5;
	}
	public String getOperation55() {
		return operation55;
	}
	public void setOperation55(String operation55) {
		this.operation55 = operation55;
	}
	public String getAging6() {
		return aging6;
	}
	public void setAging6(String aging6) {
		this.aging6 = aging6;
	}
	public String getMoney6() {
		return money6;
	}
	public void setMoney6(String money6) {
		this.money6 = money6;
	}
	public String getCollectionTime6() {
		return collectionTime6;
	}
	public void setCollectionTime6(String collectionTime6) {
		this.collectionTime6 = collectionTime6;
	}
	public String getCollectionTime66() {
		return collectionTime66;
	}
	public void setCollectionTime66(String collectionTime66) {
		this.collectionTime66 = collectionTime66;
	}
	public String getCollectionWay6() {
		return collectionWay6;
	}
	public void setCollectionWay6(String collectionWay6) {
		this.collectionWay6 = collectionWay6;
	}
	public String getCollectionWay66() {
		return collectionWay66;
	}
	public void setCollectionWay66(String collectionWay66) {
		this.collectionWay66 = collectionWay66;
	}
	public String getOperation6() {
		return operation6;
	}
	public void setOperation6(String operation6) {
		this.operation6 = operation6;
	}
	public String getOperation66() {
		return operation66;
	}
	public void setOperation66(String operation66) {
		this.operation66 = operation66;
	}
	public String getAging7() {
		return aging7;
	}
	public void setAging7(String aging7) {
		this.aging7 = aging7;
	}
	public String getMoney7() {
		return money7;
	}
	public void setMoney7(String money7) {
		this.money7 = money7;
	}
	public String getCollectionTime7() {
		return collectionTime7;
	}
	public void setCollectionTime7(String collectionTime7) {
		this.collectionTime7 = collectionTime7;
	}
	public String getCollectionTime77() {
		return collectionTime77;
	}
	public void setCollectionTime77(String collectionTime77) {
		this.collectionTime77 = collectionTime77;
	}
	public String getCollectionWay7() {
		return collectionWay7;
	}
	public void setCollectionWay7(String collectionWay7) {
		this.collectionWay7 = collectionWay7;
	}
	public String getCollectionWay77() {
		return collectionWay77;
	}
	public void setCollectionWay77(String collectionWay77) {
		this.collectionWay77 = collectionWay77;
	}
	public String getOperation7() {
		return operation7;
	}
	public void setOperation7(String operation7) {
		this.operation7 = operation7;
	}
	public String getOperation77() {
		return operation77;
	}
	public void setOperation77(String operation77) {
		this.operation77 = operation77;
	}
	public String getAging8() {
		return aging8;
	}
	public void setAging8(String aging8) {
		this.aging8 = aging8;
	}
	public String getMoney8() {
		return money8;
	}
	public void setMoney8(String money8) {
		this.money8 = money8;
	}
	public String getCollectionTime8() {
		return collectionTime8;
	}
	public void setCollectionTime8(String collectionTime8) {
		this.collectionTime8 = collectionTime8;
	}
	public String getCollectionTime88() {
		return collectionTime88;
	}
	public void setCollectionTime88(String collectionTime88) {
		this.collectionTime88 = collectionTime88;
	}
	public String getCollectionWay8() {
		return collectionWay8;
	}
	public void setCollectionWay8(String collectionWay8) {
		this.collectionWay8 = collectionWay8;
	}
	public String getCollectionWay88() {
		return collectionWay88;
	}
	public void setCollectionWay88(String collectionWay88) {
		this.collectionWay88 = collectionWay88;
	}
	public String getOperation8() {
		return operation8;
	}
	public void setOperation8(String operation8) {
		this.operation8 = operation8;
	}
	public String getOperation88() {
		return operation88;
	}
	public void setOperation88(String operation88) {
		this.operation88 = operation88;
	}
	public String getAging9() {
		return aging9;
	}
	public void setAging9(String aging9) {
		this.aging9 = aging9;
	}
	public String getMoney9() {
		return money9;
	}
	public void setMoney9(String money9) {
		this.money9 = money9;
	}
	public String getCollectionTime9() {
		return collectionTime9;
	}
	public void setCollectionTime9(String collectionTime9) {
		this.collectionTime9 = collectionTime9;
	}
	public String getCollectionTime99() {
		return collectionTime99;
	}
	public void setCollectionTime99(String collectionTime99) {
		this.collectionTime99 = collectionTime99;
	}
	public String getCollectionWay9() {
		return collectionWay9;
	}
	public void setCollectionWay9(String collectionWay9) {
		this.collectionWay9 = collectionWay9;
	}
	public String getCollectionWay99() {
		return collectionWay99;
	}
	public void setCollectionWay99(String collectionWay99) {
		this.collectionWay99 = collectionWay99;
	}
	public String getOperation9() {
		return operation9;
	}
	public void setOperation9(String operation9) {
		this.operation9 = operation9;
	}
	public String getOperation99() {
		return operation99;
	}
	public void setOperation99(String operation99) {
		this.operation99 = operation99;
	}
	public String getAging10() {
		return aging10;
	}
	public void setAging10(String aging10) {
		this.aging10 = aging10;
	}
	public String getMoney10() {
		return money10;
	}
	public void setMoney10(String money10) {
		this.money10 = money10;
	}
	public String getCollectionTime10() {
		return collectionTime10;
	}
	public void setCollectionTime10(String collectionTime10) {
		this.collectionTime10 = collectionTime10;
	}
	public String getCollectionTime110() {
		return collectionTime110;
	}
	public void setCollectionTime110(String collectionTime110) {
		this.collectionTime110 = collectionTime110;
	}
	public String getCollectionWay10() {
		return collectionWay10;
	}
	public void setCollectionWay10(String collectionWay10) {
		this.collectionWay10 = collectionWay10;
	}
	public String getCollectionWay110() {
		return collectionWay110;
	}
	public void setCollectionWay110(String collectionWay110) {
		this.collectionWay110 = collectionWay110;
	}
	public String getOperation10() {
		return operation10;
	}
	public void setOperation10(String operation10) {
		this.operation10 = operation10;
	}
	public String getOperation110() {
		return operation110;
	}
	public void setOperation110(String operation110) {
		this.operation110 = operation110;
	}
	public String getAging13() {
		return aging13;
	}
	public void setAging13(String aging13) {
		this.aging13 = aging13;
	}
	public String getMoney13() {
		return money13;
	}
	public void setMoney13(String money13) {
		this.money13 = money13;
	}
	public String getCollectionTime13() {
		return collectionTime13;
	}
	public void setCollectionTime13(String collectionTime13) {
		this.collectionTime13 = collectionTime13;
	}
	public String getCollectionTime113() {
		return collectionTime113;
	}
	public void setCollectionTime113(String collectionTime113) {
		this.collectionTime113 = collectionTime113;
	}
	public String getCollectionWay13() {
		return collectionWay13;
	}
	public void setCollectionWay13(String collectionWay13) {
		this.collectionWay13 = collectionWay13;
	}
	public String getCollectionWay113() {
		return collectionWay113;
	}
	public void setCollectionWay113(String collectionWay113) {
		this.collectionWay113 = collectionWay113;
	}
	public String getOperation13() {
		return operation13;
	}
	public void setOperation13(String operation13) {
		this.operation13 = operation13;
	}
	public String getOperation113() {
		return operation113;
	}
	public void setOperation113(String operation113) {
		this.operation113 = operation113;
	}
	public String getOverdueDayEnd1() {
		return overdueDayEnd1;
	}
	public void setOverdueDayEnd1(String overdueDayEnd1) {
		this.overdueDayEnd1 = overdueDayEnd1;
	}
	public String getOverdueDayStart1() {
		return overdueDayStart1;
	}
	public void setOverdueDayStart1(String overdueDayStart1) {
		this.overdueDayStart1 = overdueDayStart1;
	}
	
	

	

}
