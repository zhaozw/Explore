package com.explore.android.mobile.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.explore.android.R;
import com.explore.android.core.base.BaseHttpActivity;
import com.explore.android.mobile.activity.settings.SetServerUrlActicity;
import com.explore.android.mobile.common.Common;
import com.explore.android.mobile.constants.RequestConstants;
import com.explore.android.mobile.constants.ResponseConstant;
import com.explore.android.mobile.data.request.LoginRequest;

public class RegisterActivity extends BaseHttpActivity {

	private static final int REQUEST_REGISTER = 1;
	
	private EditText edt_register_username;
	private EditText edt_register_password;
	private Button btn_register;
	private ProgressDialog progressDialog = null;
	
	@Override
	protected int setLayoutId() {
		return R.layout.activity_register;
	}

	@Override
	public void initViews() {
		edt_register_username = (EditText) findViewById(R.id.register_username);
		edt_register_password = (EditText) findViewById(R.id.register_password);
		btn_register = (Button) findViewById(R.id.btn_register_btn);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle(R.string.register_title);
	}

	@Override
	public void initValues() {
		
	}

	@Override
	public void initListener() {
		btn_register.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (Common.isNetWorkEnable(RegisterActivity.this)) {
					if (!("").equals(getPreferences().getHttpUrl())) {
						if ("".equals(edt_register_password.getText().toString().trim()) || "".equals(edt_register_username.getText().toString().trim())) {
							showToast(R.string.msg_pass_uname_bland);
						} else {
							userRegister();
						}
					} else {
						showToast(R.string.msg_no_server_url);
						Intent intent = new Intent(RegisterActivity.this, SetServerUrlActicity.class);
						startActivity(intent);
					}

				} else {
					showToast(R.string.msg_connet_failed);
				}
			}
		});
	}

	@Override
	public void showLoading() {
		progressDialog = ProgressDialog.show(RegisterActivity.this, getResources().getString(R.string.task_waitting), getResources().getString(R.string.task_loading), true);
	}

	@Override
	public void dismissLoading() {
		if(progressDialog != null && progressDialog.isShowing()){
			progressDialog.dismiss();
		}
	}

	@Override
	public void handlerResponse(String response, int what) {
		if( REQUEST_REGISTER == what ){
			try {
				JSONObject json = new JSONObject(response);
				if(ResponseConstant.OK.equals(json.get(ResponseConstant.STATUS))){
					showToast(R.string.msg_register_success);
					RegisterActivity.this.finish();
				}else{
					showToast(R.string.msg_register_failed);
				}
			} catch (JSONException e) {
				showToast(R.string.msg_register_failed);
			}
		}
	}

	private void userRegister() {
		LoginRequest request = new LoginRequest();
		request.setUserName(edt_register_username.getText().toString().trim());
		request.setPassword(edt_register_password.getText().toString().trim());
		request.setImei(getPreferences().getDeviceIMEI());
		request.setImsi(getPreferences().getDeviceIMSI());
		submitRequest(RequestConstants.REGISTER, request.toJsonString(), REQUEST_REGISTER);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
		return super.onOptionsItemSelected(item);
	}
}
