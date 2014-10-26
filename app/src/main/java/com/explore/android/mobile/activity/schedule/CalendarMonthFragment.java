package com.explore.android.mobile.activity.schedule;

import java.util.HashMap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;

import com.explore.android.R;

public class CalendarMonthFragment extends Fragment {

	private CalendarView calView;

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
		
		View view = inflater.inflate(R.layout.frag_calendar_month_layout, container, false);
		calView = (CalendarView) view.findViewById(R.id.schedule_calendar_view);
		initListener();
		return view;
	}
	
	private void initListener(){
		calView.setOnDateChangeListener(new OnDateChangeListener(){
			@Override
			public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
				HashMap<String,Integer> dateInfo = new HashMap<String,Integer>();
				dateInfo.put("year", year);
				dateInfo.put("month", month);
				dateInfo.put("date", dayOfMonth);
				switchScheduleMode(1,dateInfo);
			}
		});
	}
	
	private void switchScheduleMode(int mode, HashMap<String,Integer> dateInfo) {
		if (getActivity() == null){
			return;
		}
		
		if (getActivity() instanceof CalendarActivity) {
			CalendarActivity fca = (CalendarActivity) getActivity();
			fca.switchScheduleMode(mode,dateInfo);
		}
	}
}
