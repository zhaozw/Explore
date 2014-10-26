package com.explore.android.mobile.view;

import com.explore.android.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TitleTextView extends LinearLayout{
	
	private TextView tv_title;
	private TextView tv_content;

	public TitleTextView(Context context) {
		super(context);
	}
	
	public TitleTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_textview_title, this);
		tv_title = (TextView) findViewById(R.id.view_textview_title_title);
		tv_content = (TextView) findViewById(R.id.view_textview_title_content);
	}
	
	public void setTitle(String title) {
		tv_title.setText(title);
	}
	
	public void setTitle(int resId) {
		tv_title.setText(resId);
	}
	
	public void setContent(String content) {
		tv_content.setText(content);
	}
	
	public void setContent(int resId) {
		tv_content.setText(resId);
	}
	
}
