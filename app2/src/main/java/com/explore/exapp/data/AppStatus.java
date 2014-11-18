package com.explore.exapp.data;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.explore.exapp.R;
import com.explore.exapp.base.util.ToastUtil;

import java.util.HashMap;

/**
 * Created by ryan on 14/11/1.
 */
public class AppStatus {

    public static final boolean IS_DEBUG = false;

    private static HashMap<String, String> imeiImsiMap = new HashMap<String, String>();
    public static HashMap<String, String> getImeiImstInfo(Context context) {
        HashMap<String, String> map = new HashMap<String, String>();
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager.getDeviceId() == null) {
            map.put("IMEI", "");
            ToastUtil.showToast(context, R.string.phone_error_no_imei);
        } else {
            map.put("IMEI", telephonyManager.getDeviceId());
        }
        if (telephonyManager.getSubscriberId() == null) {
            ToastUtil.showToast(context, R.string.phone_error_no_imsi);
            map.put("IMSI", "");
        } else {
            map.put("IMSI", telephonyManager.getSubscriberId());
        }
        imeiImsiMap.putAll(map);
        return imeiImsiMap;
    }

}
