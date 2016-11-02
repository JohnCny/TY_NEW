/**
 * 
 */
package com.cardpay.pccredit.intopieces.constant;

/**
 * 卡片状态
 * 
 * @author 徐超
 * 
 *         2014年11月28日 下午3:38:28
 */
public enum CardStatus {
	ORGANIZATION_ISSUED(1), ORGANIZATION_UNISSUED(0), CUSTOMER_ISSUED(1), CUSTOMER_UNISSUED(0);
	private int nCode;

	private CardStatus(int _nCode) {
		this.nCode = _nCode;
	}

	@Override
	public String toString() {
		return String.valueOf(this.nCode);
	}

}
