package com.cardpay.pccredit.manager.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.customer.model.CustomerInforWeb;
import com.cardpay.pccredit.manager.filter.InformationOfficerFilter;
import com.cardpay.pccredit.manager.filter.ManagerInformationClientFilter;
import com.cardpay.pccredit.manager.model.InformationOfficer;
import com.cardpay.pccredit.manager.model.ManagerInformationClient;
import com.cardpay.pccredit.manager.web.InformationOfficerEvaluateForm;
import com.cardpay.pccredit.system.model.Dict;
import com.wicresoft.util.annotation.Mapper;

@Mapper
public interface InformationOfficerDao {
	/**
	 * 得到客户信息
	 * @param filter
	 * @return
	 */
	public List<CustomerInforWeb> findCustomerInforWebsByFilter(ManagerInformationClientFilter filter);
	/**
	 * 得到客户信息数量
	 * @param filter
	 * @return
	 */
	public int findCustomerInforWebsCountByFilter(ManagerInformationClientFilter filter);
	/**
	 * 得到该信息员下的客户
	 * @param messengerId
	 * @param customerManagerId
	 * @return
	 */
	public List<Dict> findCustomerInforsById(@Param("messengerId") String messengerId,@Param("customerManagerId") String customerManagerId);
	/**
	 * 得到客户经理下未被分配给信息员的客户
	 * @param messengerId
	 * @param customerManagerId
	 * @return
	 */
	public List<Dict> findCustomerInfors(@Param("messengerId") String messengerId,@Param("customerManagerId") String customerManagerId);
	/**
	 * 删除在该信息员下的客户关联信息
	 * @param messengerId
	 * @param customerManagerId
	 * @return
	 */
	public int deleteById(@Param("messengerId") String messengerId,@Param("customerManagerId") String customerManagerId);
	/**
	 * 批量添加客户经理-信息员-客户关联表
	 * @param messengerId
	 * @param customerManagerId
	 * @param list
	 * @return
	 */
	public int insertBatchByList(List<ManagerInformationClient> list);
	/**
	 * 信息员评价信息
	 * @param messengerId
	 * @return
	 */
	public InformationOfficerEvaluateForm findInformationOfficerEvaluateFormById(@Param("id") String messengerId);
	/**
	 * 检查信息员评价是否重复
	 * @param id
	 * @return
	 */
	public String checkRepeatById(@Param("id") String id);
	/**
	 * 得到当前客户经理名下的信息员
	 * @param id
	 * @return
	 */
	public List<Dict> findInformationOfficerByUserId(@Param("id") String id);
	/**
	 * 得到页面显示信息员信息
	 * @param filter
	 * @return
	 */
	public List<InformationOfficer> findInformationOfficerByFilter(InformationOfficerFilter filter);
	/**
	 * 得到页面显示信息员信息数量
	 * @param filter
	 * @return
	 */
	public int findInformationOfficerCountByFilter(InformationOfficerFilter filter);
}
