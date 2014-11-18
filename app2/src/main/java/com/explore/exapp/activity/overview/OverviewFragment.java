package com.explore.exapp.activity.overview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.explore.exapp.R;
import com.explore.exapp.base.BaseFragment;
import com.explore.exapp.base.util.LogUtil;

/**
 * Created by ryan on 14/11/12.
 */
public class OverviewFragment extends BaseFragment implements View.OnClickListener {

    private RelativeLayout table1Item1;
    private RelativeLayout table1Item2;
    private RelativeLayout table1Item3;
    private RelativeLayout table1Item4;

    public static OverviewFragment newInstance() {
        OverviewFragment overviewFragment = new OverviewFragment();
        return overviewFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_overview, container, false);

        table1Item1 = (RelativeLayout) view.findViewById(R.id.overview_table1_item1);
        table1Item2 = (RelativeLayout) view.findViewById(R.id.overview_table1_item2);
        table1Item3 = (RelativeLayout) view.findViewById(R.id.overview_table1_item3);
        table1Item4 = (RelativeLayout) view.findViewById(R.id.overview_table1_item4);

        table1Item1.setOnClickListener(this);
        table1Item2.setOnClickListener(this);
        table1Item3.setOnClickListener(this);
        table1Item4.setOnClickListener(this);

        return view;
    }

    @Override
    public String getFragmentName() {
        return "OverviewFragment";
    }

    @Override
    public void onClick(View view) {

    }
}
