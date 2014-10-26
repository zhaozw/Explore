package com.explore.android.mobile.activity.product;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.explore.android.mobile.data.request.ProductModifyPreRequest;
import com.explore.android.mobile.data.request.ProductModifyRequest;
import com.explore.android.mobile.model.ProductKV;
import com.explore.android.mobile.view.DialogUtil;

public class ProductModifyDetailActivity extends Activity{

	private RelativeLayout loading;
	private LinearLayout layout_productInfo;
	private LinearLayout layout_b2cId;
	private EditText edt_partno;
	private EditText edt_npartno;
	private EditText edt_productName;
	private EditText edt_basePrice;
	private EditText edt_costPrice;
	private EditText edt_productModel;
	private EditText edt_productOtherModel;
	private EditText edt_productOtherRate;
	private EditText edt_productUnitStr;
	private EditText edt_productUnitRateStr;
	private EditText edt_overDay;
	private EditText edt_freshDay;
	private EditText edt_vatRate;
	private EditText edt_length;
	private EditText edt_width;
	private EditText edt_height;
	private EditText edt_gWeight;
	private EditText edt_nWeight;
	private EditText edt_qz;
	private EditText edt_poUnit;
	private EditText edt_soUnit;
	private EditText edt_relationProductId;
	
	private Spinner spi_isB2C;
	private Spinner spi_projectId;
	private Spinner spi_customerDeptId;
	
	private SimpleSpinnerAdapter isB2CAdapter;
	private SimpleSpinnerAdapter projectAdapter;
	private SimpleSpinnerAdapter cusDeptAdapter;
	
	private Button btn_submit;
	private Button btn_reset;
	
	private SubmitTask submitTask;
	private AsynDataTask loadPreDataTask;
	
	private List<BaseKeyValue> projectSpiList;
	private List<BaseKeyValue> cusdeptList;
	private List<BaseKeyValue> isB2CList;
	
	private List<ProductKV> productKVList;
	private List<ProProject> projectList;
	private List<ProKey> proKeys;
	
	private String productCategoryId;
	
	private List<Spinner> infoSpinners;
	private List<SimpleSpinnerAdapter> infoAdapters;
	
	private LayoutParams LP_MW = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	private LayoutParams TITLE_MW;
	
	private String requestUrl;
	private String requestUrl2;
	private String productId;
	
	private static final int MODE_NEW = 1;
	private static final int MODE_MODIFY = 2;
	
