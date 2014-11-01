package com.explore.exapp.base.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by ryan on 14/10/31.
 */
public class ToastUtil {

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, int msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
