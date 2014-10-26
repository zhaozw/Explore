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
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.explore.android.R;
import com.explore.android.core.base.BaseHttpFragment;
import com.explore.android.core.model.BaseKeyValue;
import com.explore.android.core.util.DateUtil;
import com.explore.android.mobile.activity.ExHomeActivity;
import com.explore.android.mobile.activity.out.OutCreateDTCActivity;
import com.explore.android.mobile.activity.out.OutCreateSALActivity;
import com.explore.android.mobile.activity.out.OutCreateTTCActivity;
import com.explore.android.mobile.activity.out.OutDetailActivity;
import com.explore.android.mobile.activity.out.OutSimpleDetailActivity;
import com.explore.android.mobile.adapter.OutListItemAdapter;
import com.explore.android.mobile.adapter.SimpleSpinnerAdapter;
import com.explore.android.mobile.common.ExCacheManager;
import com.explore.android.mobile.constants.ActivityCode;
import com.explore.android.mobile.constants.AppConstant;
import com.explore.android.mobile.constants.DataConstants;
import com.explore.android.mobile.constants.RequestConstants;
import com.explore.android.mobile.constants.ResponseConstant;
import com.explore.android.mobile.constants.SDConstants;
import com.explore.android.mobile.data.DataSource;
import com.explore.android.mobile.data.cache.GlobalData;
import com.explore.android.mobile.data.predata.AsynPreData;
import com.explore.android.mobile.data.predata.SDSearchPreData;
import com.explore.android.mobile.data.request.ExRequest;
import com.explore.android.mobile.data.request.OutCusDeptExtraRequest;
import com.explore.android.mobile.data.request.OutSearchRequest;
import com.explore.android.mobile.model.Out;
import com.explore.android.mobile.view.PullToRefreshView;
import com.explore.android.mobile.view.PullToRefreshView.OnFooterRefreshListener;
import com.explore.android.mobile.view.PullToRefreshView.OnHeaderRefreshListener;

/**
 * 销售管理
 * @author Ryan
 */
