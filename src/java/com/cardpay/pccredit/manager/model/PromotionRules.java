/**
 * 
 */
package com.cardpay.pccredit.manager.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 描述 ：客户经理晋升规则
 * @author 张石树
 *
 * 2014-11-20 下午5:31:52
 */
@ModelParam(table="promotion_rules")
public class PromotionRules extends BusinessModel {

	private String initialLevel;
	
	private String promotionLevel;
	
	private String quarterAverageOverBalance;
	
	private String teamDailyOverdraftBalance;
	
	private String levelTeamMember;
	
	private Integer subMangerNumber;
	
	private Integer tubeNumber;
	
	private String activationRate;
	
	private String activeRate;

	public String getInitialLevel() {
		return initialLevel;
	}

	public void setInitialLevel(String initialLevel) {
		this.initialLevel = initialLevel;
	}

	public String getPromotionLevel() {
		return promotionLevel;
	}

	public void setPromotionLevel(String promotionLevel) {
		this.promotionLevel = promotionLevel;
	}

	public String getQuarterAverageOverBalance() {
		return quarterAverageOverBalance;
	}

	public void setQuarterAverageOverBalance(String quarterAverageOverBalance) {
		this.quarterAverageOverBalance = quarterAverageOverBalance;
	}

	public String getTeamDailyOverdraftBalance() {
		return teamDailyOverdraftBalance;
	}

	public void setTeamDailyOverdraftBalance(String teamDailyOverdraftBalance) {
		this.teamDailyOverdraftBalance = teamDailyOverdraftBalance;
	}

	public String getLevelTeamMember() {
		return levelTeamMember;
	}

	public void setLevelTeamMember(String levelTeamMember) {
		this.levelTeamMember = levelTeamMember;
	}

	public Integer getSubMangerNumber() {
		return subMangerNumber;
	}

	public void setSubMangerNumber(Integer subMangerNumber) {
		this.subMangerNumber = subMangerNumber;
	}

	public Integer getTubeNumber() {
		return tubeNumber;
	}

	public void setTubeNumber(Integer tubeNumber) {
		this.tubeNumber = tubeNumber;
	}

	public String getActivationRate() {
		return activationRate;
	}

	public void setActivationRate(String activationRate) {
		this.activationRate = activationRate;
	}

	public String getActiveRate() {
		return activeRate;
	}

	public void setActiveRate(String activeRate) {
		this.activeRate = activeRate;
	}

}
