package com.explore.android.mobile.activity.stock;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.explore.android.R;
import com.explore.android.core.base.BaseHttpActivity;
import com.explore.android.core.util.ExStringUtil;
import com.explore.android.mobile.adapter.DetailInfoAdapter;
import com.explore.android.mobile.constants.RequestConstants;
import com.explore.android.mobile.constants.ResponseConstant;
import com.explore.android.mobile.data.request.StockDetailRequest;
import com.explore.android.mobile.model.DetailInfo;

public class StockDetailActivity extends BaseHttpActivity{
	
	private static final int REQUEST_DETAIL = 1;

	private String stockLineId;
	private ListView listView;
	private RelativeLayout loading;

	private DetailInfoAdapter adapter;
	private List<DetailInfo> detailList;
	
	@Override
	protected int setLayoutId() {
		return R.layout.activity_search_detail;
	}

	@Override
	public void initViews() {
		listView = (ListView) findViewById(R.id.search_detail_list);
		loading = (RelativeLayout) findViewById(R.id.app_loading_layout);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle(R.string.stockdetail_title);
	}

	@Override
	public void initValues() {
		Intent intent = getIntent();
		if (intent.hasExtra("stocklineId")) {
			stockLineId = intent.getStringExtra("stocklineId");
			detailList = new ArrayList<DetailInfo>();
			adapter = new DetailInfoAdapter(StockDetailActivity.this,detailList);
			listView.setAdapter(adapter);
			loadCustomerDetail();
		}
	}

	@Override
	public void initListener() {
	}

	@Override
	public void showLoading() {
		loading.setVisibility(View.VISIBLE);
	}

	@Override
	public void dismissLoading() {
		loading.setVisibility(View.GONE);
	}

	@Override
	public void handlerResponse(String response, int what) {
		if( REQUEST_DETAIL == what ){
			bindStockDetailData(response);
		}
	}
	
	private void loadCustomerDetail(){
		StockDetailRequest request = new StockDetailRequest();
		request.setUserId(getPreferences().getUserId());
		request.setToken(getPreferences().getTokenLast8());
		request.setStockLineId(stockLineId);
        asynDataRequest(RequestConstants.STOCK_DETAIL, request.toJsonString(), REQUEST_DETAIL);
	}
	
	private boolean bindStockDetailData(String resStr){
		try {
			JSONObject jsonObject = new JSONObject(resStr);
			if (ResponseConstant.OK.equals(jsonObject.get(ResponseConstant.STATUS))) {
				
				JSONObject json = jsonObject.getJSONObject("STOCKLINE");
				
				List<String> keyList = new ArrayList<String>();
				keyList.add("stocklineId");
				keyList.add("partNo");
				keyList.add("nPartNo");
				keyList.add("productName");
				keyList.add("project");
				keyList.add("warehouse");
				keyList.add("loc");
//				keyList.add("logNo");
				keyList.add("whs");
				keyList.add("standardProductName");
				keyList.add("customerDept");
				keyList.add("vendor");
				keyList.add("inCode1");
				keyList.add("inCode2");
				keyList.add("poCode1");
				keyList.add("poCode2");
				keyList.add("poPrice");
				keyList.add("stockQty");
				keyList.add("holdQty");
				keyList.add("stockQtyBak");
				keyList.add("stockTime");
				keyList.add("beginDate");
				keyList.add("endDate");
				keyList.add("remarks");
				
				for (int i = 0; i < keyList.size(); i++) {
					String key = keyList.get(i);
					DetailInfo info = new DetailInfo();
					int strId = getResourceIdByKey(key);
					info.setContent(json.getString(key));
					if(ExStringUtil.isResNoBlank(info.getContent()) && strId != R.string.app_null){
						info.setTitle(key);
						info.setResourceId(getResourceIdByKey(key));
						detailList.add(info);
					}
				}
				
				adapter.notifyDataSetChanged();
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
		
		if("partNo".endsWith(name)){
			return R.string.stockdetail_partno;
			
		} else if("stocklineId".equals(name)){
			return R.string.app_null;
			
		} else if("nPartNo".equals(name)){
			return R.string.stockdetail_npartno;
			
		} else if("productName".equals(name)){
			return R.string.stockdetail_productname;
			
		} else if("project".equals(name)){
			return R.string.stockdetail_project;
			
		} else if("warehouse".equals(name)){
			return R.string.stockdetail_warehouse;
			
		} else if("loc".equals(name)){
			return R.string.stockdetail_loc;
			
		} else if("logon".equals(name)){
			return R.string.stockdetail_logno;
			
		} else if("whs".equals(name)){
			return R.string.stockdetail_whs;
			
		} else if("customerDept".equals(name)){
			return R.string.stockdetail_cusdept;
			
		} else if("vendor".equals(name)){
			return R.string.stockdetail_vendor;
			
		} else if("inCode1".equals(name)){
			return R.string.stockdetail_incode1;
			
		} else if("inCode2".equals(name)){
			return R.string.stockdetail_incode2;
			
		} else if("poCode1".equals(name)){
			return R.string.stockdetail_pocode1;
			
		} else if("poCode2".equals(name)){
			return R.string.stockdetail_pocode2;
			
		} else if("poPrice".equals(name)){
			return R.string.stockdetail_poprice;
			
		} else if("endDate".equals(name)){
			return R.string.stockdetail_enddate;
			
		} else if("remarks".equals(name)){
			return R.string.stockdetail_remarks;
			
		} else if("stockQty".equals(name)){
			return R.string.stockdetail_stockqty;
			
		} else if("holdQty".equals(name)){
			return R.string.stockdetail_holdqty;
			
		} else if("stockTime".equals(name)){
			return R.string.stockdetail_stocktime;
			
		} else if("beginDate".equals(name)){
			return R.string.stockdetail_begindate;
			
		} else if("standardProductName".equals(name)){
			return R.string.stockdetail_standardproname;
			
		} else if("stockQtyBak".equals(name)){
			return R.string.stockdetail_stockqtybak;
			
		} else{
			return R.string.stockdetail_info;
		}
		
	}
	
}
