package com.cardpay.pccredit.intopieces.dao.comdao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.customer.model.CustomerCareersInformation;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.intopieces.filter.IntoPiecesFilter;
import com.cardpay.pccredit.intopieces.filter.MakeCardFilter;
import com.cardpay.pccredit.intopieces.model.ApplicationDataImport;
import com.cardpay.pccredit.intopieces.model.CustomerAccountData;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationCom;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationContact;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationGuarantor;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationInfo;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationOther;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationProcessForm;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationRecom;
import com.cardpay.pccredit.intopieces.model.CustomerCreditInfo;
import com.cardpay.pccredit.intopieces.model.IntoPieces;
import com.cardpay.pccredit.intopieces.model.MakeCard;
import com.cardpay.pccredit.intopieces.model.VideoAccessories;
import com.cardpay.pccredit.intopieces.web.ApproveHistoryForm;
import com.cardpay.pccredit.product.model.AddressAccessories;
import com.cardpay.pccredit.product.model.ManagerProductsConfiguration;
import com.cardpay.pccredit.riskControl.model.RiskCustomer;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.model.QueryResult;

@Service
public class IntoPiecesComdao {

	@Autowired
	private CommonDao commonDao;

	/* 查询进价信息 */
	public QueryResult<IntoPieces> findintoPiecesByFilter(
			IntoPiecesFilter filter) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		String id = filter.getId();
		String chineseName  = filter.getChineseName();
		String productName = filter.getProductName();
		String userId = filter.getUserId();
		String cardId = filter.getCardId();
		String status = filter.getStatus();
		params.put("userId", userId);
		StringBuffer sql = new StringBuffer("select * from (select t.id,t.customer_id,b.ty_customer_id,b.chinese_name,t.product_id,p.product_name,b.card_id,t.apply_quota,t.final_approval,t.status,t.CREATED_TIME,t.ACTUAL_QUOTE as reqlmt,usr.display_name, b.user_id,"
				+ "(select org.name from sys_organization org where id in(select t.org_id from sys_department t where t.id in (select tt.dept_id from sys_dept_user tt where tt.user_id = b.user_id)))as organcode "
				+ "from customer_application_info t,basic_customer_information b,product_attribute p,sys_user usr "
				+ "where t.customer_id=b.id  and t.product_id=p.id and usr.id = b.user_id) where 1=1 ");
		if(StringUtils.trimToNull(userId)!=null){
			sql.append("and user_id = #{userId}");
		}
		
		if(StringUtils.trimToNull(filter.getStartAmt())!=null){
			params.put("startAmt", filter.getStartAmt());
			sql.append("and APPLY_QUOTA >= #{startAmt}");
		}
		
		if(StringUtils.trimToNull(filter.getEndAmt())!=null){
			params.put("endAmt", filter.getEndAmt());
			sql.append("and APPLY_QUOTA <= #{endAmt}");
		}
		
		String custManagerIds = filter.getCustManagerIds();
		if(StringUtils.trimToNull(custManagerIds)!=null){
			sql.append("and user_id in ");
			sql.append(custManagerIds);
		}
		
		if(StringUtils.trimToNull(filter.getCustManagerId())!=null&&!"0".equals(filter.getCustManagerId())){
			params.put("custManagerId", filter.getCustManagerId());
			sql.append("and user_id = #{custManagerId}");
		}
		
		if(StringUtils.trimToNull(filter.getOrganName())!=null){
			params.put("organName", filter.getOrganName());
			sql.append("and organcode = #{organName}");
		}
		
		if(StringUtils.trimToNull(productName)!=null){
			params.put("productName", productName);
			 sql.append(" and product_name like '%'||#{productName}||'%' ");
			}
		
		if(StringUtils.trimToNull(id)!=null){
			params.put("id", id);
			sql.append(" and id like '%'||#{id}||'%' ");
			}
		if(StringUtils.trimToNull(status)!=null){
			params.put("status", status);
			sql.append("and status= #{status}");
			}
		if(StringUtils.trimToNull(cardId)!=null||StringUtils.trimToNull(chineseName)!=null){
			if(StringUtils.trimToNull(cardId)!=null&&StringUtils.trimToNull(chineseName)!=null){
			    //sql.append(" and (b.card_id like '%"+cardId+"%' or b.chinese_name like '%"+chineseName+"%' )");
			    sql.append(" and card_id like '%"+cardId+"%' and chinese_name like '%"+chineseName+"%' ");
			}else if(StringUtils.trimToNull(cardId)!=null&&StringUtils.trimToNull(chineseName)==null){
				params.put("cardId", cardId);
				sql.append(" and card_id like '%'||#{cardId}||'%' ");
			}else if(StringUtils.trimToNull(cardId)==null&&StringUtils.trimToNull(chineseName)!=null){
				params.put("chineseName", chineseName);
				sql.append(" and chinese_name like '%'||#{chineseName}||'%' ");
			}
		}
		
