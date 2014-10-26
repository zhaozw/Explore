package com.explore.android.mobile.activity.customer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.explore.android.R;

public class CustomerAddPreActivity extends Activity{

	private EditText edt_cusNameCN;
	private EditText edt_cusNameCNs;
	private EditText edt_cusNameEN;
	private EditText edt_cusNameENs;
	private EditText edt_companyCode;
	private Button btn_next_step;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_customer_modifypre);
		initViews();
		initListener();
	}
	
	private void initViews(){
		edt_cusNameCN = (EditText) findViewById(R.id.act_cusmodifypre_namecn);
		edt_cusNameCNs = (EditText) findViewById(R.id.act_cusmodifypre_namecns);
		edt_cusNameEN = (EditText) findViewById(R.id.act_cusmodifypre_nameen);
		edt_cusNameENs = (EditText) findViewById(R.id.act_cusmodifypre_nameens);
		edt_companyCode = (EditText) findViewById(R.id.act_cusmodifypre_companycode);
		btn_next_step = (Button) findViewById(R.id.act_cusmodifypre_btn_next);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle(R.string.customer_title_createpre);
	}
	
	private void initListener(){
		btn_next_step.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if("".equals(edt_cusNameCN.getText().toString().trim())){
					Toast.makeText(CustomerAddPreActivity.this, R.string.msg_cuscreate_no_namecn, Toast.LENGTH_SHORT).show();
				
				} else if("".equals(edt_cusNameCNs.getText().toString().trim())){
					Toast.makeText(CustomerAddPreActivity.this, R.string.msg_cuscreate_no_namecns, Toast.LENGTH_SHORT).show();
				
				} else {
					Intent intent = new Intent(CustomerAddPreActivity.this, CustomerAddActivity.class);
					intent.putExtra("TYPE", "new");
					intent.putExtra("NAMECN", edt_cusNameCN.getText().toString().trim());
					intent.putExtra("NAMECNS", edt_cusNameCNs.getText().toString().trim());
					intent.putExtra("NAMEEN", edt_cusNameEN.getText().toString().trim());
					intent.putExtra("NAMEENS", edt_cusNameENs.getText().toString().trim());
					intent.putExtra("COMPANYCODE", edt_companyCode.getText().toString().trim());
					startActivity(intent);
					CustomerAddPreActivity.this.finish();
				}
			}
		});
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
		return super.onOptionsItemSelected(item);
	}
}
