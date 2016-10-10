package com.cardpay.pccredit.jnpad.web;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.ipad.util.JsonDateValueProcessor;
import com.cardpay.pccredit.report.service.StatisticalCommonService;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
public class JnpadTongjiInforController {
	@Autowired
	private StatisticalCommonService statisticalCommonService;
	

	@ResponseBody
	@RequestMapping(value = "/ipad/tongji.json", method = { RequestMethod.GET })
	public String indexPage(HttpServletRequest request) {

		Map<Object, Object> map = new LinkedHashMap<Object, Object>();
	
			long start = System.currentTimeMillis();
			// 当前进件状况 济南 sj 20160804
			map.put("applicationStatusJson",statisticalCommonService.getApplicationStatusJson());
		    // 统计各行已申请和通过进件数量  济南 sj 20160809
			map.put("organApplicationAuditNumJson",statisticalCommonService.getOrganApplicationAuditNumJson());
			map.put("organApplicationApprovedNumJson",statisticalCommonService.getOrganApplicationApprovedNumJson());
		    
		    //授信总金额 逾期总金额 不良总金额  sj 20160810
			map.put("organApplicationjineJson",statisticalCommonService.statisticaljine());
		    //授信总金额 逾期总金额 不良总金额 按支行汇总 sj 20160810
			map.put("organApplicationsxJson",statisticalCommonService.statisticalsxorgan());
			map.put("organApplicationyqJson",statisticalCommonService.statisticalyqorgan());
			map.put("organApplicationblJson",statisticalCommonService.statisticalblorgan());
		    long end = System.currentTimeMillis();
			System.out.println("#########################查询时间花费：" + (end - start) + "毫秒");
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
			JSONObject json = JSONObject.fromObject(map, jsonConfig);
			return json.toString();
	}

}
