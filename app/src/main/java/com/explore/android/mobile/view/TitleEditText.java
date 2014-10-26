package com.explore.android.mobile.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.explore.android.R;

public class TitleEditText extends LinearLayout {
	
	private TextView tv_title;
	private EditText edt_condition;

	public TitleEditText(Context context) {
		super(context);
	}

	public TitleEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_edittext_title, this);
		tv_title = (TextView) findViewById(R.id.view_edittext_title_title);
		edt_condition = (EditText) findViewById(R.id.view_edittext_title_content);
	}
	
	public void setTitle(String title){
		tv_title.setText(title);
	}
	
	public void setTitle(int resId){
		tv_title.setText(resId);
	}
	
	public void setInputType(int type){
		edt_condition.setInputType(type);
	}
	
	public void setHint(String hint){
		edt_condition.setHint(hint);
	}
	
	public void setHint(int hint){
		edt_condition.setHint(hint);
	}
	
	public String getValue(){
		return edt_condition.getText().toString().trim();
	}

}
