package com.explore.android.mobile.activity.product;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.explore.android.R;
import com.explore.android.core.base.BaseHttpActivity;
import com.explore.android.core.util.ExStringUtil;
import com.explore.android.mobile.adapter.DetailInfoAdapter;
import com.explore.android.mobile.constants.AppStatus;
import com.explore.android.mobile.constants.RequestConstants;
import com.explore.android.mobile.constants.ResponseConstant;
import com.explore.android.mobile.constants.SDConstants;
import com.explore.android.mobile.data.ValueToResource;
import com.explore.android.mobile.data.request.ProductDetailRequest;
import com.explore.android.mobile.model.DetailInfo;
import com.explore.android.mobile.view.DialogUtil;

public class ProductDetailActivity extends BaseHttpActivity {
	
	private static final int REQUEST_DETAIL = 1;
	private static final int REQUEST_COMMIT = 2;
	private static final int REQUEST_DELETE = 3;
	
	private String proId;
	private ListView listView;
	private RelativeLayout loading;
	
	private Button btn_modify;
	private Button btn_delete;
	private Button btn_commit;
	private LinearLayout action_btn_layout;
	
	private DetailInfoAdapter adapter;
	private List<DetailInfo> detailList;
	
	private String productCategoryName;
	private String productCategoryId;
	private String productSts;
	
	@Override
	protected int setLayoutId() {
		return R.layout.activity_search_detail;
	}

	@Override
	public void initViews() {
		listView = (ListView) findViewById(R.id.search_detail_list);
		loading = (RelativeLayout) findViewById(R.id.app_loading_layout);
		btn_modify = (Button) findViewById(R.id.act_btn_detail_edit);
		btn_commit = (Button) findViewById(R.id.act_btn_detail_submit);
		btn_delete = (Button) findViewById(R.id.act_btn_detail_delete);
		action_btn_layout = (LinearLayout) findViewById(R.id.act_search_detail_action_layout);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle(R.string.prodetail_info);
	}

	@Override
	public void initValues() {
		Intent intent = getIntent();
		if (intent.hasExtra("proId")) {
			proId = intent.getStringExtra("proId");
		}
		
		detailList = new ArrayList<DetailInfo>();
		adapter = new DetailInfoAdapter(ProductDetailActivity.this,detailList);
		listView.setAdapter(adapter);
		
		loadProductDetail();
	}

