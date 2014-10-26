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
import com.explore.android.mobile.model.Schedule;

public class ScheduleListAdapter extends BaseAdapter{
	
	private List<Schedule> scheList;
	private LayoutInflater inflater;
	
	public ScheduleListAdapter(Context con,List<Schedule> list){
		scheList = new ArrayList<Schedule>();
		scheList = list;
		inflater = LayoutInflater.from(con);
	}
	
	@Override
	public int getCount() {
		return scheList.size();
	}

	@Override
	public Object getItem(int position) {
		return scheList.get(position);
	}

	@Override
	public long getItemId(int id) {
		return id;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHolder holder;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.list_schedule_item, null);
			holder = new ViewHolder();
			holder.begin_time = (TextView) convertView.findViewById(R.id.schelist_item_begintime);
			holder.end_time = (TextView) convertView.findViewById(R.id.schelist_item_endtime);
			holder.stitle = (TextView) convertView.findViewById(R.id.schelist_item_title);
			holder.sdes = (TextView) convertView.findViewById(R.id.schelist_item_des);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.begin_time.setText(scheList.get(position).getTime().getBeginTime());
		holder.end_time.setText("~" + scheList.get(position).getTime().getEndTime());
		holder.stitle.setText(scheList.get(position).getTitle());
		holder.sdes.setText(scheList.get(position).getDes());
		return convertView;
	}
	
	static class ViewHolder{
		TextView begin_time;
		TextView end_time;
		TextView stitle;
		TextView sdes;
	}

}