public class OutManageFragment extends BaseHttpFragment 
        implements OnHeaderRefreshListener,OnFooterRefreshListener{

	private static final int REQUEST_GETDEPT = 1;
	private static final int REQUEST_DEPTCHANGE = 2;
	private static final int REQUEST_OUTSEARCH = 3;
	private static final int REQUESTCODE_SHIPTO = 1;
	private static final int REQUESTCODE_SHIPFROM = 2;
	private static final int REQUESTCODE_LIST = 3;
	private static final int REQUESTCODE_VATTO = 4;
	private static final String DEPTEXTRA_TYPE = "W";

	private ScrollView condition_layout;
	private Spinner spi_customerDept;
	private Spinner spi_warehouse;
	private EditText edt_outCode2;
	private EditText edt_outTime1;
	private EditText edt_outTime2;
	private EditText edt_remarks;
	private EditText edt_shipFrom;
	private EditText edt_shipTo;
	private EditText edt_vatTo;
	private Button btn_shipFrom;
	private Button btn_shipTo;
	private Button btn_vatTo;
	private Button btn_outTime1;
	private Button btn_outTime2;
	private Button btn_search;
	private Button btn_reset;
	private List<BaseKeyValue> warehouseList;
	private SimpleSpinnerAdapter cusDeptAdapter;
	private SimpleSpinnerAdapter warehouseAdapter;
	private PopupWindow popWindow;

	private ViewStub result_stub;
	private RelativeLayout search_layout;
	private Button show_condition_btn;
	private ListView listView;
	private PullToRefreshView mPullToRefreshView;
	private List<Out> outList;
	private OutListItemAdapter outListAdapter;

	private ExCacheManager cacheManager;
	private BaseKeyValue shipToKv;
	private BaseKeyValue shipFromKv;
	private BaseKeyValue vatToKv;
	private int year,month,date;
	private String deptId;
	private static int page_num = 1;
	private int mode;
	private boolean isStubInflate;

	public static OutManageFragment newInstance(int navCode) {
		OutManageFragment fragment = new OutManageFragment();
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
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	protected int setLayoutId() {
		return R.layout.frag_outman_layout;
	}

	@Override
	public void initViews(View view) {
		// condition views
		condition_layout = (ScrollView) view.findViewById(R.id.frag_outman_condition_scv);
		edt_outCode2 = (EditText) view.findViewById(R.id.frag_edt_outman_outcode2);
		edt_remarks = (EditText) view.findViewById(R.id.frag_edt_outman_remarks);
		spi_customerDept = (Spinner) view.findViewById(R.id.frag_spi_outman_cusdept);
		spi_warehouse = (Spinner) view.findViewById(R.id.frag_spi_outman_warehouse);
		edt_shipFrom = (EditText) view.findViewById(R.id.frag_edt_outman_shipfrom);
		btn_shipFrom = (Button) view.findViewById(R.id.frag_btn_outman_shipfrom);
		edt_shipTo = (EditText) view.findViewById(R.id.frag_edt_outman_shipto);
		btn_shipTo = (Button) view.findViewById(R.id.frag_btn_outman_shipto);
		edt_vatTo = (EditText) view.findViewById(R.id.frag_edt_outman_vatto);
		btn_vatTo = (Button) view.findViewById(R.id.frag_btn_outman_vatto);
		edt_outTime1 = (EditText) view.findViewById(R.id.frag_edt_outman_outtime1);
		edt_outTime2 = (EditText) view.findViewById(R.id.frag_edt_outman_outtime2);
		btn_outTime1 = (Button) view.findViewById(R.id.frag_btn_outman_outtime1);
		btn_outTime2 = (Button) view.findViewById(R.id.frag_btn_outman_outtime2);
		btn_search = (Button) view.findViewById(R.id.frag_btn_outman_search);
		btn_reset = (Button) view.findViewById(R.id.frag_resetbtn_outman_search);
		
		result_stub = (ViewStub) view.findViewById(R.id.frag_outman_result_stub);
	}

	@Override
	public void initValues() {
		page_num = 1;
		if(getActivity() != null && getActivity() instanceof ExHomeActivity){
			shipToKv = new BaseKeyValue("", "");
			shipFromKv = new BaseKeyValue("", "");
			vatToKv = new BaseKeyValue("", "");
			cacheManager = ExCacheManager.getInstance(getActivity());
			Calendar cal = Calendar.getInstance();
			year = cal.get(Calendar.YEAR);
			month = cal.get(Calendar.MONTH);
			date = cal.get(Calendar.DATE);
			
			SDSearchPreData.init();
			warehouseList = new ArrayList<BaseKeyValue>();
			warehouseList.add(new BaseKeyValue(getResources().getString(R.string.out_warehouse), ""));
			warehouseAdapter = new SimpleSpinnerAdapter(getActivity(), warehouseList);
			cusDeptAdapter = new SimpleSpinnerAdapter(getActivity(), GlobalData.customerDeptList);
			spi_customerDept.setAdapter(cusDeptAdapter);
			spi_warehouse.setAdapter(warehouseAdapter);
			spi_warehouse.setEnabled(false);
			
			if(GlobalData.customerDeptList.size() == 0){
				loadCustomerDept();
			}
		}
	}

	@Override
	public void initListener() {
		
		btn_search.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(!isStubInflate) {
					initStubView();
				}
				loadOutList();
				condition_layout.setVisibility(View.GONE);
				search_layout.setVisibility(View.VISIBLE);
			}
		});
		
		btn_reset.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				resetData();
			}
		});
		
		btn_shipTo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getShiptoData(REQUESTCODE_SHIPTO);
			}
		});

		btn_vatTo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getShiptoData(REQUESTCODE_VATTO);
			}
		});

		btn_shipFrom.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getShiptoData(REQUESTCODE_SHIPFROM);
			}
		});

		btn_outTime1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				DatePickerDialog dlg = new DatePickerDialog(getActivity(), onOutTimeDateSetListener1, year, month, date);
				dlg.setCanceledOnTouchOutside(true);
				dlg.show();
			}
		});

		btn_outTime2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DatePickerDialog dlg = new DatePickerDialog(getActivity(), onOutTimeDateSetListener2, year, month, date);
				dlg.setCanceledOnTouchOutside(true);
				dlg.show();
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
	}

	private void initStubView(){
		View stubView = result_stub.inflate();
		// search result veiws
		search_layout = (RelativeLayout) stubView.findViewById(R.id.search_result_layout);
		mPullToRefreshView = (PullToRefreshView) stubView.findViewById(R.id.frag_search_pullrefresh_view);
		show_condition_btn = (Button) stubView.findViewById(R.id.frag_search_showcond_btn);
		listView = (ListView) stubView.findViewById(R.id.frag_result_list);

		outList = new ArrayList<Out>();
		outListAdapter = new OutListItemAdapter(getActivity(),outList);
		listView.setAdapter(outListAdapter);
		mPullToRefreshView.setOnHeaderRefreshListener(OutManageFragment.this);
		mPullToRefreshView.setOnFooterRefreshListener(OutManageFragment.this);

		show_condition_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				condition_layout.setVisibility(View.VISIBLE);
				search_layout.setVisibility(View.GONE);
				if( getAsynDataTask() != null){
					getAsynDataTask().cancel(true);
				}
				if( getSubmitTask() != null){
					getSubmitTask().cancel(true);
				}
				clearList();
			}
		});

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
				String outType1 = outList.get(position).getOutType();
				String outType2 = outList.get(position).getOutType2();
				String outType;
				
				if(SDConstants.OUTTYPE_SAL.equals(outType1) && !SDConstants.OUTTYPE2_GEN.equals(outType2)){
					outType = outType2;
				} else {
					outType = outType1;
				}
				
				//需要考虑是否代垫
				if("SAL".equals(outType) || "TTC".equals(outType) || "DTC".equals(outType)){
					Intent intent = new Intent();
					intent.putExtra("outId", outList.get(position).getOutId());
					intent.putExtra("sts", outList.get(position).getSts());
					intent.putExtra("outtype", outType);
					intent.setClass(getActivity(), OutDetailActivity.class);
//					startActivity(intent);
					startActivityForResult(intent, REQUESTCODE_LIST);
				}else{
					Intent intent = new Intent();
					intent.putExtra("outId", outList.get(position).getOutId());
					intent.setClass(getActivity(), OutSimpleDetailActivity.class);
					startActivity(intent);
				}
			}
		});
		
		isStubInflate = true;
	}

	@Override
	public void showLoading() {
	}

	@Override
	public void dismissLoading() {
	}

	@Override
	public void handlerResponse(String response, int what) {
		switch(what){
		case REQUEST_GETDEPT:
			GlobalData.initCustomerDept(response);
			cusDeptAdapter.notifyDataSetChanged();
			break;

		case REQUEST_DEPTCHANGE:
			bindCusDeptExtraData(response, false);
			break;

		case REQUEST_OUTSEARCH:
			bindOutListData(response);
			break;

		default:
			break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		//super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
		case ActivityCode.OUT_DETAIL_CHANGES:
			clearList();
			loadOutList();
			break;
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

	private void customerDeptDataChange(boolean available){
		if(!available){
			spi_warehouse.setEnabled(false);
		}
		spi_warehouse.setSelection(0);
	}

	private void resetData(){
		spi_customerDept.setSelection(0);
		spi_warehouse.setSelection(0);
		edt_outCode2.setText("");
		edt_outTime1.setText("");
		edt_outTime2.setText("");
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

	private void getShiptoData(int requestCode){
		if (REQUESTCODE_SHIPTO == requestCode || REQUESTCODE_SHIPFROM == requestCode){
			AsynPreData preData = new AsynPreData();
			preData.setJsonName("CUSTOMERDEPTNAME");
			preData.setJsonTitle("SHIPTOLIST");
			preData.setJsonValue("CUSTOMERDEPTID");
			preData.setUrl(RequestConstants.CUSTOMER_SEARCHBY_KEYWORD);
			preData.setTitleRes(R.string.dataloader_title_customer);
			preData.setType("SHIPTO");
			String[] params1 = new String[2];
			params1[0] = "CUSTOMERDEPTID";
			params1[1] = GlobalData.customerDeptList.get(spi_customerDept.getSelectedItemPosition()).getValue();
			preData.setExtraParams(params1);
			startActivityForResult(DataSource.getAsynDataIntent(getActivity(), preData), requestCode);
		} else if (REQUESTCODE_VATTO == requestCode) {
			AsynPreData preData = new AsynPreData();
			preData.setJsonName("CUSTOMERDEPTNAME");
			preData.setJsonTitle("VATTOLIST");
			preData.setJsonValue("CUSTOMERDEPTID");
			preData.setUrl(RequestConstants.CUSTOMER_SEARCHBY_KEYWORD);
			preData.setTitleRes(R.string.dataloader_title_vatto);
			preData.setType("VATTO");
			String[] params2 = new String[2];
			params2[0] = "CUSTOMERDEPTID";
			params2[1] = GlobalData.customerDeptList.get(spi_customerDept.getSelectedItemPosition()).getValue();
			preData.setExtraParams(params2);
			startActivityForResult(DataSource.getAsynDataIntent(getActivity(), preData), requestCode);
		}
	}

	private void loadCustomerDept(){
		ExRequest request = new ExRequest();
		request.setUserId(getPreferences().getUserId());
		request.setToken(getPreferences().getTokenLast8());
		asynDataRequest(RequestConstants.OUT_GET_CUSTOMERDEPT, request.toJsonString(), REQUEST_GETDEPT);
	}

	private void loadOutList(){
		OutSearchRequest request = new OutSearchRequest();
		request.setUserId(getPreferences().getUserId());
		request.setToken(getPreferences().getTokenLast8());
		request.setCustomerDept(GlobalData.customerDeptList.get(spi_customerDept.getSelectedItemPosition()).getValue());
		request.setWarehouse(warehouseList.get(spi_warehouse.getSelectedItemPosition()).getValue());
		request.setVatTo(vatToKv.getValue());
		request.setOutCode2(edt_outCode2.getText().toString().trim());
		request.setOutTimeBegin(edt_outTime1.getText().toString().trim());
		request.setOutTimeEnd(edt_outTime2.getText().toString().trim());
		if(shipToKv != null) {
			request.setShipTo(shipToKv.getValue());
		}
		if (shipFromKv != null) {
			request.setShipFrom(shipFromKv.getValue());
		}
		request.setOutType(SDConstants.OUTTYPE_SAL);
		request.setRemarks(edt_remarks.getText().toString().trim());
		request.setSts("NEW,FIE,DSH");
		request.setPsize(RequestConstants.SEARCH_PAGE_SIZE);
		request.setPageNum(page_num);
		asynDataRequest(RequestConstants.OUT_OUTSEARCH, request.toJsonString(), REQUEST_OUTSEARCH);
	}

	private void loadDeptExtraData(String id){
		OutCusDeptExtraRequest request = new OutCusDeptExtraRequest();
		deptId = id;
		request.setUserId(getPreferences().getUserId());
		request.setToken(getPreferences().getTokenLast8());
		request.setDeptId(deptId);
		request.setType(DEPTEXTRA_TYPE);
		asynDataRequest(RequestConstants.OUT_GET_DEPT_EXTRA,request.toJsonString(), REQUEST_DEPTCHANGE);
	}

	private void bindOutListData(String dataStr){

		try {
			JSONObject json = new JSONObject(dataStr);
			if(ResponseConstant.OK.equals(json.get(ResponseConstant.STATUS))){
				JSONArray jsonArray = json.getJSONArray("OUTS");
				int length = jsonArray.length();
				if(1 == page_num){
					outList.clear();
				}
				if (length > 0) {
					page_num ++;
					for (int i = 0; i < length; i++) {
						Out out = new Out();
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						out.setOutId(jsonObject.getString("OUTID"));
						out.setOutCode2(jsonObject.getString("OUTCODE2"));
						out.setOutType(jsonObject.getString("OUTTYPE"));
						out.setOutType2(jsonObject.getString("OUTTYPE2"));
						out.setCustomerDept(jsonObject.getString("CUSTOMERDEPT"));
						out.setWarehouse(jsonObject.getString("WAREHOUSE"));
						out.setShipTo(jsonObject.getString("SHIPTO"));
						out.setVatTo(jsonObject.getString("VATTO"));
						out.setCreateByTime(jsonObject.getString("CREATEBYTIME"));
						out.setSts(jsonObject.getString("STS"));
						out.setOutMoney1(jsonObject.getString("OUTMONEY1"));
						outList.add(out);
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
				outListAdapter.notifyDataSetChanged();
				
				if(AppConstant.SYNC_LOADMORE == mode) {
					mPullToRefreshView.onFooterRefreshComplete();
				} else if (AppConstant.SYNC_REFRESH == mode) {
					String nowTime = DateUtil.dateFormatWithDayTime(new Date());
					mPullToRefreshView.onHeaderRefreshComplete(nowTime);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void bindCusDeptExtraData(String dataStr, boolean isFromCache){
		try {
			JSONObject json = new JSONObject(dataStr);
			if(ResponseConstant.OK.equals(json.get(ResponseConstant.STATUS))){
				JSONArray warehouseArray = json.getJSONArray("WAREHOUSES");
				warehouseList.clear();
				warehouseList.add(new BaseKeyValue(getResources().getString(R.string.out_warehouse), ""));
				int length1 = warehouseArray.length();
				for (int i = 0; i < length1; i++) {
					BaseKeyValue info = new BaseKeyValue();
					JSONObject jsonObject = warehouseArray.getJSONObject(i);
					info.setValue(jsonObject.getString("CUSTOMERDEPTID"));
					info.setKey(jsonObject.getString("CUSTOMERDEPTNAME"));
					warehouseList.add(info);
				}
				warehouseAdapter.notifyDataSetChanged();
				spi_warehouse.setSelection(0);
				if(!isFromCache){
					cacheManager.addOutCusDeptExtraCache(dataStr, DEPTEXTRA_TYPE, deptId);
				}
				spi_warehouse.setEnabled(true);
				btn_shipTo.setEnabled(true);
				btn_shipFrom.setEnabled(true);
				btn_vatTo.setEnabled(true);
			} else {
				showToast(R.string.msg_response_failed);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			showToast(R.string.msg_response_failed);
		}
	}

	private DatePickerDialog.OnDateSetListener onOutTimeDateSetListener1 = new DatePickerDialog.OnDateSetListener() {  
        public void onDateSet(DatePicker view, int y, int m, int d) {
        	edt_outTime1.setText(DateUtil.dateFormatWithDateNum(y, m+1, d));
        }
    };

    private DatePickerDialog.OnDateSetListener onOutTimeDateSetListener2 = new DatePickerDialog.OnDateSetListener() {  
        public void onDateSet(DatePicker view, int y, int m, int d) {
        	edt_outTime2.setText(DateUtil.dateFormatWithDateNum(y, m+1, d));
        }
    };

    private void clearList(){
		page_num = 1;
		if(outList != null){
			outList.clear();
		}
	}

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.single_create_menu, menu);
        ActionBar actionBar = ((ActionBarActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_create) {
        	showPopupWindow(condition_layout);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showPopupWindow(View view){
    	LayoutInflater inflater = LayoutInflater.from(getActivity());
    	View popView = inflater.inflate(R.layout.popupwindow_create_out, null);
    	popWindow = new PopupWindow(popView,LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT,true);
    	Button btn_sal = (Button) popView.findViewById(R.id.popwin_btn_sal);
    	Button btn_ttc = (Button) popView.findViewById(R.id.popwin_btn_ttc);
    	Button btn_dtc = (Button) popView.findViewById(R.id.popwin_btn_dtc);

    	btn_sal.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent sal_intent = new Intent(getActivity(), OutCreateSALActivity.class);
				startActivity(sal_intent);
				popWindow.dismiss();
			}
		});

    	btn_dtc.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent dtc_intent = new Intent(getActivity(), OutCreateDTCActivity.class);
				startActivity(dtc_intent);
				popWindow.dismiss();
			}
		});

    	btn_ttc.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent ttc_intent = new Intent(getActivity(), OutCreateTTCActivity.class);
				startActivity(ttc_intent);
				popWindow.dismiss();
			}
		});

    	Rect frame = new Rect();
    	getActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
    	int statusBarHeight = frame.top;
    	int acHeight = statusBarHeight + getActivity().getActionBar().getHeight();

    	popWindow.setFocusable(true);
    	popWindow.setTouchable(true);
    	popWindow.setOutsideTouchable(true);
    	popWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
    	popWindow.showAtLocation(view, Gravity.TOP|Gravity.RIGHT, 5, acHeight+5);
    	popWindow.update();
    }

	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		mode = AppConstant.SYNC_LOADMORE;
		mPullToRefreshView.postDelayed(new Runnable() {
			@Override
			public void run() {
				loadOutList();
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
				loadOutList();
			}
		}, 500);
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (condition_layout != null) {
			condition_layout.setVisibility(View.VISIBLE);
		}
		if (search_layout != null) {
			search_layout.setVisibility(View.GONE);
		}
		clearList();
	}
}
