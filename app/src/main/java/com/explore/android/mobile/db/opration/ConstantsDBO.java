package com.explore.android.mobile.db.opration;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.explore.android.core.base.BaseDBO;
import com.explore.android.core.model.BaseKeyValue;
import com.explore.android.core.util.QuerySql;
import com.explore.android.mobile.db.DB;
import com.explore.android.mobile.db.DBManager;
import com.explore.android.mobile.model.ExConstant;

public class ConstantsDBO extends BaseDBO{

	private SQLiteDatabase db;
	private DBManager dbManager;
	
	public ConstantsDBO(Context con){
		dbManager = new DBManager(con);
	}
	
	public long save(ExConstant constant){
		ContentValues contentValues = createConstantValues(constant);
		return db.insert(DB.TABLE_NAME_CONSTANTS, null, contentValues);
	}
	
	public int importConstants(List<ExConstant> conList){
		db.beginTransaction();
		
		try {
			dbManager.cleanConstants(db);
			
			for (int i = 0; i < conList.size(); i++) {
				save(conList.get(i));
			}
			
			db.setTransactionSuccessful();
			return SUCCESS;
			
		} catch (Exception e) {
			
		} finally {
			db.endTransaction();
		}
		
		return FAILED;
	}
	
	public ExConstant loadById(long id){
		QuerySql query = new QuerySql(DB.TABLE_NAME_CONSTANTS);
		query.addCondition(DB.CONSTANTS_ID + "=" + id);
		Cursor cursor = db.rawQuery(query.getQuerySql(), null);
		if(cursor.getCount() == 0 || cursor.getCount() > 1){
			cursor.close();
		} else {
			ExConstant constant = new ExConstant();
			constant = setConstantValues(cursor);
			cursor.close();
			return constant;
		}
		return null;
	}
	
	public List<ExConstant> getProvinces(){
		List<ExConstant> list = new ArrayList<ExConstant>();
		QuerySql query = new QuerySql(DB.TABLE_NAME_CONSTANTS);
		query.addCondition(DB.CONSTANTS_TYPE + "=PROVINCE");
		Cursor cursor = db.rawQuery(query.getQuerySql(), null);
		while(cursor.moveToNext()){
			ExConstant constant = setConstantValues(cursor);
			list.add(constant);
		}
		cursor.close();
		return list;
	}
	
	public List<ExConstant> getCitiesByProvince(String proviceName){
		List<ExConstant> list = new ArrayList<ExConstant>();
		QuerySql query = new QuerySql(DB.TABLE_NAME_CONSTANTS);
		query.addCondition(DB.CONSTANTS_TYPE + "=CITY");
		query.addConditionAnd(DB.CONSTANTS_CATEGORY1 + "=" + proviceName);
		Cursor cursor = db.rawQuery(query.getQuerySql(), null);
		while(cursor.moveToNext()){
			ExConstant constant = setConstantValues(cursor);
			list.add(constant);
		}
		cursor.close();
		return list;
	}
	
	public List<ExConstant> getAreasByCity(String cityName){
		List<ExConstant> list = new ArrayList<ExConstant>();
		QuerySql query = new QuerySql(DB.TABLE_NAME_CONSTANTS);
		query.addCondition(DB.CONSTANTS_TYPE + "=AREA");
		query.addConditionAnd(DB.CONSTANTS_CATEGORY1 + "=" + cityName);
		Cursor cursor = db.rawQuery(query.getQuerySql(), null);
		while(cursor.moveToNext()){
			ExConstant constant = setConstantValues(cursor);
			list.add(constant);
		}
		cursor.close();
		return list;
	}
	
