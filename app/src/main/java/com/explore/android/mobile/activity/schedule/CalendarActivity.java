package com.explore.android.mobile.activity.schedule;

import java.util.HashMap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.explore.android.R;
import com.explore.android.mobile.adapter.SectionPagerAdapter;

public class CalendarActivity extends FragmentActivity {

	private static final int ITEM_NUMS = 3;
	
	private static final int MODE_MONTH = 0;
	private static final int MODE_WEEK = 1;
	private static final int MODE_DAY = 2;

	private ViewPager viewPager;
	private SectionPagerAdapter adapter;
	private Fragment[] fragments;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar);
		initViews();
		initFragments();
		initListener();
	}
	
	private void initViews(){
		viewPager = (ViewPager) findViewById(R.id.schedule_view_pager);
	}

	private void initListener(){
	}
	
	private void initFragments() {

		fragments = new Fragment[ITEM_NUMS];
		fragments[MODE_DAY] = new CalendarDayFragment();
		fragments[MODE_MONTH] = new CalendarMonthFragment();
		fragments[MODE_WEEK] = new CalendarWeekFragment();

		adapter = new SectionPagerAdapter(getSupportFragmentManager(), fragments);
		viewPager.setAdapter(adapter);
	}

	public void switchScheduleMode(int mode, HashMap<String,Integer> dateInfo) {
		switch (mode) {
		case MODE_MONTH:
			viewPager.setCurrentItem(MODE_MONTH);
			break;
		case MODE_WEEK:
			viewPager.setCurrentItem(MODE_WEEK);
			break;
		case MODE_DAY:
			viewPager.setCurrentItem(MODE_DAY);
			break;
		default:
			break;
		}
	}

}
