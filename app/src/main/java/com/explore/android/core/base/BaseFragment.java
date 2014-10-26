package com.explore.android.core.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.explore.android.mobile.common.SharePreferencesManager;

public abstract class BaseFragment extends Fragment{

	private SharePreferencesManager preferences;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(setLayoutId(), container, false);
		if(getActivity() != null){
			preferences = SharePreferencesManager.getInstance(getActivity());
		} else {
			preferences = null;
		}
		initViews(view);
		initValues();
		initListener();
		return view;
	}
	
	public SharePreferencesManager getPreferences() {
		return preferences;
	}

	/**
	 * 显示Toast，直接显示字符
	 * @param message
	 */
	public void showToast(String message){
		if(getActivity() != null){
			Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 显示Toast，根据字符资源的ID显示
	 * @param message
	 */
	public void showToast(int message){
		if(getActivity() != null && isAdded()){
			Toast.makeText(getActivity(), getResources().getString(message), Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 设置布局文件
	 * @return
	 */
	protected abstract int setLayoutId();

	/**
	 * 初始化控件
	 * @param view
	 */
	public abstract void initViews(View view);

	/**
	 * 初始化值
	 */
	public abstract void initValues();

	/**
	 * 初始化事件监听
	 */
	public abstract void initListener();
}
