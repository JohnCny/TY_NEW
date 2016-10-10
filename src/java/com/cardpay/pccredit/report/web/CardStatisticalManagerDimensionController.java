package com.cardpay.pccredit.report.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.product.filter.ProductFilter;
import com.cardpay.pccredit.product.model.ProductAttribute;
import com.cardpay.pccredit.product.service.ProductService;
import com.cardpay.pccredit.report.filter.StatisticalFilter;
import com.cardpay.pccredit.report.model.CardStatistical;
import com.cardpay.pccredit.report.service.CardStatisticalService;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.util.date.DateHelper;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;

/**
 * @author chenzhifang
 *
 * 2014-11-28上午10:09:21
 */
@Controller
@RequestMapping("/report/cardmanagerdimension/*")
@JRadModule("report.cardmanagerdimension")
public class CardStatisticalManagerDimensionController extends BaseController {
	@Autowired
	private CardStatisticalService cardStatisticalService;
	
	@Autowired
	private ProductService productService;
	
	/**
	 * 浏览“灵活金”发卡进展情况统计表（客户经理/微贷经理、营销经理维度）
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "browse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browse(@ModelAttribute StatisticalFilter filter, HttpServletRequest request) {
		filter.setRequest(request);

		ProductFilter pFilter = new ProductFilter();
		pFilter.setLimit(Integer.MAX_VALUE);
		QueryResult<ProductAttribute> qs = productService.findProductsByFilter(pFilter);
		
		if(StringUtils.isEmpty(filter.getProductId())){
			filter.setProductId(qs.getItems().size() != 0 ? qs.getItems().get(0).getId() : "");
		}
		if(filter.getBasicDate() == null){
			filter.setBasicDate(DateHelper.getDateFormat("2013-08-01", "yyyy-MM-dd"));
		}
		if(filter.getReportDate() == null){
			filter.setReportDate(new Date());
		}
		List<CardStatistical> list = cardStatisticalService.getManagerCardStatistical(filter);

		JRadModelAndView mv = new JRadModelAndView("/report/cardstatistical/cardstatistical_manager_browse", request);
		mv.addObject("list", list);
		mv.addObject("filter", filter);
		
		mv.addObject(PAGED_RESULT, qs);
		return mv;
	}
}
