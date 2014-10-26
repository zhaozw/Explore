package com.explore.android.core.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;

import com.explore.android.R;
import com.explore.android.mobile.fragment.NavigationFragment;

public class BaseSlideActivity extends ActionBarActivity {

	protected Fragment mFrag;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//setBehindContentView(R.layout.menu_frame);
		if(savedInstanceState == null){
			FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
			mFrag = new NavigationFragment();
			t.replace(R.id.menu_frame, mFrag);
			t.commit();
		} else {
			mFrag = (Fragment)this.getSupportFragmentManager().findFragmentById(R.id.menu_frame);
		}
		/*
		SlidingMenu sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow_left);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		*/
		
	}
	
}
