package com.cardpay.pccredit.riskControl.xml.singlerequest;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author chenzhifang
 *
 * 2014-10-30下午6:09:22
 */
public class SingleCheckRequestMessage0001 {
	@XStreamAlias("ID")
	private String id;
	
	@XStreamAlias("Name")
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
