package com.explore.android.mobile.activity.customer;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.explore.android.mobile.data.request.CustomerAddPreRequest;
import com.explore.android.mobile.data.request.CustomerModifyDeptRequest;
import com.explore.android.mobile.data.request.CustomerModifyPreRequest;
import com.explore.android.mobile.model.CustomerArea;
import com.explore.android.mobile.model.KaType;
import com.explore.android.mobile.model.VatToCustomer;
import com.explore.android.mobile.view.DialogUtil;

public class CustomerModifyDeptActivity extends Activity{
	
	private static final String TYPE = "DEPT";

	private EditText edt_amountMoney;
	private EditText edt_zqDay;
	private EditText edt_zqMonth;
	private EditText edt_gdDay;
	private EditText edt_dhRate;
	private Spinner spi_cusDept;
	private Spinner spi_kaType;
	private Spinner spi_cusArea;
	private Spinner spi_dzType;
	private Spinner spi_zqType;
	private Spinner spi_isVat;
	private Spinner spi_isDh;
	private Spinner spi_vatTo;
	
	private Button btn_submit;
	private Button btn_reset;
	private RelativeLayout loading;
	
	private List<BaseKeyValue> isVatList;
	private List<BaseKeyValue> isDhList;
	private List<BaseKeyValue> dzTypeList;
	private List<BaseKeyValue> zqTypeList;
	private List<BaseKeyValue> cusDeptList;
	private List<BaseKeyValue> kaTypeSpinnerList;
	private List<BaseKeyValue> cusAreaSpinnerList;
	private List<VatToCustomer> cusVatToList;
	private List<BaseKeyValue> cusVatToSpinnerList;
	
	private List<KaType> kaTypeList;
	private List<CustomerArea> cusAreaList;
	
	private SimpleSpinnerAdapter isVatAdapter;
	private SimpleSpinnerAdapter isDhAdapter;
	private SimpleSpinnerAdapter kaTypeAdapter;
	private SimpleSpinnerAdapter dzTypeAdapter;
	private SimpleSpinnerAdapter zqTypeAdapter;
	private SimpleSpinnerAdapter cusDeptListAdapter;
	private SimpleSpinnerAdapter cusAreaAdapter;
	private SimpleSpinnerAdapter cusVatToAdapter;
	
	private LinearLayout layout_zqtypeGdr; 	//固定日
	private LinearLayout layout_zqtypeGdt; 	//固定天
	private LinearLayout layout_dhRate;		// 调货扣率
	private LinearLayout layout_vatTo;		// 开票客户
	
	private AsynDataTask modifyPreTask;
	private AsynDataTask initPreDataTask;
	private SubmitTask submitTask;
	private String customerId;
	private SharePreferencesManager preferences;
	
