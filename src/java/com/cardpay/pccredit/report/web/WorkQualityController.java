package com.cardpay.pccredit.report.web;

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
import com.cardpay.pccredit.report.dao.WorkQualityDao;
import com.cardpay.pccredit.report.filter.WorkQualityFilter;
import com.cardpay.pccredit.report.model.WorkQuality;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;

@Controller
@RequestMapping("/report/workQuality/*")
@JRadModule("report.workQuality")
public class WorkQualityController extends BaseController{
	
	@Autowired
	private WorkQualityDao workQualityDao;
	
	@Autowired
	private ProductService productService;
	
	/**
	 * 统计信用卡中心辅助岗位人员工作质量检测报告
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "browse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browse(@ModelAttribute WorkQualityFilter filter, HttpServletRequest request) {
		filter.setRequest(request);
		
		ProductFilter pFilter = new ProductFilter();
		pFilter.setLimit(Integer.MAX_VALUE);
		QueryResult<ProductAttribute> qs = productService.findProductsByFilter(pFilter);
		
		if(StringUtils.isEmpty(filter.getProductId())){
			filter.setProductId(qs.getItems().size() != 0 ? qs.getItems().get(0).getId() : "");
		}
		
	
		List<WorkQuality> list = workQualityDao.getCountWorkQuality(filter);

		JRadModelAndView mv = new JRadModelAndView("/report/workquality/work_quality_browse", request);
		mv.addObject("list", list);
		mv.addObject("filter", filter);
		
		mv.addObject(PAGED_RESULT, qs);
		return mv;
	}
}


