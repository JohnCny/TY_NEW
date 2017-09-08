package com.cardpay.pccredit.rongyaoka.service;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;







import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cardpay.pccredit.common.SFTPUtil;
import com.cardpay.pccredit.common.UploadFileTool;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.divisional.service.DivisionalService;
import com.cardpay.pccredit.intopieces.constant.Constant;
import com.cardpay.pccredit.intopieces.dao.IntoPiecesDao;
import com.cardpay.pccredit.intopieces.dao.comdao.IntoPiecesComdao;
import com.cardpay.pccredit.intopieces.filter.IntoPiecesFilter;
import com.cardpay.pccredit.intopieces.model.AppManagerAuditLog;
import com.cardpay.pccredit.intopieces.model.IntoPieces;
import com.cardpay.pccredit.intopieces.model.LocalExcel;
import com.cardpay.pccredit.intopieces.web.CustomerApplicationIntopieceWaitForm;
import com.cardpay.pccredit.jnpad.model.FwqUtils;
import com.cardpay.pccredit.report.dao.CustomerTransferFlowDao;
import com.cardpay.pccredit.rongyaoka.dao.RyIntoPiecesComDao;
import com.cardpay.pccredit.tools.JXLReadExcel;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.model.QueryResult;

@Service("ryIntoPiecesService")
public class ryIntoPiecesService {

	// TODO 路径使用相对路径，首先获得应用所在路径，之后建立上传文件目录，图片类型使用IMG，文件使用DOC

	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private IntoPiecesDao intoPiecesDao;

	@Autowired
	private  IntoPiecesComdao intoPiecesComdao;
	
	@Autowired
	private CustomerInforService customerInforService;
	
	@Autowired
	private DivisionalService divisionalService;

	@Autowired
	private CustomerTransferFlowDao customerTransferFlowDao;
	@Autowired
	private RyIntoPiecesComDao rpcdao;
	
	
	/* 查询进价信息 */
	/*
	 * TODO 1.添加注释 2.SQL写进DAO层
	 */
	public   QueryResult<IntoPieces> findintoPiecesByFilter(
			IntoPiecesFilter filter) {
		//List<IntoPieces> queryResult=customerTransferFlowDao.findintoPiecesByFilters(filter);
		//int sum = customerTransferFlowDao.findintoPiecesByFilterCount(filter);
		List<IntoPieces> queryResult = rpcdao.findintoPiecesByFilters(filter);
		int sum = rpcdao.findintoPiecesByFilterCount(filter);
		QueryResult<IntoPieces> qs = new QueryResult<IntoPieces>(sum, queryResult);
		List<IntoPieces> intoPieces = qs.getItems();
		for(IntoPieces pieces : intoPieces){
			if(pieces.getStatus()==null){
				pieces.setNodeName("未提交申请");
			}
			else{
				if(pieces.getStatus().equals(Constant.SAVE_INTOPICES)){
					pieces.setNodeName("未提交申请");
				}else if(pieces.getStatus().equals(Constant.APPROVE_INTOPICES)){
					//String nodeName = intoPiecesComdao.findAprroveProgress(pieces.getId());
					String nodeName = intoPiecesComdao.findNodeName(pieces.getId());
					if(StringUtils.isNotEmpty(nodeName)){
						pieces.setNodeName(nodeName);
					} else {
						pieces.setNodeName("不在审批中");
					}
				}else if(pieces.getStatus().equals(Constant.REFUSE_INTOPICES)||pieces.getStatus().equals("returnedToFirst")){
					List<HashMap<String, Object>> list = intoPiecesComdao.findNodeNameJN(pieces.getId());
					String refusqlReason ="";
					String fallBackReason ="";
					if(list != null && list.size() > 0){
						HashMap<String, Object> map = list.get(0);
						refusqlReason = (String) map.get("REFUSAL_REASON");
						fallBackReason =(String) map.get("FALLBACK_REASON");
					}
					pieces.setNodeName("审批结束");
					pieces.setRefusqlReason(refusqlReason);
					pieces.setFallBackReason(fallBackReason);
				}else {
					pieces.setNodeName("审批结束");
				}
			}
		}
		return qs;
	/*	//QueryResult<IntoPieces> queryResult = intoPiecesComdao.findintoPiecesByFilter(filter);
		List<IntoPieces> plans = intoPiecesDao.findIntoPiecesList(filter);
		int size = intoPiecesDao.findIntoPiecesCountList(filter);
		QueryResult<IntoPieces> queryResult = new QueryResult<IntoPieces>(size,plans);
		
		List<IntoPieces> intoPieces = queryResult.getItems();
		for(IntoPieces pieces : intoPieces){
			if(pieces.getStatus().equals(Constant.SAVE_INTOPICES)){
				pieces.setNodeName("未提交申请");
			} else if(pieces.getStatus().equals(Constant.APPROVE_INTOPICES)){
				String nodeName = intoPiecesComdao.findAprroveProgress(pieces.getId());
				if(StringUtils.isNotEmpty(nodeName)){
					pieces.setNodeName(nodeName);
				} else {
					pieces.setNodeName("不在审批中");
				}
			} else {
				pieces.setNodeName("审批通过");
			}
			//获取客户经理组长
			String userId = pieces.getUserId();
			String sql = "select d.* from account_manager_parameter c,sys_user d where c.user_id=d.id and c.id in (select a.parent_id from manager_belong_map a,account_manager_parameter b where a.child_id=b.id and b.user_id='"+userId+"')";
			List<SystemUser> list = commonDao.queryBySql(SystemUser.class, sql, null);
			if(list.size()>0){
				pieces.setGroupName(list.get(0).getDisplayName());
			}
		}
		return queryResult;*/
	}


