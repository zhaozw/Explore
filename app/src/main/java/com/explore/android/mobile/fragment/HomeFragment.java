package com.explore.android.mobile.fragment;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.explore.android.R;
import com.explore.android.core.util.DateUtil;
import com.explore.android.mobile.activity.ExHomeActivity;
import com.explore.android.mobile.activity.message.ExMessageManager;
import com.explore.android.mobile.activity.message.MessageActivity;
import com.explore.android.mobile.adapter.HomeMessageAdapter;
import com.explore.android.mobile.common.SharePreferencesManager;
import com.explore.android.mobile.constants.AppConstant;

public class HomeFragment extends Fragment {

	private TextView tv_date_info;
	private TextView tv_username_info;
	private TextView tv_list_nodata;

	private ListView news_list;
	private HomeMessageAdapter adapter;

	public GridView gridView;
	private SharePreferencesManager preferences;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	public static HomeFragment newInstance(int navCode) {
		HomeFragment fragment = new HomeFragment();
		Bundle args = new Bundle();
        args.putInt(AppConstant.CURRENT_FRAGMENT_TITLE, navCode);
        fragment.setArguments(args);
		return fragment;
	}
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((ExHomeActivity) activity).onSectionAttached(
                getArguments().getInt(AppConstant.CURRENT_FRAGMENT_TITLE));
    }

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_home_layout, container, false);
		initViews(view);
		initValues();
		initListener();
		return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if(ExMessageManager.UPDATA){
			loadMessages();
		}
	}

	private void initViews(View view) {
		tv_date_info = (TextView) view.findViewById(R.id.frag_home_tv_date);
		tv_list_nodata = (TextView) view.findViewById(R.id.frag_home_content_list_nodata);
		tv_username_info = (TextView) view.findViewById(R.id.frag_home_tv_username);
		news_list = (ListView) view.findViewById(R.id.frag_home_content_list);
	}

	private void initValues(){
		preferences = SharePreferencesManager.getInstance(getActivity());
		loadMessages();
		tv_date_info.setText(DateUtil.DateFormatWithoutDayTime(new Date()));
		tv_username_info.setText(getString(R.string.welcome) + ", " + preferences.getRealName());
	}

	private void initListener() {
		news_list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,long arg3) {
				Intent intent = new Intent(getActivity(), MessageActivity.class);
				intent.putExtra("INFOID", ExMessageManager.msgList.get(position).getInfoId());
				startActivity(intent);
			}
		});
	}
	
	private void loadMessages(){
		adapter = null;
		adapter = new HomeMessageAdapter(ExMessageManager.msgList, getActivity());
		if(ExMessageManager.msgList.size() > 0){
			tv_list_nodata.setVisibility(View.GONE);
			news_list.setVisibility(View.VISIBLE);
		} else {
			tv_list_nodata.setVisibility(View.VISIBLE);
			news_list.setVisibility(View.GONE);
			
		}
		news_list.setAdapter(adapter);
		ExMessageManager.UPDATA = false;
	}
	
}
