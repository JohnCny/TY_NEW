package com.cardpay.pccredit.jnpad.model;

import java.util.Date;
import java.util.List;

/**
 * 客户进件信息
 * @author Administrator
 *
 */
public class RetrainingVo {
	
		private String id;
		// 培训目标
		private String trainingObjective;
		// 培训内容
		private String trainingContent;
		// 培训方式
		private String trainingMethod;
		// 培训地点
		private String trainingLocation;
		// 培训时间
		private Date trainingTime;
		// 培训实施人
		private String trainingPeople;
		// 是否废弃
		private String whetherAbandoned;
		
		private String createdBy;
		
		
		private List<String> userList;
		
		
		public String getCreatedBy() {
			return createdBy;
		}
		public void setCreatedBy(String createdBy) {
			this.createdBy = createdBy;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public List<String> getUserList() {
			return userList;
		}
		public void setUserList(List<String> userList) {
			this.userList = userList;
		}
		public String getTrainingObjective() {
			return trainingObjective;
		}
		public void setTrainingObjective(String trainingObjective) {
			this.trainingObjective = trainingObjective;
		}
		public String getTrainingContent() {
			return trainingContent;
		}
		public void setTrainingContent(String trainingContent) {
			this.trainingContent = trainingContent;
		}
		public String getTrainingMethod() {
			return trainingMethod;
		}
		public void setTrainingMethod(String trainingMethod) {
			this.trainingMethod = trainingMethod;
		}
		public String getTrainingLocation() {
			return trainingLocation;
		}
		public void setTrainingLocation(String trainingLocation) {
			this.trainingLocation = trainingLocation;
		}
		public Date getTrainingTime() {
			return trainingTime;
		}
		public void setTrainingTime(Date trainingTime) {
			this.trainingTime = trainingTime;
		}
		public String getTrainingPeople() {
			return trainingPeople;
		}
		public void setTrainingPeople(String trainingPeople) {
			this.trainingPeople = trainingPeople;
		}
		public String getWhetherAbandoned() {
			return whetherAbandoned;
		}
		public void setWhetherAbandoned(String whetherAbandoned) {
			this.whetherAbandoned = whetherAbandoned;
		}
		
		
   
}
