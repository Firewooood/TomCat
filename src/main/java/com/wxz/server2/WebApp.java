package com.wxz.server2;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class WebApp {
	private static WebContext  webContext ;

	// 静态代码块,
	// 在调用类的静态方法,静态变量,创建类的一个实例时,该静态代码块会执行, 且只执行一次.
	static {
		try {
			//SAX解析
			//1、获取解析工厂
			SAXParserFactory factory=SAXParserFactory.newInstance();
			//2、从解析工厂获取解析器
			SAXParser parse =factory.newSAXParser();
			//3、编写处理器
			//4、加载文档Document注册处理器
			WebHandler handler=new WebHandler();
			//5、解析
			parse.parse(Thread.currentThread().getContextClassLoader()
			.getResourceAsStream("web.xml")
			,handler);			
			//获取数据
			webContext = new WebContext(handler.getEntitys(),handler.getMappings());
		}catch(Exception e) {
			System.out.println("解析配置文件错误");
		}
	}

	/**
	 * 通过url获取配置文件对应的servlet
	 * @param url
	 * @return
	 */

	public static Servlet getServletFromUrl(String url) {		
		//假设你输入了 /login
		String className = webContext.getClz("/"+url);
		Class clz;
		try {
			System.out.println(url+"-->"+className+"-->");
			clz = Class.forName(className);
			Servlet servlet =(Servlet)clz.getConstructor().newInstance();
			return servlet;
		} catch (Exception e) {
			
		}
		
		return null;
		
	}
}
