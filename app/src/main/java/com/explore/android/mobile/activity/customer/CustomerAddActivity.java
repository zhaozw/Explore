package com.explore.android.mobile.activity.customer;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.explore.android.R;
import com.explore.android.core.base.BaseAsynTask;
import com.explore.android.core.base.BaseAsynTask.TaskOnPostExecuteListener;
import com.explore.android.core.base.BaseAsynTask.TaskResult;
import com.explore.android.core.http.AsynDataTask;
import com.explore.android.core.http.SubmitTask;
import com.explore.android.core.model.BaseKeyValue;
import com.explore.android.core.model.ExResponse;
import com.explore.android.mobile.adapter.SectionPagerAdapter;
import com.explore.android.mobile.common.SharePreferencesManager;
import com.explore.android.mobile.constants.AppStatus;
import com.explore.android.mobile.constants.ErrorConstants;
import com.explore.android.mobile.constants.RequestConstants;
import com.explore.android.mobile.constants.ResponseConstant;
import com.explore.android.mobile.data.request.CustomerAddPreRequest;
import com.explore.android.mobile.data.request.CustomerAddRequest;
import com.explore.android.mobile.model.CustomerArea;
import com.explore.android.mobile.model.KaType;
import com.explore.android.mobile.model.VatToCustomer;
import com.explore.android.mobile.view.DialogUtil;

public class CustomerAddActivity extends FragmentActivity {

	private static final int FRAG_ADDRESS = 0;
	private static final int FRAG_DEPT = 1;
	private static final int ITEM_NUMS = 2;

	private ViewPager viewPager;
	private SectionPagerAdapter adapter;
	private Fragment[] fragments;

	private RadioGroup tab_button_group;
	private RadioButton customer_address;
	private RadioButton customer_dept;

	private Button btn_save;
	private Button btn_reset;
	
	private String nameCN;
	private String nameCNs;
	private String nameEN;
	private String nameENs;
	private String companyCode;

	private AsynDataTask cusAddPreTask;
	private SubmitTask addCustomerTask;
	
	private CustomerAddRequest addRequest;
	private List<BaseKeyValue> customerDeptList;
	private List<KaType> kaTypeList;
	private List<CustomerArea> cusAreaList;
	private List<VatToCustomer> vatToList;
	
	private CustomerAddDeptFragment deptFragment;
	private CustomerAddAddressFragment addressFragment;
	private SharePreferencesManager preferences;
	
