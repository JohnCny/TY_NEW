package com.cardpay.pccredit.riskControl.xml.singlerequest;

import com.cardpay.pccredit.riskControl.xml.Head;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author chenzhifang
 *
 * 2014-10-31上午10:47:29
 */
@XStreamAlias("CFX")
public class SingleRequestCfx {
	@XStreamAlias("HEAD")
	private Head head = new Head();
	
	@XStreamAlias("MSG")
	private SingleRequestMsg msg = new SingleRequestMsg();
	
	public void initHead(){
		head.setVer("1.0");
		head.setSrc("202010000000");
		head.setDes("100000000000");
		head.setApp("联网核查公民身份信息系统");
		head.setMsgNo("0001");
		head.setMsgID("20051024092733000440");
		head.setMsgRef("20051024092733000440");
		head.setWorkDate("20011217");
		head.setReserve("String");
	}
	
	public Head getHead() {
		return head;
	}

	public void setHead(Head head) {
		this.head = head;
	}

	public SingleRequestMsg getMsg() {
		return msg;
	}

	public void setMsg(SingleRequestMsg msg) {
		this.msg = msg;
	}
}
