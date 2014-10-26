package com.explore.android.mobile.activity.out;

import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.DatePickerDialog;
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
import com.explore.android.mobile.data.request.OutShipToExtraRequest;

public class OutCreateDTCActivity extends BaseHttpActivity{
	
	private static final int REQUEST_DEPT = 1;
	private static final int REQUEST_DEPTEXTRA = 2;
	private static final int REQUEST_SHIPTOEXREA = 3;
	
	private static final int REQUESTCODE_SHIPTO = 1;
	private static final int REQUESTCODE_VATTO = 2;
	private static final int REQUESTCODE_SAVE = 3;
	private static final String DEPTEXTRA_TYPE = "S";
	
	private EditText edt_transportCode1;
	private Spinner spi_shipToAdress;
	private Spinner spi_cusDept;
	private Spinner spi_soPriceLogon;
	
	private EditText edt_shipTo;
	private EditText edt_vatTo;
	private Button btn_shipTo;
	private Button btn_vatTo;
	
	private Button btn_pickOutTime;
	private EditText edt_outtime;
	
	private Button btn_save;
	private Button btn_reset;
	
	private ExCacheManager cacheManager;
	
	private SimpleSpinnerAdapter cusDeptAdapter;
	private SimpleSpinnerAdapter soPriceLogonAdapter;
	private SimpleSpinnerAdapter shipToAdressAdapter;
	
	private int year,month,date;
	private String shipToId,deptId;
	
	private BaseKeyValue shipToKv;
	private BaseKeyValue vatToKv;
	
	@Override
	protected int setLayoutId() {
		return R.layout.frag_outdtc_create_layout;
	}

	@Override
	public void initViews() {
		edt_transportCode1 = (EditText) findViewById(R.id.frag_edt_outdtc_transportCode1);
		
		spi_shipToAdress = (Spinner) findViewById(R.id.frag_edt_outdtc_address);
		spi_cusDept = (Spinner) findViewById(R.id.frag_spi_outdtc_cusdept);
		spi_soPriceLogon = (Spinner) findViewById(R.id.frag_spi_outdtc_sologon);
		
		edt_shipTo = (EditText) findViewById(R.id.frag_edt_outdtc_shipto);
		edt_vatTo = (EditText) findViewById(R.id.frag_edt_outdtc_vatto);
		btn_shipTo = (Button) findViewById(R.id.frag_btn_outdtc_shipto);
		btn_vatTo = (Button) findViewById(R.id.frag_btn_outdtc_vatto);
		
		btn_pickOutTime = (Button) findViewById(R.id.frag_btn_outdtc_outtime);
		edt_outtime = (EditText) findViewById(R.id.frag_edt_outdtc_outtime);
		
		btn_save = (Button) findViewById(R.id.frag_btn_outdtc_save);
		btn_reset = (Button) findViewById(R.id.frag_btn_outdtc_reset);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle(R.string.outdtc_title);
	}

