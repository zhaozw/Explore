package com.explore.exapp.activity.login;

import android.os.Bundle;
import android.widget.EditText;

import com.explore.exapp.R;
import com.explore.exapp.base.SubActivity;

/**
 * Created by ryan on 14/11/7.
 */
public class InitSettingActivity extends SubActivity {

    // private EditText

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init_setting);

        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setTitle(R.string.init_setting_title);
    }
}
