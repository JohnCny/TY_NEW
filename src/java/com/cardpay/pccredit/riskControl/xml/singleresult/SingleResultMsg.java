package com.cardpay.pccredit.riskControl.xml.singleresult;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author chenzhifang
 *
 * 2014-10-31上午10:52:05
 */
@XStreamAlias("MSG")
public class SingleResultMsg {
	
	@XStreamAlias("SingleCheckResultHead0002")
	private SingleCheckResultHead0002 head;
	
	@XStreamAlias("SingleCheckResultMessage0002")
	private SingleCheckResultMessage0002 message;

	public SingleCheckResultHead0002 getHead() {
		return head;
	}

	public void setHead(SingleCheckResultHead0002 head) {
		this.head = head;
	}

	public SingleCheckResultMessage0002 getMessage() {
		return message;
	}

	public void setMessage(SingleCheckResultMessage0002 message) {
		this.message = message;
	}
}
