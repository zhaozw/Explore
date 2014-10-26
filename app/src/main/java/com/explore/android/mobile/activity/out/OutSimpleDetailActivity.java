package com.explore.android.mobile.activity.out;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.explore.android.R;
import com.explore.android.core.base.BaseAsynTask;
import com.explore.android.core.base.BaseAsynTask.TaskOnPostExecuteListener;
import com.explore.android.core.base.BaseAsynTask.TaskResult;
import com.explore.android.core.http.AsynDataTask;
import com.explore.android.core.model.ExResponse;
import com.explore.android.mobile.adapter.DetailInfoAdapter;
import com.explore.android.mobile.common.SharePreferencesManager;
import com.explore.android.mobile.constants.ErrorConstants;
import com.explore.android.mobile.constants.RequestConstants;
import com.explore.android.mobile.constants.ResponseConstant;
import com.explore.android.mobile.data.request.OutDetailRequest;
import com.explore.android.mobile.model.DetailInfo;

public class OutSimpleDetailActivity extends Activity{

	private String outId;
	private ListView listView;
	private RelativeLayout loading;
	
	private DetailInfoAdapter adapter;
	private List<DetailInfo> detailList;
	
	private AsynDataTask outDetailTask;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_detail);
		initValues();
		initViews();
		loadOutDetail();
	}
	
	private void initViews() {
		listView = (ListView) findViewById(R.id.search_detail_list);
		loading = (RelativeLayout) findViewById(R.id.app_loading_layout);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle(R.string.outdetail_outinfodetail);
	}

	private void initValues() {
		Intent intent = getIntent();
		if (intent.hasExtra("outId")) {
			outId = intent.getStringExtra("outId");
		}
		
		detailList = new ArrayList<DetailInfo>();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		outDetailTask.cancel(true);
	}
	
	private void loadOutDetail(){
		
		loading.setVisibility(View.VISIBLE);
		OutDetailRequest request = new OutDetailRequest();
		
		outDetailTask = new AsynDataTask();
		outDetailTask.setTaskOnPostExecuteListener(new TaskOnPostExecuteListener<ExResponse>(){

			@Override
			public void onPostExecute(TaskResult<? extends ExResponse> result) {
				if(result != null && result.Status == BaseAsynTask.STATUS_SUCCESS){
					loading.setVisibility(View.GONE);
					ExResponse response = result.Value;
					if(ErrorConstants.VALUE_NULL.equals(response.getResMessage())){
						if (response.getResCode() == -3) {
							Toast.makeText(OutSimpleDetailActivity.this, getResources().getString(R.string.msg_request_timeout), Toast.LENGTH_SHORT).show();
						}else{
							Toast.makeText(OutSimpleDetailActivity.this, getResources().getString(R.string.msg_server_nores), Toast.LENGTH_LONG).show();
						}
						return;
					}else{
						Log.e(null, response.getResMessage());
						bindProductDetailData(response.getResMessage());
					}
				}
			}
		});

		SharePreferencesManager preferences = SharePreferencesManager.getInstance(this);
		request.setUserId(preferences.getUserId());
		request.setToken(preferences.getTokenLast8());
		request.setOutId(outId);
		outDetailTask.execute(preferences.getHttpUrl(),RequestConstants.OUT_GET_DETAIL,request.toJsonString());
	}
	
	@SuppressWarnings("unchecked")
	private boolean bindProductDetailData(String resStr){
		try {
			JSONObject jsonObject = new JSONObject(resStr);
			if (ResponseConstant.OK.equals(jsonObject.get(ResponseConstant.STATUS))) {
				
				JSONObject json = jsonObject.getJSONObject("OUT");
				
				Iterator<String> jsonKeys = json.keys();
				String key;
				while (jsonKeys.hasNext()) {
					DetailInfo info = new DetailInfo();
					key = jsonKeys.next();
					info.setContent(json.getString(key));
					if(!"null".equals(info.getContent()) && "".equals(info.getContent())){
						info.setTitle(key);
						info.setResourceId(getResourceIdByKey(key));
						detailList.add(info);
					}
				}
				
				adapter = new DetailInfoAdapter(OutSimpleDetailActivity.this,detailList);
				listView.setAdapter(adapter);
				
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
	
	private int getResourceIdByKey(String name){
		
		if("outCode1".equals(name)){
			return R.string.outdetail_outcode1;
			
		}else if("outCode2".equals(name)){
			return R.string.outdetail_outcode2;
			
		}else if("customerDeptId".equals(name)){
			return R.string.outdetail_customerdept;
			
		}else if("warehouseId".equals(name)){
			return R.string.outdetail_warehouse;
			
		}else if("shipTo".equals(name)){
			return R.string.outdetail_shipto;
			
		}else if("vatTo".equals(name)){
			return R.string.outdetail_vatto;
			
		}else if("outType".equals(name)){
			return R.string.outdetail_outtype;
			
		}else if("transportQty1".equals(name)){
			return R.string.outdetail_transportqty1;
			
		}else if("outTime".equals(name)){
			return R.string.outdetail_outtime;
			
		}else if("sts".equals(name)){
			return R.string.outdetail_sts;
			
		}else if("remarks".equals(name)){
			return R.string.outdetail_remarks;
			
		}else if("utMoney1".equals(name)){
			return R.string.outdetail_outmoney1;
			
		}else if("transportMoney1".equals(name)){
			return R.string.outdetail_transportmoney1;
			
		}else if("transportQty2".equals(name)){
			return R.string.outdetail_transportqty2;
			
		}else if("transportMoney2".equals(name)){
			return R.string.outdetail_transportmoney2;
			
		}else if("soPriceLogonId".equals(name)){
			return R.string.outdetail_sopricelogonid;
			
		}else if("createByName".equals(name)){
			return R.string.outdetail_createbyname;
			
		}else if("createByTime".equals(name)){
			return R.string.outdetail_createbytime;
			
		}else if("commitByName".equals(name)){
			return R.string.outdetail_commitbyname;
			
		}else if("commitByTime".equals(name)){
			return R.string.outdetail_commitbytime;
			
		}else if("vendorId".equals(name)){
			return R.string.outdetail_vendorid;
			
		}else if("projectId".equals(name)){
			return R.string.outdetail_projectid;
			
		}else if("logNo".equals(name)){
			return R.string.outdetail_logno;
			
		}else if("warehouseTo".equals(name)){
			return R.string.outdetail_warehouseto;
			
		}else if("outTimeEnd".equals(name)){
			return R.string.outdetail_outtimeend;
			
		}else if("outType2".equals(name)){
			return R.string.outdetail_outtype2;
			
		}else if("shipFrom".equals(name)){
			return R.string.outdetail_shipfrom;
			
		}else {
			return R.string.outdetail_outinfo;
		}
	}
}
