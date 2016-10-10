/**
 * 
 */
package com.cardpay.pccredit.ipad.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.customer.model.CustomerCareersInformation;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.intopieces.constant.Constant;
import com.cardpay.pccredit.intopieces.filter.IntoPiecesFilter;
import com.cardpay.pccredit.intopieces.model.CustomerAccountData;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationCom;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationContact;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationGuarantor;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationInfo;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationOther;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationRecom;
import com.cardpay.pccredit.intopieces.model.IntoPieces;
import com.cardpay.pccredit.intopieces.service.IntoPiecesService;
import com.cardpay.pccredit.ipad.service.CustomerInforForIpadService;
import com.cardpay.pccredit.ipad.util.JsonDateValueProcessor;
import com.cardpay.pccredit.product.model.AddressAccessories;
import com.cardpay.pccredit.product.model.AppendixDict;
import com.cardpay.pccredit.product.model.ProductAttribute;
import com.cardpay.pccredit.product.service.ProductService;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.constant.JRadConstants;
import com.wicresoft.jrad.base.database.id.IDGenerator;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.i18n.I18nHelper;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.util.spring.Beans;

/**
 * @author shaoming
 *
 * 2014年12月8日   上午9:24:47
 */
@Controller
public class IntopiecesIpadController {
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CustomerInforForIpadService customerInforService;
	
	@Autowired
	private IntoPiecesService intoPiecesService;
	
	/**
	 * 提供userId得到该客户经理名下所有客户接口
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/intopieces/getIntopieces.json")
	public String getCustomerInforByUserId(HttpServletRequest request) {
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		String userId = request.getParameter("userId");
		if(StringUtils.isNotEmpty(userId)){
			IntoPiecesFilter filter = new IntoPiecesFilter();
			filter.setUserId(userId);
			String currentPage=request.getParameter("currentPage");
			String pageSize=request.getParameter("pageSize");
			int page = 1;
			int limit = 10;
			if(StringUtils.isNotEmpty(currentPage)){
				page = Integer.parseInt(currentPage);
			}
			if(StringUtils.isNotEmpty(pageSize)){
				limit = Integer.parseInt(pageSize);
			}
			filter.setPage(page - 1);
			filter.setLimit(limit);
			
			QueryResult<IntoPieces> result = intoPiecesService.findintoPiecesByFilter(filter);
			for(IntoPieces intoPieces : result.getItems()){
				//当前申请状态 未申请save  已申请audit 申请未通过nopass  被拒接refuse 审批结束 approved  申请成功 succeed
				if(intoPieces.getStatus().equalsIgnoreCase(Constant.SAVE_INTOPICES)){
					intoPieces.setStatusName("暂存");
				}
				if(intoPieces.getStatus().equalsIgnoreCase(Constant.APPROVE_INTOPICES)){
					intoPieces.setStatusName("已申请");
				}
				if(intoPieces.getStatus().equalsIgnoreCase(Constant.NOPASS_REPLENISH_INTOPICES)){
					intoPieces.setStatusName("申请退回");
				}
				if(intoPieces.getStatus().equalsIgnoreCase(Constant.REFUSE_INTOPICES)){
					intoPieces.setStatusName("拒接");
				}
				if(intoPieces.getStatus().equalsIgnoreCase(Constant.APPROVED_INTOPICES)){
					intoPieces.setStatusName("审批结束");
				}
				if(intoPieces.getStatus().equalsIgnoreCase(Constant.SUCCESS_INTOPICES)){
					intoPieces.setStatusName("申请成功");
				}
			}
			
			map.put("totalCount", result.getTotalCount());
			map.put("result",result.getItems());			
		}
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
	
}
