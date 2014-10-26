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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.explore.android.R;
import com.explore.android.core.base.BaseHttpFragment;
import com.explore.android.core.model.BaseKeyValue;
import com.explore.android.core.util.DateUtil;
import com.explore.android.mobile.activity.ExHomeActivity;
import com.explore.android.mobile.activity.product.ProductCategoryActivity;
import com.explore.android.mobile.activity.product.ProductDetailActivity;
import com.explore.android.mobile.adapter.ProductListItemAdapter;
import com.explore.android.mobile.adapter.SimpleSpinnerAdapter;
import com.explore.android.mobile.common.ExCacheManager;
import com.explore.android.mobile.constants.AppConstant;
import com.explore.android.mobile.constants.AppStatus;
import com.explore.android.mobile.constants.RequestConstants;
import com.explore.android.mobile.constants.ResponseConstant;
import com.explore.android.mobile.data.ValueToResource;
import com.explore.android.mobile.data.cache.GlobalData;
import com.explore.android.mobile.data.request.ExRequest;
import com.explore.android.mobile.data.request.OutCusDeptExtraRequest;
import com.explore.android.mobile.data.request.ProductSearchRequest;
import com.explore.android.mobile.model.Product;
import com.explore.android.mobile.view.PullToRefreshView;
import com.explore.android.mobile.view.PullToRefreshView.OnFooterRefreshListener;
import com.explore.android.mobile.view.PullToRefreshView.OnHeaderRefreshListener;
import com.zbar.lib.CaptureActivity;

