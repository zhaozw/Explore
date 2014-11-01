package com.explore.exapp.base;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by ryan on 14/10/31.
 */
public class MyApplication extends Application {

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
