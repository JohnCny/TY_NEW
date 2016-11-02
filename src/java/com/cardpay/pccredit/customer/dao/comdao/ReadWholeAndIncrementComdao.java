package com.cardpay.pccredit.customer.dao.comdao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.tools.UpdateCustomerTool;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;


@Service
public class ReadWholeAndIncrementComdao {
	public Logger log = Logger.getLogger(ReadWholeAndIncrementComdao.class);
	@Autowired
	private CommonDao commonDao;

	@Autowired
	private JdbcTemplate jdbcTemplate;  
	
	//参数字典基本信息表
	public void insertParamClass(List<Map<String, Object>> list){
		 final List<Map<String, Object>> shopsList = list;
	     String sql = "  insert into T_PARAM_CLASS     (id,                "+
		    		  "                                name,               "+
		    		  "                                display,            "+
		    		  "                                type,               "+
		    		  "                                scope,              "+
		    		  "                                tree,               "+
		    		  "                                codegenerator,      "+
		    		  "                                codelength,         "+
		    		  "                                codeautogenerate,   "+
		    		  "                                description,        "+
		    		  "                                namecaption,        "+
		    		  "                                codecaption,        "+
		    		  "                                briefnamecaption,   "+
		    		  "                                classuri,           "+
		    		  "                                repositoryprocessor,"+
		    		  "                                displayuri,CREATE_TIME)         "+
		    		  "  values(?,                                         "+
		    		  "  			 ?,                                    "+
		    		  "  			 ?,                                    "+
		    		  "  			 ?,                                    "+
		    		  "  			 ?,                                    "+
		    		  "  			 ?,                                    "+
		    		  "  			 ?,                                    "+
		    		  "  			 ?,                                    "+
		    		  "  			 ?,                                    "+
		    		  "  			 ?,                                    "+
		    		  "  			 ?,                                    "+
		    		  "  			 ?,                                    "+
		    		  "  			 ?,                                    "+
		    		  "  			 ?,                                    "+
		    		  "  			 ?,                                    "+
		    		  "  			 ?,?)                                    ";
	      jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){
	        public void setValues(PreparedStatement ps,int i)throws SQLException
	           {
	        	ps.setString(1, ((Map<String, Object>)shopsList.get(i)).get("id").toString());
	        	ps.setString(2, ((Map<String, Object>)shopsList.get(i)).get("name").toString());
	        	ps.setString(3, ((Map<String, Object>)shopsList.get(i)).get("display").toString());
	        	ps.setString(4, ((Map<String, Object>)shopsList.get(i)).get("type").toString());
	        	ps.setString(5, ((Map<String, Object>)shopsList.get(i)).get("scope").toString());
	        	ps.setString(6, ((Map<String, Object>)shopsList.get(i)).get("tree").toString());
	        	ps.setString(7, ((Map<String, Object>)shopsList.get(i)).get("codegenerator").toString());
	        	ps.setString(8, ((Map<String, Object>)shopsList.get(i)).get("codelength").toString());
	        	ps.setString(9, ((Map<String, Object>)shopsList.get(i)).get("codeautogenerate").toString());
	        	ps.setString(10, ((Map<String, Object>)shopsList.get(i)).get("description").toString());
	        	ps.setString(11, ((Map<String, Object>)shopsList.get(i)).get("namecaption").toString());
	        	ps.setString(12, ((Map<String, Object>)shopsList.get(i)).get("codecaption").toString());
	        	ps.setString(13, ((Map<String, Object>)shopsList.get(i)).get("briefnamecaption").toString());
	        	ps.setString(14, ((Map<String, Object>)shopsList.get(i)).get("classuri").toString());
	        	ps.setString(15, ((Map<String, Object>)shopsList.get(i)).get("repositoryprocessor").toString());
	        	ps.setString(16, ((Map<String, Object>)shopsList.get(i)).get("displayuri").toString());
	        	ps.setString(17, ((Map<String, Object>)shopsList.get(i)).get("createTime").toString());
	           }
	           public int getBatchSize()
	           {
	            return shopsList.size();
	           }
	     });
	}
	
	//参数字典列表
	public void insertParamParm(List<Map<String, Object>> list){
		final List<Map<String, Object>> shopsList = list;
	    String sql = " insert into T_PARAM_PARAM (id,          	  "+
		    		 "                               parentid,    "+
		    		 "                               classid,     "+
		    		 "                               groupid,     "+
		    		 "                               code,        "+
		    		 "                               name,        "+
		    		 "                               valid,       "+
		    		 "                               description, "+
		    		 "                               briefname,   "+
		    		 "                               updatedate,  "+
		    		 "                               userid,      "+
		    		 "                               displayorder,CREATE_TIME)"+  
		    		 " values( ?,                                 "+      
		    		 " 			  ?,                              "+        
		    		 " 			  ?,                              "+        
		    		 " 			  ?,                              "+        
		    		 " 			  ?,                              "+        
		    		 " 			  ?,                              "+        
		    		 " 			  ?,                              "+        
		    		 " 			  ?,                              "+        
		    		 " 			  ?,                              "+        
		    		 " 			  ?,                              "+        
		    		 " 			  ?,                              "+        
		    		 " 			  ?,?)                              ";  
	      jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){
	        public void setValues(PreparedStatement ps,int i)throws SQLException
	           {
	        	ps.setString(1, ((Map<String, Object>)shopsList.get(i)).get("id").toString()); 
    			ps.setString(2, ((Map<String, Object>)shopsList.get(i)).get("parentid").toString()); 
    			ps.setString(3, ((Map<String, Object>)shopsList.get(i)).get("classid").toString());	
    			ps.setString(4, ((Map<String, Object>)shopsList.get(i)).get("groupid").toString()); 
    			ps.setString(5, ((Map<String, Object>)shopsList.get(i)).get("code").toString()); 
    			ps.setString(6, ((Map<String, Object>)shopsList.get(i)).get("name").toString()); 
    			ps.setString(7, ((Map<String, Object>)shopsList.get(i)).get("valid").toString()); 
    			ps.setString(8, ((Map<String, Object>)shopsList.get(i)).get("description").toString()); 
    			ps.setString(9, ((Map<String, Object>)shopsList.get(i)).get("briefname").toString()); 
    			ps.setString(10, ((Map<String, Object>)shopsList.get(i)).get("updatedate").toString());
    			ps.setString(11, ((Map<String, Object>)shopsList.get(i)).get("userid").toString());
    			ps.setString(12, ((Map<String, Object>)shopsList.get(i)).get("displayorder").toString());
    			ps.setString(13, ((Map<String, Object>)shopsList.get(i)).get("createTime").toString());
	           }
	           public int getBatchSize()
	           {
	            return shopsList.size();
	           }
	     });
	}
	
	
	//客户类型表
	public void insertParamType(List<Map<String, Object>> list){
		final List<Map<String, Object>> shopsList = list;
	   String sql = " insert into T_PARTY_TYPE (id,                 "+
		    		"                           parentid,           "+
		    		"                           name,               "+
		    		"                           displayname,        "+
		    		"                           description,        "+
		    		"                           displayuri,         "+
		    		"                           codepolicymanager,  "+
		    		"                           repositorytable,    "+
		    		"                           createduser,        "+
		    		"                           createdtime,        "+
		    		"                           modifieduser,       "+
		    		"                           modifiedtime,       "+
		    		"                 			groupid,CREATE_TIME)           "+                       
		    		"     values(?,                                "+   
		    		"     			  ?,                           "+     
		    		"     			  ?,                           "+     
		    		"     			  ?,                           "+     
		    		"     			  ?,                           "+     
		    		"     			  ?,                           "+     
		    		"     			  ?,                           "+     
		    		"     			  ?,                           "+     
		    		"     			  ?,                           "+     
		    		"     			  ?,                           "+     
		    		"     			  ?,                           "+  
		    		" 		 		  ?,                           "+
		    		" 		 		  ?,?)                           ";         
	      jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){
	        public void setValues(PreparedStatement ps,int i)throws SQLException
	           {
	        	 ps.setString(1, ((Map<String, Object>)shopsList.get(i)).get("id").toString());  
	        	 ps.setString(2, ((Map<String, Object>)shopsList.get(i)).get("parentid").toString());  
	        	 ps.setString(3, ((Map<String, Object>)shopsList.get(i)).get("name").toString());  
	        	 ps.setString(4, ((Map<String, Object>)shopsList.get(i)).get("displayname").toString());  
	        	 ps.setString(5, ((Map<String, Object>)shopsList.get(i)).get("description").toString());  
	        	 ps.setString(6, ((Map<String, Object>)shopsList.get(i)).get("displayuri").toString());  
	        	 ps.setString(7, ((Map<String, Object>)shopsList.get(i)).get("codepolicymanager").toString());  
	        	 ps.setString(8, ((Map<String, Object>)shopsList.get(i)).get("repositorytable").toString());  
	        	 ps.setString(9, ((Map<String, Object>)shopsList.get(i)).get("createduser").toString());  
	        	 ps.setString(10, ((Map<String, Object>)shopsList.get(i)).get("createdtime").toString());  
	        	 ps.setString(11, ((Map<String, Object>)shopsList.get(i)).get("modifieduser").toString());  
	        	 ps.setString(12, ((Map<String, Object>)shopsList.get(i)).get("modifiedtime").toString());  
	        	 ps.setString(13, ((Map<String, Object>)shopsList.get(i)).get("groupid").toString());
	        	 ps.setString(14, ((Map<String, Object>)shopsList.get(i)).get("createTime").toString());
	           }
	           public int getBatchSize()
	           {
	            return shopsList.size();
	           }
	     });
	}
	
	
	
	//机构表
	public void insertRbacGroup(List<Map<String, Object>> list){
		final List<Map<String, Object>> shopsList = list;
	  String sql = "insert into T_RBAC_GROUP (id,                 "+
				   "                          parentid,           "+
				   "                          code,               "+
				   "                          name,               "+
				   "                          briefname,          "+
				   "                          nodename,           "+
				   "                          description,        "+
				   "                          type,               "+
				   "                          valid,              "+
				   "                          bizparentid,        "+
				   "                          modifieduserid,     "+
				   "                          createduserid,      "+
				   "                		  createdtime,        "+                    
				   "               			  modifiedtime,       "+                  
				   "               			  canloancert,        "+                   
				   "               			  brhfinger,          "+                   
				   "               			  ifcorpcredit,       "+                   
				   "               			  address,            "+                   
				   "               			  telephone,          "+                   
				   "               			  fax,                "+                   
				   "               			  zipcode,            "+                   
				   "               		      contactperson,      "+                   
				   "               			  districtcity,       "+                   
				   "               			  districtcounty,     "+                
				   "		            	  citycode,           "+                
				   "		            	  sorttype,           "+                         
				   "		                  grouplimit,         "+                 
				   "		                  iscombine,          "+                 
				   "		                  paymodelimit,       "+                 
				   "                          loancentercode,     "+
				   "                          pricipal,           "+
				   "                          groupsinglelimit,   "+
				   "                          lcunoinlimit,       "+
				   "                          fnonglimint,        "+
				   "                          ifnsh,              "+
				   "                          callilogflag,CREATE_TIME)       "+
				   "  values(?,                                   "+
				   "  				?,                            "+
				   "  				?,                            "+
				   "  				?,                            "+
				   "  				?,                            "+
				   "  				?,                            "+
				   "         ?,                                   "+
				   "         ?,                                   "+
				   "         ?,                                   "+
				   "         ?,                                   "+
				   "         ?,                                   "+
				   "         ?,                                   "+
				   "         ?,                                   "+
				   "         ?,                                   "+
				   "         ?,                                   "+
				   "         ?,                                   "+
				   "         ?,                                   "+
				   "         ?,                                   "+
				   "         ?,                                   "+
				   "         ?,                                   "+
				   "         ?,                                   "+
				   "         ?,                                   "+
				   "         ?,                                   "+
				   "         ?,                                   "+
				   "         ?,                                   "+
				   "         ?,                                   "+
				   "         ?,                                   "+
				   "         ?,                                   "+
				   "         ?,                                   "+
				   "         ?,                                   "+
				   "         ?,                                   "+
				   "         ?,                                   "+
				   "         ?,                                   "+
				   "         ?,                                   "+
				   "         ?,                                   "+
				   "         ?,?)                                   ";      
	      jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){
	        public void setValues(PreparedStatement ps,int i)throws SQLException
	           {
	        	ps.setString(1, ((Map<String, Object>)shopsList.get(i)).get("id").toString());              
	        	ps.setString(2, ((Map<String, Object>)shopsList.get(i)).get("parentid").toString());         
	        	ps.setString(3, ((Map<String, Object>)shopsList.get(i)).get("code").toString());             
	        	ps.setString(4, ((Map<String, Object>)shopsList.get(i)).get("name").toString());
	        	ps.setString(5, ((Map<String, Object>)shopsList.get(i)).get("briefname").toString());
	        	ps.setString(6, ((Map<String, Object>)shopsList.get(i)).get("nodename").toString());
	        	ps.setString(7, ((Map<String, Object>)shopsList.get(i)).get("description").toString());
	        	ps.setString(8, ((Map<String, Object>)shopsList.get(i)).get("type").toString());
	        	ps.setString(9, ((Map<String, Object>)shopsList.get(i)).get("valid").toString());
	        	ps.setString(10, ((Map<String, Object>)shopsList.get(i)).get("bizparentid").toString());
	        	ps.setString(11, ((Map<String, Object>)shopsList.get(i)).get("modifieduserid").toString());
	        	ps.setString(12, ((Map<String, Object>)shopsList.get(i)).get("createduserid").toString());
	        	ps.setString(13, ((Map<String, Object>)shopsList.get(i)).get("createdtime").toString());
	        	ps.setString(14, ((Map<String, Object>)shopsList.get(i)).get("modifiedtime").toString());
	        	ps.setString(15, ((Map<String, Object>)shopsList.get(i)).get("canloancert").toString());
	        	ps.setString(16, ((Map<String, Object>)shopsList.get(i)).get("brhfinger").toString());
	        	ps.setString(17, ((Map<String, Object>)shopsList.get(i)).get("ifcorpcredit").toString());
	        	ps.setString(18, ((Map<String, Object>)shopsList.get(i)).get("address").toString());
	        	ps.setString(19, ((Map<String, Object>)shopsList.get(i)).get("telephone").toString());
	        	ps.setString(20, ((Map<String, Object>)shopsList.get(i)).get("fax").toString());
	        	ps.setString(21, ((Map<String, Object>)shopsList.get(i)).get("zipcode").toString());
	        	ps.setString(22, ((Map<String, Object>)shopsList.get(i)).get("contactperson").toString());
	        	ps.setString(23, ((Map<String, Object>)shopsList.get(i)).get("districtcity").toString());
	        	ps.setString(24, ((Map<String, Object>)shopsList.get(i)).get("districtcounty").toString());
	        	ps.setString(25, ((Map<String, Object>)shopsList.get(i)).get("citycode").toString());
	        	ps.setString(26, ((Map<String, Object>)shopsList.get(i)).get("sorttype").toString());
	        	ps.setString(27, ((Map<String, Object>)shopsList.get(i)).get("grouplimit").toString());
	        	ps.setString(28, ((Map<String, Object>)shopsList.get(i)).get("iscombine").toString());
	        	ps.setString(29, ((Map<String, Object>)shopsList.get(i)).get("paymodelimit").toString());
	        	ps.setString(30, ((Map<String, Object>)shopsList.get(i)).get("loancentercode").toString());
	        	ps.setString(31, ((Map<String, Object>)shopsList.get(i)).get("pricipal").toString());
	        	ps.setString(32, ((Map<String, Object>)shopsList.get(i)).get("groupsinglelimit").toString());
	        	ps.setString(33, ((Map<String, Object>)shopsList.get(i)).get("lcunoinlimit").toString());
	        	ps.setString(34, ((Map<String, Object>)shopsList.get(i)).get("fnonglimint").toString());
	        	ps.setString(35, ((Map<String, Object>)shopsList.get(i)).get("ifnsh").toString());
	        	ps.setString(36, ((Map<String, Object>)shopsList.get(i)).get("callilogflag").toString());
	        	ps.setString(37, ((Map<String, Object>)shopsList.get(i)).get("createTime").toString());
	           }
	           public int getBatchSize()
	           {
	            return shopsList.size();
	           }
	     });
	}
	
	//用户表
	public void insertRbacUser(List<Map<String, Object>> list){
		final List<Map<String, Object>> shopsList = list;
	 String sql = "insert into T_RBAC_USER (ID,                  "+            
				  "                          LOGINNAME,           "+            
				  "                          PASSWORD,            "+            
				  "                          NAME,                "+            
				  "                          GROUPID,             "+            
				  "                          DESCRIPTION,         "+            
				  "                          TYPE,                "+            
				  "                          VALID,               "+            
				  "                          LOCKED,              "+           
				  "                          MODIFIEDTIME,        "+            
				  "                          CREATEDTIME,         "+     
				  "                          CREATEDUSERID,       "+     
				  "                		     MODIFIEDUSERID,      "+                     
				  "               			 COMMITTEE,           "+                  
				  "               			 IDCARDNO,            "+                   
				  "               			 GROUPMODIFIEDDATE,   "+                   
				  "               			 PWDMODIFIEDDATE,CREATE_TIME)     "+
				  " values(?,                                     "+
				  " 				?,                            "+
				  " 				?,                            "+
				  " 				?,                            "+
				  " 				?,                            "+
				  " 				?,                            "+
				  " 				?,                            "+
				  "        ?,       					          "+                    
				  "        ?,       					          "+
				  "        ?,                                     "+
				  "        ?,       					          "+
				  "        ?,       					          "+
				  "        ?,       					          "+
				  "        ?,       					          "+
				  "        ?,                                     "+
				  "        ?,                                     "+
				  "        ?,?)       					          ";
	      jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){
	        public void setValues(PreparedStatement ps,int i)throws SQLException
	           {
	        	 ps.setString(1, ((Map<String, Object>)shopsList.get(i)).get("id").toString());              
	        	 ps.setString(2, ((Map<String, Object>)shopsList.get(i)).get("loginname").toString());       
	        	 ps.setString(3, ((Map<String, Object>)shopsList.get(i)).get("password").toString());        
	        	 ps.setString(4, ((Map<String, Object>)shopsList.get(i)).get("name").toString());            
	        	 ps.setString(5, ((Map<String, Object>)shopsList.get(i)).get("groupid").toString());         
	        	 ps.setString(6, ((Map<String, Object>)shopsList.get(i)).get("description").toString());     
	        	 ps.setString(7, ((Map<String, Object>)shopsList.get(i)).get("type").toString());            
	        	 ps.setString(8, ((Map<String, Object>)shopsList.get(i)).get("valid").toString());           
	        	 ps.setString(9, ((Map<String, Object>)shopsList.get(i)).get("locked").toString());          
	        	 ps.setString(10, ((Map<String, Object>)shopsList.get(i)).get("modifiedtime").toString());   
	        	 ps.setString(11, ((Map<String, Object>)shopsList.get(i)).get("createdtime").toString());    
	        	 ps.setString(12, ((Map<String, Object>)shopsList.get(i)).get("createduserid").toString());  
	        	 ps.setString(13, ((Map<String, Object>)shopsList.get(i)).get("modifieduserid").toString()); 
	        	 ps.setString(14, ((Map<String, Object>)shopsList.get(i)).get("committee").toString());      
	        	 ps.setString(15, ((Map<String, Object>)shopsList.get(i)).get("idcardno").toString());       
	        	 ps.setString(16, ((Map<String, Object>)shopsList.get(i)).get("groupmodifieddate").toString());
	        	 ps.setString(17, ((Map<String, Object>)shopsList.get(i)).get("pwdmodifieddate").toString());
	        	 ps.setString(18, ((Map<String, Object>)shopsList.get(i)).get("createTime").toString());
	           }
	           public int getBatchSize()
	           {
	            return shopsList.size();
	           }
	     });
	}
	
