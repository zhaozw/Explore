package com.explore.exapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.explore.exapp.activity.guide.GuideActivity;
import com.explore.exapp.activity.login.LoginActivity;
import com.explore.exapp.base.component.CircleButton;
import com.explore.exapp.base.util.LogUtil;
import com.explore.exapp.data.AppPreferences;
import com.google.code.microlog4android.config.PropertyConfigurator;


public class MainActivity extends Activity implements View.OnClickListener{

    private TextView text;
    private CircleButton circleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActionBar().setTitle("Home");
        PropertyConfigurator.getConfigurator(this).configure();
        LogUtil.createLogFile();

        text = (TextView) findViewById(R.id.main_text);
        text.setText("Welcome");
        circleButton = (CircleButton) findViewById(R.id.main_circle_btn);
        circleButton.setOnClickListener(this);

        initPreferences();
    }

    public void initPreferences() {
        AppPreferences.prfs(this).edit().putBoolean(AppPreferences.Config.KEEP_LOGIN, true).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (view == circleButton) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }
}
