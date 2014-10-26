package com.explore.android.mobile.activity.out;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import com.explore.android.R;
import com.explore.android.mobile.adapter.SectionPagerAdapter;
import com.explore.android.mobile.constants.ActivityCode;
import com.explore.android.mobile.constants.SDConstants;
import com.explore.android.mobile.data.predata.OutCreatePreData;

public class OutDetailActivity extends ActionBarActivity{

	private static final int ITEM_NUMS = 3;
	
	public static final int TAB_OUTINFO = 0;
	public static final int TAB_OUTLINE = 1;
	public static final int TAB_OUTINFOAUDIT = 2;
	
	private ActionBar actionBar;
	private ViewPager viewPager;
	private SectionPagerAdapter adapter;
	private Fragment[] fragments;
	private OutDetailInfoFragment outInfoFragment;
	
	private String outId;
	private String outType;
	private String shipTo;
	private String soPriceLogonId;
	private boolean editable;
	private boolean hasEdit;
	boolean hasAudit;
	
	private final TabListener mTabListener = new TabListener() {
		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction arg1) {
		}
		
		@Override
		public void onTabSelected(Tab tab, FragmentTransaction arg1) {
			if (viewPager != null) {
				viewPager.setCurrentItem(tab.getPosition());
			}
		}
		
		@Override
		public void onTabReselected(Tab tab, FragmentTransaction arg1) {
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_out_detail);
		
		initViews();
		initValues();
		initFragments();
		initListener();
	}
	
	private void initViews(){
		viewPager = (ViewPager) findViewById(R.id.outdetail_view_page);
	}
	
	@SuppressLint("DefaultLocale")
	private void initValues(){
		
		Intent intent = getIntent();
		if(intent.hasExtra("outId") && intent.hasExtra("outtype")){
			setOutId(intent.getStringExtra("outId"));
			setOutType(intent.getStringExtra("outtype"));
			
			if(SDConstants.OUTTYPE2_DTC.equals(getOutType())){
			}
			
			if(intent.hasExtra("sts")){
				if("new".equals(intent.getStringExtra("sts").toLowerCase())){
					hasAudit = false;
					editable = true;
				} else {
					editable = false;
					hasAudit = true;
				}
			}
			
			actionBar = getSupportActionBar();
			actionBar.setDisplayHomeAsUpEnabled(true);
			actionBar.setTitle(R.string.transportline_info);
			actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
			actionBar.addTab(actionBar.newTab().setText(R.string.outdetail_outinfo).setTabListener(mTabListener));
			actionBar.addTab(actionBar.newTab().setText(R.string.outdetail_outinfodetail).setTabListener(mTabListener));
			if(hasAudit){
				actionBar.addTab(actionBar.newTab().setText(R.string.outdetail_outinfoaudit).setTabListener(mTabListener));
			}
		} else {
			Toast.makeText(OutDetailActivity.this, getResources().getString(R.string.msg_getdata_failed), Toast.LENGTH_SHORT).show();
			OutDetailActivity.this.finish();
		}
	}
	
	private void initListener(){
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				actionBar.selectTab(actionBar.getTabAt(position));
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}
	
	private void initFragments(){
		if(!hasAudit){
			fragments = new Fragment[ITEM_NUMS-1];
		}else{
			fragments = new Fragment[ITEM_NUMS];
			fragments[TAB_OUTINFOAUDIT] = new OutDetailAuditFragment();
		}
		outInfoFragment = new OutDetailInfoFragment();
		fragments[TAB_OUTINFO] = outInfoFragment;
		fragments[TAB_OUTLINE] = new OutDetailOutLineFragment();
		
		adapter = new SectionPagerAdapter(getSupportFragmentManager(), fragments);
		viewPager.setAdapter(adapter);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		OutCreatePreData.clear();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == android.R.id.home) {
			if(hasEdit){
				setResult(ActivityCode.OUT_DETAIL_CHANGES);
			}
			OutDetailActivity.this.finish();
            return true;
        }
		return super.onOptionsItemSelected(item);
	}
	
	public void updateOutInfo() {
		outInfoFragment.loadOutDetail();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			if(hasEdit){
				setResult(1);
			}
			OutDetailActivity.this.finish();
		}
		return false;
	}
	
	public String getOutId() {
		return outId;
	}

	public void setOutId(String outId) {
		this.outId = outId;
	}

	public String getOutType() {
		return outType;
	}

	public void setOutType(String outType) {
		this.outType = outType;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public String getShipTo() {
		return shipTo;
	}

	public void setShipTo(String shipTo) {
		this.shipTo = shipTo;
	}

	public String getSoPriceLogonId() {
		return soPriceLogonId;
	}

	public void setSoPriceLogonId(String soPriceLogonId) {
		this.soPriceLogonId = soPriceLogonId;
	}

}
