package com.cardpay.pccredit.manager.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.customer.model.CustomerInforWeb;
import com.cardpay.pccredit.manager.dao.InformationOfficerDao;
import com.cardpay.pccredit.manager.dao.comdao.InformationOfficerComdao;
import com.cardpay.pccredit.manager.filter.InformationOfficerFilter;
import com.cardpay.pccredit.manager.filter.ManagerInformationClientFilter;
import com.cardpay.pccredit.manager.model.InformationOfficer;
import com.cardpay.pccredit.manager.model.InformationOfficerEvaluate;
import com.cardpay.pccredit.manager.model.ManagerInformationClient;
import com.cardpay.pccredit.manager.web.InformationOfficerEvaluateForm;
import com.cardpay.pccredit.system.model.Dict;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.id.IDGenerator;
import com.wicresoft.jrad.base.database.model.QueryResult;

/**
 * 
 * @author shaoming
 *
 * 2014年10月31日   下午5:52:33
 */
@Service
public class InformationOfficerService {
	
	@Autowired
	private InformationOfficerDao informationOfficerDao;
	
	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private InformationOfficerComdao informationOfficerComdao;
	/**
	 * 得到页面显示信息员信息
	 * @param filter
	 * @return
	 */
	public QueryResult<InformationOfficer> findInformationOfficersByFilter(InformationOfficerFilter filter){
		List<InformationOfficer> list = informationOfficerDao.findInformationOfficerByFilter(filter);
		int size = informationOfficerDao.findInformationOfficerCountByFilter(filter);
		QueryResult<InformationOfficer> qr = new QueryResult<InformationOfficer>(size,list);
		return qr;
	}
	/**
	 * 添加信息员信息
	 * @param informationOfficer
	 * @return
	 */
	public String insertInformationOfficer(InformationOfficer informationOfficer,ManagerInformationClient managerInformationClient){
		informationOfficer.setCreatedTime(new Date());
		informationOfficer.setId(IDGenerator.generateID());
		commonDao.insertObject(informationOfficer);
		managerInformationClient.setId(IDGenerator.generateID());
		managerInformationClient.setCreatedTime(new Date());
		String messengerId = informationOfficer.getId();
		managerInformationClient.setMessengerId(messengerId);
		commonDao.insertObject(managerInformationClient);
		return messengerId;
	}
	/**
	 * 通过id得到信息员信息
	 * @param id
	 * @return
	 */
	public InformationOfficer findInformationOfficerById(String id){
		return commonDao.findObjectById(InformationOfficer.class, id);
	}
	/**
	 * 得到信息员评价信息
	 * @param messengerId
	 * @return
	 */
	public InformationOfficerEvaluateForm findInformationOfficerEvaluateFormById(String messengerId){
		return informationOfficerDao.findInformationOfficerEvaluateFormById(messengerId);
	}
	/**
	 * 得到显示页面的客户信息
	 * @param filter
	 * @return
	 */
	public List<CustomerInforWeb> findCustomerInforWebByFilter(ManagerInformationClientFilter filter){
		return informationOfficerDao.findCustomerInforWebsByFilter(filter);
	}
	/**
	 * 修改信息员
	 * @param informationOfficer
	 * @return
	 */
	public boolean updateInformationOfficer(InformationOfficer informationOfficer){
		int i = commonDao.updateObject(informationOfficer);
		return i>0?true:false;
	}
	/**
	 * 得到该信息员下的客户
	 * @param messengerId
	 * @param customerManagerId
	 * @return
	 */
	public List<Dict> findCustomerInforsById(String messengerId,String customerManagerId){
		return informationOfficerDao.findCustomerInforsById(messengerId, customerManagerId);
	}
	/**
	 * 得到客户经理下未被分配给信息员的客户
	 * @param messengerId
	 * @param customerManagerId
	 * @return
	 */
	public List<Dict> findCustomerInfors(String messengerId,String customerManagerId){
		return informationOfficerDao.findCustomerInfors(messengerId, customerManagerId);
	}
	
	public boolean updateManagerInformationClient(List<ManagerInformationClient> managerInformationClients){
		int i = deleteById(managerInformationClients.get(0).getMessengerId(),managerInformationClients.get(0).getCustomerManagerId());
		String id = managerInformationClients.get(0).getId();
		if(id!=null && !id.equals("")){
			int j = insertBatchByManagerInformationClient(managerInformationClients);
			return j>0?true:false;
		}else{
			return i>0?true:false;
		}
	}
	/**
	 * 删除在该信息员下的客户关联信息
	 * @param messengerId
	 * @param customerManagerId
	 * @return
	 */
	public int deleteById(String messengerId,String customerManagerId){
		return informationOfficerDao.deleteById(messengerId, customerManagerId);
	}
	/**
	 * 批量添加客户经理-信息员-客户关联表
	 * @param managerInformationClient
	 * @return
	 */
	public int insertBatchByManagerInformationClient(List<ManagerInformationClient> managerInformationClients){
		return informationOfficerDao.insertBatchByList(managerInformationClients);
	}
	/**
	 * 更新信息员评价
	 * @param informationOfficerEvaluate
	 * @return
	 */
	public boolean updateInformationOfficerEvaluate(InformationOfficerEvaluate informationOfficerEvaluate){
		String messengerId = informationOfficerEvaluate.getMessengerId();
		if(StringUtils.isNotEmpty(messengerId)){
			String id = informationOfficerDao.checkRepeatById(messengerId);
			if(id!=null && !id.equals("")){
				/*存在记录,则修改当前记录*/
				informationOfficerEvaluate.setCreatedBy(null);
				informationOfficerEvaluate.setModifiedTime(new Date());
				informationOfficerEvaluate.setId(id);
				int i = commonDao.updateObject(informationOfficerEvaluate);
				return i>0?true:false;
			}else{
				/*不存在记录,则插入新记录*/
				informationOfficerEvaluate.setModifiedBy(null);
				informationOfficerEvaluate.setCreatedTime(new Date());
				int i = commonDao.insertObject(informationOfficerEvaluate);
				return i>0?true:false;
			}
		}
		return false;
	}
	/**
	 * 得到当前客户经理名下的信息员
	 * @param userId
	 * @return
	 */
	public List<Dict> findInformationOfficerByUserId(String userId){
		return informationOfficerDao.findInformationOfficerByUserId(userId);
	}
}
