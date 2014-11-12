package com.explore.exapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.explore.exapp.R;
import com.explore.exapp.base.BaseActivity;
import com.explore.exapp.base.util.ToastUtil;

/**
 * Created by ryan on 14/11/9.
 */
public class MainTabActivity extends BaseActivity implements View.OnClickListener {

    public static final int TAB_OVERVIEW = 1;
    public static final int TAB_ORDER = 2;
    public static final int TAB_LOGISTICES = 3;
    public static final int TAB_PRODUCT = 4;
    public static final int TAB_MORE = 5;

    private RelativeLayout tabOverview;
    private RelativeLayout tabOrder;
    private RelativeLayout tabLogistics;
    private RelativeLayout tabProduct;
    private RelativeLayout tabMore;

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

    private void refreshActionBar() {
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