package com.explore.android.mobile.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.explore.android.R;
import com.explore.android.mobile.model.ExpandListData;

public class CustomExpandListAdapter extends BaseExpandableListAdapter{

	private LayoutInflater inflater;
	private List<ExpandListData> outInfoDetail_list;
	private Context context;
	
	public CustomExpandListAdapter(List<ExpandListData> list, Context con){
		inflater = LayoutInflater.from(con);
		outInfoDetail_list = new ArrayList<ExpandListData>();
		outInfoDetail_list = list;
		context = con;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return outInfoDetail_list.get(groupPosition).getDetailList().get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean arg2, View childView, ViewGroup arg4) {
		ChildViewHolder holder;
		if ( childView == null ) {
			childView = inflater.inflate(R.layout.exlist_kvtype_child_item, null);
			holder = new ChildViewHolder();
			holder.title = (TextView) childView.findViewById(R.id.exlist_tv_child_title);
			holder.content = (TextView) childView.findViewById(R.id.exlist_tv_child_content);
			childView.setTag(holder);
		} else {
			holder = (ChildViewHolder) childView.getTag();
		}
		holder.title.setText(outInfoDetail_list.get(groupPosition).getDetailList().get(childPosition).getResourceId());
		holder.content.setText(outInfoDetail_list.get(groupPosition).getDetailList().get(childPosition).getContent());
		return childView;
	}
	
	static class ChildViewHolder{
		TextView title;
		TextView content;
	}

	@Override
	public int getChildrenCount(int position) {
		return outInfoDetail_list.get(position).getDetailList().size();
	}

	@Override
	public Object getGroup(int position) {
		return outInfoDetail_list.get(position);
	}

	@Override
	public int getGroupCount() {
		return outInfoDetail_list.size();
	}

	@Override
	public long getGroupId(int arg0) {
		return 0;
	}
	
	@Override
	public View getGroupView(int position, boolean isExpanded, View groupView, ViewGroup arg3) {
		GroupViewHolder holder;
		if(groupView == null){
			groupView = inflater.inflate(R.layout.exlist_text_title_group_item, null);
			holder = new GroupViewHolder();
			holder.item_layout = (LinearLayout) groupView.findViewById(R.id.exlist_detailinfo_layout);
			holder.title1 = (TextView) groupView.findViewById(R.id.exlist_detailinfo_tv_group_title1);
			holder.title2 = (TextView) groupView.findViewById(R.id.exlist_detailinfo_tv_group_title2);
			holder.title3 = (TextView) groupView.findViewById(R.id.exlist_detailinfo_tv_group_title3);
			groupView.setTag(holder);
		} else {
			holder = (GroupViewHolder) groupView.getTag();
		}
		
		if(isExpanded){
			holder.item_layout.setBackgroundColor(context.getResources().getColor(R.color.out_exlist_group_selected));
		}else{
			holder.item_layout.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.exlist_group_item_style));
		}
		
		holder.title1.setText(outInfoDetail_list.get(position).getTitle1());
		
		if(outInfoDetail_list.get(position).getTitle2() != null && !"".equals(outInfoDetail_list.get(position).getTitle2().trim())){
			holder.title2.setText(outInfoDetail_list.get(position).getTitle2());
		} else {
			holder.title2.setVisibility(View.GONE);
		}
		
		if(outInfoDetail_list.get(position).getTitle3() != null && !"".equals(outInfoDetail_list.get(position).getTitle3().trim())){
			holder.title3.setText(outInfoDetail_list.get(position).getTitle3());
		} else {
			holder.title3.setVisibility(View.GONE);
		}
		
		return groupView;
	}
	
	static class GroupViewHolder{
		LinearLayout item_layout;
		TextView title1;
		TextView title2;
		TextView title3;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		return false;
	}

}
