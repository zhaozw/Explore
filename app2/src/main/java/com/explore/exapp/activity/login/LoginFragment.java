package com.explore.exapp.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.explore.exapp.R;
import com.explore.exapp.activity.login.model.LoginInfo;
import com.explore.exapp.base.BaseFragment;
import com.explore.exapp.base.component.CustomProgress;
import com.explore.exapp.base.util.HttpUtil;
import com.explore.exapp.base.util.ToastUtil;
import com.explore.exapp.data.Api;
import com.explore.exapp.data.AppConstants;
import com.explore.exapp.data.AppPreferences;
import com.explore.exapp.data.AppStatus;
import com.google.gson.JsonObject;

import java.util.HashMap;

/**
 * Created by ryan on 14/11/6.
 */
public class LoginFragment extends BaseFragment implements View.OnClickListener {

    private Button buttonRegister;
    private Button buttonLogin;
    private EditText edtUserName;
    private EditText edtPassword;
    private TextView loginTitle;
    private LinearLayout loginInputLayout;

    public static LoginFragment newInstance() {
        LoginFragment loginFragment = new LoginFragment();
        return loginFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        buttonRegister = (Button) view.findViewById(R.id.login_register);
        buttonLogin = (Button) view.findViewById(R.id.login_btn);
        edtUserName = (EditText) view.findViewById(R.id.login_edt_username);
        edtPassword = (EditText) view.findViewById(R.id.login_edt_password);
        loginTitle = (TextView) view.findViewById(R.id.login_title);
        loginInputLayout = (LinearLayout) view.findViewById(R.id.login_layout_input);

        buttonLogin.setOnClickListener(this);
        buttonRegister.setOnClickListener(this);
        loginTitle.setOnLongClickListener(titleLongClickListener);

        return view;
    }

    @Override
    public String getFragmentName() {
        return "LoginFragment";
    }

    @Override
    public void onClick(View view) {
        if (view == buttonLogin) {
            if (buttonLogin.getText().equals(getString(R.string.login_btn_text))) {
                login();
            } else if (buttonLogin.getText().equals(getString(R.string.register_btn_text))){
                register();
            }
        } else if (view == buttonRegister) {
            setButtonMode();
        }
    }

    private View.OnLongClickListener titleLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            Intent intent = new Intent(attachActivity, InitSettingActivity.class);
            startActivity(intent);
            return true;
        }
    };

    private void setButtonMode() {
        buttonLogin.setClickable(false);
        final Animation anim1 = AnimationUtils.loadAnimation(attachActivity, R.anim.login_button_hide);
        final Animation anim2 = AnimationUtils.loadAnimation(attachActivity, R.anim.login_button_show);

        anim1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                edtPassword.setText("");
                if (buttonLogin.getText().equals(getString(R.string.login_btn_text))) {
                    buttonLogin.setText(R.string.register_btn_text);
                    buttonRegister.setText(R.string.login);
                    edtUserName.setHint(R.string.register_username_hint);
                    edtPassword.setHint(R.string.register_password_hint);
                } else if (buttonLogin.getText().equals(getString(R.string.register_btn_text))){
                    buttonLogin.setText(R.string.login_btn_text);
                    buttonRegister.setText(R.string.register);
                    edtUserName.setHint(R.string.login_username_hint);
                    edtPassword.setHint(R.string.login_password_hint);
                }
                loginInputLayout.startAnimation(anim2);
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
        loginInputLayout.startAnimation(anim1);
    }

    public void login() {
        String name = edtUserName.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (name.length() == 0 || password.length() == 0) {
            ToastUtil.showToast(attachActivity, R.string.login_username_empty);
            return;
        }

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("USERNAME", name);
        map.put("PASSWORD", password);
        HashMap<String, String> info = AppStatus.getImeiImstInfo(attachActivity);
        String imei = info.get("IMEI");
        String imsi = info.get("IMSI");
        if (imei.length() > 4 && imsi.length() > 4) {
            map.put("IMEI", imei.substring(imei.length() - 4, imei.length()));
            map.put("IMSI", imsi.substring(imsi.length() - 4, imsi.length()));
        } else {
            return;
        }
        CustomProgress.getInstance(attachActivity).show();
        HttpUtil.request(attachActivity)
                .setUrl(Api.getUrl(Api.LOGIN))
                .setParaMeters("DATA", gson.toJson(map))
                .setProgressFinishListener(new HttpUtil.OnProgressFinishListener() {
                    @Override
                    public void onProgressFinish() {
                        CustomProgress.getInstance(attachActivity).dismiss();
                    }
                })
                .setCompleteListener(new HttpUtil.OnCompleteListener() {
                    @Override
                    public void onComplete(JsonObject jsonObject) {
                        if (AppConstants.OK.equals(jsonObject.get(AppConstants.HTTP_STATUS).getAsString())) {
                            LoginInfo loginInfo = gson.fromJson(jsonObject, LoginInfo.class);
                            AppPreferences.initLoginPreferences(attachActivity, loginInfo);
                            ToastUtil.showToast(attachActivity, R.string.login_success);
                        }
                    }
                })
                .execute();
    }

    public void register() {
        String name = edtUserName.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (name.length() == 0 || password.length() == 0) {
            ToastUtil.showToast(attachActivity, R.string.login_username_empty);
            return;
        }

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("USERNAME", name);
        map.put("PASSWORD", password);
        HashMap<String, String> info = AppStatus.getImeiImstInfo(attachActivity);
        String imei = info.get("IMEI");
        String imsi = info.get("IMSI");
        if (imei.length() > 4 && imsi.length() > 4) {
            map.putAll(info);
        } else {
            return;
        }
        CustomProgress.getInstance(attachActivity).show();
        HttpUtil.request(attachActivity)
                .setUrl(Api.getUrl(Api.REGISTER))
                .setParaMeters("DATA", gson.toJson(map))
                .setProgressFinishListener(new HttpUtil.OnProgressFinishListener() {
                    @Override
                    public void onProgressFinish() {
                        CustomProgress.getInstance(attachActivity).dismiss();
                    }
                })
                .setCompleteListener(new HttpUtil.OnCompleteListener() {
                    @Override
                    public void onComplete(JsonObject jsonObject) {
                        if (AppConstants.OK.equals(jsonObject.get(AppConstants.HTTP_STATUS).getAsString())) {
                            ToastUtil.showToast(attachActivity, R.string.register_success);
                        }
                    }
                })
                .execute();

    }
}
