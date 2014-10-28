package com.explore.android.core.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.util.Log;

import com.explore.android.core.util.Base64Coder;
import com.explore.android.mobile.constants.ErrorConstants;
import com.explore.android.mobile.constants.HttpConstant;

public class HttpConn {

	private static final String TAG = HttpConn.class.getName();
	
	private static HttpConn instance;

	public static HttpConn getInstance() {
		if (instance == null) {
			instance = new HttpConn();
		}
		return instance;
	}

	private HttpConn() {
	}

	private int reqTimeOut = HttpConstant.ConnectTimeout; // 请求超时时间
	private String reqURLStr;
	private String reqParaStr; // 参数
	private int execResultCode = -1; // 执行结果
	private String execResultStr; // 返回执行结果字符串
	private HttpURLConnection httpUrlConnection = null; // 建立连接
	
	private static final String ENCODETYPE = "UTF-8";

	public int execute() {

		StringBuffer responseMessage = null;
		int responseCode = -1;

		try {

			Log.e(TAG, "Request Url:"+getReqURLStr());
			
			URL postUrl = new URL(getReqURLStr());
			httpUrlConnection = (HttpURLConnection) postUrl.openConnection();

			// 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在http正文内，因此需要设为true,默认情况下是false;
			httpUrlConnection.setDoOutput(true);

			// 设置是否从httpUrlConnection读入，默认情况下是true;
			httpUrlConnection.setDoInput(true);

			// Post 请求不能使用缓存
			httpUrlConnection.setUseCaches(false);

			// 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的
			// 意思是正文是urlencoded编码过的form参数，下面我们可以看到我们对正文内容使用URLEncoder.encode
			// 进行编码
			httpUrlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			// 获得上传信息的字节大小及长度
			byte[] data = getReqParaStr().getBytes();
			httpUrlConnection.setRequestProperty("Content-Length", String.valueOf(data.length));

			// 设定请求的方法为"POST"，默认是GET
			httpUrlConnection.setRequestMethod("POST");

			httpUrlConnection.setConnectTimeout(getReqTimeOut());
			httpUrlConnection.setReadTimeout(getReqTimeOut() * 2);
			// httpUrlConnection.setInstanceFollowRedirects(true);
			// 进行连接
			httpUrlConnection.connect();

			DataOutputStream objOutputStrm = new DataOutputStream(
					httpUrlConnection.getOutputStream());

			// The URL-encoded contend
			// 正文，正文内容其实跟get的URL中'?'后的参数字符串一致
			objOutputStrm.writeBytes(getReqParaStr());
			
			// 刷新对象输出流，将任何字节都写入潜在的流中（些处为ObjectOutputStream）
			objOutputStrm.flush();

			// 关闭流对象。此时，不能再向对象输出流写入任何数据，先前写入的数据存在于内存缓冲区中,
			// 在调用下边的getInputStream()函数时才把准备好的http请求正式发送到服务器
			objOutputStrm.close();

			BufferedReader br = new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream(), "UTF-8"));

			responseMessage = new StringBuffer();
			int charCount = -1;
			while ((charCount = br.read()) != -1) {
				responseMessage.append((char) charCount);
			}

			// 传递返回值
			responseCode = httpUrlConnection.getResponseCode();
			
			setExecResultCode(responseCode);
			if (responseCode == 200) {
				// 调用上层接收消息函数接口
				// receiveData(responseMessage.toString(), reqType);
				setExecResultStr(responseMessage.toString());
				// 关闭
				br.close();
			} else {
				setExecResultStr("");
			}

		} catch (MalformedURLException e) {
			Log.e(TAG, "MalformedURLException");
			e.printStackTrace();
			setExecResultStr("");
		} catch (ConnectException e) {
			Log.e(TAG, "ConnectException");
			responseCode = -2;
			setExecResultStr("");
		} catch (SocketTimeoutException e){
			Log.e(TAG, "SocketTimeoutException");
			responseCode = -3;
			setExecResultStr("");
		} catch (IOException e) {
			Log.e(TAG, "IOException");
			e.printStackTrace();
			setExecResultStr("");
		} finally {
			httpUrlConnection.disconnect();
		}

		return responseCode;
	}
	
	public String simpleAction(String url,String data) {
		String res = null;
		try {
			HttpPost post = new HttpPost(url);
			ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("data", data));
			post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			DefaultHttpClient client = new DefaultHttpClient();
			HttpResponse httpResponse = client.execute(post);
			if(httpResponse.getStatusLine().getStatusCode()!=200)
				throw new IOException();
			HttpEntity entity = httpResponse.getEntity();
			res = EntityUtils.toString(entity);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public void destory() {
		if(httpUrlConnection != null){
			httpUrlConnection.disconnect();
		}
	}

	private String getReqURLStr() {
		return reqURLStr;
	}

	public void setReqURLStr(String reqURLStr) {
		this.reqURLStr = reqURLStr;
	}

	private String getReqParaStr() {
		return reqParaStr;
	}

	public void setReqParaStr(String reqParaStr) {
		
		try {
			this.reqParaStr = URLEncoder.encode("DATA", ENCODETYPE)+"="+URLEncoder.encode(Base64Coder.encode(reqParaStr),ENCODETYPE);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {	
			e.printStackTrace();
		}
	}
	
	public void setReqParaStr(List<String> requests){
		try {
			StringBuffer buffer = new StringBuffer();
			for (int i = 0; i < requests.size(); i++) {
				if(0 == i){
					buffer.append(URLEncoder.encode("DATA", ENCODETYPE)+"="+URLEncoder.encode(Base64Coder.encode(requests.get(i)),ENCODETYPE));
				} else {
					buffer.append("&"+URLEncoder.encode("DATA"+i, ENCODETYPE)+"="+URLEncoder.encode(Base64Coder.encode(requests.get(i)),ENCODETYPE));
				}
			}
			this.reqParaStr = buffer.toString();
            Log.e("lcr","setReqParaStr");
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {	
			e.printStackTrace();
		}
	}

	private int getReqTimeOut() {
		return reqTimeOut;
	}

	public void setReqTimeOut(int reqTimeOut) {
		this.reqTimeOut = reqTimeOut;
	}

	public int getExecResultCode() {
		return execResultCode;
	}

	private void setExecResultCode(int execResultCode) {
		this.execResultCode = execResultCode;
	}

	public String getExecResultStr() {
		if(execResultStr != null && !("").equals(execResultStr)){
			try {
				Log.e(TAG, "Response:" + Base64Coder.decode(execResultStr));
				return Base64Coder.decode(execResultStr);
			} catch (Exception e) {
				return ErrorConstants.DECODE_ERROR;
			}
		} else {
			Log.e(TAG, "No response.");
		}
		return ErrorConstants.VALUE_NULL;
	}

	public String getExecResultStrSrc() {
		return execResultStr;
	}

	private void setExecResultStr(String execResultStr) {
		this.execResultStr = execResultStr;
	}

	public void setHttpUrlConnection(HttpURLConnection httpUrlConnection) {
		this.httpUrlConnection = httpUrlConnection;
	}
}
