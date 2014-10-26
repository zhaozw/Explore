package com.explore.android.mobile.fragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.explore.android.R;
import com.explore.android.core.base.BaseHttpFragment;
import com.explore.android.core.util.DateUtil;
import com.explore.android.mobile.activity.ExHomeActivity;
import com.explore.android.mobile.activity.customer.CustomerAddPreActivity;
import com.explore.android.mobile.activity.customer.CustomerDetailActivity;
import com.explore.android.mobile.adapter.CustomerListItemAdapter;
import com.explore.android.mobile.constants.AppConstant;
import com.explore.android.mobile.constants.AppStatus;
import com.explore.android.mobile.constants.RequestConstants;
import com.explore.android.mobile.constants.ResponseConstant;
import com.explore.android.mobile.data.request.CustomerSearchRequest;
import com.explore.android.mobile.model.Customer;
import com.explore.android.mobile.view.PullToRefreshView;
import com.explore.android.mobile.view.PullToRefreshView.OnFooterRefreshListener;
import com.explore.android.mobile.view.PullToRefreshView.OnHeaderRefreshListener;

public class CustomerSearchFragment extends BaseHttpFragment implements OnHeaderRefreshListener,OnFooterRefreshListener{
	
	private static final int REQUEST_CUSTOMERLIST = 1;
	
	private ScrollView condition_layout;
	private Button search_btn;
	private Button reset_btn;
	private EditText edt_cusname;
	private EditText edt_cuscode;
	private List<Customer> customerList;
	
	private ViewStub result_stub;
	private RelativeLayout search_layout;
	private Button show_condition_btn;
	private ListView listView;
	private CustomerListItemAdapter adapter;
	
	private static int page_num = 1;
	private PullToRefreshView mPullToRefreshView;
	private int mode;
	private boolean isStubInflate;
	
	public static CustomerSearchFragment newInstance(int navCode) {
		CustomerSearchFragment fragment = new CustomerSearchFragment();
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
		return R.layout.frag_customer_search;
	}

	@Override
	public void initViews(View v) {
		condition_layout = (ScrollView) v.findViewById(R.id.frag_product_condition_scv);
		search_btn = (Button) v.findViewById(R.id.frag_btn_cussearch);
		reset_btn = (Button) v.findViewById(R.id.frag_resetbtn_cussearch);
		edt_cusname = (EditText) v.findViewById(R.id.frag_edt_cussearch_cusname);
		edt_cuscode = (EditText) v.findViewById(R.id.frag_edt_cussearch_cuscode);

		result_stub = (ViewStub) v.findViewById(R.id.frag_customer_result_stub);
	}

	@Override
	public void initValues() {
		page_num = 1;
		isStubInflate = false;
	}

