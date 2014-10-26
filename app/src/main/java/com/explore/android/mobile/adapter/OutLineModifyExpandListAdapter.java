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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.explore.android.R;
import com.explore.android.core.base.BaseAsynTask.TaskOnPostExecuteListener;
import com.explore.android.core.base.BaseAsynTask.TaskResult;
import com.explore.android.core.http.AsynDataTask;
import com.explore.android.core.http.SubmitTask;
import com.explore.android.core.model.ExResponse;
import com.explore.android.mobile.activity.out.OutDetailOutLineFragment;
import com.explore.android.mobile.activity.outline.OutLineModifyData;
import com.explore.android.mobile.common.SharePreferencesManager;
import com.explore.android.mobile.constants.AppStatus;
import com.explore.android.mobile.constants.ErrorConstants;
import com.explore.android.mobile.constants.RequestConstants;
import com.explore.android.mobile.constants.ResponseConstant;
import com.explore.android.mobile.data.request.OutLine1DeleteRequest;
import com.explore.android.mobile.data.request.OutLine1UpdatePreRequest;
import com.explore.android.mobile.data.request.OutLine1UpdateRequest;
import com.explore.android.mobile.model.ExpandListData;
import com.explore.android.mobile.view.CustomDialog;
import com.explore.android.mobile.view.DialogUtil;

public class OutLineModifyExpandListAdapter extends BaseExpandableListAdapter{

	public static final int MODE_NEW = 1;
	public static final int MODE_EDIT = 2;
	
	private LayoutInflater inflater;
	private List<ExpandListData> proList;
	private Context context;
	private OutLineModifyData preData;
	private OutDetailOutLineFragment outlineFragment;
	private boolean editable;
	
