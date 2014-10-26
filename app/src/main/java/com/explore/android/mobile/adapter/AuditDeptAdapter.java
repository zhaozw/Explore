package com.explore.android.mobile.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.explore.android.R;
import com.explore.android.mobile.model.ExDept;

/**
 * 审核,Spinner选择部门
 * @author ryan
 *
 */
public class AuditDeptAdapter extends BaseAdapter{

	private LayoutInflater inflater;
	private ArrayList<ExDept> deptList;
	
	public AuditDeptAdapter(Context context,ArrayList<ExDept> list){
		deptList = new ArrayList<ExDept>();
		this.deptList = list;
		this.inflater = LayoutInflater.from(context);
	}
	
	public int getCount() {
		return deptList.size();
	}

	public Object getItem(int position) {
		return deptList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		
		if ( convertView == null) {
			convertView = this.inflater.inflate(R.layout.spinner_audit_dept, null);
			holder = new ViewHolder();
			holder.tv_name = (TextView) convertView.findViewById(R.id.audit_dept_spi_name);
			holder.tv_id = (TextView) convertView.findViewById(R.id.audit_dept_spi_id);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.tv_name.setText(deptList.get(position).getDeptName());
		holder.tv_id.setText(deptList.get(position).getDeptId());
		return convertView;
	}
	
	static class ViewHolder{
		TextView tv_name;
		TextView tv_id;
	}

}
