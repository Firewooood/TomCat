package com.wxz.server2;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * 加入状态和内容处理
 */
public class Dispatcher1 implements Runnable{

    private Socket client;
    private  Request request;
    private Response response;
    public Dispatcher1(Socket client) {
        this.client = client;
        try {
            //获取请求协议
            // 此处初始response, response为空,在servlet中填充response值
            this.response =new Response(client);
            this.request =new Request(client);
        } catch (IOException e) {
            e.printStackTrace();
            this.release();
        }

    }

    @Override
    public void run() {
        try {
            if(request.getUrl() == null || request.getUrl().equals("")){
                InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("index.html");
                response.print(new String(is.readAllBytes()));
                response.pushToBrowser(200);
                is.close();
                return ;
            }
            // xml中规定了url和servlet的对应关系
            Servlet servlet = WebApp.getServletFromUrl(request.getUrl());
            if(servlet != null){
                servlet.service(request,response);
                // 执行成功,response的关注码改为200
                response.pushToBrowser(200);
            }else
            {	// 错误的情况
                InputStream is =Thread.currentThread().getContextClassLoader().getResourceAsStream("error.html");
                response.print((new String(is.readAllBytes())));
                response.pushToBrowser(404);
                is.close();
            }
        } catch (IOException e) {
            try {
                response.println("服务器出现了一点问题");
                response.pushToBrowser(500);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        // 此处需要调用release函数, 关闭socket client
        this.release();
    }

    // 释放资源
    private void release(){
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
