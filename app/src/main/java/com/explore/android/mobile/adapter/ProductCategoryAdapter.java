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
import com.explore.android.mobile.model.ProductCategory;

public class ProductCategoryAdapter extends BaseAdapter{

	private LayoutInflater inflater;
	private List<ProductCategory> list;
	
	public ProductCategoryAdapter(Context con, List<ProductCategory> list){
		inflater = LayoutInflater.from(con);
		this.list = new ArrayList<ProductCategory>();
		this.list = list;
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int id) {
		return id;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHolder holder;
		
		if(convertView == null){
			convertView = inflater.inflate(R.layout.spinner_simple_item, null);
			holder = new ViewHolder();
			holder.content = (TextView) convertView.findViewById(R.id.simple_spinner_content);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.content.setText(list.get(position).getProductCategoryName());
		return convertView;
	}
	
	static class ViewHolder{
		TextView content;
	}

}
