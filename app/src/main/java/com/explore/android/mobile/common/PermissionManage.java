package com.explore.android.mobile.common;

import java.lang.reflect.Field;

import android.content.Context;
import android.util.Log;

import com.explore.android.R;
import com.explore.android.mobile.model.Permission;

public class PermissionManage {

	private Permission audit;
	private final String TAG = "AuditManage";
	private final String GENERAL_PACKAGE = "com.explore.android.mobile.activity.general.";
	private final String AUDIT_PACKAGE = "com.explore.android.mobile.activity.audit.";

	public Permission getAudit() {
		return audit;
	}

	public void setAudit(Permission audit) {
		this.audit = audit;
	}
	
	private static final PermissionManage instance = new PermissionManage();
	
	private PermissionManage(){
	}
	
	public static PermissionManage getInstance(){
		return instance;
	}

	/**
	 * Used in TabHomeActivity.
	 * Find the activity which is available for current user by class name.
	 * @param act_name
	 * @param context
	 * @return
	 */
	public Class<?> getActivityByName(String act_name, Context context) {

		String whole_name = GENERAL_PACKAGE + act_name;

		try {

			return Class.forName(whole_name);

		} catch (ClassNotFoundException e) {

			Log.e(TAG, e.toString());

			return null;

		}

	}
	
	public Class<?> getGeneralByName(String act_name, Context context) {
		return null;
	}

	/**
	 * Used in TabAuditActivity
	 * According to the audit type,choose the corresponding activity to jump.
	 * @param act_name
	 * @return
	 */
	public Class<?> getAuditDetailActivityByName(String act_name) {

		String whole_name = AUDIT_PACKAGE + act_name;

		try {
			
			return Class.forName(whole_name);

		} catch (ClassNotFoundException e) {

			Log.e(TAG, e.toString());

			return null;

		}
	}

	public int getIdIdByFieldName(String fName) {

		String cls_name = R.id.class.getName().toString();

		try {

			Field field = Class.forName(cls_name).getField(fName);

			int id = field.getInt(cls_name);

			return id;

		} catch (Exception e) {

			Log.e(TAG, e.toString());

		}

		return -1;
	}

	public int getDrawableIdByFieldName(String fName) {

		String cls_name = R.drawable.class.getName().toString();

		try {

			Field field = Class.forName(cls_name).getField(fName);

			int id = field.getInt(cls_name);

			return id;

		} catch (Exception e) {

			Log.e(TAG, e.toString());

		}

		return -1;
	}

}