	private boolean isCustomerExist;
	private int cur_frag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_customer);

		initViews();
		initValues();
		initFragments();
		initListener();
	}

	private void initViews() {

		viewPager = (ViewPager) findViewById(R.id.act_cusadd_view_page);

		tab_button_group = (RadioGroup) findViewById(R.id.act_rgroud_cusadd);
		customer_address = (RadioButton) findViewById(R.id.act_cusadd_btn_address);
		customer_dept = (RadioButton) findViewById(R.id.act_cusadd_btn_dept);
		
		btn_save = (Button) findViewById(R.id.act_btn_cusadd_submit);
		btn_reset = (Button) findViewById(R.id.act_btn_cusadd_reset);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle(R.string.customer_title_create);
	}

	private void initValues() {
		preferences = SharePreferencesManager.getInstance(this);
		addRequest = new CustomerAddRequest();
		
		Intent init_intent = getIntent();
		nameCN = init_intent.getStringExtra("NAMECN");
		nameCNs = init_intent.getStringExtra("NAMECNS");
		nameEN = init_intent.getStringExtra("NAMEEN");
		nameENs = init_intent.getStringExtra("NAMEENS");
		companyCode = init_intent.getStringExtra("COMPANYCODE");

		customerDeptList = new ArrayList<BaseKeyValue>();
		kaTypeList = new ArrayList<KaType>();
		cusAreaList = new ArrayList<CustomerArea>();
		vatToList = new ArrayList<VatToCustomer>();
		loadAddPreData();
	}

	private void initListener() {
		tab_button_group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup arg0, int position) {
				switchScheduleMode(position);
			}
		});

		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				setTabChecked(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
		
		btn_save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				btn_save.setEnabled(false);
				deptFragment.setRequestData();
				addressFragment.setRequestData();
				initCustomerInfoData();
				addRequest.setUserId(preferences.getUserId());
				addRequest.setToken(preferences.getTokenLast8());
				int flag = 0;
				int temp_num = 0;
				
				if("GDR".equals(addRequest.getCusZqType()) && "Y".equals(addRequest.getCusIsDh())){
					if(CustomerAddRequest.MAX_COUNT_GDR_DH == addRequest.getCount()){
						flag = 1;
						
					}else {
						temp_num = CustomerAddRequest.MAX_COUNT_GDR_DH - addRequest.getCount();
						flag = 2;
					}
				} else if("GDR".equals(addRequest.getCusZqType()) && "N".equals(addRequest.getCusIsDh())){
					if(CustomerAddRequest.MAX_COUNT_GDR_NODH == addRequest.getCount()){
						flag = 1;
						
					}else {
						temp_num = CustomerAddRequest.MAX_COUNT_GDR_NODH - addRequest.getCount();
						flag = 2;
					}
				} else if("GDT".equals(addRequest.getCusZqType()) && "Y".equals(addRequest.getCusIsDh())){
					if(CustomerAddRequest.MAX_COUNT_GDT_DH == addRequest.getCount()){
						flag = 1;
						
					} else {
						temp_num = CustomerAddRequest.MAX_COUNT_GDT_DH - addRequest.getCount();
						flag = 2;
					}
				} else if("GDT".equals(addRequest.getCusZqType()) && "N".equals(addRequest.getCusIsDh())){
					if(CustomerAddRequest.MAX_COUNT_GDT_NODH == addRequest.getCount()){
						flag = 1;
						
					} else {
						temp_num = CustomerAddRequest.MAX_COUNT_GDT_NODH - addRequest.getCount();
						flag = 2;
					}
				} else {
					flag = 3;
				}
				
				if("Y".equals(addRequest.getCusIsVat())){
					if("".equals(addRequest.getCusVatTo())){
						flag = 4;
					}
				} 
				
				if(flag == 1){
					addCustomer();
				} else if(flag == 2){
					Toast.makeText(CustomerAddActivity.this, temp_num + getString(R.string.msg_data_incomplete_withnum), Toast.LENGTH_SHORT).show();
					
				} else if(flag == 3){
					Toast.makeText(CustomerAddActivity.this, getString(R.string.msg_data_incomplete), Toast.LENGTH_SHORT).show();
				
				} else if(flag == 4){
					Toast.makeText(CustomerAddActivity.this, getString(R.string.msg_customer_no_vatto), Toast.LENGTH_SHORT).show();
				}
				addRequest.setCount(0);
			}
		});
		
		btn_reset.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(cur_frag == FRAG_ADDRESS){
					addressFragment.reset();
					
				} else if(cur_frag == FRAG_DEPT){
					deptFragment.reset();
				}
			}
		});
	}
	
	private void addCustomer(){
		addCustomerTask = new SubmitTask();
		addCustomerTask.setTaskOnPostExecuteListener(new TaskOnPostExecuteListener<ExResponse>() {
			@Override
			public void onPostExecute(TaskResult<? extends ExResponse> result) {
				if (result != null && result.Status == BaseAsynTask.STATUS_SUCCESS) {
					ExResponse response = result.Value;
					btn_save.setEnabled(true);
					if (ErrorConstants.VALUE_NULL.equals(response.getResMessage())) {
						if (response.getResCode() == -3) {
							Toast.makeText(CustomerAddActivity.this, getResources().getString(R.string.msg_request_timeout), Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(CustomerAddActivity.this, getResources().getString(R.string.msg_server_nores), Toast.LENGTH_LONG).show();
						}
						return;
					} else {
						try {
							JSONObject json = new JSONObject(response.getResMessage());
							if(ResponseConstant.OK.equals(json.get(ResponseConstant.STATUS))){
								Toast.makeText(CustomerAddActivity.this, getResources().getString(R.string.msg_response_success), Toast.LENGTH_SHORT).show();
								AppStatus.PRODUCT_DATA_UPDATE = true;
								AppStatus.PRODUCT_LIST_UPDATE = true;
								CustomerAddActivity.this.finish();
							} else {
								Toast.makeText(CustomerAddActivity.this, getResources().getString(R.string.msg_submit_failed), Toast.LENGTH_SHORT).show();
							}
						} catch (JSONException e) {
							e.printStackTrace();
							Toast.makeText(CustomerAddActivity.this, getResources().getString(R.string.msg_submit_failed), Toast.LENGTH_SHORT).show();
						}
					}
				}
			}
		});
		
		addCustomerTask.execute(preferences.getHttpUrl(), RequestConstants.CUSTOMER_ADD, addRequest.toJsonString());
	}

	private void loadAddPreData() {
		cusAddPreTask = new AsynDataTask();
		cusAddPreTask.setTaskOnPostExecuteListener(new TaskOnPostExecuteListener<ExResponse>() {
			@Override
			public void onPostExecute(TaskResult<? extends ExResponse> result) {
				if (result != null && result.Status == BaseAsynTask.STATUS_SUCCESS) {
					ExResponse response = result.Value;
					if (ErrorConstants.VALUE_NULL.equals(response.getResMessage())) {
						if (response.getResCode() == -3) {
							Toast.makeText(CustomerAddActivity.this, getResources().getString(R.string.msg_request_timeout), Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(CustomerAddActivity.this, getResources().getString(R.string.msg_server_nores), Toast.LENGTH_LONG).show();
						}
						return;
					} else {

						try {
							JSONObject json = new JSONObject(response.getResMessage());
							if (ResponseConstant.OK.equals(json.get(ResponseConstant.STATUS))) {
								bindAddPreData(response.getResMessage());
							} else {
								Toast.makeText(CustomerAddActivity.this, getResources().getString(R.string.msg_response_failed), Toast.LENGTH_SHORT).show();
							}
						} catch (JSONException e) {
							e.printStackTrace();
							Toast.makeText(CustomerAddActivity.this, getResources().getString(R.string.msg_response_failed), Toast.LENGTH_SHORT).show();
						}

					}
				}
			}
		});

		CustomerAddPreRequest request = new CustomerAddPreRequest();
		request.setUserId(preferences.getUserId());
		request.setToken(preferences.getTokenLast8());
		request.setCompanyCode(companyCode);
		cusAddPreTask.execute(preferences.getHttpUrl(), RequestConstants.CUSTOMER_ADDPRE, request.toJsonString());
	}

	private void bindAddPreData(String dataStr) throws JSONException {
		JSONObject json = new JSONObject(dataStr);

		JSONArray cusDeptArray = json.getJSONArray("CUSTOMERDEPTS");
		JSONArray kaTypeArray = json.getJSONArray("KATYPELIST");
		JSONArray cusAreaArray = json.getJSONArray("CUSTOMERAREALIST");
		JSONArray vatToArray = json.getJSONArray("VATTOLIST");

		if ("N".equals(json.getString("COMPANYCODESTS"))) {
			isCustomerExist = false;
		} else if ("Y".equals(json.getString("COMPANYCODESTS"))) {
			isCustomerExist = true;
		}

		customerDeptList.clear();
		for (int i = 0; i < cusDeptArray.length(); i++) {
			JSONObject jsontemp = cusDeptArray.getJSONObject(i);
			BaseKeyValue dept = new BaseKeyValue();
			dept.setValue(jsontemp.getString("CUSTOMERDEPTID"));
			dept.setKey(jsontemp.getString("CUSTOMERDEPTNAME"));
			customerDeptList.add(dept);
		}

		kaTypeList.clear();
		for (int i = 0; i < kaTypeArray.length(); i++) {
			JSONObject jsontemp = kaTypeArray.getJSONObject(i);
			KaType kaType = new KaType();
			kaType.setValue(jsontemp.getString("KATYPE"));
			kaType.setName(jsontemp.getString("KATYPENAME"));
			kaType.setCustomerDeptId(jsontemp.getString("CUSTOMERDEPTID"));
			kaTypeList.add(kaType);
		}
		
		cusAreaList.clear();
		for (int i = 0; i < cusAreaArray.length(); i++) {
			JSONObject jsontemp = cusAreaArray.getJSONObject(i);
			CustomerArea area = new CustomerArea();
			area.setAreaName(jsontemp.getString("AREANAME"));
			area.setAreaValue(jsontemp.getString("AREAVALUE"));
			area.setCustomerId(jsontemp.getString("CUSTOMERID"));
			cusAreaList.add(area);
		}
		
		vatToList.clear();
		for (int i = 0; i < vatToArray.length(); i++) {
			JSONObject jsontemp = vatToArray.getJSONObject(i);
			VatToCustomer vatTo = new VatToCustomer();
			vatTo.setCustomerDeptId(jsontemp.getString("CUSTOMERDEPTID"));
			vatTo.setVatToId(jsontemp.getString("VATTOID"));
			vatTo.setVatToName(jsontemp.getString("VATTONAME"));
			vatToList.add(vatTo);
		}
		
		deptFragment.loadInitData();
	}
	
	private void initCustomerInfoData(){
		addRequest.setCustomerNameCN(nameCN);
		addRequest.setCustomerNameCNs(nameCNs);
		addRequest.setCustomerNameEN(nameEN);
		addRequest.setCustomerNameEns(nameENs);
		addRequest.setCompanyCode(companyCode);
	}

	private void initFragments() {

		if(isCustomerExist){
			Log.e(null, "customer exist.");
		}
		fragments = new Fragment[ITEM_NUMS];
		addressFragment = new CustomerAddAddressFragment();
		fragments[FRAG_ADDRESS] = addressFragment;
		deptFragment = new CustomerAddDeptFragment();
		fragments[FRAG_DEPT] = deptFragment;

		adapter = new SectionPagerAdapter(getSupportFragmentManager(), fragments);
		viewPager.setAdapter(adapter);
	}

	public void switchScheduleMode(int id) {
		switch (id) {
		case R.id.act_cusadd_btn_address:
			viewPager.setCurrentItem(FRAG_ADDRESS);
			cur_frag = FRAG_ADDRESS;
			btn_reset.setText(R.string.cusmodify_reset_address);
			break;

		case R.id.act_cusadd_btn_dept:
			viewPager.setCurrentItem(FRAG_DEPT);
			cur_frag = FRAG_DEPT;
			btn_reset.setText(R.string.cusmodify_reset_dept);
			break;

		default:
			break;
		}
	}

	public void setTabChecked(int tabNum) {
		switch (tabNum) {

		case FRAG_ADDRESS:
			customer_address.setChecked(true);
			break;

		case FRAG_DEPT:
			customer_dept.setChecked(true);
			break;

		default:
			break;
		}
	}
	
	private void backWithConfirm() {
		DialogUtil.createMessageDialog(CustomerAddActivity.this, 
				R.string.product_dlg_exit_modify_title, 
				R.string.product_dlg_exit_modify_info, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int arg1) {
						dialog.dismiss();
						CustomerAddActivity.this.finish();
					}
				});
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == android.R.id.home) {
			backWithConfirm();
            return true;
        }
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if(keyCode == KeyEvent.KEYCODE_BACK){
			backWithConfirm();
		}
		return false;
	}

	public String getNameCN() {
		return nameCN;
	}

	public void setNameCN(String nameCN) {
		this.nameCN = nameCN;
	}

	public String getNameCNs() {
		return nameCNs;
	}

	public void setNameCNs(String nameCNs) {
		this.nameCNs = nameCNs;
	}

	public String getNameEN() {
		return nameEN;
	}

	public void setNameEN(String nameEN) {
		this.nameEN = nameEN;
	}

	public String getNameENs() {
		return nameENs;
	}

	public void setNameENs(String nameENs) {
		this.nameENs = nameENs;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public CustomerAddRequest getAddRequest() {
		return addRequest;
	}

	public void setAddRequest(CustomerAddRequest addRequest) {
		this.addRequest = addRequest;
	}

	public List<BaseKeyValue> getCustomerDeptList() {
		return customerDeptList;
	}

	public void setCustomerDeptList(List<BaseKeyValue> customerDeptList) {
		this.customerDeptList = customerDeptList;
	}

	public List<KaType> getKaTypeList() {
		return kaTypeList;
	}

	public void setKaTypeList(List<KaType> kaTypeList) {
		this.kaTypeList = kaTypeList;
	}

	public List<CustomerArea> getCusAreaList() {
		return cusAreaList;
	}

	public void setCusAreaList(List<CustomerArea> cusAreaList) {
		this.cusAreaList = cusAreaList;
	}

	public List<VatToCustomer> getVatToList() {
		return vatToList;
	}

	public void setVatToList(List<VatToCustomer> vatToList) {
		this.vatToList = vatToList;
	}

}
