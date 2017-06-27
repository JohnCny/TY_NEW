package com.cardpay.pccredit.jnpad.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.customer.filter.CustomerInforFilter;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.ipad.util.JsonDateValueProcessor;
import com.cardpay.pccredit.jnpad.model.JBUser;
import com.cardpay.pccredit.jnpad.model.MonthlyStatisticsModel;
import com.cardpay.pccredit.jnpad.service.JnIpadJBUserService;
import com.cardpay.pccredit.jnpad.service.MonthlyStatisticsService;
import com.cardpay.pccredit.manager.service.ManagerBelongMapService;
import com.cardpay.pccredit.manager.web.ManagerBelongMapForm;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.database.model.QueryResult;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
public class MonthlyStatisticsController {
	@Autowired
	private MonthlyStatisticsService StatisticsService;
	@Autowired
	private ManagerBelongMapService MapService;
	@Autowired
	private JnIpadJBUserService JnIpadJBUser;
	/**
	 * 绘画区域月季贷款统计图
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/addIntoPieces/userTj1.json", method = { RequestMethod.GET })
	public String browseCustomer1(HttpServletRequest request) {
		  Map<String,Object> map = new LinkedHashMap<String,Object>();
      String userId=request.getParameter("userId");
      String yeardate=request.getParameter("year");
      Integer formatDate=0;
    
			//如果是区域经理，查询该区域下面的几个组
			List<JBUser> depart=JnIpadJBUser.selectDepartUser(userId);
			 //查询当前客户经理哪几个年份有贷款信息
		      List<MonthlyStatisticsModel> year=StatisticsService.selectYear1(depart.get(0).getQname());
			//查询该区域的月季贷款情况
			  MonthlyStatisticsModel resultModel=null;
		      MonthlyStatisticsModel Model=new MonthlyStatisticsModel();
		      //查询团队当年的月季贷款信息
		      if(yeardate.equals("0")){
		    	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		          Date date = new Date();
		          formatDate = Integer.parseInt(sdf.format(date));  
		      }else{
		    	  formatDate=Integer.parseInt(yeardate);
		      }
		      Model.setCustomeryeah(formatDate);
		      Model.setOrgteam(depart.get(0).getQname());
		      resultModel=StatisticsService.selectTeamYear(Model);
		      resultModel.setCustomeryeah(Model.getCustomeryeah());
		      //计算团队年份贷款总额
		      MonthlyStatisticsModel Model1=new MonthlyStatisticsModel();
		      Model1.setOrgteam(depart.get(0).getQname());
		      MonthlyStatisticsModel result= StatisticsService.selectTeamYear(Model1);
		      double money=result.getTotalAmount();
		      map.put("result", resultModel);
		      map.put("depart", depart);
		      map.put("departsize", depart.size());
		      map.put("year", year);
		      map.put("yearsize", year.size());
		      map.put("money", money);
		    
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
	
	/**
	 * 绘画当前客户经理的月季贷款统计图
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/addIntoPieces/userTj.json", method = { RequestMethod.GET })
	public String browseCustomer(HttpServletRequest request) {
      String userId=request.getParameter("userId");
      String yeardate=request.getParameter("year");
      Integer formatDate=0;
      List<MonthlyStatisticsModel> resultModel=null;
      //查询当前客户经理哪几个年份有贷款信息
      List<MonthlyStatisticsModel> year=StatisticsService.selectYear(userId);
      //查询当前客户经理当年的月季贷款信息
      if(yeardate.equals("0")){
    	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
          Date date = new Date();
          formatDate = Integer.parseInt(sdf.format(date));  
      }else{
    	  formatDate=Integer.parseInt(yeardate);
      }
      List<MonthlyStatisticsModel> result=StatisticsService.selectUser(userId, formatDate);
      //查询当前客户经理的用信额度
      double money=StatisticsService.selecTotalAmount(userId);
      //查询当前客户经理是否为小组长以及手下的客户经理ID
      resultModel=StatisticsService.findxzz(userId);
      Map<String,Object> map = new LinkedHashMap<String,Object>();
      map.put("year", year);
      map.put("yearsize", year.size());
      map.put("result", result);
      map.put("money", money);
      map.put("resultModel", resultModel);
      if(resultModel!=null){
    	  map.put("size", resultModel.size());
      }
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
	/**
	 * 绘画团队月季贷款统计图
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/addIntoPieces/selectTeamYear.json", method = { RequestMethod.GET })
	public String selectTeamYear(HttpServletRequest request) {
      String team=request.getParameter("team");
      String yeardate=request.getParameter("year");
      Integer formatDate=0;
     MonthlyStatisticsModel resultModel=null;
      MonthlyStatisticsModel Model=new MonthlyStatisticsModel();
      //查询团队当年的月季贷款信息
      if(yeardate.equals("0")){
    	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
          Date date = new Date();
          formatDate = Integer.parseInt(sdf.format(date));  
      }else{
    	  formatDate=Integer.parseInt(yeardate);
      }
      Model.setCustomeryeah(formatDate);
      Model.setTeam(team);
      resultModel=StatisticsService.selectTeamYear(Model);
      //计算团队年份贷款总额
      MonthlyStatisticsModel Model1=new MonthlyStatisticsModel();
      Model1.setTeam(team);
      MonthlyStatisticsModel result= StatisticsService.selectTeamYear(Model1);
      double money=result.getTotalAmount();
      Map<String,Object> map = new LinkedHashMap<String,Object>();
      map.put("resultModel", resultModel);
      map.put("money", money);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
	
	
	
	/**
	 * 绘画所有月季贷款统计图
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/addIntoPieces/selectAllTeamxq.json", method = { RequestMethod.GET })
	public String selectAllTeamxq(HttpServletRequest request) {
      String yeardate=request.getParameter("yaar");
      Integer formatDate=0;
     MonthlyStatisticsModel resultModel=null;
      MonthlyStatisticsModel Model=new MonthlyStatisticsModel();
      //查询全部的月季贷款信息
      if(yeardate.equals("0")){
    	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
          Date date = new Date();
          formatDate = Integer.parseInt(sdf.format(date));  
      }else{
    	  formatDate=Integer.parseInt(yeardate);
      }
      Model.setCustomeryeah(formatDate);
      resultModel=StatisticsService.selectTeamYear(Model);
      //查询贷款总额
      MonthlyStatisticsModel StatisticsModel =new MonthlyStatisticsModel();
      MonthlyStatisticsModel result=StatisticsService.selectTeamYear(StatisticsModel);
      double money=result.getTotalAmount();
      List<MonthlyStatisticsModel> team=StatisticsService.selectAllteam1();
      for(int a=team.size()-1;a>=0;a--){
    	  if(team.get(a).getOrgteam().equals("后台")){
    		  team.remove(a);
    	  }else if(team.get(a).getOrgteam().equals("其他")){
    		  team.remove(a);
    	  }else if(team.get(a).getOrgteam().equals("太原市城区农村信用合作联社")){
    		  team.remove(a);
    	  }
      }
      List<MonthlyStatisticsModel> team1=StatisticsService.selectAllteam2();
      for(int a=team1.size()-1;a>=0;a--){
    	  if(team1.get(a).getTeam().equals("管理团队")){
    		  team1.remove(a);
    	  }else if(team1.get(a).getTeam().equals("后台")){
    		  team1.remove(a);
    	  }else if(team1.get(a).getTeam().equals("其他部门")){
    		  team1.remove(a);
    	  }
      }
      List<MonthlyStatisticsModel> year=StatisticsService.selectAllYear();
      Map<String,Object> map = new LinkedHashMap<String,Object>();
      map.put("result", resultModel);
      map.put("money", money);
      map.put("team", team);
      map.put("teamsize", team.size());
      map.put("team1", team1);
      map.put("team1size", team1.size());
      map.put("year", year);
      map.put("yearsize", year.size());
      map.put("formatDate", formatDate);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/ipad/addIntoPieces/selectTeamorOrgTeam.json", method = { RequestMethod.GET })
	public String selectTeamorOrgTeam(HttpServletRequest request) {
      String team=request.getParameter("team");
      String yeardate=request.getParameter("customeryeah");
      String orgteam=request.getParameter("orgteam");
      MonthlyStatisticsModel resultModel=null;
      MonthlyStatisticsModel Model=new MonthlyStatisticsModel();
      Model.setCustomeryeah(Integer.parseInt(yeardate));
      if(!team.equals("0")){
    	  Model.setTeam(team);
      }
      if(!orgteam.equals("0")){
    	  Model.setOrgteam(orgteam);
      }
      resultModel=StatisticsService.selectTeamYear(Model);
      //计算团队年份贷款总额
      MonthlyStatisticsModel Model1=new MonthlyStatisticsModel();
      if(!team.equals("0")){
    	  Model1.setTeam(team);
      }
      if(!orgteam.equals("0")){
    	  Model1.setOrgteam(orgteam);
      }
      MonthlyStatisticsModel result= StatisticsService.selectTeamYear(Model1);
      double money=result.getTotalAmount();
      Map<String,Object> map = new LinkedHashMap<String,Object>();
      map.put("result", resultModel);
      map.put("money", money);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
}
