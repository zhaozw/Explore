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
import com.explore.android.mobile.model.Transport;

public class TransportListItemAdapter extends BaseAdapter{
	
	private List<Transport> transList;
	private LayoutInflater inflater;
	
	public TransportListItemAdapter(Context con, List<Transport> transList){
		inflater = LayoutInflater.from(con);
		this.transList = new ArrayList<Transport>();
		this.transList = transList;
	}

	@Override
	public int getCount() {
		return transList.size();
	}

	@Override
	public Object getItem(int position) {
		return transList.get(position);
	}

	@Override
	public long getItemId(int id) {
		return id;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		
		ViewHolder holder;
		if (convertView == null){
			convertView = inflater.inflate(R.layout.list_transport_item, null);
			holder = new ViewHolder();
			holder.outtype = (TextView) convertView.findViewById(R.id.frag_translist_outtype);
			holder.creByName = (TextView) convertView.findViewById(R.id.frag_translist_crebyname);
			holder.creByTime = (TextView) convertView.findViewById(R.id.frag_translist_crebytime);
			holder.comByName = (TextView) convertView.findViewById(R.id.frag_translist_combyname);
			holder.comByTime = (TextView) convertView.findViewById(R.id.frag_translist_combytime);
			holder.transCode1 = (TextView) convertView.findViewById(R.id.frag_translist_code1);
			holder.transCode2 = (TextView) convertView.findViewById(R.id.frag_translist_code2);
			holder.warehouse = (TextView) convertView.findViewById(R.id.frag_translist_warehouse);
			holder.vatto = (TextView) convertView.findViewById(R.id.frag_translist_vatto);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.outtype.setText(transList.get(position).getOuttype());
		holder.creByName.setText(transList.get(position).getCreateByName());
		holder.creByTime.setText(transList.get(position).getCreateByTime());
		holder.comByName.setText(transList.get(position).getCommitByName());
		holder.comByTime.setText(transList.get(position).getCommitByTime());
		holder.transCode1.setText(transList.get(position).getTransportCode1());
		holder.transCode2.setText(transList.get(position).getTransportCode2());
		holder.warehouse.setText(transList.get(position).getWarehouse());
		holder.vatto.setText(transList.get(position).getVatto());
		
		return convertView;
	}
	
	static class ViewHolder{
		TextView outtype;
		TextView creByName;
		TextView creByTime;
		TextView comByName;
		TextView comByTime;
		TextView transCode1;
		TextView transCode2;
		TextView warehouse;
		TextView vatto;
	}

}
