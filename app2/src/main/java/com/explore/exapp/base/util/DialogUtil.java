package com.explore.exapp.base.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import com.explore.exapp.R;

/**
 * Created by ryan on 14/11/9.
 */
public class DialogUtil {

    private static Dialog mDialog;

    public static void dismissDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    public static void showMessageDialog(Context context, int title, String message,
                                    int button1, DialogInterface.OnClickListener listener1) {
        mDialog = new AlertDialog.Builder(context).setTitle(title)
                .setMessage(message).setPositiveButton(button1, listener1).create();
        mDialog.show();
    }

    public static void showConfirmDialgo(Context context, int title, String message,
                                         DialogInterface.OnClickListener positionListener) {
        mDialog = new AlertDialog.Builder(context).setTitle(title)
                .setMessage(message).setPositiveButton(R.string.ok, positionListener)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create();
        mDialog.show();
    }
}
