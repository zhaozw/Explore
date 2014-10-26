package com.explore.android.mobile.constants;

/**
 * 服务器连接相关常量
 * @author Ryan
 *
 */
public class HttpConstant {
	
	public static final String HTTPURL = "HTTPURL";// 本地SharedPreference文件常量：服务器地址

	public static final String HTTPPORT = "HTTPPORT";// 本地SharedPreference文件常量：服务器地址所用端口

	public static final String TOKEN = "TOKEN";// 本地SharedPreference文件常量：TOKEN（发送请求时需要使用）

	public static final String USERID = "USERID";// 本地SharedPreference文件常量：用户ID（发送请求时需要使用）

	public static final String HTTPESB = "pioneer.imzone.in:8001/exesb";// 本地SharedPreference文件常量：默认测试地址
	
	public static final String IMEI = "IMEI";// 本地SharedPreference文件常量：设备IMEI编号

	public static final String IMSI = "IMSI";// 本地SharedPreference文件常量：设备IMSI编号

	public static final String USERNAME = "USERNAME";// 本地SharedPreference文件常量：用户名

	public static final String PASSWORD = "PASSWORD";// 本地SharedPreference文件常量：登录密码（操作时加密）
	
	public static final int ConnectTimeout = 20000;

}
