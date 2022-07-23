package com.wxz.server2;

import java.io.IOException;
import java.net.Socket;

public class Dispatcher implements Runnable{

    private Socket client;
    private  Request request;
    private Response response;
    public Dispatcher(Socket client) {
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
            // xml中规定了url和servlet的对应关系
            Servlet servlet = WebApp.getServletFromUrl(request.getUrl());
            if(servlet != null){
                servlet.service(request,response);
                // 执行成功,response的关注码改为200
                response.pushToBrowser(200);
            }else
            {	// 错误的情况
                response.pushToBrowser(404);
            }
        } catch (IOException e) {
            try {
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
