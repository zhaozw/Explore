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
import com.explore.android.mobile.common.SharePreferencesManager;
import com.explore.android.mobile.constants.ActivityCode;
import com.explore.android.mobile.constants.DataConstants;
import com.explore.android.mobile.constants.RequestConstants;
import com.explore.android.mobile.constants.ResponseConstant;
import com.explore.android.mobile.data.DataSource;
import com.explore.android.mobile.data.predata.AsynPreData;
import com.explore.android.mobile.data.predata.OutCreatePreData;
import com.explore.android.mobile.data.request.ExRequest;
import com.explore.android.mobile.data.request.OutCusDeptExtraRequest;
import com.explore.android.mobile.data.request.OutShipToExtraRequest;
import com.explore.android.mobile.data.request.OutTTCCreateRequest;

public class OutCreateTTCActivity extends BaseHttpActivity{
	
	private static final int REQUEST_CUSDEPT = 1;
	private static final int REQUEST_CUSDEPTEXTRA = 2;
	private static final int REQUEST_SHIPTOEXTRA = 3;
	private static final int REQUEST_SUBMIT = 4;
	
	private static final int REQUESTCODE_SHIPTO = 1;
	private static final int REQUESTCODE_SHIPFROM = 2;
	private static final int REQUESTCODE_VATTO = 3;
	
	private static final String DEPTEXTRA_TYPE = "WS";

	private EditText edt_outCode2;
	private EditText edt_logon;
	private EditText edt_remarks;
	
	private Spinner spi_shipToAdress;
	private Spinner spi_cusDept;
	private Spinner spi_warehouse;
	private Spinner spi_soPriceLogon;
	
	private EditText edt_shipTo;
	private EditText edt_shipFrom;
	private EditText edt_vatTo;
	private Button btn_shipTo;
	private Button btn_shipFrom;
	private Button btn_vatTo;
	
	private Button btn_pickOutTime;
	private EditText edt_outtime;
	
	private Button btn_save;
	private Button btn_reset;
	
	private SharePreferencesManager preferences;
	private ExCacheManager cacheManager;
	
	private SimpleSpinnerAdapter cusDeptAdapter;
	private SimpleSpinnerAdapter warehouseAdapter;
	private SimpleSpinnerAdapter soPriceLogonAdapter;
	private SimpleSpinnerAdapter shipToAdressAdapter;
	
	private int year,month,date;
	private String shipToId,deptId;
	private BaseKeyValue shipToKv;
	private BaseKeyValue shipFromKv;
	private BaseKeyValue vatToKv;
	
	
	private ProgressDialog progressDialog;
	
	@Override
	protected int setLayoutId() {
		return R.layout.frag_outttc_create_layout;
	}

	@Override
	public void initViews() {
		edt_outCode2 = (EditText) findViewById(R.id.frag_edt_outttc_outcode2);
		edt_logon = (EditText) findViewById(R.id.frag_edt_outttc_logno);
		edt_remarks = (EditText) findViewById(R.id.frag_edt_outttc_remarks);
		
		spi_shipToAdress = (Spinner) findViewById(R.id.frag_spi_outttc_shiptoadress);
		spi_cusDept = (Spinner) findViewById(R.id.frag_spi_outttc_cusdept);
		spi_warehouse = (Spinner) findViewById(R.id.frag_spi_outttc_warehouse);
		spi_soPriceLogon = (Spinner) findViewById(R.id.frag_spi_outttc_sologon);
		
		edt_shipTo = (EditText) findViewById(R.id.frag_edt_outttc_shipto);
		edt_shipFrom = (EditText) findViewById(R.id.frag_edt_outttc_shipFrom);
		edt_vatTo = (EditText) findViewById(R.id.frag_edt_outttc_vatto);
		btn_shipTo = (Button) findViewById(R.id.frag_btn_outttc_shipto);
		btn_vatTo = (Button) findViewById(R.id.frag_btn_outttc_vatto);
		btn_shipFrom = (Button) findViewById(R.id.frag_btn_outttc_shipFrom);
		
		btn_pickOutTime = (Button) findViewById(R.id.frag_btn_outttc_outtime);
		edt_outtime = (EditText) findViewById(R.id.frag_edt_outttc_outtime);
		
		btn_save = (Button) findViewById(R.id.frag_btn_outttc_save);
		btn_reset = (Button) findViewById(R.id.frag_btn_outttc_reset);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle(R.string.outttc_title);
	}

