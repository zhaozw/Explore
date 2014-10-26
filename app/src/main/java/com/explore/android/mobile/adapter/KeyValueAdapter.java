package com.explore.android.mobile.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.explore.android.core.model.BaseKeyValue;

public class KeyValueAdapter extends BaseAdapter {

	private List<BaseKeyValue> kvList;
	private int mResource;
	private LayoutInflater mInflater;
	private int mFieldId;
	private KvMode mMode;

	public KeyValueAdapter(Context context, int resource, int textViewResId,
			List<BaseKeyValue> list, KvMode mode) {
		kvList = list;
		mResource = resource;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mFieldId = textViewResId;
		mMode = mode;
	}

	@Override
	public int getCount() {
		return kvList.size();
	}

	@Override
	public Object getItem(int position) {
		return kvList.get(position);
	}

	@Override
	public long getItemId(int id) {
		return id;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return createViewFromResource(position, convertView, parent, mResource);
	}

	private View createViewFromResource(int position, View convertView,
			ViewGroup parent, int resource) {
		View view;
		TextView text;

		if (convertView == null) {
			view = mInflater.inflate(mResource, parent, false);
		} else {
			view = convertView;
		}

		text = (TextView) view.findViewById(mFieldId);
		if (mMode == KvMode.RES) {
			text.setText(kvList.get(position).getKeyResourceId());
		} else if (mMode == KvMode.STR) {
			text.setText(kvList.get(position).getKey());
		}
		return view;
	}

	public enum KvMode {
		RES, STR
	}

	public enum ActionMode {
		CLICK, CHECK
	}
}
