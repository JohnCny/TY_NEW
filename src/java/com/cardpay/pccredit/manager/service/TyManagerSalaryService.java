package com.cardpay.pccredit.manager.service;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.common.Arith;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.model.TyMibusidataForm;
import com.cardpay.pccredit.manager.constant.ReturnReceiptConstant;
import com.cardpay.pccredit.manager.dao.ManagerSalaryDao;
import com.cardpay.pccredit.manager.filter.ManagerCashConfigurationFilter;
import com.cardpay.pccredit.manager.filter.ManagerSalaryFilter;
import com.cardpay.pccredit.manager.model.AccountManagerParameter;
import com.cardpay.pccredit.manager.model.ManagerCashConfiguration;
import com.cardpay.pccredit.manager.model.ManagerSalary;
import com.cardpay.pccredit.manager.model.ManagerSalaryForm;
import com.cardpay.pccredit.manager.model.ManagerSalaryParameter;
import com.cardpay.pccredit.manager.model.SalaryParameter;
import com.cardpay.pccredit.manager.model.TJxParameters;
import com.cardpay.pccredit.manager.model.TJxSpecificParameters;
import com.cardpay.pccredit.manager.model.TMibusidata;
import com.cardpay.pccredit.manager.model.TPerformanceParameters;
import com.cardpay.pccredit.manager.model.TRiskMargin;
import com.cardpay.pccredit.manager.model.TRiskMarginSpecific;
import com.cardpay.pccredit.manager.model.TyPerformanceParameters;
import com.cardpay.pccredit.manager.model.TyRiskMargin;
import com.cardpay.pccredit.manager.model.TyRiskMarginSpecific;
import com.cardpay.pccredit.manager.model.TyTPerformanceParameters;
import com.cardpay.pccredit.manager.web.AccountManagerParameterForm;
import com.cardpay.pccredit.manager.web.ManagerBelongMapForm;
import com.cardpay.pccredit.manager.web.ManagerBelongMapForms;
import com.cardpay.pccredit.postLoan.model.MibusidateView;
import com.cardpay.pccredit.system.model.SystemUser;
import com.thoughtworks.xstream.io.binary.Token.Formatter;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.id.IDGenerator;
import com.wicresoft.jrad.base.database.model.QueryResult;

/**
 * @author zhengbo
 * 2016-09-01下午5:56:18
 */
@Service
public class TyManagerSalaryService {
	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private ManagerSalaryDao managerSalaryDao;
	
	@Autowired
	private ManagerCashConfigurationService managerCashConfigurationService;
	
	@Autowired
	private ManagerAssessmentScoreService managerAssessmentScoreService;
	
	@Autowired
	private ManagerPerformanceParametersService managerPerformanceParametersService;
	@Autowired
	private AccountManagerParameterService accountManagerParameterService;
	/**
	 * 过滤查询
	 * @param filter
	 * @return
	 */
	public QueryResult<ManagerSalary> findManagerSalaryByFilter(ManagerSalaryFilter filter) {
		List<ManagerSalary> list = managerSalaryDao.findManagerSalarysByFilter(filter);
		int size = managerSalaryDao.findManagerSalarysCountByFilter(filter);
		QueryResult<ManagerSalary> qs = new QueryResult<ManagerSalary>(size, list);
		return qs;
	}
	
