package com.explore.exapp.base;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

import com.explore.exapp.base.component.CustomProgress;
import com.google.gson.Gson;

/**
 * Created by ryan on 14/11/1.
 */
public abstract class BaseFragment extends Fragment {

    protected Activity attachActivity;
    protected Gson gson;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.attachActivity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gson = new Gson();
    }

    abstract public String getFragmentName();


}
