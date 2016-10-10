package com.cardpay.pccredit.manager.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.manager.model.DownGradeRule;
import com.cardpay.pccredit.manager.service.ManagerDownRuleService;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.modules.dictionary.DictionaryManager;
import com.wicresoft.jrad.modules.dictionary.model.Dictionary;
import com.wicresoft.jrad.modules.dictionary.model.DictionaryItem;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;

@Controller
@RequestMapping("/manager/managerDownRule/*")
@JRadModule("manager.managerDownRule")
public class ManagerDownRuleController {
	
	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private ManagerDownRuleService managerDownRuleService;
	
	/**
	 * 客户经理降级规则显示
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "update.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browse(HttpServletRequest request) {
		List<DownGradeRule> downGradeRulelist = managerDownRuleService.getDownGradeRule();
		JRadModelAndView mv = new JRadModelAndView("/manager/managerdownrule/manager_down_update", request);
		DictionaryManager dictMgr = Beans.get(DictionaryManager.class);
		// 根据指定名得到字典值列表
		Dictionary dictionary = dictMgr.getDictionaryByName("CUSTOMERMANAGERLEVEL");
		List<DictionaryItem> dictItems = dictionary.getItems();
		List<DownGradeRule> downGradeRulel = new ArrayList<DownGradeRule>();
		if(dictItems.size() > 0){
		   for(int i=1;i<dictItems.size();i++){
			   DownGradeRule downGradeRule = new DownGradeRule();
			   downGradeRule.setCurrentLevel(dictItems.get(i).getName());
			   if(i == 1 || i == 2){
				   downGradeRule.setDowngradeLevel(dictItems.get(i-1).getName());
			   }else{
				   downGradeRule.setDowngradeLevel(dictItems.get(i-2).getName());
				  
				}
			   downGradeRulel.add(downGradeRule); 
		   }
		
		}
		mv.addObject("downGradeRulelist",downGradeRulelist);
		mv.addObject("downGradeRulel",downGradeRulel);
		return mv;
	}
	/**
	 * 客户经降级级规则保存
	 * 
	 * @param request
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "save.page", method = { RequestMethod.POST })
	@JRadOperation(JRadOperation.CHANGE)
	public AbstractModelAndView maintenanceAccountManagerchange(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/manager/managerdownrule/manager_down_update", request);
		try {
			managerDownRuleService.updateDownGradeRule(request);
			List<DownGradeRule> downGradeRulelist = managerDownRuleService.getDownGradeRule();
			mv.addObject("downGradeRulelist",downGradeRulelist);
		} catch (Exception e) {
            //TODO 有日志功能，在这一步应保持返回统一，出错以后查看日志
			logger.error("执行修改客户经理客户经降级级规则"+e.getMessage());
		}
		return mv;
	}

}
