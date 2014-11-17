package com.explore.exapp.activity.guide;

import android.content.Intent;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.explore.exapp.R;
import com.explore.exapp.activity.MainTabActivity;
import com.explore.exapp.activity.login.LoginActivity;
import com.explore.exapp.base.BaseActivity;
import com.explore.exapp.base.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryan on 14/11/3.
 */
public class GuideActivity extends BaseActivity implements SurfaceHolder.Callback, View.OnClickListener{

    private ViewPager guidePager;
    private Button buttonLogin;
    private Button buttonRegister;
    private RadioButton pagerItem1;
    private RadioButton pagerItem2;
    private RadioButton pagerItem3;

    private GuideViewAdapter guideViewAdapter;
    private View[] pagerViews;

    private GridView p1_logos_grid; // page1
    private RelativeLayout p1_logos_layout; // new page1
    private LinearLayout p2_layout;
    private ImageView[] p2_banner_photos;
    private ListView p2_images_list; // page2
    private LinearLayout p2_photos_layout;
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

    private Animation gridAnimation;
    private Animation listAnimation;
    private int p2_anim_index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        LogUtil.clearLogFile();

        guidePager = (ViewPager) findViewById(R.id.guide_view_pager);
        buttonLogin = (Button) findViewById(R.id.guide_btn_login);
        buttonRegister = (Button) findViewById(R.id.guide_btn_register);

        buttonLogin.setText("登录");
        buttonLogin.setOnClickListener(this);
        buttonRegister.setText("注册");
        buttonRegister.setOnClickListener(this);

        pagerItem1 = (RadioButton) findViewById(R.id.guide_pager_item1);
        pagerItem2 = (RadioButton) findViewById(R.id.guide_pager_item2);
        pagerItem3 = (RadioButton) findViewById(R.id.guide_pager_item3);

        // ViewPager title
        LayoutInflater inflater = LayoutInflater.from(this);
        pagerViews = new View[3];
        pagerViews[0] = inflater.inflate(R.layout.view_guide_pager, null);
        ((TextView) pagerViews[0].findViewById(R.id.view_guide_pager_title)).setText("微信开店首选口袋通");
        ((TextView) pagerViews[0].findViewById(R.id.view_guide_pager_subtitle)).setText("超过 200,000 大小卖家正在使用");
        pagerViews[1] = inflater.inflate(R.layout.view_guide_pager, null);
        ((TextView) pagerViews[1].findViewById(R.id.view_guide_pager_title)).setText("拍照即可上架商品");
        ((TextView) pagerViews[1].findViewById(R.id.view_guide_pager_subtitle)).setText("手机拍照，再设置价格即可开卖");
        pagerViews[2] = inflater.inflate(R.layout.view_guide_pager, null);
        ((TextView) pagerViews[2].findViewById(R.id.view_guide_pager_title)).setText("发送给你的粉丝们");
        ((TextView) pagerViews[2].findViewById(R.id.view_guide_pager_subtitle)).setText("在微信、朋友圈、微博、QQ空间上出售");

        guideViewAdapter = new GuideViewAdapter(pagerViews);
        guidePager.setAdapter(guideViewAdapter);
        guidePager.setOnPageChangeListener(new GuidePagerChangeListener());
        guidePager.setAnimationCacheEnabled(false);

        // page1
        // p1_logos_grid = (GridView)findViewById(R.id.guide_view_customer_logos);
        // p1_logos_grid.setSelector(new ColorDrawable(Color.TRANSPARENT));
        p1_icons = new ArrayList<Bitmap>();
        imgAdapter = new ImageAdapter();
        // p1_logos_grid.setAdapter(imgAdapter);
        // initGridView();
        p1_logos_layout = (RelativeLayout) findViewById(R.id.guide_page1_layout);

        gridAnimation = AnimationUtils.loadAnimation(this, R.anim.guide_page1_grid_layout);
        LayoutAnimationController lac = new LayoutAnimationController(gridAnimation);
        lac.setInterpolator(new AccelerateInterpolator());
        lac.setOrder(LayoutAnimationController.ORDER_RANDOM);
        lac.setDelay(0.2f);
        p1_logos_layout.setLayoutAnimation(lac);

