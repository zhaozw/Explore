package com.explore.exapp.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ryan on 14/11/2.
 */
public class AppPreferences {

    public static SharedPreferences preferences = null;
    public static SharedPreferences prfs(Context context) {
        if (preferences == null) {
            preferences = context.getSharedPreferences("conf", Context.MODE_PRIVATE);
        }
        return preferences;
    }

    // 开关
    public static final class Config {

        public static final String FIRST_LOGIN = "first_login"; // 是否首次登录

        public static final String PERMISSION_INIT = "permission_init";// 权限文件是否初始化

        public static final String PRODUCTCATEGORY_INIT = "productcategory_init"; // 商品类别是否初始化

        public static final String ADDRESSCONSTANTS_INIT = "addressconstants_init"; // 地址常量信息是否初始化

        public static final String KEEP_LOGIN = "auto_login";// 是否保持登录

        public static final String SAVE_LOGIN_INFO = "save_logininfo";// 是否保存登录信息

        public static final String LOCATION_SERVICE_OPEN = "location_server";// 是否打开位置服务

        public static final String RECEIVE_P2PINFOS = "recevie_p2pinfos"; // 是否自动接收系统推送服务
    }

    public static final class Default {

        public static final String CUSTOMER_DEPT = "customer_dept";

    }
}
