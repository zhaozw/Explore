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
import com.explore.android.mobile.model.OutDTCListItem;

public class OutDTCCreateDataAdapter extends BaseAdapter{

	private List<OutDTCListItem> titleList;
	private LayoutInflater inflater;
	
	public OutDTCCreateDataAdapter(List<OutDTCListItem> list, Context con){
		titleList = new ArrayList<OutDTCListItem>();
		inflater = LayoutInflater.from(con);
		titleList = list;
	}
	
	
	@Override
	public int getCount() {
		return titleList.size();
	}

	@Override
	public Object getItem(int position) {
		return titleList.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int posotion, View convertView, ViewGroup arg2) {
		ViewHolder holder;
		if ( convertView == null ) {
			convertView = inflater.inflate(R.layout.list_outdtc_item, null);
			holder = new ViewHolder();
			holder.tv_partno = (TextView) convertView.findViewById(R.id.list_item_outdtc_partno);
			holder.tv_qty = (TextView) convertView.findViewById(R.id.list_item_outdtc_qty);
			holder.tv_soprice = (TextView) convertView.findViewById(R.id.list_item_outdtc_soprice);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.tv_partno.setText(titleList.get(posotion).getPartno());
		holder.tv_qty.setText(titleList.get(posotion).getOutQty() + "/" + titleList.get(posotion).getTransportQty1S());
		holder.tv_soprice.setText("" + titleList.get(posotion).getSoPrice());
		return convertView;
	}
	
	static class ViewHolder{
		TextView tv_partno;
		TextView tv_qty;
		TextView tv_soprice;
	}

}
