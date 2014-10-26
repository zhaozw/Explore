package com.explore.android.mobile.common;

import java.io.File;
import java.io.IOException;

import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.util.Log;

import com.explore.android.LoginActivity;
import com.explore.android.core.http.HttpHelper;
import com.explore.android.mobile.constants.AppConstant;
import com.explore.android.mobile.constants.RequestConstants;

public class Common {
	
	/**
	 * Create the application directory.
	 * 
	 * @return
	 */
	public static boolean createAppDir() {
		if (isSDCardAvailable()) {
			File appFile = new File(AppConstant.SDCARD_PATH +"/"+ AppConstant.APP_DIR);
			if (!appFile.exists()) {
				return appFile.mkdir();
			} else {
				return true;
			}
		}
		return false;
	}

	/**
	 * Create the log file under application directory.
	 * 
	 * @return
	 */
	public static boolean initLogFile() {
		File logFile = new File(AppConstant.APP_HOME_PATH +"/"+ AppConstant.APP_LOG_DIR + "/" + AppConstant.APP_LOG_USERLOG);
		File logDir = new File(AppConstant.APP_HOME_PATH +"/" + AppConstant.APP_LOG_DIR);
		
		Log.e("app init", "logFile"+ AppConstant.APP_HOME_PATH +"/"+ AppConstant.APP_LOG_DIR + "/" + AppConstant.APP_LOG_USERLOG);
		Log.e("app init", "logDir"+ AppConstant.APP_HOME_PATH +"/" + AppConstant.APP_LOG_DIR);
		
		if (createAppDir()) {
			if (logDir.exists()) {
				try {
					logFile.createNewFile();
					return true;
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				if (logDir.mkdir()) {
					try {
						logFile.createNewFile();
						return true;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return false;
	}

	/**
	 * Check if the Sdcard is available.
	 * 
	 * @return
	 */
	public static boolean isSDCardAvailable() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 查看是否有可用网络
	 * @param context
	 * @return
	 */
	public static boolean isNetWorkEnable(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo != null) {
			return networkInfo.isAvailable();
		}
		return false;
	}
	
	/**
	 * 查看wifi是否可用
	 * @param context
	 * @return
	 */
	public static boolean isWifiEnable(Context context){
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);  
        return wifiManager.isWifiEnabled();  
	}
	
	/**
	 * 查看GPS是否可用
	 * @param context
	 * @return
	 */
	public static boolean isGPSEnable(Context context){
		LocationManager locationManager = ((LocationManager) context.getSystemService(Context.LOCATION_SERVICE));  
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);  
	}
	
	/**
	 * 查看服务器是否运行
	 * @param url
	 * @return
	 * @throws org.json.JSONException
	 */
	public static boolean isServerAvailable(String url) {
		boolean bool = false;
		String result = HttpHelper.simpleConn(url, RequestConstants.TEST_CONNECTION, "1");
		if (result != null) {
			bool = true;
		}
		return bool;
	}
	
	public static void backToLoginIn(Activity act){
		Intent intent = new Intent(act, LoginActivity.class);
		act.startActivity(intent);
		act.finish();
	}
	
}
