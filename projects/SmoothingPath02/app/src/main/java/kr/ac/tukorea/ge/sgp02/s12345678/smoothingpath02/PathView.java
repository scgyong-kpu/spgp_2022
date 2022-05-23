package kr.ac.tukorea.ge.sgp02.s12345678.smoothingpath02;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * TODO: document your custom view class.
 */
public class PathView extends View {
    private static final float DIRECTION_FACTOR = 6;
    private Path path;
    private Bitmap bitmap;
    private float halfWidth, halfHeight;
    private PointF fighterPos = new PointF();

    public int getPointCount() {
        return points.size();
    }

    public void start() {
        int ptCount = points.size();
        if (ptCount < 2) { return; }
        PathMeasure pm = new PathMeasure(path, false);
        float length = pm.getLength();
        ValueAnimator animator = ValueAnimator.ofFloat(0, length);
        animator.setDuration((ptCount - 1) * 300);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            float[] pos = new float[2];
            float[] tan = new float[2];
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float dist = (Float) valueAnimator.getAnimatedValue();
                pm.getPosTan(dist, pos, tan);
                fighterPos.x = pos[0];
                fighterPos.y = pos[1];
                invalidate();
            }
        });
        animator.start();
    }

    public interface Listener {
        public void onAdd();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    protected Listener listener;

    protected class Point {
        float x, y;
        float dx, dy;
    }

//    private String mExampleString; // TODO: use a default from R.string...
    private int mExampleColor = Color.RED; // TODO: use a default from R.color...
//    private float mExampleDimension = 0; // TODO: use a default from R.dimen...
//    private Drawable mExampleDrawable;

//    private TextPaint mTextPaint;
//    private float mTextWidth;
//    private float mTextHeight;

    protected ArrayList<Point> points = new ArrayList<>();
    protected Paint paint;

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

        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.plane_240);
        halfWidth = bitmap.getWidth() / 2;
        halfHeight = bitmap.getHeight() / 2;
    }

//    private void invalidateTextPaintAndMeasurements() {
//        mTextPaint.setTextSize(mExampleDimension);
//        mTextPaint.setColor(mExampleColor);
//        mTextWidth = mTextPaint.measureText(mExampleString);
//
//        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
//        mTextHeight = fontMetrics.bottom;
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int ptCount = points.size();
        if (ptCount == 0) return;

        Point first = points.get(0);
        if (ptCount == 1) {
            canvas.drawCircle(first.x, first.y, 5.0f, paint);
        } else {
            canvas.drawPath(path, paint);
        }
        canvas.drawBitmap(bitmap,
                fighterPos.x - halfWidth,
                fighterPos.y - halfHeight,
                null);
    }

    protected void buildPath() {
        int ptCount = points.size();
        if (ptCount < 2) return;

        for (int i = ptCount - 2; i < ptCount; i++) {
            Point pt = points.get(i);
            if (i == 0) { // only next
                Point next = points.get(i + 1);
                pt.dx = ((next.x - pt.x) / DIRECTION_FACTOR);
                pt.dy = ((next.y - pt.y) / DIRECTION_FACTOR);
            } else if (i == ptCount - 1) { // only prev
                Point prev = points.get(i - 1);
                pt.dx = ((pt.x - prev.x) / DIRECTION_FACTOR);
                pt.dy = ((pt.y - prev.y) / DIRECTION_FACTOR);
            } else { // prev and next
                Point next = points.get(i + 1);
                Point prev = points.get(i - 1);
                pt.dx = ((next.x - prev.x) / DIRECTION_FACTOR);
                pt.dy = ((next.y - prev.y) / DIRECTION_FACTOR);
            }
        }

        Point prev = points.get(0);
        path = new Path();
        path.moveTo(prev.x, prev.y);
        for (int i = 1; i < ptCount; i++) {
            Point pt = points.get(i);
            path.cubicTo(
                    prev.x + prev.dx, prev.y + prev.dy,
                    pt.x - pt.dx, pt.y - pt.dy,
                    pt.x, pt.y);
            prev = pt;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Point point = new Point();
            point.x = event.getX();
            point.y = event.getY();
            points.add(point);
            if (listener != null) {
                listener.onAdd();
            }
            buildPath();
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
        //invalidateTextPaintAndMeasurements();
    }

    public void clear() {
        points.clear();
        invalidate();
    }
}