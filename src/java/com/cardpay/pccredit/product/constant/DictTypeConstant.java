package com.cardpay.pccredit.product.constant;

import java.util.HashMap;
import java.util.Map;

import com.cardpay.pccredit.common.Dictionary;

public class DictTypeConstant {
	public static Map<String, Object> TypeMap=new HashMap<String, Object>();
	static{
		TypeMap.put(ProductFilterColumn.TITLE,Dictionary.titleList);
		TypeMap.put(ProductFilterColumn.POSITIO,Dictionary.positioList);
		TypeMap.put(ProductFilterColumn.DEGREE_EDUCATION,Dictionary.degreeeducationList);
		TypeMap.put(ProductFilterColumn.RESIDENTIAL_PROPERTIE,Dictionary.residentialPropertieList);
		TypeMap.put(ProductFilterColumn.UNIT_NATURE,Dictionary.unitPropertisList);
		TypeMap.put(ProductFilterColumn.INDUSTRY_TYPE,Dictionary.industryTypeList);
	}
}
