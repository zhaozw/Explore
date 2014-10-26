package com.explore.android.mobile.data.predata;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.explore.android.R;
import com.explore.android.core.model.BaseKeyValue;

public class OutCreatePreData {

	public static List<BaseKeyValue> cusDeptList = new ArrayList<BaseKeyValue>();
	public static List<BaseKeyValue> soPriceLogonList = new ArrayList<BaseKeyValue>();
	public static List<BaseKeyValue> shipToList = new ArrayList<BaseKeyValue>();
	public static List<BaseKeyValue> shipToAdressList = new ArrayList<BaseKeyValue>();
	public static List<BaseKeyValue> vatToList = new ArrayList<BaseKeyValue>();
	public static List<BaseKeyValue> shipFromList = new ArrayList<BaseKeyValue>();
	public static List<BaseKeyValue> warehouseList = new ArrayList<BaseKeyValue>();
	
	public static void init(Context context){
		clear();
		cusDeptList.add(new BaseKeyValue(context.getResources().getString(R.string.out_cusdept), ""));
		warehouseList.add(new BaseKeyValue(context.getResources().getString(R.string.out_warehouse), ""));
		soPriceLogonList.add(new BaseKeyValue(context.getResources().getString(R.string.out_sopricelogon), ""));
		shipToList.add(new BaseKeyValue(context.getResources().getString(R.string.out_shipto), ""));
		shipFromList.add(new BaseKeyValue(context.getResources().getString(R.string.outttc_shipfrom), ""));
		vatToList.add(new BaseKeyValue(context.getResources().getString(R.string.out_vatto), ""));
		shipToAdressList.add(new BaseKeyValue(context.getResources().getString(R.string.out_shipto_adress), ""));
	}
	
	public static void clear(){
		cusDeptList.clear();
		soPriceLogonList.clear();
		shipToList.clear();
		shipToAdressList.clear();
		vatToList.clear();
		shipFromList.clear();
		warehouseList.clear();
	}
	
}
