package com.explore.android.mobile.activity.customer;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.explore.android.R;
import com.explore.android.core.base.BaseAsynTask;
import com.explore.android.core.base.BaseAsynTask.TaskOnPostExecuteListener;
import com.explore.android.core.base.BaseAsynTask.TaskResult;
import com.explore.android.core.http.AsynDataTask;
import com.explore.android.core.http.SubmitTask;
import com.explore.android.core.model.ExResponse;
import com.explore.android.core.util.ExStringUtil;
import com.explore.android.mobile.common.SharePreferencesManager;
import com.explore.android.mobile.constants.AppStatus;
import com.explore.android.mobile.constants.ErrorConstants;
import com.explore.android.mobile.constants.RequestConstants;
import com.explore.android.mobile.constants.ResponseConstant;
import com.explore.android.mobile.data.request.CustomerModifyInfoRequest;
import com.explore.android.mobile.data.request.CustomerModifyPreRequest;
import com.explore.android.mobile.view.DialogUtil;

public class CustomerModifyInfoActivity extends Activity{
	
	private static final String TYPE = "INFO";

	private EditText edt_customerCode;
	private EditText edt_cusNameCN;
	private EditText edt_cusNameCNs;
	private EditText edt_cusNameEN;
	private EditText edt_cusNameENs;
	private EditText edt_companyCode;
	
	private Button btn_submit;
	private Button btn_reset;
	private RelativeLayout loading;
	
	private AsynDataTask modifyPreTask;
	private String customerId;
	private SubmitTask submitTask;
	
