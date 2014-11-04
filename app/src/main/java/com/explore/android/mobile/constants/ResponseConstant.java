package com.explore.android.mobile.constants;

public class ResponseConstant {
	
	public static final String TOKEN = "TOKEN";
	
	public static final String REALNAME = "REALNAME";
	
	public static final String USERID = "USERID";
	
	public static final String STATUS = "STATUS";
	
	public static final String CONNFAILED = "CONNFAILED";

	public static final String OK = "OK";// 服务器返回值常量：操作成功
	
	public static final String EXCEPTION = "EXCEPTION";// 服务器返回值常量：操作出现错误

    public static final String USEREXCEPTION = "USEREXCEPTION";// 用户提交数据异常

    public static final String EXCEPTIONLIST = "EXCEPTIONLIST";// 用户提交异常信息

	public static final String LOGIN_NOUSER = "NOUSER";// 登录常量：用户不存在

	public static final String LOGIN_NODEVICE = "NODEVICE";// 登录常量：设备未注册

	public static final String LOGIN_PASSWORDERROE = "PASSWORDERROR";// 登录常量：密码错误

	public static final String LOGIN_NOBINDING = "NOBINDING";// 登录常量：设备未绑定到用户

	public static final String DUPLICATED = "DUPLICATED";// 服务器返回值常量：改用户设备已注册

	public static final String LOCKUSER = "LOCKUSER";// 服务器返回值常量：用户被锁定

	public static final String LOCKDEVICE = "LOCKDEVICE";// 服务器返回值常量：设备被锁定

	public static final String JSONERROR = "JSONERROR";// 服务器返回值常量：Json处理时出现错误

	public static final String CONNERROR = "CONNERROR";// 服务器返回值常量：连接错误

	public static final String TIMEOUT = "TIMEOUT";// 服务器返回值常量：操作超时

}
