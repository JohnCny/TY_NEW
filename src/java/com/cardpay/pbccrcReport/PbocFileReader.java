	
package com.cardpay.pbccrcReport;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

import com.cardpay.pbccrcReport.pojo.DWDB_Info;
import com.cardpay.pbccrcReport.pojo.JZ_Info;
import com.cardpay.pbccrcReport.pojo.PO_Info;
import com.cardpay.pbccrcReport.pojo.Query_Info;
import com.cardpay.pbccrcReport.pojo.WJQDK_Info;
import com.cardpay.pbccrcReport.pojo.WXHDJK_Info;
import com.cardpay.pbccrcReport.pojo.XYTS_Info;
import com.cardpay.pbccrcReport.pojo.YH_Info;
import com.cardpay.pbccrcReport.pojo.YQ_Info;
import com.cardpay.pbccrcReport.pojo.ZY_Info;

/**   
* @ClassName: PbocFileReader    
* @Description: 读取html内容    
* @author 谭文华  
* @date 2014年12月8日 上午10:11:26        
*/
public class PbocFileReader {
	/**   
	* @Title: readUrl    
	* @Description: 加载html文件   
	* @param url
	* @return List<Element>
	* @throws SQLException 
	*/
	private static List<Element> readUrl(String url){
		Source source=null;
		try
		{
			source=new Source(new URL(url));
		}
		catch(MalformedURLException e)
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		List<Element> elementList=source.getAllElements(HTMLElementName.TABLE);
		Element content_Element = elementList.get(Constants.PBOC_CONTENT_TABLEINDEX);
		List<Element> content_Element_List = content_Element.getAllElements(HTMLElementName.TABLE);
		return content_Element_List;
	}

