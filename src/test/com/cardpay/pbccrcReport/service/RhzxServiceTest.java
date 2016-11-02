package com.cardpay.pbccrcReport.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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

/**
 * @author chenzhifang
 *
 * 2014-12-24上午10:49:42
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-junit.xml")
public class RhzxServiceTest {

	@Autowired
	private RhzxService rhzxService;
	
	/**
	 * 插入对外担保信息汇总
	 */
	@Test
	public void testDWDB_Info() {
		DWDB_Info dwdbInfo = new DWDB_Info();
		dwdbInfo.setCustomerId("dwdbInfo");
		dwdbInfo.setCapital("bb");
		rhzxService.insertDWDB_Info(dwdbInfo);
	}
	
	/**
	 * 插入居住信息
	 */
	@Test
	public void testJZ_Info() {
		JZ_Info jzInfo = new JZ_Info();
		jzInfo.setCustomerId("jzInfo");
		rhzxService.insertJZ_Info(jzInfo);
	}
	
	/**
	 * 插入配偶信息
	 * @param poInfo
	 * @return
	 */
	@Test
	public void testPO_Info() {
		PO_Info poInfo = new PO_Info();
		poInfo.setCustomerId("poInfo");
		rhzxService.insertPO_Info(poInfo);
	}
	
	/**
	 * 插入查询记录汇总
	 */
	@Test
	public void testQuery_Info() {
		Query_Info queryInfo = new Query_Info();
		queryInfo.setCustomerId("queryInfo");
		rhzxService.insertQuery_Info(queryInfo);
	}
	
	/**
	 * 插入未结清贷款信息汇总
	 */
	@Test
	public void testWJQDK_Info() {
		WJQDK_Info wjqdkInfo = new WJQDK_Info();
		wjqdkInfo.setCustomerId("wjqdkInfo");
		rhzxService.insertWJQDK_Info(wjqdkInfo);
	}
	
	/**
	 * 插入
	 */
	@Test
	public void testWXHDJK_Info() {
		WXHDJK_Info wxhdjkInfo = new WXHDJK_Info();
		wxhdjkInfo.setCustomerId("wxhdjkInfo");
		rhzxService.insertWXHDJK_Info(wxhdjkInfo);
	}
	
	/**
	 * 插入
	 */
	@Test
	public void testXYTS_Info() {
		XYTS_Info xytsInfo = new XYTS_Info();
		xytsInfo.setCustomerId("xytsInfo");
		rhzxService.insertXYTS_Info(xytsInfo);
	}
	
	/**
	 * 插入
	 */
	@Test
	public void testYH_Info() {
		YH_Info yhInfo = new YH_Info();
		yhInfo.setCustomerId("yhInfo");
		rhzxService.insertYH_Info(yhInfo);
	}
	
	/**
	 * 插入
	 */
	@Test
	public void testYQ_Info() {
		YQ_Info yqInfo = new YQ_Info();
		yqInfo.setCustomerId("yqInfo");
		rhzxService.insertYQ_Info(yqInfo);
	}
	
	/**
	 * 插入职业信息
	 * @param zyInfo
	 * @return
	 */
	@Test
	public void testZY_Info() {
		ZY_Info zyInfo = new ZY_Info();
		zyInfo.setCustomerId("zyInfo");
		rhzxService.insertZY_Info(zyInfo);
	}

}
