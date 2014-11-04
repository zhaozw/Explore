package com.explore.exapp.activity.guide;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.explore.exapp.R;
import com.explore.exapp.base.BaseActivity;
import com.explore.exapp.base.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryan on 14/11/3.
 */
public class GuideActivity extends BaseActivity implements SurfaceHolder.Callback{

    private ViewPager guidePager;
    private Button buttonLogin;
    private Button buttonRegister;
    private RadioButton pagerItem1;
    private RadioButton pagerItem2;
    private RadioButton pagerItem3;

    private GuideViewAdapter guideViewAdapter;
    private View[] pagerViews;

    private GridView p1_logos_grid; // page1
    private ListView p2_imgs_listview; // page2
    private List<Bitmap> p1_icons;
    // page3
    private RelativeLayout p3_views;
    private ImageView p3_banner;
    private ImageView[] p3_logos;
    private ImageAdapter imgAdapter;
    private List<XyPoint> pointList;
    private SurfaceView page3Canvas;
    private SurfaceHolder surfaceHolder;
    private float[] rate;
    private LineThread lineThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        LogUtil.clearLogFile();

        guidePager = (ViewPager) findViewById(R.id.guide_view_pager);
        buttonLogin = (Button) findViewById(R.id.guide_btn_login);
        buttonRegister = (Button) findViewById(R.id.guide_btn_register);

        buttonLogin.setText("登录");
        buttonRegister.setText("注册");

        pagerItem1 = (RadioButton) findViewById(R.id.guide_pager_item1);
        pagerItem2 = (RadioButton) findViewById(R.id.guide_pager_item2);
        pagerItem3 = (RadioButton) findViewById(R.id.guide_pager_item3);

        // ViewPager title
        LayoutInflater inflater = LayoutInflater.from(this);
        pagerViews = new View[3];
        pagerViews[0] = inflater.inflate(R.layout.view_guide_page1, null);
        ((TextView) pagerViews[0].findViewById(R.id.view_guide_pager_title)).setText("微信开店首选口袋通");
        ((TextView) pagerViews[0].findViewById(R.id.view_guide_pager_subtitle)).setText("超过 200,000 大小卖家正在使用");
        pagerViews[1] = inflater.inflate(R.layout.view_guide_page1, null);
        ((TextView) pagerViews[1].findViewById(R.id.view_guide_pager_title)).setText("拍照即可上架商品");
        ((TextView) pagerViews[1].findViewById(R.id.view_guide_pager_subtitle)).setText("手机拍照，再设置价格即可开卖");
        pagerViews[2] = inflater.inflate(R.layout.view_guide_page1, null);
        ((TextView) pagerViews[2].findViewById(R.id.view_guide_pager_title)).setText("发送给你的粉丝们");
        ((TextView) pagerViews[2].findViewById(R.id.view_guide_pager_subtitle)).setText("在微信、朋友圈、微博、QQ空间上出售");

        guideViewAdapter = new GuideViewAdapter(pagerViews);
        guidePager.setAdapter(guideViewAdapter);
        guidePager.setOnPageChangeListener(new GuidePagerChangeListener());
        guidePager.setAnimationCacheEnabled(false);