	/**   
	* @Title: readYH    
	* @Description: 读取用户信息
	* @param url
	* @return YH_Info
	*/
	public static YH_Info readYH(String url)
	{
		List<Element> content_Element_List = readUrl(url);
		
		/** start **/
		YH_Info yh_Info = new YH_Info();
		Element yh_Element = content_Element_List.get(Constants.PBOC_YH_INFO_TABLEINDEX).getAllElements(HTMLElementName.TR).get(1);
		/*<td  class="tdStyle" width="20%"> 
	        <div class=high align=center><font color=#0066cc><span class=high>蒋建峰</span></font></div>
	      </td>
	      <td  class="tdStyle" width="20%">
	        <div class=high align=center><font color=#0066cc><span class=high>身份证</span></font></div>
	      </td>
	      <td  class="tdStyle" width="20%">
	        <div class=high align=center><font color=#0066cc><font color=#0066cc>320421197803130918</font></font></div>
	      </td>
	      <td  class="tdStyle" width="20%">
	        <div class=high align=center><font color=#0066cc><span class=high>江苏江南农村商业银行股份有限公司/jnnshgr253</span></font></div>
	      </td>
	      <td  class="tdStyle" width="20%">
	        <div class=high align=center><font color=#0066cc><span class=high>信用卡审批</span></font></div>
	      </td>*/
		List<Element> yh_Info_List = yh_Element.getAllElements(HTMLElementName.TD);
		yh_Info.setUserName(yh_Info_List.get(0).getTextExtractor().toString());
		yh_Info.setCreditType(yh_Info_List.get(1).getTextExtractor().toString());
		yh_Info.setCreditNO(yh_Info_List.get(2).getTextExtractor().toString());
		yh_Info.setQueryOperator(yh_Info_List.get(3).getTextExtractor().toString());
		yh_Info.setQueryReason(yh_Info_List.get(4).getTextExtractor().toString());
		/** end **/
		
		/** start **/
		yh_Element = content_Element_List.get(Constants.PBOC_YH_OTHER_INFO_TABLEINDEX).getAllElements(HTMLElementName.TR).get(1);
		/*<td   style="word-break:break-all" class="tdStyle" > 
	        <div  align=center><font color=#0066cc><span class=high>男性</span></font></div>
	      </td>
	      <td  class="tdStyle" style="word-break:break-all"> 
	        <div  align=center><font color=#0066cc><font color=#0066cc>1978.03.13</font></font></div>
	      </td>
	      <TD  class="tdStyle" style="word-break:break-all"> 
	        <DIV  align=center><FONT color=#0066cc><font color=#0066cc>未婚</font></FONT></DIV>
	      </TD>
	      <td class="tdStyle" style="word-break:break-all"> 
	        <div  align=center><font color=#0066cc><span class=high>15051982796</span></font></div>
	      </td>
	      <td class="tdStyle"  style="word-break:break-all"> 
	        <div  align=center><font color=#0066cc><span class=high>051986555306</span></font></div>
	      </td>
	      <td  class="tdStyle" style="word-break:break-all"> 
	        <div  align=center><font color=#0066cc><span class=high>051986555306</span></font></div>
	      </td>
	      <td  class="tdStyle" style="word-break:break-all"> 
	        <div  align=center><font color=#0066cc><span class=high>初中</span></font></div>
	      </td>
	      <td class="tdStyle" style="word-break:break-all"> 
	        <div  align=center><font color=#0066cc><span class=high>其他</span></font></div>
	      </td>*/
		List<Element> yh_Other_Info_List_1 = yh_Element.getAllElements(HTMLElementName.TD);
		yh_Info.setSex(yh_Other_Info_List_1.get(0).getTextExtractor().toString());
		yh_Info.setBirth(yh_Other_Info_List_1.get(1).getTextExtractor().toString());
		yh_Info.setMarriage(yh_Other_Info_List_1.get(2).getTextExtractor().toString());
		yh_Info.setCellPhone(yh_Other_Info_List_1.get(3).getTextExtractor().toString());
		yh_Info.setWorkPhone(yh_Other_Info_List_1.get(4).getTextExtractor().toString());
		yh_Info.setHomePhone(yh_Other_Info_List_1.get(4).getTextExtractor().toString());
		yh_Info.setEducation(yh_Other_Info_List_1.get(4).getTextExtractor().toString());
		yh_Info.setDegree(yh_Other_Info_List_1.get(4).getTextExtractor().toString());
		/** end **/
		
		/** start **/
		yh_Element = content_Element_List.get(Constants.PBOC_YH_OTHER_INFO_TABLEINDEX).getAllElements(HTMLElementName.TR).get(3);
		/*<td  class="tdStyle" style="word-break:break-all" colspan=3> 
            <div  align=center><font color=#0066cc><span class=high>江苏省常州市武进区湖塘镇周家巷村委杨树园33号</span></font></div>
          </td>
          <td class="tdStyle" style="word-break:break-all" colspan=5> 
            <div  align=center><font color=#0066cc><span class=high>江苏省武进区</span></font></div>
          </td>*/
		List<Element> yh_Other_Info_List_2 = yh_Element.getAllElements(HTMLElementName.TD);
		yh_Info.setMailingAddress(yh_Other_Info_List_2.get(0).getTextExtractor().toString());
		yh_Info.setResidenceAddress(yh_Other_Info_List_2.get(1).getTextExtractor().toString());
		/** end **/
		
		return yh_Info;
	}
	
	/**   
	* @Title: readPO    
	* @Description: 读取配偶信息    
	* @param url
	* @return PO_Info
	*/
	public static PO_Info readPO(String url)
	{
		List<Element> content_Element_List = readUrl(url);
		
		/** start **/
		PO_Info po_Info = new PO_Info();
		Element po_Element = content_Element_List.get(Constants.PBOC_PO_INFO_TABLEINDEX).getAllElements(HTMLElementName.TR).get(1);
		/*<TD  class="tdStyle" style="word-break:break-all" colspan=2> 
            <DIV  align=center><FONT color=#0066cc><SPAN class=high>--</SPAN></FONT></DIV>
          </TD>
          <TD class="tdStyle"  style="word-break:break-all" colspan=1> 
            <DIV  align=center><FONT color=#0066cc><SPAN class=high>--</SPAN></FONT></DIV>
          </TD>
          <TD class="tdStyle" style="word-break:break-all" colspan=1> 
            <DIV  align=center><FONT color=#0066cc><SPAN class=high>--</SPAN></FONT></DIV>
          </TD>
          <TD class="tdStyle"  style="word-break:break-all" colspan=3> 
            <DIV  align=center><FONT color=#0066cc><SPAN class=high>--</SPAN></FONT></DIV>
          </TD>
          <TD class="tdStyle"  style="word-break:break-all" colspan=1> 
            <DIV  align=center><FONT color=#0066cc><SPAN class=high>--</SPAN></FONT></DIV>
          </TD>*/
		List<Element> po_Info_List = po_Element.getAllElements(HTMLElementName.TD);
		po_Info.setName(po_Info_List.get(0).getTextExtractor().toString());
		po_Info.setCreditType(po_Info_List.get(0).getTextExtractor().toString());
		po_Info.setCreditNO(po_Info_List.get(0).getTextExtractor().toString());
		po_Info.setWorkUnit(po_Info_List.get(0).getTextExtractor().toString());
		po_Info.setPhone(po_Info_List.get(0).getTextExtractor().toString());
		return po_Info;
		/** end **/
	}
	
