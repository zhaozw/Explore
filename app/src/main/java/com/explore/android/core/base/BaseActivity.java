package com.explore.android.core.base;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.explore.android.mobile.common.SharePreferencesManager;

public abstract class BaseActivity extends Activity {

	private SharePreferencesManager preferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(setLayoutId());
		preferences = SharePreferencesManager
				.getInstance(getApplicationContext());
		initViews();
		initValues();
		initListener();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	/**
	 * 显示Toast，直接显示字符
	 * 
	 * @param message Toast 显示的字符串
	 */
	public void showToast(String message) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT)
				.show();
	}

	/**
	 * 显示Toast，根据字符资源的ID显示
	 * 
	 * @param message Toast 显示的字符串资源
	 */
	public void showToast(int message) {
		Toast.makeText(this,getResources().getString(message), Toast.LENGTH_SHORT).show();
	}

	public SharePreferencesManager getPreferences() {
		return preferences;
	}

	/**
	 * 设置布局文件
	 * 
	 * @return 对应的Layout资源ID
	 */
	protected abstract int setLayoutId();

	/**
	 * 初始化控件
	 *
	 */
	public abstract void initViews();

	/**
	 * 初始化值
	 */
	public abstract void initValues();

	/**
	 * 初始化事件监听
	 */
	public abstract void initListener();

}
