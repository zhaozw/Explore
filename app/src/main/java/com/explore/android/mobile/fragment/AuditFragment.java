package com.explore.android.mobile.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.explore.android.R;
import com.explore.android.core.base.BaseHttpFragment;
import com.explore.android.mobile.activity.ExHomeActivity;
import com.explore.android.mobile.activity.audit.AuditDetailActivity;
import com.explore.android.mobile.adapter.AuditAdapter;
import com.explore.android.mobile.common.ExCacheManager;
import com.explore.android.mobile.common.SharePreferencesManager;
import com.explore.android.mobile.constants.AppConstant;
import com.explore.android.mobile.constants.AppStatus;
import com.explore.android.mobile.constants.RequestConstants;
import com.explore.android.mobile.constants.ResponseConstant;
import com.explore.android.mobile.data.request.ExRequest;
import com.explore.android.mobile.model.ExAudit;

public class AuditFragment extends BaseHttpFragment{
	
	private static final int REQUEST_AUDITLIST = 1;

	private RelativeLayout loading;
	private ListView listView;
	
	private List<ExAudit> auditList;
	private AuditAdapter adapter;
	private ExCacheManager cacheManager;
	
	public static AuditFragment newInstance(int navCode) {
		AuditFragment fragment = new AuditFragment();
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
	public void onResume() {
		if(AppStatus.AUDIT_LIST_UPDATE){
			loadAuditsFromServer();
			AppStatus.AUDIT_LIST_UPDATE = false;
		}
		super.onResume();
	}
	
	@Override
	protected int setLayoutId() {
		return R.layout.frag_audit_layout;
	}

	@Override
	public void initViews(View v) {
		listView = (ListView) v.findViewById(R.id.frag_audit_list);
		loading = (RelativeLayout) v.findViewById(R.id.app_loading_layout);
	}

	@Override
	public void initValues() {
		auditList = new ArrayList<ExAudit>();
		adapter = new AuditAdapter(getActivity(),auditList);
		listView.setAdapter(adapter);
		cacheManager = ExCacheManager.getInstance(getActivity());
		if( cacheManager.getAuditCache() == null){
			loadAuditsFromServer();
		}else{
			bindAuditData(cacheManager.getAuditCache());
		}
	}

	@Override
	public void initListener() {
		
		listView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), AuditDetailActivity.class);
				intent.putExtra("id", auditList.get(position).getAuditId());
				String auditType = auditList.get(position).getBillCode().substring(0, 3);
				intent.putExtra("type", auditType);
				startActivity(intent);
			}
		});
	}

	@Override
	public void showLoading() {
		loading.setVisibility(View.VISIBLE);
	}

	@Override
	public void dismissLoading() {
		loading.setVisibility(View.GONE);
	}

	@Override
	public void handlerResponse(String response, int what) {
		if(REQUEST_AUDITLIST == what){
			bindAuditData(response);
		}
	}
	
	private void loadAuditsFromServer(){
		ExRequest request = new ExRequest();
		SharePreferencesManager preferences = SharePreferencesManager.getInstance(getActivity());
		request.setUserId(preferences.getUserId());
		request.setToken(preferences.getTokenLast8());
		asynDataRequest(RequestConstants.AUDIT_LIST, request.toJsonString(), REQUEST_AUDITLIST);
	}
	
	private void bindAuditData(String dataStr){
		try{
			JSONObject json = new JSONObject(dataStr);
			if(ResponseConstant.OK.equals(json.get(ResponseConstant.STATUS))){
				cacheManager.addAuditCache(dataStr);
				JSONArray jsonArray = json.getJSONArray("AUDITLIST");
				int length = jsonArray.length();
				auditList.clear();
				for(int i = 0; i < length; i++){
					ExAudit audit = new ExAudit();
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					audit.setAuditId(Integer.parseInt(jsonObject.getString("auditId")));
					audit.setCreateById(Integer.parseInt(jsonObject.getString("auditId")));
					audit.setCreateByName(jsonObject.getString("createBy"));
					audit.setCreateByTime(jsonObject.getString("createByTime"));
					audit.setBillCode(jsonObject.getString("billCode"));
					audit.setBillType(jsonObject.getString("billName"));
					audit.setDept(jsonObject.getString("deptName"));
					audit.setSts(jsonObject.getString("stsName"));
					audit.setContent(jsonObject.getString("content"));
					auditList.add(audit);
				}
				adapter.notifyDataSetChanged();
				listView.setVisibility(View.VISIBLE);
			} else {
				showToast(R.string.msg_response_failed);
			}
		} catch(JSONException e){
			e.printStackTrace();
			showToast(R.string.msg_response_failed);
		}
	}
}
