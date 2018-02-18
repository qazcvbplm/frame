package com.wxenterprisepay;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;


/**
 *  ����xml���������ַ������ڶ����л�ȡ�ӽڵ㡣 
 *  ����û��ʹ�õ���ʹ�õ�jar���� jdom-1.0.jar��������Ŀ����jar��ʹ�ã��������ࡣ
 *
 */
public class XMLParse {
	/**
	 * ����xml,�ڶ��ַ���     ���ص�һ��Ԫ�ؼ�ֵ�ԡ������һ��Ԫ�����ӽڵ㣬��˽ڵ��ֵ���ӽڵ��xml���ݡ�
	 * @param strxml
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes" })
	public static Map doXMLParse(String strxml)  {
		Map<String, Object> map = new HashMap<String, Object>();
		InputStream in = null;
		try {
			strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");

			if(null == strxml || "".equals(strxml)) {
				return null;
			}
			
			in  = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
			SAXBuilder builder = new SAXBuilder();
			Document doc = builder.build(in);
			Element root = doc.getRootElement();
			List list = root.getChildren();
			Iterator it = list.iterator();
			while(it.hasNext()) {
				Element e = (Element) it.next();
				String k = e.getName();
				String v = "";
				List children = e.getChildren();
				if(children.isEmpty()) {
					v = e.getTextNormalize();
				} else {
					v = XMLParse.getChildrenText(children);
				}
				map.put(k, v);
			}
		}catch (JDOMException je ) {
			je.printStackTrace();
	    } catch (IOException e1) {
			e1.printStackTrace();
		}finally{ 
			//�ر���
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return map;
	}
	
	/**
	 * ��ȡ�ӽ���xml
	 * @param children
	 * @return String
	 */
	@SuppressWarnings("rawtypes")
	public static String getChildrenText(List children) {
		StringBuffer sb = new StringBuffer();
		if(!children.isEmpty()) {
			Iterator it = children.iterator();
			while(it.hasNext()) {
				Element e = (Element) it.next();
				String name = e.getName();
				String value = e.getTextNormalize();
				List list = e.getChildren();
				sb.append("<" + name + ">");
				if(!list.isEmpty()) {
					sb.append(XMLParse.getChildrenText(list));
				}
				sb.append(value);
				sb.append("</" + name + ">");
			}
		}
		return sb.toString();
	}
	
	/**
	 * @Description�����������ת��Ϊxml��ʽ��string
	 * @param parameters  �������
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String getRequestXml(TreeMap<String, String> parameters) throws Exception{
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while(it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();
			String k = (String)entry.getKey();
			String v = (String)entry.getValue();
			if ("attach".equalsIgnoreCase(k)||"body".equalsIgnoreCase(k)||"sign".equalsIgnoreCase(k)) {
				sb.append("<"+k+">"+"<![CDATA["+v+"]]></"+k+">");
			}else {
				sb.append("<"+k+">"+v+"</"+k+">");
			}
		}
		sb.append("</xml>");
//		return new String(sb.toString().getBytes(), "ISO8859-1");
		return sb.toString();
	}
	
	
	
	/**
	 * ����xml �����ַ���
	 * @param xml
	 * @return
	 */
	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	private static Map parseXmlToList2(String xml) {
		Map retMap = new HashMap();
		try {
			StringReader read = new StringReader(xml);
			// �����µ�����ԴSAX ��������ʹ�� InputSource ������ȷ����ζ�ȡ XML ����
			InputSource source = new InputSource(read);
			// ����һ���µ�SAXBuilder
			SAXBuilder sb = new SAXBuilder();
			// ͨ������Դ����һ��Document
			Document doc = (Document) sb.build(source);
			Element root = doc.getRootElement();// ָ����ڵ�
			List<Element> es = root.getChildren();
			if (es != null && es.size() != 0) {
				for (Element element : es) {
					retMap.put(element.getName(), element.getValue());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retMap;
	}	
	
}
