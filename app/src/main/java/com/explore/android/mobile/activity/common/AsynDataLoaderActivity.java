package com.explore.android.mobile.activity.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.explore.android.R;
import com.explore.android.core.base.BaseAsynTask;
import com.explore.android.core.base.BaseAsynTask.TaskOnPostExecuteListener;
import com.explore.android.core.base.BaseAsynTask.TaskResult;
import com.explore.android.core.http.AsynDataTask;
import com.explore.android.core.model.BaseKeyValue;
import com.explore.android.core.model.ExResponse;
import com.explore.android.core.util.DateUtil;
import com.explore.android.mobile.adapter.KeyValueAdapter;
import com.explore.android.mobile.adapter.KeyValueAdapter.KvMode;
import com.explore.android.mobile.common.Common;
import com.explore.android.mobile.common.SharePreferencesManager;
import com.explore.android.mobile.constants.ActivityCode;
import com.explore.android.mobile.constants.AppConstant;
import com.explore.android.mobile.constants.DataConstants;
import com.explore.android.mobile.constants.ErrorConstants;
import com.explore.android.mobile.constants.RequestConstants;
import com.explore.android.mobile.constants.ResponseConstant;
import com.explore.android.mobile.data.predata.AsynPreData;
import com.explore.android.mobile.data.request.DataLoaderRequest;
import com.explore.android.mobile.view.PullToRefreshView;
import com.explore.android.mobile.view.PullToRefreshView.OnFooterRefreshListener;
import com.explore.android.mobile.view.PullToRefreshView.OnHeaderRefreshListener;

public class AsynDataLoaderActivity extends ListActivity implements OnHeaderRefreshListener,OnFooterRefreshListener{

	private AsynPreData preData;
	private DataLoaderRequest request;
	private SharePreferencesManager preferences;
	private static int page_num = 1;
	private PullToRefreshView mPullToRefreshView;

	List<BaseKeyValue> kvList;
	KeyValueAdapter kvAdapter;
	ImageButton btn_search;
	EditText edt_keyword;
	
