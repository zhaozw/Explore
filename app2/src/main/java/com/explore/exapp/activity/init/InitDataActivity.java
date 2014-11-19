package com.explore.exapp.activity.init;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.explore.exapp.R;
import com.explore.exapp.activity.MainTabActivity;
import com.explore.exapp.activity.init.model.MapAddress;
import com.explore.exapp.activity.init.model.ProductCategory;
import com.explore.exapp.activity.login.ChooseDeptActivity;
import com.explore.exapp.base.BaseAsyncTask;
import com.explore.exapp.base.util.LogUtil;
import com.explore.exapp.data.AppPreferences;

import org.litepal.crud.DataSupport;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

public class InitDataActivity extends Activity{
	
	private static final int TYPE_CATEGORY = 1;
	private static final int TYPE_LOCATION = 2;
	private static final int TYPE_FINISH = 100;
	
	private TextView tv_category_item;
	private TextView tv_location_item;
	private ProgressBar load_category;
	private ProgressBar load_location;
	
	private InitDataTask categoryTask;
	private InitDataTask locationTask;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first_login_init);
		initViews();
		initValues();
	}
	
	private void initViews(){
		tv_location_item = (TextView) findViewById(R.id.act_appinit_location_title);
		tv_category_item = (TextView) findViewById(R.id.act_appinit_procategory_title);
		load_location = (ProgressBar) findViewById(R.id.act_appinit_load_location);
		load_category = (ProgressBar) findViewById(R.id.act_appinit_load_procategory);
		
		getActionBar().setTitle(R.string.first_login_actionbar_title);
	}
	
	private void initValues(){
		initHandler.sendEmptyMessage(TYPE_CATEGORY);
	}


    private class InitDataTask extends BaseAsyncTask<Integer, Void, Integer>{

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
                AppPreferences.prfs(InitDataActivity.this).edit().putBoolean(AppPreferences.Config.BASIC_DATA_INIT, true).apply();
                Intent intent = new Intent(InitDataActivity.this, MainTabActivity.class);
                startActivity(intent);
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
		categoryTask.setTaskOnPostExecuteListener(new BaseAsyncTask.TaskOnPostExecuteListener<Integer>() {
			@Override
			public void onPostExecute(BaseAsyncTask.TaskResult<? extends Integer> result) {
				tv_category_item.setVisibility(View.VISIBLE);
				load_category.setVisibility(View.GONE);
				if(result != null && result.Status == BaseAsyncTask.STATUS_SUCCESS){
					if(result.Value == TYPE_CATEGORY){
						tv_category_item.setText(R.string.finish);
					} else {
						tv_category_item.setText(R.string.failed);
					}
				} else {
					tv_category_item.setText(R.string.failed);
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
		locationTask.setTaskOnPostExecuteListener(new BaseAsyncTask.TaskOnPostExecuteListener<Integer>() {
			@Override
			public void onPostExecute(BaseAsyncTask.TaskResult<? extends Integer> result) {
				tv_location_item.setVisibility(View.VISIBLE);
				load_location.setVisibility(View.GONE);
				if(result != null && result.Status == BaseAsyncTask.STATUS_SUCCESS){
					if(result.Value == TYPE_LOCATION){
						tv_location_item.setText(R.string.finish);
					} else {
						tv_location_item.setText(R.string.failed);
					}
				} else {
					tv_location_item.setText(R.string.failed);
				}
				initHandler.sendEmptyMessage(TYPE_FINISH);
			}
		});
		locationTask.execute(TYPE_LOCATION);
	}

	private void productCategoryInit() throws Exception {
        boolean flag =AppPreferences.prfs(this).getBoolean(AppPreferences.Config.PRODUCTCATEGORY_INIT, true);
        if (flag) {
            List<ProductCategory> productCategories = new ArrayList<ProductCategory>();
            SAXParserFactory sax = SAXParserFactory.newInstance();
            XMLReader reader = sax.newSAXParser().getXMLReader();
            reader.setContentHandler(new ProductCategoryXMLHandler(productCategories));
            reader.parse(new InputSource(getResources().openRawResource(R.raw.product_category)));
            DataSupport.saveAll(productCategories);
            AppPreferences.prfs(this).edit().putBoolean(AppPreferences.Config.PRODUCTCATEGORY_INIT, false).apply();
        }
	}

	private void addressConstantsInit() throws Exception{
        boolean flag =AppPreferences.prfs(this).getBoolean(AppPreferences.Config.ADDRESSCONSTANTS_INIT, true);
        if (flag) {
            List<MapAddress> constants = new ArrayList<MapAddress>();
            SAXParserFactory sax = SAXParserFactory.newInstance();
            XMLReader reader = sax.newSAXParser().getXMLReader();
            reader.setContentHandler(new AddressConstantsXMLHandler(constants));
            reader.parse(new InputSource(getResources().openRawResource(R.raw.address_constants)));
            DataSupport.saveAll(constants);
            AppPreferences.prfs(this).edit().putBoolean(AppPreferences.Config.ADDRESSCONSTANTS_INIT, false).apply();
        }
	}

    private final class ProductCategoryXMLHandler extends DefaultHandler {

        private List<ProductCategory> proCatList;
        private ProductCategory proCat;
        private String per_tag;
        public ProductCategoryXMLHandler(List<ProductCategory> list){
            super();
            this.proCatList = list;
        }

        @Override
        public void startDocument() throws SAXException {
            LogUtil.debug("Parser xml file.");
            super.startDocument();
        }

        @Override
        public void startElement(String uri, String localName, String qName,
                                 Attributes attributes) throws SAXException {
            per_tag = localName;
            if("productCategory".equals(localName)){
                proCat = null;
                proCat = new ProductCategory();
            }
            super.startElement(uri, localName, qName, attributes);
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            if("productCategoryId".equals(per_tag)){
                proCat.setProductCategoryId(Integer.parseInt(new String(ch, start, length)));
            } else if ("productCategoryName".equals(per_tag)){
                proCat.setProductCategoryName(new String(ch, start, length));
            } else if ("isLeaf".equals(per_tag)){
                proCat.setIsLeaf(new String(ch, start, length));
            } else if ("upProductCategoryId".equals(per_tag)){
                proCat.setUpProductCategoryId(Integer.parseInt(new String(ch, start, length)));
            }
            super.characters(ch, start, length);
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            per_tag = "";
            if("productCategory".equals(localName)){
                proCatList.add(proCat);
            }
            super.endElement(uri, localName, qName);
        }
    }

    private final class AddressConstantsXMLHandler extends DefaultHandler {
        private String TAG = "AddressConstantsXMLHandler.class";

        private List<MapAddress> conList;
        private MapAddress constant;
        private String per_tag;

        public AddressConstantsXMLHandler(List<MapAddress> constants) {
            super();
            this.conList = constants;
        }

        public void startDocument() throws SAXException {
            LogUtil.debug("Parser xml file.");
            super.startDocument();
        }

        public void characters(char[] ch, int start, int length) throws SAXException {
            if ("constantType".equals(per_tag)) {
                constant.setType(new String(ch, start, length));
            } else if("constantName".equals(per_tag)){
                constant.setName(new String(ch, start, length));
            }else if("constantValue".equals(per_tag)){
                constant.setValue(new String(ch, start, length));
            }else if("constantOrder".equals(per_tag)){
                constant.setAddressOrder(Integer.parseInt(new String(ch, start, length)));
            }
            super.characters(ch, start, length);
        }

        public void startElement(String uri, String localName, String qName,
                                 Attributes attributes) throws SAXException {
            per_tag = localName;
            if("addressConstant".equals(localName)){
                constant = null;
                constant = new MapAddress();
            }
            super.startElement(uri, localName, qName, attributes);
        }

        public void endElement(String uri, String localName, String qName)
                throws SAXException {
            per_tag = "";
            if("addressConstant".equals(localName)){
                conList.add(constant);
            }
            super.endElement(uri, localName, qName);
        }

        public void endDocument() throws SAXException {
            super.endDocument();
        }
    }


	
}
