package com.cardpay.pccredit.product.constant;

import java.util.ArrayList;
import java.util.List;

public class ProductOperateEnum {
	public static List<String> list=new ArrayList<String>();
	static{
		list.add(">");
		list.add(">=");
		list.add("<");
		list.add("<=");
	}
}
