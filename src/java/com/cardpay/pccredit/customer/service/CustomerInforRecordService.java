package com.cardpay.pccredit.customer.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cardpay.pccredit.common.Dictionary;
import com.cardpay.pccredit.common.UploadFileTool;
import com.cardpay.pccredit.customer.constant.CustomerInforConstant;
import com.cardpay.pccredit.customer.constant.CustomerInforDStatusEnum;
import com.cardpay.pccredit.customer.constant.WfProcessInfoType;
import com.cardpay.pccredit.customer.dao.CustomerInforDao;
import com.cardpay.pccredit.customer.dao.CustomerInforMoveDao;
import com.cardpay.pccredit.customer.dao.CustomerInforRecordDao;
import com.cardpay.pccredit.customer.dao.comdao.CustomerInforCommDao;
import com.cardpay.pccredit.customer.filter.CustomerInforFilter;
import com.cardpay.pccredit.customer.filter.CustomerRecordFilter;
import com.cardpay.pccredit.customer.filter.VideoAccessoriesFilter;
import com.cardpay.pccredit.customer.model.CustomerCareersInformation;
import com.cardpay.pccredit.customer.model.CustomerFirsthendBase;
import com.cardpay.pccredit.customer.model.CustomerFirsthendBaseLocal;
import com.cardpay.pccredit.customer.model.CustomerFirsthendFamilyCc;
import com.cardpay.pccredit.customer.model.CustomerFirsthendFamilyCy;
import com.cardpay.pccredit.customer.model.CustomerFirsthendManage;
import com.cardpay.pccredit.customer.model.CustomerFirsthendSafe;
import com.cardpay.pccredit.customer.model.CustomerFirsthendStudy;
import com.cardpay.pccredit.customer.model.CustomerFirsthendWork;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.model.CustomerInforWeb;
import com.cardpay.pccredit.customer.model.CustomerRecord;
import com.cardpay.pccredit.customer.model.MaintenanceLog;
import com.cardpay.pccredit.customer.model.TyCustomerMove;
import com.cardpay.pccredit.customer.model.TyCustomerRecord;
import com.cardpay.pccredit.customer.model.TyProductType;
import com.cardpay.pccredit.customer.model.TyRepayTkmx;
import com.cardpay.pccredit.customer.model.TyRepayYehz;
import com.cardpay.pccredit.customer.web.CustomerInfoMoveForm;
import com.cardpay.pccredit.customer.web.MaintenanceForm;
import com.cardpay.pccredit.datapri.service.DataAccessSqlService;
import com.cardpay.pccredit.intopieces.constant.Constant;
import com.cardpay.pccredit.intopieces.constant.IntoPiecesException;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationContact;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationGuarantor;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationInfo;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationOther;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationProcess;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationRecom;
import com.cardpay.pccredit.intopieces.model.IntoPieces;
import com.cardpay.pccredit.intopieces.model.LocalExcel;
import com.cardpay.pccredit.intopieces.model.VideoAccessories;
import com.cardpay.pccredit.ipad.model.ProductAttribute;
import com.cardpay.pccredit.riskControl.xml.XmlUtil;
import com.cardpay.pccredit.system.constants.NodeAuditTypeEnum;
import com.cardpay.pccredit.system.constants.YesNoEnum;
import com.cardpay.pccredit.system.model.Dict;
import com.cardpay.pccredit.system.model.NodeAudit;
import com.cardpay.pccredit.system.model.NodeControl;
import com.cardpay.pccredit.system.model.SystemUser;
import com.cardpay.pccredit.system.service.NodeAuditService;
import com.cardpay.pccredit.tools.CardFtpUtils;
import com.cardpay.pccredit.tools.DataFileConf;
import com.cardpay.pccredit.tools.ImportBankDataFileTools;
import com.cardpay.pccredit.tools.ImportParameter;
import com.cardpay.pccredit.tools.JXLReadExcel;
import com.cardpay.pccredit.tools.SPTxt;
import com.cardpay.pccredit.tools.UpdateCustomerTool;
import com.cardpay.workflow.models.WfProcessInfo;
import com.cardpay.workflow.models.WfStatusInfo;
import com.cardpay.workflow.models.WfStatusResult;
import com.cardpay.workflow.service.ProcessService;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.id.IDGenerator;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.modules.privilege.model.User;

