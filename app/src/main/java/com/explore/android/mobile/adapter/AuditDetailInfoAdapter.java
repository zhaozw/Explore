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
import com.explore.android.mobile.model.SingleAuditInfo;

public class AuditDetailInfoAdapter extends BaseAdapter{
	
	 private List<SingleAuditInfo> auditList;
	 private LayoutInflater inflater;
	 
	 public AuditDetailInfoAdapter(Context con, List<SingleAuditInfo> list){
		 this.inflater = LayoutInflater.from(con);
		 this.auditList = new ArrayList<SingleAuditInfo>();
		 this.auditList = list;
	 }

	@Override
	public int getCount() {
		return auditList.size();
	}

	@Override
	public Object getItem(int position) {
		return auditList.get(position);
	}

	@Override
	public long getItemId(int id) {
		return id;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHolder holder;
		if ( convertView == null ) {
			convertView = inflater.inflate(R.layout.layout_detail_info_item, null);
			holder = new ViewHolder();
			holder.title = (TextView) convertView.findViewById(R.id.audit_detail_info_title);
			holder.content = (TextView) convertView.findViewById(R.id.audit_detail_info_content);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.title.setText(auditList.get(position).getResourceId());
		holder.content.setText(auditList.get(position).getContent());
		
		return convertView;
	}
	
	static class ViewHolder{
		TextView title;
		TextView content;
	}

}
