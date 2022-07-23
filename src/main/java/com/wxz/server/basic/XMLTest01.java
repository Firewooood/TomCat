package com.wxz.server.basic;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class XMLTest01 {
    public static void main(String[] args)throws ParserConfigurationException, SAXException, IOException  {
        //SAX解析
        //1、获取解析工厂
        SAXParserFactory factory=SAXParserFactory.newInstance();
        //2、从解析工厂获取解析器
        SAXParser parse =factory.newSAXParser();
        //3、编写处理器
        //4、加载文档 Document 注册处理器
        PHandler handler=new PHandler();
        //5、解析
        System.out.println(Thread.currentThread().getContextClassLoader());
        // getResourceAsStream(name)使用相对于当前项目的classpath的相对路径来查找资源。
        // 顶级包是在 target 文件夹下的 classes, 顶级包下的文件直接用文件名或者./ 访问, ** 不能以/开头
        parse.parse(Thread.currentThread().getContextClassLoader()
//                        .getResourceAsStream("./p.xml")
                        .getResourceAsStream("p.xml")
                ,handler);
    }
}

class PHandler extends DefaultHandler{
    @Override
    public void startDocument() throws SAXException {
        System.out.println("----解析文档开始----");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        System.out.println(qName+"-->解析开始");
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String contents = new String(ch,start,length).trim();
        if(contents.length()>0) {
            System.out.println("内容为->"+contents);
        }else {
            System.out.println("内容为->"+"空");
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        System.out.println(qName+"-->解析结束");
    }
    @Override
    public void endDocument() throws SAXException {
        System.out.println("----解析文档结束----");
    }
}
