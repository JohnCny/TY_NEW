/**   
* 
* 
*/
package com.cardpay.workflow.utils;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**   
 * @Title: LoadDBXML.java 
 * @Description: 读取配置文件 
 * @author 谭文华   
 * @date 2014-11-26 下午2:29:07
 */
public class LoadDBXML {
	
	/**驱动 */
	private String driver = "oracle.jdbc.driver.OracleDriver";//"com.mysql.jdbc.Driver";
    /**连接字串 */
	private String url = "jdbc:oracle:thin:@192.168.1.143:1521:orcl";//"jdbc:mysql://127.0.0.1/simdb";
    /** 数据库用户名 */
	private String username = "PCCREDIT";
    /** 数据库密码 */
	private String password = "PCCREDIT";
	
	/**
	 * 读取配置文件
	 */
	public LoadDBXML(){
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
        try  
        {  
            DocumentBuilder db = dbf.newDocumentBuilder();  
            Document doc = db.parse("pet2.xml");  
  
            NodeList dogList = doc.getElementsByTagName("dog");  
            System.out.println("共有" + dogList.getLength() + "个dog节点");  
            for (int i = 0; i < dogList.getLength(); i++)  
            {  
                Node dog = dogList.item(i);  
                Element elem = (Element) dog;  
                System.out.println("id:" + elem.getAttribute("id"));  
                for (Node node = dog.getFirstChild(); node != null; node = node.getNextSibling())  
                {  
                    if (node.getNodeType() == Node.ELEMENT_NODE)  
                    {  
                        String name = node.getNodeName();  
                        String value = node.getFirstChild().getNodeValue();  
                        System.out.print(name + ":" + value + "\t");  
                    }  
                } 
            } 
		}  
	    catch (Exception e)  
	    {  
	        e.printStackTrace();  
	    }  
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}  
