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

    public static boolean IS_DEBUG = true;

    public static final HashMap<String, String> imeiImsiMap = new HashMap<String, String>();
    public static HashMap<String, String> getImeiImstInfo(Context context) {
        if (imeiImsiMap.size() == 0) {
            HashMap<String, String> map = new HashMap<String, String>();
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            map.put("IMEI", telephonyManager.getDeviceId());
            if (telephonyManager.getSubscriberId() == null) {
                ToastUtil.showToast(context, R.string.phone_error_no_imsi);
                map.put("IMSI", "");
            } else {
                map.put("IMSI", telephonyManager.getSubscriberId());
            }
            imeiImsiMap.putAll(map);
        }
        return imeiImsiMap;
    }

}
