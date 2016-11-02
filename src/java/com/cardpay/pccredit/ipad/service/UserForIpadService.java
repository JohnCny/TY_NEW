/**
 * 
 */
package com.cardpay.pccredit.ipad.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.ipad.model.UserIpad;
import com.wicresoft.jrad.modules.dao.modulesComdao;
import com.wicresoft.jrad.modules.privilege.model.User;

/**
 * @author shaoming
 *
 * 2014年11月28日   下午2:11:47
 */
@Service
public class UserForIpadService {
	
	@Autowired
	private modulesComdao modulesCommondao;
	
	/*登陆*/
	public UserIpad login(String login,String passwd){
		User user = modulesCommondao.find(login, passwd);
		UserIpad ipad = null ;
		if(user!=null){
			ipad = new UserIpad();
			ipad.setId(user.getId());
			ipad.setDisplayName(user.getDisplayName());
		}
		return ipad;
	}
}
