package com.explore.exapp.base.component;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;

import com.explore.exapp.R;

/**
 * Created by ryan on 14/11/6.
 */
public class CustomProgress extends Dialog {

    private CustomProgress(Context context) {
        super(context, R.style.CustomDialog);
        this.setContentView(R.layout.loading);
    }

    public static CustomProgress progress;

    public static CustomProgress getInstance(Context context) {
        if (progress == null) {
            progress = new CustomProgress(context);
        }
        progress.setOnKeyListener(keyListener);
        progress.setCanceledOnTouchOutside(false);
        return  progress;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        progress = null;
    }

    private static final OnKeyListener keyListener = new OnKeyListener() {
        @Override
        public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                return true;
            }
            return false;
        }
    };
}
