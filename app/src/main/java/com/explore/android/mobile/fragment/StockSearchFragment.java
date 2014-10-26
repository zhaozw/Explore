package com.explore.android.mobile.fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.explore.android.mobile.activity.stock.StockDetailActivity;
import com.explore.android.mobile.adapter.SimpleSpinnerAdapter;
import com.explore.android.mobile.adapter.StockListItemAdapter;
import com.explore.android.mobile.common.ExCacheManager;
import com.explore.android.mobile.constants.AppConstant;
import com.explore.android.mobile.constants.RequestConstants;
import com.explore.android.mobile.constants.ResponseConstant;
import com.explore.android.mobile.constants.SDConstants;
import com.explore.android.mobile.data.cache.GlobalData;
import com.explore.android.mobile.data.request.ExRequest;
import com.explore.android.mobile.data.request.OutCusDeptExtraRequest;
import com.explore.android.mobile.data.request.StockSearchRequest;
import com.explore.android.mobile.model.ExStock;
import com.explore.android.mobile.view.PullToRefreshView;
import com.explore.android.mobile.view.PullToRefreshView.OnFooterRefreshListener;
import com.explore.android.mobile.view.PullToRefreshView.OnHeaderRefreshListener;

public class StockSearchFragment extends BaseHttpFragment implements OnHeaderRefreshListener,OnFooterRefreshListener{
	
	private static final int REQUEST_SEARCH = 2;
	private static final int REQUEST_CUSDEPT = 3;
	private static final int REQUEST_DEPTEXTRA = 4;
	
	private static final String DEPTEXTRA_TYPE = "WP";

	private ScrollView condition_layout;
	private Button search_btn;
	private Button reset_btn;
	private Spinner spi_customerDept;
	private Spinner spi_warehouse;
	private Spinner spi_prosts;
	private Spinner spi_project;
	private EditText edt_partno;
	private EditText edt_npartno;
	private EditText edt_proname;
	private EditText edt_pocode1;
	private EditText edt_pocode2;
	private EditText edt_incode1;
	private EditText edt_incode2;
	private EditText edt_logon;
	private EditText edt_location;
	private EditText edt_stocktime_start,edt_stocktime_end,edt_begindate_start,edt_begindate_end,edt_enddate_start,edt_enddate_end;
	private Button btn_stocktime_start,btn_stocktime_end,btn_begindate_start,btn_begindate_end,btn_enddate_start,btn_enddate_end;
	private List<BaseKeyValue> proList;
	private List<BaseKeyValue> whList;
	private List<BaseKeyValue> stsList;
	private SimpleSpinnerAdapter proAdapter;
	private SimpleSpinnerAdapter whAdapter;
	private SimpleSpinnerAdapter cusDeptAdapter;
	
	private ViewStub result_stub;
	private RelativeLayout search_layout;
	private PullToRefreshView mPullToRefreshView;
	private ListView listView;
	private Button show_condition_btn;
	private List<ExStock> stockList;
	private StockListItemAdapter adapter;
	private ExCacheManager cacheManager;
	
	private int mode;	
	private String deptId;
	private static int page_num = 1;
	private int year,month,date;
	private boolean isStubInflate;
	
