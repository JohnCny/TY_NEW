package com.cardpay.pccredit.jnpad.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cardpay.pccredit.common.UploadFileTool;
import com.cardpay.pccredit.customer.constant.WfProcessInfoType;
import com.cardpay.pccredit.intopieces.constant.Constant;
import com.cardpay.pccredit.intopieces.dao.LocalExcelDao;
import com.cardpay.pccredit.intopieces.dao.LocalImageDao;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationInfo;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationProcess;
import com.cardpay.pccredit.intopieces.model.LocalExcel;
import com.cardpay.pccredit.intopieces.model.LocalImage;
import com.cardpay.pccredit.intopieces.web.AddIntoPiecesForm;
import com.cardpay.pccredit.system.constants.NodeAuditTypeEnum;
import com.cardpay.pccredit.system.constants.YesNoEnum;
import com.cardpay.pccredit.system.model.NodeAudit;
import com.cardpay.pccredit.system.model.NodeControl;
import com.cardpay.pccredit.system.service.NodeAuditService;
import com.cardpay.workflow.models.WfProcessInfo;
import com.cardpay.workflow.models.WfStatusInfo;
import com.cardpay.workflow.models.WfStatusResult;
import com.cardpay.workflow.service.ProcessService;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.id.IDGenerator;
@Service
public class JnpadLocalImageService {
	@Autowired
	private CommonDao commonDao;
	@Autowired
	private NodeAuditService nodeAuditService;
	@Autowired
	private ProcessService processService;
	@Autowired
	private LocalImageDao localImageDao;
	public void importImage(MultipartFile file, String productId,
			String customerId,String applicationId,String fileName_1) {
		// TODO Auto-generated method stub
		//上传到本地服务器
		Map<String, String> map = UploadFileTool.uploadYxzlFileBySpring1(file,customerId,fileName_1);
		//上传到ftp
		/*Map<String, String> map = SFTPUtil.upload(file, customerId);*/
		String fileName = map.get("fileName");
		String url = map.get("url");
		LocalImage localImage = new LocalImage();
		localImage.setProductId(productId);
		localImage.setCustomerId(customerId);
		if(applicationId != null){
			localImage.setApplicationId(applicationId);
		}
		localImage.setCreatedTime(new Date());
		if (StringUtils.trimToNull(url) != null) {
			localImage.setUri(url);
		}
		if (StringUtils.trimToNull(fileName) != null) {
			localImage.setAttachment(fileName);
		}
		
		commonDao.insertObject(localImage);
	}
	
	public void addIntopieces(AddIntoPiecesForm addIntoPiecesForm,String userId) {
		// TODO Auto-generated method stub
		CustomerApplicationInfo app = new CustomerApplicationInfo();
		app.setCustomerId(addIntoPiecesForm.getCustomerId());
		app.setProductId(addIntoPiecesForm.getProductId());
		app.setCreatedBy(userId);
		app.setCreatedTime(new Date());
		app.setStatus(Constant.APPROVE_INTOPICES);
		app.setId(IDGenerator.generateID());
		
		/*//将调查表和影像件 关联到该app
		LocalExcel localExcel = localExcelDao.findById(addIntoPiecesForm.getExcelId());
		localExcel.setApplicationId(app.getId());*/
		//保存申请额度
		app.setApplyQuota("此产品无申请金额");
		//保存进件表
		commonDao.insertObject(app);
		//commonDao.updateObject(localExcel);	
		List<LocalImage> ls = localImageDao.findAllByProductAndCustomer(addIntoPiecesForm.getProductId(),addIntoPiecesForm.getCustomerId());
		for(LocalImage obj : ls){
			obj.setApplicationId(app.getId());
			commonDao.updateObject(obj);
		}
		//添加流程 20160328 sc
				addProcess(app.getId(),addIntoPiecesForm.getProductId());
			}
			
			public void addProcess(String appId,String productId){
				//添加申请件流程
				WfProcessInfo wf=new WfProcessInfo();
				wf.setProcessType(WfProcessInfoType.process_type);
				wf.setVersion("1");
				commonDao.insertObject(wf);
				List<NodeAudit> list=nodeAuditService.findByNodeTypeAndProductId(NodeAuditTypeEnum.Product.name(),productId);
				boolean startBool=false;
				boolean endBool=false;
				//节点id和WfStatusInfo id的映射
				Map<String, String> nodeWfStatusMap = new HashMap<String, String>();
				for(NodeAudit nodeAudit:list){
					if(nodeAudit.getIsstart().equals(YesNoEnum.YES.name())){
						startBool=true;
					}
					
					if(startBool&&!endBool){
						WfStatusInfo wfStatusInfo=new WfStatusInfo();
						wfStatusInfo.setIsStart(nodeAudit.getIsstart().equals(YesNoEnum.YES.name())?"1":"0");
						wfStatusInfo.setIsClosed(nodeAudit.getIsend().equals(YesNoEnum.YES.name())?"1":"0");
						wfStatusInfo.setRelationedProcess(wf.getId());
						wfStatusInfo.setStatusName(nodeAudit.getNodeName());
						wfStatusInfo.setStatusCode(nodeAudit.getId());
						commonDao.insertObject(wfStatusInfo);
						
						nodeWfStatusMap.put(nodeAudit.getId(), wfStatusInfo.getId());
						
						if(nodeAudit.getIsstart().equals(YesNoEnum.YES.name())){
							//添加初始审核
							CustomerApplicationProcess customerApplicationProcess=new CustomerApplicationProcess();
							String serialNumber = processService.start(wf.getId());
							customerApplicationProcess.setSerialNumber(serialNumber);
							customerApplicationProcess.setNextNodeId(nodeAudit.getId()); 
							customerApplicationProcess.setApplicationId(appId);
							commonDao.insertObject(customerApplicationProcess);
							
							CustomerApplicationInfo applicationInfo = commonDao.findObjectById(CustomerApplicationInfo.class, appId);
							applicationInfo.setSerialNumber(serialNumber);
							commonDao.updateObject(applicationInfo);
						}
					}
					
					if(nodeAudit.getIsend().equals(YesNoEnum.YES.name())){
						endBool=true;
					}
				}
				//节点关系
				List<NodeControl> nodeControls = nodeAuditService.findNodeControlByNodeTypeAndProductId(NodeAuditTypeEnum.Product.name(), productId);
				for(NodeControl control : nodeControls){
					WfStatusResult wfStatusResult = new WfStatusResult();
					wfStatusResult.setCurrentStatus(nodeWfStatusMap.get(control.getCurrentNode()));
					wfStatusResult.setNextStatus(nodeWfStatusMap.get(control.getNextNode()));
					wfStatusResult.setExamineResult(control.getCurrentStatus());
					commonDao.insertObject(wfStatusResult);
				}
			}}