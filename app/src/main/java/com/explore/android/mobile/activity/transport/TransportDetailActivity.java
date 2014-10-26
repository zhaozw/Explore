package com.explore.android.mobile.activity.transport;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.explore.android.R;
import com.explore.android.mobile.adapter.SectionPagerAdapter;

public class TransportDetailActivity extends ActionBarActivity {
	
	private static final int TAB_ITEM_NUMS = 3;
	private static final int CACHE_PAGE_LIMIT = 2;
	
	private ViewPager viewPager;
	private ActionBar actionBar;

	private Fragment[] fragments;
	private SectionPagerAdapter adapter;
	String transportId;
	
	JSONObject responseInfo;
	JSONObject responseLine1;
	JSONObject responseLine2;
	
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
		setContentView(R.layout.activity_transport_detail);
		initViews();
		initValues();
		initListener();
	}
	
	private void initViews() {
		viewPager = (ViewPager) findViewById(R.id.transport_detail_view_page);
		viewPager.setOffscreenPageLimit(CACHE_PAGE_LIMIT);
		actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle(R.string.transportline_info);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.addTab(actionBar.newTab().setText(R.string.transdetail_title).setTabListener(mTabListener));
		actionBar.addTab(actionBar.newTab().setText(R.string.transportline1_title).setTabListener(mTabListener));
		actionBar.addTab(actionBar.newTab().setText(R.string.transportline2_title).setTabListener(mTabListener));
	}

	private void initValues() {
		Intent intent = getIntent();
		if (intent.hasExtra("transId")) {
			transportId = intent.getStringExtra("transId");
			initFragment();
		} else {
			Toast.makeText(this, getResources().getString(R.string.msg_getdata_failed), Toast.LENGTH_SHORT).show();
			TransportDetailActivity.this.finish();
		}
	}
	
	private void initListener() {
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
	
	private void initFragment() {
		fragments = new Fragment[TAB_ITEM_NUMS];
		fragments[0] = new TransportInfoFragment();
		fragments[1] = new TransportLine1Fragment();
		fragments[2] = new TransportLine2LocFragment();
		adapter = new SectionPagerAdapter(getSupportFragmentManager(), fragments);
		viewPager.setAdapter(adapter);
		viewPager.setCurrentItem(0);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
		return super.onOptionsItemSelected(item);
	}
	
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what){
			
			}
		}
		
	};
	
}
