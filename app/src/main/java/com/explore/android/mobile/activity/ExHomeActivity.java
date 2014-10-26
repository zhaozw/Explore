package com.explore.android.mobile.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.explore.android.R;
import com.explore.android.mobile.activity.message.ExMessageManager;
import com.explore.android.mobile.common.SharePreferencesManager;
import com.explore.android.mobile.constants.AppConstant;
import com.explore.android.mobile.constants.AppStatus;
import com.explore.android.mobile.fragment.AppPreferenceFragment;
import com.explore.android.mobile.fragment.AuditFragment;
import com.explore.android.mobile.fragment.ContactFragment;
import com.explore.android.mobile.fragment.CustomerSearchFragment;
import com.explore.android.mobile.fragment.HomeFragment;
import com.explore.android.mobile.fragment.NavigationFragment;
import com.explore.android.mobile.fragment.NavigationFragment.NavigationFragmentCallbacks;
import com.explore.android.mobile.fragment.OutManageFragment;
import com.explore.android.mobile.fragment.ProductSearchFragment;
import com.explore.android.mobile.fragment.ScheduleFragment;
import com.explore.android.mobile.fragment.StockSearchFragment;
import com.explore.android.mobile.fragment.TransportSearchFragment;
import com.explore.android.mobile.service.P2PStaticService;

public class ExHomeActivity extends ActionBarActivity implements NavigationFragmentCallbacks{

	private static boolean isExit = false;
	private SharePreferencesManager preferences;
	private NavigationFragment navigationFragment;
	private CharSequence mTitle;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_homepage);
		preferences = SharePreferencesManager.getInstance(this);

		ExMessageManager.loadMessages(this);
		AppStatus.HOME_ACT_RUNNING = true;

		navigationFragment = (NavigationFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getString(R.string.top_title_home);

		navigationFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

		// Open P2P Service or not.
		if (preferences.getIsReceiveP2PInfo()){
			initP2PService();
		} else {
			Intent intent = new Intent(ExHomeActivity.this, P2PStaticService.class);
			stopService(intent);
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			if(!isExit){
				isExit = true;
				Toast.makeText(ExHomeActivity.this, getResources().getString(R.string.app_back_event_msg), Toast.LENGTH_SHORT).show();
				keyHandle.sendEmptyMessageDelayed(0, 2000);
			}else{
//				PollingManage.stopPollingService(ExHomeActivity.this, P2PService.class, P2PService.ACTION);
				AppStatus.HOME_ACT_RUNNING = false;
				System.exit(0);
			}
		}
		
		return false;
	}
	
	@SuppressLint("HandlerLeak")
	private Handler keyHandle = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			isExit = false;
		}
	};
	
	private void initP2PService(){
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				// PollingManage.startPollingService(ExHomeActivity.this, 15, P2PService.class, P2PService.ACTION);
				Intent intent = new Intent(ExHomeActivity.this, P2PStaticService.class);
				startService(intent);
			}
		}, 2000);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		AppStatus.HOME_ACT_RUNNING = false;
	}

	@Override
	public void onNavigationFragmentSelected(int navCode) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		switch (navCode) {
		case AppConstant.NAVCODE_HOME:
			fragmentManager.beginTransaction()
            .replace(R.id.container, HomeFragment.newInstance(AppConstant.NAVCODE_HOME))
            .commit();
			break;
		case AppConstant.NAVCODE_PROSEA:
			fragmentManager.beginTransaction()
            .replace(R.id.container, ProductSearchFragment.newInstance(AppConstant.NAVCODE_PROSEA))
            .commit();
			break;
		case AppConstant.NAVCODE_CUSSEA:
			fragmentManager.beginTransaction()
			.replace(R.id.container, CustomerSearchFragment.newInstance(AppConstant.NAVCODE_CUSSEA))
			.commit();
			break;
		case AppConstant.NAVCODE_STOSEA:
			fragmentManager.beginTransaction()
			.replace(R.id.container, StockSearchFragment.newInstance(AppConstant.NAVCODE_STOSEA))
			.commit();
			break;
		case AppConstant.NAVCODE_OUTSEA:
			fragmentManager.beginTransaction()
			.replace(R.id.container, TransportSearchFragment.newInstance(AppConstant.NAVCODE_OUTSEA))
			.commit();
			break;
		case AppConstant.NAVCODE_CONTACT:
			fragmentManager.beginTransaction()
			.replace(R.id.container, ContactFragment.newInstance(AppConstant.NAVCODE_CONTACT))
			.commit();
			break;
		case AppConstant.NAVCODE_AUDIT:
			fragmentManager.beginTransaction()
			.replace(R.id.container, AuditFragment.newInstance(AppConstant.NAVCODE_AUDIT))
			.commit();
			break;
		case AppConstant.NAVCODE_SCHEDULE:
			fragmentManager.beginTransaction()
			.replace(R.id.container, ScheduleFragment.newInstance(AppConstant.NAVCODE_SCHEDULE))
			.commit();
			break;
		case AppConstant.NAVCODE_SETTINGS:
			fragmentManager.beginTransaction()
			.replace(R.id.container, AppPreferenceFragment.newInstance(AppConstant.NAVCODE_SETTINGS))
			.commit();
			break;
		case AppConstant.NAVCODE_OUTMAN:
			fragmentManager.beginTransaction()
			.replace(R.id.container, OutManageFragment.newInstance(AppConstant.NAVCODE_OUTMAN))
			.commit();
		default:
			break;
		}
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!navigationFragment.isDrawerOpen()) {
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }
    
    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle(mTitle);
    }
    
    public void onSectionAttached(int navCode) {
    	switch (navCode) {
    	case AppConstant.NAVCODE_NAVIGATION:
    		mTitle = getString(R.string.top_title_navigation);
    		break;
    		
    	case AppConstant.NAVCODE_HOME:
    		mTitle = getString(R.string.top_title_home);
    		break;
    		
    	case AppConstant.NAVCODE_PROSEA:
    		mTitle = getString(R.string.top_title_product_search);
			break;
			
		case AppConstant.NAVCODE_CUSSEA:
			mTitle = getString(R.string.top_title_customer_search);
			break;
			
		case AppConstant.NAVCODE_STOSEA:
			mTitle = getString(R.string.top_title_stock_search);
			break;
			
		case AppConstant.NAVCODE_OUTSEA:
			mTitle = getString(R.string.top_title_transport_search);
			break;
			
		case AppConstant.NAVCODE_CONTACT:
			mTitle = getString(R.string.top_title_contact);
			break;
			
		case AppConstant.NAVCODE_AUDIT:
			mTitle = getString(R.string.top_title_audit);
			break;
			
		case AppConstant.NAVCODE_SCHEDULE:
			mTitle = getString(R.string.top_title_schedule);
			break;
			
		case AppConstant.NAVCODE_SETTINGS:
			mTitle = getString(R.string.top_title_settings);
			break;
			
		case AppConstant.NAVCODE_OUTMAN:
			mTitle = getString(R.string.top_title_outmanage);
			
		default:
			break;
		}
    }
    
}
