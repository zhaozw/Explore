package com.explore.android.mobile.db.opration;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.explore.android.core.base.BaseDBO;
import com.explore.android.core.model.BaseKeyValue;
import com.explore.android.core.util.QuerySql;
import com.explore.android.mobile.db.DB;
import com.explore.android.mobile.db.DBManager;
import com.explore.android.mobile.model.ProductCategory;

public class ProductCategoryDBO extends BaseDBO{

	private SQLiteDatabase db;
	private DBManager dbManager;
	
	public ProductCategoryDBO(Context context){
		dbManager = new DBManager(context);
	}
	
	@Override
	public BaseDBO open() {
		db = dbManager.getWritableDatabase();
		return this;
	}

	@Override
	public void close() {
		db.close();
	}
	
	public long save(ProductCategory proCat){
		ContentValues contentValues = createProductCategoryValue(proCat);
		return db.insert(DB.TABLE_NAME_PROCATEGORY, null, contentValues);
	}
	
	public void save2(ProductCategory proCat){
		
	}
	
	public int importCategorys(List<ProductCategory> list){
		
		db.beginTransaction();
		
		try {
			dbManager.cleanProductCategory(db);
			
			for (int i = 0; i < list.size(); i++) {
				save(list.get(i));
			}
			
			db.setTransactionSuccessful();
			return SUCCESS;
			
		} catch (Exception e) {
			Log.e("ProductCategoryDBO", "!mport data failed.");
		} finally { 
			db.endTransaction();
		}
		
		return FAILED;
	}
	
	public ProductCategory loadById(long id){
		QuerySql query = new QuerySql(DB.TABLE_NAME_PROCATEGORY);
		query.addCondition(DB.PROCAT_ID + "=" + id);
		Cursor cursor = db.rawQuery(query.getQuerySql(), null);
		if(cursor.getCount() == 0 || cursor.getCount() > 1){
			cursor.close();
		} else {
			ProductCategory proCat = new ProductCategory();
			proCat = setProductCategoryValues(cursor);
			cursor.close();
			return proCat;
		}
		return null;
	}
	
	/**
	 * 取上一级目录
	 * @param upId
	 * @return
	 */
	public ProductCategory getUpperCategory(long upId){
		QuerySql query = new QuerySql(DB.TABLE_NAME_PROCATEGORY);
		query.addCondition(DB.PROCAT_UPPROCATID + "=" + upId);
		Cursor cursor = db.rawQuery(query.getQuerySql(), null);
		if(cursor.getCount() == 0 || cursor.getCount() > 1){
			cursor.close();
		} else {
			ProductCategory proCat = new ProductCategory();
			proCat = setProductCategoryValues(cursor);
			cursor.close();
			if(proCat.getProductCategoryId() == upId){
				return null;
			}
			return proCat;
		}
		return null;
	}
	
	public List<ProductCategory> getALLUpperCategorys(long id){
		QuerySql query = new QuerySql(DB.TABLE_NAME_PROCATEGORY);
		query.addCondition(DB.PROCAT_ID + "=" + id);
		Cursor cursor = db.rawQuery(query.getQuerySql(), null);
		if(cursor.getCount() == 0 || cursor.getCount() > 1){
			cursor.close();
		} else {
			List<ProductCategory> list = new ArrayList<ProductCategory>();
			ProductCategory proCat = setProductCategoryValues(cursor);
			cursor.close();
			int nextId = -1;
			while(true){
				ProductCategory cat = new ProductCategory();
				if(nextId == -1){
					cat = getUpperCategory(proCat.getProductCategoryId());
				} else {
					cat = getUpperCategory(nextId);
				}
				if(cat != null){
					list.add(cat);
					nextId = cat.getUpProductCategoryId();
				} else {
					break;
				}
			}
		}
		
		return null;
	}
	
	/**
	 * 获取一级目录
	 * @return
	 */
	public List<ProductCategory> getTopCategory(){
		List<ProductCategory> list = new ArrayList<ProductCategory>();
		QuerySql query = new QuerySql(DB.TABLE_NAME_PROCATEGORY);
		query.addCondition(DB.PROCAT_ID + "=" + DB.PROCAT_UPPROCATID);
		query.setOrderByDESC(DB.PROCAT_ID);
		Cursor cursor = db.rawQuery(query.getQuerySql(), null);
		while (cursor.moveToNext()) {
			ProductCategory proCat = setProductCategoryValues(cursor);
			list.add(proCat);
		}
		cursor.close();
		return list;
	}
	
