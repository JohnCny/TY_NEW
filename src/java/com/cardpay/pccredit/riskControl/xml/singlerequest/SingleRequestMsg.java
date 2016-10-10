package com.cardpay.pccredit.riskControl.xml.singlerequest;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author chenzhifang
 *
 * 2014-10-31上午10:52:05
 */
@XStreamAlias("MSG")
public class SingleRequestMsg {
	
	@XStreamAlias("SingleCheckBusinessHead0001")
	private SingleCheckBusinessHead0001 head = new SingleCheckBusinessHead0001();
	
	@XStreamAlias("SingleCheckRequestMessage0001")
	private SingleCheckRequestMessage0001 Message = new SingleCheckRequestMessage0001();

	public SingleCheckBusinessHead0001 getHead() {
		return head;
	}

	public void setHead(SingleCheckBusinessHead0001 head) {
		this.head = head;
	}

	public SingleCheckRequestMessage0001 getMessage() {
		return Message;
	}

	public void setMessage(SingleCheckRequestMessage0001 message) {
		Message = message;
	}
}
