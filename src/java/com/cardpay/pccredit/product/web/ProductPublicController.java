package com.cardpay.pccredit.product.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.common.PccOrganizationService;
import com.cardpay.pccredit.datapri.web.FlatTreeNode;
import com.cardpay.pccredit.product.filter.ProductFilter;
import com.cardpay.pccredit.product.model.ProductAttribute;
import com.cardpay.pccredit.product.model.ProductsAgencyAssociation;
import com.cardpay.pccredit.product.service.ProductService;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.jrad.modules.privilege.constant.PrivilegeConstants;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;
import com.wicresoft.util.web.RequestHelper;

/**
 * Description of ProductPublicController
 * 
 * @author 王海东
 * @created on 2014-11-03
 * 
 * @version $Id:$
 */
@Controller
@RequestMapping("/product/productpublic/*")
@JRadModule("product.productpublic")
public class ProductPublicController extends BaseController {

	@Autowired
	private ProductService productService;

	@Autowired
	private PccOrganizationService pccOrganizationService;

	/**
	 * 浏览产品发布首页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "browse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browse(@ModelAttribute ProductFilter filter, HttpServletRequest request) {
		filter.setRequest(request);

		QueryResult<ProductAttribute> result = productService.findProductsByNameFilter(filter);
		JRadPagedQueryResult<ProductAttribute> pagedResult = new JRadPagedQueryResult<ProductAttribute>(filter, result);

		JRadModelAndView mv = new JRadModelAndView("/product/product_public", request);
		mv.addObject(PAGED_RESULT, pagedResult);

		return mv;
	}

	/**
	 * 跳转到产品添加机构页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "filter.page")
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView create(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/product/product_organization", request);
		String productId = request.getParameter("id");
		List<FlatTreeNode> list = pccOrganizationService.queryAllOrgTreeList(PrivilegeConstants.INIT_ID);
		mv.addObject("organization", list);
		mv.addObject("productId", productId);
		return mv;
	}

	/**
	 * 产品所属机构添加
	 * 
	 * @param productsAgencyAssociation
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertCpjg.json")
	@JRadOperation(JRadOperation.APPROVEPASS)
	public JRadReturnMap insertCpjg(@ModelAttribute ProductsAgencyAssociation productsAgencyAssociation, HttpServletRequest request) {
		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), productsAgencyAssociation);
		if (returnMap.isSuccess()) {
			try {
				String productId = RequestHelper.getStringValue(request, "productId");
				String seqno= request.getParameter("seqno"); //产品编号
				String organizationIds = request.getParameter("organizationIds");
				if (StringUtils.trimToNull(organizationIds) != null) {
					String[] temp = organizationIds.split(",");
					if (temp != null && temp.length > 0) {
						// TODO 使用iterator较合适，无兼容考量可保留
						for (String aid : temp) {
							if (StringUtils.trimToNull(aid) != null) {
								ProductsAgencyAssociation productAA = new ProductsAgencyAssociation();
								productAA.setInstitution(aid);
								productAA.setProductId(productId);
								productService.insertProductsAgencyAssociation(productAA);
							}
						}
					}
				}
			
				productService.updateProductSeqnoByProductId(productId, seqno);
				productService.updateProductApproved(productId); //把产品状态改为已审核
				returnMap.put("productId", productId);
				returnMap.addGlobalMessage("审核通过");
			} catch (Exception e) {
				e.printStackTrace();
				return WebRequestHelper.processException(e);

			}
		}
		return returnMap;
	}

	/**
	 * 对未发布产品进行审核通过操作
	 * 
	 * @param request
	 * @return
	 */
	/*@ResponseBody
	@RequestMapping(value = "changeToApproved.json")
	public JRadReturnMap changeToApproved(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		String productId = request.getParameter("id");
		productService.updateProductApproved(productId);
		returnMap.addGlobalMessage(CHANGE_SUCCESS);
		return returnMap;
	}*/
	
	/**
	 * 对审核通过的产品进行发布操作
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "productPublished.json")
	@JRadOperation(JRadOperation.PUBLISHED)
	public JRadReturnMap productPublished(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		String productId = request.getParameter("id");
		productService.updateProductPublished(productId); // 把产品状态修改为已发布
		returnMap.addGlobalMessage("发布成功");
		return returnMap;
	}

}
