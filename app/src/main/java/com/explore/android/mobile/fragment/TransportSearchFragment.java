package com.explore.android.mobile.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
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
import android.widget.Toast;

import com.explore.android.R;
import com.explore.android.core.base.BaseHttpFragment;
import com.explore.android.core.model.BaseKeyValue;
import com.explore.android.core.util.DateUtil;
import com.explore.android.mobile.activity.ExHomeActivity;
import com.explore.android.mobile.activity.transport.TransportDetailActivity;
import com.explore.android.mobile.adapter.SimpleSpinnerAdapter;
import com.explore.android.mobile.adapter.TransportListItemAdapter;
import com.explore.android.mobile.common.ExCacheManager;
import com.explore.android.mobile.common.SharePreferencesManager;
import com.explore.android.mobile.constants.ActivityCode;
import com.explore.android.mobile.constants.AppConstant;
import com.explore.android.mobile.constants.DataConstants;
import com.explore.android.mobile.constants.RequestConstants;
import com.explore.android.mobile.constants.ResponseConstant;
import com.explore.android.mobile.data.DataSource;
import com.explore.android.mobile.data.cache.GlobalData;
import com.explore.android.mobile.data.predata.AsynPreData;
import com.explore.android.mobile.data.predata.SDSearchPreData;
import com.explore.android.mobile.data.request.ExRequest;
import com.explore.android.mobile.data.request.OutCusDeptExtraRequest;
import com.explore.android.mobile.data.request.OutProjectExtraRequest;
import com.explore.android.mobile.data.request.TransportSearchRequest;
import com.explore.android.mobile.model.Transport;
import com.explore.android.mobile.view.PullToRefreshView;
import com.explore.android.mobile.view.PullToRefreshView.OnFooterRefreshListener;
import com.explore.android.mobile.view.PullToRefreshView.OnHeaderRefreshListener;

public class TransportSearchFragment extends BaseHttpFragment implements OnHeaderRefreshListener,OnFooterRefreshListener{
	
	private static final int REQUEST_PRE_DEPT = 1;
	private static final int REQUEST_PRE_DEPTEXTRA = 2;
	private static final int REQUEST_PRE_PROJECTEXTRA = 3;
	private static final int REQUEST_SEARCH = 4;
	
	private static final int REQUESTCODE_SHIPTO = 1;
	private static final int REQUESTCODE_SHIPFROM = 2;
	private static final int REQUESTCODE_VATTO = 3;
	
	private static final String DEPTEXTRA_TYPE = "WP";
	
	@SuppressLint("SimpleDateFormat")
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	private ScrollView condition_layout;
	private Button search_btn;
	private Button reset_btn;
	private Button btn_transtime_start;
	private Button btn_transtime_end;
	private Spinner spi_customerdept;
	private Spinner spi_warehouse;
	private Spinner spi_warehouseto;
	private Spinner spi_project;
	private Spinner spi_vendor;
	private Spinner spi_sts;
	private Spinner spi_isdd;
	private Spinner spi_outtype;
	private EditText edt_outcode1;
	private EditText edt_outcode2;
	private EditText edt_transcode1;
	private EditText edt_transcode2;
	private EditText edt_transtime_start;
	private EditText edt_transtime_end;
	private EditText edt_remarks;
	private EditText edt_shipTo;
	private EditText edt_shipFrom;
	private EditText edt_vatTo;
	private Button btn_shipTo;
	private Button btn_shipFrom;
	private Button btn_vatTo;
	
	private ViewStub result_stub;
	private RelativeLayout search_layout;
	private ListView listView;
	private List<Transport> transList;
	private TransportListItemAdapter adapter;
	private Button show_condition_btn;
	private PullToRefreshView mPullToRefreshView;
	
