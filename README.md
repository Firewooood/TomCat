# 简介

本项目基于尚学堂视频https://www.bilibili.com/video/BV1LW411C72L **2018百战程序员JAVA全系列终结版第11阶：手写服务器httpserver项目** , 手动实现,后续可能会进行相应的更新

com.wxz.server2 为最终实现代码
**项目运行**: 

	运行Server09.java中的main函数, 在浏览器中键入localhost:8888/login?name=wxz&password=12345 或 访问resources中的login.html,填写表单来发送请求.

# 项目思路

# 1 获取请求协议

* 1. 创建ServerSocket
* 2. 建立连接获取Socket
* 3. 通过输出流InputStream获取请求协议Get, Post

# 2 返回响应协议
* 1. 准备内容
* 2. 获取字节数长度
* 3. 拼接响应协议
* 4. 使用输入输出流

# 3 封装响应信息
* 1. 动态添加内容print
* 2. 累加字节数的长度
* 3. 根据状态码拼接响应头协议
* 4. 根据状态码统一推送出去

# 4 封装请求信息
* 1. 通过分解字符串获取method, URL 和请求参数 *(Post请求可能还存在请求体)*

# 封装业务类并使用多线程
* 1. 将业务代码解耦到对应的业务类中(具体的Servlet)
* 2. 根据xml配置文件动态读取类名,反射获取对应的Servlet处理业务
* 3. Dispatcher 加入多线程, 可同时处理多个请求, 使用短连接