package com.explore.android.mobile.db;

public class DB {

	public static final String DB_NAME = "exApp.db";
	public static final int DATABASE_VERSION = 1;
	public static final String NODATA = "0";
	
	// ===========================================================
	// TABLE: app_info
	// ===========================================================
	public static final String TABLE_NAME_APPINFO = "app_info";
	public static final String APPINFO_ID = "appinfoid";
	public static final String APPINFO_INFO = "description";
	public static final String CREATE_APPINFO_TABLE = "create table "+ TABLE_NAME_APPINFO + "(" 
			+ APPINFO_ID + " integer primary key autoincrement," 
			+ APPINFO_INFO + " varchar(100) not null"
			+ ")";
	
	// ===========================================================
	// TABLE: ex_contact
	// ===========================================================
	public static final String TABLE_NAME_EMPLOYEE = "ex_employee";
	public static final String EMPLOYEE_ID = "contactid";
	public static final String EMPLOYEE_UID = "userid";
	public static final String EMPLOYEE_EID = "empid";
	public static final String EMPLOYEE_NAME = "name";
	public static final String EMPLOYEE_GENDER = "gender";
	public static final String EMPLOYEE_PHONE = "mobile";
	public static final String EMPLOYEE_EMAIL = "email";
	public static final String EMPLOYEE_DEPARTMENT = "department";
	public static final String EMPLOYEE_POSITION = "position";
	public static final String EMPLOYEE_POSITIONLEVEL = "positionlevel";
	public static final String CREATE_TABLE_EMPLOYEE = "create table " + TABLE_NAME_EMPLOYEE + "("
			+ EMPLOYEE_ID + " integer primary key autoincrement,"
			+ EMPLOYEE_UID + " varchar(5),"
			+ EMPLOYEE_EID + " varchar(5)," 
			+ EMPLOYEE_NAME + " varchar(50),"
			+ EMPLOYEE_GENDER + " varchar(5)," 
			+ EMPLOYEE_PHONE + " varchar(20)," 
			+ EMPLOYEE_EMAIL + " varchar(50)," 
			+ EMPLOYEE_DEPARTMENT + " varchar(200)," 
			+ EMPLOYEE_POSITION + " varchar(100)," 
			+ EMPLOYEE_POSITIONLEVEL + " varchar(50))";

	// ===========================================================
	// TABLE: ex_permission
	// ===========================================================	
	public static final String TABLE_NAME_PERMISSION = "ex_permission";
	public static final String PERMISSION_ID = "permissionid";
	public static final String PERMISSION_CODE = "code";
	public static final String PERMISSION_NAME = "name";
	public static final String PERMISSION_ICON = "icon";
	public static final String PERMISSION_ACTIVITY = "activity";
	public static final String PERMISSION_LEVEL = "level";
	public static final String PERMISSION_HAS_PERMISSION = "has_permission";
	public static final String PERMISSION_FATHER_CODE = "father_code";
	public static final String CREATE_TABLE_PERMISSION = "create table " + TABLE_NAME_PERMISSION + "(" 
			+ PERMISSION_ID + " integer primary key autoincrement,"
			+ PERMISSION_CODE + " integer," 
			+ PERMISSION_NAME + " varchar(50)," 
			+ PERMISSION_ICON + " varchar(50)," 
			+ PERMISSION_ACTIVITY + " varchar(50)," 
			+ PERMISSION_LEVEL + " integer," 
			+ PERMISSION_HAS_PERMISSION + " char(1)," 
			+ PERMISSION_FATHER_CODE + " varchar(5))";
	
	// ===========================================================
	// TABLE: ex_cache 数据缓存表
	// ===========================================================
	public static final String TABLE_NAME_CACHE = "ex_cache";
	public static final String CACHE_ID = "cacheid";
	public static final String CACHE_CODE = "code";
	public static final String CACHE_FOR = "cache_for";
	public static final String CACHE_DATA = "data";
	public static final String CACHE_DATA_TYPE = "data_type";
	public static final String CACHE_TIME = "time";
	public static final String CREATE_TABLE_CACHE = "create table " + TABLE_NAME_CACHE + "("
			+ CACHE_ID + " integer primary key autoincrement,"
			+ CACHE_CODE + " varchar(100) not null,"
			+ CACHE_FOR + " integer not null,"
			+ CACHE_DATA +" text,"
			+ CACHE_DATA_TYPE +" varchar(20),"
			+ CACHE_TIME +" datetime not null)";
	