	private List<BaseKeyValue> projectList;
	private List<BaseKeyValue> vendorList;
	private List<BaseKeyValue> warehouseList;
	private List<BaseKeyValue> warehouseToList;
	private SimpleSpinnerAdapter cusDeptAdapter;
	private SimpleSpinnerAdapter projectAdapter;
	private SimpleSpinnerAdapter vendorAdapter;
	private SimpleSpinnerAdapter warehouseAdapter;
	private SimpleSpinnerAdapter warehouseToAdapter;
	private SimpleSpinnerAdapter outtypeAdapter;
	private SimpleSpinnerAdapter stsAdapter;
	private SimpleSpinnerAdapter isDdAdapter;
	
	private ExCacheManager cacheManager;
	private Calendar cal;
	private int year, month, date;
	private String projectId,deptId;
	private BaseKeyValue shipToKv;
	private BaseKeyValue shipFromKv;
	private BaseKeyValue vatToKv;
	
	private int mode;	
	private static int page_num = 1;
	private boolean isStubInflate;
	
	public static TransportSearchFragment newInstance(int navCode) {
		TransportSearchFragment fragment = new TransportSearchFragment();
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
		return R.layout.frag_transport_search;
	}
	
	@Override
	public void initViews(View v) {
		condition_layout = (ScrollView) v.findViewById(R.id.frag_transport_condition_scv);
		search_btn = (Button) v.findViewById(R.id.frag_tranportsearch_btn);
		reset_btn = (Button) v.findViewById(R.id.frag_tranportsearch_resetbtn);
		spi_customerdept = (Spinner) v.findViewById(R.id.frag_spi_transsearch_cusdept);
		spi_isdd = (Spinner) v.findViewById(R.id.frag_spi_transsearch_isdd);
		spi_outtype = (Spinner) v.findViewById(R.id.frag_spi_transsearch_outtype);
		spi_project = (Spinner) v.findViewById(R.id.frag_spi_transsearch_project);
		spi_vendor = (Spinner) v.findViewById(R.id.frag_spi_transsearch_vendor);
		spi_warehouse = (Spinner) v.findViewById(R.id.frag_spi_transsearch_warehouse);
		spi_warehouseto = (Spinner) v.findViewById(R.id.frag_spi_transsearch_warehouseto);
		spi_sts = (Spinner) v.findViewById(R.id.frag_spi_transsearch_sts);
		edt_shipTo = (EditText) v.findViewById(R.id.frag_edt_transsearch_shipto);
		edt_shipFrom = (EditText) v.findViewById(R.id.frag_edt_transsearch_shipfrom);
		edt_vatTo = (EditText) v.findViewById(R.id.frag_edt_transsearch_vatto);
		btn_shipTo = (Button) v.findViewById(R.id.frag_btn_transsearch_shipto);
		btn_shipFrom = (Button) v.findViewById(R.id.frag_btn_transsearch_shipfrom);
		btn_vatTo = (Button) v.findViewById(R.id.frag_btn_transsearch_vatto);
		edt_remarks = (EditText) v.findViewById(R.id.frag_edt_transsearch_remarks);
		edt_outcode1 = (EditText) v.findViewById(R.id.frag_edt_transsearch_outcode1);
		edt_outcode2 = (EditText) v.findViewById(R.id.frag_edt_transsearch_outcode2);
		edt_transcode1 = (EditText) v.findViewById(R.id.frag_edt_transsearch_trascode1);
		edt_transcode2 = (EditText) v.findViewById(R.id.frag_edt_transsearch_trascode2);
		edt_transtime_start = (EditText) v.findViewById(R.id.frag_edt_transsearch_transtime_start);
		edt_transtime_end = (EditText) v.findViewById(R.id.frag_edt_transsearch_transtime_end);
		btn_transtime_start = (Button) v.findViewById(R.id.frag_btn_transsearch_transtime_start);
		btn_transtime_end = (Button) v.findViewById(R.id.frag_btn_transsearch_transtime_end);
		
		result_stub = (ViewStub) v.findViewById(R.id.frag_tranportsearch_result_stub);
	}

	@Override
	public void initValues() {
		page_num = 1;
		isStubInflate = false;
		shipToKv = new BaseKeyValue("", "");
		shipFromKv = new BaseKeyValue("", "");
		vatToKv = new BaseKeyValue("", "");
			
		cacheManager = ExCacheManager.getInstance(getActivity());
			
		cal = Calendar.getInstance();
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH);
		date = cal.get(Calendar.DATE);
			
