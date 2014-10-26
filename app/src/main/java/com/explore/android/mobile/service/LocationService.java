package com.explore.android.mobile.service;

import android.app.IntentService;
import android.content.Intent;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

public class LocationService extends IntentService {

	public static final String PARAM = "param";
	public static final String TAG = IntentService.class.getName();
	public static final String ACTION = "com.explore.android.mobile.service.LocationService";
	public static final int MODE_NETWORK = 1;
	public static final int MODE_GPS = 2;

	private LocationClient locationClient = null;

	// private ExLocationListener locationListener = new ExLocationListener();

	public LocationService(String name) {
		super("IntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		setLocationOption();
	}

	private void setLocationOption() {
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		option.setAddrType("all");// 返回的定位结果包含地址信息
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
		option.setScanSpan(3000);// 设置发起定位请求的间隔时间为5000ms
		option.disableCache(true);// 禁止启用缓存定位
		option.setPoiNumber(5); // 最多返回POI个数
		option.setPoiDistance(1000); // poi查询距离
		option.setPoiExtraInfo(true); // 是否需要POI的电话和地址等详细信息
		locationClient.setLocOption(option);
	}

}