	/**   
	* @Title: readJZ    
	* @Description: 读取居住信息    
	* @param url
	* @return List<JZ_Info>
	*/
	public static List<JZ_Info> readJZ(String url)
	{
		List<Element> content_Element_List = readUrl(url);
		/** start **/
		List<JZ_Info> jz_Info_List = new ArrayList<JZ_Info>();
		List<Element> jz_Element_List = content_Element_List.get(Constants.PBOC_JZ_INFO_TABLEINDEX).getAllElements(HTMLElementName.TR);
		jz_Element_List.remove(0);
		/*<td width="6%"  style="word-break:break-all" class="tdStyle"> 
              <div class=high align=center><font color=#0066cc><span class=high>1</span></font></div>
            </td>
            <td width="59%"  style="word-break:break-all" class="tdStyle"> 
              <div class=high align="center"><font color=#0066cc><span class=high>江苏省常州市武进区湖塘镇周家巷村委杨树园33号</span></font></div>
            </td>
            <td width="15%"  style="word-break:break-all" class="tdStyle"> 
              <div class=high align=center><font color=#0066cc><span class=high>自置</span></font></div>
            </td>
            <td width="20%"  style="word-break:break-all" class="tdStyle"> 
              <div class=high align=center><font color=#0066cc><span class=high>2011.09.15</span></font></div>
            </td>*/
		for(int i=0;i<jz_Element_List.size();i++){
			JZ_Info jz_Info = new JZ_Info();
			List<Element> tmp_ls = jz_Element_List.get(i).getAllElements(HTMLElementName.TD);
			jz_Info.setIndex_(tmp_ls.get(0).getTextExtractor().toString());
			jz_Info.setAddress(tmp_ls.get(1).getTextExtractor().toString());
			jz_Info.setCondition(tmp_ls.get(2).getTextExtractor().toString());
			jz_Info.setUpdateDate(tmp_ls.get(3).getTextExtractor().toString());
			jz_Info_List.add(jz_Info);
		}
		return jz_Info_List;
		/** end **/
	}
	
