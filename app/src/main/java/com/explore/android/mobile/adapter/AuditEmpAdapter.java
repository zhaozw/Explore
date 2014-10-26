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
import com.explore.android.mobile.model.ExDeptEmp;

/**
 * 审核,Spinner选择人员
 * @author ryan
 *
 */
public class AuditEmpAdapter extends BaseAdapter{

	private LayoutInflater inflater;
	private List<ExDeptEmp> empList;
	
	public AuditEmpAdapter(Context context,List<ExDeptEmp> list){
		empList = new ArrayList<ExDeptEmp>();
		this.empList = list;
		this.inflater = LayoutInflater.from(context);
	}
	
	public int getCount() {
		return empList.size();
	}

	public Object getItem(int position) {
		return empList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if ( convertView == null ) {
			convertView = this.inflater.inflate(R.layout.spinner_audit_dept, null);
			holder = new ViewHolder();
			holder.tv_name = (TextView)convertView.findViewById(R.id.audit_dept_spi_name);
			holder.tv_id = (TextView)convertView.findViewById(R.id.audit_dept_spi_id);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv_name.setText(empList.get(position).getEmpName());
		holder.tv_id.setText(empList.get(position).getEmpId());
		return convertView;
	}
	
	static class ViewHolder{
		TextView tv_name;
		TextView tv_id;
	}

}