	@Override
	public void initListener() {
		btn_delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				DialogUtil.createMessageDialog(ProductDetailActivity.this, 
						R.string.product_dlg_delete_title, 
						R.string.product_dlg_delete_info, new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int arg1) {
								dialog.dismiss();
								delete();
							}
						});
			}
		});
		
		btn_commit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(productSts.equals(SDConstants.PRODUCT_STS_RUN)){
					showToast(R.string.msg_product_wrong_sts);
				} else {
					DialogUtil.createMessageDialog(ProductDetailActivity.this, 
							R.string.product_dlg_submit_title, 
							R.string.product_dlg_submit_info, new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int arg1) {
									dialog.dismiss();
									commit();
								}
							});
				}
			}
		});
		
		btn_modify.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ProductDetailActivity.this, ProductCategoryActivity.class);
				intent.putExtra("TYPE", "modify");
				intent.putExtra("PRODUCTID", "" + proId);
				intent.putExtra("CATEGORYNAME", productCategoryName);
				intent.putExtra("CATEGORYID", productCategoryId);
				startActivity(intent);
			}
		});
		
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
		switch(what){
		case REQUEST_DETAIL:
			bindData(response);
			break;
		case REQUEST_COMMIT:
			handlerSubmitData(response);
			break;
		case REQUEST_DELETE:
			handlerSubmitData(response);
			break;
		default:
			break;
		}
	}
	
	private void bindData(String dataStr){
		try {
			JSONObject jsonObject = new JSONObject(dataStr);
			if (ResponseConstant.OK.equals(jsonObject.get(ResponseConstant.STATUS))) {
				
				JSONObject json = jsonObject.getJSONObject("PRODUCT");
				
				productCategoryName = json.getString("productCategoryName");
				productCategoryId = json.getString("productCategoryId");
				productSts = json.getString("sts");
				if("NEW".equals(productSts)){
					action_btn_layout.setVisibility(View.VISIBLE);
				} else {
					action_btn_layout.setVisibility(View.GONE);
				}
				
				List<String> keyList = new ArrayList<String>();
				keyList.add("productId");
				keyList.add("partNo");
				keyList.add("nPartNo");
				keyList.add("productName");
				keyList.add("productCategoryName");
				keyList.add("projectName");
				keyList.add("productModel");
				keyList.add("productRate");
				keyList.add("productOtherModel");
				keyList.add("productOtherRate");
				keyList.add("basePrice");
				keyList.add("costPrice");
				keyList.add("productUnitStr");
				keyList.add("productUnitRateStr");
				keyList.add("overday");
				keyList.add("freshday");
				keyList.add("vatRate");
				keyList.add("standardproductName");
				keyList.add("sts");
				keyList.add("length");
				keyList.add("width");
				keyList.add("height");
				keyList.add("gWeight");
				keyList.add("nWeight");
				
				detailList.clear();
				
				for (int i = 0; i < keyList.size(); i++) {
					String key = keyList.get(i);
					DetailInfo info = new DetailInfo();
					int strId = getResourceIdByKey(key);
					if("sts".equals(key)){
						info.setContent(getResources().getString(ValueToResource.getProductStsValue(json.getString(key))));
					} else {
						info.setContent(json.getString(key));
					}
					if(ExStringUtil.isResNoBlank(info.getContent()) && strId != R.string.app_null){
						info.setTitle(key);
						info.setResourceId(strId);
						detailList.add(info);
					}
				}
				
				adapter.notifyDataSetChanged();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	private void handlerSubmitData(String dataStr){
		try {
			JSONObject json = new JSONObject(dataStr);
			if(ResponseConstant.OK.equals(json.get(ResponseConstant.STATUS))){
				showToast(R.string.msg_response_success);
				AppStatus.PRODUCT_LIST_UPDATE = true;
				ProductDetailActivity.this.finish();
			} else if(ResponseConstant.EXCEPTION.equals(json.get(ResponseConstant.STATUS))){
				showToast(R.string.msg_action_failed);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			showToast(R.string.msg_response_failed);
		}
	}
	
	@Override
	protected void onResume() {
		if(AppStatus.PRODUCT_DATA_UPDATE){
			loadProductDetail();
			AppStatus.PRODUCT_DATA_UPDATE = false;
		}
		super.onResume();
	}
	
	private void loadProductDetail(){
		ProductDetailRequest request = new ProductDetailRequest();
		request.setUserId(getPreferences().getUserId());
		request.setToken(getPreferences().getTokenLast8());
		request.setProId(proId);
		asynDataRequest(RequestConstants.PRODUCT_DETAIL,request.toJsonString(),REQUEST_DETAIL);
	}
	
	private void delete(){
		ProductDetailRequest request = new ProductDetailRequest();
		request.setUserId(getPreferences().getUserId());
		request.setToken(getPreferences().getTokenLast8());
		request.setProId(proId);
		submitRequest(RequestConstants.PRODUCT_DELETE,request.toJsonString(),REQUEST_DELETE);
	}
	
	private void commit(){
		ProductDetailRequest request = new ProductDetailRequest();
		request.setUserId(getPreferences().getUserId());
		request.setToken(getPreferences().getTokenLast8());
		request.setProId(proId);
		submitRequest(RequestConstants.PRODUCT_COMMIT,request.toJsonString(),REQUEST_COMMIT);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
		return super.onOptionsItemSelected(item);
	}
	
	private int getResourceIdByKey(String name) {
		
		if("partNo".equals(name)){
			return R.string.prodetail_partno;
			
		} else if("productId".equals(name)){
			return R.string.app_null;
			
		} else if("nPartNo".equals(name)){
			return R.string.prodetail_npartno;
			
		} else if("productName".equals(name)){
			return R.string.prodetail_proname;
			
		} else if("productCategoryName".equals(name)){
			return R.string.prodetail_categoryname;
			
		} else if("projectName".equals(name)){
			return R.string.prodetail_projectname;
			
		} else if("productModel".equals(name)){
			return R.string.prodetail_productmodel;
			
		} else if("productRate".equals(name)){
			return R.string.prodetail_productrate;
			
		} else if("productOtherModel".equals(name)){
			return R.string.prodetail_othermodel;
			
		} else if("productOtherRate".equals(name)){
			return R.string.prodetail_otherrate;
			
		} else if("basePrice".equals(name)){
			return R.string.prodetail_baseprice;
			
		} else if("productUnitStr".equals(name)){
			return R.string.prodetail_unitstr;
			
		} else if("productUnitRateStr".equals(name)){
			return R.string.prodetail_unitratestr;
			
		} else if("overday".equals(name)){
			return R.string.prodetail_overday;
			
		} else if("freshday".equals(name)){
			return R.string.prodetail_freshday;
			
		} else if("vatRate".equals(name)){
			return R.string.prodetail_vatrate;
			
		} else if("sts".equals(name)){
			return R.string.prodetail_sts;
			
		} else if("length".equals(name)){
			return R.string.prodetail_length;
			
		} else if("width".equals(name)){
			return R.string.prodetail_width;
			
		} else if("height".equals(name)){
			return R.string.prodetail_height;
			
		} else if("gWeight".equals(name)){
			return R.string.prodetail_gweight;
			
		} else if("nWeight".equals(name)){
			return R.string.prodetail_nweight;
			
		} else if("costPrice".equals(name)){
			return R.string.prodetail_costprice;
			
		} else if("standardproductName".equals(name)){
			return R.string.prodetail_stanardproname;
			
		} else {
			return R.string.prodetail_info;
		}
		
	}

}
