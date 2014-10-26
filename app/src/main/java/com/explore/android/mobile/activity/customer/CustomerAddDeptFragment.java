package com.explore.android.mobile.activity.customer;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.explore.android.R;
import com.explore.android.core.model.BaseKeyValue;
import com.explore.android.mobile.adapter.SimpleSpinnerAdapter;
import com.explore.android.mobile.model.CustomerArea;
import com.explore.android.mobile.model.KaType;
import com.explore.android.mobile.model.VatToCustomer;

public class CustomerAddDeptFragment extends Fragment{

	private EditText edt_amountMoney;
	private EditText edt_zqDay;
	private EditText edt_zqMonth;
	private EditText edt_gdDay;
	private EditText edt_dhRate;
	private Spinner spi_cusDept;
	private Spinner spi_kaType;
	private Spinner spi_dzType;
	private Spinner spi_zqType;
	private Spinner spi_isVat;
	private Spinner spi_isDh;
	private Spinner spi_cusArea;
	private Spinner spi_vatTo;
	
	private List<BaseKeyValue> isVatList;
	private List<BaseKeyValue> isDhList;
	private List<KaType> kaTypeList;
	private List<BaseKeyValue> kaTypeSpinnerList;
	private List<BaseKeyValue> dzTypeList;
	private List<BaseKeyValue> zqTypeList;
	private List<BaseKeyValue> cusDeptList;
	private List<BaseKeyValue> cusAreaSpinnerList;
	private List<CustomerArea> cusAreaList;
	private List<VatToCustomer> cusVatToList;
	private List<BaseKeyValue> cusVatToSpinnerList;
	
	private SimpleSpinnerAdapter isVatAdapter;
	private SimpleSpinnerAdapter isDhAdapter;
	private SimpleSpinnerAdapter kaTypeAdapter;
	private SimpleSpinnerAdapter dzTypeAdapter;
	private SimpleSpinnerAdapter zqTypeAdapter;
	private SimpleSpinnerAdapter cusDeptListAdapter;
	private SimpleSpinnerAdapter cusAreaAdapter;
	private SimpleSpinnerAdapter cusVatToAdapter;
	
	private LinearLayout layout_zqtypeGdr; 	// 固定日
	private LinearLayout layout_zqtypeGdt; 	// 固定天
	private LinearLayout layout_dhRate;		// 调货扣率
	private LinearLayout layout_vatTo;		// 开票客户
	
	private CustomerAddActivity mActivity;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		if(getActivity() != null && getActivity() instanceof CustomerAddActivity){
			mActivity = (CustomerAddActivity) getActivity();
		}else{
			mActivity = null;
		}
		
		View view = inflater.inflate(R.layout.frag_customer_add_dept, container, false);
		initViews(view);
		initValues();
		initListener();
		
