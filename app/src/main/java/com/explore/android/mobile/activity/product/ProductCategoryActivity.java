package com.explore.android.mobile.activity.product;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.explore.android.R;
import com.explore.android.core.model.BaseKeyValue;
import com.explore.android.mobile.adapter.SimpleSpinnerAdapter;
import com.explore.android.mobile.db.opration.ProductCategoryDBO;

public class ProductCategoryActivity extends Activity{
	
	private Spinner spi_category_l1;
	private Spinner spi_category_l2;
	private Spinner spi_category_l3;
	private Button btn_next_step;
	
	private LinearLayout modify_title_layout;
	private TextView tv_modify_category;

	private SimpleSpinnerAdapter categoryL1Adapter;
	private SimpleSpinnerAdapter categoryL2Adapter;
	private SimpleSpinnerAdapter categoryL3Adapter;
	
	private List<BaseKeyValue> categoryL1List;
	private List<BaseKeyValue> categoryL2List;
	private List<BaseKeyValue> categoryL3List;
	
	private ProductCategoryDBO catDbo;
	
	private String productCategoryId;
	private String mode;
	private String productId;
	private String modifyCategoryName;
	private String modifyCategoryId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_product_category);
		
		initViews();
		initValues();
		initListener();
	}

	private void initViews(){
		modify_title_layout = (LinearLayout) findViewById(R.id.act_add_product_formodify_layout);
		tv_modify_category = (TextView) findViewById(R.id.act_add_product_category_modify);
		
		spi_category_l1 = (Spinner) findViewById(R.id.act_add_product_catl1);
		spi_category_l2 = (Spinner) findViewById(R.id.act_add_product_catl2);
		spi_category_l3 = (Spinner) findViewById(R.id.act_add_product_catl3);
		btn_next_step = (Button) findViewById(R.id.act_add_product_cat_btn_next);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	private void initValues(){
		
		Intent typeIntent = getIntent();
		if(typeIntent.hasExtra("TYPE")){
			mode = typeIntent.getStringExtra("TYPE");
			if("modify".equals(mode)){
				productId = typeIntent.getStringExtra("PRODUCTID");
				modifyCategoryName = typeIntent.getStringExtra("CATEGORYNAME");
				modifyCategoryId = typeIntent.getStringExtra("CATEGORYID");
			}
		} else {
			this.finish();
		}
		
		if("new".equals(mode)){
			getActionBar().setTitle(R.string.top_title_add_product_cat);
		} else if ("modify".equals(mode)){
			getActionBar().setTitle(R.string.top_title_modify_product_cat);
			modify_title_layout.setVisibility(View.VISIBLE);
			tv_modify_category.setText(modifyCategoryName);
		}

		categoryL1List = new ArrayList<BaseKeyValue>();
		categoryL2List = new ArrayList<BaseKeyValue>();
		categoryL3List = new ArrayList<BaseKeyValue>();
		
		catDbo = new ProductCategoryDBO(this);
		catDbo.open();
		categoryL1List = catDbo.getSpinnerCategory();
		catDbo.close();
		
		categoryL1Adapter = new SimpleSpinnerAdapter(ProductCategoryActivity.this, categoryL1List);
		spi_category_l1.setAdapter(categoryL1Adapter);
		
		categoryL2Adapter = new SimpleSpinnerAdapter(ProductCategoryActivity.this, categoryL2List);
		categoryL3Adapter = new SimpleSpinnerAdapter(ProductCategoryActivity.this, categoryL3List);
		spi_category_l2.setAdapter(categoryL2Adapter);
		spi_category_l3.setAdapter(categoryL3Adapter);
		
		if("modify".equals(mode) && modifyCategoryId != null){
			btn_next_step.setEnabled(true);
		}
	}
	
	private void initListener(){
		btn_next_step.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				if(spi_category_l1.getSelectedItemPosition() == 0
						&& spi_category_l2.getSelectedItemPosition() == 0
						&& spi_category_l3.getSelectedItemPosition() == 0){
					productCategoryId = null;
					if("modify".equals(mode) && modifyCategoryId != null){
						productCategoryId = modifyCategoryId;
					}
				} else {
					if (spi_category_l1.getSelectedItemPosition() != 0){
						productCategoryId = categoryL1List.get(spi_category_l1.getSelectedItemPosition()).getValue();
					}
					
					if (spi_category_l2.getSelectedItemPosition() != 0){
						productCategoryId = categoryL2List.get(spi_category_l2.getSelectedItemPosition()).getValue();
					}
					
					if (spi_category_l3.getSelectedItemPosition() != 0){
						productCategoryId = categoryL3List.get(spi_category_l3.getSelectedItemPosition()).getValue();
					}
				}
				
				if(productCategoryId != null){
					Intent intent = new Intent(ProductCategoryActivity.this, ProductModifyDetailActivity.class);
					intent.putExtra("TYPE", mode);
					intent.putExtra("CATEGORY", productCategoryId);
					if("modify".equals(mode)){
						intent.putExtra("PRODUCTID", productId);
					}
					startActivity(intent);
					ProductCategoryActivity.this.finish();
				} else {
					Toast.makeText(ProductCategoryActivity.this, getString(R.string.msg_no_productcategory), Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		spi_category_l1.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View view, int position, long id) {
				
				if (position != 0){
					categoryL2List.clear();
					spi_category_l2.setVisibility(View.VISIBLE);
					int catId = Integer.parseInt(categoryL1List.get(position).getValue());
					catDbo.open();
					catDbo.getSpinnerSubCategoryById(catId, categoryL2List);
					catDbo.close();
					
					categoryL2Adapter.notifyDataSetChanged();
					
					spi_category_l2.setSelection(0);
					spi_category_l3.setSelection(0);
				} else {
					spi_category_l2.setVisibility(View.GONE);
					spi_category_l3.setVisibility(View.GONE);
					categoryL2List.clear();
					categoryL3List.clear();
					categoryL2Adapter.notifyDataSetChanged();
					categoryL3Adapter.notifyDataSetChanged();
					
					spi_category_l2.setSelection(0);
					spi_category_l3.setSelection(0);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
		
		spi_category_l2.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View view,
					int position, long id) {
				
				if (position != 0){
					categoryL3List.clear();
					spi_category_l3.setVisibility(View.VISIBLE);
					int catId = Integer.parseInt(categoryL2List.get(position).getValue());
					catDbo.open();
					catDbo.getSpinnerSubCategoryById(catId, categoryL3List);
					catDbo.close();
					
					categoryL3Adapter.notifyDataSetChanged();
					
					spi_category_l3.setSelection(0);
				} else {
					spi_category_l3.setVisibility(View.GONE);
					categoryL3List.clear();
					categoryL3Adapter.notifyDataSetChanged();
					spi_category_l3.setSelection(0);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
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
