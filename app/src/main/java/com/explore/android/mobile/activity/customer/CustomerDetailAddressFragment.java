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
import android.widget.TextView;
import android.widget.Toast;

import com.explore.android.R;
import com.explore.android.core.util.ExStringUtil;
import com.explore.android.mobile.adapter.DetailInfoAdapter;
import com.explore.android.mobile.model.DetailInfo;

public class CustomerDetailAddressFragment extends Fragment{
	
	private TextView tv_noData;
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
		tv_noData = (TextView) view.findViewById(R.id.frag_customer_tv_nodata);
	}
	
	private void initValues(){
		if(getActivity() != null && getActivity() instanceof CustomerDetailActivity){
			mActivity = (CustomerDetailActivity) getActivity();
			
			detailList = new ArrayList<DetailInfo>();
			adapter = new DetailInfoAdapter(getActivity(),detailList);
			listView.setAdapter(adapter);
			bindDetailData(mActivity.getResponseAddress());
		}else{
			mActivity = null;
		}
	}
	
	private void initListener(){
		
	}
	
	
	public void bindDetailData(String dataStr){
		try {
			JSONObject jsonObject = new JSONObject(dataStr);
			if(jsonObject.isNull("ADDRESS")){
				tv_noData.setVisibility(View.VISIBLE);
				listView.setVisibility(View.GONE);
				tv_noData.setText(R.string.cusdetail_no_address_data);
			} else {
				JSONObject json = jsonObject.getJSONObject("ADDRESS");
				
				List<String> keyList = new ArrayList<String>();
				keyList.add("PROVINCE");
				keyList.add("CITY");
				keyList.add("AREA");
				keyList.add("ADDRESSTYPE");
				keyList.add("ADDRESS");
				keyList.add("POSTCODE");
				keyList.add("CONTACTNAME");
				keyList.add("MTEL");
				keyList.add("TEL");
				keyList.add("FAX");
				keyList.add("EMAIL");
				keyList.add("REMARKS");
				keyList.add("LAT");
				keyList.add("LNG");
				
				for (int i = 0; i < keyList.size(); i++) {
					String key = keyList.get(i);
					DetailInfo info = new DetailInfo();
					int strId = getResourceIdByKey(key);
					if("ADDRESSTYPE".equals(key)){
						if(json.getString(key).equals("TMP")){
							info.setContent(getResources().getString(R.string.customer_address_type_tmp));
						} else if(json.getString(key).equals("FIX")){
							info.setContent(getResources().getString(R.string.customer_address_type_fix));
							
						}
					} else {
						info.setContent(json.getString(key));
					}
					if(ExStringUtil.isResNoBlank(info.getContent()) && strId != R.string.app_null){
						info.setTitle(key);
						info.setResourceId(getResourceIdByKey(key));
						detailList.add(info);
					}
				}
				adapter.notifyDataSetChanged();
			}
		} catch (JSONException e) {
			e.printStackTrace();
			Toast.makeText(getActivity(), getResources().getString(R.string.msg_response_failed), Toast.LENGTH_SHORT).show();
		}
	}
	
	private int getResourceIdByKey(String name){
		
		if("PROVINCE".equals(name)){
			return R.string.cusmodify_title_address_province;
			
		} else if("CITY".equals(name)){
			return R.string.cusmodify_title_address_city;
			
		} else if("AREA".equals(name)){
			return R.string.cusmodify_title_address_area;
			
		} else if("ADDRESSTYPE".equals(name)){
			return R.string.cusmodify_title_address_addresstype;
			
		} else if("ADDRESS".equals(name)){
			return R.string.cusmodify_title_address_address;
			
		} else if("POSTCODE".equals(name)){
			return R.string.cusmodify_title_address_postcode;
			
		} else if("CONTACTNAME".equals(name)){
			return R.string.cusmodify_title_address_contactname;
			
		} else if("MTEL".equals(name)){
			return R.string.cusmodify_title_address_mtel;
			
		} else if("TEL".equals(name)){
			return R.string.cusmodify_title_address_tel;
			
		} else if("FAX".equals(name)){
			return R.string.cusmodify_title_address_fax;
			
		} else if("EMAIL".equals(name)){
			return R.string.cusmodify_title_address_email;
			
		} else if("REMARKS".equals(name)){
			return R.string.cusmodify_title_address_remarks;
			
		} else if("LNG".equals(name)){
			return R.string.cusmodify_title_address_lng;
			
		} else if("LAT".equals(name)){
			return R.string.cusmodify_title_address_lat;
			
		} else{
			return R.string.cusdetail_address_info;
		}
	}
}
