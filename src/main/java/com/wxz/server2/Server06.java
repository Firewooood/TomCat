package com.wxz.server2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 目标: 加入了servlet, 解耦了业务代码
 * 可根据url的不同,选择不同的处理逻辑
 *
 *
 */
public class Server06 {
	private ServerSocket serverSocket ;
	public static void main(String[] args) {
		Server06 server = new Server06();
		server.start();
	}
	//启动服务
	public void start() {
		try {
			serverSocket =  new ServerSocket(8888);
			 receive();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("服务器启动失败....");
		}
	}
	//接受连接处理
	public void receive() {
		try {
			Socket client = serverSocket.accept();
			System.out.println("一个客户端建立了连接....");
			//获取请求协议
			Request request =new Request(client);
			Response response =new Response(client);

			Servlet servlet = null;
			//根据url的不同,选择不同的servlet
			if(request.getUrl().equals("login")){
				servlet = new LoginServlet();
			}else if(request.getUrl().equals("reg"))
			{
				servlet = new RegisterServlet();
			}else {
				servlet = new OthersServlet();
			}
			servlet.service(request,response);
			//关注了状态码
			response.pushToBrowser(200);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("客户端错误");
		}
	}
	//停止服务
	public void stop() {
		
	}
}
