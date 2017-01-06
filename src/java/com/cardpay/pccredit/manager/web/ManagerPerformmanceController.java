package com.cardpay.pccredit.manager.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.jrad.modules.privilege.model.User;
import com.cardpay.pccredit.ipad.util.JsonDateValueProcessor;
import com.cardpay.pccredit.manager.form.BankListForm;
import com.cardpay.pccredit.manager.form.DeptMemberForm;
import com.cardpay.pccredit.manager.form.ManagerPerformmanceForm;
import com.cardpay.pccredit.manager.model.ManagerPerformmance;
import com.cardpay.pccredit.manager.service.ManagerPerformmanceService;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.constant.JRadConstants;
import com.wicresoft.jrad.base.database.id.IDGenerator;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@RequestMapping("/manager/performmance/*")
@JRadModule("manager.performmance")
public class ManagerPerformmanceController extends BaseController {

	@Autowired
	private ManagerPerformmanceService managerPerformmanceService;

	/**
	 * 业绩录入页面
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insert.page")
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView create(HttpServletRequest request) {        
		JRadModelAndView mv = new JRadModelAndView("/manager/performmance/performmanceInsert", request);
		User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
		
		//统计每天申请拒绝数
		int refuseNum=managerPerformmanceService.queryRefuse(user.getId());
		//统计每天申请数
		int applyNum= managerPerformmanceService.queryApply(user.getId());
		//统计每天内审数
		//int auditNum=managerPerformmanceService.queryAudit(user.getId());
		//统计每天上会数
		//int willNum=managerPerformmanceService.queryWill(user.getId());
		//统计每天通过数
		int passNum=managerPerformmanceService.queryPass(user.getId());
		Map<String, Integer> map=new HashMap<String, Integer>();
		map.put("applyNum", applyNum);
		map.put("refuseNum", refuseNum);
		//map.put("auditNum", auditNum);
		//map.put("willNum", willNum);
		map.put("passNum", passNum);
		mv.addObject("ssss", map);
		return mv;
	}
	/**
	 * 客户经理业绩进度查询
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "browse.page")
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browse(HttpServletRequest request) { 
		List<BankListForm> bankListForm = managerPerformmanceService.findALlbank();
		List<ManagerPerformmanceForm> gxperformList = new ArrayList<ManagerPerformmanceForm>();
		String satrtDate = request.getParameter("startdate");
		String endDate = request.getParameter("enddate");
		if(satrtDate!=null&&satrtDate!=""){
			satrtDate+=" 00:00:00";
		}
		if(endDate!=null&&endDate!=""){
			endDate+=" 23:59:59";
		}
		for(int j=0;j<bankListForm.size();j++){
			String id= bankListForm.get(j).getId();
			List<DeptMemberForm> gxMumberList = managerPerformmanceService.findMumberByDeptId(id);
			for(int i=0;i<gxMumberList.size();i++){
				String managerId =gxMumberList.get(i).getId();
				ManagerPerformmanceForm managerPerformmanceForm= managerPerformmanceService.findSumPerformmanceById(managerId,satrtDate,endDate);
				if(managerPerformmanceForm==null){
					continue;
				}
				ManagerPerformmance managerPerformmanceold = managerPerformmanceService.finManagerPerformmanceById(managerId);
				if(managerPerformmanceold!=null){
				managerPerformmanceForm.setApplycount(managerPerformmanceold.getApplycount());
				managerPerformmanceForm.setApplyrefuse(managerPerformmanceold.getApplyrefuse());
				managerPerformmanceForm.setCreditcount(managerPerformmanceold.getCreditcount());
				managerPerformmanceForm.setCreditrefuse(managerPerformmanceold.getCreditrefuse());
				managerPerformmanceForm.setGivemoneycount(managerPerformmanceold.getGivemoneycount());
				managerPerformmanceForm.setInternalcount(managerPerformmanceold.getInternalcount());
				managerPerformmanceForm.setMeetingcout(managerPerformmanceold.getMeetingcout());
				managerPerformmanceForm.setPasscount(managerPerformmanceold.getPasscount());
				managerPerformmanceForm.setRealycount(managerPerformmanceold.getRealycount());
				managerPerformmanceForm.setReportcount(managerPerformmanceold.getReportcount());
				managerPerformmanceForm.setSigncount(managerPerformmanceold.getSigncount());
				managerPerformmanceForm.setVisitcount(managerPerformmanceold.getVisitcount());
				}
				managerPerformmanceForm.setName(gxMumberList.get(i).getOname());
				managerPerformmanceForm.setManagerName(gxMumberList.get(i).getDisplay_name());
				gxperformList.add(managerPerformmanceForm);
			}
			ManagerPerformmanceForm managerPerformmanceForm1 = managerPerformmanceService.findDeptSumPerformmanceById(id,satrtDate,endDate);
			if(managerPerformmanceForm1==null){
				continue;
			}
			ManagerPerformmance managerPerformmancezhi = managerPerformmanceService.findDeptTodayPerformmanceById(id);
			if(managerPerformmancezhi!=null){
				managerPerformmanceForm1.setApplycount(managerPerformmancezhi.getApplycount());
				managerPerformmanceForm1.setApplyrefuse(managerPerformmancezhi.getApplyrefuse());
				managerPerformmanceForm1.setCreditcount(managerPerformmancezhi.getCreditcount());
				managerPerformmanceForm1.setCreditrefuse(managerPerformmancezhi.getCreditrefuse());
				managerPerformmanceForm1.setGivemoneycount(managerPerformmancezhi.getGivemoneycount());
				managerPerformmanceForm1.setInternalcount(managerPerformmancezhi.getInternalcount());
				managerPerformmanceForm1.setMeetingcout(managerPerformmancezhi.getMeetingcout());
				managerPerformmanceForm1.setPasscount(managerPerformmancezhi.getPasscount());
				managerPerformmanceForm1.setRealycount(managerPerformmancezhi.getRealycount());
				managerPerformmanceForm1.setReportcount(managerPerformmancezhi.getReportcount());
				managerPerformmanceForm1.setSigncount(managerPerformmancezhi.getSigncount());
				managerPerformmanceForm1.setVisitcount(managerPerformmancezhi.getVisitcount());
			}
			managerPerformmanceForm1.setName(bankListForm.get(j).getName());
			managerPerformmanceForm1.setManagerName("汇总");
			gxperformList.add(managerPerformmanceForm1);
		}
		ManagerPerformmanceForm managerPerformmanceForm2 = managerPerformmanceService.findALLDeptSumPerformmanceById(satrtDate,endDate);
		if(managerPerformmanceForm2!=null){
			ManagerPerformmance managerPerformmancezong = managerPerformmanceService.findDeptTodaySumPerformmanceById();	
			if(managerPerformmancezong!=null){
				managerPerformmanceForm2.setApplycount(managerPerformmancezong.getApplycount());
				managerPerformmanceForm2.setApplyrefuse(managerPerformmancezong.getApplyrefuse());
				managerPerformmanceForm2.setCreditcount(managerPerformmancezong.getCreditcount());
				managerPerformmanceForm2.setCreditrefuse(managerPerformmancezong.getCreditrefuse());
				managerPerformmanceForm2.setGivemoneycount(managerPerformmancezong.getGivemoneycount());
				managerPerformmanceForm2.setInternalcount(managerPerformmancezong.getInternalcount());
				managerPerformmanceForm2.setMeetingcout(managerPerformmancezong.getMeetingcout());
				managerPerformmanceForm2.setPasscount(managerPerformmancezong.getPasscount());
				managerPerformmanceForm2.setRealycount(managerPerformmancezong.getRealycount());
				managerPerformmanceForm2.setReportcount(managerPerformmancezong.getReportcount());
				managerPerformmanceForm2.setSigncount(managerPerformmancezong.getSigncount());
				managerPerformmanceForm2.setVisitcount(managerPerformmancezong.getVisitcount());
			}
			managerPerformmanceForm2.setName("统计");
			managerPerformmanceForm2.setManagerName("总计");
			gxperformList.add(managerPerformmanceForm2);
		}
		JRadModelAndView mv = new JRadModelAndView("/manager/performmance/performmance_sum", request);
		mv.addObject("gxperformList", gxperformList);
		mv.addObject("satrtDate", satrtDate);
		mv.addObject("endDate", endDate);
		return mv;
	}

	/**
	 * 执行录入
	 * @param managerPerformmance
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "update.json")
	public JRadReturnMap update(@ModelAttribute ManagerPerformmance managerPerformmance,HttpServletRequest request) {        
		JRadReturnMap returnMap = new JRadReturnMap();
		try {

			User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
			managerPerformmance.setManager_id(user.getId());
			ManagerPerformmance managerPerformmanceold = managerPerformmanceService.finManagerPerformmanceById(user.getId());
			if(managerPerformmanceold!=null){
//			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//			String date1=format.format(managerPerformmanceold.getCrateday());
//			String date2=format.format(new Date());
//			if(date1.equals(date2)){
				returnMap.put(JRadConstants.SUCCESS, false);
				returnMap.put("mess", "当天已经提交过，不能重复提交");
				return returnMap;
//			}
			}
			String id = IDGenerator.generateID();
			managerPerformmance.setId(id);
			managerPerformmance.setCrateday(new Date());
			managerPerformmanceService.insertmanagerPerformmance(managerPerformmance); 
			returnMap.addGlobalMessage(CREATE_SUCCESS);
			returnMap.put("mess", "提交成功");
		} catch (Exception e) {
			returnMap.put(JRadConstants.SUCCESS, false);
			returnMap.put("mess", "提交失败");
			returnMap.addGlobalMessage("保存失败");
		}

		return returnMap;
	}

	//修改进度页面
	@ResponseBody
	@RequestMapping(value = "performUpdate.page")
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView performUpdate(HttpServletRequest request) {        
		JRadModelAndView mv = new JRadModelAndView("/manager/performmance/performmanceUpdate", request);
		return mv;
	}
	//根据ID查指定客户经理业绩进度总和
		@ResponseBody
		@RequestMapping(value = "performsumselect.json")
		@JRadOperation(JRadOperation.BROWSE)
		public String performselect(HttpServletRequest request) {        
			
			String managerId= request.getParameter("managerId");
			String changedate = request.getParameter("changedate");
			ManagerPerformmance managerperformmance= managerPerformmanceService.finManagerPerformmanceSumById(managerId,changedate);
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
			JSONObject json = JSONObject.fromObject(managerperformmance, jsonConfig);
			return json.toString();
		}

	//导出业绩进度信息
	@ResponseBody
	@RequestMapping(value = "performexport.json", method = { RequestMethod.GET })
	public JRadReturnMap JRadReturnMap(HttpServletRequest request,HttpServletResponse response) {        

		JRadReturnMap returnMap = new JRadReturnMap();
		String satrtDate = request.getParameter("startdate");
		String endDate = request.getParameter("enddate");
		String satrtDate1 = satrtDate;
		String endDate1 = endDate;
		try{
			List<BankListForm> bankListForm = managerPerformmanceService.findALlbank();
			List<ManagerPerformmanceForm> gxperformList = new ArrayList<ManagerPerformmanceForm>();
			if(satrtDate!=null&&satrtDate!=""){
				satrtDate+=" 00:00:00";
			}
			if(endDate!=null&&endDate!=""){
				endDate+=" 23:59:59";
			}
			for(int j=0;j<bankListForm.size();j++){
				String id= bankListForm.get(j).getId();
				List<DeptMemberForm> gxMumberList = managerPerformmanceService.findMumberByDeptId(id);
				for(int i=0;i<gxMumberList.size();i++){
					String managerId =gxMumberList.get(i).getId();
					ManagerPerformmanceForm managerPerformmanceForm= managerPerformmanceService.findSumPerformmanceById(managerId,satrtDate,endDate);
					if(managerPerformmanceForm==null){
						continue;
					}
					ManagerPerformmance managerPerformmanceold = managerPerformmanceService.finManagerPerformmanceById(managerId);
					if(managerPerformmanceold!=null){
					managerPerformmanceForm.setApplycount(managerPerformmanceold.getApplycount());
					managerPerformmanceForm.setApplyrefuse(managerPerformmanceold.getApplyrefuse());
					managerPerformmanceForm.setCreditcount(managerPerformmanceold.getCreditcount());
					managerPerformmanceForm.setCreditrefuse(managerPerformmanceold.getCreditrefuse());
					managerPerformmanceForm.setGivemoneycount(managerPerformmanceold.getGivemoneycount());
					managerPerformmanceForm.setInternalcount(managerPerformmanceold.getInternalcount());
					managerPerformmanceForm.setMeetingcout(managerPerformmanceold.getMeetingcout());
					managerPerformmanceForm.setPasscount(managerPerformmanceold.getPasscount());
					managerPerformmanceForm.setRealycount(managerPerformmanceold.getRealycount());
					managerPerformmanceForm.setReportcount(managerPerformmanceold.getReportcount());
					managerPerformmanceForm.setSigncount(managerPerformmanceold.getSigncount());
					managerPerformmanceForm.setVisitcount(managerPerformmanceold.getVisitcount());
					}
					managerPerformmanceForm.setName(gxMumberList.get(i).getOname());
					managerPerformmanceForm.setManagerName(gxMumberList.get(i).getDisplay_name());
					gxperformList.add(managerPerformmanceForm);
				}
				ManagerPerformmanceForm managerPerformmanceForm1 = managerPerformmanceService.findDeptSumPerformmanceById(id,satrtDate,endDate);
				if(managerPerformmanceForm1==null){
					continue;
				}
				ManagerPerformmance managerPerformmancezhi = managerPerformmanceService.findDeptTodayPerformmanceById(id);
				if(managerPerformmancezhi!=null){
					managerPerformmanceForm1.setApplycount(managerPerformmancezhi.getApplycount());
					managerPerformmanceForm1.setApplyrefuse(managerPerformmancezhi.getApplyrefuse());
					managerPerformmanceForm1.setCreditcount(managerPerformmancezhi.getCreditcount());
					managerPerformmanceForm1.setCreditrefuse(managerPerformmancezhi.getCreditrefuse());
					managerPerformmanceForm1.setGivemoneycount(managerPerformmancezhi.getGivemoneycount());
					managerPerformmanceForm1.setInternalcount(managerPerformmancezhi.getInternalcount());
					managerPerformmanceForm1.setMeetingcout(managerPerformmancezhi.getMeetingcout());
					managerPerformmanceForm1.setPasscount(managerPerformmancezhi.getPasscount());
					managerPerformmanceForm1.setRealycount(managerPerformmancezhi.getRealycount());
					managerPerformmanceForm1.setReportcount(managerPerformmancezhi.getReportcount());
					managerPerformmanceForm1.setSigncount(managerPerformmancezhi.getSigncount());
					managerPerformmanceForm1.setVisitcount(managerPerformmancezhi.getVisitcount());
				}
				managerPerformmanceForm1.setName(bankListForm.get(j).getName());
				managerPerformmanceForm1.setManagerName("汇总");
				gxperformList.add(managerPerformmanceForm1);
			}
			ManagerPerformmanceForm managerPerformmanceForm2 = managerPerformmanceService.findALLDeptSumPerformmanceById(satrtDate,endDate);
			if(managerPerformmanceForm2!=null){
				ManagerPerformmance managerPerformmancezong = managerPerformmanceService.findDeptTodaySumPerformmanceById();	
				if(managerPerformmancezong!=null){
					managerPerformmanceForm2.setApplycount(managerPerformmancezong.getApplycount());
					managerPerformmanceForm2.setApplyrefuse(managerPerformmancezong.getApplyrefuse());
					managerPerformmanceForm2.setCreditcount(managerPerformmancezong.getCreditcount());
					managerPerformmanceForm2.setCreditrefuse(managerPerformmancezong.getCreditrefuse());
					managerPerformmanceForm2.setGivemoneycount(managerPerformmancezong.getGivemoneycount());
					managerPerformmanceForm2.setInternalcount(managerPerformmancezong.getInternalcount());
					managerPerformmanceForm2.setMeetingcout(managerPerformmancezong.getMeetingcout());
					managerPerformmanceForm2.setPasscount(managerPerformmancezong.getPasscount());
					managerPerformmanceForm2.setRealycount(managerPerformmancezong.getRealycount());
					managerPerformmanceForm2.setReportcount(managerPerformmancezong.getReportcount());
					managerPerformmanceForm2.setSigncount(managerPerformmancezong.getSigncount());
					managerPerformmanceForm2.setVisitcount(managerPerformmancezong.getVisitcount());
				}
				managerPerformmanceForm2.setName("统计");
				managerPerformmanceForm2.setManagerName("总计");
				gxperformList.add(managerPerformmanceForm2);
			}
			managerPerformmanceService.getExportWageData(gxperformList, response,satrtDate1,endDate1);
		}
		catch (Exception e) {
			return WebRequestHelper.processException(e);
		}
		return returnMap;
	}


	//	//执行修改进度
		@ResponseBody
		@RequestMapping(value = "performUpdate.json")
		public JRadReturnMap updateinfo(@ModelAttribute ManagerPerformmance ManagerPerformmance,HttpServletRequest request) {        
			JRadReturnMap returnMap = new JRadReturnMap();
	
			if(ManagerPerformmance.getManager_id().equals("0")){
				returnMap.put("mess", "请选择客户经理");
				return returnMap;
			}
			try {
				String changedate = request.getParameter("changedate");
				ManagerPerformmance managerperformmance= managerPerformmanceService.finManagerPerformmanceSumById(ManagerPerformmance.getManager_id(),changedate);
				if(managerperformmance==null){
					String id = IDGenerator.generateID();
					ManagerPerformmance.setId(id);
					String oldDate=changedate+" 12:00:00";
					ManagerPerformmance.setCrateday(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(oldDate));
					managerPerformmanceService.insertmanagerPerformmance(ManagerPerformmance);
					returnMap.put("mess", "该客户经理指定日期进度不存在，已补录成功！");
				}else{
					
					ManagerPerformmance.setId(managerperformmance.getId());
					managerPerformmanceService.updateManagerPerformmanceSumInfor(ManagerPerformmance);
					
					returnMap.put("mess", "修改进度成功");
				}
			} catch (Exception e) {
				returnMap.put(JRadConstants.SUCCESS, false);
				returnMap.put("mess", "提交失败");
				returnMap.addGlobalMessage("保存失败");
			}
			return returnMap;
		}


}
