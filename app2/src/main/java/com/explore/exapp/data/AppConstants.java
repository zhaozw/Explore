package com.explore.exapp.data;

import com.explore.exapp.R;

/**
 * Created by ryan on 14/11/2.
 */
public class AppConstants {

    public static final String PREFERENCES_FILE_NAME = "conf";

    public static final String HTTP_STATUS = "STATUS";

    public static final String OK = "OK";

    public static final String EXCEPTION = "EXCEPTION";

    public static final String USEREXCEPTION = "USEREXCEPTION";

    public static final String EXCEPTIONLIST = "EXCEPTIONLIST";

    public static final int[] TAB_ICON = new int[] {
            R.drawable.icon_home,
            R.drawable.icon_order,
            R.drawable.icon_logistics,
            R.drawable.icon_product,
            R.drawable.icon_more
    };

    public static final int[] TAB_ICON_SELECTED = new int[] {
            R.drawable.icon_home_selected,
            R.drawable.icon_order_selected,
            R.drawable.icon_logistics_selected,
            R.drawable.icon_product_selected,
            R.drawable.icon_more_selected
    };
}
