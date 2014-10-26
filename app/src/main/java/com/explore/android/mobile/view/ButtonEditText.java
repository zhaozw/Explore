package com.explore.android.mobile.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.explore.android.R;

public class ButtonEditText extends LinearLayout{
	
	private EditText myEditText;
	private ImageButton myBtn;

	private TextWatcher myTextWatcher = new TextWatcher() {
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			
		}
	};
	
	public ButtonEditText(Context context){
		super(context);
	}
	
	public ButtonEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_edittext_button, this);
		myEditText = (EditText) findViewById(R.id.view_edittext_button_edt);
		myBtn = (ImageButton) findViewById(R.id.view_edittext_button_button);
		initViews();
	}
	
	private void initViews(){
		myEditText.addTextChangedListener(myTextWatcher);
		myBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				hideImageButton();
				myEditText.setText("");
			}
		});
	}
	
	protected void hideImageButton() {
		myBtn.setVisibility(View.GONE);
	}
	
	protected void showImageButton(){
		myBtn.setVisibility(View.VISIBLE);
	}

}
