package com.explore.exapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.explore.exapp.R;
import com.explore.exapp.base.BaseActivity;
import com.explore.exapp.base.util.HttpUtil;
import com.explore.exapp.base.util.LogUtil;
import com.explore.exapp.data.AppStatus;
import com.google.gson.Gson;

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
        map.putAll(AppStatus.getImeiImstInfo(this));
        Gson gson = new Gson();
        LogUtil.debug(gson.toJson(map));
        HttpUtil.request(this)
                .setUrl("")
                .setParaMeters("DATA", gson.toJson(map))
                .execute();
    }
}
