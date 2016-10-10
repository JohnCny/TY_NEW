/*
 * Copyright 2013 Wicresoft, Inc. All rights reserved.
 */

package com.wicresoft.jrad.base.velocity.directive;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;
import org.apache.velocity.runtime.parser.node.SimpleNode;

import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.system.model.Dict;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.auth.JRadOperationHelper;
import com.wicresoft.jrad.base.constant.JRadConstants;
import com.wicresoft.jrad.base.enviroment.GlobalSetting;
import com.wicresoft.jrad.base.web.menu.UIMenuMgr;
import com.wicresoft.jrad.base.web.menu.UiMenu;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.modules.dictionary.DictionaryManager;
import com.wicresoft.jrad.modules.dictionary.model.Dictionary;
import com.wicresoft.jrad.modules.dictionary.model.DictionaryItem;
import com.wicresoft.jrad.modules.privilege.business.AccessRightManager;
import com.wicresoft.jrad.modules.privilege.business.impl.AccessRightManagerImpl;
import com.wicresoft.jrad.modules.privilege.model.AccessRight;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;

/**
 * Description of ButtonPrivilegeDirective
 * 
 * @author Vincent
 * @created on Dec 25, 2013
 * 
 * @version $Id: ButtonPrivilegeDirective.java 1704 2014-10-21 01:40:28Z steven $
 */
public class ShowDictionaryDirective extends Directive implements JRadConstants {

	@Override
	public String getName() {
		return "sdpriv";
	}

	@Override
	public int getType() {
		return LINE;
	}
	
