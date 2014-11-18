package com.explore.exapp.activity.product;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.explore.exapp.R;
import com.explore.exapp.base.BaseFragment;

/**
 * Created by ryan on 14/11/12.
 */
public class ProductFragment extends BaseFragment {

    public static ProductFragment newInstance() {
        ProductFragment productFragment = new ProductFragment();
        return productFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        return view;
    }

    @Override
    public String getFragmentName() {
        return "ProductFragment";
    }
}
