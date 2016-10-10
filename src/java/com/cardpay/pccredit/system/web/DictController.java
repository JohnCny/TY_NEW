package com.cardpay.pccredit.system.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.system.constants.DictTypeConstants;
import com.cardpay.pccredit.system.filter.DictFilter;
import com.cardpay.pccredit.system.model.Dict;
import com.cardpay.pccredit.system.service.DictService;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;

/**
 * 
 * 描述 ：数据字典controller
 * @author 张石树
 *
 * 2014-11-4 上午10:16:24
 */
@Controller
@RequestMapping("/system/dict/*")
@JRadModule("system.dict")
public class DictController extends BaseController {
	
	@Autowired
	private DictService dictService;
	
	/**
	 * 浏览页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "browse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browse(@ModelAttribute DictFilter filter, HttpServletRequest request) {
        filter.setRequest(request);
		QueryResult<Dict> result = dictService.findDictByFilter(filter);
		JRadPagedQueryResult<Dict> pagedResult = new JRadPagedQueryResult<Dict>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/system/dict/dict_browse",request);
		mv.addObject(PAGED_RESULT, pagedResult);
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
	public AbstractModelAndView create(@ModelAttribute DictFilter filter, HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/system/dict/dict_create", request);
		return mv;
	}
	
	/**
	 * 增加
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insert.json")
	@JRadOperation(JRadOperation.CREATE)
	public JRadReturnMap insert(@ModelAttribute Dict dict, HttpServletRequest request) {
		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), dict);
		if (returnMap.isSuccess()) {
			try {
				List<Dict> dicts = dictService.findByDictTypeAndTypeCode(dict.getDictType().trim(), dict.getTypeCode().trim());
				if(dicts != null && dicts.size() > 0){
					returnMap.addGlobalError(DictTypeConstants.DICT_INSERT_EXITS.replace("{0}", dict.getDictType()).replace("{1}", dict.getTypeCode()));
					returnMap.setSuccess(false);
				} else {
					IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
					dict.setCreatedBy(user.getId());
					dict.setCreatedTime(new Date());
					dict.setDictType(dict.getDictType().trim());
					dict.setTypeCode(dict.getTypeCode().trim());
					dictService.insertDict(dict);
					returnMap.addGlobalMessage(CREATE_SUCCESS);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return WebRequestHelper.processException(e);

			}
		}
		return returnMap;
	}
	
	/**
	 * 增加页面
	 * 
	 * @param request
	 * @return
	 */
	/**
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "change.page")
	@JRadOperation(JRadOperation.CHANGE)
	public AbstractModelAndView change(@ModelAttribute DictFilter filter, HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/system/dict/dict_change", request);
		String id = request.getParameter("id");
		
		Dict dict = dictService.findDictById(id);
		mv.addObject("dict", dict);
		
		return mv;
	}
	
	/**
	 * 修改
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "update.json")
	@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap update(@ModelAttribute Dict dict, HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			dict.setModifiedBy(user.getId());
			dict.setModifiedTime(new Date());
			dictService.updateDict(dict);
			returnMap.addGlobalMessage(CHANGE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			return WebRequestHelper.processException(e);
		}
		return returnMap;
	}
	
	/**
	 * 删除
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "delete.json")
	@JRadOperation(JRadOperation.DELETE)
	public JRadReturnMap delete(@ModelAttribute Dict dict, HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		String id = request.getParameter("id");
		try {
			dictService.deleteDictById(id);
			returnMap.addGlobalMessage(DELETE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			return WebRequestHelper.processException(e);

		}
		return returnMap;
	}
}
