package com.explore.android.mobile.common;

import java.util.Date;

import android.content.Context;
import android.util.Log;

import com.explore.android.core.util.DateUtil;
import com.explore.android.mobile.constants.AppConstant;
import com.explore.android.mobile.db.opration.CacheDBO;
import com.explore.android.mobile.model.ExCache;

/**
 * 
 * 请求的缓存管理器
 * 
 * @author Ryan
 * 
 */
public class ExCacheManager {

	public static final int CACHE_SCHEDULE = 1; 		// 日程
	public static final int CACHE_CONTACT = 2; 			// 联系人
	public static final int CACHE_AUDITLIST = 3; 		// 审核列表
	public static final int CACHE_AUDIT_DETAIL = 4; 	// 审核详情
	public static final int CACHE_CUSTOMER_DEPT = 5; 	// 经营单位
	public static final int CACHE_CUSDEPT_EXTRA = 6; 	// 经营单位附加信息
	public static final int CACHE_SHIPTO_EXTRA = 7; 	// 配送到附加信息
	public static final int CACHE_DTC_CUSTOMER = 8; 	// 客户列表
	public static final int CACHE_PROJECT_EXTRA = 9; 	// 项目附加信息
	public static final int CACHE_OUT_PRODUCT = 10; 	// 商品信息
	public static final int CACHE_OUTLINE_LIST = 11; 	// 商品详情列表
	public static final int CACHE_PRODUCT_MODIFYPRE = 12; // 商品修改预处理
	public static final int CACHE_CUSTOMER_DETAIL = 13;	// 客户信息

	public static final String TYPE_JSON = "json";

	private static ExCacheManager instance = null;
	private static CacheDBO dbo;

	private ExCacheManager() {
	}

	public static ExCacheManager getInstance(Context con) {
		if (instance == null) {
			instance = new ExCacheManager();
		}
		if(dbo == null){
			dbo = new CacheDBO(con);
		}
		return instance;
	}

	// -------------------------------------
	// Schedule 日程缓存
	// -------------------------------------
	public void addScheduleCache(int y, int m, int d, String data) {
		String code = "sche-" + y + "-" + m + "-" + d;
		addCache(CACHE_SCHEDULE, code, data, TYPE_JSON);
	}

	public String getScheduleCache(int y, int m, int d) {
		String code = "sche-" + y + "-" + m + "-" + d;
		return getCache(code);
	}

	public boolean clearScheduleCache(int y, int m, int d) {
		String code = "sche-" + y + "-" + m + "-" + d;
		return clearCache(code);
	}

	// -------------------------------------
	// Contact 联系人缓存
	// -------------------------------------
	public void addContactCache(String data) {
		String code = "contact";
		addCache(CACHE_CONTACT, code, data, TYPE_JSON);
	}

	public String getContactCache() {
		String code = "contact";
		return getCache(code);
	}

	public boolean clearContactCache() {
		String code = "contact";
		return clearCache(code);
	}

	// -------------------------------------
	// Audit 审核缓存
	// -------------------------------------
	public void addAuditCache(String data) {
		String code = "auditlist";
		addCache(CACHE_AUDITLIST, code, data, TYPE_JSON);
	}

	public String getAuditCache() {
		String code = "auditlist";
		return getCurrentCache(code);
	}

	public boolean clearAuditCache() {
		String code = "auditlist";
		return clearCache(code);
	}

	// -------------------------------------
	// Audit 审核详情缓存
	// -------------------------------------
	public void addAuditDetailCache(String data, int auditId) {
		String code = "audit-" + auditId;
		addCache(CACHE_AUDIT_DETAIL, code, data, TYPE_JSON);
	}

	public String getAuditDetailCache(int auditId) {
		String code = "audit-" + auditId;
		return getCache(code);
	}

	public boolean clearAuditDetailCache(int auditId) {
		String code = "audit-" + auditId;
		return clearCache(code);
	}

	// -------------------------------------
	// CustomerDept 根据USERID获取的经营单位缓存（OutSALFragment）
	// -------------------------------------
	public void addOutCusDeptCache(String data, String userId) {
		String code = "cusdept-" + userId;
		addCache(CACHE_CUSTOMER_DEPT, code, data, TYPE_JSON);
	}

	public String getOutCusDeptCache(String userId) {
		String code = "cusdept-" + userId;
		return getCache(code);
	}

	public boolean clearOutCusDeptCache(String userId) {
		String code = "cusdept-" + userId;
		return clearCache(code);
	}

	// -------------------------------------
	// CusDeptExtra 经营单位获取的附加信息（OutSALFragment）
	// -------------------------------------
	public void addOutCusDeptExtraCache(String data, String mode, String deptId) {
		String code = "cusdept-extra-" + mode + deptId;
		addCache(CACHE_CUSDEPT_EXTRA, code, data, TYPE_JSON);
	}

	public String getOutCusDeptExtraCache(String mode, String deptId) {
		String code = "cusdept-extra-" + mode + deptId;
		return getCache(code);
	}

	public boolean clearOutCusDeptExtraCache(String mode, String deptId) {
		String code = "cusdept-extra-" + mode + deptId;
		return clearCache(code);
	}

	// -------------------------------------
	// ShipToExtra 经营单位获取的附加信息（OutSALFragment）
	// -------------------------------------
	public void addOutShipToExtraCache(String data, String shipToId) {
		String code = "shipto-extra-" + shipToId;
		addCache(CACHE_SHIPTO_EXTRA, code, data, TYPE_JSON);
	}

	public String getOutShipToExtraCache(String shipToId) {
		String code = "shipto-extra-" + shipToId;
		return getCache(code);
	}

