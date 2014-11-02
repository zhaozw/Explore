package com.explore.exapp.activity;

import android.os.Bundle;
import android.view.View;
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
        } else if (view == buttonRegister) {

        }
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
                        Gson gson2 = new Gson();
                        ToastUtil.showToast(LoginActivity.this, gson2.toJson(o));
                        gson2.toJson(o);
                    }
                })
                .execute();
    }
}
