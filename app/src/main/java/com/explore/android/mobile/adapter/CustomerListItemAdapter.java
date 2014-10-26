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
import com.explore.android.mobile.model.Customer;

public class CustomerListItemAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<Customer> cusList;

	public CustomerListItemAdapter(Context con, List<Customer> cusList) {
		inflater = LayoutInflater.from(con);
		this.cusList = new ArrayList<Customer>();
		this.cusList = cusList;
	}

	@Override
	public int getCount() {
		return cusList.size();
	}

	@Override
	public Object getItem(int position) {
		return cusList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int postion, View convertView, ViewGroup arg2) {
		ViewHolder holder;
		if ( convertView == null ) {
			convertView = inflater.inflate(R.layout.list_customer_item, null);
			holder = new ViewHolder();
			holder.cus_code = (TextView) convertView.findViewById(R.id.frag_cuslist_cuscode);
			holder.cus_name = (TextView) convertView.findViewById(R.id.frag_cuslist_cusname);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.cus_name.setText(cusList.get(postion).getCustomerName());
		holder.cus_code.setText(cusList.get(postion).getCustomerCode());
		return convertView;
	}
	
	static class ViewHolder{
		TextView cus_code;
		TextView cus_name;
	}

}