		sql.append(" order by id asc");
		return commonDao.queryBySqlInPagination(IntoPieces.class, sql.toString(), params,
				filter.getStart(), filter.getLimit());
	}
	
	
	
	/* 查询进价信息1 */
	public List<IntoPieces> findintoPiecesListByFilter(
			IntoPiecesFilter filter) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		String id = filter.getId();
		String chineseName  = filter.getChineseName();
		String productName = filter.getProductName();
		String userId = filter.getUserId();
		String cardId = filter.getCardId();
		String status = filter.getStatus();
		params.put("userId", userId);
		StringBuffer sql = new StringBuffer("select * from (select t.id,t.customer_id,b.ty_customer_id,b.chinese_name,t.product_id,p.product_name,b.card_id,t.apply_quota,t.final_approval,t.status,t.CREATED_TIME,t.ACTUAL_QUOTE as reqlmt,usr.display_name, b.user_id,(select org.name from sys_organization org where id in(select t.org_id from sys_department t where t.id in (select tt.dept_id from sys_dept_user tt where tt.user_id = b.user_id)))as organcode from customer_application_info t,basic_customer_information b,product_attribute p,sys_user usr where t.customer_id=b.id  and t.product_id=p.id and usr.id = b.user_id) where 1=1 ");
		if(StringUtils.trimToNull(userId)!=null){
			sql.append("and user_id = #{userId}");
		}
		
		if(StringUtils.trimToNull(filter.getStartAmt())!=null){
			params.put("startAmt", filter.getStartAmt());
			sql.append("and APPLY_QUOTA >= #{startAmt}");
		}
		
		if(StringUtils.trimToNull(filter.getEndAmt())!=null){
			params.put("endAmt", filter.getEndAmt());
			sql.append("and APPLY_QUOTA <= #{endAmt}");
		}
		
		String custManagerIds = filter.getCustManagerIds();
		if(StringUtils.trimToNull(custManagerIds)!=null){
			sql.append("and user_id in ");
			sql.append(custManagerIds);
		}
		
		if(StringUtils.trimToNull(filter.getCustManagerId())!=null&&!"0".equals(filter.getCustManagerId())){
			params.put("custManagerId", filter.getCustManagerId());
			sql.append("and user_id = #{custManagerId}");
		}
		
		if(StringUtils.trimToNull(filter.getOrganName())!=null){
			params.put("organName", filter.getOrganName());
			sql.append("and organcode = #{organName}");
		}
		
		if(StringUtils.trimToNull(productName)!=null){
			params.put("productName", productName);
			 sql.append(" and product_name like '%'||#{productName}||'%' ");
			}
		
		if(StringUtils.trimToNull(id)!=null){
			params.put("id", id);
			sql.append(" and id like '%'||#{id}||'%' ");
			}
		if(StringUtils.trimToNull(status)!=null){
			params.put("status", status);
			sql.append("and status= #{status}");
			}
		if(StringUtils.trimToNull(cardId)!=null||StringUtils.trimToNull(chineseName)!=null){
			if(StringUtils.trimToNull(cardId)!=null&&StringUtils.trimToNull(chineseName)!=null){
			    //sql.append(" and (b.card_id like '%"+cardId+"%' or b.chinese_name like '%"+chineseName+"%' )");
			    sql.append(" and card_id like '%"+cardId+"%' and chinese_name like '%"+chineseName+"%' ");
			}else if(StringUtils.trimToNull(cardId)!=null&&StringUtils.trimToNull(chineseName)==null){
				params.put("cardId", cardId);
				sql.append(" and card_id like '%'||#{cardId}||'%' ");
			}else if(StringUtils.trimToNull(cardId)==null&&StringUtils.trimToNull(chineseName)!=null){
				params.put("chineseName", chineseName);
				sql.append(" and chinese_name like '%'||#{chineseName}||'%' ");
			}
		}
		
		sql.append(" order by id asc");
		return commonDao.queryBySql(IntoPieces.class, sql.toString(), params);
	}
	
	/* 查询进价信息 */
	public QueryResult<IntoPieces> findintoApplayPiecesByFilter(
			IntoPiecesFilter filter) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		String sql = "select t.id,t.customer_id,b.chinese_name,t.product_id,p.product_name,b.card_id,t.apply_quota,t.status from customer_application_info t,basic_customer_information b,product_attribute p where t.customer_id=b.id and t.product_id=p.id and t.status='succeed' order by t.id asc";
		return commonDao.queryBySqlInPagination(IntoPieces.class, sql, params,
				filter.getStart(), filter.getLimit());
	}
	
	/* 查询进价信息 */
	public QueryResult<MakeCard> findCardByFilter(
			MakeCardFilter filter) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer("select * from make_card t where 1=1 ");
		if(filter!=null&&StringUtils.trimToNull(filter.getCardNumber())!=null){
			sql.append(" and card_number="+"'"+StringUtils.trim(filter.getCardNumber())+"'");
		}
		if(filter!=null&&StringUtils.trimToNull(filter.getCardOrganization())!=null){
			sql.append(" and card_organization="+"'"+StringUtils.trim(filter.getCardOrganization())+"'");
		}
		return commonDao.queryBySqlInPagination(MakeCard.class, sql.toString(), params,
				filter.getStart(), filter.getLimit());
	}
	

	/* 客户查询用户名模糊匹配 */
	public void selectLikeByCardId(HttpServletResponse response,
			String tempParam) throws Exception {
		String country = null;
		List<CustomerInfor> allList = commonDao.queryBySql(CustomerInfor.class,
				"select * from basic_customer_information", null);
		List<CustomerInfor> matched = new ArrayList<CustomerInfor>();
		for (CustomerInfor customerInfor : allList) {
			if (customerInfor.getCardId() != null) {
				country = customerInfor.getCardId().toLowerCase();
				if (tempParam != null
						&& country.startsWith(tempParam.toLowerCase())) {
					matched.add(customerInfor);
				}
			}
		}
		if (!matched.isEmpty()) {
			JSONArray obj = JSONArray.fromObject(matched);
			response.getWriter().println(obj.toString());
		}
	}

	/* 根据客户id查询客户职业资料 */
	public CustomerCareersInformation findCustomerCareersInformationByCustomerId(
			String customerId) {
		List<CustomerCareersInformation> list = commonDao.queryBySql(
				CustomerCareersInformation.class,
				"select * from customer_careers_information where customer_id='"
						+ customerId + "'", null);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	/* 根据申请id查询客户联系人资料 */
	public List<CustomerApplicationContact> findCustomerApplicationContactsByApplicationId(
			String applicationId) {
		List<CustomerApplicationContact> list = commonDao
				.queryBySql(
						CustomerApplicationContact.class,
						"select * from customer_application_contact t where t.main_application_form_id ='"
								+ applicationId + "' order by created_time ", null);
		return list;
	}

	/* 根据申请id查询客户推荐人资料 */
	public List<CustomerApplicationRecom> findCustomerApplicationRecomsByApplicationId(
			String applicationId) {
		List<CustomerApplicationRecom> list = commonDao
				.queryBySql(
						CustomerApplicationRecom.class,
						"select * from customer_application_recom t where t.main_application_form_id ='"
								+ applicationId + "' order by created_time ", null);
		return list;
	}

	/* 根据申请id查询担保人资料 */
	public List<CustomerApplicationGuarantor> findCustomerApplicationGuarantorsByApplicationId(
			String applicationId) {
		List<CustomerApplicationGuarantor> list = commonDao
				.queryBySql(
						CustomerApplicationGuarantor.class,
						"select * from customer_application_guarantor t where t.main_application_form_id ='"
								+ applicationId + "' order by created_time ", null);
		return list;
	}
	
	/* 根据申请id查询申请主表信息 */
	public CustomerApplicationInfo findCustomerApplicationInfoByApplicationId(
			String applicationId) {
		CustomerApplicationInfo customerApplicationInfo = commonDao
				.findObjectById(
						CustomerApplicationInfo.class,applicationId);
		return customerApplicationInfo;
	}
	
	/* 根据id查询申请主表信息 的其他资料信息*/
	public CustomerApplicationOther findCustomerApplicationOtherByApplicationId(
			String applicationId) {
		List<CustomerApplicationOther> list = commonDao
				.queryBySql(
						CustomerApplicationOther.class,
						"select * from customer_application_other t where t.main_application_form_id ='"
								+ applicationId + "'", null);
		if(list!=null&&!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	
	/* 根据id查询申请主表信息 的行社专栏信息*/
	public CustomerApplicationCom findCustomerApplicationComByApplicationId(
			String applicationId) {
		List<CustomerApplicationCom> list = commonDao
				.queryBySql(
						CustomerApplicationCom.class,
						"select * from customer_application_com t where t.main_application_form_id ='"
								+ applicationId + "'", null);
		if(list!=null&&!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	
	/* 根据id查询申请主表信息 的行社专栏信息*/
	public CustomerAccountData findCustomerAccountDataByApplicationId(
			String applicationId) {
		List<CustomerAccountData> list = commonDao
				.queryBySql(
						CustomerAccountData.class,
						"select * from customer_account_data t where t.main_application_form_id ='"
								+ applicationId + "'", null);
		if(list!=null&&!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	
	
	/* 根据客户id查询客户申请主表信息 的影像资料信息*/
	public VideoAccessories findVideoAccessoriesByCustomerId(
			String customerId) {
		List<VideoAccessories> list = commonDao
				.queryBySql(
						VideoAccessories.class,
						"select * from video_accessories t,customer_application_info f where f.id=t.application_id and f.customer_id='"
								+ customerId + "'", null);
		if(list!=null&&!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	
	
	
	/* 根据客户信息是否在系统中存在*/
	public CustomerInfor findCustomerInforById(
			String id) {
		CustomerInfor customerInfor = commonDao.findObjectById(CustomerInfor.class, id);
		return customerInfor;
	}
	
	/* 根据客户是否填写过职业信息*/
	public CustomerCareersInformation findCustomerCareersInformationById(
			String id) {
		CustomerCareersInformation customerCareersInformation = commonDao.findObjectById(CustomerCareersInformation.class, id);
		return customerCareersInformation;
	}
	
	/* 根据客户是否申请过申请主表信息*/
	public CustomerApplicationInfo findCustomerApplicationInfoById(
			String id) {
		CustomerApplicationInfo customerApplicationInfo = commonDao.findObjectById(CustomerApplicationInfo.class, id);
		return customerApplicationInfo;
	}
	
	public CustomerApplicationProcessForm findCustomerApplicationProcessById(String id) {
		String sql ="select t.examine_amount,s.display_name,t.EXAMINE_LV  from customer_application_process t,sys_user s where s.id = t.audit_user and t.application_id='"+id+"'";
		List<CustomerApplicationProcessForm> list = commonDao.queryBySql(CustomerApplicationProcessForm.class,sql, null);
		if(list!=null&&!list.isEmpty()){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	
	public CustomerInfor findCustomerManager(String id) {//客户id
		String sql ="select * from basic_customer_information t where  t.ID='"+id+"'";
		List<CustomerInfor> list = commonDao.queryBySql(CustomerInfor.class,sql, null);
		if(list!=null&&!list.isEmpty()){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	/* 查询申请的某一笔进件申请单中上传的产品的附件*/
	public List<AddressAccessories> findAddressAccessories(
			String applicationId,String productId) {
		List<AddressAccessories> list = commonDao.queryBySql(AddressAccessories.class, "select * from address_accessories t where t.application_id='"+applicationId+"' and t.product_id='"+productId+"'", null);
		return list;
	}
	
	/* 查找接口配置表*/
	public List<ApplicationDataImport> findApplicationDataImport() {
		List<ApplicationDataImport> applicationDataImportList = commonDao.queryBySql(ApplicationDataImport.class, "select * from application_data_import t order by to_number(id) ", null);
		return applicationDataImportList;
	}
	
	public List<CustomerInfor> checkValue(String userId,String valueType){
		return commonDao.queryBySql(CustomerInfor.class," select count(1) from account_manager_parameter a, manager_customer_type c   where  a.level_information = c.level_id  and  a.user_id='"+userId+"' and c.customer_type = '"+valueType+"'", null);
	}
	
	/**
	 * 
	 * @param id
	 * @param dataType application 进件 amountadjustment 调额信息
	 * @return
	 */
	public List<ApproveHistoryForm> findApproveHistoryByDataId(String id, String dataType){
		/*String sql = "select s.status_name, t.examine_result, su.display_name, t.examine_amount, t.start_examine_time " +
				" from wf_status_queue_record t left join wf_status_info s on t.current_status = s.id " +
				" left join wf_process_record pr on t.current_process = pr.id";
		if(dataType.equals("application")){
			sql += " left join customer_application_process aa on pr.id = aa.serial_number" +
					" left join sys_user su on t.examine_user = su.id " +
					" where aa.application_id = #{id}";
		} else if(dataType.equals("amountadjustment")){
			sql += " left join amount_adjustment_process aa on pr.id = aa.serial_number" +
					" left join sys_user su on t.examine_user = su.id " +
					" where aa.amount_adjustment_id = #{id}";
		}
		sql += " order by t.start_examine_time desc";*/
		String sql= " select (case when cai.status ='audit' and tlog.examine_amount is null and sdh.sdtime is null then '已申请' " 
				   +" when cai.id  in (select capid from CUSTOMER_APPLICATION_SDH) and sdh.sdtime is null and sdh.time is not null then '初审通过' "
				   +" when t.examine_result is not null and cai.id not in (select capid from CUSTOMER_APPLICATION_SDH) then s.status_name "
				   +" when cai.id  in (select capid from CUSTOMER_APPLICATION_SDH) and sdh.sdtime is not null then  '审贷决议'  else '' end) as status_name,"
		           +" t.examine_result, su.display_name, "
		           +" (case when cai.id  in (select application_id from T_APP_MANAGER_AUDIT_LOG) and sdh.sdtime is null then tlog.examine_amount"
		           +" when t.examine_result='APPROVE' then t.examine_amount else '' end) as examine_amount, t.start_examine_time " 
				   +" from wf_status_queue_record t left join wf_status_info s on t.current_status = s.id " 
				   +" left join wf_process_record pr on t.current_process = pr.id";
		if(dataType.equals("application")){
			 sql += " left join customer_application_process aa on pr.id = aa.serial_number" +
					" left join sys_user su on t.examine_user = su.id " +
					" left join customer_application_info cai  on cai.id=aa.application_id"+
					" left join T_APP_MANAGER_AUDIT_LOG tlog on tlog.application_id =cai.id"+
					" left join CUSTOMER_APPLICATION_SDH sdh on sdh.capid=cai.id"+
					" where aa.application_id = #{id}";
		} else if(dataType.equals("amountadjustment")){
			 sql += " left join amount_adjustment_process aa on pr.id = aa.serial_number" +
					" left join sys_user su on t.examine_user = su.id " +
					" left join customer_application_info cai  on cai.id=aa.application_id"+
					" left join T_APP_MANAGER_AUDIT_LOG tlog on tlog.application_id =cai.id"+
					" left join CUSTOMER_APPLICATION_SDH sdh on sdh.capid=cai.id"+
					" where aa.amount_adjustment_id = #{id}";
		}
		     sql += " order by t.start_examine_time desc";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		
		return commonDao.queryBySql(ApproveHistoryForm.class, sql, params);
	}
	
	/*public List<AppManagerAuditLogForm> findAuditConfigureById(String id){
		String sql = "select l.examine_amount as examineAmount ,                     "+
				"       l.examine_lv as examineLv,l.AUDIT_TYPE as auditType,         "+
				"       u1.display_name as userName1,               "+
				"       u2.display_name as userName2,               "+
				"       u3.display_name as userName3                "+
				"from                                              "+
				"    t_app_manager_audit_log l                     "+
				"  left join  sys_user u1 on l.user_id_1 = u1.id   "+
				"  left join  sys_user u2 on l.user_id_2 = u2.id   "+
				"  left join  sys_user u3 on l.user_id_3 = u3.id   "+
				" where APPLICATION_ID = #{id} 					   ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return commonDao.queryBySql(AppManagerAuditLogForm.class, sql, params);
	}*/
	
	/**
	 * 查找进件的审批中的节点
	 * @param id
	 * @return
	 */
	public String findAprroveProgress(String id){
		String sql = " select status_name from (select s.status_name from wf_status_queue_record t "+
				" left join wf_status_info s on t.current_status = s.id "+
				" left join wf_process_record pr on t.current_process = pr.id "+
				" left join customer_application_process aa on pr.id = aa.serial_number "+
				" where aa.application_id=#{id} " +
				" order by t.start_examine_time desc) where rownum=1";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		List<HashMap<String, Object>> list = commonDao.queryBySql(sql, params);
		if(list != null && list.size() > 0){
			HashMap<String, Object> map = list.get(0);
			return (String) map.get("STATUS_NAME");
		} else {
			return null;
		}
	}
	
	public String findNodeName(String id){
		String sql = "select d.node_name from customer_application_process p,Node_audit d where p.next_node_id = d.id and p.APPLICATION_ID =#{id}";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		List<HashMap<String, Object>> list = commonDao.queryBySql(sql, params);
		if(list != null && list.size() > 0){
			HashMap<String, Object> map = list.get(0);
			return (String) map.get("NODE_NAME");
		} else {
			return null;
		}
	}
	
	public List<HashMap<String, Object>> findNodeNameJN(String id){
		String sql = "select d.node_name,p.REFUSAL_REASON,p.FALLBACK_REASON from customer_application_process p,Node_audit d where p.next_node_id = d.id and p.APPLICATION_ID =#{id}";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		List<HashMap<String, Object>> list = commonDao.queryBySql(sql, params);
		return list;
		/*if(list != null && list.size() > 0){
			HashMap<String, Object> map = list.get(0);
			return (String) map.get("NODE_NAME");
		} else {
			return null;
		}*/
	}
	
	
	public Float checkApplyQuota(String userId,String productId){
		String sql = "select * from MANAGER_PRODUCTS_CONFIGURATION t where exists (select 1 from ACCOUNT_MANAGER_PARAMETER f where t.customer_manager_level = f.level_information and f.user_id = '"+userId+"' and t.product_id = '"+productId+"')";
		List<ManagerProductsConfiguration> list = commonDao.queryBySql(ManagerProductsConfiguration.class, sql, null);
		if(list!=null&&!list.isEmpty()){
			if(StringUtils.trimToNull(list.get(0).getCreditLine())!=null){
				return Float.valueOf(list.get(0).getCreditLine());
			}else {
				return null;
			}
		}else{
			return null;
		}
	}
	
	
	
	
	public List<IntoPieces> findCustomerApplicationInfo() {
		//String sql = "select * from customer_application_info where status = 'approved'";
		
		/*String sql =     	"        select a.id,a.customer_id as customerId,e.id as productId,d.jjh,d.jkrq "+
			    "           from customer_application_info  a,    "+              
			    "                basic_customer_information b,    "+              
			    "                ty_customer_base           c,    "+             
			    "                ty_repay_tkmx              d,    "+
			    "                product_attribute          e     "+    
			    "           where a.customer_id =b.id             "+             
			    "                 and b.ty_customer_id = c.id     "+              
			    "                 and c.KHNM = d.khh               "+
			    "                 and a.product_id = e.id         "+
			    "                 and e.PRODUCT_TYPE_CODE = d.cpmc     "+                       
			    "                 and a.status ='approved'        "+             
			    "                 and d.sfzf !='1.0' ";*/
		String sql="   select a.id,a.customer_id as customerId,e.id as productId,d.jjh,d.jkrq  "+
				 "        from customer_application_info  a,                  "+
				 "         basic_customer_information b,                  "+
				 "         ty_customer_base           c,                 "+
				 "         ty_repay_tkmx              d,    				"+
				 "         product_attribute          e,         		"+
				 "          ty_product_type            f					"+
				 "    where a.customer_id =b.id                          "+
				 "          and b.ty_customer_id = c.id                  "+
				 "          and c.KHNM = d.khh               			"+
				 "          and a.product_id = e.id         				"+
				 "        and e.product_type_code=f.product_code			"+
				 "			and f.product_code=d.cpmc 					"+
				 "          and a.status ='approved'                 	"+ 
				 "          and d.sfzf !='1.0'";
		
		
		List<IntoPieces> list = commonDao.queryBySql(IntoPieces.class,sql,null);
		return list;
	}
	
	
	public List<IntoPieces> findCustomerApplicationInfoJnFk() {
		/*String sql ="select t.id,                               "+
					"       r.money                             "+
					"  from customer_application_info  t,       "+
					"       basic_customer_information b,       "+
					"       t_gcloancredit r                    "+
					" where t.customer_id = b.TY_CUSTOMER_ID    "+
					"       and r.custid = b.ty_customer_id     "+
					"       and r.CREDITSTATE ='0004'           ";*/
		String sql = " select a.id,                                  "+
					 "              sum(c.money) as amt              "+
					 "         from customer_application_info  a,    "+
					 "              basic_customer_information b,    "+
					 "              t_gccontractmain           c,    "+
					 "              t_gccontractmulticlient    d     "+
					 "        where a.customer_id = b.id             "+
					 "        and trim(c.KEYCODE) = d.KEYCODE        "+
					 "        and b.ty_customer_id = d.CUSTID        "+
					 "        and c.keyeffectedstate ='1'            "+
					 "        and d.keyeffectedstate ='1'            "+
					 "        and a.status = 'approved'              "+
					 "        group by a.id                          ";
		List<IntoPieces> list = commonDao.queryBySql(IntoPieces.class,sql,null);
		return list;
	}
	
	
	public List<IntoPieces> findCustomerApplicationInfoJnHQ() {
	/*	String sql ="select t.id, c.reqlmt                  " + 
					"  from customer_application_info  t,   " +  
					"       basic_customer_information b,   " +  
					"       t_cclmtapplyinfo           c,   " +  
					"       t_gccontractmain m              " + 
					" where c.custid = b.TY_CUSTOMER_ID     " + 
					"   and b.id = t.CUSTOMER_ID            " + 
					"   and t.status = 'approved'           " + 
					"   and c.state = '1'                   " + 
					"   and c.appcode = m.appcode           " + 
					"   and m.squarestate ='1'              "; */
		String sql = "select a.id,                                                "+ 
					 "                        sum(c.money)                        "+ 
					 "                   from customer_application_info  a,       "+ 
					 "                        basic_customer_information b,       "+ 
					 "                        t_gccontractmain           c,       "+ 
					 "                        t_gccontractmulticlient    d        "+ 
					 "                  where a.customer_id = b.id                "+ 
					 "                  and trim(c.KEYCODE) = d.KEYCODE           "+ 
					 "                  and b.ty_customer_id = d.CUSTID           "+ 
					 "                  and a.status = 'end'                      "+ 
					 "                  and c.SQUARESTATE ='1'                    "+ 
					 "                  group by a.id                             ";
		List<IntoPieces> list = commonDao.queryBySql(IntoPieces.class,sql,null);
		return list;
	}
	
	
	public CustomerCreditInfo findCustCreditInfomation(String appId){
		
	   String sql = "		select tkmz.jzll,							  "+
					"		       tkmz.htll,                             "+
					"		       tkmz.ffje,                             "+
					"		       tkmz.jkrq,                             "+
					"		       tkmz.dqrq,                             "+
					"		       tkmz.dkqx,                             "+
					"		       yehz.jkje,                             "+
					"		       yehz.zhhkr,                            "+
					"		       yehz.shbj,                             "+
					"		       yehz.shlx,                             "+
					" case when  tkmz.DQRQ < to_char(sysdate,'yyyyMMdd') and yehz.DKYE!=0 then yehz.DKYE else '0' end as yqbj"+	
					"		    from customer_application_info app,       "+
					"		         basic_customer_information info,     "+
					"		         product_attribute prod,              "+
					"		         ty_customer_base base,               "+
					"		         ty_repay_tkmx tkmz,                  "+
					"		         ty_repay_yehz yehz                   "+
					"		    where app.customer_id = info.id           "+
					"		          and app.product_id  = prod.id       "+
					"		          and info.ty_customer_id = base.id   "+
					"		          and prod.PRODUCT_TYPE_CODE = tkmz.cpmc   "+
					"		          and base.KHNM = tkmz.khh             "+
					"		          and tkmz.jjh = yehz.jjh             "+
	   				"				  and app.id = '"+appId+"'		      ";

		List<CustomerCreditInfo> list = commonDao.queryBySql(CustomerCreditInfo.class,sql,null);
		if(list!=null&&!list.isEmpty()){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	public RiskCustomer findRiskListByCustomerId(String customerId){
		String sql = "select * from risk_list where CUSTOMER_ID ='"+customerId+"'";
		List<RiskCustomer> list = commonDao.queryBySql(RiskCustomer.class,sql,null);
		if(list!=null&&!list.isEmpty()){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	
	/* 查询进件信息count */
	public int findintoPiecesByFilterCount(IntoPiecesFilter filter) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		String id = filter.getId();
		String chineseName = filter.getChineseName();
		String productName = filter.getProductName();
		String userId = filter.getUserId();
		String cardId = filter.getCardId();
		String status = filter.getStatus();
		StringBuffer sql = null;

//			// 获取自己及下属id
//			String userSql = "select * from account_manager_parameter where id in ( select t.child_id from manager_belong_map t left join account_manager_parameter amp on amp.id = t.parent_id where amp.user_id = '"
//					+ userId + "')";
//			List<AccountManagerParameter> userList = commonDao.queryBySql(
//					AccountManagerParameter.class, userSql, null);
//			String users = "('" + userId + "',";
//			for (int i = 0; i < userList.size(); i++) {
//				users += "'" + userList.get(i).getUserId() + "',";
//			}
//			users = users.substring(0, users.length() - 1) + ")";
		sql = new StringBuffer(
				"select * from (select t.id,t.customer_id,b.ty_customer_id,b.chinese_name,t.product_id,p.product_name,b.card_id,t.apply_quota,t.final_approval,t.status,t.CREATED_TIME,t.ACTUAL_QUOTE as reqlmt,usr.display_name, b.user_id,(select org.name from sys_organization org where id in(select t.org_id from sys_department t where t.id in (select tt.dept_id from sys_dept_user tt where tt.user_id = b.user_id)))as organcode from customer_application_info t,basic_customer_information b,product_attribute p,sys_user usr where t.customer_id=b.id  and t.product_id=p.id and usr.id = b.user_id) where 1=1 ");
		if(StringUtils.trimToNull(userId)!=null){
			params.put("userId", userId);
			sql.append("and user_id = #{userId}");
		}
		
		if(StringUtils.trimToNull(filter.getStartAmt())!=null){
			params.put("startAmt", filter.getStartAmt());
			sql.append("and APPLY_QUOTA >= #{startAmt}");
		}
		
		if(StringUtils.trimToNull(filter.getEndAmt())!=null){
			params.put("endAmt", filter.getEndAmt());
			sql.append("and APPLY_QUOTA <= #{endAmt}");
		}
		
		String custManagerIds = filter.getCustManagerIds();
		if(StringUtils.trimToNull(custManagerIds)!=null){
			sql.append("and user_id in ");
		    sql.append(custManagerIds);
		}
		
		if(StringUtils.trimToNull(filter.getCustManagerId())!=null&&!"0".equals(filter.getCustManagerId())){
			params.put("custManagerId", filter.getCustManagerId());
			sql.append("and user_id = #{custManagerId}");
		}
		
		if(StringUtils.trimToNull(filter.getOrganName())!=null){
			params.put("organName", filter.getOrganName());
			sql.append("and organcode = #{organName}");
		}
		
		if(StringUtils.trimToNull(status)!=null){
			params.put("status", status);
			sql.append("and status= #{status}");
		}
		
		if (StringUtils.trimToNull(cardId) != null
				|| StringUtils.trimToNull(chineseName) != null) {
			if (StringUtils.trimToNull(cardId) != null
					&& StringUtils.trimToNull(chineseName) != null) {
				sql.append(" and (card_id like '%" + cardId
						+ "%' or chinese_name like '%" + chineseName + "%' )");
			} else if (StringUtils.trimToNull(cardId) != null
					&& StringUtils.trimToNull(chineseName) == null) {
				params.put("cardId", cardId);
				sql.append(" and card_id like '%'||#{cardId}||'%' ");
			} else if (StringUtils.trimToNull(cardId) == null
					&& StringUtils.trimToNull(chineseName) != null) {
				params.put("chineseName", chineseName);
				sql.append(" and chinese_name like '%'||#{chineseName}||'%' ");
			}
		}

		sql.append(" order by id asc");
		return commonDao.queryBySql(IntoPieces.class, sql.toString(), params)
				.size();
	}



	/**
	 * 查询已经放款的银联进件信息（还款结束）
	 * @return
	 */
		public List<CustomerApplicationInfo> findCustomerApplicationInfoEnd() {			
			String sql ="select a.*   from customer_application_info  a,  ty_repay_tkmx  d   "+
				    "           where a.jjh =d.jjh            "+                            
				    "            and a.status ='end'        "+             
				    "            and d.sfzf ='1.0' ";
			List<CustomerApplicationInfo> list = commonDao.queryBySql(CustomerApplicationInfo.class,sql,null);
			return list;
		}

	
}
