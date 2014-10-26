package com.explore.android;

import android.app.Application;

import com.explore.android.core.util.AppErrorHandler;
import com.explore.android.mobile.data.cache.GlobalData;

public class ExApplication extends Application {

	private String token;
	private String userId;
	private String tokenLast8;

	@Override
	public void onCreate() {
		super.onCreate();
		AppErrorHandler appLogHandler = AppErrorHandler.getInstance();
		appLogHandler.init(this);
		GlobalData.customerDeptList.clear();
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
		tokenLast8 = token.substring(token.length() - 8, token.length());
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTokenLast8() {
		return tokenLast8;
	}

}
