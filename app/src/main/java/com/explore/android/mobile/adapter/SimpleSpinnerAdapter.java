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
import com.explore.android.core.model.BaseKeyValue;

public class SimpleSpinnerAdapter extends BaseAdapter{

	private LayoutInflater inflater;
	private List<BaseKeyValue> list;
	
	public SimpleSpinnerAdapter(Context con, List<BaseKeyValue> list){
		inflater = LayoutInflater.from(con);
		this.list = new ArrayList<BaseKeyValue>();
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
			holder.text = (TextView) convertView.findViewById(R.id.simple_spinner_content);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		if (list.get(position).getKeyResourceId() > 0) {
			holder.text.setText(list.get(position).getKeyResourceId());
		}else{
			holder.text.setText(list.get(position).getKey());
		}
		
		return convertView;
	}
	
	static class ViewHolder{
		TextView text;
	}

}
