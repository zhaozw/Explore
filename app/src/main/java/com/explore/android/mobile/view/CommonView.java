package com.explore.android.mobile.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

/**
 * 控件样式的帮助类
 * @author Ryan
 */
public class CommonView {

	/**
	 * 添加一条线
	 * @param height 线条高度dimens资源ID
	 * @param context 
	 * @param color 线条颜色的资源文件ID
	 * @return 返回这个线条的View类的一个实例
	 */
	public static View getSingleLine(int height, Context context, int color) {
		View view = new View(context);
		view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, height));
		view.setBackgroundResource(color);
		return view;
	}
}
