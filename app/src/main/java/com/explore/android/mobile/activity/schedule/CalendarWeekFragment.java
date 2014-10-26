package com.explore.android.mobile.activity.schedule;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.explore.android.R;
import com.explore.android.core.base.BaseAsynTask;
import com.explore.android.core.base.BaseAsynTask.TaskOnPostExecuteListener;
import com.explore.android.core.base.BaseAsynTask.TaskResult;
import com.explore.android.core.http.AsynDataTask;
import com.explore.android.core.model.ExResponse;
import com.explore.android.core.util.DateUtil;
import com.explore.android.mobile.adapter.ScheduleListAdapter;
import com.explore.android.mobile.common.ExCacheManager;
import com.explore.android.mobile.common.SharePreferencesManager;
import com.explore.android.mobile.constants.ErrorConstants;
import com.explore.android.mobile.constants.RequestConstants;
import com.explore.android.mobile.constants.ResponseConstant;
import com.explore.android.mobile.data.request.ScheduleRequest;
import com.explore.android.mobile.model.Schedule;

public class CalendarWeekFragment extends Fragment {

	private RadioGroup dayGroup;
	private RadioButton[] weekDays = new RadioButton[7];
	private ListView listView;
	private RelativeLayout loading;
	
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
		
		View view = inflater.inflate(R.layout.frag_calendar_week_layout, container, false);
		initViews(view);
		initValues();
		initListener();
		return view;
	}
	
	public void initValues() {

		cacheManager = ExCacheManager.getInstance(getActivity());
		
		HashMap<String, Date> dateMap = DateUtil.getWeekDays(new Date());
		Calendar cal = Calendar.getInstance();

		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH) + 1;
		date = cal.get(Calendar.DATE);

		for (int i = 0; i < weekDays.length; i++) {
			cal.setTime(dateMap.get("day" + i));
			if (cal.get(Calendar.DATE) == date) {
				weekDays[i].setChecked(true);
			}
			weekDays[i].setText(cal.get(Calendar.DATE) + "");
		}

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
	
	private void initViews(View v) {

		loading = (RelativeLayout) v.findViewById(R.id.app_loading_layout);
		
		listView = (ListView) v.findViewById(R.id.frag_schedule_list);

		dayGroup = (RadioGroup) v.findViewById(R.id.frag_schedule_daygroup);
		weekDays[0] = (RadioButton) v.findViewById(R.id.frag_schedule_weekdate1);
		weekDays[1] = (RadioButton) v.findViewById(R.id.frag_schedule_weekdate2);
		weekDays[2] = (RadioButton) v.findViewById(R.id.frag_schedule_weekdate3);
		weekDays[3] = (RadioButton) v.findViewById(R.id.frag_schedule_weekdate4);
		weekDays[4] = (RadioButton) v.findViewById(R.id.frag_schedule_weekdate5);
		weekDays[5] = (RadioButton) v.findViewById(R.id.frag_schedule_weekdate6);
		weekDays[6] = (RadioButton) v.findViewById(R.id.frag_schedule_weekdate7);
	}
	
	private void initListener(){
		dayGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checked) {

				for (int i = 0; i < weekDays.length; i++) {

					if (weekDays[i].getId() == checked) {
						date = Integer.parseInt(weekDays[i].getText().toString());
						
						String data = cacheManager.getScheduleCache(year, month, date);
						if(data == null){
							getDayScheduleFromServer(year, month, date);
						}else{
							try {
								bindScheduleData(data);
							} catch (JSONException e) {
								Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.msg_response_failed), Toast.LENGTH_SHORT).show();
								cacheManager.clearScheduleCache(year, month, date);
							}
						}
						break;
					}
				}
			}
		});
		
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
						
						JSONObject json;
						try {
							json = new JSONObject(response.getResMessage());
							if (ResponseConstant.OK.equals(json.get(ResponseConstant.STATUS))) {
								cacheManager.addScheduleCache(year, month, date, response.getResMessage());
								bindScheduleData(response.getResMessage());
								
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
		//scheList = DataSource.getSchedulesList(json);
		adapter = new ScheduleListAdapter(getActivity(),scheList);
		listView.setAdapter(adapter);
	}
}
