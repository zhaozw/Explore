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
import com.explore.android.mobile.model.Product;

public class ProductListItemAdapter extends BaseAdapter{

	private List<Product> proList;
	private LayoutInflater inflater;
	
	public ProductListItemAdapter(Context con, List<Product> proList){
		inflater = LayoutInflater.from(con);
		this.proList = new ArrayList<Product>();
		this.proList = proList;
	}
	
	@Override
	public int getCount() {
		return proList.size();
	}

	@Override
	public Object getItem(int position) {
		return proList.get(position);
	}

	@Override
	public long getItemId(int id) {
		return id;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		
		ViewHolder holder;
		
		if (convertView == null){
			convertView = inflater.inflate(R.layout.list_product_adapter_item, null);
			holder = new ViewHolder();
			holder.partNo = (TextView) convertView.findViewById(R.id.frag_prolist_partno);
			holder.proName = (TextView) convertView.findViewById(R.id.frag_prolist_proname);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		StringBuilder builder1 = new StringBuilder();
		builder1.append(proList.get(position).getPartNo());
		builder1.append("," + proList.get(position).getNpartNo());
		holder.partNo.setText(builder1);
		
		StringBuilder builder2 = new StringBuilder();
		builder2.append(proList.get(position).getProName());
		builder2.append("," + proList.get(position).getSts());
		holder.proName.setText(builder2);
		
		return convertView;
	}
	
	static class ViewHolder{
		TextView partNo;
		TextView proName;
	}

}
