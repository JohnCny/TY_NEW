package com.cardpay.pbccrcReport.service;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pbccrcReport.pojo.DWDB_Info;
import com.cardpay.pbccrcReport.pojo.JZ_Info;
import com.cardpay.pbccrcReport.pojo.PO_Info;
import com.cardpay.pbccrcReport.pojo.Query_Info;
import com.cardpay.pbccrcReport.pojo.WJQDK_Info;
import com.cardpay.pbccrcReport.pojo.WXHDJK_Info;
import com.cardpay.pbccrcReport.pojo.XYTS_Info;
import com.cardpay.pbccrcReport.pojo.YH_Info;
import com.cardpay.pbccrcReport.pojo.YQ_Info;
import com.cardpay.pbccrcReport.pojo.ZY_Info;
import com.cardpay.pccredit.sample2.filter.Sample2Filter;
import com.cardpay.pccredit.sample2.model.Sample2;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.model.QueryResult;

/**
 * 人行征信服务类
 * @author chenzhifang
 *
 * 2014-12-24上午9:45:51
 */
@Service
public class RhzxService {
	
	@Autowired
	private CommonDao commonDao;
	
	/**
	 * 插入对外担保信息汇总
	 * @param dwdbInfo
	 * @return
	 */
	public String insertDWDB_Info(DWDB_Info dwdbInfo) {
		// 创建时间
		dwdbInfo.setCreatedTime(Calendar.getInstance().getTime());
		// 修改时间
		dwdbInfo.setModifiedTime(Calendar.getInstance().getTime());
		
		commonDao.insertObject(dwdbInfo);
		return dwdbInfo.getId();
	}
	
	/**
	 * 插入居住信息
	 * @param jzInfo
	 * @return
	 */
	public String insertJZ_Info(JZ_Info jzInfo) {
		// 创建时间
		jzInfo.setCreatedTime(Calendar.getInstance().getTime());
		// 修改时间
		jzInfo.setModifiedTime(Calendar.getInstance().getTime());
		
		commonDao.insertObject(jzInfo);
		return jzInfo.getId();
	}
	
	/**
	 * 插入配偶信息
	 * @param poInfo
	 * @return
	 */
	public String insertPO_Info(PO_Info poInfo) {
		// 创建时间
		poInfo.setCreatedTime(Calendar.getInstance().getTime());
		// 修改时间
		poInfo.setModifiedTime(Calendar.getInstance().getTime());
		
		commonDao.insertObject(poInfo);
		return poInfo.getId();
	}
	
	/**
	 * 插入查询记录汇总
	 * @param queryInfo
	 * @return
	 */
	public String insertQuery_Info(Query_Info queryInfo) {
		// 创建时间
		queryInfo.setCreatedTime(Calendar.getInstance().getTime());
		// 修改时间
		queryInfo.setModifiedTime(Calendar.getInstance().getTime());
		
		commonDao.insertObject(queryInfo);
		return queryInfo.getId();
	}
	
	/**
	 * 插入未结清贷款信息汇总
	 * @param wjqdkInfo
	 * @return
	 */
	public String insertWJQDK_Info(WJQDK_Info wjqdkInfo) {
		// 创建时间
		wjqdkInfo.setCreatedTime(Calendar.getInstance().getTime());
		// 修改时间
		wjqdkInfo.setModifiedTime(Calendar.getInstance().getTime());
		
		commonDao.insertObject(wjqdkInfo);
		return wjqdkInfo.getId();
	}
	
	/**
	 * 插入
	 * @param wxhdjkInfo
	 * @return
	 */
	public String insertWXHDJK_Info(WXHDJK_Info wxhdjkInfo) {
		// 创建时间
		wxhdjkInfo.setCreatedTime(Calendar.getInstance().getTime());
		// 修改时间
		wxhdjkInfo.setModifiedTime(Calendar.getInstance().getTime());
		
		commonDao.insertObject(wxhdjkInfo);
		return wxhdjkInfo.getId();
	}
	
	/**
	 * 插入
	 * @param xytsInfo
	 * @return
	 */
	public String insertXYTS_Info(XYTS_Info xytsInfo) {
		// 创建时间
		xytsInfo.setCreatedTime(Calendar.getInstance().getTime());
		// 修改时间
		xytsInfo.setModifiedTime(Calendar.getInstance().getTime());
		
		commonDao.insertObject(xytsInfo);
		return xytsInfo.getId();
	}
	
	/**
	 * 插入
	 * @param yhInfo
	 * @return
	 */
	public String insertYH_Info(YH_Info yhInfo) {
		// 创建时间
		yhInfo.setCreatedTime(Calendar.getInstance().getTime());
		// 修改时间
		yhInfo.setModifiedTime(Calendar.getInstance().getTime());
		
		commonDao.insertObject(yhInfo);
		return yhInfo.getId();
	}
	
	/**
	 * 插入
	 * @param yqInfo
	 * @return
	 */
	public String insertYQ_Info(YQ_Info yqInfo) {
		// 创建时间
		yqInfo.setCreatedTime(Calendar.getInstance().getTime());
		// 修改时间
		yqInfo.setModifiedTime(Calendar.getInstance().getTime());
		
		commonDao.insertObject(yqInfo);
		return yqInfo.getId();
	}
	
	/**
	 * 插入职业信息
	 * @param zyInfo
	 * @return
	 */
	public String insertZY_Info(ZY_Info zyInfo) {
		// 创建时间
		zyInfo.setCreatedTime(Calendar.getInstance().getTime());
		// 修改时间
		zyInfo.setModifiedTime(Calendar.getInstance().getTime());
		
		commonDao.insertObject(zyInfo);
		return zyInfo.getId();
	}
	
	/**
	 * 通过id查找
	 * @param clazz
	 * @param id
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object findSample2ById(Class clazz, String id) {
		return commonDao.findObjectById(clazz, id);
	}

	/**
	 * @return
	 */
	public QueryResult<Sample2> findSample2sByFilter(Sample2Filter filter) {
		return commonDao.findObjectsByFilter(Sample2.class, filter);
	}
}
