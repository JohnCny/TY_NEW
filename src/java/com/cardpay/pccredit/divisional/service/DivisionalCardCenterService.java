package com.cardpay.pccredit.divisional.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.divisional.constant.DivisionalProgressEnum;
import com.cardpay.pccredit.divisional.dao.DivisionalDao;
import com.cardpay.pccredit.divisional.dao.comdao.DivisionalCommDao;
import com.cardpay.pccredit.divisional.filter.DivisionalFilter;
import com.cardpay.pccredit.divisional.model.DivisionalWeb;
import com.wicresoft.jrad.base.database.model.QueryResult;
/**
 * 
 * @author 姚绍明
 *
 * 2014年10月24日 上午11:10:12
 */
@Service
public class DivisionalCardCenterService {

	@Autowired
	private DivisionalCommDao divisionalcommDao;
	
	@Autowired
	private DivisionalDao divisionalDao;
	
	/**
	 * 获得分案申请信息
	 * @param filter
	 * @return
	 */
	public QueryResult<DivisionalWeb> findDivisional(DivisionalFilter filter){
		return divisionalcommDao.findDivisionalByCardCenter(filter);
	}
	/**
	 * 修改分案申请
	 * @param id
	 * @param orgId 现机构id
	 * @param divisionalprogressenum 分案进度
	 * @return
	 */
	public int updateDivisionalProcessAndOrg(String id,String orgId,DivisionalProgressEnum divisionalprogressenum){
		return divisionalDao.updateDivisionalProcessAndOrg(id, orgId, divisionalprogressenum.toString());
	}
}
