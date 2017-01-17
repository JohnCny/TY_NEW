package com.cardpay.pccredit.zrrtz.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.intopieces.constant.Constant;
import com.cardpay.pccredit.intopieces.filter.IntoPiecesFilter;
import com.cardpay.pccredit.intopieces.model.IntoPieces;
import com.cardpay.pccredit.system.model.SystemUser;
import com.cardpay.pccredit.zrrtz.dao.ZrrtzDao;
import com.cardpay.pccredit.zrrtz.model.ZrrtzFilter;
import com.cardpay.pccredit.zrrtz.model.IncomingData;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.database.model.QueryResult;
@Service
public class ZrrtzcService {
	@Autowired
	private ZrrtzDao dao;
	public QueryResult<IncomingData> findintoPiecesByFilter(ZrrtzFilter filter, IUser user) {
		        String name=user.getLogin();
				List<IncomingData> plans = dao.findIntoPiecesList(filter);
				int size = dao.findIntoPiecesCountList(filter);
				QueryResult<IncomingData> queryResult = new QueryResult<IncomingData>(size,plans);
				return queryResult;
	}
	public List<IncomingData> finddate(String id) {
		// TODO Auto-generated method stub
		return dao.finddate(id);
	}
	
	

}
