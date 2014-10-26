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
import com.explore.android.core.util.ExStringUtil;
import com.explore.android.mobile.model.ExStock;

public class StockListItemAdapter extends BaseAdapter {
	
	private List<ExStock> stockList;
	private LayoutInflater inflater;
	
	public StockListItemAdapter(Context con, List<ExStock> stockList){
		inflater = LayoutInflater.from(con);
		this.stockList = new ArrayList<ExStock>();
		this.stockList = stockList;
	}
	
	@Override
	public int getCount() {
		return stockList.size();
	}

	@Override
	public Object getItem(int position) {
		return stockList.get(position);
	}

	@Override
	public long getItemId(int id) {
		return id;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_stock_item, arg2, false);
			holder = new ViewHolder();
			holder.partNo = (TextView) convertView.findViewById(R.id.frag_stocklist_partno);
			holder.nPartNo = (TextView) convertView.findViewById(R.id.frag_stocklist_npartno);
			holder.proName = (TextView) convertView.findViewById(R.id.frag_stocklist_proname);
			holder.project = (TextView) convertView.findViewById(R.id.frag_stocklist_project);
			holder.wareHouse = (TextView) convertView.findViewById(R.id.frag_stocklist_warehouse);
			holder.loc = (TextView) convertView.findViewById(R.id.frag_stocklist_loc);
			holder.whs = (TextView) convertView.findViewById(R.id.frag_stocklist_whs);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.partNo.setText(stockList.get(position).getPartNo());
		holder.nPartNo.setText(stockList.get(position).getnPartNo());
		holder.proName.setText(stockList.get(position).getProductName());
		holder.wareHouse.setText(stockList.get(position).getWareHouse());
		holder.loc.setText(stockList.get(position).getLoc());
		holder.whs.setText(stockList.get(position).getWhs());
		
		if(ExStringUtil.isResNoBlank(stockList.get(position).getProject())){
			holder.project.setText(stockList.get(position).getProject());
		}
		
		return convertView;
	}
	
	static class ViewHolder{
		TextView partNo;
		TextView nPartNo;
		TextView proName;
		TextView project;
		TextView wareHouse;
		TextView loc;
		TextView whs;
	}

}
