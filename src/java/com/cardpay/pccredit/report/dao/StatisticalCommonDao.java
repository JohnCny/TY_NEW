package com.cardpay.pccredit.report.dao;

import java.util.List;

import com.cardpay.pccredit.report.model.NameValueRecord;
import com.wicresoft.util.annotation.Mapper;

/**
 * @author chenzhifang
 *
 * 2014-12-18下午3:47:49
 */
@Mapper
public interface StatisticalCommonDao {

	/**
     * 统计当前进件状况
     * @return
     */
	public List<NameValueRecord> statisticalApplicationStatus();
	/**
     * pad统计当前进件状况
     * @return
     */
	public List<NameValueRecord> statisticalApplicationStatus1();
	/**
     * 统计当前贷款状况
     * @return
     */
	public List<NameValueRecord> statisticalCreditStatus();
	
	/**
     * 统计当前卡片状况
     * @return
     */
	public List<NameValueRecord> statisticalCardStatus();
	
	
	//统计各行 已申请进件数量
	public List<NameValueRecord> statisticalAuditStatus();
	
	
	//统计各行 通过进件数量
	public List<NameValueRecord> statisticalApprovedStatus();
	
	//统计放款总金额  逾期总金额  不良总金额
	public List<NameValueRecord> statisticaljine();
	//统计 各支行放款总金额
	public List<NameValueRecord> statisticalsxorgan();
	
	//pad统计 各支行放款总金额
	public List<NameValueRecord> statisticalsxorgan1();
	//统计 各支行逾期总金额
	public List<NameValueRecord> statisticalyqorgan();
	//pad统计 各支行逾期总金额
		public List<NameValueRecord> statisticalyqorgan1();
	//统计 各支行不良总金额
	public List<NameValueRecord> statisticalblorgan();
	//pad统计 各支行不良总金额
		public List<NameValueRecord> statisticalblorgan1();
	
	//pad统计各行 已申请进件数量
	public List<NameValueRecord> statisticalAuditStatus1();
	//pad统计各行 通过进件数量
	public List<NameValueRecord> statisticalApprovedStatus1();
	
	//计算用信总额selectALLyqze
	public int selectALLyxze();
	//计算逾期总额
	public String selectALLyqze();
}
