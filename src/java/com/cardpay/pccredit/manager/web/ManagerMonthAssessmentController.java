package com.cardpay.pccredit.manager.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.manager.model.MangerMonthAssessment;
import com.cardpay.pccredit.manager.service.ManagerMonthAssessmentService;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.modules.dictionary.DictionaryManager;
import com.wicresoft.jrad.modules.dictionary.model.Dictionary;
import com.wicresoft.jrad.modules.dictionary.model.DictionaryItem;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;

@Controller
@RequestMapping("/manager/mangerMonthAssessment/*")
@JRadModule("manager.mangerMonthAssessment")
public class ManagerMonthAssessmentController {
	
	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private ManagerMonthAssessmentService managerMonthAssessmentService;
	
	/**
	 * 客户经理层级月度考核指标配置显示
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "update.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browse(HttpServletRequest request) {
		List<MangerMonthAssessment> mangerMonthAssessmentlist = managerMonthAssessmentService.getMangerMonthAssessment();
		JRadModelAndView mv = new JRadModelAndView("/manager/mangermonthassessment/manager_assessment_update", request);
		DictionaryManager dictMgr = Beans.get(DictionaryManager.class);
		// 根据指定名得到字典值列表
		Dictionary dictionary = dictMgr.getDictionaryByName("CUSTOMERMANAGERLEVEL");
		List<DictionaryItem> dictItems = dictionary.getItems();
		mv.addObject("mangerMonthAssessmentlist",mangerMonthAssessmentlist);
		mv.addObject("dictItems",dictItems);
		return mv;
	}
	/**
	 * 客户经理层级月度考核指标配置保存
	 * 
	 * @param request
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "save.page", method = { RequestMethod.POST })
	@JRadOperation(JRadOperation.CHANGE)
	public AbstractModelAndView maintenanceAccountManagerchange(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/manager/mangermonthassessment/manager_assessment_update", request);
		try {
			managerMonthAssessmentService.updateMangerMonthAssessment(request);
			List<MangerMonthAssessment> mangerMonthAssessmentlist = managerMonthAssessmentService.getMangerMonthAssessment();
			mv.addObject("mangerMonthAssessmentlist",mangerMonthAssessmentlist);
		} catch (Exception e) {
            //TODO 有日志功能，在这一步应保持返回统一，出错以后查看日志
			logger.error("执行修改客户经理层级月度考核指标配置错误"+e.getMessage());
		}
		return mv;
	}

}