        // page2
        p2_layout = (LinearLayout) findViewById(R.id.guide_page2_layout);
        p2_banner_photos = new ImageView[3];
        p2_banner_photos[0] = (ImageView) findViewById(R.id.guide_page2_photo1);
        p2_banner_photos[1] = (ImageView) findViewById(R.id.guide_page2_photo2);
        p2_banner_photos[2] = (ImageView) findViewById(R.id.guide_page2_photo3);
        p2_photos_layout = (LinearLayout) findViewById(R.id.guide_page2_photo_layout);
        p2_images_list = (ListView) findViewById(R.id.guide_view_products);
        p2_images_list.setAdapter(imgAdapter);

        listAnimation = AnimationUtils.loadAnimation(GuideActivity.this, R.anim.guide_page2_listview_layout);
        LayoutAnimationController lac2 = new LayoutAnimationController(listAnimation);
        lac2.setInterpolator(new AccelerateInterpolator());
        lac2.setOrder(LayoutAnimationController.ORDER_NORMAL);
        lac2.setDelay(0.2f);
        p2_images_list.setLayoutAnimation(lac2);

        // page3
        p3_views = (RelativeLayout) findViewById(R.id.guide_page3_layout);
        p3_banner = (ImageView) findViewById(R.id.guide_page3_banner);
        page3Canvas = (SurfaceView) findViewById(R.id.guide_page3_canvas);
        surfaceHolder = page3Canvas.getHolder();
        surfaceHolder.addCallback(this);
        p3_logos = new ImageView[5];
        p3_logos[0] = (ImageView) findViewById(R.id.guide_page3_logo1);
        p3_logos[1] = (ImageView) findViewById(R.id.guide_page3_logo2);
        p3_logos[2] = (ImageView) findViewById(R.id.guide_page3_logo3);
        p3_logos[3] = (ImageView) findViewById(R.id.guide_page3_logo4);
        p3_logos[4] = (ImageView) findViewById(R.id.guide_page3_logo5);
    }

    @Override
    public void onClick(View view) {
        if (view == buttonLogin) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else if (view == buttonRegister) {
            Intent intent = new Intent(this, MainTabActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (surfaceHolder != null) {
            surfaceHolder.addCallback(this);
            initPage3View();
        }
    }

    private void initGridView() {
        p1_icons.add(BitmapFactory.decodeResource(getResources(), R.drawable.guide_page1_logo1));
        p1_icons.add(BitmapFactory.decodeResource(getResources(), R.drawable.guide_page1_logo2));
        p1_icons.add(BitmapFactory.decodeResource(getResources(), R.drawable.guide_page1_logo3));
        p1_icons.add(BitmapFactory.decodeResource(getResources(), R.drawable.guide_page1_logo1));
        p1_icons.add(BitmapFactory.decodeResource(getResources(), R.drawable.guide_page1_logo2));
        p1_icons.add(BitmapFactory.decodeResource(getResources(), R.drawable.guide_page1_logo3));
        p1_icons.add(BitmapFactory.decodeResource(getResources(), R.drawable.guide_page1_logo1));
        p1_icons.add(BitmapFactory.decodeResource(getResources(), R.drawable.guide_page1_logo2));
        p1_icons.add(BitmapFactory.decodeResource(getResources(), R.drawable.guide_page1_logo3));
        imgAdapter.notifyDataSetChanged();
    }

    private void initPage2View() {
        p2_anim_index = 0;
        p2_layout.setVisibility(View.VISIBLE);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.guide_page2_photo_anim);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                p2_anim_index ++;
                if (p2_anim_index == 1) {
                    mHandler.sendEmptyMessage(15);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                LogUtil.debug("repeat");
                animation.setDuration(animation.getDuration() - 15);
            }
        });
        LayoutAnimationController lac = new LayoutAnimationController(animation);
        lac.setOrder(LayoutAnimationController.ORDER_NORMAL);
        lac.setDelay(0.2f);
        p2_photos_layout.setLayoutAnimation(lac);
        p2_photos_layout.setVisibility(View.VISIBLE);
        p2_photos_layout.startLayoutAnimation();
    }

    private void clearImagesView() {
        p1_icons.clear();
        // imgAdapter.notifyDataSetChanged();
    }

    private void clearPage1View() {
        p1_logos_layout.setVisibility(View.GONE);
    }

    private void clearPage2View() {
        p2_images_list.setVisibility(View.GONE);
        p2_photos_layout.setVisibility(View.GONE);
        p2_layout.setVisibility(View.GONE);
    }

    private void clearPage3View() {
        for (ImageView logo : p3_logos) {
            logo.setVisibility(View.INVISIBLE);
        }
    }

    private void initPage3View() {

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

        Animation bannerAnim = AnimationUtils.loadAnimation(this, R.anim.guide_page3_banner_anim);
        bannerAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                lineThread = new LineThread(surfaceHolder);
                lineThread.start();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        p3_banner.setAnimation(bannerAnim);
        bannerAnim.startNow();

    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what >= 0 && msg.what < p3_logos.length) {
                final int index = msg.what;
                final Animation anim = AnimationUtils.loadAnimation(GuideActivity.this, R.anim.guide_page3_logo_anim);
                p3_logos[index].setVisibility(View.VISIBLE);
                p3_logos[index].setAnimation(anim);
                anim.startNow();
                return;
            } else if (msg.what == 15) {
                p1_icons.add(BitmapFactory.decodeResource(getResources(), R.drawable.guide_page2_img2));
                p1_icons.add(BitmapFactory.decodeResource(getResources(), R.drawable.guide_page2_img3));
                p1_icons.add(BitmapFactory.decodeResource(getResources(), R.drawable.guide_page2_img4));
                p1_icons.add(BitmapFactory.decodeResource(getResources(), R.drawable.guide_page2_img5));
                p1_icons.add(BitmapFactory.decodeResource(getResources(), R.drawable.guide_page2_img6));
                p2_images_list.setVisibility(View.VISIBLE);
                imgAdapter.notifyDataSetChanged();
                p2_images_list.startLayoutAnimation();
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
            paint.setColor(Color.parseColor("#cccccc"));
            paint.setStrokeWidth(2.0f);

            float x = 0.1f;
            int i = 0;
            int delay = 10;
            boolean[] flags = new boolean[5];
            while (isRun) {

                x = x + 0.12f + 0.03f * i;

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

                    if ( !flags[j] && (pointList.get(j).y1 + x * Math.abs(c)) <= pointList.get(j).y2) {
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
                        if (!flags[j]) {
                            mHandler.sendEmptyMessage(j);
                        }
                        flags[j] = true;
                    }
                }
                holder.unlockCanvasAndPost(canvas);
                i++;

                int b = 0;
                for (boolean flag : flags) {
                    if (flag) {
                        b++;
                    }
                }

                if (b == 5) {
                    break;
                }

                try {
                    Thread.sleep(20);
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
                //p1_logos_grid.setAlpha(1.0f - v);
                p1_logos_layout.setAlpha(1.0f - v);
                p2_layout.setAlpha(v);
            } else if (i == 1) {
                p2_layout.setAlpha(1.0f -v);
                p3_views.setAlpha(v);
            } else if (i == 2) {
                p3_views.setAlpha(1.0f - v);
            }
        }

        @Override
        public void onPageSelected(int i) {
            switch (i) {
                case 0:
                    clearImagesView();
                    clearPage2View();
                    // p1_logos_grid.setVisibility(View.VISIBLE);
                    p1_logos_layout.setVisibility(View.VISIBLE);
                    p1_logos_layout.startLayoutAnimation();
                    initGridView();
                    pagerItem1.setChecked(true);
                    break;
                case 1:
                    // p1_logos_grid.setVisibility(View.GONE);
                    clearImagesView();
                    clearPage1View();
                    clearPage3View();
                    initPage2View();
                    pagerItem2.setChecked(true);
                    p3_views.setVisibility(View.GONE);
                    if (lineThread != null) {
                        lineThread.stopRun();
                        lineThread.interrupt();
                        lineThread = null;
                    }
                    break;
                case 2:
                    clearPage2View();
                    clearPage2View();
                    p3_views.setVisibility(View.VISIBLE);
                    pagerItem3.setChecked(true);
                    initPage3View();
                    break;
                default:
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
        LogUtil.debug("surfaceCreated");
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        this.surfaceHolder = surfaceHolder;
        LogUtil.debug("surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        surfaceHolder.removeCallback(this);
        LogUtil.debug("surfaceDestroyed");
    }
}