	/**
	 * 插入客户经理薪资
	 * @param riskCustomer
	 * @return
	 */
	public String insertManagerSalary(ManagerSalary managerSalary) {
		if(managerSalary.getCreatedTime() == null){
			managerSalary.setCreatedTime(Calendar.getInstance().getTime());
		}
		if(managerSalary.getModifiedTime() == null){
			managerSalary.setModifiedTime(Calendar.getInstance().getTime());
		}
		commonDao.insertObject(managerSalary);
		return managerSalary.getId();
	}
	/**
	 * 计算客户经理月度薪资
	 * @param year
	 * @param month
	 * @return
	 */
	public boolean calculateMonthlySalary(int year, int month){
		boolean flag = true;
		try{
			Calendar calendar = Calendar.getInstance();
			calendar.set(year, month, 1);
			
			calendar.add(Calendar.MONTH, -1);
			
			year = calendar.get(Calendar.YEAR);
			month = calendar.get(Calendar.MONTH);
			managerSalaryDao.deleteManagerSalaryByYearAndMonth(year, month);
			// 获取客户经理最大层级
			int maxLevel = managerSalaryDao.getMaxManagerLevel();
			for(int i = maxLevel; i > 0; i--){
				calculateLevelSalary(year, month, i);
			}
		}catch(Exception e){
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 计算某个层级的客户经理的薪资
	 * @param year
	 * @param month
	 * @param level
	 * @return
	 */
	public void calculateLevelSalary(int year, int month, int level){
		// 
		List<SalaryParameter> list = managerSalaryDao.findSalaryParametersByFilter(level, year, month);
		Map<String, ManagerSalary> salaryMap = getManagerSalary(list, year, month);
		
		ManagerSalary managerSalary = null;
		SalaryParameter salaryParameter = null;
		for(int i = 0; i < list.size(); i++){
			salaryParameter = list.get(i);
			/*if("0000000049ea417e0149f05573f81d2e".equals(salaryParameter.getManagerId())){
				int a = 0;
			}*/
			managerSalary = salaryMap.get(salaryParameter.getManagerId());
			// 责任工资
			managerSalary.setDutySalary(salaryParameter.calculateDutySalary());
			// 津贴
			managerSalary.setAllowance(salaryParameter.getAllowance());
			// 底薪
			managerSalary.setBasePay(salaryParameter.getBasePay());
			// 返还金额(等于三年前本月存入)
			managerSalary.setReturnPrepareAmount(salaryParameter.getInsertPrepareAmount());
			
			// 管理绩效
			String managePerformance = "0";
			// 判断是否为叶子节点
			if(!salaryParameter.isLeaf()){
				// 获取客户经理管理绩效利息
				managePerformance = managerSalaryDao.getManagePerformance(managerSalary.getCustomerId(), year, month);
			}
			// 计算并设置绩效工资
			managerSalary.setRewardIncentiveInformation(salaryParameter.calculatePerformanceSalary(managePerformance));
			
			String reward = managerSalary.getRewardIncentiveInformation();
			// 计算并设置存入的风险准备金(乘以风险准备金权数)
			String insertPrepareAmount = Arith.mulReturnStr(getExtractRate(reward),reward);
			insertPrepareAmount = Arith.mulReturnStr(insertPrepareAmount,salaryParameter.getMarginExtractInfo());
			managerSalary.setInsertPrepareAmount(insertPrepareAmount);
			
			// 本月风险准备金余额  = 上月风险准备金余额  + 本月存入的风险准备金 - 返还金额(等于三年前本月存入)
			String reserveBalances = Arith.subReturnStr(Arith.addReturnStr(salaryParameter.getRiskReserveBalances(), managerSalary.getInsertPrepareAmount()), managerSalary.getReturnPrepareAmount());
			managerSalary.setRiskReserveBalances(reserveBalances);
			// 保存客户经理薪资
			insertManagerSalary(managerSalary);
		}
	}
	
	public Map<String, ManagerSalary> getManagerSalary(List<SalaryParameter> list, int year, int month){
		Map<String, ManagerSalary> hm = new HashMap<String, ManagerSalary>();
		ManagerSalary managerSalary = null;
		for(SalaryParameter salaryParameter : list){
			managerSalary = new ManagerSalary();
			managerSalary.setCustomerId(salaryParameter.getManagerId());
			managerSalary.setYear(year+"");
			managerSalary.setMonth(month+"");
			hm.put(managerSalary.getCustomerId(), managerSalary);
		}
		return hm;
	}
	
	/*
	 * 获取提取比率
	 */
	public String getExtractRate(String amount){
		ManagerCashConfigurationFilter filter = new ManagerCashConfigurationFilter();
		QueryResult<ManagerCashConfiguration> qs = managerCashConfigurationService.findManagerCashConfigurationByFilter(filter);
		for(ManagerCashConfiguration cashConfiguration : qs.getItems()){
			if(Arith.compare(amount, cashConfiguration.getStartAmount()) >= 0 
					&& Arith.compare(cashConfiguration.getEndAmount(), amount) >= 0){
				return Arith.divReturnStr(cashConfiguration.getMarginExtractInfo(), "100");
			}
		}
		return "0";
	}
	/**
	 * 得到风险保证余额
	 * @param year
	 * @param month
	 * @param id
	 * @return
	 */
	public String getReturnPrepareAmountById(int year,int month,String id){
		if(StringUtils.isNotEmpty(id)){
			return managerSalaryDao.getReturnPrepareAmountById(year, month, id);
		}else{
			return "-1";
		}
	}
	/**
	 * 得到客户经理绩效工资
	 * @param year
	 * @param month
	 * @param id
	 * @return
	 */
	public String getRewardIncentiveInformation(int year,int month,String id){
		if(StringUtils.isNotEmpty(id)){
			return managerSalaryDao.getRewardIncentiveInformation(year, month, id);
		}else{
			return "-1";
		}
	}
	
	/**
	 * 计算客户经理月度薪资(太原)
	 * @param year
	 * @param month
	 * @return
	 */
	public boolean calculateMonthlySalaryTy(int year, int month){
		boolean flag = true;
		try{
			managerSalaryDao.deleteManagerSalaryByYearAndMonth(year, month);
			//获取所有客户经理
			List<AccountManagerParameterForm> accountList = accountManagerParameterService.findAccountManagerParameterAll();
			//循环计算客户经理当月工资
			for(int i=0;i<accountList.size();i++){
				//获取此客户经理的绩效参数
				TyPerformanceParameters parameter = managerPerformanceParametersService.getParameterByLevel(accountList.get(i).getUserId());
				if(parameter!=null){
					calculateSalaryExactly(year, month,parameter,accountList.get(i).getUserId());
				}
			}
			//计算主管额外绩效
			String accountSql = "select b.* from manager_belong_map a,account_manager_parameter b where  a.child_id=b.id and a.parent_id in (select child_id from manager_belong_map where parent_id is null)";
			List<AccountManagerParameter> accountGroupList = commonDao.queryBySql(AccountManagerParameter.class, accountSql, null);
			//循环计算客户经理主管当月工资
			for(int i=0;i<accountGroupList.size();i++){
				calculateSalaryExactlyGroup(year, month,accountGroupList.get(i).getUserId());
			}
		}catch(Exception e){
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 *具体每月工资计算 （客户经理）
	 */
	private void calculateSalaryExactly(int year,int month,TyPerformanceParameters parameter ,String customerId){
		String date = "";
		if(month<10){
			date = year +"0"+month;
		}else{
			date = year +""+month;
		}
		
		//获得每月绩效参数值
		String sql = "select * from manager_salary_parameter where user_id='"+customerId+"' and month='"+date+"'";
		List<ManagerSalaryParameter> salaryList = commonDao.queryBySql(ManagerSalaryParameter.class, sql, null);
		if(salaryList.size()>0){
			ManagerSalaryParameter salaryParameter = salaryList.get(0);
			//主调绩效
			Double zdPerform = Double.parseDouble(parameter.getA()) * Double.parseDouble(salaryParameter.getZdCount());
			//辅调绩效
			Double fdPerform = Double.parseDouble(parameter.getF()) * Double.parseDouble(salaryParameter.getFdCount());
			//管户绩效
			Double ghPerform = Double.parseDouble(parameter.getB()) * Double.parseDouble(salaryParameter.getTubes());
			//审批绩效
			Double spPerform = Double.parseDouble(parameter.getD()) * Double.parseDouble(salaryParameter.getSpCount());
			//岗位绩效
			Double gwPerform = Double.parseDouble(parameter.getE());
			//完成度
			Double pet = Double.parseDouble(salaryParameter.getCompeterCount())/Double.parseDouble(parameter.getObjectCount());
			//完成绩效
			Double compet = 0.00;
			if(pet>=0.8&&pet<1){
				compet = 600.00;
			}else if(pet>=1&&pet<1.2){
				compet = 1000.00;
			}else if(pet>=1.2){
				compet = 1300.00;
			}
			String competerPet = String.format("%.2f", pet);
			Double monthPerform = zdPerform+fdPerform+ghPerform+spPerform+gwPerform+compet;
			//保存当月工资单
			ManagerSalary salary = new ManagerSalary();
			salary.setYear(year+"");
			salary.setMonth(month+"");
			salary.setCustomerId(customerId);
			salary.setBasePay(parameter.getBasicPerformance());
			salary.setCompeterPet(competerPet);
			salary.setRewardIncentiveInformation(String.valueOf(monthPerform));
			salary.setZdPerform(zdPerform+"");
			salary.setFdPerform(fdPerform+"");
			salary.setSpPerform(spPerform+"");
			salary.setGhPerform(ghPerform+"");
			salary.setGwPerform(gwPerform+"");
			salary.setCompeterPerform(compet+"");
			commonDao.insertObject(salary);
			
		}

	}
	/**
	 *具体每月工资计算 （主管）
	 */
	private void calculateSalaryExactlyGroup(int year,int month ,String customerId){
		//获取组员客户经理id
		String childSql = "select * from manager_salary where customer_id in (select user_id from ACCOUNT_MANAGER_PARAMETER where id in( SELECT a.CHILD_ID FROM MANAGER_BELONG_MAP a ,ACCOUNT_MANAGER_PARAMETER b where a.PARENT_ID=b.id and b.USER_ID='"+customerId+"')) and year='"+year+"' and month='"+month+"'";
		List<ManagerSalary> childsSalaryList = commonDao.queryBySql(ManagerSalary.class,childSql , null);
		//团队完成数
		Double groupAll=0.00;
		//团队完成比例
		Double groupAllPet=0.00;
		//主管额外绩效
		Double groupSalary =0.00;
		for(int i=0;i<childsSalaryList.size();i++){
			groupAll+=Double.parseDouble(childsSalaryList.get(i).getCompeterPet());
		}
		groupAllPet = groupAll/childsSalaryList.size();
		if(groupAllPet>=0.8){
			groupSalary = 1000.00;
		}else{
			groupSalary = 500.00;
		}
		List<ManagerSalary> groupSalaryList = commonDao.queryBySql(ManagerSalary.class,"select * from manager_salary where customer_id='"+customerId+"' and  year='"+year+"' and month='"+month+"'" , null);
		if(groupSalaryList.size()>0){
			ManagerSalary group = groupSalaryList.get(0);
			group.setGroupSalary(groupSalary+"");
			group.setRewardIncentiveInformation(Double.parseDouble(group.getRewardIncentiveInformation())+groupSalary+"");
			commonDao.updateObject(group);
		}
	}

	
	/*
	 * 逾期扣款比例
	 */
	private double getOverLoanPer(double overdueLoan){
		if(overdueLoan==0){
			return 0;
		}else if(overdueLoan<=0.005){
			return 0.05;
		}else if(overdueLoan>0.005&&overdueLoan<=0.01){
			return 0.1;
		}else if(overdueLoan>0.01&&overdueLoan<=0.015){
			return  0.2;
		}else{
			return 0.3;
		}
	}
	/*
	 * 新增风险保证金计算
	 */
	private double getAddRisk(double monthPerform){
		double addRisk = 0;
		if(monthPerform<=2000){
			return addRisk;
		}else if(monthPerform>2000&&monthPerform<=5000){
			addRisk = (monthPerform-2000)*0.1;
		}else{
			addRisk=3000*0.1+(monthPerform-5000)*0.2;
		}
		return addRisk;
	}
	
	/*
	 * 返还风险保证金
	 */
	private double outRiskMargin(int year,int month,String customerId){
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, 1);
		
		calendar.add(Calendar.MONTH, -18);
		//获取18个月前的时间
		int  year_18 = calendar.get(Calendar.YEAR);
		int month_18 = calendar.get(Calendar.MONTH);
		//查询18月前风险保证金
		TyRiskMarginSpecific specific = getRiskMarginSpecific(year_18,month_18,customerId);
		if(specific==null){
			return 0;
		}else{
			return (Double.parseDouble(specific.getInRiskMargin())-Double.parseDouble(specific.getDeductRiskMargin()))>0?Double.parseDouble(specific.getInRiskMargin())-Double.parseDouble(specific.getDeductRiskMargin()):0;
		}
	}
	
	/*
	 * 根据年月查询风险保证金log
	 */
	public TyRiskMarginSpecific getRiskMarginSpecific(int year,int month,String customerId){
		String sql ="select * from ty_risk_margin_specific where risk_id in (select id from ty_risk_margin where customer_id='"+customerId+"') and year='"+year+"' and month='"+month+"'";
		List<TyRiskMarginSpecific> tyRiskMarginSpecifics = commonDao.queryBySql(TyRiskMarginSpecific.class, sql, null);
		if(tyRiskMarginSpecifics.size()>0){
			return tyRiskMarginSpecifics.get(0);
		}else{
			return null;
		}
	}
	/*
	 * 根据customerId获取总风险保证金
	 */
	public TyRiskMargin getRiskMarginByCustomerId(String customerId){
		String sql = "select * from ty_risk_margin where customer_id='"+customerId+"'";
		List<TyRiskMargin> tyRiskMarginsList = commonDao.queryBySql(TyRiskMargin.class, sql, null);
		if(tyRiskMarginsList.size()>0){
			return tyRiskMarginsList.get(0);
		}else{
			return null;
		}
	}
	/*
	 * 绩效导出
	 */
	public void getExportData(ManagerSalaryFilter filter,HttpServletResponse response) throws Exception{
		List<ManagerSalary> salaryList = managerSalaryDao.findManagerSalarys(filter);
		
		// 第一步，创建一个webbook，对应一个Excel文件  
        HSSFWorkbook wb = new HSSFWorkbook();  
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        HSSFSheet sheet = wb.createSheet("客户经理绩效详情");  
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
        HSSFRow row = sheet.createRow((int) 0);  
        // 第四步，创建单元格，并设置值表头 设置表头居中  
        HSSFCellStyle style = wb.createCellStyle();  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        HSSFCell cell = row.createCell((short) 0);  
        cell.setCellValue("年份");  
        cell.setCellStyle(style);  
        cell = row.createCell((short) 1);  
        cell.setCellValue("月份");  
        cell.setCellStyle(style);  
        cell = row.createCell((short) 2);  
        cell.setCellValue("小微团队");  
        cell.setCellStyle(style);  
        cell = row.createCell((short) 3);  
        cell.setCellValue("客户经理");  
        cell.setCellStyle(style);
        cell = row.createCell((short) 4);  
        cell.setCellValue("底薪");  
        cell.setCellStyle(style);
        cell = row.createCell((short) 5);  
        cell.setCellValue("主管绩效");  
        cell.setCellStyle(style);
        cell = row.createCell((short) 6);  
        cell.setCellValue("主调绩效");  
        cell.setCellStyle(style);
        cell = row.createCell((short) 7);  
        cell.setCellValue("辅调绩效");  
        cell.setCellStyle(style);
        cell = row.createCell((short) 8);  
        cell.setCellValue("管户绩效");  
        cell.setCellStyle(style);
        cell = row.createCell((short) 9);  
        cell.setCellValue("审批绩效");  
        cell.setCellStyle(style);
        cell = row.createCell((short) 10);  
        cell.setCellValue("岗位绩效");  
        cell.setCellStyle(style);
        cell = row.createCell((short) 11);  
        cell.setCellValue("完成度绩效");  
        cell.setCellStyle(style);
        cell = row.createCell((short) 12);  
        cell.setCellValue("总绩效");  
        cell.setCellStyle(style);
        for(int i=0;i<salaryList.size();i++){
        	row = sheet.createRow((int) i + 1);
        	ManagerSalary salary = salaryList.get(i);
        	row.createCell((short) 0).setCellValue((String) salary.getYear());  
        	row.createCell((short) 1).setCellValue((String) salary.getMonth());  
        	row.createCell((short) 2).setCellValue((String) salary.getShortName());  
        	row.createCell((short) 3).setCellValue((String) salary.getManagerName());  
        	row.createCell((short) 4).setCellValue((String) salary.getBasePay());  
        	row.createCell((short) 5).setCellValue((String) salary.getGroupSalary());  
        	row.createCell((short) 6).setCellValue((String) salary.getZdPerform());  
        	row.createCell((short) 7).setCellValue((String) salary.getFdPerform());  
        	row.createCell((short) 8).setCellValue((String) salary.getGhPerform());  
        	row.createCell((short) 9).setCellValue((String) salary.getSpPerform());  
        	row.createCell((short) 10).setCellValue((String) salary.getGwPerform());  
        	row.createCell((short) 11).setCellValue((String) salary.getCompeterPerform());  
        	row.createCell((short) 12).setCellValue((String) salary.getRewardIncentiveInformation());  
        }
        response.setHeader("Connection", "close");
        response.setHeader("Content-Type", "application/vnd.ms-excel;charset=GBK");
        response.setHeader("Content-Disposition", "attachment;filename="                + new String("客户经理绩效详情.xls".getBytes(), "iso-8859-1"));
        OutputStream out = response.getOutputStream();  
        wb.write(out);
        
        out.close();
	}
	
	
	//----------------------------------------------济南绩效start----------------------------------------------------//
	
	public QueryResult<ManagerSalaryForm> findManagerSalaryByFilterJn(ManagerSalaryFilter filter) {
		List<ManagerSalaryForm> list = managerSalaryDao.findManagerSalarysByFilterJn(filter);
		int size = managerSalaryDao.findManagerSalarysCountByFilterJn(filter);
		QueryResult<ManagerSalaryForm> qs = new QueryResult<ManagerSalaryForm>(size, list);
		return qs;
	}
	
	public ManagerSalary findManagerSalaryById(String id) {
		ManagerSalary salary = commonDao.findObjectById(ManagerSalary.class, id);
		SystemUser user = commonDao.findObjectById(SystemUser.class,salary.getCustomerId());
		salary.setManagerName(user.getDisplayName());
		return salary;
	}
	

	public int updateManagerSalary(ManagerSalary salary) {
		return commonDao.updateObject(salary);
	}
	
	/**
	 * 计算当月绩效
	 * 济南绩效计算方法:客户经理应发薪酬 = 基本薪酬 + 贷款业务绩效 + 其他业务绩效 - 风险保证金
	 * 
	 * 
	 * 太原绩效计算方法：绩效工资=150×A+20×B+10×C+D+100×E+F-G
	 * A：客户经理当月发放贷款的绩效
	 * B：客户经理管户绩效
	 * D: 客户经理等级岗位绩效
	 * E：客户经理辅助调查绩效 
	 * F：客户经理任务完成度绩效
	 * G：逾期比率超过容忍度的处罚标准
	 * 
	 * @param year
	 * @param month
	 */
	public void docalculateMonthlySalaryTy(String year,String month,String managerNum) {
		
		// 判断该月客户经理月度薪资是否已经存在
		int count = managerSalaryDao.findManagerSalaryCount(year, month);
		if (count > 0) {
			throw new RuntimeException("该月客户经理薪资已存在！");
		}

		// 查询行编以及外聘客户经理list
		List<AccountManagerParameter> list = commonDao
				.queryBySql(
						AccountManagerParameter.class,
						"select * from account_manager_parameter where manager_type in ('1','2')",
						null);

		/*// 查询风险岗list
		List<AccountManagerParameter> alist = commonDao
				.queryBySql(
						AccountManagerParameter.class,
						"select * from account_manager_parameter where manager_type in ('3')",
						null);*/

		// 生成 T_JX_PARAMETERS表数据
		for (AccountManagerParameter accountManagerParameter : list) {
			generateJxParameters(accountManagerParameter.getUserId(), year,month);
		}

		/*// 生成 T_JX_SPECIFIC_PARAMETERS表数据
		for (AccountManagerParameter accountManagerParameter : list) {
			generateJxSpecificParameters(accountManagerParameter.getUserId(),year, month);
		}*/

		BigDecimal zhMonthPerformance=BigDecimal.valueOf(0); //各客户经理总绩效用于算后台平均绩效初始值为0
		BigDecimal MonthPerformance1=BigDecimal.valueOf(0); //一区客户经理总绩效  //用于计算各区域经理绩效初始值为0
		BigDecimal MonthPerformance2=BigDecimal.valueOf(0);	//二区客户经理总绩效初始值为0
		BigDecimal MonthPerformance3=BigDecimal.valueOf(0);  //三区客户经理总绩效初始值为0
		// 具体计算行编以及外聘客户经理的当月工资
		for (AccountManagerParameter accountManagerParameter : list) {
			if(accountManagerParameter.getManagerType().equals("1")){
			Map<String, Object>dcsmap=doCalculateSalaryExactly(year, month,
					accountManagerParameter.getUserId(),
					accountManagerParameter.getBasePay(),
					accountManagerParameter.getManagerType());
			if(dcsmap.get("organName").equals("一区")){
				MonthPerformance1=new BigDecimal(dcsmap.get("MonthPerformance").toString())
					.add(MonthPerformance1);
			}
			if(dcsmap.get("organName").equals("二区")){
				MonthPerformance2=new BigDecimal(dcsmap.get("MonthPerformance").toString())
					.add(MonthPerformance2);
			}if(dcsmap.get("organName").equals("三区")){
				MonthPerformance3=new BigDecimal(dcsmap.get("MonthPerformance").toString())
				.add(MonthPerformance3);
			}
			zhMonthPerformance=new BigDecimal(dcsmap.get("MonthPerformance").toString()).add(zhMonthPerformance);
			System.out.println("create success!");
			}
		}

		// 具体计算后台运营岗的当月工资
		for(AccountManagerParameter accountManagerParameter : list) {
			if(accountManagerParameter.getManagerType().equals("6")){
				doBackgroundoperationSalary(year, month,managerNum,zhMonthPerformance,
						accountManagerParameter.getUserId(),
						accountManagerParameter.getBasePay(),
						accountManagerParameter.getManagerType());
			}
		}
				
		// 具体计算管理层人员的当月工资
		for (AccountManagerParameter accountManagerParameter : list) {
			if(accountManagerParameter.getManagerType()=="2"||			// 部门主管
					accountManagerParameter.getManagerType()=="3"||		//机构主管
					accountManagerParameter.getManagerType()=="7"){ 	//副机构主管
			doManagermentSalary(year, month, accountManagerParameter.getUserId(),
					accountManagerParameter.getBasePay(),
					accountManagerParameter.getManagerType(),
					zhMonthPerformance,MonthPerformance1,MonthPerformance2,MonthPerformance3);
			}
		}
	}
	
	/**
	 * 具体计算 管理岗绩效
	 * @param year 年份
	 * @param month  月份
	 * @param userId 客户经理id
	 * @param basePay 基本工资
	 * @param managerType 客户经理类型
	 * @param monthPerformance3  三区客户经理 总绩效
	 * @param monthPerformance2  二区客户经理总绩效
	 * @param monthPerformance1  一区客户经理总绩效
	 * @param zhMonthPerformance 所有客户经理总绩效
	 * @return 
	 */
	private void doManagermentSalary(String year, String month, String userId,
			String basePay, String managerType, BigDecimal zhMonthPerformance, BigDecimal monthPerformance1, BigDecimal monthPerformance2, BigDecimal monthPerformance3) {
		// TODO Auto-generated method stub
		//查询客户所属机构
		String organName = managerSalaryDao.getOrganName(userId);
		
		//1.计算基本工资
		Map<String, Object> basemap = managerBasePay(year,month,userId);
		
		if(managerType.equals("2")){   //辖区域客户经理平均绩效*岗位系数+所辖区域任务完成度绩效-所辖区域不良贷款超过容忍度的绩效处罚
			int managernum=managerSalaryDao.findmanagernum(organName);
			if(organName.equals("一区")){
				//辖区域客户经理平均绩效*岗位系数
				BigDecimal departmanagersjx=monthPerformance1.divide(BigDecimal.valueOf(managernum),2, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(2.5));
				//所辖区域任务完成度绩效
			 	BigDecimal rw=findrw(managernum,organName,year,month);
				//所辖区域不良贷款超过容忍度的绩效处罚
			 	BigDecimal G=findbl(organName); //扣款比率
			 	BigDecimal blje=departmanagersjx.add(rw).multiply(G);
			 	
			 	zhMonthPerformance=new BigDecimal(basemap.get("zhbasepay").toString()).add(departmanagersjx)
			 			.add(rw).subtract(blje);
			 	
			 	BigDecimal zhMonthPerformance1=new BigDecimal(basemap.get("zhbasepay").toString()).add(departmanagersjx)
			 			.add(rw).multiply(BigDecimal.valueOf(1).subtract(G));
			 // 保存当月工资单
				ManagerSalary salary = new ManagerSalary();
				salary.setYear(year);
				salary.setMonth(month);
				salary.setInstCode(organName);//所属机构
				salary.setCustomerId(userId);//客户经理
				salary.setBasePay(basePay);//固定工资
				salary.setZhbasepay(basemap.get("zhbasepay").toString());
				salary.setMonthPerformance(zhMonthPerformance.toString());
				managerSalaryDao.deleteSalarybyuserid(userId);
				commonDao.insertObject(salary);
			 	
			}
		}
		if(managerType.equals("3")){
					
		}
		if(managerType.equals("7")){
			
		}
		
		
	}

	private BigDecimal findbl(String organName) {
		// TODO Auto-generated method stub
		BigDecimal G=new BigDecimal(0);
	 	String managerId=null;
		BigDecimal bl=new BigDecimal(findblsum(managerId,organName));
		BigDecimal Dkye=new BigDecimal(findDkyesum(managerId,organName));
		BigDecimal lv=bl.divide(Dkye,2, BigDecimal.ROUND_HALF_UP);
		if(lv.compareTo(BigDecimal.valueOf(0.01))==-1||lv.compareTo(BigDecimal.valueOf(0.01))==0){ //小于或者d等于0.01的 
			G=BigDecimal.valueOf(0);
		}
		if(lv.compareTo(BigDecimal.valueOf(0.01))==1){ //大于0.01的 
			if(lv.compareTo(BigDecimal.valueOf(0.015))==0||lv.compareTo(BigDecimal.valueOf(0.015))==-1){ //小于或者等于0.015的
				G=BigDecimal.valueOf(0.3);
			}
		}
		if(lv.compareTo(BigDecimal.valueOf(0.015))==1){ //大于0.015的 
			if(lv.compareTo(BigDecimal.valueOf(0.02))==0||lv.compareTo(BigDecimal.valueOf(0.02))==-1){ //小于或者等于0.02的
				G=BigDecimal.valueOf(0.5);
			}
		}
		if(lv.compareTo(BigDecimal.valueOf(0.02))==1){ //大于0.02的
			G=BigDecimal.valueOf(1);
		}
		return G;
	}

	private BigDecimal findrw(int managernum, String organName, String year, String month) {
		// TODO Auto-generated method stub
		BigDecimal a=new BigDecimal(0);
		String rwlists=managerSalaryDao.findrwjebyorganName(organName,year,month);
		BigDecimal reqlmtsum=new BigDecimal(rwlists); //区域内总贷款
		BigDecimal zrw=BigDecimal.valueOf(100).multiply(BigDecimal.valueOf(managernum));//区域内总任务
		BigDecimal rwwcl=reqlmtsum.divide(zrw,2, BigDecimal.ROUND_HALF_UP);
		if(rwwcl.compareTo(BigDecimal.valueOf(0.6))==-1){ //小于0.6
			a=BigDecimal.valueOf(0);
		}
		if(rwwcl.compareTo(BigDecimal.valueOf(0.6))==0||rwwcl.compareTo(BigDecimal.valueOf(0.6))==1){ //大于或等于0.6
			if(rwwcl.compareTo(BigDecimal.valueOf(0.8))==-1){ //小于 0.8
				a=BigDecimal.valueOf(1000);
			}
		}
		if(rwwcl.compareTo(BigDecimal.valueOf(0.8))==0||rwwcl.compareTo(BigDecimal.valueOf(0.8))==1){ //大于或等于0.8
			if(rwwcl.compareTo(BigDecimal.valueOf(1))==-1){ //小于 1
				a=BigDecimal.valueOf(2000);
			}
		}
		if(rwwcl.compareTo(BigDecimal.valueOf(1))==0||rwwcl.compareTo(BigDecimal.valueOf(1))==1){ //大于或等于1
			if(rwwcl.compareTo(BigDecimal.valueOf(1.1))==-1){ //小于 1.1
				a=BigDecimal.valueOf(3000);
			}
		}
		if(rwwcl.compareTo(BigDecimal.valueOf(1.1))==0||rwwcl.compareTo(BigDecimal.valueOf(1.1))==1){ //大于或等于1
			if(rwwcl.compareTo(BigDecimal.valueOf(1.2))==-1){ //小于 1.1
				a=BigDecimal.valueOf(4000);
			}
		}
		if(rwwcl.compareTo(BigDecimal.valueOf(1.2))==1){ //大于或等于1
			a=BigDecimal.valueOf(5000);
		}
		return a;
	}

	private void doBackgroundoperationSalary(String year, String month,
			String managerNum, BigDecimal zhMonthPerformance, String ManagerId,
			String basePay, String managerType) {
		// TODO Auto-generated method stub
		//查询客户所属机构
				String organName = managerSalaryDao.getOrganName(ManagerId);
		//1.计算基本工资
				Map<String, Object> basemap = managerBasePay(year,month,ManagerId);
				
		//2.计算后台绩效  第八条后台运营岗绩效  后台运营岗绩效工资=客户经理的平均绩效*80% 
		//注：客户经理平均绩效=客户经理绩效总和÷客户经理总数
		BigDecimal htjx=zhMonthPerformance.divide(new BigDecimal(managerNum),
				   2, BigDecimal.ROUND_HALF_UP);
		
		// 保存当月工资单
		ManagerSalary salary = new ManagerSalary();
		salary.setYear(year);
		salary.setMonth(month);
		salary.setInstCode(organName);//所属机构
		salary.setCustomerId(ManagerId);//客户经理
		salary.setBasePay(basePay);//固定工资
		salary.setMonthPerformance(htjx+""); //当月后台绩效
	}

	/**
	 * 具体计算
	 * @param year 年份
	 * @param month  月份
	 * @param ManagerId 客户经理id
	 * @param basePay 基本工资
	 * @param managerType 客户经理类型
	 * @return 
	 */
	private Map<String, Object> doCalculateSalaryExactly(String year,String month,String ManagerId,String basePay,String managerType){
		//查询客户所属机构
		String organName = managerSalaryDao.getOrganName(ManagerId);
		
		//1.计算客户经理基本工资
		Map<String, Object> basemap = managerBasePay(year,month,ManagerId);
		
		// 2.计算贷款业务绩效
		Map<String, Object> map = doCalLoanPerformance(year,month,ManagerId);
		
		
		
		
		// 保存当月工资单
				ManagerSalary salary = new ManagerSalary();
				salary.setYear(year);
				salary.setMonth(month);
				salary.setInstCode(organName);//所属机构
				salary.setCustomerId(ManagerId);//客户经理
				salary.setBasePay(basePay);//固定工资
				salary.setZhbasepay(basemap.get("zhbasepay").toString());
				salary.setMonthPerformance(map.get("MonthPerformance").toString());
				salary.setZb(map.get("zb").toString());
				salary.setXb(map.get("xb").toString());
				salary.setDyffdkjx(map.get("dyffdkjx").toString());
				salary.setGh(map.get("gh").toString());
				salary.setSp(map.get("sp").toString());
				salary.setDj(map.get("dj").toString());
				//salary.setFd(map.get("fd").toString());
				salary.setRw(map.get("rw").toString());
				salary.setBl(map.get("bl").toString());
				salary.setZg(map.get("zg").toString());
				managerSalaryDao.deleteSalarybyuserid(ManagerId);
				commonDao.insertObject(salary);
				
		//返回客户经理当月的绩效工资便于后台人员的绩效工资的计算
		Map<String, Object> dcsmap = new HashMap<String, Object>();
		dcsmap.put("organName", organName);
		dcsmap.put("MonthPerformance", map.get("MonthPerformance"));
		return dcsmap;
	}
	
	private Map<String, Object> managerBasePay(String year, String month,
			String managerId) {
		// TODO Auto-generated method stub
		List<AccountManagerParameter> list = commonDao.queryBySql(
						AccountManagerParameter.class,
						"select * from account_manager_parameter where USER_ID='"+managerId+"'",
						null);
		BigDecimal a=new BigDecimal(list.get(0).getBasePay());//基本保障工资
		BigDecimal b=new BigDecimal(list.get(0).getFoodSubsidy());//餐补
		BigDecimal c=new BigDecimal(list.get(0).getTravelAllowance());//交通补
		BigDecimal d=new BigDecimal(list.get(0).getPhoneAllowance());//通讯补
		BigDecimal e=new BigDecimal(list.get(0).getAgeAllowance());//工龄补
		BigDecimal f=new BigDecimal(list.get(0).getEducationAllowance());//学历补贴
		BigDecimal zhbasepay=a.add(b).add(c).add(d).add(e).add(f);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("zhbasepay", zhbasepay);
		return map;
	}

	/**
	 * 计算贷款业务绩效
	 */
	public Map<String, Object> doCalLoanPerformance(String year,String month,String ManagerId){
		// 查询客户经理绩效参数
		TyTPerformanceParameters parameters = commonDao.queryBySql(TyTPerformanceParameters.class,"select * from T_PERFORMANCE_PARAMETERS ",null).get(0);
		
		// 查询客户经理绩效每月绩效参数表 
		TJxParameters jxParameters = findTJxParameters(year,month,ManagerId);
		
		//查询客户经理客户经理参数信息
		List<AccountManagerParameter> AMPlist = commonDao.queryBySql(
				AccountManagerParameter.class,
				"select * from account_manager_parameter where USER_ID='"+ManagerId+"'",
				null);
		
		/*// 查询客户经理详细绩效参数 主要是客户日均贷款余额 产品利率等
		List<TJxSpecificParameters> list = findTJxSpecificParametersList(year,month,ManagerId);*/
		
		//1、客户经理当月发放贷款的笔数绩效（主办150×A 协办100xB）
		//放贷金额与收益相匹配的原则，客户经理当月放贷款的实际笔数将根据发放贷款金额大小进行折算
		updateJxParameters(ManagerId,year,month);
		BigDecimal zb  = new BigDecimal(jxParameters.getMonthLoanNum()).multiply(new BigDecimal(parameters.getA()));//主办  
		BigDecimal xb  = new BigDecimal(jxParameters.getMonthTimes()).multiply(new BigDecimal(parameters.getB()));//协办  
		BigDecimal dyffdkjx = zb.add(xb);
		
		//2、客户经理当前管户户数绩效(20×B)
		BigDecimal gh=new BigDecimal(jxParameters.getMonthEffectNum()).multiply(new BigDecimal(parameters.getD()));
		
		//3、客户经理当月承担审批的贷款笔数绩效(10×C)
		BigDecimal sp=new BigDecimal(jxParameters.getMonthApprovalNum()).multiply(new BigDecimal(parameters.getC()));
		
		// 4、客户经理等级岗位绩效（D）
		String levelInformation=AMPlist.get(0).getLevelInformation();
		//int levelInformationjx=findlevelInformationjxbyManagerId(ManagerId,levelInformation);
		BigDecimal dj=new BigDecimal(findlevelInformationjxbyManagerId(ManagerId,levelInformation));
		
		//5、客户经理辅助调查的笔数绩效（100×E）此处所计算笔数为贷款自然笔数  (协办就是辅调 只拿一笔绩效)
		//BigDecimal fd=new BigDecimal(jxParameters.getMonthAcasiNum()).multiply(new BigDecimal(parameters.getK()));
		
		//6、客户经理任务完成度绩效(F)   询问后台人员全部按照正式员工完成度绩效
		BigDecimal rw=findfwbyManagerId(ManagerId,year,month);
		
		//7、不良贷款比率超过容忍度的处罚标准（G） 不良贷款比率(指不良贷款本息金额/维护贷款余额) blje-被扣掉的绩效
		BigDecimal G=findG(ManagerId);
		BigDecimal blje=(dyffdkjx.add(gh).add(sp).add(dj)
				.add(rw)).multiply(G);  
		
		// 8.当月贷款业务绩效    	绩效工资=150×A+20×B+10×C+D+100×E+F-G
		BigDecimal MonthPerformance=(dyffdkjx.add(gh).add(sp).add(dj)
				.add(rw)).multiply(new BigDecimal("1").subtract(G));
		BigDecimal MonthPerformance1=dyffdkjx.add(gh).add(sp).add(dj)
				.add(rw).subtract(blje);
		
		//第九条审贷会委员绩效 10/笔(统计当月承担审批的贷款笔数绩效相同)
		//BigDecimal sdwjx=null;
		
		//第十条业务主管绩效(客户经理主管)    
		BigDecimal zg=new BigDecimal(0);
		List<ManagerBelongMapForms> findxzz=managerSalaryDao.findchildrenbymanagerid(ManagerId);
		if(findxzz.size()>0){
			if(findxzz.get(0).getUserType().toString().equals("1")){
				int findzzs=managerSalaryDao.findchildrenbyparentid(findxzz.get(0).getChildId());
				if(findzzs==0){
					zg=findzgjx(findxzz,ManagerId,year,month,findxzz.size());
					MonthPerformance=MonthPerformance.add(zg);   //业务主管的绩效=I+1000*（1-G2-G3）
				}
			}
		}
		
		// map
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("MonthPerformance", MonthPerformance);
		map.put("zb", zb);
		map.put("xb", xb);
		map.put("dyffdkjx", dyffdkjx);
		map.put("gh", gh);
		map.put("sp", sp);
		map.put("dj", dj);
		//map.put("fd", fd);
		map.put("rw", rw);
		map.put("bl", blje);
		map.put("zg", zg);
		return map;
	}

	private BigDecimal findzgjx(List<ManagerBelongMapForms> findxzz,String ManagerId,
			String year, String month,  int size) {
		// TODO Auto-generated method stub
		//zchildreqlmt该客户经理手下及自己的客户当月贷款的数目总和
		BigDecimal zchildreqlmt=new BigDecimal("0");
		BigDecimal zbl=new BigDecimal("0");
		BigDecimal zDkye=new BigDecimal("0");
		BigDecimal G2=null;
		BigDecimal G3=null;
		BigDecimal zg=null;
		BigDecimal G=null;
		
		for (ManagerBelongMapForms child : findxzz) {
			String sql= " select  nvl(sum(a.reqlmt),0) as HYK from ty_mibusidata a where 1=1 and             "+
						"	 a.userid='"+child.getUserid()+"' and      "+
						"	 substr(a.loandate, '0', '4') = '"+year+"' and            "+
						"	substr(a.loandate, '5', '2') = '"+month+"' ";
		List<HashMap<String, Object>> list = commonDao.queryBySql(sql, null);
		BigDecimal b = (BigDecimal) list.get(0).get("HYK");
		zchildreqlmt=b.add(zchildreqlmt);
		
		String organName=null;
		BigDecimal bl=new BigDecimal(findblsum(child.getUserid(),organName));
		zbl=bl.add(zbl);
		BigDecimal Dkye=new BigDecimal(findDkyesum(child.getUserid(),organName));
		zDkye=Dkye.add(zDkye);
		}
		String sql= " select  nvl(sum(a.reqlmt),0) as HYK from ty_mibusidata a where 1=1 and             "+
				"	 a.userid='"+ManagerId+"' and      "+
				"	 substr(a.loandate, '0', '4') = '"+year+"' and            "+
				"	substr(a.loandate, '5', '2') = '"+month+"' ";
					List<HashMap<String, Object>> list = commonDao.queryBySql(sql, null);
					BigDecimal b1 = (BigDecimal) list.get(0).get("HYK");
		zchildreqlmt=zchildreqlmt.add(b1);
		//zrw 该客户经理及手下客户经理的目标总和
		BigDecimal zrw=new BigDecimal(findxzz.size()+1).multiply(new BigDecimal("100"));
		G=zchildreqlmt.divide(zrw,2,BigDecimal.ROUND_HALF_UP);
		if(G.compareTo(BigDecimal.valueOf(0.8))==-1){ 			//80%以下的
			G2=new BigDecimal(0.5);
		}else{
			G2=new BigDecimal(0);
		}
		
		String organName=null;
		zbl=zbl.add(new BigDecimal(findblsum(ManagerId,organName)));
		zDkye=zDkye.add(new BigDecimal(findDkyesum(ManagerId,organName)));
	    G=zbl.divide(zDkye,2, BigDecimal.ROUND_HALF_UP);
		if(G.compareTo(BigDecimal.valueOf(0.015))==-1||G.compareTo(BigDecimal.valueOf(0.015))==0){
			G3=new BigDecimal(0);
		}
		if(G.compareTo(BigDecimal.valueOf(0.015))==1){
			if(G.compareTo(BigDecimal.valueOf(0.02))==-1||G.compareTo(BigDecimal.valueOf(0.02))==0){
				G3=new BigDecimal(0.3);
			}
		}
		if(G.compareTo(BigDecimal.valueOf(0.02))==1){
			if(G.compareTo(BigDecimal.valueOf(0.025))==-1||G.compareTo(BigDecimal.valueOf(0.025))==0){
				G3=new BigDecimal(0.5);
			}
		}
		if(G.compareTo(BigDecimal.valueOf(0.025))==1){
			G3=new BigDecimal(1);
		}
		
		G=new BigDecimal(1).subtract(G2).subtract(G3);
		if(G.compareTo(BigDecimal.valueOf(0))==-1){
			G=new BigDecimal(0);
		}
		zg=new BigDecimal(1000).multiply(G);
		return zg;
	}

	private BigDecimal findG(String managerId) {
		// TODO Auto-generated method stub
		BigDecimal G=null;
		String organName=null;
		BigDecimal bl=new BigDecimal(findblsum(managerId,organName));
		BigDecimal Dkye=new BigDecimal(findDkyesum(managerId,organName));
		BigDecimal lv=bl.divide(Dkye,2, BigDecimal.ROUND_HALF_UP);
		if(lv.compareTo(BigDecimal.valueOf(0.015))==-1||lv.compareTo(BigDecimal.valueOf(0.015))==0){  //小于或者等于1.5%的
			G=new BigDecimal("0");
		}
		if(lv.compareTo(BigDecimal.valueOf(0.015))==1){ //大于1.5%的
			if(lv.compareTo(BigDecimal.valueOf(0.02))==-1||lv.compareTo(BigDecimal.valueOf(0.02))==0){ //小于或者等于0.2
				G=new BigDecimal("0.3");
			}
		}
		if(lv.compareTo(BigDecimal.valueOf(0.02))==1){ //大于0.02
			if(lv.compareTo(BigDecimal.valueOf(0.025))==-1||lv.compareTo(BigDecimal.valueOf(0.025))==0){ //小于或者等于0.025的
				G=new BigDecimal("0.5");
			}
		}
		if(lv.compareTo(BigDecimal.valueOf(0.025))==1){ //大于0.025的绩效扣光
			G=new BigDecimal("1");
		}
		return G;
	}

	private String findDkyesum(String managerId, String organName) {
		// TODO Auto-generated method stub
		return managerSalaryDao.findDkyesum(managerId,organName);
	}

	private String findblsum(String managerId, String organName) {
		// TODO Auto-generated method stub
		return managerSalaryDao.findblsum(managerId,organName);
	}

	private BigDecimal findfwbyManagerId(String managerId, String year, String month) {
		// TODO Auto-generated method stub
		BigDecimal a=new BigDecimal(0);
		String rwlists=managerSalaryDao.findrwjebyManagerId(managerId,year,month);
		if(rwlists==null||rwlists==""){ //如果客户经理当月没有贷款的话当月贷款总额为0
			rwlists="0";
		}
		BigDecimal reqlmtsum=new BigDecimal(rwlists);
		BigDecimal rwwcl=reqlmtsum.divide(new BigDecimal("100"),2, BigDecimal.ROUND_HALF_UP);
		if(rwwcl.compareTo(BigDecimal.valueOf(0.80))==-1){		//小于0.8
			a=new BigDecimal("0");
		}
		if(rwwcl.compareTo(BigDecimal.valueOf(0.80))==1||rwwcl.compareTo(BigDecimal.valueOf(0.8))==0){//大于或0.8
			if(rwwcl.compareTo(BigDecimal.valueOf(1))==-1){    //小于1 
				a=new BigDecimal("600");
			}
		}
		if(rwwcl.compareTo(BigDecimal.valueOf(1))==1||rwwcl.compareTo(BigDecimal.valueOf(1))==0){//大于或0.8
			if(rwwcl.compareTo(BigDecimal.valueOf(1.2))==-1){ 	//小于1.2
				a=new BigDecimal("1000");
			}
		}
		if(rwwcl.compareTo(BigDecimal.valueOf(1.2))==1){		//大于1.2
			a=new BigDecimal("1300");
		}
		return a;
	}

	private int findlevelInformationjxbyManagerId(String managerId,
			String levelInformation) {
		// TODO Auto-generated method stub
		int a =0;
		switch (levelInformation) {
		case "MANA001": a=0; break;
		case "MANA002": a=400; break;
		case "MANA003": a=800; break;
		case "MANA004": a=1200; break;
		case "MANA005": a=2000; break;
		default: break;
		}
		return a;
	}

	//放贷金额与收益相匹配的原则修改放款户数
	private void updateJxParameters(String managerId, String year, String month) {
		// TODO Auto-generated method stub
		List<TyMibusidataForm>lists=managerSalaryDao.findffjebyManagerId(managerId,year,month);
		int zhmonthLoanNum=0;
		for (TyMibusidataForm tyMibusidataForm : lists) {
			int monthLoanNum=0;
			BigDecimal ffje=new BigDecimal(tyMibusidataForm.getREQLMT());
			//小于10wan大于0.5万的折算一笔
			if(ffje.compareTo(BigDecimal.valueOf(100000))==-1){ //如果小于10万
				if(ffje.compareTo(BigDecimal.valueOf(5000))==1||ffje.compareTo(BigDecimal.valueOf(5000))==0){ //如果大于或等于5000
					monthLoanNum=1;
				}
			}
			//小于30万 大于10万的折算两笔
			if(ffje.compareTo(BigDecimal.valueOf(300000))==-1){ //如果小于30万
				if(ffje.compareTo(BigDecimal.valueOf(100000))==1||ffje.compareTo(BigDecimal.valueOf(100000))==0){ //如果大于或等于10万
					monthLoanNum=2;
				}
			}
			//小于50万 大于30万的折算3笔
			if(ffje.compareTo(BigDecimal.valueOf(500000))==-1){ //如果小于50万
				if(ffje.compareTo(BigDecimal.valueOf(300000))==1||ffje.compareTo(BigDecimal.valueOf(300000))==0){ //如果大于或等于30万
					monthLoanNum=3;
				}
			}
			//小于100万 大于50万的折算4笔
			if(ffje.compareTo(BigDecimal.valueOf(1000000))==-1){ //如果小于100万或者等于100万的
				if(ffje.compareTo(BigDecimal.valueOf(500000))==1||ffje.compareTo(BigDecimal.valueOf(500000))==0){ //如果大于或等于50万
					monthLoanNum=4;
				}
			}
			//100万以上的折算6笔
			if(ffje.compareTo(BigDecimal.valueOf(1000000))==1||ffje.compareTo(BigDecimal.valueOf(1000000))==0){ //如果大于或等于100万
				monthLoanNum=6;
			}
			zhmonthLoanNum+=monthLoanNum;
		}
		managerSalaryDao.updateTJxParameters(Integer.toString(zhmonthLoanNum),managerId);
	}

	/**
	 * 查询客户经理绩效每月绩效参数表 
	 * @param year
	 * @param month
	 * @param ManagerId
	 * @return
	 */
	public TJxParameters findTJxParameters(String year,String month,String ManagerId){
		String sql = "select * from T_JX_PARAMETERS where "+
					 "year = '"+year+"' "+ 
					 "and month = '"+month+"' "+ 
					 "and CUSTOMER_MANAGER_ID = '"+ManagerId+"'";
		return commonDao.queryBySql(TJxParameters.class, sql, null).get(0);
	}
	
	/**
	 * 查询客户经理详细绩效参数 主要是客户日均贷款余额 产品利率等
	 * @param year
	 * @param month
	 * @param ManagerId
	 * @return
	 */
	public List<TJxSpecificParameters> findTJxSpecificParametersList(String year,String month,String ManagerId){
		String sqls = "select * from T_JX_SPECIFIC_PARAMETERS where "+ 
				  "year = '"+year+"' "+ 
			      "and month = '"+month+"' "+ 
	              "and CUSTOMER_MANAGER_ID = '"+ManagerId+"'";
		return commonDao.queryBySql(TJxSpecificParameters.class, sqls, null);
	}
	
	
	/**
	 * 计算利润提成 list 是当月该客户经理下所有客户的集合
	 * 当月日均贷款余额*（产品利率 - FTP价格）/ 12 * A * R 
	 * @param list
	 * @param parameters
	 * @return
	 */
	public BigDecimal calLr(List<TJxSpecificParameters> list,TPerformanceParameters parameters){
		BigDecimal lrs = new BigDecimal("0");
		
		for(TJxSpecificParameters tJxSpecificParameters :list){
		/*	String R = returnR(tJxSpecificParameters,parameters);
			BigDecimal  cha =  new BigDecimal(tJxSpecificParameters.getProdLimit()).subtract(new BigDecimal(parameters.getE()));
			cha = cha.divide(new BigDecimal("100"));
			BigDecimal  ar =  new BigDecimal(ReturnReceiptConstant.lr).multiply(new BigDecimal(parameters.getF())).multiply(new BigDecimal(R)).divide(new BigDecimal("100"));
			BigDecimal  profit = new BigDecimal(tJxSpecificParameters.getMonthDayAverageCustLoanamt()).multiply(cha).divide(ar,2,BigDecimal.ROUND_HALF_UP);
			lrs = lrs.add(profit);*/
			
			String R = returnR(tJxSpecificParameters,parameters);
			BigDecimal  cha =  new BigDecimal(tJxSpecificParameters.getProdLimit()).subtract(new BigDecimal(parameters.getE()));
			cha = cha.divide(new BigDecimal("100"));//（产品利率 - FTP价格）/100
			BigDecimal  profit = new BigDecimal(tJxSpecificParameters.getMonthDayAverageCustLoanamt()).multiply(cha).multiply(new BigDecimal(parameters.getF())).multiply(new BigDecimal(R)).divide(new BigDecimal("100")).divide(new BigDecimal(ReturnReceiptConstant.lr),2,BigDecimal.ROUND_HALF_UP);
			lrs = lrs.add(profit);
		}
		return lrs;
	}
	
	
	/**
	 * 获取风险保障系数R
	 * 根据产品类型
	 */
	public String returnR(TJxSpecificParameters tJxSpecificParameters,TPerformanceParameters parameters){
		//判断产品类型 
		String R ="";
		if(ReturnReceiptConstant.PR0D_TYPE_C101.equals(tJxSpecificParameters.getProdType())){
			R = parameters.getH(); //风险保障系数(R-保证类)
		}else if(ReturnReceiptConstant.PR0D_TYPE_C102.equals(tJxSpecificParameters.getProdType())){
			R = parameters.getI(); //风险保障系数(R-抵押类)
		}else{
			R = parameters.getG(); //风险保障系数(R-信用类)
		}
		return R;
	}
	
	/**
	 * 新增风险保证金计算
	 */
	private double getAddMonthVentureDeposit(double monthPerform){
		double addRisk = 0;
		if(monthPerform<=2000){
			addRisk = 2000 * 0.1;
			
		}else if(monthPerform>2000&&monthPerform<=5000){
			addRisk = 2000 * 0.1 + (monthPerform-2000)*0.2;
			
		}else if(monthPerform>5000&&monthPerform<=8000) {
			addRisk = 2000 * 0.1 + 3000*0.2 + (monthPerform-5000)*0.3;
			
		}else if(monthPerform>8000&&monthPerform<=15000) {
			addRisk = 2000 * 0.1 + 3000*0.2 + 3000*0.3 + (monthPerform-8000)*0.4;
			
		}else{
			addRisk = 2000 * 0.1 + 3000*0.2 + 3000*0.3 + 7000*0.4+ (monthPerform-15000)*0.5;
		}
		return addRisk;
	}
	
	/**
	 * 返还风险保证金
	 * (风险保证金采取延期支付的方式,延期支付年限为三年,即从第四年开始,
	 * 每年按上年度末风险保证金累计总额（除扣除部分之外）的1/3支付;)
	 * customerId-客户经理id
	 */
	private double outMonthVentureDeposit(String year,String month,String customerId){
		
		TPerformanceParameters parameters = commonDao.queryBySql(TPerformanceParameters.class,
				"select * from T_PERFORMANCE_PARAMETERS ",null).get(0);

		Calendar calendar = Calendar.getInstance();
		calendar.set(Integer.parseInt(year),Integer.parseInt(month),1);
		calendar.add(Calendar.MONTH, -Integer.parseInt(parameters.getK()));
		
		// 获取36个月前的时间
		int  year36 = calendar.get(Calendar.YEAR);
		int  month36 = calendar.get(Calendar.MONTH);
		
		
		// 查询36月前风险保证金
		TRiskMargin margin = getSpecificRiskMargin(String.valueOf(year36),String.valueOf(month36),customerId);
		if(margin==null){
			return 0;
		}else{
			int years = Integer.parseInt(parameters.getK())+12;
			parameters.setK(years+"");
			commonDao.updateObject(parameters);
			return Double.parseDouble(margin.getTotalRiskMargin())/3;
		}
	}
	
	
	public TRiskMargin getSpecificRiskMargin(String year,String month,String customerId){
		String sql ="select * from t_risk_margin where CUSTOMER_ID='"+customerId+"'and ACCOUNT_OPEN_YEAR='"+year+"' and ACCOUNT_OPEN_MONTH='"+month+"'";
		List<TRiskMargin> riskMargins = commonDao.queryBySql(TRiskMargin.class, sql, null);
		if(riskMargins.size()>0){
			return riskMargins.get(0);
		}else{
			return null;
		}
	}
	

	public TRiskMargin getTRiskMarginByCustomerId(String customerId){
		String sql = "select * from t_risk_margin where customer_id='"+customerId+"'";
		List<TRiskMargin> tRiskMarginsList = commonDao.queryBySql(TRiskMargin.class, sql, null);
		if(tRiskMarginsList.size()>0){
			return tRiskMarginsList.get(0);
		}else{
			return null;
		}
	}
	
	
	/**
	 * 生成 T_JX_PARAMETERS表数据
	 * userId 客户经理
	 */
	public void generateJxParameters(String userId,String year,String month){
		
		// 查询当月该客户经理协办次数 approved
		int xbNum = managerSalaryDao.findXbCountByManagerId(userId,year,month);
				
		TJxParameters jxParameters = new TJxParameters();
		jxParameters.setId(IDGenerator.generateID());
		jxParameters.setYear(year);
		jxParameters.setMonth(month);
		jxParameters.setCustomerManagerId(userId);//客户经理
		jxParameters.setMonthLoanNum(findLoanCusts(userId,year,month)+"");//当月发放贷款户数
		jxParameters.setMonthEffectNum(findEffectLoanCusts(userId,year,month)+"");//当月有效管户数
		jxParameters.setMonthOverdueLoannum(findOverdueNum(userId,year,month)+"");//当月逾期贷款笔数
		jxParameters.setMonthOverdueDays(findOverdueDays(userId,year,month)+"");//当月逾期贷款天数
		jxParameters.setMonthTimes(xbNum+"");//当月协办次数
		jxParameters.setMonthApprovalNum(findMonthApprovalNum(userId,year,month)+"");//客户经理当月承担审批的贷款笔数()
		jxParameters.setMonthAcasiNum(findMonthacasiNum(userId,year,month)+""); //客户经理当月辅助调查笔数 (协办就是辅掉  只拿一笔绩效)
		jxParameters.setMonthApprovalDecisionNum(findMonthApprovalDecisionNum(userId,year,month)+"");//客户经理当月承担审贷委次数 (与当月承担审批的贷款笔数相同)
		/*try {
			int a=managerSalaryDao.insertjxs(jxParameters);
			System.out.println(a);
		} catch (Exception e) {
			// TODO: handle exception
		}*/
		managerSalaryDao.deletejxparametersbyuserid(userId);
		commonDao.insertObject(jxParameters);
		System.out.println("success!");
	}
	
	private int findMonthApprovalDecisionNum(String userId, String year,
			String month) {
		// TODO Auto-generated method stub
		/*String sql="select count(*) as HYK from CUSTOMER_APPLICATION_SDH cas,                                     "+
					"	sys_user sysuser,                                                           "+
					"	customer_application_info app,                                              "+
					"	basic_customer_information basic                                            "+
					"	where 1=1 and                                                               "+
					"	basic.id=app.customer_id and                                                "+
					"	basic.user_id= sysuser.id and                                               "+
					"	app.id=cas.capid                                                            "+
					"	and substr(to_char(cas.time,'yyyy/mm/dd'), '0', '4') = '"+year+"'                "+
					"	and substr(to_char(cas.time,'yyyy/mm/dd'), '6', '2') = '"+month+"'  and              "+
					"	(cas.sdwuser1='"+userId+"' or                         "+
					"	cas.sdwuser2='"+userId+"' or                                                          "+
					"	cas.sdwuser3='"+userId+"')															"*/;
					String sql="select  count(*) as HYK from customer_sp sp,                              "+
							"	sys_user sysuser                                                          "+
							"	where 1=1 and                                                             "+
							"	sp.spuserid=sysuser.id                                                    "+
							"	and substr(to_char(sp.sptime,'yyyy/mm/dd'), '0', '4') = '"+year+"'        "+       
							"	and substr(to_char(sp.sptime,'yyyy/mm/dd'), '6', '2') ='"+month+"'  and   "+
							"	sp.spuserid='"+userId+"'";
		List<HashMap<String, Object>> list = commonDao.queryBySql(sql, null);
		BigDecimal c = (BigDecimal) list.get(0).get("HYK");
		return c.intValue();
	}

		//客户经理当月辅助调查笔数
		private int findMonthacasiNum(String userId, String year, String month) {
			// TODO Auto-generated method stub
			String sql="select count(*) as HYK from "+ 	   
					   "( select applog.*,sysuser.id ,applog.user_id_1 as uid1,applog.user_id_2 as uid2 from sys_user sysuser,"+
				       "   basic_customer_information basic,                                                                                   "+
				       "   customer_application_info app,                                                                                      "+
				       "   T_APP_MANAGER_AUDIT_LOG applog                                                                                      "+
				       "   where 1=1 and                                                                                                       "+
				       "   app.customer_id=basic.id and                                                                                        "+
				       "   basic.user_id=sysuser.id and                                                                                        "+
				       "   app.id=applog.application_id                                                                                    "+
				       "   and substr(to_char(app.created_time,'yyyy/mm/dd'), '0', '4') = '"+year+"'                                            "+
				       "    and substr(to_char(app.created_time,'yyyy/mm/dd'), '6', '2') = '"+month+"' and                                      "+
				       "    applog.user_id_3='"+userId+"')";
			List<HashMap<String, Object>> list = commonDao.queryBySql(sql, null);
			BigDecimal b = (BigDecimal) list.get(0).get("HYK");
			return b.intValue();
		}

		//客户经理当月承担审批的贷款笔数
		private int findMonthApprovalNum(String userId, String year, String month) {
			// TODO Auto-generated method stub
		/*String sql="select count(*) as HYK from CUSTOMER_APPLICATION_SDH cas,                                     "+
					"	sys_user sysuser,                                                           "+
					"	customer_application_info app,                                              "+
					"	basic_customer_information basic                                            "+
					"	where 1=1 and                                                               "+
					"	basic.id=app.customer_id and                                                "+
					"	basic.user_id= sysuser.id and                                               "+
					"	app.id=cas.capid                                                            "+
					"	and substr(to_char(cas.time,'yyyy/mm/dd'), '0', '4') = '"+year+"'                "+
					"	and substr(to_char(cas.time,'yyyy/mm/dd'), '6', '2') = '"+month+"'  and              "+
					"	(cas.sdwuser1='"+userId+"' or                         "+
					"	cas.sdwuser2='"+userId+"' or                                                          "+
					"	cas.sdwuser3='"+userId+"')															";*/
			String sql="select  count(*) as HYK from customer_sp sp,                              "+
					"	sys_user sysuser                                                          "+
					"	where 1=1 and                                                             "+
					"	sp.spuserid=sysuser.id                                                    "+
					"	and substr(to_char(sp.sptime,'yyyy/mm/dd'), '0', '4') = '"+year+"'        "+       
					"	and substr(to_char(sp.sptime,'yyyy/mm/dd'), '6', '2') ='"+month+"'  and   "+
					"	sp.spuserid='"+userId+"'";
			List<HashMap<String, Object>> list = commonDao.queryBySql(sql, null);
			BigDecimal b = (BigDecimal) list.get(0).get("HYK");
			return b.intValue();
		}
	
	/**
	 * 当月发放贷款户数
	 */
	public Integer findLoanCusts(String customerManagerId,String year,String month){
		/*String sql = "select count(distinct(a.CUSTID)) as HYK                    "+
				 "  from t_mibusidata_view a, basic_customer_information b   "+
				 " where a.custid = b.ty_customer_id                         "+
				 "   and substr(loandate, '0', '4') = '"+year+"'             "+
				 "   and substr(loandate, '6', '2') = '"+month+"'            "+
				 "   and b.USER_ID = '"+customerManagerId+"'                 ";*/
				String sql=    "   select                                      "+
						       " count(distinct tkmx.ywbh)   as HYK            "+
						       " from                                          "+
						       "        ty_repay_tkmx tkmx,                    "+
						       "        ty_customer_base b,                    "+
						       "        ty_customer_rygl rygl,                 "+
						       "        sys_user sysuser,                      "+
						       "       ty_kdk_jh jh,                           "+
						       "        ty_repay_yehz yehz,                    "+
						       "        ty_repay_lsz lsz                       "+
						       "        where b.khjl=rygl.dm and               "+
						       "        rygl.ddrq=sysuser.external_id and      "+
						       "        b.khnm=tkmx.khh and                    "+
						       "        jh.ywbh=tkmx.ywbh||'HT' and            "+
						       "        yehz.jjh=tkmx.jjh and                  "+
						       "        tkmx.jjh =lsz.jjh                      "+
						       "   and substr(tkmx.jkrq, '0', '4') = '"+year+"'             "+
						       "   and substr(tkmx.jkrq, '5', '2') = '"+month+"'            "+
						       "   and sysuser.id = '"+customerManagerId+"'                 ";
		List<HashMap<String, Object>> list = commonDao.queryBySql(sql, null);
		BigDecimal b = (BigDecimal) list.get(0).get("HYK");
		return b.intValue();
	}
	/**
	 * 当月有效管户数
	 * 有效管户即有有收息的客户
	 */
	public Integer findEffectLoanCusts(String customerManagerId,String year,String month){
		/*String sql = " select count(distinct(b.CUSTID))		as HYK			"+
					 "        from t_gccontractmain           a,         		"+
					 "             t_gccontractmulticlient    b,          		"+
					 "             basic_customer_information c           		"+
					 "       where trim(a.KEYCODE) = b.KEYCODE            		"+
					 "         and c.ty_customer_id = b.CUSTID           		"+
					 "         and a.keyeffectedstate ='1'                		"+
					 "         and b.keyeffectedstate ='1'                		"+
					 "         and substr(a.STARTDATE, '0', '4') = '"+year+"' 	"+
					 "         and substr(a.STARTDATE, '6', '2') = '"+month+"'  "+
					 "         and c.USER_ID = '"+customerManagerId+"'    		";*/
		/*String sql =    "select count(distinct(t.id)) as HYK                  "+
						"  from mibusidata t, basic_customer_information b      "+
						" where 1 = 1                                             "+
						"   and substr(t.operdatetime, '0', '4') = '"+year+"'     "+
						"   and substr(t.operdatetime, '6', '2') = '"+month+"'    "+
						"   and t.id = b.ty_customer_id                        "+
						"   and nvl(PAYDEBT, 0) > 0                               "+
						"   and b.user_id = '"+customerManagerId+"'    			  ";*/
		String sql=             
					"	select count(distinct tkmx.ywbh) as HYK                       "+
					"	                    from                                      "+
					"	                    ty_customer_rygl rygl,                    "+
					"	                    ty_customer_base base,                    "+
					"	                    ty_product_type protype,                  "+
					"	                    ty_repay_tkmx tkmx,                       "+
					"	                    SYS_USER sysuser  ,                       "+
					"	                    ty_repay_yehz yehz,                       "+
					"	                    ty_repay_lsz lsz                          "+
					"	                    where   base.khjl=rygl.dm and             "+
					"	                      rygl.ddrq=sysuser.external_id           "+
					"	                    and base.khnm=tkmx.khh                    "+
					"	                    and tkmx.JJH=yehz.JJH                     "+
					"	                    and protype.product_code=tkmx.cpmc        "+
					"	                    and lsz.jjh=tkmx.jjh                      "+
					"	                    and lsz.zy='批量自动扣利息'                    "+
				/*	"	                    and df>0                                  "+*/
					"   and substr(lsz.jzrq, '0', '4') = '"+year+"'             "+
				       "   and substr(lsz.jzrq, '5', '2') = '"+month+"'            "+              
				       "   and sysuser.id = '"+customerManagerId+"'                 ";
		List<HashMap<String, Object>> list = commonDao.queryBySql(sql, null);
		BigDecimal b = (BigDecimal) list.get(0).get("HYK");
		return b.intValue();
	}
	
	/**
	 * 当月逾期贷款笔数
	 */
	public Integer findOverdueNum(String customerManagerId,String year,String month){
		/*String sql =    " select nvl(sum(DEBTINTERESTTIMES),0)  as HYK             "+
						"  from t_mibusidata_view a, basic_customer_information b  "+
						" where a.custid = b.ty_customer_id                        "+
						"   and substr(loandate, '0', '4') = '"+year+"'            "+
						"   and substr(loandate, '6', '2') = '"+month+"'           "+
						"   and b.USER_ID ='"+customerManagerId+"'                 ";*/
		String sql=  "  select count(a.ywbh)  as HYK  from                                                                                                                                                                                            "+
				     "  (select                                                                                                                                                                                                              "+
				     "             distinct                                                                                                                                                                                                  "+
				     "               tkmx.YWBH ,                                                                                                                                                                                             "+
				     "                (case when(to_date(max(lsz.jzrq),'yyyy-mm-dd')-to_date(tkmx.dqrq,'yyyy-mm-dd')) > 0 then (to_date(max(lsz.jzrq),'yyyy-mm-dd')-to_date(tkmx.dqrq,'yyyy-mm-dd')) else null  end ) as DELAYINTERESTDAYS,  "+
				     "                (case when(to_date(max(lsz.jzrq),'yyyy-mm-dd')-to_date(tkmx.dqrq,'yyyy-mm-dd')) > 0 then (to_date(max(lsz.jzrq),'yyyy-mm-dd')-to_date(tkmx.dqrq,'yyyy-mm-dd')) else null  end )as DELAYAMTDAYS,        "+
				     "                sysuser.id                                                                                                                                                                                             "+
				     "            from                                                                                                                                                                                                       "+
				     "                   ty_repay_tkmx tkmx,                                                                                                                                                                                 "+
				     "                   ty_customer_base b,                                                                                                                                                                                 "+
				     "                   ty_customer_rygl rygl,                                                                                                                                                                              "+
				     "                   sys_user sysuser,                                                                                                                                                                                   "+
				     "                  ty_kdk_jh jh,                                                                                                                                                                                        "+
				     "                   ty_repay_yehz yehz,                                                                                                                                                                                 "+
				     "                   ty_repay_lsz lsz                                                                                                                                                                                    "+
				     "                   where b.khjl=rygl.dm and                                                                                                                                                                            "+
				     "                   rygl.ddrq=sysuser.external_id and                                                                                                                                                                   "+
				     "                   b.khnm=tkmx.khh and                                                                                                                                                                                 "+
				     "                   jh.ywbh=tkmx.ywbh||'HT' and                                                                                                                                                                         "+
				     "                   yehz.jjh=tkmx.jjh and                                                                                                                                                                               "+
				     "                   tkmx.jjh =lsz.jjh and                                                                                                                                                                                  "+
				     "				     substr(lsz.jzrq, '0', '4') = '2017' and 																																							 "+
				     "		              substr(lsz.jzrq, '5', '2') = '02'																																								 "+
				     "                   group by tkmx.ywbh,tkmx.dqrq,sysuser.id) a                                                                                                                                                          "+
				     "                  where 1=1                                                                                                                                                                                            "+
				     "                  and (nvl(a.DELAYINTERESTDAYS,0)  >   0 or nvl(a.DELAYAMTDAYS,0)  >  0)                                                                                                                               "+
				     "   and a.id ='"+customerManagerId+"'                 ";
		List<HashMap<String, Object>> list = commonDao.queryBySql(sql, null);
		BigDecimal b = (BigDecimal) list.get(0).get("HYK");
		return b.intValue();
	}
	
	/**
	 * 当月逾期贷款天数
	 */
	public int findOverdueDays(String customerManagerId,String year,String month){
	   return 0 ;
	}
	
	/**
	 * 生成 t_jx_specific_parameters
	 * 按客户id 区分 开
	 */
	public void generateJxSpecificParameters(String userId,String year,String month){
		
		// 查询该客户经理名下的客户
		List<CustomerInfor> list = commonDao.queryBySql(CustomerInfor.class,
		"select * from basic_customer_information where TY_CUSTOMER_ID is not null and USER_ID='"+ userId + "' ", null);
	    
	    // 贷款余额
	    BigDecimal balamt = new BigDecimal("0");
	    
	    for(CustomerInfor fro :list){
	    	// 产品利率
	    	String prodLimit="";
	    	
	    	// 产品类型
	    	String prodType="";
	    	
	    	// 客户id
	    	String customerId = fro.getId();
	    	
	    	// 行内客户标识id
	    	String tyCustomerId = fro.getTyCustomerId();
	    	
	    	// 查询客户贷款[产品利率]和[产品类型]
	    	List<Map<String, Object>> mapList = findProdLimitAndType(customerId,year,month);
	    	
	    	if(mapList == null || mapList.size() ==0){
	    		prodLimit = "0";
	    		prodType  = "0";
	    	}else{
		    	Map<String, Object> obj = mapList.get(0);
		    	prodLimit = obj.get("INTEREST").toString();
		       // prodType =  obj.get("MAINASSURE").toString();
	    	}
	    	
	        // 计算贷款余额
	        balamt = findBalamt(tyCustomerId,year,month);
	        
	    	// 插入t_jx_specific_parameters表数据
		    TJxSpecificParameters jxSpecificParameters = new TJxSpecificParameters();
			jxSpecificParameters.setId(IDGenerator.generateID());
			jxSpecificParameters.setYear(year);
			jxSpecificParameters.setMonth(month);
			jxSpecificParameters.setMonthDayAverageCustLoanamt(balamt.toString());//当月客户日均贷款余额
			jxSpecificParameters.setProdLimit(prodLimit);//产品利率
			jxSpecificParameters.setProdType(prodType);//产品类型（C101-保证  C102-抵押  C100-信用）
			jxSpecificParameters.setCustomerId(customerId);//客户id
			jxSpecificParameters.setCustomerManagerId(userId);//客户经理id
			int a=commonDao.insertObject(jxSpecificParameters);
			if(a>0){
				System.out.println(222);
			}
	    }
	}
	
	/**
	 * 查询产品利率以及产品类型
	 * @param customerId
	 * @param year
	 * @param month
	 * @return
	 */
	public List<Map<String, Object>> findProdLimitAndType(String customerId,String year,String month){
		return managerSalaryDao.findProdLimitAndType(customerId, year, month);
	}
	
	/**
	 * 查询当月客户日均贷款余额
	 * tyCustomerId 行内客户标识id
	 */
	public BigDecimal findBalamt(String tyCustomerId,String year,String month){
		// 该客户该月的日均贷款余额
		BigDecimal balamt = new BigDecimal("0");
		 
		// 查询客户当月生成台帐BUSICODE编号的次数
		List<Map<String, Object>> mapList =  managerSalaryDao.findDistinctBusicode(tyCustomerId, year, month);
		
		// 根据BUSICODE再次筛选
		for (Map<String, Object> obj : mapList){
			String sql =    /*" select t.busicode,				   		    		"+				
							"       t.money,                                		"+
							"       t.loandate,                             		"+
							"       t.balamt,                               		"+
							"       t.operdatetime,                         		"+
							//"       t.limit,                                		"+
							"       t.MAINASSURE,                          			"+
							"       t.custid                                		"+
							"  from t_mibusidata t                          	    "+
							" where substr(OPERDATETIME, '0', '4') = '"+year+"'     "+
							"   and substr(OPERDATETIME, '6', '2') = '"+month+"'    "+
							"   and custid = '"+tyCustomerId+"'                     "+
							"   and busicode = '"+obj.get("BUSICODE").toString()+"' "+
							" order by OPERDATETIME asc                             ";*/
					//" 	select a.YWBH as busicode,a.REQLMT as money,a.LOANDATE,a.BALAMT,a.operdatetime ,a.id as custid        "+
					" 	select a.ywbh ,a.reqlmt,a.loandate,a.balamt,a.operdatetime ,a.id         "+
		            " 	 from mibusidata a                                             "+
		            " 	 where substr(a.operdatetime , '0', '4') = '"+year+"'          "+
		            "  	and substr(OPERDATETIME, '5', '2') = '"+month+"'              "+
		            "   and a.id = '"+tyCustomerId+"'                                "+
		            "    and a.YWBH = '"+obj.get("BUSICODE").toString()+"'           "+
		            "    order by a.operdatetime asc                                 ";
			/*List<TMibusidata> mibusidataList = new ArrayList<TMibusidata>();
			mibusidataList =  commonDao.queryBySql(TMibusidata.class, sql, null);*/
			List<MibusidateView> mibusidataList = new ArrayList<MibusidateView>();
			mibusidataList =  commonDao.queryBySql(MibusidateView.class, sql, null);
			balamt = balamt.add(doCalAmt(mibusidataList, year, month));
		}
		return balamt;
	}
	
	/**
	 * 筛选计算
	 * @param mibusidataList
	 * @return
	 * 一笔busicode 的list 一笔算
	 */
	public BigDecimal doCalAmt(List<MibusidateView> mibusidataList,String year,String month){
		// 获取当月实际天数
		int  days = getMonthLastDay(Integer.parseInt(year),Integer.parseInt(month));
		
		// 贷款余额
		String balamt = "";
		
		// 操作时间
		String operTime = "";
		
		/**
		 * ∑(贷款余额 x 实际使用天数)/当月实际天数  求和     分段求和
		 * 30000   2016-08-01
		 * 20000   2016-08-15
		 * 10000   2016-08-25
		 * 	0	   2016-08-31(月末日期)
		 */
		List<String> list  = new ArrayList<String>();
		
		for(MibusidateView data : mibusidataList){
		    if(balamt != data.getBalamt().toString()){
		    	balamt = data.getBalamt().toString();
		    	operTime = data.getOperdatetime().toString();
		    	list.add(balamt+"@"+operTime);
		    }
		}
		
		// 添加月末日期
		String lastDay = year +"-"+ month +"-"+ days;
		list.add(0+"@"+lastDay);
		
		// 汇总BUSICODE单笔台帐的日均贷款余额
		BigDecimal amt   = new BigDecimal("0");
		BigDecimal amt0  = new BigDecimal("0");
		BigDecimal amt1  = new BigDecimal("0");
		
		// 实际使用天数
		int ts =0;
		String str0[];
		String str1[];
		String time0;
		String time1;
	
		
		for(int i = 0;i<list.size();i++){
			System.out.println(list.size());
			// 第0个List 的Amt 以及操作时间
			str0 = list.get(0).split("@");
			amt0 =  new BigDecimal(str0[0]);
			time0 = str0[1];
			
			// 第1个List 的Amt 以及操作时间
			str1 = list.get(1).split("@");
			amt1 =  new BigDecimal(str1[0]);
			time1 = str1[1];
			
			// 计算两个时间戳的时间差
		    ts = calMistTime(time0,time1);
		    
		    // 计算Amt
			amt = amt.add(amt0.multiply(new BigDecimal(ts)).divide(new BigDecimal(days),2,BigDecimal.ROUND_HALF_UP));
			
			// 计算完毕移除第0条数据 List的Size随之减小
			list.remove(0);
			System.out.println(list.size());
			i =0;
		}
		return amt;
	}
	
	/**
	 * 获得当月实际使用天数
	 * @param year
	 * @param month
	 * @return
	 */
	 public static int getMonthLastDay(int year, int month)  
	  {  
	      Calendar a = Calendar.getInstance();  
	      a.set(Calendar.YEAR, year);  
	      a.set(Calendar.MONTH, month - 1);  
	      a.set(Calendar.DATE, 1);//把日期设置为当月第一天  
	      a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天  
	      int maxDate = a.get(Calendar.DATE);  
	      return maxDate;  
	  }  
	 
	 /**
	  * 计算两个时间戳的时间差 即天数 例 2016-09-01 2016-09-05
	  */
	 public int calMistTime(String startDate,String endDate){
		 // 创建一个日历对象。
		 Calendar calendar = Calendar.getInstance();
		
		 // start
		 List<String> startDateList = findYearAndMonthAndDay(startDate);
		 calendar.set(Integer.parseInt(startDateList.get(0)), 
				 	  Integer.parseInt(startDateList.get(1)), 
					  Integer.parseInt(startDateList.get(2))); 
	     long start = calendar.getTimeInMillis();
	     
	     // end
	     DateFormat df=new SimpleDateFormat("yyyyMMdd");
	     DateFormat df1=new SimpleDateFormat("yyyy-MM-dd");
	     String endDates = null;
		try {
			endDates = df.format(df1.parse(endDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     List<String> endDateList = findYearAndMonthAndDay(endDates);
	     calendar.set(Integer.parseInt(endDateList.get(0)),
	    		 	  Integer.parseInt(endDateList.get(1)),
	    			  Integer.parseInt(endDateList.get(2))); 
	     long end = calendar.getTimeInMillis();
	     
	     // 时间差
	     long interdays = (end - start) / (1000 * 60 * 60 * 24);
	     return new Long(interdays).intValue();
	 }
	 
	 /**
	  * @param date
	  * @return
	  */
	  public static List<String> findYearAndMonthAndDay(String date){
		  List<String>  list = new ArrayList<String>();
		  String year = date.substring(0, 4);
		  String month = date.substring(4, 6);
		  String day = date.substring(6, 8);
		  list.add(year);
		  list.add(month);
		  list.add(day);
		  return list;
	  }
	  
	  
	  
	  /**
	   * 济南绩效导出
	   * 行编和外聘 客户经理
	   * @param filter
	   * @param response
	   * @throws Exception
	   */
		public void getExportWageData(ManagerSalaryFilter filter,HttpServletResponse response) throws Exception{
			String year  = filter.getYear();//年份
			String month = filter.getMonth();//月份
			String mon = Integer.parseInt(month)+"";
			
			// 计算当月的下一个月
			String nextMonth = "";
			Calendar calendar = Calendar.getInstance();
			calendar.set(Integer.parseInt(year),Integer.parseInt(month),1);
			calendar.add(Calendar.MONTH, 1);
			nextMonth = calendar.get(Calendar.MONTH)+"";
			
			// 判断客户经理 类型
			String title ="";
			/*if("1".equals(filter.getManagerType())){*/
				 title ="太原城区联社小微信贷中心行编客户经理薪酬测算表("+year+"年"+month+"月)";
			/*}else if("2".equals(filter.getManagerType())){
				 title ="太原城区联社小微信贷中心外聘客户经理薪酬测算表("+year+"年"+month+"月)";
			}else{
				 title ="太原城区联社小微信贷中心风险岗薪酬测算表("+year+"年"+month+"月)";
			}*/
			
			// 查询工资单
			List<ManagerSalaryForm> salaryList = managerSalaryDao.findManagerSalaryForm(filter);
			
			// 查询客户经理绩效参数
			TyTPerformanceParameters parameters = commonDao.queryBySql(TyTPerformanceParameters.class,"select * from T_PERFORMANCE_PARAMETERS ",null).get(0);
			
			/*//T_JX_PARAMETERS
			// 查询客户经理绩效每月绩效参数表 
			TJxParameters jxParameters = findTJxParameters(year,month,filter.getCustomerManagerId());*/
			
			// 第一步，创建一个webbook，对应一个Excel文件  
	        HSSFWorkbook wb = new HSSFWorkbook();  
	        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
	        HSSFSheet sheet = wb.createSheet("sheet1");  
	        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
	        HSSFRow row = sheet.createRow((int) 0);  
	        HSSFCell cellTmp = row.createCell((short) 0);
			cellTmp.setCellValue(title);  //设置表格标题 For Example :济南农商行小微信贷中心外聘客户经理薪酬测算表（2016年09月）
			
			// 设置标题字体
			HSSFFont font16 = wb.createFont();
			font16.setFontHeightInPoints((short) 20);
			font16.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			font16.setFontName("华文楷体");
			
			// 设置标题字体
			HSSFFont font1 = wb.createFont();
			font1.setFontHeightInPoints((short) 12);
			font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			font1.setFontName("宋体");
			
			// 设置单元格居中
			HSSFCellStyle styleCenter = wb.createCellStyle();
			styleCenter.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			styleCenter.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			styleCenter.setFont(font16);
			
			// 设置居右
			HSSFCellStyle styleFirst = wb.createCellStyle();
			styleFirst.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			styleFirst.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
			styleFirst.setFont(font1);
			
			// 合并单元格
			CellRangeAddress region = new CellRangeAddress(0, 0, 0,22);
			sheet.addMergedRegion(region);
			cellTmp.setCellStyle(styleCenter);
			
	        // 第四步，创建单元格，并设置值表头 设置表头居中  
	        HSSFCellStyle style = wb.createCellStyle();  
	        // 创建一个居中格式
	        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	        style.setWrapText(true);
	        style.setFillForegroundColor(HSSFColor.LIGHT_ORANGE.index);
	        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	        
	        // 设置第二行 制表日期
	        row = sheet.createRow((int) 1);
	        HSSFCell tmp = row.createCell((short) 18);
	        tmp.setCellValue("制表日期：");
	        CellRangeAddress reg = new CellRangeAddress(1, 1, 18,22);
	        sheet.addMergedRegion(reg);
	        tmp.setCellStyle(styleFirst);
	        
	        // excel 正文内容
	        row = sheet.createRow((int) 2);
	        //row.s
	        HSSFCell cell = row.createCell((short) 0);  
	        cell.setCellValue("序号");  
	        cell.setCellStyle(style);
	        	        
	        cell = row.createCell((short) 1);  
	        cell.setCellValue("管辖行");  
	        cell.setCellStyle(style);  
	        
	        cell = row.createCell((short) 2);  
	        cell.setCellValue("姓名");  
	        cell.setCellStyle(style);
	        
	        cell = row.createCell((short) 3);  
	        cell.setCellValue("等级");  
	        cell.setCellStyle(style);
	        
	        cell = row.createCell((short) 4);  
	        cell.setCellValue(mon+"月基本\n工资\n（元）");  
	        cell.setCellStyle(style);
	        sheet.setColumnWidth(4, 10*256);
	        
	        cell = row.createCell((short) 5);  
	        cell.setCellValue(mon+"月总绩效\n（元）");  
	        cell.setCellStyle(style);
	        sheet.setColumnWidth(5, 10*256);
	        
	        cell = row.createCell((short) 6);  
	        cell.setCellValue(mon+"月放款\n绩效\n（元）");  
	        cell.setCellStyle(style);
	        sheet.setColumnWidth(6, 10*256);
	        
	        cell = row.createCell((short) 7);  
	        cell.setCellValue(mon+"月主办放款\n绩效\n（元）");  
	        cell.setCellStyle(style);
	        sheet.setColumnWidth(7, 10*256);
	        
	        cell = row.createCell((short) 8);  
	        cell.setCellValue(mon+"月协办放款\n绩效\n（元）");  
	        cell.setCellStyle(style);
	        sheet.setColumnWidth(8, 10*256);
	        
	       /* cell = row.createCell((short) 5);  
	        cell.setCellValue(mon+"月基础\n任务量\n奖金\n（元）");  
	        cell.setCellStyle(style);
	        sheet.setColumnWidth(5, 10*256);
	        
	        cell = row.createCell((short) 6);  
	        cell.setCellValue(mon+"月主办\n放款户\n数\n（户）");  
	        cell.setCellStyle(style);
	        sheet.setColumnWidth(6, 10*256);
	        
	        cell = row.createCell((short) 7);  
	        cell.setCellValue(mon+"月主办\n放款户\n数奖金\n（元）");  
	        cell.setCellStyle(style);
	        sheet.setColumnWidth(7, 10*256);
	        
	        cell = row.createCell((short) 8);  
	        cell.setCellValue(mon+"月协办\n放款户\n数\n（户）");  
	        cell.setCellStyle(style);
	        sheet.setColumnWidth(8, 10*256);*/
	        
	        cell = row.createCell((short) 9);  
	        cell.setCellValue(mon+"月管户数\n（户）");  
	        cell.setCellStyle(style);
	        sheet.setColumnWidth(9, 10*256);
	        
	        cell = row.createCell((short) 10);  
	        cell.setCellValue(mon+"月管户数绩效\n（元）");  
	        cell.setCellStyle(style);
	        sheet.setColumnWidth(10, 10*256);
	        
	        cell = row.createCell((short) 11);  
	        cell.setCellValue(mon+"月承担审批数\n（户）");  
	        cell.setCellStyle(style);
	        sheet.setColumnWidth(11, 10*256);
	        
	        cell = row.createCell((short) 12);  
	        cell.setCellValue(mon+"月承担审批数绩效\n（元）");  
	        cell.setCellStyle(style);
	        sheet.setColumnWidth(12, 10*256);
	        
	        cell = row.createCell((short) 13);  
	        cell.setCellValue("等级岗位绩效\n（元）");  
	        cell.setCellStyle(style);
	        sheet.setColumnWidth(13, 10*256);
	        
	        /*cell = row.createCell((short) 14);  
	        cell.setCellValue(mon+"月辅助调查数\n（户）");  
	        cell.setCellStyle(style);
	        sheet.setColumnWidth(14, 10*256);
	        
	        cell = row.createCell((short) 15);  
	        cell.setCellValue(mon+"月辅助调查绩效\n（元）");  
	        cell.setCellStyle(style);
	        sheet.setColumnWidth(15, 10*256);*/
	        
	        cell = row.createCell((short) 14);  
	        cell.setCellValue(mon+"月任务完成度绩效\n（元）");  
	        cell.setCellStyle(style);
	        sheet.setColumnWidth(16, 10*256);
	        
	        cell = row.createCell((short) 15);  
	        cell.setCellValue(mon+"超过不良贷款容忍度\n扣（元）");  
	        cell.setCellStyle(style);
	        sheet.setColumnWidth(17, 10*256);
	        
	        cell = row.createCell((short) 16);  
	        cell.setCellValue(mon+"主管加成绩效\n扣（元）");  
	        cell.setCellStyle(style);
	        sheet.setColumnWidth(18, 10*256);
	        
	        cell = row.createCell((short) 17);  
	        cell.setCellValue(mon+"月缺勤\n扣（元）");  
	        cell.setCellStyle(style);
	        sheet.setColumnWidth(19, 10*256);
	        
	        cell = row.createCell((short) 18);  
	        cell.setCellValue("前期差\n错补扣\n（元）");  
	        cell.setCellStyle(style);
	        sheet.setColumnWidth(20, 10*256);
	        
	        cell = row.createCell((short) 19);  
	        cell.setCellValue(mon+"月其他\n业务绩\n效\n（元）");  
	        cell.setCellStyle(style);
	        sheet.setColumnWidth(21, 10*256);
	        
	        cell = row.createCell((short) 20);  
	        cell.setCellValue(mon+"月应发\n绩效\n（元）");  
	        cell.setCellStyle(style);
	        sheet.setColumnWidth(22, 10*256);
	        
	        cell = row.createCell((short) 21);  
	        cell.setCellValue(nextMonth+"月应发\n薪酬\n（元）");  
	        cell.setCellStyle(style);
	        sheet.setColumnWidth(23, 10*256);
	        
	        cell = row.createCell((short) 22);  
	        cell.setCellValue("风险保\n证金\n（元）");  
	        cell.setCellStyle(style);
	        sheet.setColumnWidth(24, 10*256);
	        
	        cell = row.createCell((short) 23);  
	        cell.setCellValue(nextMonth+"月实发\n薪酬\n（元）");  
	        cell.setCellStyle(style);
	        sheet.setColumnWidth(25, 10*256);
	        
	        
	        
	        for(int i=0;i<salaryList.size();i++){
	        	row = sheet.createRow((int) i + 3);
	        	ManagerSalaryForm salary = salaryList.get(i);
	        	row.createCell((short) 0).setCellValue(i+1);  
	        	row.createCell((short) 1).setCellValue((String) salary.getInstCode());            //管辖行
	        	row.createCell((short) 2).setCellValue((String) salary.getManagerName());         //姓名                       
	        	row.createCell((short) 3).setCellValue("");     						          //岗位  备注： 暂无  后续会设定客户经理级别 级别与基本工资挂钩                     
	        	row.createCell((short) 4).setCellValue((String) salary.getZhbasepay());             //9月基本工资（元）          
	        	row.createCell((short) 5).setCellValue((String) salary.getMonthPerformance());      //8月基础任务量奖金（元） 备注:外聘客户经理所有 行编客户经理无   
	        	row.createCell((short) 6).setCellValue((String) salary.getDyffdkjx());        //8月主办放款户数（户）      
	        	row.createCell((short) 7).setCellValue((String) salary.getZb());//8月主办放款户数奖金（元）  
	        	row.createCell((short) 8).setCellValue((String) salary.getXb());          //8月协办放款户数（户）      
	        	row.createCell((short) 9).setCellValue(salary.getMonthEffectNum());//8月协办放款户数奖金（元）  
	        	row.createCell((short) 10).setCellValue(salary.getGh());     //8月有效管户数（户）        
	        	row.createCell((short) 11).setCellValue(salary.getMonthApprovalNum()); //承担审批的笔数
	        	/*new BigDecimal(parameters.getD()).multiply(new BigDecimal(salary.getMonthEffectNum()))+""*/
	        	row.createCell((short) 12).setCellValue((String) salary.getSp());  //承担审批的绩效     
	        	row.createCell((short) 13).setCellValue((String) salary.getDj());   //等级岗位绩效      
	        	//row.createCell((short) 14).setCellValue(salary.getMonthAcasiNum());//辅助调查          
	        	//row.createCell((short) 15).setCellValue((String) salary.getFd());  //辅助调查绩效
	        	row.createCell((short) 14).setCellValue((String) salary.getRw());  	//月任务完成度绩效								
	        	row.createCell((short) 15).setCellValue((String) salary.getBl());  	//超过不良贷款容忍度扣款								
	        	row.createCell((short) 16).setCellValue((String) salary.getZg());  	//主管加成绩效								 
	        	row.createCell((short) 17).setCellValue("");  			 //8月缺勤扣（元）             手填						 
	        	row.createCell((short) 18).setCellValue("");  			 //前期差错补扣（元）        手填						 
	        	row.createCell((short) 19).setCellValue((String) salary.getInsertPrepareAmount());//8月其他业务绩效（元） 手填
	        	row.createCell((short) 20).setCellValue("");  //8月应发绩效（元）         待确定      
	        	row.createCell((short) 21).setCellValue(""); //9月应发薪酬（元）        待确定        
	        	row.createCell((short) 22).setCellValue(""); //风险保证金（元）           待确定   
	        	row.createCell((short) 23).setCellValue(""); //9月实发薪酬（元）        待确定      
	        }
	        title = title+".xls";
	        response.setHeader("Connection", "close");
	        response.setHeader("Content-Type", "application/vnd.ms-excel;charset=utf-8");
	        response.setHeader("Content-Disposition", "attachment;filename="
	        + new String(title.getBytes(), "iso-8859-1"));
	        OutputStream out = response.getOutputStream();  
	        wb.write(out);
	        out.close();
		}
		
		
		
		
		
		
		
		/**
		   * 济南绩效导出 
		   * 风险岗
		   * @param filter
		   * @param response
		   * @throws Exception
		   */
		public void getExportWageDatas(ManagerSalaryFilter filter,HttpServletResponse response) throws Exception{
			String year  = filter.getYear();//年份
			String month = filter.getMonth();//月份
			String mon = Integer.parseInt(month)+"";
			
			//计算当月的下一个月
			String nextMonth = "";
			Calendar calendar = Calendar.getInstance();
			calendar.set(Integer.parseInt(year),Integer.parseInt(month),1);
			calendar.add(Calendar.MONTH, 1);
			nextMonth = calendar.get(Calendar.MONTH)+"";
			
			//判断客户经理 类型
			String title ="";
			if("1".equals(filter.getManagerType())){
				 title ="济南农商行小微信贷中心行编客户经理薪酬测算表("+year+"年"+month+"月)";
			}else if("2".equals(filter.getManagerType())){
				 title ="济南农商行小微信贷中心外聘客户经理薪酬测算表("+year+"年"+month+"月)";
			}else{
				 title ="济南农商行小微信贷中心风险岗薪酬测算表("+year+"年"+month+"月)";
			}
			
			//查询工资单
			List<ManagerSalaryForm> salaryList = managerSalaryDao.findManagerSalaryForm(filter);
			
			//查询客户经理绩效参数
			TPerformanceParameters parameters = commonDao.queryBySql(TPerformanceParameters.class,"select * from T_PERFORMANCE_PARAMETERS ",null).get(0);
			
			// 第一步，创建一个webbook，对应一个Excel文件  
	        HSSFWorkbook wb = new HSSFWorkbook();  
	        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
	        HSSFSheet sheet = wb.createSheet("sheet1");  
	        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
	        HSSFRow row = sheet.createRow((int) 0);  
	        HSSFCell cellTmp = row.createCell((short) 0);
			cellTmp.setCellValue(title);  //设置表格标题 For Example :济南农商行小微信贷中心外聘客户经理薪酬测算表（2016年09月）
			
			// 设置标题字体
			HSSFFont font16 = wb.createFont();
			font16.setFontHeightInPoints((short) 20);
			font16.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			font16.setFontName("华文楷体");
			
			// 设置标题字体
			HSSFFont font1 = wb.createFont();
			font1.setFontHeightInPoints((short) 12);
			font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			font1.setFontName("宋体");
			
			// 设置单元格居中
			HSSFCellStyle styleCenter = wb.createCellStyle();
			styleCenter.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			styleCenter.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			styleCenter.setFont(font16);
			
			// 设置居右
			HSSFCellStyle styleFirst = wb.createCellStyle();
			styleFirst.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			styleFirst.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
			styleFirst.setFont(font1);
			
			// 合并单元格
			CellRangeAddress region = new CellRangeAddress(0, 0, 0,13);
			sheet.addMergedRegion(region);
			cellTmp.setCellStyle(styleCenter);
			
	        // 第四步，创建单元格，并设置值表头 设置表头居中  
	        HSSFCellStyle style = wb.createCellStyle();  
	        // 创建一个居中格式
	        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	        style.setWrapText(true);
	        style.setFillForegroundColor(HSSFColor.LIGHT_ORANGE.index);
	        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	        
	        // 设置第二行 制表日期
	        row = sheet.createRow((int) 1);
	        HSSFCell tmp = row.createCell((short) 9);
	        tmp.setCellValue("制表日期：");
	        CellRangeAddress reg = new CellRangeAddress(1, 1, 9,13);
	        sheet.addMergedRegion(reg);
	        tmp.setCellStyle(styleFirst);
	        
	        // excel 正文内容
	        row = sheet.createRow((int) 2);
	        //row.s
	        HSSFCell cell = row.createCell((short) 0);  
	        cell.setCellValue("序号");  
	        cell.setCellStyle(style);
	        	        
	        cell = row.createCell((short) 1);  
	        cell.setCellValue("管辖行");  
	        cell.setCellStyle(style);  
	        
	        cell = row.createCell((short) 2);  
	        cell.setCellValue("姓名");  
	        cell.setCellStyle(style);
	        
	        cell = row.createCell((short) 3);  
	        cell.setCellValue("岗位");  
	        cell.setCellStyle(style);
	        
	        cell = row.createCell((short) 4);  
	        cell.setCellValue(nextMonth+"月基本\n工资\n（元）");  
	        cell.setCellStyle(style);
	        sheet.setColumnWidth(4, 10*256);
	        
	        cell = row.createCell((short) 5);  
	        cell.setCellValue("岗位津贴");  
	        cell.setCellStyle(style);
	        sheet.setColumnWidth(4, 10*256);
	        
	        cell = row.createCell((short) 6);  
	        cell.setCellValue(mon+"当月参与审贷会\n审议\n通过\n笔数(笔)");  
	        cell.setCellStyle(style);
	        sheet.setColumnWidth(4, 10*256);
	        
	        cell = row.createCell((short) 7);  
	        cell.setCellValue(mon+"月业务\n量绩效\n（元）");  
	        cell.setCellStyle(style);
	        sheet.setColumnWidth(15, 10*256);
	        
	        cell = row.createCell((short) 8);  
	        cell.setCellValue(mon+"月缺勤\n扣（元）");  
	        cell.setCellStyle(style);
	        sheet.setColumnWidth(16, 10*256);
	        
	        cell = row.createCell((short) 9);  
	        cell.setCellValue("前期差\n错补扣\n（元）");  
	        cell.setCellStyle(style);
	        sheet.setColumnWidth(17, 10*256);
	        
	        cell = row.createCell((short) 10);  
	        cell.setCellValue(mon+"月其他\n业务绩\n效\n（元）");  
	        cell.setCellStyle(style);
	        sheet.setColumnWidth(18, 10*256);
	        
	        cell = row.createCell((short) 11);  
	        cell.setCellValue(mon+"月应发\n绩效\n（元）");  
	        cell.setCellStyle(style);
	        sheet.setColumnWidth(19, 10*256);
	        
	        cell = row.createCell((short) 12);  
	        cell.setCellValue(nextMonth+"月应发\n薪酬\n（元）");  
	        cell.setCellStyle(style);
	        sheet.setColumnWidth(20, 10*256);
	       
	        cell = row.createCell((short) 13);  
	        cell.setCellValue(nextMonth+"月实发\n薪酬\n（元）");  
	        cell.setCellStyle(style);
	        sheet.setColumnWidth(22, 10*256);
	        
	        
	        
	        for(int i=0;i<salaryList.size();i++){
	        	row = sheet.createRow((int) i + 3);
	        	ManagerSalaryForm salary = salaryList.get(i);
	        	row.createCell((short) 0).setCellValue(i+1);  
	        	row.createCell((short) 1).setCellValue((String) salary.getInstCode());            //管辖行
	        	row.createCell((short) 2).setCellValue((String) salary.getManagerName());         //姓名                       
	        	row.createCell((short) 3).setCellValue("");     						          //岗位  备注： 暂无  后续会设定客户经理级别 级别与基本工资挂钩                     
	        	row.createCell((short) 4).setCellValue((String) salary.getBasePay());             //9月基本工资（元）  
	        	row.createCell((short) 5).setCellValue((String) salary.getSubsidies());			  //岗位津贴
	        	row.createCell((short) 6).setCellValue((String) salary.getAuditNum());			  //当月参与审贷会审议通过笔数
	        	row.createCell((short) 7).setCellValue((String) salary.getVolumePerformance());   //8月业务量绩效（元）        
	        	row.createCell((short) 8).setCellValue("");  									  //8月缺勤扣（元）             手填
	        	row.createCell((short) 9).setCellValue("");  									  //前期差错补扣（元）        手填
	        	row.createCell((short) 10).setCellValue("");  									  //8月其他业务绩效（元） 手填
	        	row.createCell((short) 11).setCellValue("");  									  //8月应发绩效（元）         待确定      
	        	row.createCell((short) 12).setCellValue("");  									  //9月应发薪酬（元）         待确定        
	        	row.createCell((short) 13).setCellValue("");  									  //9月实发薪酬（元）         待确定      
	        }
	        title = title+".xls";
	        response.setHeader("Connection", "close");
	        response.setHeader("Content-Type", "application/vnd.ms-excel;charset=utf-8");
	        response.setHeader("Content-Disposition", "attachment;filename="
	        + new String(title.getBytes(), "iso-8859-1"));
	        OutputStream out = response.getOutputStream();  
	        wb.write(out);
	        out.close();
		}
	//----------------------------------------------济南绩效end----------------------------------------------------//
	
}
