package com.explore.exapp.base;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.litepal.LitePalApplication;

/**
 * Created by ryan on 14/10/31.
 */
public class MyApplication extends LitePalApplication {

    private static MyApplication application;
    public static RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        application = MyApplication.this;
        mRequestQueue = Volley.newRequestQueue(this);
    }

    public static MyApplication getInstance() {
        return application;
    }

    public static RequestQueue getRequestQueue() {
        return mRequestQueue;
    }
}
