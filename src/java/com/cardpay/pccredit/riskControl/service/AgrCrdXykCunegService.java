package com.cardpay.pccredit.riskControl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.riskControl.dao.AgrCrdXykCunegDao;
import com.cardpay.pccredit.riskControl.filter.AgrCrdXykCunegFilter;
import com.cardpay.pccredit.riskControl.model.AgrCrdXykCuneg;
import com.wicresoft.jrad.base.database.model.QueryResult;

/**
 * 黑名单数据资料
 * @author chenzhifang
 *
 * 2014-12-22下午4:22:41
 */
@Service
public class AgrCrdXykCunegService {
	@Autowired
	private AgrCrdXykCunegDao agrCrdXykCunegDao;
	
	public QueryResult<AgrCrdXykCuneg> findAgrCrdXykCuneg(AgrCrdXykCunegFilter filter) {
		List<AgrCrdXykCuneg> datas = agrCrdXykCunegDao.findAgrCrdXykCunegsByFilter(filter);
		int size = agrCrdXykCunegDao.findAgrCrdXykCunegsCountByFilter(filter);
		QueryResult<AgrCrdXykCuneg> qs = new QueryResult<AgrCrdXykCuneg>(size, datas);
		return qs;
	}
	
}
