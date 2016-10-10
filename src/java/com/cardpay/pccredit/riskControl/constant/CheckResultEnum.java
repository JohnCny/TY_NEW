package com.cardpay.pccredit.riskControl.constant;

/**
 * 公民身份核对结果
 * 
 * @author chenzhifang
 *
 * 2014-10-31上午11:42:44
 */
public enum CheckResultEnum {
	
	Conformity("00", "公民身份号码与姓名一致，且存在照片"),
	ConformityNoPhoto("01", "公民身份号码与姓名一致，但不存在照片"),
	IdExistNameNotMatch("02", "公民身份号码存在，但与姓名不匹配"),
	IdNonexistence("03", "公民身份号码不存在"),
	OtherError("04", "其他错误"),
	ParameterError("05", "输入的参数错误");
	
	CheckResultEnum(String code, String msg){
		this.code = code;
		this.msg = msg;
	}
	
	private String msg;
	
	private String code;
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/*
	 * 通过回执结果代码得到枚举类
	 * @param code 
	 * @return CheckResultEnum
	 */
	public static CheckResultEnum getCheckResultEnumByCode(String code){
		CheckResultEnum resultEnum = null;
		if(Conformity.getCode().equals(code)){
			resultEnum = Conformity;
		}else if(ConformityNoPhoto.getCode().equals(code)){
			resultEnum = ConformityNoPhoto;
		}else if(IdExistNameNotMatch.getCode().equals(code)){
			resultEnum = IdExistNameNotMatch;
		}else if(IdNonexistence.getCode().equals(code)){
			resultEnum = IdNonexistence;
		}else if(OtherError.getCode().equals(code)){
			resultEnum = OtherError;
		}else{
			resultEnum = ParameterError;
		}
		return resultEnum;
	}
}