/**
 * 
 * @author shaoming
 *
 */
@Service
public class CustomerInforRecordService {
	public Logger log = Logger.getLogger(UpdateCustomerTool.class);

	@Autowired
	private CommonDao commonDao;

	@Autowired
	private CustomerInforRecordDao customerInforRecordDao;
	

	/**
	 * 过滤查询
	 * @param filter
	 * @return
	 */
	public QueryResult<CustomerRecord> findCustomerRecordByFilter(CustomerRecordFilter filter) {
		List<CustomerRecord> ls = customerInforRecordDao.findCustomerRecordList(filter);
		int size = customerInforRecordDao.findCustomerRecordListCount(filter);
		QueryResult<CustomerRecord> queryResult = new QueryResult<CustomerRecord>(size,ls);
		List<CustomerRecord> intoPieces = queryResult.getItems();
		for(CustomerRecord pieces : intoPieces){

			//获取客户经理组长
			String userId = pieces.getUserId();
			String sql = "select d.* from account_manager_parameter c,sys_user d where c.user_id=d.id and c.id in (select a.parent_id from manager_belong_map a,account_manager_parameter b where a.child_id=b.id and b.user_id='"+userId+"')";
			List<SystemUser> list = commonDao.queryBySql(SystemUser.class, sql, null);
			if(list.size()>0){
				pieces.setGroupName(list.get(0).getDisplayName());
			}else{
				pieces.setGroupName(pieces.getDisplayName());
			}
		}
		return queryResult;
	}
	
	/**
	 * 申请借阅
	 * @param id
	 */
	public void apply(String id){
		TyCustomerRecord record = commonDao.findObjectById(TyCustomerRecord.class, id);
		record.setStatus(Constant.RECORD_APPLY);
		commonDao.updateObject(record);
	}
	/**
	 * 审批借阅
	 * @param id
	 */
	public void approve(String id){
			TyCustomerRecord record = commonDao.findObjectById(TyCustomerRecord.class, id);
			record.setStatus(Constant.RECORD_OUT);
			record.setOutTime(new Date());
			commonDao.updateObject(record);
	}
	/**
	 * 归还
	 * @param id
	 */
	public void approveOut(String id){
			TyCustomerRecord record = commonDao.findObjectById(TyCustomerRecord.class, id);
			record.setStatus(Constant.RECORD_IN);
			record.setInTime(new Date());
			commonDao.updateObject(record);
	}
	
	/**
	 * 录入过滤查询
	 * @param filter
	 * @return
	 */
	public QueryResult<CustomerRecord> findCustomerRecordEnterByFilter(CustomerRecordFilter filter) {
		List<CustomerRecord> ls = customerInforRecordDao.findCustomerRecordEnterList(filter);
		int size = customerInforRecordDao.findCustomerRecordEnterListCount(filter);
		QueryResult<CustomerRecord> queryResult = new QueryResult<CustomerRecord>(size,ls);
		List<CustomerRecord> intoPieces = queryResult.getItems();
		for(CustomerRecord pieces : intoPieces){

			//获取客户经理组长
			String userId = pieces.getUserId();
			String sql = "select d.* from account_manager_parameter c,sys_user d where c.user_id=d.id and c.id in (select a.parent_id from manager_belong_map a,account_manager_parameter b where a.child_id=b.id and b.user_id='"+userId+"')";
			List<SystemUser> list = commonDao.queryBySql(SystemUser.class, sql, null);
			if(list.size()>0){
				pieces.setGroupName(list.get(0).getDisplayName());
			}else{
				pieces.setGroupName(pieces.getDisplayName());
			}
		}
		return queryResult;
	}
	
