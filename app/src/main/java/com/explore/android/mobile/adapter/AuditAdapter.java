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
import com.explore.android.mobile.model.ExAudit;

public class AuditAdapter extends BaseAdapter{
	
	private List<ExAudit> auditList;
	private LayoutInflater inflater;
	
	public AuditAdapter(Context con,List<ExAudit> list){
		inflater = LayoutInflater.from(con);
		this.auditList = new ArrayList<ExAudit>();
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
			convertView = inflater.inflate(R.layout.list_audit_adapter_item, null);
			holder = new ViewHolder();
			holder.creByName = (TextView) convertView.findViewById(R.id.frag_audititem_crebyname);
			holder.creByTime = (TextView) convertView.findViewById(R.id.frag_audititem_crebytime);
			holder.billType = (TextView) convertView.findViewById(R.id.frag_audititem_billtype);
			holder.dept = (TextView) convertView.findViewById(R.id.frag_audititem_dept);
			holder.content = (TextView) convertView.findViewById(R.id.frag_audititem_content);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.creByName.setText(auditList.get(position).getCreateByName());
		holder.creByTime.setText(auditList.get(position).getCreateByTime());
		holder.billType.setText(auditList.get(position).getBillType());
		holder.dept.setText(auditList.get(position).getDept());
		holder.content.setText(auditList.get(position).getContent());
		
		return convertView;
	}
	
	static class ViewHolder{
		TextView creByName;
		TextView creByTime;
		TextView billType;
		TextView dept;
		TextView content;
	}

}
