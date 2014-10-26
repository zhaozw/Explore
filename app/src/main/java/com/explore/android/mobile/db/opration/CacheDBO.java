package com.explore.android.mobile.db.opration;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.explore.android.core.base.BaseDBO;
import com.explore.android.core.util.QuerySql;
import com.explore.android.mobile.db.DB;
import com.explore.android.mobile.db.DBManager;
import com.explore.android.mobile.model.ExCache;

public class CacheDBO extends BaseDBO {

	@SuppressLint("SimpleDateFormat")
	public static final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final int MAX_CACHE_COUNT = 150;
	
	private SQLiteDatabase db;
	private DBManager dbManager;

	public CacheDBO(Context con) {
		dbManager = new DBManager(con);
	}

	public long save(ExCache cache) {
		ContentValues contentValues = createCacheValues(cache);
		return db.insert(DB.TABLE_NAME_CACHE, null, contentValues);
	}
	
	public long addCache(ExCache cache){
		limiteCache();
		QuerySql query = new QuerySql(DB.TABLE_NAME_CACHE);
		query.addCondition(DB.CACHE_CODE+"='"+cache.getCacheCode()+"'");
		Cursor cursor = db.rawQuery(query.getQuerySql(), null);
		if(cursor.getCount() == 0){
			return save(cache);
		}else{
			delete(cache.getCacheCode());
			return save(cache);
		}
	}
	
	public int delete(String code){
		return db.delete(DB.TABLE_NAME_CACHE, DB.CACHE_CODE + "='" + code +"'", null);
	}
	
	public int update(ExCache cache){
		int flag = -1;
		if(cache.getChacheFor() == -1){
			return flag;
		}
		ContentValues contentValues = createCacheValues(cache);
		flag = db.update(DB.TABLE_NAME_CACHE, contentValues, DB.CACHE_CODE + "='" + cache.getCacheCode() +"'", null);
		return flag;
	}
	
	public ExCache getDataByCode(String code){
		QuerySql query = new QuerySql(DB.TABLE_NAME_CACHE);
		query.addCondition(DB.CACHE_CODE+"='"+code+"'");
		Cursor cursor = db.rawQuery(query.getQuerySql(), null);
		if(cursor.getCount() < 1){
			return null;
		}
		cursor.moveToFirst();
		ExCache cache = new ExCache();
		cache = setCacheValues(cursor);
		cursor.close();
		return cache;
	}
	
	private ContentValues createCacheValues(ExCache cache) {
		ContentValues values = new ContentValues();
		values.put(DB.CACHE_CODE, cache.getCacheCode());
		values.put(DB.CACHE_FOR, cache.getChacheFor());
		values.put(DB.CACHE_DATA, cache.getData());
		values.put(DB.CACHE_DATA_TYPE, cache.getDataType());
		values.put(DB.CACHE_TIME, DF.format(cache.getTime()));
		return values;
	}
	
	private ExCache setCacheValues(Cursor cursor){
		ExCache cache = new ExCache();
		cache.setCacheId(cursor.getInt(cursor.getColumnIndex(DB.CACHE_ID)));
		cache.setCacheCode(cursor.getString(cursor.getColumnIndex(DB.CACHE_CODE)));
		cache.setChacheFor(cursor.getInt(cursor.getColumnIndex(DB.CACHE_FOR)));
		cache.setData(cursor.getString(cursor.getColumnIndex(DB.CACHE_DATA)));
		cache.setDataType(cursor.getString(cursor.getColumnIndex(DB.CACHE_DATA_TYPE)));
		try {
			cache.setTime(DF.parse(cursor.getString(cursor.getColumnIndex(DB.CACHE_TIME))));
		} catch (ParseException e) {
			cache.setTime(new Date(1900,1,1));
		}
		return cache;
	}
	
	private void limiteCache(){
		QuerySql query = new QuerySql(DB.TABLE_NAME_CACHE);
		Cursor cursor = db.rawQuery(query.getQuerySql(), null);
		if(cursor.getCount() >= MAX_CACHE_COUNT){
			String delSql = "delete from " + DB.TABLE_NAME_CACHE + " where id in(select min(id) from " + DB.TABLE_NAME_CACHE + ")";
			db.execSQL(delSql);
		}
		cursor.close();
	}
	
	@Override
	public BaseDBO open() {
		db = dbManager.getWritableDatabase();
		return this;
	}

	@Override
	public void close() {
		dbManager.close();
	}
	
	public void clearCache(){
		dbManager.cleanCache(db);
	}

}