	public static StockSearchFragment newInstance(int navCode) {
		StockSearchFragment fragment = new StockSearchFragment();
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
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	protected int setLayoutId() {
		return R.layout.frag_stock_search;
	}

	@Override
	public void initViews(View v) {
		condition_layout = (ScrollView) v.findViewById(R.id.frag_stock_condition_scv);
		search_btn = (Button) v.findViewById(R.id.frag_stocksearch_btn);
		reset_btn = (Button) v.findViewById(R.id.frag_stocksearch_resetbtn);
		btn_stocktime_start = (Button) v.findViewById(R.id.frag_btn_stocksearch_stocktime_start);
		btn_stocktime_end = (Button) v.findViewById(R.id.frag_btn_stocksearch_stocktime_end);
		btn_begindate_start = (Button) v.findViewById(R.id.frag_btn_stocksearch_begindate_start);
		btn_begindate_end = (Button) v.findViewById(R.id.frag_btn_stocksearch_begindate_end);
		btn_enddate_start = (Button) v.findViewById(R.id.frag_btn_stocksearch_enddate_start);
		btn_enddate_end = (Button) v.findViewById(R.id.frag_btn_stocksearch_enddate_end);
		edt_stocktime_start = (EditText) v.findViewById(R.id.frag_edt_stocksearch_stocktime_start);
		edt_stocktime_end = (EditText) v.findViewById(R.id.frag_edt_stocksearch_stocktime_end);
		edt_begindate_start = (EditText) v.findViewById(R.id.frag_edt_stocksearch_begindate_start);
		edt_begindate_end = (EditText) v.findViewById(R.id.frag_edt_stocksearch_begindate_end);
		edt_enddate_start = (EditText) v.findViewById(R.id.frag_edt_stocksearch_enddate_start);
		edt_enddate_end = (EditText) v.findViewById(R.id.frag_edt_stocksearch_enddate_end);
		edt_partno = (EditText) v.findViewById(R.id.frag_edt_stocksearch_partno);
		edt_npartno = (EditText) v.findViewById(R.id.frag_edt_stocksearch_npartno);
		edt_proname = (EditText) v.findViewById(R.id.frag_edt_stocksearch_proname);
		edt_location = (EditText) v.findViewById(R.id.frag_edt_stocksearch_location);
		edt_logon = (EditText) v.findViewById(R.id.frag_edt_stocksearch_logno);
		edt_pocode1 = (EditText) v.findViewById(R.id.frag_edt_stocksearch_pocode1);
		edt_pocode2 = (EditText) v.findViewById(R.id.frag_edt_stocksearch_pocode2);
		edt_incode1 = (EditText) v.findViewById(R.id.frag_edt_stocksearch_incode1);
		edt_incode2 = (EditText) v.findViewById(R.id.frag_edt_stocksearch_incode2);
		spi_customerDept = (Spinner) v.findViewById(R.id.frag_spi_stock_cusdept);
		spi_warehouse = (Spinner) v.findViewById(R.id.frag_spi_stocksearch_warehouse);
		spi_project = (Spinner) v.findViewById(R.id.frag_spi_stocksearch_project);
		spi_prosts = (Spinner) v.findViewById(R.id.frag_spi_stocksearch_prosts);
		
		result_stub = (ViewStub) v.findViewById(R.id.frag_stocksearch_result_stub);
	}

	@Override
	public void initValues() {
		if(getActivity() != null && getActivity() instanceof ExHomeActivity){
			page_num = 1;
			isStubInflate = false;
			
			cacheManager = ExCacheManager.getInstance(getActivity());
			
			Calendar cal = Calendar.getInstance();
			year = cal.get(Calendar.YEAR);
			month = cal.get(Calendar.MONTH);
			date = cal.get(Calendar.DATE);
			
			whList = new ArrayList<BaseKeyValue>();
			proList = new ArrayList<BaseKeyValue>();
			stsList = new ArrayList<BaseKeyValue>();
			
			stsList.add(new BaseKeyValue(getResources().getString(R.string.stockdetail_whs), ""));
			stsList.add(new BaseKeyValue(getResources().getString(R.string.sd_whs_good), SDConstants.WHS_01));
			stsList.add(new BaseKeyValue(getResources().getString(R.string.sd_whs_overday), SDConstants.WHS_02));
			stsList.add(new BaseKeyValue(getResources().getString(R.string.sd_whs_back), SDConstants.WHS_50));
			stsList.add(new BaseKeyValue(getResources().getString(R.string.sd_whs_scrap), SDConstants.WHS_99));
			stsList.add(new BaseKeyValue(getResources().getString(R.string.sd_whs_package), SDConstants.WHS_49));
			
			spi_prosts.setAdapter(new SimpleSpinnerAdapter(getActivity(),stsList));
			
			whList.add(new BaseKeyValue(getResources().getString(R.string.stockdetail_warehouse),""));
			whAdapter = new SimpleSpinnerAdapter(getActivity(),whList);
			spi_warehouse.setAdapter(whAdapter);
			
			proList.add(new BaseKeyValue(getResources().getString(R.string.stockdetail_project),""));
			proAdapter = new SimpleSpinnerAdapter(getActivity(),proList);
			spi_project.setAdapter(proAdapter);
			
			cusDeptAdapter = new SimpleSpinnerAdapter(getActivity(), GlobalData.customerDeptList);
			spi_customerDept.setAdapter(cusDeptAdapter);
			
			if(GlobalData.customerDeptList.size() == 0){
				loadCustomerDept();
			}
			//loadStockPreData();
		}
	}
	
	private void initStubView() {
		View stubView = result_stub.inflate();
		listView = (ListView) stubView.findViewById(R.id.frag_result_list);
		mPullToRefreshView = (PullToRefreshView) stubView.findViewById(R.id.frag_search_pullrefresh_view);
		show_condition_btn = (Button) stubView.findViewById(R.id.frag_search_showcond_btn);
		search_layout = (RelativeLayout) stubView.findViewById(R.id.search_result_layout);
		
		mPullToRefreshView.setOnHeaderRefreshListener(StockSearchFragment.this);
		mPullToRefreshView.setOnFooterRefreshListener(StockSearchFragment.this);
		
		stockList = new ArrayList<ExStock>();
		adapter = new StockListItemAdapter(getActivity(),stockList);
		listView.setAdapter(adapter);
		
		show_condition_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				condition_layout.setVisibility(View.VISIBLE);
				search_layout.setVisibility(View.GONE);
				clearList();
			}
		});
		
		listView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), StockDetailActivity.class);
				intent.putExtra("stocklineId", stockList.get(position).getStockLIneID());
				startActivity(intent);
			}
		});
		
		isStubInflate = true;
	}

	@Override
	public void initListener() {
		
		btn_stocktime_start.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				DatePickerDialog dlg = new DatePickerDialog(getActivity(), stockTimeStartListener, year, month, date);
				dlg.setCanceledOnTouchOutside(true);
				dlg.show();
			}
		});
		
		btn_stocktime_end.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				DatePickerDialog dlg = new DatePickerDialog(getActivity(), stockTimeEndListener, year, month, date);
				dlg.setCanceledOnTouchOutside(true);
				dlg.show();
			}
		});
		
		btn_begindate_start.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				DatePickerDialog dlg = new DatePickerDialog(getActivity(), beginDateStartListener, year, month, date);
				dlg.setCanceledOnTouchOutside(true);
				dlg.show();
			}
		});
		
		btn_begindate_end.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				DatePickerDialog dlg = new DatePickerDialog(getActivity(), beginDateEndListener, year, month, date);
				dlg.setCanceledOnTouchOutside(true);
				dlg.show();
			}
		});
		
		btn_enddate_start.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				DatePickerDialog dlg = new DatePickerDialog(getActivity(), endDateStartListener, year, month, date);
				dlg.setCanceledOnTouchOutside(true);
				dlg.show();
			}
		});
		
		btn_enddate_end.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				DatePickerDialog dlg = new DatePickerDialog(getActivity(), endDateEndListener, year, month, date);
				dlg.setCanceledOnTouchOutside(true);
				dlg.show();
			}
		});
		
		search_btn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				if(!isStubInflate) {
					initStubView();
				}
				loadStockList();
				condition_layout.setVisibility(View.GONE);
				search_layout.setVisibility(View.VISIBLE);
			}
		});
		
		reset_btn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				edt_partno.setText("");
				edt_npartno.setText("");
				edt_proname.setText("");
				edt_location.setText("");
				edt_logon.setText("");
				edt_pocode1.setText("");
				edt_pocode2.setText("");
				edt_incode1.setText("");
				edt_incode2.setText("");
				edt_stocktime_start.setText("");
				edt_stocktime_end.setText("");
				edt_begindate_start.setText("");
				edt_begindate_end.setText("");
				edt_enddate_start.setText("");
				edt_enddate_end.setText("");
				
				spi_project.setSelection(0);
				spi_prosts.setSelection(0);
				spi_warehouse.setSelection(0);
			}
		});
		
		spi_customerDept.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
				if(GlobalData.customerDeptList.size() > 0) {
					if(cacheManager.getOutCusDeptExtraCache(DEPTEXTRA_TYPE, GlobalData.customerDeptList.get(position).getValue()) != null){
						bindCusDeptExtraData(cacheManager.getOutCusDeptExtraCache(DEPTEXTRA_TYPE, GlobalData.customerDeptList.get(position).getValue()), true);
					}else{
						loadDeptExtraData(GlobalData.customerDeptList.get(position).getValue());
					}
				} else {
					spi_project.setSelection(0);
					spi_warehouse.setSelection(0);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	}

	@Override
	public void showLoading() {
	}

	@Override
	public void dismissLoading() {
	}

	@Override
	public void handlerResponse(String response, int what) {
		switch (what) {
		case REQUEST_SEARCH:
			bindStockListData(response);
			break;
			
		case REQUEST_CUSDEPT:
			GlobalData.initCustomerDept(response);
			cusDeptAdapter.notifyDataSetChanged();
			break;
			
		case REQUEST_DEPTEXTRA:
			bindCusDeptExtraData(response, false);
			break;

		default:
			break;
		}
	}
	
	private void loadCustomerDept(){
		ExRequest request = new ExRequest();
		request.setUserId(getPreferences().getUserId());
		request.setToken(getPreferences().getTokenLast8());
		asynDataRequest(RequestConstants.OUT_GET_CUSTOMERDEPT, request.toJsonString(), REQUEST_CUSDEPT);
	}
	
	private void loadDeptExtraData(String id){
		OutCusDeptExtraRequest request = new OutCusDeptExtraRequest();
		deptId = id;
		request.setUserId(getPreferences().getUserId());
		request.setToken(getPreferences().getTokenLast8());
		request.setDeptId(deptId);
		request.setType(DEPTEXTRA_TYPE);
		asynDataRequest(RequestConstants.OUT_GET_DEPT_EXTRA,request.toJsonString(), REQUEST_DEPTEXTRA);
	}
	
	private void loadStockList() {
		StockSearchRequest request = new StockSearchRequest();
		request.setUserId(getPreferences().getUserId());
		request.setToken(getPreferences().getTokenLast8());
		request.setWarehouseId(whList.get(spi_warehouse.getSelectedItemPosition()).getValue());
		request.setProjectId(proList.get(spi_project.getSelectedItemPosition()).getValue());
		request.setWhs(stsList.get(spi_prosts.getSelectedItemPosition()).getValue());
		request.setPartNo(edt_partno.getText().toString().trim());
		request.setNpartNo(edt_npartno.getText().toString().trim());
		request.setProName(edt_proname.getText().toString().trim());
		request.setLogon(edt_logon.getText().toString().trim());
		request.setProlocation(edt_location.getText().toString().trim());
		request.setPoCode1(edt_pocode1.getText().toString().trim());
		request.setPoCode2(edt_pocode2.getText().toString().trim());
		request.setInCode1(edt_incode1.getText().toString().trim());
		request.setInCode2(edt_incode2.getText().toString().trim());
		request.setStockTimeStart(edt_stocktime_start.getText().toString().trim());
		request.setStockTimeEnd(edt_stocktime_end.getText().toString().trim());
		request.setBeginDateStart(edt_begindate_start.getText().toString().trim());
		request.setBeginDateEnd(edt_begindate_end.getText().toString().trim());
		request.setEndDateStart(edt_enddate_start.getText().toString().trim());
		request.setEndDateEnd(edt_enddate_end.getText().toString().trim());
		request.setPageNum(page_num);
		request.setPsize(RequestConstants.SEARCH_PAGE_SIZE);
			
		asynDataRequest(RequestConstants.STOCK_SEARCH, request.toJsonString(), REQUEST_SEARCH);
	}
	
	private void bindCusDeptExtraData(String dataStr, boolean isFromCache){
		try {
			JSONObject json = new JSONObject(dataStr);
			if(ResponseConstant.OK.equals(json.get(ResponseConstant.STATUS))){
				
				JSONArray warehouseArray = json.getJSONArray("WAREHOUSES");
				JSONArray projectArray = json.getJSONArray("PROJECTS");
						
				whList.clear();
				whList.add(new BaseKeyValue(getResources().getString(R.string.stockdetail_warehouse), ""));
				proList.clear();
				proList.add(new BaseKeyValue(getResources().getString(R.string.out_project), ""));
						
				int length1 = warehouseArray.length();
				for (int i = 0; i < length1; i++) {
					BaseKeyValue info = new BaseKeyValue();
					JSONObject jsonObject = warehouseArray.getJSONObject(i);
					info.setValue(jsonObject.getString("CUSTOMERDEPTID"));
					info.setKey(jsonObject.getString("CUSTOMERDEPTNAME"));
					whList.add(info);
				}
						
						
				int length4 = projectArray.length();
				for (int i = 0; i < length4; i++) {
					BaseKeyValue info = new BaseKeyValue();
					JSONObject jsonObject = projectArray.getJSONObject(i);
					info.setValue(jsonObject.getString("CUSTOMERDEPTID"));
					info.setKey(jsonObject.getString("CUSTOMERDEPTNAME"));
					proList.add(info);
				}
						
				proAdapter.notifyDataSetChanged();
				whAdapter.notifyDataSetChanged();
						
				spi_project.setSelection(0);
				spi_warehouse.setSelection(0);
				
				if(!isFromCache){
					cacheManager.addOutCusDeptExtraCache(dataStr, DEPTEXTRA_TYPE, deptId);
				}
				
				spi_project.setEnabled(true);
				spi_warehouse.setEnabled(true);
			} else {
				showToast(R.string.msg_response_failed);
			}
		} catch (JSONException e) {
			showToast(R.string.msg_response_failed);
		}
	}
	
	private boolean bindStockListData(String resStr){
		try {
			JSONObject json = new JSONObject(resStr);
			if (ResponseConstant.OK.equals(json.get(ResponseConstant.STATUS))) {
				
				JSONArray jsonArray = json.getJSONArray("STOCKLINELIST");
				int length = jsonArray.length();
				
				if(1 == page_num){
					stockList.clear();
				}
				
				if (length > 0) {
					page_num ++;
					for(int i=0; i < length; i++){
						ExStock stock = new ExStock();
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						stock.setStockLIneID(jsonObject.getString("stocklineId"));
						stock.setPartNo(jsonObject.getString("partNo"));
						stock.setnPartNo(jsonObject.getString("nPartNo"));
						stock.setProductName(jsonObject.getString("productName"));
						stock.setProject(jsonObject.getString("project"));
						stock.setWareHouse(jsonObject.getString("warehouse"));
						stock.setLoc(jsonObject.getString("loc"));
						stock.setWhs(jsonObject.getString("whs"));
						stockList.add(stock);
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
				
				if(AppConstant.SYNC_LOADMORE == mode) {
					mPullToRefreshView.onFooterRefreshComplete();
				} else if (AppConstant.SYNC_REFRESH == mode) {
					String nowTime = DateUtil.dateFormatWithDayTime(new Date());
					mPullToRefreshView.onHeaderRefreshComplete(nowTime);
				}
				
				adapter.notifyDataSetChanged();
				return true;
			}
		} catch (JSONException e) {
			e.printStackTrace();
			stockList = null;
		}
		return false;
	}
	
	private void clearList(){
		page_num = 1;
		stockList.clear();
	}
	
	private DatePickerDialog.OnDateSetListener stockTimeStartListener = new DatePickerDialog.OnDateSetListener() {  
        public void onDateSet(DatePicker view, int y, int m, int d) {
        	edt_stocktime_start.setText(DateUtil.dateFormatWithDateNum(y, m+1, d));
        }
    };
    
    private DatePickerDialog.OnDateSetListener stockTimeEndListener = new DatePickerDialog.OnDateSetListener() {  
        public void onDateSet(DatePicker view, int y, int m, int d) {
        	edt_stocktime_end.setText(DateUtil.dateFormatWithDateNum(y, m+1, d));
        }
    };
    
    private DatePickerDialog.OnDateSetListener beginDateStartListener = new DatePickerDialog.OnDateSetListener() {  
    	public void onDateSet(DatePicker view, int y, int m, int d) {
    		edt_begindate_start.setText(DateUtil.dateFormatWithDateNum(y, m+1, d));
    	}
    };
    
    private DatePickerDialog.OnDateSetListener beginDateEndListener = new DatePickerDialog.OnDateSetListener() {  
    	public void onDateSet(DatePicker view, int y, int m, int d) {
    		edt_begindate_end.setText(DateUtil.dateFormatWithDateNum(y, m+1, d));
    	}
    };
    private DatePickerDialog.OnDateSetListener endDateStartListener = new DatePickerDialog.OnDateSetListener() {  
    	public void onDateSet(DatePicker view, int y, int m, int d) {
    		edt_enddate_start.setText(DateUtil.dateFormatWithDateNum(y, m+1, d));
    	}
    };
    
    private DatePickerDialog.OnDateSetListener endDateEndListener = new DatePickerDialog.OnDateSetListener() {  
    	public void onDateSet(DatePicker view, int y, int m, int d) {
    		edt_enddate_end.setText(DateUtil.dateFormatWithDateNum(y, m+1, d));
    	}
    };

	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		mode = AppConstant.SYNC_LOADMORE;
		mPullToRefreshView.postDelayed(new Runnable() {
			@Override
			public void run() {
				loadStockList();
			}
		}, 500);
	}

	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		mode = AppConstant.SYNC_REFRESH;
		mPullToRefreshView.postDelayed(new Runnable() {
			@Override
			public void run() {
				page_num = 1;
				loadStockList();
			}
		}, 500);
	}
	
}
