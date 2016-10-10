package com.cardpay.pccredit.manager.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cardpay.pccredit.manager.model.ManagerInformationClient;
import com.wicresoft.jrad.base.database.id.IDGenerator;
import com.wicresoft.jrad.base.web.form.BaseForm;

public class ManagerInformationClientForm extends BaseForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String messengerId;
	private String customerId;
	private String customerManagerId;
	public List<ManagerInformationClient> StringToList(){
		String cid = this.getCustomerId();
		List<ManagerInformationClient> list = new ArrayList<ManagerInformationClient>();
		if(cid!=null && !cid.equals("")){
			String[] customerArrIds = this.getCustomerId().split(",");
			List<String> customerIds = java.util.Arrays.asList(customerArrIds);
			for(String customerId : customerIds){
				ManagerInformationClient managerInformationClient = new ManagerInformationClient();
				managerInformationClient.setId(IDGenerator.generateID());
				managerInformationClient.setCustomerId(customerId);
				managerInformationClient.setCustomerManagerId(this.getCustomerManagerId());
				managerInformationClient.setMessengerId(this.getMessengerId());
				managerInformationClient.setCreatedBy(this.getCustomerManagerId());
				managerInformationClient.setCreatedTime(new Date());
				list.add(managerInformationClient);
			}
			return list;
		}else{
			ManagerInformationClient managerInformationClient = new ManagerInformationClient();
			managerInformationClient.setCustomerManagerId(this.getCustomerManagerId());
			managerInformationClient.setMessengerId(this.getMessengerId());
			list.add(managerInformationClient);
			return list;
		}		
	}
	public String getMessengerId() {
		return messengerId;
	}
	public void setMessengerId(String messengerId) {
		this.messengerId = messengerId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCustomerManagerId() {
		return customerManagerId;
	}
	public void setCustomerManagerId(String customerManagerId) {
		this.customerManagerId = customerManagerId;
	}
	
}