	@Override
	public void initListener() {
		
		search_btn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				if(!isStubInflate) {
					initStubView();
				}
				loadCustomersList();
				condition_layout.setVisibility(View.GONE);
				search_layout.setVisibility(View.VISIBLE);
			}
		});
		
		reset_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				edt_cusname.setText("");
				edt_cuscode.setText("");
			}
		});
	}
	
	private void initStubView() {
		View stubView = result_stub.inflate();
		mPullToRefreshView = (PullToRefreshView) stubView.findViewById(R.id.frag_search_pullrefresh_view);
		search_layout = (RelativeLayout) stubView.findViewById(R.id.search_result_layout);
		show_condition_btn = (Button) stubView.findViewById(R.id.frag_search_showcond_btn);
		listView = (ListView) stubView.findViewById(R.id.frag_result_list);
		
		customerList = new ArrayList<Customer>();
		adapter = new CustomerListItemAdapter(getActivity(),customerList);
		listView.setAdapter(adapter);
		
		mPullToRefreshView.setOnHeaderRefreshListener(CustomerSearchFragment.this);
		mPullToRefreshView.setOnFooterRefreshListener(CustomerSearchFragment.this);
		
		show_condition_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				condition_layout.setVisibility(View.VISIBLE);
				search_layout.setVisibility(View.GONE);
				cancelTask();
				clearList();
				adapter.notifyDataSetChanged();
			}
		});
		
		listView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				Intent intent = new Intent();
				intent.putExtra("CUSTOMERID", customerList.get(position).getCustomerId());
				intent.setClass(getActivity(), CustomerDetailActivity.class);
				startActivity(intent);
			}
			
		});
		
		isStubInflate = true;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case 1:
			if (data.hasExtra("VALUE")) {
				showToast(data.getStringExtra("VALUE"));
			}
			break;

		default:
			break;
		}
	}

	@Override
	public void showLoading() {
		return;
	}

	@Override
	public void dismissLoading() {
		return;
	}

	@Override
	public void handlerResponse(String response, int what) {
		if(REQUEST_CUSTOMERLIST == what){
			bindCustomerData(response);
		}
	}
	
	@Override
	public void onResume() {
		if(AppStatus.CUSTOMER_LIST_UPDATE){
			if(isStubInflate){
				condition_layout.setVisibility(View.VISIBLE);
				search_layout.setVisibility(View.GONE);
				clearList();
				adapter.notifyDataSetChanged();
				AppStatus.CUSTOMER_LIST_UPDATE = false;
			}
		}
		super.onResume();
	}
	
	private void loadCustomersList() {
		CustomerSearchRequest request = new CustomerSearchRequest();
		request.setUserId(getPreferences().getUserId());
		request.setToken(getPreferences().getTokenLast8());
		request.setCustomerCode(edt_cuscode.getText().toString().trim());
		request.setCustomerNameCN(edt_cusname.getText().toString().trim());
		request.setpSize(RequestConstants.SEARCH_PAGE_SIZE);
		request.setPageNum(page_num);
		asynDataRequest(RequestConstants.CUSTOMER_SEARCH, request.toJsonString(), REQUEST_CUSTOMERLIST);
	}
	
	private void bindCustomerData(String resStr){
		try {
			JSONObject json = new JSONObject(resStr);
			if (ResponseConstant.OK.equals(json.get(ResponseConstant.STATUS))) {
				
				JSONArray jsonArray = json.getJSONArray("CUSTOMERLIST");
				int length = jsonArray.length();
				
				if(1 == page_num){
					customerList.clear();
				}
				
				if (length > 0) {
					page_num ++;
					for(int i=0; i < length; i++){
						Customer customer = new Customer();
						JSONObject jsonObject = jsonArray.getJSONObject(i);
	
						customer.setCustomerId(jsonObject.getString("customerId"));
						customer.setCustomerCode(jsonObject.getString("customerCode"));
						customer.setCustomerName(jsonObject.getString("customerName"));
						
						customerList.add(customer);
					}
					if(page_num > 2){
						String msg = getString(R.string.pull_to_refresh_data_count);
						showToast(String.format(msg, length));
					}
				} else {
					if (1 == page_num){
						showToast(R.string.msg_search_no_result);
					} else {
						showToast(R.string.pull_to_refresh_nomore_data);
					}
				}
				adapter.notifyDataSetChanged();
				
				if(AppConstant.SYNC_LOADMORE == mode) {
					mPullToRefreshView.onFooterRefreshComplete();
				} else if (AppConstant.SYNC_REFRESH == mode) {
					String nowTime = DateUtil.dateFormatWithDayTime(new Date());
					mPullToRefreshView.onHeaderRefreshComplete(nowTime);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
			customerList = null;
		}
	}
	
	private void clearList(){
		page_num = 1;
		customerList.clear();
	}
	
	@Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Indicate that this fragment would like to influence the set of actions in the action bar.
        setHasOptionsMenu(true);
    }
	
	@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.action_create_menu, menu);
        ActionBar actionBar = ((ActionBarActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        super.onCreateOptionsMenu(menu, inflater);
    }
	
	 @Override
	public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_create) {
	        Intent intent = new Intent(getActivity(), CustomerAddPreActivity.class);
	        startActivity(intent);
	        return true;
	    }
	    return super.onOptionsItemSelected(item);
	}

	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		mode = AppConstant.SYNC_LOADMORE;
		mPullToRefreshView.postDelayed(new Runnable() {
			@Override
			public void run() {
				loadCustomersList();
			}
		}, 300);
	}

	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		mode = AppConstant.SYNC_REFRESH;
		mPullToRefreshView.postDelayed(new Runnable() {
			@Override
			public void run() {
				page_num = 1;
				loadCustomersList();
			}
		}, 300);
	}

}
