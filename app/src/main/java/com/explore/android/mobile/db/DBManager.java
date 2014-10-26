package com.explore.android.mobile.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.explore.android.core.log.Logger;
import com.explore.android.core.log.LoggerFactory;

public class DBManager extends SQLiteOpenHelper {

	private static final String TAG = SQLiteOpenHelper.class.getName();
	private static final Logger logger = LoggerFactory.getLogger(TAG);

	public DBManager(Context context) {
		super(context, DB.DB_NAME, null, DB.DATABASE_VERSION);
	}

	public void onCreate(SQLiteDatabase db) {

		logger.info("#------------ Create tables. ------------#");

		db.execSQL(DB.CREATE_APPINFO_TABLE);
		db.execSQL(DB.CREATE_TABLE_EMPLOYEE);
		db.execSQL(DB.CREATE_TABLE_PERMISSION);
		db.execSQL(DB.CREATE_TABLE_CACHE);
		db.execSQL(DB.CREATE_TABLE_MESSAGE);
		db.execSQL(DB.CREATE_TABLE_PROCATEGORY);
		db.execSQL(DB.CREATE_TABLE_CONSTANTS);
		db.execSQL(DB.CREATE_TABLE_KVCACHE);
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		logger.info("#------------ Upgrade tables. ------------#");

		db.execSQL("drop table if exists " + DB.TABLE_NAME_APPINFO);
		db.execSQL("drop table if exists " + DB.TABLE_NAME_EMPLOYEE);
		db.execSQL("drop table if exists " + DB.TABLE_NAME_PERMISSION);
		db.execSQL("drop table if exists " + DB.TABLE_NAME_CACHE);
		db.execSQL("drop table if exists " + DB.TABLE_NAME_MESSAGE);
		db.execSQL("drop table if exists " + DB.TABLE_NAME_PROCATEGORY);
		db.execSQL("drop table if exists " + DB.TABLE_NAME_CONSTANTS);
		db.execSQL("drop table if exists " + DB.TABLE_NAME_KVCACHE);
		onCreate(db);
	}

	public void cleanEmployee(SQLiteDatabase db) {
		cleanTable(db, DB.TABLE_NAME_EMPLOYEE);
		logger.info("DB-----Clean table employee.");
	}

	public void cleanAppInfo(SQLiteDatabase db) {
		cleanTable(db, DB.TABLE_NAME_APPINFO);
		logger.info("DB-----Clean table application info.");
	}

	public void cleanPermission(SQLiteDatabase db) {
		cleanTable(db, DB.TABLE_NAME_PERMISSION);
		logger.info("DB-----Clean table permission.");
	}

	public void cleanCache(SQLiteDatabase db) {
		cleanTable(db, DB.TABLE_NAME_CACHE);
		logger.info("DB-----Clean table cache.");
	}

	public void cleanMessage(SQLiteDatabase db) {
		cleanTable(db, DB.TABLE_NAME_MESSAGE);
		logger.info("DB-----Clean table message.");
	}

	public void cleanProductCategory(SQLiteDatabase db) {
		cleanTable(db, DB.TABLE_NAME_PROCATEGORY);
		logger.info("DB-----Clean table ex_procategory.");
	}

	public void cleanConstants(SQLiteDatabase db) {
		cleanTable(db, DB.TABLE_NAME_CONSTANTS);
		logger.info("DB-----Clean table ex_constant.");
	}

	public void cleanKvCache(SQLiteDatabase db) {
		cleanTable(db, DB.TABLE_NAME_KVCACHE);
		logger.info("DB-----Clean table ex_constant.");
	}

	private void cleanTable(SQLiteDatabase db, String tableName) {
		String sql1 = "delete from " + tableName;
		String sql2 = "update sqlite_sequence set seq=0 where name='" + tableName + "'";
		db.execSQL(sql1);
		db.execSQL(sql2);
	}

}