	private int mode;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_product_detail);
		
		int width = getResources().getDimensionPixelSize(R.dimen.product_modify_item_height);
		TITLE_MW = new LayoutParams(LayoutParams.MATCH_PARENT, width);
		
		initViews();
		initValues();
		initListener();
	}
	
	private void initViews(){
		layout_productInfo = (LinearLayout) findViewById(R.id.act_add_prodetail_info_layout);
		loading = (RelativeLayout) findViewById(R.id.app_loading_layout);
		layout_b2cId = (LinearLayout) findViewById(R.id.act_add_product_b2cid_layout);
		edt_partno = (EditText) findViewById(R.id.act_add_product_partno);
		edt_npartno = (EditText) findViewById(R.id.act_add_product_npartno);
		edt_productName = (EditText) findViewById(R.id.act_add_product_proname);
		edt_basePrice = (EditText) findViewById(R.id.act_add_product_baseprice);
		edt_costPrice = (EditText) findViewById(R.id.act_add_product_costprice);
		edt_productModel = (EditText) findViewById(R.id.act_add_product_model);
		edt_productOtherModel = (EditText) findViewById(R.id.act_add_product_othermodel);
		edt_productOtherRate = (EditText) findViewById(R.id.act_add_product_otherrate);
		edt_productUnitStr = (EditText) findViewById(R.id.act_add_product_unitstr);
		edt_productUnitRateStr = (EditText) findViewById(R.id.act_add_product_unitratestr);
		spi_isB2C = (Spinner) findViewById(R.id.act_add_product_isb2C);
		edt_overDay = (EditText) findViewById(R.id.act_add_product_overday);
		edt_freshDay = (EditText) findViewById(R.id.act_add_product_freshday);
		edt_vatRate = (EditText) findViewById(R.id.act_add_product_vatrate);
		edt_length = (EditText) findViewById(R.id.act_add_product_length);
		edt_width = (EditText) findViewById(R.id.act_add_product_width);
		edt_height = (EditText) findViewById(R.id.act_add_product_height);
		edt_gWeight = (EditText) findViewById(R.id.act_add_product_gweight);
		edt_nWeight = (EditText) findViewById(R.id.act_add_product_nweight);
		edt_qz = (EditText) findViewById(R.id.act_add_product_qz);
		edt_poUnit = (EditText) findViewById(R.id.act_add_product_pounit);
		edt_soUnit = (EditText) findViewById(R.id.act_add_product_sounit);
		edt_relationProductId = (EditText) findViewById(R.id.act_add_product_relationproductid);
		spi_projectId = (Spinner) findViewById(R.id.act_add_product_project);
		spi_customerDeptId = (Spinner) findViewById(R.id.act_add_product_cusdept);
		
		btn_submit = (Button) findViewById(R.id.act_add_product_btn_submit);
		btn_reset = (Button) findViewById(R.id.act_add_product_btn_reset);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	private void initValues(){
		
		Intent infoIntent = getIntent();
		if(infoIntent.hasExtra("TYPE")){
			if("new".equals(infoIntent.getStringExtra("TYPE"))){
				productCategoryId = infoIntent.getStringExtra("CATEGORY");
				requestUrl = RequestConstants.PRODUCT_CREATE_PRE;
				requestUrl2 = RequestConstants.PRODUCT_CREATE;
				getActionBar().setTitle(R.string.top_title_add_product_detail);
				mode = MODE_NEW;
			} else if("modify".equals(infoIntent.getStringExtra("TYPE"))){
				productId = infoIntent.getStringExtra("PRODUCTID");
				productCategoryId = infoIntent.getStringExtra("CATEGORY");
				requestUrl = RequestConstants.PRODUCT_MODIFY_PRE;
				requestUrl2 = RequestConstants.PRODUCT_MODIFY;
				getActionBar().setTitle(R.string.top_title_modify_product_detail);
				mode = MODE_MODIFY;
			}
		}
		
		isB2CList = new ArrayList<BaseKeyValue>();
		isB2CList.add(new BaseKeyValue(R.string.product_add_isb2C, ""));
		isB2CList.add(new BaseKeyValue(R.string.app_yes, "Y"));
		isB2CList.add(new BaseKeyValue(R.string.app_no, "N"));
		
		cusdeptList = new ArrayList<BaseKeyValue>();
		cusdeptList.add(new BaseKeyValue(R.string.product_add_cusdept, ""));
		
		projectSpiList = new ArrayList<BaseKeyValue>();
		projectSpiList.add(new BaseKeyValue(R.string.product_add_project, ""));
		projectList = new ArrayList<ProProject>();

		productKVList = new ArrayList<ProductKV>();
		
		isB2CAdapter = new SimpleSpinnerAdapter(this, isB2CList);
		spi_isB2C.setAdapter(isB2CAdapter);
		
		cusDeptAdapter = new SimpleSpinnerAdapter(this, cusdeptList);
		spi_customerDeptId.setAdapter(cusDeptAdapter);
		
		projectAdapter = new SimpleSpinnerAdapter(this, projectSpiList);
		spi_projectId.setAdapter(projectAdapter);
		
		infoSpinners = new ArrayList<Spinner>();
		infoAdapters = new ArrayList<SimpleSpinnerAdapter>();
		
		loadPreData();
	}
	
	private void initListener(){
		
		btn_submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				submit();
			}
		});
		
		btn_reset.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				edt_partno.setText("");
				edt_npartno.setText("");
				edt_productName.setText("");
				edt_basePrice.setText("");
				edt_costPrice.setText("");
				edt_productModel.setText("");
				edt_productOtherModel.setText("");
				edt_productOtherRate.setText("");
				edt_productUnitStr.setText("");
				edt_productUnitRateStr.setText("");
				edt_overDay.setText("");
				edt_freshDay.setText("");
				edt_vatRate.setText("");
				edt_length.setText("");
				edt_width.setText("");
				edt_height.setText("");
				edt_gWeight.setText("");
				edt_nWeight.setText("");
				edt_qz.setText("");
				edt_poUnit.setText("");
				edt_soUnit.setText("");
				edt_relationProductId.setText("");
				
				spi_projectId.setSelection(0);
				spi_customerDeptId.setSelection(0);
				spi_isB2C.setSelection(0);
				
				for (int i = 0; i < infoSpinners.size(); i++) {
					infoSpinners.get(i).setSelection(0);
				}
			}
		});
		
		spi_customerDeptId.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View view, int position, long id) {
				if(position > 0){
					projectSpiList.clear();
					projectSpiList.add(new BaseKeyValue(R.string.product_add_project, ""));
					long deptId = Long.parseLong(cusdeptList.get(position).getValue());
					
					for (int i = 0; i < projectList.size(); i++) {
						if(projectList.get(i).cusDeptId == deptId){
							BaseKeyValue kv = new BaseKeyValue();
							kv.setKey(projectList.get(i).projectName);
							kv.setValue(String.valueOf(projectList.get(i).projectId));
							projectSpiList.add(kv);
						}
					}
					
					projectAdapter.notifyDataSetChanged();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		
		spi_isB2C.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				if("Y".equals(isB2CList.get(position).getValue())){
					layout_b2cId.setVisibility(View.VISIBLE);
				} else {
					edt_relationProductId.setText("");
					layout_b2cId.setVisibility(View.GONE);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	}
	
	private void loadPreData(){
		loading.setVisibility(View.VISIBLE);
		loadPreDataTask = new AsynDataTask();
		loadPreDataTask.setTaskOnPostExecuteListener(new TaskOnPostExecuteListener<ExResponse>() {
			@Override
			public void onPostExecute(TaskResult<? extends ExResponse> result) {
				if(result != null && result.Status == BaseAsynTask.STATUS_SUCCESS){
					loading.setVisibility(View.GONE);
					ExResponse response = result.Value;
					if(ErrorConstants.VALUE_NULL.equals(response.getResMessage())){
						if (response.getResCode() == -3) {
							Toast.makeText(ProductModifyDetailActivity.this, getResources().getString(R.string.msg_request_timeout), Toast.LENGTH_SHORT).show();
						}else{
							Toast.makeText(ProductModifyDetailActivity.this, getResources().getString(R.string.msg_server_nores), Toast.LENGTH_LONG).show();
						}
						return;
					} else {
						
						try {
							JSONObject json = new JSONObject(response.getResMessage());
							if(ResponseConstant.OK.equals(json.get(ResponseConstant.STATUS))){
								bindPreData(response.getResMessage());
							} else {
								Toast.makeText(ProductModifyDetailActivity.this, getResources().getString(R.string.msg_response_failed), Toast.LENGTH_SHORT).show();
							}
						} catch (JSONException e) {
							e.printStackTrace();
							Toast.makeText(ProductModifyDetailActivity.this, getResources().getString(R.string.msg_response_failed), Toast.LENGTH_SHORT).show();
						}
						
					}
				}
			}
		});
		
		ProductModifyPreRequest request = new ProductModifyPreRequest();
		SharePreferencesManager preferences = SharePreferencesManager.getInstance(ProductModifyDetailActivity.this);
		
		request.setUserId(preferences.getUserId());
		request.setToken(preferences.getTokenLast8());
		request.setProductCategoryId(productCategoryId);
		if(mode == 2){
			request.setProductId(productId);
		}
		loadPreDataTask.execute(preferences.getHttpUrl(),requestUrl,request.toJsonString());
	}
	
	private void bindPreData(String dataStr) throws JSONException{
		JSONObject json = new JSONObject(dataStr);
		JSONArray deptArray = json.getJSONArray("CUSTOMERDEPTS");
		
		cusdeptList.clear();
		cusdeptList.add(new BaseKeyValue("", ""));
		for (int i = 0; i < deptArray.length(); i++) {
			BaseKeyValue info = new BaseKeyValue();
			JSONObject jsonObject = deptArray.getJSONObject(i);
			info.setValue(jsonObject.getString("CUSTOMERDEPTID"));
			info.setKey(jsonObject.getString("CUSTOMERDEPTNAME"));
			cusdeptList.add(info);
		}
		cusDeptAdapter.notifyDataSetChanged();
		
		JSONArray projectArray = json.getJSONArray("PROJECTS");
		projectList.clear();
		for (int i = 0; i < projectArray.length(); i++) {
			JSONObject jsonTemp = projectArray.getJSONObject(i);
			ProProject pj = new ProProject();
			pj.projectId = jsonTemp.getLong("CUSTOMERDEPTID");
			pj.projectName = jsonTemp.getString("CUSTOMERDEPTNAME");
			pj.cusDeptId = jsonTemp.getLong("PROJECTCUSTOMERDEPTID");
			projectList.add(pj);
		}
		
		if(json.has("PRODUCTKEYS")){
			JSONArray proKeyArray = json.getJSONArray("PRODUCTKEYS");
			proKeys = new ArrayList<ProKey>();
			for (int i = 0; i < proKeyArray.length(); i++) {
				JSONObject jsonTemp = proKeyArray.getJSONObject(i);
				ProKey key = new ProKey();
				key.productKey = jsonTemp.getString("PRODUCTKEY");
				key.productKeyId = jsonTemp.getLong("PRODUCTKEYID");
				proKeys.add(key);
			}
			
			JSONArray proKVArray = json.getJSONArray("PRODUCTVALUES");
			for (int i = 0; i < proKVArray.length(); i++) {
				JSONObject jsonTemp2 = proKVArray.getJSONObject(i);
				ProValue value = new ProValue();
				value.productKeyId = jsonTemp2.getLong("PRODUCTKEYID");
				value.productValue = jsonTemp2.getString("PRODUCTVALUE");
				value.productValueId = jsonTemp2.getLong("PRODUCTVALUEID");
				
				for (int j = 0; j < proKeys.size(); j++) {
					if (proKeys.get(j).productKeyId == value.productKeyId) {
						proKeys.get(j).kvList.add(value);
					}
				}
			}
			
			for (int i = 0; i < proKeys.size(); i++) {
				List<BaseKeyValue> spiList = new ArrayList<BaseKeyValue>();
				spiList.add(new BaseKeyValue("", ""));
				for (int j = 0; j < proKeys.get(i).kvList.size(); j++) {
					BaseKeyValue bkv = new BaseKeyValue();
					bkv.setKey(proKeys.get(i).kvList.get(j).productValue);
					bkv.setValue(String.valueOf(proKeys.get(i).kvList.get(j).productValueId));
					spiList.add(bkv);
				}
				
				TextView titleText = new TextView(ProductModifyDetailActivity.this);
				titleText.setLayoutParams(TITLE_MW);
				titleText.setText(proKeys.get(i).productKey);
				titleText.setBackgroundColor(Color.parseColor("#FAFAFA"));
				titleText.setPadding(3, 0, 0, 0);
				titleText.setGravity(Gravity.CENTER_VERTICAL);
				layout_productInfo.addView(titleText);
				
				Spinner spinner = new Spinner(ProductModifyDetailActivity.this);
				spinner.setLayoutParams(LP_MW);
				SimpleSpinnerAdapter adapter = new SimpleSpinnerAdapter(ProductModifyDetailActivity.this, spiList);
				spinner.setAdapter(adapter);
				infoAdapters.add(adapter);
				infoSpinners.add(spinner);
				layout_productInfo.addView(spinner);
			}
		}
		
		if(mode == 2){
			
			JSONObject productJson  = json.getJSONObject("PRODUCT");
			edt_partno.setText(productJson.getString("PARTNO"));
			edt_npartno.setText(productJson.getString("NPARTNO"));
			edt_productName.setText(productJson.getString("PRODUCTNAME"));
			edt_basePrice.setText(productJson.getString("BASEPRICE"));
			edt_costPrice.setText(productJson.getString("COSTPRICE"));
			edt_productModel.setText(productJson.getString("PRODUCTMODEL"));
			edt_productUnitStr.setText(productJson.getString("PRODUCTUNITSTR"));
			edt_productUnitRateStr.setText(productJson.getString("PRODUCTUNITRATESTR"));
			edt_overDay.setText(productJson.getString("OVERDAY"));
			edt_freshDay.setText(productJson.getString("FRESHDAY"));
			edt_vatRate.setText(productJson.getString("VATRATE"));
			edt_length.setText(productJson.getString("LENGTH"));
			edt_width.setText(productJson.getString("WIDTH"));
			edt_height.setText(productJson.getString("HEIGHT"));
			edt_gWeight.setText(productJson.getString("GWEIGHT"));
			edt_nWeight.setText(productJson.getString("NWEIGHT"));
			edt_qz.setText(productJson.getString("QZ"));
			edt_poUnit.setText(productJson.getString("POUNIT"));
			edt_soUnit.setText(productJson.getString("SOUNIT"));
			
			if(!"null".equals(productJson.getString("RELATIONPRODUCTID"))){
				edt_relationProductId.setText(productJson.getString("RELATIONPRODUCTID"));
			}
			
			if(!"null".equals(productJson.getString("PRODUCTOTHERRATE"))){
				edt_productOtherRate.setText(productJson.getString("PRODUCTOTHERRATE"));
			}
			
			if(!"null".equals(productJson.getString("PRODUCTOTHERMODEL"))){
				edt_productOtherModel.setText(productJson.getString("PRODUCTOTHERMODEL"));
			}
			
			String cusdeptId = productJson.getString("CUSTOMERDEPTID");
			int deptPostion = 0;
			for (int j = 0; j < cusdeptList.size(); j++) {
				if(cusdeptId.equals(cusdeptList.get(j).getValue())){
					spi_customerDeptId.setSelection(j);
					deptPostion = j;
				}
			}
			
			String projectId = productJson.getString("PROJECTID");
			projectSpiList.clear();
			projectSpiList.add(new BaseKeyValue("", ""));
			long deptId = Long.parseLong(cusdeptList.get(deptPostion).getValue());
			
			for (int i = 0; i < projectList.size(); i++) {
				if(projectList.get(i).cusDeptId == deptId){
					BaseKeyValue kv = new BaseKeyValue();
					kv.setKey(projectList.get(i).projectName);
					kv.setValue(String.valueOf(projectList.get(i).projectId));
					projectSpiList.add(kv);
				}
			}
			
			projectAdapter.notifyDataSetChanged();
			for (int i = 0; i < projectSpiList.size(); i++) {
				if(projectId.equals(projectSpiList.get(i).getValue())){
					spi_projectId.setSelection(i);
				}
			}
			
			String b2c = productJson.getString("ISB2C");
			for (int i = 0; i < isB2CList.size(); i++) {
				if(b2c.equals(isB2CList.get(i).getValue())){
					spi_isB2C.setSelection(i);
				}
			}
			
			if("Y".equals(b2c)){
				layout_b2cId.setVisibility(View.VISIBLE);
			}
			
			JSONArray proKVArray = json.getJSONArray("PRODUCTKV");
			for (int i = 0; i < proKVArray.length(); i++) {
				JSONObject jsonTemp = proKVArray.getJSONObject(i);
				
				for (int j = 0; j < proKeys.size(); j++) {
					long keyId = Long.parseLong(jsonTemp.getString("PRODUCTKEYID"));
					if(proKeys.get(j).productKeyId == keyId){
						proKeys.get(j).productKVId = jsonTemp.getString("PRODUCTKVID");
						Log.e(null, jsonTemp.getString("PRODUCTKVID")+",prokeys:"+proKeys.get(j).productKVId);
						
						for (int k = 0; k < proKeys.get(j).kvList.size(); k++) {
							long valueId = Long.parseLong(jsonTemp.getString("PRODUCTVALUE"));
							if(proKeys.get(j).kvList.get(k).productValueId == valueId){
								infoSpinners.get(j).setSelection(k+1);
							}
						}
					}
				}
			}
		}
	}
	
	private void submit(){
		submitTask = new SubmitTask();
		submitTask.setTaskOnPostExecuteListener(new TaskOnPostExecuteListener<ExResponse>() {
			@Override
			public void onPostExecute(TaskResult<? extends ExResponse> result) {
				ExResponse response = result.Value;
				if (ErrorConstants.VALUE_NULL.equals(response.getResMessage())) {
					if (response.getResCode() == -3) {
						Toast.makeText(ProductModifyDetailActivity.this, getResources().getString(R.string.msg_request_timeout), Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(ProductModifyDetailActivity.this, getResources().getString(R.string.msg_server_nores), Toast.LENGTH_LONG).show();
					}
					return;
				} else {
					try {
						JSONObject json = new JSONObject(response.getResMessage());
						if(ResponseConstant.OK.equals(json.get(ResponseConstant.STATUS))){
							Toast.makeText(ProductModifyDetailActivity.this, getResources().getString(R.string.msg_response_success), Toast.LENGTH_SHORT).show();
							AppStatus.PRODUCT_DATA_UPDATE = true;
							AppStatus.PRODUCT_LIST_UPDATE = true;
							ProductModifyDetailActivity.this.finish();
						} else {
							Toast.makeText(ProductModifyDetailActivity.this, getResources().getString(R.string.msg_submit_failed), Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						e.printStackTrace();
						Toast.makeText(ProductModifyDetailActivity.this, getResources().getString(R.string.msg_submit_failed), Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
		
		productKVList.clear();
		
		for (int i = 0; i < infoSpinners.size(); i++) {
			if(infoSpinners.get(i).getSelectedItemPosition() > 0){
				ProductKV proKV = new ProductKV();
				proKV.setProductKeyId(String.valueOf(proKeys.get(i).productKeyId));
				proKV.setProductValue(String.valueOf(proKeys.get(i).kvList.get(infoSpinners.get(i).getSelectedItemPosition()-1).productValueId));
				if(mode == 2){
					proKV.setProductKVId(proKeys.get(i).productKVId);
				}else {
					proKV.setProductKVId("");
				}
				productKVList.add(proKV);
			} 
		}

		ProductModifyRequest request = new ProductModifyRequest();
		SharePreferencesManager preferences = SharePreferencesManager.getInstance(ProductModifyDetailActivity.this);
		
		request.setUserId(preferences.getUserId());
		request.setToken(preferences.getTokenLast8());
			
		if (mode == 2) {
			request.setProductId(productId);
		}
		request.setPartno(edt_partno.getText().toString().trim());
		request.setNpartno(edt_npartno.getText().toString().trim());
		request.setProductName(edt_productName.getText().toString().trim());
		request.setCostPrice(edt_costPrice.getText().toString().trim());
		request.setBasePrice(edt_basePrice.getText().toString().trim());
		request.setProductModel(edt_productModel.getText().toString().trim());
		request.setProductOtherModel(edt_productOtherModel.getText().toString().trim());
		request.setProductOtherRate(edt_productOtherRate.getText().toString().trim());
		request.setProductUnitStr(edt_productUnitStr.getText().toString().trim());
		request.setProductUnitRateStr(edt_productUnitRateStr.getText().toString().trim());
		request.setIsB2C(isB2CList.get(spi_isB2C.getSelectedItemPosition()).getValue());
		request.setOverDay(edt_overDay.getText().toString().trim());
		request.setFreshDay(edt_freshDay.getText().toString().trim());
		request.setVatRate(edt_vatRate.getText().toString().trim());
		request.setLength(edt_length.getText().toString().trim());
		request.setWidth(edt_width.getText().toString().trim());
		request.setHeight(edt_height.getText().toString().trim());
		request.setgWeight(edt_gWeight.getText().toString().trim());
		request.setnWeight(edt_nWeight.getText().toString().trim());
		request.setQz(edt_qz.getText().toString().trim());
		request.setPoUnit(edt_poUnit.getText().toString().trim());
		request.setSoUnit(edt_soUnit.getText().toString().trim());
		request.setProjectId(projectSpiList.get(spi_projectId.getSelectedItemPosition()).getValue());
		request.setRelationProductId(edt_relationProductId.getText().toString().trim());
		request.setCustomerDeptId(cusdeptList.get(spi_customerDeptId.getSelectedItemPosition()).getValue());
		request.setProductCategoryId(productCategoryId);
		request.setProductKVList(productKVList);
		
		if (request.getCount() < ProductModifyRequest.MUST_NUM) {
			int temp_num = ProductModifyRequest.MUST_NUM - request.getCount();
			Toast.makeText(ProductModifyDetailActivity.this, temp_num + getString(R.string.msg_data_incomplete_withnum), Toast.LENGTH_SHORT).show();
		
		}else if (request.getProductUnitStr().indexOf(request.getPoUnit()) == -1){
			Toast.makeText(ProductModifyDetailActivity.this, getResources().getString(R.string.msg_product_modify_pounit), Toast.LENGTH_SHORT).show();
		
		} else if (request.getProductUnitStr().indexOf(request.getSoUnit()) == -1){
			Toast.makeText(ProductModifyDetailActivity.this, getResources().getString(R.string.msg_product_modify_sounit), Toast.LENGTH_SHORT).show();
			
		} else {
			submitTask.execute(preferences.getHttpUrl(),requestUrl2,request.toJsonString(), request.toJsonString2());
		}
	}
	
	private void backWithConfirm() {
		DialogUtil.createMessageDialog(ProductModifyDetailActivity.this, 
				R.string.product_dlg_exit_modify_title, 
				R.string.product_dlg_exit_modify_info, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int arg1) {
						dialog.dismiss();
						ProductModifyDetailActivity.this.finish();
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
	
	private class ProKey{
		public String productKey;
		public long productKeyId;
		public String productKVId;
		public List<ProValue> kvList;
		
		public ProKey(){
			kvList = new ArrayList<ProValue>();
		}
	}
	
	private class ProValue{
		public String productValue;
		public long productValueId;
		public long productKeyId;
	}
	
	private class ProProject{
		public String projectName;
		public long projectId;
		public long cusDeptId;
	}
	
}
