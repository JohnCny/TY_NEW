package com.cardpay.pccredit.riskControl.xml;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.SAXValidator;
import org.dom4j.io.XMLWriter;
import org.dom4j.util.XMLErrorHandler;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 输出xml和解析xml的工具类
 * @author 陈志方
 *
 * 2014-10-31上午9:28:20
 */
public class XmlUtil{
	
	Logger log = Logger.getLogger(XmlUtil.class);
	
    /**
     * java 转换成xml
     * @Title: toXml 
     * @Description: TODO 
     * @param obj 对象实例
     * @return String xml字符串
     */
    public static String toXml(Object obj){
        //XStream xstream=new XStream();
//      XStream xstream=new XStream(new DomDriver()); //直接用jaxp dom来解释
      XStream xstream=new XStream(new DomDriver("utf-8")); //指定编码解析器,直接用jaxp dom来解释
         
        ////如果没有这句，xml中的根元素会是<包.类名>；或者说：注解根本就没生效，所以的元素名就是类的属性
        xstream.processAnnotations(obj.getClass()); //通过注解方式的，一定要有这句话
        return xstream.toXML(obj);
    }
     
    /**
     *  将传入xml文本转换成Java对象
     * @Title: toBean 
     * @Description: TODO 
     * @param xmlStr
     * @param cls  xml对应的class类
     * @return T   xml对应的class类的实例对象
     * 
     * 调用的方法实例：PersonBean person=XmlUtil.toBean(xmlStr, PersonBean.class);
     */
    public static <T> T  toBean(String xmlStr,Class<T> cls){
        //注意：不是new Xstream(); 否则报错：java.lang.NoClassDefFoundError: org/xmlpull/v1/XmlPullParserFactory
        XStream xstream=new XStream(new DomDriver());
        xstream.processAnnotations(cls);
        T obj=(T)xstream.fromXML(xmlStr);
        return obj;         
    } 

    /**
     * 把xml文件转换为字符串
     * @author 陈志方
     * @param xmlFile xml文件文件名(完整的路径和文件名)
     * @return String
     */
    public static String parseXMLFileToString(File xmlFile){
        try {
        	//创建一个读取工具
            SAXReader xmlReader = new SAXReader();
            //获取要校验xml文档实例
			Document xmlDocument = (Document) xmlReader.read(xmlFile);
			java.io.StringWriter out = new java.io.StringWriter();
			XMLWriter xw = new XMLWriter (out, new OutputFormat ("", true, "GBK"));
			xw.write(xmlDocument);
			return out.toString(); 
        } catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return "";
    }
    
    /**
     * 通过XSD（XML Schema）校验XML
     * @author 陈志方
     * @param xmlStr 对象
     * @param xsdFileName xsd文件名(完整的路径和文件名)
     * @return boolean
     */
    public static boolean validateXMLByXSD(String xmlStr, String xsdFileName) {
        boolean flag = false;
    	try {
            //创建默认的XML错误处理器
            XMLErrorHandler errorHandler = new XMLErrorHandler();
            //获取基于 SAX 的解析器的实例
            SAXParserFactory factory = SAXParserFactory.newInstance();
            //解析器在解析时验证 XML 内容。
            factory.setValidating(true);
            //指定由此代码生成的解析器将提供对 XML 名称空间的支持。
            factory.setNamespaceAware(true);
            //使用当前配置的工厂参数创建 SAXParser 的一个新实例。
            SAXParser parser = factory.newSAXParser();
            //创建一个读取工具
            SAXReader xmlReader = new SAXReader();
            //获取要校验xml文档实例 
            Document xmlDocument = (Document) xmlReader.read(new StringReader(xmlStr));
            //设置 XMLReader 的基础实现中的特定属性。核心功能和属性列表可以在 [url]http://sax.sourceforge.net/?selected=get-set[/url] 中找到。
            parser.setProperty(
                    "http://java.sun.com/xml/jaxp/properties/schemaLanguage",
                    "http://www.w3.org/2001/XMLSchema");
            parser.setProperty(
                    "http://java.sun.com/xml/jaxp/properties/schemaSource",
                    "file:" + xsdFileName);
            //创建一个SAXValidator校验工具，并设置校验工具的属性
            SAXValidator validator = new SAXValidator(parser.getXMLReader());
            //设置校验工具的错误处理器，当发生错误时，可以从处理器对象中得到错误信息。
            validator.setErrorHandler(errorHandler);
            //校验
            validator.validate(xmlDocument);

            XMLWriter writer = new XMLWriter(OutputFormat.createPrettyPrint());
            //如果错误信息不为空，说明校验失败，打印错误信息
            if (errorHandler.getErrors().hasContent()) {
            	writer.write(errorHandler.getErrors());
            }else{
            	flag = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    	return flag;
    }
}