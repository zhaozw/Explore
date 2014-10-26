package com.explore.android.mobile.view;

import com.explore.android.R;
import com.explore.android.mobile.adapter.SimpleSpinnerAdapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class TitleSpinner extends LinearLayout{
	
	private TextView tv_title;
	private Spinner spinner;
	private SimpleSpinnerAdapter adapter;

	public TitleSpinner(Context context){
		super(context);
	}
	
	public TitleSpinner(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_spinner_title, this);
		tv_title = (TextView) findViewById(R.id.view_spinner_title_title);
		spinner = (Spinner) findViewById(R.id.view_spinner_title_spinner);
	}
	
	public void setTitle(String title){
		tv_title.setText(title);
	}
	
	public void setTitle(int resId){
		tv_title.setText(resId);
	}
	
	public void setOnItemSelectedListener(OnItemSelectedListener listener){
		spinner.setOnItemSelectedListener(listener);
	}
	
	public void setAdapter(SimpleSpinnerAdapter adapter){
		this.adapter = adapter;
		spinner.setAdapter(adapter);
	}
	
	public void refreshSpinner(){
		adapter.notifyDataSetChanged();
	}

}