        // page1
        p1_logos_grid = (GridView)findViewById(R.id.guide_view_customer_logos);
        p1_logos_grid.setSelector(new ColorDrawable(Color.TRANSPARENT));
        p1_icons = new ArrayList<Bitmap>();
        imgAdapter = new ImageAdapter();
        p1_logos_grid.setAdapter(imgAdapter);
        initGridView();

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.guide_page1_grid_layout);
        LayoutAnimationController lac = new LayoutAnimationController(animation);
        lac.setInterpolator(new AccelerateInterpolator());
        lac.setOrder(LayoutAnimationController.ORDER_RANDOM);
        lac.setDelay(0.3f);

        p1_logos_grid.setLayoutAnimation(lac);

        // page2
        p2_imgs_listview = (ListView) findViewById(R.id.guide_view_products);
        p2_imgs_listview.setVisibility(View.GONE);
        p2_imgs_listview.setAdapter(imgAdapter);

        Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.guide_page2_listview_layout);
        LayoutAnimationController lac2 = new LayoutAnimationController(animation1);
        lac2.setInterpolator(new AccelerateInterpolator());
        lac2.setOrder(LayoutAnimationController.ORDER_NORMAL);
        lac2.setDelay(0.3f);

        p2_imgs_listview.setLayoutAnimation(lac2);

        // page3
        p3_views = (RelativeLayout) findViewById(R.id.guide_page3_layout);
        p3_banner = (ImageView) findViewById(R.id.guide_page3_banner);

        p3_logos = new ImageView[5];
        p3_logos[0] = (ImageView) findViewById(R.id.guide_page3_logo1);
        p3_logos[1] = (ImageView) findViewById(R.id.guide_page3_logo2);
        p3_logos[2] = (ImageView) findViewById(R.id.guide_page3_logo3);
        p3_logos[3] = (ImageView) findViewById(R.id.guide_page3_logo4);
        p3_logos[4] = (ImageView) findViewById(R.id.guide_page3_logo5);
    }

    private void initGridView() {
        p1_icons.add(BitmapFactory.decodeResource(getResources(), R.drawable.logo1));
        p1_icons.add(BitmapFactory.decodeResource(getResources(), R.drawable.logo2));
        p1_icons.add(BitmapFactory.decodeResource(getResources(), R.drawable.logo3));
        p1_icons.add(BitmapFactory.decodeResource(getResources(), R.drawable.logo1));
        p1_icons.add(BitmapFactory.decodeResource(getResources(), R.drawable.logo2));
        p1_icons.add(BitmapFactory.decodeResource(getResources(), R.drawable.logo3));
        p1_icons.add(BitmapFactory.decodeResource(getResources(), R.drawable.logo1));
        p1_icons.add(BitmapFactory.decodeResource(getResources(), R.drawable.logo2));
        p1_icons.add(BitmapFactory.decodeResource(getResources(), R.drawable.logo3));
        imgAdapter.notifyDataSetChanged();
    }

    private void initListView() {
        p1_icons.add(BitmapFactory.decodeResource(getResources(), R.drawable.page2_img1));
        p1_icons.add(BitmapFactory.decodeResource(getResources(), R.drawable.page2_img2));
        p1_icons.add(BitmapFactory.decodeResource(getResources(), R.drawable.page2_img3));
        p1_icons.add(BitmapFactory.decodeResource(getResources(), R.drawable.page2_img4));
        p1_icons.add(BitmapFactory.decodeResource(getResources(), R.drawable.page2_img5));
        p1_icons.add(BitmapFactory.decodeResource(getResources(), R.drawable.page2_img6));
        imgAdapter.notifyDataSetChanged();
    }

    private void clearIamgesView() {
        p1_icons.clear();
        imgAdapter.notifyDataSetChanged();
    }

    private void clearPage3View() {
        for (int i = 0; i < p3_logos.length; i++) {
            p3_logos[i].setVisibility(View.INVISIBLE);
        }
    }


    private void initPage3View() {

        page3Canvas = (SurfaceView) findViewById(R.id.guide_page3_canvas);
        surfaceHolder = page3Canvas.getHolder();

        int banner_x = p3_banner.getLeft() + p3_banner.getWidth()/2;
        int banner_y = p3_banner.getTop() + p3_banner.getHeight();

        pointList = new ArrayList<XyPoint>();

        LinearLayout layout1 = (LinearLayout) findViewById(R.id.guide_page3_logos_layout1);
        LinearLayout layout2 = (LinearLayout) findViewById(R.id.guide_page3_logos_layout2);

        pointList.add(new XyPoint(banner_x - 100, banner_y, (p3_logos[0].getLeft() + p3_logos[0].getWidth() / 2), layout1.getTop() + p3_logos[0].getTop()));
        pointList.add(new XyPoint(banner_x - 50, banner_y, (p3_logos[3].getLeft() + p3_logos[3].getWidth() / 2), layout2.getTop() + p3_logos[3].getTop()));
        pointList.add(new XyPoint(banner_x, banner_y, (p3_logos[1].getLeft() + p3_logos[1].getWidth() / 2), layout1.getTop() + p3_logos[1].getTop()));
        pointList.add(new XyPoint(banner_x + 50, banner_y, (p3_logos[4].getLeft() + p3_logos[4].getWidth() / 2), layout2.getTop() + p3_logos[4].getTop()));
        pointList.add(new XyPoint(banner_x + 100, banner_y, (p3_logos[2].getLeft() + p3_logos[2].getWidth() / 2), layout1.getTop() + p3_logos[2].getTop()));

        int c = 0;
        rate = new float[pointList.size()];
        for (XyPoint point: pointList) {
            rate[c] = Math.abs((float) (point.x1 - point.x2) / (float) (point.y1 - point.y2));
            c++;
        }

        lineThread = new LineThread(surfaceHolder);
        lineThread.start();
    }

    private Handler mHanlder = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // super.handleMessage(msg);
            if (msg.what >= 0 && msg.what < p3_logos.length) {
                final int index = msg.what;
                final Animation anim1 = AnimationUtils.loadAnimation(GuideActivity.this, R.anim.guide_page3_logo_anim1);
                final Animation anim2 = AnimationUtils.loadAnimation(GuideActivity.this, R.anim.guide_page3_logo_anim1);
                anim1.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        p3_logos[index].startAnimation(anim2);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
                p3_logos[index].setVisibility(View.VISIBLE);
                p3_logos[index].setAnimation(anim1);
                anim1.startNow();
            }
        }
    };

    private class LineThread extends Thread {

        private SurfaceHolder holder;
        private Canvas canvas;
        private boolean isRun;

        public LineThread(SurfaceHolder holder) {
            this.holder = holder;
            isRun = true;
        }

        public void stopRun() {
            isRun = false;
            this.interrupt();
        }

        @Override
        public void run() {

            final Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.parseColor("#aaaaaa"));
            paint.setStrokeWidth(2.0f);

            float x = 0.2f;
            int i = 0;
            int delay = 10;
            boolean[] flags = new boolean[5];
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (isRun) {

                x = x + 0.08f;

                canvas = holder.lockCanvas();
                if (canvas == null) {
                    isRun = false;
                    break;
                }
                canvas.drawColor(Color.WHITE);
                for (int j = 0; j < pointList.size(); j ++) {
                    int c = j * delay - i;
                    if ( c >= 0) {
                        continue;
                    }

                    if ( flags[j] == false && (pointList.get(j).y1 + x * Math.abs(c)) <= pointList.get(j).y2) {
                        if (j <= 2) {
                            canvas.drawLine(pointList.get(j).x1, pointList.get(j).y1,
                                    pointList.get(j).x1 + (x * c * rate[j]), pointList.get(j).y1 + x * Math.abs(c),
                                    paint);
                        } else {
                            canvas.drawLine(pointList.get(j).x1, pointList.get(j).y1,
                                    pointList.get(j).x1 + Math.abs(x * c * rate[j]), pointList.get(j).y1 + x * Math.abs(c),
                                    paint);
                        }

                        flags[j] = false;
                    } else {
                        canvas.drawLine(pointList.get(j).x1, pointList.get(j).y1,
                                pointList.get(j).x2, pointList.get(j).y2,
                                paint);
                        if (flags[j] == false) {
                            LogUtil.debug("finish:" + j);
                            mHanlder.sendEmptyMessage(j);
                        }
                        flags[j] = true;
                    }
                }
                holder.unlockCanvasAndPost(canvas);
                i++;

                int b = 0;
                for (int a = 0; a < flags.length; a++) {
                    if (flags[a] == true) {
                        b++;
                    }
                }

                if (b == 5) {
                    break;
                }

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    public class XyPoint {
        int x1, x2;
        int y1, y2;

        XyPoint(int a, int b, int c, int d) {
            x1 = a;
            y1 = b;
            x2 = c;
            y2 = d;
        }

    }

    private final class GuidePagerChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int i, float v, int i2) {
            if (i == 0) {
                p1_logos_grid.setAlpha(1.0f - v);
                p2_imgs_listview.setAlpha(v);
            } else if (i == 1) {
                p2_imgs_listview.setAlpha(1.0f -v);
                p3_views.setAlpha(v);
            } else if (i == 2) {
                p3_views.setAlpha(1.0f - v);
            }
        }

        @Override
        public void onPageSelected(int i) {
            switch (i) {
                case 0:
                    p2_imgs_listview.setVisibility(View.GONE);
                    clearIamgesView();
                    p1_logos_grid.setVisibility(View.VISIBLE);
                    p1_logos_grid.startLayoutAnimation();
                    initGridView();
                    pagerItem1.setChecked(true);
                    break;
                case 1:
                    p1_logos_grid.setVisibility(View.GONE);
                    clearIamgesView();
                    p2_imgs_listview.setVisibility(View.VISIBLE);
                    p2_imgs_listview.startLayoutAnimation();
                    initListView();
                    pagerItem2.setChecked(true);
                    clearPage3View();
                    p3_views.setVisibility(View.GONE);
                    if (lineThread != null) {
                        lineThread.stopRun();
                        lineThread.interrupt();
                        lineThread = null;
                    }
                    break;
                case 2:
                    p2_imgs_listview.setVisibility(View.GONE);
                    p3_views.setVisibility(View.VISIBLE);
                    pagerItem3.setChecked(true);
                    initPage3View();
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    }

    private final class GuideViewAdapter extends PagerAdapter {
        private View[] views;

        public GuideViewAdapter(View[] views) {
            this.views = views;
        }

        @Override
        public int getCount() {
            return views.length;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // super.destroyItem(container, position, object);
            container.removeView(views[position]);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // return super.instantiateItem(container, position);
            container.addView(views[position]);
            return views[position];
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

    }

    private final class ImageAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return p1_icons.size();
        }

        @Override
        public Object getItem(int i) {
            return p1_icons.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ImageView img = new ImageView(GuideActivity.this);
            img.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT));
            img.setImageBitmap(p1_icons.get(i));
            return img;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        this.surfaceHolder = surfaceHolder;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        surfaceHolder.removeCallback(this);
    }
}
