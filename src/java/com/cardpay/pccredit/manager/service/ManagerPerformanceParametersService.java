package com.cardpay.pccredit.manager.service;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uorm.pojo.generator.GenUtil;
import org.xhtmlrenderer.util.GeneralUtil;

import com.cardpay.pccredit.manager.dao.comdao.ManagerMonthAssessmentComdao;
import com.cardpay.pccredit.manager.dao.comdao.ManagerPerformanceParamersComdao;
import com.cardpay.pccredit.manager.model.MangerMonthAssessment;
import com.cardpay.pccredit.manager.model.TPerformanceParameters;
import com.cardpay.pccredit.manager.model.TyPerformanceCenter;
import com.cardpay.pccredit.manager.model.TyPerformanceParameters;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.id.IDGenerator;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.util.spring.Beans;
/**
 * 
 * @author 季东晓
 *
 * 2014-11-17 下午4:38:43
 */
@Service
public class ManagerPerformanceParametersService {
	
	
	@Autowired
	private CommonDao commonDao;
	
	
	@Autowired
	private ManagerPerformanceParamersComdao managerPerformanceParamersComdao;
	
	/**
	 * 客户经理绩效参数配置
	 * @return
	 */
	public List<TyPerformanceParameters> getManagerPerformanceParamers(){
	
		return managerPerformanceParamersComdao.getManagerPerformanceParamers();
		
	}
	
	/**
	 * 客户经理绩效参数配置
	 * @param request
	 */
	public void updateManagerPerformanceParamers(HttpServletRequest request) {
		
		String[] levelCodes= request.getParameterValues("levelCode");
		String[] managerLevels= request.getParameterValues("managerLevel");
		String[] basicPerformances= request.getParameterValues("basicPerformance");
		String[] As= request.getParameterValues("A");
		String[] Bs= request.getParameterValues("B");
		String[] Ds= request.getParameterValues("D");
		String[] Es= request.getParameterValues("E");
		String[] Fs= request.getParameterValues("F");
		String[] objectCounts= request.getParameterValues("objectCount");
		//删除历史记录
		managerPerformanceParamersComdao.deleteList();
		for(int i=0;i<levelCodes.length;i++){
		    TyPerformanceParameters parameters = new TyPerformanceParameters();
			String levelCode= levelCodes[i];
			String managerLevel=managerLevels[i];
			String basicPerformance=basicPerformances[i];
			String a=As[i];
			String b=Bs[i];
			String d=Ds[i];
			String e=Es[i];
			String f=Fs[i];
			String objectCount=objectCounts[i];
			parameters.setId(IDGenerator.generateID());
			parameters.setLevelCode(levelCode);
			parameters.setManagerLevel(managerLevel);
			parameters.setBasicPerformance(basicPerformance);
			parameters.setA(a);
			parameters.setB(b);
			parameters.setD(d);
			parameters.setE(e);
			parameters.setF(f);
			parameters.setObjectCount(objectCount);
			commonDao.insertObject(parameters);		
					
			}
		
	}
	/**
	 * 获取特定客户经理绩效参数
	 * @return
	 */
	public TyPerformanceParameters getParameterByLevel(String customerId){
		String sql = "select * from ty_performance_parameters where level_code in (select level_information from account_manager_parameter  where user_id='"+customerId+"')";
		List<TyPerformanceParameters>tyPerformanceParameters = commonDao.queryBySql(TyPerformanceParameters.class, sql, null);
		if(tyPerformanceParameters.size()>0){
			return tyPerformanceParameters.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 中心人员绩效参数配置
	 * @return
	 */
	public List<TyPerformanceCenter> getManagerPerformanceCenter(){
		return managerPerformanceParamersComdao.getManagerPerformanceCenter();
	}
	
	//=====================================济南绩效=======================================//
	public List<TPerformanceParameters> getTManagerPerformanceParamers(){
		return managerPerformanceParamersComdao.getTManagerPerformanceParamers();
	}
	
	
	/**
	 * 客户经理绩效参数配置
	 * @param request
	 */
	public void updateTManagerPerformanceParamers(HttpServletRequest request) {
		//获取参数
		String[] ids= request.getParameterValues("id");
		String[] As= request.getParameterValues("A");
		String[] Bs= request.getParameterValues("B");
		String[] Ds= request.getParameterValues("D");
		String[] Es= request.getParameterValues("E");
		String[] Fs= request.getParameterValues("F");
		String[] Gs= request.getParameterValues("G");
		String[] Hs= request.getParameterValues("H");
		String[] Is= request.getParameterValues("I");
		String[] Js= request.getParameterValues("J");
		String[] Ks= request.getParameterValues("K");
		String[] Ls= request.getParameterValues("L");
		
		//删除历史记录
		managerPerformanceParamersComdao.deleteTList();
		
		for(int i=0;i<ids.length;i++){
		    TPerformanceParameters parameters = new TPerformanceParameters();
			parameters.setA(As[i]);
			parameters.setB(Bs[i]);
			parameters.setD(Ds[i]);
			parameters.setE(Es[i]);
			parameters.setF(Fs[i]);
			parameters.setG(Gs[i]);
			parameters.setH(Hs[i]);
			parameters.setI(Is[i]);
			parameters.setJ(Js[i]);
			parameters.setK(Ks[i]);
			parameters.setL(Ls[i]);
			commonDao.insertObject(parameters);		
		}
	}
}
