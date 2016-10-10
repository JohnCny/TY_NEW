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
import com.cardpay.pccredit.report.model.AcctStatistical;
import com.cardpay.pccredit.report.service.AcctStatisticalService;
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
 * 2014-12-1上午10:02:41
 */
@Controller
@RequestMapping("/report/acctorgdimension/*")
@JRadModule("report.acctorgdimension")
public class AcctStatisticalOrgDimController extends BaseController {
	@Autowired
	private AcctStatisticalService acctStatisticalService;
	
	@Autowired
	private ProductService productService;
	
	/**
	 * 浏览“灵活金”透支情况统计表（一级支行、二级支行维度）（单位：万元）
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
		List<AcctStatistical> list = acctStatisticalService.getOrgAcctStatistical(filter);

		JRadModelAndView mv = new JRadModelAndView("/report/acctstatistical/acctstatistical_org_browse", request);
		mv.addObject("list", list);
		mv.addObject("filter", filter);
		
		mv.addObject(PAGED_RESULT, qs);
		return mv;
	}
}
