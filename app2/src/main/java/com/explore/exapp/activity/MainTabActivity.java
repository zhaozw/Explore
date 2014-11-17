package com.explore.exapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.explore.exapp.R;
import com.explore.exapp.activity.overview.OverviewFragment;
import com.explore.exapp.base.BaseActivity;
import com.explore.exapp.base.util.ToastUtil;
import com.explore.exapp.data.AppConstants;

/**
 * Created by ryan on 14/11/9.
 */
public class MainTabActivity extends BaseActivity implements View.OnClickListener {

    public static final int TAB_OVERVIEW = 0;
    public static final int TAB_ORDER = 1;
    public static final int TAB_LOGISTICES = 2;
    public static final int TAB_PRODUCT = 3;
    public static final int TAB_MORE = 4;

    private RelativeLayout tabOverview;
    private RelativeLayout tabOrder;
    private RelativeLayout tabLogistics;
    private RelativeLayout tabProduct;
    private RelativeLayout tabMore;

    private TextView[] tabTagViews;
    private ImageView[] tabIconViews;

    private OverviewFragment overviewFragment;

    public int currentTabId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_main);

        tabOverview = (RelativeLayout) findViewById(R.id.tab_main_overview_tab);
        tabOrder = (RelativeLayout) findViewById(R.id.tab_main_order_tab);
        tabLogistics = (RelativeLayout) findViewById(R.id.tab_main_logistics_tab);
        tabProduct = (RelativeLayout) findViewById(R.id.tab_main_product_tab);
        tabMore = (RelativeLayout) findViewById(R.id.tab_main_more_tab);

        tabOverview.setOnClickListener(this);
        tabOrder.setOnClickListener(this);
        tabLogistics.setOnClickListener(this);
        tabProduct.setOnClickListener(this);
        tabMore.setOnClickListener(this);

        tabTagViews = new TextView[5];
        tabTagViews[0] = (TextView) findViewById(R.id.tab_main_tab_tag_overview);
        tabTagViews[1] = (TextView) findViewById(R.id.tab_main_tab_tag_order);
        tabTagViews[2] = (TextView) findViewById(R.id.tab_main_tab_tag_logistics);
        tabTagViews[3] = (TextView) findViewById(R.id.tab_main_tab_tag_product);
        tabTagViews[4] = (TextView) findViewById(R.id.tab_main_tab_tag_more);

        tabIconViews = new ImageView[5];
        tabIconViews[0] = (ImageView) findViewById(R.id.tab_main_icon_overview);
        tabIconViews[1] = (ImageView) findViewById(R.id.tab_main_icon_order);
        tabIconViews[2] = (ImageView) findViewById(R.id.tab_main_icon_logistics);
        tabIconViews[3] = (ImageView) findViewById(R.id.tab_main_icon_product);
        tabIconViews[4] = (ImageView) findViewById(R.id.tab_main_icon_more);

        overviewFragment = OverviewFragment.getInstance();
        currentTabId = 0;
        refreshActionBar();

        getFragmentManager().beginTransaction().replace(R.id.frame_tab_main_pages, overviewFragment).commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tab_main_overview_tab:
                currentTabId = TAB_OVERVIEW;
                refreshActionBar();
                break;
            case R.id.tab_main_order_tab:
                currentTabId = TAB_ORDER;
                refreshActionBar();
                break;
            case R.id.tab_main_logistics_tab:
                currentTabId = TAB_LOGISTICES;
                refreshActionBar();
                break;
            case R.id.tab_main_product_tab:
                currentTabId = TAB_PRODUCT;
                refreshActionBar();
                break;
            case R.id.tab_main_more_tab:
                currentTabId = TAB_MORE;
                refreshActionBar();
                break;
        }
    }

    private void resetTabStatus() {
        for (int i = 0; i < 5; i++) {
            tabIconViews[i].setImageResource(AppConstants.TAB_ICON[i]);
            tabTagViews[i].setTextColor(getResources().getColor(R.color.bottom_tab_tag_text));
        }
    }

    private void refreshActionBar() {
        resetTabStatus();
        tabIconViews[currentTabId].setImageResource(AppConstants.TAB_ICON_SELECTED[currentTabId]);
        tabTagViews[currentTabId].setTextColor(getResources().getColor(R.color.bottom_tab_tag_selected));
        switch (currentTabId) {
            case TAB_OVERVIEW:
                mActionBar.setTitle(R.string.tab_title_overview);
                break;
            case TAB_ORDER:
                mActionBar.setTitle(R.string.tab_title_order);
                break;
            case TAB_LOGISTICES:
                mActionBar.setTitle(R.string.tab_title_logistics);
                break;
            case TAB_PRODUCT:
                mActionBar.setTitle(R.string.tab_title_product);
                break;
            case TAB_MORE:
                mActionBar.setTitle(R.string.tab_title_more);
                break;
            default:
                break;
        }
    }
}