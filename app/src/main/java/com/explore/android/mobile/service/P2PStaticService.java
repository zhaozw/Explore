package com.explore.android.mobile.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.explore.android.R;
import com.explore.android.core.base.BaseAsynTask;
import com.explore.android.core.base.BaseAsynTask.TaskOnPostExecuteListener;
import com.explore.android.core.base.BaseAsynTask.TaskResult;
import com.explore.android.core.http.AsynDataTask;
import com.explore.android.core.model.ExResponse;
import com.explore.android.mobile.activity.message.ExMessageManager;
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
public class P2PStaticService extends Service {

	public static final String PARAM = "param";
	private static final long REFRESH_TIME = 60*1000;
	private static final String TAG = P2PStaticService.class.getName();
	public static final String ACTION = "com.explore.android.mobile.service.P2PStaticService";

	private SharePreferencesManager preferences;
	private Notification notification;
	private NotificationManager notificationManager;
	
	private final Handler handler = new Handler();
	private final Timer timer = new Timer();
	private MyBinder myBinder = new MyBinder();
	
	private int infoId;
	
	@Override
	public IBinder onBind(Intent intent) {
		return myBinder;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		preferences = SharePreferencesManager.getInstance(this);
		initNotifiManager();
		timer.schedule(pollingP2P, 0, REFRESH_TIME);
	}
	
	private TimerTask pollingP2P = new TimerTask() {
		@Override
		public void run() {
			handler.post(new Runnable() {
				@Override
				public void run() {
					getP2PInfo();
				}
			});
		}
	};
	
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
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
						infoId = msg.getInfoId();
						showNofification(msg.getTitle());
					}
					
					ExMessageManager.UPDATA = true;
					MessageDBO msgDbo = new MessageDBO(P2PStaticService.this);
					msgDbo.open();
					msgDbo.addMessages(msgList);
					msgDbo.close();
					ExMessageManager.loadMessages(P2PStaticService.this);
				}
				
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
		if(infoId != 0){
			intent.putExtra("INFOID", infoId);
		}
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
				intent, Intent.FLAG_ACTIVITY_NEW_TASK);
		notification.setLatestEventInfo(this,
				getResources().getString(R.string.app_name), msg_summary, pendingIntent);
		notificationManager.notify(0, notification);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.i(TAG, "Explroe P2PService destroy.");
	}
	
	public class MyBinder extends Binder {
		public P2PStaticService getService() {
			return P2PStaticService.this;
		}
	}
	
}
