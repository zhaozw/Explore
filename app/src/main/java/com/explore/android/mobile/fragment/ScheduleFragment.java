package com.explore.android.mobile.fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.explore.android.R;
import com.explore.android.core.base.BaseHttpFragment;
import com.explore.android.core.util.DateUtil;
import com.explore.android.mobile.activity.ExHomeActivity;
import com.explore.android.mobile.adapter.ScheduleListAdapter;
import com.explore.android.mobile.common.ExCacheManager;
import com.explore.android.mobile.constants.AppConstant;
import com.explore.android.mobile.constants.RequestConstants;
import com.explore.android.mobile.constants.ResponseConstant;
import com.explore.android.mobile.data.request.ScheduleRequest;
import com.explore.android.mobile.model.Schedule;

public class ScheduleFragment extends BaseHttpFragment {
	
	private static final int REQUEST_SCHEDULE = 1;

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
	
	public static ScheduleFragment newInstance(int navCode) {
		ScheduleFragment fragment = new ScheduleFragment();
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
	protected int setLayoutId() {
		return R.layout.frag_schedule_layout;
	}

	@Override
	public void initViews(View v) {
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

	@Override
	public void initListener() {
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
							bindScheduleData(data, true);
						}
						break;
					}
				}
			}
		});
		
		/*
		calendarBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getActivity(),CalendarActivity.class);
				startActivity(intent);
			}
			
		});*/
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
		if(REQUEST_SCHEDULE == what){
			bindScheduleData(response, false);
		}
	}

	public void initValues() {
		HashMap<String, Date> dateMap = DateUtil.getWeekDays(new Date());
		Calendar cal = Calendar.getInstance();

		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH) + 1;
		date = cal.get(Calendar.DATE);

		//titleMonth.setText(DateUtil.getMonthName(month));

		for (int i = 0; i < weekDays.length; i++) {
			cal.setTime(dateMap.get("day" + i));
			if (cal.get(Calendar.DATE) == date) {
				weekDays[i].setChecked(true);
			}
			weekDays[i].setText(cal.get(Calendar.DATE) + "");
		}
		
		cacheManager = ExCacheManager.getInstance(getActivity());
		if(cacheManager.getScheduleCache(year, month, date) == null){
			getDayScheduleFromServer(year, month, date);
		}else{
			bindScheduleData(cacheManager.getScheduleCache(year, month, date), true);
		}
	}

	private void getDayScheduleFromServer(int y, int m, int d) {
		ScheduleRequest request = new ScheduleRequest();
		request.setUserId(getPreferences().getUserId());
		request.setToken(getPreferences().getTokenLast8());
		request.setYear(y+"");
		request.setMonth(m+"");
		request.setDate(d+"");
		asynDataRequest(RequestConstants.SCHEDULE, request.toJsonString(), REQUEST_SCHEDULE);
	}
	
	private void bindScheduleData(String resStr, boolean isFromCache){
		try {
			JSONObject json = new JSONObject(resStr);
			if (ResponseConstant.OK.equals(json.get(ResponseConstant.STATUS))) {
				if(!isFromCache){
					cacheManager.addScheduleCache(year, month, date, resStr);
				}
				
				JSONArray jsonArray = json.getJSONArray("SCHEDULES");
				int length = jsonArray.length();
				for (int i = 0; i < length; i++) {
					JSONObject jsonTemp = (JSONObject) jsonArray.get(i);
					Schedule schedule = new Schedule();
					schedule.setTimeNum(jsonTemp.getInt("hour"));
					schedule.setTime(jsonTemp.getInt("hour"));
					schedule.setDes(jsonTemp.getString("description"));
					schedule.setTitle(jsonTemp.getString("title"));
					schedule.setScheLineId(jsonTemp.getInt("scheduleLineId"));

					scheList.add(schedule);
				}
				
				adapter = new ScheduleListAdapter(getActivity(),scheList);
				listView.setAdapter(adapter);
			} else {
				Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.msg_response_failed), Toast.LENGTH_SHORT).show();
			}
		} catch (JSONException e) {
			e.printStackTrace();
			Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.msg_response_failed), Toast.LENGTH_SHORT).show();
		}
	}
}
