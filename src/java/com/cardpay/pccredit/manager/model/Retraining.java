package com.cardpay.pccredit.manager.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 再培训计划表
 * @author chenzhifang
 *
 * 2014-11-12上午10:35:48
 */
@ModelParam(table = "retraining")
public class Retraining extends BusinessModel {
	private static final long serialVersionUID = 4824417908234645018L;
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
