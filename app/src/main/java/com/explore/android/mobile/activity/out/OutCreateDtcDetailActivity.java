package com.explore.android.mobile.activity.out;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.explore.android.R;
import com.explore.android.core.base.BaseFragmentActivity;
import com.explore.android.mobile.adapter.SectionPagerAdapter;
import com.explore.android.mobile.constants.ActivityCode;
import com.explore.android.mobile.constants.RequestConstants;
import com.explore.android.mobile.constants.ResponseConstant;
import com.explore.android.mobile.data.request.OutDTCCreateRequest;
import com.explore.android.mobile.model.OutDTCTransportData;
import com.explore.android.mobile.view.DialogUtil;

public class OutCreateDtcDetailActivity extends BaseFragmentActivity{

	private static final int FRAG_TRANSINFO = 0;
	private static final int FRAG_TRANSLINE = 1;
	private static final int ITEM_NUMS = 2;
	private static final int REQUEST_SUBMIT = 1;
	
	private RadioGroup tab_button_group;
	private RadioButton rabtn_transportInfo;
	private RadioButton rabtn_transportLine;
	
	private ViewPager viewPager;
	private SectionPagerAdapter adapter;
	private Fragment[] fragments;
	private RelativeLayout loading;
	
	private Button btn_save;
	private Button btn_back;
	
	private OutCreateDtcInfoFragment infoFragment;
	private OutCreateDtcLineFragment lineFragment;
	
	OutDTCCreateRequest request;
	String transportId;
	List<OutDTCTransportData> transportList;
	
	@Override
	protected int setLayoutId() {
		return R.layout.activity_out_create_dtc;
	}

	@Override
	public void initViews() {
		tab_button_group = (RadioGroup) findViewById(R.id.act_rgroud_dtccreate_detail);
		rabtn_transportInfo = (RadioButton) findViewById(R.id.act_out_ctratedtc_btn_transinfo);
		rabtn_transportLine = (RadioButton) findViewById(R.id.act_out_ctratedtc_btn_transline);
		
		viewPager = (ViewPager) findViewById(R.id.act_createdtc_detail_virepager);
		loading = (RelativeLayout) findViewById(R.id.app_loading_layout);
		
		btn_save = (Button) findViewById(R.id.act_createdtc_detail_btn_save);
		btn_back = (Button) findViewById(R.id.act_createdtc_detail_btn_back);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle(R.string.outdtc_transport_title);
		btn_save.setEnabled(false);
	}

	@Override
	public void initValues() {
		Intent dtc_intent = getIntent();
		request = new OutDTCCreateRequest();
		request.setOutType("DTC");
		request.setTransportCode1(dtc_intent.getStringExtra("TRANSPORTCODE1"));
		request.setCustomerDept(dtc_intent.getStringExtra("CUSTOMERDEPT"));
		request.setOutTime(dtc_intent.getStringExtra("OUTTIME"));
		request.setShipTo(dtc_intent.getStringExtra("SHIPTO"));
		request.setShipToAdress(dtc_intent.getStringExtra("SHIPTOADRESS"));
		request.setSoPriceLogon(dtc_intent.getStringExtra("SOPRICELOGON"));
		request.setVatTo(dtc_intent.getStringExtra("VATTO"));
		
		initFragments();
		
//		loadDTCTransportData(transportCode1);
		// infoFragment.getInfoData();
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
		
		btn_save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				submitOutSalCreate();
			}
		});
		
		btn_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				OutCreateDtcDetailActivity.this.finish();
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
		if(REQUEST_SUBMIT == what) {
			handlerSubmitData(response);
		}
	}
	
	private void initFragments() {
		fragments = new Fragment[ITEM_NUMS];
		infoFragment = new OutCreateDtcInfoFragment();
		fragments[FRAG_TRANSINFO] = infoFragment;
		lineFragment = new OutCreateDtcLineFragment();
		fragments[FRAG_TRANSLINE] = lineFragment;

		adapter = new SectionPagerAdapter(getSupportFragmentManager(), fragments);
		viewPager.setAdapter(adapter);
	}
	
	public void setTabChecked(int tabNum) {
		switch (tabNum) {

		case FRAG_TRANSINFO:
			rabtn_transportInfo.setChecked(true);
			break;

		case FRAG_TRANSLINE:
			rabtn_transportLine.setChecked(true);
			break;
			
		default:
			break;
		}
	}
	
	public void switchScheduleMode(int id) {
		switch (id) {
		case R.id.act_out_ctratedtc_btn_transinfo:
			viewPager.setCurrentItem(FRAG_TRANSINFO);
			break;
		
		case R.id.act_out_ctratedtc_btn_transline:
			viewPager.setCurrentItem(FRAG_TRANSLINE);
			break;

		default:
			break;
		}
	}
	
	private void submitOutSalCreate(){
		DialogUtil.createProgressDialog(this, R.string.out_dialog_title_product_add, R.string.msg_loadding);
		request.setUserId(getPreferences().getUserId());
		request.setToken(getPreferences().getTokenLast8());
		request.setOutType("DTC");
		request.setTransportId(transportId);
		request.setResults(transportList);
		asynDataRequest(RequestConstants.OUTDTC_CREATE_SUBMIT,request.toJsonString(), request.toJson2String(), REQUEST_SUBMIT);
	}

	private void handlerSubmitData(String resStr){
		try {
			JSONObject json = new JSONObject(resStr);
			if(ResponseConstant.OK.equals(json.get(ResponseConstant.STATUS))){
				DialogUtil.progressDialog.dismiss();
				Toast.makeText(OutCreateDtcDetailActivity.this, getResources().getString(R.string.app_action_success), Toast.LENGTH_SHORT).show();
				
				String outId = json.getString("OUTID");
				
				Intent result_intent = new Intent(OutCreateDtcDetailActivity.this, OutCreateDTCActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("OUTID", outId);
				result_intent.putExtra("OUTID", bundle);
				setResult(ActivityCode.OUTDTC_SAVE_SUCCESS, result_intent);
				OutCreateDtcDetailActivity.this.finish();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public void enableSave(){
		btn_save.setEnabled(true);
	}
	
	public void disableSave(){
		btn_save.setEnabled(false);
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
