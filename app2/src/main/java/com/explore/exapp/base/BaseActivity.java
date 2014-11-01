package com.explore.exapp.base;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by ryan on 14/10/31.
 */
public abstract class BaseActivity extends Activity {

    protected MyApplication myApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myApplication = (MyApplication) getApplication();
    }

}
