
package com.cardpay.pccredit.dimensional.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.dimensional.model.Dimensional;
import com.wicresoft.jrad.base.database.dao.query.QueryContext;
import com.wicresoft.util.annotation.Mapper;


/**
 * Description of DimensionalDao
 *
 * @author 王海东
 * @created on 2014-10-20
 * 
 * @version $Id:$
 */
@Mapper
public interface DimensionalDao {
	//根据Filter查询四维评估表
	public List<Dimensional> findDimensionalByFilter(QueryContext qc);
	//根据customerId修改四维评估表
	public void updateDimensionalByCustomerId(Dimensional dimensional);
	//根据customerId 查询四维评估表
	public Dimensional  findDimensionalByCustomerId(String customerId);
	
	/**
	 * 根据进件申请Id查询思维评估信息
	 * @param applicationId
	 * @return
	 */
	public Dimensional findDimensionalByAppId(@Param("applicationId") String applicationId);

}