	@Override
	public void initValues() {
		preferences = SharePreferencesManager.getInstance(this);
		cacheManager = ExCacheManager.getInstance(this);
		
		shipToKv = new BaseKeyValue("", "");
		shipFromKv = new BaseKeyValue("", "");
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
		
		if(cacheManager.getOutCusDeptCache(preferences.getUserId()) == null){
			loadCustomerDept();
		} else {
			bindCustomerDeptData(cacheManager.getOutCusDeptCache(preferences.getUserId()), true);
		}
	}

	@Override
	public void initListener() {
		btn_save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				int res = checkDataAvailable();
				if(0 == res){
					submitOutTTCCreate();
				}else{
					Toast.makeText(OutCreateTTCActivity.this, res + getResources().getString(R.string.msg_request_cannot_blank), Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		btn_reset.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				resetCreateInfo();
			}
		});
		
		btn_pickOutTime.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DatePickerDialog dlg = new DatePickerDialog(OutCreateTTCActivity.this, onDateSetListener, year, month, date);
				dlg.setCanceledOnTouchOutside(true);
				dlg.show();
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
						loadTTCDeptExtraData();
					}
					spi_warehouse.setSelection(0);
					spi_soPriceLogon.setSelection(0);
					spi_shipToAdress.setSelection(0);
				}else{
					spi_warehouse.setEnabled(false);
					spi_soPriceLogon.setEnabled(false);
					btn_shipTo.setEnabled(false);
					btn_shipFrom.setEnabled(false);
					btn_vatTo.setEnabled(false);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> view) {
			}
		});
		
		btn_shipFrom.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				gotoPickData(REQUESTCODE_SHIPFROM);
			}
		});
		
		btn_shipTo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				gotoPickData(REQUESTCODE_SHIPTO);
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
	public void handlerResponse(String response, int what) {
		switch (what) {
		case REQUEST_CUSDEPT:
			bindCustomerDeptData(response, false);
			break;
			
		case REQUEST_CUSDEPTEXTRA:
			bindCusDeptExtraData(response, false);
			break;
			
		case REQUEST_SHIPTOEXTRA:
			bindShipToExtraData(response, false);
			break;
			
		case REQUEST_SUBMIT:
			handlerSubmitData(response);
			break;

		default:
			break;
		}
	}

	@Override
	public void showLoading() {
		return;
	}

	@Override
	public void dismissLoading() {
		return;
	}
	
	private void gotoPickData(int requestCode){
		if (REQUESTCODE_SHIPTO == requestCode || REQUESTCODE_SHIPFROM == requestCode) {
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
			startActivityForResult(DataSource.getAsynDataIntent(OutCreateTTCActivity.this, preData), requestCode);
		
		} else if (REQUESTCODE_VATTO == requestCode) {
			AsynPreData preData = new AsynPreData();
			preData.setJsonName("CUSTOMERDEPTNAME");
			preData.setJsonTitle("SHIPTOLIST");
			preData.setJsonValue("CUSTOMERDEPTID");
			preData.setUrl(RequestConstants.CUSTOMER_SEARCHBY_KEYWORD);
			preData.setTitleRes(R.string.dataloader_title_vatto);
			preData.setType("VATTO");
			String[] params2 = new String[2];
			params2[0] = "CUSTOMERDEPTID";
			params2[1] = OutCreatePreData.cusDeptList.get(spi_cusDept.getSelectedItemPosition()).getValue();
			preData.setExtraParams(params2);
			startActivityForResult(DataSource.getAsynDataIntent(OutCreateTTCActivity.this, preData), requestCode);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
		case ActivityCode.AYSN_DATA_RESULT:
			if(requestCode == REQUESTCODE_SHIPTO) {
				if(data.hasExtra(DataConstants.KVRESULT)){
					Bundle bundle = data.getBundleExtra(DataConstants.KVRESULT);
					shipToKv = new BaseKeyValue();
					shipToKv.setKey(bundle.getString(DataConstants.NAME, ""));
					shipToKv.setValue(bundle.getString(DataConstants.VALUE, ""));
					edt_shipTo.setText(shipToKv.getKey());
					
					vatToKv = new BaseKeyValue();
					vatToKv.setKey(bundle.getString("VATTONAME", ""));
					vatToKv.setValue(bundle.getString("VATTO", ""));
					edt_vatTo.setText(vatToKv.getKey());
					
					shipToId = shipToKv.getValue();
					loadShipExtraData();
				}
				
			} else if(requestCode == REQUESTCODE_VATTO) {
				if(data.hasExtra(DataConstants.KVRESULT)){
					Bundle bundle = data.getBundleExtra(DataConstants.KVRESULT);
					if(vatToKv == null) {
						vatToKv = new BaseKeyValue();
					}
					vatToKv.setKey(bundle.getString(DataConstants.NAME, ""));
					vatToKv.setValue(bundle.getString(DataConstants.VALUE, ""));
					edt_vatTo.setText(vatToKv.getKey());
				}
				
			} else if(requestCode == REQUESTCODE_SHIPFROM) {
				if(data.hasExtra(DataConstants.KVRESULT)){
					Bundle bundle = data.getBundleExtra(DataConstants.KVRESULT);
					shipFromKv = new BaseKeyValue();
					shipFromKv.setKey(bundle.getString(DataConstants.NAME, ""));
					shipFromKv.setValue(bundle.getString(DataConstants.VALUE, ""));
					edt_shipFrom.setText(shipFromKv.getKey());
				}
			}
			break;
		default:
			break;
		}
	}
	
	private void resetCreateInfo(){
		edt_outCode2.setText("");
		edt_logon.setText("");
		edt_remarks.setText("");
		edt_outtime.setText("");
		edt_shipTo.setText("");
		edt_shipFrom.setText("");
		edt_vatTo.setText("");
		spi_shipToAdress.setSelection(0, true);
		spi_cusDept.setSelection(0, true);
		spi_warehouse.setSelection(0, true);
		spi_soPriceLogon.setSelection(0, true);
	}
	
	/**
	 * 提交保存
	 */
	private void submitOutTTCCreate(){
		progressDialog = ProgressDialog.show(OutCreateTTCActivity.this, getString(R.string.dialog_outsal_create_title), getString(R.string.msg_loadding));

		OutTTCCreateRequest request = new OutTTCCreateRequest();
		request.setUserId(preferences.getUserId());
		request.setToken(preferences.getTokenLast8());
		request.setOutType("SAL");
		request.setOutType2("TTC");
		request.setOutCode2(edt_outCode2.getText().toString().trim());
		request.setShipFrom(shipFromKv.getValue());
		request.setCustomerDept(OutCreatePreData.cusDeptList.get(spi_cusDept.getSelectedItemPosition()).getValue());
		request.setLogon(edt_logon.getText().toString().trim());
		request.setOutTime(edt_outtime.getText().toString().trim());
		request.setRemarks(edt_remarks.getText().toString().trim());
		request.setShipTo(shipToKv.getValue());
		request.setShipToAdress(OutCreatePreData.shipToAdressList.get(spi_shipToAdress.getSelectedItemPosition()).getValue());
		request.setSoPriceLogon(OutCreatePreData.soPriceLogonList.get(spi_soPriceLogon.getSelectedItemPosition()).getValue());
		request.setVatTo(vatToKv.getValue());
		request.setWarehouse(OutCreatePreData.warehouseList.get(spi_warehouse.getSelectedItemPosition()).getValue());
		submitRequest(RequestConstants.OUTTTC_CREATE_SUBMIT, request.toJsonString(), REQUEST_SUBMIT);
	}
	
	/**
	 * 获取经营单位下拉数据
	 */
	private void loadCustomerDept(){
		ExRequest request = new ExRequest();
		request.setUserId(preferences.getUserId());
		request.setToken(preferences.getTokenLast8());
		asynDataRequest(RequestConstants.OUT_GET_CUSTOMERDEPT, request.toJsonString(), REQUEST_CUSDEPT);
	}
	
	/**
	 * 在经营单位选项改变时，根据经营单位获取对应仓库、销售批次、配送到的数据
	 * @param deptId
	 */
	private void loadTTCDeptExtraData(){
		OutCusDeptExtraRequest request = new OutCusDeptExtraRequest();
		request.setUserId(preferences.getUserId());
		request.setToken(preferences.getTokenLast8());
		request.setDeptId(deptId);
		request.setType(DEPTEXTRA_TYPE);
		asynDataRequest(RequestConstants.OUT_GET_DEPT_EXTRA, request.toJsonString(), REQUEST_CUSDEPTEXTRA);
	}
	
	/**
	 * 根据配送到获取送货地址、开票到的数据
	 * @param shipToId
	 */
	private void loadShipExtraData(){
		OutShipToExtraRequest request = new OutShipToExtraRequest();
		request.setUserId(preferences.getUserId());
		request.setToken(preferences.getTokenLast8());
		request.setCustomerDeptId(deptId);
		request.setShipToId(shipToId);
		asynDataRequest(RequestConstants.OUT_GET_SHIPTO_EXTRA, request.toJsonString(), REQUEST_SHIPTOEXTRA);
	}
	
	private void handlerSubmitData(String data) {
		progressDialog.dismiss();
		try {
			JSONObject json = new JSONObject(data);
			if(ResponseConstant.OK.equals(json.get(ResponseConstant.STATUS))){
				Toast.makeText(OutCreateTTCActivity.this, getResources().getString(R.string.app_action_success), Toast.LENGTH_SHORT).show();
				
				String outId = json.getString("OUTID");
				
				//保存成功进入详情页面
				Intent detail_intent = new Intent(OutCreateTTCActivity.this, OutDetailActivity.class);
				detail_intent.putExtra("outtype", "TTC");
				detail_intent.putExtra("outId", outId);
				startActivity(detail_intent);
				OutCreateTTCActivity.this.finish();
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
					cacheManager.addOutCusDeptCache(data, preferences.getUserId());
				}
				
				btn_shipTo.setEnabled(true);
				btn_shipFrom.setEnabled(true);
				btn_vatTo.setEnabled(true);
				
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
		try {
			JSONObject json = new JSONObject(data);
			if(ResponseConstant.OK.equals(json.get(ResponseConstant.STATUS))){
						
				JSONArray warehouseArray = json.getJSONArray("WAREHOUSES");
				JSONArray sopriceArray = json.getJSONArray("SOPRICELOGONS");
						
				OutCreatePreData.warehouseList.clear();
				OutCreatePreData.warehouseList.add(new BaseKeyValue(getResources().getString(R.string.out_warehouse), ""));
				OutCreatePreData.soPriceLogonList.clear();
				OutCreatePreData.soPriceLogonList.add(new BaseKeyValue(getResources().getString(R.string.out_sopricelogon), ""));
						
				for (int i = 0; i < warehouseArray.length(); i++) {
					BaseKeyValue info = new BaseKeyValue();
					JSONObject jsonObject = warehouseArray.getJSONObject(i);
					info.setValue(jsonObject.getString("CUSTOMERDEPTID"));
					info.setKey(jsonObject.getString("CUSTOMERDEPTNAME"));
					OutCreatePreData.warehouseList.add(info);
				}
						
				for (int i = 0; i < sopriceArray.length(); i++) {
					BaseKeyValue info = new BaseKeyValue();
					JSONObject jsonObject = sopriceArray.getJSONObject(i);
					info.setValue(jsonObject.getString("SOPRICELOGONID"));
					info.setKey(jsonObject.getString("SOPRICELOGONNAME"));
					OutCreatePreData.soPriceLogonList.add(info);
				}
						
				warehouseAdapter.notifyDataSetChanged();
				soPriceLogonAdapter.notifyDataSetChanged();
						
				spi_warehouse.setEnabled(true);
				spi_soPriceLogon.setEnabled(true);
				
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
						
				OutCreatePreData.shipToAdressList.clear();
				OutCreatePreData.shipToAdressList.add(new BaseKeyValue(getResources().getString(R.string.out_shipto_adress), ""));
						
				for (int i = 0; i < adressArray.length(); i++) {
					BaseKeyValue info = new BaseKeyValue();
					JSONObject jsonObject = adressArray.getJSONObject(i);
					info.setValue(jsonObject.getString("ADDRESSID"));
					info.setKey(jsonObject.getString("ADDRESS"));
					OutCreatePreData.shipToAdressList.add(info);
				}
						
				if(adressArray.length() > 0){
					spi_shipToAdress.setSelection(1);
				}
						
				shipToAdressAdapter.notifyDataSetChanged();
				spi_shipToAdress.setEnabled(true);
				
				if(!isFromCache){
					cacheManager.addOutShipToExtraCache(data, shipToId);
				}
				
			} else {
				showToast(R.string.msg_response_failed);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			showToast(R.string.msg_response_failed);
		}
	}
	
	private int checkDataAvailable(){
		if(0 == spi_cusDept.getSelectedItemPosition()){
			return 1;
		}else if(0 == spi_warehouse.getSelectedItemPosition()){
			return 2;
		}else if(0 == spi_soPriceLogon.getSelectedItemPosition()){
			return 3;
		}else if("".equals(edt_logon.getText().toString().trim())){
			return 4;
		}else if("".equals(edt_outtime.getText().toString().trim())){
			return 5;
		}else if("".equals(OutCreatePreData.shipToAdressList.get(spi_shipToAdress.getSelectedItemPosition()).getValue())){
			return 6;
		}else{
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
