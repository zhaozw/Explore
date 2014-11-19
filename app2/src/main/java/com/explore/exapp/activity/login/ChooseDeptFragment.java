package com.explore.exapp.activity.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.explore.exapp.R;
import com.explore.exapp.activity.MainTabActivity;
import com.explore.exapp.activity.login.model.CustomerDept;
import com.explore.exapp.base.BaseFragment;
import com.explore.exapp.base.component.CustomProgress;
import com.explore.exapp.base.util.DialogUtil;
import com.explore.exapp.base.util.HttpUtil;
import com.explore.exapp.base.util.LogUtil;
import com.explore.exapp.data.Api;
import com.explore.exapp.data.AppConstants;
import com.explore.exapp.data.AppPreferences;
import com.explore.exapp.data.CommonData;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ryan on 14/11/19.
 */
public class ChooseDeptFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    private List<CustomerDept> deptList;
    private ListView deptListView;
    private DeptAdapter deptAdapter;

    public static ChooseDeptFragment newInstance() {
        ChooseDeptFragment chooseDeptFragment = new ChooseDeptFragment();
        return  chooseDeptFragment;
    }

    @Override
    public String getFragmentName() {
        return "ChooseDeptFragment";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_dept, container, false);
        deptList = new ArrayList<CustomerDept>();
        deptListView = (ListView) view.findViewById(R.id.choose_dept_list);
        deptAdapter = new DeptAdapter();
        deptListView.setAdapter(deptAdapter);
        deptListView.setOnItemClickListener(this);
        getDepts();
        return view;
    }

    private void getDepts() {
        Map<String, String> map = new HashMap<String, String>();
        map.putAll(CommonData.getBaseRequestMap(attachActivity));
        deptList = new ArrayList<CustomerDept>();
        CustomProgress.getInstance(attachActivity).show();
        HttpUtil.request(attachActivity)
                .setUrl(Api.getUrl(attachActivity, Api.GET_CUSTOMERDEPT))
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
                            LogUtil.debug(jsonObject.toString());
                            JsonArray json = jsonObject.get("CUSTOMERDEPTS").getAsJsonArray();
                            deptList = gson.fromJson(json, new TypeToken<List<CustomerDept>>(){}.getType());
                            deptAdapter.notifyDataSetChanged();
                        }
                    }
                }).execute();

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
        String msg_temp = getResources().getString(R.string.choose_dept_dialog_msg);
        String msg = String.format(msg_temp, deptList.get(i).getName());
        DialogUtil.showConfirmDialgo(attachActivity, R.string.choose_dept_dialog_title, msg, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i2) {
                AppPreferences.prfs(attachActivity).edit().putString(AppPreferences.Default.DEPT_NAME, deptList.get(i).getName()).apply();
                AppPreferences.prfs(attachActivity).edit().putString(AppPreferences.Default.DEPT_ID, deptList.get(i).getId()).apply();
                Intent intent = new Intent(attachActivity, MainTabActivity.class);
                startActivity(intent);
                attachActivity.setResult(AppConstants.RESULT_OK);
                attachActivity.finish();
            }
        });
    }

    private final class DeptAdapter extends BaseAdapter {

        private LayoutInflater layoutInflater;

        public DeptAdapter() {
            layoutInflater = LayoutInflater.from(attachActivity);
        }

        @Override
        public int getCount() {
            return deptList.size();
        }

        @Override
        public Object getItem(int i) {
            return deptList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            TextView deptName;
            if (view == null) {
                view = layoutInflater.inflate(R.layout.list_item_dept_adapter, viewGroup, false);
                deptName = (TextView) view.findViewById(R.id.list_item_dept_name);
                view.setTag(deptName);
            } else {
                deptName = (TextView) view.getTag();
            }

            deptName.setText(deptList.get(i).getName());
            return view;
        }
    }

}
