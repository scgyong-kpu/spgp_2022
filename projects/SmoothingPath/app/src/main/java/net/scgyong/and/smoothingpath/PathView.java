package net.scgyong.and.smoothingpath;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * TODO: document your custom view class.
 */
public class PathView extends View {
    private static final String TAG = PathView.class.getSimpleName();
    private int mExampleColor = Color.RED; // TODO: use a default from R.color...
    Path path;
    private Paint paint;
    class Point {
        float x, y;
        float dx, dy;
    }
    ArrayList<Point> points = new ArrayList<>();
    private Listener listener;

    public interface Listener {
        public void onAdd();
    }
    public void setListener(Listener listener) {
        this.listener = listener;
    }
    public int getCount() {
        return points.size();
    }

    public PathView(Context context) {
        super(context);
        init(null, 0);
    }

    public PathView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public PathView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.PathView, defStyle, 0);

        mExampleColor = a.getColor(
                R.styleable.PathView_exampleColor,
                mExampleColor);

        a.recycle();

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2.0f);
        paint.setColor(mExampleColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        // TODO: consider storing these as member variables to reduce
//        // allocations per draw cycle.
//        int paddingLeft = getPaddingLeft();
//        int paddingTop = getPaddingTop();
//        int paddingRight = getPaddingRight();
//        int paddingBottom = getPaddingBottom();
//
//        int contentWidth = getWidth() - paddingLeft - paddingRight;
//        int contentHeight = getHeight() - paddingTop - paddingBottom;

        int ptCount = points.size();
        if (ptCount == 0) { return; }
        Point first = points.get(0);
        if (ptCount == 1) {
            canvas.drawCircle(first.x, first.y, 5.0f, paint);
            return;
        }
        canvas.drawPath(path, paint);
    }
    private void buildPath() {
        int ptCount = points.size();
        if (ptCount < 2) { return; }
        path = new Path();
        Point first = points.get(0);
        path.moveTo(first.x, first.y);
        for (int i = 1; i < ptCount; i++) {
            Point pt = points.get(i);
            path.lineTo(pt.x, pt.y);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (event.getPointerCount() > 1) {
                points.clear();
                return false;
            }
            Point pt = new Point();
            pt.x = event.getX();
            pt.y = event.getY();
            points.add(pt);
            buildPath();
            Log.d(TAG, "Points:" + points.size());

            if (listener != null) {
                listener.onAdd();
            }
            invalidate();
        }
        return super.onTouchEvent(event);
    }

    /**
     * Gets the example color attribute value.
     *
     * @return The example color attribute value.
     */
    public int getExampleColor() {
        return mExampleColor;
    }

    /**
     * Sets the view"s example color attribute value. In the example view, this color
     * is the font color.
     *
     * @param exampleColor The example color attribute value to use.
     */
    public void setExampleColor(int exampleColor) {
        mExampleColor = exampleColor;
    }

    public void clear() {
        points.clear();
        invalidate();
    }
}