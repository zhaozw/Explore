package com.explore.android.mobile.activity.settings;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.explore.android.R;
import com.explore.android.mobile.common.SharePreferencesManager;
import com.explore.android.mobile.constants.PreferencesConstant;

public class SetServerUrlActicity extends ActionBarActivity{
	
	private EditText edt_url;
	private Button btn_set_url;
	private Button btn_reset_url;
	private Button btn_default_url;
	private SharePreferencesManager preferences;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acvitity_setupurl);
		preferences = SharePreferencesManager.getInstance(this);
		intiViews();
		if(preferences.getHttpUrl().equals("")){
			edt_url.setText(PreferencesConstant.HTTPESB);
		} else {
			edt_url.setText(preferences.getHttpUrl());
		}
		
		btn_set_url.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				String uri = edt_url.getText().toString().trim();
				if(uri.equals("")){
					Toast.makeText(SetServerUrlActicity.this, getString(R.string.msg_url_setup_null), Toast.LENGTH_SHORT).show();
				} else {
					preferences.setHttpUrl(uri);
					Toast.makeText(SetServerUrlActicity.this, "OK", Toast.LENGTH_SHORT).show();
					SetServerUrlActicity.this.finish();
				}
			}
		});
		
		btn_reset_url.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				edt_url.setText("");
			}
		});
		
		btn_default_url.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				edt_url.setText(PreferencesConstant.HTTPESB);
			}
		});
		
	}
	
	private void intiViews(){
		edt_url = (EditText) findViewById(R.id.edt_setup_inputurl);
		btn_set_url = (Button) findViewById(R.id.btn_setupurl_submit);
		btn_reset_url = (Button) findViewById(R.id.btn_setupurl_reset);
		btn_default_url = (Button) findViewById(R.id.btn_setupurl_default);
		
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setTitle(R.string.test_setupurl_title);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
		return super.onOptionsItemSelected(item);
	}
	
	
}