	public OutLineModifyExpandListAdapter(List<ExpandListData> list, Context con, OutDetailOutLineFragment outlineFragment, boolean editable){
		inflater = LayoutInflater.from(con);
		proList = new ArrayList<ExpandListData>();
		proList = list;
		context = con;
		this.editable = editable;
		this.outlineFragment = outlineFragment;
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
		if(childView == null){
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
	
	static class GroupViewHolder{
		RelativeLayout item_layout;
		LinearLayout button_layout;
		TextView title1;
		TextView title2;
		TextView title3;
		ImageButton btn_edit;
		ImageButton btn_delete;
	}

	@Override
	public View getGroupView(int position, boolean isExpanded, View groupView, ViewGroup arg3) {
		final int index = position;
		GroupViewHolder holder;
		if ( groupView == null) {
			groupView = inflater.inflate(R.layout.exlist_outline_modify_group_item, null);
			holder = new GroupViewHolder();
			holder.item_layout = (RelativeLayout) groupView.findViewById(R.id.exlist_outline_modify_group_layout);
			holder.button_layout = (LinearLayout) groupView.findViewById(R.id.exlist_outline_modify_group_btns);
			holder.title1 = (TextView) groupView.findViewById(R.id.exlist_outline_modify_group_title1);
			holder.title2 = (TextView) groupView.findViewById(R.id.exlist_outline_modify_group_title2);
			holder.title3 = (TextView) groupView.findViewById(R.id.exlist_outline_modify_group_title3);
			holder.btn_edit = (ImageButton) groupView.findViewById(R.id.exlist_outline_group_modify_btn_edit);
			holder.btn_delete = (ImageButton) groupView.findViewById(R.id.exlist_outline_modify_group_btn_delete);
			groupView.setTag(holder);
		} else {
			holder = (GroupViewHolder) groupView.getTag();
		}
		if (!editable) {
		    holder.button_layout.setVisibility(View.GONE);
		}
		holder.title1.setText(context.getResources().getString(R.string.outline_npartno_title) + proList.get(index).getTitle4() + "[" +proList.get(index).getTitle3() + "]");
		holder.title2.setText(proList.get(index).getTitle1() + ",");
		holder.title3.setText(proList.get(index).getTitle2());
		
		if(isExpanded){
			holder.item_layout.setBackgroundColor(context.getResources().getColor(R.color.out_exlist_group_selected));
		}else{
			holder.item_layout.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.exlist_group_item_style));
		}
		
		holder.btn_edit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				createEditDialog(index);
			}
		});
		
		holder.btn_delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				DialogUtil.createMessageDialog(context, R.string.outline_dlg_delete_title,
						R.string.outline_dlg_delete_msg, new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								deleteOutLine1(index);
								dialog.dismiss();
							}
						});
			}
		});

		return groupView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		return false;
	}
	
	private CustomDialog editDialog;
	private void createEditDialog(final int index){
		editDialog = new CustomDialog(context);
		CustomDialog.Builder builder = new CustomDialog.Builder(context);
		builder.setTitle(R.string.out_dialog_title_product_edit);
		
		View conView = inflater.inflate(R.layout.dialog_outline_edit, null);
		
		final DialogViewHolder viewHolder = new DialogViewHolder();
		
		viewHolder.outline_layout = (LinearLayout) conView.findViewById(R.id.dlg_outline_edit_info_layout);
		viewHolder.loadding = (ProgressBar)conView.findViewById(R.id.dlg_outline_edit_loading);
		viewHolder.tv_load_failed = (TextView)conView.findViewById(R.id.dlg_outline_edit_loaddata_failed);
		viewHolder.tv_partno = (TextView) conView.findViewById(R.id.dlg_tv_outline_edit_partno);
		viewHolder.tv_npartno = (TextView) conView.findViewById(R.id.dlg_tv_outline_edit_npartno);
		viewHolder.tv_proname = (TextView) conView.findViewById(R.id.dlg_tv_outline_edit_proname);
		viewHolder.spi_prosts = (Spinner) conView.findViewById(R.id.dlg_spi_outline_edit_prosts);
		viewHolder.edt_logon = (EditText) conView.findViewById(R.id.dlg_edt_outline_edit_logno);
		viewHolder.edt_outqty = (EditText) conView.findViewById(R.id.dlg_edt_outline_edit_outqty);
		viewHolder.spi_qtyunit = (Spinner) conView.findViewById(R.id.dlg_spi_outline_edit_qtyunit);
		viewHolder.edt_soprice = (EditText) conView.findViewById(R.id.dlg_edt_outline_edit_soprice);
		viewHolder.spi_priceunit = (Spinner) conView.findViewById(R.id.dlg_spi_outline_edit_priceunit);
		viewHolder.edt_remarks = (EditText) conView.findViewById(R.id.dlg_edt_outline_edit_remarks);
		
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
				
				if (preData == null){
					Toast.makeText(context, context.getResources().getString(R.string.msg_response_failed), Toast.LENGTH_SHORT).show();
				} else if (Integer.parseInt(viewHolder.edt_outqty.getText().toString()) == 0) {
					Toast.makeText(context, context.getResources().getString(R.string.msg_outline_qty_could_not_empty), Toast.LENGTH_SHORT).show();
				} else {
					editOutLine1(viewHolder,index);
					dialog.dismiss();
				}
			}
		});
		
		editDialog = builder.create();
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = windowManager.getDefaultDisplay();
		int dlg_width = (int) (display.getWidth() * 0.8);
		Window dlg_window = editDialog.getWindow();
		WindowManager.LayoutParams lp = dlg_window.getAttributes();
		lp.width = dlg_width;
		dlg_window.setAttributes(lp);
		editDialog.setCanceledOnTouchOutside(false);
		
		editDialog.show();
	}
	
	private void editOutLine1(final DialogViewHolder viewHolder, final int index){
		
		DialogUtil.createProgressDialog(context, R.string.out_dialog_title_product_add, R.string.msg_loadding);
		
		SubmitTask outline1UpdateTask = new SubmitTask();
		
		outline1UpdateTask.setTaskOnPostExecuteListener(new TaskOnPostExecuteListener<ExResponse>() {
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
							outlineFragment.loadOutInfoDetail();
							outlineFragment.signalForUpdate();
							Toast.makeText(context, context.getResources().getString(R.string.app_action_success), Toast.LENGTH_LONG).show();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		SharePreferencesManager preferences = SharePreferencesManager.getInstance(context);
		OutLine1UpdateRequest request = new OutLine1UpdateRequest();
			
		request.setUserId(preferences.getUserId());
		request.setToken(preferences.getTokenLast8());
		request.setOutLine1Id(proList.get(index).getId());
		request.setProductId(proList.get(index).getId2());
		request.setIsDd("N");
		request.setOutType("SAL");
		request.setOutQty1(viewHolder.edt_outqty.getText().toString().trim());
		request.setQtyUnit(preData.getQtyUnitList().get(viewHolder.spi_qtyunit.getSelectedItemPosition()).getKey());
		request.setPrice(viewHolder.edt_soprice.getText().toString().trim());
		request.setPriceUnit(preData.getQtyUnitList().get(viewHolder.spi_priceunit.getSelectedItemPosition()).getKey());
		request.setLogNo(viewHolder.edt_logon.getText().toString().trim());
		request.setWhs(preData.getWhsList().get(viewHolder.spi_prosts.getSelectedItemPosition()).getValue());
		request.setRemarks(viewHolder.edt_remarks.getText().toString().trim());
			
		outline1UpdateTask.execute(preferences.getHttpUrl(), RequestConstants.OUTLINE_UPDATE, request.toJsonString());
	}
	
	private void deleteOutLine1(final int index){
		
		DialogUtil.createProgressDialog(context, R.string.out_dialog_title_product_add, R.string.msg_loadding);
		
		SubmitTask outLine1DeleteTask = new SubmitTask();
		outLine1DeleteTask.setTaskOnPostExecuteListener(new TaskOnPostExecuteListener<ExResponse>() {
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
							outlineFragment.loadOutInfoDetail();
							Toast.makeText(context, context.getResources().getString(R.string.app_action_success), Toast.LENGTH_LONG).show();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		SharePreferencesManager preferences = SharePreferencesManager.getInstance(context);
		OutLine1DeleteRequest request = new OutLine1DeleteRequest();
		request.setUserId(preferences.getUserId());
		request.setToken(preferences.getTokenLast8());
		request.setOutLine1Id(proList.get(index).getId());
		
		outLine1DeleteTask.execute(preferences.getHttpUrl(), RequestConstants.OUTLINE_DELETE, request.toJsonString());
	}
	
	private void loadOutLineAddPreData(int index, final DialogViewHolder viewHolder){
		viewHolder.loadding.setVisibility(View.VISIBLE);
		viewHolder.tv_load_failed.setVisibility(View.GONE);
		
		OutLine1UpdatePreRequest request = new OutLine1UpdatePreRequest();
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
							
							preData = new OutLineModifyData();
							preData.setPriceUnitList(jsonObject.getJSONArray("PRODUCTUNITS"));
							preData.setQtyUnitList(jsonObject.getJSONArray("PRODUCTUNITS"));
							
							viewHolder.spi_prosts.setAdapter(new SimpleSpinnerAdapter(context, preData.getDefaultWhsList()));
							viewHolder.spi_qtyunit.setAdapter(new SimpleSpinnerAdapter(context, preData.getQtyUnitList()));
							viewHolder.spi_priceunit.setAdapter(new SimpleSpinnerAdapter(context, preData.getPriceUnitList()));
							
							viewHolder.spi_qtyunit.setSelection(preData.getQtyUnitList().size() - 1);
							viewHolder.spi_priceunit.setSelection(preData.getQtyUnitList().size() - 1);
							
							preData.initData(jsonObject.getJSONObject("OUTLINE1UPDATEPREDATA"));
							
							if(!"null".equals(preData.getLogNo())){
								viewHolder.edt_logon.setText(preData.getLogNo());
							}
							
							if(!"null".equals(preData.getRemarks())){
								viewHolder.edt_remarks.setText(preData.getRemarks());
							}
							
							viewHolder.tv_partno.setText(preData.getPartNo());
							viewHolder.tv_npartno.setText(preData.getNpartNo());
							viewHolder.tv_proname.setText(preData.getProductName());
							viewHolder.edt_outqty.setText(preData.getOutQty());
							viewHolder.edt_soprice.setText(preData.getPrice());
							viewHolder.spi_prosts.setSelection(preData.getWhsSelection());
							viewHolder.loadding.setVisibility(View.GONE);
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
		request.setOutLine1Id(proList.get(index).getId());
		request.setProductId(proList.get(index).getId2());
			
		loadPreDataTask.execute(preferences.getHttpUrl(), RequestConstants.OUTLINE_UPDATE_PREDATA, request.toJsonString());
	}
	
	private static class DialogViewHolder{
		public LinearLayout outline_layout;
		public ProgressBar loadding;
		public TextView tv_load_failed;
		public TextView tv_partno;
		public TextView tv_npartno;
		public TextView tv_proname;
		public Spinner spi_prosts;
		public EditText edt_logon;
		public EditText edt_outqty;
		public Spinner spi_qtyunit;
		public EditText edt_soprice;
		public Spinner spi_priceunit;
		public EditText edt_remarks;
	}
}
