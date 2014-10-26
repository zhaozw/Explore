package com.explore.android.mobile.adapter;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.DialogInterface;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.explore.android.R;
import com.explore.android.core.base.BaseAsynTask.TaskOnPostExecuteListener;
import com.explore.android.core.base.BaseAsynTask.TaskResult;
import com.explore.android.core.http.AsynDataTask;
import com.explore.android.core.http.SubmitTask;
import com.explore.android.core.model.ExResponse;
import com.explore.android.mobile.common.SharePreferencesManager;
import com.explore.android.mobile.constants.AppStatus;
import com.explore.android.mobile.constants.ErrorConstants;
import com.explore.android.mobile.constants.RequestConstants;
import com.explore.android.mobile.constants.ResponseConstant;
import com.explore.android.mobile.data.predata.OutLineAddPreData;
import com.explore.android.mobile.data.request.OutLine1AddPreDataRequest;
import com.explore.android.mobile.data.request.OutLine1AddRequest;
import com.explore.android.mobile.model.ExpandListData;
import com.explore.android.mobile.view.CustomDialog;
import com.explore.android.mobile.view.DialogUtil;

public class OutLineAddExpandListAdapter extends BaseExpandableListAdapter{

	public static final int MODE_NEW = 1;
	public static final int MODE_EDIT = 2;
	
	private LayoutInflater inflater;
	private List<ExpandListData> proList;
	private Context context;
	private String outId;
	private String soPriceLogonId;
	private String customerId;
	private OutLineAddPreData preData;
	
