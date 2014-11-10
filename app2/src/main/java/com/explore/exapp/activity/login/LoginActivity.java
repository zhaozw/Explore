package com.explore.exapp.activity.login;

import android.app.FragmentManager;
import android.os.Bundle;

import com.explore.exapp.R;
import com.explore.exapp.base.BaseActivity;

/**
 * Created by ryan on 14/11/1.
 */
public class LoginActivity extends BaseActivity {

    private LoginFragment loginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginFragment = LoginFragment.newInstance();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_login, loginFragment).commit();
    }

}
