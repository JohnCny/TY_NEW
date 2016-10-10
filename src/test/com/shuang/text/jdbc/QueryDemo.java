package com.shuang.text.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class QueryDemo {
	 public static void main(String[] args){
		         String JDriver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
		         String url="jdbc:sqlserver://172.17.0.27:1433;DatabaseName=jradbase";
		         String user="sa";
		         String password="WinRoot01!";
		         try{
		             Class.forName(JDriver);// 动态导入数据库的驱动
		             Connection conn=DriverManager.getConnection(url, user, password);// 获取数据库链接
		             String query="SELECT * FROM sample";// 创造SQL语句
		            Statement stmt=conn.createStatement();// 执行SQL语句
		             ResultSet rs=stmt.executeQuery(query);
			            while(rs.next()){
		                System.out.println(rs.getString("id")+":"+rs.getString(2));
		                //密码字段的编号从1开始，密码排第二位
		           }
		             System.out.println("查询数据成功");
		             rs.close();
		             stmt.close();
		            conn.close();
		        }catch(Exception e){
		             e.printStackTrace();
		        }
		    }
}