	public OutLineAddExpandListAdapter(List<ExpandListData> list, Context con, String outId, String soPriceLogonId, String customerId){
		inflater = LayoutInflater.from(con);
		proList = new ArrayList<ExpandListData>();
		proList = list;
		context = con;
		this.soPriceLogonId = soPriceLogonId;
		this.customerId = customerId;
		this.outId = outId;
	}
	
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return proList.get(groupPosition).getDetailList().get(childPosition);
	}

	@Override
	public long getChildId(int arg0, int arg1) {
		return 0;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean arg2, View childView, ViewGroup arg4) {
		ChildViewHolder holder;
		if ( childView == null ) {
			childView = inflater.inflate(R.layout.exlist_kvtype_child_item, null);
			holder = new ChildViewHolder();
			holder.title = (TextView) childView.findViewById(R.id.exlist_tv_child_title);
			holder.content = (TextView) childView.findViewById(R.id.exlist_tv_child_content);
			childView.setTag(holder);
		} else {
			holder = (ChildViewHolder) childView.getTag();
		}
		
		holder.title.setText(proList.get(groupPosition).getDetailList().get(childPosition).getResourceId());
		holder.content.setText(proList.get(groupPosition).getDetailList().get(childPosition).getContent());
		
		return childView;
	}
	
	static class ChildViewHolder{
		TextView title;
		TextView content;
	}

	@Override
	public int getChildrenCount(int position) {
		return proList.get(position).getDetailList().size();
	}

	@Override
	public Object getGroup(int position) {
		return proList.get(position);
	}

	@Override
	public int getGroupCount() {
		return proList.size();
	}

	@Override
	public long getGroupId(int arg0) {
		return 0;
	}

	@Override
	public View getGroupView(int position, boolean isExpanded, View groupView, ViewGroup arg3) {
		final int index = position;
		GroupViewHolder holder;
		if ( groupView == null ) {
			groupView = inflater.inflate(R.layout.exlist_title_button_group_item, null);
			holder = new GroupViewHolder();
			holder.item_layout = (LinearLayout) groupView.findViewById(R.id.exlist_withbtn_layout);
			holder.title1 = (TextView) groupView.findViewById(R.id.exlist_withbtn_tv_group_title1);
			holder.title2 = (TextView) groupView.findViewById(R.id.exlist_withbtn_tv_group_title2);
			holder.btn_operation = (ImageButton) groupView.findViewById(R.id.exlist_withbtn_tv_group_btn);
			groupView.setTag(holder);
		} else {
			holder = (GroupViewHolder) groupView.getTag();
		}
		
		
		if(isExpanded){
			holder.item_layout.setBackgroundColor(context.getResources().getColor(R.color.out_exlist_group_selected));
		}else{
			holder.item_layout.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.exlist_group_item_style));
		}
		
		holder.btn_operation.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				createDialog(index);
			}
		});

		holder.title1.setText(proList.get(index).getTitle1());
		holder.title2.setText(proList.get(index).getTitle2());
		
		return groupView;
	}
	
	static class GroupViewHolder{
		LinearLayout item_layout;
		TextView title1;
		TextView title2;
		ImageButton btn_operation;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		return false;
	}
	
	private CustomDialog dialog;
	private void createDialog(final int index){
		dialog = new CustomDialog(context);
		CustomDialog.Builder builder = new CustomDialog.Builder(context);
		builder.setTitle(R.string.out_dialog_title_product_add);
		
		View conView = inflater.inflate(R.layout.dialog_outline_add, null);
		
		final DialogViewHolder viewHolder = new DialogViewHolder();
		
		viewHolder.outline_layout = (LinearLayout) conView.findViewById(R.id.dlg_outline_add_info_layout);
		viewHolder.loadding = (ProgressBar)conView.findViewById(R.id.dlg_outline_add_loading);
		viewHolder.tv_load_failed = (TextView)conView.findViewById(R.id.dlg_outline_add_loaddata_failed);
		viewHolder.edt_partno = (EditText) conView.findViewById(R.id.dlg_edt_outline_add_partno);
		viewHolder.edt_npartno = (EditText) conView.findViewById(R.id.dlg_edt_outline_add_npartno);
		viewHolder.edt_proname = (EditText) conView.findViewById(R.id.dlg_edt_outline_add_proname);
		viewHolder.spi_prosts = (Spinner) conView.findViewById(R.id.dlg_spi_outline_add_prosts);
		viewHolder.edt_logon = (EditText) conView.findViewById(R.id.dlg_edt_outline_add_logno);
		viewHolder.edt_outqty = (EditText) conView.findViewById(R.id.dlg_edt_outline_add_outqty);
		viewHolder.spi_qtyunit = (Spinner) conView.findViewById(R.id.dlg_spi_outline_add_qtyunit);
		viewHolder.edt_soprice = (EditText) conView.findViewById(R.id.dlg_edt_outline_add_soprice);
		viewHolder.spi_priceunit = (Spinner) conView.findViewById(R.id.dlg_spi_outline_add_priceunit);
		viewHolder.edt_remarks = (EditText) conView.findViewById(R.id.dlg_edt_outline_add_remarks);
		
		loadOutLineAddPreData(index, viewHolder);
		
		builder.setContentView(conView);
			
		builder.setNegativeButton(context.getResources().getString(R.string.dialog_cancel), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		
		builder.setPositiveButton(context.getResources().getString(R.string.dialog_save), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				if(preData == null){
					Toast.makeText(context, context.getResources().getString(R.string.msg_response_failed), Toast.LENGTH_SHORT).show();
				}else{
					addOutLine1(viewHolder, index);
					dialog.dismiss();
				}
			}
		});
		
		dialog = builder.create();
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = windowManager.getDefaultDisplay();
		int dlg_width = (int) (display.getWidth() * 0.8);
		Window dlg_window = dialog.getWindow();
		WindowManager.LayoutParams lp = dlg_window.getAttributes();
		lp.width = dlg_width;
		dlg_window.setAttributes(lp);
		dialog.setCanceledOnTouchOutside(false);
		
		dialog.show();
	}
	
	private void addOutLine1(DialogViewHolder viewHolder, int index){
		
		DialogUtil.createProgressDialog(context, R.string.out_dialog_title_product_add, R.string.msg_loadding);
		
		SubmitTask outlineAddTask = new SubmitTask();
		
		outlineAddTask.setTaskOnPostExecuteListener(new TaskOnPostExecuteListener<ExResponse>() {
			@Override
			public void onPostExecute(TaskResult<? extends ExResponse> result) {
				ExResponse response = result.Value;
				DialogUtil.progressDialog.dismiss();
				if (ErrorConstants.VALUE_NULL.equals(response.getResMessage())) {
					if (response.getResCode() == -3) {
						Toast.makeText(context, context.getResources().getString(R.string.msg_request_timeout), Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(context, context.getResources().getString(R.string.msg_server_nores), Toast.LENGTH_LONG).show();
					}
					return;
				} else {
					try {
						JSONObject jsonObject = new JSONObject(response.getResMessage());
						if(ResponseConstant.OK.equals(jsonObject.get(ResponseConstant.STATUS))){
							AppStatus.OUTLINE_DATA_ADD = true;
							Toast.makeText(context, context.getResources().getString(R.string.app_action_success), Toast.LENGTH_LONG).show();
						} else {
							Toast.makeText(context, context.getResources().getString(R.string.app_action_failed), Toast.LENGTH_LONG).show();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		SharePreferencesManager preferences = SharePreferencesManager.getInstance(context);
		OutLine1AddRequest request = new OutLine1AddRequest();
				
		request.setUserId(preferences.getUserId());
		request.setToken(preferences.getTokenLast8());
		request.setOutId(outId);
		request.setOutType("SAL");
		request.setProductId(proList.get(index).getId());
		request.setPartNo(viewHolder.edt_partno.getText().toString().trim());
		request.setNpartNo(viewHolder.edt_npartno.getText().toString().trim());
		request.setProductName(viewHolder.edt_proname.getText().toString().trim());
		request.setWhs(preData.getDefaultWmsList().get(viewHolder.spi_prosts.getSelectedItemPosition()).getValue());
		request.setLogNo(viewHolder.edt_logon.getText().toString().trim());
		request.setOutQty1(viewHolder.edt_outqty.getText().toString().trim());
		request.setSoPrice(viewHolder.edt_soprice.getText().toString().trim());
		request.setRemarks(viewHolder.edt_remarks.getText().toString().trim());
		request.setQtyUnit(preData.getQtyUnitList().get(viewHolder.spi_qtyunit.getSelectedItemPosition()).getKey());
		request.setSoPriceLineId(preData.getSoPriceLineId());
		request.setPriceUnit(preData.getPriceUnitList().get(viewHolder.spi_priceunit.getSelectedItemPosition()).getKey());
				
		outlineAddTask.execute(preferences.getHttpUrl(), RequestConstants.OUTLINE_ADD, request.toJsonString(), request.toJson2String());
	}
	
	private void loadOutLineAddPreData(final int index, final DialogViewHolder viewHolder){
		viewHolder.loadding.setVisibility(View.VISIBLE);
		viewHolder.tv_load_failed.setVisibility(View.GONE);
		OutLine1AddPreDataRequest request = new OutLine1AddPreDataRequest();
		AsynDataTask loadPreDataTask = new AsynDataTask();

		loadPreDataTask.setTaskOnPostExecuteListener(new TaskOnPostExecuteListener<ExResponse>() {

			@Override
			public void onPostExecute(TaskResult<? extends ExResponse> result) {
				ExResponse response = result.Value;
				if (ErrorConstants.VALUE_NULL.equals(response.getResMessage())) {
					if (response.getResCode() == -3) {
						Toast.makeText(context, context.getResources().getString(R.string.msg_request_timeout), Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(context, context.getResources().getString(R.string.msg_server_nores), Toast.LENGTH_LONG).show();
					}
					viewHolder.tv_load_failed.setVisibility(View.VISIBLE);
					return;
				} else {
					try {
						JSONObject jsonObject = new JSONObject(response.getResMessage());
						if(ResponseConstant.OK.equals(jsonObject.get(ResponseConstant.STATUS))){
							
							preData = new OutLineAddPreData();
							
							preData.setPriceUnitList(jsonObject.getJSONArray("PRODUCTUNITS"));
							preData.setQtyUnitList(jsonObject.getJSONArray("PRODUCTUNITS"));
							preData.setDataInit(jsonObject.getJSONObject("OUTLINEADDPREDATA"));
							
							viewHolder.spi_prosts.setAdapter(new SimpleSpinnerAdapter(context, preData.getDefaultWmsList()));
							viewHolder.spi_qtyunit.setAdapter(new SimpleSpinnerAdapter(context, preData.getQtyUnitList()));
							viewHolder.spi_priceunit.setAdapter(new SimpleSpinnerAdapter(context, preData.getPriceUnitList()));
								
							viewHolder.spi_qtyunit.setSelection(preData.getQtyUnitPosition());
							viewHolder.spi_priceunit.setSelection(preData.getPriceUnitPosition());
							
							if(!"null".equals(preData.getLogNo())){
								viewHolder.edt_logon.setText(preData.getLogNo());
							}
							
							viewHolder.edt_soprice.setText(preData.getSoPrice());
							if(!"".equals(preData.getOutQty()) && !"null".equals(preData.getOutQty())){
								viewHolder.edt_outqty.setText(preData.getOutQty());
							}
							viewHolder.edt_partno.setText(proList.get(index).getTitle2());
							viewHolder.edt_npartno.setText(proList.get(index).getTitle3());
							viewHolder.edt_proname.setText(proList.get(index).getTitle1());
							viewHolder.outline_layout.setVisibility(View.VISIBLE);
						}
						
					} catch (JSONException e) {
						e.printStackTrace();
						preData = null;
						viewHolder.tv_load_failed.setVisibility(View.VISIBLE);
					}
					
					viewHolder.loadding.setVisibility(View.GONE);
				}
			}
		});
		
		SharePreferencesManager preferences = SharePreferencesManager.getInstance(context);
		request.setUserId(preferences.getUserId());
		request.setToken(preferences.getTokenLast8());
		request.setOutId(outId);
		request.setProductId(proList.get(index).getId());
		request.setSoPriceLogonId(soPriceLogonId);
		request.setCustomerId(customerId);
			
		loadPreDataTask.execute(preferences.getHttpUrl(), RequestConstants.OUTLINE_ADD_PREDATA, request.toJsonString());
	}
	
	private class DialogViewHolder{
		public LinearLayout outline_layout;
		public ProgressBar loadding;
		public TextView tv_load_failed;
		public EditText edt_partno;
		public EditText edt_npartno;
		public EditText edt_proname;
		public Spinner spi_prosts;
		public EditText edt_logon;
		public EditText edt_outqty;
		public Spinner spi_qtyunit;
		public EditText edt_soprice;
		public Spinner spi_priceunit;
		public EditText edt_remarks;
	}

	public OutLineAddPreData getPreData() {
		return preData;
	}

	public void setPreData(OutLineAddPreData preData) {
		this.preData = preData;
	}
	
}
