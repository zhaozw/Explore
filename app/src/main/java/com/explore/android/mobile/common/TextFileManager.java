package com.explore.android.mobile.common;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;

/**
 * 
 * @author Ryan
 *
 */
public class TextFileManager {
	
	private String fileName;

	private Context context;

	public TextFileManager(Context con, String fName) {

		this.context = con;

		this.fileName = fName;

	}

	public boolean isExist() {

		String[] fileNameArray = context.fileList();

		for (int i = 0; i < fileNameArray.length; i++) {

			if (fileNameArray[i].equals(fileName)) {

				return true;

			}

		}

		return false;

	}

	public boolean create() {

		boolean result = false;

		if (!isExist()) {

			FileOutputStream fos = null;

			try {

				fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);

			} catch (FileNotFoundException e) {

				e.printStackTrace();

			}
			if (fos != null) {

				result = true;

				try {

					fos.close();

				} catch (IOException e) {

					e.printStackTrace();

				}

			}

		}

		return result;
	}

	public boolean write(String str) {

		boolean result = true;

		FileOutputStream fos = null;

		if (isExist()) {

			try {

				fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);

			} catch (FileNotFoundException e) {

				e.printStackTrace();

				result = false;

			}

			byte bt[] = (str).getBytes();

			if (fos != null) {

				try {

					fos.write(bt);

				} catch (IOException e) {

					e.printStackTrace();

					result = false;

				}

				try {

					fos.close();

				} catch (Exception e) {

					e.printStackTrace();

					result = false;
				}

			}

		}

		return result;

	}

	public String readLine() {

		String str = "";

		FileInputStream fis = null;

		if (isExist()) {

			try {

				fis = context.openFileInput(fileName);

			} catch (FileNotFoundException e) {

				e.printStackTrace();

			}

			BufferedReader br = new BufferedReader(new InputStreamReader(fis));

			try {

				str = br.readLine();

				fis.close();

			} catch (IOException e) {

				e.printStackTrace();

			}

		}

		return str;

	}

	public String read() {

		String str = "";

		String line = "";

		FileInputStream fis = null;

		if (isExist()) {

			try {

				fis = context.openFileInput(fileName);

			} catch (FileNotFoundException e) {

				e.printStackTrace();

			}

			BufferedReader br = new BufferedReader(new InputStreamReader(fis));

			try {

				while ((line = br.readLine()) != null) {

					str += line;

				}
				fis.close();

			} catch (IOException e) {

				e.printStackTrace();

			}

		}

		return str;

	}

	@SuppressWarnings("unchecked")
	public Map<String, String> getConfigMap() {

		String line = "";

		FileInputStream fis = null;

		@SuppressWarnings("rawtypes")
		Map<String, String> map = new HashMap();

		if (isExist()) {

			try {

				fis = context.openFileInput(fileName);

			} catch (FileNotFoundException e) {

				e.printStackTrace();

			}

			BufferedReader br = new BufferedReader(new InputStreamReader(fis));

			try {

				while ((line = br.readLine()) != null) {

					String[] array;
					array = line.split("=");

					String a1 = array[0];
					String a2 = array[1];

					map.put(a1, a2);

					array = null;

				}

				fis.close();

			} catch (IOException e) {

				e.printStackTrace();

			}

		}

		return map;

	}

	public void clean() {

		delete();

		create();

	}

	public void delete() {

		context.deleteFile(fileName);

	}

	public String getConfigFromFile(String cn) {

		String config = "";

		return config;

	}
}
