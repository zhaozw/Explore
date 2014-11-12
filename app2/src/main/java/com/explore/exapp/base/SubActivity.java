package com.explore.exapp.base;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

/**
 * Created by ryan on 14/11/1.
 */
public abstract class SubActivity extends Activity {

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
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
