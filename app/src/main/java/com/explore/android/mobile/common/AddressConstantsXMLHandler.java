package com.explore.android.mobile.common;

import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

import com.explore.android.mobile.model.ExConstant;

public class AddressConstantsXMLHandler extends DefaultHandler{

	private String TAG = "AddressConstantsXMLHandler.class";
	
	private List<ExConstant> conList;
	private ExConstant constant;
	private String per_tag;
	
	public AddressConstantsXMLHandler(){
		super();
	}
	
	public AddressConstantsXMLHandler(List<ExConstant> constants) {
		super();
		this.conList = constants;
	}
	
	public void startDocument() throws SAXException {
		Log.i(TAG, "文档解析开始");
		super.startDocument();
	}
	
	public void characters(char[] ch, int start, int length) throws SAXException {
		if ("constantType".equals(per_tag)) {
			constant.setConstantType(new String(ch, start, length));
		} else if("constantName".equals(per_tag)){
			constant.setConstantName(new String(ch, start, length));
		}else if("constantValue".equals(per_tag)){
			constant.setConstantValue(new String(ch, start, length));
		}else if("constantOrder".equals(per_tag)){
			constant.setConstantOrder(Integer.parseInt(new String(ch, start, length)));
		}else if("category1".equals(per_tag)){
			constant.setCategory1(new String(ch, start, length));
		}else if("category2".equals(per_tag)){
			constant.setCategory2(new String(ch, start, length));
		}
		super.characters(ch, start, length);
	}
	
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		per_tag = localName;
		if("addressConstant".equals(localName)){
			constant = null;
			constant = new ExConstant();
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
		Log.i(TAG, "文档结束");
		super.endDocument();
	}
	
}
