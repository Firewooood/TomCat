package com.wxz.server.basic;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class XMLTest02 {
    public static void main(String[] args)throws ParserConfigurationException, SAXException, IOException  {
        //SAX解析
        //1、获取解析工厂
        SAXParserFactory factory=SAXParserFactory.newInstance();
        //2、从解析工厂获取解析器
        SAXParser parse =factory.newSAXParser();
        //3、编写处理器
        //4、加载文档 Document 注册处理器
        PersonHandler handler=new PersonHandler();
        //5、解析
        System.out.println(Thread.currentThread().getContextClassLoader());
        // getResourceAsStream(name)使用相对于当前项目的classpath的相对路径来查找资源。
        // 顶级包是在 target 文件夹下的 classes, 顶级包下的文件直接用文件名或者./ 访问, ** 不能以/开头
        parse.parse(Thread.currentThread().getContextClassLoader()
//                        .getResourceAsStream("./p.xml")
                        .getResourceAsStream("p.xml")
                ,handler);

        // 获取数据
        List<Person> persons = handler.getPersons();
        for(Person p:persons) {
            System.out.println(p.getName()+"-->"+p.getAge());
        }
    }
}

class PersonHandler extends DefaultHandler{
    private List<Person> persons;
    private Person person;
    private String tag; //存储操作的标签

    @Override
    public void startDocument() throws SAXException {
        System.out.println("----解析文档开始----");
        persons = new ArrayList<Person>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        System.out.println(qName+"-->解析开始");

        if(qName != null){
            // 存储当前标签名
            tag = qName;
            //标签名 == person, 初始化person
            if(tag.equals("person")){
                person = new Person();
            }
        }

    }

    // Receive notification of character data inside an element.
    // 在每一次startElement 和 endElement后都会执行
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String contents = new String(ch,start,length).trim();
        if(tag != null){
            if(tag.equals("name")) {
                person.setName(contents);
            }else if(tag.equals("age")) {
                if(contents.length() > 0) {
                    person.setAge(Integer.valueOf(contents));
                }
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        System.out.println(qName+"-->解析结束");
        if(qName != null){
            // person 加入persons容器中
            if(qName.equals("person")){
                persons.add(person);
            }
        }
        tag = null; //丢弃tag;
    }
    @Override
    public void endDocument() throws SAXException {
        System.out.println("----解析文档结束----");
    }

    public List<Person> getPersons() {
        return persons;
    }
}
