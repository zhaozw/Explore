package com.explore.android.mobile.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.explore.android.core.util.Base64Coder;
import com.explore.android.mobile.constants.AppConstant;
import com.explore.android.mobile.constants.PreferencesConstant;

public class SharePreferencesManager {

	private static SharePreferencesManager instance;
	private SharedPreferences exPreferences;

	private SharePreferencesManager() {
	}

	private SharePreferencesManager(Context context) {
		this.exPreferences = context.getSharedPreferences(
				AppConstant.APP_SP_FILE_NAME, Context.MODE_PRIVATE);
	}

	public static SharePreferencesManager getInstance(Context con) {
		if (instance == null) {
			instance = new SharePreferencesManager(con);
		}
		return instance;
	}

	/**
	 * Get the preference
	 * 
	 * @param paraName
	 * @return
	 */
	public String get(String paraName) {
		return this.exPreferences.getString(paraName, "");
	}

	/**
	 * Set the preference
	 * 
	 * @param paraName
	 * @param value
	 */
	public void set(String paraName, String value) {
		this.exPreferences.edit().putString(paraName, value).commit();
	}


	// ------------------get方法
	public String getToken() {
		return this.exPreferences.getString(PreferencesConstant.TOKEN, "");
	}

	public String getTokenLast8() {
		String token = this.exPreferences.getString(PreferencesConstant.TOKEN, "");
		if(token.length() < 8){
			return "";
		}else {
			return token.substring(token.length() - 8, token.length());
		}
	}
	
	public String getUserId() {
		return this.exPreferences.getString(PreferencesConstant.USERID, "");
	}

	public String getHttpUrl() {
		return this.exPreferences.getString(PreferencesConstant.HTTPURL, "");
	}

	public String getHttpPort() {
		return this.exPreferences.getString(PreferencesConstant.HTTPPORT, "");
	}

	public String getDeviceIMEI() {
		return this.exPreferences.getString(PreferencesConstant.IMEI, "");
	}

	public String getDeviceIMEILast4() {
		String imei = this.exPreferences.getString(PreferencesConstant.IMEI, "");
		if(imei.length() > 10){
			return imei.substring(imei.length() - 4, imei.length());
		} else {
			return "";
		}
	}

	public String getDeviceIMSI() {
		return this.exPreferences.getString(PreferencesConstant.IMSI, "");
	}

	public String getDeviceIMSILast4() {
		String imsi = this.exPreferences.getString(PreferencesConstant.IMSI, "");
		if(imsi.length() > 10){
			return imsi.substring(imsi.length() - 4, imsi.length());
		} else {
			return "";
		}
	}

	public String getUserName() {
		return this.exPreferences.getString(PreferencesConstant.USERNAME, "");
	}
	
	public String getRealName(){
		return this.exPreferences.getString(PreferencesConstant.REALNAME, "");
	}

	public String getUserPassword() {
		String password = this.exPreferences.getString(PreferencesConstant.PASSWORD,"");
		try {
			return Base64Coder.decode(password);
		} catch (Exception e) {
			return "";
		}
	}
	
	public boolean isFirstLogin(){
		return this.exPreferences.getBoolean(PreferencesConstant.IS_FIRST_LOGIN, true);
	}

	public boolean isPermissionInit() {
		return this.exPreferences.getBoolean(PreferencesConstant.IS_PERMISSION_INIT, false);
	}
	
	public boolean isProductCategoryInit() {
		return this.exPreferences.getBoolean(PreferencesConstant.IS_PRODUCTCATEGORY_INIT, false);
	}
	
	public boolean isAddressConstantsInit(){
		return this.exPreferences.getBoolean(PreferencesConstant.IS_ADDRESSCONSTANTS_INIT, false);
	}

	public boolean getIsKeepLogin() {
		return this.exPreferences.getBoolean(PreferencesConstant.IS_KEEP_LOGIN, false);
	}

	public boolean getIsSaveLoginInfo() {
		return this.exPreferences.getBoolean(PreferencesConstant.IS_SAVE_LOGININFO, false);
	}

