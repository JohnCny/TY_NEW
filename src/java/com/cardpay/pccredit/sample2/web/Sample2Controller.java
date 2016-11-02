/*
 * Copyright 2014 Wicresoft, Inc. All rights reserved.
 */

package com.cardpay.pccredit.sample2.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.sample2.filter.Sample2Filter;
import com.cardpay.pccredit.sample2.model.Sample2;
import com.cardpay.pccredit.sample2.service.Sample2Service;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.DataBindHelper;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.export.DictConverter;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.util.export.ExportProperties;
import com.wicresoft.util.export.converter.DateConverter;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;
import com.wicresoft.util.web.RequestHelper;

/**
 * Description of Sample2Controller
 * 
 * @author Vincent
 * @created on Apr 21, 2014
 * 
 * @version $Id: Sample2Controller.java 1650 2014-10-09 14:55:25Z vincent $
 */
@Controller
@RequestMapping("/sample2/*")
@JRadModule("examples.sample2")
public class Sample2Controller extends BaseController {

	@Autowired
	private Sample2Service sample2Service;

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
	public AbstractModelAndView browse(@ModelAttribute Sample2Filter filter, HttpServletRequest request) {
		filter.setRequest(request);

		QueryResult<Sample2> result = sample2Service.findSample2sByFilter(filter);
		JRadPagedQueryResult<Sample2> pagedResult = new JRadPagedQueryResult<Sample2>(filter, result);

		JRadModelAndView mv = new JRadModelAndView("/sample2/sample2_browse", request);
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
	public AbstractModelAndView create(HttpServletRequest request) {

		JRadModelAndView mv = new JRadModelAndView("/sample2/sample2_create", request);

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
		JRadModelAndView mv = new JRadModelAndView("/sample2/sample2_change", request);

		String sample2Id = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(sample2Id)) {
			Sample2 sample2 = sample2Service.findSample2ById(sample2Id);
			mv.addObject("sample2", sample2);
		}

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
		JRadModelAndView mv = new JRadModelAndView("/sample2/sample2_display", request);

		String sample2Id = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(sample2Id)) {
			Sample2 sample2 = sample2Service.findSample2ById(sample2Id);
			mv.addObject("sample2", sample2);
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
	public JRadReturnMap insert(@ModelAttribute Sample2Form sample2Form, HttpServletRequest request) {
		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), sample2Form);
		String sampleDate = request.getParameter("sampleDate");
		if (returnMap.isSuccess()) {
			try {
				Sample2 sample2 = sample2Form.createModel(Sample2.class);
				String id = sample2Service.insertSample2(sample2);
				returnMap.put(RECORD_ID, id);
				returnMap.addGlobalMessage(CREATE_SUCCESS);
			}
			catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
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
	public JRadReturnMap update(@ModelAttribute Sample2Form sample2Form, HttpServletRequest request) {

		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), sample2Form);
		if (returnMap.isSuccess()) {
			try {
				Sample2 sample2 = sample2Form.createModel(Sample2.class);
				sample2Service.updateSample2(sample2);
				returnMap.put(RECORD_ID, sample2.getId());
				returnMap.addGlobalMessage(CHANGE_SUCCESS);
			}
			catch (Exception e) {
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
			String sample2Id = RequestHelper.getStringValue(request, ID);
			sample2Service.deleteSample2(sample2Id);
			returnMap.addGlobalMessage(DELETE_SUCCESS);
		}
		catch (Exception e) {
			return WebRequestHelper.processException(e);
		}

		return returnMap;
	}

	/**
	 * 执行批量删除
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "batchDelete.json")
	@JRadOperation(JRadOperation.DELETE)
	public JRadReturnMap batchDelete(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();

		try {
			List<String> sample2Ids = RequestHelper.getStringList(request, ID);

			sample2Service.batchDeleteSample2s(sample2Ids);
		}
		catch (Exception e) {
			return WebRequestHelper.processException(e);
		}

		return returnMap;
	}

	/**
	 * 数据导出
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "export.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.EXPORT)
	public AbstractModelAndView export(@ModelAttribute Sample2Filter filter, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		filter.setPage(0);
		filter.setLimit(10000);

		QueryResult<Sample2> result = sample2Service.findSample2sByFilter(filter);

		DictConverter dictConverter = new DictConverter("ProductType");
		DateConverter dateConverter = new DateConverter("yyyy-MM-dd");
		DateConverter datetimeConverter = new DateConverter("yyyy-MM-dd hh:mm:ss");

		ExportProperties expProps = new ExportProperties();
		addExportProperty(expProps, "seqNo", 5);
		addExportProperty(expProps, "name", 10);
		addExportProperty(expProps, "type", 10, dictConverter);
		addExportProperty(expProps, "nativeName", 20);
		addExportProperty(expProps, "sampleDate", 15, dateConverter);
		addExportProperty(expProps, "description", 30);
		addExportProperty(expProps, "timeCreated", 20, datetimeConverter);
		addExportProperty(expProps, "timeLastModified", 20, datetimeConverter);

		exportExcel(expProps, result.getItems(), request, response);

		return null;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DataBindHelper.initStandardBinder(binder);
	}

}
