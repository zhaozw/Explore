package com.explore.android.mobile.activity.out;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.explore.android.R;
import com.explore.android.core.base.BaseHttpFragment;
import com.explore.android.core.model.BaseKeyValue;
import com.explore.android.core.util.DateUtil;
import com.explore.android.core.util.ExStringUtil;
import com.explore.android.mobile.adapter.DetailInfoAdapter;
import com.explore.android.mobile.adapter.SimpleSpinnerAdapter;
import com.explore.android.mobile.common.SharePreferencesManager;
import com.explore.android.mobile.constants.ActionConstants;
import com.explore.android.mobile.constants.AppStatus;
import com.explore.android.mobile.constants.RequestConstants;
import com.explore.android.mobile.constants.ResponseConstant;
import com.explore.android.mobile.data.ValueToResource;
import com.explore.android.mobile.data.request.OutDetailRequest;
import com.explore.android.mobile.data.request.OutModifyRequest;
import com.explore.android.mobile.data.request.OutUpdatePreRequest;
import com.explore.android.mobile.model.DetailInfo;
import com.explore.android.mobile.view.CustomDialog;
import com.explore.android.mobile.view.DialogUtil;

/**
 * 销售信息
 * @author Ryan
 *
 */
public class OutDetailInfoFragment extends BaseHttpFragment{
	
	private static final int REQUEST_OUTLINE_MODIFYPRE = 1;
	private static final int REQUEST_OUTDETAIL = 2;
	private static final int REQUEST_MODIFY = 3;
	private static final int REQUEST_DELETE = 4;
	private static final int REQUEST_COMMIT = 5;

	private ListView listView;
	private RelativeLayout loading;
	private LinearLayout actionLayout;
	
	private Button btn_commit;
	private Button btn_edit;
	private Button btn_delete;
	private final ViewHolder mHolder = new ViewHolder();
	
	private DetailInfoAdapter adapter;
	private List<DetailInfo> detailList;
	
	private OutDetailActivity mActivity;
	private String outId;
	
	private List<BaseKeyValue> shipToAdressList;
	private SimpleSpinnerAdapter shipToAdressAdapter;
	
	private SharePreferencesManager preferences;
	
	private OutModifyData modifyData;
	private int year,month,date;
	
	@Override
	protected int setLayoutId() {
		return R.layout.frag_outinfo_layout;
	}

	@Override
	public void initViews(View view) {
		listView = (ListView) view.findViewById(R.id.frag_outdetail_list);
		loading = (RelativeLayout) view.findViewById(R.id.app_loading_layout);
		actionLayout= (LinearLayout) view.findViewById(R.id.frag_out_detail_action_layout);
		btn_commit = (Button) view.findViewById(R.id.frag_btn_outdetail_submit);
		btn_edit = (Button) view.findViewById(R.id.frag_btn_outdetail_edit);
		btn_delete = (Button) view.findViewById(R.id.frag_btn_outdetail_delete);
		btn_edit.setEnabled(false);
	}

	@Override
	public void initValues() {
		Calendar cal = Calendar.getInstance();
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH);
		date = cal.get(Calendar.DATE);
		preferences = SharePreferencesManager.getInstance(getActivity());
		
