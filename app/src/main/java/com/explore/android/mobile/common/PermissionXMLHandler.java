package com.explore.android.mobile.common;

import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

import com.explore.android.mobile.model.Permission;

/**
 * Parse the xml files.
 * The xml file  include all used permission in apps.
 * 
 * @author Ryan
 *
 */
public class PermissionXMLHandler extends DefaultHandler {

	private final String TAG = "PermissionHandler.class";

	private List<Permission> per_list;
	private Permission permission;
	private String per_tag;

	public PermissionXMLHandler() {
		super();
	}

	public PermissionXMLHandler(List<Permission> permissionList) {
		super();
		this.per_list = permissionList;
	}

	public void startDocument() throws SAXException {
		Log.i(TAG, "文档解析开始");
		super.startDocument();
	}

	/**
	 * Get the permission informations,assigned to permission object.
	 */
	public void characters(char[] ch, int start, int length) throws SAXException {
		if ("name".equals(per_tag)) {
			permission.setPermissionName(new String(ch, start, length));
		} else if("code".equals(per_tag)){
			permission.setPermissionCode(Integer.parseInt(new String(ch, start, length)));
		}else if("action".equals(per_tag)){
			permission.setActivityName(new String(ch, start, length));
		}else if("level".equals(per_tag)){
			permission.setPermissionLevel(Integer.parseInt(new String(ch, start, length)));
		}else if("father".equals(per_tag)){
			permission.setFatherCode(new String(ch, start, length));
		}else if("haspermission".equals(per_tag)){
			permission.setHaspermission(new String(ch, start, length));
		}else if("icon".equals(per_tag)){
			permission.setPermissionIcon(new String(ch, start, length));
		}
		super.characters(ch, start, length);
	}

	/**
	 * Find the start element from xml file.
	 * When find a start element,new a permission object.
	 */
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		per_tag = localName;
		if("permission".equals(localName)){
			permission = null;
			permission = new Permission();
		}
		super.startElement(uri, localName, qName, attributes);
	}

	/**
	 * Find the end element.
	 * When find a end element add the permission object to list.
	 */
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		per_tag = "";
		if("permission".equals(localName)){
			per_list.add(permission);
		}
		super.endElement(uri, localName, qName);
	}

	public void endDocument() throws SAXException {
		Log.i(TAG, "文档结束");
		super.endDocument();
	}

}
