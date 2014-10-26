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
import com.explore.android.mobile.data.ValueToResource;
import com.explore.android.mobile.model.DetailInfo;

public class CustomerDetailDeptFragment extends Fragment{
	
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
			bindDetailData(mActivity.getResponseDept());
		}else{
			mActivity = null;
		}
	}
	
	private void initListener(){
		
	}
	
	
	public void bindDetailData(String dataStr){
		try {
			JSONObject jsonObject = new JSONObject(dataStr);
			JSONObject json = jsonObject.getJSONObject("DEPT");
			
			List<String> keyList = new ArrayList<String>();
			keyList.add("CUSTOMERDEPTNAME");
			keyList.add("AMOUNTMONEY");
			keyList.add("KATYPENAME");
			keyList.add("CUSTOMERAREANAME");
			keyList.add("DZTYPE");
			keyList.add("ZQTYPE");
			keyList.add("GDDAY");
			keyList.add("ZQDAY");
			keyList.add("ZQMONTHM");
			keyList.add("ISVAT");
			keyList.add("ISDH");
			keyList.add("DHRATE");
			keyList.add("VATTONAME");
			
			for (int i = 0; i < keyList.size(); i++) {
				String key = keyList.get(i);
				DetailInfo info = new DetailInfo();
				int strId = getResourceIdByKey(key);
				info.setContent(json.getString(key));
				if("ISVAT".equals(key) || "ISDH".equals(key) || "DZTYPE".equals(key) || "ZQTYPE".equals(key)){
					info.setContent(getResources().getString(ValueToResource.getCustomerDeptValue(json.getString(key))));
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
		} catch (JSONException e) {
			e.printStackTrace();
			Toast.makeText(getActivity(), getResources().getString(R.string.msg_response_failed), Toast.LENGTH_SHORT).show();
		}
	}
	
	private int getResourceIdByKey(String name){
		
		if("CUSTOMERDEPTNAME".equals(name)){
			return R.string.cusmodify_title_dept_cusdept;
			
		} else if("AMOUNTMONEY".equals(name)){
			return R.string.cusmodify_title_dept_amountmoney;
			
		} else if("KATYPE".equals(name)){
			return R.string.cusmodify_title_dept_katype;
			
		} else if("CUSTOMERAREANAME".equals(name)){
			return R.string.cusmodify_title_dept_cusarea;
			
		} else if("DZTYPE".equals(name)){
			return R.string.cusmodify_title_dept_dztype;
			
		} else if("ZQTYPE".equals(name)){
			return R.string.cusmodify_title_dept_zqtype;
			
		} else if("ZQDAY".equals(name)){
			return R.string.cusmodify_title_dept_zqday;
			
		} else if("GDDAY".equals(name)){
			return R.string.cusmodify_title_dept_gdday;
			
		} else if("ISVAT".equals(name)){
			return R.string.cusmodify_title_dept_isvat;
			
		} else if("ISDH".equals(name)){
			return R.string.cusmodify_title_dept_isdh;
			
		} else if("DHRATE".equals(name)){
			return R.string.cusmodify_title_dept_dhrate;
			
		} else if("VATTONAME".equals(name)){
			return R.string.cusmodify_title_dept_vatto;
			
		} else{
			return R.string.cusdetail_info;
		}
	}
	
}