		if(getActivity() != null && getActivity() instanceof OutDetailActivity){
			mActivity = (OutDetailActivity) getActivity();
			outId = mActivity.getOutId();
			detailList = new ArrayList<DetailInfo>();
			adapter = new DetailInfoAdapter(getActivity(),detailList);
			listView.setAdapter(adapter);
			modifyData = new OutModifyData();
			modifyData.setOutId(outId);
			loadOutDetail();
			if(mActivity.hasAudit) {
				actionLayout.setVisibility(View.GONE);
			}
		}else{
			mActivity = null;
		}
	}

	@Override
	public void initListener() {
		btn_commit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				DialogUtil.createMessageDialog(getActivity(), R.string.out_dlg_commit_title,
						R.string.out_dlg_commit_msg, new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
								commitOutInfo(outId);
							}
						});
			}
		});
		
		btn_edit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				createEditDialog();
			}
		});
		
		btn_delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DialogUtil.createMessageDialog(getActivity(), R.string.out_dlg_delete_title,
						R.string.out_dlg_delete_msg, new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
								deleteOutInfo(outId);
							}
						});
			}
		});
	}
	
	@Override
	public void handlerResponse(String response, int what) {
		switch (what) {
		case REQUEST_OUTLINE_MODIFYPRE:
			mHolder.loadding.setVisibility(View.GONE);
			bindUpdatePreData(response, mHolder);
			break;
			
		case REQUEST_OUTDETAIL:
			loading.setVisibility(View.GONE);
			bindOutDetailData(response);
			break;
			
		case REQUEST_MODIFY:
			handlerModifyResult(response);
			break;
			
		case REQUEST_COMMIT:
			loading.setVisibility(View.GONE);
			handlerCommitResult(response);
			break;
			
		case REQUEST_DELETE:
			loading.setVisibility(View.GONE);
			handlerDelteResult(response);
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
		if (loading != null) {
			loading.setVisibility(View.GONE);
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if (AppStatus.OUT_DETAIL_UPDATE) {
			loadOutDetail();
		}
		AppStatus.OUT_DETAIL_UPDATE = false;
	}
	
	public void loadOutDetail(){
		loading.setVisibility(View.VISIBLE);
		OutDetailRequest request = new OutDetailRequest();
		request.setUserId(preferences.getUserId());
		request.setToken(preferences.getTokenLast8());
		request.setOutId(outId);
		asynDataRequest(RequestConstants.OUT_GET_DETAIL, request.toJsonString(), REQUEST_OUTDETAIL);
	}
	
	private void bindOutDetailData(String resStr){
		try {
			JSONObject jsonObject = new JSONObject(resStr);
			if (ResponseConstant.OK.equals(jsonObject.get(ResponseConstant.STATUS))) {
				detailList.clear();
				JSONObject json = jsonObject.getJSONObject("OUT");
				
				List<String> keyList = new ArrayList<String>();
				//keyList.add("OUTCODE1");
				keyList.add("OUTCODE2");
				keyList.add("OUTTYPE");
				keyList.add("OUTTYPE2");
				keyList.add("CUSTOMERDEPTNAME");
				keyList.add("PROJECTNAME");
				keyList.add("VENDORNAME");
				keyList.add("WAREHOUSENAME");
				keyList.add("SHIPTO");
				keyList.add("VATTO");
				keyList.add("VATTONAME");
				keyList.add("SHIPTOADDRESS");
				keyList.add("SHIPFROM");
				keyList.add("LOGNO");
				keyList.add("SOPRICELOGONNAME");
				keyList.add("OUTQTY1");
				keyList.add("OUTMONEY1");
				keyList.add("OUTNOTAXMONEY1");
				//update-2014-05-10 去掉以下显示字段
				//keyList.add("TRANSPORTQTY1");
				//keyList.add("TRANSPORTMONEY1");
				//keyList.add("TRANSPORTNOTAXMONEY1");
				//keyList.add("TRANSPORTQTY2");
				//keyList.add("TRANSPROTMONEY2");
				//keyList.add("TRANSPROTNOTAXMONEY2");
				keyList.add("DDQTY");
				keyList.add("DDMONEY");
				keyList.add("DDNOTAXMONEY");
				keyList.add("OUTTIME");
				keyList.add("OUTTIMEEND");
				keyList.add("REMARKS");
				keyList.add("ISDD");
				keyList.add("CREATEBYNAME");
				keyList.add("CREATEBYTIME");
				keyList.add("WAREHOUSEID");
				keyList.add("WAREHOUSETO");
				keyList.add("SHIPTOID");
				keyList.add("SOPRICELOGONID");
				keyList.add("CUSTOMERDEPTID");
				
				for (int i = 0; i < keyList.size(); i++) {
					String key = keyList.get(i);
					DetailInfo info = new DetailInfo();
					int strId = getResourceIdByKey(key, json.getString(key));
					if("OUTTYPE2".equals(key)){
						info.setContent(getResources().getString(ValueToResource.getOutType(json.getString(key))));
					} else {
						info.setContent(json.getString(key));
					}
					if(ExStringUtil.isResNoBlank(info.getContent()) && strId != R.string.app_null){
						info.setTitle(key);
						info.setResourceId(getResourceIdByKey(key, json.getString(key)));
						detailList.add(info);
					}
				}
				
				adapter.notifyDataSetChanged();
				btn_edit.setEnabled(true);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Intent data_ready_intent = new Intent();
		data_ready_intent.setAction(ActionConstants.ACTION_OUTDETAIL_DATA_READY);
		data_ready_intent.putExtra("STATUS", "ready");
		getActivity().sendBroadcast(data_ready_intent);
	}
	
	private CustomDialog edt_dialog;
	private void createEditDialog(){
		edt_dialog = new CustomDialog(getActivity());
		CustomDialog.Builder builder = new CustomDialog.Builder(getActivity());
		
		builder.setTitle(R.string.app_edit);
		
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		View view = inflater.inflate(R.layout.dialog_out_modify, null);
		
		mHolder.outline_layout = (LinearLayout) view.findViewById(R.id.dlg_out_modify_info_layout);
		mHolder.loadding = (ProgressBar) view.findViewById(R.id.dlg_out_modify_loading);
		mHolder.spi_shipToAddress = (Spinner) view.findViewById(R.id.dlg_spi_out_modify_shiptoaddress);
		mHolder.outline_layout.setVisibility(View.GONE);
		
		mHolder.tv_shipTo = (TextView) view.findViewById(R.id.dlg_tv_out_modify_shipto);
		mHolder.tv_outCode1 = (TextView) view.findViewById(R.id.dlg_tv_out_modify_outcode1);
		mHolder.tv_customerDept = (TextView) view.findViewById(R.id.dlg_tv_out_modify_customerDept);
		mHolder.tv_warehouse = (TextView) view.findViewById(R.id.dlg_tv_out_modify_warehouse);
		mHolder.tv_sopricelogonName = (TextView) view.findViewById(R.id.dlg_tv_out_modify_sopricelogon);
		mHolder.tv_shipfrom = (TextView) view.findViewById(R.id.dlg_tv_out_modify_shipfrom);
		mHolder.tv_vatto = (TextView) view.findViewById(R.id.dlg_tv_out_modify_vatto);
		mHolder.tv_isdd = (TextView) view.findViewById(R.id.dlg_tv_out_modify_isdd);
		mHolder.tv_logno = (TextView) view.findViewById(R.id.dlg_tv_out_modify_logno);
		
		loadUpdatePreData(mHolder);

		mHolder.edt_outCode2 = (EditText) view.findViewById(R.id.dlg_edt_out_modify_outcode2);
		mHolder.edt_outtime = (EditText) view.findViewById(R.id.dlg_edt_out_modify_outtime);
		mHolder.edt_remarks = (EditText) view.findViewById(R.id.dlg_edt_out_modify_remarks);
		
		Button btn_date_pick = (Button)view.findViewById(R.id.dlg_btn_out_modify_outtime);
		btn_date_pick.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				DatePickerDialog dlg = new DatePickerDialog(getActivity(), new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
						mHolder.edt_outtime.setText(DateUtil.dateFormatWithDateNum(year, monthOfYear+1, dayOfMonth));
					}
				}, year, month, date);
				dlg.setCanceledOnTouchOutside(true);
				dlg.show();
			}
		});
		
		mHolder.tv_shipTo.setText(modifyData.getShipToName());
		mHolder.tv_outCode1.setText(modifyData.getOutCode1());
		mHolder.tv_customerDept.setText(modifyData.getCustomerDeptName());
		mHolder.tv_warehouse.setText(modifyData.getWarehouse());
		mHolder.tv_sopricelogonName.setText(modifyData.getSoPriceLogonName());
		
		if(modifyData.getShipFrom() != null && !"".equals(modifyData.getShipFrom().trim())){
			((LinearLayout) view.findViewById(R.id.dlg_out_modify_shipfrom_layout)).setVisibility(View.VISIBLE);
			mHolder.tv_shipfrom.setText(modifyData.getShipFrom());
		}
		
		mHolder.tv_isdd.setText(modifyData.getIsDd());
		
		if(ExStringUtil.isResNoBlank(modifyData.getVatToName())){
			mHolder.tv_vatto.setText(modifyData.getVatToName());
		}
		
		if(ExStringUtil.isResNoBlank(modifyData.getOutCode2())){
			mHolder.edt_outCode2.setText(modifyData.getOutCode2());
		}
		if(ExStringUtil.isResNoBlank(modifyData.getLogNo())){
			mHolder.tv_logno.setText(modifyData.getLogNo());
		}
		if(ExStringUtil.isResNoBlank(modifyData.getOutTime())){
			mHolder.edt_outtime.setText(modifyData.getOutTime());
		}
		if(ExStringUtil.isResNoBlank(modifyData.getRemarks())){
			mHolder.edt_remarks.setText(modifyData.getRemarks());
		}
		
		builder.setContentView(view);
		
		builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				getAsynDataTask().cancel(true);
				dialog.dismiss();
			}
		});
		
		builder.setEditButton(R.string.dialog_reset, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if(!"null".equals(modifyData.getOutCode2())){
					mHolder.edt_outCode2.setText(modifyData.getOutCode2());
				} else {
					mHolder.edt_outCode2.setText("");
				}
				
				for (int i = 0; i < shipToAdressList.size(); i++) {
					if(shipToAdressList.get(i).getValue().equals(modifyData.getShipToAdress())){
						mHolder.spi_shipToAddress.setSelection(i);
					}
				}
				
				if(!"null".equals(modifyData.getOutTime())){
					mHolder.edt_outtime.setText(modifyData.getOutTime());
				} else {
					mHolder.edt_outtime.setText("");
				}
				
				if(!"null".equals(modifyData.getRemarks())){
					mHolder.edt_remarks.setText(modifyData.getRemarks());
				} else {
					mHolder.edt_remarks.setText("");
				}
			}
		});
		
		builder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				OutModifyRequest request = new OutModifyRequest();
				
				request.setOutId(modifyData.getOutId());
				request.setOutCode2(mHolder.edt_outCode2.getText().toString().trim());
				request.setRemarks(mHolder.edt_remarks.getText().toString().trim());
				request.setShipToAdress(shipToAdressList.get(mHolder.spi_shipToAddress.getSelectedItemPosition()).getValue());
				request.setOutTime(mHolder.edt_outtime.getText().toString().trim());
				request.setOutCode1(modifyData.getOutCode1());
				request.setOutType(modifyData.getOutType());
				request.setOutType2(modifyData.getOutType2());
				request.setCustomerDeptId(modifyData.getCustomerDeptId());
				request.setShipTo(modifyData.getShipToId());
				request.setWareHouseId(modifyData.getWarehouseId());
				request.setVatTo(modifyData.getVatTo());
				request.setProjectId(modifyData.getProjectId());
				request.setVendorId(modifyData.getVendorId());
				request.setSoPriceLogonId(modifyData.getSoPriceLogonId());
				edt_dialog.dismiss();
				editOutInfo(request);
			}
		});
		
		edt_dialog = builder.create();
		WindowManager windowManager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
		Display display = windowManager.getDefaultDisplay();
		int dlg_width = (int) (display.getWidth() * 0.8);
		Window dlg_window = edt_dialog.getWindow();
		WindowManager.LayoutParams lp = dlg_window.getAttributes();
		lp.width = dlg_width;
		dlg_window.setAttributes(lp);
		edt_dialog.setCanceledOnTouchOutside(false);
		
		edt_dialog.show();
	}
	
	private void editOutInfo(OutModifyRequest request){
		request.setUserId(preferences.getUserId());
		request.setToken(preferences.getTokenLast8());
		submitRequest(RequestConstants.OUT_UPDATE, request.toJsonString(), REQUEST_MODIFY);
	}
	
	private void deleteOutInfo(String outId){
		loading.setVisibility(View.VISIBLE);
		OutDetailRequest request = new OutDetailRequest();
		request.setUserId(preferences.getUserId());
		request.setToken(preferences.getTokenLast8());
		request.setOutId(outId);
		submitRequest(RequestConstants.OUT_DELETE, request.toJsonString(), REQUEST_DELETE);
	}
	
	private void commitOutInfo(String outId){
		loading.setVisibility(View.VISIBLE);
		OutDetailRequest request = new OutDetailRequest();
		request.setUserId(preferences.getUserId());
		request.setToken(preferences.getTokenLast8());
		request.setOutId(outId);
		submitRequest(RequestConstants.OUT_COMMIT, request.toJsonString(), REQUEST_COMMIT);
	}
	
	private void handlerModifyResult(String response) {
		try {
			JSONObject json = new JSONObject(response);
			if(ResponseConstant.OK.equals(json.get(ResponseConstant.STATUS))){
				loadOutDetail();
				showToast(R.string.app_action_success);
			}else{
				showToast(R.string.msg_action_failed);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			showToast(R.string.msg_action_failed);
		}
	}
	
	private void handlerDelteResult(String response) {
		try {
			JSONObject json = new JSONObject(response);
			if(ResponseConstant.OK.equals(json.get(ResponseConstant.STATUS))){
				showToast(R.string.app_delete_success);
				getActivity().setResult(1);
				getActivity().finish();
			} else {
				showToast(R.string.app_delete_failed);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	private void handlerCommitResult(String response) {
		try {
			JSONObject json = new JSONObject(response);
			if(ResponseConstant.OK.equals(json.get(ResponseConstant.STATUS))){
				showToast(R.string.app_action_success);
				getActivity().setResult(1);
				getActivity().finish();
			} else {
				showToast(R.string.app_action_failed);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	private void loadUpdatePreData(final ViewHolder viewHolder){
		OutUpdatePreRequest request = new OutUpdatePreRequest();
		viewHolder.loadding.setVisibility(View.VISIBLE);
//		viewHolder.tv_load_failed.setVisibility(View.GONE);
//		viewHolder.tv_load_failed.setVisibility(View.VISIBLE);
		request.setUserId(preferences.getUserId());
		request.setToken(preferences.getTokenLast8());
		request.setShipTo(modifyData.getShipToId());
		request.setCustomerDeptId(modifyData.getCustomerDeptId());
		asynDataRequest(RequestConstants.OUT_UPDATEPRE, request.toJsonString(), REQUEST_OUTLINE_MODIFYPRE);
	}
	
	private void bindUpdatePreData(String dataStr, ViewHolder viewHolder){
		try {
			JSONObject json = new JSONObject(dataStr);
			if(ResponseConstant.OK.equals(json.get(ResponseConstant.STATUS))){
				JSONArray adressArray = json.getJSONArray("ADDRESSES");
				
				if(shipToAdressList == null){
					shipToAdressList = new ArrayList<BaseKeyValue>();
				} else {
					shipToAdressList.clear();
				}
				
				for (int i = 0; i < adressArray.length(); i++) {
					BaseKeyValue info = new BaseKeyValue();
					JSONObject jsonObject = adressArray.getJSONObject(i);
					info.setValue(jsonObject.getString("ADDRESSID"));
					info.setKey(jsonObject.getString("ADDRESS"));
					shipToAdressList.add(info);
				}
				
				if(shipToAdressAdapter == null){
					shipToAdressAdapter = new SimpleSpinnerAdapter(getActivity(), shipToAdressList);
				} else {
					shipToAdressAdapter.notifyDataSetChanged();
				}
				
				viewHolder.spi_shipToAddress.setAdapter(shipToAdressAdapter);
				mHolder.outline_layout.setVisibility(View.VISIBLE);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		viewHolder.loadding.setVisibility(View.GONE);
	}
	
	private class ViewHolder{
		LinearLayout outline_layout;
		ProgressBar loadding;
		Spinner spi_shipToAddress;
		TextView tv_shipTo;
		TextView tv_outCode1;
		TextView tv_customerDept;
		TextView tv_warehouse;
		TextView tv_sopricelogonName;
		TextView tv_shipfrom;
		TextView tv_vatto;
		TextView tv_isdd;
		TextView tv_logno;
		EditText edt_outCode2;
		EditText edt_outtime;
		EditText edt_remarks;
		
	}
	
	private int getResourceIdByKey(String name, String content){
		
		if("OUTCODE1".equals(name)){
			modifyData.setOutCode1(content);
			return R.string.outdetail_outcode1;
			
		} else if("OUTCODE2".equals(name)){
			modifyData.setOutCode2(content);
			return R.string.outdetail_outcode2;
			
		} else if("CUSTOMERDEPTNAME".equals(name)){
			modifyData.setCustomerDeptName(content);
			return R.string.outdetail_customerdept;
			
		} else if("CUSTOMERDEPTID".equals(name)){
			modifyData.setCustomerDeptId(content);
			return R.string.app_null;
			
		} else if("WAREHOUSENAME".equals(name)){
			modifyData.setWarehouse(content);
			return R.string.outdetail_warehouse;
			
		} else if("SHIPTO".equals(name)){
			modifyData.setShipToName(content);
			return R.string.outdetail_shipto;
			
		} else if("SHIPTOID".equals(name)){
			modifyData.setShipToId(content);
			mActivity.setShipTo(content);
			return R.string.app_null;
			
		} else if("VATTO".equals(name)){
			modifyData.setVatTo(content);
			return R.string.app_null;
			
		} else if("VATTONAME".equals(name)){
			modifyData.setVatToName(content);
			return R.string.outdetail_vatto;
			
		} else if("OUTTYPE".equals(name)){
			modifyData.setOutType(content);
			return R.string.app_null;
			
		} else if("OUTTYPE2".equals(name)){
			modifyData.setOutType2(content);
			return R.string.outdetail_outtype;
			
		} else if("TRANSPORTQTY1".equals(name)){
			return R.string.outdetail_transportqty1;
			
		} else if("OUTTIME".equals(name)){
			modifyData.setOutTime(content);
			return R.string.outdetail_outtime;
			
		} else if("STS".equals(name)){
			return R.string.outdetail_sts;
			
		} else if("REMARKS".equals(name)){
			modifyData.setRemarks(content);
			return R.string.outdetail_remarks;
			
		} else if("UTMONEY1".equals(name)){
			return R.string.outdetail_outmoney1;
			
		} else if("TRANSPORTMONEY1".equals(name)){
			return R.string.outdetail_transportmoney1;
			
		} else if("TRANSPORTQTY2".equals(name)){
			return R.string.outdetail_transportqty2;
			
		} else if("TRANSPORTMONEY2".equals(name)){
			return R.string.outdetail_transportmoney2;
			
		} else if("SOPRICELOGONID".equals(name)){
			modifyData.setSoPriceLogonId(content);
			mActivity.setSoPriceLogonId(content);
			return R.string.app_null;
			
		} else if("SOPRICELOGONNAME".equals(name)){
			modifyData.setSoPriceLogonName(content);
			return R.string.outdetail_sopricelogonname;
			
		} else if("CREATEBYNAME".equals(name)){
			return R.string.outdetail_createbyname;
			
		} else if("CREATEBYTIME".equals(name)){
			return R.string.outdetail_createbytime;
			
		} else if("COMMITBYNAME".equals(name)){
			return R.string.outdetail_commitbyname;
			
		} else if("COMMITBYTIME".equals(name)){
			return R.string.outdetail_commitbytime;
			
		} else if("VENDORNAME".equals(name)){
			return R.string.outdetail_vendorid;
			
		} else if("PROJECTNAME".equals(name)){
			return R.string.outdetail_projectid;
			
		} else if("LOGNO".equals(name)){
			modifyData.setLogNo(content);
			return R.string.outdetail_logno;
			
		} else if("WAREHOUSETO".equals(name)){
			return R.string.outdetail_warehouseto;
			
		} else if("OUTTIMEEND".equals(name)){
			return R.string.outdetail_outtimeend;
			
		} else if("WAREHOUSEID".equals(name)){
			modifyData.setWarehouseId(content);
			return R.string.app_null;
			
		} else if("OUTTYPE2".equals(name)){
			return R.string.outdetail_outtype2;
			
		} else if("SHIPFROM".equals(name)){
			modifyData.setShipFrom(content);
			return R.string.outdetail_shipfrom;
			
		} else if("SHIPTOADDRESS".equals(name)){
			modifyData.setShipToAdress(content);
			return R.string.outdetail_shiptoaddress;
			
		} else if("ISDD".equals(name)){
			modifyData.setIsDd(content);
			return R.string.outdetail_isdd;
			
		} else if("OUTQTY1".equals(name)){
			return R.string.outdetail_outqty;
			
		} else if("OUTMONEY1".equals(name)){
			return R.string.outdetail_outmoney1;
			
		} else if("OUTNOTAXMONEY1".equals(name)){
			return R.string.outdetail_outnotaxmoney1;
			
		} else if("TRANSPORTQTY1".equals(name)){
			return R.string.outdetail_transportqty1;
			
		} else if("TRANSPORTMONEY1".equals(name)){
			return R.string.outdetail_transportmoney1;
			
		} else if("TRANSPORTNOTAXMONEY1".equals(name)){
			return R.string.outdetail_transportnotaxmoney1;
			
		} else if("TRANSPORTQTY2".equals(name)){
			return R.string.outdetail_transportqty2;
			
		} else if("TRANSPROTMONEY2".equals(name)){
			return R.string.outdetail_transportmoney2;
			
		} else if("ISDD".equals(name)){
			return R.string.outdetail_isdd;
			
		} else if("TRANSPROTNOTAXMONEY2".equals(name)){
			return R.string.outdetail_transportnotaxmoney2;
			
		} else if("DDQTY".equals(name)){
			return R.string.outdetail_ddqty;
			
		} else if("DDMONEY".equals(name)){
			return R.string.outdetail_ddmoney;
			
		} else if("DDNOTAXMONEY".equals(name)){
			return R.string.outdetail_ddnotaxmoney;
			
		} else if("OUTTIMEEND".equals(name)){
			return R.string.outdetail_outtimeend;
			
		} else if("ADDRESSID".equals(name)){
			modifyData.setShipToAdressId(content);
			return R.string.app_null;
			
		} else if("OUTID".equals(name)){
			modifyData.setOutId(content);
			return R.string.app_null;
			
		} else if("PROJECTID".equals(name)){
			modifyData.setProjectId(content);
			return R.string.app_null;
			
		} else if("VENDORID".equals(name)){
			modifyData.setVendorId(content);
			return R.string.app_null;
			
		} else {
			return R.string.outdetail_outinfo;
		}
	}

}