	// ===========================================================
	// TABLE: ex_message 消息列表
	// ===========================================================
	public static final String TABLE_NAME_MESSAGE = "ex_message";
	public static final String MESSAGE_ID = "msgid";
	public static final String MESSAGE_INFOID = "infoid";
	public static final String MESSAGE_TYPE = "type";
	public static final String MESSAGE_TITLE = "title";
	public static final String MESSAGE_CONTENT = "content";
	public static final String MESSAGE_CREATEBY = "createby";
	public static final String MESSAGE_CREATEBYNAME = "createbyname";
	public static final String MESSAGE_CREATEBYTIME = "createbytime";
	public static final String MESSAGE_STS = "sts";
	public static final String MESSAGE_STS2 = "sts2";
	public static final String MESSAGE_SENDBYTIME = "sendbytime";
	public static final String MESSAGE_CONFIRMBYTIME = "confirmbytime";
	public static final String CREATE_TABLE_MESSAGE = "create table " + TABLE_NAME_MESSAGE + "("
			+ MESSAGE_ID + " integer primary key autoincrement,"
			+ MESSAGE_INFOID + " integer unique not null,"
			+ MESSAGE_TYPE + " varchar(10) not null,"
			+ MESSAGE_TITLE + " varchar(100),"
			+ MESSAGE_CONTENT + " varchar(200),"
			+ MESSAGE_CREATEBY + " integer not null,"
			+ MESSAGE_CREATEBYNAME + " varchar(20),"
			+ MESSAGE_CREATEBYTIME + " date,"
			+ MESSAGE_STS + " varchar(10),"
			+ MESSAGE_STS2 + " varchar(10),"
			+ MESSAGE_SENDBYTIME + " date,"
			+ MESSAGE_CONFIRMBYTIME + " date)";
	
	// ===========================================================
	// TABLE: ex_procategory 商品类别表
	// ===========================================================
	public static final String TABLE_NAME_PROCATEGORY = "ex_procategory";
	public static final String PROCAT_ID = "catid";
	public static final String PROCAT_NAME = "catname";
	public static final String PROCAT_ISLEAF = "isleaf";
	public static final String PROCAT_UPPROCATID = "upcatid";
	public static final String CREATE_TABLE_PROCATEGORY = "create table " + TABLE_NAME_PROCATEGORY + "("
			+ PROCAT_ID + " integer primary key,"
			+ PROCAT_NAME + " varchar(100),"
			+ PROCAT_ISLEAF + " varchar(2),"
			+ PROCAT_UPPROCATID + " integer)";
	
	// ===========================================================
	// TABLE: ex_constants 常量表
	// ===========================================================
	public static final String TABLE_NAME_CONSTANTS = "ex_constants";
	public static final String CONSTANTS_ID = "conid";
	public static final String CONSTANTS_TYPE = "type";
	public static final String CONSTANTS_NAME = "name";
	public static final String CONSTANTS_VALUE = "value";
	public static final String CONSTANTS_CATEGORY1 = "category1";
	public static final String CONSTANTS_CATEGORY2 = "category2";
	public static final String CONSTANTS_ORDER = "conorder";
	public static final String CREATE_TABLE_CONSTANTS = "create table " + TABLE_NAME_CONSTANTS + "("
			+ CONSTANTS_ID + " integer primary key autoincrement,"
			+ CONSTANTS_TYPE + " varchar(50) not null,"
			+ CONSTANTS_NAME + " varchar(100) not null,"
			+ CONSTANTS_VALUE + " varchar(200) not null,"
			+ CONSTANTS_CATEGORY1 + " varchar(200),"
			+ CONSTANTS_CATEGORY2 + " varchar(200),"
			+ CONSTANTS_ORDER + " integer not null)";
	
	// ===========================================================
	// TABLE: ex_kvcache 数据查询列表缓存
	// ===========================================================
	public static final String TABLE_NAME_KVCACHE = "ex_kvcache";
	public static final String KVCACHE_CACHE_ID = "kvid";
	public static final String KVCACHE_KEY = "key";
	public static final String KVCACHE_KEYRES = "keyres";
	public static final String KVCACHE_VALUE = "value";
	public static final String KVCACHE_TYPE = "type";
	public static final String KVCACHE_TIMES = "times";
	public static final String KVCACHE_LASTTIME = "lasttime";
	public static final String KVCACHE_REMARKS = "remarks";
	public static final String CREATE_TABLE_KVCACHE = "create table " + TABLE_NAME_KVCACHE + "("
			+ KVCACHE_CACHE_ID + " integer primary key autoincrement,"
			+ KVCACHE_KEY + " varchar(100) not null,"
			+ KVCACHE_KEYRES + " integer,"
			+ KVCACHE_VALUE + " varchar(200) not null,"
			+ KVCACHE_TYPE + " varchar(100) not null,"
			+ KVCACHE_TIMES + " integer,"
			+ KVCACHE_LASTTIME + " datetime,"
			+ KVCACHE_REMARKS + " varchar(100))";
	
	
}