	public void getSpinnerProvinces(List<BaseKeyValue> list){
		list.clear();
		QuerySql query = new QuerySql(DB.TABLE_NAME_CONSTANTS);
		query.addCondition(DB.CONSTANTS_TYPE + "='PROVINCE'");
		Cursor cursor = db.rawQuery(query.getQuerySql(), null);
		list.add(new BaseKeyValue("", ""));
		while(cursor.moveToNext()){
			BaseKeyValue kv = new BaseKeyValue();
			kv.setKey(cursor.getString(cursor.getColumnIndex(DB.CONSTANTS_NAME)));
			kv.setValue(cursor.getString(cursor.getColumnIndex(DB.CONSTANTS_VALUE)));
			list.add(kv);
		}
		cursor.close();
	}
	
	public void getSpinnerCitiesByProvince(String proviceName, List<BaseKeyValue> list){
		list.clear();
		QuerySql query = new QuerySql(DB.TABLE_NAME_CONSTANTS);
		query.addCondition(DB.CONSTANTS_TYPE + "='CITY'");
		query.addConditionAnd(DB.CONSTANTS_CATEGORY1 + "='" + proviceName + "'");
		Cursor cursor = db.rawQuery(query.getQuerySql(), null);
		list.add(new BaseKeyValue("", ""));
		while(cursor.moveToNext()){
			BaseKeyValue kv = new BaseKeyValue();
			kv.setKey(cursor.getString(cursor.getColumnIndex(DB.CONSTANTS_NAME)));
			kv.setValue(cursor.getString(cursor.getColumnIndex(DB.CONSTANTS_VALUE)));
			list.add(kv);
		}
		cursor.close();
	}
	
	public void getSpinnerAreasByCity(String cityName, List<BaseKeyValue> list){
		list.clear();
		QuerySql query = new QuerySql(DB.TABLE_NAME_CONSTANTS);
		query.addCondition(DB.CONSTANTS_TYPE + "='AREA'");
		query.addConditionAnd(DB.CONSTANTS_CATEGORY1 + "='" + cityName + "'");
		Cursor cursor = db.rawQuery(query.getQuerySql(), null);
		list.add(new BaseKeyValue("", ""));
		while(cursor.moveToNext()){
			BaseKeyValue kv = new BaseKeyValue();
			kv.setKey(cursor.getString(cursor.getColumnIndex(DB.CONSTANTS_NAME)));
			kv.setValue(cursor.getString(cursor.getColumnIndex(DB.CONSTANTS_VALUE)));
			list.add(kv);
		}
		cursor.close();
	}
	
	private ExConstant setConstantValues(Cursor cursor){
		ExConstant constant = new ExConstant();
		constant.setConstantId(cursor.getInt(cursor.getColumnIndex(DB.CONSTANTS_ID)));
		constant.setConstantType(cursor.getString(cursor.getColumnIndex(DB.CONSTANTS_TYPE)));
		constant.setConstantName(cursor.getString(cursor.getColumnIndex(DB.CONSTANTS_NAME)));
		constant.setConstantValue(cursor.getString(cursor.getColumnIndex(DB.CONSTANTS_VALUE)));
		constant.setCategory1(cursor.getString(cursor.getColumnIndex(DB.CONSTANTS_CATEGORY1)));
		constant.setCategory2(cursor.getString(cursor.getColumnIndex(DB.CONSTANTS_CATEGORY2)));
		constant.setConstantOrder(cursor.getInt(cursor.getColumnIndex(DB.CONSTANTS_ORDER)));
		return constant;
	}
	
	private ContentValues createConstantValues(ExConstant constant){
		ContentValues values = new ContentValues();
		values.put(DB.CONSTANTS_TYPE, constant.getConstantType());
		values.put(DB.CONSTANTS_NAME, constant.getConstantName());
		values.put(DB.CONSTANTS_VALUE, constant.getConstantValue());
		values.put(DB.CONSTANTS_CATEGORY1, constant.getCategory1());
		values.put(DB.CONSTANTS_CATEGORY2, constant.getCategory2());
		values.put(DB.CONSTANTS_ORDER, constant.getConstantOrder());
		return values;
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

}
