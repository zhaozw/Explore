package com.explore.android;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.explore.android.core.base.BaseAsynTask;
import com.explore.android.core.base.BaseAsynTask.TaskOnPostExecuteListener;
import com.explore.android.core.base.BaseAsynTask.TaskResult;
import com.explore.android.core.http.SubmitTask;
import com.explore.android.core.model.ExResponse;
import com.explore.android.mobile.activity.ExHomeActivity;
import com.explore.android.mobile.activity.InitDataActivity;
import com.explore.android.mobile.activity.RegisterActivity;
import com.explore.android.mobile.activity.settings.SetServerUrlActicity;
import com.explore.android.mobile.common.Common;
import com.explore.android.mobile.common.PermissionLevel;
import com.explore.android.mobile.common.SharePreferencesManager;
import com.explore.android.mobile.constants.ErrorConstants;
import com.explore.android.mobile.constants.RequestConstants;
import com.explore.android.mobile.constants.ResponseConstant;
import com.explore.android.mobile.data.cache.GlobalData;
import com.explore.android.mobile.data.msg.LoginMessage;
import com.explore.android.mobile.data.request.LoginRequest;
import com.explore.android.mobile.db.opration.PermissionDBO;

public class LoginActivity extends Activity {

	private EditText edt_username;
	private EditText edt_password;
	private Button btn_login;
	private Button btn_test;
	private ImageButton btn_register;

	private SharePreferencesManager preferences;
	private ProgressDialog progressDialog = null;

