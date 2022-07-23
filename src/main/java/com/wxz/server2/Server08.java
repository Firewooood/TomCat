package com.wxz.server2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 目标: 多线程处理,加入分发器
 */
public class Server08 {
	private ServerSocket serverSocket ;
	private boolean isRunning; 	// 表示是否有线程正在运行
	public static void main(String[] args) {
		Server08 server = new Server08();
		server.start();
	}
	//启动服务
	public void start() {
		try {
			serverSocket =  new ServerSocket(8888);
			// 开始服务时,isRunning为true
			isRunning = true;
			receive();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("服务器启动失败....");
			stop();
		}
	}
	//接受连接处理
	public void receive() {
		while(isRunning){
			try {
				Socket client = serverSocket.accept();
				System.out.println("一个客户端建立了连接....");

				// 此处进行多线程处理	request 和 response 的处理逻辑封装在Dispatcher中
				new Thread(new Dispatcher(client)).start();

			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("客户端错误");
			}
		}
	}

	//停止服务
	public void stop() {
		// 结束服务时 isRunning为false
		isRunning = false;
		try {
			this.serverSocket.close();
			System.out.println("服务器已停止");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
