package com.explore.exapp.base.util;

import org.apache.commons.codec.binary.Base64;

/**
 * Base64 Coder for encoding/decoding
 * 
 * @author Nan Lei
 * 
 */
public class Base64Coder {
	private static final String ENCODING = "UTF-8";

	/**
	 * Standard encoding of Base64
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	private static String encodeBase64(String data) throws Exception {
		byte[] bytes = Base64.encodeBase64(data.getBytes(ENCODING));
		return new String(bytes, ENCODING);
	}

	/**
	 * Standard decoding of Base64
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	private static String decodeBase64(String data) throws Exception {
		byte[] bytes = Base64.decodeBase64(data.getBytes(ENCODING));
		return new String(bytes, ENCODING);
	}

	/**
	 * Encoding for project
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String encode(String data) throws Exception {
		String base64Str = encodeBase64(data);
		int length = base64Str.length() / 4;
		StringBuffer sb = new StringBuffer();
		sb.append(base64Str.subSequence(length + 1, length * 2));
		sb.append(base64Str.subSequence(0, length + 1));

		sb.append(base64Str.subSequence(length * 3 + 1, length * 4));
		sb.append(base64Str.subSequence(length * 2, length * 3 + 1));

		return sb.toString();
	}

	/**
	 * Decoding for project
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String decode(String data) throws Exception {
		int length = data.length() / 4;
		StringBuffer sb = new StringBuffer();
		sb.append(data.subSequence(length - 1, length * 2));
		sb.append(data.subSequence(0, length - 1));

		sb.append(data.subSequence(length * 3 - 1, length * 4));
		sb.append(data.subSequence(length * 2, length * 3 - 1));

		return decodeBase64(sb.toString());
	}
}
