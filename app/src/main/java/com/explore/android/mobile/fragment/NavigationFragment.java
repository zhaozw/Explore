package com.explore.android.mobile.fragment;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageButton;

import com.explore.android.R;
import com.explore.android.mobile.adapter.MenuItemExpandListAdapter;
import com.explore.android.mobile.common.SharePreferencesManager;
import com.explore.android.mobile.data.cache.GlobalData;
import com.explore.android.mobile.model.SingleMenuItem;

public class NavigationFragment extends Fragment {

	/**
	 * A pointer to the current callbacks instance (the Activity).
	 */
	public NavigationFragmentCallbacks mCallbacks;

	/**
	 * Remember the position of the selected item.
	 */
	private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";
	
	private static final String STATE_SELECTED_GROUP = "selected_navigation_drawer_group";
	
	private static final String STATE_SELECTED_CHILD = "selected_navigation_drawer_child";

	/**
	 * Helper component that ties the action bar to the navigation drawer.
	 */
	private ActionBarDrawerToggle mDrawerToggle;
	private ExpandableListView mExpandableList;
	private MenuItemExpandListAdapter menuAdapter;
	private DrawerLayout mDrawerLayout;
	private View mFragmentContainerView;
	private boolean mFromSavedInstanceState;
	private boolean mUserLearnedDrawer;
	
	private int mCurrentCode = 0;
	private int mCurrentGroup = 0;
	private int mCurrentChild = 0;
	private int mPreviewCode = -1;
	private SharePreferencesManager preferences;
	
