package com.explore.exapp.activity.more;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.explore.exapp.R;
import com.explore.exapp.base.BaseFragment;

/**
 * Created by ryan on 14/11/18.
 */
public class MoreFragment extends BaseFragment {

    public static MoreFragment newInstance() {
        MoreFragment moreFragment = new MoreFragment();
        return moreFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more, container, false);

        return view;
    }

    @Override
    public String getFragmentName() {
        return "MoreFragment";
    }
}
