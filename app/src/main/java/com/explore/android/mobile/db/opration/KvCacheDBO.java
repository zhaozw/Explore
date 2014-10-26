package com.explore.android.mobile.db.opration;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.explore.android.core.base.BaseDBO;
import com.explore.android.core.util.QuerySql;
import com.explore.android.mobile.db.DB;
import com.explore.android.mobile.db.DBManager;
import com.explore.android.mobile.model.KvCache;

public class KvCacheDBO extends BaseDBO{
	
	@SuppressLint("SimpleDateFormat")
	public static final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private SQLiteDatabase db;
	private DBManager dbManager;
	
	public KvCacheDBO(Context con) {
		dbManager = new DBManager(con);
	}
	
	public long save(KvCache kv) {
		ContentValues contentValues = createCacheValues(kv);
		return db.insert(DB.TABLE_NAME_KVCACHE, null, contentValues); 
	}
	
	public long addKvCache(KvCache kv) {
		QuerySql query = new QuerySql(DB.TABLE_NAME_KVCACHE);
		query.addCondition(DB.KVCACHE_TYPE + "=" + kv.getType());
		query.addConditionAnd(DB.KVCACHE_VALUE + "=" + kv.getValue());
		Cursor cursor = db.rawQuery(query.getQuerySql(), null);
		if(cursor.getCount() == 0){
			return save(kv);
		} else if(cursor.getCount() == 1){
			long id = cursor.getLong(cursor.getColumnIndex(DB.KVCACHE_CACHE_ID));
			int time = cursor.getInt(cursor.getInt(cursor.getColumnIndex(DB.KVCACHE_TIMES)));
			kv.setKvId(id);
			kv.setTimes(time+1);
			 return update(kv);
		} else {
			return delete(kv);
		}
	}
	
	public int delete(KvCache kv){
		StringBuffer bf = new StringBuffer();
		bf.append(DB.KVCACHE_TYPE + "='" + kv.getType() + "'");
		bf.append("and " + DB.KVCACHE_VALUE + "='" + kv.getValue() + "'");
		return db.delete(DB.TABLE_NAME_KVCACHE, bf.toString(), null);
	}
	
	public int update(KvCache kv){
		ContentValues contentValues = createCacheValues(kv);
		StringBuffer bf = new StringBuffer();
		if(kv.getKvId() > 0){
			bf.append(DB.KVCACHE_CACHE_ID + "=" + kv.getKvId());
		} else {
			bf.append(DB.KVCACHE_TYPE + "='" + kv.getType() + "'");
			bf.append("and " + DB.KVCACHE_VALUE + "='" + kv.getValue() + "'");
		}
		return db.update(DB.TABLE_NAME_CACHE, contentValues, bf.toString(), null);
	}
	
	public KvCache loadById(long id) {
		QuerySql query = new QuerySql(DB.TABLE_NAME_KVCACHE);
		query.addCondition(DB.KVCACHE_CACHE_ID + "=" + id);
		Cursor cursor = db.rawQuery(query.getQuerySql(), null);
		if(cursor.getCount() == 0 || cursor.getCount() > 1){
			cursor.close();
		} else {
			KvCache kv = new KvCache();
			kv = setKvCacheValues(cursor);
			cursor.close();
			return kv;
		}
		return null;
	}
	
	public List<KvCache> loadByType(String type) {
		List<KvCache> kvList = new ArrayList<KvCache>();
		QuerySql query = new QuerySql(DB.TABLE_NAME_KVCACHE);
		query.addCondition(DB.KVCACHE_TYPE + "='" + type +"'");
		query.setOrderByDESC(DB.KVCACHE_TIMES);
		Cursor cursor = db.rawQuery(query.getQuerySql(), null);
		while(cursor.moveToNext()){
			KvCache kv = setKvCacheValues(cursor);
			kvList.add(kv);
		}
		cursor.close();
		return kvList;
	}
	
	private ContentValues createCacheValues(KvCache kv) {
		ContentValues values = new ContentValues();
		values.put(DB.KVCACHE_KEY, kv.getKey());
		values.put(DB.KVCACHE_KEYRES, kv.getKeyRes());
		values.put(DB.KVCACHE_LASTTIME, DF.format(kv.getLastTime()));
		values.put(DB.KVCACHE_REMARKS, kv.getRemarks());
		values.put(DB.KVCACHE_TIMES, kv.getTimes());
		values.put(DB.KVCACHE_TYPE, kv.getType());
		values.put(DB.KVCACHE_VALUE, kv.getValue());
		return values;
	}
	
	private KvCache setKvCacheValues(Cursor cursor) {
		KvCache kv = new KvCache();
		kv.setKvId(cursor.getLong(cursor.getColumnIndex(DB.KVCACHE_CACHE_ID)));
		kv.setKey(cursor.getString(cursor.getColumnIndex(DB.KVCACHE_KEY)));
		kv.setKeyRes(cursor.getInt(cursor.getColumnIndex(DB.KVCACHE_KEYRES)));
		kv.setRemarks(cursor.getString(cursor.getColumnIndex(DB.KVCACHE_REMARKS)));
		kv.setTimes(cursor.getInt(cursor.getColumnIndex(DB.KVCACHE_TIMES)));
		kv.setType(cursor.getString(cursor.getColumnIndex(DB.KVCACHE_TYPE)));
		kv.setValue(cursor.getString(cursor.getColumnIndex(DB.KVCACHE_VALUE)));
		try {
			kv.setLastTime(DF.parse(cursor.getString(cursor.getColumnIndex(DB.KVCACHE_LASTTIME))));
		} catch (ParseException e) {
			kv.setLastTime(new Date(1900,1,1));
		}
		return kv;
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
	
	public void clearKvCache(){
		dbManager.cleanKvCache(db);
	}

}