	/**   
	* @Title: readZY    
	* @Description: 读取职业信息    
	* @param url
	* @return List<ZY_Info>
	*/
	public static List<ZY_Info> readZY(String url)
	{
		List<Element> content_Element_List = readUrl(url);
		
		/** start **/
		List<ZY_Info> zy_Info_List = new ArrayList<ZY_Info>();
		List<Element> zy_Element_List = content_Element_List.get(Constants.PBOC_ZY_INFO_TABLEINDEX).getAllElements(HTMLElementName.TR);
		/*<td width="6%" style="word-break:break-all" class="tdStyle"> 
              <div class=high align=center><font color=#0066cc><span class=high>1</span></font></div>
            </td>
            <td colspan=4 style="word-break:break-all" class="tdStyle"> 
              <div class=high align=center><font color=#0066cc><span class=high>常州市天峰轴承厂</span></font></div>
            </td>
            <td colspan=2  style="word-break:break-all" class="tdStyle"> 
              <div class=high align=center><font color=#0066cc><span class=high>中国江苏常州武进区湖圹镇周家港村委常州市天峰轴承厂</span></font></div>
            </td>
            -----------
            <td  width="6%" style="word-break:break-all" class="tdStyle"> 
              <div class=high align=center><font color=#0066cc><span class=high>1</span></font></div>
            </td>
            <td width="16%" style="word-break:break-all" class="tdStyle"> 
              <div class=high align=center><font color=#0066cc><span class=high>不便分类的其它从业人员</span></font></div>
            </td>
            <td width="16%" style="word-break:break-all" class="tdStyle"> 
              <div class=high align=center><font color=#0066cc><span class=high>金融业</span></font></div>
            </td>
            <td width="16%" style="word-break:break-all" class="tdStyle"> 
              <div class=high align=center><font color=#0066cc><span class=high>高级领导（行政级别局级及局级以上领导或大公司高级管理人员）</span></font></div>
            </td>
            <td width="16%" style="word-break:break-all" class="tdStyle"> 
              <div class=high align=center><font color=#0066cc><span class=high>高级</span></font></div>
            </td>
            <td width="16%" style="word-break:break-all" class="tdStyle"> 
              <div class=high align=center><font color=#0066cc><span class=high>--</span></font></div>
            </td>
            <td width="14%"  style="word-break:break-all" class="tdStyle"> 
              <div class=high align=center><font color=#0066cc><span class=high>2009.04.28</span></font></div>
            </td>*/
		zy_Element_List.remove(zy_Element_List.size()/2);
		zy_Element_List.remove(0);
		
		for(int i=0;i<zy_Element_List.size()/2;i++){
			ZY_Info zy_Info = new ZY_Info();
			List<Element> tmp_ls = zy_Element_List.get(i).getAllElements(HTMLElementName.TD);
			zy_Info.setIndex_(tmp_ls.get(0).getTextExtractor().toString());
			zy_Info.setWorkUnit(tmp_ls.get(1).getTextExtractor().toString());
			zy_Info.setWorkAddress(tmp_ls.get(2).getTextExtractor().toString());
			zy_Info_List.add(zy_Info);
		}
		
		for(int i=zy_Element_List.size()/2,j=0;i<zy_Element_List.size();i++,j++){
			List<Element> tmp_ls = zy_Element_List.get(i).getAllElements(HTMLElementName.TD);
			zy_Info_List.get(j).setJob(tmp_ls.get(1).getTextExtractor().toString());
			zy_Info_List.get(j).setIndustry(tmp_ls.get(2).getTextExtractor().toString());
			zy_Info_List.get(j).setDuty(tmp_ls.get(3).getTextExtractor().toString());
			zy_Info_List.get(j).setHeadship(tmp_ls.get(4).getTextExtractor().toString());
			zy_Info_List.get(j).setJoinTime(tmp_ls.get(5).getTextExtractor().toString());
			zy_Info_List.get(j).setUpdateDate(tmp_ls.get(6).getTextExtractor().toString());
		}
		return zy_Info_List;
		/** end **/
	}
	
	/**   
	* @Title: readXYTS    
	* @Description: 读取信息提示   
	* @param url
	* @return XYTS_Info
	*/
	public static XYTS_Info readXYTS(String url)
	{
		List<Element> content_Element_List = readUrl(url);
		
		/** start **/
		XYTS_Info xyts_Info = new XYTS_Info();
		Element po_Element = content_Element_List.get(Constants.PBOC_XYTS_INFO_TABLEINDEX).getAllElements(HTMLElementName.TR).get(1);
		/*<td width="10%" class="tdStyle"> 
            <div class=high align=center><font color=#0066cc><strong>0</strong></font></div>
          </td>
          <td width="10%" class="tdStyle"> 
            <div class=high align=center><font color=#0066cc>5</font></div>
          </td>
          
          <td width="13%" class="tdStyle"> 
            <div class=high align=center><font color=#0066cc>2010.05</font></div>
          </td>
         <td width="10%" class="tdStyle"> 
            <div class=high align=center><font color=#0066cc>5</font></div>
          </td>
          
          <td width="13%" class="tdStyle"> 
            <div class=high align=center><font color=#0066cc>2008.03</font></div>
          </td>
         <td width="10%" class="tdStyle"> 
            <div class=high align=center><font color=#0066cc>0</font></div>
          </td>
         
          <td width="14%" class="tdStyle"> 
            <div class=high align=center><font color=#0066cc>--</font></div>
          </td>
         <td width="10%" class="tdStyle"> 
            <div class=high align=center><font color=#0066cc>0</font></div>
          </td>
          <td width="10%" class="tdStyle"> 
            <div class=high align=center><font color=#0066cc>0</font></div>
          </td>*/
		List<Element> xyts_Info_List = po_Element.getAllElements(HTMLElementName.TD);
		xyts_Info.setHousingLoanNum(xyts_Info_List.get(0).getTextExtractor().toString());
		xyts_Info.setOtherLoanNum(xyts_Info_List.get(1).getTextExtractor().toString());
		xyts_Info.setFirstLoanDate(xyts_Info_List.get(2).getTextExtractor().toString());
		xyts_Info.setCreditCardNum(xyts_Info_List.get(3).getTextExtractor().toString());
		xyts_Info.setFirstCreditCardDate(xyts_Info_List.get(4).getTextExtractor().toString());
		xyts_Info.setQuasiCreditCardNum(xyts_Info_List.get(5).getTextExtractor().toString());
		xyts_Info.setFirstQuasiCreditCardDate(xyts_Info_List.get(6).getTextExtractor().toString());
		xyts_Info.setDeclareNum(xyts_Info_List.get(7).getTextExtractor().toString());
		xyts_Info.setMarkNum(xyts_Info_List.get(8).getTextExtractor().toString());
		
		return xyts_Info;
		/** end **/
	}
	
