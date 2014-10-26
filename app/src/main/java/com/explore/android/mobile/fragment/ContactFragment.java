package com.explore.android.mobile.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.explore.android.R;
import com.explore.android.core.base.BaseHttpFragment;
import com.explore.android.mobile.activity.ExHomeActivity;
import com.explore.android.mobile.adapter.ContactAdapter;
import com.explore.android.mobile.common.ExCacheManager;
import com.explore.android.mobile.constants.AppConstant;
import com.explore.android.mobile.constants.RequestConstants;
import com.explore.android.mobile.constants.ResponseConstant;
import com.explore.android.mobile.data.request.ExRequest;
import com.explore.android.mobile.db.opration.EmployeeDBO;
import com.explore.android.mobile.model.Employee;
import com.explore.android.mobile.view.PullToRefreshView;
import com.explore.android.mobile.view.PullToRefreshView.OnFooterRefreshListener;
import com.explore.android.mobile.view.PullToRefreshView.OnHeaderRefreshListener;

public class ContactFragment extends BaseHttpFragment
        implements OnHeaderRefreshListener,OnFooterRefreshListener{

	private static final int REQUEST_CONTACTLIST = 1;

	private List<Employee> empList;
	private ContactAdapter adapter;
	private RelativeLayout loading;
	private RelativeLayout list_layout;
	private EmployeeDBO dbo;
	private ExCacheManager cacheManager;

	private ListView listView;

	public static ContactFragment newInstance(int navCode) {
		ContactFragment fragment = new ContactFragment();
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
		return R.layout.frag_contact_layout;
	}

	@Override
	public void initViews(View view) {
		loading = (RelativeLayout) view.findViewById(R.id.app_loading_layout);
		list_layout = (RelativeLayout) view.findViewById(R.id.contact_list_layout);
		listView = (ListView) view.findViewById(R.id.frag_contact_list);
	}

	@Override
	public void initValues() {
		empList = new ArrayList<Employee>();

		dbo = new EmployeeDBO(getActivity());

		cacheManager = ExCacheManager.getInstance(getActivity());
		if (cacheManager.getContactCache() == null) {
			loadContacts();
		} else {
			bindContactData(cacheManager.getContactCache());
		}
	}

	@Override
	public void initListener() {

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {

			}
		});
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
		if(REQUEST_CONTACTLIST == what){
			bindContactData(response);
		}
	}

	private void listContact() {
		dbo.open();
		empList = dbo.getEmployees();
		dbo.close();
		adapter = new ContactAdapter(getActivity(), empList);
		listView.setAdapter(adapter);
	}

	private void loadContacts() {
		ExRequest request = new ExRequest();
		request.setUserId(getPreferences().getUserId());
		request.setToken(getPreferences().getTokenLast8());
		asynDataRequest(RequestConstants.ADRESSBOOK, request.toJsonString(), REQUEST_CONTACTLIST);
	}

	private void bindContactData(String resStr) {
		try {
			JSONObject json = new JSONObject(resStr);
			if (ResponseConstant.OK.equals(json.get(ResponseConstant.STATUS))) {
				dbo.open();
				dbo.importEmps(json, "ADDRESSBOOK");
				empList = dbo.getEmployees();
				dbo.close();

				adapter = new ContactAdapter(getActivity(), empList);
				listView.setAdapter(adapter);
				list_layout.setVisibility(View.VISIBLE);
			} else {
				listContact();
				showToast(R.string.msg_response_failed);
			}
		} catch (JSONException e) {
			listContact();
			e.printStackTrace();
			showToast(R.string.msg_response_failed);
		}

	}

	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		
	}

	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		
	}

}
