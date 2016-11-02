package com.cardpay.pccredit.jnpad.web;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.ipad.util.JsonDateValueProcessor;
import com.cardpay.pccredit.jnpad.service.JnpadRiskCustomerCollectionService;
import com.cardpay.pccredit.manager.constant.ManagerLevelAdjustmentConstant;
import com.cardpay.pccredit.notification.constant.NotificationConstant;
import com.cardpay.pccredit.notification.constant.NotificationEnum;
import com.cardpay.pccredit.notification.filter.NotificationMessageFilter;
import com.cardpay.pccredit.notification.model.NotificationMessage;
import com.cardpay.pccredit.notification.service.NotificationService;
import com.cardpay.pccredit.riskControl.constant.RiskControlRole;
import com.cardpay.pccredit.riskControl.constant.RiskCreateTypeEnum;
import com.cardpay.pccredit.riskControl.filter.RiskCustomerFilter;
import com.cardpay.pccredit.riskControl.model.RiskCustomer;
import com.cardpay.pccredit.riskControl.service.RiskCustomerService;
import com.cardpay.pccredit.riskControl.web.RiskCustomerForm;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.jrad.modules.privilege.model.User;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;
import com.wicresoft.util.web.RequestHelper;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
public class JnpadTrainMessageController {
	
	
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private JnpadRiskCustomerCollectionService jnpadRiskCustomerCollectionService;
	
	@Autowired
	private RiskCustomerService riskCustomerService;
	
/**
 * 培训通知
 * @param filter
 * @param request
 * @return
 */
	
	@ResponseBody
	@RequestMapping(value = "/ipad/NotifictionMessage/browse.json", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public String browse(@ModelAttribute NotificationMessageFilter filter, HttpServletRequest request) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		String userId= request.getParameter("userId");
		filter.setIsCheck(NotificationConstant.no_read);
		filter.setUserId(userId);
		filter.setNoticeType(NotificationEnum.peixun.toString());
		QueryResult<NotificationMessage> result = notificationService.findNotificationMessageByFilter(filter);
		map.put("result", result);
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
	
	
	/**
	 * 修改为已查看
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/NotifictionMessage/delete.json")
	@JRadOperation(JRadOperation.CHECKED)
	public String delete(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();

		try {
			String messageId = RequestHelper.getStringValue(request, "id");
			notificationService.deleteMessage(messageId);
            //TODO 保持统一返回
			returnMap.addGlobalMessage("已阅读");
			returnMap.put("mess","已阅读" );
		}
		catch (Exception e) {
            //TODO 有日志功能，在这一步应保持返回统一，出错以后查看日志
			returnMap.put("mess","修改错误" );
		}

		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(returnMap, jsonConfig);
		return json.toString();
	}
	
	/**
	 * 风险客户列表
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/NotifictionMessage/managerbrowse.json", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public String managerBrowse(@ModelAttribute RiskCustomerFilter filter,HttpServletRequest request) {
        filter.setRequest(request);
       String userId = request.getParameter("userId");
       Map<String, Object> map = new LinkedHashMap<String, Object>();
        // 客户经理Id
        //filter.setReportedIdManager(user.getId());
        filter.setCustManagerId(userId);
        // 风险类型
        filter.setRiskCreateType(RiskCreateTypeEnum.manual.toString());
        // 客户经理
        filter.setRole(RiskControlRole.manager.toString());
		List<RiskCustomer> result = jnpadRiskCustomerCollectionService.findRiskCustomersByFilter(filter);
		map.put("result", result);
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
	/**
	 * 出风险名单
	 * 对于风险已降低的客户可选择出风险名单操作
	 * @param riskCustomerForm
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/NotifictionMessage/removeRisk.json")
	public String removeRisk(@ModelAttribute RiskCustomerForm riskCustomerForm, HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			String riskCustomerId = RequestHelper.getStringValue(request, "customerId");
			//删除
			riskCustomerService.deleteRiskCustomer(riskCustomerId);
			returnMap.put("mess", "操作成功");
		} catch (Exception e) {
			returnMap.put("mess", "操作失败");			
		}

		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(returnMap, jsonConfig);
		return json.toString();
	}
	
}
