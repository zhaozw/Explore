package com.explore.exapp.activity.overview;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.explore.exapp.R;
import com.explore.exapp.activity.login.ChooseDeptActivity;
import com.explore.exapp.base.BaseFragment;
import com.explore.exapp.base.util.LogUtil;
import com.explore.exapp.data.AppConstants;
import com.explore.exapp.data.AppPreferences;

/**
 * Created by ryan on 14/11/12.
 */
public class OverviewFragment extends BaseFragment implements View.OnClickListener {

    private RelativeLayout table1Item1;
    private RelativeLayout table1Item2;
    private RelativeLayout table1Item3;
    private RelativeLayout table1Item4;
    private RelativeLayout table2Item1;
    private RelativeLayout table2Item2;

    private TextView realNameText;
    private TextView defaultDeptText;
    private TextView defaultDeptSetText;

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
        table2Item1 = (RelativeLayout) view.findViewById(R.id.overview_table2_item1);
        table2Item2 = (RelativeLayout) view.findViewById(R.id.overview_table2_item2);

        realNameText = (TextView) view.findViewById(R.id.overview_real_name);
        defaultDeptText = (TextView) view.findViewById(R.id.overview_default_dept_name);
        defaultDeptSetText = (TextView) view.findViewById(R.id.overview_default_dept_name_edit);

        table1Item1.setOnClickListener(this);
        table1Item2.setOnClickListener(this);
        table1Item3.setOnClickListener(this);
        table1Item4.setOnClickListener(this);
        table2Item1.setOnClickListener(this);
        table2Item2.setOnClickListener(this);

        String realName = AppPreferences.prfs(attachActivity).getString(AppPreferences.Login.REALNAME, "User");
        realNameText.setText(realName);
        checkDeptInit();

        return view;
    }

    private void checkDeptInit() {
        String deptInitName = AppPreferences.prfs(attachActivity).getString(AppPreferences.Default.DEPT_NAME, "");
        if (deptInitName.equals("")) {
            Intent intent = new Intent(attachActivity, ChooseDeptActivity.class);
            attachActivity.startActivityForResult(intent, 1);
        } else {
            defaultDeptText.setText(deptInitName);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == AppConstants.RESULT_OK) {
            if (requestCode == ChooseDeptActivity.REQUEST_SET_DEPT) {
                String deptInitName = AppPreferences.prfs(attachActivity).getString(AppPreferences.Default.DEPT_NAME, "");
                if (deptInitName.equals("")) {
                    defaultDeptSetText.setVisibility(View.VISIBLE);
                } else {
                    defaultDeptSetText.setVisibility(View.GONE);
                }
                defaultDeptText.setText(deptInitName);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public String getFragmentName() {
        return "OverviewFragment";
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.overview_default_dept_name_edit:
                Intent intent = new Intent(attachActivity, ChooseDeptActivity.class);
                startActivityForResult(intent, 1);
                break;
        }
    }
}
