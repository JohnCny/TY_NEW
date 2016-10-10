package com.cardpay.pccredit.customer.service;

import java.io.File;
import java.io.IOException;
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
import com.cardpay.pccredit.customer.dao.comdao.CustomerInforCommDao;
import com.cardpay.pccredit.customer.filter.CustomerInforFilter;
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
import com.cardpay.pccredit.customer.model.MaintenanceLog;
import com.cardpay.pccredit.customer.model.TyCustomerMove;
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
public class CustomerInforMoveService {
	public Logger log = Logger.getLogger(UpdateCustomerTool.class);

	@Autowired
	private CommonDao commonDao;

	@Autowired
	private CustomerInforMoveDao customerInforMoveDao;
	

	/**
	 * 过滤查询
	 * @param filter
	 * @return
	 */
	public QueryResult<CustomerInfoMoveForm> findCustomerMoveByFilter(CustomerInforFilter filter) {
		List<CustomerInfoMoveForm> ls = customerInforMoveDao.findCustomerMoveList(filter);
		int size = customerInforMoveDao.findCustomerMoveListCount(filter);
		QueryResult<CustomerInfoMoveForm> qr = new QueryResult<CustomerInfoMoveForm>(size,ls);
		return qr;
	}
	/**
	 * 获取客户信息
	 * @param filter
	 * @return
	 */
	public CustomerInfor  queryCustomerById(String id) {
		return commonDao.findObjectById(CustomerInfor.class, id);
	}
	/**
	 * 获取客户经理列表
	 * @param filter
	 * @return
	 */
	public List<SystemUser>  queryCustomerAll() {
		return commonDao.queryBySql(SystemUser.class, "select * from sys_user where user_type='1'", null);
	}
	
	/**
	 * 转交申请提交
	 * @param filter
	 * @return
	 */
	public void  approve(String customerId,String approveId,String moveId) {
		try {
			SystemUser approveUser = commonDao.findObjectById(SystemUser.class, approveId) ;
			SystemUser moveUser = commonDao.findObjectById(SystemUser.class, moveId) ;
			TyCustomerMove tyCustomerMove = new TyCustomerMove();
			tyCustomerMove.setCustomerId(customerId);
			tyCustomerMove.setApproveId(approveId);
			tyCustomerMove.setApproveGh(approveUser.getExternalId());
			tyCustomerMove.setApproveName(approveUser.getDisplayName());
			tyCustomerMove.setMoveId(moveId);
			tyCustomerMove.setMoveGh(moveUser.getExternalId());
			tyCustomerMove.setMoveName(moveUser.getDisplayName());
			//申请状态
			tyCustomerMove.setStatus("1");
			tyCustomerMove.setId(IDGenerator.generateID());
			//删除历史记录
			commonDao.queryBySql("delete from ty_customer_move where customer_id='"+customerId+"'", null);
			commonDao.insertObject(tyCustomerMove);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 转交审批
	 * @param filter
	 * @return
	 */
	public void  examine(String id,String type) {
		try {
			//更新申请状态
			TyCustomerMove tyCustomerMove = commonDao.findObjectById(TyCustomerMove.class, id);
			tyCustomerMove.setStatus(type);
			commonDao.updateObject(tyCustomerMove);
			//转交成功
			if(type.equals("2")){
				CustomerInfor infor = commonDao.findObjectById(CustomerInfor.class, tyCustomerMove.getCustomerId());
				infor.setUserId(tyCustomerMove.getMoveId());
				commonDao.updateObject(infor);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
