package com.explore.android.mobile.constants;

/**
 * 
 * Constants about the keyword of local Share Preferences.
 * @author Ryan
 *
 */
public class PreferencesConstant {
	
	public static final String FILE_NAME = "explore_config"; // SharedPreference文件名

	public static final String IMEI = "imei"; // 设备IMEI编号

	public static final String IMSI = "imsi"; // 设备IMSI编号

	public static final String USERNAME = "username"; // 用户名
	
	public static final String REALNAME = "realname"; // 用户的名字

	public static final String PASSWORD = "password"; // 登录密码（操作时加密）

	public static final String HTTPURL = "httpurl"; // 服务器地址

	public static final String HTTPPORT = "httpport"; // 服务器地址所用端口

	public static final String TOKEN = "token"; // TOKEN（发送请求时需要使用）

	public static final String USERID = "userid"; // 用户ID（发送请求时需要使用）
	
	public static final String IS_FIRST_LOGIN = "first_login"; // 是否首次登录
	
	public static final String IS_PERMISSION_INIT = "permission_init";// 权限文件是否初始化
	
	public static final String IS_PRODUCTCATEGORY_INIT = "productcategory_init"; // 商品类别是否初始化
	
	public static final String IS_ADDRESSCONSTANTS_INIT = "addressconstants_init"; // 地址常量信息是否初始化
	
	public static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned"; //菜单是否展开
	
	/**
	 * Save as Preference
	 */
	public static final String IS_KEEP_LOGIN = "auto_login";// 是否保持登录

	public static final String IS_SAVE_LOGININFO = "save_logininfo";// 是否保存登录信息

	public static final String IS_LOCATION_SERVICE_OPEN = "location_server";// 是否打开位置服务

	public static final String IS_RECEIVE_P2PINFOS = "recevie_p2pinfos"; // 是否自动接收系统推送服务

	public static final String LOGOUT_PREFS = "prefs_logout";
	
	/**
	 * ---------- SharePreferences Value
	 */
	public static final String HTTPESB_TEST = "172.20.0.53:7002/exploreesb"; // 默认测试地址
	
	public static final String HTTPESB = "explore.imwork.net:6666/exploreesb"; // 默认正式地址

    public static final String HTTPESB_DEBUG = "explore.imwork.net:7002/exploreesb"; // 默认正式地址

}