	/**
	 * 保存档案编号
	 * @param id
	 */
	public String enterSave(String recordNo,String appId){
		List<TyCustomerRecord> list = commonDao.queryBySql(TyCustomerRecord.class, "select * from ty_customer_record where record_id='"+recordNo+"'", null);
		if(list.size()>0){
			return "档案编号已存在!";
		}else{
			List<TyCustomerRecord> recordlist = commonDao.queryBySql(TyCustomerRecord.class, "select * from ty_customer_record where application_id='"+appId+"'", null);
			if(recordlist.size()>0){
				TyCustomerRecord record = recordlist.get(0);
				record.setRecordId(recordNo);
				commonDao.updateObject(record);
			}else{
				TyCustomerRecord record = new TyCustomerRecord();
				record.setId(IDGenerator.generateID());
				record.setApplicationId(appId);
				record.setRecordId(recordNo);
				record.setStatus(Constant.RECORD_UN);
				commonDao.insertObject(record);
			}
			return null;
		}
	}
	
	public Boolean enterFileSave(MultipartFile file, String customerId) {
		Map<String, String> map = UploadFileTool.uploadYxzlFileBySpring(file,customerId);
		JXLReadExcel excel = new JXLReadExcel();
		String url = map.get("url");
		 File sourcefile = new File(url);
		 InputStream is=null;
		try {
			is = new FileInputStream(sourcefile);
			Workbook wb = WorkbookFactory.create(is);
			List<String> list = new ArrayList<String>();
                Sheet sheet = wb.getSheetAt(0);//获取第一个Sheet的内容
         	   int lastRowNum = sheet.getLastRowNum();
         	  Row row = null;        //兼容
         	  Cell cell = null;    //兼容
         	  for (int rowNum = sheet.getFirstRowNum(); rowNum <= lastRowNum; rowNum++) {
		       		   row = sheet.getRow(rowNum);
		       		   if(rowNum==0){
			       			cell = row.getCell(0);
				       		String	stringValue = excel.getCellValue(cell);
				       		if(!stringValue.contains("编号")){
				       			return false;
				       		}
		       		    }else{
		       		    	cell = row.getCell(0);
				       		String	recordNo = excel.getCellValue(cell);
				       		cell = row.getCell(1);
				       		String	cardId = excel.getCellValue(cell);
				       		cell = row.getCell(2);
				       		String	quota = excel.getCellValue(cell);
				       		list.add(recordNo+"@"+cardId+"@"+quota);
		       		    }
         	  }
         	  if(list.size()>0){
         		  for(int i=0;i<list.size();i++){
         			  String str = list.get(i);
         			  String recordNo = str.split("@")[0];
         			  String cardId = str.split("@")[1];
         			  String quota = str.split("@")[2];
         			  String sql = "select * from customer_application_info where SUBSTR(actual_quote,1,3)='"+quota.substring(0,3)+"' and " +
         			  		"customer_id in (select id from basic_customer_information where card_id='"+cardId+"')";
         			  List<CustomerApplicationInfo> infoList = commonDao.queryBySql(CustomerApplicationInfo.class, sql, null);
         			  if(infoList.size()>0){
         				  String appId = infoList.get(0).getId();
         				  List<TyCustomerRecord> recordlist = commonDao.queryBySql(TyCustomerRecord.class, "select * from ty_customer_record where application_id='"+appId+"'", null);
	         				if(recordlist.size()>0){
	         					TyCustomerRecord record = recordlist.get(0);
	         					record.setRecordId(recordNo);
	         					commonDao.updateObject(record);
	         				}else{
	         					TyCustomerRecord record = new TyCustomerRecord();
	         					record.setId(IDGenerator.generateID());
	         					record.setApplicationId(appId);
	         					record.setRecordId(recordNo);
	         					record.setStatus(Constant.RECORD_UN);
	         					commonDao.insertObject(record);
	         				}
         			  }
         		  }
         	  }
         	  return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	
	}
	
	public Boolean delay(String userId){
		String sql = "select b.* from CUSTOMER_APPLICATION_INFO a,TY_CUSTOMER_RECORD b,dual c where a.id=b.APPLICATION_ID and b.STATUS='4'  " +
				"and TO_DATE(a.JKRQ, 'yyyymmdd')<trunc(SYSDATE-8) and a.customer_id in (select id from basic_customer_information where user_id='"+userId+"')";
		List<TyCustomerRecord> list = commonDao.queryBySql(TyCustomerRecord.class, sql, null);
		if(list.size()>0){
			return false;
		}
		return true;
	}
}
