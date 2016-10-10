package com.cardpay.pccredit.product.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.product.constant.DictTypeConstant;
import com.cardpay.pccredit.product.constant.ProductFilterColumn;
import com.cardpay.pccredit.product.filter.FilterDictFilter;
import com.cardpay.pccredit.product.model.FilterDict;
import com.cardpay.pccredit.product.service.ProductFilterService;
import com.cardpay.pccredit.sample2.model.Sample2;
import com.cardpay.pccredit.sample2.web.Sample2Form;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.jrad.modules.dictionary.DictionaryManager;
import com.wicresoft.jrad.modules.dictionary.model.Dictionary;
import com.wicresoft.jrad.modules.dictionary.model.DictionaryItem;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;
import com.wicresoft.util.web.RequestHelper;


/**
 * ProductFilterController类的描述
 *
 * @author 王海东
 * @created on 2014-11-24
 * 
 * @version $Id:$
 */
@Controller
@RequestMapping("/product/productfilter/*")
@JRadModule("product.productfilter")
public class ProductFilterController extends BaseController{
	
	@Autowired
	private  ProductFilterService productFilterService;
	
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
	public AbstractModelAndView browse(@ModelAttribute FilterDictFilter filter, HttpServletRequest request) {
		filter.setRequest(request);

		QueryResult<FilterDict> result = productFilterService.findFilterDictsByFilter(filter);
		JRadPagedQueryResult<FilterDict> pagedResult = new JRadPagedQueryResult<FilterDict>(filter, result);

		JRadModelAndView mv = new JRadModelAndView("/product/productfilter/product_filter_browse", request);
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

		JRadModelAndView mv = new JRadModelAndView("product/productfilter/product_filter_create", request);

		return mv;
	}
	/**
	 * 执行添加
	 * 
	 * @param filterDictForm
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insert.json")
	@JRadOperation(JRadOperation.CREATE)
	public JRadReturnMap insert(@ModelAttribute FilterDictForm filterDictForm, HttpServletRequest request) {
		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), filterDictForm);
		if (returnMap.isSuccess()) {
			try {
				FilterDict filterDict = filterDictForm.createModel(FilterDict.class);
				String id = productFilterService.insertFilterDict(filterDict);
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
	 * 查找筛选table下的字段
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "findColumnNameByTableName.json")
	public JRadReturnMap findColumnNameByTableName(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		String tableName = request.getParameter("tableName");
		if (returnMap.isSuccess()) {
			try {
				DictionaryManager dictMgr = Beans.get(DictionaryManager.class);
				if (tableName.equals(ProductFilterColumn.CUSTOMER_TABLE)) {
					Dictionary dictionary = dictMgr.getDictionaryByName("customerTableColumnName");
					List<DictionaryItem> dictItems = dictionary.getItems();
					returnMap.put("results", dictItems);
				}else if(tableName.equals(ProductFilterColumn.CARRER_TABLE)){
					Dictionary dictionary = dictMgr.getDictionaryByName("carrerTableColumnName");
					List<DictionaryItem> dictItems = dictionary.getItems();
					returnMap.put("results", dictItems);
				}
				
			}
			catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}


		return returnMap;
	}
	/**
	 * 查找筛选字段下的数据字典
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "findValueByColumnName.json")
	public JRadReturnMap findValueByColumnName(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		String columnName = request.getParameter("columnName");
		if (returnMap.isSuccess()) {
			try {
				List<DictionaryItem> dictItems=new ArrayList<DictionaryItem>();
				Map<String, String> map=(Map<String, String>) DictTypeConstant.TypeMap.get(columnName);
				for(Entry<String,String> entry:map.entrySet()){
					DictionaryItem item=new DictionaryItem();
					item.setName(entry.getKey());
					item.setTitle(entry.getValue());
					dictItems.add(item);
				}
				returnMap.put("results", dictItems);
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
			String filterId = RequestHelper.getStringValue(request, ID);
			productFilterService.deleteFilterDict(filterId);
			returnMap.addGlobalMessage(DELETE_SUCCESS);
		}
		catch (Exception e) {
			return WebRequestHelper.processException(e);
		}

		return returnMap;
	}



}
