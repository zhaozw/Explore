package com.explore.android.mobile.activity.outline;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.explore.android.R;
import com.explore.android.core.base.BaseAsynTask.TaskOnPostExecuteListener;
import com.explore.android.core.base.BaseAsynTask.TaskResult;
import com.explore.android.core.http.AsynDataTask;
import com.explore.android.core.model.ExResponse;
import com.explore.android.mobile.adapter.OutLineAddAdapter;
import com.explore.android.mobile.common.SharePreferencesManager;
import com.explore.android.mobile.constants.ErrorConstants;
import com.explore.android.mobile.constants.RequestConstants;
import com.explore.android.mobile.constants.ResponseConstant;
import com.explore.android.mobile.data.request.OutProductSearchRequest;
import com.explore.android.mobile.model.OutLineForAdd;

public class OutLineAddActivity extends Activity{

	private RelativeLayout loading;
	private Button btn_search;
	private EditText edt_keyword;
	
	private ListView product_listView;
	private OutLineAddAdapter productAdapter;
	private List<OutLineForAdd> productList;
	
	private AsynDataTask searchProductTask;
	
	private String outId;
	private String soPriceLogonId;
	private String customerId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_outline_add_layout);
		
		initView();
		initValue();
		initListener();
		
	}
	
	private void initView(){
		loading = (RelativeLayout) findViewById(R.id.app_loading_layout);
		btn_search = (Button) findViewById(R.id.act_outline_add_btn_search);
		edt_keyword = (EditText) findViewById(R.id.act_outline_add_edt_procond);
		product_listView = (ListView) findViewById(R.id.act_outline_add_product_list);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle(R.string.top_title_outline_add);
	}
	
	private void initValue(){
		
		Intent intent = getIntent();
		if(intent.hasExtra("OUTID") && intent.hasExtra("SOPRICELOGONID") && intent.hasExtra("CUSTOMERID")) {
			outId = intent.getStringExtra("OUTID");
			soPriceLogonId = intent.getStringExtra("SOPRICELOGONID");
			customerId = intent.getStringExtra("CUSTOMERID");
			
			productList = new ArrayList<OutLineForAdd>();
			productAdapter = new OutLineAddAdapter(productList, this, outId, soPriceLogonId, customerId);
			product_listView.setAdapter(productAdapter);
		} else {
			Toast.makeText(this, getResources().getString(R.string.msg_outline_init_failed), Toast.LENGTH_SHORT).show();
			OutLineAddActivity.this.finish();
		}
	}
	
	private void initListener(){
		btn_search.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadSearchProductData(edt_keyword.getText().toString().trim(), outId);
			}
		});
		
	}
	
	private void loadSearchProductData(String keyword, String id) {
		loading.setVisibility(View.VISIBLE);
		OutProductSearchRequest request = new OutProductSearchRequest();

		searchProductTask = new AsynDataTask();
		searchProductTask.setTaskOnPostExecuteListener(new TaskOnPostExecuteListener<ExResponse>() {
					@Override
					public void onPostExecute( TaskResult<? extends ExResponse> result) {
						loading.setVisibility(View.GONE);
						ExResponse response = result.Value;
						if (ErrorConstants.VALUE_NULL.equals(response.getResMessage())) {
							if (response.getResCode() == -3) {
								Toast.makeText(OutLineAddActivity.this, getResources().getString(R.string.msg_request_timeout), Toast.LENGTH_SHORT).show();
							} else {
								Toast.makeText(OutLineAddActivity.this, getResources().getString(R.string.msg_server_nores), Toast.LENGTH_LONG).show();
							}
							return;
						} else {
							bindSearchProductData(response.getResMessage());
						}
					}
				});

		SharePreferencesManager preferences = SharePreferencesManager.getInstance(this);
		request.setUserId(preferences.getUserId());
		request.setToken(preferences.getTokenLast8());
		request.setOutId(id);
		request.setKeyWord(keyword);
		searchProductTask.execute(preferences.getHttpUrl(), RequestConstants.OUT_SEARCH_PRODUCT, request.toJsonString());
	}
	
	private boolean bindSearchProductData(String dataStr){
		
		try {
			JSONObject jsonObject = new JSONObject(dataStr);
			if(ResponseConstant.OK.equals(jsonObject.get(ResponseConstant.STATUS))){
				JSONArray jsonArray = jsonObject.getJSONArray("PRODUCTS");
				productList.clear();
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject json = jsonArray.getJSONObject(i);
					OutLineForAdd product = new OutLineForAdd();
					product.setProductName(json.getString("PRODUCTNAME"));
					product.setPartNo(json.getString("PARTNO"));
					product.setNpartNo(json.getString("NPARTNO"));
					product.setProductId(json.getString("PRODUCTID"));
					product.setProductInfo(json.getString("PRODUCT"));
					productList.add(product);
				}
				
				productAdapter.notifyDataSetChanged();
				return true;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return false;
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
