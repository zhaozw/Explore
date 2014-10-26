package com.explore.android.mobile.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.explore.android.R;

public class CustomDialog extends Dialog {

	public CustomDialog(Context context) {
		super(context);
	}

	public CustomDialog(Context context, int theme) {
		super(context, theme);
	}

	public static class Builder {
		private Context context;
		private String title;
		private String message;
		private String positiveButtonText;
		private String negativeButtonText;
		private String editButtonText;
		private View contentView;
		
		private Button positiveButton;
		
		private OnClickListener positiveButtonClickListener,
				negativeButtonClickListener, exitButtonClickListener;

		public Builder(Context con) {
			this.context = con;
		}

		public Builder setMessage(String msg) {
			this.message = msg;
			return this;
		}

		public Builder setMessage(int msg) {
			this.message = (String) context.getText(msg);
			return this;
		}

		public Builder setTitle(String title) {
			this.title = title;
			return this;
		}

		public Builder setTitle(int title) {
			this.title = (String) context.getText(title);
			return this;
		}

		public Builder setContentView(View v) {
			this.contentView = v;
			return this;
		}
		
		public Builder setContentView(int resId) {
			LayoutInflater inflater = LayoutInflater.from(context);
			this.contentView = inflater.inflate(resId, null);
			return this;
		}

		//定义PositiveButton
		public Builder setPositiveButton(int positiveButtonText, OnClickListener listener) {
			this.positiveButtonText = (String) context.getText(positiveButtonText);
			this.positiveButtonClickListener = listener;
			return this;
		}

		public Builder setPositiveButton(String positiveButtonText, OnClickListener listener) {
			this.positiveButtonText = positiveButtonText;
			this.positiveButtonClickListener = listener;
			return this;
		}

		//定义EditButton
		public Builder setEditButton(int editButtonText, OnClickListener listener) {
			this.editButtonText = (String) context.getText(editButtonText);
			this.exitButtonClickListener = listener;
			return this;
		}

		public Builder setEditButton(String editButtonText, OnClickListener listener) {
			this.editButtonText = editButtonText;
			this.exitButtonClickListener = listener;
			return this;
		}

		//定义NegativeButton
		public Builder setNegativeButton(int negativeButtonText, OnClickListener listener) {
			this.negativeButtonText = (String) context.getText(negativeButtonText);
			this.negativeButtonClickListener = listener;
			return this;
		}

		public Builder setNegativeButton(String negativeButtonText, OnClickListener listener) {
			this.negativeButtonText = negativeButtonText;
			this.negativeButtonClickListener = listener;
			return this;
		}
		
		public void setPositiveButtonEnable(boolean flag){
			positiveButton.setEnabled(true);
		}
		
		public CustomDialog create() {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final CustomDialog dialog = new CustomDialog(context, R.style.CustomDialog);
			View layout = inflater.inflate(R.layout.dialog_custom, null);
			((TextView) layout.findViewById(R.id.dialog_cus_tv_title)).setText(title);

			// 设置positiveButtonText
			if (positiveButtonText != null) {
				positiveButton = (Button) layout.findViewById(R.id.dialog_cus_pbtn);
				positiveButton.setText(positiveButtonText);
				if (positiveButtonClickListener != null) {
					positiveButton.setOnClickListener(new View.OnClickListener() {
								public void onClick(View arg0) {
									positiveButtonClickListener.onClick(dialog,DialogInterface.BUTTON1);
								}
							});
				}
			} else {
				layout.findViewById(R.id.dialog_cus_pbtn).setVisibility(View.GONE);
			}

			// 设置editButtonText
			if (editButtonText != null) {
				((Button) layout.findViewById(R.id.dialog_cus_ebtn)).setText(editButtonText);
				if (exitButtonClickListener != null) {
					((Button) layout.findViewById(R.id.dialog_cus_ebtn)).setOnClickListener(new View.OnClickListener() {
								public void onClick(View arg0) {
									exitButtonClickListener.onClick(dialog, DialogInterface.BUTTON2);
								}
							});
				}
			} else {
				layout.findViewById(R.id.dialog_cus_ebtn).setVisibility(
						View.GONE);
			}

			// 设置negativeButtonText
			if (negativeButtonText != null) {
				((Button) layout.findViewById(R.id.dialog_cus_nbtn)).setText(negativeButtonText);
				if (negativeButtonClickListener != null) {
					((Button) layout.findViewById(R.id.dialog_cus_nbtn)).setOnClickListener(new View.OnClickListener() {
								public void onClick(View arg0) {
									negativeButtonClickListener.onClick(dialog, DialogInterface.BUTTON3);
								}
							});
				}
			} else {
				layout.findViewById(R.id.dialog_cus_nbtn).setVisibility(
						View.GONE);
			}

			if (message != null) {
				((TextView) layout.findViewById(R.id.dialog_cus_tv_msg)).setText(message);
				
			}else if (contentView != null) {
				((LinearLayout) layout.findViewById(R.id.dialog_cus_layout_content)).removeAllViews();
				((LinearLayout) layout.findViewById(R.id.dialog_cus_layout_content)).addView(contentView, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
			}
			
			dialog.setContentView(layout);
			return dialog;
		}
		
	}

}
