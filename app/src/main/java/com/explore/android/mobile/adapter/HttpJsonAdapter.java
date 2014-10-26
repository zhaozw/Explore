package com.explore.android.mobile.adapter;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.explore.android.core.model.ResourceKeyValue;

public class HttpJsonAdapter extends BaseAdapter{
	
	private JSONArray array;
	private LayoutInflater inflater;
	private int layoutId;
	private List<ResourceKeyValue> resKey;
	
	public HttpJsonAdapter(Context context, JSONArray array, int layout, List<ResourceKeyValue> resKey) {
		inflater = LayoutInflater.from(context);
		this.array = array;
		this.layoutId = layout;
		this.resKey = resKey;
	}
	
	public String getData(int position, String key) {
		JSONObject json;
		try {
			json = (JSONObject) array.get(position);
			return json.getString(key);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@Override
	public int getCount() {
		if (array != null) {
			return array.length();
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		if (array != null) {
			try {
				return array.get(position);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public long getItemId(int id) {
		return id;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHolder holder;
		int length = resKey.size();
		
		if (convertView == null){
			convertView = inflater.inflate(layoutId, null);
			holder = new ViewHolder();
			holder.testViews = new ArrayList<TextView>();
			for (int i = 0; i < length; i++) {
				TextView tv = (TextView) convertView.findViewById(resKey.get(i).getValue());
				holder.testViews.add(tv);
			}
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		JSONObject json;
		try {
			json = (JSONObject) array.get(position);
			for (int i = 0; i < length; i++) {
				if (json.has(resKey.get(i).getKey())) {
					holder.testViews.get(i).setText(json.getString(resKey.get(i).getKey()));
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return convertView;
	}
	
	static class ViewHolder {
		List<TextView> testViews;
	}

}
