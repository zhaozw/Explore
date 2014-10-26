package com.explore.android.mobile.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SectionPagerAdapter extends FragmentPagerAdapter{
	
	private Fragment[] fragments;

	public SectionPagerAdapter(FragmentManager fm, Fragment[] frags) {
		super(fm);
		this.fragments = frags;
	}

	@Override
	public Fragment getItem(int position) {
		return fragments[position];
	}

	@Override
	public int getCount() {
		return fragments.length;
	}

}
