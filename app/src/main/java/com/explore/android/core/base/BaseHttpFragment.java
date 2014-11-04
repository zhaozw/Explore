package com.explore.android.core.base;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.explore.android.mobile.constants.ResponseConstant;
import com.explore.android.mobile.view.DialogUtil;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class BaseHttpFragment extends Fragment implements ExHttpRequest{
	
	private AsynDataTask asynDataTask;
	private SubmitTask submitTask;
	private SharePreferencesManager preferences;
	private String responseData;

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
	
	@Override
	public void onDestroy() {
		cancelTask();
		super.onDestroy();
	}
	
	public void cancelTask(){
		if( asynDataTask != null){
			asynDataTask.cancel(true);
		}
		if( submitTask != null){
			submitTask.cancel(true);
		}
	}

	@Override
	public void asynDataRequest(String url, final String request, final int what) {
		showLoading();
		
		if(Common.isNetWorkEnable(getActivity().getApplicationContext()) == false){
			showToast(R.string.msg_network_is_not_available);
			dismissLoading();
			return;
		}
		
		asynDataTask = null;
		asynDataTask = new AsynDataTask();
		asynDataTask.setTaskOnPostExecuteListener(new TaskOnPostExecuteListener<ExResponse>(){
			@Override
			public void onPostExecute(TaskResult<? extends ExResponse> result) {
				if(result != null && result.Status == BaseAsynTask.STATUS_SUCCESS){
					dismissLoading();
					ExResponse response = result.Value;
					if(ErrorConstants.VALUE_NULL.equals(response.getResMessage())){
						if (response.getResCode() == -3) {
							showToast(R.string.msg_request_timeout);
						}else{
							showToast(R.string.msg_server_nores);
						}
						return;
					} else {
						responseData = response.getResMessage();
                        JSONObject json = null;
                        try {
                            json = new JSONObject(response.getResMessage());
                            if(json.has(ResponseConstant.STATUS)
                                    && ResponseConstant.USEREXCEPTION.equals(json.get(ResponseConstant.STATUS))){
                                String msg = json.getString(ResponseConstant.EXCEPTIONLIST);
                                DialogUtil.createMessageDialog(getActivity(), R.string.app_user_action_failed,
                                        R.string.app_user_action_know, R.string.dialog_cancel,
                                        msg, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.dismiss();
                                            }
                                        });
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
						handlerResponse(response.getResMessage(), what);
					}
				}
			}
		});
		
		if(preferences != null){
			asynDataTask.execute(preferences.getHttpUrl(),url,request);
		} else {
			showToast(R.string.error_no_preference);
		}
	}
	
	@Override
	public void submitRequest(String url, String request, final int what) {
		showLoading();
		
		if(Common.isNetWorkEnable(getActivity().getApplicationContext()) == false){
			showToast(R.string.msg_network_is_not_available);
			dismissLoading();
			return;
		}
		
		submitTask = null;
		submitTask = new SubmitTask();
		submitTask.setTaskOnPostExecuteListener(new TaskOnPostExecuteListener<ExResponse>(){
			@Override
			public void onPostExecute(TaskResult<? extends ExResponse> result) {
				if(result != null && result.Status == BaseAsynTask.STATUS_SUCCESS){
					dismissLoading();
					ExResponse response = result.Value;
					if(ErrorConstants.VALUE_NULL.equals(response.getResMessage())){
						if (response.getResCode() == -3) {
							showToast(R.string.msg_request_timeout);
						}else{
							showToast(R.string.msg_server_nores);
						}
						return;
					} else {
                        JSONObject json = null;
                        try {
                            json = new JSONObject(response.getResMessage());
                            if(json.has(ResponseConstant.STATUS)
                                    && ResponseConstant.USEREXCEPTION.equals(json.get(ResponseConstant.STATUS))){
                                String msg = json.getString(ResponseConstant.EXCEPTIONLIST);
                                DialogUtil.createMessageDialog(getActivity(), R.string.app_user_action_failed,
                                        R.string.app_user_action_know, R.string.dialog_cancel,
                                        msg, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.dismiss();
                                            }
                                        });
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
						handlerResponse(response.getResMessage(), what);
					}
				}
			}
		});
		
		if(preferences != null){
            submitTask.execute(preferences.getHttpUrl(),url,request);
		} else {
			showToast(R.string.error_no_preference);
		}
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
