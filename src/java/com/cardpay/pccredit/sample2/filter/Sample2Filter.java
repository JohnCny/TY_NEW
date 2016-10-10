/*
 * Copyright 2014 Wicresoft, Inc. All rights reserved.
 */

package com.cardpay.pccredit.sample2.filter;

import java.util.Date;

import com.wicresoft.jrad.base.database.dao.business.BusinessFilter;
import com.wicresoft.jrad.base.database.dao.query.Operator;
import com.wicresoft.jrad.base.database.dao.query.OrderBy;
import com.wicresoft.jrad.base.database.dao.query.QueryParam;

/**
 * Description of SampleFilter
 * 
 * @author Vincent
 * @created on May 5, 2014
 * 
 * @version $Id: Sample2Filter.java 1650 2014-10-09 14:55:25Z vincent $
 */
public class Sample2Filter extends BusinessFilter {

	private String seqNo;

	private String name;

	private String type;

	private String nativeName;

	@QueryParam(operator = Operator.moreThanEquals, column = "sample_date")
	private Date sampleDateStart;

	@QueryParam(operator = Operator.lessThanEquals, column = "sample_date")
	private Date sampleDateEnd;

	@Override
	public OrderBy getDefaultOrderBy() {
		return OrderBy.desc("createdTime");
	}

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNativeName() {
		return nativeName;
	}

	public void setNativeName(String nativeName) {
		this.nativeName = nativeName;
	}

	public Date getSampleDateStart() {
		return sampleDateStart;
	}

	public void setSampleDateStart(Date sampleDateStart) {
		this.sampleDateStart = sampleDateStart;
	}

	public Date getSampleDateEnd() {
		return sampleDateEnd;
	}

	public void setSampleDateEnd(Date sampleDateEnd) {
		this.sampleDateEnd = sampleDateEnd;
	}

}
