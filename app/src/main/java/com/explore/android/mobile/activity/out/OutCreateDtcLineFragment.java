package com.explore.android.mobile.activity.out;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.DialogInterface;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.explore.android.R;
import com.explore.android.core.base.BaseHttpFragment;
import com.explore.android.mobile.adapter.OutDTCCreateDataAdapter;
import com.explore.android.mobile.constants.RequestConstants;
import com.explore.android.mobile.constants.ResponseConstant;
import com.explore.android.mobile.data.request.OutDTCTransportSearchRequest;
import com.explore.android.mobile.model.OutDTCListItem;
import com.explore.android.mobile.model.OutDTCTransportData;
import com.explore.android.mobile.view.CustomDialog;

public class OutCreateDtcLineFragment extends BaseHttpFragment{
	
	private static final int REQUEST_TRANSPORTLINE = 1;
	
	private TextView tv_nodata;
	private ListView dtc_transport_listView;
	
	private OutDTCCreateDataAdapter adapter;
//	private List<OutDTCTransportData> transportList;
	private List<OutDTCListItem> titleList;
	
	private RelativeLayout loading;
	private OutCreateDtcDetailActivity activity;
	private int position;
	private String transportId;

	@Override
	protected int setLayoutId() {
		return R.layout.frag_out_createdtc_transline;
	}

	@Override
	public void initViews(View view) {
		loading = (RelativeLayout) view.findViewById(R.id.app_loading_layout);
		dtc_transport_listView = (ListView) view.findViewById(R.id.frag_out_createdtc_transline_list);
		tv_nodata = (TextView) view.findViewById(R.id.frag_out_createdtc_transline_list_nodata);
	}

	@Override
	public void initValues() {
		if(getActivity() != null && getActivity() instanceof OutCreateDtcDetailActivity){
			activity = (OutCreateDtcDetailActivity) getActivity();
			activity.transportList = new ArrayList<OutDTCTransportData>();
			titleList = new ArrayList<OutDTCListItem>();
			String transportCode1 = activity.request.getTransportCode1();
			
			loadDTCTransportData(transportCode1);
		}
	}

	@Override
	public void initListener() {
		
		dtc_transport_listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int posi, long arg3) {
				position = posi;
				createDTCDialog();
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
		if(REQUEST_TRANSPORTLINE == what) {
			bindTransportData(response);
		}
		
	}
	
	private void loadDTCTransportData(String transCode){
		OutDTCTransportSearchRequest preRequest = new OutDTCTransportSearchRequest();
		preRequest.setUserId(getPreferences().getUserId());
		preRequest.setToken(getPreferences().getTokenLast8());
		preRequest.setTransportCode1(transCode);
		asynDataRequest(RequestConstants.OUTDTC_CREATE_PRE, preRequest.toJsonString(), REQUEST_TRANSPORTLINE);
	}
	
	private void createDTCDialog(){
		
		CustomDialog dialog = new CustomDialog(getActivity());
		CustomDialog.Builder builder = new CustomDialog.Builder(getActivity());
		builder.setTitle(R.string.dialog_out_dtc_title);
		
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		View view = inflater.inflate(R.layout.dialog_out_dtc_create, null);
		
		final EditText edt_partno = (EditText) view.findViewById(R.id.dlg_edt_outdtc_partno);
		final EditText edt_npartno = (EditText) view.findViewById(R.id.dlg_edt_outdtc_npartno);
		final EditText edt_productName = (EditText) view.findViewById(R.id.dlg_edt_outdtc_productname);
		final EditText edt_trasnportQty1S = (EditText) view.findViewById(R.id.dlg_edt_outdtc_transqty1s);
		final EditText edt_trasnportQty1B = (EditText) view.findViewById(R.id.dlg_edt_outdtc_transqty1b);
		final EditText edt_outqty = (EditText)view.findViewById(R.id.dlg_edt_outdtc_outqty);
		final EditText edt_soPrice = (EditText) view.findViewById(R.id.dlg_edt_outdtc_soprice);
		
		edt_partno.setText(activity.transportList.get(position).getPartno());
		edt_npartno.setText(activity.transportList.get(position).getNpartno());
		edt_productName.setText(activity.transportList.get(position).getProductName());
		edt_trasnportQty1S.setText(activity.transportList.get(position).getTransportQty1S());
		edt_trasnportQty1B.setText(activity.transportList.get(position).getTransportQty1B());
		edt_outqty.setText(activity.transportList.get(position).getOutQty());
		edt_soPrice.setText(activity.transportList.get(position).getSoPrice());
		
		builder.setContentView(view);
		builder.setPositiveButton(getString(R.string.dialog_ok), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				activity.transportList.get(position).setOutQty(edt_outqty.getText().toString().trim());
				activity.transportList.get(position).setSoPrice(edt_soPrice.getText().toString().trim());
				titleList.get(position).setOutQty(edt_outqty.getText().toString().trim());
				titleList.get(position).setSoPrice(edt_soPrice.getText().toString().trim());
				adapter.notifyDataSetChanged();
				dialog.dismiss();
			}
		});
		
		builder.setNegativeButton(getString(R.string.dialog_cancel), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		
		dialog = builder.create();
		WindowManager windowManager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
		Display display = windowManager.getDefaultDisplay();
		int dlg_width = (int) (display.getWidth() * 0.8);
		Window dlg_window = dialog.getWindow();
		WindowManager.LayoutParams lp = dlg_window.getAttributes();
		lp.width = dlg_width;
		dlg_window.setAttributes(lp);
		dialog.setCanceledOnTouchOutside(true);
		dialog.show();
	}
	
	private void bindTransportData(String dataStr){
		try {
			JSONObject jsonObject = new JSONObject(dataStr);
			if(ResponseConstant.OK.equals(jsonObject.get(ResponseConstant.STATUS))){
				
				JSONArray jsonArray = jsonObject.getJSONArray("TRANSPORTLINE1S");
				
				if(jsonArray.length() < 1){
					tv_nodata.setVisibility(View.GONE);
				}
				
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject json = jsonArray.getJSONObject(i);
					
					OutDTCTransportData data = new OutDTCTransportData();
					data.setTransportLine1Id(json.getString("transportLine1Id"));
					data.setPartno(json.getString("partno"));
					data.setNpartno(json.getString("npartno"));
					data.setProductName(json.getString("productName"));
					data.setTransportQty1S(json.getString("transportQty1S"));
					data.setTransportQty1B(json.getString("transportQty1B"));
					if(transportId == null || "".equals(transportId)){
						transportId = json.getString("transportId");
					}
					data.setOutQty("");
					data.setSoPrice("");
					activity.transportList.add(data);
					
					OutDTCListItem item = new OutDTCListItem();
					item.setPartno(json.getString("partno"));
					item.setTransportQty1S(json.getString("transportQty1S"));
					item.setOutQty("0"+ getString(R.string.sd_qtyunit_pack));
					item.setSoPrice(getString(R.string.app_data_null));
					titleList.add(item);
					
					adapter = new OutDTCCreateDataAdapter(titleList, getActivity());
					dtc_transport_listView.setAdapter(adapter);
					activity.enableSave();
				}
				
			} else {
				showToast(R.string.msg_out_credtc_notransport);
				// getActivity().finish();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
