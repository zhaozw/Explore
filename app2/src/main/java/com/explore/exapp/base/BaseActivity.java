package com.explore.exapp.base;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;

/**
 * Created by ryan on 14/10/31.
 */
public abstract class BaseActivity extends Activity {

    protected MyApplication myApplication;
    protected ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myApplication = (MyApplication) getApplication();

        mActionBar = getActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayShowHomeEnabled(false);
            mActionBar.setIcon(android.R.color.transparent);
        }
    }

}
