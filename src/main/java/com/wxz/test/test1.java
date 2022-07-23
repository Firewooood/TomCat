package com.wxz.test;

import javax.print.attribute.standard.PagesPerMinute;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class test1 {
    public static void main(String[] args) throws Exception{
        Socket socket = new Socket(InetAddress.getByName("127.0.0.1"),8080);
        OutputStream os = socket.getOutputStream();
        boolean autoFlush = true;
        PrintWriter out = new PrintWriter(socket.getOutputStream(),autoFlush);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
        );
        //发送一条Http request 给web服务器
        out.println("GET /index.jsp HTTP/1.1");
        out.println("Host: localhost:8080");
        out.println("Connection: Close");
        out.println();

        //读取response
        boolean loop = true;
        StringBuffer sb = new StringBuffer(8096);
        while(loop){
            if(in.ready()){
                int i = 0;
                while(i != -1){
                    i = in.read();
                    sb.append((char)i);
                }
                loop = false;
            }
            Thread.currentThread().sleep(50);
        }

        //展示响应结果到控制台
        System.out.println(sb.toString());
        socket.close();
    }


}
