package com.explore.android.mobile.data.cache;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.explore.android.core.model.BaseKeyValue;
import com.explore.android.mobile.constants.ResponseConstant;
import com.explore.android.mobile.db.opration.PermissionDBO;
import com.explore.android.mobile.model.Permission;
import com.explore.android.mobile.model.SingleMenuItem;

/**
 * Define some public static value for the App.
 * @author ryan
 *
 */
public class GlobalData {

	// 用户Id
	public static String userId = "";
	
	// 导航条目
	public static List<SingleMenuItem> navList = null;
	
	/**
	 * 
	 * @param con
	 */
	public static void loadNavigationList(Context con) {
		navList = new ArrayList<SingleMenuItem>();
		PermissionDBO dbo = new PermissionDBO(con);
		dbo.open();
		List<Permission> perList = dbo.getMenuList("1", 0);
		for (int i = 0; i < perList.size(); i++) {
			SingleMenuItem singleMenu = new SingleMenuItem();
			singleMenu.setId(perList.get(i).getPermissionId());
			singleMenu.setName(perList.get(i).getPermissionName());
			singleMenu.setCode(perList.get(i).getPermissionCode());
			singleMenu.setActionName(perList.get(i).getActivityName());
					
			List<SingleMenuItem> subMenus = new ArrayList<SingleMenuItem>();
			List<Permission> subPers = dbo.getMenuList("2", singleMenu.getCode());
			if (subPers.size() < 1) {
				continue;
			} else {
				for (int j = 0; j < subPers.size(); j++) {
					SingleMenuItem submenu = new SingleMenuItem();
					submenu.setId(subPers.get(j).getPermissionId());
					submenu.setName(subPers.get(j).getPermissionName());
					submenu.setCode(subPers.get(j).getPermissionCode());
					submenu.setActionName(subPers.get(j).getActivityName());
					subMenus.add(submenu);
				}
				singleMenu.setGroups(subMenus);
				navList.add(singleMenu);
			}
		}
		dbo.close();
	}

	// 经营单位缓存
	public static List<BaseKeyValue> customerDeptList = new ArrayList<BaseKeyValue>();
	public static boolean initCustomerDept(String strData) {
		try {
			JSONObject json = new JSONObject(strData);
			if(ResponseConstant.OK.equals(json.get(ResponseConstant.STATUS))){
				customerDeptList.clear();
				JSONArray deptArray = json.getJSONArray("CUSTOMERDEPTS");
				//customerDeptList.add(new BaseKeyValue(R.string.out_cusdept, ""));
				int length = deptArray.length();
				for (int i = 0; i < length; i++) {
					BaseKeyValue info = new BaseKeyValue();
					JSONObject jsonObject = deptArray.getJSONObject(i);
					info.setValue(jsonObject.getString("CUSTOMERDEPTID"));
					info.setKey(jsonObject.getString("CUSTOMERDEPTNAME"));
					customerDeptList.add(info);
				}
			} 
		} catch (JSONException e) {
		}
		return false;
	}
	
}
