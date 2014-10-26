package com.explore.android.mobile.view;

import com.explore.android.R;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

public class DialogUtil {

	public static void createMessageDialog(Context context, int title, int msg, OnClickListener positiveListener) {
		CustomDialog dialog = new CustomDialog(context);
		CustomDialog.Builder builder = new CustomDialog.Builder(context);
		
		builder.setTitle(title);
		builder.setMessage(msg);
		
		builder.setPositiveButton(R.string.dialog_ok, positiveListener);
		builder.setNegativeButton(R.string.dialog_cancel, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		
		dialog = builder.create();
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = windowManager.getDefaultDisplay();
		int dlg_width = (int) (display.getWidth() * 0.8);
		Window dlg_window = dialog.getWindow();
		WindowManager.LayoutParams lp = dlg_window.getAttributes();
		lp.width = dlg_width;
		dlg_window.setAttributes(lp);
		dialog.setCanceledOnTouchOutside(false);
		
		dialog.show();
	}
	
	public static ProgressDialog progressDialog;
	public static void createProgressDialog(Context context, int title, int msg){
		progressDialog = new ProgressDialog(context);
		progressDialog.setTitle(title);
		progressDialog.setMessage(context.getText(msg));
		progressDialog.show();
	}
}
