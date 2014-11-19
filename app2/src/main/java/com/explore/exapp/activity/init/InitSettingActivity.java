package com.explore.exapp.activity.init;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.explore.exapp.R;
import com.explore.exapp.base.SubActivity;
import com.explore.exapp.data.Api;
import com.explore.exapp.data.AppPreferences;
import com.explore.exapp.data.CommonData;

/**
 * Created by ryan on 14/11/7.
 */
public class InitSettingActivity extends SubActivity implements View.OnClickListener {

    private EditText urlEdit;
    private Button buttonReset;
    private Button buttonDefault;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init_setting);
        mActionBar.setTitle(R.string.init_setting);

        urlEdit = (EditText) findViewById(R.id.init_setting_url);
        buttonDefault = (Button) findViewById(R.id.init_setting_default_button);
        buttonReset = (Button) findViewById(R.id.init_setting_reset_button);

        buttonReset.setOnClickListener(this);
        buttonDefault.setOnClickListener(this);

        if (!"".equals(AppPreferences.getServerUrl(this))) {
            urlEdit.setText(AppPreferences.getServerUrl(this));
        }
        if (CommonData.IS_DEBUG) {
            urlEdit.setText(Api.HTTPESB_DEBUG);
        } else {
            urlEdit.setText(Api.HTTPESB);
        }

        urlEdit.setSelection(urlEdit.getText().length());
    }

    @Override
    public void onClick(View view) {
        if (view == buttonDefault) {
            urlEdit.setText(Api.HTTPESB);
        } else if (view == buttonReset) {
            urlEdit.setText("");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_complete, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_complete) {
            AppPreferences.setServerUrl(this, urlEdit.getText().toString().trim());
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
