package com.explore.android.mobile.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.explore.android.R;
import com.explore.android.mobile.model.Employee;

public class ContactAdapter extends BaseAdapter implements ListAdapter{

	private List<Employee> empList;
	private LayoutInflater mInflater;

	public ContactAdapter(Context con, List<Employee> list) {
		empList = new ArrayList<Employee>();
		empList = list;
		mInflater = LayoutInflater.from(con);
	}

	@Override
	public int getCount() {
		return empList.size();
	}

	@Override
	public Employee getItem(int position) {
		return empList.get(position);
	}

	@Override
	public long getItemId(int id) {
		return empList.get(id).getEmployeeId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_contact_item, null);
			holder = new ViewHolder();
			holder.empName = (TextView) convertView.findViewById(R.id.contact_emp_name);
			holder.empMobile = (TextView) convertView.findViewById(R.id.contact_emp_mobile);
			holder.empPosition = (TextView) convertView.findViewById(R.id.contact_emp_position);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.empName.setText(empList.get(position).getName());
		holder.empMobile.setText(empList.get(position).getMobile());
		holder.empPosition.setText(empList.get(position).getPosition());
		return convertView;
	}
	
	static class ViewHolder{
		TextView empName;
		TextView empMobile;
		TextView empPosition;
	}

}