public class ProductSearchFragment extends BaseHttpFragment 
        implements OnHeaderRefreshListener,OnFooterRefreshListener{
	
	private static final int REQUEST_SEARCHPRE_DEPT = 1;
	private static final int REQUEST_SEARCHPRE_DEPTEXTRA = 2;
	private static final int REQUEST_SEARCH = 3;
	private static final int ACTION_REQUEST_QRCODE = 11;
	private static final String DEPTEXTRA_TYPE = "P";

	private ScrollView condition_layout;
	private Button search_btn;
	private Button reset_btn;
	private EditText edt_proname;
	private EditText edt_prono;
	private EditText edt_nprono;
	private Spinner spi_customerDept;
	private Spinner spi_project;
	private Spinner spi_sts;
	
	private ViewStub result_stub;
	private RelativeLayout search_layout;
	private ListView listView;
	private PullToRefreshView mPullToRefreshView;
	private ProductListItemAdapter adapter;
	private Button show_condition_btn;
	
	private List<Product> proList;
	private List<BaseKeyValue> projectList;
	private List<BaseKeyValue> proStsList;
	private SimpleSpinnerAdapter cusDeptAdapter;
	private SimpleSpinnerAdapter projectAdapter;
	private SimpleSpinnerAdapter proStsAdapter;
	
	private ExCacheManager cacheManager;
	private static int page_num = 1;
	private int mode;
	private String deptId;
	private boolean isStubInflate;
	
	public static ProductSearchFragment newInstance(int navCode) {
		ProductSearchFragment fragment = new ProductSearchFragment();
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
		return R.layout.frag_product_search;
	}

	@Override
	public void initViews(View v) {
		condition_layout = (ScrollView) v.findViewById(R.id.frag_product_condition_scv);
		search_btn = (Button) v.findViewById(R.id.frag_btn_prosearch);
		reset_btn = (Button) v.findViewById(R.id.frag_resetbtn_prosearch);
		spi_customerDept = (Spinner) v.findViewById(R.id.frag_spi_prosearch_cusdept);
		spi_project = (Spinner) v.findViewById(R.id.frag_spi_prosearch_project);
		spi_sts = (Spinner) v.findViewById(R.id.frag_spi_prosearch_sts);
		edt_nprono = (EditText) v.findViewById(R.id.frag_edt_prosearch_npartno);
		edt_prono = (EditText) v.findViewById(R.id.frag_edt_prosearch_partno);
		edt_proname = (EditText) v.findViewById(R.id.frag_edt_prosearch_partname);
		
		result_stub = (ViewStub) v.findViewById(R.id.frag_product_result_stub);
	}

	@Override
	public void initValues() {
		page_num = 1;
		isStubInflate = false;
		cacheManager = ExCacheManager.getInstance(getActivity());
		
		projectList = new ArrayList<BaseKeyValue>();
		proStsList = new ArrayList<BaseKeyValue>();
			
		projectList.add(new BaseKeyValue(getResources().getString(R.string.out_project), ""));
		proStsList.add(new BaseKeyValue(getResources().getString(R.string.out_prosts), ""));
		proStsList.add(new BaseKeyValue(getResources().getString(R.string.sd_product_new), "NEW"));
		proStsList.add(new BaseKeyValue(getResources().getString(R.string.sd_product_run), "RUN"));
		proStsList.add(new BaseKeyValue(getResources().getString(R.string.sd_product_loc), "LOC"));
			
		cusDeptAdapter = new SimpleSpinnerAdapter(getActivity(), GlobalData.customerDeptList);
		projectAdapter = new SimpleSpinnerAdapter(getActivity(), projectList);
		proStsAdapter = new SimpleSpinnerAdapter(getActivity(), proStsList);
			
		spi_customerDept.setAdapter(cusDeptAdapter);
		spi_project.setAdapter(projectAdapter);
		spi_project.setEnabled(false);
		spi_sts.setAdapter(proStsAdapter);
			
		if(GlobalData.customerDeptList.size() == 0){
			loadCustomerDept();
		}
	}

	@Override
	public void initListener() {
		
		search_btn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				if(!isStubInflate) {
					initStubView();
				}
				loadProductList();
				condition_layout.setVisibility(View.GONE);
				search_layout.setVisibility(View.VISIBLE);
			}
		});
		
		reset_btn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				edt_nprono.setText("");
				edt_prono.setText("");
				edt_proname.setText("");
				spi_customerDept.setSelection(0);
				spi_project.setSelection(0);
				spi_sts.setSelection(0);
			}
		});
		
		spi_customerDept.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View view, int position, long id) {
				if(GlobalData.customerDeptList.size() > 0){
					if(cacheManager.getOutCusDeptExtraCache(DEPTEXTRA_TYPE, GlobalData.customerDeptList.get(position).getValue()) != null){
						bindCusDeptExtraData(cacheManager.getOutCusDeptExtraCache(DEPTEXTRA_TYPE, GlobalData.customerDeptList.get(position).getValue()), true);
					}else{
						loadDeptExtraData(GlobalData.customerDeptList.get(position).getValue());
					}
				} else {
					spi_project.setEnabled(false);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	}
	
	private void initStubView() {
		View stubView = result_stub.inflate();
		listView = (ListView) stubView.findViewById(R.id.frag_result_list);
		show_condition_btn = (Button) stubView.findViewById(R.id.frag_search_showcond_btn);
		search_layout = (RelativeLayout) stubView.findViewById(R.id.search_result_layout);
		mPullToRefreshView = (PullToRefreshView) stubView.findViewById(R.id.frag_search_pullrefresh_view);
		
		proList = new ArrayList<Product>();
		adapter = new ProductListItemAdapter(getActivity(),proList);
		listView.setAdapter(adapter);
		
		mPullToRefreshView.setOnHeaderRefreshListener(this);
		mPullToRefreshView.setOnFooterRefreshListener(this);
		
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
			public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), ProductDetailActivity.class);
				intent.putExtra("proId", proList.get(position).getProId());
				startActivity(intent);
			}
		});
		
		isStubInflate = true;
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
		switch (what) {
		case REQUEST_SEARCHPRE_DEPT:
			GlobalData.initCustomerDept(response);
			cusDeptAdapter.notifyDataSetChanged();
			break;
			
		case REQUEST_SEARCHPRE_DEPTEXTRA:
			bindCusDeptExtraData(response, false);
			break;
			
		case REQUEST_SEARCH:
			bindProductData(response);
			break;

		default:
			break;
		}
	}
	
	@Override
	public void onResume() {
		if(AppStatus.PRODUCT_LIST_UPDATE){
			if (isStubInflate) {
				condition_layout.setVisibility(View.VISIBLE);
				search_layout.setVisibility(View.GONE);
				clearList();
				adapter.notifyDataSetChanged();
			}
			AppStatus.PRODUCT_LIST_UPDATE = false;
		}
		super.onResume();
	}
	
	
	private void loadCustomerDept(){
		ExRequest request = new ExRequest();
		request.setUserId(getPreferences().getUserId());
		request.setToken(getPreferences().getTokenLast8());
		asynDataRequest(RequestConstants.OUT_GET_CUSTOMERDEPT, request.toJsonString(), REQUEST_SEARCHPRE_DEPT);
	}
	
	private void loadDeptExtraData(String id){
		deptId = id;
		OutCusDeptExtraRequest request = new OutCusDeptExtraRequest();
		request.setUserId(getPreferences().getUserId());
		request.setToken(getPreferences().getTokenLast8());
		request.setDeptId(deptId);
		request.setType(DEPTEXTRA_TYPE);
		asynDataRequest(RequestConstants.OUT_GET_DEPT_EXTRA, request.toJsonString(), REQUEST_SEARCHPRE_DEPTEXTRA);
	}
	
	
	private void loadProductList() {
		ProductSearchRequest request = new ProductSearchRequest();
		request.setUserId(getPreferences().getUserId());
		request.setToken(getPreferences().getTokenLast8());
		request.setNprono(edt_nprono.getText().toString().trim());
		request.setProname(edt_proname.getText().toString().trim());
		request.setProno(edt_prono.getText().toString().trim());
		request.setSts(proStsList.get(spi_sts.getSelectedItemPosition()).getValue());
		request.setCustomerDeptId(GlobalData.customerDeptList.get(spi_customerDept.getSelectedItemPosition()).getValue());
		request.setProjectId(projectList.get(spi_project.getSelectedItemPosition()).getValue());
		request.setPsize(RequestConstants.SEARCH_PAGE_SIZE);
		request.setPageNum(page_num);
		asynDataRequest(RequestConstants.PRODUCT_SEARCH, request.toJsonString(), REQUEST_SEARCH);
	}
	
	private void bindCusDeptExtraData(String dataStr, boolean isFromCache){
		try {
			JSONObject json = new JSONObject(dataStr);
			if(ResponseConstant.OK.equals(json.get(ResponseConstant.STATUS))){

				if(!isFromCache){
					cacheManager.addOutCusDeptExtraCache(dataStr, DEPTEXTRA_TYPE, deptId);
				}
				
				JSONArray projectArray = json.getJSONArray("PROJECTS");
				projectList.clear();
				projectList.add(new BaseKeyValue(getResources().getString(R.string.out_project), ""));
						
				for (int i = 0; i < projectArray.length(); i++) {
					BaseKeyValue info = new BaseKeyValue();
					JSONObject jsonObject = projectArray.getJSONObject(i);
					info.setValue(jsonObject.getString("CUSTOMERDEPTID"));
					info.setKey(jsonObject.getString("CUSTOMERDEPTNAME"));
					projectList.add(info);
				}
						
				projectAdapter.notifyDataSetChanged();
				spi_project.setSelection(0);
				spi_project.setEnabled(true);
			} else {
				showToast(R.string.msg_response_failed);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			showToast(R.string.msg_response_failed);
		}
	}
	
	private void bindProductData(String resStr){
		
		try {
			JSONObject json = new JSONObject(resStr);
			if (ResponseConstant.OK.equals(json.get(ResponseConstant.STATUS))) {
				JSONArray jsonArray = json.getJSONArray("PRODUCTLIST");
				int length = jsonArray.length();
				
				if(1 == page_num){
					proList.clear();
				}
				
				if (length > 0) {
					page_num ++;
					for (int i=0; i < length; i++) {
						Product product = new Product();
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						product.setProName(jsonObject.getString("productName"));
						product.setPartNo(jsonObject.getString("partNo"));
						product.setNpartNo(jsonObject.getString("nPartNo"));
						product.setProId(jsonObject.getString("productId"));
						int resId = ValueToResource.getGenStsValue(jsonObject.getString("sts"));
						product.setSts(getResources().getString(resId));
						proList.add(product);
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
					mPullToRefreshView.onHeaderRefreshComplete(DateUtil.dateFormatWithDayTime(new Date()));
					showToast(R.string.pull_to_refresh_finish);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	private void clearList(){
		page_num = 1;
		if (proList != null) {
			proList.clear();
		}
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
        	Intent intent = new Intent(getActivity(), ProductCategoryActivity.class);
			intent.putExtra("TYPE", "new");
			startActivity(intent);
	        return true;
	    } else if (item.getItemId() == R.id.action_barcode_search) {
	    	Intent intent = new Intent(getActivity(), CaptureActivity.class);
	    	startActivityForResult(intent, ACTION_REQUEST_QRCODE);
	    }
	    return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == -1){
			switch (requestCode) {
			case ACTION_REQUEST_QRCODE:
				edt_nprono.setText(data.hasExtra("CODE") ? data.getStringExtra("CODE") : "");
				break;

			default:
				break;
			}
		}
	}

	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		mode = AppConstant.SYNC_LOADMORE;
		mPullToRefreshView.postDelayed(new Runnable() {
			@Override
			public void run() {
				loadProductList();
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
				loadProductList();
			}
		}, 300);
	}
	
}