	private String payMoney;
	private String receiveMoney;
	private String cvdId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cusmodify_dept);
		initViews();
		initValues();
		initListener();
	}
	
	private void initViews(){
		spi_cusDept = (Spinner) findViewById(R.id.act_cusmodify_spi_cusdept);
		spi_kaType = (Spinner) findViewById(R.id.act_cusmodify_spi_katype);
		spi_cusArea = (Spinner) findViewById(R.id.act_cusmodify_spi_cusarea);
		spi_dzType = (Spinner) findViewById(R.id.act_cusmodify_spi_dztype);
		spi_zqType = (Spinner) findViewById(R.id.act_cusmodify_spi_zqtype);
		spi_isVat = (Spinner) findViewById(R.id.act_cusmodify_spi_isvat);
		spi_vatTo = (Spinner) findViewById(R.id.act_cusmodify_spi_vatto);
		spi_isDh = (Spinner) findViewById(R.id.act_cusmodify_spi_isdh);
		edt_gdDay = (EditText) findViewById(R.id.act_cusmodify_edt_gdday);
		edt_amountMoney = (EditText) findViewById(R.id.act_cusmodify_edt_amountmoney);
		edt_zqDay = (EditText) findViewById(R.id.act_cusmodify_edt_zqday);
		edt_zqMonth = (EditText) findViewById(R.id.act_cusmodify_edt_zqday_zqmonth);
		edt_dhRate = (EditText) findViewById(R.id.act_cusmodify_edt_dhrate);
		
		layout_dhRate = (LinearLayout) findViewById(R.id.act_cusmodif_dhrate_layout);
		layout_zqtypeGdr = (LinearLayout) findViewById(R.id.act_cusmodif_zqgdr_layout);
		layout_zqtypeGdt = (LinearLayout) findViewById(R.id.act_cusmodif_zqgdt_layout);
		layout_vatTo = (LinearLayout) findViewById(R.id.act_cusmodif_vatto_layout);
		
		btn_submit = (Button) findViewById(R.id.cusmodify_title_dept_btn_submit);
		btn_reset = (Button) findViewById(R.id.cusmodify_title_dept_btn_reset);
		loading = (RelativeLayout) findViewById(R.id.app_loading_layout);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle(R.string.cusmodify_page_title_dept);
	}
	
	private void initValues(){
		
		preferences = SharePreferencesManager.getInstance(this);
		
		isDhList = new ArrayList<BaseKeyValue>();
		isDhList.add(new BaseKeyValue("", ""));
		isDhList.add(new BaseKeyValue(R.string.app_yes, "Y"));
		isDhList.add(new BaseKeyValue(R.string.app_no, "N"));
		isDhAdapter = new SimpleSpinnerAdapter(CustomerModifyDeptActivity.this, isDhList);
		spi_isDh.setAdapter(isDhAdapter);
		
		isVatList = new ArrayList<BaseKeyValue>();
		isVatList.add(new BaseKeyValue("", ""));
		isVatList.add(new BaseKeyValue(R.string.app_yes, "Y"));
		isVatList.add(new BaseKeyValue(R.string.app_no, "N"));
		isVatAdapter = new SimpleSpinnerAdapter(CustomerModifyDeptActivity.this, isVatList);
		spi_isVat.setAdapter(isVatAdapter);
		
		zqTypeList = new ArrayList<BaseKeyValue>();
		zqTypeList.add(new BaseKeyValue("", ""));
		zqTypeList.add(new BaseKeyValue(R.string.customer_modify_dztype_gdr, "GDR"));
		zqTypeList.add(new BaseKeyValue(R.string.customer_modify_dztype_gdt, "GDT"));
		zqTypeAdapter = new SimpleSpinnerAdapter(CustomerModifyDeptActivity.this, zqTypeList);
		spi_zqType.setAdapter(zqTypeAdapter);
		
		dzTypeList = new ArrayList<BaseKeyValue>();
		dzTypeList.add(new BaseKeyValue("", ""));
		dzTypeList.add(new BaseKeyValue(R.string.customer_modify_zqtype_zqd, "ZQD"));
		dzTypeList.add(new BaseKeyValue(R.string.customer_modify_zqtype_jsd, "JSD"));
		dzTypeAdapter = new SimpleSpinnerAdapter(CustomerModifyDeptActivity.this, dzTypeList);
		spi_dzType.setAdapter(dzTypeAdapter);
		
		kaTypeSpinnerList = new ArrayList<BaseKeyValue>();
		kaTypeSpinnerList.add(new BaseKeyValue("", ""));
		kaTypeAdapter = new SimpleSpinnerAdapter(CustomerModifyDeptActivity.this, kaTypeSpinnerList);
		spi_kaType.setAdapter(kaTypeAdapter);
		
		cusAreaSpinnerList = new ArrayList<BaseKeyValue>();
		cusAreaSpinnerList.add(new BaseKeyValue("", ""));
		cusAreaAdapter = new SimpleSpinnerAdapter(CustomerModifyDeptActivity.this, cusAreaSpinnerList);
		spi_cusArea.setAdapter(cusAreaAdapter);
		
		cusDeptList = new ArrayList<BaseKeyValue>();
		cusDeptList.add(new BaseKeyValue("", ""));
		cusDeptListAdapter = new SimpleSpinnerAdapter(CustomerModifyDeptActivity.this, cusDeptList);
		spi_cusDept.setAdapter(cusDeptListAdapter);
		
		cusVatToSpinnerList = new ArrayList<BaseKeyValue>();
		cusVatToSpinnerList.add(new BaseKeyValue("", ""));
		cusVatToAdapter = new SimpleSpinnerAdapter(CustomerModifyDeptActivity.this, cusVatToSpinnerList);
		spi_vatTo.setAdapter(cusVatToAdapter);
		
		kaTypeList = new ArrayList<KaType>();
		cusAreaList = new ArrayList<CustomerArea>();
		cusVatToList = new ArrayList<VatToCustomer>();
		
		Intent intent = getIntent();
		if(intent.hasExtra("CUSTOMERID")){
			customerId = intent.getStringExtra("CUSTOMERID");
			mHandler.sendEmptyMessage(1);
		}
	}
	
	private void initListener(){
		
		/*
		btn_top_left.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				DialogUtil.createMessageDialog(CustomerModifyDeptActivity.this, 
						R.string.product_dlg_exit_modify_title, 
						R.string.product_dlg_exit_modify_info, new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int arg1) {
								dialog.dismiss();
								CustomerModifyDeptActivity.this.finish();
							}
						});
			}
		});
		*/
		
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
		
		spi_zqType.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				if(position == 1){
					layout_zqtypeGdr.setVisibility(View.VISIBLE);
					layout_zqtypeGdt.setVisibility(View.GONE);
					edt_gdDay.setText("");
				} else if(position == 2){
					layout_zqtypeGdr.setVisibility(View.GONE);
					layout_zqtypeGdt.setVisibility(View.VISIBLE);
					edt_zqDay.setText("");
					edt_zqMonth.setText("");
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		
		spi_isDh.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				if(position == 1){
					layout_dhRate.setVisibility(View.VISIBLE);
				} else if(position == 2){
					layout_dhRate.setVisibility(View.GONE);
					edt_dhRate.setText("");
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
		
		spi_cusDept.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
				kaTypeSpinnerList.clear();
				kaTypeSpinnerList.add(new BaseKeyValue("", ""));
				for (int i = 0; i < kaTypeList.size(); i++) {
					if(cusDeptList.get(position).getValue().equals(kaTypeList.get(i).getCustomerDeptId())){
						BaseKeyValue katype = new BaseKeyValue();
						katype.setKey(kaTypeList.get(i).getName());
						katype.setValue(kaTypeList.get(i).getValue());
						kaTypeSpinnerList.add(katype);
					}
				}
				kaTypeAdapter.notifyDataSetChanged();
				
				cusAreaSpinnerList.clear();
				cusAreaSpinnerList.add(new BaseKeyValue("", ""));
				for (int i = 0; i < cusAreaList.size(); i++) {
					if(cusDeptList.get(position).getValue().equals(cusAreaList.get(i).getCustomerId())){
						BaseKeyValue area = new BaseKeyValue();
						area.setKey(cusAreaList.get(i).getAreaName());
						area.setValue(cusAreaList.get(i).getAreaValue());
						cusAreaSpinnerList.add(area);
					}
				}
				cusAreaAdapter.notifyDataSetChanged();
				
				cusVatToSpinnerList.clear();
				cusVatToSpinnerList.add(new BaseKeyValue("", ""));
				for (int i = 0; i < cusVatToList.size(); i++) {
					if(cusDeptList.get(position).getValue().equals(cusVatToList.get(i).getCustomerDeptId())){
						BaseKeyValue vatTo = new BaseKeyValue();
						vatTo.setKey(cusVatToList.get(i).getVatToName());
						vatTo.setValue(cusVatToList.get(i).getVatToId());
						cusVatToSpinnerList.add(vatTo);
					}
				}
				cusVatToAdapter.notifyDataSetChanged();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		
		spi_isVat.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				if(position == 1){
					layout_vatTo.setVisibility(View.VISIBLE);
				} else if(position == 2){
					layout_vatTo.setVisibility(View.GONE);
					spi_vatTo.setSelection(0);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
	}
	
	private void submit(){
		loading.setVisibility(View.VISIBLE);
		submitTask = new SubmitTask();
		submitTask.setTaskOnPostExecuteListener(new TaskOnPostExecuteListener<ExResponse>() {
			@Override
			public void onPostExecute(TaskResult<? extends ExResponse> result) {
				if(result != null && result.Status == BaseAsynTask.STATUS_SUCCESS){
					btn_submit.setEnabled(true);
					loading.setVisibility(View.GONE);
					ExResponse response = result.Value;
					if (ErrorConstants.VALUE_NULL.equals(response.getResMessage())) {
						
						if (response.getResCode() == -3) {
							Toast.makeText(CustomerModifyDeptActivity.this, getResources().getString(R.string.msg_request_timeout), Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(CustomerModifyDeptActivity.this, getResources().getString(R.string.msg_server_nores), Toast.LENGTH_LONG).show();
						}
						return;
					} else {
						try {
							JSONObject json = new JSONObject(response.getResMessage());
							if(ResponseConstant.OK.equals(json.get(ResponseConstant.STATUS))){
								Toast.makeText(CustomerModifyDeptActivity.this, getResources().getString(R.string.msg_response_success), Toast.LENGTH_SHORT).show();
								AppStatus.CUSTOMER_DATA_UPDATE = true;
								btn_submit.setEnabled(true);
								CustomerModifyDeptActivity.this.finish();
							} else if(ResponseConstant.EXCEPTION.equals(json.get(ResponseConstant.STATUS))){
								Toast.makeText(CustomerModifyDeptActivity.this, getResources().getString(R.string.msg_action_failed), Toast.LENGTH_SHORT).show();
							}
						} catch (JSONException e) {
							e.printStackTrace();
							Toast.makeText(CustomerModifyDeptActivity.this, getResources().getString(R.string.msg_action_failed), Toast.LENGTH_SHORT).show();
						}
					}
				}
			}
		});
		
		CustomerModifyDeptRequest request = new CustomerModifyDeptRequest();
		request.setUserId(preferences.getUserId());
		request.setToken(preferences.getTokenLast8());
		request.setType(TYPE);
		request.setCvdId(cvdId);
		request.setCusCusDeptId(cusDeptList.get(spi_cusDept.getSelectedItemPosition()).getValue());
		request.setCusAmountMoney(edt_amountMoney.getText().toString().trim());
		request.setCusKaType(kaTypeSpinnerList.get(spi_kaType.getSelectedItemPosition()).getValue());
		request.setCusArea(cusAreaSpinnerList.get(spi_cusArea.getSelectedItemPosition()).getValue());
		request.setCusDzType(dzTypeList.get(spi_dzType.getSelectedItemPosition()).getValue());
		request.setCusZqType(zqTypeList.get(spi_zqType.getSelectedItemPosition()).getValue());
		request.setCusZqDay(edt_zqDay.getText().toString().trim());
		request.setCusZqMonth(edt_zqMonth.getText().toString().trim());
		request.setCusGdDay(edt_gdDay.getText().toString().trim());
		request.setCusIsVat(isVatList.get(spi_isVat.getSelectedItemPosition()).getValue());
		request.setCusVatTo(cusVatToSpinnerList.get(spi_vatTo.getSelectedItemPosition()).getValue());
		request.setCusIsDh(isDhList.get(spi_isDh.getSelectedItemPosition()).getValue());
		request.setCusDhRate(edt_dhRate.getText().toString().trim());
		request.setPayMoney(payMoney);
		request.setCustomerId(customerId);
		request.setReceiveMoney(receiveMoney);
		submitTask.execute(preferences.getHttpUrl(),RequestConstants.CUSTOMER_MODIFY,request.toJsonString());
	}

	private void loadInitData() {
		initPreDataTask = new AsynDataTask();
		initPreDataTask.setTaskOnPostExecuteListener(new TaskOnPostExecuteListener<ExResponse>() {
			@Override
			public void onPostExecute(TaskResult<? extends ExResponse> result) {
				if (result != null && result.Status == BaseAsynTask.STATUS_SUCCESS) {
					ExResponse response = result.Value;
					if (ErrorConstants.VALUE_NULL.equals(response.getResMessage())) {
						if (response.getResCode() == -3) {
							Toast.makeText(CustomerModifyDeptActivity.this, getResources().getString(R.string.msg_request_timeout), Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(CustomerModifyDeptActivity.this, getResources().getString(R.string.msg_server_nores), Toast.LENGTH_LONG).show();
						}
						mHandler.sendEmptyMessage(3);
						return;
					} else {

						try {
							JSONObject json = new JSONObject(response.getResMessage());
							if (ResponseConstant.OK.equals(json.get(ResponseConstant.STATUS))) {
								bindAddPreData(response.getResMessage());
							} else {
								Toast.makeText(CustomerModifyDeptActivity.this, getResources().getString(R.string.msg_response_failed), Toast.LENGTH_SHORT).show();
							}
						} catch (JSONException e) {
							e.printStackTrace();
							Toast.makeText(CustomerModifyDeptActivity.this, getResources().getString(R.string.msg_response_failed), Toast.LENGTH_SHORT).show();
							mHandler.sendEmptyMessage(3);
						}

					}
				}
			}
		});

		CustomerAddPreRequest request = new CustomerAddPreRequest();
		request.setUserId(preferences.getUserId());
		request.setToken(preferences.getTokenLast8());
		request.setCompanyCode("");
		initPreDataTask.execute(preferences.getHttpUrl(), RequestConstants.CUSTOMER_ADDPRE, request.toJsonString());
	}
	
	private void bindAddPreData(String dataStr) throws JSONException {
		JSONObject json = new JSONObject(dataStr);

		JSONArray cusDeptArray = json.getJSONArray("CUSTOMERDEPTS");
		JSONArray kaTypeArray = json.getJSONArray("KATYPELIST");
		JSONArray cusAreaArray = json.getJSONArray("CUSTOMERAREALIST");
		JSONArray vatToArray = json.getJSONArray("VATTOLIST");
		
		cusDeptList.clear();
		for (int i = 0; i < cusDeptArray.length(); i++) {
			JSONObject jsontemp = cusDeptArray.getJSONObject(i);
			BaseKeyValue dept = new BaseKeyValue();
			dept.setValue(jsontemp.getString("CUSTOMERDEPTID"));
			dept.setKey(jsontemp.getString("CUSTOMERDEPTNAME"));
			cusDeptList.add(dept);
		}
		cusDeptListAdapter.notifyDataSetChanged();

		kaTypeList.clear();
		for (int i = 0; i < kaTypeArray.length(); i++) {
			JSONObject jsontemp = kaTypeArray.getJSONObject(i);
			KaType kaType = new KaType();
			kaType.setValue(jsontemp.getString("KATYPE"));
			kaType.setName(jsontemp.getString("KATYPENAME"));
			kaType.setCustomerDeptId(jsontemp.getString("CUSTOMERDEPTID"));
			kaTypeList.add(kaType);
		}
		
		cusAreaList.clear();
		for (int i = 0; i < cusAreaArray.length(); i++) {
			JSONObject jsontemp = cusAreaArray.getJSONObject(i);
			CustomerArea area = new CustomerArea();
			area.setAreaName(jsontemp.getString("AREANAME"));
			area.setAreaValue(jsontemp.getString("AREAVALUE"));
			area.setCustomerId(jsontemp.getString("CUSTOMERID"));
			cusAreaList.add(area);
		}
		
		cusVatToList.clear();
		for (int i = 0; i < vatToArray.length(); i++) {
			JSONObject jsontemp = vatToArray.getJSONObject(i);
			VatToCustomer vatTo = new VatToCustomer();
			vatTo.setCustomerDeptId(jsontemp.getString("CUSTOMERDEPTID"));
			vatTo.setVatToId(jsontemp.getString("VATTOID"));
			vatTo.setVatToName(jsontemp.getString("VATTONAME"));
			cusVatToList.add(vatTo);
		}
		
		mHandler.sendEmptyMessage(2);
	}
	
	private void loadModifyPreData(){
		modifyPreTask = new AsynDataTask();
		modifyPreTask.setTaskOnPostExecuteListener(new TaskOnPostExecuteListener<ExResponse>(){
			@Override
			public void onPostExecute(TaskResult<? extends ExResponse> result) {
				if(result != null && result.Status == BaseAsynTask.STATUS_SUCCESS){
					mHandler.sendEmptyMessage(3);
					ExResponse response = result.Value;
					if(ErrorConstants.VALUE_NULL.equals(response.getResMessage())){
						if (response.getResCode() == -3) {
							Toast.makeText(CustomerModifyDeptActivity.this, getResources().getString(R.string.msg_request_timeout), Toast.LENGTH_SHORT).show();
						}else{
							Toast.makeText(CustomerModifyDeptActivity.this, getResources().getString(R.string.msg_server_nores), Toast.LENGTH_LONG).show();
						}
						return;
					} else {
						
						try {
							JSONObject json = new JSONObject(response.getResMessage());
							if(ResponseConstant.OK.equals(json.get(ResponseConstant.STATUS))){
								bindAModifyPreData(response.getResMessage());
							} else {
								Toast.makeText(CustomerModifyDeptActivity.this, getResources().getString(R.string.msg_response_failed), Toast.LENGTH_SHORT).show();
							}
						} catch (JSONException e) {
							e.printStackTrace();
							Toast.makeText(CustomerModifyDeptActivity.this, getResources().getString(R.string.msg_response_failed), Toast.LENGTH_SHORT).show();
							mHandler.sendEmptyMessage(3);
						}
						
					}
				}
			}
		});
		CustomerModifyPreRequest request = new CustomerModifyPreRequest();
		SharePreferencesManager preferences = SharePreferencesManager.getInstance(CustomerModifyDeptActivity.this);
		request.setUserId(preferences.getUserId());
		request.setToken(preferences.getTokenLast8());
		request.setCustomerId(customerId);
		request.setType(TYPE);
		modifyPreTask.execute(preferences.getHttpUrl(),RequestConstants.CUSTOMER_MODIFYPRE,request.toJsonString());
	}
	
	private void bindAModifyPreData(String dataStr) throws JSONException{
		JSONObject jsonObject = new JSONObject(dataStr);
		JSONObject json = jsonObject.getJSONObject("DEPT");
		
		String customerDeptId = json.getString("CUSTOMERDEPTID");
		String kaType = json.getString("KATYPE");
		String customerArea = json.getString("CUSTOMERAREA");
		String dzType = json.getString("DZTYPE");
		String zqType = json.getString("ZQTYPE");
		String isVat = json.getString("ISVAT");
		String vatToId = json.getString("VATTO");
		String isDh = json.getString("ISDH");
		
		cvdId = json.getString("CVDID");
		payMoney = json.getString("PAYMONEY");
		receiveMoney = json.getString("RECEIVEMONEY");
		
		if("GDR".equals(zqType)){
			layout_zqtypeGdr.setVisibility(View.VISIBLE);
			layout_zqtypeGdt.setVisibility(View.GONE);
			edt_zqDay.setText(json.getString("ZQDAY"));
			edt_zqMonth.setText(json.getString("ZQMONTHM"));
		} else if("GDT".equals(zqType)){
			layout_zqtypeGdr.setVisibility(View.GONE);
			layout_zqtypeGdt.setVisibility(View.VISIBLE);
			edt_gdDay.setText("GDDAY");
		}
		
		for (int i = 0; i < zqTypeList.size(); i++) {
			if(zqTypeList.get(i).getValue().equals(zqType)){
				spi_zqType.setSelection(i);
			}
		}
		
		if("Y".equals(isDh)){
			layout_dhRate.setVisibility(View.VISIBLE);
			if(!"null".equals(json.getString("DHRATE"))){
				edt_dhRate.setText(json.getString("DHRATE"));
			} else{
				edt_dhRate.setText(json.getString(""));
			}
		} else {
			layout_dhRate.setVisibility(View.GONE);
		}
		
		for (int i = 0; i < isVatList.size(); i++) {
			if(isVatList.get(i).getValue().equals(isVat)){
				spi_isVat.setSelection(i);
			}
		}
		
		for (int i = 0; i < isDhList.size(); i++) {
			if(isDhList.get(i).getValue().equals(isDh)){
				spi_isDh.setSelection(i);
			}
		}
		
		for (int i = 0; i < dzTypeList.size(); i++) {
			if(dzTypeList.get(i).getValue().equals(dzType)){
				spi_dzType.setSelection(i);
			}
		}
		
		for (int i = 0; i < cusDeptList.size(); i++) {
			if(cusDeptList.get(i).getValue().equals(customerDeptId)){
				spi_cusDept.setSelection(i);
			}
		}
		
		kaTypeSpinnerList.clear();
		kaTypeSpinnerList.add(new BaseKeyValue("", ""));
		for (int i = 0; i < kaTypeList.size(); i++) {
			if(customerDeptId.equals(kaTypeList.get(i).getCustomerDeptId())){
				BaseKeyValue katype = new BaseKeyValue();
				katype.setKey(kaTypeList.get(i).getName());
				katype.setValue(kaTypeList.get(i).getValue());
				kaTypeSpinnerList.add(katype);
			}
		}
		kaTypeAdapter.notifyDataSetChanged();
		
		cusAreaSpinnerList.clear();
		cusAreaSpinnerList.add(new BaseKeyValue("", ""));
		for (int i = 0; i < cusAreaList.size(); i++) {
			if(customerDeptId.equals(cusAreaList.get(i).getCustomerId())){
				BaseKeyValue area = new BaseKeyValue();
				area.setKey(cusAreaList.get(i).getAreaName());
				area.setValue(cusAreaList.get(i).getAreaValue());
				cusAreaSpinnerList.add(area);
			}
		}
		cusAreaAdapter.notifyDataSetChanged();
		
		cusVatToSpinnerList.clear();
		cusVatToSpinnerList.add(new BaseKeyValue("", ""));
		for (int i = 0; i < cusVatToList.size(); i++) {
			if(customerDeptId.equals(cusVatToList.get(i).getCustomerDeptId())){
				BaseKeyValue kv = new BaseKeyValue();
				kv.setKey(cusVatToList.get(i).getVatToName());
				kv.setValue(cusVatToList.get(i).getVatToId());
				cusVatToSpinnerList.add(kv);
			}
		}
		cusVatToAdapter.notifyDataSetChanged();
		
		
		for (int i = 0; i < kaTypeSpinnerList.size(); i++) {
			if(kaTypeSpinnerList.get(i).getValue().equals(kaType)){
				spi_kaType.setSelection(i);
			}
		}
		
		for (int i = 0; i < cusAreaSpinnerList.size(); i++) {
			if(cusAreaSpinnerList.get(i).getValue().equals(customerArea)){
				spi_cusArea.setSelection(i);
			}
		}
		
		for (int i = 0; i < cusVatToSpinnerList.size(); i++) {
			if(cusVatToSpinnerList.get(i).getValue().equals(vatToId)){
				spi_vatTo.setSelection(i);
			}
		}
		
		edt_amountMoney.setText(json.getString("AMOUNTMONEY"));
	}
	
	private void backWithConfirm() {
		DialogUtil.createMessageDialog(CustomerModifyDeptActivity.this, 
				R.string.product_dlg_exit_modify_title, 
				R.string.product_dlg_exit_modify_info, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int arg1) {
						dialog.dismiss();
						CustomerModifyDeptActivity.this.finish();
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
		spi_cusDept.setSelection(0);
		spi_kaType.setSelection(0);
		spi_dzType.setSelection(0);
		spi_zqType.setSelection(0);
		spi_isVat.setSelection(0);
		spi_isDh.setSelection(0);
		edt_gdDay.setText("");
		edt_amountMoney.setText("");
		edt_zqDay.setText("");
		edt_zqMonth.setText("");
	}
	
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				loading.setVisibility(View.VISIBLE);
				loadInitData();
				break;
			case 2:
				loadModifyPreData();
				break;
			case 3:
				loading.setVisibility(View.GONE);
			default:
				break;
			}
		}
		
	};
}
