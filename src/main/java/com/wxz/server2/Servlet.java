package com.wxz.server2;


/**
 * 服务器脚本接口
 */
public interface Servlet {
	void service(Request request, Response response);
}
