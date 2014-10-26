package com.explore.android.core.http;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.explore.android.R;
import com.explore.android.core.util.ExParseException;
import com.explore.android.core.util.WebAddress;

/**
 * tip:apk application/vnd.android.package-archive
 * @author Ryan
 *
 */
public class DownloadHandler {

	private static final String TAG = "Ex-DownloadHandler";
	
	static void startDownload(Activity activity, String url, String title, String description) {
		String status = Environment.getExternalStorageState();
		if (!status.equals(Environment.MEDIA_MOUNTED)) {
			String msg;
			if (Environment.MEDIA_SHARED.equals(status)) {
				msg = activity.getString(R.string.download_sdcard_busy_dlg_title);
				Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
			} else {
				msg = activity.getString(R.string.download_no_sdcard_dlg_title);
				Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
			}
			return;
		}
		
		WebAddress webAddress;
		try {
			webAddress = new WebAddress(url);
			webAddress.setmPath(encodePath(webAddress.getmPath()));
		} catch (ExParseException e) {
			// TODO: handle exception
			Log.e(TAG, "Exception trying to parse url:" + url);
			return;
		}
		
		String addressString = webAddress.toString();
		Uri uri = Uri.parse(addressString);
		final DownloadManager.Request request;
		try {
			request = new DownloadManager.Request(uri);
		} catch (IllegalArgumentException e) {
			Toast.makeText(activity, R.string.cannot_download, Toast.LENGTH_SHORT).show();
			return;
		}
		
		request.setTitle(title);
		request.setDescription(description);
		request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
		IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
		final DownloadManager manager = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
		new Thread("Explore Download") {
			@Override
			public void run() {
				manager.enqueue(request);
			}
		}.start();
	}
	
	public BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle extras = intent.getExtras();
			long downloadId = extras.getLong(DownloadManager.EXTRA_DOWNLOAD_ID);
			
		}
	};
	
	private static String encodePath(String path) {
        char[] chars = path.toCharArray();

        boolean needed = false;
        for (char c : chars) {
            if (c == '[' || c == ']' || c == '|') {
                needed = true;
                break;
            }
        }
        if (needed == false) {
            return path;
        }

        StringBuilder sb = new StringBuilder("");
        for (char c : chars) {
            if (c == '[' || c == ']' || c == '|') {
                sb.append('%');
                sb.append(Integer.toHexString(c));
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }
}
