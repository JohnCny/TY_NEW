/*
 * Copyright 2014 Wicresoft, Inc. All rights reserved.
 */

package com.cardpay.pccredit.sample2.service;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.sample2.dao.Sample2Dao;
import com.cardpay.pccredit.sample2.filter.Sample2Filter;
import com.cardpay.pccredit.sample2.model.Sample2;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.model.QueryResult;

/**
 * Description of Sample2Service
 * 
 * @author Vincent
 * @created on May 5, 2014
 * 
 * @version $Id: Sample2Service.java 1650 2014-10-09 14:55:25Z vincent $
 */
@Service
public class Sample2Service {

	@Autowired
	private CommonDao commonDao;

	@Autowired
	private Sample2Dao sample2Dao;

	public String insertSample2(Sample2 sample2) {
		sample2.setCreatedTime(Calendar.getInstance().getTime());
		sample2.setIsDeleted(false);
		commonDao.insertObject(sample2);

		return sample2.getId();
	}

	public int updateSample2(Sample2 sample2) {
		sample2.setModifiedTime(Calendar.getInstance().getTime());

		return commonDao.updateObject(sample2);
	}

	public int deleteSample2(String sample2Id) {
		return commonDao.deleteObject(Sample2.class, sample2Id);
	}

	public Sample2 findSample2ById(String sample2Id) {
		return commonDao.findObjectById(Sample2.class, sample2Id);
	}

	public QueryResult<Sample2> findSample2sByFilter(Sample2Filter filter) {
		return commonDao.findObjectsByFilter(Sample2.class, filter);
	}

	public int batchDeleteSample2s(List<String> sample2Ids) {
		return commonDao.batchDeleteObjects(Sample2.class, sample2Ids);
	}
}
