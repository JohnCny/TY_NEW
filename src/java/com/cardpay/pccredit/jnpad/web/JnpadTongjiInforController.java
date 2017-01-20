package com.cardpay.pccredit.jnpad.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.intopieces.filter.IntoPiecesFilter;
import com.cardpay.pccredit.intopieces.model.IntoPieces;
import com.cardpay.pccredit.ipad.util.JsonDateValueProcessor;
import com.cardpay.pccredit.jnpad.model.JBUser;
import com.cardpay.pccredit.jnpad.service.JnIpadJBUserService;
import com.cardpay.pccredit.manager.service.ManagerBelongMapService;
import com.cardpay.pccredit.manager.web.ManagerBelongMapForm;
import com.cardpay.pccredit.report.model.NameValueRecord;
import com.cardpay.pccredit.report.service.StatisticalCommonService;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.database.model.QueryResult;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
public class JnpadTongjiInforController {
	@Autowired
	private ManagerBelongMapService BelongMapService;
	@Autowired
	private StatisticalCommonService statisticalCommonService;
	@Autowired
	private com.cardpay.pccredit.jnpad.service.JnIpadCustAppInfoXxService JnIpadCustAppInfoXxService;
	@Autowired
	private JnIpadJBUserService JnIpadJBUser;
	@ResponseBody
	@RequestMapping(value = "/ipad/tongji.json", method = { RequestMethod.GET })
	public String indexPage(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		String allyq;
		Map<Object, Object> map = new LinkedHashMap<Object, Object>();
			long start = System.currentTimeMillis();
			//各区域放款总额与逾期总额
			List<NameValueRecord> records=statisticalCommonService.statisticalyqorgan1();
			map.put("oneyq", records.get(0));
			map.put("twoyq", records.get(1));
			map.put("threeyq", records.get(2));
			map.put("fouryq", records.get(3));
			List<NameValueRecord> result1=statisticalCommonService.statisticalsxorgan1();
			map.put("oneyx", result1.get(0));
			map.put("twoyx", result1.get(1));
			map.put("threeyx", result1.get(2));
			map.put("fouryx", result1.get(3));
			// 当前进件状况 济南 sj 20160804
			List<NameValueRecord> result=statisticalCommonService.statisticalApplicationStatus1();
			map.put("allspover", Integer.parseInt(result.get(0).getId()));
			map.put("nopss", Integer.parseInt(result.get(0).getName()));
			map.put("ysq", Integer.parseInt(result.get(0).getValue()));
			map.put("back", Integer.parseInt(result.get(0).getValue1()));
			map.put("success", Integer.parseInt(result.get(0).getValue2()));
		    // 统计各行已申请和通过进件数量  济南 sj 20160809
			//map.put("organApplicationAuditNumJson",statisticalCommonService.getOrganApplicationAuditNumJson1());
			//map.put("organApplicationApprovedNumJson",statisticalCommonService.getOrganApplicationApprovedNumJson1());
			//int allvalue=statisticalCommonService.selectALLyxze();
			//map.put("yxze", allvalue);
			/*allyq=statisticalCommonService.selectALLyqze();
			if(allyq!=null){
				map.put("yqze", allyq);
			}else{
				map.put("yqze", "0.00");
			}*/
			Date datetime=new Date();
			SimpleDateFormat sdfe=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String datetime1=sdfe.format(datetime);
			String datetime2= datetime1.substring(0, 4);
			List<NameValueRecord> ydlist=statisticalCommonService.selectYDyx(userId);
			Integer ylist1=0;
			Integer ylist2=0;
			Integer ylist3=0;
			Integer ylist4=0;
			Integer ylist5=0;
			Integer ylist6=0;
			Integer ylist7=0;
			Integer ylist8=0;
			Integer ylist9=0;
			Integer ylist10=0;
			Integer ylist11=0;
			Integer ylist12=0;
			for(int i=0;i<ydlist.size();i++){
				String loantime=ydlist.get(i).getName();
				String time2=loantime.substring(4, 6);
				String nowTime=loantime.substring(0, 4);
				if(time2.equals("01") && nowTime.equals(datetime2)){
					ylist1+=Integer.parseInt(ydlist.get(i).getValue().replace(".00",""));
				}else if(time2.equals("02") && nowTime.equals(datetime2)){
					ylist2+=Integer.parseInt(ydlist.get(i).getValue().replace(".00",""));
				}
				else if(time2.equals("03") && nowTime.equals(datetime2)){
					ylist3+=Integer.parseInt(ydlist.get(i).getValue().replace(".00",""));
				}
				else if(time2.equals("04") && nowTime.equals(datetime2)){
					ylist4+=Integer.parseInt(ydlist.get(i).getValue().replace(".00",""));
				}
				else if(time2.equals("05") && nowTime.equals(datetime2)){
					ylist5+=Integer.parseInt(ydlist.get(i).getValue().replace(".00",""));
				}
				else if(time2.equals("06") && nowTime.equals(datetime2)){
					ylist6+=Integer.parseInt(ydlist.get(i).getValue().replace(".00",""));
				}
				else if(time2.equals("07") && nowTime.equals(datetime2)){
					ylist7+=Integer.parseInt(ydlist.get(i).getValue().replace(".00",""));
				}
				else if(time2.equals("08") && nowTime.equals(datetime2)){
					ylist8+=Integer.parseInt(ydlist.get(i).getValue().replace(".00",""));
				}
				else if(time2.equals("09") && nowTime.equals(datetime2)){
					ylist9+=Integer.parseInt(ydlist.get(i).getValue().replace(".00",""));
				}
				else if(time2.equals("10") && nowTime.equals(datetime2)){
					ylist10+=Integer.parseInt(ydlist.get(i).getValue().replace(".00",""));
				}
				else if(time2.equals("11") && nowTime.equals(datetime2)){
					ylist11+=Integer.parseInt(ydlist.get(i).getValue().replace(".00",""));
				}
				else if(time2.equals("12") && nowTime.equals(datetime2)){
					ylist12+=Integer.parseInt(ydlist.get(i).getValue().replace(".00",""));
				}
			}
			map.put("ylist1", ylist1);
			map.put("ylist2", ylist2);
			map.put("ylist3", ylist3);
			map.put("ylist4", ylist4);
			map.put("ylist5", ylist5);
			map.put("ylist6", ylist6);
			map.put("ylist7", ylist7);
			map.put("ylist8", ylist8);
			map.put("ylist9", ylist9);
			map.put("ylist10", ylist10);
			map.put("ylist11", ylist11);
			map.put("ylist12", ylist12);
			
			//申请拒绝
					List clist1=new ArrayList();
					List clist2=new ArrayList();
					List clist3=new ArrayList();
					List clist4=new ArrayList();
					List clist5=new ArrayList();
					List clist6=new ArrayList();
					List clist7=new ArrayList();
					List clist8=new ArrayList();
					List clist9=new ArrayList();
					List clist10=new ArrayList();
					List clist11=new ArrayList();
					List clist12=new ArrayList();
					List<IntoPieces> listt=JnIpadCustAppInfoXxService.findCustomerResulf(userId);
					for(int i=0;i<listt.size();i++){
						SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String time1=sdf.format(listt.get(i).getCreatime());
						String time3=time1.substring(0, 4);
						String[] time2=time1.split("-");
						if(time2[1].equals("01") && time3.equals(datetime2)){
							clist1.add(listt.get(i));
						}else if(time2[1].equals("02") && time3.equals(datetime2)){
							clist2.add(listt.get(i));
						}
						else if(time2[1].equals("03") && time3.equals(datetime2)){
							clist3.add(listt.get(i));
						}
						else if(time2[1].equals("04") && time3.equals(datetime2)){
							clist4.add(listt.get(i));
						}
						else if(time2[1].equals("05") && time3.equals(datetime2)){
							clist5.add(listt.get(i));
						}
						else if(time2[1].equals("06") && time3.equals(datetime2)){
							clist6.add(listt.get(i));
						}
						else if(time2[1].equals("07") && time3.equals(datetime2)){
							clist7.add(listt.get(i));
						}
						else if(time2[1].equals("08") && time3.equals(datetime2)){
							clist8.add(listt.get(i));
						}
						else if(time2[1].equals("09") && time3.equals(datetime2)){
							clist9.add(listt.get(i)); 
						}
						else if(time2[1].equals("10") && time3.equals(datetime2)){
							clist10.add(listt.get(i));
						}
						else if(time2[1].equals("11") && time3.equals(datetime2)){
							clist11.add(listt.get(i));
						}
						else if(time2[1].equals("12") && time3.equals(datetime2)){
							clist12.add(listt.get(i));
						}
					}
					map.put("clist1", clist1.size());
					map.put("clist2", clist2.size());
					map.put("clist3", clist3.size());
					map.put("clist4", clist4.size());
					map.put("clist5", clist5.size());
					map.put("clist6", clist6.size());
					map.put("clist7", clist7.size());
					map.put("clist8", clist8.size());
					map.put("clist9", clist9.size());
					map.put("clist10", clist10.size());
					map.put("clist11", clist11.size());
					map.put("clist12", clist12.size());
			//申请成功
			List list1=new ArrayList();
			List list2=new ArrayList();
			List list3=new ArrayList();
			List list4=new ArrayList();
			List list5=new ArrayList();
			List list6=new ArrayList();
			List list7=new ArrayList();
			List list8=new ArrayList();
			List list9=new ArrayList();
			List list10=new ArrayList();
			List list11=new ArrayList();
			List list12=new ArrayList();
			List<IntoPieces> list=JnIpadCustAppInfoXxService.findCustomersuccess(userId);
			for(int i=0;i<list.size();i++){
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String time1=sdf.format(list.get(i).getCreatime());
				String time4=time1.substring(0,4);
				String[] time2=time1.split("-");
				if(time2[1].equals("01") && time4.equals(datetime2)){
					list1.add(list.get(i));
				}else if(time2[1].equals("02") && time4.equals(datetime2)){
					list2.add(list.get(i));
				}
				else if(time2[1].equals("03")&& time4.equals(datetime2)){
					list3.add(list.get(i));
				}
				else if(time2[1].equals("04")&& time4.equals(datetime2)){
					list4.add(list.get(i));
				}
				else if(time2[1].equals("05")&& time4.equals(datetime2)){
					list5.add(list.get(i));
				}
				else if(time2[1].equals("06")&& time4.equals(datetime2)){
					list6.add(list.get(i));
				}
				else if(time2[1].equals("07")&& time4.equals(datetime2)){
					list7.add(list.get(i));
				}
				else if(time2[1].equals("08")&& time4.equals(datetime2)){
					list8.add(list.get(i));
				}
				else if(time2[1].equals("09")&& time4.equals(datetime2)){
					list9.add(list.get(i));
				}
				else if(time2[1].equals("10")&& time4.equals(datetime2)){
					list10.add(list.get(i));
				}
				else if(time2[1].equals("11")&& time4.equals(datetime2)){
					list11.add(list.get(i));
				}
				else if(time2[1].equals("12")&& time4.equals(datetime2)){
					list12.add(list.get(i));
				}
			}
			map.put("list1", list1.size());
			map.put("list2", list2.size());
			map.put("list3", list3.size());
			map.put("list4", list4.size());
			map.put("list5", list5.size());
			map.put("list6", list6.size());
			map.put("list7", list7.size());
			map.put("list8", list8.size());
			map.put("list9", list9.size());
			map.put("list10", list10.size());
			map.put("list11", list11.size());
			map.put("list12", list12.size());
			long end = System.currentTimeMillis();
			System.out.println("#########################查询时间花费：" + (end - start) + "毫秒");
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
			JSONObject json = JSONObject.fromObject(map, jsonConfig);
			return json.toString();
	}
	
	
	
	
	/**
	 * 按月度查询当前客户经理进件申请信息
	 */
	/*@ResponseBody
	@RequestMapping(value = "/ipad/customerIntopiece/selectByYF.json", method = { RequestMethod.GET })
	public String selectByYF( HttpServletRequest request) {
		String userId = request.getParameter("userId");
		//申请成功
				List clist1=new ArrayList();
				List clist2=new ArrayList();
				List clist3=new ArrayList();
				List clist4=new ArrayList();
				List clist5=new ArrayList();
				List clist6=new ArrayList();
				List clist7=new ArrayList();
				List clist8=new ArrayList();
				List clist9=new ArrayList();
				List clist10=new ArrayList();
				List clist11=new ArrayList();
				List clist12=new ArrayList();
				List<IntoPieces> list=JnIpadCustAppInfoXxService.findCustomersuccess(userId);
				for(int i=0;i<list.size();i++){
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String time1=sdf.format(list.get(1).getCreatime());
					String[] time2=time1.split("-");
					if(time2[1].equals("01")){
						clist1.add(list.get(i));
					}else if(time2[1].equals("02")){
						clist2.add(list.get(i));
					}
					else if(time2[1].equals("03")){
						clist3.add(list.get(i));
					}
					else if(time2[1].equals("04")){
						clist4.add(list.get(i));
					}
					else if(time2[1].equals("05")){
						clist5.add(list.get(i));
					}
					else if(time2[1].equals("06")){
						clist6.add(list.get(i));
					}
					else if(time2[1].equals("07")){
						clist7.add(list.get(i));
					}
					else if(time2[1].equals("08")){
						clist8.add(list.get(i));
					}
					else if(time2[1].equals("09")){
						clist9.add(list.get(i));
					}
					else if(time2[1].equals("10")){
						clist10.add(list.get(i));
					}
					else if(time2[1].equals("11")){
						clist11.add(list.get(i));
					}
					else if(time2[1].equals("12")){
						clist12.add(list.get(i));
					}
				}
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("clist1", clist1.size());
				map.put("clist2", clist2.size());
				map.put("clist3", clist3.size());
				map.put("clist4", clist4.size());
				map.put("clist5", clist5.size());
				map.put("clist6", clist6.size());
				map.put("clist7", clist7.size());
				map.put("clist8", clist8.size());
				map.put("clist9", clist9.size());
				map.put("clist10", clist10.size());
				map.put("clist11", clist11.size());
				map.put("clist12", clist12.size());
		//申请成功
		List list1=new ArrayList();
		List list2=new ArrayList();
		List list3=new ArrayList();
		List list4=new ArrayList();
		List list5=new ArrayList();
		List list6=new ArrayList();
		List list7=new ArrayList();
		List list8=new ArrayList();
		List list9=new ArrayList();
		List list10=new ArrayList();
		List list11=new ArrayList();
		List list12=new ArrayList();
		List<IntoPieces> list=JnIpadCustAppInfoXxService.findCustomersuccess(userId);
		for(int i=0;i<list.size();i++){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time1=sdf.format(list.get(1).getCreatime());
			String[] time2=time1.split("-");
			if(time2[1].equals("01")){
				list1.add(list.get(i));
			}else if(time2[1].equals("02")){
				list2.add(list.get(i));
			}
			else if(time2[1].equals("03")){
				list3.add(list.get(i));
			}
			else if(time2[1].equals("04")){
				list4.add(list.get(i));
			}
			else if(time2[1].equals("05")){
				list5.add(list.get(i));
			}
			else if(time2[1].equals("06")){
				list6.add(list.get(i));
			}
			else if(time2[1].equals("07")){
				list7.add(list.get(i));
			}
			else if(time2[1].equals("08")){
				list8.add(list.get(i));
			}
			else if(time2[1].equals("09")){
				list9.add(list.get(i));
			}
			else if(time2[1].equals("10")){
				list10.add(list.get(i));
			}
			else if(time2[1].equals("11")){
				list11.add(list.get(i));
			}
			else if(time2[1].equals("12")){
				list12.add(list.get(i));
			}
		}
		map.put("list1", list1.size());
		map.put("list2", list2.size());
		map.put("list3", list3.size());
		map.put("list4", list4.size());
		map.put("list5", list5.size());
		map.put("list6", list6.size());
		map.put("list7", list7.size());
		map.put("list8", list8.size());
		map.put("list9", list9.size());
		map.put("list10", list10.size());
		map.put("list11", list11.size());
		map.put("list12", list12.size());
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}*/
	
