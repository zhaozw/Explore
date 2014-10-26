package com.explore.android.mobile.activity;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.explore.android.R;
import com.explore.android.core.base.BaseAsynTask;
import com.explore.android.core.base.BaseAsynTask.TaskOnPostExecuteListener;
import com.explore.android.core.base.BaseAsynTask.TaskResult;
import com.explore.android.core.http.AsynDataTask;
import com.explore.android.mobile.common.AddressConstantsXMLHandler;
import com.explore.android.mobile.common.ProductCategoryXMLHandler;
import com.explore.android.mobile.common.SharePreferencesManager;
import com.explore.android.mobile.db.opration.ConstantsDBO;
import com.explore.android.mobile.db.opration.ProductCategoryDBO;
import com.explore.android.mobile.model.ExConstant;
import com.explore.android.mobile.model.ProductCategory;

public class InitDataActivity extends Activity{
	
	private static final int TYPE_CATEGORY = 1;
	private static final int TYPE_LOCATION = 2;
	private static final int TYPE_FINISH = 100;
	
	private TextView tv_category_item;
	private TextView tv_location_item;
	private ProgressBar load_category;
	private ProgressBar load_location;
	
	private SharePreferencesManager preferences;
	private InitDataTask categoryTask;
	private InitDataTask locationTask;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_init_data);
		initViews();
		initValues();
	}
	
	private void initViews(){
		tv_location_item = (TextView) findViewById(R.id.act_appinit_location_title);
		tv_category_item = (TextView) findViewById(R.id.act_appinit_procategory_title);
		load_location = (ProgressBar) findViewById(R.id.act_appinit_load_location);
		load_category = (ProgressBar) findViewById(R.id.act_appinit_load_procategory);
		
		getActionBar().setTitle(R.string.top_title_init);
	}
	
	private void initValues(){
		preferences = SharePreferencesManager.getInstance(this);
		/*
		Intent intent = getIntent();
		if(intent.hasExtra("TYPE")){
			if("init".equals(intent.getStringExtra("TYPE"))){
				btn_top_left.setVisibility(View.GONE);
			}
		}
		*/
		initHandler.sendEmptyMessage(TYPE_CATEGORY);
	}
	
	
	private class InitDataTask extends BaseAsynTask<Integer, Void, Integer>{

		@Override
		protected Integer doInBackground(Integer... params) {
			if(params.length >0){
				int type = params[0];
				if(type == TYPE_CATEGORY){
					try {
						productCategoryInit();
					} catch (Exception e) {
						e.printStackTrace();
						return 0;
					}
					return TYPE_CATEGORY;
				} else if(type == TYPE_LOCATION){
					try {
						addressConstantsInit();
					} catch (Exception e) {
						e.printStackTrace();
						return 0;
					}
					return TYPE_LOCATION;
				}
			}
			return -1;
		}
		
	}
	
	@SuppressLint("HandlerLeak")
	private Handler initHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case TYPE_CATEGORY:
				initCategory();
				break;
				
			case TYPE_LOCATION:
				initLocation();
				break;
				
			case TYPE_FINISH:
				Intent intent = new Intent(InitDataActivity.this, ExHomeActivity.class);
				startActivity(intent);
				preferences.setIsFirstLogin(false);
				Toast.makeText(InitDataActivity.this, getString(R.string.msg_init_data_success), Toast.LENGTH_SHORT).show();
				InitDataActivity.this.finish();
				break;
				
			default:
				break;
			}
		}
		
	};
	
	private void initCategory(){
		load_category.setVisibility(View.VISIBLE);
		tv_location_item.setVisibility(View.VISIBLE);
		categoryTask = new InitDataTask();
		categoryTask.setTaskOnPostExecuteListener(new TaskOnPostExecuteListener<Integer>() {
			@Override
			public void onPostExecute(TaskResult<? extends Integer> result) {
				tv_category_item.setVisibility(View.VISIBLE);
				load_category.setVisibility(View.GONE);
				if(result != null && result.Status == AsynDataTask.STATUS_SUCCESS){
					if(result.Value == TYPE_CATEGORY){
						tv_category_item.setText(R.string.app_finish);
					} else {
						tv_category_item.setText(R.string.app_failed);
					}
				} else {
					tv_category_item.setText(R.string.app_failed);
				}
				initHandler.sendEmptyMessage(TYPE_LOCATION);
			}
		});
		categoryTask.execute(TYPE_CATEGORY);
	}
	
	private void initLocation(){
		load_location.setVisibility(View.VISIBLE);
		tv_location_item.setVisibility(View.GONE);
		locationTask = new InitDataTask();
		locationTask.setTaskOnPostExecuteListener(new TaskOnPostExecuteListener<Integer>() {
			@Override
			public void onPostExecute(TaskResult<? extends Integer> result) {
				tv_location_item.setVisibility(View.VISIBLE);
				load_location.setVisibility(View.GONE);
				if(result != null && result.Status == AsynDataTask.STATUS_SUCCESS){
					if(result.Value == TYPE_LOCATION){
						tv_location_item.setText(R.string.app_finish);
					} else {
						tv_location_item.setText(R.string.app_failed);
					}
				} else {
					tv_location_item.setText(R.string.app_failed);
				}
				initHandler.sendEmptyMessage(TYPE_FINISH);
			}
		});
		locationTask.execute(TYPE_LOCATION);
	}
	
	private void productCategoryInit() throws Exception {
		if(!preferences.isProductCategoryInit()) {
			ProductCategoryDBO proDbo = new ProductCategoryDBO(InitDataActivity.this);
			proDbo.open();
			proDbo.importCategorys(parserProductCategoryXML());
			proDbo.close();
			preferences.setProductCategoryInitStatus(true);
		}
	}
	
	private void addressConstantsInit() throws Exception{
		if(!preferences.isAddressConstantsInit()){
			ConstantsDBO conDbo = new ConstantsDBO(InitDataActivity.this);
			conDbo.open();
			conDbo.importConstants(parserAddressConstantXML());
			conDbo.close();
			preferences.setAddressConstantsInitStatus(true);
		}
	}
	
	private List<ProductCategory> parserProductCategoryXML() throws Exception {
		List<ProductCategory> productCategories = new ArrayList<ProductCategory>();
		SAXParserFactory sax = SAXParserFactory.newInstance();
		XMLReader reader = sax.newSAXParser().getXMLReader();
		reader.setContentHandler(new ProductCategoryXMLHandler(productCategories));
		reader.parse(new InputSource(getResources().openRawResource(R.raw.product_category)));
		return productCategories;
		
	}
	
	private List<ExConstant> parserAddressConstantXML() throws Exception {
		List<ExConstant> constants = new ArrayList<ExConstant>();
		try {
			SAXParserFactory sax = SAXParserFactory.newInstance();
			XMLReader reader = sax.newSAXParser().getXMLReader();
			reader.setContentHandler(new AddressConstantsXMLHandler(constants));
			reader.parse(new InputSource(getResources().openRawResource(R.raw.address_constants)));
		} catch (Exception e) {
			Toast.makeText(InitDataActivity.this, getResources().getString(R.string.msg_init_address_failed), Toast.LENGTH_SHORT).show();
		}
		return constants;
	}
	
}