	public boolean clearOutShipToExtraCache(String shipToId) {
		String code = "shipto-extra-" + shipToId;
		return clearCache(code);
	}

	// -------------------------------------
	// ShipToExtra 经营单位获取的附加信息（OutSALFragment）
	// -------------------------------------
	public void addOutDTCCustomerCache(String data, String userId) {
		String code = "dtc-customer-" + userId;
		addCache(CACHE_SHIPTO_EXTRA, code, data, TYPE_JSON);
	}

	public String getOutDTCCustomerCache(String userId) {
		String code = "dtc-customer-" + userId;
		return getCache(code);
	}

	public boolean clearOutDTCCustomerCache(String userId) {
		String code = "dtc-customer-" + userId;
		return clearCache(code);
	}

	// -------------------------------------
	// ShipToExtra 经营单位获取的附加信息（OutSALFragment）
	// -------------------------------------
	public void addProjectExtraCache(String data, String projectId) {
		String code = "project-extra-" + projectId;
		addCache(CACHE_PROJECT_EXTRA, code, data, TYPE_JSON);
	}

	public String getProjectExtraCache(String projectId) {
		String code = "project-extra-" + projectId;
		return getCache(code);
	}

	public boolean clearProjectExtraCache(String projectId) {
		String code = "project-extra-" + projectId;
		return clearCache(code);
	}

	// -------------------------------------
	// 商品缓存
	// -------------------------------------
	public void addProductCache(String data, String productId) {
		String code = "product-" + productId;
		addCache(CACHE_OUT_PRODUCT, code, data, TYPE_JSON);
	}

	public String getProductCache(String productId) {
		String code = "product-" + productId;
		return getCache(code, 20000L);
	}

	public boolean clearProductCache(String productId) {
		String code = "product-" + productId;
		return clearCache(code);
	}

	// -------------------------------------
	// 销售明细缓存
	// -------------------------------------
	public void addOutLineListCache(String data, String outId){
		String code = "outline-list-" + outId;
		addCache(CACHE_OUTLINE_LIST, code, data, TYPE_JSON);
	}
	
	public String getOutLineListCache(String outId){
		String code = "outline-list-" + outId;
		return getCurrentCache(code);
	}
	
	public boolean clearOutLineListCache(String outId){
		String code = "outline-list-" + outId;
		return clearCache(code);
	}
	
	// -------------------------------------
	// 客户明细缓存
	// -------------------------------------
	public void addCustomerDetailCache(String data, String type, String customerId){
		String code = "cusdetail-" + type + "-" + customerId;
		addCache(CACHE_CUSTOMER_DETAIL, code, data, TYPE_JSON);
	}
	
	public String getCustomerDetailCache(String type, String customerId){
		String code = "cusdetail-" + type + "-" + customerId;
		return getCurrentCache(code);
	}
	
	public boolean clearCustomerDetailCache(String type, String customerId){
		String code = "cusdetail-" + type + "-" + customerId;
		return clearCache(code);
	}

	/**
	 * 添加缓存
	 * 
	 * @param cafor
	 * @param code
	 * @param data
	 * @param datatype
	 */
	public void addCache(int cafor, String code, String data, String datatype) {
		ExCache cache = new ExCache();
		cache.setCacheCode(code);
		cache.setChacheFor(CACHE_CONTACT);
		cache.setData(data);
		cache.setDataType(TYPE_JSON);
		cache.setTime(new Date());
		dbo.open();
		dbo.addCache(cache);
		dbo.close();
		Log.i("Cache Manager", "Add Cache.Code:" + code);
	}
	
	/**
	 * 获取缓存
	 * @param code Code for cache.
	 * @param time Fresh time.
	 * @return
	 */
	public String getCache(String code, long time) {
		ExCache cache = new ExCache();
		dbo.open();
		cache = dbo.getDataByCode(code);
		dbo.close();
		if (cache != null) {
			if (DateUtil.checkCacheTime(new Date(), cache.getTime(), time)) {
				return null;
			}
			Log.i("Cache Manager", "Get Cache.Code:" + code);
			return cache.getData();
		} else {
			return null;
		}
	}

	/**
	 * 获取缓存,如果返回值为null,则表示应该从服务端获取
	 * 
	 * @param code Code for cache.
	 * @return
	 */
	public String getCache(String code) {
		ExCache cache = new ExCache();
		dbo.open();
		cache = dbo.getDataByCode(code);
		dbo.close();
		if (cache != null) {
			if (DateUtil.checkCacheTime(new Date(), cache.getTime(), AppConstant.CACHE_FRESH_TIME)) {
				return null;
			}
			Log.i("Cache Manager", "Get Cache.Code:" + code);
			return cache.getData();
		} else {
			return null;
		}
	}
	
	/**
	 * 获取临时缓存,如果返回值为null,则表示应该从服务端获取
	 * 
	 * @param code Code for cache.
	 * @return
	 */
	public String getCurrentCache(String code){
		ExCache cache = new ExCache();
		dbo.open();
		cache = dbo.getDataByCode(code);
		dbo.close();
		if (cache != null) {
			if (DateUtil.checkCacheTime(new Date(), cache.getTime(), AppConstant.CACHE_FRESH_CURRENT_TIME)) {
				return null;
			}
			Log.i("Cache Manager", "Get Current Cache.Code:" + code);
			return cache.getData();
		} else {
			return null;
		}
	}

	/**
	 * 清空缓存
	 * 
	 * @param code
	 * @return
	 */
	public boolean clearCache(String code) {
		dbo.open();
		int flag = dbo.delete(code);
		dbo.close();
		if (flag == 0) {
			return false;
		} else {
			return true;
		}
	}

}