		projectList = new ArrayList<BaseKeyValue>();
		vendorList = new ArrayList<BaseKeyValue>();
		warehouseList = new ArrayList<BaseKeyValue>();
		warehouseToList = new ArrayList<BaseKeyValue>();
			
		SDSearchPreData.init();
		projectList.add(new BaseKeyValue(getResources().getString(R.string.out_project), ""));
		vendorList.add(new BaseKeyValue(getResources().getString(R.string.out_vendor), ""));
		warehouseList.add(new BaseKeyValue(getResources().getString(R.string.out_warehouse), ""));
		warehouseToList.add(new BaseKeyValue(getResources().getString(R.string.out_warehouseto), ""));
			
		cusDeptAdapter = new SimpleSpinnerAdapter(getActivity(), GlobalData.customerDeptList);
		projectAdapter = new SimpleSpinnerAdapter(getActivity(), projectList);
		vendorAdapter = new SimpleSpinnerAdapter(getActivity(), vendorList);
		warehouseAdapter = new SimpleSpinnerAdapter(getActivity(), warehouseList);
		outtypeAdapter = new SimpleSpinnerAdapter(getActivity(), SDSearchPreData.outtypeList);
		stsAdapter = new SimpleSpinnerAdapter(getActivity(), SDSearchPreData.transportStsList);
		warehouseToAdapter = new SimpleSpinnerAdapter(getActivity(), warehouseToList);
		isDdAdapter = new SimpleSpinnerAdapter(getActivity(), SDSearchPreData.isDdList);
			
		spi_customerdept.setAdapter(cusDeptAdapter);
		spi_project.setAdapter(projectAdapter);
		spi_vendor.setAdapter(vendorAdapter);
		spi_warehouse.setAdapter(warehouseAdapter);
		spi_warehouseto.setAdapter(warehouseToAdapter);
		spi_outtype.setAdapter(outtypeAdapter);
		spi_sts.setAdapter(stsAdapter);
		spi_isdd.setAdapter(isDdAdapter);
			
		spi_project.setEnabled(false);
		spi_vendor.setEnabled(false);
		spi_warehouseto.setEnabled(false);
		spi_warehouse.setEnabled(false);
			
