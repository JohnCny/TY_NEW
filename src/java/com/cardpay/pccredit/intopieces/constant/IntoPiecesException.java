package com.cardpay.pccredit.intopieces.constant;

public class IntoPiecesException extends Exception {

	/**
	 * 进件后台验证错误提示信息
	 */
	private static final long serialVersionUID = -5230597619161075240L;

	public IntoPiecesException() {
		super();
	}

	public IntoPiecesException(String msg) {
		super(msg);
	}

	public IntoPiecesException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public IntoPiecesException(Throwable cause) {
		super(cause);
	}

}
