/**
 * 
 */
package com.cardpay.pccredit.ipad.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.customer.filter.CustomerInforFilter;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.intopieces.model.Dzjy;
import com.cardpay.pccredit.ipad.dao.CustomerInforIpadDao;
import com.cardpay.pccredit.ipad.model.CustomerInforIpad;
import com.cardpay.pccredit.riskControl.model.RiskCustomer;
import com.cardpay.pccredit.system.model.SystemUser;
import com.wicresoft.jrad.base.constant.JRadConstants;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.id.IDGenerator;
import com.wicresoft.util.date.DateHelper;

/**
 * @author shaoming
 *
 * 2014年11月28日   上午11:41:49
 */
@Service
public class CustomerInforForIpadService {
	
	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private CustomerInforIpadDao customerInfor;
	
	@Autowired
	private CustomerInforService customerInforService;
	/**
	 * 添加客户基本信息
	 * @param customerInfor
	 * @return
	 */
	public Map<String,Object> addCustomer(String name,String cardId,String cardType,String userId){
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		CustomerInforFilter filter = new CustomerInforFilter();
		filter.setCardId(cardId);
		List<CustomerInfor> ls = customerInforService.findCustomerInforByFilter(filter).getItems();
		if(ls != null && ls.size()>0){
			String message = "";
			String gId = customerInforService.getUserInform(ls.get(0).getUserId());
			if(gId==null){
				message = "此客户已挂在客户经理"+ls.get(0).getUserId()+"下!";
//				returnMap.put(JRadConstants.MESSAGE, "此客户已挂在客户经理"+ls.get(0).getUserId()+"下!");
			}else{
				message = "此客户已挂在客户经理"+gId+"下!";
//				returnMap.put(JRadConstants.MESSAGE, "此客户已挂在客户经理"+gId+"下!");
			}
			//查询是否为风险名单
			List<RiskCustomer> riskCustomers = customerInforService.findRiskByCardId(cardId);
			if(riskCustomers.size()>0){
				SystemUser user = customerInforService.getUseById(riskCustomers.get(0).getCreatedBy());
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String dateString = format.format(riskCustomers.get(0).getCreatedTime());
				message+="此客户于"+dateString+"被"+user.getDisplayName()+"拒绝，原因为"+riskCustomers.get(0).getRefuseReason();
			}
			map.put("result", "fail");
			map.put("message", message);
		}else{
			CustomerInfor infor = new CustomerInfor();
			infor.setId(IDGenerator.generateID());
			infor.setCardId(cardId);
			infor.setCardType("CST0000000000A");
			infor.setChineseName(name);
			commonDao.insertObject(infor);
			map.put("result", "success");
		}
		return map;
	}
	public List<CustomerInforIpad> getCustomerInforByUserId(String userId,int currentPage,int pageSize){
		currentPage = currentPage - 1;
		if(currentPage<0){
			currentPage = 0;
		}
		List<CustomerInforIpad> list = customerInfor.getCustomerInforByUserId(userId, currentPage, pageSize);
		return list;
	}
	public int getCustomerInforCountByUserId(String userId){
		int i = customerInfor.getCustomerInforCountByUserId(userId);
		return i;
	}
	/**
	 * 通过id查询客户基本信息
	 * @param id
	 * @return
	 */
	public CustomerInforIpad findCustomerInforById(String id){
		return customerInfor.findCustomerInforById(id);
	}
	/**
	 * 通过证件号查询客户基本信息
	 * @param cardId
	 * @return
	 */
	public CustomerInforIpad findCustomerInforByCardId(String cardId){
		return customerInfor.findCustomerInforByCardId(cardId);
	}
	
	/**
	 * 添加个人信息
	 * @param cardId
	 * @return
	 */
	public Boolean addGrxx(Dzjy dzjy){
		try {
			String sql = "select * from TY_DZ_MODEL_JY where customer_id='"+dzjy.getCustomer_id()+"' and product_id='"+dzjy.getProduct_id()+"' and application_id is null";
			List<Dzjy> list = commonDao.queryBySql(Dzjy.class, sql, null);
			if(list.size()>0){
				Dzjy oldDzjy = list.get(0);
				oldDzjy.setSqr_sex(dzjy.getSqr_sex());
				oldDzjy.setSqr_hy(dzjy.getSqr_hy());
				oldDzjy.setSqr_hjd(dzjy.getSqr_hjd());
				oldDzjy.setSqr_hjxx(dzjy.getSqr_hjxx());
				oldDzjy.setSqr_xl(dzjy.getSqr_xl());
				oldDzjy.setSqr_mobile(dzjy.getSqr_mobile());
				commonDao.updateObject(oldDzjy);
			}else{
				dzjy.setId(IDGenerator.generateID());
				commonDao.insertObject(dzjy);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 查询个人信息
	 * @param cardId
	 * @return
	 */
	public Dzjy queryGrxx(String customerId,String productId){
		String sql = "select * from TY_DZ_MODEL_JY where customer_id='"+customerId+"' and product_id='"+productId+"' and application_id is null";
		List<Dzjy> list = commonDao.queryBySql(Dzjy.class, sql, null);
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
}
