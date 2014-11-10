package com.explore.exapp.base.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by ryan on 14/11/9.
 */
public class DialogUtil {

    public static void showMessageDialog(Context context, int title, String message,
                                    int button1, DialogInterface.OnClickListener listener1) {
        new AlertDialog.Builder(context).setTitle(title)
                .setMessage(message).setPositiveButton(button1, listener1).show();
    }
}
