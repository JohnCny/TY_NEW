package com.cardpay.pccredit.divisional.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.system.model.Dict;
import com.wicresoft.util.annotation.Mapper;
/**
 * 
 * @author shaoming
 *
 */
@Mapper
public interface DivisionalDao {
	/**
	 * 得到客户经理的id和name
	 * @param id
	 * @return
	 */
	public List<Dict> findCustomerManagers(@Param("id") String id);
	/**
	 * 得到客户id
	 * @param id
	 * @return
	 */
	public String findCustomerIdById(@Param("id") String id);
	/**
	 * 修改分案申请信息中的现机构id,客户经理id,分案结果
	 * @param id
	 * @param customerManagerId
	 * @param orgId
	 * @return
	 */
	public int updateDivisional(@Param("id")String id,@Param("customerManagerId")String customerManagerId,@Param("orgId")String orgId,@Param("result")String result);
	/**
	 * 得到分案最终结果
	 * @param id
	 * @return
	 */
	public String findDivisionalResultById(@Param("id")String id);
	/**
	 * 得到分案进度
	 * @param id
	 * @return
	 */
	public String findDivisionalProcessById(@Param("id")String id);
	/**
	 * 修改分案进度
	 * @param id
	 * @return
	 */
	public int findDivisionalProcessById(@Param("id")String id,@Param("process") String process);
	
	/**
	 * 修改分案进度toCardCenter
	 * @param id
	 * @return
	 */
	public int updateDivisionalProcessToCardCenter(@Param("id")String id,@Param("process") String process);
	/**
	 * 修改分案申请
	 * @param id
	 * @param result 分案结果
	 * @param process 分案进度
	 * @return
	 */
	public int updateDivisionalResultAndProcess(@Param("id") String id,@Param("result") String result,@Param("process") String process);
	
	/**
	 * 修改分案申请
	 * @param id
	 * @param orgId 现机构id
	 * @param process 分案进度
	 * @return
	 */
	public int updateDivisionalProcessAndOrg(@Param("id") String id,@Param("orgId") String orgId,@Param("process") String process);
	
	/**
	 * 得到用户名
	 * @param id
	 * @return
	 */
	public String getUserNameByUserId(@Param("id") String id);
	/**
	 * 统计今日分案申请数量
	 * @param customerManagerId 现 客户经理id
	 * @param result 分案结果
	 * @param process 分案进度
	 * @return
	 */
	public int findDivisionalCounsToday(@Param("customerManagerId") String customerManagerId,@Param("result") String result,@Param("process") String process);
}
