package com.explore.exapp.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.explore.exapp.R;
import com.explore.exapp.activity.logistics.LogisticsFragment;
import com.explore.exapp.activity.more.MoreFragment;
import com.explore.exapp.activity.order.OrderFragment;
import com.explore.exapp.activity.overview.OverviewFragment;
import com.explore.exapp.activity.product.ProductFragment;
import com.explore.exapp.base.BaseActivity;
import com.explore.exapp.data.AppConstants;

/**
 * Created by ryan on 14/11/9.
 */
public class MainTabActivity extends BaseActivity implements View.OnClickListener {

    public static final int TAB_OVERVIEW = 0;
    public static final int TAB_ORDER = 1;
    public static final int TAB_LOGISTICS = 2;
    public static final int TAB_PRODUCT = 3;
    public static final int TAB_MORE = 4;

    private static final String OVERVIEW_TAG = "TAB_OVERVIEW";
    private static final String ORDER_TAG = "TAB_ORDER";
    private static final String LOGISTICS_TAG = "TAB_LOGISTICS";
    private static final String PRODUCT_TAG = "TAB_PRODUCT";
    private static final String MORE_TAG = "TAB_MORE";

    private RelativeLayout tabOverview;
    private RelativeLayout tabOrder;
    private RelativeLayout tabLogistics;
    private RelativeLayout tabProduct;
    private RelativeLayout tabMore;

    private TextView[] tabTagViews;
    private ImageView[] tabIconViews;

    private Fragment mFragment;
    private OverviewFragment overviewFragment;
    private LogisticsFragment logisticsFragment;
    private OrderFragment orderFragment;
    private ProductFragment productFragment;
    private MoreFragment moreFragment;

    public int currentTabId = 0;
    public String currentTabTag;

    private static final String[] TAB_TAG_ARRAR = new String[] {
            "TAB_OVERVIEW",
            "TAB_ORDER",
            "TAB_LOGISTICS",
            "TAB_PRODUCT",
            "TAB_MORE"
    };

    private static final int[] TAB_CONTENT_ARRAY = new int[] {
            R.id.tab_content_0,
            R.id.tab_content_1,
            R.id.tab_content_2,
            R.id.tab_content_3,
            R.id.tab_content_4
    };

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

        // overviewFragment = OverviewFragment.newInstance();
        currentTabId = 0;
        currentTabTag = OVERVIEW_TAG;
        refreshActionBar();
        updateTabPage();

        // getFragmentManager().beginTransaction().replace(R.id.frame_tab_main_pages, overviewFragment).commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tab_main_overview_tab:
                if (currentTabId == TAB_OVERVIEW) {
                    return;
                }
                currentTabTag = OVERVIEW_TAG;
                currentTabId = TAB_OVERVIEW;
                break;
            case R.id.tab_main_order_tab:
                if (currentTabId == TAB_ORDER) {
                    return;
                }
                currentTabTag = ORDER_TAG;
                currentTabId = TAB_ORDER;
                break;
            case R.id.tab_main_logistics_tab:
                if (currentTabId == TAB_LOGISTICS) {
                    return;
                }
                currentTabTag = LOGISTICS_TAG;
                currentTabId = TAB_LOGISTICS;
                break;
            case R.id.tab_main_product_tab:
                if (currentTabId == TAB_PRODUCT) {
                    return;
                }
                currentTabTag = PRODUCT_TAG;
                currentTabId = TAB_PRODUCT;
                break;
            case R.id.tab_main_more_tab:
                if (currentTabId == TAB_MORE) {
                    return;
                }
                currentTabTag = MORE_TAG;
                currentTabId = TAB_MORE;
                break;
        }
        refreshActionBar();
        updateTabPage();
    }

    private void resetTabStatus() {
        for (int i = 0; i < 5; i++) {
            tabIconViews[i].setImageResource(AppConstants.TAB_ICON[i]);
            tabTagViews[i].setTextColor(getResources().getColor(R.color.bottom_tab_tag_text));
        }
    }

    private void updateTabPage() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        mFragment = fragmentManager.findFragmentById(currentTabId);

        if (mFragment == null) {
            switch (currentTabId) {
                case TAB_OVERVIEW:
                    if (overviewFragment == null) {
                        overviewFragment = OverviewFragment.newInstance();
                    }
                    mFragment = overviewFragment;
                    break;
                case TAB_ORDER:
                    if (orderFragment == null) {
                        orderFragment = OrderFragment.newInstance();
                    }
                    mFragment =  orderFragment;
                    break;
                case TAB_LOGISTICS:
                    if (logisticsFragment == null) {
                        logisticsFragment = LogisticsFragment.newInstance();
                    }
                    mFragment = logisticsFragment;
                    break;
                case TAB_PRODUCT:
                    if (productFragment == null) {
                        productFragment = ProductFragment.newInstance();
                    }
                    mFragment = productFragment;
                    break;
                case TAB_MORE:
                    if (moreFragment == null) {
                        moreFragment = MoreFragment.newInstance();
                    }
                    mFragment = moreFragment;
                    break;
            }
        }

        if (mFragment != null) {
            // fragmentTransaction.add(TAB_CONTENT_ARRAY[currentTabId], mFragment, currentTabTag).commit();
            fragmentTransaction.replace(R.id.frame_tab_main_pages, mFragment).commit();
        } else {
            fragmentTransaction.attach(mFragment).commit();
        }

        /*
        if (mFragment == null) {
            if (OVERVIEW_TAG.equals(currentTabTag)) {
                mFragment = OverviewFragment.newInstance();
            }
            else if (LOGISTICS_TAG.equals(currentTabTag)) {
                mFragment = LogisticsFragment.newInstance();
            }
            else if (PRODUCT_TAG.equals(currentTabTag)) {
                mFragment = ProductFragment.newInstance();
            }
            else if (ORDER_TAG.equals(currentTabTag)) {
                mFragment = OrderFragment.newInstance();
            }
            else if (MORE_TAG.equals(currentTabTag)) {
                mFragment = MoreFragment.newInstance();
            }
        }

        if (mFragment != null) {
            fragmentTransaction.add(TAB_CONTENT_ARRAY[currentTabId], mFragment, currentTabTag).commit();
        } else {
            fragmentTransaction.attach(mFragment).commit();
        }
        */

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
            case TAB_LOGISTICS:
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