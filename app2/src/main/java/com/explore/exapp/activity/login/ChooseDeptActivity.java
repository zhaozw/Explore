package com.explore.exapp.activity.login;

import android.os.Bundle;

import com.explore.exapp.R;
import com.explore.exapp.base.BaseActivity;
import com.explore.exapp.base.SubActivity;

/**
 * Created by ryan on 14/11/19.
 */
public class ChooseDeptActivity extends SubActivity {

    public static final int REQUEST_SET_DEPT = 1;

    private ChooseDeptFragment chooseDeptFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_dept);

        chooseDeptFragment = ChooseDeptFragment.newInstance();
        getFragmentManager().beginTransaction().replace(R.id.frame_choose_dept, chooseDeptFragment).commit();
        mActionBar.setTitle(R.string.choose_dept_actionbar_title);
    }
}
