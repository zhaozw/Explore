package com.explore.exapp.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;
import com.explore.exapp.R;
import com.explore.exapp.base.BaseActivity;
import com.explore.exapp.base.util.Base64Coder;
import com.explore.exapp.base.util.HttpUtil;
import com.explore.exapp.base.util.LogUtil;
import com.explore.exapp.base.util.ToastUtil;
import com.explore.exapp.data.Api;
import com.explore.exapp.data.AppStatus;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.HashMap;

/**
 * Created by ryan on 14/11/1.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener{

    private Button buttonRegister;
    private Button buttonLogin;
    private EditText edtUserName;
    private EditText edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buttonRegister = (Button) findViewById(R.id.login_register);
        buttonLogin = (Button) findViewById(R.id.login_btn);
        edtUserName = (EditText) findViewById(R.id.login_edt_username);
        edtPassword = (EditText) findViewById(R.id.login_edt_password);

        buttonLogin.setOnClickListener(this);
        buttonRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == buttonLogin) {
            login();
            if (buttonLogin.getText().equals(getString(R.string.login))) {
                login();
            } else if (buttonLogin.getText().equals(getString(R.string.login_register))){
                register();
            }
        } else if (view == buttonRegister) {
            setButtonMode();
        }
    }

    private void setButtonMode() {
        buttonLogin.setClickable(false);
        final Animation anim1 = AnimationUtils.loadAnimation(this, R.anim.login_button_hide);
        final Animation anim2 = AnimationUtils.loadAnimation(this, R.anim.login_button_show);

        anim1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (buttonLogin.getText().equals(getString(R.string.login))) {
                    buttonLogin.setText(R.string.login_register);
                    buttonRegister.setText(R.string.login);
                    Drawable img = getResources().getDrawable(R.drawable.arrow_left);
                    img.setBounds(0, 0, img.getIntrinsicWidth(), img.getIntrinsicHeight());
                    buttonRegister.setCompoundDrawables(img, null, null, null);
                    buttonRegister.setCompoundDrawablePadding(5);
                } else if (buttonLogin.getText().equals(getString(R.string.login_register))){
                    buttonLogin.setText(R.string.login);
                    buttonRegister.setText(R.string.login_register);
                    Drawable img = getResources().getDrawable(R.drawable.arrow_right);
                    img.setBounds(0, 0, img.getIntrinsicWidth(), img.getIntrinsicHeight());
                    buttonRegister.setCompoundDrawables(null, null, img, null);
                    buttonRegister.setCompoundDrawablePadding(5);
                }
                buttonLogin.startAnimation(anim2);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        anim2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                buttonLogin.setClickable(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        buttonLogin.startAnimation(anim1);
    }

    public void login() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("USERNAME", edtUserName.getText().toString().trim());
        map.put("PASSWORD", edtPassword.getText().toString().trim());
        HashMap<String, String> info = AppStatus.getImeiImstInfo(this);
        String imei = info.get("IMEI");
        String imsi = info.get("IMSI");
        if (imei.length() <= 4 || imsi.length() <= 4) {
            ToastUtil.showToast(this, R.string.phone_error_imsi_info);
        } else {
            map.put("IMEI", imei.substring(imei.length() - 4, imei.length()));
            map.put("IMSI", imsi.substring(imsi.length() - 4, imsi.length()));
        }

        final Gson gson = new Gson();
        LogUtil.debug(gson.toJson(map));
        HttpUtil.request(this)
                .setUrl(Api.getUrl(Api.LOGIN))
                .setParaMeters("DATA", gson.toJson(map))
                .setCompleteListener(new Response.Listener() {
                    @Override
                    public void onResponse(Object o) {
                        ToastUtil.showToast(LoginActivity.this, gson.toJson(o));
                        gson.toJson(o);
                    }
                })
                .execute();
    }

    public void register() {
        ToastUtil.showToast(this, "Register");
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("USERNAME", edtUserName.getText().toString().trim());
        map.put("PASSWORD", edtPassword.getText().toString().trim());
        map.putAll(AppStatus.getImeiImstInfo(this));
    }
}
