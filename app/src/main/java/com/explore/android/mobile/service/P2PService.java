package com.explore.android.mobile.service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import com.explore.android.R;
import com.explore.android.core.base.BaseAsynTask;
import com.explore.android.core.base.BaseAsynTask.TaskOnPostExecuteListener;
import com.explore.android.core.base.BaseAsynTask.TaskResult;
import com.explore.android.core.http.AsynDataTask;
import com.explore.android.core.model.ExResponse;
import com.explore.android.mobile.activity.message.MessageActivity;
import com.explore.android.mobile.common.SharePreferencesManager;
import com.explore.android.mobile.constants.ErrorConstants;
import com.explore.android.mobile.constants.RequestConstants;
import com.explore.android.mobile.constants.ResponseConstant;
import com.explore.android.mobile.data.request.P2PRequest;
import com.explore.android.mobile.db.opration.MessageDBO;
import com.explore.android.mobile.model.ExMessage;

/**
 * Explore轮询服务
 * 
 * @author Ryan
 * 
 */
public class P2PService extends IntentService {

	public static final String PARAM = "param";
	private static final String TAG = P2PService.class.getName();
	public static final String ACTION = "com.explore.android.mobile.service.P2PService";

	private SharePreferencesManager preferences;
	private Notification notification;
	private NotificationManager notificationManager;

	public P2PService() {
		super("P2PService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		String action = intent.getExtras().getString(PARAM);
		if("polling".equals(action)){
			Log.e(TAG, "onHandleIntent >> polling");
			getP2PInfo();
		}
	}

	@Override
	public void onCreate() {
		super.onCreate();
		preferences = SharePreferencesManager.getInstance(this);
		initNotifiManager();
	}
	
	private void getP2PInfo() {
		AsynDataTask p2pRequestTask = new AsynDataTask();
		P2PRequest request = new P2PRequest();

		p2pRequestTask.setTaskOnPostExecuteListener(new TaskOnPostExecuteListener<ExResponse>() {
			@Override
			public void onPostExecute(TaskResult<? extends ExResponse> result) {
				if(result != null && result.Status == BaseAsynTask.STATUS_SUCCESS){
					ExResponse response = result.Value;
					if(ErrorConstants.VALUE_NULL.equals(response.getResMessage())){
						return;
					}else{
						bindP2PInfoData(response.getResMessage());
					}
				}
			}
		});

		request.setUserId(preferences.getUserId());
		request.setToken(preferences.getTokenLast8());
		request.setType("REQ");
		request.setInfoId("");
		p2pRequestTask.execute(preferences.getHttpUrl(),RequestConstants.P2P_INFO, request.toJsonString());
	}
	
	private boolean bindP2PInfoData(String dataStr){
		try {
			JSONObject jsonObject = new JSONObject(dataStr);
			if (ResponseConstant.OK.equals(jsonObject.get(ResponseConstant.STATUS))) {
				
				JSONArray jsonArray = jsonObject.getJSONArray("P2PINFOLIST");
				
				List<ExMessage> msgList = new ArrayList<ExMessage>();
				
				if(jsonArray.length() > 0){
					for (int i = 0; i < jsonArray.length(); i++) {
						ExMessage msg = new ExMessage();
						JSONObject json = jsonArray.getJSONObject(i);
						msg.setInfoId(json.getInt("INFOID"));
						msg.setType(json.getString("TYPE"));
						msg.setTitle(json.getString("TITLE"));
						msg.setContent(json.getString("CONTENT"));
						msg.setCreateBy(json.getInt("CREATEBY"));
						msg.setCreateByName(json.getString("CREATEBYNAME"));
						msg.setCreateByTime(json.getString("CREATEBYTIME"));
						msgList.add(msg);
						showNofification(msg.getTitle());
					}
				}
				
				Log.e(TAG, "msg_size="+msgList.size());
				
				MessageDBO msgDbo = new MessageDBO(P2PService.this);
				msgDbo.open();
				msgDbo.addMessages(msgList);
				msgDbo.close();
				return true;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	private void initNotifiManager() {
		notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		int icon = R.drawable.ic_launcher;
		notification = new Notification();
		notification.icon = icon;
		notification.tickerText = getResources().getString(R.string.notifi_ticker);
		notification.defaults |= Notification.DEFAULT_SOUND;
		notification.flags = Notification.FLAG_AUTO_CANCEL;
	}

	private void showNofification(String msg_summary) {
		notification.when = System.currentTimeMillis();
		Intent intent = new Intent(this, MessageActivity.class);
		intent.putExtra("TYPE", "NEW_MSG");
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,intent, Intent.FLAG_ACTIVITY_NEW_TASK);
		notification.setLatestEventInfo(this,getResources().getString(R.string.app_name), msg_summary, pendingIntent);
		notificationManager.notify(0, notification);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.i(TAG, "Explroe P2PService destroy.");
	}
}
