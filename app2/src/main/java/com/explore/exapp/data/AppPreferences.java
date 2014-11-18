package com.explore.exapp.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.explore.exapp.activity.login.model.LoginInfo;
import com.explore.exapp.base.util.Base64Coder;

import java.net.URI;

/**
 * Created by ryan on 14/11/2.
 */
public class AppPreferences {

    private static SharedPreferences preferences = null;
    public static SharedPreferences prfs(Context context) {
        if (preferences == null) {
            preferences = context.getSharedPreferences("conf", Context.MODE_PRIVATE);
        }
        return preferences;
    }

    public static void initLoginPreferences(Context context, LoginInfo info) {
        if (info == null) {
            return;
        }
        prfs(context).edit().putString(Login.TOKEN, info.getToken()).apply();
        prfs(context).edit().putString(Login.USERID, info.getUserId()).apply();
        prfs(context).edit().putString(Login.REALNAME, info.getRealName()).apply();
        prfs(context).edit().putString(Login.USERNAME, info.getUserName()).apply();
        prfs(context).edit().putString(Login.L1, info.getL1()).apply();
        prfs(context).edit().putString(Login.L2, info.getL2()).apply();
        prfs(context).edit().putString(Login.L3, info.getL3()).apply();
        prfs(context).edit().putString(Login.AUDITACCOUNT, info.getAuditCount()).apply();
    }

    public static String getUserId(Context context) {
        return prfs(context).getString(Login.USERID, "0");
    }

    public static String getTokenLast8(Context context) {
        String token = prfs(context).getString(Login.TOKEN, "");
        if (token.length() > 8) {
            token = token.substring(token.length() - 8, token.length());
        }
        return token;
    }

    public static void setServerUrl(Context context, String url) {
        prfs(context).edit().putString(Default.SERVER_URL, url).apply();
    }

    public static String getServerUrl(Context context) {
        return prfs(context).getString(Default.SERVER_URL, "");
    }

    // 开关
    public static interface Config {

        public static final String FIRST_LOGIN = "first_login"; // 是否首次登录

        public static final String PERMISSION_INIT = "permission_init";// 权限文件是否初始化

        public static final String PRODUCTCATEGORY_INIT = "productcategory_init"; // 商品类别是否初始化

        public static final String ADDRESSCONSTANTS_INIT = "addressconstants_init"; // 地址常量信息是否初始化

        public static final String KEEP_LOGIN = "auto_login";// 是否保持登录

        public static final String SAVE_LOGIN_INFO = "save_logininfo";// 是否保存登录信息

        public static final String LOCATION_SERVICE_OPEN = "location_server";// 是否打开位置服务

        public static final String RECEIVE_P2PINFOS = "recevie_p2pinfos"; // 是否自动接收系统推送服务
    }

    public static interface Login {

        public static final String TOKEN = "token";

        public static final String REALNAME = "real_name";

        public static final String USERNAME = "user_name";

        public static final String USERID = "user_id";

        public static final String AUDITACCOUNT = "audit_account";

        public static final String L1 = "l1";

        public static final String L2 = "l2";

        public static final String L3 = "l3";

        public static final String PASSWORD = "password";

    }

    public static interface Default {

        public static final String SERVER_URL = "server_url";

        public static final String CUSTOMER_DEPT = "customer_dept";

    }
}