	@Override
	public boolean render(InternalContextAdapter context, Writer writer, Node node) throws IOException,
			ResourceNotFoundException, ParseErrorException, MethodInvocationException {
		
		HttpServletRequest request = (HttpServletRequest) context.get(AbstractModelAndView.HTTP_REQUEST);
		LoginManager loginService = Beans.get(LoginManager.class);

		int argCount = node.jjtGetNumChildren();
		if (argCount < 1) {
			writer.write("Invalid arguments #dict(dictType, dictValue, selectFirst, ...)");
		} else {
			// 获得字段名称
			SimpleNode dictTypeNode = (SimpleNode) node.jjtGetChild(0);
			String fieldname = (String) dictTypeNode.value(context);

			//fieldValue
			SimpleNode fieldValueNode = (SimpleNode) node.jjtGetChild(1);
			String fieldValue = String.valueOf(fieldValueNode.value(context));

			String fieldValueTitle = fieldValue;
			//-国籍
			if(fieldname.equalsIgnoreCase("nationality")){
				// 获取字典服务类
				DictionaryManager dictMgr = Beans.get(DictionaryManager.class);
				// 根据指定名得到字典值列表
				Dictionary dictionary = dictMgr.getDictionaryByName("CMMSCNTC");
				List<DictionaryItem> dictItems = dictionary.getItems();
				for (DictionaryItem dictItem : dictItems) {
					if (dictItem.getName().equals(fieldValue)) {
						fieldValueTitle = dictItem.getTitle();
						break;
					}
				}
			}
			//-证件类型
			if(fieldname.equalsIgnoreCase("card_type")){
				// 获取字典服务类
				DictionaryManager dictMgr = Beans.get(DictionaryManager.class);
				// 根据指定名得到字典值列表
				Dictionary dictionary = dictMgr.getDictionaryByName("CARDTYPE");
				List<DictionaryItem> dictItems = dictionary.getItems();
				for (DictionaryItem dictItem : dictItems) {
					if (dictItem.getName().equals(fieldValue)) {
						fieldValueTitle = dictItem.getTitle();
						break;
					}
				}
			}
			//-住宅类型
			if(fieldname.equalsIgnoreCase("residential_propertie")){
				// 获取字典服务类
				DictionaryManager dictMgr = Beans.get(DictionaryManager.class);
				// 根据指定名得到字典值列表
				Dictionary dictionary = dictMgr.getDictionaryByName("CMMSCHOM");
				List<DictionaryItem> dictItems = dictionary.getItems();
				for (DictionaryItem dictItem : dictItems) {
					if (dictItem.getName().equals(fieldValue)) {
						fieldValueTitle = dictItem.getTitle();
						break;
					}
				}
			}
			//性别
			if(fieldname.equalsIgnoreCase("sex")){
				
				if(fieldValue.equalsIgnoreCase("Male"))
					{fieldValueTitle="男";}
				if(fieldValue.equalsIgnoreCase("Female"))
				    {fieldValueTitle="女";}
			}
			//-婚姻状况
			if(fieldname.equalsIgnoreCase("marital_status")){
				// 获取字典服务类
				DictionaryManager dictMgr = Beans.get(DictionaryManager.class);
				// 根据指定名得到字典值列表
				Dictionary dictionary = dictMgr.getDictionaryByName("CMMSCMRC");
				List<DictionaryItem> dictItems = dictionary.getItems();
				for (DictionaryItem dictItem : dictItems) {
					if (dictItem.getName().equals(fieldValue)) {
						fieldValueTitle = dictItem.getTitle();
						break;
					}
				}
			}
			//-单位性质分值
			if(fieldname.equalsIgnoreCase("unit_nature")){
				// 获取字典服务类
				DictionaryManager dictMgr = Beans.get(DictionaryManager.class);
				// 根据指定名得到字典值列表
				Dictionary dictionary = dictMgr.getDictionaryByName("CMMSCCOM");
				List<DictionaryItem> dictItems = dictionary.getItems();
				for (DictionaryItem dictItem : dictItems) {
					if (dictItem.getName().equals(fieldValue)) {
						fieldValueTitle = dictItem.getTitle();
						break;
					}
				}
			}
			//行业类别（陌拜信息）
			if(fieldname.equalsIgnoreCase("engaged_industry")){
				// 获取字典服务类
				DictionaryManager dictMgr = Beans.get(DictionaryManager.class);
				// 根据指定名得到字典值列表
				Dictionary dictionary = dictMgr.getDictionaryByName("HYLB_");
				List<DictionaryItem> dictItems = dictionary.getItems();
				for (DictionaryItem dictItem : dictItems) {
					if (dictItem.getName().equals(fieldValue)) {
						fieldValueTitle = dictItem.getTitle();
						break;
					}
				}
			}
			//行业类别（职业信息）
			if(fieldname.equalsIgnoreCase("industry_type")){
				// 获取字典服务类
				DictionaryManager dictMgr = Beans.get(DictionaryManager.class);
				// 根据指定名得到字典值列表
				Dictionary dictionary = dictMgr.getDictionaryByName("HYLB_");
				List<DictionaryItem> dictItems = dictionary.getItems();
				for (DictionaryItem dictItem : dictItems) {
					if (dictItem.getName().equals(fieldValue)) {
						fieldValueTitle = dictItem.getTitle();
						break;
					}
				}
			}
			//教育程度
			if(fieldname.equalsIgnoreCase("degree_education")){
				// 获取字典服务类
				DictionaryManager dictMgr = Beans.get(DictionaryManager.class);
				// 根据指定名得到字典值列表
				Dictionary dictionary = dictMgr.getDictionaryByName("DEGREEEDUCATION");
				List<DictionaryItem> dictItems = dictionary.getItems();
				for (DictionaryItem dictItem : dictItems) {
					if (dictItem.getName().equals(fieldValue)) {
						fieldValueTitle = dictItem.getTitle();
						break;
					}
				}
			}
			//是否有信贷需求
			if(fieldname.equalsIgnoreCase("demand_credit")){
				
				if(fieldValue.equalsIgnoreCase("1"))
					{fieldValueTitle="是";}
				if(fieldValue.equalsIgnoreCase("0"))
				    {fieldValueTitle="否";}
			}
			//是否建议再营销
			if(fieldname.equalsIgnoreCase("suggested_marketing_again")){
				
				if(fieldValue.equalsIgnoreCase("1"))
				{fieldValueTitle="是";}
			if(fieldValue.equalsIgnoreCase("0"))
			    {fieldValueTitle="否";}
			}
			//是否潜在客户
			if(fieldname.equalsIgnoreCase("potential_customer")){
				
				if(fieldValue.equalsIgnoreCase("1"))
				{fieldValueTitle="是";}
			if(fieldValue.equalsIgnoreCase("0"))
			    {fieldValueTitle="否";}
			}
		
			//职务
			if(fieldname.equalsIgnoreCase("positio")){
				CustomerInforService customerInforService =Beans.get(CustomerInforService.class);
				List<Dict> positios = customerInforService.findPositio();
				for (Dict dictItem : positios) {
					if (dictItem.getTypeCode().equals(fieldValue)) {
						fieldValueTitle = dictItem.getTypeName();
						break;
					}
				}
			}
			
			//职称
			if(fieldname.equalsIgnoreCase("title")){
				CustomerInforService customerInforService =Beans.get(CustomerInforService.class);
				List<Dict> titles = customerInforService.findTitle();
				for (Dict dictItem : titles) {
					if (dictItem.getTypeCode().equals(fieldValue)) {
						fieldValueTitle = dictItem.getTypeName();
						break;
					}
				}
			}
			//信用(还款）方式
			if(fieldname.equalsIgnoreCase("numbererest_repayment_way")){
				// 获取字典服务类
				DictionaryManager dictMgr = Beans.get(DictionaryManager.class);
				// 根据指定名得到字典值列表
				Dictionary dictionary = dictMgr.getDictionaryByName("CMMHKFS");
				List<DictionaryItem> dictItems = dictionary.getItems();
				for (DictionaryItem dictItem : dictItems) {
					if (dictItem.getName().equals(fieldValue)) {
						fieldValueTitle = dictItem.getTitle();
						break;
					}
				}
			}
			if (StringUtils.isNotEmpty(fieldValueTitle)) {
				writer.write(fieldValueTitle);
			}
		}

		return true;
	}
}
