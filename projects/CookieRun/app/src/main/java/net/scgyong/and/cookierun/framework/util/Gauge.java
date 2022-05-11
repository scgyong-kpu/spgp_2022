package net.scgyong.and.cookierun.framework.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import net.scgyong.and.cookierun.framework.view.GameView;

public class Gauge {
    private final float width;
    private float value;
    private Paint fgPaint = new Paint();
    private Paint bgPaint = new Paint();

    public Gauge(float fgWidth, int fgColorResId, float bgWidth, int bgColorResId, float width) {
        Context context = GameView.view.getContext();
        fgPaint.setStyle(Paint.Style.STROKE);
        fgPaint.setStrokeWidth(fgWidth);
        fgPaint.setColor(ContextCompat.getColor(context, fgColorResId));
        fgPaint.setStrokeCap(Paint.Cap.ROUND);
        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setStrokeWidth(bgWidth);
        bgPaint.setColor(ContextCompat.getColor(context, bgColorResId));
        bgPaint.setStrokeCap(Paint.Cap.ROUND);
        this.width = width;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public float getValue() {
        return this.value;
    }

    public void draw(Canvas canvas, float x, float y) {
        float left = x - width / 2;
        float fg = width * value;
        canvas.drawLine(left, y, left + width, y, bgPaint);
        if (fg > 0) {
            canvas.drawLine(left, y, left + fg, y, fgPaint);
        }
    }
}
