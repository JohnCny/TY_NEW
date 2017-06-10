package com.cardpay.pccredit.report.dao;

import java.util.List;

import com.cardpay.pccredit.intopieces.model.CustomerApplicationInfo;
import com.cardpay.pccredit.postLoan.model.MibusidateView;
import com.cardpay.pccredit.report.filter.AccLoanCollectFilter;
import com.cardpay.pccredit.report.filter.CustomerMoveFilter;
import com.cardpay.pccredit.report.filter.ReportFilter;
import com.cardpay.pccredit.report.model.AccLoanCollectInfo;
import com.cardpay.pccredit.report.model.BjjdktjbbForm;
import com.cardpay.pccredit.report.model.CustomerMoveForm;
import com.cardpay.pccredit.report.model.DkyetjbbForm;
import com.cardpay.pccredit.report.model.DqzzdktjbbForm;
import com.cardpay.pccredit.report.model.TkmxLszBasic;
import com.cardpay.pccredit.report.model.XdlctjbbForm;
import com.cardpay.pccredit.report.model.YffdktjbbForm;
import com.cardpay.pccredit.report.model.YqdktjbbForm;
import com.cardpay.pccredit.report.model.YqhkdktjbbForm;
import com.wicresoft.util.annotation.Mapper;

@Mapper
public interface CustomerTransferFlowDao {
    
	List<CustomerMoveForm> findCustomerTransferList(CustomerMoveFilter filter);
	int findCustomerMoveCountList(CustomerMoveFilter filter);
	public List<CustomerMoveForm> getCustomerMovelList(CustomerMoveFilter filter);
	
	List<YffdktjbbForm> findYffdktjbbFormList(ReportFilter filter);
	int findYffdktjbbFormCountList(ReportFilter filter);
	public List<YffdktjbbForm> getYffdktjbbFormlList(ReportFilter filter);
	
	List<BjjdktjbbForm> findbjjdktjbbFormList(ReportFilter filter);
	int findbjjdktjbbFormCountList(ReportFilter filter);
	public List<BjjdktjbbForm> getbjjdktjbbFormList(ReportFilter filter);
	
	List<DqzzdktjbbForm> findDqzzdktjbbFormList(ReportFilter filter);
	int findDqzzdktjbbFormCountList(ReportFilter filter);
	public List<DqzzdktjbbForm> getDqzzdktjbbFormList(ReportFilter filter);
	
	List<DkyetjbbForm> findDkyetjbbFormList(ReportFilter filter);
	int findDkyetjbbFormCountList(ReportFilter filter);
	public List<DkyetjbbForm> getDkyetjbbFormList(ReportFilter filter);
	
	List<YqdktjbbForm> findYqdktjbbFormList(ReportFilter filter);
	int findYqdktjbbFormCountList(ReportFilter filter);
	public List<YqdktjbbForm> getYqdktjbbFormList(ReportFilter filter);
	
	List<YqhkdktjbbForm> findYqhkdktjbbFormList(ReportFilter filter);
	int findYqhkdktjbbFormCountList(ReportFilter filter);
	public List<TkmxLszBasic> getYqhkdktjbbFormList(ReportFilter filter);
	
	
	List<XdlctjbbForm> findXdlctjbbFormList(ReportFilter filter);
	int findXdlctjbbFormCountList(ReportFilter filter);
	public List<XdlctjbbForm> getXdlctjbbFormList(ReportFilter filter);
	
	public List<AccLoanCollectInfo> getAccLoanCollect(AccLoanCollectFilter filter);
	String finddqrq();
	List<MibusidateView> findrykapplicationinfobyuserid(ReportFilter filter);
	int findrykapplicationinfobyuseridsize(ReportFilter filter);
}