		if(GlobalData.customerDeptList.size() == 0){
			loadCustomerDept();
		}
	}

	private void initStubView() {
		View stubView = result_stub.inflate();
		listView = (ListView) stubView.findViewById(R.id.frag_result_list);
		show_condition_btn = (Button) stubView.findViewById(R.id.frag_search_showcond_btn);
		search_layout = (RelativeLayout) stubView.findViewById(R.id.search_result_layout);
		mPullToRefreshView = (PullToRefreshView) stubView.findViewById(R.id.frag_search_pullrefresh_view);
		
		transList = new ArrayList<Transport>();
		adapter = new TransportListItemAdapter(getActivity(), transList);
		listView.setAdapter(adapter);
		
		mPullToRefreshView.setOnHeaderRefreshListener(TransportSearchFragment.this);
		mPullToRefreshView.setOnFooterRefreshListener(TransportSearchFragment.this);
		
		show_condition_btn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				condition_layout.setVisibility(View.VISIBLE);
				search_layout.setVisibility(View.GONE);
				cancelTask();
				clearList();
			}
		});
		
		listView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), TransportDetailActivity.class);
				intent.putExtra("transId", transList.get(position).getTransportId());
				startActivity(intent);
			}
		});
		
		isStubInflate = true;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public void initListener() {
		
		btn_transtime_start.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DatePickerDialog dlg = new DatePickerDialog(getActivity(), transportTimeStartListener, year, month, date);
				dlg.setCanceledOnTouchOutside(true);
				dlg.show();
			}
		});
		
		btn_transtime_end.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DatePickerDialog dlg = new DatePickerDialog(getActivity(), transportTimeEndListener, year, month, date);
				dlg.setCanceledOnTouchOutside(true);
				dlg.show();
			}
		});

		search_btn.setOnClickListener(new OnClickListener() {
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
		
		btn_shipTo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getShiptoData(REQUESTCODE_SHIPTO);
			}
		});
		
		btn_shipFrom.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getShiptoData(REQUESTCODE_SHIPFROM);
			}
		});
		
		btn_vatTo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getShiptoData(REQUESTCODE_VATTO);
			}
		});
		
		reset_btn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				
				spi_customerdept.setSelection(0);
				spi_project.setSelection(0);
				spi_vendor.setSelection(0);
				spi_warehouse.setSelection(0);
				spi_outtype.setSelection(0);
				spi_sts.setSelection(0);
				spi_warehouseto.setSelection(0);
				
				edt_transtime_start.setText("");
				edt_transtime_end.setText("");
				edt_transcode1.setText("");
				edt_transcode2.setText("");
				edt_outcode1.setText("");
				edt_outcode2.setText("");
				edt_remarks.setText("");
				
				edt_shipFrom.setText("");
				edt_shipTo.setText("");
				edt_vatTo.setText("");
				shipFromKv = null;
				shipToKv = null;
				shipToKv = null;
				shipToKv = new BaseKeyValue("", "");
				shipFromKv = new BaseKeyValue("", "");
				vatToKv = new BaseKeyValue("", "");
			}
		});
		
		spi_customerdept.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View view, int position, long id) {
				if(GlobalData.customerDeptList.size() > 0){
					if(cacheManager.getOutCusDeptExtraCache(DEPTEXTRA_TYPE, GlobalData.customerDeptList.get(position).getValue()) != null){
						bindCusDeptExtraData(cacheManager.getOutCusDeptExtraCache(DEPTEXTRA_TYPE, GlobalData.customerDeptList.get(position).getValue()), true);
					}else{
						loadDeptExtraData(GlobalData.customerDeptList.get(position).getValue());
					}
				} else {
					customerDeptDataChange(false);
					btn_shipTo.setEnabled(false);
					btn_shipFrom.setEnabled(false);
					btn_vatTo.setEnabled(false);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		
		spi_project.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View view, int position, long id) {
				if(position != 0){
					if(cacheManager.getProjectExtraCache(projectList.get(position).getValue()) != null){
						bindProjectExtraData(cacheManager.getProjectExtraCache(projectList.get(position).getValue()), true);
					}else{
						loadProjectExtraData(projectList.get(position).getValue());
					}
				} else {
					spi_vendor.setSelection(0);
					spi_vendor.setEnabled(false);
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
		case REQUEST_PRE_DEPT:
			GlobalData.initCustomerDept(response);
			cusDeptAdapter.notifyDataSetChanged();
			break;
			
		case REQUEST_PRE_DEPTEXTRA:
			bindCusDeptExtraData(response, false);
			break;
			
		case REQUEST_PRE_PROJECTEXTRA:
			bindProjectExtraData(response, false);
			break;
			
		case REQUEST_SEARCH:
			bindTransportData(response);
			break;

		default:
			break;
		}
	}
	
	private void getShiptoData(int requestCode){
		if (REQUESTCODE_SHIPTO == requestCode || REQUESTCODE_SHIPFROM == requestCode){
			AsynPreData preData = new AsynPreData();
			preData.setJsonName("CUSTOMERDEPTNAME");
			preData.setJsonTitle("SHIPTOLIST");
			preData.setJsonValue("CUSTOMERDEPTID");
			preData.setUrl(RequestConstants.CUSTOMER_SEARCHBY_KEYWORD);
			preData.setTitleRes(R.string.dataloader_title_customer);
			preData.setType("SHIPTO");
			String[] params = new String[2];
			params[0] = "CUSTOMERDEPTID";
			params[1] = GlobalData.customerDeptList.get(spi_customerdept.getSelectedItemPosition()).getValue();
			preData.setExtraParams(params);
			startActivityForResult(DataSource.getAsynDataIntent(getActivity(), preData), requestCode);
		} else if (REQUESTCODE_VATTO == requestCode) {
			AsynPreData preData = new AsynPreData();
			preData.setJsonName("CUSTOMERDEPTNAME");
			preData.setJsonTitle("VATTOLIST");
			preData.setJsonValue("CUSTOMERDEPTID");
			preData.setUrl(RequestConstants.CUSTOMER_SEARCHBY_KEYWORD);
			preData.setTitleRes(R.string.dataloader_title_vatto);
			preData.setType("VATTO");
			String[] params = new String[2];
			params[0] = "CUSTOMERDEPTID";
			params[1] = GlobalData.customerDeptList.get(spi_customerdept.getSelectedItemPosition()).getValue();
			preData.setExtraParams(params);
			startActivityForResult(DataSource.getAsynDataIntent(getActivity(), preData), requestCode);
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		//super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
		case ActivityCode.AYSN_DATA_RESULT:
			if (REQUESTCODE_SHIPTO == requestCode) {
				if(data.hasExtra(DataConstants.KVRESULT)){
					Bundle bundle = data.getBundleExtra(DataConstants.KVRESULT);
					if(shipToKv == null){
						shipToKv = new BaseKeyValue();
					}
					shipToKv.setKey(bundle.getString(DataConstants.NAME, ""));
					shipToKv.setValue(bundle.getString(DataConstants.VALUE, ""));
					edt_shipTo.setText(shipToKv.getKey());
				}
				
			} else if (REQUESTCODE_SHIPFROM == requestCode) {
				if(data.hasExtra(DataConstants.KVRESULT)){
					Bundle bundle = data.getBundleExtra(DataConstants.KVRESULT);
					if(shipFromKv == null) {
						shipFromKv = new BaseKeyValue();
					}
					shipFromKv.setKey(bundle.getString(DataConstants.NAME, ""));
					shipFromKv.setValue(bundle.getString(DataConstants.VALUE, ""));
					edt_shipFrom.setText(shipFromKv.getKey());
				}
				
			}else if(REQUESTCODE_VATTO == requestCode) {
				if(data.hasExtra(DataConstants.KVRESULT)){
					Bundle bundle = data.getBundleExtra(DataConstants.KVRESULT);
					if(vatToKv == null) {
						vatToKv = new BaseKeyValue();
					}
					vatToKv.setKey(bundle.getString(DataConstants.NAME, ""));
					vatToKv.setValue(bundle.getString(DataConstants.VALUE, ""));
					edt_vatTo.setText(vatToKv.getKey());
				}
			}
			break;
		default:
			break;
		}
	}
	
    private DatePickerDialog.OnDateSetListener transportTimeStartListener = new DatePickerDialog.OnDateSetListener() {  
        public void onDateSet(DatePicker view, int y, int m, int d) {
        	edt_transtime_start.setText(DateUtil.dateFormatWithDateNum(y, m+1, d));
        }
    };
    
    private DatePickerDialog.OnDateSetListener transportTimeEndListener = new DatePickerDialog.OnDateSetListener() {  
        public void onDateSet(DatePicker view, int y, int m, int d) {
        	edt_transtime_end.setText(DateUtil.dateFormatWithDateNum(y, m+1, d));
        }
    };
    
    private void customerDeptDataChange(boolean available){
		if(!available){
			spi_project.setEnabled(false);
			spi_vendor.setEnabled(false);
			spi_warehouse.setEnabled(false);
			spi_warehouseto.setEnabled(false);
		}
		spi_project.setSelection(0);
		spi_vendor.setSelection(0);
		spi_warehouse.setSelection(0);
		spi_warehouseto.setSelection(0);
	}
    
	private void loadStockList() {
		TransportSearchRequest request = new TransportSearchRequest();
		SharePreferencesManager preferences = SharePreferencesManager.getInstance(getActivity());
		request.setUserId(preferences.getUserId());
		request.setToken(preferences.getTokenLast8());
		request.setTransportCode1(edt_transcode1.getText().toString().trim());
		request.setTransprotCode2(edt_transcode2.getText().toString().trim());
		request.setTransprotStartTime(edt_transtime_start.getText().toString().trim());
		request.setTransportEndTime(edt_transtime_end.getText().toString().trim());
		request.setOutCode1(edt_outcode1.getText().toString().trim());
		request.setOutCode2(edt_outcode2.getText().toString().trim());
		request.setRemarks(edt_remarks.getText().toString().trim());
		request.setCustomerDeptId(GlobalData.customerDeptList.get(spi_customerdept.getSelectedItemPosition()).getValue());
		request.setProjectId(projectList.get(spi_project.getSelectedItemPosition()).getValue());
		request.setVendorId(vendorList.get(spi_vendor.getSelectedItemPosition()).getValue());
		request.setWarehouse(warehouseList.get(spi_warehouse.getSelectedItemPosition()).getValue());
		request.setWarehouseTo(warehouseToList.get(spi_warehouseto.getSelectedItemPosition()).getValue());
		request.setShipTo(shipToKv.getValue());
		request.setShipFrom(shipFromKv.getValue());
		request.setVatTo(vatToKv.getValue());
		request.setIsDd(SDSearchPreData.isDdList.get(spi_isdd.getSelectedItemPosition()).getValue());
		request.setSts(SDSearchPreData.whsList.get(spi_sts.getSelectedItemPosition()).getValue());
		request.setOutType(SDSearchPreData.outtypeList.get(spi_outtype.getSelectedItemPosition()).getValue());
		request.setPageNum(page_num);
		request.setpSize(RequestConstants.SEARCH_PAGE_SIZE);
		asynDataRequest(RequestConstants.TRANSPORT_SEARCH, request.toJsonString(), REQUEST_SEARCH);
	}
	
	private void loadCustomerDept(){
		ExRequest request = new ExRequest();
		request.setUserId(getPreferences().getUserId());
		request.setToken(getPreferences().getTokenLast8());
		asynDataRequest(RequestConstants.OUT_GET_CUSTOMERDEPT, request.toJsonString(), REQUEST_PRE_DEPT);
	}

	private void loadDeptExtraData(String id){
		OutCusDeptExtraRequest request = new OutCusDeptExtraRequest();
		deptId = id;
		request.setUserId(getPreferences().getUserId());
		request.setToken(getPreferences().getTokenLast8());
		request.setDeptId(deptId);
		request.setType(DEPTEXTRA_TYPE);
		asynDataRequest(RequestConstants.OUT_GET_DEPT_EXTRA, request.toJsonString(), REQUEST_PRE_DEPTEXTRA);
	}
	
	private void loadProjectExtraData(String id){
		OutProjectExtraRequest request = new OutProjectExtraRequest();
		projectId = id;
		request.setUserId(getPreferences().getUserId());
		request.setToken(getPreferences().getTokenLast8());
		request.setProjectId(projectId);
		asynDataRequest(RequestConstants.OUT_GET_PROJECT_EXTRA, request.toJsonString(), REQUEST_PRE_PROJECTEXTRA);
	}
	
	private void bindTransportData(String resStr) {
		try {
			JSONObject json = new JSONObject(resStr);
			if (ResponseConstant.OK.equals(json.get(ResponseConstant.STATUS))) {
				JSONArray jsonArray = json.getJSONArray("TRANSPORTLIST");
				int length = jsonArray.length();
				
				if(1 == page_num){
					transList.clear();
				}
				
				if (length > 0) {
					page_num ++;
					for(int i = 0; i < length; i++){
						JSONObject transJson = jsonArray.getJSONObject(i);
						Transport trans = new Transport();
						trans.setTransportId(transJson.getString("transportId"));
						trans.setOuttype(transJson.getString("outType"));
						trans.setCommitByName(transJson.getString("commitByName"));
						trans.setCommitByTime(transJson.getString("commitByTime"));
						trans.setCreateByName(transJson.getString("createByName"));
						trans.setCreateByTime(transJson.getString("createByTime"));
						trans.setWarehouse(transJson.getString("warehouse"));
						trans.setVatto(transJson.getString("vatTo"));
						trans.setTransportCode1(transJson.getString("transportCode1"));
						trans.setTransportCode2(transJson.getString("transportCode2"));
						transList.add(trans);
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
			transList = null;
		}
	}
	
	private void bindCusDeptExtraData(String dataStr, boolean isFromCache){
		try {
			JSONObject json = new JSONObject(dataStr);
			if(ResponseConstant.OK.equals(json.get(ResponseConstant.STATUS))){
				
				JSONArray warehouseArray = json.getJSONArray("WAREHOUSES");
				JSONArray projectArray = json.getJSONArray("PROJECTS");
						
				warehouseList.clear();
				warehouseList.add(new BaseKeyValue(getResources().getString(R.string.out_warehouse), ""));
				warehouseToList.clear();
				warehouseToList.add(new BaseKeyValue(getResources().getString(R.string.out_warehouseto), ""));
				projectList.clear();
				projectList.add(new BaseKeyValue(getResources().getString(R.string.out_project), ""));
						
				int length1 = warehouseArray.length();
				for (int i = 0; i < length1; i++) {
					BaseKeyValue info = new BaseKeyValue();
					JSONObject jsonObject = warehouseArray.getJSONObject(i);
					info.setValue(jsonObject.getString("CUSTOMERDEPTID"));
					info.setKey(jsonObject.getString("CUSTOMERDEPTNAME"));
					warehouseList.add(info);
					warehouseToList.add(info);
				}
						
						
				int length4 = projectArray.length();
				for (int i = 0; i < length4; i++) {
					BaseKeyValue info = new BaseKeyValue();
					JSONObject jsonObject = projectArray.getJSONObject(i);
					info.setValue(jsonObject.getString("CUSTOMERDEPTID"));
					info.setKey(jsonObject.getString("CUSTOMERDEPTNAME"));
					projectList.add(info);
				}
						
				projectAdapter.notifyDataSetChanged();
				warehouseAdapter.notifyDataSetChanged();
				warehouseToAdapter.notifyDataSetChanged();
						
				spi_project.setSelection(0);
				spi_warehouse.setSelection(0);
				spi_warehouseto.setSelection(0);
				
				if(!isFromCache){
					cacheManager.addOutCusDeptExtraCache(dataStr, DEPTEXTRA_TYPE, deptId);
				}
				
				spi_project.setEnabled(true);
				spi_warehouse.setEnabled(true);
				spi_warehouseto.setEnabled(true);
				btn_shipTo.setEnabled(true);
				btn_shipFrom.setEnabled(true);
				btn_vatTo.setEnabled(true);
			} else {
				showToast(R.string.msg_response_failed);
			}
		} catch (JSONException e) {
			showToast(R.string.msg_response_failed);
		}
	}
	
	private void bindProjectExtraData(String dataStr, boolean isFromCache){
		try {
			JSONObject json = new JSONObject(dataStr);
			if(ResponseConstant.OK.equals(json.get(ResponseConstant.STATUS))){
				
				JSONArray vendorArray = json.getJSONArray("VENDORS");
				vendorList.clear();
				vendorList.add(new BaseKeyValue(getResources().getString(R.string.out_vendor), ""));
						
				int length = vendorArray.length();
				for (int i = 0; i < length; i++) {
					BaseKeyValue info = new BaseKeyValue();
					JSONObject jsonObject = vendorArray.getJSONObject(i);
					info.setValue(jsonObject.getString("CUSTOMERDEPTID"));
					info.setKey(jsonObject.getString("CUSTOMERDEPTNAME"));
					vendorList.add(info);
				}
				
				spi_vendor.setSelection(0);
				spi_vendor.setEnabled(true);
						
				vendorAdapter.notifyDataSetChanged();
				
				if(!isFromCache){
					cacheManager.addProjectExtraCache(dataStr, projectId);
				}
			}
		} catch (JSONException e) {
			Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.msg_response_failed), Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
	}
	
	
	private void clearList(){
		page_num = 1;
		
		if(transList != null && transList.size()>0){
			transList.clear();
		}
	}

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