	private String customerType;
	private String mCustomerType;
	private String isLock;
	private String isModify;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cusmodify_cusinfo);
		
		initViews();
		initValues();
		initListener();
	}
	
	private void initViews(){
		edt_customerCode = (EditText) findViewById(R.id.act_cusmodify_cuscode);
		edt_cusNameCN = (EditText) findViewById(R.id.act_cusmodify_namecn);
		edt_cusNameCNs = (EditText) findViewById(R.id.act_cusmodify_namecns);
		edt_cusNameEN = (EditText) findViewById(R.id.act_cusmodify_nameen);
		edt_cusNameENs = (EditText) findViewById(R.id.act_cusmodify_nameens);
		edt_companyCode = (EditText) findViewById(R.id.act_cusmodify_companycode);
		
		btn_submit = (Button) findViewById(R.id.act_cusmodify_cusinfo_btn_submit);
		btn_reset = (Button) findViewById(R.id.act_cusmodify_cusinfo_btn_reset);
		loading = (RelativeLayout) findViewById(R.id.app_loading_layout);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle(R.string.cusmodify_page_title_info);
	}
	
	private void initValues(){
		Intent intent = getIntent();
		customerId = intent.getStringExtra("CUSTOMERID");
		loadModifyPreData();
	}
	
	private void initListener(){
		btn_submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				submit();
			}
		});
		
		btn_reset.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				reset();
			}
		});
	}
	
	private void loadModifyPreData(){
		loading.setVisibility(View.VISIBLE);
		modifyPreTask = new AsynDataTask();
		modifyPreTask.setTaskOnPostExecuteListener(new TaskOnPostExecuteListener<ExResponse>(){
			@Override
			public void onPostExecute(TaskResult<? extends ExResponse> result) {
				if(result != null && result.Status == BaseAsynTask.STATUS_SUCCESS){
					loading.setVisibility(View.GONE);
					ExResponse response = result.Value;
					if(ErrorConstants.VALUE_NULL.equals(response.getResMessage())){
						if (response.getResCode() == -3) {
							Toast.makeText(CustomerModifyInfoActivity.this, getResources().getString(R.string.msg_request_timeout), Toast.LENGTH_SHORT).show();
						}else{
							Toast.makeText(CustomerModifyInfoActivity.this, getResources().getString(R.string.msg_server_nores), Toast.LENGTH_LONG).show();
						}
						return;
					} else {
						
						try {
							JSONObject json = new JSONObject(response.getResMessage());
							if(ResponseConstant.OK.equals(json.get(ResponseConstant.STATUS))){
								bindAModifyPreData(response.getResMessage());
							} else {
								Toast.makeText(CustomerModifyInfoActivity.this, getResources().getString(R.string.msg_response_failed), Toast.LENGTH_SHORT).show();
							}
						} catch (JSONException e) {
							e.printStackTrace();
							Toast.makeText(CustomerModifyInfoActivity.this, getResources().getString(R.string.msg_response_failed), Toast.LENGTH_SHORT).show();
						}
						
					}
				}
			}
		});
		
		CustomerModifyPreRequest request = new CustomerModifyPreRequest();
		SharePreferencesManager preferences = SharePreferencesManager.getInstance(CustomerModifyInfoActivity.this);
		request.setUserId(preferences.getUserId());
		request.setToken(preferences.getTokenLast8());
		request.setCustomerId(customerId);
		request.setType(TYPE);
		modifyPreTask.execute(preferences.getHttpUrl(),RequestConstants.CUSTOMER_MODIFYPRE,request.toJsonString());
	}
	
	private void submit(){
		loading.setVisibility(View.VISIBLE);
		submitTask = new SubmitTask();
		submitTask.setTaskOnPostExecuteListener(new TaskOnPostExecuteListener<ExResponse>() {
			@Override
			public void onPostExecute(TaskResult<? extends ExResponse> result) {
				loading.setVisibility(View.GONE);
				ExResponse response = result.Value;
				if (ErrorConstants.VALUE_NULL.equals(response.getResMessage())) {
					if (response.getResCode() == -3) {
						Toast.makeText(CustomerModifyInfoActivity.this, getResources().getString(R.string.msg_request_timeout), Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(CustomerModifyInfoActivity.this, getResources().getString(R.string.msg_server_nores), Toast.LENGTH_LONG).show();
					}
					return;
				} else {
					try {
						JSONObject json = new JSONObject(response.getResMessage());
						if(ResponseConstant.OK.equals(json.get(ResponseConstant.STATUS))){
							Toast.makeText(CustomerModifyInfoActivity.this, getResources().getString(R.string.msg_response_success), Toast.LENGTH_SHORT).show();
							AppStatus.CUSTOMER_DATA_UPDATE = true;
							CustomerModifyInfoActivity.this.finish();
						} else if(ResponseConstant.EXCEPTION.equals(json.get(ResponseConstant.STATUS))){
							Toast.makeText(CustomerModifyInfoActivity.this, getResources().getString(R.string.msg_product_delete_failed), Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						e.printStackTrace();
						Toast.makeText(CustomerModifyInfoActivity.this, getResources().getString(R.string.msg_response_failed), Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
		
		CustomerModifyInfoRequest request = new CustomerModifyInfoRequest();
		SharePreferencesManager preferences = SharePreferencesManager.getInstance(CustomerModifyInfoActivity.this);
		request.setUserId(preferences.getUserId());
		request.setToken(preferences.getTokenLast8());
		request.setCustomerId(customerId);
		request.setType(TYPE);
		request.setCustomerCode(edt_customerCode.getText().toString().trim());
		request.setCustomerNameCN(edt_cusNameCN.getText().toString().trim());
		request.setCustomerNameCNs(edt_cusNameCNs.getText().toString().trim());
		request.setCustomerNameEN(edt_cusNameEN.getText().toString().trim());
		request.setCustomerNameENs(edt_cusNameENs.getText().toString().trim());
		request.setCompanyCode(edt_companyCode.getText().toString().trim());
		request.setCustomerType(customerType);
		request.setmCustomerType(mCustomerType);
		request.setIsLock(isLock);
		request.setIsModify(isModify);
		submitTask.execute(preferences.getHttpUrl(),RequestConstants.CUSTOMER_MODIFY,request.toJsonString());
	}
	
	private void bindAModifyPreData(String dataStr) throws JSONException{
		JSONObject jsonObject = new JSONObject(dataStr);
		JSONObject json = jsonObject.getJSONObject("INFO");
		edt_cusNameCN.setText(json.getString("CUSTOMERNAMECN"));
		edt_cusNameCNs.setText(json.getString("CUSTOMERNAMECNS"));
		if(ExStringUtil.isResNoBlank(json.getString("CUSTOMERNAMEEN"))){
			edt_cusNameEN.setText(json.getString("CUSTOMERNAMEEN"));
		}
		if(ExStringUtil.isResNoBlank(json.getString("CUSTOMERNAMEENS"))){
			edt_cusNameENs.setText(json.getString("CUSTOMERNAMEENS"));
		}
		if(ExStringUtil.isResNoBlank(json.getString("COMPANYCODE"))){
			edt_companyCode.setText(json.getString("COMPANYCODE"));
		}
		edt_customerCode.setText(json.getString("CUSTOMERCODE"));
		customerType = json.getString("CUSTOMERTYPE");
		mCustomerType = json.getString("MCUSTOMERTYPE");
		isLock = json.getString("ISLOCK");
		isModify = json.getString("ISMODIFY");
	}
	
	private void reset(){
		edt_cusNameCN.setText("");
		edt_cusNameCNs.setText("");
		edt_cusNameEN.setText("");
		edt_cusNameENs.setText("");
		edt_companyCode.setText("");
	}
	
	private void backWithConfirm() {
		DialogUtil.createMessageDialog(CustomerModifyInfoActivity.this, 
				R.string.product_dlg_exit_modify_title, 
				R.string.product_dlg_exit_modify_info, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int arg1) {
						dialog.dismiss();
						CustomerModifyInfoActivity.this.finish();
					}
				});
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == android.R.id.home) {
			backWithConfirm();
            return true;
        }
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if(keyCode == KeyEvent.KEYCODE_BACK){
			backWithConfirm();
		}
		return false;
	}
}
