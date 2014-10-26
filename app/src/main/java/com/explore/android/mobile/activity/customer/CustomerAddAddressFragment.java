package com.explore.android.mobile.activity.customer;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.explore.android.R;
import com.explore.android.core.base.BaseAsynTask;
import com.explore.android.core.base.BaseAsynTask.TaskOnPostExecuteListener;
import com.explore.android.core.base.BaseAsynTask.TaskResult;
import com.explore.android.core.http.AsynDataTask;
import com.explore.android.core.model.BaseKeyValue;
import com.explore.android.core.model.ExResponse;
import com.explore.android.mobile.adapter.SimpleSpinnerAdapter;
import com.explore.android.mobile.common.SharePreferencesManager;
import com.explore.android.mobile.constants.ErrorConstants;
import com.explore.android.mobile.constants.RequestConstants;
import com.explore.android.mobile.constants.ResponseConstant;
import com.explore.android.mobile.data.request.LocationGetRequest;
import com.explore.android.mobile.db.opration.ConstantsDBO;

public class CustomerAddAddressFragment extends Fragment{

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
	
	private CustomerAddActivity mActivity;
	private ConstantsDBO dbo;
	
	private AsynDataTask locationTask;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_customer_add_address, container, false);
		
		initViews(view);
		initValues();
		initListener();
		
		return view;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	private void initViews(View view){
		spi_province = (Spinner) view.findViewById(R.id.frag_cusmodify_province);
		spi_city = (Spinner) view.findViewById(R.id.frag_cusmodify_city);
		spi_area = (Spinner) view.findViewById(R.id.frag_cusmodify_area);
		spi_addressType = (Spinner) view.findViewById(R.id.frag_cusmodify_addresstype);
		spi_isDefault = (Spinner) view.findViewById(R.id.frag_cusmodify_isdefault);
		edt_addressAddress = (EditText) view.findViewById(R.id.frag_cusmodify_address);
		edt_postcode = (EditText) view.findViewById(R.id.frag_cusmodify_postcode);
		edt_contact = (EditText) view.findViewById(R.id.frag_cusmodify_contactname);
		edt_mtel = (EditText) view.findViewById(R.id.frag_cusmodify_mtel);
		edt_tel = (EditText) view.findViewById(R.id.frag_cusmodify_tel);
		edt_fax = (EditText) view.findViewById(R.id.frag_cusmodify_fax);
		edt_email = (EditText) view.findViewById(R.id.frag_cusmodify_email);
		edt_remarks = (EditText) view.findViewById(R.id.frag_cusmodify_remarks);
		edt_longitude = (EditText) view.findViewById(R.id.frag_cusmodify_lng);
		edt_latitude = (EditText) view.findViewById(R.id.frag_cusmodify_lat);
		btn_get_location = (Button) view.findViewById(R.id.frag_cusmodify_btn_getgps);
	}
	
	private void initValues(){
		if(getActivity() != null && getActivity() instanceof CustomerAddActivity){
			mActivity = (CustomerAddActivity) getActivity();
			dbo = new ConstantsDBO(getActivity());
			
			isDefaultList = new ArrayList<BaseKeyValue>();
			isDefaultList.add(new BaseKeyValue(R.string.app_yes, "Y"));
			isDefaultList.add(new BaseKeyValue(R.string.app_no, "N"));
			isDefaultAdapter = new SimpleSpinnerAdapter(getActivity(), isDefaultList);
			spi_isDefault.setAdapter(isDefaultAdapter);
			
			loadLocationData();
		}else{
			mActivity = null;
		}
		
	}
	
	private void initListener(){
		
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
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.msg_address_no_province), Toast.LENGTH_SHORT).show();
				
				} else if(spi_city.getSelectedItemPosition() == 0){
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.msg_address_no_city), Toast.LENGTH_SHORT).show();
				
				} else if(spi_area.getSelectedItemPosition() == 0){
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.msg_address_no_area), Toast.LENGTH_SHORT).show();
				
				} else if("".equals(edt_addressAddress.getText().toString().trim())){
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.msg_address_no_address), Toast.LENGTH_SHORT).show();
					
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
	
	private void loadLocationInfo(String address){
		locationTask = new AsynDataTask();
		locationTask.setTaskOnPostExecuteListener(new TaskOnPostExecuteListener<ExResponse>(){
			@Override
			public void onPostExecute(TaskResult<? extends ExResponse> result) {
				if(result != null && result.Status == BaseAsynTask.STATUS_SUCCESS){
					ExResponse response = result.Value;
					if(ErrorConstants.VALUE_NULL.equals(response.getResMessage())){
						if (response.getResCode() == -3) {
							Toast.makeText(getActivity(), getResources().getString(R.string.msg_request_timeout), Toast.LENGTH_SHORT).show();
						}else{
							Toast.makeText(getActivity(), getResources().getString(R.string.msg_server_nores), Toast.LENGTH_LONG).show();
						}
						return;
					} else {
						
						try {
							JSONObject json = new JSONObject(response.getResMessage());
							if(ResponseConstant.OK.equals(json.get(ResponseConstant.STATUS))){
								edt_latitude.setText(json.getString("LAT"));
								edt_longitude.setText(json.getString("LNG"));
								Toast.makeText(getActivity(), getResources().getString(R.string.msg_get_location_success), Toast.LENGTH_SHORT).show();
							} else {
								Toast.makeText(getActivity(), getResources().getString(R.string.msg_get_location_failed), Toast.LENGTH_SHORT).show();
							}
						} catch (JSONException e) {
							e.printStackTrace();
							Toast.makeText(getActivity(), getResources().getString(R.string.msg_get_location_failed), Toast.LENGTH_SHORT).show();
						}
					}
				}
			}
		});
		
		LocationGetRequest request = new LocationGetRequest();
		SharePreferencesManager preferences = SharePreferencesManager.getInstance(getActivity());
		request.setUserId(preferences.getUserId());
		request.setToken(preferences.getTokenLast8());
		request.setTypeGPS();
		request.setAddress(address);
		locationTask.execute(preferences.getHttpUrl(),RequestConstants.LOCATION_GET,request.toJsonString());
	}
	
	private void loadLocationData(){
		dbo.open();
		provinceList = new ArrayList<BaseKeyValue>();
		dbo.getSpinnerProvinces(provinceList);
		dbo.close();
		provinceAdapter = new SimpleSpinnerAdapter(getActivity(), provinceList);
		spi_province.setAdapter(provinceAdapter);
		
		addressTypeList = new ArrayList<BaseKeyValue>();
		addressTypeList.add(new BaseKeyValue("", ""));
		addressTypeList.add(new BaseKeyValue(R.string.customer_address_type_fix, "FIX"));
		addressTypeList.add(new BaseKeyValue(R.string.customer_address_type_tmp, "TMP"));
		addressTypeAdapter = new SimpleSpinnerAdapter(getActivity(), addressTypeList);
		spi_addressType.setAdapter(addressTypeAdapter);
		
		cityList = new ArrayList<BaseKeyValue>();
		areaList = new ArrayList<BaseKeyValue>();
		cityAdapter = new SimpleSpinnerAdapter(getActivity(), cityList);
		areaAdapter = new SimpleSpinnerAdapter(getActivity(), areaList);
		spi_city.setAdapter(cityAdapter);
		spi_area.setAdapter(areaAdapter);
	}
	
	public void setRequestData(){
		mActivity.getAddRequest().setAddressProvince(provinceList.get(spi_province.getSelectedItemPosition()).getValue());
		mActivity.getAddRequest().setAddressCity(cityList.get(spi_city.getSelectedItemPosition()).getValue());
		mActivity.getAddRequest().setAddressArea(areaList.get(spi_area.getSelectedItemPosition()).getValue());
		mActivity.getAddRequest().setAddressType(addressTypeList.get(spi_addressType.getSelectedItemPosition()).getValue());
		mActivity.getAddRequest().setAddressAddress(edt_addressAddress.getText().toString().trim());
		mActivity.getAddRequest().setAddressPostCode(edt_postcode.getText().toString().trim());
		mActivity.getAddRequest().setAddressContactName(edt_contact.getText().toString().trim());
		mActivity.getAddRequest().setAddressMtel(edt_mtel.getText().toString().trim());
		mActivity.getAddRequest().setAddressTel(edt_tel.getText().toString().trim());
		mActivity.getAddRequest().setAddressEmail(edt_email.getText().toString().trim());
		mActivity.getAddRequest().setAddressFax(edt_fax.getText().toString().trim());
		mActivity.getAddRequest().setAddressLat(edt_latitude.getText().toString().trim());
		mActivity.getAddRequest().setAddressLng(edt_longitude.getText().toString().trim());
		mActivity.getAddRequest().setAddressRemarks(edt_remarks.getText().toString().trim());
		mActivity.getAddRequest().setAddressIsDefault(isDefaultList.get(spi_isDefault.getSelectedItemPosition()).getValue());
	}
	
	public void reset(){
		spi_province.setSelection(0);
		spi_city.setSelection(0);
		spi_area.setSelection(0);
		spi_addressType.setSelection(0);
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
