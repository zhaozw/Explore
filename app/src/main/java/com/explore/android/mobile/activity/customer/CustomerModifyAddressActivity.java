package com.explore.android.mobile.activity.customer;

import java.util.ArrayList;
import java.util.List;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.explore.android.R;
import com.explore.android.core.base.BaseAsynTask;
import com.explore.android.core.base.BaseAsynTask.TaskOnPostExecuteListener;
import com.explore.android.core.base.BaseAsynTask.TaskResult;
import com.explore.android.core.http.AsynDataTask;
import com.explore.android.core.http.SubmitTask;
import com.explore.android.core.model.BaseKeyValue;
import com.explore.android.core.model.ExResponse;
import com.explore.android.mobile.adapter.SimpleSpinnerAdapter;
import com.explore.android.mobile.common.SharePreferencesManager;
import com.explore.android.mobile.constants.AppStatus;
import com.explore.android.mobile.constants.ErrorConstants;
import com.explore.android.mobile.constants.RequestConstants;
import com.explore.android.mobile.constants.ResponseConstant;
import com.explore.android.mobile.data.request.CustomerModifyAddressRequest;
import com.explore.android.mobile.data.request.CustomerModifyPreRequest;
import com.explore.android.mobile.data.request.LocationGetRequest;
import com.explore.android.mobile.db.opration.ConstantsDBO;
import com.explore.android.mobile.view.DialogUtil;

public class CustomerModifyAddressActivity extends Activity{
	
	private static final String TYPE = "ADDRESS";

	private Spinner spi_province;
	private Spinner spi_city;
	private Spinner spi_area;
	private Spinner spi_addressType;
	private Spinner spi_isDefault;
	private EditText edt_addressAddress;
	private EditText edt_postcode;
	private EditText edt_contact;
	private EditText edt_mtel;
	private EditText edt_tel;
	private EditText edt_fax;
	private EditText edt_email;
	private EditText edt_remarks;
	private EditText edt_longitude;
	private EditText edt_latitude;
	
	private Button btn_get_location;
	private RelativeLayout loading;
	
	private Button btn_submit;
	private Button btn_reset;
	
	private List<BaseKeyValue> provinceList;
	private List<BaseKeyValue> cityList;
	private List<BaseKeyValue> areaList;
	private List<BaseKeyValue> addressTypeList;
	private List<BaseKeyValue> isDefaultList;
	
	private SimpleSpinnerAdapter provinceAdapter;
	private SimpleSpinnerAdapter cityAdapter;
	private SimpleSpinnerAdapter areaAdapter;
	private SimpleSpinnerAdapter addressTypeAdapter;
	private SimpleSpinnerAdapter isDefaultAdapter;
	
	private ConstantsDBO dbo;
	
