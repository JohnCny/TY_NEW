package com.cardpay.pccredit.Sx.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.Sx.dao.SxDao;
import com.cardpay.pccredit.Sx.model.SxInputData;
import com.cardpay.pccredit.Sx.model.SxOutputData;
import com.cardpay.pccredit.customer.model.TyRepaySxForm;
import com.cardpay.pccredit.customerappguarantor.model.CustomerApplicationGuarantor;
import com.cardpay.pccredit.postLoan.filter.SxFilter;
import com.wicresoft.jrad.base.database.model.QueryResult;
@Service
public class SxService {
@Autowired
private SxDao dao;
	
	public QueryResult<SxOutputData> findSxListByFilter(SxInputData filter) {
		
		List<SxOutputData> lists = dao.findSxListByFilter(filter);
		int size = dao.findSxListCountByFilter(filter);
		QueryResult<SxOutputData> qr = new QueryResult<SxOutputData>(size,lists);
		return qr;
	}

	public List<SxOutputData> findSxListByFilterNoPage(SxInputData filter) {
		List<SxOutputData> lists = dao.findSxListByFilterNoPage(filter);
		return lists;
	}

	public QueryResult<SxOutputData> findje(SxInputData filter) {
		// TODO Auto-generated method stub
		List<SxOutputData>lists= dao.findje(filter);
		int size = dao.findSxListCountByFilterje(filter);
		QueryResult<SxOutputData> qr = new QueryResult<SxOutputData>(size, lists);
		return qr;
	}
	
	public List<SxOutputData> findteam() {
		// TODO Auto-generated method stub
		return dao.findteam();
	}

	public List<SxOutputData> finduser() {
		// TODO Auto-generated method stub
		return dao.finduser();
	}
	//-------------------------
	public List<CustomerApplicationGuarantor> findguarantor(String infoid) {
		// TODO Auto-generated method stub
		return dao.findguarantor(infoid);
	}

	public void insertguarantor(CustomerApplicationGuarantor filter) {
		// TODO Auto-generated method stub
		 dao.insertguarantor(filter);
	}

	public int guarantorcount(String infoid) {
		// TODO Auto-generated method stub
		return dao.guarantorcount(infoid);
	}

	public List<CustomerApplicationGuarantor> findguarantorcustomer(
			String customerid) {
		// TODO Auto-generated method stub
		return dao.findguarantorcustomer(customerid);
	}
	
	
	

}
