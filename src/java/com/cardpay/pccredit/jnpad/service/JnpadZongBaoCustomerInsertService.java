package com.cardpay.pccredit.jnpad.service;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.intopieces.constant.Constant;
import com.cardpay.pccredit.intopieces.dao.comdao.IntoPiecesComdao;
import com.cardpay.pccredit.intopieces.filter.IntoPiecesFilter;
import com.cardpay.pccredit.intopieces.model.IntoPieces;
import com.cardpay.pccredit.jnpad.dao.JnpadZongBaoCustomerInsertDao;
import com.cardpay.pccredit.jnpad.model.CustomerInfo;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.model.QueryResult;

@Service
public class JnpadZongBaoCustomerInsertService {

	@Autowired
	JnpadZongBaoCustomerInsertDao jnpadZongBaoCustomerInsertDao;
	
	@Autowired
	private IntoPiecesComdao intoPiecesComdao;
	
	@Autowired
	private CommonDao commonDao;
	
	public List<CustomerInfo> selectCustomerInfo(String userId) {
		// TODO Auto-generated method stub
		return jnpadZongBaoCustomerInsertDao.selectCustomerInfo(userId);
	}

	public void updateCustomerInfo(String customerId, String userId) {
		// TODO Auto-generated method stub
		jnpadZongBaoCustomerInsertDao.updateCustomerInfo(customerId,userId);
	}

	public QueryResult<IntoPieces> findCustomerInfor(IntoPiecesFilter filter) {

		int sum = intoPiecesComdao.findintoPiecesByFilterCount(filter);
		QueryResult<IntoPieces> queryResult = this.findintoPiecesInfoByFilter(filter,sum);
		QueryResult<IntoPieces> qs = new QueryResult<IntoPieces>(sum, queryResult.getItems());
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
		}

	
	
	
	public QueryResult<IntoPieces> findintoPiecesInfoByFilter(
			IntoPiecesFilter filter,int sum) {
		HashMap<String, Object> params = new HashMap<String, Object>();
//		String id = filter.getId();
//		String chineseName  = filter.getChineseName();
//		String productName = filter.getProductName();
		String userId = filter.getUserId();
//		String cardId = filter.getCardId();
//		String status = filter.getStatus();
		params.put("userId", userId);
		StringBuffer sql = new StringBuffer("select t.id,t.customer_id,b.ty_customer_id,b.chinese_name,t.product_id,p.product_name,b.card_id,t.apply_quota,t.final_approval,t.status,t.CREATED_TIME,t.ACTUAL_QUOTE as reqlmt  from basic_customer_information b LEFT JOIN customer_application_info t ON t.customer_id=b.id LEFT JOIN product_attribute p ON t.product_id=p.id where 1=1 ");
		if(StringUtils.trimToNull(userId)!=null){
			sql.append("and b.user_id <> #{userId} and b.created_by =#{userId}");
		}else{
			sql.append("and b.user_id <> b.created_by ");
		}
		


		sql.append(" order by t.id asc");
		return commonDao.queryBySqlInPagination(IntoPieces.class, sql.toString(), params,
				filter.getStart(),sum);
	}
}
