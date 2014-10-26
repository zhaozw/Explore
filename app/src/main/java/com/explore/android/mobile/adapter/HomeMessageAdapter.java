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
import com.explore.android.mobile.model.ExMessage;

public class HomeMessageAdapter extends BaseAdapter {

	private List<ExMessage> msgList;
	private LayoutInflater inflater;

	public HomeMessageAdapter(List<ExMessage> list, Context context) {
		msgList = new ArrayList<ExMessage>();
		msgList = list;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return msgList.size();
	}

	@Override
	public Object getItem(int position) {
		return msgList.get(position);
	}

	@Override
	public long getItemId(int id) {
		return id;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHodler hodler;
		if ( convertView == null ) {
			convertView = inflater.inflate(R.layout.list_home_news_item, null);
			hodler = new ViewHodler();
			hodler.title = (TextView) convertView.findViewById(R.id.home_news_title);
			hodler.content = (TextView) convertView.findViewById(R.id.home_news_content);
			convertView.setTag(hodler);
		} else {
			hodler = (ViewHodler) convertView.getTag();
		}
		hodler.title.setText(msgList.get(position).getTitle());
		hodler.content.setText(msgList.get(position).getCreateByName());
		return convertView;
	}
	
	static class ViewHodler{
		TextView title;
		TextView content;
	}

}
