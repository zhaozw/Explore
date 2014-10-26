package com.explore.android.mobile.common;

import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;

public class ExLocationListener implements BDLocationListener {

	private static final String TAG = "ExLocationListener";

	/**
	 * location.getLocType()的返回值含义： 
	 * 61 ： GPS定位结果 
	 * 62 ： 扫描整合定位依据失败。此时定位结果无效。 
	 * 63 ： 网络异常，没有成功向服务器发起请求。此时定位结果无效。 
	 * 65 ： 定位缓存的结果。 
	 * 66 ： 离线定位结果，通过requestOfflineLocaiton调用时对应的返回结果。
	 * 67 ： 离线定位失败，通过requestOfflineLocaiton调用时对应的返回结果。
	 * 68 ： 网络连接失败时，查找本地离线定位时对应的返回结果
	 * 161：表示网络定位结果 
	 * 162~167： 服务端定位失败。
	 **/

	@Override
	public void onReceiveLocation(BDLocation location) {
		if (location == null) {
			return;
		}

		StringBuffer sb = new StringBuffer(256);
		sb.append("time : ");
		sb.append(location.getTime());
		sb.append("\nerror code : ");
		sb.append(location.getLocType());
		sb.append("\nlatitude : ");
		sb.append(location.getLatitude());
		sb.append("\nlontitude : ");
		sb.append(location.getLongitude());
		sb.append("\nradius : ");
		sb.append(location.getRadius());
		if (location.getLocType() == BDLocation.TypeGpsLocation) {
			sb.append("\nspeed : ");
			sb.append(location.getSpeed());
			sb.append("\nsatellite : ");
			sb.append(location.getSatelliteNumber());
		} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
			sb.append("\naddr : ");
			sb.append(location.getAddrStr());
		}
		Log.e(TAG, sb.toString());
	}

	@Override
	public void onReceivePoi(BDLocation poiLocation) {
		if (poiLocation == null) {
			return;
		}
	}

}
