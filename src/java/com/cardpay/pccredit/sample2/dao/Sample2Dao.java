/*
 * Copyright 2014 Wicresoft, Inc. All rights reserved.
 */

package com.cardpay.pccredit.sample2.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wicresoft.jrad.base.database.dao.query.QueryContext;
import com.wicresoft.jrad.modules.sample.model.Sample;
import com.wicresoft.util.annotation.Mapper;

/**
 * Description of Sample2Dao
 * 
 * @author Vincent
 * @created on Apr 18, 2014
 * 
 * @version $Id: Sample2Dao.java 1650 2014-10-09 14:55:25Z vincent $
 */
@Mapper
public interface Sample2Dao {
	
	public Sample findSampleById(@Param("id") String id);
	
	public List<Sample> findSamplesByFilter(QueryContext qc);
}
