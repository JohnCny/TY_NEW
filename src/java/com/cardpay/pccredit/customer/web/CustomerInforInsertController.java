package com.cardpay.pccredit.customer.web;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.common.IdCardValidate;
import com.cardpay.pccredit.customer.constant.CustomerInforConstant;
import com.cardpay.pccredit.customer.filter.CustomerInforFilter;
import com.cardpay.pccredit.customer.model.BusinessTackling;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.service.BusinessTacklingService;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.datapri.constant.DataPriConstants;
import com.cardpay.pccredit.jnpad.model.CustomerInfo;
import com.cardpay.pccredit.riskControl.model.RiskCustomer;
import com.cardpay.pccredit.system.model.SystemUser;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.constant.JRadConstants;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.jrad.modules.log.model.OperationLog;
import com.wicresoft.jrad.modules.log.service.UserLogService;
import com.wicresoft.jrad.modules.privilege.model.User;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;
import com.wicresoft.util.web.RequestHelper;

@Controller
@RequestMapping("/customer/basiccustomerinforCreate/*")
@JRadModule("customer.basiccustomerinforCreate")
public class CustomerInforInsertController extends BaseController{
	
	@Autowired
	private CustomerInforService customerInforService;
	
	@Autowired
	private UserLogService userLogService;
	//业务核查
	@Autowired
	private BusinessTacklingService btService;
	/**
	 * 跳转到增加客户信息页面
	 * 
	 * @param request
	 * @return
	*/
	@ResponseBody
	@RequestMapping(value = "insert.page")
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView create(HttpServletRequest request) {        
		JRadModelAndView mv = new JRadModelAndView("/customer/customerInforInsert/customerinfor_create", request);
		return mv;
	}
	
	/**
	 * 执行添加客户信息
	 * @param customerinfoForm
	 * @param request
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "insert.json")
	@JRadOperation(JRadOperation.CREATE)
	public JRadReturnMap insert(@ModelAttribute CustomerInforForm customerinfoForm, HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		if (returnMap.isSuccess()) {
			try {
				CustomerInforFilter filter = new CustomerInforFilter();
				filter.setCardId(customerinfoForm.getCardId());
				//身份证号码验证
				String msg ="";
				if("CST0000000000A".equals(customerinfoForm.getCardType())){
					 msg = IdCardValidate.IDCardValidate(customerinfoForm.getCardId().trim());
				}
				if(msg !="" && msg != null){
					returnMap.put(JRadConstants.MESSAGE, msg);
					returnMap.put(JRadConstants.SUCCESS, false);
					return returnMap;
				}
				filter.setCardType(customerinfoForm.getCardType());
				List<CustomerInfor> ls = customerInforService.findCustomerInforByFilter(filter).getItems();
				if(ls != null && ls.size()>0){
					String message = "";
					String gId = customerInforService.getUserInform(ls.get(0).getUserId());
					if(gId==null){
						message = "保存失败，此客户已挂在客户经理【"+ls.get(0).getUserId()+"】下，请线下及时联系!";
					}else{
						message = "保存失败，此客户已挂在客户经理【"+gId+"】下!";
//						returnMap.put(JRadConstants.MESSAGE, "此客户已挂在客户经理"+gId+"下!");
					}
					//查询是否为风险名单
					List<RiskCustomer> riskCustomers = customerInforService.findRiskByCardId(customerinfoForm.getCardId());
					if(riskCustomers.size()>0){
						SystemUser user = customerInforService.getUseById(riskCustomers.get(0).getCreatedBy());
						DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String dateString = format.format(riskCustomers.get(0).getCreatedTime());
						message+="此客户于"+dateString+"被"+user.getDisplayName()+"拒绝，原因为"+riskCustomers.get(0).getRefuseReason();
					}
					returnMap.put(JRadConstants.MESSAGE, message);
					returnMap.put(JRadConstants.SUCCESS, false);
					return returnMap;
				}else{
					returnMap.put(JRadConstants.SUCCESS, true);
				}
			    
//				int i = customerInforService.findCustomerOriginaCountList(filter);
//				if(i!=0){
//					returnMap.put(JRadConstants.MESSAGE, "证件号码已存在");
//					returnMap.put(JRadConstants.SUCCESS, false);
//					return returnMap;
//				}
				CustomerInfor customerinfor = customerinfoForm.createModel(CustomerInfor.class);
				String name=request.getParameter("chineseName");
				String sp=request.getParameter("spmc");
				try {
					String chineseName=new String(name.getBytes("iso-8859-1"),"utf-8");
					String spmc=new String(sp.getBytes("iso-8859-1"),"utf-8");
					customerinfor.setChineseName(chineseName);
					customerinfor.setSpmc(spmc);
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
				customerinfor.setCreatedBy(user.getId());
				customerinfor.setUserId(user.getId());
				String id = customerInforService.insertCustomerInfor(customerinfor);
				
				//日志记录
				OperationLog ol = new OperationLog();
				ol.setUser_id(user.getId());
			    ol.setUser_login(user.getDisplayName());
			    ol.setModule("客户新增");
			    ol.setOperation_result("SUCCESS");
			    ol.setOperation_name("ADD");
			    ol.setIp_address(request.getRemoteAddr());
				userLogService.addUserLog(ol);
				
				returnMap.put(RECORD_ID, id);
				returnMap.addGlobalMessage(CREATE_SUCCESS);
			}catch (Exception e) {
				returnMap.put(JRadConstants.MESSAGE, DataPriConstants.SYS_EXCEPTION_MSG);
				returnMap.put(JRadConstants.SUCCESS, false);
				return WebRequestHelper.processException(e);
			}
		}else{
			returnMap.setSuccess(false);
			returnMap.addGlobalError(CustomerInforConstant.CREATEERROR);
		}
		return returnMap;
	}
	
	
	/**
	 * 业务核查
	 * 描述 ： 行内现有业务核查
	 * @author 周文杰
	 *
	 * 2016年10月24日14:59:36
	 */
	@ResponseBody
	@RequestMapping(value = "queryByBusinessTackling.page", method = { RequestMethod.GET})
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView change( 
			HttpServletRequest request
			) {
		JRadModelAndView mv =null;
		String idcard=request.getParameter("cardId");
		List<BusinessTackling> btlist=btService.queryByIdCard(idcard);
		mv = new JRadModelAndView("/customer/customerInfor/BusinessTacking_browse", request);
		mv.addObject("btlist",btlist);
		for(BusinessTackling bb:btlist){
			if (bb.getSettle()!=null) {
				if(bb.getSettle().equals("0.0")){
					bb.setSettle("未结清");
				}else{
					bb.setSettle("已结清");
				}
			}else{
				bb.setSettle("");
			}
		}
		return mv;
	}
	
	@ResponseBody
	@RequestMapping(value = "queryByIdCard.json", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public JRadReturnMap queryBusinessTackling(HttpServletRequest request)
		{
			JRadReturnMap returnMap = new JRadReturnMap();
					String cardId=request.getParameter("cardId");
					List<CustomerInfo> customer=btService.queryById(cardId); 
					if(customer.size()>0){
						returnMap.put(MESSAGE, "1");
						return returnMap;
					}
					return returnMap;
}
}
