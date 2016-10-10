package com.cardpay.pbccrcReport.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.cardpay.pbccrcReport.PbccrcReport;
import com.cardpay.pccredit.customer.filter.CustomerInforFilter;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.wicresoft.jrad.base.database.model.QueryResult;

/**
 * 人行征信同步
 * @author chenzhifang
 *
 * 2014-12-23下午1:37:01
 */
public class SynPbccrcReportService {
	
	// 客户基本信息表
	@Autowired
	private CustomerInforService customerInforService;
	
	/**
	 * 同步人行征信报告
	 * @return
	 */
	public void synPbccrcReport(){
		CustomerInforFilter filter = new CustomerInforFilter();
		// 设置每次最大查询记录数
		filter.setLimit(50);
		// 查询页码
		filter.setPage(0);
		// 查询客户信息
		QueryResult<CustomerInfor> qs = customerInforService.findCustomerInforByFilter(filter);
		try{
			PbccrcReport pbccrcReport = new PbccrcReport();
			while(qs.getItems().size() != 0){
				for(CustomerInfor customerInfor : qs.getItems()){
					// 查询人行征信信息
					pbccrcReport.manuProcessPbocCreditInfo(customerInfor.getChineseName(), customerInfor.getCardType(), customerInfor.getCardId());
				}
				// 设置查询的页码
				filter.setPage(filter.getPage() + 1);
				qs = customerInforService.findCustomerInforByFilter(filter);
			}
		}catch(Exception e){
			throw new RuntimeException(e.getMessage());
		}
	}
	
}
