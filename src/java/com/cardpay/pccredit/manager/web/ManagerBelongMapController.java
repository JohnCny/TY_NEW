package com.cardpay.pccredit.manager.web;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.axis.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.manager.constant.ManagerBelongMapConstants;
import com.cardpay.pccredit.manager.dao.ManagerBelongMapDao;
import com.cardpay.pccredit.manager.filter.ManagerBelongMapFilter;
import com.cardpay.pccredit.manager.model.ManagerBelongMap;
import com.cardpay.pccredit.manager.service.ManagerBelongMapService;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.constant.JRadConstants;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;
import com.wicresoft.util.web.RequestHelper;
/**
 * 
 * 描述 ：客户经理从属controller
 * @author 张石树
 *
 * 2014-11-10 下午3:32:01
 */
@Controller
@RequestMapping("/manager/belongmap/*")
@JRadModule("manager.belongmap")
public class ManagerBelongMapController extends BaseController{
	
	@Autowired
	private ManagerBelongMapService managerBelongMapService;
	
	@Autowired
	private ManagerBelongMapDao managerBelongMapDao;
	
	/**
	 * 客户经理从属tree
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "browse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView query(HttpServletRequest request) {
		String managerBelongMapJson = managerBelongMapService.getManagerBelongTree();
		JRadModelAndView mv = new JRadModelAndView("/manager/belong_map/manager_belong_tree", request);
		mv.addObject("children", managerBelongMapJson);
		return mv;
	}
	
	/**
	 *  根据parentId获取子tree 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "loadPartManagerBelongTree.page", method = { RequestMethod.POST })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView loadPartOrganizationTree(HttpServletRequest request, 
			HttpServletResponse response) throws IOException {
		String parentId = request.getParameter("parentId");
		String managerBelongMapJson = managerBelongMapService.getManagerBelongTreeById(parentId);
		response.getWriter().print(managerBelongMapJson);
		return null;
	}

	/**
	 * 根据parentId获取下属的客户经理从属信息
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "displayManagerBelong.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView displayOrganization(@ModelAttribute ManagerBelongMapFilter filter,
			HttpServletRequest request) {
		filter.setRequest(request);
		if(StringUtils.isEmpty(filter.getParentId())){
			filter.setUserName("Root");//不进行过滤用 只显示
			filter.setParentId(ManagerBelongMapConstants.INIT_ID);
		}
		QueryResult<ManagerBelongMapForm> result = managerBelongMapService.findManagerBelongMapByFilter(filter);
		JRadPagedQueryResult<ManagerBelongMapForm> pagedResult = new JRadPagedQueryResult<ManagerBelongMapForm>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/manager/belong_map/manager_belong_list", request);
		mv.addObject(PAGED_RESULT, pagedResult);
		mv.addObject("organization", filter);
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
	public AbstractModelAndView create(@ModelAttribute ManagerBelongMapFilter filter, HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/manager/belong_map/manager_belong_create", request);
		mv.addObject("organization", filter);
		return mv;
	}
	
	/**
	 * 执行添加
	 * 
	 * @param sample
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insert.json")
	@JRadOperation(JRadOperation.CREATE)
	public JRadReturnMap insert(@ModelAttribute ManagerBelongMapForm managerBelongMapForm, HttpServletRequest request) {
		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), managerBelongMapForm);
		if (returnMap.isSuccess()) {
			try {
				IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
 				
				ManagerBelongMap managerBelongMap = managerBelongMapForm.createModel(ManagerBelongMap.class);
 				managerBelongMap.setCreatedBy(user.getId());
 				managerBelongMap.setCreatedTime(new Date());
 				managerBelongMap.setModifiedBy(user.getId());
 				managerBelongMap.setModifiedTime(new Date());
				managerBelongMapService.insertBelongMap(managerBelongMap);
				
				returnMap.put("recordId", managerBelongMap.getId());
				returnMap.addGlobalMessage(CREATE_SUCCESS);
			} catch (Exception e) {
				e.printStackTrace();
				return WebRequestHelper.processException(e);

			}
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
			
			ManagerBelongMap managerBelongMap = managerBelongMapService.findById(id);
			List<ManagerBelongMapForm> childBelongMapList = managerBelongMapDao.findByParentId(managerBelongMap.getChildId());
			if(childBelongMapList != null && childBelongMapList.size() > 0){
				returnMap.setSuccess(false);
				returnMap.addGlobalError("该客户经理存在下属客户经理不可删除.");
				return returnMap;
			}
			
			managerBelongMapService.deleteById(id);
			returnMap.addGlobalMessage(JRadConstants.DELETE_SUCCESS);
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}
		return returnMap;
	}
	
	/**
	 * 执行 转移下属客户经理
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "change.json")
	@JRadOperation(JRadOperation.SUBTURNOTHER)
	public JRadReturnMap change(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();

		try {
			String id = RequestHelper.getStringValue(request, ID);
			String targetId = request.getParameter("targetId");
			
			ManagerBelongMap managerBelongMap = managerBelongMapService.findById(id);
			List<ManagerBelongMapForm> childBelongMapList = managerBelongMapDao.findByParentId(managerBelongMap.getChildId());
			if(childBelongMapList == null || childBelongMapList.size() == 0){
				returnMap.setSuccess(false);
				returnMap.addGlobalError("该客户经理不存在下属客户经理不需要此操作");
				return returnMap;
			}
			
			
			managerBelongMapService.copyXiaShuToTarget(childBelongMapList, targetId);
			returnMap.addGlobalMessage(JRadConstants.CHANGE_SUCCESS);
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}
		return returnMap;
	}
	
}
