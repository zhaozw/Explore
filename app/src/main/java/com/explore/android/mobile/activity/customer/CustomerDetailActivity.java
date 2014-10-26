package com.explore.android.mobile.activity.customer;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;

import com.explore.android.R;
import com.explore.android.core.base.BaseFragmentActivity;
import com.explore.android.mobile.adapter.SectionPagerAdapter;
import com.explore.android.mobile.constants.AppStatus;
import com.explore.android.mobile.constants.RequestConstants;
import com.explore.android.mobile.constants.ResponseConstant;
import com.explore.android.mobile.data.request.CustomerActionRequest;
import com.explore.android.mobile.data.request.CustomerModifyPreRequest;
import com.explore.android.mobile.view.DialogUtil;

public class CustomerDetailActivity extends BaseFragmentActivity{
	
	private static final int FRAG_INFO = 0;
	private static final int FRAG_ADDRESS = 1;
	private static final int FRAG_DEPT = 2;
	private static final int ITEM_NUMS = 3;
	
	private static final int REQUEST_INFO = 1;
	private static final int REQUEST_ADDRESS = 2;
	private static final int REQUEST_DEPT = 3;
	private static final int REQUEST_ACTION = 4;
	
	private Button btn_commit;
	private Button btn_modify;
	private Button btn_delete;
	
	private RadioGroup tab_button_group;
	private RadioButton cusModify_info;
	private RadioButton cusModify_address;
	private RadioButton cusModify_dept;
	private RelativeLayout loading;
	private LinearLayout action_layout;
	
	private CustomerDetailInfoFragment infoFragment;
	private CustomerDetailAddressFragment addressFragment;
	private CustomerDetailDeptFragment deptFragment;
	private ViewPager viewPager;
	private SectionPagerAdapter adapter;
	private Fragment[] fragments;
	private int cur_frag;
	
	private String customerId;
	private String cvdId;
	private String responseInfo;
	private String responseAddress;
	private String responseDept;
	
	@Override
	protected int setLayoutId() {
		return R.layout.activity_modify_customer;
	}

	@Override
	public void initViews() {
		viewPager = (ViewPager) findViewById(R.id.act_cusmodify_frag_virepager);
		loading = (RelativeLayout) findViewById(R.id.app_loading_layout);
		action_layout = (LinearLayout) findViewById(R.id.act_cusmodify_action_layout);
		
		btn_commit = (Button) findViewById(R.id.act_btn_customer_modify_commit);
		btn_modify = (Button) findViewById(R.id.act_btn_customer_modify_modify);
		btn_delete = (Button) findViewById(R.id.act_btn_customer_modify_delete);
		
		tab_button_group = (RadioGroup) findViewById(R.id.act_rgroud_cusmodify);
		cusModify_info = (RadioButton) findViewById(R.id.act_cusmodify_btn_info);
		cusModify_address = (RadioButton) findViewById(R.id.act_cusmodify_btn_address);
		cusModify_dept = (RadioButton) findViewById(R.id.act_cusmodify_btn_dept);
		tab_button_group.setEnabled(false);
		viewPager.setEnabled(false);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle(R.string.customer_title_modify);
	}

	@Override
	public void initValues() {
		Intent init_intent = getIntent();
		if(init_intent.hasExtra("CUSTOMERID")){
			customerId = init_intent.getStringExtra("CUSTOMERID");
			mHandler.sendEmptyMessage(1);
		} else {
			showToast(R.string.msg_customer_id_notfound);
			this.finish();
		}
		
	}

