/**
 * 
 */
package com.cardpay.pccredit.divisional.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.customer.constant.CustomerInforDStatusEnum;
import com.cardpay.pccredit.customer.dao.CustomerInforDao;
import com.cardpay.pccredit.customer.service.CardInfomationService;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.customer.web.CardInfomationFrom;
import com.cardpay.pccredit.divisional.constant.DivisionalProgressEnum;
import com.cardpay.pccredit.divisional.constant.DivisionalTypeEnum;
import com.cardpay.pccredit.divisional.dao.DivisionalDao;
import com.cardpay.pccredit.divisional.dao.comdao.DivisionalCommDao;
import com.cardpay.pccredit.divisional.filter.DivisionalFilter;
import com.cardpay.pccredit.divisional.model.Divisional;
import com.cardpay.pccredit.divisional.model.DivisionalTransfer;
import com.cardpay.pccredit.divisional.model.DivisionalWeb;
import com.cardpay.pccredit.riskControl.constant.RiskType;
import com.cardpay.pccredit.riskControl.service.AccountabilityService;
import com.cardpay.pccredit.system.model.Dict;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.modules.privilege.model.Organization;
import com.wicresoft.jrad.modules.privilege.service.OrganizationService;
import com.wicresoft.jrad.modules.privilege.service.impl.OrganizationServiceImpl;
import com.wicresoft.util.spring.Beans;

/**
 * 移交客户
 * 
 * @author Evans zhang
 * 
 *         2014-10-15 上午11:23:08
 */
@Service
public class DivisionalService {

	@Autowired
	private DivisionalDao divisionaldao;

	@Autowired
	private CommonDao commonDao;

	@Autowired
	private CustomerInforDao customerInforDao;
	@Autowired
	private DivisionalCommDao divisionalcommDao;
	
	@Autowired
	private CustomerInforService customerInforService;
	
	@Autowired
	private CardInfomationService cardInfomationService;
	
	@Autowired
	private AccountabilityService accountabilityService;

	/**
	 * 移交方法
	 * 
	 * @param string
	 *            customerId
	 * 
	 * @return boolean
	 */

	public boolean insertDivisionalCustomer(String customerId,
			DivisionalTypeEnum divisionalEnum,
			DivisionalProgressEnum divisionalProgressEnum) {
		// 先通过customerId 找到经理Id
		String userId = customerInforDao.findCustomerManagerIdById(customerId);
		if (StringUtils.isNotEmpty(userId)) {
			// 先通过customerId 找到机构Id
			Organization organization = Beans
					.get(OrganizationService.class).findOrgByUserId(userId);

			Divisional divisional = new Divisional();
			divisional.setOriginalManagerOld(userId);
			divisional.setOriginalOrganizationOld(organization.getId());
			divisional.setCurrentOrganizationId(organization.getId());
			divisional.setDivisionalProgress(divisionalProgressEnum.toString());
			divisional.setDivisionalType(divisionalEnum.toString());
			divisional.setCustomerId(customerId);
			divisional.setCreatedTime(new Date());
			divisional.setCreatedBy(userId);
			int i = commonDao.insertObject(divisional);
			if (i > 0) {
				if(customerInforService.updateCustomerInforDivisionalStatus(customerId, CustomerInforDStatusEnum.turn)){
					//调用问责接口  查询客户下的产品
					List<CardInfomationFrom> results = cardInfomationService.findCardsByCustomerId(customerId);	
					for(CardInfomationFrom cardInfomationFrom:results){
						accountabilityService.insertAccountAbility(userId, customerId, cardInfomationFrom.getProductId(), RiskType.turn, "主动移交产生的问责", userId);
					}
				}
				return true;
			} else {
				return false;
			}
		} else {
			//移交到卡中心
			Divisional divisional = new Divisional();
			divisional.setOriginalManagerOld(userId);
			divisional.setDivisionalProgress(DivisionalProgressEnum.cfcc.toString());
			divisional.setDivisionalType(divisionalEnum.toString());
			divisional.setCustomerId(customerId);
			divisional.setCreatedTime(new Date());
			divisional.setCreatedBy(userId);
			int i = commonDao.insertObject(divisional);
			if (i > 0) {
				customerInforService.updateCustomerInforDivisionalStatus(customerId, CustomerInforDStatusEnum.turn);
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * 获得分案申请表信息
	 * 
	 * @param filter
	 * @return
	 */
	public QueryResult<DivisionalWeb> findDivisional(DivisionalFilter filter) {
		return divisionalcommDao.findDivisional(filter);
	}
	/**
	 * 获得移交客户页面所需信息
	 * @param filter
	 * @return
	 */
	public QueryResult<DivisionalTransfer> findDivisionalTransfer(DivisionalFilter filter) {
		return divisionalcommDao.findDivisionalTransfer(filter);
	}
	/**
	 * 获得客户经理信息
	 * 
	 * @param id
	 * @return
	 */
	public List<Dict> findCustomerManagers(String id) {
		return divisionaldao.findCustomerManagers(id);
	}

	/**
	 * 获得客户信息吧ById
	 * 
	 * @param divisionalId
	 * @return
	 */
	public String findCustomerIdById(String divisionalId) {
		return divisionaldao.findCustomerIdById(divisionalId);
	}

	/**
	 * 更新分案申请信息
	 * 
	 * @param id
	 * @param customerManagerId
	 * @param orgId
	 * @return
	 */
	public int updateDivisional(String id, String customerManagerId,
			String orgId, String result) {
		return divisionaldao.updateDivisional(id, customerManagerId, orgId,
				result);
	}

	/**
	 * 通过id得到分案结果
	 * 
	 * @param id
	 * @return
	 */
	public String findDivisionalResultById(String id) {
		return divisionaldao.findDivisionalResultById(id);
	}
	/**
	 * 通过id得到分案进度
	 * 
	 * @param id
	 * @return
	 */
	public String findDivisionalProcessById(String id){
		return divisionaldao.findDivisionalProcessById(id);
	}
	/**
	 * 将分案申请上传到 卡中心
	 * 
	 * @param id
	 * @return
	 */
	public boolean uploadToCardCenter(String id, String process) {
		int i=divisionaldao.updateDivisionalProcessToCardCenter(id, process);
		if(i>0){
			return true;
		}else{
			return false;
		}
	}
	
	public Divisional findDivisinalById(String id){
		return commonDao.findObjectById(Divisional.class, id);
	}
	/**
	 * 统计今日分案申请数量 ()
	 * @return
	 */
	public int findDivisionalCounsToday(String customerManagerId,String result,String process){
		return divisionaldao.findDivisionalCounsToday(customerManagerId, result, process);
	}
}