	private AsynDataTask locationTask;
	private AsynDataTask modifyPreTask;
	private SubmitTask commitTask;
	private String customerId;
	private String customerDeptId;
	private String addressId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cusmodify_address);
		initViews();
		initValues();
		initListener();
		loadModifyPreData();
	}
	
	private void initViews(){
		spi_province = (Spinner) findViewById(R.id.act_cusmodify_province);
		spi_city = (Spinner) findViewById(R.id.act_cusmodify_city);
		spi_area = (Spinner) findViewById(R.id.act_cusmodify_area);
		spi_addressType = (Spinner) findViewById(R.id.act_cusmodify_addresstype);
		spi_isDefault = (Spinner) findViewById(R.id.act_cusmodify_isdefault);
		edt_addressAddress = (EditText) findViewById(R.id.act_cusmodify_address);
		edt_postcode = (EditText) findViewById(R.id.act_cusmodify_postcode);
		edt_contact = (EditText) findViewById(R.id.act_cusmodify_contactname);
		edt_mtel = (EditText) findViewById(R.id.act_cusmodify_mtel);
		edt_tel = (EditText) findViewById(R.id.act_cusmodify_tel);
		edt_fax = (EditText) findViewById(R.id.act_cusmodify_fax);
		edt_email = (EditText) findViewById(R.id.act_cusmodify_email);
		edt_remarks = (EditText) findViewById(R.id.act_cusmodify_remarks);
		edt_longitude = (EditText) findViewById(R.id.act_cusmodify_lng);
		edt_latitude = (EditText) findViewById(R.id.act_cusmodify_lat);
		btn_get_location = (Button) findViewById(R.id.act_cusmodify_btn_getgps);
		
		btn_submit = (Button) findViewById(R.id.act_cusmodify_address_btn_submit);
		btn_reset = (Button) findViewById(R.id.act_cusmodify_address_btn_reset);
		loading = (RelativeLayout) findViewById(R.id.app_loading_layout);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle(R.string.cusmodify_page_title_address);
	}
	
	private void initValues(){
		dbo = new ConstantsDBO(this);
		Intent intent = getIntent();
		if(intent.hasExtra("CUSTOMERID")){
			customerId = intent.getStringExtra("CUSTOMERID");
			initLocationData();
		}
	}
	
	private void initListener(){
		btn_submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				btn_submit.setEnabled(false);
				submit();
			}
		});
		
		btn_reset.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				reset();
			}
		});
		
		spi_province.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				dbo.open();
				dbo.getSpinnerCitiesByProvince(provinceList.get(position).getValue(), cityList);
				dbo.close();
				cityAdapter.notifyDataSetChanged();
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
		
		spi_city.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				dbo.open();
				dbo.getSpinnerAreasByCity(cityList.get(position).getValue(), areaList);
				dbo.close();
				areaAdapter.notifyDataSetChanged();
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		
		btn_get_location.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(spi_province.getSelectedItemPosition() == 0){
					Toast.makeText(CustomerModifyAddressActivity.this, getResources().getString(R.string.msg_address_no_province), Toast.LENGTH_SHORT).show();
				
				} else if(spi_city.getSelectedItemPosition() == 0){
					Toast.makeText(CustomerModifyAddressActivity.this, getResources().getString(R.string.msg_address_no_city), Toast.LENGTH_SHORT).show();
				
				} else if(spi_area.getSelectedItemPosition() == 0){
					Toast.makeText(CustomerModifyAddressActivity.this, getResources().getString(R.string.msg_address_no_area), Toast.LENGTH_SHORT).show();
				
				} else if("".equals(edt_addressAddress.getText().toString().trim())){
					Toast.makeText(CustomerModifyAddressActivity.this, getResources().getString(R.string.msg_address_no_address), Toast.LENGTH_SHORT).show();
					
				} else {
					StringBuffer addressbf = new StringBuffer();
					addressbf.append(provinceList.get(spi_province.getSelectedItemPosition()).getValue());
					addressbf.append(cityList.get(spi_city.getSelectedItemPosition()).getValue());
					addressbf.append(areaList.get(spi_area.getSelectedItemPosition()).getValue());
					addressbf.append(edt_addressAddress.getText().toString().trim());
					loadLocationInfo(addressbf.toString());
				}
			}
		});
	}
	
	private void initLocationData(){
		dbo.open();
		provinceList = new ArrayList<BaseKeyValue>();
		dbo.getSpinnerProvinces(provinceList);
		dbo.close();
		provinceAdapter = new SimpleSpinnerAdapter(CustomerModifyAddressActivity.this, provinceList);
		spi_province.setAdapter(provinceAdapter);
		
		addressTypeList = new ArrayList<BaseKeyValue>();
		addressTypeList.add(new BaseKeyValue("", ""));
		addressTypeList.add(new BaseKeyValue(R.string.customer_address_type_fix, "FIX"));
		addressTypeList.add(new BaseKeyValue(R.string.customer_address_type_tmp, "TMP"));
		addressTypeAdapter = new SimpleSpinnerAdapter(CustomerModifyAddressActivity.this, addressTypeList);
		spi_addressType.setAdapter(addressTypeAdapter);
		
		cityList = new ArrayList<BaseKeyValue>();
		areaList = new ArrayList<BaseKeyValue>();
		cityAdapter = new SimpleSpinnerAdapter(CustomerModifyAddressActivity.this, cityList);
		areaAdapter = new SimpleSpinnerAdapter(CustomerModifyAddressActivity.this, areaList);
		spi_city.setAdapter(cityAdapter);
		spi_area.setAdapter(areaAdapter);
		
		isDefaultList = new ArrayList<BaseKeyValue>();
		isDefaultList.add(new BaseKeyValue(R.string.app_yes, "Y"));
		isDefaultList.add(new BaseKeyValue(R.string.app_no, "N"));
		isDefaultAdapter = new SimpleSpinnerAdapter(CustomerModifyAddressActivity.this, isDefaultList);
		spi_isDefault.setAdapter(isDefaultAdapter);
	}
	
	private void submit(){
		loading.setVisibility(View.VISIBLE);
		commitTask = new SubmitTask();
		commitTask.setTaskOnPostExecuteListener(new TaskOnPostExecuteListener<ExResponse>() {
			@Override
			public void onPostExecute(TaskResult<? extends ExResponse> result) {
				if(result != null && result.Status == BaseAsynTask.STATUS_SUCCESS){
					loading.setVisibility(View.GONE);
					ExResponse response = result.Value;
					if (ErrorConstants.VALUE_NULL.equals(response.getResMessage())) {
						
						if (response.getResCode() == -3) {
							Toast.makeText(CustomerModifyAddressActivity.this, getResources().getString(R.string.msg_request_timeout), Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(CustomerModifyAddressActivity.this, getResources().getString(R.string.msg_server_nores), Toast.LENGTH_LONG).show();
						}
						btn_submit.setEnabled(true);
						return;
					} else {
						try {
							JSONObject json = new JSONObject(response.getResMessage());
							if(ResponseConstant.OK.equals(json.get(ResponseConstant.STATUS))){
								Toast.makeText(CustomerModifyAddressActivity.this, getResources().getString(R.string.msg_response_success), Toast.LENGTH_SHORT).show();
								AppStatus.CUSTOMER_DATA_UPDATE = true;
								btn_submit.setEnabled(true);
								CustomerModifyAddressActivity.this.finish();
							} else if(ResponseConstant.EXCEPTION.equals(json.get(ResponseConstant.STATUS))){
								Toast.makeText(CustomerModifyAddressActivity.this, getResources().getString(R.string.msg_action_failed), Toast.LENGTH_SHORT).show();
							}
						} catch (JSONException e) {
							e.printStackTrace();
							btn_submit.setEnabled(true);
							Toast.makeText(CustomerModifyAddressActivity.this, getResources().getString(R.string.msg_action_failed), Toast.LENGTH_SHORT).show();
						}
					}
				}
			}
		});
		
		CustomerModifyAddressRequest request = new CustomerModifyAddressRequest();
		SharePreferencesManager preferences = SharePreferencesManager.getInstance(CustomerModifyAddressActivity.this);
		request.setUserId(preferences.getUserId());
		request.setToken(preferences.getTokenLast8());
		request.setAddressProvince(provinceList.get(spi_province.getSelectedItemPosition()).getValue());
		request.setAddressCity(cityList.get(spi_city.getSelectedItemPosition()).getValue());
		request.setAddressArea(areaList.get(spi_area.getSelectedItemPosition()).getValue());
		request.setAddressType(addressTypeList.get(spi_addressType.getSelectedItemPosition()).getValue());
		request.setAddressAddress(edt_addressAddress.getText().toString().trim());
		request.setAddressPostCode(edt_postcode.getText().toString().trim());
		request.setAddressContactName(edt_contact.getText().toString().trim());
		request.setAddressMtel(edt_mtel.getText().toString().trim());
		request.setAddressTel(edt_tel.getText().toString().trim());
		request.setAddressEmail(edt_email.getText().toString().trim());
		request.setAddressFax(edt_fax.getText().toString().trim());
		request.setAddressLat(edt_latitude.getText().toString().trim());
		request.setAddressLng(edt_longitude.getText().toString().trim());
		request.setAddressRemarks(edt_remarks.getText().toString().trim());
		request.setAddressId(addressId);
		request.setCustomerId(customerId);
		request.setAddressIsDefault(isDefaultList.get(spi_isDefault.getSelectedItemPosition()).getValue());
		request.setType(TYPE);
		request.setCustomerDeptId(customerDeptId);
		commitTask.execute(preferences.getHttpUrl(),RequestConstants.CUSTOMER_MODIFY,request.toJsonString());
	}
	
	private void loadLocationInfo(String address){
		loading.setVisibility(View.VISIBLE);
		locationTask = new AsynDataTask();
		locationTask.setTaskOnPostExecuteListener(new TaskOnPostExecuteListener<ExResponse>(){
			@Override
			public void onPostExecute(TaskResult<? extends ExResponse> result) {
				if(result != null && result.Status == BaseAsynTask.STATUS_SUCCESS){
					loading.setVisibility(View.GONE);
					ExResponse response = result.Value;
					if(ErrorConstants.VALUE_NULL.equals(response.getResMessage())){
						if (response.getResCode() == -3) {
							Toast.makeText(CustomerModifyAddressActivity.this, getResources().getString(R.string.msg_request_timeout), Toast.LENGTH_SHORT).show();
						}else{
							Toast.makeText(CustomerModifyAddressActivity.this, getResources().getString(R.string.msg_server_nores), Toast.LENGTH_LONG).show();
						}
						return;
					} else {
						
						try {
							JSONObject json = new JSONObject(response.getResMessage());
							if(ResponseConstant.OK.equals(json.get(ResponseConstant.STATUS))){
								edt_latitude.setText(json.getString("LAT"));
								edt_longitude.setText(json.getString("LNG"));
								Toast.makeText(CustomerModifyAddressActivity.this, getResources().getString(R.string.msg_get_location_success), Toast.LENGTH_SHORT).show();
							} else {
								Toast.makeText(CustomerModifyAddressActivity.this, getResources().getString(R.string.msg_get_location_failed), Toast.LENGTH_SHORT).show();
							}
						} catch (JSONException e) {
							e.printStackTrace();
							Toast.makeText(CustomerModifyAddressActivity.this, getResources().getString(R.string.msg_get_location_failed), Toast.LENGTH_SHORT).show();
						}
					}
				}
			}
		});
		
		LocationGetRequest request = new LocationGetRequest();
		SharePreferencesManager preferences = SharePreferencesManager.getInstance(CustomerModifyAddressActivity.this);
		request.setUserId(preferences.getUserId());
		request.setToken(preferences.getTokenLast8());
		request.setTypeGPS();
		request.setAddress(address);
		locationTask.execute(preferences.getHttpUrl(),RequestConstants.LOCATION_GET,request.toJsonString());
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
							Toast.makeText(CustomerModifyAddressActivity.this, getResources().getString(R.string.msg_request_timeout), Toast.LENGTH_SHORT).show();
						}else{
							Toast.makeText(CustomerModifyAddressActivity.this, getResources().getString(R.string.msg_server_nores), Toast.LENGTH_LONG).show();
						}
						return;
					} else {
						
						try {
							JSONObject json = new JSONObject(response.getResMessage());
							if(ResponseConstant.OK.equals(json.get(ResponseConstant.STATUS))){
								bindAModifyPreData(response.getResMessage());
							} else {
								Toast.makeText(CustomerModifyAddressActivity.this, getResources().getString(R.string.msg_response_failed), Toast.LENGTH_SHORT).show();
							}
						} catch (JSONException e) {
							e.printStackTrace();
							Toast.makeText(CustomerModifyAddressActivity.this, getResources().getString(R.string.msg_response_failed), Toast.LENGTH_SHORT).show();
						}
						
					}
				}
			}
		});
		
		CustomerModifyPreRequest request = new CustomerModifyPreRequest();
		SharePreferencesManager preferences = SharePreferencesManager.getInstance(CustomerModifyAddressActivity.this);
		request.setUserId(preferences.getUserId());
		request.setToken(preferences.getTokenLast8());
		request.setCustomerId(customerId);
		request.setType(TYPE);
		modifyPreTask.execute(preferences.getHttpUrl(),RequestConstants.CUSTOMER_MODIFYPRE,request.toJsonString());
	}
	
	private void bindAModifyPreData(String dataStr) throws JSONException{
		JSONObject jsonObject = new JSONObject(dataStr);
		JSONObject json = jsonObject.getJSONObject("ADDRESS");
		
		String province = json.getString("PROVINCE");
		String city = json.getString("CITY");
		String area = json.getString("AREA");
		String addressType = json.getString("ADDRESSTYPE");
		
		addressId = json.getString("ADDRESSID");
		customerDeptId = json.getString("CUSTOMERDEPTID");
		
		edt_addressAddress.setText(json.getString("ADDRESS"));
		edt_postcode.setText(json.getString("POSTCODE"));
		edt_contact.setText(json.getString("CONTACTNAME"));
		edt_mtel.setText(json.getString("MTEL"));
		edt_tel.setText(json.getString("TEL"));
		edt_fax.setText(json.getString("FAX"));
		edt_email.setText(json.getString("EMAIL"));
		edt_remarks.setText(json.getString("REMARKS"));
		edt_latitude.setText(json.getString("LAT"));
		edt_longitude.setText(json.getString("LNG"));
		
		for (int i = 0; i < provinceList.size(); i++) {
			if(provinceList.get(i).getValue().equals(province)){
				spi_province.setSelection(i);
			}
		}
		
		dbo.open();
		dbo.getSpinnerCitiesByProvince(province, cityList);
		cityAdapter.notifyDataSetChanged();
		
		for (int i = 0; i < cityList.size(); i++) {
			if(cityList.get(i).getValue().equals(city)){
				spi_city.setSelection(i);
			}
		}
		
		dbo.getSpinnerAreasByCity(city, areaList);
		dbo.close();
		areaAdapter.notifyDataSetChanged();
		
		for (int i = 0; i < areaList.size(); i++) {
			if(areaList.get(i).getValue().equals(area)){
				spi_area.setSelection(i);
			}
		}
		
		for (int i = 0; i < addressTypeList.size(); i++) {
			if(addressTypeList.get(i).getValue().equals(addressType)){
				spi_addressType.setSelection(i);
			}
		}
		
	}
	
	private void backWithConfirm() {
		DialogUtil.createMessageDialog(CustomerModifyAddressActivity.this, 
				R.string.product_dlg_exit_modify_title, 
				R.string.product_dlg_exit_modify_info, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int arg1) {
						dialog.dismiss();
						CustomerModifyAddressActivity.this.finish();
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
	
	public void reset(){
		spi_province.setSelection(0);
		spi_city.setSelection(0);;
		spi_area.setSelection(0);;
		spi_addressType.setSelection(0);;
		edt_addressAddress.setText("");
		edt_postcode.setText("");
		edt_contact.setText("");
		edt_mtel.setText("");
		edt_tel.setText("");
		edt_fax.setText("");
		edt_email.setText("");
		edt_remarks.setText("");
		edt_longitude.setText("");
		edt_latitude.setText("");
	}
}