	@Override
	public void initValues() {
		cacheManager = ExCacheManager.getInstance(this);
		
		shipToKv = new BaseKeyValue("", "");
		vatToKv = new BaseKeyValue("", "");
		
		OutCreatePreData.init(this);
		
		cusDeptAdapter = new SimpleSpinnerAdapter(this, OutCreatePreData.cusDeptList);
		soPriceLogonAdapter = new SimpleSpinnerAdapter(this, OutCreatePreData.soPriceLogonList);
		shipToAdressAdapter = new SimpleSpinnerAdapter(this, OutCreatePreData.shipToAdressList);
		
		spi_cusDept.setAdapter(cusDeptAdapter);
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
				DatePickerDialog dlg = new DatePickerDialog(OutCreateDTCActivity.this, onDateSetListener, year, month, date);
				dlg.setCanceledOnTouchOutside(true);
				dlg.show();
			}
		});
		
		btn_save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				int res = checkDataAvailable();
				if(0 == res){
					Intent dtc_intent = new Intent(OutCreateDTCActivity.this, OutCreateDtcDetailActivity.class);
					dtc_intent.putExtra("TRANSPORTCODE1", edt_transportCode1.getText().toString().trim());
					dtc_intent.putExtra("CUSTOMERDEPT", OutCreatePreData.cusDeptList.get(spi_cusDept.getSelectedItemPosition()).getValue());
					dtc_intent.putExtra("CUSTOMERDEPTNAME", OutCreatePreData.cusDeptList.get(spi_cusDept.getSelectedItemPosition()).getKey());
					dtc_intent.putExtra("OUTTIME", edt_outtime.getText().toString().trim());
					dtc_intent.putExtra("SHIPTO", shipToKv.getValue());
					dtc_intent.putExtra("SHIPTONAME", shipToKv.getKey());
					dtc_intent.putExtra("SHIPTOADRESS", OutCreatePreData.shipToAdressList.get(spi_shipToAdress.getSelectedItemPosition()).getValue());
					dtc_intent.putExtra("SHIPTOADRESSNAME", OutCreatePreData.shipToAdressList.get(spi_shipToAdress.getSelectedItemPosition()).getKey());
					dtc_intent.putExtra("SOPRICELOGON", OutCreatePreData.soPriceLogonList.get(spi_soPriceLogon.getSelectedItemPosition()).getValue());
					dtc_intent.putExtra("SOPRICELOGONNAME", OutCreatePreData.soPriceLogonList.get(spi_soPriceLogon.getSelectedItemPosition()).getKey());
					dtc_intent.putExtra("VATTO", vatToKv.getValue());
					dtc_intent.putExtra("VATTONAME", vatToKv.getKey());
					startActivityForResult(dtc_intent, REQUESTCODE_SAVE);
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
					if(cacheManager.getOutCusDeptExtraCache(DEPTEXTRA_TYPE,deptId) == null){
						loadDeptExtraData();
					} else {
						bindCusDeptExtraData(cacheManager.getOutCusDeptExtraCache(DEPTEXTRA_TYPE,deptId), true);
					}
					spi_shipToAdress.setSelection(0);
					spi_soPriceLogon.setSelection(0);
				} else {
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
	public void showLoading() {
		return;
	}

	@Override
	public void dismissLoading() {
		return;
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
			
		case REQUEST_SHIPTOEXREA:
			bindShipToExtraData(response, false);
			break;

		default:
			break;
		}
	}
	
	private void gotoPickData(int requestCode){
		if (REQUESTCODE_SHIPTO == requestCode) {
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
			startActivityForResult(DataSource.getAsynDataIntent(OutCreateDTCActivity.this, preData), requestCode);
		
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
			startActivityForResult(DataSource.getAsynDataIntent(OutCreateDTCActivity.this, preData), requestCode);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case ActivityCode.OUTDTC_SAVE_SUCCESS:
			//保存成功进入详情页面
			Intent detail_intent = new Intent(OutCreateDTCActivity.this, OutDetailActivity.class);
			Bundle bundle = data.getExtras();
			String outId = bundle.getString("OUTID");
			detail_intent.putExtra("outtype", "DTC");
			detail_intent.putExtra("outId", outId);
			startActivity(detail_intent);
			OutCreateDTCActivity.this.finish();
			break;
			
		case ActivityCode.AYSN_DATA_RESULT:
			if(requestCode == REQUESTCODE_SHIPTO) {
				if(data.hasExtra(DataConstants.KVRESULT)){
					Bundle bundle2 = data.getBundleExtra(DataConstants.KVRESULT);
					shipToKv = new BaseKeyValue();
					shipToKv.setKey(bundle2.getString(DataConstants.NAME, ""));
					shipToKv.setValue(bundle2.getString(DataConstants.VALUE, ""));
					edt_shipTo.setText(shipToKv.getKey());
					
					vatToKv = new BaseKeyValue();
					vatToKv.setKey(bundle2.getString("VATTONAME", ""));
					vatToKv.setValue(bundle2.getString("VATTO", ""));
					edt_vatTo.setText(vatToKv.getKey());
					
					shipToId = shipToKv.getValue();
					loadShipToExtraData();
				}
				
			} else if(requestCode == REQUESTCODE_VATTO) {
				if(data.hasExtra(DataConstants.KVRESULT)){
					Bundle bundle3 = data.getBundleExtra(DataConstants.KVRESULT);
					if(vatToKv == null) {
						vatToKv = new BaseKeyValue();
					}
					vatToKv.setKey(bundle3.getString(DataConstants.NAME, ""));
					vatToKv.setValue(bundle3.getString(DataConstants.VALUE, ""));
					edt_vatTo.setText(vatToKv.getKey());
				}
			}
			break;

		default:
			break;
		}
	}
	
	private void resetCreateInfo(){
		edt_transportCode1.setText("");
		edt_outtime.setText("");
		spi_shipToAdress.setSelection(0);
		spi_cusDept.setSelection(0);
		spi_soPriceLogon.setSelection(0);
		edt_shipTo.setText("");
		edt_vatTo.setText("");
	}
	
	/**
	 * 获取经营单位信息
	 */
	private void loadCustomerDept(){
		ExRequest request = new ExRequest();
		request.setUserId(getPreferences().getUserId());
		request.setToken(getPreferences().getTokenLast8());
		asynDataRequest(RequestConstants.OUT_GET_CUSTOMERDEPT, request.toJsonString(), REQUEST_DEPT);
	}
	
	/**
	 * 在经营单位选项改变时，根据经营单位获取对应仓库、销售批次、配送到的数据
	 * @param deptId
	 */
	private void loadDeptExtraData(){
		OutCusDeptExtraRequest request = new OutCusDeptExtraRequest();
		request.setUserId(getPreferences().getUserId());
		request.setToken(getPreferences().getTokenLast8());
		request.setDeptId(deptId);
		request.setType(DEPTEXTRA_TYPE);
		asynDataRequest(RequestConstants.OUT_GET_DEPT_EXTRA, request.toJsonString(), REQUEST_DEPTEXTRA);
	}
	
	/**
	 * 根据配送到获取送货地址、开票到的数据
	 * @param shipToId
	 */
	private void loadShipToExtraData(){
		OutShipToExtraRequest request = new OutShipToExtraRequest();
		request.setUserId(getPreferences().getUserId());
		request.setToken(getPreferences().getTokenLast8());
		request.setShipToId(shipToId);
		request.setCustomerDeptId(deptId);
		asynDataRequest(RequestConstants.OUT_GET_SHIPTO_EXTRA, request.toJsonString(), REQUEST_SHIPTOEXREA);
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
				JSONArray sopriceArray = json.getJSONArray("SOPRICELOGONS");
						
				OutCreatePreData.soPriceLogonList.clear();
				OutCreatePreData.soPriceLogonList.add(new BaseKeyValue(getResources().getString(R.string.out_sopricelogon), ""));
						
				for (int i = 0; i < sopriceArray.length(); i++) {
					BaseKeyValue info = new BaseKeyValue();
					JSONObject jsonObject = sopriceArray.getJSONObject(i);
					info.setValue(jsonObject.getString("SOPRICELOGONID"));
					info.setKey(jsonObject.getString("SOPRICELOGONNAME"));
					OutCreatePreData.soPriceLogonList.add(info);
				}
						
				soPriceLogonAdapter.notifyDataSetChanged();
				spi_soPriceLogon.setEnabled(true);
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
						
				OutCreatePreData.shipToAdressList.clear();
				OutCreatePreData.shipToAdressList.add(new BaseKeyValue(getResources().getString(R.string.out_shipto_adress), ""));
				
				for (int i = 0; i < adressArray.length(); i++) {
					BaseKeyValue info = new BaseKeyValue();
					JSONObject jsonObject = adressArray.getJSONObject(i);
					info.setValue(jsonObject.getString("ADDRESSID"));
					info.setKey(jsonObject.getString("ADDRESS"));
					OutCreatePreData.shipToAdressList.add(info);
				}
						
				shipToAdressAdapter.notifyDataSetChanged();
				spi_shipToAdress.setEnabled(true);

				if(adressArray.length() > 0){
					spi_shipToAdress.setSelection(1);
				}
						
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
		if (0 == spi_cusDept.getSelectedItemPosition()){
			return 1;
		} else if(0 == spi_soPriceLogon.getSelectedItemPosition()){
			return 2;
		} else if("".equals(edt_outtime.getText().toString().trim())){
			return 4;
		} else if("".equals(OutCreatePreData.shipToAdressList.get(spi_shipToAdress.getSelectedItemPosition()).getValue())){
			return 5;
		} else{
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
