package com.cardpay.pccredit.system.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.system.filter.NodeAuditFilter;
import com.cardpay.pccredit.system.model.NodeAudit;
import com.cardpay.pccredit.system.model.NodeControl;
import com.cardpay.pccredit.system.service.NodeAuditService;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.constant.JRadConstants;
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

/**
 * 
 * 描述 ：流程节点配置controller
 * @author 张石树
 *
 * 2014-11-27 下午5:38:29
 */
@Controller
@RequestMapping("/system/nodeaudit/*")
public class NodeAuditController implements JRadConstants {

	@Autowired
	private NodeAuditService nodeAuditService;

	/**
	 * 配置产品流程节点信息
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "browse.page")
	public AbstractModelAndView productApproveConfigBrowse(@ModelAttribute NodeAuditFilter filter, HttpServletRequest request) {
		filter.setRequest(request);

		QueryResult<NodeAuditForm> result = nodeAuditService.findProductsNodeAuditByFilter(filter);
		JRadPagedQueryResult<NodeAuditForm> pagedResult = new JRadPagedQueryResult<NodeAuditForm>(filter, result);

		JRadModelAndView mv = new JRadModelAndView("/system/nodeaudit/nodeaudit_config_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);
		mv.addObject("productId", request.getParameter("productId"));
		mv.addObject("nodeType", request.getParameter("nodeType"));
		return mv;
	}
	
	/**
	 * 增加页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "create.page")
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView create(HttpServletRequest request) {

		JRadModelAndView mv = new JRadModelAndView("/system/nodeaudit/nodeaudit_config_create", request);
		
		mv.addObject("productId", request.getParameter("productId"));
		mv.addObject("nodeType", request.getParameter("nodeType"));
		
		return mv;
	}
	
	/**
	 * 修改页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "change.page")
	@JRadOperation(JRadOperation.CHANGE)
	public AbstractModelAndView change(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/system/nodeaudit/nodeaudit_config_update", request);

		String id = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(id)) {
			NodeAuditForm nodeAudit = nodeAuditService.findNodeAuditById(id);
			mv.addObject("nodeAudit", nodeAudit);
			mv.addObject("productId", request.getParameter("productId"));
			mv.addObject("nodeType", request.getParameter("nodeType"));
		}

		return mv;
	}
	
	/**
	 * 维护节点关系
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "nodecontrol.page")
	@JRadOperation(JRadOperation.CHANGE)
	public AbstractModelAndView nodeControl(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/system/nodeaudit/nodeaudit_control_config", request);
		String productId = request.getParameter("productId");
		String nodeType = request.getParameter("nodeType");
		List<NodeAudit> nodeAudits = nodeAuditService.findByNodeTypeAndProductId(nodeType, productId);
		
		List<NodeControl> nodeControls = nodeAuditService.findNodeControlByNodeTypeAndProductId(nodeType, productId);
		
		mv.addObject("nodeAudits", nodeAudits);
		mv.addObject("nodeControls", nodeControls);
		mv.addObject("productId", request.getParameter("productId"));
		mv.addObject("nodeType", request.getParameter("nodeType"));

		return mv;
	}

	/**
	 * 显示页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "display.page")
	@JRadOperation(JRadOperation.DISPLAY)
	public AbstractModelAndView display(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/system/nodeaudit/nodeaudit_config_display", request);

		String id = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(id)) {
			NodeAuditForm nodeAudit = nodeAuditService.findNodeAuditById(id);
			mv.addObject("nodeAudit", nodeAudit);
		}

		return mv;
	}

	/**
	 * 执行添加
	 * 
	 * @param sample2
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insert.json")
	@JRadOperation(JRadOperation.CREATE)
	public JRadReturnMap insert(@ModelAttribute NodeAuditForm nodeAudit, HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
			nodeAudit.setCreatedBy(user.getId());
			nodeAudit.setCreatedTime(new Date());
			nodeAudit.setModifiedBy(user.getId());
			nodeAudit.setModifiedTime(new Date());
			
			nodeAuditService.insertNodeAudit(nodeAudit);
			returnMap.put(RECORD_ID, nodeAudit.getId());
			returnMap.addGlobalMessage(CREATE_SUCCESS);
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}

		return returnMap;
	}

	/**
	 * 执行修改
	 * 
	 * @param sample2
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "update.json")
	@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap update(@ModelAttribute NodeAuditForm nodeAudit, HttpServletRequest request) {

		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
			nodeAudit.setModifiedBy(user.getId());
			nodeAudit.setModifiedTime(new Date());
			nodeAuditService.updateNodeAudit(nodeAudit);
			returnMap.put(RECORD_ID, nodeAudit.getId());
			returnMap.addGlobalMessage(CHANGE_SUCCESS);
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}

		return returnMap;
	}

	/**
	 * 执行删除
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "delete.json")
	@JRadOperation(JRadOperation.DELETE)
	public JRadReturnMap delete(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			String id = RequestHelper.getStringValue(request, ID);
			nodeAuditService.deleteNodeAuditById(id);
			returnMap.addGlobalMessage(DELETE_SUCCESS);
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}

		return returnMap;
	}
	
	/**
	 * 执行添加
	 * 
	 * @param sample2
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "saveNodeControl.json")
	@JRadOperation(JRadOperation.CREATE)
	public JRadReturnMap saveNodeControl(@ModelAttribute NodeAuditForm nodeAudit, HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			String productId = request.getParameter("productId");
			String nodeType = request.getParameter("nodeType");
			String[] currentNodes = request.getParameterValues("currentNode");
			String[] currentStatus = request.getParameterValues("currentStatus");
			String[] nextNodes = request.getParameterValues("nextNode");
			List<NodeControl> nodeControls = new ArrayList<NodeControl>();
			for(int i = 0; i < currentNodes.length; i++){
				NodeControl nodeControl = new NodeControl();
				nodeControl.setCurrentNode(currentNodes[i]);
				nodeControl.setCurrentStatus(currentStatus[i]);
				nodeControl.setNextNode(nextNodes[i]);
				nodeControls.add(nodeControl);
			}
			
			nodeAuditService.insertNodeControl(productId, nodeType, nodeControls);
			returnMap.put(RECORD_ID, nodeAudit.getId());
			returnMap.addGlobalMessage(CREATE_SUCCESS);
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}

		return returnMap;
	}
}
