package com.explore.android.mobile.activity.out;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.explore.android.R;
import com.explore.android.core.base.BaseAsynTask.TaskOnPostExecuteListener;
import com.explore.android.core.base.BaseAsynTask.TaskResult;
import com.explore.android.core.http.AsynDataTask;
import com.explore.android.core.model.ExResponse;
import com.explore.android.mobile.adapter.CustomExpandListAdapter;
import com.explore.android.mobile.common.SharePreferencesManager;
import com.explore.android.mobile.constants.ErrorConstants;
import com.explore.android.mobile.constants.RequestConstants;
import com.explore.android.mobile.constants.ResponseConstant;
import com.explore.android.mobile.data.request.OutDetailRequest;
import com.explore.android.mobile.model.DetailInfo;
import com.explore.android.mobile.model.ExpandListData;

/**
 * 审核流程
 * @author Ryan
 */
public class OutDetailAuditFragment extends Fragment{

	private RelativeLayout loading;
	private ExpandableListView audit_exListView;
	private LinearLayout list_layout;
	private TextView tv_nodata;
	
	private AsynDataTask outAuditTask;
	private List<ExpandListData> outAuditList;
	private CustomExpandListAdapter outAuditAdapter;
	
	private OutDetailActivity mActivity;
	
	private String outId;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_outinfo_audit_layout, container, false);
		initViews(view);
		initValues();
		return view;
	}
	
	private void initViews(View view){
		loading = (RelativeLayout) view.findViewById(R.id.app_loading_layout);
		audit_exListView = (ExpandableListView) view.findViewById(R.id.frag_outinfo_audit_exlist);
		list_layout = (LinearLayout) view.findViewById(R.id.frag_outinfo_audit_list_layout); 
		tv_nodata = (TextView) view.findViewById(R.id.frag_outinfo_audit_nodata);
		
		audit_exListView.setGroupIndicator(null);
	}
	
	private void initValues(){
		
		if(getActivity() != null && getActivity() instanceof OutDetailActivity){
			mActivity = (OutDetailActivity) getActivity();
			outAuditList = new ArrayList<ExpandListData>();
			outId = mActivity.getOutId();
			loadOutAuditInfo();
		}else{
			mActivity = null;
		}
	}
	
	private void loadOutAuditInfo(){
		loading.setVisibility(View.VISIBLE);
		OutDetailRequest request = new OutDetailRequest();
		
		outAuditTask = new AsynDataTask();
		outAuditTask.setTaskOnPostExecuteListener(new TaskOnPostExecuteListener<ExResponse>() {
					@Override
					public void onPostExecute( TaskResult<? extends ExResponse> result) {
						loading.setVisibility(View.GONE);
						ExResponse response = result.Value;
						if (ErrorConstants.VALUE_NULL.equals(response.getResMessage())) {
							if (response.getResCode() == -3) {
								Toast.makeText(getActivity(), getResources().getString(R.string.msg_request_timeout), Toast.LENGTH_SHORT).show();
							} else {
								Toast.makeText(getActivity(), getResources().getString(R.string.msg_server_nores), Toast.LENGTH_LONG).show();
							}
							return;
						} else {
							Log.e(null, response.getResMessage());
							bindOutAuditData(response.getResMessage());
						}
					}
				});

		SharePreferencesManager preferences = SharePreferencesManager.getInstance(getActivity());

		request.setUserId(preferences.getUserId());
		request.setToken(preferences.getTokenLast8());
		request.setOutId(outId);
		outAuditTask.execute(preferences.getHttpUrl(), RequestConstants.OUT_GET_AUDITINFO, request.toJsonString());
	}
	
	@SuppressWarnings("unchecked")
	private boolean bindOutAuditData(String dataStr){
		try {
			JSONObject jsonObject = new JSONObject(dataStr);
			if (ResponseConstant.OK.equals(jsonObject.get(ResponseConstant.STATUS))) {
				
				JSONArray jsonArray = jsonObject.getJSONArray("OUTTASKLINES");
				
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject json = jsonArray.getJSONObject(i);
					Iterator<String> jsonKeys = json.keys();
					
					ExpandListData auditInfo = new ExpandListData();
					List<DetailInfo> infoList = new ArrayList<DetailInfo>();
					
					while (jsonKeys.hasNext()) {
						
						DetailInfo info = new DetailInfo();
						String key = jsonKeys.next();
						
						if("TASKLINENAME".equals(key)){
							auditInfo.setTitle1(json.getString(key));
						}
						
						info.setTitle(key);
						info.setResourceId(getResourceIdByKey(key));
						info.setContent(json.getString(key));
						if(!"null".equals(info.getContent())){
							infoList.add(info);
						}
					}
					auditInfo.setDetailList(infoList);
					
					outAuditList.add(auditInfo);
					
				}
				
				outAuditAdapter = new CustomExpandListAdapter(outAuditList, getActivity());
				audit_exListView.setAdapter(outAuditAdapter);
				
				if(outAuditList.size() > 0){
					list_layout.setVisibility(View.VISIBLE);
					tv_nodata.setVisibility(View.GONE);
				} else {
					list_layout.setVisibility(View.GONE);
					tv_nodata.setVisibility(View.VISIBLE);
				}
				
				return true;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private int getResourceIdByKey(String name){
		
		if("TASKLINENAME".equals(name)){
			return R.string.audit_taskline_name;
			
		} else if("AUDITBYNAME".equals(name)){
			return R.string.audit_taskline_auditbyname;
			
		} else if("AUDITCONTENT".equals(name)){
			return R.string.audit_taskline_auditcontent;
			
		} else if("TIMEOUTSTS".equals(name)){
			return R.string.audit_taskline_timeoutsts;
			
		} else if("TIMEOUT".equals(name)){
			return R.string.audit_taskline_timeout;
			
		} else if("AUDITSTS".equals(name)){
			return R.string.audit_taskline_auditsts;
			
		} else {
			return R.string.audit_taskline_title;
		}
		
	}
}
