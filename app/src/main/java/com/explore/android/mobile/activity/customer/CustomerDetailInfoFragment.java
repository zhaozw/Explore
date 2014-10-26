package com.explore.android.mobile.activity.customer;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.explore.android.R;
import com.explore.android.core.util.ExStringUtil;
import com.explore.android.mobile.adapter.DetailInfoAdapter;
import com.explore.android.mobile.model.DetailInfo;

public class CustomerDetailInfoFragment extends Fragment{
	
	private ListView listView;
	private List<DetailInfo> detailList;
	private DetailInfoAdapter adapter;
	private CustomerDetailActivity mActivity;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_customer_details, container, false);
		initViews(view);
		initValues();
		initListener();
		return view;
	}
	
	private void initViews(View view){
		listView = (ListView) view.findViewById(R.id.frag_customer_detail_list);
	}
	
	private void initValues(){
		if(getActivity() != null && getActivity() instanceof CustomerDetailActivity){
			mActivity = (CustomerDetailActivity) getActivity();
			
			detailList = new ArrayList<DetailInfo>();
			adapter = new DetailInfoAdapter(getActivity(),detailList);
			listView.setAdapter(adapter);
			bindDetailData(mActivity.getResponseInfo());
			// loadDetailData();
		}else{
			mActivity = null;
		}
	}
	
	private void initListener(){
		
	}
	
	
	public void bindDetailData(String dataStr){
		try {
			JSONObject jsonObject = new JSONObject(dataStr);
			JSONObject json = jsonObject.getJSONObject("INFO");
			
			List<String> keyList = new ArrayList<String>();
			keyList.add("CUSTOMERNAMECN");
			keyList.add("CUSTOMERNAMECNS");
			keyList.add("CUSTOMERNAMEEN");
			keyList.add("CUSTOMERNAMEENS");
			keyList.add("COMPANYCODE");
			
			for (int i = 0; i < keyList.size(); i++) {
				String key = keyList.get(i);
				DetailInfo info = new DetailInfo();
				int strId = getResourceIdByKey(key);
				info.setContent(json.getString(key));
				if(ExStringUtil.isResNoBlank(info.getContent()) && strId != R.string.app_null){
					info.setTitle(key);
					info.setResourceId(getResourceIdByKey(key));
					detailList.add(info);
				}
			}
			adapter.notifyDataSetChanged();
		} catch (JSONException e) {
			e.printStackTrace();
			Toast.makeText(getActivity(), getResources().getString(R.string.msg_response_failed), Toast.LENGTH_SHORT).show();
		}
	}
	
	private int getResourceIdByKey(String name){
		
		if("CUSTOMERNAMECN".equals(name)){
			return R.string.cusmodify_title_namecn;
			
		} else if("CUSTOMERNAMECNS".equals(name)){
			return R.string.cusmodify_title_namecns;
			
		} else if("CUSTOMERNAMEEN".equals(name)){
			return R.string.cusmodify_title_nameen;
			
		} else if("CUSTOMERNAMEENS".equals(name)){
			return R.string.cusmodify_title_nameens;
			
		} else if("COMPANYCODE".equals(name)){
			return R.string.cusmodify_title_companycode;
			
		} else{
			return R.string.cusdetail_info;
		}
	}
}
