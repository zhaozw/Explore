package com.explore.android.mobile.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;

import com.explore.android.LoginActivity;
import com.explore.android.R;
import com.explore.android.core.preference.PreferenceFragment;
import com.explore.android.mobile.activity.ExHomeActivity;
import com.explore.android.mobile.common.SharePreferencesManager;
import com.explore.android.mobile.constants.AppConstant;
import com.explore.android.mobile.constants.PreferencesConstant;

public class AppPreferenceFragment extends PreferenceFragment implements
		OnPreferenceClickListener {

	private SharePreferencesManager exPreferences;
	PreferenceScreen scr_app;

	CheckBoxPreference save_logininfo;
	CheckBoxPreference auto_login;
	SwitchPreference receive_p2pinfo;
	SwitchPreference location_service;

	public static AppPreferenceFragment newInstance(int navCode) {
		AppPreferenceFragment fragment = new AppPreferenceFragment();
		Bundle args = new Bundle();
		args.putInt(AppConstant.CURRENT_FRAGMENT_TITLE, navCode);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((ExHomeActivity) activity).onSectionAttached(getArguments().getInt(
				AppConstant.CURRENT_FRAGMENT_TITLE));
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.app_preference);
		exPreferences = SharePreferencesManager.getInstance(getActivity());

		save_logininfo = (CheckBoxPreference) findPreference(PreferencesConstant.IS_SAVE_LOGININFO);
		auto_login = (CheckBoxPreference) findPreference(PreferencesConstant.IS_KEEP_LOGIN);
		receive_p2pinfo = (SwitchPreference) findPreference(PreferencesConstant.IS_RECEIVE_P2PINFOS);
		location_service = (SwitchPreference) findPreference(PreferencesConstant.IS_LOCATION_SERVICE_OPEN);

		save_logininfo.setChecked(exPreferences.getIsSaveLoginInfo());
		auto_login.setChecked(exPreferences.getIsKeepLogin());
		receive_p2pinfo.setDisableDependentsState(exPreferences
				.getIsReceiveP2PInfo());
		location_service.setDisableDependentsState(exPreferences
				.getIsLocationServiceOpen());

		Preference prefs_logout = findPreference(PreferencesConstant.LOGOUT_PREFS);
		prefs_logout.setOnPreferenceClickListener(this);
	}

	@Override
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
			Preference preference) {
		String prefsKey = preference.getKey();
		if (PreferencesConstant.IS_SAVE_LOGININFO.equals(prefsKey)) {
			exPreferences.setIsSavaLoginInfo(save_logininfo.isChecked());

		} else if (PreferencesConstant.IS_KEEP_LOGIN.equals(prefsKey)) {
			exPreferences.setIsKeepLogin(auto_login.isChecked());

		} else if (PreferencesConstant.IS_LOCATION_SERVICE_OPEN
				.equals(prefsKey)) {
			exPreferences.setIsLocationServiceStatus(location_service
					.getDisableDependentsState());

		} else if (PreferencesConstant.IS_RECEIVE_P2PINFOS.equals(prefsKey)) {
			exPreferences.setIsReceiveP2PInfo(receive_p2pinfo
					.getDisableDependentsState());
		}
		return super.onPreferenceTreeClick(preferenceScreen, preference);
	}

	@Override
	public boolean onPreferenceClick(Preference preference) {
		if (PreferencesConstant.LOGOUT_PREFS.equals(preference.getKey())) {
			exPreferences.removeUserInfo();
			exPreferences.setIsKeepLogin(false);
			Intent intent = new Intent(getActivity(), LoginActivity.class);
			startActivity(intent);
			getActivity().finish();
		}
		return false;
	}

}
