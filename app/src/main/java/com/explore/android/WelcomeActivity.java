package com.explore.android;

import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.explore.android.core.log.Logger;
import com.explore.android.core.log.LoggerFactory;
import com.explore.android.core.log.config.PropertyConfigurator;
import com.explore.android.mobile.activity.ExHomeActivity;
import com.explore.android.mobile.common.Common;
import com.explore.android.mobile.common.PermissionXMLHandler;
import com.explore.android.mobile.common.SharePreferencesManager;
import com.explore.android.mobile.constants.AppStatus;
import com.explore.android.mobile.constants.PreferencesConstant;
import com.explore.android.mobile.data.cache.GlobalData;
import com.explore.android.mobile.db.opration.PermissionDBO;
import com.explore.android.mobile.model.Permission;

public class WelcomeActivity extends Activity{

    private static final String TAG = WelcomeActivity.class.getName();
    private static final Logger logger = LoggerFactory.getLogger(TAG);
	
	private SharePreferencesManager preferences;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		
		if(Common.createAppDir()){
			Common.initLogFile();
		}
		
		AppStatus.HOME_ACT_RUNNING = false;
        PropertyConfigurator.getConfigurator(this).configure();
		
		preferences = SharePreferencesManager.getInstance(this);
		loadImeiAndImsi();
		
		if(preferences.getIsKeepLogin()){
			
			ExApplication application = (ExApplication) getApplicationContext();
			application.setToken(preferences.getToken());
			application.setUserId(preferences.getUserId());
			GlobalData.userId = preferences.getUserId();
			
			Intent intent = new Intent(WelcomeActivity.this,ExHomeActivity.class);
			startActivity(intent);
			WelcomeActivity.this.finish();
			
		}else{
			
			appInit();
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					permissionInit();
					Intent intent = new Intent();
					intent.setClass(WelcomeActivity.this, LoginActivity.class);
					startActivity(intent);
					WelcomeActivity.this.finish();
				}
			}, 2000);
		}
		
	}
	
	private void permissionInit() {
		if (!preferences.isPermissionInit()) {
			PermissionDBO perDbo = new PermissionDBO(WelcomeActivity.this);
			perDbo.open();
			perDbo.importPermissions(parserPermissionXML());
			perDbo.close();
			preferences.setPermissionInitStatus(true);
			logger.info("Explore Application Permission Init.");
		}
	}
	

	private List<Permission> parserPermissionXML() {
		List<Permission> permissions = new ArrayList<Permission>();
		try {
			SAXParserFactory sax = SAXParserFactory.newInstance();
			XMLReader reader = sax.newSAXParser().getXMLReader();
			reader.setContentHandler(new PermissionXMLHandler(permissions));
			reader.parse(new InputSource(getResources().openRawResource(R.raw.permission_init)));
			return permissions;
		} catch (Exception e) {
			Log.i(TAG, e.toString());
			Toast.makeText(this, getResources().getString(R.string.msg_init_failed), Toast.LENGTH_SHORT).show();
		}
		
		return permissions;
	}
	
	private void appInit() {
        /*
		if("".equals(preferences.getHttpUrl())){
			preferences.setHttpUrl(PreferencesConstant.HTTPESB);
		}

        if (AppStatus.IS_DEBUG_MODE) {
            preferences.setHttpUrl(PreferencesConstant.HTTPESB_DEBUG);
        } else {
            preferences.setHttpUrl(PreferencesConstant.HTTPESB);
        }
        */
        preferences.setHttpUrl(PreferencesConstant.HTTPESB);
        preferences.setIsSavaLoginInfo(true);
		GlobalData.loadNavigationList(this);
	}
	
	private void loadImeiAndImsi(){
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        preferences.setDeviceIMEI(telephonyManager.getDeviceId());
        if(telephonyManager.getSubscriberId() == null){
            Toast.makeText(this, getString(R.string.error_empty_imsi), Toast.LENGTH_SHORT).show();
            return;
        }
		if(telephonyManager.getSubscriberId().length() < 10){
			Toast.makeText(WelcomeActivity.this, getResources().getString(R.string.msg_imsi_error), Toast.LENGTH_SHORT).show();
		} else {
			preferences.setDeviceIMSI(telephonyManager.getSubscriberId());
		}
	}
	
}
