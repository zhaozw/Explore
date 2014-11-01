package com.explore.exapp.base.component;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.util.AttributeSet;
import android.view.View;

import com.explore.exapp.R;

/**
 * Created by ryan on 14/10/31.
 */
public class CircleButton extends View {

    private static final String DEFAULT_COLOR = "#EEEEEE";
    private static final String DEFAULT_SHADOW_COLOR = "#666666";

    private Context mContext;
    private int icon;
    private float radius;
    private int color;

    private int padding;
    private float width;
    private float height;

    public CircleButton(Context context) {
        super(context);
        mContext = context;
    }

    public CircleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleButton);
        radius = a.getDimension(R.styleable.CircleButton_circleRadius, 0);
        color = a.getColor(R.styleable.CircleButton_circleColor, Color.parseColor(DEFAULT_COLOR));
        icon = a.getResourceId(R.styleable.CircleButton_circleIcon, 0);

        padding = 4;
    }

    public CircleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG));

        Paint paint = new Paint();
        paint.setColor(color);
        float width = radius * 2;

        int x = (int) (radius + padding);
        int y = (int) (radius + padding);

        Resources res = mContext.getResources();
        Bitmap bitmap = BitmapFactory.decodeResource(res, icon);
        int color1 = Color.parseColor(DEFAULT_SHADOW_COLOR);
        paint.setShadowLayer(padding, 0, 0, color1);
        canvas.drawCircle(x, y, radius, paint);

        Paint paint1 = new Paint();
        Matrix matrix = new Matrix();
        float rate = radius / bitmap.getWidth();
        matrix.setScale(rate, rate);

        int length = (int) (bitmap.getHeight() * rate);
        matrix.postTranslate((width - length)/2 + padding, (width - length)/2 + padding);

        if (0 != icon) {
            canvas.drawBitmap(bitmap, matrix, paint1);
        }
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setImageBitmap(Bitmap bm) {

    }
}