	/**   
	* @Title: readYQ    
	* @Description: 读取逾期信息  
	* @param url
	* @return YQ_Info
	*/
	public static YQ_Info readYQ(String url)
	{
		List<Element> content_Element_List = readUrl(url);
		
		/** start **/
		YQ_Info yq_Info = new YQ_Info();
		Element yq_Element = content_Element_List.get(Constants.PBOC_YQ_INFO_TABLEINDEX).getAllElements(HTMLElementName.TR).get(2);
		/*<td class="tdStyle" colspan=1> 
              <div class=high align=center><font color=#0066cc><span class=high>2</span></font></div>
            </td>
            <td class="tdStyle" colspan=1> 
              <div class=high align=center><font color=#0066cc><span class=high>23</span></font></div>
            </td>
            <td class="tdStyle" colspan=1> 
              <div class=high align=center><font color=#0066cc><span class=high>141,351</span></font></div>
            </td>
            <td class="tdStyle" colspan=1> 
              <div class=high align=center><font color=#0066cc><span class=high>7</span></font></div>
            </td>
             
            
            <td class="tdStyle" colspan=1> 
              <div class=high align=center><font color=#0066cc><span class=high>1</span></font></div>
            </td>
            <td class="tdStyle" colspan=1> 
              <div class=high align=center><font color=#0066cc><span class=high>29</span></font></div>
            </td>
            <td class="tdStyle" colspan=1> 
              <div class=high align=center><font color=#0066cc><span class=high>3,606</span></font></div>
            </td>
            <td class="tdStyle" colspan=1> 
              <div class=high align=center><font color=#0066cc><span class=high>7</span></font></div>
            </td>
             
            
            <td class="tdStyle" colspan=1> 
              <div class=high align=center><font color=#0066cc><span class=high>0</span></font></div>
            </td>
            <td class="tdStyle" colspan=1> 
              <div class=high align=center><font color=#0066cc><span class=high>0</span></font></div>
            </td>
            <td class="tdStyle" colspan=1> 
              <div class=high align=center><font color=#0066cc><span class=high>0</span></font></div>
            </td>
            <td class="tdStyle" colspan=1> 
              <div class=high align=center><font color=#0066cc><span class=high>0</span></font></div>
            </td>*/
		List<Element> yq_Info_List = yq_Element.getAllElements(HTMLElementName.TD);
		yq_Info.setLoanOverdueNum(yq_Info_List.get(0).getTextExtractor().toString());
		yq_Info.setLoanOverdueMonth(yq_Info_List.get(1).getTextExtractor().toString());
		yq_Info.setLoanOverdueAmountTotal(yq_Info_List.get(2).getTextExtractor().toString());
		yq_Info.setLoanOverdueLongestMonth(yq_Info_List.get(3).getTextExtractor().toString());
		
		yq_Info.setCreditCardNum(yq_Info_List.get(4).getTextExtractor().toString());
		yq_Info.setCreditCardMonth(yq_Info_List.get(5).getTextExtractor().toString());
		yq_Info.setCreditCardAmountTotal(yq_Info_List.get(6).getTextExtractor().toString());
		yq_Info.setCreditCardLongestMonth(yq_Info_List.get(7).getTextExtractor().toString());
		
		yq_Info.setQuasiCreditCardNum(yq_Info_List.get(8).getTextExtractor().toString());
		yq_Info.setQuasiCreditCardMonth(yq_Info_List.get(9).getTextExtractor().toString());
		yq_Info.setQuasiCreditCardAmountTotal(yq_Info_List.get(10).getTextExtractor().toString());
		yq_Info.setQuasiCreditCardLongestMon(yq_Info_List.get(11).getTextExtractor().toString());
		return yq_Info;
		/** end **/
	}