	public List<BaseKeyValue> getSpinnerCategory(){
		List<BaseKeyValue> list = new ArrayList<BaseKeyValue>();
		QuerySql query = new QuerySql(DB.TABLE_NAME_PROCATEGORY);
		query.addCondition(DB.PROCAT_ID + "=" + DB.PROCAT_UPPROCATID);
		query.setOrderByDESC(DB.PROCAT_ID);
		Cursor cursor = db.rawQuery(query.getQuerySql(), null);
		list.add(new BaseKeyValue("选择类别", ""));
		while (cursor.moveToNext()) {
			BaseKeyValue data = new BaseKeyValue();
			data.setValue(cursor.getString(cursor.getColumnIndex(DB.PROCAT_ID)));
			data.setKey(cursor.getString(cursor.getColumnIndex(DB.PROCAT_NAME)));
			list.add(data);
		}
		cursor.close();
		return list;
	}
	
	/**
	 * 获取子目录 
	 * @param id
	 * @return
	 */
	public List<ProductCategory> getSubCategoryById(int id){
		if(id == 0){
			return getTopCategory();
		} else {
			List<ProductCategory> list = new ArrayList<ProductCategory>();
			ProductCategory cat = loadById(id);
			if(cat.getIsLeaf() == "N"){
				QuerySql query = new QuerySql(DB.TABLE_NAME_PROCATEGORY);
				query.addCondition(DB.PROCAT_UPPROCATID + "=" + id);
				query.addConditionAnd(DB.PROCAT_ID + "!=" + id);
				query.setOrderByDESC(DB.PROCAT_ID);
				Cursor cursor = db.rawQuery(query.getQuerySql(), null);
				while (cursor.moveToNext()) {
					ProductCategory proCat = setProductCategoryValues(cursor);
					list.add(proCat);
				}
				cursor.close();
				return list;
			} else {
				return null;
			}
		}
	}
	
	public void getSpinnerSubCategoryById(int id, List<BaseKeyValue> list){
		QuerySql query = new QuerySql(DB.TABLE_NAME_PROCATEGORY);
		query.addCondition(DB.PROCAT_UPPROCATID + "=" + id);
		query.addConditionAnd(DB.PROCAT_ID + "!=" + id);
		query.setOrderByDESC(DB.PROCAT_ID);
		Cursor cursor = db.rawQuery(query.getQuerySql(), null);
		list.add(new BaseKeyValue("选择子类别", ""));
		while (cursor.moveToNext()) {
			BaseKeyValue data = new BaseKeyValue();
			data.setValue(cursor.getString(cursor.getColumnIndex(DB.PROCAT_ID)));
			data.setKey(cursor.getString(cursor.getColumnIndex(DB.PROCAT_NAME)));
			list.add(data);
		}
		cursor.close();
	}
	
	private ProductCategory setProductCategoryValues(Cursor cursor){
		ProductCategory proCat = new ProductCategory();
		proCat.setProductCategoryId(cursor.getInt(cursor.getColumnIndex(DB.PROCAT_ID)));
		proCat.setProductCategoryName(cursor.getString(cursor.getColumnIndex(DB.PROCAT_NAME)));
		proCat.setIsLeaf(cursor.getString(cursor.getColumnIndex(DB.PROCAT_ISLEAF)));
		proCat.setUpProductCategoryId(cursor.getInt(cursor.getColumnIndex(DB.PROCAT_UPPROCATID)));
		return proCat;
	}
	
	private ContentValues createProductCategoryValue(ProductCategory proCat){
		ContentValues values = new ContentValues();
		values.put(DB.PROCAT_ID, proCat.getProductCategoryId());
		values.put(DB.PROCAT_NAME, proCat.getProductCategoryName());
		values.put(DB.PROCAT_ISLEAF, proCat.getIsLeaf());
		values.put(DB.PROCAT_UPPROCATID, proCat.getUpProductCategoryId());
		return values;
	}

}

