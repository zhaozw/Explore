package com.explore.android.mobile.activity.schedule;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.explore.android.R;
import com.explore.android.core.base.BaseAsynTask;
import com.explore.android.core.base.BaseAsynTask.TaskOnPostExecuteListener;
import com.explore.android.core.base.BaseAsynTask.TaskResult;
import com.explore.android.core.http.AsynDataTask;
import com.explore.android.core.model.ExResponse;
import com.explore.android.mobile.adapter.ScheduleListAdapter;
import com.explore.android.mobile.common.ExCacheManager;
import com.explore.android.mobile.common.SharePreferencesManager;
import com.explore.android.mobile.constants.ErrorConstants;
import com.explore.android.mobile.constants.RequestConstants;
import com.explore.android.mobile.constants.ResponseConstant;
import com.explore.android.mobile.data.request.ScheduleRequest;
import com.explore.android.mobile.model.Schedule;

public class CalendarDayFragment extends Fragment {
	
	private RelativeLayout loading;
	private ListView listView;
	
	private List<Schedule> scheList = new ArrayList<Schedule>();
	private ScheduleListAdapter adapter;
	
	private ExCacheManager cacheManager;
	
	private int year;
	private int month;
	private int date;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.frag_calendar_day_layout, container, false);
		initViews(view);
		initValues();
		initListener();
		return view;
	}

	private void initViews(View v){
		loading = (RelativeLayout) v.findViewById(R.id.app_loading_layout);
		listView = (ListView) v.findViewById(R.id.schedule_day_list);
	}
	
	private void initValues(){
		
		cacheManager = ExCacheManager.getInstance(getActivity());
		
		Calendar cal = Calendar.getInstance();

		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH) + 1;
		date = cal.get(Calendar.DATE);
		
		if(cacheManager.getScheduleCache(year, month, date) == null){
			getDayScheduleFromServer(year, month, date);
		}else{
			try {
				bindScheduleData(cacheManager.getScheduleCache(year, month, date));
			} catch (JSONException e) {
				Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.msg_response_failed), Toast.LENGTH_SHORT).show();
				cacheManager.clearScheduleCache(year, month, date);
			}
		}
	}

	private void initListener() {
		
	}

	@SuppressWarnings("unused")
	private void switchScheduleMode(int mode, HashMap<String, Integer> dateInfo) {
		if (getActivity() == null) {
			return;
		}
		if (getActivity() instanceof CalendarActivity) {
			CalendarActivity fca = (CalendarActivity) getActivity();
			fca.switchScheduleMode(mode, dateInfo);
		}
	}
	
	private void getDayScheduleFromServer(int y, int m, int d) {

		ScheduleRequest request = new ScheduleRequest();
		loading.setVisibility(View.VISIBLE);
		AsynDataTask scheTask = new AsynDataTask();

		scheTask.setTaskOnPostExecuteListener(new TaskOnPostExecuteListener<ExResponse>() {
			public void onPostExecute(TaskResult<? extends ExResponse> result) {
				if (result != null && result.Status == BaseAsynTask.STATUS_SUCCESS) {
					loading.setVisibility(View.GONE);
					ExResponse response = result.Value;
					if(ErrorConstants.VALUE_NULL.equals(response.getResMessage())){
						if (response.getResCode() == -3) {
							Toast.makeText(getActivity(), getResources().getString(R.string.msg_request_timeout), Toast.LENGTH_SHORT).show();
						}else{
							Toast.makeText(getActivity(), getResources().getString(R.string.msg_server_nores), Toast.LENGTH_LONG).show();
						}
						return;
					} else {
						
						try {
							JSONObject json = new JSONObject(response.getResMessage());
							if (ResponseConstant.OK.equals(json.get(ResponseConstant.STATUS))) {
								cacheManager.addScheduleCache(year, month, date, response.getResMessage());
							} else {
								Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.msg_response_failed), Toast.LENGTH_SHORT).show();
							}
						} catch (JSONException e) {
							e.printStackTrace();
							Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.msg_response_failed), Toast.LENGTH_SHORT).show();
						}
					}
				}
			}

		});
		
		SharePreferencesManager preferences = SharePreferencesManager.getInstance(getActivity());
		request.setUserId(preferences.getUserId());
		request.setToken(preferences.getTokenLast8());
		scheTask.execute(preferences.getHttpUrl(),RequestConstants.SCHEDULE,request.toJsonString());
	}
	
	private void bindScheduleData(String resStr) throws JSONException{
		JSONObject json = new JSONObject(resStr);
		// scheList = DataSource.getSchedulesList(json);
		adapter = new ScheduleListAdapter(getActivity(),scheList);
		listView.setAdapter(adapter);
	}
}