	/**   
	* @Title: readWJQDK    
	* @Description: 读取未结清贷款      
	* @param url
	* @Return WJQDK_Info
	*/
	public static WJQDK_Info readWJQDK(String url)
	{
		List<Element> content_Element_List = readUrl(url);
		
		/** start **/
		WJQDK_Info wjqdk_Info = new WJQDK_Info();
		Element wjqdk_Element = content_Element_List.get(Constants.PBOC_WJQDK_INFO_TABLEINDEX).getAllElements(HTMLElementName.TR).get(1);
		/* <td class="tdStyle" width=25%>
            <div class=high align=center><font color=#0066cc>2</font></div>
          </td>
          <td class="tdStyle" width=15%>
            <div class=high align=center><font color=#0066cc>2</font></div>
          </td>
          <td class="tdStyle" width=10%>
            <div class=high align=center><font color=#0066cc>2</font></div>
          </td>
          <td class="tdStyle" width=15%>
            <div class=high align=center><font color=#0066cc>400,000</font></div>
          </td>
          <td class="tdStyle" width=10%>
            <div class=high align=center><font color=#0066cc>100,000</font></div>
          </td>
          <td class="tdStyle" width=25%>
            <div class=high align=center><font color=#0066cc>147,279</font></div>
          </td>*/
		List<Element> wjqdk_Info_List = wjqdk_Element.getAllElements(HTMLElementName.TD);
		wjqdk_Info.setLegalInstitutionOrgNum(wjqdk_Info_List.get(0).getTextExtractor().toString());
		wjqdk_Info.setLegalOrgNum(wjqdk_Info_List.get(1).getTextExtractor().toString());
		wjqdk_Info.setNum(wjqdk_Info_List.get(2).getTextExtractor().toString());
		wjqdk_Info.setAmountTotal(wjqdk_Info_List.get(3).getTextExtractor().toString());
		wjqdk_Info.setRemaining(wjqdk_Info_List.get(4).getTextExtractor().toString());
		wjqdk_Info.setAvgPer6month(wjqdk_Info_List.get(5).getTextExtractor().toString());
		return wjqdk_Info;
		/** end **/
	}
	
	/**   
	* @Title: readWXHDJK    
	* @Description: 读取未销户贷记卡信息   
	* @param url
	* @return WXHDJK_Info
	*/
	public static WXHDJK_Info readWXHDJK(String url)
	{
		List<Element> content_Element_List = readUrl(url);
		
		/** start **/
		WXHDJK_Info wxhdjk_Info = new WXHDJK_Info();
		Element wxhdjk_Element = content_Element_List.get(Constants.PBOC_WXHDJK_INFO_TABLEINDEX).getAllElements(HTMLElementName.TR).get(1);
		/* <td class="tdStyle" width="12%"> 
              <div class=high align=center><font color=#0066cc><span class=high>2</span></font></div>
            </td>
            <td class="tdStyle" width="12%">  
              <div class=high align=center><font color=#0066cc><span class=high>2</span></font></div>
            </td>
            <td class="tdStyle" width="12%">  
              <div class=high align=center><font color=#0066cc><span class=high>3</span></font></div>
            </td>
            <td class="tdStyle" width="12%">  
              <div class=high align=center><font color=#0066cc><span class=high>5,000</span></font></div>
            </td>
            <td class="tdStyle" width="12%">  
              <div class=high align=center><font color=#0066cc><font color=#0066cc>3,000</span></div>
            </td>
            <td class="tdStyle" width="12%">  
              <div class=high align=center><font color=#0066cc><span class=high>2,000</font></div>
            </td>
            <td class="tdStyle" width="14%"> 
              <div class=high align=center><font color=#0066cc><font color=#0066cc>3,659</span></div>
            </td>
            <td class="tdStyle" width="14%"> 
              <div class=high align=center><font color=#0066cc><span class=high>3,530</font></div>
            </td>*/
		List<Element> wjqdk_Info_List = wxhdjk_Element.getAllElements(HTMLElementName.TD);
		wxhdjk_Info.setInstitutionOrgNum(wjqdk_Info_List.get(0).getTextExtractor().toString());
		wxhdjk_Info.setOrgNum(wjqdk_Info_List.get(1).getTextExtractor().toString());
		wxhdjk_Info.setAccountNum(wjqdk_Info_List.get(2).getTextExtractor().toString());
		wxhdjk_Info.setAwardingTotal(wjqdk_Info_List.get(3).getTextExtractor().toString());
		wxhdjk_Info.setMaxAwarding(wjqdk_Info_List.get(4).getTextExtractor().toString());
		wxhdjk_Info.setMinAwarding(wjqdk_Info_List.get(5).getTextExtractor().toString());
		wxhdjk_Info.setUsed(wjqdk_Info_List.get(6).getTextExtractor().toString());
		wxhdjk_Info.setAvgUsed(wjqdk_Info_List.get(7).getTextExtractor().toString());
		
		return wxhdjk_Info;
		/** end **/
	}
	