	@Override
	public void initListener() {
		tab_button_group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup arg0, int position) {
				switchScheduleMode(position);
			}
		});
		
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				setTabChecked(position);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		
		btn_modify.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(cur_frag == FRAG_INFO){
					Intent intent1 = new Intent(CustomerDetailActivity.this, CustomerModifyInfoActivity.class);
					intent1.putExtra("CUSTOMERID", customerId);
					startActivity(intent1);
					
				} else if(cur_frag == FRAG_ADDRESS){
					Intent intent2 = new Intent(CustomerDetailActivity.this, CustomerModifyAddressActivity.class);
					intent2.putExtra("CUSTOMERID", customerId);
					startActivity(intent2);
					
				} else if(cur_frag == FRAG_DEPT){
					Intent intent3 = new Intent(CustomerDetailActivity.this, CustomerModifyDeptActivity.class);
					intent3.putExtra("CUSTOMERID", customerId);
					startActivity(intent3);
				}
			}
		});
		
		btn_commit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DialogUtil.createMessageDialog(CustomerDetailActivity.this, 
						R.string.product_dlg_submit_title, 
						R.string.product_dlg_submit_info, new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int arg1) {
								dialog.dismiss();
								commit();
							}
						});
			}
		});
		
		btn_delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DialogUtil.createMessageDialog(CustomerDetailActivity.this, 
						R.string.customer_dlg_delete_title, 
						R.string.customer_dlg_delete_info, new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int arg1) {
								dialog.dismiss();
								delete();
							}
						});
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
		switch(what){
		case REQUEST_INFO:
			bindInfoData(response);
			break;
		case REQUEST_ADDRESS:
			bindAddressData(response);
			break;
		case REQUEST_DEPT:
			bindDeptData(response);
			break;
		case REQUEST_ACTION:
			handlerActionData(response);
			break;
		default:
			break;
		}
	}
	
	@Override
	protected void onResume() {
		if(AppStatus.CUSTOMER_DATA_UPDATE){
			mHandler.sendEmptyMessage(1);
			switchScheduleMode(FRAG_INFO);
			setTabChecked(FRAG_INFO);
			AppStatus.CUSTOMER_DATA_UPDATE = false;
		}
		super.onResume();
	}
	
	public void setTabChecked(int tabNum) {
		switch (tabNum) {

		case FRAG_INFO:
			cusModify_info.setChecked(true);
			break;

		case FRAG_ADDRESS:
			cusModify_address.setChecked(true);
			break;
			
		case FRAG_DEPT:
			cusModify_dept.setChecked(true);
			break;
			
		default:
			break;
		}
	}
	
	private void initFragments() {
		fragments = new Fragment[ITEM_NUMS];
		infoFragment = new CustomerDetailInfoFragment();
		fragments[FRAG_INFO] = infoFragment;
		addressFragment = new CustomerDetailAddressFragment();
		fragments[FRAG_ADDRESS] = addressFragment;
		deptFragment = new CustomerDetailDeptFragment();
		fragments[FRAG_DEPT] = deptFragment;

		adapter = new SectionPagerAdapter(getSupportFragmentManager(), fragments);
		viewPager.setAdapter(adapter);
		mHandler.sendEmptyMessage(5);
	}
	
	public void switchScheduleMode(int id) {
		switch (id) {
		case R.id.act_cusmodify_btn_info:
			viewPager.setCurrentItem(FRAG_INFO);
			cur_frag = FRAG_INFO;
			break;
		
		case R.id.act_cusmodify_btn_address:
			viewPager.setCurrentItem(FRAG_ADDRESS);
			cur_frag = FRAG_ADDRESS;
			break;

		case R.id.act_cusmodify_btn_dept:
			viewPager.setCurrentItem(FRAG_DEPT);
			cur_frag = FRAG_DEPT;
			break;

		default:
			break;
		}
	}
	
	private void loadInfoData(String type,int what) {
		CustomerModifyPreRequest request = new CustomerModifyPreRequest();
		request.setUserId(getPreferences().getUserId());
		request.setToken(getPreferences().getTokenLast8());
		request.setCustomerId(customerId);
		request.setType(type);
		asynDataRequest(RequestConstants.CUSTOMER_MODIFYPRE, request.toJsonString(), what);
	}
	
	private void bindInfoData(String dataStr){
		try {
			JSONObject json = new JSONObject(dataStr);
			if (ResponseConstant.OK.equals(json.get(ResponseConstant.STATUS))) {
				responseInfo = dataStr;
				JSONObject jsonObject = new JSONObject(responseInfo);
				JSONObject infoJson = jsonObject.getJSONObject("INFO");
				if(infoJson.has("STS")){
					if("NEW".equals(infoJson.getString("STS"))){
						action_layout.setVisibility(View.VISIBLE);
					}
				}
				mHandler.sendEmptyMessage(2);
			}
		} catch (JSONException e) {
			mHandler.sendEmptyMessage(5);
			e.printStackTrace();
		}
	}
	
	private void bindAddressData(String dataStr){
		try {
			JSONObject json = new JSONObject(dataStr);
			if (ResponseConstant.OK.equals(json.get(ResponseConstant.STATUS))) {
				responseAddress = dataStr;
				mHandler.sendEmptyMessage(3);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			mHandler.sendEmptyMessage(5);
		}
	}
	
	private void bindDeptData(String dataStr){
		try {
			JSONObject json = new JSONObject(dataStr);
			if (ResponseConstant.OK.equals(json.get(ResponseConstant.STATUS))) {
				responseDept = dataStr;
				JSONObject jsonObject = new JSONObject(responseDept);
				JSONObject deptJson = jsonObject.getJSONObject("DEPT");
				if(deptJson.has("CVDID")){
					cvdId = deptJson.getString("CVDID");
				}
				mHandler.sendEmptyMessage(4);
			}
		} catch (JSONException e) {
			mHandler.sendEmptyMessage(5);
			e.printStackTrace();
		}
	}
	
	private void commit(){
		CustomerActionRequest request = new CustomerActionRequest();
		request.setUserId(getPreferences().getUserId());
		request.setToken(getPreferences().getTokenLast8());
		request.setCustomerId(customerId);
		request.setCvdId(cvdId);
		submitRequest(RequestConstants.CUSTOMER_DELETE,request.toJsonString(), REQUEST_ACTION);
	}
	
	private void delete(){
		CustomerActionRequest request = new CustomerActionRequest();
		request.setUserId(getPreferences().getUserId());
		request.setToken(getPreferences().getTokenLast8());
		request.setCustomerId(customerId);
		request.setCvdId(cvdId);
		submitRequest(RequestConstants.CUSTOMER_DELETE,request.toJsonString(), REQUEST_ACTION);
	}
	
	private void handlerActionData(String dataStr){
		try {
			JSONObject json = new JSONObject(dataStr);
			if(ResponseConstant.OK.equals(json.get(ResponseConstant.STATUS))){
				showToast(R.string.msg_response_success);
				CustomerDetailActivity.this.finish();
			} else if(ResponseConstant.EXCEPTION.equals(json.get(ResponseConstant.STATUS))){
				showToast(R.string.msg_action_failed);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			showToast(R.string.msg_response_failed);
		}
	}
	
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what){
			case 1:
				loading.setVisibility(View.VISIBLE);
				loadInfoData("INFO", REQUEST_INFO);
				break;
				
			case 2:
				loadInfoData("ADDRESS", REQUEST_ADDRESS);
				break;
				
			case 3:
				loadInfoData("DEPT", REQUEST_DEPT);
				break;
				
			case 4:
				loading.setVisibility(View.GONE);
				tab_button_group.setEnabled(true);
				viewPager.setEnabled(true);
				initFragments();
				break;
				
			case 5:
				loading.setVisibility(View.GONE);
				
			default:
				break;
			}
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

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getResponseInfo() {
		return responseInfo;
	}

	public void setResponseInfo(String responseInfo) {
		this.responseInfo = responseInfo;
	}

	public String getResponseAddress() {
		return responseAddress;
	}

	public void setResponseAddress(String responseAddress) {
		this.responseAddress = responseAddress;
	}

	public String getResponseDept() {
		return responseDept;
	}

	public void setResponseDept(String responseDept) {
		this.responseDept = responseDept;
	}

}