//=========================================【全量end】==================================================================================================//
	//GC担保信息表 增量
	public void insertGCASSUREMAIN(List<Map<String, Object>> list){
		final List<Map<String, Object>> shopsList = list;
	   String sql = "insert into T_GCASSUREMAIN (keycode,                       "+ 
					"                            keytype,                       "+
					"                            extendkeycode,                 "+
					"                            gcbusinesslevel,               "+
					"                            gcsyslevel,                    "+
					"                            upkeycode,                     "+
					"                            upkeytype,                     "+
					"                            upgcbusinesslevel,             "+
					"                            upgcsyslevel,                  "+
					"                            money,                         "+
					"                            currency,                      "+
					"                            startdate,                     "+
					"                		     enddate,                       "+         
					"               			 lastdate,                      "+      
					"               			 limit,                         "+       
					"               			 limitunit,                     "+       
					"               			 keyeffectedstate,              "+
					"                            keydatestate,                  "+
					"                            debtorcustid,                  "+
					"                            wealthcoowner,                 "+
					"                            guarmod,                       "+
					"                            guarbalance,                   "+
					"                            guarbasprice,                  "+
					"                            guarbasepricecur,              "+
					"  					         guarmaxprice,                  "+          
					"  					         guarmaxpricecur,               "+
					"                            guarbusitype,                  "+
					"  					         otherguarbusitype,             "+
					"  					         accessorystate,                "+
					"  					         intrstrate,                    "+
					"  					         intrstrateunit,                "+
					"                            espacc,                        "+
					"                            trnasinsuterm,                 "+
					"  					         counterclaimcond,              "+
					"				             disputedsolve,                 "+
					"		            		 arbitratedept,                 "+        
					"		                     contnum,                       "+
					"		                     otherpromise,                  "+
					"		                     comments,                      "+
					"                            evaluationorgcode,             "+
					"                            evaluateddate,                 "+
					"                            evaluationorgname,             "+
					"                            busimanager,                   "+
					"                            avoidassuredutycond,           "+
					"                            elseduticond,                  "+
					"                            assureduty,                    "+
					"                            passeddate,                    "+
					"                            estatehandovertype,            "+
					"                            otherpeoplename,               "+
					"                            editionno,                     "+
					"                            deptcode,                      "+
					"                            instcode,                      "+
					"                            instcitycode,                  "+
					"                            operator,                      "+
					"                            operdatetime,                  "+
					"                            istrans,CREATE_TIME)           "+
					"   values(?,                                               "+
					"   				?,                                      "+
					"   				?,                                      "+
					"   				?,                                      "+
					"   				?,                                      "+
					"   				?,                                      "+
					"          ?,                                               "+
					"          ?,                                               "+
					"          ?,                                               "+
					"          ?,                                               "+
					"          ?,                                               "+
					"          ?,                                               "+
					"          ?,                                               "+
					"          ?,                                               "+
					"          ?,                                               "+
					"          ?,                                               "+
					"          ?,                                               "+
					"          ?,                                               "+
					"          ?,                                               "+
					"          ?,                                               "+
					"          ?,                                               "+
					"          ?,                                               "+
					"          ?,                                               "+
					"          ?,                                               "+
					"          ?,                                               "+
					"          ?,                                               "+
					"          ?,                                               "+
					"          ?,                                               "+
					"          ?,                                               "+
					"          ?,                                               "+
					"          ?,                                               "+
					"          ?,                                               "+
					"          ?,                                               "+
					"          ?,                                               "+
					"          ?,                                               "+
					"          ?,                                               "+
					"          ?,                                               "+
					"          ?,                                               "+
					"          ?,                                               "+
					"          ?,                                               "+
					"          ?,                                               "+
					"          ?,                                               "+
					"          ?,                                               "+
					"          ?,                                               "+
					"          ?,                                               "+
					"          ?,                                               "+
					"          ?,                                               "+
					"          ?,                                               "+
					"          ?,                                               "+
					"          ?,                                               "+
					"          ?,                                               "+
					"          ?,                                               "+
					"          ?,                                               "+
					"          ?,                                               "+
					"          ?,                                               "+
					"          ?,?)                                             ";
	      jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){
	        public void setValues(PreparedStatement ps,int i)throws SQLException
	           {
	        	ps.setString(1, ((Map<String, Object>)shopsList.get(i)).get("keycode").toString());																													
	            ps.setString(2, ((Map<String, Object>)shopsList.get(i)).get("keytype").toString());
	            ps.setString(3, ((Map<String, Object>)shopsList.get(i)).get("extendkeycode").toString());
	            ps.setString(4, ((Map<String, Object>)shopsList.get(i)).get("gcbusinesslevel").toString());
	            ps.setString(5, ((Map<String, Object>)shopsList.get(i)).get("gcsyslevel").toString());
	            ps.setString(6, ((Map<String, Object>)shopsList.get(i)).get("upkeycode").toString());
	            ps.setString(7, ((Map<String, Object>)shopsList.get(i)).get("upkeytype").toString());
	            ps.setString(8, ((Map<String, Object>)shopsList.get(i)).get("upgcbusinesslevel").toString());
	            ps.setString(9, ((Map<String, Object>)shopsList.get(i)).get("upgcsyslevel").toString());
	            ps.setString(10, ((Map<String, Object>)shopsList.get(i)).get("money").toString());
	            ps.setString(11, ((Map<String, Object>)shopsList.get(i)).get("currency").toString());
	            ps.setString(12, ((Map<String, Object>)shopsList.get(i)).get("startdate").toString());
	            ps.setString(13, ((Map<String, Object>)shopsList.get(i)).get("enddate").toString());
	            ps.setString(14, ((Map<String, Object>)shopsList.get(i)).get("lastdate").toString());
	            ps.setString(15, ((Map<String, Object>)shopsList.get(i)).get("limit").toString());
	            ps.setString(16, ((Map<String, Object>)shopsList.get(i)).get("limitunit").toString());
	            ps.setString(17, ((Map<String, Object>)shopsList.get(i)).get("keyeffectedstate").toString());
	            ps.setString(18, ((Map<String, Object>)shopsList.get(i)).get("keydatestate").toString());
	            ps.setString(19, ((Map<String, Object>)shopsList.get(i)).get("debtorcustid").toString());
	            ps.setString(20, ((Map<String, Object>)shopsList.get(i)).get("wealthcoowner").toString());
	            ps.setString(21, ((Map<String, Object>)shopsList.get(i)).get("guarmod").toString());
	            ps.setString(22, ((Map<String, Object>)shopsList.get(i)).get("guarbalance").toString());
	            ps.setString(23, ((Map<String, Object>)shopsList.get(i)).get("guarbasprice").toString());
	            ps.setString(24, ((Map<String, Object>)shopsList.get(i)).get("guarbasepricecur").toString());
	            ps.setString(25, ((Map<String, Object>)shopsList.get(i)).get("guarmaxprice").toString());
	            ps.setString(26, ((Map<String, Object>)shopsList.get(i)).get("guarmaxpricecur").toString());
	            ps.setString(27, ((Map<String, Object>)shopsList.get(i)).get("guarbusitype").toString());
	            ps.setString(28, ((Map<String, Object>)shopsList.get(i)).get("otherguarbusitype").toString());
	            ps.setString(29, ((Map<String, Object>)shopsList.get(i)).get("accessorystate").toString());
	            ps.setString(30, ((Map<String, Object>)shopsList.get(i)).get("intrstrate").toString());
	            ps.setString(31, ((Map<String, Object>)shopsList.get(i)).get("intrstrateunit").toString());
	            ps.setString(32, ((Map<String, Object>)shopsList.get(i)).get("espacc").toString());
	            ps.setString(33, ((Map<String, Object>)shopsList.get(i)).get("trnasinsuterm").toString());
	            ps.setString(34, ((Map<String, Object>)shopsList.get(i)).get("counterclaimcond").toString());
	            ps.setString(35, ((Map<String, Object>)shopsList.get(i)).get("disputedsolve").toString());
	            ps.setString(36, ((Map<String, Object>)shopsList.get(i)).get("arbitratedept").toString());
	            ps.setString(37, ((Map<String, Object>)shopsList.get(i)).get("contnum").toString());                                                                                                                         
	            ps.setString(38, ((Map<String, Object>)shopsList.get(i)).get("otherpromise").toString());
	            ps.setString(39, ((Map<String, Object>)shopsList.get(i)).get("comments").toString());
	            ps.setString(40, ((Map<String, Object>)shopsList.get(i)).get("evaluationorgcode").toString());
	            ps.setString(41, ((Map<String, Object>)shopsList.get(i)).get("evaluateddate").toString());
	            ps.setString(42, ((Map<String, Object>)shopsList.get(i)).get("evaluationorgname").toString());
	            ps.setString(43, ((Map<String, Object>)shopsList.get(i)).get("busimanager").toString());
	            ps.setString(44, ((Map<String, Object>)shopsList.get(i)).get("avoidassuredutycond").toString());
	            ps.setString(45, ((Map<String, Object>)shopsList.get(i)).get("elseduticond").toString());
	            ps.setString(46, ((Map<String, Object>)shopsList.get(i)).get("assureduty").toString());
	            ps.setString(47, ((Map<String, Object>)shopsList.get(i)).get("passeddate").toString());
	            ps.setString(48, ((Map<String, Object>)shopsList.get(i)).get("estatehandovertype").toString());
	            ps.setString(49, ((Map<String, Object>)shopsList.get(i)).get("otherpeoplename").toString());
	            ps.setString(50, ((Map<String, Object>)shopsList.get(i)).get("editionno").toString());
	            ps.setString(51, ((Map<String, Object>)shopsList.get(i)).get("deptcode").toString());
	            ps.setString(52, ((Map<String, Object>)shopsList.get(i)).get("instcode").toString());
	            ps.setString(53, ((Map<String, Object>)shopsList.get(i)).get("instcitycode").toString());
	            ps.setString(54, ((Map<String, Object>)shopsList.get(i)).get("operator").toString());
	            ps.setString(55, ((Map<String, Object>)shopsList.get(i)).get("operdatetime").toString());
	            ps.setString(56, ((Map<String, Object>)shopsList.get(i)).get("istrans").toString());
	            ps.setString(57, ((Map<String, Object>)shopsList.get(i)).get("createTime").toString());
	           }
	           public int getBatchSize()
	           {
	            return shopsList.size();
	           }
	     });
	}
	
	
	
	
	//GC从合同多方信息表 增量
	public void insertGCASSUREMULTICLIENT(List<Map<String, Object>> list){
	   final List<Map<String, Object>> shopsList = list;
	  String sql = "insert into T_GCASSUREMULTICLIENT   (keycode,             "+           
				   "                            	     keytype,             "+        
				   "                            	     gcbusinesslevel,     "+    
				   "                         			 gcsyslevel,          "+             
				   "                        			 sequenceno,          "+              
				   "                         			 clienttype,          "+                
				   "                         			 custid,              "+                
				   "                         			 cname,               "+                
				   "                         		     isprimaryclient,     "+                
				   "                         		     spokesman,           "+                
				   "                         			 inkdate,             "+                
				   "                         			 keyeffectedstate,    "+                
				   "                                     keydatestate,        "+
				   "                                     editionno,           "+
				   "                                     instcitycode,        "+
				   "                                     operdatetime,        "+
				   "                                     istrans,CREATE_TIME) "+
				   "    values(?,                                             "+
				   "    			 ?,                                       "+
				   "    			 ?,                                       "+
				   "    			 ?,                                       "+
				   "    			 ?,                                       "+
				   "    			 ?,                                       "+
				   "           ?,                                             "+
				   "           ?,                                             "+
				   "           ?,                                             "+
				   "           ?,                                             "+
				   "           ?,                                             "+
				   "           ?,                                             "+
				   "           ?,                                             "+
				   "           ?,                                             "+
				   "           ?,                                             "+
				   "           ?,                                             "+
				   "           ?,?)                                           ";
	      jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){
	        public void setValues(PreparedStatement ps,int i)throws SQLException
	           {
	        	ps.setString(1, ((Map<String, Object>)shopsList.get(i)).get("keycode").toString());  
	        	ps.setString(2, ((Map<String, Object>)shopsList.get(i)).get("keytype").toString());  
	        	ps.setString(3, ((Map<String, Object>)shopsList.get(i)).get("gcbusinesslevel").toString());  
	        	ps.setString(4, ((Map<String, Object>)shopsList.get(i)).get("gcsyslevel").toString());  
	        	ps.setString(5, ((Map<String, Object>)shopsList.get(i)).get("sequenceno").toString());  
	        	ps.setString(6, ((Map<String, Object>)shopsList.get(i)).get("clienttype").toString());  
	        	ps.setString(7, ((Map<String, Object>)shopsList.get(i)).get("custid").toString());  
	        	ps.setString(8, ((Map<String, Object>)shopsList.get(i)).get("cname").toString());  
	        	ps.setString(9, ((Map<String, Object>)shopsList.get(i)).get("isprimaryclient").toString());  
	        	ps.setString(10, ((Map<String, Object>)shopsList.get(i)).get("spokesman").toString()); 
	        	ps.setString(11, ((Map<String, Object>)shopsList.get(i)).get("inkdate").toString()); 
	        	ps.setString(12, ((Map<String, Object>)shopsList.get(i)).get("keyeffectedstate").toString()); 
	        	ps.setString(13, ((Map<String, Object>)shopsList.get(i)).get("keydatestate").toString()); 
	        	ps.setString(14, ((Map<String, Object>)shopsList.get(i)).get("editionno").toString()); 
	        	ps.setString(15, ((Map<String, Object>)shopsList.get(i)).get("instcitycode").toString()); 
	        	ps.setString(16, ((Map<String, Object>)shopsList.get(i)).get("operdatetime").toString()); 
	        	ps.setString(17, ((Map<String, Object>)shopsList.get(i)).get("istrans").toString()); 
	        	ps.setString(18, ((Map<String, Object>)shopsList.get(i)).get("createTime").toString()); 
	           }
	           public int getBatchSize()
	           {
	            return shopsList.size();
	           }
	     });
	}
	
	
	
	
	
	//GC合同基本表  增量
	public void insertGCCONTRACTMAIN(List<Map<String, Object>> list){
	   final List<Map<String, Object>> shopsList = list;
	 String sql = "insert into T_GCCONTRACTMAIN   (keycode,                 "+    
				  "                            	   keytype,               	"+     
				  "                            	   extendkeycode,         	"+ 
				  "                         	   appcode,               	"+          
				  "                        		   gcbusinesslevel,       	"+           
				  "                         	   gcsyslevel,            	"+	           
				  "                         	   upkeycode,             	"+	           
				  "                         	   upkeytype,            	"+	           
				  "                         	   upgcbusinesslevel,     	"+	           
				  "                         	   upgcsyslevel,          	"+	           
				  "                         	   oldkeycode,            	"+	           
				  "                         	   money,                 	"+	           
				  "                                currency,                "+   
				  "                                startdate,               "+   
				  "                                enddate,                 "+   
				  "                                lastdate,                "+   
				  "                                limit_1,                   "+  
				  "                                limitunit,               "+       
				  "                                keyeffectedstate,        "+       
				  "                                keydatestate,            "+       
				  "                                squarestate,             "+       
				  "                                subbusicodetype,         "+       
				  "                                billmoney,               "+       
				  "                                billtype,                "+       
				  "                                billnum,                 "+       
				  "                                bailacc,                 "+       
				  "                                assuremoney,             "+       
				  "                                assurecur,               "+       
				  "                                assurerate,              "+       
				  "                                interest,                "+       
				  "                                interestunit,            "+       
				  "                                squareinterestmode,      "+       
				  "                                ratetype,                "+       
				  "                                floattype,               "+       
				  "                                baserate,                "+
				  "                                baseratetype,            "+
				  "                                floatrate,               "+
				  "                                intrstratefdot,          "+
				  "                                remittername,            "+
				  "                                remitteraccount,         "+
				  "                                createbank,              "+
				  "                                createbankname,          "+
				  "                                poundagecurtype,         "+
				  "                                poundageamt,             "+
				  "                                poundagerate,            "+
				  "                                loanpurpose,             "+
				  "                                regroupflag,             "+
				  "                                revoldflag,              "+
				  "                                changeflag,              "+
				  "                                isbankloan,              "+
				  "                                loancardflag,            "+
				  "                                contractcount,           "+
				  "                                solutionmode,            "+
				  "                                usetype,                 "+
				  "                                terminaldate,            "+
				  "                                aribtrateorganization,   "+
				  "                                busimanager,             "+
				  "                                createdate,              "+
				  "                                passeddate,              "+
				  "                                editionno,               "+
				  "                                assistbusimanager,       "+
				  "                                loantype,                "+
				  "                                busistate,               "+
				  "                                lowriskflag,             "+
				  "                                mainassure,              "+
				  "                                subassure,               "+
				  "                                accounttype,             "+
				  "                                savingaccountnum,        "+
				  "                                repaytype,               "+
				  "                                repaymethod,             "+
				  "                                repaycycle,              "+
				  "                                repaymoney,              "+
				  "                                loanpurposeremark,       "+
				  "                                item,                    "+
				  "                                loanflag,                "+
				  "                                firstloandate,           "+
				  "                                transferstate,           "+
				  "                                deptcode,                "+
				  "                                instcode,                "+
				  "                                accantdept,              "+
				  "                                instcitycode,            "+
				  "                                operdatetime,            "+
				  "                                operator_1,                "+
				  "                                istrans,                 "+
				  "                                isreferagricultural,     "+
				  "                                isunitcust,              "+
				  "                                unionid,                 "+
				  "                                ifspecialfloatrate,      "+
				  "                                iffnongcard,             "+
				  "                                frozenstate,             "+
				  "                                busiflag,                "+
				  "                                paymode,CREATE_TIME)     "+
				  "    values(?,                                            "+
				  "    			 ?,                                         "+
				  "    			 ?,                                         "+
				  "    			 ?,                                         "+
				  "    			 ?,                                         "+
				  "    			 ?,                                         "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,                                            "+
				  "           ?,?)                                            ";
	      jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){
	        public void setValues(PreparedStatement ps,int i)throws SQLException
	           {
	        	ps.setString(1, ((Map<String, Object>)shopsList.get(i)).get("keycode").toString()); 
	        	ps.setString(2, ((Map<String, Object>)shopsList.get(i)).get("keytype").toString()); 
	        	ps.setString(3, ((Map<String, Object>)shopsList.get(i)).get("extendkeycode").toString()); 
	        	ps.setString(4, ((Map<String, Object>)shopsList.get(i)).get("appcode").toString()); 
	        	ps.setString(5, ((Map<String, Object>)shopsList.get(i)).get("gcbusinesslevel").toString()); 
	        	ps.setString(6, ((Map<String, Object>)shopsList.get(i)).get("gcsyslevel").toString()); 
	        	ps.setString(7, ((Map<String, Object>)shopsList.get(i)).get("upkeycode").toString()); 
	        	ps.setString(8, ((Map<String, Object>)shopsList.get(i)).get("upkeytype").toString()); 
	        	ps.setString(9, ((Map<String, Object>)shopsList.get(i)).get("upgcbusinesslevel").toString()); 
	        	ps.setString(10, ((Map<String, Object>)shopsList.get(i)).get("upgcsyslevel").toString());
	        	ps.setString(11, ((Map<String, Object>)shopsList.get(i)).get("oldkeycode").toString());
	        	ps.setString(12, ((Map<String, Object>)shopsList.get(i)).get("money").toString());
	        	ps.setString(13, ((Map<String, Object>)shopsList.get(i)).get("currency").toString());
	        	ps.setString(14, ((Map<String, Object>)shopsList.get(i)).get("startdate").toString());
	        	ps.setString(15, ((Map<String, Object>)shopsList.get(i)).get("enddate").toString());
	        	ps.setString(16, ((Map<String, Object>)shopsList.get(i)).get("lastdate").toString());
	        	ps.setString(17, ((Map<String, Object>)shopsList.get(i)).get("limit").toString());
	        	ps.setString(18, ((Map<String, Object>)shopsList.get(i)).get("limitunit").toString());
	        	ps.setString(19, ((Map<String, Object>)shopsList.get(i)).get("keyeffectedstate").toString());
	        	ps.setString(20, ((Map<String, Object>)shopsList.get(i)).get("keydatestate").toString());
	        	ps.setString(21, ((Map<String, Object>)shopsList.get(i)).get("squarestate").toString());
	        	ps.setString(22, ((Map<String, Object>)shopsList.get(i)).get("subbusicodetype").toString());
	        	ps.setString(23, ((Map<String, Object>)shopsList.get(i)).get("billmoney").toString());
	        	ps.setString(24, ((Map<String, Object>)shopsList.get(i)).get("billtype").toString());
	        	ps.setString(25, ((Map<String, Object>)shopsList.get(i)).get("billnum").toString());
	        	ps.setString(26, ((Map<String, Object>)shopsList.get(i)).get("bailacc").toString());
	        	ps.setString(27, ((Map<String, Object>)shopsList.get(i)).get("assuremoney").toString());
	        	ps.setString(28, ((Map<String, Object>)shopsList.get(i)).get("assurecur").toString());
	        	ps.setString(29, ((Map<String, Object>)shopsList.get(i)).get("assurerate").toString());
	        	ps.setString(30, ((Map<String, Object>)shopsList.get(i)).get("interest").toString());
	        	ps.setString(31, ((Map<String, Object>)shopsList.get(i)).get("interestunit").toString());
	        	ps.setString(32, ((Map<String, Object>)shopsList.get(i)).get("squareinterestmode").toString());
	        	ps.setString(33, ((Map<String, Object>)shopsList.get(i)).get("ratetype").toString());
	        	ps.setString(34, ((Map<String, Object>)shopsList.get(i)).get("floattype").toString());
	        	ps.setString(35, ((Map<String, Object>)shopsList.get(i)).get("baserate").toString());
	        	ps.setString(36, ((Map<String, Object>)shopsList.get(i)).get("baseratetype").toString());
	        	ps.setString(37, ((Map<String, Object>)shopsList.get(i)).get("floatrate").toString());
	        	ps.setString(38, ((Map<String, Object>)shopsList.get(i)).get("intrstratefdot").toString());
	        	ps.setString(39, ((Map<String, Object>)shopsList.get(i)).get("remittername").toString());
	        	ps.setString(40, ((Map<String, Object>)shopsList.get(i)).get("remitteraccount").toString());
	        	ps.setString(41, ((Map<String, Object>)shopsList.get(i)).get("createbank").toString());
	        	ps.setString(42, ((Map<String, Object>)shopsList.get(i)).get("createbankname").toString());
	        	ps.setString(43, ((Map<String, Object>)shopsList.get(i)).get("poundagecurtype").toString());
	        	ps.setString(44, ((Map<String, Object>)shopsList.get(i)).get("poundageamt").toString());
	        	ps.setString(45, ((Map<String, Object>)shopsList.get(i)).get("poundagerate").toString());
	        	ps.setString(46, ((Map<String, Object>)shopsList.get(i)).get("loanpurpose").toString());
	        	ps.setString(47, ((Map<String, Object>)shopsList.get(i)).get("regroupflag").toString());
	        	ps.setString(48, ((Map<String, Object>)shopsList.get(i)).get("revoldflag").toString());
	        	ps.setString(49, ((Map<String, Object>)shopsList.get(i)).get("changeflag").toString());
	        	ps.setString(50, ((Map<String, Object>)shopsList.get(i)).get("isbankloan").toString());
	        	ps.setString(51, ((Map<String, Object>)shopsList.get(i)).get("loancardflag").toString());
	        	ps.setString(52, ((Map<String, Object>)shopsList.get(i)).get("contractcount").toString());
	        	ps.setString(53, ((Map<String, Object>)shopsList.get(i)).get("solutionmode").toString());
	        	ps.setString(54, ((Map<String, Object>)shopsList.get(i)).get("usetype").toString());
	        	ps.setString(55, ((Map<String, Object>)shopsList.get(i)).get("terminaldate").toString());
	        	ps.setString(56, ((Map<String, Object>)shopsList.get(i)).get("aribtrateorganization").toString());
	        	ps.setString(57, ((Map<String, Object>)shopsList.get(i)).get("busimanager").toString());
	        	ps.setString(58, ((Map<String, Object>)shopsList.get(i)).get("createdate").toString());
	        	ps.setString(59, ((Map<String, Object>)shopsList.get(i)).get("passeddate").toString());
	        	ps.setString(60, ((Map<String, Object>)shopsList.get(i)).get("editionno").toString());
	        	ps.setString(61, ((Map<String, Object>)shopsList.get(i)).get("assistbusimanager").toString());
	        	ps.setString(62, ((Map<String, Object>)shopsList.get(i)).get("loantype").toString());
	        	ps.setString(63, ((Map<String, Object>)shopsList.get(i)).get("busistate").toString());
	        	ps.setString(64, ((Map<String, Object>)shopsList.get(i)).get("lowriskflag").toString());
	        	ps.setString(65, ((Map<String, Object>)shopsList.get(i)).get("mainassure").toString());
	        	ps.setString(66, ((Map<String, Object>)shopsList.get(i)).get("subassure").toString());
	        	ps.setString(67, ((Map<String, Object>)shopsList.get(i)).get("accounttype").toString());
	        	ps.setString(68, ((Map<String, Object>)shopsList.get(i)).get("savingaccountnum").toString());
	        	ps.setString(69, ((Map<String, Object>)shopsList.get(i)).get("repaytype").toString());
	        	ps.setString(70, ((Map<String, Object>)shopsList.get(i)).get("repaymethod").toString());
	        	ps.setString(71, ((Map<String, Object>)shopsList.get(i)).get("repaycycle").toString());
	        	ps.setString(72, ((Map<String, Object>)shopsList.get(i)).get("repaymoney").toString());
	        	ps.setString(73, ((Map<String, Object>)shopsList.get(i)).get("loanpurposeremark").toString());
	        	ps.setString(74, ((Map<String, Object>)shopsList.get(i)).get("item").toString());
	        	ps.setString(75, ((Map<String, Object>)shopsList.get(i)).get("loanflag").toString());
	        	ps.setString(76, ((Map<String, Object>)shopsList.get(i)).get("firstloandate").toString());
	        	ps.setString(77, ((Map<String, Object>)shopsList.get(i)).get("transferstate").toString());
	        	ps.setString(78, ((Map<String, Object>)shopsList.get(i)).get("deptcode").toString());
	        	ps.setString(79, ((Map<String, Object>)shopsList.get(i)).get("instcode").toString());
	        	ps.setString(80, ((Map<String, Object>)shopsList.get(i)).get("accantdept").toString());
	        	ps.setString(81, ((Map<String, Object>)shopsList.get(i)).get("instcitycode").toString());
	        	ps.setString(82, ((Map<String, Object>)shopsList.get(i)).get("operdatetime").toString());
	        	ps.setString(83, ((Map<String, Object>)shopsList.get(i)).get("operator").toString());
	        	ps.setString(84, ((Map<String, Object>)shopsList.get(i)).get("istrans").toString());
	        	ps.setString(85, ((Map<String, Object>)shopsList.get(i)).get("isreferagricultural").toString());
	        	ps.setString(86, ((Map<String, Object>)shopsList.get(i)).get("isunitcust").toString());
	        	ps.setString(87, ((Map<String, Object>)shopsList.get(i)).get("unionid").toString());
	        	ps.setString(88, ((Map<String, Object>)shopsList.get(i)).get("ifspecialfloatrate").toString());
	        	ps.setString(89, ((Map<String, Object>)shopsList.get(i)).get("iffnongcard").toString());
	        	ps.setString(90, ((Map<String, Object>)shopsList.get(i)).get("frozenstate").toString());
	        	ps.setString(91, ((Map<String, Object>)shopsList.get(i)).get("busiflag").toString());
	        	ps.setString(92, ((Map<String, Object>)shopsList.get(i)).get("paymode").toString());
	        	ps.setString(93, ((Map<String, Object>)shopsList.get(i)).get("createTime").toString());
	           }
	           public int getBatchSize()
	           {
	            return shopsList.size();
	           }
	           
	     });
	      
	}
	
	
	
	
	
	
	
	
	
	
	
	//GC押品主表 增量
	public void insertGCGUARANTYMAIN(List<Map<String, Object>> list){
	   final List<Map<String, Object>> shopsList = list;
	  String sql = "insert into T_GCGUARANTYMAIN(keycode,                  			"+
			  "                                  keytype,                           "+
			  "                                  gcbusinesslevel,                   "+
			  "                                  gcsyslevel,                        "+
			  "                                  upkeycode,                         "+
			  "                                  upkeytype,                         "+
			  "                                  upgcbusinesslevel,                 "+
			  "                                  upgcsysleveldetail,                "+
			  "                                  bigguarantytype ,                  "+
			  "                                  custid,                            "+
			  "                                  username,                          "+
			  "                                  keyeffectedstate,                  "+
			  "                                  keydatestate,                      "+
			  "                                  guarantystate,                     "+
			  "                                  guarantyname,                      "+
			  "                                  money,                             "+
			  "                                  moneycur,                          "+
			  "                                  regulateline,                      "+
			  "                                  guarrate,                          "+
			  "                                  guarantypurpose,                   "+
			  "                                  purposeremark,                     "+
			  "                                  evaluatingunitprice,               "+
			  "                                  evaluatingvalue,                   "+
			  "                                  evaluatingcurrency,                "+
			  "                                  evaluatingdate,                    "+
			  "                                  evaluatinggroup,                   "+
			  "                                  startdate,                         "+
			  "                                  enddate,                           "+
			  "                                  storeno,                           "+
			  "                                  guarantyvalue,                     "+
			  "                                  attachingorigivalue,               "+
			  "                                  attachingnetvalue,                 "+
			  "                                  guarcount,                         "+
			  "                                  unit,                              "+
			  "                                  model,                             "+
			  "                                  guarlife,                          "+
			  "                                  guartimes,                         "+
			  "                                  qualitystatus,                     "+
			  "                                  attachingstatus,                   "+
			  "                                  site,                              "+
			  "                                  coordinate,                        "+
			  "                                  usingstatus,                       "+
			  "                                  elserightstatus,                   "+
			  "                                  certificateno,                     "+
			  "                                  certificatename,                   "+
			  "                                  certificatething,                  "+
			  "                                  relalandcertifino,                 "+
			  "                                  relabuildingcertifino,             "+
			  "                                  relaconstrucertifino,              "+
			  "                                  safekeeping,                       "+
			  "                                  safekeepingdept,                   "+
			  "                                  bookdept,                          "+
			  "                                  safetymoney,                       "+
			  "                                  safetyno,                          "+
			  "                                  safetystartdate,                   "+
			  "                                  safetyenddate,                     "+
			  "                                  note,                              "+
			  "                                  removedate,                        "+
			  "                                  importdate,                        "+
			  "                                  exportdate,                        "+
			  "                                  terminaldate,                      "+
			  "                                  busimanager,                       "+
			  "                                  createdate,                        "+
			  "                                  editionno,                         "+
			  "                                  sign,                              "+
			  "                                  othercertifino,                    "+
			  "                                  exportreason,                      "+
			  "                                  houserealtype,                     "+
			  "                                  buildingarea,                      "+
			  "                                  buildingdate,                      "+
			  "                                  buildingframe,                     "+
			  "                                  projectplan,                       "+
			  "                                  usingarea,                         "+
			  "                                  areaunit,                          "+
			  "                                  usefullife,                        "+
			  "                                  ifrightdisputed,                   "+
			  "                                  iflending,                         "+
			  "                                  propertyrightowner,                "+
			  "                                  rightownernames,                   "+
			  "                                  analysis,                          "+
			  "                                  depositrate,                       "+
			  "                                  deptcode,                          "+
			  "                                  instcode,                          "+
			  "                                  instcitycode,                      "+
			  "                                  operator,                          "+
			  "                                  operdatetime,                      "+
			  "                                  ifaddowner,                        "+
			  "                                  cckeycode,                         "+
			  "                                  istrans,                           "+
			  "                                  uselifedate,                       "+
			  "                                  outtype,CREATE_TIME)               "+
			  "      values(?,                                                      "+
			  "       	 		 ?,                                                 "+
			  "       	 		 ?,                                                 "+
			  "       	 		 ?,                                                 "+
			  "       	 		 ?,                                                 "+
			  "       	 		 ?,                                                 "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,                                                      "+
			  "             ?,?)                                                      ";

	      jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){
	        public void setValues(PreparedStatement ps,int i)throws SQLException
	           {
	        	ps.setString(1, ((Map<String, Object>)shopsList.get(i)).get("keycode").toString());                         
	        	ps.setString(2, ((Map<String, Object>)shopsList.get(i)).get("keytype").toString());                         
	        	ps.setString(3, ((Map<String, Object>)shopsList.get(i)).get("gcbusinesslevel").toString());                 
	        	ps.setString(4, ((Map<String, Object>)shopsList.get(i)).get("gcsyslevel").toString());                      
	        	ps.setString(5, ((Map<String, Object>)shopsList.get(i)).get("upkeycode").toString());                       
	        	ps.setString(6, ((Map<String, Object>)shopsList.get(i)).get("upkeytype").toString());                       
	        	ps.setString(7, ((Map<String, Object>)shopsList.get(i)).get("upgcbusinesslevel").toString());               
	        	ps.setString(8, ((Map<String, Object>)shopsList.get(i)).get("upgcsysleveldetail").toString());              
	        	ps.setString(9, ((Map<String, Object>)shopsList.get(i)).get("bigguarantytype").toString());                 
	        	ps.setString(10, ((Map<String, Object>)shopsList.get(i)).get("custid").toString());                         
	        	ps.setString(11, ((Map<String, Object>)shopsList.get(i)).get("username").toString());                       
	        	ps.setString(12, ((Map<String, Object>)shopsList.get(i)).get("keyeffectedstate").toString());               
	        	ps.setString(13, ((Map<String, Object>)shopsList.get(i)).get("keydatestate").toString());                   
	        	ps.setString(14, ((Map<String, Object>)shopsList.get(i)).get("guarantystate").toString());                  
	        	ps.setString(15, ((Map<String, Object>)shopsList.get(i)).get("guarantyname").toString());                   
	        	ps.setString(16, ((Map<String, Object>)shopsList.get(i)).get("money").toString());                          
	        	ps.setString(17, ((Map<String, Object>)shopsList.get(i)).get("moneycur").toString());                       
	        	ps.setString(18, ((Map<String, Object>)shopsList.get(i)).get("regulateline").toString());                   
	        	ps.setString(19, ((Map<String, Object>)shopsList.get(i)).get("guarrate").toString());                       
	        	ps.setString(20, ((Map<String, Object>)shopsList.get(i)).get("guarantypurpose").toString());                
	        	ps.setString(21, ((Map<String, Object>)shopsList.get(i)).get("purposeremark").toString());                  
	        	ps.setString(22, ((Map<String, Object>)shopsList.get(i)).get("evaluatingunitprice").toString());            
	        	ps.setString(23, ((Map<String, Object>)shopsList.get(i)).get("evaluatingvalue").toString());                
	        	ps.setString(24, ((Map<String, Object>)shopsList.get(i)).get("evaluatingcurrency").toString());             
	        	ps.setString(25, ((Map<String, Object>)shopsList.get(i)).get("evaluatingdate").toString());                 
	        	ps.setString(26, ((Map<String, Object>)shopsList.get(i)).get("evaluatinggroup").toString());                
	        	ps.setString(27, ((Map<String, Object>)shopsList.get(i)).get("startdate").toString());                      
	        	ps.setString(28, ((Map<String, Object>)shopsList.get(i)).get("enddate").toString());                        
	        	ps.setString(29, ((Map<String, Object>)shopsList.get(i)).get("storeno").toString());                        
	        	ps.setString(30, ((Map<String, Object>)shopsList.get(i)).get("guarantyvalue").toString());                  
	        	ps.setString(31, ((Map<String, Object>)shopsList.get(i)).get("attachingorigivalue").toString());            
	        	ps.setString(32, ((Map<String, Object>)shopsList.get(i)).get("attachingnetvalue").toString());              
	        	ps.setString(33, ((Map<String, Object>)shopsList.get(i)).get("guarcount").toString());                      
	        	ps.setString(34, ((Map<String, Object>)shopsList.get(i)).get("unit").toString());                           
	        	ps.setString(35, ((Map<String, Object>)shopsList.get(i)).get("model").toString());                          
	        	ps.setString(36, ((Map<String, Object>)shopsList.get(i)).get("guarlife").toString());                       
	        	ps.setString(37, ((Map<String, Object>)shopsList.get(i)).get("guartimes").toString());                      
	        	ps.setString(38, ((Map<String, Object>)shopsList.get(i)).get("qualitystatus").toString());                  
	        	ps.setString(39, ((Map<String, Object>)shopsList.get(i)).get("attachingstatus").toString());                
	        	ps.setString(40, ((Map<String, Object>)shopsList.get(i)).get("site").toString());                           
	        	ps.setString(41, ((Map<String, Object>)shopsList.get(i)).get("coordinate").toString());                     
	        	ps.setString(42, ((Map<String, Object>)shopsList.get(i)).get("usingstatus").toString());                    
	        	ps.setString(43, ((Map<String, Object>)shopsList.get(i)).get("elserightstatus").toString());                
	        	ps.setString(44, ((Map<String, Object>)shopsList.get(i)).get("certificateno").toString());                  
	        	ps.setString(45, ((Map<String, Object>)shopsList.get(i)).get("certificatename").toString());                
	        	ps.setString(46, ((Map<String, Object>)shopsList.get(i)).get("certificatething").toString());               
	        	ps.setString(47, ((Map<String, Object>)shopsList.get(i)).get("relalandcertifino").toString());              
	        	ps.setString(48, ((Map<String, Object>)shopsList.get(i)).get("relabuildingcertifino").toString());          
	        	ps.setString(49, ((Map<String, Object>)shopsList.get(i)).get("relaconstrucertifino").toString());           
	        	ps.setString(50, ((Map<String, Object>)shopsList.get(i)).get("safekeeping").toString());                    
	        	ps.setString(51, ((Map<String, Object>)shopsList.get(i)).get("safekeepingdept").toString());                
	        	ps.setString(52, ((Map<String, Object>)shopsList.get(i)).get("bookdept").toString());                       
	        	ps.setString(53, ((Map<String, Object>)shopsList.get(i)).get("safetymoney").toString());                    
	        	ps.setString(54, ((Map<String, Object>)shopsList.get(i)).get("safetyno").toString());                       
	        	ps.setString(55, ((Map<String, Object>)shopsList.get(i)).get("safetystartdate").toString());                
	        	ps.setString(56, ((Map<String, Object>)shopsList.get(i)).get("safetyenddate").toString());                  
	        	ps.setString(57, ((Map<String, Object>)shopsList.get(i)).get("note").toString());                           
	        	ps.setString(58, ((Map<String, Object>)shopsList.get(i)).get("removedate").toString());                     
	        	ps.setString(59, ((Map<String, Object>)shopsList.get(i)).get("importdate").toString());                     
	        	ps.setString(60, ((Map<String, Object>)shopsList.get(i)).get("exportdate").toString());                     
	        	ps.setString(61, ((Map<String, Object>)shopsList.get(i)).get("terminaldate").toString());                   
	        	ps.setString(62, ((Map<String, Object>)shopsList.get(i)).get("busimanager").toString());                    
	        	ps.setString(63, ((Map<String, Object>)shopsList.get(i)).get("createdate").toString());                     
	        	ps.setString(64, ((Map<String, Object>)shopsList.get(i)).get("editionno").toString());                      
	        	ps.setString(65, ((Map<String, Object>)shopsList.get(i)).get("sign").toString());                           
	        	ps.setString(66, ((Map<String, Object>)shopsList.get(i)).get("othercertifino").toString());                 
	        	ps.setString(67, ((Map<String, Object>)shopsList.get(i)).get("exportreason").toString());                   
	        	ps.setString(68, ((Map<String, Object>)shopsList.get(i)).get("houserealtype").toString());                  
	        	ps.setString(69, ((Map<String, Object>)shopsList.get(i)).get("buildingarea").toString());                   
	        	ps.setString(70, ((Map<String, Object>)shopsList.get(i)).get("buildingdate").toString());                   
	        	ps.setString(71, ((Map<String, Object>)shopsList.get(i)).get("buildingframe").toString());                  
	        	ps.setString(72, ((Map<String, Object>)shopsList.get(i)).get("projectplan").toString());                    
	        	ps.setString(73, ((Map<String, Object>)shopsList.get(i)).get("usingarea").toString());                      
	        	ps.setString(74, ((Map<String, Object>)shopsList.get(i)).get("areaunit").toString());                       
	        	ps.setString(75, ((Map<String, Object>)shopsList.get(i)).get("usefullife").toString());                     
	        	ps.setString(76, ((Map<String, Object>)shopsList.get(i)).get("ifrightdisputed").toString());                
	        	ps.setString(77, ((Map<String, Object>)shopsList.get(i)).get("iflending").toString());                      
	        	ps.setString(78, ((Map<String, Object>)shopsList.get(i)).get("propertyrightowner").toString());             
	        	ps.setString(79, ((Map<String, Object>)shopsList.get(i)).get("rightownernames").toString());                
	        	ps.setString(80, ((Map<String, Object>)shopsList.get(i)).get("analysis").toString());                       
	        	ps.setString(81, ((Map<String, Object>)shopsList.get(i)).get("depositrate").toString());                    
	        	ps.setString(82, ((Map<String, Object>)shopsList.get(i)).get("deptcode").toString());                       
	        	ps.setString(83, ((Map<String, Object>)shopsList.get(i)).get("instcode").toString());                       
	        	ps.setString(84, ((Map<String, Object>)shopsList.get(i)).get("instcitycode").toString());                   
	        	ps.setString(85, ((Map<String, Object>)shopsList.get(i)).get("operator").toString());                       
	        	ps.setString(86, ((Map<String, Object>)shopsList.get(i)).get("operdatetime").toString());                   
	        	ps.setString(87, ((Map<String, Object>)shopsList.get(i)).get("ifaddowner").toString());                     
	        	ps.setString(88, ((Map<String, Object>)shopsList.get(i)).get("cckeycode").toString());                      
	        	ps.setString(89, ((Map<String, Object>)shopsList.get(i)).get("istrans").toString());                        
	        	ps.setString(90, ((Map<String, Object>)shopsList.get(i)).get("uselifedate").toString());                    
	        	ps.setString(91, ((Map<String, Object>)shopsList.get(i)).get("outtype").toString());
	        	ps.setString(92, ((Map<String, Object>)shopsList.get(i)).get("createTime").toString());
	           }
	           public int getBatchSize()
	           {
	            return shopsList.size();
	           }
	     });
	}
	
	
	
	
	
	
	
		//Gc贷款证表  增量
		public void insertGCLOANCARD(List<Map<String, Object>> list){
		   final List<Map<String, Object>> shopsList = list;
		   String sql = "insert into T_GCLOANCARD     		 (loancardid,             "+  
				   "                                   		  custid,                 "+  
				   "                                   		  state,                  "+  
				   "                       					  loancardtype,           "+  
				   "                       					  password,               "+  
				   "                       					  initloancardid,         "+  
				   "                      					  lastloancardid,         "+  
				   "                       					  changetype,             "+  
				   "                      					  changereason,           "+  
				   "                       					  changetimes,            "+  
				   "                       					  grantdate,              "+  
				   "                                          deptcode,               "+  
				   "                                          instcode,               "+  
				   "                                          instcitycode,           "+  
				   "                                          operdatetime,           "+  
				   "                                          operator,               "+  
				   "                                          istrans,CREATE_TIME)    "+  
				   "   values(?,                                               	      "+  
				   "   			 ?,                                                   "+  
				   "   			 ?,                                                   "+  
				   "   			 ?,                                                   "+  
				   "   			 ?,                                                   "+  
				   "   			 ?,                                                   "+  
				   "          ?,                                               		  "+  
				   "          ?,                                                      "+  
				   "          ?,                                                      "+  
				   "          ?,                                                 	  "+  
				   "          ?,                                                	  "+  
				   "          ?,                                                      "+  
				   "          ?,                                                      "+  
				   "          ?,                                                      "+  
				   "          ?,                                                      "+  
				   "          ?,                                                      "+  
				   "          ?,?)                                                      ";  
		      jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){
		        public void setValues(PreparedStatement ps,int i)throws SQLException
		           {
		        	ps.setString(1, ((Map<String, Object>)shopsList.get(i)).get("loancardid").toString());     
		        	ps.setString(2, ((Map<String, Object>)shopsList.get(i)).get("custid").toString());         
		        	ps.setString(3, ((Map<String, Object>)shopsList.get(i)).get("state").toString());          
		        	ps.setString(4, ((Map<String, Object>)shopsList.get(i)).get("loancardtype").toString());   
		        	ps.setString(5, ((Map<String, Object>)shopsList.get(i)).get("password").toString());       
		        	ps.setString(6, ((Map<String, Object>)shopsList.get(i)).get("initloancardid").toString()); 
		        	ps.setString(7, ((Map<String, Object>)shopsList.get(i)).get("lastloancardid").toString()); 
		        	ps.setString(8, ((Map<String, Object>)shopsList.get(i)).get("changetype").toString());     
		        	ps.setString(9, ((Map<String, Object>)shopsList.get(i)).get("changereason").toString());   
		        	ps.setString(10, ((Map<String, Object>)shopsList.get(i)).get("changetimes").toString());   
		        	ps.setString(11, ((Map<String, Object>)shopsList.get(i)).get("grantdate").toString());     
		        	ps.setString(12, ((Map<String, Object>)shopsList.get(i)).get("deptcode").toString());      
		        	ps.setString(13, ((Map<String, Object>)shopsList.get(i)).get("instcode").toString());      
		        	ps.setString(14, ((Map<String, Object>)shopsList.get(i)).get("instcitycode").toString());  
		        	ps.setString(15, ((Map<String, Object>)shopsList.get(i)).get("operdatetime").toString());  
		        	ps.setString(16, ((Map<String, Object>)shopsList.get(i)).get("operator").toString());      
		        	ps.setString(17, ((Map<String, Object>)shopsList.get(i)).get("istrans").toString());
		        	ps.setString(18, ((Map<String, Object>)shopsList.get(i)).get("createTime").toString()); 
		           }
		           public int getBatchSize()
		           {
		            return shopsList.size();
		           }
		     });
		}
		
		
		
		
		
		
		
		//Gc贷款证合同关联关系表  增量
		public void insertGCLOANCARDCONTRACT(List<Map<String, Object>> list){
		   final List<Map<String, Object>> shopsList = list;
		   String sql = "insert into T_GCLOANCARDCONTRACT  (sequenceno,    "+  
				   "                                   		loancardid,    "+  
				   "                                   		contractid,    "+  
				   "                       					custid,        "+  
				   "                       					state,         "+  
				   "                       				    associatedate, "+  
				   "                      					deptcode,      "+  
				   "                       				    instcode,      "+  
				   "                      					instcitycode,  "+  
				   "                       				    operator,      "+  
				   "                       				    operdatetime,  "+  
				   "                                        istrans,CREATE_TIME)       "+  
				   "   values(?,                                           "+  
				   "   			  ?,                                       "+  
				   "   			  ?,                                       "+  
				   "   			  ?,                                       "+  
				   "   			  ?,                                       "+  
				   "   			  ?,                                       "+  
				   "          ?,                                           "+  
				   "          ?,                                           "+  
				   "          ?,                                           "+  
				   "          ?,                                           "+  
				   "          ?,                                           "+  
				   "          ?,?)                                           ";  
		      jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){
		        public void setValues(PreparedStatement ps,int i)throws SQLException
		           {
		        	 ps.setString(1, ((Map<String, Object>)shopsList.get(i)).get("sequenceno").toString());
		        	 ps.setString(2, ((Map<String, Object>)shopsList.get(i)).get("loancardid").toString());
		        	 ps.setString(3, ((Map<String, Object>)shopsList.get(i)).get("contractid").toString());
		        	 ps.setString(4, ((Map<String, Object>)shopsList.get(i)).get("custid").toString());
		        	 ps.setString(5, ((Map<String, Object>)shopsList.get(i)).get("state").toString());
		        	 ps.setString(6, ((Map<String, Object>)shopsList.get(i)).get("associatedate").toString());
		        	 ps.setString(7, ((Map<String, Object>)shopsList.get(i)).get("deptcode").toString());
		        	 ps.setString(8, ((Map<String, Object>)shopsList.get(i)).get("instcode").toString());
		        	 ps.setString(9, ((Map<String, Object>)shopsList.get(i)).get("instcitycode").toString());
		        	 ps.setString(10, ((Map<String, Object>)shopsList.get(i)).get("operator").toString());
		        	 ps.setString(11, ((Map<String, Object>)shopsList.get(i)).get("operdatetime").toString());
		        	 ps.setString(12, ((Map<String, Object>)shopsList.get(i)).get("istrans").toString());
		        	 ps.setString(13, ((Map<String, Object>)shopsList.get(i)).get("createTime").toString());
		           }
		           public int getBatchSize()
		           {
		            return shopsList.size();
		           }
		     });
		}
		
		
		
		//Gc凭证信息表  增量
		public void insertGCLOANCREDIT(List<Map<String, Object>> list){
		   final List<Map<String, Object>> shopsList = list;
		   String sql = "insert into T_GCLOANCREDIT     (keycode,                              "+
				   "                                     keytype,                              "+
				   "                                     extendkeycode,                        "+
				   "                       				 gcbusinesslevel,                      "+
				   "                       				 gcsyslevel,                           "+
				   "                       			     oldkeycode,                           "+
				   "                      			     upkeycode,                            "+
				   "                       			     upkeytype,                            "+
				   "                      			     upgcbusinesslevel,                    "+
				   "                       				 upgcsyslevel,                         "+
				   "                       				 corecode,                             "+
				   "                                     oldcorecode,                          "+
				   "                                     custid,                               "+
				   "                                     money,                                "+
				   "                                     currency,                             "+
				   "                                     loandate,                             "+
				   "                                     startdate,                            "+
				   "                                     enddate,                              "+
				   "                                     lastdate,                             "+
				   "                                     limit_1,                                "+
				   "                                     limitunit,                            "+
				   "                                     keyeffectedstate,                     "+
				   "                                     keydatestate,                         "+
				   "                                     squarestate,                          "+
				   "                                     squaredate,                           "+
				   "                                     subbusicodetype,                      "+
				   "                                     busicodesequenceno,                   "+
				   "                                     busitype,                             "+
				   "                                     islimitcontrol,                       "+
				   "                                     busicontrolsequence,                  "+
				   "                                     contractlistsequence,                 "+
				   "                                     lncardno,                             "+
				   "                                     payplanflag,                          "+
				   "                                     repayplanflag,                        "+
				   "                                     repayaccount,                         "+
				   "                                     ischange,                             "+
				   "                                     transacc,                             "+
				   "                                     bailnum,                              "+
				   "                                     disposemod,                           "+
				   "                                     transaccname,                         "+
				   "                                     accantdept,                           "+
				   "                                     payintmode,                           "+
				   "                                     firsttradeflag,                       "+
				   "                                     regroupflag,                          "+
				   "                                     simulateloanflag,                     "+
				   "                                     simulatekeycode,                      "+
				   "                                     regroupcode,                          "+
				   "                                     revoldflag,                           "+
				   "                                     revoldacc,                            "+
				   "                                     dftno,                                "+
				   "                                     dftamt,                               "+
				   "                                     repayamt,                             "+
				   "                                     drafttype,                            "+
				   "                                     experiodamt,                          "+
				   "                                     dayputflag,                           "+
				   "                                     otermamt,                             "+
				   "                                     acceptdate,                           "+
				   "                                     repaytimes,                           "+
				   "                                     experiodtimes,                        "+
				   "                                     revoldtimes,                          "+
				   "                                     interest,                             "+
				   "                                     interestunit,                         "+
				   "                                     ratetype,                             "+
				   "                                     floattype,                            "+
				   "                                     baserate,                             "+
				   "                                     baseratetype,                         "+
				   "                                     floatrate,                            "+
				   "                                     intrstratefdot,                       "+
				   "                                     loanpurpose,                          "+
				   "                                     comments,                             "+
				   "                                     usetype,                              "+
				   "                                     bailacc,                              "+
				   "                                     bailcurtype,                          "+
				   "                                     assurerate,                           "+
				   "                                     bailamt,                              "+
				   "                                     item,                                 "+
				   "                                     loanmanager,                          "+
				   "                                     busimanager,                          "+
				   "                                     createdate,                           "+
				   "                                     passeddate,                           "+
				   "                                     creditstate,                          "+
				   "                                     editionno,                            "+
				   "                                     lmtseq,                               "+
				   "                                     paymethod,                            "+
				   "                                     paymode,                              "+
				   "                                     moneyusereportcycle,                  "+
				   "                                     purposeremark,                        "+
				   "                                     deptcode,                             "+
				   "                                     instcode,                             "+
				   "                                     instcitycode,                         "+
				   "                                     operator_1,                             "+
				   "                                     operdatetime,                         "+
				   "                                     firstrevolddate,                      "+
				   "                                     transferflag,                         "+
				   "                                     tmpcontractcode,                      "+
				   "                                     istrans,                              "+
				   "                                     transfirstmanager,                    "+
				   "                                     transgovernmanager,                   "+
				   "                                     countavailflag,                       "+
				   "                                     isreferagricultural,                  "+
				   "                                     reason,                               "+
				   "                                     loanpurposeremark,                    "+
				   "                                     iffnongcard,                          "+
				   "                                     loanchannel,                          "+
				   "                                     repaychannel,                         "+
				   "                                     selfhelploannet,                      "+
				   "                                     selfhelprepaynet,                     "+
				   "                                     lmtusemode,                           "+
				   "                                     contractusemode,                      "+
				   "                                     busiflag,CREATE_TIME)                 "+
				   "    values(?,                                                              "+
				   "    			 ?,                                                        "+
				   "    			 ?,                                                        "+
				   "    			 ?,                                                        "+
				   "    			 ?,                                                        "+
				   "    			 ?,                                                        "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,                                                              "+
				   "           ?,?,?,?,?,?,?,?,?,?,?,?)                                                              ";

		      jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){
		        public void setValues(PreparedStatement ps,int i)throws SQLException
		           {
		        	 ps.setString(1, ((Map<String, Object>)shopsList.get(i)).get("keycode").toString());
		        	 ps.setString(2, ((Map<String, Object>)shopsList.get(i)).get("keytype").toString());
		        	 ps.setString(3, ((Map<String, Object>)shopsList.get(i)).get("extendkeycode").toString());
		        	 ps.setString(4, ((Map<String, Object>)shopsList.get(i)).get("gcbusinesslevel").toString());
		        	 ps.setString(5, ((Map<String, Object>)shopsList.get(i)).get("gcsyslevel").toString());
		        	 ps.setString(6, ((Map<String, Object>)shopsList.get(i)).get("oldkeycode").toString());
		        	 ps.setString(7, ((Map<String, Object>)shopsList.get(i)).get("upkeycode").toString());
		        	 ps.setString(8, ((Map<String, Object>)shopsList.get(i)).get("upkeytype").toString());
		        	 ps.setString(9, ((Map<String, Object>)shopsList.get(i)).get("upgcbusinesslevel").toString());
		        	 ps.setString(10, ((Map<String, Object>)shopsList.get(i)).get("upgcsyslevel").toString());
		        	 ps.setString(11, ((Map<String, Object>)shopsList.get(i)).get("corecode").toString());
		        	 ps.setString(12, ((Map<String, Object>)shopsList.get(i)).get("oldcorecode").toString());
		        	 ps.setString(13, ((Map<String, Object>)shopsList.get(i)).get("custid").toString());
		        	 ps.setString(14, ((Map<String, Object>)shopsList.get(i)).get("money").toString());
		        	 ps.setString(15, ((Map<String, Object>)shopsList.get(i)).get("currency").toString());
		        	 ps.setString(16, ((Map<String, Object>)shopsList.get(i)).get("loandate").toString());
		        	 ps.setString(17, ((Map<String, Object>)shopsList.get(i)).get("startdate").toString());
		        	 ps.setString(18, ((Map<String, Object>)shopsList.get(i)).get("enddate").toString());
		        	 ps.setString(19, ((Map<String, Object>)shopsList.get(i)).get("lastdate").toString());
		        	 ps.setString(20, ((Map<String, Object>)shopsList.get(i)).get("limit").toString());
		        	 ps.setString(21, ((Map<String, Object>)shopsList.get(i)).get("limitunit").toString());
		        	 ps.setString(22, ((Map<String, Object>)shopsList.get(i)).get("keyeffectedstate").toString());
		        	 ps.setString(23, ((Map<String, Object>)shopsList.get(i)).get("keydatestate").toString());
		        	 ps.setString(24, ((Map<String, Object>)shopsList.get(i)).get("squarestate").toString());
		        	 ps.setString(25, ((Map<String, Object>)shopsList.get(i)).get("squaredate").toString());
		        	 ps.setString(26, ((Map<String, Object>)shopsList.get(i)).get("subbusicodetype").toString());
		        	 ps.setString(27, ((Map<String, Object>)shopsList.get(i)).get("busicodesequenceno").toString());
		        	 ps.setString(28, ((Map<String, Object>)shopsList.get(i)).get("busitype").toString());
		        	 ps.setString(29, ((Map<String, Object>)shopsList.get(i)).get("islimitcontrol").toString());
		        	 ps.setString(30, ((Map<String, Object>)shopsList.get(i)).get("busicontrolsequence").toString());
		        	 ps.setString(31, ((Map<String, Object>)shopsList.get(i)).get("contractlistsequence").toString());
		        	 ps.setString(32, ((Map<String, Object>)shopsList.get(i)).get("lncardno").toString());
		        	 ps.setString(33, ((Map<String, Object>)shopsList.get(i)).get("payplanflag").toString());
		        	 ps.setString(34, ((Map<String, Object>)shopsList.get(i)).get("repayplanflag").toString());
		        	 ps.setString(35, ((Map<String, Object>)shopsList.get(i)).get("repayaccount").toString());
		        	 ps.setString(36, ((Map<String, Object>)shopsList.get(i)).get("ischange").toString());
		        	 ps.setString(37, ((Map<String, Object>)shopsList.get(i)).get("transacc").toString());
		        	 ps.setString(38, ((Map<String, Object>)shopsList.get(i)).get("bailnum").toString());
		        	 ps.setString(39, ((Map<String, Object>)shopsList.get(i)).get("disposemod").toString());
		        	 ps.setString(40, ((Map<String, Object>)shopsList.get(i)).get("transaccname").toString());
		        	 ps.setString(41, ((Map<String, Object>)shopsList.get(i)).get("accantdept").toString());
		        	 ps.setString(42, ((Map<String, Object>)shopsList.get(i)).get("payintmode").toString());
		        	 ps.setString(43, ((Map<String, Object>)shopsList.get(i)).get("firsttradeflag").toString());
		        	 ps.setString(44, ((Map<String, Object>)shopsList.get(i)).get("regroupflag").toString());
		        	 ps.setString(45, ((Map<String, Object>)shopsList.get(i)).get("simulateloanflag").toString());
		        	 ps.setString(46, ((Map<String, Object>)shopsList.get(i)).get("simulatekeycode").toString());
		        	 ps.setString(47, ((Map<String, Object>)shopsList.get(i)).get("regroupcode").toString());
		        	 ps.setString(48, ((Map<String, Object>)shopsList.get(i)).get("revoldflag").toString());
		        	 ps.setString(49, ((Map<String, Object>)shopsList.get(i)).get("revoldacc").toString());
		        	 ps.setString(50, ((Map<String, Object>)shopsList.get(i)).get("dftno").toString());
		        	 ps.setString(51, ((Map<String, Object>)shopsList.get(i)).get("dftamt").toString());
		        	 ps.setString(52, ((Map<String, Object>)shopsList.get(i)).get("repayamt").toString());
		        	 ps.setString(53, ((Map<String, Object>)shopsList.get(i)).get("drafttype").toString());
		        	 ps.setString(54, ((Map<String, Object>)shopsList.get(i)).get("experiodamt").toString());
		        	 ps.setString(55, ((Map<String, Object>)shopsList.get(i)).get("dayputflag").toString());
		        	 ps.setString(56, ((Map<String, Object>)shopsList.get(i)).get("otermamt").toString());
		        	 ps.setString(57, ((Map<String, Object>)shopsList.get(i)).get("acceptdate").toString());
		        	 ps.setString(58, ((Map<String, Object>)shopsList.get(i)).get("repaytimes").toString());
		        	 ps.setString(59, ((Map<String, Object>)shopsList.get(i)).get("experiodtimes").toString());
		        	 ps.setString(60, ((Map<String, Object>)shopsList.get(i)).get("revoldtimes").toString());
		        	 ps.setString(61, ((Map<String, Object>)shopsList.get(i)).get("interest").toString());
		        	 ps.setString(62, ((Map<String, Object>)shopsList.get(i)).get("interestunit").toString());
		        	 ps.setString(63, ((Map<String, Object>)shopsList.get(i)).get("ratetype").toString());
		        	 ps.setString(64, ((Map<String, Object>)shopsList.get(i)).get("floattype").toString());
		        	 ps.setString(65, ((Map<String, Object>)shopsList.get(i)).get("baserate").toString());
		        	 ps.setString(66, ((Map<String, Object>)shopsList.get(i)).get("baseratetype").toString());
		        	 ps.setString(67, ((Map<String, Object>)shopsList.get(i)).get("floatrate").toString());
		        	 ps.setString(68, ((Map<String, Object>)shopsList.get(i)).get("intrstratefdot").toString());
		        	 ps.setString(69, ((Map<String, Object>)shopsList.get(i)).get("loanpurpose").toString());
		        	 ps.setString(70, ((Map<String, Object>)shopsList.get(i)).get("comments").toString());
		        	 ps.setString(71, ((Map<String, Object>)shopsList.get(i)).get("usetype").toString());
		        	 ps.setString(72, ((Map<String, Object>)shopsList.get(i)).get("bailacc").toString());
		        	 ps.setString(73, ((Map<String, Object>)shopsList.get(i)).get("bailcurtype").toString());
		        	 ps.setString(74, ((Map<String, Object>)shopsList.get(i)).get("assurerate").toString());
		        	 ps.setString(75, ((Map<String, Object>)shopsList.get(i)).get("bailamt").toString());
		        	 ps.setString(76, ((Map<String, Object>)shopsList.get(i)).get("item").toString());
		        	 ps.setString(77, ((Map<String, Object>)shopsList.get(i)).get("loanmanager").toString());
		        	 ps.setString(78, ((Map<String, Object>)shopsList.get(i)).get("busimanager").toString());
		        	 ps.setString(79, ((Map<String, Object>)shopsList.get(i)).get("createdate").toString());
		        	 ps.setString(80, ((Map<String, Object>)shopsList.get(i)).get("passeddate").toString());
		        	 ps.setString(81, ((Map<String, Object>)shopsList.get(i)).get("creditstate").toString());
		        	 ps.setString(82, ((Map<String, Object>)shopsList.get(i)).get("editionno").toString());
		        	 ps.setString(83, ((Map<String, Object>)shopsList.get(i)).get("lmtseq").toString());
		        	 ps.setString(84, ((Map<String, Object>)shopsList.get(i)).get("paymethod").toString());
		        	 ps.setString(85, ((Map<String, Object>)shopsList.get(i)).get("paymode").toString());
		        	 ps.setString(86, ((Map<String, Object>)shopsList.get(i)).get("moneyusereportcycle").toString());
		        	 ps.setString(87, ((Map<String, Object>)shopsList.get(i)).get("purposeremark").toString());
		        	 ps.setString(88, ((Map<String, Object>)shopsList.get(i)).get("deptcode").toString());
		        	 ps.setString(89, ((Map<String, Object>)shopsList.get(i)).get("instcode").toString());
		        	 ps.setString(90, ((Map<String, Object>)shopsList.get(i)).get("instcitycode").toString());
		        	 ps.setString(91, ((Map<String, Object>)shopsList.get(i)).get("operator").toString());
		        	 ps.setString(92, ((Map<String, Object>)shopsList.get(i)).get("operdatetime").toString());
		        	 ps.setString(93, ((Map<String, Object>)shopsList.get(i)).get("firstrevolddate").toString());
		        	 ps.setString(94, ((Map<String, Object>)shopsList.get(i)).get("transferflag").toString());
		        	 ps.setString(95, ((Map<String, Object>)shopsList.get(i)).get("tmpcontractcode").toString());
		        	 ps.setString(96, ((Map<String, Object>)shopsList.get(i)).get("istrans").toString());
		        	 ps.setString(97, ((Map<String, Object>)shopsList.get(i)).get("transfirstmanager").toString());
		        	 ps.setString(98, ((Map<String, Object>)shopsList.get(i)).get("transgovernmanager").toString());
		        	 ps.setString(99, ((Map<String, Object>)shopsList.get(i)).get("countavailflag").toString());
		        	 ps.setString(100, ((Map<String, Object>)shopsList.get(i)).get("isreferagricultural").toString());
		        	 ps.setString(101, ((Map<String, Object>)shopsList.get(i)).get("reason").toString());
		        	 ps.setString(102, ((Map<String, Object>)shopsList.get(i)).get("loanpurposeremark").toString());
		        	 ps.setString(103, ((Map<String, Object>)shopsList.get(i)).get("iffnongcard").toString());
		        	 ps.setString(104, ((Map<String, Object>)shopsList.get(i)).get("loanchannel").toString());
		        	 ps.setString(105, ((Map<String, Object>)shopsList.get(i)).get("repaychannel").toString());
		        	 ps.setString(106, ((Map<String, Object>)shopsList.get(i)).get("selfhelploannet").toString());
		        	 ps.setString(107, ((Map<String, Object>)shopsList.get(i)).get("selfhelprepaynet").toString());
		        	 ps.setString(108, ((Map<String, Object>)shopsList.get(i)).get("lmtusemode").toString());
		        	 ps.setString(109, ((Map<String, Object>)shopsList.get(i)).get("contractusemode").toString());
		        	 ps.setString(110, ((Map<String, Object>)shopsList.get(i)).get("busiflag").toString());
		        	 ps.setString(111, ((Map<String, Object>)shopsList.get(i)).get("createTime").toString());
		           }
		           public int getBatchSize()
		           {
		            return shopsList.size();
		           }
		     });
		}
		
		
		
		//台账——综合业务信息表   增量
		public void insertMIBUSIDATA(List<Map<String, Object>> list){
		   final List<Map<String, Object>> shopsList = list;
		   String sql = "insert into T_MIBUSIDATA       (busicode,                   "+
				   "                                     busitype,                   "+
				   "                                     loanaccount,                "+
				   "                                     bmcfg,                      "+
				   "                                     cnlno,                      "+
				   "                                     dtlnam,                     "+
				   "                                     custid,                     "+
				   "                                     cname,                      "+
				   "                                     typeid,                     "+
				   "                                     custtype,                   "+
				   "                                     custidtype,                 "+
				   "                                     custidno,                   "+
				   "                                     brhid,                      "+
				   "                                     custproperty,               "+
				   "                                     unitcusttype,               "+
				   "                                     industry,                   "+
				   "                                     city,                       "+
				   "                                     districtcounty,             "+
				   "                                     town,                       "+
				   "                                     community,                  "+
				   "                                     instcode,                   "+
				   "                                     deptcode,                   "+
				   "                                     instcitycode,               "+
				   "                                     telephone,                  "+
				   "                                     syaddr,                     "+
				   "                                     messageaddr,                "+
				   "                                     relaman,                    "+
				   "                                     isreferagricultural,        "+
				   "                                     isrelamanloan,              "+
				   "                                     enterprisestatus,           "+
				   "                                     ecoattr,                    "+
				   "                                     custscale,                  "+
				   "                                     controlmode,                "+
				   "                                     contractno,                 "+
				   "                                     contractmoney,              "+
				   "                                     delaytype,                  "+
				   "                                     loantype,                   "+
				   "                                     mainassure,                 "+
				   "                                     assuremanname,              "+
				   "                                     creditlevel,                "+
				   "                                     reqlmt,                     "+
				   "                                     isexp,                      "+
				   "                                     purposeremark,              "+
				   "                                     busimanager,                "+
				   "                                     assistbusimanager,          "+
				   "                                     firstloandate,              "+
				   "                                     repayaccount1,              "+
				   "                                     repayaccount2,              "+
				   "                                     repayaccount3,              "+
				   "                                     loandate,                   "+
				   "                                     startdate,                  "+
				   "                                     orienddate,                 "+
				   "                                     enddate,                    "+
				   "                                     limit,                      "+
				   "                                     limittype,                  "+
				   "                                     ratetype,                   "+
				   "                                     floattype,                  "+
				   "                                     baserate,                   "+
				   "                                     baseratetype,               "+
				   "                                     crtrate,                    "+
				   "                                     interest,                   "+
				   "                                     floatrate,                  "+
				   "                                     money,                      "+
				   "                                     currency,                   "+
				   "                                     balamt,                     "+
				   "                                     loanpurpose,                "+
				   "                                     accountstate,               "+
				   "                                     closedate,                  "+
				   "                                     notcounttype,               "+
				   "                                     ofiveclass,                 "+
				   "                                     fiveclass,                  "+
				   "                                     otenclass,                  "+
				   "                                     tenclass,                   "+
				   "                                     pastratescale,              "+
				   "                                     repaytype,                  "+
				   "                                     repaymethod,                "+
				   "                                     delayamtdays,               "+
				   "                                     delayinterestdays,          "+
				   "                                     firstinterestdate,          "+
				   "                                     debtinterest,               "+
				   "                                     debtinteresttimes,          "+
				   "                                     normalaccno,                "+
				   "                                     normalamt,                  "+
				   "                                     dlayaccno,                  "+
				   "                                     dlayamt,                    "+
				   "                                     dlaydt,                     "+
				   "                                     tovdlyaccno,                "+
				   "                                     tovdlyamt,                  "+
				   "                                     tovdlydt,                   "+
				   "                                     paydebt,                    "+
				   "                                     indebtamt,                  "+
				   "                                     outdebtamt,                 "+
				   "                                     debtinterestflag,           "+
				   "                                     paymode,                    "+
				   "                                     squareinterestmode,         "+
				   "                                     squarestate,                "+
				   "                                     isadm,                      "+
				   "                                     admamt,                     "+
				   "                                     badtimepriod,               "+
				   "                                     badloandate,                "+
				   "                                     tobadloanreason,            "+
				   "                                     draftstatus,                "+
				   "                                     bailamt,                    "+
				   "                                     assurerate,                 "+
				   "                                     bailacc,                    "+
				   "                                     bailcurtype,                "+
				   "                                     poundagerate,               "+
				   "                                     poundagecurtype,            "+
				   "                                     poundageamt,                "+
				   "                                     remittername,               "+
				   "                                     remitteraccount,            "+
				   "                                     createbank,                 "+
				   "                                     createbankname,             "+
				   "                                     payeename,                  "+
				   "                                     payeeaccbankname,           "+
				   "                                     payeeaccbankcode,           "+
				   "                                     payeeacc,                   "+
				   "                                     dftno,                      "+
				   "                                     autosortresult,             "+
				   "                                     lowriskflag,                "+
				   "                                     operdatetime,               "+
				   "                                     item,                       "+
				   "                                     mainbusiness,               "+
				   "                                     operator,                   "+
				   "                                     loanmanager,                "+
				   "                                     revoldtimes,                "+
				   "                                     firstrevolddate,            "+
				   "                                     loancardflag,               "+
				   "                                     istrans,                    "+
				   "                                     firstmanager,               "+
				   "                                     isouttableloan,             "+
				   "                                     transout,                   "+
				   "                                     iffnongcard,                "+
				   "                                     loanchannel,                "+
				   "                                     repaychannel,               "+
				   "                                     selfhelploannet,            "+
				   "                                     selfhelprepaynet,           "+
				   "                                     guarantornames,             "+
				   "                                     lmtcode,                    "+
				   "                                     usemode,                    "+
				   "                                     contractusemode,            "+
				   "                                     clreqlmt,                   "+
				   "                                     busiflag,CREATE_TIME)       "+
				   "   values(?,                                                     "+
				   "   				?,                                               "+
				   "   				?,                                               "+
				   "   				?,                                               "+
				   "   				?,                                               "+
				   "   				?,                                               "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?,                                                     "+
				   "          ?)                                                     ";
		      jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){
		        public void setValues(PreparedStatement ps,int i)throws SQLException
		           {
		        	ps.setString(1, ((Map<String, Object>)shopsList.get(i)).get("busicode").toString()); 
		        	 ps.setString(2, ((Map<String, Object>)shopsList.get(i)).get("busitype").toString());
		        	 ps.setString(3, ((Map<String, Object>)shopsList.get(i)).get("loanaccount").toString());
		        	 ps.setString(4, ((Map<String, Object>)shopsList.get(i)).get("bmcfg").toString());
		        	 ps.setString(5, ((Map<String, Object>)shopsList.get(i)).get("cnlno").toString());
		        	 ps.setString(6, ((Map<String, Object>)shopsList.get(i)).get("dtlnam").toString());
		        	 ps.setString(7, ((Map<String, Object>)shopsList.get(i)).get("custid").toString());
		        	 ps.setString(8, ((Map<String, Object>)shopsList.get(i)).get("cname").toString());
		        	 ps.setString(9, ((Map<String, Object>)shopsList.get(i)).get("typeid").toString());
		        	 ps.setString(10, ((Map<String, Object>)shopsList.get(i)).get("custtype").toString());
		        	 ps.setString(11, ((Map<String, Object>)shopsList.get(i)).get("custidtype").toString());
		        	 ps.setString(12, ((Map<String, Object>)shopsList.get(i)).get("custidno").toString());
		        	 ps.setString(13, ((Map<String, Object>)shopsList.get(i)).get("brhid").toString());
		        	 ps.setString(14, ((Map<String, Object>)shopsList.get(i)).get("custproperty").toString());
		        	 ps.setString(15, ((Map<String, Object>)shopsList.get(i)).get("unitcusttype").toString());
		        	 ps.setString(16, ((Map<String, Object>)shopsList.get(i)).get("industry").toString());
		        	 ps.setString(17, ((Map<String, Object>)shopsList.get(i)).get("city").toString());
		        	 ps.setString(18, ((Map<String, Object>)shopsList.get(i)).get("districtcounty").toString());
		        	 ps.setString(19, ((Map<String, Object>)shopsList.get(i)).get("town").toString());
		        	 ps.setString(20, ((Map<String, Object>)shopsList.get(i)).get("community").toString());
		        	 ps.setString(21, ((Map<String, Object>)shopsList.get(i)).get("instcode").toString());
		        	 ps.setString(22, ((Map<String, Object>)shopsList.get(i)).get("deptcode").toString());
		        	 ps.setString(23, ((Map<String, Object>)shopsList.get(i)).get("instcitycode").toString());
		        	 ps.setString(24, ((Map<String, Object>)shopsList.get(i)).get("telephone").toString());
		        	 ps.setString(25, ((Map<String, Object>)shopsList.get(i)).get("syaddr").toString());
		        	 ps.setString(26, ((Map<String, Object>)shopsList.get(i)).get("messageaddr").toString());
		        	 ps.setString(27, ((Map<String, Object>)shopsList.get(i)).get("relaman").toString());
		        	 ps.setString(28, ((Map<String, Object>)shopsList.get(i)).get("isreferagricultural").toString());
		        	 ps.setString(29, ((Map<String, Object>)shopsList.get(i)).get("isrelamanloan").toString());
		        	 ps.setString(30, ((Map<String, Object>)shopsList.get(i)).get("enterprisestatus").toString());
		        	 ps.setString(31, ((Map<String, Object>)shopsList.get(i)).get("ecoattr").toString());
		        	 ps.setString(32, ((Map<String, Object>)shopsList.get(i)).get("custscale").toString());
		        	 ps.setString(33, ((Map<String, Object>)shopsList.get(i)).get("controlmode").toString());
		        	 ps.setString(34, ((Map<String, Object>)shopsList.get(i)).get("contractno").toString());
		        	 ps.setString(35, ((Map<String, Object>)shopsList.get(i)).get("contractmoney").toString());
		        	 ps.setString(36, ((Map<String, Object>)shopsList.get(i)).get("delaytype").toString());
		        	 ps.setString(37, ((Map<String, Object>)shopsList.get(i)).get("loantype").toString());
		        	 ps.setString(38, ((Map<String, Object>)shopsList.get(i)).get("mainassure").toString());
		        	 ps.setString(39, ((Map<String, Object>)shopsList.get(i)).get("assuremanname").toString());
		        	 ps.setString(40, ((Map<String, Object>)shopsList.get(i)).get("creditlevel").toString());
		        	 ps.setString(41, ((Map<String, Object>)shopsList.get(i)).get("reqlmt").toString());
		        	 ps.setString(42, ((Map<String, Object>)shopsList.get(i)).get("isexp").toString());
		        	 ps.setString(43, ((Map<String, Object>)shopsList.get(i)).get("purposeremark").toString());
		        	 ps.setString(44, ((Map<String, Object>)shopsList.get(i)).get("busimanager").toString());
		        	 ps.setString(45, ((Map<String, Object>)shopsList.get(i)).get("assistbusimanager").toString());
		        	 ps.setString(46, ((Map<String, Object>)shopsList.get(i)).get("firstloandate").toString());
		        	 ps.setString(47, ((Map<String, Object>)shopsList.get(i)).get("repayaccount1").toString());
		        	 ps.setString(48, ((Map<String, Object>)shopsList.get(i)).get("repayaccount2").toString());
		        	 ps.setString(49, ((Map<String, Object>)shopsList.get(i)).get("repayaccount3").toString());
		        	 ps.setString(50, ((Map<String, Object>)shopsList.get(i)).get("loandate").toString());
		        	 ps.setString(51, ((Map<String, Object>)shopsList.get(i)).get("startdate").toString());
		        	 ps.setString(52, ((Map<String, Object>)shopsList.get(i)).get("orienddate").toString());
		        	 ps.setString(53, ((Map<String, Object>)shopsList.get(i)).get("enddate").toString());
		        	 ps.setString(54, ((Map<String, Object>)shopsList.get(i)).get("limit").toString());
		        	 ps.setString(55, ((Map<String, Object>)shopsList.get(i)).get("limittype").toString());
		        	 ps.setString(56, ((Map<String, Object>)shopsList.get(i)).get("ratetype").toString());
		        	 ps.setString(57, ((Map<String, Object>)shopsList.get(i)).get("floattype").toString());
		        	 ps.setString(58, ((Map<String, Object>)shopsList.get(i)).get("baserate").toString());
		        	 ps.setString(59, ((Map<String, Object>)shopsList.get(i)).get("baseratetype").toString());
		        	 ps.setString(60, ((Map<String, Object>)shopsList.get(i)).get("crtrate").toString());
		        	 ps.setString(61, ((Map<String, Object>)shopsList.get(i)).get("interest").toString());
		        	 ps.setString(62, ((Map<String, Object>)shopsList.get(i)).get("floatrate").toString());
		        	 ps.setString(63, ((Map<String, Object>)shopsList.get(i)).get("money").toString());
		        	 ps.setString(64, ((Map<String, Object>)shopsList.get(i)).get("currency").toString());
		        	 ps.setString(65, ((Map<String, Object>)shopsList.get(i)).get("balamt").toString());
		        	 ps.setString(66, ((Map<String, Object>)shopsList.get(i)).get("loanpurpose").toString());
		        	 ps.setString(67, ((Map<String, Object>)shopsList.get(i)).get("accountstate").toString());
		        	 ps.setString(68, ((Map<String, Object>)shopsList.get(i)).get("closedate").toString());
		        	 ps.setString(69, ((Map<String, Object>)shopsList.get(i)).get("notcounttype").toString());
		        	 ps.setString(70, ((Map<String, Object>)shopsList.get(i)).get("ofiveclass").toString());
		        	 ps.setString(71, ((Map<String, Object>)shopsList.get(i)).get("fiveclass").toString());
		        	 ps.setString(72, ((Map<String, Object>)shopsList.get(i)).get("otenclass").toString());
		        	 ps.setString(73, ((Map<String, Object>)shopsList.get(i)).get("tenclass").toString());
		        	 ps.setString(74, ((Map<String, Object>)shopsList.get(i)).get("pastratescale").toString());
		        	 ps.setString(75, ((Map<String, Object>)shopsList.get(i)).get("repaytype").toString());
		        	 ps.setString(76, ((Map<String, Object>)shopsList.get(i)).get("repaymethod").toString());
		        	 ps.setString(77, ((Map<String, Object>)shopsList.get(i)).get("delayamtdays").toString());
		        	 ps.setString(78, ((Map<String, Object>)shopsList.get(i)).get("delayinterestdays").toString());
		        	 ps.setString(79, ((Map<String, Object>)shopsList.get(i)).get("firstinterestdate").toString());
		        	 ps.setString(80, ((Map<String, Object>)shopsList.get(i)).get("debtinterest").toString());
		        	 ps.setString(81, ((Map<String, Object>)shopsList.get(i)).get("debtinteresttimes").toString());
		        	 ps.setString(82, ((Map<String, Object>)shopsList.get(i)).get("normalaccno").toString());
		        	 ps.setString(83, ((Map<String, Object>)shopsList.get(i)).get("normalamt").toString());
		        	 ps.setString(84, ((Map<String, Object>)shopsList.get(i)).get("dlayaccno").toString());
		        	 ps.setString(85, ((Map<String, Object>)shopsList.get(i)).get("dlayamt").toString());
		        	 ps.setString(86, ((Map<String, Object>)shopsList.get(i)).get("dlaydt").toString());
		        	 ps.setString(87, ((Map<String, Object>)shopsList.get(i)).get("tovdlyaccno").toString());
		        	 ps.setString(88, ((Map<String, Object>)shopsList.get(i)).get("tovdlyamt").toString());
		        	 ps.setString(89, ((Map<String, Object>)shopsList.get(i)).get("tovdlydt").toString());
		        	 ps.setString(90, ((Map<String, Object>)shopsList.get(i)).get("paydebt").toString());
		        	 ps.setString(91, ((Map<String, Object>)shopsList.get(i)).get("indebtamt").toString());
		        	 ps.setString(92, ((Map<String, Object>)shopsList.get(i)).get("outdebtamt").toString());
		        	 ps.setString(93, ((Map<String, Object>)shopsList.get(i)).get("debtinterestflag").toString());
		        	 ps.setString(94, ((Map<String, Object>)shopsList.get(i)).get("paymode").toString());
		        	 ps.setString(95, ((Map<String, Object>)shopsList.get(i)).get("squareinterestmode").toString());
		        	 ps.setString(96, ((Map<String, Object>)shopsList.get(i)).get("squarestate").toString());
		        	 ps.setString(97, ((Map<String, Object>)shopsList.get(i)).get("isadm").toString());
		        	 ps.setString(98, ((Map<String, Object>)shopsList.get(i)).get("admamt").toString());
		        	 ps.setString(99, ((Map<String, Object>)shopsList.get(i)).get("badtimepriod").toString());
		        	 ps.setString(100, ((Map<String, Object>)shopsList.get(i)).get("badloandate").toString());
		        	 ps.setString(101, ((Map<String, Object>)shopsList.get(i)).get("tobadloanreason").toString());
		        	 ps.setString(102, ((Map<String, Object>)shopsList.get(i)).get("draftstatus").toString());
		        	 ps.setString(103, ((Map<String, Object>)shopsList.get(i)).get("bailamt").toString());
		        	 ps.setString(104, ((Map<String, Object>)shopsList.get(i)).get("assurerate").toString());
		        	 ps.setString(105, ((Map<String, Object>)shopsList.get(i)).get("bailacc").toString());
		        	 ps.setString(106, ((Map<String, Object>)shopsList.get(i)).get("bailcurtype").toString());
		        	 ps.setString(107, ((Map<String, Object>)shopsList.get(i)).get("poundagerate").toString());
		        	 ps.setString(108, ((Map<String, Object>)shopsList.get(i)).get("poundagecurtype").toString());
		        	 ps.setString(109, ((Map<String, Object>)shopsList.get(i)).get("poundageamt").toString());
		        	 ps.setString(110, ((Map<String, Object>)shopsList.get(i)).get("remittername").toString());
		        	 ps.setString(111, ((Map<String, Object>)shopsList.get(i)).get("remitteraccount").toString());
		        	 ps.setString(112, ((Map<String, Object>)shopsList.get(i)).get("createbank").toString());
		        	 ps.setString(113, ((Map<String, Object>)shopsList.get(i)).get("createbankname").toString());
		        	 ps.setString(114, ((Map<String, Object>)shopsList.get(i)).get("payeename").toString());
		        	 ps.setString(115, ((Map<String, Object>)shopsList.get(i)).get("payeeaccbankname").toString());
		        	 ps.setString(116, ((Map<String, Object>)shopsList.get(i)).get("payeeaccbankcode").toString());
		        	 ps.setString(117, ((Map<String, Object>)shopsList.get(i)).get("payeeacc").toString());
		        	 ps.setString(118, ((Map<String, Object>)shopsList.get(i)).get("dftno").toString());
		        	 ps.setString(119, ((Map<String, Object>)shopsList.get(i)).get("autosortresult").toString());
		        	 ps.setString(120, ((Map<String, Object>)shopsList.get(i)).get("lowriskflag").toString());
		        	 ps.setString(121, ((Map<String, Object>)shopsList.get(i)).get("operdatetime").toString());
		        	 ps.setString(122, ((Map<String, Object>)shopsList.get(i)).get("item").toString());
		        	 ps.setString(123, ((Map<String, Object>)shopsList.get(i)).get("mainbusiness").toString());
		        	 ps.setString(124, ((Map<String, Object>)shopsList.get(i)).get("operator").toString());
		        	 ps.setString(125, ((Map<String, Object>)shopsList.get(i)).get("loanmanager").toString());
		        	 ps.setString(126, ((Map<String, Object>)shopsList.get(i)).get("revoldtimes").toString());
		        	 ps.setString(127, ((Map<String, Object>)shopsList.get(i)).get("firstrevolddate").toString());
		        	 ps.setString(128, ((Map<String, Object>)shopsList.get(i)).get("loancardflag").toString());
		        	 ps.setString(129, ((Map<String, Object>)shopsList.get(i)).get("istrans").toString());
		        	 ps.setString(130, ((Map<String, Object>)shopsList.get(i)).get("firstmanager").toString());
		        	 ps.setString(131, ((Map<String, Object>)shopsList.get(i)).get("isouttableloan").toString());
		        	 ps.setString(132, ((Map<String, Object>)shopsList.get(i)).get("transout").toString());
		        	 ps.setString(133, ((Map<String, Object>)shopsList.get(i)).get("iffnongcard").toString());
		        	 ps.setString(134, ((Map<String, Object>)shopsList.get(i)).get("loanchannel").toString());
		        	 ps.setString(135, ((Map<String, Object>)shopsList.get(i)).get("repaychannel").toString());
		        	 ps.setString(136, ((Map<String, Object>)shopsList.get(i)).get("selfhelploannet").toString());
		        	 ps.setString(137, ((Map<String, Object>)shopsList.get(i)).get("selfhelprepaynet").toString());
		        	 ps.setString(138, ((Map<String, Object>)shopsList.get(i)).get("guarantornames").toString());
		        	 ps.setString(139, ((Map<String, Object>)shopsList.get(i)).get("lmtcode").toString());
		        	 ps.setString(140, ((Map<String, Object>)shopsList.get(i)).get("usemode").toString());
		        	 ps.setString(141, ((Map<String, Object>)shopsList.get(i)).get("contractusemode").toString());
		        	 ps.setString(142, ((Map<String, Object>)shopsList.get(i)).get("clreqlmt").toString());
		        	 ps.setString(143, ((Map<String, Object>)shopsList.get(i)).get("busiflag").toString());
		        	 ps.setString(144, ((Map<String, Object>)shopsList.get(i)).get("createTime").toString());
		           }
		           public int getBatchSize()
		           {
		            return shopsList.size();
		           }
		     });
		}
		
		
		/**
		 * 台帐 update
		 * @param list
		 */
		public void updateMIBUSIDATA(List<Map<String, Object>> list){
			 final List<Map<String, Object>> shopsList = list;
		        String sql =    " update  T_MIBUSIDATA set			"+
		        		"    busicode             = ?   "+
		        		"   ,busitype             = ?   "+
		        		"   ,loanaccount          = ?   "+
		        		"   ,bmcfg                = ?   "+
		        		"   ,cnlno                = ?   "+
		        		"   ,dtlnam               = ?   "+
		        		"   ,custid               = ?   "+
		        		"   ,cname                = ?   "+
		        		"   ,typeid               = ?   "+
		        		"   ,custtype             = ?   "+
		        		"   ,custidtype           = ?   "+
		        		"   ,custidno             = ?   "+
		        		"   ,brhid                = ?   "+
		        		"   ,custproperty         = ?   "+
		        		"   ,unitcusttype         = ?   "+
		        		"   ,industry             = ?   "+
		        		"   ,city                 = ?   "+
		        		"   ,districtcounty       = ?   "+
		        		"   ,town                 = ?   "+
		        		"   ,community            = ?   "+
		        		"   ,instcode             = ?   "+
		        		"   ,deptcode             = ?   "+
		        		"   ,instcitycode         = ?   "+
		        		"   ,telephone            = ?   "+
		        		"   ,syaddr               = ?   "+
		        		"   ,messageaddr          = ?   "+
		        		"   ,relaman              = ?   "+
		        		"   ,isreferagricultural  = ?   "+
		        		"   ,isrelamanloan        = ?   "+
		        		"   ,enterprisestatus     = ?   "+
		        		"   ,ecoattr              = ?   "+
		        		"   ,custscale            = ?   "+
		        		"   ,controlmode          = ?   "+
		        		"   ,contractno           = ?   "+
		        		"   ,contractmoney        = ?   "+
		        		"   ,delaytype            = ?   "+
		        		"   ,loantype             = ?   "+
		        		"   ,mainassure           = ?   "+
		        		"   ,assuremanname        = ?   "+
		        		"   ,creditlevel          = ?   "+
		        		"   ,reqlmt               = ?   "+
		        		"   ,isexp                = ?   "+
		        		"   ,purposeremark        = ?   "+
		        		"   ,busimanager          = ?   "+
		        		"   ,assistbusimanager    = ?   "+
		        		"   ,firstloandate        = ?   "+
		        		"   ,repayaccount1        = ?   "+
		        		"   ,repayaccount2        = ?   "+
		        		"   ,repayaccount3        = ?   "+
		        		"   ,loandate             = ?   "+
		        		"   ,startdate            = ?   "+
		        		"   ,orienddate           = ?   "+
		        		"   ,enddate              = ?   "+
		        		"   ,limit                = ?   "+
		        		"   ,limittype            = ?   "+
		        		"   ,ratetype             = ?   "+
		        		"   ,floattype            = ?   "+
		        		"   ,baserate             = ?   "+
		        		"   ,baseratetype         = ?   "+
		        		"   ,crtrate              = ?   "+
		        		"   ,interest             = ?   "+
		        		"   ,floatrate            = ?   "+
		        		"   ,money                = ?   "+
		        		"   ,currency             = ?   "+
		        		"   ,balamt               = ?   "+
		        		"   ,loanpurpose          = ?   "+
		        		"   ,accountstate         = ?   "+
		        		"   ,closedate            = ?   "+
		        		"   ,notcounttype         = ?   "+
		        		"   ,ofiveclass           = ?   "+
		        		"   ,fiveclass            = ?   "+
		        		"   ,otenclass            = ?   "+
		        		"   ,tenclass             = ?   "+
		        		"   ,pastratescale        = ?   "+
		        		"   ,repaytype            = ?   "+
		        		"   ,repaymethod          = ?   "+
		        		"   ,delayamtdays         = ?   "+
		        		"   ,delayinterestdays    = ?   "+
		        		"   ,firstinterestdate    = ?   "+
		        		"   ,debtinterest         = ?   "+
		        		"   ,debtinteresttimes    = ?   "+
		        		"   ,normalaccno          = ?   "+
		        		"   ,normalamt            = ?   "+
		        		"   ,dlayaccno            = ?   "+
		        		"   ,dlayamt              = ?   "+
		        		"   ,dlaydt               = ?   "+
		        		"   ,tovdlyaccno          = ?   "+
		        		"   ,tovdlyamt            = ?   "+
		        		"   ,tovdlydt             = ?   "+
		        		"   ,paydebt              = ?   "+
		        		"   ,indebtamt            = ?   "+
		        		"   ,outdebtamt           = ?   "+
		        		"   ,debtinterestflag     = ?   "+
		        		"   ,paymode              = ?   "+
		        		"   ,squareinterestmode   = ?   "+
		        		"   ,squarestate          = ?   "+
		        		"   ,isadm                = ?   "+
		        		"   ,admamt               = ?   "+
		        		"   ,badtimepriod         = ?   "+
		        		"   ,badloandate          = ?   "+
		        		"   ,tobadloanreason      = ?   "+
		        		"   ,draftstatus          = ?   "+
		        		"   ,bailamt              = ?   "+
		        		"   ,assurerate           = ?   "+
		        		"   ,bailacc              = ?   "+
		        		"   ,bailcurtype          = ?   "+
		        		"   ,poundagerate         = ?   "+
		        		"   ,poundagecurtype      = ?   "+
		        		"   ,poundageamt          = ?   "+
		        		"   ,remittername         = ?   "+
		        		"   ,remitteraccount      = ?   "+
		        		"   ,createbank           = ?   "+
		        		"   ,createbankname       = ?   "+
		        		"   ,payeename            = ?   "+
		        		"   ,payeeaccbankname     = ?   "+
		        		"   ,payeeaccbankcode     = ?   "+
		        		"   ,payeeacc             = ?   "+
		        		"   ,dftno                = ?   "+
		        		"   ,autosortresult       = ?   "+
		        		"   ,lowriskflag          = ?   "+
		        		"   ,operdatetime         = ?   "+
		        		"   ,item                 = ?   "+
		        		"   ,mainbusiness         = ?   "+
		        		"   ,operator             = ?   "+
		        		"   ,loanmanager          = ?   "+
		        		"   ,revoldtimes          = ?   "+
		        		"   ,firstrevolddate      = ?   "+
		        		"   ,loancardflag         = ?   "+
		        		"   ,istrans              = ?   "+
		        		"   ,firstmanager         = ?   "+
		        		"   ,isouttableloan       = ?   "+
		        		"   ,transout             = ?   "+
		        		"   ,iffnongcard          = ?   "+
		        		"   ,loanchannel          = ?   "+
		        		"   ,repaychannel         = ?   "+
		        		"   ,selfhelploannet      = ?   "+
		        		"   ,selfhelprepaynet     = ?   "+
		        		"   ,guarantornames       = ?   "+
		        		"   ,lmtcode              = ?   "+
		        		"   ,usemode              = ?   "+
		        		"   ,contractusemode      = ?   "+
		        		"   ,clreqlmt             = ?   "+
		        		"   ,busiflag             = ?   "+
		        		"   ,create_time          = ?   "+
		        		" where  trim(busicode) 	      = ?   ";

		        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){
		            public void setValues(PreparedStatement ps,int i)throws SQLException
		            {
		            	 ps.setString(1, ((Map<String, Object>)shopsList.get(i)).get("busicode").toString()); 
			        	 ps.setString(2, ((Map<String, Object>)shopsList.get(i)).get("busitype").toString());
			        	 ps.setString(3, ((Map<String, Object>)shopsList.get(i)).get("loanaccount").toString());
			        	 ps.setString(4, ((Map<String, Object>)shopsList.get(i)).get("bmcfg").toString());
			        	 ps.setString(5, ((Map<String, Object>)shopsList.get(i)).get("cnlno").toString());
			        	 ps.setString(6, ((Map<String, Object>)shopsList.get(i)).get("dtlnam").toString());
			        	 ps.setString(7, ((Map<String, Object>)shopsList.get(i)).get("custid").toString());
			        	 ps.setString(8, ((Map<String, Object>)shopsList.get(i)).get("cname").toString());
			        	 ps.setString(9, ((Map<String, Object>)shopsList.get(i)).get("typeid").toString());
			        	 ps.setString(10, ((Map<String, Object>)shopsList.get(i)).get("custtype").toString());
			        	 ps.setString(11, ((Map<String, Object>)shopsList.get(i)).get("custidtype").toString());
			        	 ps.setString(12, ((Map<String, Object>)shopsList.get(i)).get("custidno").toString());
			        	 ps.setString(13, ((Map<String, Object>)shopsList.get(i)).get("brhid").toString());
			        	 ps.setString(14, ((Map<String, Object>)shopsList.get(i)).get("custproperty").toString());
			        	 ps.setString(15, ((Map<String, Object>)shopsList.get(i)).get("unitcusttype").toString());
			        	 ps.setString(16, ((Map<String, Object>)shopsList.get(i)).get("industry").toString());
			        	 ps.setString(17, ((Map<String, Object>)shopsList.get(i)).get("city").toString());
			        	 ps.setString(18, ((Map<String, Object>)shopsList.get(i)).get("districtcounty").toString());
			        	 ps.setString(19, ((Map<String, Object>)shopsList.get(i)).get("town").toString());
			        	 ps.setString(20, ((Map<String, Object>)shopsList.get(i)).get("community").toString());
			        	 ps.setString(21, ((Map<String, Object>)shopsList.get(i)).get("instcode").toString());
			        	 ps.setString(22, ((Map<String, Object>)shopsList.get(i)).get("deptcode").toString());
			        	 ps.setString(23, ((Map<String, Object>)shopsList.get(i)).get("instcitycode").toString());
			        	 ps.setString(24, ((Map<String, Object>)shopsList.get(i)).get("telephone").toString());
			        	 ps.setString(25, ((Map<String, Object>)shopsList.get(i)).get("syaddr").toString());
			        	 ps.setString(26, ((Map<String, Object>)shopsList.get(i)).get("messageaddr").toString());
			        	 ps.setString(27, ((Map<String, Object>)shopsList.get(i)).get("relaman").toString());
			        	 ps.setString(28, ((Map<String, Object>)shopsList.get(i)).get("isreferagricultural").toString());
			        	 ps.setString(29, ((Map<String, Object>)shopsList.get(i)).get("isrelamanloan").toString());
			        	 ps.setString(30, ((Map<String, Object>)shopsList.get(i)).get("enterprisestatus").toString());
			        	 ps.setString(31, ((Map<String, Object>)shopsList.get(i)).get("ecoattr").toString());
			        	 ps.setString(32, ((Map<String, Object>)shopsList.get(i)).get("custscale").toString());
			        	 ps.setString(33, ((Map<String, Object>)shopsList.get(i)).get("controlmode").toString());
			        	 ps.setString(34, ((Map<String, Object>)shopsList.get(i)).get("contractno").toString());
			        	 ps.setString(35, ((Map<String, Object>)shopsList.get(i)).get("contractmoney").toString());
			        	 ps.setString(36, ((Map<String, Object>)shopsList.get(i)).get("delaytype").toString());
			        	 ps.setString(37, ((Map<String, Object>)shopsList.get(i)).get("loantype").toString());
			        	 ps.setString(38, ((Map<String, Object>)shopsList.get(i)).get("mainassure").toString());
			        	 ps.setString(39, ((Map<String, Object>)shopsList.get(i)).get("assuremanname").toString());
			        	 ps.setString(40, ((Map<String, Object>)shopsList.get(i)).get("creditlevel").toString());
			        	 ps.setString(41, ((Map<String, Object>)shopsList.get(i)).get("reqlmt").toString());
			        	 ps.setString(42, ((Map<String, Object>)shopsList.get(i)).get("isexp").toString());
			        	 ps.setString(43, ((Map<String, Object>)shopsList.get(i)).get("purposeremark").toString());
			        	 ps.setString(44, ((Map<String, Object>)shopsList.get(i)).get("busimanager").toString());
			        	 ps.setString(45, ((Map<String, Object>)shopsList.get(i)).get("assistbusimanager").toString());
			        	 ps.setString(46, ((Map<String, Object>)shopsList.get(i)).get("firstloandate").toString());
			        	 ps.setString(47, ((Map<String, Object>)shopsList.get(i)).get("repayaccount1").toString());
			        	 ps.setString(48, ((Map<String, Object>)shopsList.get(i)).get("repayaccount2").toString());
			        	 ps.setString(49, ((Map<String, Object>)shopsList.get(i)).get("repayaccount3").toString());
			        	 ps.setString(50, ((Map<String, Object>)shopsList.get(i)).get("loandate").toString());
			        	 ps.setString(51, ((Map<String, Object>)shopsList.get(i)).get("startdate").toString());
			        	 ps.setString(52, ((Map<String, Object>)shopsList.get(i)).get("orienddate").toString());
			        	 ps.setString(53, ((Map<String, Object>)shopsList.get(i)).get("enddate").toString());
			        	 ps.setString(54, ((Map<String, Object>)shopsList.get(i)).get("limit").toString());
			        	 ps.setString(55, ((Map<String, Object>)shopsList.get(i)).get("limittype").toString());
			        	 ps.setString(56, ((Map<String, Object>)shopsList.get(i)).get("ratetype").toString());
			        	 ps.setString(57, ((Map<String, Object>)shopsList.get(i)).get("floattype").toString());
			        	 ps.setString(58, ((Map<String, Object>)shopsList.get(i)).get("baserate").toString());
			        	 ps.setString(59, ((Map<String, Object>)shopsList.get(i)).get("baseratetype").toString());
			        	 ps.setString(60, ((Map<String, Object>)shopsList.get(i)).get("crtrate").toString());
			        	 ps.setString(61, ((Map<String, Object>)shopsList.get(i)).get("interest").toString());
			        	 ps.setString(62, ((Map<String, Object>)shopsList.get(i)).get("floatrate").toString());
			        	 ps.setString(63, ((Map<String, Object>)shopsList.get(i)).get("money").toString());
			        	 ps.setString(64, ((Map<String, Object>)shopsList.get(i)).get("currency").toString());
			        	 ps.setString(65, ((Map<String, Object>)shopsList.get(i)).get("balamt").toString());
			        	 ps.setString(66, ((Map<String, Object>)shopsList.get(i)).get("loanpurpose").toString());
			        	 ps.setString(67, ((Map<String, Object>)shopsList.get(i)).get("accountstate").toString());
			        	 ps.setString(68, ((Map<String, Object>)shopsList.get(i)).get("closedate").toString());
			        	 ps.setString(69, ((Map<String, Object>)shopsList.get(i)).get("notcounttype").toString());
			        	 ps.setString(70, ((Map<String, Object>)shopsList.get(i)).get("ofiveclass").toString());
			        	 ps.setString(71, ((Map<String, Object>)shopsList.get(i)).get("fiveclass").toString());
			        	 ps.setString(72, ((Map<String, Object>)shopsList.get(i)).get("otenclass").toString());
			        	 ps.setString(73, ((Map<String, Object>)shopsList.get(i)).get("tenclass").toString());
			        	 ps.setString(74, ((Map<String, Object>)shopsList.get(i)).get("pastratescale").toString());
			        	 ps.setString(75, ((Map<String, Object>)shopsList.get(i)).get("repaytype").toString());
			        	 ps.setString(76, ((Map<String, Object>)shopsList.get(i)).get("repaymethod").toString());
			        	 ps.setString(77, ((Map<String, Object>)shopsList.get(i)).get("delayamtdays").toString());
			        	 ps.setString(78, ((Map<String, Object>)shopsList.get(i)).get("delayinterestdays").toString());
			        	 ps.setString(79, ((Map<String, Object>)shopsList.get(i)).get("firstinterestdate").toString());
			        	 ps.setString(80, ((Map<String, Object>)shopsList.get(i)).get("debtinterest").toString());
			        	 ps.setString(81, ((Map<String, Object>)shopsList.get(i)).get("debtinteresttimes").toString());
			        	 ps.setString(82, ((Map<String, Object>)shopsList.get(i)).get("normalaccno").toString());
			        	 ps.setString(83, ((Map<String, Object>)shopsList.get(i)).get("normalamt").toString());
			        	 ps.setString(84, ((Map<String, Object>)shopsList.get(i)).get("dlayaccno").toString());
			        	 ps.setString(85, ((Map<String, Object>)shopsList.get(i)).get("dlayamt").toString());
			        	 ps.setString(86, ((Map<String, Object>)shopsList.get(i)).get("dlaydt").toString());
			        	 ps.setString(87, ((Map<String, Object>)shopsList.get(i)).get("tovdlyaccno").toString());
			        	 ps.setString(88, ((Map<String, Object>)shopsList.get(i)).get("tovdlyamt").toString());
			        	 ps.setString(89, ((Map<String, Object>)shopsList.get(i)).get("tovdlydt").toString());
			        	 ps.setString(90, ((Map<String, Object>)shopsList.get(i)).get("paydebt").toString());
			        	 ps.setString(91, ((Map<String, Object>)shopsList.get(i)).get("indebtamt").toString());
			        	 ps.setString(92, ((Map<String, Object>)shopsList.get(i)).get("outdebtamt").toString());
			        	 ps.setString(93, ((Map<String, Object>)shopsList.get(i)).get("debtinterestflag").toString());
			        	 ps.setString(94, ((Map<String, Object>)shopsList.get(i)).get("paymode").toString());
			        	 ps.setString(95, ((Map<String, Object>)shopsList.get(i)).get("squareinterestmode").toString());
			        	 ps.setString(96, ((Map<String, Object>)shopsList.get(i)).get("squarestate").toString());
			        	 ps.setString(97, ((Map<String, Object>)shopsList.get(i)).get("isadm").toString());
			        	 ps.setString(98, ((Map<String, Object>)shopsList.get(i)).get("admamt").toString());
			        	 ps.setString(99, ((Map<String, Object>)shopsList.get(i)).get("badtimepriod").toString());
			        	 ps.setString(100, ((Map<String, Object>)shopsList.get(i)).get("badloandate").toString());
			        	 ps.setString(101, ((Map<String, Object>)shopsList.get(i)).get("tobadloanreason").toString());
			        	 ps.setString(102, ((Map<String, Object>)shopsList.get(i)).get("draftstatus").toString());
			        	 ps.setString(103, ((Map<String, Object>)shopsList.get(i)).get("bailamt").toString());
			        	 ps.setString(104, ((Map<String, Object>)shopsList.get(i)).get("assurerate").toString());
			        	 ps.setString(105, ((Map<String, Object>)shopsList.get(i)).get("bailacc").toString());
			        	 ps.setString(106, ((Map<String, Object>)shopsList.get(i)).get("bailcurtype").toString());
			        	 ps.setString(107, ((Map<String, Object>)shopsList.get(i)).get("poundagerate").toString());
			        	 ps.setString(108, ((Map<String, Object>)shopsList.get(i)).get("poundagecurtype").toString());
			        	 ps.setString(109, ((Map<String, Object>)shopsList.get(i)).get("poundageamt").toString());
			        	 ps.setString(110, ((Map<String, Object>)shopsList.get(i)).get("remittername").toString());
			        	 ps.setString(111, ((Map<String, Object>)shopsList.get(i)).get("remitteraccount").toString());
			        	 ps.setString(112, ((Map<String, Object>)shopsList.get(i)).get("createbank").toString());
			        	 ps.setString(113, ((Map<String, Object>)shopsList.get(i)).get("createbankname").toString());
			        	 ps.setString(114, ((Map<String, Object>)shopsList.get(i)).get("payeename").toString());
			        	 ps.setString(115, ((Map<String, Object>)shopsList.get(i)).get("payeeaccbankname").toString());
			        	 ps.setString(116, ((Map<String, Object>)shopsList.get(i)).get("payeeaccbankcode").toString());
			        	 ps.setString(117, ((Map<String, Object>)shopsList.get(i)).get("payeeacc").toString());
			        	 ps.setString(118, ((Map<String, Object>)shopsList.get(i)).get("dftno").toString());
			        	 ps.setString(119, ((Map<String, Object>)shopsList.get(i)).get("autosortresult").toString());
			        	 ps.setString(120, ((Map<String, Object>)shopsList.get(i)).get("lowriskflag").toString());
			        	 ps.setString(121, ((Map<String, Object>)shopsList.get(i)).get("operdatetime").toString());
			        	 ps.setString(122, ((Map<String, Object>)shopsList.get(i)).get("item").toString());
			        	 ps.setString(123, ((Map<String, Object>)shopsList.get(i)).get("mainbusiness").toString());
			        	 ps.setString(124, ((Map<String, Object>)shopsList.get(i)).get("operator").toString());
			        	 ps.setString(125, ((Map<String, Object>)shopsList.get(i)).get("loanmanager").toString());
			        	 ps.setString(126, ((Map<String, Object>)shopsList.get(i)).get("revoldtimes").toString());
			        	 ps.setString(127, ((Map<String, Object>)shopsList.get(i)).get("firstrevolddate").toString());
			        	 ps.setString(128, ((Map<String, Object>)shopsList.get(i)).get("loancardflag").toString());
			        	 ps.setString(129, ((Map<String, Object>)shopsList.get(i)).get("istrans").toString());
			        	 ps.setString(130, ((Map<String, Object>)shopsList.get(i)).get("firstmanager").toString());
			        	 ps.setString(131, ((Map<String, Object>)shopsList.get(i)).get("isouttableloan").toString());
			        	 ps.setString(132, ((Map<String, Object>)shopsList.get(i)).get("transout").toString());
			        	 ps.setString(133, ((Map<String, Object>)shopsList.get(i)).get("iffnongcard").toString());
			        	 ps.setString(134, ((Map<String, Object>)shopsList.get(i)).get("loanchannel").toString());
			        	 ps.setString(135, ((Map<String, Object>)shopsList.get(i)).get("repaychannel").toString());
			        	 ps.setString(136, ((Map<String, Object>)shopsList.get(i)).get("selfhelploannet").toString());
			        	 ps.setString(137, ((Map<String, Object>)shopsList.get(i)).get("selfhelprepaynet").toString());
			        	 ps.setString(138, ((Map<String, Object>)shopsList.get(i)).get("guarantornames").toString());
			        	 ps.setString(139, ((Map<String, Object>)shopsList.get(i)).get("lmtcode").toString());
			        	 ps.setString(140, ((Map<String, Object>)shopsList.get(i)).get("usemode").toString());
			        	 ps.setString(141, ((Map<String, Object>)shopsList.get(i)).get("contractusemode").toString());
			        	 ps.setString(142, ((Map<String, Object>)shopsList.get(i)).get("clreqlmt").toString());
			        	 ps.setString(143, ((Map<String, Object>)shopsList.get(i)).get("busiflag").toString());
			        	 ps.setString(144, ((Map<String, Object>)shopsList.get(i)).get("createTime").toString());
			        	 ps.setString(145, ((Map<String, Object>)shopsList.get(i)).get("busicode").toString()); 
		            }
		            public int getBatchSize()
		            {
		                return shopsList.size();
		            }
		        });
			
		}
		
		
		
		
		//台账——贷款卡片  增量
		public void insertMILOANCARD(List<Map<String, Object>> list){
		   final List<Map<String, Object>> shopsList = list;
		 String sql = "insert into T_MILOANCARD(busicode,           		 "+
				   "                            busitype,                    "+
				   "                            loanaccount,                 "+
				   "                            bmcfg,                       "+
				   "                            cnlno,                       "+
				   "                            dtlnam,                      "+
				   "                            custid,                      "+
				   "                            cname,                       "+
				   "                            typeid,                      "+
				   "                            custtype,                    "+
				   "                            custidtype,                  "+
				   "                            custidno,                    "+
				   "                            brhid,                       "+
				   "                            custproperty,                "+
				   "                            unitcusttype,                "+
				   "                            industry,                    "+
				   "                            city,                        "+
				   "                            districtcounty,              "+
				   "                            town,                        "+
				   "                            community,                   "+
				   "                            instcode,                    "+
				   "                            deptcode,                    "+
				   "                            instcitycode,                "+
				   "                            telephone,                   "+
				   "                            syaddr,                      "+
				   "                            messageaddr,                 "+
				   "                            relaman,                     "+
				   "                            isreferagricultural,         "+
				   "                            isrelamanloan,               "+
				   "                            enterprisestatus,            "+
				   "                            ecoattr,                     "+
				   "                            custscale,                   "+
				   "                            controlmode,                 "+
				   "                            contractno,                  "+
				   "                            contractmoney,               "+
				   "                            delaytype,                   "+
				   "                            loantype,                    "+
				   "                            mainassure,                  "+
				   "                            assuremanname,               "+
				   "                            creditlevel,                 "+
				   "                            reqlmt,                      "+
				   "                            isexp,                       "+
				   "                            purposeremark,               "+
				   "                            busimanager,                 "+
				   "                            assistbusimanager,           "+
				   "                            firstloandate,               "+
				   "                            repayaccount1,               "+
				   "                            repayaccount2,               "+
				   "                            repayaccount3,               "+
				   "                            loandate,                    "+
				   "                            startdate,                   "+
				   "                            orienddate,                  "+
				   "                            enddate,                     "+
				   "                            limit,                       "+
				   "                            limittype,                   "+
				   "                            ratetype,                    "+
				   "                            floattype,                   "+
				   "                            baserate,                    "+
				   "                            baseratetype,                "+
				   "                            crtrate,                     "+
				   "                            interest,                    "+
				   "                            floatrate,                   "+
				   "                            money,                       "+
				   "                            currency,                    "+
				   "                            balamt,                      "+
				   "                            loanpurpose,                 "+
				   "                            accountstate,                "+
				   "                            closedate,                   "+
				   "                            notcounttype,                "+
				   "                            ofiveclass,                  "+
				   "                            fiveclass,                   "+
				   "                            otenclass,                   "+
				   "                            tenclass,                    "+
				   "                            pastratescale,               "+
				   "                            repaytype,                   "+
				   "                            repaymethod,                 "+
				   "                            delayamtdays,                "+
				   "                            delayinterestdays,           "+
				   "                            firstinterestdate,           "+
				   "                            debtinterest,                "+
				   "                            debtinteresttimes,           "+
				   "                            normalaccno,                 "+
				   "                            normalamt,                   "+
				   "                            dlayaccno,                   "+
				   "                            dlayamt,                     "+
				   "                            dlaydt,                      "+
				   "                            tovdlyaccno,                 "+
				   "                            tovdlyamt,                   "+
				   "                            tovdlydt,                    "+
				   "                            paydebt,                     "+
				   "                            indebtamt,                   "+
				   "                            outdebtamt,                  "+
				   "                            debtinterestflag,            "+
				   "                            paymode,                     "+
				   "                            squareinterestmode,          "+
				   "                            squarestate,                 "+
				   "                            badtimepriod,                "+
				   "                            badloandate,                 "+
				   "                            tobadloanreason,             "+
				   "                            autosortresult,              "+
				   "                            lowriskflag,                 "+
				   "                            operdatetime,                "+
				   "                            operator,                    "+
				   "                            mainbusiness,                "+
				   "                            revoldtimes,                 "+
				   "                            firstrevolddate,             "+
				   "                            loancardflag,                "+
				   "                            istrans,                     "+
				   "                            item,                        "+
				   "                            firstmanager,                "+
				   "                            batchflag,                   "+
				   "                            contactmobiletel,            "+
				   "                            isouttableloan,              "+
				   "                            loanpurposeremark,           "+
				   "                            iffnongcard,                 "+
				   "                            loanchannel,                 "+
				   "                            repaychannel,                "+
				   "                            selfhelploannet,             "+
				   "                            selfhelprepaynet,            "+
				   "                            guarantornames,              "+
				   "                            lmtcode,                     "+
				   "                            usemode,                     "+
				   "                            contractusemode,             "+
				   "                            clreqlmt,                    "+
				   "                            busiflag,CREATE_TIME)        "+
				   "   values(?,                                             "+
				   "   				?,                                       "+
				   "   				?,                                       "+
				   "   				?,                                       "+
				   "   				?,                                       "+
				   "   				?,                                       "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,                                             "+
				   "          ?,?)                                             ";

		      jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){
		        public void setValues(PreparedStatement ps,int i)throws SQLException
		           {
		        	ps.setString(1, ((Map<String, Object>)shopsList.get(i)).get("busicode").toString());
		            ps.setString(2, ((Map<String, Object>)shopsList.get(i)).get("busitype").toString());
		            ps.setString(3, ((Map<String, Object>)shopsList.get(i)).get("loanaccount").toString());
		            ps.setString(4, ((Map<String, Object>)shopsList.get(i)).get("bmcfg").toString());
		            ps.setString(5, ((Map<String, Object>)shopsList.get(i)).get("cnlno").toString());
		            ps.setString(6, ((Map<String, Object>)shopsList.get(i)).get("dtlnam").toString());
		            ps.setString(7, ((Map<String, Object>)shopsList.get(i)).get("custid").toString());
		            ps.setString(8, ((Map<String, Object>)shopsList.get(i)).get("cname").toString());
		            ps.setString(9, ((Map<String, Object>)shopsList.get(i)).get("typeid").toString());
		            ps.setString(10, ((Map<String, Object>)shopsList.get(i)).get("custtype").toString());
		            ps.setString(11, ((Map<String, Object>)shopsList.get(i)).get("custidtype").toString());
		            ps.setString(12, ((Map<String, Object>)shopsList.get(i)).get("custidno").toString());
		            ps.setString(13, ((Map<String, Object>)shopsList.get(i)).get("brhid").toString());
		            ps.setString(14, ((Map<String, Object>)shopsList.get(i)).get("custproperty").toString());
		            ps.setString(15, ((Map<String, Object>)shopsList.get(i)).get("unitcusttype").toString());
		            ps.setString(16, ((Map<String, Object>)shopsList.get(i)).get("industry").toString());
		            ps.setString(17, ((Map<String, Object>)shopsList.get(i)).get("city").toString());
		            ps.setString(18, ((Map<String, Object>)shopsList.get(i)).get("districtcounty").toString());
		            ps.setString(19, ((Map<String, Object>)shopsList.get(i)).get("town").toString());
		            ps.setString(20, ((Map<String, Object>)shopsList.get(i)).get("community").toString());
		            ps.setString(21, ((Map<String, Object>)shopsList.get(i)).get("instcode").toString());
		            ps.setString(22, ((Map<String, Object>)shopsList.get(i)).get("deptcode").toString());
		            ps.setString(23, ((Map<String, Object>)shopsList.get(i)).get("instcitycode").toString());
		            ps.setString(24, ((Map<String, Object>)shopsList.get(i)).get("telephone").toString());
		            ps.setString(25, ((Map<String, Object>)shopsList.get(i)).get("syaddr").toString());
		            ps.setString(26, ((Map<String, Object>)shopsList.get(i)).get("messageaddr").toString());
		            ps.setString(27, ((Map<String, Object>)shopsList.get(i)).get("relaman").toString());
		            ps.setString(28, ((Map<String, Object>)shopsList.get(i)).get("isreferagricultural").toString());
		            ps.setString(29, ((Map<String, Object>)shopsList.get(i)).get("isrelamanloan").toString());
		            ps.setString(30, ((Map<String, Object>)shopsList.get(i)).get("enterprisestatus").toString());
		            ps.setString(31, ((Map<String, Object>)shopsList.get(i)).get("ecoattr").toString());
		            ps.setString(32, ((Map<String, Object>)shopsList.get(i)).get("custscale").toString());
		            ps.setString(33, ((Map<String, Object>)shopsList.get(i)).get("controlmode").toString());
		            ps.setString(34, ((Map<String, Object>)shopsList.get(i)).get("contractno").toString());
		            ps.setString(35, ((Map<String, Object>)shopsList.get(i)).get("contractmoney").toString());
		            ps.setString(36, ((Map<String, Object>)shopsList.get(i)).get("delaytype").toString());
		            ps.setString(37, ((Map<String, Object>)shopsList.get(i)).get("loantype").toString());
		            ps.setString(38, ((Map<String, Object>)shopsList.get(i)).get("mainassure").toString());
		            ps.setString(39, ((Map<String, Object>)shopsList.get(i)).get("assuremanname").toString());
		            ps.setString(40, ((Map<String, Object>)shopsList.get(i)).get("creditlevel").toString());
		            ps.setString(41, ((Map<String, Object>)shopsList.get(i)).get("reqlmt").toString());
		            ps.setString(42, ((Map<String, Object>)shopsList.get(i)).get("isexp").toString());
		            ps.setString(43, ((Map<String, Object>)shopsList.get(i)).get("purposeremark").toString());
		            ps.setString(44, ((Map<String, Object>)shopsList.get(i)).get("busimanager").toString());
		            ps.setString(45, ((Map<String, Object>)shopsList.get(i)).get("assistbusimanager").toString());
		            ps.setString(46, ((Map<String, Object>)shopsList.get(i)).get("firstloandate").toString());
		            ps.setString(47, ((Map<String, Object>)shopsList.get(i)).get("repayaccount1").toString());
		            ps.setString(48, ((Map<String, Object>)shopsList.get(i)).get("repayaccount2").toString());
		            ps.setString(49, ((Map<String, Object>)shopsList.get(i)).get("repayaccount3").toString());
		            ps.setString(50, ((Map<String, Object>)shopsList.get(i)).get("loandate").toString());
		            ps.setString(51, ((Map<String, Object>)shopsList.get(i)).get("startdate").toString());
		            ps.setString(52, ((Map<String, Object>)shopsList.get(i)).get("orienddate").toString());
		            ps.setString(53, ((Map<String, Object>)shopsList.get(i)).get("enddate").toString());
		            ps.setString(54, ((Map<String, Object>)shopsList.get(i)).get("limit").toString());
		            ps.setString(55, ((Map<String, Object>)shopsList.get(i)).get("limittype").toString());
		            ps.setString(56, ((Map<String, Object>)shopsList.get(i)).get("ratetype").toString());
		            ps.setString(57, ((Map<String, Object>)shopsList.get(i)).get("floattype").toString());
		            ps.setString(58, ((Map<String, Object>)shopsList.get(i)).get("baserate").toString());
		            ps.setString(59, ((Map<String, Object>)shopsList.get(i)).get("baseratetype").toString());
		            ps.setString(60, ((Map<String, Object>)shopsList.get(i)).get("crtrate").toString());
		            ps.setString(61, ((Map<String, Object>)shopsList.get(i)).get("interest").toString());
		            ps.setString(62, ((Map<String, Object>)shopsList.get(i)).get("floatrate").toString());
		            ps.setString(63, ((Map<String, Object>)shopsList.get(i)).get("money").toString());
		            ps.setString(64, ((Map<String, Object>)shopsList.get(i)).get("currency").toString());
		            ps.setString(65, ((Map<String, Object>)shopsList.get(i)).get("balamt").toString());
		            ps.setString(66, ((Map<String, Object>)shopsList.get(i)).get("loanpurpose").toString());
		            ps.setString(67, ((Map<String, Object>)shopsList.get(i)).get("accountstate").toString());
		            ps.setString(68, ((Map<String, Object>)shopsList.get(i)).get("closedate").toString());
		            ps.setString(69, ((Map<String, Object>)shopsList.get(i)).get("notcounttype").toString());
		            ps.setString(70, ((Map<String, Object>)shopsList.get(i)).get("ofiveclass").toString());
		            ps.setString(71, ((Map<String, Object>)shopsList.get(i)).get("fiveclass").toString());
		            ps.setString(72, ((Map<String, Object>)shopsList.get(i)).get("otenclass").toString());
		            ps.setString(73, ((Map<String, Object>)shopsList.get(i)).get("tenclass").toString());
		            ps.setString(74, ((Map<String, Object>)shopsList.get(i)).get("pastratescale").toString());
		            ps.setString(75, ((Map<String, Object>)shopsList.get(i)).get("repaytype").toString());
		            ps.setString(76, ((Map<String, Object>)shopsList.get(i)).get("repaymethod").toString());
		            ps.setString(77, ((Map<String, Object>)shopsList.get(i)).get("delayamtdays").toString());
		            ps.setString(78, ((Map<String, Object>)shopsList.get(i)).get("delayinterestdays").toString());
		            ps.setString(79, ((Map<String, Object>)shopsList.get(i)).get("firstinterestdate").toString());
		            ps.setString(80, ((Map<String, Object>)shopsList.get(i)).get("debtinterest").toString());
		            ps.setString(81, ((Map<String, Object>)shopsList.get(i)).get("debtinteresttimes").toString());
		            ps.setString(82, ((Map<String, Object>)shopsList.get(i)).get("normalaccno").toString());
		            ps.setString(83, ((Map<String, Object>)shopsList.get(i)).get("normalamt").toString());
		            ps.setString(84, ((Map<String, Object>)shopsList.get(i)).get("dlayaccno").toString());
		            ps.setString(85, ((Map<String, Object>)shopsList.get(i)).get("dlayamt").toString());
		            ps.setString(86, ((Map<String, Object>)shopsList.get(i)).get("dlaydt").toString());
		            ps.setString(87, ((Map<String, Object>)shopsList.get(i)).get("tovdlyaccno").toString());
		            ps.setString(88, ((Map<String, Object>)shopsList.get(i)).get("tovdlyamt").toString());
		            ps.setString(89, ((Map<String, Object>)shopsList.get(i)).get("tovdlydt").toString());
		            ps.setString(90, ((Map<String, Object>)shopsList.get(i)).get("paydebt").toString());
		            ps.setString(91, ((Map<String, Object>)shopsList.get(i)).get("indebtamt").toString());
		            ps.setString(92, ((Map<String, Object>)shopsList.get(i)).get("outdebtamt").toString());
		            ps.setString(93, ((Map<String, Object>)shopsList.get(i)).get("debtinterestflag").toString());
		            ps.setString(94, ((Map<String, Object>)shopsList.get(i)).get("paymode").toString());
		            ps.setString(95, ((Map<String, Object>)shopsList.get(i)).get("squareinterestmode").toString());
		            ps.setString(96, ((Map<String, Object>)shopsList.get(i)).get("squarestate").toString());
		            ps.setString(97, ((Map<String, Object>)shopsList.get(i)).get("badtimepriod").toString());
		            ps.setString(98, ((Map<String, Object>)shopsList.get(i)).get("badloandate").toString());
		            ps.setString(99, ((Map<String, Object>)shopsList.get(i)).get("tobadloanreason").toString());
		            ps.setString(100, ((Map<String, Object>)shopsList.get(i)).get("autosortresult").toString());
		            ps.setString(101, ((Map<String, Object>)shopsList.get(i)).get("lowriskflag").toString());
		            ps.setString(102, ((Map<String, Object>)shopsList.get(i)).get("operdatetime").toString());
		            ps.setString(103, ((Map<String, Object>)shopsList.get(i)).get("operator").toString());
		            ps.setString(104, ((Map<String, Object>)shopsList.get(i)).get("mainbusiness").toString());
		            ps.setString(105, ((Map<String, Object>)shopsList.get(i)).get("revoldtimes").toString());
		            ps.setString(106, ((Map<String, Object>)shopsList.get(i)).get("firstrevolddate").toString());
		            ps.setString(107, ((Map<String, Object>)shopsList.get(i)).get("loancardflag").toString());
		            ps.setString(108, ((Map<String, Object>)shopsList.get(i)).get("istrans").toString());
		            ps.setString(109, ((Map<String, Object>)shopsList.get(i)).get("item").toString());
		            ps.setString(110, ((Map<String, Object>)shopsList.get(i)).get("firstmanager").toString());
		            ps.setString(111, ((Map<String, Object>)shopsList.get(i)).get("batchflag").toString());
		            ps.setString(112, ((Map<String, Object>)shopsList.get(i)).get("contactmobiletel").toString());
		            ps.setString(113, ((Map<String, Object>)shopsList.get(i)).get("isouttableloan").toString());
		            ps.setString(114, ((Map<String, Object>)shopsList.get(i)).get("loanpurposeremark").toString());
		            ps.setString(115, ((Map<String, Object>)shopsList.get(i)).get("iffnongcard").toString());
		            ps.setString(116, ((Map<String, Object>)shopsList.get(i)).get("loanchannel").toString());
		            ps.setString(117, ((Map<String, Object>)shopsList.get(i)).get("repaychannel").toString());
		            ps.setString(118, ((Map<String, Object>)shopsList.get(i)).get("selfhelploannet").toString());
		            ps.setString(119, ((Map<String, Object>)shopsList.get(i)).get("selfhelprepaynet").toString());
		            ps.setString(120, ((Map<String, Object>)shopsList.get(i)).get("guarantornames").toString());
		            ps.setString(121, ((Map<String, Object>)shopsList.get(i)).get("lmtcode").toString());
		            ps.setString(122, ((Map<String, Object>)shopsList.get(i)).get("usemode").toString());
		            ps.setString(123, ((Map<String, Object>)shopsList.get(i)).get("contractusemode").toString());
		            ps.setString(124, ((Map<String, Object>)shopsList.get(i)).get("clreqlmt").toString());
		            ps.setString(125, ((Map<String, Object>)shopsList.get(i)).get("busiflag").toString());
		            ps.setString(126, ((Map<String, Object>)shopsList.get(i)).get("createTime").toString());
		           }
		           public int getBatchSize()
		           {
		            return shopsList.size();
		           }
		     });
		}
		
		
		
		
		
		//黑名单客户结果表  增量
		public void insertBWLIST(List<Map<String, Object>> list){
		   final List<Map<String, Object>> shopsList = list;
		   String sql = "insert into T_PARTY_BWLIST (id,               "+
				   "                                partyid,           "+
				   "                                partyinitid,       "+
				   "                                partytypeid,       "+
				   "                                custid,            "+
				   "                                custstatus,        "+
				   "                                blackreason,       "+
				   "                                otherreason,       "+
				   "                                busimanager,       "+
				   "                                instcode,          "+
				   "                                deptcode,          "+
				   "                                instcitycode,      "+
				   "                                causeremark,       "+
				   "                                createduser,       "+
				   "                                createdtime,       "+
				   "                                modifieduser,      "+
				   "                                modifiedtime,      "+
				   "                                activatestatus,    "+
				   "                                istrans,CREATE_TIME)           "+
				   "  values(?,                                        "+
				   "  			 ?,                                    "+
				   "  			 ?,                                    "+
				   "  			 ?,                                    "+
				   "  			 ?,                                    "+
				   "  			 ?,                                    "+
				   "         ?,                                        "+
				   "         ?,                                        "+
				   "         ?,                                        "+
				   "         ?,                                        "+
				   "         ?,                                        "+
				   "         ?,                                        "+
				   "         ?,                                        "+
				   "         ?,                                        "+
				   "         ?,                                        "+
				   "         ?,                                        "+
				   "         ?,                                        "+
				   "         ?,                                        "+
				   "         ?,?)                                        ";
		      jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){
		        public void setValues(PreparedStatement ps,int i)throws SQLException
		           {
		        	ps.setString(1, ((Map<String, Object>)shopsList.get(i)).get("id").toString());   
		        	ps.setString(2, ((Map<String, Object>)shopsList.get(i)).get("partyid").toString());   
		        	ps.setString(3, ((Map<String, Object>)shopsList.get(i)).get("partyinitid").toString());   
		        	ps.setString(4, ((Map<String, Object>)shopsList.get(i)).get("partytypeid").toString());   
		        	ps.setString(5, ((Map<String, Object>)shopsList.get(i)).get("custid").toString());   
		        	ps.setString(6, ((Map<String, Object>)shopsList.get(i)).get("custstatus").toString());   
		        	ps.setString(7, ((Map<String, Object>)shopsList.get(i)).get("blackreason").toString());   
		        	ps.setString(8, ((Map<String, Object>)shopsList.get(i)).get("otherreason").toString());   
		        	ps.setString(9, ((Map<String, Object>)shopsList.get(i)).get("busimanager").toString());   
		        	ps.setString(10, ((Map<String, Object>)shopsList.get(i)).get("instcode").toString());   
		        	ps.setString(11, ((Map<String, Object>)shopsList.get(i)).get("deptcode").toString());   
		        	ps.setString(12, ((Map<String, Object>)shopsList.get(i)).get("instcitycode").toString());   
		        	ps.setString(13, ((Map<String, Object>)shopsList.get(i)).get("causeremark").toString());   
		        	ps.setString(14, ((Map<String, Object>)shopsList.get(i)).get("createduser").toString());   
		        	ps.setString(15, ((Map<String, Object>)shopsList.get(i)).get("createdtime").toString());   
		        	ps.setString(16, ((Map<String, Object>)shopsList.get(i)).get("modifieduser").toString());   
		        	ps.setString(17, ((Map<String, Object>)shopsList.get(i)).get("modifiedtime").toString());   
		        	ps.setString(18, ((Map<String, Object>)shopsList.get(i)).get("activatestatus").toString());   
		        	ps.setString(19, ((Map<String, Object>)shopsList.get(i)).get("istrans").toString()); 
		        	ps.setString(20, ((Map<String, Object>)shopsList.get(i)).get("createTime").toString());  
		           }
		           public int getBatchSize()
		           {
		            return shopsList.size();
		           }
		     });
		}
		
		
		
		
		//还款情况表   增量
		public void insertRAREPAYLIST(List<Map<String, Object>> list){
		   final List<Map<String, Object>> shopsList = list;
		   String sql = "insert into T_RAREPAYLIST(keycode,            "+
				   "                                repaydate,          "+
				   "                                busicode,           "+
				   "                                repayamt,           "+
				   "                                rapayinterest,      "+
				   "                                squaremodel,        "+
				   "                                squareoper,         "+
				   "                                handleschemaid,     "+
				   "                                handletypeid,       "+
				   "                                instcode,           "+
				   "                                deptcode,           "+
				   "                                instcitycode,       "+
				   "                                operdatetime,       "+
				   "                                operator_1,           "+
				   "                                loanstatus,         "+
				   "                                istrans,CREATE_TIME)            "+
				   "   values(?,                                        "+
				   "   				?,                                  "+
				   "   				?,                                  "+
				   "   				?,                                  "+
				   "   				?,                                  "+
				   "   				?,                                  "+
				   "          ?,                                        "+
				   "          ?,                                        "+
				   "          ?,                                        "+
				   "          ?,                                        "+
				   "          ?,                                        "+
				   "          ?,                                        "+
				   "          ?,                                        "+
				   "          ?,                                        "+
				   "          ?,                                        "+
				   "          ?,?)                                        ";

		      jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){
		        public void setValues(PreparedStatement ps,int i)throws SQLException
		           {
		        	  ps.setString(1, ((Map<String, Object>)shopsList.get(i)).get("keycode").toString());
		        	  ps.setString(2, ((Map<String, Object>)shopsList.get(i)).get("repaydate").toString());
		        	  ps.setString(3, ((Map<String, Object>)shopsList.get(i)).get("busicode").toString());
		        	  ps.setString(4, ((Map<String, Object>)shopsList.get(i)).get("repayamt").toString());
		        	  ps.setString(5, ((Map<String, Object>)shopsList.get(i)).get("rapayinterest").toString());
		        	  ps.setString(6, ((Map<String, Object>)shopsList.get(i)).get("squaremodel").toString());
		        	  ps.setString(7, ((Map<String, Object>)shopsList.get(i)).get("squareoper").toString());
		        	  ps.setString(8, ((Map<String, Object>)shopsList.get(i)).get("handleschemaid").toString());
		        	  ps.setString(9, ((Map<String, Object>)shopsList.get(i)).get("handletypeid").toString());
		        	  ps.setString(10, ((Map<String, Object>)shopsList.get(i)).get("instcode").toString());
		        	  ps.setString(11, ((Map<String, Object>)shopsList.get(i)).get("deptcode").toString());
		        	  ps.setString(12, ((Map<String, Object>)shopsList.get(i)).get("instcitycode").toString());
		        	  ps.setString(13, ((Map<String, Object>)shopsList.get(i)).get("operdatetime").toString());
		        	  ps.setString(14, ((Map<String, Object>)shopsList.get(i)).get("operator").toString());
		        	  ps.setString(15, ((Map<String, Object>)shopsList.get(i)).get("loanstatus").toString());
		        	  ps.setString(16, ((Map<String, Object>)shopsList.get(i)).get("istrans").toString());
		        	  ps.setString(17, ((Map<String, Object>)shopsList.get(i)).get("createTime").toString());
		           }
		           public int getBatchSize()
		           {
		            return shopsList.size();
		           }
		     });
		}
		
		
		
		
		//不良贷款信息   增量
		public void insertSPECIALASSET(List<Map<String, Object>> list){
		   final List<Map<String, Object>> shopsList = list;
		   String sql = "insert into T_SARM_SPECIALASSET(id,                           "+
				   "                                    typeid,                        "+
				   "                                    code,                          "+
				   "                                    manresperson,                  "+
				   "                                    loanstatus,                    "+
				   "                                    isfocus,                       "+
				   "                                    transferstatus,                "+
				   "                                    forwardtransfertime,           "+
				   "                                    backwardstransfertime,         "+
				   "                                    restoredamount,                "+
				   "                                    restoredinterest,              "+
				   "                                    exitamount,                    "+
				   "                                    description,                   "+
				   "                                    credentialid,                  "+
				   "                                    credentialtype,                "+
				   "                                    ownerid,                       "+
				   "                                    ownertypeid,                   "+
				   "                                    ownercode,                     "+
				   "                                    groupid,                       "+
				   "                                    islossexit,                    "+
				   "                                    loss,                          "+
				   "                                    ispromotion,                   "+
				   "                                    focustime,                     "+
				   "                                    ormanresperson,                "+
				   "                                    originalgroupid,               "+
				   "                                    issuedate,                     "+
				   "                                    duedate,                       "+
				   "                                    loansduration,                 "+
				   "                                    grantamount,                   "+
				   "                                    interestrates,                 "+
				   "                                    guaranteeway,                  "+
				   "                                    accountstatus,                 "+
				   "                                    loanpattern,                   "+
				   "                                    emergereason,                  "+
				   "                                    emergedate,                    "+
				   "                                    modifieduser,                  "+
				   "                                    operdatetime,                  "+
				   "                                    deptcode,                      "+
				   "                                    instcode,                      "+
				   "                                    instcitycode,                  "+
				   "                                    loanbalance,                   "+
				   "                                    hostaccountmanager,            "+
				   "                                    contractid,                    "+
				   "                                    contracttypeid,                "+
				   "                                    currency,                      "+
				   "                                    loanpurpose,                   "+
				   "                                    debtinterest,                  "+
				   "                                    flowstatus,                    "+
				   "                                    firstmanager,                  "+
				   "                                    istrans,                       "+
				   "                                    tobwdate,                      "+
				   "                                    city,                          "+
				   "                                    districtcounty,                "+
				   "                                    town,                          "+
				   "                                    community,                     "+
				   "                                    contractmoney,                 "+
				   "                                    syaddr,                        "+
				   "                                    telephone,                     "+
				   "                                    closedate,                     "+
				   "                                    caseduedate,                   "+
				   "                                    brhid,                         "+
				   "                                    lmtcode,                       "+
				   "                                    usemode,                       "+
				   "                                    contractusemode,               "+
				   "                                    custidno,                      "+
				   "                                    changeamount,CREATE_TIME)                  "+
				   "    values(?,                                                      "+
				   "    			 ?,                                                "+
				   "    			 ?,                                                "+
				   "    			 ?,                                                "+
				   "    			 ?,                                                "+
				   "    			 ?,                                                "+
				   "           ?,                                                      "+
				   "           ?,                                                      "+
				   "           ?,                                                      "+
				   "           ?,                                                      "+
				   "           ?,                                                      "+
				   "           ?,                                                      "+
				   "           ?,                                                      "+
				   "           ?,                                                      "+
				   "           ?,                                                      "+
				   "           ?,                                                      "+
				   "           ?,                                                      "+
				   "           ?,                                                      "+
				   "           ?,                                                      "+
				   "           ?,                                                      "+
				   "           ?,                                                      "+
				   "           ?,                                                      "+
				   "           ?,                                                      "+
				   "           ?,                                                      "+
				   "           ?,                                                      "+
				   "           ?,                                                      "+
				   "           ?,                                                      "+
				   "           ?,                                                      "+
				   "           ?,                                                      "+
				   "           ?,                                                      "+
				   "           ?,                                                      "+
				   "           ?,                                                      "+
				   "           ?,                                                      "+
				   "           ?,                                                      "+
				   "           ?,                                                      "+
				   "           ?,                                                      "+
				   "           ?,                                                      "+
				   "           ?,                                                      "+
				   "           ?,                                                      "+
				   "           ?,                                                      "+
				   "           ?,                                                      "+
				   "           ?,                                                      "+
				   "           ?,                                                      "+
				   "           ?,                                                      "+
				   "           ?,                                                      "+
				   "           ?,                                                      "+
				   "           ?,                                                      "+
				   "           ?,                                                      "+
				   "           ?,                                                      "+
				   "           ?,                                                      "+
				   "           ?,                                                      "+
				   "           ?,                                                      "+
				   "           ?,                                                      "+
				   "           ?,                                                      "+
				   "           ?,                                                      "+
				   "           ?,                                                      "+
				   "           ?,                                                      "+
				   "           ?,                                                      "+
				   "           ?,                                                      "+
				   "           ?,                                                      "+
				   "           ?,                                                      "+
				   "           ?,                                                      "+
				   "           ?,                                                      "+
				   "           ?,                                                      "+
				   "           ?,                                                      "+
				   "           ?,?)                                                      ";

		      jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){
		        public void setValues(PreparedStatement ps,int i)throws SQLException
		           {
		        	  ps.setString(1, ((Map<String, Object>)shopsList.get(i)).get("id").toString()); 
		        	  ps.setString(2, ((Map<String, Object>)shopsList.get(i)).get("typeid").toString()); 
		        	  ps.setString(3, ((Map<String, Object>)shopsList.get(i)).get("code").toString()); 
		        	  ps.setString(4, ((Map<String, Object>)shopsList.get(i)).get("manresperson").toString()); 
		        	  ps.setString(5, ((Map<String, Object>)shopsList.get(i)).get("loanstatus").toString()); 
		        	  ps.setString(6, ((Map<String, Object>)shopsList.get(i)).get("isfocus").toString()); 
		        	  ps.setString(7, ((Map<String, Object>)shopsList.get(i)).get("transferstatus").toString()); 
		        	  ps.setString(8, ((Map<String, Object>)shopsList.get(i)).get("forwardtransfertime").toString()); 
		        	  ps.setString(9, ((Map<String, Object>)shopsList.get(i)).get("backwardstransfertime").toString()); 
		        	  ps.setString(10, ((Map<String, Object>)shopsList.get(i)).get("restoredamount").toString()); 
		        	  ps.setString(11, ((Map<String, Object>)shopsList.get(i)).get("restoredinterest").toString()); 
		        	  ps.setString(12, ((Map<String, Object>)shopsList.get(i)).get("exitamount").toString()); 
		        	  ps.setString(13, ((Map<String, Object>)shopsList.get(i)).get("description").toString()); 
		        	  ps.setString(14, ((Map<String, Object>)shopsList.get(i)).get("credentialid").toString()); 
		        	  ps.setString(15, ((Map<String, Object>)shopsList.get(i)).get("credentialtype").toString()); 
		        	  ps.setString(16, ((Map<String, Object>)shopsList.get(i)).get("ownerid").toString()); 
		        	  ps.setString(17, ((Map<String, Object>)shopsList.get(i)).get("ownertypeid").toString()); 
		        	  ps.setString(18, ((Map<String, Object>)shopsList.get(i)).get("ownercode").toString()); 
		        	  ps.setString(19, ((Map<String, Object>)shopsList.get(i)).get("groupid").toString()); 
		        	  ps.setString(20, ((Map<String, Object>)shopsList.get(i)).get("islossexit").toString()); 
		        	  ps.setString(21, ((Map<String, Object>)shopsList.get(i)).get("loss").toString()); 
		        	  ps.setString(22, ((Map<String, Object>)shopsList.get(i)).get("ispromotion").toString()); 
		        	  ps.setString(23, ((Map<String, Object>)shopsList.get(i)).get("focustime").toString()); 
		        	  ps.setString(24, ((Map<String, Object>)shopsList.get(i)).get("ormanresperson").toString()); 
		        	  ps.setString(25, ((Map<String, Object>)shopsList.get(i)).get("originalgroupid").toString()); 
		        	  ps.setString(26, ((Map<String, Object>)shopsList.get(i)).get("issuedate").toString()); 
		        	  ps.setString(27, ((Map<String, Object>)shopsList.get(i)).get("duedate").toString()); 
		        	  ps.setString(28, ((Map<String, Object>)shopsList.get(i)).get("loansduration").toString()); 
		        	  ps.setString(29, ((Map<String, Object>)shopsList.get(i)).get("grantamount").toString()); 
		        	  ps.setString(30, ((Map<String, Object>)shopsList.get(i)).get("interestrates").toString()); 
		        	  ps.setString(31, ((Map<String, Object>)shopsList.get(i)).get("guaranteeway").toString()); 
		        	  ps.setString(32, ((Map<String, Object>)shopsList.get(i)).get("accountstatus").toString()); 
		        	  ps.setString(33, ((Map<String, Object>)shopsList.get(i)).get("loanpattern").toString()); 
		        	  ps.setString(34, ((Map<String, Object>)shopsList.get(i)).get("emergereason").toString()); 
		        	  ps.setString(35, ((Map<String, Object>)shopsList.get(i)).get("emergedate").toString()); 
		        	  ps.setString(36, ((Map<String, Object>)shopsList.get(i)).get("modifieduser").toString()); 
		        	  ps.setString(37, ((Map<String, Object>)shopsList.get(i)).get("operdatetime").toString()); 
		        	  ps.setString(38, ((Map<String, Object>)shopsList.get(i)).get("deptcode").toString()); 
		        	  ps.setString(39, ((Map<String, Object>)shopsList.get(i)).get("instcode").toString()); 
		        	  ps.setString(40, ((Map<String, Object>)shopsList.get(i)).get("instcitycode").toString()); 
		        	  ps.setString(41, ((Map<String, Object>)shopsList.get(i)).get("loanbalance").toString()); 
		        	  ps.setString(42, ((Map<String, Object>)shopsList.get(i)).get("hostaccountmanager").toString()); 
		        	  ps.setString(43, ((Map<String, Object>)shopsList.get(i)).get("contractid").toString()); 
		        	  ps.setString(44, ((Map<String, Object>)shopsList.get(i)).get("contracttypeid").toString()); 
		        	  ps.setString(45, ((Map<String, Object>)shopsList.get(i)).get("currency").toString()); 
		        	  ps.setString(46, ((Map<String, Object>)shopsList.get(i)).get("loanpurpose").toString()); 
		        	  ps.setString(47, ((Map<String, Object>)shopsList.get(i)).get("debtinterest").toString()); 
		        	  ps.setString(48, ((Map<String, Object>)shopsList.get(i)).get("flowstatus").toString()); 
		        	  ps.setString(49, ((Map<String, Object>)shopsList.get(i)).get("firstmanager").toString()); 
		        	  ps.setString(50, ((Map<String, Object>)shopsList.get(i)).get("istrans").toString()); 
		        	  ps.setString(51, ((Map<String, Object>)shopsList.get(i)).get("tobwdate").toString()); 
		        	  ps.setString(52, ((Map<String, Object>)shopsList.get(i)).get("city").toString()); 
		        	  ps.setString(53, ((Map<String, Object>)shopsList.get(i)).get("districtcounty").toString()); 
		        	  ps.setString(54, ((Map<String, Object>)shopsList.get(i)).get("town").toString()); 
		        	  ps.setString(55, ((Map<String, Object>)shopsList.get(i)).get("community").toString()); 
		        	  ps.setString(56, ((Map<String, Object>)shopsList.get(i)).get("contractmoney").toString()); 
		        	  ps.setString(57, ((Map<String, Object>)shopsList.get(i)).get("syaddr").toString()); 
		        	  ps.setString(58, ((Map<String, Object>)shopsList.get(i)).get("telephone").toString()); 
		        	  ps.setString(59, ((Map<String, Object>)shopsList.get(i)).get("closedate").toString()); 
		        	  ps.setString(60, ((Map<String, Object>)shopsList.get(i)).get("caseduedate").toString()); 
		        	  ps.setString(61, ((Map<String, Object>)shopsList.get(i)).get("brhid").toString()); 
		        	  ps.setString(62, ((Map<String, Object>)shopsList.get(i)).get("lmtcode").toString()); 
		        	  ps.setString(63, ((Map<String, Object>)shopsList.get(i)).get("usemode").toString()); 
		        	  ps.setString(64, ((Map<String, Object>)shopsList.get(i)).get("contractusemode").toString()); 
		        	  ps.setString(65, ((Map<String, Object>)shopsList.get(i)).get("custidno").toString()); 
		        	  ps.setString(66, ((Map<String, Object>)shopsList.get(i)).get("changeamount").toString()); 
		        	  ps.setString(67, ((Map<String, Object>)shopsList.get(i)).get("createTime").toString());
		           }
		           public int getBatchSize()
		           {
		            return shopsList.size();
		           }
		     });
		}
		
		
		
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	//GC合同多方信息表  增量
	public void insertGCCONTRACTMULTICLIENT(List<Map<String, Object>> list){
	   final List<Map<String, Object>> shopsList = list;
	   String sql =   "insert into T_GCCONTRACTMULTICLIENT(keycode,         "+
					   "                                keytype,            "+
					   "                                gcbusinesslevel,    "+
					   "                                gcsyslevel,         "+
					   "                                sequenceno,         "+
					   "                                clienttype,         "+
					   "                                custid,             "+
					   "                                cname,              "+
					   "                                isprimaryclient,    "+
					   "                                spokesman,          "+
					   "                                inkdate,            "+
					   "                                keyeffectedstate,   "+
					   "                                keydatestate,       "+
					   "                                editionno,          "+
					   "                                instcitycode,       "+
					   "                                operdatetime,       "+
					   "                                istrans,            "+
					   "                                create_time)        "+
					   "    values(?,                                       "+
					   "    			 ?,                                 "+
					   "    			 ?,                                 "+
					   "    			 ?,                                 "+
					   "           ?,                                       "+
					   "           ?,                                       "+
					   "           ?,                                       "+
					   "           ?,                                       "+
					   "           ?,                                       "+
					   "           ?,                                       "+
					   "           ?,                                       "+
					   "           ?,                                       "+
					   "           ?,                                       "+
					   "           ?,                                       "+
					   "           ?,                                       "+
					   "           ?,                                       "+
					   "           ?,                                       "+
					   "           ?)                                       ";

	      jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){
	        public void setValues(PreparedStatement ps,int i)throws SQLException
	           {
	        	ps.setString(1, ((Map<String, Object>)shopsList.get(i)).get("keycode").toString());     
	        	ps.setString(2, ((Map<String, Object>)shopsList.get(i)).get("keytype").toString());         
	        	ps.setString(3, ((Map<String, Object>)shopsList.get(i)).get("gcbusinesslevel").toString());          
	        	ps.setString(4, ((Map<String, Object>)shopsList.get(i)).get("gcsyslevel").toString());   
	        	ps.setString(5, ((Map<String, Object>)shopsList.get(i)).get("sequenceno").toString());       
	        	ps.setString(6, ((Map<String, Object>)shopsList.get(i)).get("clienttype").toString()); 
	        	ps.setString(7, ((Map<String, Object>)shopsList.get(i)).get("custid").toString()); 
	        	ps.setString(8, ((Map<String, Object>)shopsList.get(i)).get("cname").toString());     
	        	ps.setString(9, ((Map<String, Object>)shopsList.get(i)).get("isprimaryclient").toString());   
	        	ps.setString(10, ((Map<String, Object>)shopsList.get(i)).get("spokesman").toString());   
	        	ps.setString(11, ((Map<String, Object>)shopsList.get(i)).get("inkdate").toString());     
	        	ps.setString(12, ((Map<String, Object>)shopsList.get(i)).get("keyeffectedstate").toString());      
	        	ps.setString(13, ((Map<String, Object>)shopsList.get(i)).get("keydatestate").toString());      
	        	ps.setString(14, ((Map<String, Object>)shopsList.get(i)).get("editionno").toString());  
	        	ps.setString(15, ((Map<String, Object>)shopsList.get(i)).get("instcitycode").toString());  
	        	ps.setString(16, ((Map<String, Object>)shopsList.get(i)).get("operdatetime").toString());      
	        	ps.setString(17, ((Map<String, Object>)shopsList.get(i)).get("istrans").toString());
	        	ps.setString(18, ((Map<String, Object>)shopsList.get(i)).get("createTime").toString()); 
	           }
	           public int getBatchSize()
	           {
	            return shopsList.size();
	           }
	     });
	}
	
	
	
	
	//GC合同授信品种信息表 
	public void insertGCCREDITTYPEINFORMATION(List<Map<String, Object>> list){
	   final List<Map<String, Object>> shopsList = list;
	   String sql ="insert into T_GCCREDITTYPEINFORMATION(keycode,													"+									
			   "																		  keytype, 					"+									
			   "																		  gcbusinesslevel,          "+
			   "																		  gcsyslevel,               "+
			   "																		  sequenceno,               "+
			   "																		  custid,                   "+
			   "																		  upkeycode,                "+
			   "																		  upkeytype,                "+
			   "																		  upgcbusinesslevel,        "+
			   "																		  upgcsyslevel,             "+
			   "																		  busitype,                 "+
			   "																		  money,                    "+
			   "																		  currency,                 "+
			   "																		  startdate,                "+
			   "																		  enddate,                  "+
			   "																		  limit,                    "+
			   "																		  limitunit,                "+
			   "																		  lastdate,                 "+
			   "																		  usedmethod,               "+
			   "																		  islimitcontrol,           "+
			   "																		  busicontrolsequence,      "+
			   "																		  interest,                 "+
			   "																		  interestunit,             "+
			   "																		  keyeffectedstate,         "+
			   "																		  keydatestate,             "+
			   "																		  editionno,                "+
			   "																		  appointedaccount,         "+
			   "																		  ratetype,                 "+
			   "																		  floatrate,                "+
			   "																		  rateajustcycle,           "+
			   "																		  repaymode,                "+
			   "																		  repaymethod,              "+
			   "																		  loanpurpose,              "+
			   "																		  loanpurposeremark,        "+
			   "																		  loancardid,               "+
			   "																		  busibalmoney,             "+
			   "																		  simulatekeycode,          "+
			   "																		  deptcode,                 "+
			   "																		  instcode,                 "+
			   "																		  instcitycode,             "+
			   "																		  operdatetime,             "+
			   "																		  operator,                 "+
			   "																		  istrans,                  "+
			   "																		  busimanager,              "+
			   "																		  create_time)              "+
			   "			values(?,                                                 		  "+
			   "							?,                                                "+
			   "							?,                                                "+
			   "							?,                                                "+
			   "							?,                                                "+
			   "							?,                                                "+
			   "							?,                                                "+
			   "							?,                                                "+
			   "							?,                                                "+
			   "							?,                                                "+
			   "							?,                                                "+
			   "							?,                                                "+
			   "							?,                                                "+
			   "							?,                                                "+
			   "							?,                                                "+
			   "							?,                                                "+
			   "							?,                                                "+
			   "							?,                                                "+
			   "							?,                                                "+
			   "							?,                                                "+
			   "							?,                                                "+
			   "							?,                                                "+
			   "							?,                                                "+
			   "							?,                                                "+
			   "							?,                                                "+
			   "							?,                                                "+
			   "							?,                                                "+
			   "							?,                                                "+
			   "							?,                                                "+
			   "							?,                                                "+
			   "							?,                                                "+
			   "							?,                                                "+
			   "							?,                                                "+
			   "							?,                                                "+
			   "							?,                                                "+
			   "							?,                                                "+
			   "							?,                                                "+
			   "							?,                                                "+
			   "							?,                                                "+
			   "							?,                                                "+
			   "							?,                                                "+
			   "							?,                                                "+
			   "							?,                                                "+
			   "							?,                                                "+
			   "							?)                                                ";


	      jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){
	        public void setValues(PreparedStatement ps,int i)throws SQLException
	           {
	        	 ps.setString(1, ((Map<String, Object>)shopsList.get(i)).get("keycode").toString());
	        	 ps.setString(2, ((Map<String, Object>)shopsList.get(i)).get("keytype").toString());
	        	 ps.setString(3, ((Map<String, Object>)shopsList.get(i)).get("gcbusinesslevel").toString());
	        	 ps.setString(4, ((Map<String, Object>)shopsList.get(i)).get("gcsyslevel").toString());
	        	 ps.setString(5, ((Map<String, Object>)shopsList.get(i)).get("sequenceno").toString());
	        	 ps.setString(6, ((Map<String, Object>)shopsList.get(i)).get("custid").toString());
	        	 ps.setString(7, ((Map<String, Object>)shopsList.get(i)).get("upkeycode").toString());
	        	 ps.setString(8, ((Map<String, Object>)shopsList.get(i)).get("upkeytype").toString());
	        	 ps.setString(9, ((Map<String, Object>)shopsList.get(i)).get("upgcbusinesslevel").toString());
	        	 ps.setString(10, ((Map<String, Object>)shopsList.get(i)).get("upgcsyslevel").toString());
	        	 ps.setString(11, ((Map<String, Object>)shopsList.get(i)).get("busitype").toString());
	        	 ps.setString(12, ((Map<String, Object>)shopsList.get(i)).get("money").toString());
	        	 ps.setString(13, ((Map<String, Object>)shopsList.get(i)).get("currency").toString());
	        	 ps.setString(14, ((Map<String, Object>)shopsList.get(i)).get("startdate").toString());
	        	 ps.setString(15, ((Map<String, Object>)shopsList.get(i)).get("enddate").toString());
	        	 ps.setString(16, ((Map<String, Object>)shopsList.get(i)).get("limit").toString());
	        	 ps.setString(17, ((Map<String, Object>)shopsList.get(i)).get("limitunit").toString());
	        	 ps.setString(18, ((Map<String, Object>)shopsList.get(i)).get("lastdate").toString());
	        	 ps.setString(19, ((Map<String, Object>)shopsList.get(i)).get("usedmethod").toString());
	        	 ps.setString(20, ((Map<String, Object>)shopsList.get(i)).get("islimitcontrol").toString());
	        	 ps.setString(21, ((Map<String, Object>)shopsList.get(i)).get("busicontrolsequence").toString());
	        	 ps.setString(22, ((Map<String, Object>)shopsList.get(i)).get("interest").toString());
	        	 ps.setString(23, ((Map<String, Object>)shopsList.get(i)).get("interestunit").toString());
	        	 ps.setString(24, ((Map<String, Object>)shopsList.get(i)).get("keyeffectedstate").toString());
	        	 ps.setString(25, ((Map<String, Object>)shopsList.get(i)).get("keydatestate").toString());
	        	 ps.setString(26, ((Map<String, Object>)shopsList.get(i)).get("editionno").toString());
	        	 ps.setString(27, ((Map<String, Object>)shopsList.get(i)).get("appointedaccount").toString());
	        	 ps.setString(28, ((Map<String, Object>)shopsList.get(i)).get("ratetype").toString());
	        	 ps.setString(29, ((Map<String, Object>)shopsList.get(i)).get("floatrate").toString());
	        	 ps.setString(30, ((Map<String, Object>)shopsList.get(i)).get("rateajustcycle").toString());
	        	 ps.setString(31, ((Map<String, Object>)shopsList.get(i)).get("repaymode").toString());
	        	 ps.setString(32, ((Map<String, Object>)shopsList.get(i)).get("repaymethod").toString());
	        	 ps.setString(33, ((Map<String, Object>)shopsList.get(i)).get("loanpurpose").toString());
	        	 ps.setString(34, ((Map<String, Object>)shopsList.get(i)).get("loanpurposeremark").toString());
	        	 ps.setString(35, ((Map<String, Object>)shopsList.get(i)).get("loancardid").toString());
	        	 ps.setString(36, ((Map<String, Object>)shopsList.get(i)).get("busibalmoney").toString());
	        	 ps.setString(37, ((Map<String, Object>)shopsList.get(i)).get("simulatekeycode").toString());
	        	 ps.setString(38, ((Map<String, Object>)shopsList.get(i)).get("deptcode").toString());
	        	 ps.setString(39, ((Map<String, Object>)shopsList.get(i)).get("instcode").toString());
	        	 ps.setString(40, ((Map<String, Object>)shopsList.get(i)).get("instcitycode").toString());
	        	 ps.setString(41, ((Map<String, Object>)shopsList.get(i)).get("operdatetime").toString());
	        	 ps.setString(42, ((Map<String, Object>)shopsList.get(i)).get("operator").toString());
	        	 ps.setString(43, ((Map<String, Object>)shopsList.get(i)).get("istrans").toString());
	        	 ps.setString(44, ((Map<String, Object>)shopsList.get(i)).get("busimanager").toString());
	        	 ps.setString(45, ((Map<String, Object>)shopsList.get(i)).get("createTime").toString());
	           }

	           public int getBatchSize()
	           {
	            return shopsList.size();
	           }
	     });
	}
	
	

}
