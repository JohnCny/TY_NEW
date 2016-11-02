package com.cardpay.pccredit.product.dao.comdao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.product.constant.CustomerConstant;
import com.cardpay.pccredit.product.constant.ProductOperateEnum;
import com.cardpay.pccredit.product.dao.ProductDao;
import com.cardpay.pccredit.product.model.FilterDict;

/**
 * 
 * 通过产品Id查找筛选规则
 *
 * @author Evans zhang
 *
 * 2014-10-24 下午2:54:08
 */

@Service
public class ProductComDao {
	
	@Autowired
	private ProductDao productDao;
	
	public List<String> findCustomerIdByProductId(String productId){
		//查找规则
		List<FilterDict> list=productDao.findFilterDict(productId);
		Map<String, String> map=new HashMap<String, String>();
		for(int i=0;i<list.size();i++){
			FilterDict filterDict=list.get(i);
			if(filterDict==null){
				continue;
			}
			if(ProductOperateEnum.list.contains(filterDict.getOperate())){
				filterDict.setColumnName("to_number("+filterDict.getColumnName()+")");
			}
			if(!map.containsKey(filterDict.getTableName())){
				if(filterDict.getTableName().equals(CustomerConstant.CUSTOMER_TABLE)){
					map.put(filterDict.getTableName(),"select id from "+filterDict.getTableName()+" where "+filterDict.getColumnName()+filterDict.getOperate()+"'"+filterDict.getValue()+"'");
				}else{
					map.put(filterDict.getTableName(),"select customer_id as id from "+filterDict.getTableName()+" where "+filterDict.getColumnName()+filterDict.getOperate()+"'"+filterDict.getValue()+"'");
				}
			}else{
				String str=map.get(filterDict.getTableName());
				map.put(filterDict.getTableName(), str+" and "+filterDict.getColumnName()+filterDict.getOperate()+"'"+filterDict.getValue()+"'");
			}	
		}
	
		List<String> customerIdSet=new ArrayList<String>();
		int j=0;
		for(Entry<String, String> entry:map.entrySet()){
			List<String> customerList=productDao.findCustomerByDict(entry.getValue());
			if(j==0){
				customerIdSet.addAll(customerList);
			}else{
				customerIdSet.retainAll(customerList);
			}
			j++;
		}
		return customerIdSet;
	}
	
	
}
