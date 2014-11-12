package com.explore.exapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.explore.exapp.activity.MainTabActivity;
import com.explore.exapp.activity.guide.GuideActivity;
import com.explore.exapp.activity.login.LoginActivity;
import com.explore.exapp.base.component.CircleButton;
import com.explore.exapp.base.component.RoundProgressBar;
import com.explore.exapp.base.util.LogUtil;
import com.explore.exapp.data.AppPreferences;
import com.google.code.microlog4android.config.PropertyConfigurator;


public class MainActivity extends Activity implements View.OnClickListener{

    private RoundProgressBar mRoundProgressBar1, mRoundProgressBar2 ,mRoundProgressBar3, mRoundProgressBar4, mRoundProgressBar5;
    private int progress = 0;

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

        mRoundProgressBar1 = (RoundProgressBar) findViewById(R.id.roundProgressBar1);

        ((Button)findViewById(R.id.button1)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        while(progress <= 1000){
                            progress += 2;

                            System.out.println(progress);

                            mRoundProgressBar1.setProgress(progress);

                            try {
                                Thread.sleep(5);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }).start();
            }
        });
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
            // Intent intent = new Intent(this, MainTabActivity.class);
            Intent intent = new Intent(this, GuideActivity.class);
            startActivity(intent);
        }
    }
}