		return view;
	}
	
	private void initViews(View view){
		spi_cusDept = (Spinner) view.findViewById(R.id.frag_cusmodify_spi_cusdept);
		spi_kaType = (Spinner) view.findViewById(R.id.frag_cusmodify_spi_katype);
		spi_cusArea = (Spinner) view.findViewById(R.id.frag_cusmodify_spi_cusarea);
		spi_dzType = (Spinner) view.findViewById(R.id.frag_cusmodify_spi_dztype);
		spi_zqType = (Spinner) view.findViewById(R.id.frag_cusmodify_spi_zqtype);
		spi_isVat = (Spinner) view.findViewById(R.id.frag_cusmodify_spi_isvat);
		spi_vatTo = (Spinner) view.findViewById(R.id.frag_cusmodify_spi_vatto);
		spi_isDh = (Spinner) view.findViewById(R.id.frag_cusmodify_spi_isdh);
		edt_gdDay = (EditText) view.findViewById(R.id.frag_cusmodify_edt_gdday);
		edt_amountMoney = (EditText) view.findViewById(R.id.frag_cusmodify_edt_amountmoney);
		edt_zqDay = (EditText) view.findViewById(R.id.frag_cusmodify_edt_zqday);
		edt_zqMonth = (EditText) view.findViewById(R.id.frag_cusmodify_edt_zqday_zqmonth);
		edt_dhRate = (EditText) view.findViewById(R.id.frag_cusmodify_edt_dhrate);
		
		layout_dhRate = (LinearLayout) view.findViewById(R.id.frag_cusmodif_dhrate_layout);
		layout_zqtypeGdr = (LinearLayout) view.findViewById(R.id.frag_cusmodif_zqgdr_layout);
		layout_zqtypeGdt = (LinearLayout) view.findViewById(R.id.frag_cusmodif_zqgdt_layout);
		layout_vatTo = (LinearLayout) view.findViewById(R.id.frag_cusmodif_vatto_layout);
	}
	
	private void initValues(){
		isDhList = new ArrayList<BaseKeyValue>();
		isDhList.add(new BaseKeyValue("", ""));
		isDhList.add(new BaseKeyValue(R.string.app_yes, "Y"));
		isDhList.add(new BaseKeyValue(R.string.app_no, "N"));
		isDhAdapter = new SimpleSpinnerAdapter(getActivity(), isDhList);
		spi_isDh.setAdapter(isDhAdapter);
		
		isVatList = new ArrayList<BaseKeyValue>();
		isVatList.add(new BaseKeyValue("", ""));
		isVatList.add(new BaseKeyValue(R.string.app_yes, "Y"));
		isVatList.add(new BaseKeyValue(R.string.app_no, "N"));
		isVatAdapter = new SimpleSpinnerAdapter(getActivity(), isVatList);
		spi_isVat.setAdapter(isVatAdapter);
		
		zqTypeList = new ArrayList<BaseKeyValue>();
		zqTypeList.add(new BaseKeyValue("", ""));
		zqTypeList.add(new BaseKeyValue(R.string.customer_modify_dztype_gdr, "GDR"));
		zqTypeList.add(new BaseKeyValue(R.string.customer_modify_dztype_gdt, "GDT"));
		zqTypeAdapter = new SimpleSpinnerAdapter(getActivity(), zqTypeList);
		spi_zqType.setAdapter(zqTypeAdapter);
		
		dzTypeList = new ArrayList<BaseKeyValue>();
		dzTypeList.add(new BaseKeyValue("", ""));
		dzTypeList.add(new BaseKeyValue(R.string.customer_modify_zqtype_zqd, "ZQD"));
		dzTypeList.add(new BaseKeyValue(R.string.customer_modify_zqtype_jsd, "JSD"));
		dzTypeAdapter = new SimpleSpinnerAdapter(getActivity(), dzTypeList);
		spi_dzType.setAdapter(dzTypeAdapter);
		
		cusDeptList = mActivity.getCustomerDeptList();
		cusDeptListAdapter = new SimpleSpinnerAdapter(getActivity(), cusDeptList);
		spi_cusDept.setAdapter(cusDeptListAdapter);
		
		kaTypeSpinnerList = new ArrayList<BaseKeyValue>();
		kaTypeSpinnerList.add(new BaseKeyValue("", ""));
		kaTypeAdapter = new SimpleSpinnerAdapter(getActivity(), kaTypeSpinnerList);
		spi_kaType.setAdapter(kaTypeAdapter);
		
		cusAreaSpinnerList = new ArrayList<BaseKeyValue>();
		cusAreaSpinnerList.add(new BaseKeyValue("", ""));
		cusAreaAdapter = new SimpleSpinnerAdapter(getActivity(), cusAreaSpinnerList);
		spi_cusArea.setAdapter(cusAreaAdapter);
		
		cusVatToSpinnerList = new ArrayList<BaseKeyValue>();
		cusVatToSpinnerList.add(new BaseKeyValue("", ""));
		cusVatToAdapter = new SimpleSpinnerAdapter(getActivity(), cusVatToSpinnerList);
		spi_vatTo.setAdapter(cusVatToAdapter);
	}
	
	private void initListener(){
		
		spi_zqType.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				if(position == 1){
					layout_zqtypeGdr.setVisibility(View.VISIBLE);
					layout_zqtypeGdt.setVisibility(View.GONE);
					edt_gdDay.setText("");
				} else if(position == 2){
					layout_zqtypeGdr.setVisibility(View.GONE);
					layout_zqtypeGdt.setVisibility(View.VISIBLE);
					edt_zqDay.setText("");
					edt_zqMonth.setText("");
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		
		spi_isDh.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				if(position == 1){
					layout_dhRate.setVisibility(View.VISIBLE);
				} else if(position == 2){
					layout_dhRate.setVisibility(View.GONE);
					edt_dhRate.setText("");
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
		
		spi_isVat.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				if(position == 1){
					layout_vatTo.setVisibility(View.VISIBLE);
				} else if(position == 2){
					layout_vatTo.setVisibility(View.GONE);
					spi_vatTo.setSelection(0);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
	}
	
	public void loadInitData(){
		if(mActivity.getCustomerDeptList() != null && mActivity.getKaTypeList() != null){
			cusDeptList = mActivity.getCustomerDeptList();
			cusDeptListAdapter.notifyDataSetChanged();
			kaTypeList = mActivity.getKaTypeList();
			cusAreaList = mActivity.getCusAreaList();
			cusVatToList = mActivity.getVatToList();
		}
		
		spi_cusDept.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
				kaTypeSpinnerList.clear();
				kaTypeSpinnerList.add(new BaseKeyValue("", ""));
				for (int i = 0; i < kaTypeList.size(); i++) {
					if(cusDeptList.get(position).getValue().equals(kaTypeList.get(i).getCustomerDeptId())){
						BaseKeyValue katype = new BaseKeyValue();
						katype.setKey(kaTypeList.get(i).getName());
						katype.setValue(kaTypeList.get(i).getValue());
						kaTypeSpinnerList.add(katype);
					}
				}
				kaTypeAdapter.notifyDataSetChanged();
				
				cusAreaSpinnerList.clear();
				cusAreaSpinnerList.add(new BaseKeyValue("", ""));
				for (int i = 0; i < cusAreaList.size(); i++) {
					if(cusDeptList.get(position).getValue().equals(cusAreaList.get(i).getCustomerId())){
						BaseKeyValue area = new BaseKeyValue();
						area.setKey(cusAreaList.get(i).getAreaName());
						area.setValue(cusAreaList.get(i).getAreaValue());
						cusAreaSpinnerList.add(area);
					}
				}
				cusAreaAdapter.notifyDataSetChanged();
				
				cusVatToSpinnerList.clear();
				cusVatToSpinnerList.add(new BaseKeyValue("", ""));
				for (int i = 0; i < cusVatToList.size(); i++) {
					if(cusDeptList.get(position).getValue().equals(cusVatToList.get(i).getCustomerDeptId())){
						BaseKeyValue vatTo = new BaseKeyValue();
						vatTo.setKey(cusVatToList.get(i).getVatToName());
						vatTo.setValue(cusVatToList.get(i).getVatToId());
						cusVatToSpinnerList.add(vatTo);
					}
				}
				cusVatToAdapter.notifyDataSetChanged();
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	}
	
	public void setRequestData(){
		if(mActivity == null){
			Log.e(null, "mActivity is null");
		}
		mActivity.getAddRequest().setCusCusDeptId(cusDeptList.get(spi_cusDept.getSelectedItemPosition()).getValue());
		mActivity.getAddRequest().setCusAmountMoney(edt_amountMoney.getText().toString().trim());
		mActivity.getAddRequest().setCusKaType(kaTypeSpinnerList.get(spi_kaType.getSelectedItemPosition()).getValue());
		mActivity.getAddRequest().setCusArea(cusAreaSpinnerList.get(spi_cusArea.getSelectedItemPosition()).getValue());
		mActivity.getAddRequest().setCusZqType(zqTypeList.get(spi_zqType.getSelectedItemPosition()).getValue());
		mActivity.getAddRequest().setCusDzType(dzTypeList.get(spi_dzType.getSelectedItemPosition()).getValue());
		mActivity.getAddRequest().setCusGdDay(edt_gdDay.getText().toString().trim());
		mActivity.getAddRequest().setCusZqDay(edt_zqDay.getText().toString().trim());
		mActivity.getAddRequest().setCusZqMonth(edt_zqMonth.getText().toString().trim());
		mActivity.getAddRequest().setCusIsVat(isVatList.get(spi_isVat.getSelectedItemPosition()).getValue());
		mActivity.getAddRequest().setCusIsDh(isDhList.get(spi_isDh.getSelectedItemPosition()).getValue());
		mActivity.getAddRequest().setCusDhRate(edt_dhRate.getText().toString().trim());
		mActivity.getAddRequest().setCusVatTo(cusVatToSpinnerList.get(spi_vatTo.getSelectedItemPosition()).getValue());
	}
	
	public void reset(){
		spi_cusDept.setSelection(0);
		spi_kaType.setSelection(0);
		spi_dzType.setSelection(0);
		spi_zqType.setSelection(0);
		spi_isVat.setSelection(0);
		spi_isDh.setSelection(0);
		edt_gdDay.setText("");
		edt_amountMoney.setText("");
		edt_zqDay.setText("");
		edt_zqMonth.setText("");
	}
}
