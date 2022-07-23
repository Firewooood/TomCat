package com.wxz.server.basic;

import java.lang.reflect.InvocationTargetException;

public class ReflectTest {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        //三种方式
        //1、对象.getClass()
        Iphone iphone =new Iphone();
        Class clz = iphone.getClass();
        //2、类.class()
        clz = Iphone.class;
        //3、Class.forName("包名.类名")
        clz = Class.forName("com.wxz.server.basic.Iphone");

        //创建对象
		/*Iphone iphone2 =(Iphone)clz.newInstance();
		System.out.println(iphone2);*/

        // java9推荐此处使用构造器获得class对象
        Iphone iphone2 =(Iphone)clz.getConstructor().newInstance();
        System.out.println(iphone2);
    }

}

class Iphone{
    public Iphone() {

    }
}