	public NavigationFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		preferences = SharePreferencesManager.getInstance(getActivity());
		mUserLearnedDrawer = preferences.getIsNavigationLearn();
		if (savedInstanceState != null) {
			mCurrentCode = savedInstanceState.getInt(STATE_SELECTED_POSITION);
			mCurrentGroup = savedInstanceState.getInt(STATE_SELECTED_GROUP);
			mCurrentChild = savedInstanceState.getInt(STATE_SELECTED_CHILD);
			mFromSavedInstanceState = true;
		}
		// Select either the default item (0) or the last selected item.
		selectItem(mCurrentGroup, mCurrentChild, mCurrentCode);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mExpandableList = (ExpandableListView) inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
		mExpandableList.setGroupIndicator(null);
		addHomeMenu();
		initialized();
		mExpandableList.setSelectedChild(mCurrentGroup, mCurrentChild, true);
		return mExpandableList;
	}
	
	public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }
	
	/**
     * Users of this fragment must call this method to set up the navigation drawer interactions.
     *
     * @param fragmentId   The android:id of this fragment in its activity's layout.
     * @param drawerLayout The DrawerLayout containing this fragment's UI.
     */
	public void setUp(int fragmentId, DrawerLayout drawerLayout) {
		mFragmentContainerView = getActivity().findViewById(fragmentId);
		mDrawerLayout = drawerLayout;

		// set a custom shadow that overlays the main content when the drawer
		// opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		// set up the drawer's list view with items and click listener

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the navigation drawer and the action bar app icon.
		mDrawerToggle = new ActionBarDrawerToggle(getActivity(), /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
		R.string.navigation_drawer_open, /*
										 * "open drawer" description for
										 * accessibility
										 */
		R.string.navigation_drawer_close /*
										 * "close drawer" description for
										 * accessibility
										 */
		) {
			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
				if (!isAdded()) {
					return;
				}
				getActivity().supportInvalidateOptionsMenu(); // calls
																// onPrepareOptionsMenu()
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				if (!isAdded()) {
					return;
				}
				
				if (!mUserLearnedDrawer) {
					// The user manually opened the drawer; store this flag to
					// prevent auto-showing
					// the navigation drawer automatically in the future.
					mUserLearnedDrawer = true;
					preferences.setIsNavigationLearn(true);
					/*
					SharedPreferences sp = PreferenceManager
							.getDefaultSharedPreferences(getActivity());
					sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true)
							.commit();
							*/
				}
				getActivity().supportInvalidateOptionsMenu(); // calls
			}
		};

		// If the user hasn't 'learned' about the drawer, open it to introduce
		// them to the drawer,
		// per the navigation drawer design guidelines.
		if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
			mDrawerLayout.openDrawer(mFragmentContainerView);
		}

		// Defer code dependent on restoration of previous instance state.
		mDrawerLayout.post(new Runnable() {
			@Override
			public void run() {
				mDrawerToggle.syncState();
			}
		});

		mDrawerLayout.setDrawerListener(mDrawerToggle);
	}
	
	private void addHomeMenu() {
		final ImageButton homeMenu = new ImageButton(getActivity());
		homeMenu.setLayoutParams(
				new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT,
						getActivity().getResources().getDimensionPixelSize(R.dimen.navigation_home_height)));
		homeMenu.setBackgroundDrawable(getResources().getDrawable(R.drawable.navigation_home_bg));
		homeMenu.setImageResource(R.drawable.menu_home_icon);
		homeMenu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mDrawerLayout != null) {
					mDrawerLayout.closeDrawer(mFragmentContainerView);
				}
				if (mCallbacks != null) {
					if(mPreviewCode != 0){
						mCallbacks.onNavigationFragmentSelected(0);
						mPreviewCode = 0;
					}
				}
			}
		});
		mExpandableList.addHeaderView(homeMenu);
	}
	
	private void initialized() {
		if (GlobalData.navList == null || GlobalData.navList.size() < 1) {
			GlobalData.loadNavigationList(getActivity());
		}
		menuAdapter = new MenuItemExpandListAdapter(getActivity(), GlobalData.navList);
		mExpandableList.setAdapter(menuAdapter);

		for (int i = 0; i < GlobalData.navList.size(); i++) {
			mExpandableList.expandGroup(i);
		}

		mExpandableList.setOnGroupClickListener(new OnGroupClickListener() {
			@Override
			public boolean onGroupClick(ExpandableListView parent, View view, int position,
					long id) {
				return false;
			}
		});

		mExpandableList.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				SingleMenuItem groupItem = GlobalData.navList.get(groupPosition);
				SingleMenuItem childItem = groupItem.getGroups().get(childPosition);
				
				mCurrentGroup = groupPosition;
				mCurrentChild = childPosition;
				mCurrentCode = childItem.getCode();
				if (mDrawerLayout != null) {
					mDrawerLayout.closeDrawer(mFragmentContainerView);
				}

				if (mCallbacks != null && mPreviewCode != mCurrentCode) {
					mCallbacks.onNavigationFragmentSelected(mCurrentCode);
					mPreviewCode = mCurrentCode;
				}
				return true;
			}
		});
	}

	/**
	 * Group child code
	 * @param group group position
	 * @param child child position
	 * @param code code
	 */
	private void selectItem(int group, int child, int code) {
		mCurrentCode = code;
		mCurrentGroup = group;
		mCurrentChild = child;
		if(mExpandableList != null) {
			mExpandableList.setSelectedChild(mCurrentGroup, mCurrentChild, true);
		}
		if (mDrawerLayout != null) {
			mDrawerLayout.closeDrawer(mFragmentContainerView);
		}
		if (mCallbacks != null) {
			mCallbacks.onNavigationFragmentSelected(mCurrentCode);
		}
	}

	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (NavigationFragmentCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

	@Override
	public void onDetach() {
		super.onDetach();
		mCallbacks = null;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(STATE_SELECTED_POSITION, mCurrentCode);
		outState.putInt(STATE_SELECTED_GROUP, mCurrentGroup);
		outState.putInt(STATE_SELECTED_CHILD, mCurrentChild);
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Forward the new configuration the drawer toggle component.
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	
	@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // If the drawer is open, show the global app actions in the action bar. See also
        // showGlobalContextActionBar, which controls the top-left area of the action bar.
        if (mDrawerLayout != null && isDrawerOpen()) {
            inflater.inflate(R.menu.global, menu);
            showGlobalContextActionBar();
        }
        super.onCreateOptionsMenu(menu, inflater);
    }
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

	/**
     * Per the navigation drawer design guidelines, updates the action bar to show the global app
     * 'context', rather than just what's in the current screen.
     */
    private void showGlobalContextActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setTitle(R.string.top_title_navigation);
    }
    
	private ActionBar getActionBar() {
		return ((ActionBarActivity) getActivity()).getSupportActionBar();
	}

	public static interface NavigationFragmentCallbacks {
		/**
		 * Called when an item in the navigation drawer is selected.
		 */
		void onNavigationFragmentSelected(int code);
	}
}