	private LoginRequest user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		preferences = SharePreferencesManager.getInstance(LoginActivity.this);
		initViews();
		initValues();
		initListener();
	}

	private void initViews() {
		edt_username = (EditText) findViewById(R.id.edt_login_inputname);
		edt_password = (EditText) findViewById(R.id.edt_login_inputpwd);
		btn_login = (Button) findViewById(R.id.btn_login_btn);
		btn_test = (Button) findViewById(R.id.btn_test_btn);
		btn_register = (ImageButton) findViewById(R.id.btn_sign_in_btn);

		// 调整控件尺寸
		WindowManager windowManager = getWindowManager();
		Display display = windowManager.getDefaultDisplay();
        int screemWidth = display.getWidth();
        ((TextView) findViewById(R.id.login_info)).setText(getResources().getString(R.string.login_info));
		edt_username.setWidth((int) (screemWidth * 0.75));
		edt_password.setWidth((int) (screemWidth * 0.75));
		btn_login.setWidth((int) (screemWidth * 0.75));
		btn_test.setWidth((int) (screemWidth * 0.75));
	}

	private void initValues(){
		if(preferences.getIsSaveLoginInfo()){
			if(!("").equals(preferences.getUserName())){
				edt_username.setText(preferences.getUserName());
			}
			if(!("").equals(preferences.getUserPassword())){
				edt_password.setText(preferences.getUserPassword());
			}
		}
	}
	
	private void initListener() {
		btn_register.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
				startActivity(intent);
			}
		});

		btn_login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//btn_login.setEnabled(false);
				if (Common.isNetWorkEnable(LoginActivity.this)) {
					if (!("").equals(preferences.getHttpUrl())) {
						if ("".equals(edt_password.getText().toString().trim()) || "".equals(edt_username.getText().toString().trim())) {
							Toast.makeText(LoginActivity.this, getResources().getString(R.string.msg_pass_uname_bland), Toast.LENGTH_SHORT).show();
							btn_login.setEnabled(true);
						} else {
							login();
						}
					} else {
						Toast.makeText(LoginActivity.this, getResources().getString(R.string.msg_no_server_url), Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(LoginActivity.this, SetServerUrlActicity.class);
						startActivity(intent);
						btn_login.setEnabled(true);
					}

				} else {
					Toast.makeText(LoginActivity.this, getResources().getString(R.string.msg_connet_failed), Toast.LENGTH_SHORT).show();
					btn_login.setEnabled(true);
				}
			}
		});
		
		btn_test.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});
	}

	private final String setUrl = "设置服务器地址";

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 1, setUrl);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == 1) {
			Intent setUri_in = new Intent();
			setUri_in.setClass(LoginActivity.this, SetServerUrlActicity.class);
			startActivity(setUri_in);
		}
		return true;
	}

	private void login() {
		user = new LoginRequest();
		user.setUserName(edt_username.getText().toString().trim());
		user.setPassword(edt_password.getText().toString().trim());
		user.setImei(preferences.getDeviceIMEILast4());
		user.setImsi(preferences.getDeviceIMSILast4());
		startLoginTask();
	}
	
	private void startLoginTask() {
		progressDialog = ProgressDialog.show(LoginActivity.this, getResources().getString(R.string.task_waitting), getResources().getString(R.string.task_login), true);
		SubmitTask mTask = new SubmitTask();
		
		mTask.setTaskOnPostExecuteListener(new TaskOnPostExecuteListener<ExResponse>() {
			@Override
			public void onPostExecute(TaskResult<? extends ExResponse> result) {
				if (result != null && result.Status == BaseAsynTask.STATUS_SUCCESS) {
					btn_login.setEnabled(true);
					progressDialog.dismiss();
					ExResponse response = result.Value;
					if(ErrorConstants.VALUE_NULL.equals(response.getResMessage())){
						if (response.getResCode() == -3) {
							Toast.makeText(LoginActivity.this, getResources().getString(R.string.msg_request_timeout), Toast.LENGTH_SHORT).show();
						}else{
							Toast.makeText(LoginActivity.this, getResources().getString(R.string.msg_server_nores), Toast.LENGTH_LONG).show();
						}
						return;
					} else {
						try {
							JSONObject json = new JSONObject(response.getResMessage());
								
							if(ResponseConstant.OK.equals(json.getString(ResponseConstant.STATUS))){
									
								String token = json.getString(ResponseConstant.TOKEN);
								String id = json.getString(ResponseConstant.USERID);
								String realname = json.getString(ResponseConstant.REALNAME);

								String L1 = json.getString("L1");
								String L2 = json.getString("L2");
								String L3 = json.getString("L3");

								PermissionLevel perLevel = new PermissionLevel(L1, L2, L3);

								userPermissionInit(perLevel);

								preferences.setUserName(user.getUserName());
								preferences.setUserPassword(user.getPassword());
								preferences.setRealName(realname);
								preferences.setUserId(id);
								preferences.setToken(token);
									
								ExApplication application = (ExApplication) getApplicationContext();
								application.setToken(token);
								application.setUserId(id);
								GlobalData.userId = id;
								
								Toast.makeText(LoginActivity.this, getResources().getString(R.string.login_success).toString(), Toast.LENGTH_SHORT).show();
									
								Intent intent = new Intent();
								if(preferences.isFirstLogin()){
									intent.setClass(LoginActivity.this,InitDataActivity.class);
									intent.putExtra("TYPE", "init");
								} else {
									intent.setClass(LoginActivity.this,ExHomeActivity.class);
								}
								startActivity(intent);
								LoginActivity.this.finish();
								
							} else {
								int msg = LoginMessage.trans(json.getString(ResponseConstant.STATUS));
								Toast.makeText(LoginActivity.this, getResources().getString(msg).toString(), Toast.LENGTH_SHORT).show();
							}
								
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}
			}
		});
		mTask.execute(preferences.getHttpUrl(), RequestConstants.LOGIN, user.toJsonString());
	}
	
	/**
	 * 用户权限初始化
	 * 
	 * @param level permission level
	 */
	private void userPermissionInit(PermissionLevel level) {
		List<String> codes = new ArrayList<String>();
		codes = level.getAllCodes();
		PermissionDBO perDbo = new PermissionDBO(LoginActivity.this);
		perDbo.open();
		perDbo.grantUserPermission(codes);
		perDbo.close();
	}

}
