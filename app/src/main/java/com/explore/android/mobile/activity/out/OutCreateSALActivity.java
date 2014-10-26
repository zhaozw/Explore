package com.explore.android.mobile.activity.out;

import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.explore.android.R;
import com.explore.android.core.base.BaseHttpActivity;
import com.explore.android.core.model.BaseKeyValue;
import com.explore.android.core.util.DateUtil;
import com.explore.android.mobile.adapter.SimpleSpinnerAdapter;
import com.explore.android.mobile.common.ExCacheManager;
import com.explore.android.mobile.constants.ActivityCode;
import com.explore.android.mobile.constants.DataConstants;
import com.explore.android.mobile.constants.RequestConstants;
import com.explore.android.mobile.constants.ResponseConstant;
import com.explore.android.mobile.data.DataSource;
import com.explore.android.mobile.data.predata.AsynPreData;
import com.explore.android.mobile.data.predata.OutCreatePreData;
import com.explore.android.mobile.data.request.ExRequest;
import com.explore.android.mobile.data.request.OutCusDeptExtraRequest;
import com.explore.android.mobile.data.request.OutSALCreateRequest;
import com.explore.android.mobile.data.request.OutShipToExtraRequest;

public class OutCreateSALActivity extends BaseHttpActivity{
	
	private static final int REQUEST_DEPT = 1;
	private static final int REQUEST_DEPTEXTRA = 2;
	private static final int REQUEST_SHIPTOEXTRA = 3;
	private static final int REQUEST_SUBMIT = 4;
	
	private static final int REQUESTCODE_SHPITO = 1;
	private static final int REQUESTCODE_VATTO = 2;
	
	// change "WS" to "W"
	private static final String DEPTEXTRA_TYPE = "W";

	private EditText edt_outCode2;
	private EditText edt_logno;
	private EditText edt_remarks;
	
	//替换Spinner
	private EditText edt_shipTo;
	private EditText edt_vatTo;
	private Button btn_shipTo;
	private Button btn_vatTo;
	
	private Spinner spi_shipToAdress;
	private Spinner spi_cusDept;
	private Spinner spi_warehouse;
	private Spinner spi_soPriceLogon;
	
	private Button btn_pickOutTime;
	private EditText edt_outtime;
	
	private Button btn_save;
	private Button btn_reset;
	
	private SimpleSpinnerAdapter cusDeptAdapter;
	private SimpleSpinnerAdapter warehouseAdapter;
	private SimpleSpinnerAdapter soPriceLogonAdapter;
	private SimpleSpinnerAdapter shipToAdressAdapter;
	
	private int year,month,date;
	private String shipToId,deptId;
	
	private ExCacheManager cacheManager;
	private ProgressDialog progressDialog;
	private BaseKeyValue shipToKv;
	private BaseKeyValue vatToKv;
	
	@Override
	protected int setLayoutId() {
		return R.layout.frag_outsal_create_layout;
	}

	@Override
	public void initViews() {
		edt_outCode2 = (EditText) findViewById(R.id.frag_edt_outsal_outcode2);
		edt_logno = (EditText) findViewById(R.id.frag_edt_outsal_logno);
		edt_remarks = (EditText) findViewById(R.id.frag_edt_outsal_remarks);
		edt_shipTo = (EditText) findViewById(R.id.frag_edt_outsal_shipto);
		edt_vatTo = (EditText) findViewById(R.id.frag_edt_outsal_vatto);
		
		spi_shipToAdress = (Spinner) findViewById(R.id.frag_edt_outsal_address);
		spi_cusDept = (Spinner) findViewById(R.id.frag_spi_outsal_cusdept);
		spi_warehouse = (Spinner) findViewById(R.id.frag_spi_outsal_warehouse);
		spi_soPriceLogon = (Spinner) findViewById(R.id.frag_spi_outsal_sologon);
		
		btn_shipTo = (Button) findViewById(R.id.frag_btn_outsal_shipto);
		btn_vatTo = (Button) findViewById(R.id.frag_btn_outsal_vatto);
		btn_pickOutTime = (Button) findViewById(R.id.frag_btn_outsal_outtime);
		edt_outtime = (EditText) findViewById(R.id.frag_edt_outsal_outtime);
		
		btn_save = (Button) findViewById(R.id.frag_btn_outsal_save);
		btn_reset = (Button) findViewById(R.id.frag_btn_outsal_reset);
		
		spi_shipToAdress.setEnabled(false);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle(R.string.outsal_title);
	}

