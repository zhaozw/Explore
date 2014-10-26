package com.explore.android.core.base;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.explore.android.R;
import com.explore.android.core.base.BaseAsynTask.TaskOnPostExecuteListener;
import com.explore.android.core.base.BaseAsynTask.TaskResult;
import com.explore.android.core.http.AsynDataTask;
import com.explore.android.core.http.SubmitTask;
import com.explore.android.core.model.ExResponse;
import com.explore.android.mobile.common.Common;
import com.explore.android.mobile.common.SharePreferencesManager;
import com.explore.android.mobile.constants.ErrorConstants;

public abstract class BaseHttpActivity extends Activity implements ExHttpRequest{

	private AsynDataTask asynDataTask;
	private SubmitTask submitTask;
	private SharePreferencesManager preferences;
	private String responseData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(setLayoutId());
		preferences = SharePreferencesManager.getInstance(getApplicationContext());
		initViews();
		initValues();
		initListener();
	}
	
	@Override
	public void onDestroy() {
		if( asynDataTask != null){
			asynDataTask.cancel(true);
		}
		if( submitTask != null){
			submitTask.cancel(true);
		}
		super.onDestroy();
	}

	@Override
	public void asynDataRequest(String url, String request, final int what) {
		showLoading();
		
		if(Common.isNetWorkEnable(getApplicationContext()) == false){
			showToast(R.string.msg_network_is_not_available);
			dismissLoading();
			return;
		}
		
		asynDataTask = null;
		asynDataTask = new AsynDataTask();
		asynDataTask.setTaskOnPostExecuteListener(new TaskOnPostExecuteListener<ExResponse>(){
			@Override
			public void onPostExecute(TaskResult<? extends ExResponse> result) {
				dismissLoading();
				if(result != null && result.Status == BaseAsynTask.STATUS_SUCCESS){
					ExResponse response = result.Value;
					if(ErrorConstants.VALUE_NULL.equals(response.getResMessage())){
						if (response.getResCode() == -3) {
							showToast(R.string.msg_request_timeout);
						}else{
							showToast(R.string.msg_server_nores);
						}
					} else {
						responseData = response.getResMessage();
						handlerResponse(response.getResMessage(), what);
					}
				}
			}
		});
		asynDataTask.execute(preferences.getHttpUrl(),url,request);
	}
	
	@Override
	public void submitRequest(String url, String request, final int what) {
		showLoading();
		
		if(Common.isNetWorkEnable(getApplicationContext()) == false){
			showToast(R.string.msg_network_is_not_available);
			dismissLoading();
			return;
		}
		
		submitTask = null;
		submitTask = new SubmitTask();
		submitTask.setTaskOnPostExecuteListener(new TaskOnPostExecuteListener<ExResponse>(){
			@Override
			public void onPostExecute(TaskResult<? extends ExResponse> result) {
				dismissLoading();
				if(result != null && result.Status == BaseAsynTask.STATUS_SUCCESS){
					ExResponse response = result.Value;
					if(ErrorConstants.VALUE_NULL.equals(response.getResMessage())){
						if (response.getResCode() == -3) {
							showToast(R.string.msg_request_timeout);
						}else{
							showToast(R.string.msg_server_nores);
						}
					} else {
						handlerResponse(response.getResMessage(), what);
					}
				}
			}
		});
		submitTask.execute(preferences.getHttpUrl(),url,request);
	}

	/**
	 * 显示Toast，直接显示字符
	 * @param message
	 */
	public void showToast(String message){
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
	}
	/**
	 * 显示Toast，根据字符资源的ID显示
	 * @param message
	 */
	public void showToast(int message){
		Toast.makeText(getApplicationContext(), getResources().getString(message), Toast.LENGTH_SHORT).show();
	}
	
	public AsynDataTask getAsynDataTask() {
		return asynDataTask;
	}

	public SubmitTask getSubmitTask() {
		return submitTask;
	}

	public SharePreferencesManager getPreferences() {
		return preferences;
	}
	
	public String getResponseData() {
		return responseData;
	}
	
	/**
	 * 设置布局文件
	 * @return
	 */
	protected abstract int setLayoutId();
	
	/**
	 * 初始化控件
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
