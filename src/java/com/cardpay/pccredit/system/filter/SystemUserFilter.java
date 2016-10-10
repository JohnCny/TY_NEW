package com.cardpay.pccredit.system.filter;

import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;

public class SystemUserFilter extends BaseQueryFilter{
	
	
	private String orgId;
	
	private String oname;

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOname() {
		return oname;
	}

	public void setOname(String oname) {
		this.oname = oname;
	}
	

}