	public void updatecapnextnodeid(String nodeName, String applicationId) {
		// TODO Auto-generated method stub
		rpcdao.updatecapnextnodeid(nodeName,applicationId);
	}


	/*public QueryResult<CustomerApplicationIntopieceWaitForm> findCustomerApplicationIntopieceDecison(
			IntoPiecesFilter filter) {
		// TODO Auto-generated method stub
		List<CustomerApplicationIntopieceWaitForm> listCAI = rpcdao.findCustomerApplicationIntopieceDecisionForm(filter);
		int size = rpcdao.findCustomerApplicationIntopieceDecisionCountForm(filter);
		QueryResult<CustomerApplicationIntopieceWaitForm> qs = new QueryResult<CustomerApplicationIntopieceWaitForm>(size, listCAI);
		return qs;
	}*/

public List<CustomerApplicationIntopieceWaitForm>  findfsCustomer(String userId, String cardId, String chineseName,
			String nextNodeName){
				return rpcdao.findCustomerApplicationIntopieceDecisionForm(userId,cardId,chineseName,nextNodeName);
	
}
	public QueryResult<CustomerApplicationIntopieceWaitForm> findCustomerApplicationIntopieceDecison(
			String userId, String cardId, String chineseName,
			String nextNodeName) {
		// TODO Auto-generated method stub
		List<CustomerApplicationIntopieceWaitForm> listCAI = rpcdao.findCustomerApplicationIntopieceDecisionForm(userId,cardId,chineseName,nextNodeName);
		int size = rpcdao.findCustomerApplicationIntopieceDecisionCountForm(userId,cardId,chineseName,nextNodeName);
		QueryResult<CustomerApplicationIntopieceWaitForm> qs = new QueryResult<CustomerApplicationIntopieceWaitForm>(size, listCAI);
		return qs;
	}


	public void updateCsjl(AppManagerAuditLog appManagerAuditLog) {
		// TODO Auto-generated method stub
		rpcdao.updateCsjl(appManagerAuditLog);
	}


	public void updateCSZT(String userId, Date times, String money,
			String applicationId) {
		// TODO Auto-generated method stub
		rpcdao.updateCSZT(userId,times,money,applicationId);
	}


	//导入调查报告
	public void importExcel(MultipartFile file,String productId, String customerId) {
		// TODO Auto-generated method stub
		Map<String, String> map=null;
		if(FwqUtils.typeCode==0){
		//本地测试
		 map = UploadFileTool.uploadYxzlFileBySpring(file,customerId);
		}else{
			//指定服务器上传 
			 map=SFTPUtil.uploadJn(file, customerId);
		}
		//Map<String, String> map = UploadFileTool.uploadYxzlFileBySpring(file,customerId);
		String fileName = map.get("fileName");
		String url = map.get("url");
		LocalExcel localExcel = new LocalExcel();
		localExcel.setFileName(fileName);
		localExcel.setProductId(productId);
		localExcel.setCustomerId(customerId);
		localExcel.setCreatedTime(new Date());
		if (StringUtils.trimToNull(url) != null) {
			localExcel.setUri(url);
		}
		if (StringUtils.trimToNull(fileName) != null) {
			localExcel.setAttachment(fileName);
		}
		
		//读取excel内容
		JXLReadExcel readExcel = new JXLReadExcel();
		String sheet[];
		if(FwqUtils.typeCode==0){
			sheet= readExcel.readExcelToHtml(url, true,fileName);
		}else{
			sheet= SFTPUtil.readExcelToHtml(url, true,fileName);
		}
		//本地测试
		//String sheet[] = readExcel.readExcelToHtml(url, true,fileName);
		//服务器  修改标准经营性(新增)调查表     
		//String sheet[] = SFTPUtil.readExcelToHtml(url, true,fileName);
		for(String str : sheet){
			if(StringUtils.isEmpty(sheet[19])){
				throw new RuntimeException("导入失败，请检查excel文件与模板是否一致或者申请金额填写！");
			}
				}
		
		localExcel.setSheetXjllb(sheet[9]);
		localExcel.setSheetYfys(sheet[13]);
		localExcel.setSheetGdzc(sheet[12]);
		localExcel.setSheetDhd(sheet[11]);
		localExcel.setSheetYsyf(sheet[14]);
		localExcel.setSheetJy(sheet[17]);    //调查表  
		localExcel.setSheetJbzk(sheet[18]);
		localExcel.setHkjhb(sheet[20]);
		//消费贷
		localExcel.setZyxzcb(sheet[21]);
		localExcel.setTzxzcb(sheet[22]);
		localExcel.setFzjsb(sheet[23]);
		
		if(sheet[19].contains("元")){
			localExcel.setApproveValue(sheet[19].substring(0,sheet[19].indexOf("元")));
		}else{
		    localExcel.setApproveValue(sheet[19]);
		}
		//删除旧模板
		 String sql = "delete from local_excel where customer_id='"+customerId+"' and product_id='"+productId+"'";
		//String sql = "delete from local_excel where customer_id='"+customerId+"' and product_id='"+productId+"' and FILE_NAME='"+fileName+"'";
		commonDao.queryBySql(LocalExcel.class, sql, null);
		//添加模板
		commonDao.insertObject(localExcel);
	}


	
	
}
