package com.explore.android.mobile.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.explore.android.R;
import com.explore.android.mobile.model.SingleMenuItem;

public class MenuItemExpandListAdapter extends BaseExpandableListAdapter{
	
	private Context context;
	private LayoutInflater mInflater;
	private List<SingleMenuItem> menuItems = new ArrayList<SingleMenuItem>();
	
	public MenuItemExpandListAdapter(Context context, List<SingleMenuItem> menuItems){
		this.context = context;
		this.menuItems = menuItems;
		mInflater = LayoutInflater.from(context);
	}

	// ======================================================
	// Child
	// ======================================================
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return menuItems.get(groupPosition).getGroups().get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return menuItems.get(groupPosition).getGroups().get(childPosition).getId();
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean arg2, View childView, ViewGroup arg4) {
		ChildViewHodler hodler;
		if(childView == null){
			childView = mInflater.inflate(R.layout.menu_item_child_layout, null);
			hodler = new ChildViewHodler();
			hodler.mTextView = (TextView) childView.findViewById(R.id.menu_item_adapter_text);
			childView.setTag(hodler);
		} else {
			hodler = (ChildViewHodler) childView.getTag();
		}
		
		String name = menuItems.get(groupPosition).getGroups().get(childPosition).getName();
		/// 这一步必须要做,否则不会显示.
		Drawable drawable= context.getResources().getDrawable(R.drawable.menu_item_icon);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
		hodler.mTextView.setText(name);
		hodler.mTextView.setCompoundDrawables(null, null, drawable, null);
		return childView;
	}
	
	static class ChildViewHodler{
		TextView mTextView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return menuItems.get(groupPosition).getGroups().size();
	}

	// ======================================================
	// Group
	// ======================================================
	
	@Override
	public Object getGroup(int groupPosition) {
		return menuItems.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return menuItems.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return menuItems.get(groupPosition).getId();
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View groupView, ViewGroup parent) {
		GroupViewHolder holder;
		if(groupView == null){
			groupView = mInflater.inflate(R.layout.menu_item_parent_layout, null);
			holder = new GroupViewHolder();
			holder.mTextView = (TextView) groupView.findViewById(R.id.menu_item_adapter_text);
			groupView.setTag(holder);
		} else {
			holder = (GroupViewHolder) groupView.getTag();
		}
		
		String name = menuItems.get(groupPosition).getName();
		holder.mTextView.setText(name);
		Drawable drawable = context.getResources().getDrawable(R.drawable.menu_icon_collapse);
		if(!isExpanded){
			drawable = null;
			drawable = context.getResources().getDrawable(R.drawable.menu_icon_expand);
		}
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
		holder.mTextView.setCompoundDrawables(null, null, drawable, null);
		return groupView;
	}
	
	static class GroupViewHolder{
		TextView mTextView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
	
}
