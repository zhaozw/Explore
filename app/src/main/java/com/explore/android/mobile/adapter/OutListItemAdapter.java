package com.explore.android.mobile.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.explore.android.R;
import com.explore.android.mobile.constants.SDConstants;
import com.explore.android.mobile.data.ValueToResource;
import com.explore.android.mobile.model.Out;

public class OutListItemAdapter extends BaseAdapter{

	private List<Out> outList;
	private LayoutInflater inflater;
	
	public OutListItemAdapter(Context con, List<Out> list){
		inflater = LayoutInflater.from(con);
		outList = new ArrayList<Out>();
		outList = list;
	}
	
	@Override
	public int getCount() {
		return outList.size();
	}

	@Override
	public Object getItem(int position) {
		return outList.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_out_adapter_item, null);
			holder = new ViewHolder();
			holder.outcode2 = (TextView) convertView.findViewById(R.id.frag_outlist_outcode2);
			holder.customerDept = (TextView) convertView.findViewById(R.id.frag_outlist_cusdept);
			holder.warehouse = (TextView) convertView.findViewById(R.id.frag_outlist_warehouse);
			holder.outType = (TextView) convertView.findViewById(R.id.frag_outlist_outtype);
			holder.shipTo = (TextView) convertView.findViewById(R.id.frag_outlist_shipto);
			holder.vatTo = (TextView) convertView.findViewById(R.id.frag_outlist_vatto);
			holder.createByTime = (TextView) convertView.findViewById(R.id.frag_outlist_createbytime);
			holder.sts = (TextView) convertView.findViewById(R.id.frag_outlist_sts);
			holder.outMoney1 =(TextView)  convertView.findViewById(R.id.frag_outlist_outmoney1);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		String outType1 = outList.get(position).getOutType();
		String outType2 = outList.get(position).getOutType2();
		String type;
		
		if(SDConstants.OUTTYPE_SAL.equals(outType1) && !SDConstants.OUTTYPE2_GEN.equals(outType2)){
			type = outType2;
		} else {
			type = outType1;
		}
		
		if(ValueToResource.getOutType(type) != R.string.app_null){
			holder.outType.setText(ValueToResource.getOutType(type));
		} else {
			holder.outType.setText(type);
		}
		
		if(!"null".equals(outList.get(position).getOutCode2())){
			holder.outcode2.setText(outList.get(position).getOutCode2());
		} else {
			holder.outcode2.setText(R.string.app_no_value);
		}
		
		holder.customerDept.setText(outList.get(position).getCustomerDept());
		holder.warehouse.setText(outList.get(position).getWarehouse());
		holder.shipTo.setText(outList.get(position).getShipTo());
		holder.vatTo.setText(outList.get(position).getVatTo());
		holder.createByTime.setText(outList.get(position).getCreateByTime());
		holder.outMoney1.setText(outList.get(position).getOutMoney1());
		
		String outSts = outList.get(position).getSts();
		if(ValueToResource.getOutStsValue(outSts) != R.string.app_null){
			holder.sts.setText(ValueToResource.getOutStsValue(outSts));
		} else {
			holder.sts.setText(outSts);
		}
		
		return convertView;
	}
	
	static class ViewHolder{
		TextView outcode2;
		TextView customerDept;
		TextView warehouse;
		TextView outType;
		TextView shipTo;
		TextView vatTo;
		TextView createByTime;
		TextView sts;
		TextView outMoney1;
	}

}
