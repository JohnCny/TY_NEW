package com.cardpay.pccredit.manager.model;

import com.wicresoft.jrad.base.database.id.IDType;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;
/**
 * 
 * @author 季东晓
 *
 * 2014-11-21 下午4:01:17
 */
@ModelParam(table = "promotion_rules",generator=IDType.uuid32)
public class ManagerPromotionRule extends BusinessModel{
	
	private static final long serialVersionUID = 1L;
	
	private String	initialLevel	;//	初始层级
	private String	quarterAverageOverBalance	;//	季度日均透支余额（万）
	private String	teamDailyOverdraftBalance	;//	团队日均透支余额（万）
	private String	levelTeamMember	;//	团队成员级别
	private Integer	subMangerNumber	;//	人数
	private Integer	tubeNumber	;//	管户数（户）
	private String	activationRate	;//	激活率
	private String	activeRate	;//	活跃率
	private String	promotionLevel	;//	晋升层级
	public String getInitialLevel() {
		return initialLevel;
	}
	public void setInitialLevel(String initialLevel) {
		this.initialLevel = initialLevel;
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
	public String getPromotionLevel() {
		return promotionLevel;
	}
	public void setPromotionLevel(String promotionLevel) {
		this.promotionLevel = promotionLevel;
	}
	public Integer getSubMangerNumber() {
		return subMangerNumber;
	}
	public void setSubMangerNumber(Integer subMangerNumber) {
		this.subMangerNumber = subMangerNumber;
	}


}
