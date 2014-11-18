package com.explore.exapp.activity.logistics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.explore.exapp.R;
import com.explore.exapp.base.BaseFragment;

/**
 * Created by ryan on 14/11/12.
 */
public class LogisticsFragment extends BaseFragment {

    public static LogisticsFragment newInstance() {
        LogisticsFragment logisticsFragment = new LogisticsFragment();
        return logisticsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_logistics, container, false);

        return view;
    }

    @Override
    public String getFragmentName() {
        return "LogisticesFragment";
    }
}
