package com.explore.android.mobile.constants;

import android.os.Environment;

public class AppConstant {

	public static final String SDCARD_PATH = Environment
			.getExternalStorageDirectory().getPath();

	public static final String APP_DIR = "ExApp";

	public static final String APP_LOG_DIR = "log";

	public static final String APP_LOG_CRASHLOG = "errLog.log";

	public static final String APP_LOG_USERLOG = "user.log";

	public static final String APP_HOME_PATH = SDCARD_PATH + "/" + APP_DIR;

	public static final String APP_PACKAGE_NAME = "com.explore.android";

	public static final String APP_SP_FILE_NAME = "conf";

	public static final boolean DEBUG_STATU = true;

	public static final int APP_RESULT_OK = 1;

	public static final long CACHE_FRESH_TIME = 600000L; // 缓存刷新时间:10分钟

	public static final long CACHE_MIDDLE_TIME = 60000; // 稍长时间缓存：1分钟

	public static final long CACHE_FRESH_CURRENT_TIME = 25000L; // 临时缓存：25秒

	public static final int SYNC_REFRESH = 21;

	public static final int SYNC_LOADMORE = 22;

	public static final String CURRENT_FRAGMENT_TITLE = "CURRENT_FRAGMENT_TITLE";

	/**
	 * Navivcation Code
	 */
	// 主菜单 跟配置文件无关
	public static final int NAVCODE_NAVIGATION = 1; // 主菜单
	
	// 首页
	public static final int NAVCODE_HOME = 0; // 首页
	
	// 销售
	public static final int NAVCODE_OUTMAN = 10; // 销售管理

	// 物流
	public static final int NAVCODE_STOSEA = 20; // 库存查询
	public static final int NAVCODE_OUTSEA = 21; // 发货查询

	// 人力资源
	public static final int NAVCODE_CONTACT = 50; // 联系人

	// 配置
	public static final int NAVCODE_PROSEA = 70; // 商品查询
	public static final int NAVCODE_CUSSEA = 71; // 客户查询

	// 个人控制台
	public static final int NAVCODE_AUDIT = 80; // 审核
	public static final int NAVCODE_SCHEDULE = 81; // 日程
	public static final int NAVCODE_SETTINGS = 82; // 设置
}