	public boolean getIsLocationServiceOpen() {
		return this.exPreferences.getBoolean(PreferencesConstant.IS_LOCATION_SERVICE_OPEN, false);
	}
	
	public boolean getIsReceiveP2PInfo(){
		return this.exPreferences.getBoolean(PreferencesConstant.IS_RECEIVE_P2PINFOS, false);
	}
	
	public boolean getIsNavigationLearn(){
		return this.exPreferences.getBoolean(PreferencesConstant.PREF_USER_LEARNED_DRAWER, false);
	}

	// ------------------set方法

	public void setToken(String paramString) {
		this.exPreferences.edit().putString(PreferencesConstant.TOKEN, paramString).commit();
	}

	public void setUserId(String paramString) {
		this.exPreferences.edit().putString(PreferencesConstant.USERID, paramString).commit();
	}
	
	public void setHttpUrl(String paramString) {
		this.exPreferences.edit().putString(PreferencesConstant.HTTPURL, paramString).commit();
	}

	public void setHttpPort(String paramString) {
		this.exPreferences.edit().putString(PreferencesConstant.HTTPPORT, paramString).commit();
	}

	public void setDeviceIMEI(String paramString) {
		this.exPreferences.edit().putString(PreferencesConstant.IMEI, paramString).commit();
	}

	public void setDeviceIMSI(String paramString) {
		this.exPreferences.edit().putString(PreferencesConstant.IMSI, paramString).commit();
	}

	public void setUserName(String paramString) {
		this.exPreferences.edit().putString(PreferencesConstant.USERNAME, paramString).commit();
	}
	
	public void setRealName(String paramString) {
		this.exPreferences.edit().putString(PreferencesConstant.REALNAME, paramString).commit();
	}

	public void setUserPassword(String paramString) {
		try {
			String password = Base64Coder.encode(paramString);
			this.exPreferences.edit().putString(PreferencesConstant.PASSWORD, password).commit();
		} catch (Exception e) {
			this.exPreferences.edit().putString(PreferencesConstant.PASSWORD, "").commit();
		}
	}

	public void setIsKeepLogin(boolean isCheck) {
		this.exPreferences.edit().putBoolean(PreferencesConstant.IS_KEEP_LOGIN, isCheck).commit();
	}

	public void setIsFirstLogin(boolean isCheck){
		this.exPreferences.edit().putBoolean(PreferencesConstant.IS_FIRST_LOGIN, isCheck).commit();
	}
	
	public void setPermissionInitStatus(boolean isCheck) {
		this.exPreferences.edit().putBoolean(PreferencesConstant.IS_PERMISSION_INIT, isCheck).commit();
	}
	
	public void setProductCategoryInitStatus(boolean isCheck) {
		this.exPreferences.edit().putBoolean(PreferencesConstant.IS_PRODUCTCATEGORY_INIT, isCheck).commit();
	}

	public void setAddressConstantsInitStatus(boolean isCheck) {
		this.exPreferences.edit().putBoolean(PreferencesConstant.IS_ADDRESSCONSTANTS_INIT, isCheck).commit();
	}
	
	public void setIsSavaLoginInfo(boolean isCheck) {
		this.exPreferences.edit().putBoolean(PreferencesConstant.IS_SAVE_LOGININFO, isCheck).commit();
	}

	public void setIsLocationServiceStatus(boolean isCheck) {
		this.exPreferences.edit().putBoolean(PreferencesConstant.IS_LOCATION_SERVICE_OPEN, isCheck).commit();
	}
	
	public void setIsReceiveP2PInfo(boolean isCheck){
		this.exPreferences.edit().putBoolean(PreferencesConstant.IS_RECEIVE_P2PINFOS, isCheck).commit();
	}
	
	public void setIsNavigationLearn(boolean isCheck){
		this.exPreferences.edit().putBoolean(PreferencesConstant.PREF_USER_LEARNED_DRAWER, isCheck).commit();
	}

	// 删除用户信息
	public void removeUserInfo() {
		this.exPreferences.edit().remove(PreferencesConstant.PASSWORD).commit();
	}

}