	@Override
	public void initValues() {
		cacheManager = ExCacheManager.getInstance(this);
		
		shipToKv = new BaseKeyValue("", "");
		vatToKv = new BaseKeyValue("", "");
		
		OutCreatePreData.init(this);
		
		cusDeptAdapter = new SimpleSpinnerAdapter(this, OutCreatePreData.cusDeptList);
		warehouseAdapter = new SimpleSpinnerAdapter(this, OutCreatePreData.warehouseList);
		soPriceLogonAdapter = new SimpleSpinnerAdapter(this, OutCreatePreData.soPriceLogonList);
		shipToAdressAdapter = new SimpleSpinnerAdapter(this, OutCreatePreData.shipToAdressList);
		
		spi_cusDept.setAdapter(cusDeptAdapter);
		spi_warehouse.setAdapter(warehouseAdapter);
		spi_soPriceLogon.setAdapter(soPriceLogonAdapter);
		spi_shipToAdress.setAdapter(shipToAdressAdapter);
		
		Calendar cal = Calendar.getInstance();
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH);
		date = cal.get(Calendar.DATE);
		
		if(cacheManager.getOutCusDeptCache(getPreferences().getUserId()) == null){
			loadCustomerDept();
		}else{
			bindCustomerDeptData(cacheManager.getOutCusDeptCache(getPreferences().getUserId()), true);
		}
	}

	@Override
	public void initListener() {
		btn_pickOutTime.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DatePickerDialog dlg = new DatePickerDialog(OutCreateSALActivity.this, onDateSetListener, year, month, date);
				dlg.setCanceledOnTouchOutside(true);
				dlg.show();
			}
		});
		
		btn_save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				int res = checkDataAvailable();
				if(0 == res){
					submitOutSalCreate();
				}else{
					showToast(R.string.msg_data_incomplete);
				}
			}
		});
		
		btn_reset.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				resetCreateInfo();
			}
		});
		
		spi_cusDept.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> view, View arg1, int position, long arg3) {
				if(position != 0){
					deptId = OutCreatePreData.cusDeptList.get(position).getValue();
					if(cacheManager.getOutCusDeptExtraCache(DEPTEXTRA_TYPE, deptId) != null){
						bindCusDeptExtraData(cacheManager.getOutCusDeptExtraCache(DEPTEXTRA_TYPE, deptId), true);
					} else {
						loadDeptExtraData();
					}
					spi_warehouse.setSelection(0);
					spi_soPriceLogon.setSelection(0);
					spi_shipToAdress.setSelection(0);
				}else{
					spi_warehouse.setEnabled(false);
					spi_soPriceLogon.setEnabled(false);
					btn_shipTo.setEnabled(false);
					btn_vatTo.setEnabled(false);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> view) {
			}
		});
		
		btn_shipTo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				gotoPickData(REQUESTCODE_SHPITO);
			}
		});
		
		btn_vatTo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				gotoPickData(REQUESTCODE_VATTO);
			}
		});
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
		case ActivityCode.AYSN_DATA_RESULT:
			if (REQUESTCODE_SHPITO == requestCode) {
				if(data.hasExtra(DataConstants.KVRESULT)){
					Bundle bundle = data.getBundleExtra(DataConstants.KVRESULT);
					if (shipToKv == null) {
						shipToKv = new BaseKeyValue();
					}
					shipToKv.setKey(bundle.getString(DataConstants.NAME, ""));
					shipToKv.setValue(bundle.getString(DataConstants.VALUE, ""));
					edt_shipTo.setText(shipToKv.getKey());
					
					if (vatToKv == null) {
						vatToKv = new BaseKeyValue();
					}
					vatToKv.setKey(bundle.getString("VATTONAME", ""));
					vatToKv.setValue(bundle.getString("VATTO", ""));
					edt_vatTo.setText(vatToKv.getKey());
					
					shipToId = shipToKv.getValue();
					loadShipExtraData();
				}
			} else if(REQUESTCODE_VATTO == requestCode) {
				if(data.hasExtra(DataConstants.KVRESULT)){
					Bundle bundle = data.getBundleExtra(DataConstants.KVRESULT);
					if (vatToKv == null) {
						vatToKv = new BaseKeyValue();
					}
					vatToKv.setKey(bundle.getString(DataConstants.NAME, ""));
					vatToKv.setValue(bundle.getString(DataConstants.VALUE, ""));
					edt_vatTo.setText(vatToKv.getKey());
				}
			}
			break;

		default:
			spi_shipToAdress.setEnabled(false);
			break;
		}
	}

	@Override
	public void showLoading() {
		
	}

	@Override
	public void dismissLoading() {
		if(progressDialog != null && progressDialog.isShowing()){
			progressDialog.dismiss();
		}
	}

	@Override
	public void handlerResponse(String response, int what) {
		switch (what) {
		case REQUEST_DEPT:
			bindCustomerDeptData(response, false);
			break;
			
		case REQUEST_DEPTEXTRA:
			bindCusDeptExtraData(response, false);
			break;
			
		case REQUEST_SHIPTOEXTRA:
			bindShipToExtraData(response, false);
			break;
			
		case REQUEST_SUBMIT:
			handlerSubmit(response);
			break;

		default:
			break;
		}
	}
	
	private void resetCreateInfo(){
		edt_outCode2.setText("");
		edt_logno.setText("");
		edt_remarks.setText("");
		edt_outtime.setText("");
		edt_shipTo.setText("");
		edt_vatTo.setText("");
		spi_shipToAdress.setSelection(0);
		spi_cusDept.setSelection(0);
		spi_warehouse.setSelection(0);
		spi_soPriceLogon.setSelection(0);
	}
	
	private void gotoPickData(int requestCode){
		if (REQUESTCODE_SHPITO == requestCode) {
			AsynPreData preData = new AsynPreData();
			preData.setJsonName("CUSTOMERDEPTNAME");
			preData.setJsonTitle("SHIPTOLIST");
			preData.setJsonValue("CUSTOMERDEPTID");
			preData.setUrl(RequestConstants.CUSTOMER_SEARCHBY_KEYWORD);
			preData.setTitleRes(R.string.dataloader_title_customer);
			preData.setType("SHIPTO");
			String[] params1 = new String[2];
			params1[0] = "CUSTOMERDEPTID";
			params1[1] = OutCreatePreData.cusDeptList.get(spi_cusDept.getSelectedItemPosition()).getValue();
			preData.setExtraParams(params1);
			String[] datas = new String[2];
			datas[0] = "VATTO";
			datas[1] = "VATTONAME";
			preData.setExtraDatas(datas);
			startActivityForResult(DataSource.getAsynDataIntent(OutCreateSALActivity.this, preData), requestCode);
		
		} else if (REQUESTCODE_VATTO == requestCode) {
			AsynPreData preData = new AsynPreData();
			preData.setJsonName("CUSTOMERDEPTNAME");
			preData.setJsonTitle("VATTOLIST");
			preData.setJsonValue("CUSTOMERDEPTID");
			preData.setUrl(RequestConstants.CUSTOMER_SEARCHBY_KEYWORD);
			preData.setTitleRes(R.string.dataloader_title_vatto);
			preData.setType("VATTO");
			String[] params2 = new String[2];
			params2[0] = "CUSTOMERDEPTID";
			params2[1] = OutCreatePreData.cusDeptList.get(spi_cusDept.getSelectedItemPosition()).getValue();
			preData.setExtraParams(params2);
			startActivityForResult(DataSource.getAsynDataIntent(OutCreateSALActivity.this, preData), requestCode);
		}
	}
	
	private void submitOutSalCreate(){
		progressDialog = ProgressDialog.show(OutCreateSALActivity.this, getString(R.string.dialog_outsal_create_title), getString(R.string.msg_loadding));
		OutSALCreateRequest request = new OutSALCreateRequest();
		request.setUserId(getPreferences().getUserId());
		request.setToken(getPreferences().getTokenLast8());
		request.setOutType("SAL");
		request.setOutCode2(edt_outCode2.getText().toString().trim());
		request.setCustomerDept(OutCreatePreData.cusDeptList.get(spi_cusDept.getSelectedItemPosition()).getValue());
		request.setLogno(edt_logno.getText().toString().trim());
		request.setOutTime(edt_outtime.getText().toString().trim());
		request.setRemarks(edt_remarks.getText().toString().trim());
		request.setShipTo(shipToKv.getValue());
		request.setShipToAdress(OutCreatePreData.shipToAdressList.get(spi_shipToAdress.getSelectedItemPosition()).getValue());
		request.setSoPriceLogon(OutCreatePreData.soPriceLogonList.get(spi_soPriceLogon.getSelectedItemPosition()).getValue());
		request.setVatTo(vatToKv.getValue());
		request.setWarehouse(OutCreatePreData.warehouseList.get(spi_warehouse.getSelectedItemPosition()).getValue());
		submitRequest(RequestConstants.OUTSAL_CREATE_SUBMIT, request.toJsonString(), REQUEST_SUBMIT);
	}
	
	private void loadCustomerDept(){
		ExRequest request = new ExRequest();
		request.setUserId(getPreferences().getUserId());
		request.setToken(getPreferences().getTokenLast8());
		asynDataRequest(RequestConstants.OUT_GET_CUSTOMERDEPT, request.toJsonString(), REQUEST_DEPT);
	}
	
	private void loadDeptExtraData(){
		OutCusDeptExtraRequest request = new OutCusDeptExtraRequest();
		request.setUserId(getPreferences().getUserId());
		request.setToken(getPreferences().getTokenLast8());
		request.setDeptId(deptId);
		request.setType(DEPTEXTRA_TYPE);
		asynDataRequest(RequestConstants.OUT_GET_DEPT_EXTRA, request.toJsonString(), REQUEST_DEPTEXTRA);
	}
	
	private void loadShipExtraData(){
		OutShipToExtraRequest request = new OutShipToExtraRequest();
		request.setUserId(getPreferences().getUserId());
		request.setToken(getPreferences().getTokenLast8());
		request.setShipToId(shipToId);
		request.setCustomerDeptId(deptId);
		asynDataRequest(RequestConstants.OUT_GET_SHIPTO_EXTRA, request.toJsonString(), REQUEST_SHIPTOEXTRA);
	}
	
	private void handlerSubmit(String data) {
		
		if(progressDialog != null && progressDialog.isShowing()){
			progressDialog.dismiss();
		}
		
		try {
			JSONObject json = new JSONObject(data);
			if(ResponseConstant.OK.equals(json.get(ResponseConstant.STATUS))){
				Toast.makeText(OutCreateSALActivity.this, getResources().getString(R.string.app_action_success), Toast.LENGTH_SHORT).show();
				
				String outId = json.getString("OUTID");
				
				//保存成功进入详情页面
				Intent detail_intent = new Intent(OutCreateSALActivity.this, OutDetailActivity.class);
				detail_intent.putExtra("outtype", "SAL");
				detail_intent.putExtra("outId", outId);
				startActivity(detail_intent);
				OutCreateSALActivity.this.finish();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 将经营单位下拉数据加载到控件
	 * @param data
	 * @return
	 * @throws org.json.JSONException
	 */
	private void bindCustomerDeptData(String data, boolean isFromCache){
		try {
			JSONObject json = new JSONObject(data);
			if(ResponseConstant.OK.equals(json.get(ResponseConstant.STATUS))){
				JSONArray deptArray = json.getJSONArray("CUSTOMERDEPTS");
				
				OutCreatePreData.cusDeptList.clear();
				OutCreatePreData.cusDeptList.add(new BaseKeyValue(getResources().getString(R.string.out_cusdept), ""));
				for (int i = 0; i < deptArray.length(); i++) {
					BaseKeyValue info = new BaseKeyValue();
					JSONObject jsonObject = deptArray.getJSONObject(i);
					info.setValue(jsonObject.getString("CUSTOMERDEPTID"));
					info.setKey(jsonObject.getString("CUSTOMERDEPTNAME"));
					OutCreatePreData.cusDeptList.add(info);
				}
						
				cusDeptAdapter.notifyDataSetChanged();
				
				if(OutCreatePreData.cusDeptList.size() > 0){
					spi_cusDept.setSelection(1);
				}
				if(!isFromCache){
					cacheManager.addOutCusDeptCache(data, getPreferences().getUserId());
				}
			} else {
				showToast(R.string.msg_response_failed);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			showToast(R.string.msg_response_failed);
		}
	}
	
	/**
	 * 将经营单位引起的联动的数据（仓库、销售批次、配送到）加载到控件
	 * @param data
	 * @return
	 * @throws org.json.JSONException
	 */
	private void bindCusDeptExtraData(String data, boolean isFromCache){
		try{
			JSONObject json = new JSONObject(data);
			if(ResponseConstant.OK.equals(json.get(ResponseConstant.STATUS))){
				JSONArray warehouseArray = json.getJSONArray("WAREHOUSES");
						
				OutCreatePreData.warehouseList.clear();
				OutCreatePreData.warehouseList.add(new BaseKeyValue(getResources().getString(R.string.out_warehouse), ""));
						
				for (int i = 0; i < warehouseArray.length(); i++) {
					BaseKeyValue info = new BaseKeyValue();
					JSONObject jsonObject = warehouseArray.getJSONObject(i);
					info.setValue(jsonObject.getString("CUSTOMERDEPTID"));
					info.setKey(jsonObject.getString("CUSTOMERDEPTNAME"));
					OutCreatePreData.warehouseList.add(info);
				}
						
				warehouseAdapter.notifyDataSetChanged();
						
				spi_warehouse.setEnabled(true);
				btn_shipTo.setEnabled(true);
				btn_vatTo.setEnabled(true);
				
				if(!isFromCache){
					cacheManager.addOutCusDeptExtraCache(data, DEPTEXTRA_TYPE, deptId);
				}
			} else {
				showToast(R.string.msg_response_failed);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			showToast(R.string.msg_response_failed);
		}
	}
	
	/**
	 * 将配送到改变引起的联动数据（送货地址、开票到）加载到控件
	 * @param data
	 * @return
	 * @throws org.json.JSONException
	 */
	private void bindShipToExtraData(String data, boolean isFromCache){
		try {
			JSONObject json = new JSONObject(data);
			if(ResponseConstant.OK.equals(json.get(ResponseConstant.STATUS))){
				JSONArray adressArray = json.getJSONArray("ADDRESSES");
				JSONArray sopriceArray = json.getJSONArray("SOPRICELOGONS");
						
				OutCreatePreData.shipToAdressList.clear();
				OutCreatePreData.shipToAdressList.add(new BaseKeyValue(getResources().getString(R.string.out_shipto_adress), ""));
				OutCreatePreData.soPriceLogonList.clear();
				OutCreatePreData.soPriceLogonList.add(new BaseKeyValue(getResources().getString(R.string.out_sopricelogon), ""));
				
				for (int i = 0; i < adressArray.length(); i++) {
					BaseKeyValue info = new BaseKeyValue();
					JSONObject jsonObject = adressArray.getJSONObject(i);
					info.setValue(jsonObject.getString("ADDRESSID"));
					info.setKey(jsonObject.getString("ADDRESS"));
					OutCreatePreData.shipToAdressList.add(info);
				}
				
				for (int i = 0; i < sopriceArray.length(); i++) {
					BaseKeyValue info = new BaseKeyValue();
					JSONObject jsonObject = sopriceArray.getJSONObject(i);
					info.setValue(jsonObject.getString("SOPRICELOGONID"));
					info.setKey(jsonObject.getString("SOPRICELOGONNAME"));
					OutCreatePreData.soPriceLogonList.add(info);
				}
						
				shipToAdressAdapter.notifyDataSetChanged();
				soPriceLogonAdapter.notifyDataSetChanged();
				spi_soPriceLogon.setEnabled(true);
				spi_shipToAdress.setEnabled(true);
				if(adressArray.length() > 0){
					spi_shipToAdress.setSelection(1);
				}
						
				if(!isFromCache){
					cacheManager.addOutShipToExtraCache(data, shipToId);
				}
			} else {
				Toast.makeText(OutCreateSALActivity.this, getResources().getString(R.string.msg_response_failed), Toast.LENGTH_SHORT).show();
			}
		} catch (JSONException e) {
			e.printStackTrace();
			Toast.makeText(OutCreateSALActivity.this, getResources().getString(R.string.msg_response_failed), Toast.LENGTH_SHORT).show();
		}
	}
	
	private int checkDataAvailable(){
		if (0 == spi_cusDept.getSelectedItemPosition()) {
			return 1;
		} else if (0 == spi_warehouse.getSelectedItemPosition()) {
			return 2;
		} else if (0 == spi_soPriceLogon.getSelectedItemPosition()) {
			return 3;
		} else if ("".equals(edt_outtime.getText().toString().trim())) {
			return 4;
		} else if ("".equals(OutCreatePreData.shipToAdressList.get(spi_shipToAdress.getSelectedItemPosition()).getValue())) {
			return 5;
		} else {
			return 0;
		}
	}
	
	private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {  
        public void onDateSet(DatePicker view, int y, int m, int d) {
        	edt_outtime.setText(DateUtil.dateFormatWithDateNum(y, m+1, d));
        }
    };

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
		return super.onOptionsItemSelected(item);
	}
}
