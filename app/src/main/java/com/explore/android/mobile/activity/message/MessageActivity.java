package com.explore.android.mobile.activity.message;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

import com.explore.android.R;
import com.explore.android.mobile.activity.ExHomeActivity;
import com.explore.android.mobile.constants.AppStatus;
import com.explore.android.mobile.db.opration.MessageDBO;
import com.explore.android.mobile.model.ExMessage;

public class MessageActivity extends Activity{
	
	private TextView tv_msgTitle;
	private TextView tv_msg_sendBy;
	private TextView tv_msg_sendTime;
	private TextView tv_msg_content;
	
	private MessageDBO dbo;
	private ExMessage message;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);
		
		initViews();
		initValues();
		initListener();
	}
	
	private void initViews(){
		tv_msgTitle = (TextView) findViewById(R.id.act_tv_message_title);
		tv_msg_sendBy = (TextView) findViewById(R.id.act_tv_message_sendby);
		tv_msg_sendTime = (TextView) findViewById(R.id.act_tv_message_sendtime);
		tv_msg_content = (TextView) findViewById(R.id.act_tv_message_content);
	}
	
	private void initValues(){
		dbo = new MessageDBO(this);
		dbo.open();
		Intent intent = getIntent();
		int msgId = intent.getIntExtra("INFOID", -1);
		if(msgId != -1){
			message = dbo.loadByInfoId(msgId);
			tv_msgTitle.setText(message.getTitle());
			tv_msg_sendBy.setText(message.getCreateByName());
			tv_msg_sendTime.setText(message.getCreateByTime());
			tv_msg_content .setText(message.getContent());
		} else {
			
		}
	}
	
	private void initListener(){
		/*
		btn_top_left.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(!AppStatus.HOME_ACT_RUNNING){
					Intent intent = new Intent(MessageActivity.this, ExHomeActivity.class);
					startActivity(intent);
					AppStatus.HOME_ACT_RUNNING = true;
				}
				MessageActivity.this.finish();
			}
		});
		*/
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		return super.onKeyDown(keyCode, event);
		if(keyCode == KeyEvent.KEYCODE_BACK){
			
			if(!AppStatus.HOME_ACT_RUNNING){
				Intent intent = new Intent(MessageActivity.this, ExHomeActivity.class);
				startActivity(intent);
				AppStatus.HOME_ACT_RUNNING = true;
			}
			MessageActivity.this.finish();
		}
		return false;
	}
}
