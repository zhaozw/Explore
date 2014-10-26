package com.explore.android.mobile.data.cache;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.explore.android.core.util.DateUtil;
import com.explore.android.mobile.constants.AppConstant;

public class MemoryCache {

	public static final int MAX_CACHE_COUNT = 20;
	
	public static final int MAX_GLOBAL_CACHE_COUNT = 10;
	
	public static final long  CACHE_TIME = 60000L;
	
	public static Map<String, MCache> cacheMap = new HashMap<String, MCache>();
	
	public static final String CACHE_CUSTOMERDEPT = "CUSDEPT";
	
	public static final String CACHE_CUSTOMERDEPT_EXTRA = "CUSEXTRA";
	
	public static MCache getCustomerDeptCache(String customerDeptId) {
		String code = CACHE_CUSTOMERDEPT + customerDeptId;
		return getCache(code);
	}
	
	public static void addCustomerDeptCache(String customerDeptId, String data) {
		String code = CACHE_CUSTOMERDEPT + customerDeptId;
		addCache(code, data);
	}
	
	private static MCache getCache(String code) {
		MCache cache = cacheMap.get(code);
		if (DateUtil.checkCacheTime(new Date(), cache.time, AppConstant.CACHE_FRESH_CURRENT_TIME)) {
			cacheMap.remove(code);
			return null;
		} else {
			return cache;
		}
	}
	
	private static void addCache(String code, String data) {
		MCache cache = new MCache();
		cache.data = data;
		cache.time = new Date();
		cacheMap.put(code, cache);
	}
	
	public static void clearCache() {
		cacheMap.clear();
	}
	
	static class MCache{
		String data;
		Date time;
	}
	
}