	private int mode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_asyn_data_loader);
		preferences = SharePreferencesManager.getInstance(getApplicationContext());
		initViews();
		initValues();
		initListener();
	}

	public void initViews() {
		mPullToRefreshView = (PullToRefreshView) findViewById(R.id.act_pullrefresh_view);
		btn_search = (ImageButton) findViewById(R.id.act_aysn_data_btn_search);
		edt_keyword = (EditText) findViewById(R.id.act_aysn_data_edt_keyword);
		
		btn_search.setEnabled(false);
		mPullToRefreshView.setOnHeaderRefreshListener(this);
		mPullToRefreshView.setOnFooterRefreshListener(this);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	public void initValues() {
		page_num = 1;
		Intent taskIntent = getIntent();
		if(taskIntent.hasExtra(DataConstants.DATA_TITLE)){
			Bundle bundle = taskIntent.getBundleExtra(DataConstants.DATA_TITLE);
			preData = new AsynPreData();
			preData.setUrl(bundle.getString(DataConstants.DATA_URL,"NULL"));
			preData.setJsonTitle(bundle.getString(DataConstants.DATA_JSONTITLE,"NULL"));
			preData.setJsonName(bundle.getString(DataConstants.DATA_JSONNAME,"NULL"));
			preData.setJsonValue(bundle.getString(DataConstants.DATA_JSONVALUE,"NULL"));
			preData.setTitleRes(bundle.getInt(DataConstants.DATA_ACTIVITY, R.string.dataloader_title_default)); 
			preData.setType(bundle.getString(DataConstants.DATA_TYPE,"NULL"));
			if(bundle.containsKey(DataConstants.DATA_EXTRA_PARAMS)){
				preData.setExtraParams(bundle.getStringArray(DataConstants.DATA_EXTRA_PARAMS)); 
			}
			if(bundle.containsKey(DataConstants.DATA_EXTRA_DATAS)){
				preData.setExtraDatas(bundle.getStringArray(DataConstants.DATA_EXTRA_DATAS));
			}
			btn_search.setEnabled(true);
			getActionBar().setTitle(preData.getTitleRes());
		} else {
			setResult(0);
			finish();
		}
		
		kvList = new ArrayList<BaseKeyValue>();
		kvAdapter = new KeyValueAdapter(this, R.layout.list_asyn_data_item,
				R.id.list_item_asyn_data, kvList, KvMode.STR);
		setListAdapter(kvAdapter);
	}

	public void initListener() {
		btn_search.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(edt_keyword.getText().toString().trim().length() < 2){
					showToast(R.string.msg_input_two_atleast);
				} else {
					page_num = 1;
					asynDataRequest(preData.getUrl());
				}
			}
		});
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Bundle bundle = new Bundle();
		bundle.putString(DataConstants.VALUE, kvList.get(position).getValue());
		bundle.putString(DataConstants.NAME, kvList.get(position).getKey());
		
		if (kvList.get(position).getExtraData() != null) {
			String[] ex_data = kvList.get(position).getExtraData();
			int length3 = ex_data.length;
			for (int i = 0; i < length3; i++) {
				String key = preData.getExtraDatas()[i];
				if (ex_data[i] != null) {
					bundle.putString(key, ex_data[i]);
				}
			}
		}
		
		Intent result = new Intent();
		// result.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		result.putExtra(DataConstants.KVRESULT, bundle);
		setResult(ActivityCode.AYSN_DATA_RESULT, result);
		finish();
	}

	public void asynDataRequest(String url) {
		if(Common.isNetWorkEnable(getApplicationContext()) == false){
			showToast(R.string.msg_network_is_not_available);
			return;
		}
		AsynDataTask asynDataTask = new AsynDataTask();
		asynDataTask.setTaskOnPostExecuteListener(new TaskOnPostExecuteListener<ExResponse>(){
			@Override
			public void onPostExecute(TaskResult<? extends ExResponse> result) {
				if(result != null && result.Status == BaseAsynTask.STATUS_SUCCESS){
					ExResponse response = result.Value;
					if(ErrorConstants.VALUE_NULL.equals(response.getResMessage())){
						if (response.getResCode() == -3) {
							showToast(R.string.msg_request_timeout);
						}else{
							showToast(R.string.msg_server_nores);
						}
					} else {
						bindAsynData(response.getResMessage());
					}
				}
			}
		});
		
		if(request == null){
			request = new DataLoaderRequest();
		}
		request.setKeyword(edt_keyword.getText().toString().trim());
		request.setUserId(preferences.getUserId());
		request.setToken(preferences.getTokenLast8());
		request.setType(preData.getType());
		request.setPsize(RequestConstants.SEARCH_PAGE_SIZE);
		request.setPageNum(page_num);
		if(preData.getExtraParams() != null){
			request.setExtraParams(preData.getExtraParams());
		}
		asynDataTask.execute(preferences.getHttpUrl(),url,request.toJsonString());
	}
	
	public void showToast(String message){
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
	}
	
	public void showToast(int message){
		Toast.makeText(getApplicationContext(), getResources().getString(message), Toast.LENGTH_SHORT).show();
	}

	private void bindAsynData(String dataStr) {
		try {
			JSONObject json = new JSONObject(dataStr);
			if (ResponseConstant.OK.equals(json.get(ResponseConstant.STATUS))) {
				JSONArray dataArray = json.getJSONArray(preData.getJsonTitle());
				int length = dataArray.length();
				
				if(1 == page_num){
					kvList.clear();
				}
				
				if( length > 0) {
					page_num ++;
					for (int i = 0; i < length; i++) {
						JSONObject jsonData = dataArray.getJSONObject(i);
						BaseKeyValue kv = new BaseKeyValue(jsonData.getString(preData.getJsonName()), jsonData.getString(preData.getJsonValue()));
						if (preData.getExtraDatas() != null && preData.getExtraDatas().length > 0) {
							int pLength = preData.getExtraDatas().length;
							String[] ex_data = new String[pLength];
							for (int j = 0; j < pLength; j++) {
								String key = preData.getExtraDatas()[j];
								if (jsonData.has(key)) {
									ex_data[j] = jsonData.getString(key);
								}
							}
							kv.setExtraData(ex_data);
						}
						kvList.add(kv);
					}
					if(page_num > 2){
						String msg = getString(R.string.pull_to_refresh_data_count);
						showToast(String.format(msg, length));
					}
				} else {
					if (1 == page_num){
						showToast(R.string.msg_search_no_result);
					} else {
						showToast(R.string.pull_to_refresh_nomore_data);
					}
				}
				
				kvAdapter.notifyDataSetChanged();
				
				if(AppConstant.SYNC_LOADMORE == mode) {
					mPullToRefreshView.onFooterRefreshComplete();
				} else if (AppConstant.SYNC_REFRESH == mode) {
					String nowTime = DateUtil.dateFormatWithDayTime(new Date());
					mPullToRefreshView.onHeaderRefreshComplete(nowTime);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
			showToast(R.string.msg_response_failed);
		}
	}
	
	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		mode = AppConstant.SYNC_LOADMORE;
		mPullToRefreshView.postDelayed(new Runnable() {
			@Override
			public void run() {
				asynDataRequest(preData.getUrl());
			}
		}, 500);
	}

	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		mode = AppConstant.SYNC_REFRESH;
		mPullToRefreshView.postDelayed(new Runnable() {
			@Override
			public void run() {
				page_num = 1;
				asynDataRequest(preData.getUrl());
			}
		}, 500);
	}

}