	/**   
	* @Title: readDWDB    
	* @Description: 读取对外担保信息    
	* @param url
	* @return DWDB_Info
	*/
	public static DWDB_Info readDWDB(String url)
	{
		List<Element> content_Element_List = readUrl(url);
		
		/** start **/
		DWDB_Info dwdb_Info = new DWDB_Info();
		Element dwdb_Element = content_Element_List.get(Constants.PBOC_DWDB_INFO_TABLEINDEX).getAllElements(HTMLElementName.TR).get(1);
		/* <td class="tdStyle" width="30%"> 
              <div class=high align=center><font color=#0066cc><span class=high>1</span></font></div>
            </td>
            <td class="tdStyle" width="35%"> 
              <div class=high align=center><font color=#0066cc><span class=high>300,000</span></font></div>
            </td>
            <td class="tdStyle" width="35%"> 
              <div class=high align=center><font color=#0066cc><span class=high>0</span></font></div>
            </td>*/
		List<Element> dwdb_Info_List = dwdb_Element.getAllElements(HTMLElementName.TD);
		dwdb_Info.setNum(dwdb_Info_List.get(0).getTextExtractor().toString());
		dwdb_Info.setAmount(dwdb_Info_List.get(1).getTextExtractor().toString());
		dwdb_Info.setCapital(dwdb_Info_List.get(2).getTextExtractor().toString());
		
		return dwdb_Info;
		/** end **/
	}
	
	/**
	 * 查询该用户查询历史
	* @Title readQuery
	* @Description TODO
	* @param @param url
	* @param @return
	* @return Query_Info
	*/
	public static Query_Info readQuery(String url)
	{
		List<Element> content_Element_List = readUrl(url);
		
		/** start **/
		Query_Info query_Info = new Query_Info();
		Element query_Element = content_Element_List.get(Constants.PBOC_Query_INFO_TABLEINDEX).getAllElements(HTMLElementName.TR).get(2);
		/* <td class="tdStyle" colspan=2>  
              <div class=high align=center><font color=#0066cc><span class=high>0</span></font></div>
            </td>
            <td class="tdStyle" colspan=2>  
              <div class=high align=center><font color=#0066cc><span class=high>0</span></font></div>
            </td>
            <td class="tdStyle" colspan=2>  
              <div class=high align=center><font color=#0066cc><span class=high>0</span></font></div>
            </td>
            <td class="tdStyle" colspan=2> 
              <div class=high align=center><font color=#0066cc><span class=high>0</span></font></div>
            </td>
            <td class="tdStyle" colspan=1> 
              <div class=high align=center><font color=#0066cc><span class=high>2</span></font></div>
            </td>
            <td class="tdStyle" colspan=1> 
              <div class=high align=center><font color=#0066cc><span class=high>0</span></font></div>
            </td>
            <td class="tdStyle" colspan=2> 
              <div class=high align=center><font color=#0066cc><span class=high>0</span></font></div>
            </td>*/
		List<Element> query_Info_List = query_Element.getAllElements(HTMLElementName.TD);
		query_Info.setLoanApproval1(query_Info_List.get(0).getTextExtractor().toString());
		query_Info.setCreitApproval1(query_Info_List.get(1).getTextExtractor().toString());
		query_Info.setLoanApproval2(query_Info_List.get(2).getTextExtractor().toString());
		query_Info.setCreitApproval2(query_Info_List.get(3).getTextExtractor().toString());
		query_Info.setLoanManagement(query_Info_List.get(4).getTextExtractor().toString());
		query_Info.setGuaranteeExamination(query_Info_List.get(5).getTextExtractor().toString());
		query_Info.setRealnameReview(query_Info_List.get(6).getTextExtractor().toString());
		
		return query_Info;
		/** end **/
	}
	
	public static void ParserReporter(String url)
	{
		//readDWDB("file:///C:/Users/Administrator/Desktop/人行征信抓报告/蒋建峰.html");
		readQuery(url);
	}
}