	/**
	 * 小组长按月度查询当前客户经理进件申请信息
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/customerIntopiece/selectByYF.json", method = { RequestMethod.GET })
	public String selectByYF( HttpServletRequest request) {
		Date datetime=new Date();
		SimpleDateFormat sdfe=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datetime1=sdfe.format(datetime);
		String datetime2= datetime1.substring(0, 4);
		Integer ylist1=0;
		Integer ylist2=0;
		Integer ylist3=0;
		Integer ylist4=0;
		Integer ylist5=0;
		Integer ylist6=0;
		Integer ylist7=0;
		Integer ylist8=0;
		Integer ylist9=0;
		Integer ylist10=0;
		Integer ylist11=0;
		Integer ylist12=0;
		List clist1=new ArrayList();
		List clist2=new ArrayList();
		List clist3=new ArrayList();
		List clist4=new ArrayList();
		List clist5=new ArrayList();
		List clist6=new ArrayList();
		List clist7=new ArrayList();
		List clist8=new ArrayList();
		List clist9=new ArrayList();
		List clist10=new ArrayList();
		List clist11=new ArrayList();
		List clist12=new ArrayList();
		List list1=new ArrayList();
		List list2=new ArrayList();
		List list3=new ArrayList();
		List list4=new ArrayList();
		List list5=new ArrayList();
		List list6=new ArrayList();
		List list7=new ArrayList();
		List list8=new ArrayList();
		List list9=new ArrayList();
		List list10=new ArrayList();
		List list11=new ArrayList();
		List list12=new ArrayList();
		Map<String, Object> map = new HashMap<String, Object>();
		String userId = request.getParameter("userId");
		if(request.getParameter("userdj").equals("客户经理主管")){
			ManagerBelongMapForm BelongMapForm =new ManagerBelongMapForm();
			BelongMapForm.setId(userId);
			List<ManagerBelongMapForm> resultq=BelongMapService.findxzz(userId);
			resultq.add(BelongMapForm);
			for(int c=0;c<resultq.size();c++){
				List<IntoPieces> list=JnIpadCustAppInfoXxService.findCustomerResulf(resultq.get(c).getId());
				for(int i=0;i<list.size();i++){
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String time1=sdf.format(list.get(i).getCreatime());
					String nowTime=time1.substring(0, 4);
					String[] time2=time1.split("-");
					if(time2[1].equals("01") && nowTime.equals(datetime2)){
						clist1.add(list.get(i));
					}else if(time2[1].equals("02")&& nowTime.equals(datetime2)){
						clist2.add(list.get(i));
					}
					else if(time2[1].equals("03")&& nowTime.equals(datetime2)){
						clist3.add(list.get(i));
					}
					else if(time2[1].equals("04")&& nowTime.equals(datetime2)){
						clist4.add(list.get(i));
					}
					else if(time2[1].equals("05")&& nowTime.equals(datetime2)){
						clist5.add(list.get(i));
					}
					else if(time2[1].equals("06")&& nowTime.equals(datetime2)){
						clist6.add(list.get(i));
					}
					else if(time2[1].equals("07")&& nowTime.equals(datetime2)){
						clist7.add(list.get(i));
					}
					else if(time2[1].equals("08")&& nowTime.equals(datetime2)){
						clist8.add(list.get(i));
					}
					else if(time2[1].equals("09")&& nowTime.equals(datetime2)){
						clist9.add(list.get(i));
					}
					else if(time2[1].equals("10")&& nowTime.equals(datetime2)){
						clist10.add(list.get(i));
					}
					else if(time2[1].equals("11")&& nowTime.equals(datetime2)){
						clist11.add(list.get(i));
					}
					else if(time2[1].equals("12")&& nowTime.equals(datetime2)){
						clist12.add(list.get(i));
					}
				}
			}
			//申请成功
			for(int d=0;d<resultq.size();d++){
			List<IntoPieces> list=JnIpadCustAppInfoXxService.findCustomersuccess(resultq.get(d).getId());
			for(int i=0;i<list.size();i++){
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String time1=sdf.format(list.get(i).getCreatime());
				String time4=time1.substring(0, 4);
				String[] time2=time1.split("-");
				if(time2[1].equals("01") && time4.equals(datetime2)){
					list1.add(list.get(i));
				}else if(time2[1].equals("02")&& time4.equals(datetime2)){
					list2.add(list.get(i));
				}
				else if(time2[1].equals("03")&& time4.equals(datetime2)){
					list3.add(list.get(i));
				}
				else if(time2[1].equals("04")&& time4.equals(datetime2)){
					list4.add(list.get(i));
				}
				else if(time2[1].equals("05")&& time4.equals(datetime2)){
					list5.add(list.get(i));
				}
				else if(time2[1].equals("06")&& time4.equals(datetime2)){
					list6.add(list.get(i));
				}
				else if(time2[1].equals("07")&& time4.equals(datetime2)){
					list7.add(list.get(i));
				}
				else if(time2[1].equals("08")&& time4.equals(datetime2)){
					list8.add(list.get(i));
				}
				else if(time2[1].equals("09")&& time4.equals(datetime2)){
					list9.add(list.get(i));
				}
				else if(time2[1].equals("10")&& time4.equals(datetime2)){
					list10.add(list.get(i));
				}
				else if(time2[1].equals("11")&& time4.equals(datetime2)){
					list11.add(list.get(i));
				}
				else if(time2[1].equals("12")&& time4.equals(datetime2)){
					list12.add(list.get(i));
				}
			}
			}
			for(int c=0;c<resultq.size();c++){
			List<NameValueRecord> ydlist=statisticalCommonService.selectYDyx(resultq.get(c).getId());	
			for(int i=0;i<ydlist.size();i++){
				String loantime=ydlist.get(i).getName();
				String time3=loantime.substring(0, 4);
				String time2=loantime.substring(4, 6);
				if(time2.equals("01") && time3.equals(datetime2)){
					ylist1+=Integer.parseInt(ydlist.get(i).getValue().replace(".00",""));
				}else if(time2.equals("02") && time3.equals(datetime2)){
					ylist2+=Integer.parseInt(ydlist.get(i).getValue().replace(".00",""));
				}
				else if(time2.equals("03") && time3.equals(datetime2)){
					ylist3+=Integer.parseInt(ydlist.get(i).getValue().replace(".00",""));
				}
				else if(time2.equals("04") && time3.equals(datetime2)){
					ylist4+=Integer.parseInt(ydlist.get(i).getValue().replace(".00",""));
				}
				else if(time2.equals("05") && time3.equals(datetime2)){
					ylist5+=Integer.parseInt(ydlist.get(i).getValue().replace(".00",""));
				}
				else if(time2.equals("06") && time3.equals(datetime2)){
					ylist6+=Integer.parseInt(ydlist.get(i).getValue().replace(".00",""));
				}
				else if(time2.equals("07") && time3.equals(datetime2)){
					ylist7+=Integer.parseInt(ydlist.get(i).getValue().replace(".00",""));
				}
				else if(time2.equals("08") && time3.equals(datetime2)){
					ylist8+=Integer.parseInt(ydlist.get(i).getValue().replace(".00",""));
				}
				else if(time2.equals("09") && time3.equals(datetime2)){
					ylist9+=Integer.parseInt(ydlist.get(i).getValue().replace(".00",""));
				}
				else if(time2.equals("10") && time3.equals(datetime2)){
					ylist10+=Integer.parseInt(ydlist.get(i).getValue().replace(".00",""));
				}
				else if(time2.equals("11") && time3.equals(datetime2)){
					ylist11+=Integer.parseInt(ydlist.get(i).getValue().replace(".00",""));
				}
				else if(time2.equals("12") && time3.equals(datetime2)){
					ylist12+=Integer.parseInt(ydlist.get(i).getValue().replace(".00",""));
				}}
			}
		}else{
			List<JBUser>  qulist=new ArrayList();
			List<JBUser> depart=JnIpadJBUser.selectDepartUser(userId);
			for(int i=0;i<depart.size();i++){
				List<JBUser> findxzcy=JnIpadJBUser.selectUserByDid2(depart.get(i).getId());
				for(int a=0;a<findxzcy.size();a++){
					qulist.add(findxzcy.get(a));	
				}
		}
			for(int a=0;a<qulist.size();a++){
				List<NameValueRecord> ydlist=statisticalCommonService.selectYDyx(qulist.get(a).getUserId());
				for(int i=0;i<ydlist.size();i++){
					String loantime=ydlist.get(i).getName();
					String time5=loantime.substring(0,4);
					String time2=loantime.substring(4, 6);
					if(time2.equals("01") && time5.equals(datetime2)){
						ylist1+=Integer.parseInt(ydlist.get(i).getValue().replace(".00",""));
					}else if(time2.equals("02")&& time5.equals(datetime2)){
						ylist2+=Integer.parseInt(ydlist.get(i).getValue().replace(".00",""));
					}
					else if(time2.equals("03")&& time5.equals(datetime2)){
						ylist3+=Integer.parseInt(ydlist.get(i).getValue().replace(".00",""));
					}
					else if(time2.equals("04")&& time5.equals(datetime2)){
						ylist4+=Integer.parseInt(ydlist.get(i).getValue().replace(".00",""));
					}
					else if(time2.equals("05")&& time5.equals(datetime2)){
						ylist5+=Integer.parseInt(ydlist.get(i).getValue().replace(".00",""));
					}
					else if(time2.equals("06")&& time5.equals(datetime2)){
						ylist6+=Integer.parseInt(ydlist.get(i).getValue().replace(".00",""));
					}
					else if(time2.equals("07")&& time5.equals(datetime2)){
						ylist7+=Integer.parseInt(ydlist.get(i).getValue().replace(".00",""));
					}
					else if(time2.equals("08")&& time5.equals(datetime2)){
						ylist8+=Integer.parseInt(ydlist.get(i).getValue().replace(".00",""));
					}
					else if(time2.equals("09")&& time5.equals(datetime2)){
						ylist9+=Integer.parseInt(ydlist.get(i).getValue().replace(".00",""));
					}
					else if(time2.equals("10")&& time5.equals(datetime2)){
						ylist10+=Integer.parseInt(ydlist.get(i).getValue().replace(".00",""));
					}
					else if(time2.equals("11")&& time5.equals(datetime2)){
						ylist11+=Integer.parseInt(ydlist.get(i).getValue().replace(".00",""));
					}
					else if(time2.equals("12")&& time5.equals(datetime2)){
						ylist12+=Integer.parseInt(ydlist.get(i).getValue().replace(".00",""));
					}
				}
				
				List<IntoPieces> listt=JnIpadCustAppInfoXxService.findCustomerResulf(qulist.get(a).getUserId());
				for(int i=0;i<listt.size();i++){
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String time1=sdf.format(listt.get(i).getCreatime());
					String time5=time1.substring(0,4);
					String[] time2=time1.split("-");
					if(time2[1].equals("01")&& time5.equals(datetime2)){
						clist1.add(listt.get(i));
					}else if(time2[1].equals("02")&& time5.equals(datetime2)){
						clist2.add(listt.get(i));
					}
					else if(time2[1].equals("03")&& time5.equals(datetime2)){
						clist3.add(listt.get(i));
					}
					else if(time2[1].equals("04")&& time5.equals(datetime2)){
						clist4.add(listt.get(i));
					}
					else if(time2[1].equals("05")&& time5.equals(datetime2)){
						clist5.add(listt.get(i));
					}
					else if(time2[1].equals("06")&& time5.equals(datetime2)){
						clist6.add(listt.get(i));
					}
					else if(time2[1].equals("07")&& time5.equals(datetime2)){
						clist7.add(listt.get(i));
					}
					else if(time2[1].equals("08")&& time5.equals(datetime2)){
						clist8.add(listt.get(i));
					}
					else if(time2[1].equals("09")&& time5.equals(datetime2)){
						clist9.add(listt.get(i));
					}
					else if(time2[1].equals("10")&& time5.equals(datetime2)){
						clist10.add(listt.get(i));
					}
					else if(time2[1].equals("11")&& time5.equals(datetime2)){
						clist11.add(listt.get(i));
					}
					else if(time2[1].equals("12")&& time5.equals(datetime2)){
						clist12.add(listt.get(i));
					}
				}
				
				List<IntoPieces> list=JnIpadCustAppInfoXxService.findCustomersuccess(qulist.get(a).getUserId());
				for(int i=0;i<list.size();i++){
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String time1=sdf.format(list.get(i).getCreatime());
					String time5=time1.substring(0,4);
					String[] time2=time1.split("-");
					if(time2[1].equals("01")&& time5.equals(datetime2)){
						list1.add(list.get(i));
					}else if(time2[1].equals("02")&& time5.equals(datetime2)){
						list2.add(list.get(i));
					}
					else if(time2[1].equals("03")&& time5.equals(datetime2)){
						list3.add(list.get(i));
					}
					else if(time2[1].equals("04")&& time5.equals(datetime2)){
						list4.add(list.get(i));
					}
					else if(time2[1].equals("05")&& time5.equals(datetime2)){
						list5.add(list.get(i));
					}
					else if(time2[1].equals("06")&& time5.equals(datetime2)){
						list6.add(list.get(i));
					}
					else if(time2[1].equals("07")&& time5.equals(datetime2)){
						list7.add(list.get(i));
					}
					else if(time2[1].equals("08")&& time5.equals(datetime2)){
						list8.add(list.get(i));
					}
					else if(time2[1].equals("09")&& time5.equals(datetime2)){
						list9.add(list.get(i));
					}
					else if(time2[1].equals("10")&& time5.equals(datetime2)){
						list10.add(list.get(i));
					}
					else if(time2[1].equals("11")&& time5.equals(datetime2)){
						list11.add(list.get(i));
					}
					else if(time2[1].equals("12")&& time5.equals(datetime2)){
						list12.add(list.get(i));
					}
				}
				
				
				
			}
			
			
		}
		map.put("list1", list1.size());
		map.put("list2", list2.size());
		map.put("list3", list3.size());
		map.put("list4", list4.size());
		map.put("list5", list5.size());
		map.put("list6", list6.size());
		map.put("list7", list7.size());
		map.put("list8", list8.size());
		map.put("list9", list9.size());
		map.put("list10", list10.size());
		map.put("list11", list11.size());
		map.put("list12", list12.size());
		map.put("ylist1", ylist1);
		map.put("ylist2", ylist2);
		map.put("ylist3", ylist3);
		map.put("ylist4", ylist4);
		map.put("ylist5", ylist5);
		map.put("ylist6", ylist6);
		map.put("ylist7", ylist7);
		map.put("ylist8", ylist8);
		map.put("ylist9", ylist9);
		map.put("ylist10", ylist10);
		map.put("ylist11", ylist11);
		map.put("ylist12", ylist12);
		map.put("clist1", clist1.size());
		map.put("clist2", clist2.size());
		map.put("clist3", clist3.size());
		map.put("clist4", clist4.size());
		map.put("clist5", clist5.size());
		map.put("clist6", clist6.size());
		map.put("clist7", clist7.size());
		map.put("clist8", clist8.size());
		map.put("clist9", clist9.size());
		map.put("clist10", clist10.size());
		map.put("clist11", clist11.size());
		map.put("clist12", clist12.size());
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
	
}






