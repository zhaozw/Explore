package com.explore.android.mobile.common;

import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

import com.explore.android.mobile.model.ProductCategory;

/**
 * 读取
 * @author Ryan
 *
 */
public class ProductCategoryXMLHandler extends DefaultHandler{

	private final String TAG = "ProductCategoryHandler.class";
	
	private	List<ProductCategory> proCatList;
	private ProductCategory proCat;
	private String per_tag;
	
	public ProductCategoryXMLHandler(){
		super();
	}
	
	public ProductCategoryXMLHandler(List<ProductCategory> list){
		super();
		this.proCatList = list;
	}
	
	@Override
	public void startDocument() throws SAXException {
		Log.i(TAG, "文档解析开始");
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
