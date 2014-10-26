package com.explore.android.mobile.db.opration;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.explore.android.core.base.BaseDBO;
import com.explore.android.core.util.QuerySql;
import com.explore.android.mobile.db.DB;
import com.explore.android.mobile.db.DBManager;
import com.explore.android.mobile.model.Permission;

public class PermissionDBO extends BaseDBO{

	private SQLiteDatabase db;
	private DBManager dbManager;
	
	public PermissionDBO(Context context){
		dbManager = new DBManager(context);
	}
	
	public long save(Permission permission){
		ContentValues contentValues = createPermissionValue(permission);
		return db.insert(DB.TABLE_NAME_PERMISSION, null, contentValues);
	}
	
	public int importPermissions(List<Permission> list){
		
		db.beginTransaction();
		
		try {
			dbManager.cleanPermission(db);
			
			for (int i = 0; i < list.size(); i++) {
				save(list.get(i));
			}
			
			db.setTransactionSuccessful();
			return SUCCESS;
			
		} catch (Exception e) {
		}finally{
			db.endTransaction();
		}
		
		return FAILED;
	}
	
	public List<Permission> getSonPermissionCodes(String faCode){
		QuerySql query = new QuerySql(DB.TABLE_NAME_PERMISSION);
		query.addCondition(DB.PERMISSION_FATHER_CODE + "=" + faCode);
		Cursor cursor = db.rawQuery(query.getQuerySql(), null);
		
		if(cursor.getCount() ==0){
			cursor.close();
		}else{
			List<Permission> list = new ArrayList<Permission>();
			cursor.moveToFirst();
			while (cursor.moveToNext()) {
				list.add(setPermissionValues(cursor));
				cursor.close();
			}
			return list;
		}
		
		return null;
	}
	
	public Permission getPermissionByCode(String code){
		QuerySql query = new QuerySql(DB.TABLE_NAME_PERMISSION);
		query.addCondition(DB.PERMISSION_CODE+"="+code);
		Cursor cursor = db.rawQuery(query.getQuerySql(), null);
		
		if(cursor.getCount() == 0 || cursor.getCount() > 1){
			cursor.close();
		}else{
			Permission permission = setPermissionValues(cursor);
			cursor.close();
			return permission;
		}
		
		return null;
	}
	
	private Permission setPermissionValues(Cursor cursor){
		Permission permission = new Permission();
		permission.setPermissionId(cursor.getInt(cursor.getColumnIndexOrThrow(DB.PERMISSION_ID)));
		permission.setPermissionName(cursor.getString(cursor.getColumnIndex(DB.PERMISSION_NAME)));
		permission.setPermissionCode(cursor.getInt(cursor.getColumnIndex(DB.PERMISSION_CODE)));
		permission.setPermissionIcon(cursor.getString(cursor.getColumnIndex(DB.PERMISSION_ICON)));
		permission.setActivityName(cursor.getString(cursor.getColumnIndex(DB.PERMISSION_ACTIVITY)));
		permission.setFatherCode(cursor.getString(cursor.getColumnIndex(DB.PERMISSION_FATHER_CODE)));
		permission.setHaspermission(cursor.getString(cursor.getColumnIndex(DB.PERMISSION_HAS_PERMISSION)));
		permission.setPermissionLevel(cursor.getInt(cursor.getColumnIndex(DB.PERMISSION_LEVEL)));
		return permission;
	}
	
	private ContentValues createPermissionValue(Permission permission){
		ContentValues values = new ContentValues();
		values.put(DB.PERMISSION_CODE, permission.getPermissionCode());
		values.put(DB.PERMISSION_NAME, permission.getPermissionName());
		values.put(DB.PERMISSION_ICON, permission.getPermissionIcon());
		values.put(DB.PERMISSION_ACTIVITY, permission.getActivityName());
		values.put(DB.PERMISSION_LEVEL, permission.getPermissionLevel());
		values.put(DB.PERMISSION_HAS_PERMISSION, permission.getHaspermission());
		values.put(DB.PERMISSION_FATHER_CODE, permission.getFatherCode());
		return values;
	}
	
	public List<Permission> getMenuList(String level,int faCode){
		List<Permission> list = new ArrayList<Permission>();
		QuerySql query = new QuerySql(DB.TABLE_NAME_PERMISSION);
		query.addCondition(DB.PERMISSION_FATHER_CODE+"='"+faCode+"'");
		query.addConditionAnd(DB.PERMISSION_LEVEL+"="+level);
		query.addConditionAnd(DB.PERMISSION_HAS_PERMISSION+"="+"'Y'");
		Cursor cursor = db.rawQuery(query.getQuerySql(), null);
		while(cursor.moveToNext()){
			Permission permission = setPermissionValues(cursor);
			list.add(permission);
		}
		cursor.close();
		return list;
	}
	
	/**
	 * 根据权限编号
	 * @param codes
	 */
	public void grantUserPermission(List<String> codes){
		for (int i = 0; i < codes.size(); i++) {
			ContentValues value = new ContentValues();
			value.put(DB.PERMISSION_HAS_PERMISSION, "Y");
			db.update(DB.TABLE_NAME_PERMISSION, value, DB.PERMISSION_CODE+"=?", new String[]{codes.get(i)});
		}
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

}
