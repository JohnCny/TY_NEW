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

import com.cardpay.pccredit.customer.model.CustomerManagerTarget;
import com.cardpay.pccredit.manager.constant.ManagerTargetType;
import com.cardpay.pccredit.manager.service.AccountManagerParameterService;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.modules.dictionary.DictionaryManager;
import com.wicresoft.jrad.modules.dictionary.model.Dictionary;
import com.wicresoft.jrad.modules.dictionary.model.DictionaryItem;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;

@Controller
@RequestMapping("/manager/managerLevelTarget/*")
@JRadModule("manager.managerLevelTarget")
public class ManagerLevelTargetController {
	
	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private AccountManagerParameterService accountManagerParameterService;
	
	/**
	 * 客户经理层级业绩目标显示页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "browse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browse(HttpServletRequest request) {
		
		JRadModelAndView mv = new JRadModelAndView("/manager/managerleveltarget/manager_leveltarget_update", request);
		List<CustomerManagerTarget> customerManagerTargetList = accountManagerParameterService.getcustomerManagerTarget();
		
		List<CustomerManagerTarget> weeklist = new ArrayList<CustomerManagerTarget>();
		List<CustomerManagerTarget> monthlist = new ArrayList<CustomerManagerTarget>();
		List<CustomerManagerTarget> yearlist = new ArrayList<CustomerManagerTarget>();
		for(CustomerManagerTarget kv: customerManagerTargetList){
			String datestr = kv.getTargetDate();
			if(datestr.equalsIgnoreCase(ManagerTargetType.weekly.name())){
				weeklist.add(kv);
			}
			if(datestr.equalsIgnoreCase(ManagerTargetType.month.name())){
				monthlist.add(kv);
			}
			if(datestr.equalsIgnoreCase(ManagerTargetType.year.name())){
				yearlist.add(kv);
			}
			
		}
		
		DictionaryManager dictMgr = Beans.get(DictionaryManager.class);
		// 根据指定名得到字典值列表
		Dictionary dictionary = dictMgr.getDictionaryByName("CUSTOMERMANAGERLEVEL");
		List<DictionaryItem> dictItems = dictionary.getItems();
		mv.addObject("dictItems",dictItems);
		mv.addObject("weekly", weeklist);
		mv.addObject("month", monthlist);
		mv.addObject("year", yearlist);
		return mv;
	}
	/**
	 *  客户经理层级业绩目标修改
	 * 
	 * @param request
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "save.page", method = { RequestMethod.POST })
	@JRadOperation(JRadOperation.CHANGE)
	public AbstractModelAndView maintenanceAccountManagerchange(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/manager/managerleveltarget/manager_leveltarget_update", request);
		try {
			accountManagerParameterService.updatecustomerManagerTarget(request);
			List<CustomerManagerTarget> customerManagerTargetList = accountManagerParameterService.getcustomerManagerTarget();
			
			List<CustomerManagerTarget> weeklist = new ArrayList<CustomerManagerTarget>();
			List<CustomerManagerTarget> monthlist = new ArrayList<CustomerManagerTarget>();
			List<CustomerManagerTarget> yearlist = new ArrayList<CustomerManagerTarget>();
			for(CustomerManagerTarget kv: customerManagerTargetList){
				String datestr = kv.getTargetDate();
				if(datestr.equalsIgnoreCase(ManagerTargetType.weekly.name())){
					weeklist.add(kv);
				}
				if(datestr.equalsIgnoreCase(ManagerTargetType.month.name())){
					monthlist.add(kv);
				}
				if(datestr.equalsIgnoreCase(ManagerTargetType.year.name())){
					yearlist.add(kv);
				}
				
			}
			mv.addObject("weekly", weeklist);
			mv.addObject("month", monthlist);
			mv.addObject("year", yearlist);
			
		} catch (Exception e) {
            //TODO 有日志功能，在这一步应保持返回统一，出错以后查看日志
			logger.error("执行修改客户经理参数维护错误"+e.getMessage());
		}
		return mv;
	}

}
